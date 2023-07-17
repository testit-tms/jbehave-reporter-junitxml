# JUnit XML reporter for JBehave

This tool converts output to a xml report, suitable for applications that expect junit xml reports.

## Getting Started

### Installation

#### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>ru.testit</groupId>
    <artifactId>jbehave-reporter-junitxml</artifactId>
    <version>LATEST_VERSION</version>
    <scope>compile</scope>
</dependency>
```

#### Gradle users

Add this dependency to your project's build file:

```groovy
implementation "ru.testit:jbehave-reporter-junitxml:LATEST_VERSION"
```

### Configuration

Add reporter to JBehave configuration:

```java
import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.embedder.NullEmbedderMonitor;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.reporters.NullStoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.testit.reporter.JunitXmlReporter;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TestStoriesRunner extends ConfigurableEmbedder {
    public Embedder embedder;
    @TempDir
    Path temp;

    @Override
    @Test
    public void run() {
        embedder = new Embedder();
        embedder.useEmbedderMonitor(new NullEmbedderMonitor());
        embedder.useEmbedderControls(new EmbedderControls()
                .doGenerateViewAfterStories(false)
                .doFailOnStoryTimeout(false)
                .doBatch(false)
                .doIgnoreFailureInStories(true)
                .doIgnoreFailureInView(true)
                .doVerboseFailures(false)
                .doVerboseFiltering(false)
        );
        embedder.useConfiguration(configuration());
        embedder.useCandidateSteps(stepsFactory().createCandidateSteps());

        File dir = new File("./src/test/resources/stories");
        List<String> stories = new ArrayList<>();

        for (File file : dir.listFiles()){
            if ( file.isFile() )
                stories.add("stories/" + file.getName());
        }

        embedder.runStoriesAsPaths(stories);
    }

    public Configuration configuration() {
        final JunitXmlReporter reporter = new JunitXmlReporter();

        return new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(this.getClass()))
                .useStoryReporterBuilder(
                        new TestStoryReporterBuilder(temp.toFile())
                                .withReporters(reporter))
                .useDefaultStoryReporter(new NullStoryReporter());
    }

    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(embedder.configuration(), new SampleSteps());
    }

    static class TestStoryReporterBuilder extends StoryReporterBuilder {

        private final File outputDirectory;

        TestStoryReporterBuilder(final File outputDirectory) {
            this.outputDirectory = outputDirectory;
        }

        @Override
        public File outputDirectory() {
            return outputDirectory;
        }
    }
}
```

# Contributing

You can help to develop the project. Any contributions are **greatly appreciated**.

* If you have suggestions for adding or removing projects, feel free to [open an issue](https://github.com/testit-tms/jbehave-reporter-junitxml/issues/new) to discuss it, or directly create a pull request after you edit the *README.md* file with necessary changes.
* Please make sure you check your spelling and grammar.
* Create individual PR for each suggestion.
* Please also read through the [Code Of Conduct](https://github.com/testit-tms/jbehave-reporter-junitxml/blob/main/CODE_OF_CONDUCT.md) before posting your first idea as well.

# License

Distributed under the Apache-2.0 License. See [LICENSE](https://github.com/testit-tms/jbehave-reporter-junitxml/blob/main/LICENSE.md) for more information.


