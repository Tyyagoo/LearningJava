import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.List;


public class GameOfLifeTest extends StageTest<String> {

    int[] sizes = {5, 6, 7, 8, 9, 10};

    @DynamicTest(data = "sizes")
    CheckResult test(int size) {

        TestedProgram program = new TestedProgram();
        program.start();

        String output = program.execute(String.valueOf(size));

        if (output.isEmpty()) {
            return CheckResult.wrong("Looks like your output is empty!");
        }

        if (!output.toLowerCase().contains("generation")) {
            return CheckResult.wrong("Can't find 'Generation' word in the output!");
        }

        String[] generations = output.toLowerCase().split("generation");

        if (generations.length < 11) {
            return CheckResult.wrong("Your output should contain not less than 10 generations!");
        }

        List<Generation> generationsList = Generation.getGenerations(generations, size);

        Generation.isCorrectGenerationsList(generationsList);

        return CheckResult.correct();
    }
}
