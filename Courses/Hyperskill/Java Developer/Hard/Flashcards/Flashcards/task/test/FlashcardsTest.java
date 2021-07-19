import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Arrays;
import java.util.List;

public class FlashcardsTest extends StageTest<String> {
    
    @Override
    public List<TestCase<String>> generate() {
        return  Arrays.asList(
                new TestCase<String>()
                .setDynamicTesting(() -> {
                    TestedProgram main = new TestedProgram();
                    
                    String output = main.start();
                    String[] lines = output.toLowerCase().trim().split("\n");
                    
                    int lineCount = lines.length;
                    if (lineCount != 4) {
                        return CheckResult.wrong("Your program should print four lines");
                    }
                    
                    String lineOne = lines[0];
                    if (!lineOne.trim().equals("card:")) {
                        return CheckResult.wrong("Your program should print \"Card:\" as the first line");
                    }
                    
                    String lineTwo = lines[1];
                    if (lineTwo.equals("") || lineTwo.matches("\\s+")) {
                        return CheckResult.wrong("Your program should print a term on the second line");
                    }
                    
                    String lineThree = lines[2];
                    if (!lineThree.trim().equals("definition:")) {
                        return CheckResult.wrong("Your program should print \"Definition:\" as the third line");
                    }
                    
                    String lineFour = lines[3];
                    if (lineFour.equals("") || lineFour.matches("\\s+")) {
                        return CheckResult.wrong("Your program should print a definition on the fourth line");
                    }
                    
                    return CheckResult.correct();
                    
                })
        );
    }
    
}
