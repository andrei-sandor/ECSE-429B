package StoryTest;

import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/Features/Todos"},
//        features = {"src/test/java/run/f2-getTodo.feature", "src/test/java/run/f1-createTodo.feature"},
        glue = {"StoryTest"}

)
public class Run {
}
