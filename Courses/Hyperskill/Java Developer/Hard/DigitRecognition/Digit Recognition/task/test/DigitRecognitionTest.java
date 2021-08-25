import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.List;

class Clue {
    String answer;
    Clue(int ans) {
        answer = Integer.toString(ans);
    }
}

public class DigitRecognitionTest extends StageTest<Clue> {

    @Override
    public List<TestCase<Clue>> generate() {
        return List.of(
            new TestCase<Clue>()
                .setAttach(new Clue(1))
                .setInput(
                    "_X_\n" +
                    "_X_\n" +
                    "XX_\n" +
                    "XX_\n" +
                    "_XX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(2))
                .setInput(
                    "XX_\n" +
                    "__X\n" +
                    "__X\n" +
                    "X__\n" +
                    "XXX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(7))
                .setInput(
                    "XXX\n" +
                    "X_X\n" +
                    "__X\n" +
                    "__X\n" +
                    "__X\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(9))
                .setInput(
                    "XXX\n" +
                    "X_X\n" +
                    "XXX\n" +
                    "__X\n" +
                    "_XX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(0))
                .setInput(
                    "XXX\n" +
                    "X_X\n" +
                    "X_X\n" +
                    "X_X\n" +
                    "_XX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(3))
                .setInput(
                    "XXX\n" +
                    "__X\n" +
                    "XXX\n" +
                    "__X\n" +
                    "_XX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(4))
                .setInput(
                    "X_X\n" +
                    "__X\n" +
                    "XXX\n" +
                    "__X\n" +
                    "__X\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(5))
                .setInput(
                    "XXX\n" +
                    "X__\n" +
                    "XXX\n" +
                    "__X\n" +
                    "XX_\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(8))
                .setInput(
                    "XXX\n" +
                    "X_X\n" +
                    "XXX\n" +
                    "X_X\n" +
                    "XX_\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(6))
                .setInput(
                    "_XX\n" +
                    "X__\n" +
                    "XXX\n" +
                    "X_X\n" +
                    "XXX\n"),




            new TestCase<Clue>()
                .setAttach(new Clue(0))
                .setInput(
                    "XXX\n" +
                    "X_X\n" +
                    "X_X\n" +
                    "X_X\n" +
                    "XXX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(1))
                .setInput(
                    "_X_\n" +
                    "_X_\n" +
                    "_X_\n" +
                    "_X_\n" +
                    "_X_\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(2))
                .setInput(
                    "XXX\n" +
                    "__X\n" +
                    "XXX\n" +
                    "X__\n" +
                    "XXX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(3))
                .setInput(
                    "XXX\n" +
                    "__X\n" +
                    "XXX\n" +
                    "__X\n" +
                    "XXX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(4))
                .setInput(
                    "X_X\n" +
                    "X_X\n" +
                    "XXX\n" +
                    "__X\n" +
                    "__X\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(5))
                .setInput(
                    "XXX\n" +
                    "X__\n" +
                    "XXX\n" +
                    "__X\n" +
                    "XXX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(6))
                .setInput(
                    "XXX\n" +
                    "X__\n" +
                    "XXX\n" +
                    "X_X\n" +
                    "XXX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(7))
                .setInput(
                    "XXX\n" +
                    "__X\n" +
                    "__X\n" +
                    "__X\n" +
                    "__X\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(8))
                .setInput(
                    "XXX\n" +
                    "X_X\n" +
                    "XXX\n" +
                    "X_X\n" +
                    "XXX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(9))
                .setInput(
                    "XXX\n" +
                    "X_X\n" +
                    "XXX\n" +
                    "__X\n" +
                    "XXX\n")

            );
    }

    @Override
    public CheckResult check(String reply, Clue clue) {
        boolean contains = reply.contains(clue.answer);
        if (!contains) {
            return CheckResult.wrong("");
        }
        for (int i = 0; i < 10; i++) {
            String num = Integer.toString(i);
            if (!num.equals(clue.answer) && reply.contains(num)) {
                return new CheckResult(false,
                    "Along with the right answer, " +
                        "number " + i + " was found in the output");
            }
        }
        return CheckResult.correct();
    }
}
