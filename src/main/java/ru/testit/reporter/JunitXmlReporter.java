package ru.testit.reporter;

import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.NullStoryReporter;
import ru.testit.reporter.models.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JunitXmlReporter extends NullStoryReporter {
    private Test executableTest = null;
    private final XmlFileWriter writer;
    private final List<Test> tests = new ArrayList<>();
    private final ThreadLocal<Story> executableStory = new InheritableThreadLocal<>();

    public JunitXmlReporter() {
        writer = new XmlFileWriter(Paths.get(System.getProperty("user.dir"), "output"));
    }

    @Override
    public void beforeStory(final Story story, final boolean givenStory) {
        if (!givenStory) {
            executableStory.set(story);
        }
    }

    @Override
    public void afterStory(final boolean givenStory) {
        if (!givenStory) {
            executableStory.remove();
        }
    }

    @Override
    public void beforeScenario(final Scenario scenario) {
        startTestCase(scenario);
    }

    protected void startTestCase(Scenario scenario) {
        executableTest = ScenarioParser
                .parseScenario(executableStory.get(), scenario);
    }

    @Override
    public void afterScenario() {
        tests.add(executableTest);

        writer.write(tests);
    }

    @Override
    public void failed(final String step, final Throwable cause) {
        executableTest.setThrowable(cause);
    }
}
