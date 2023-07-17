package ru.testit.reporter;

import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import ru.testit.reporter.models.Test;


public class ScenarioParser {

    static public Test parseScenario(final Story story, final Scenario scenario) {
        String name = scenario.getTitle();
        String classname = story.getPath();

        return new Test()
                .setName(name)
                .setClassName(classname);
    }
}
