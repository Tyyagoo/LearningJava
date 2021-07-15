import life.GameOfLife;
import org.assertj.swing.fixture.JLabelFixture;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.SwingTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.swing.SwingComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hyperskill.hstest.testcase.CheckResult.correct;
import static org.hyperskill.hstest.testcase.CheckResult.wrong;

public class GameOfLifeTest extends SwingTest {

    public GameOfLifeTest() {
        super(new GameOfLife());
    }

    @SwingComponent(name = "GenerationLabel")
    JLabelFixture generationLabel;

    @SwingComponent(name = "AliveLabel")
    JLabelFixture aliveLabel;

    @DynamicTest(order = 1)
    CheckResult testWindow() {
        requireVisible(window);
        return correct();
    }

    @DynamicTest(order = 2)
    CheckResult testGenerationLabel() {
        requireEnabled(generationLabel);
        requireVisible(generationLabel);
        return correct();
    }

    @DynamicTest(order = 3)
    CheckResult testAliveLabel() {
        requireEnabled(aliveLabel);
        requireVisible(aliveLabel);
        return correct();
    }

    @DynamicTest(order = 4)
    CheckResult testForInteger() {

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(generationLabel.text());

        if (!matcher.find()) {
            return wrong("The 'GenerationLabel' doesn't contain an integer number!");
        }

        matcher = pattern.matcher(aliveLabel.text());

        if (!matcher.find()) {
            return wrong("The 'AliveLabel' doesn't contain an integer number!");
        }

        return correct();
    }
}
