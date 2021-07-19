import life.GameOfLife;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.fixture.JToggleButtonFixture;
import org.hyperskill.hstest.common.Utils;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
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

    @SwingComponent(name = "PlayToggleButton")
    JToggleButtonFixture playButton;

    @SwingComponent(name = "ResetButton")
    JButtonFixture resetButton;

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
    CheckResult testForIntegerInLabels() {

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

    @DynamicTest(order = 5)
    CheckResult testPlayButton() {
        requireEnabled(playButton);
        requireVisible(playButton);
        playButton.click();
        playButton.click();
        return correct();
    }

    @DynamicTest(order = 6)
    CheckResult testResetButton() {
        requireEnabled(resetButton);
        requireVisible(resetButton);
        resetButton.click();
        return correct();
    }

    @DynamicTest(order = 7)
    CheckResult testButtonsActions() {

        playButton.uncheck();
        resetButton.click();

        int firstGenerationNumber = getNumberFromLabel(generationLabel);
        Utils.sleep(200);
        int secondGenerationNumber = getNumberFromLabel(generationLabel);

        if (firstGenerationNumber != secondGenerationNumber) {
            return wrong("When PlayToggleButton is not toggled the program shouldn't generate new generations! The number in GenerationLabel shouldn't change!");
        }

        resetButton.click();
        firstGenerationNumber = getNumberFromLabel(generationLabel);
        playButton.check();
        Utils.sleep(200);
        secondGenerationNumber = getNumberFromLabel(generationLabel);

        if (firstGenerationNumber == secondGenerationNumber) {
            return wrong("When PlayToggleButton is toggled the program should generate new generations! The number in GenerationLabel should change!\n" +
                    "Also make sure your program doesn't sleep more than 150 ms after each generation!");
        }

        return correct();
    }

    private static int getNumberFromLabel(JLabelFixture labelFixture) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(labelFixture.text());

        System.out.println(labelFixture.text());

        if (!matcher.find()) {
            throw new WrongAnswer("Can't find a number in the '" + labelFixture.text() + "'!");
        }

        return Integer.parseInt(matcher.group());
    }
}
