import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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
                    "2\n_X_\n" +
                        "_X_\n" +
                        "XX_\n" +
                        "XX_\n" +
                        "_XX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(2))
                .setInput(
                    "2\nXX_\n" +
                        "__X\n" +
                        "__X\n" +
                        "X__\n" +
                        "XXX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(7))
                .setInput(
                    "2\nXXX\n" +
                        "X_X\n" +
                        "__X\n" +
                        "__X\n" +
                        "__X\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(9))
                .setInput(
                    "2\nXXX\n" +
                        "X_X\n" +
                        "XXX\n" +
                        "__X\n" +
                        "_XX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(0))
                .setInput(
                    "2\nXXX\n" +
                        "X_X\n" +
                        "X_X\n" +
                        "X_X\n" +
                        "_XX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(3))
                .setInput(
                    "2\nXXX\n" +
                        "__X\n" +
                        "XXX\n" +
                        "__X\n" +
                        "_XX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(4))
                .setInput(
                    "2\nX_X\n" +
                        "__X\n" +
                        "XXX\n" +
                        "__X\n" +
                        "__X\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(5))
                .setInput(
                    "2\nXXX\n" +
                        "X__\n" +
                        "XXX\n" +
                        "__X\n" +
                        "XX_\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(8))
                .setInput(
                    "2\nXXX\n" +
                        "X_X\n" +
                        "XXX\n" +
                        "X_X\n" +
                        "XX_\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(6))
                .setInput(
                    "2\n_XX\n" +
                        "X__\n" +
                        "XXX\n" +
                        "X_X\n" +
                        "XXX\n"),




            new TestCase<Clue>()
                .setAttach(new Clue(0))
                .setInput(
                    "2\nXXX\n" +
                        "X_X\n" +
                        "X_X\n" +
                        "X_X\n" +
                        "XXX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(1))
                .setInput(
                    "2\n_X_\n" +
                        "_X_\n" +
                        "_X_\n" +
                        "_X_\n" +
                        "_X_\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(2))
                .setInput(
                    "2\nXXX\n" +
                        "__X\n" +
                        "XXX\n" +
                        "X__\n" +
                        "XXX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(3))
                .setInput(
                    "2\nXXX\n" +
                        "__X\n" +
                        "XXX\n" +
                        "__X\n" +
                        "XXX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(4))
                .setInput(
                    "2\nX_X\n" +
                        "X_X\n" +
                        "XXX\n" +
                        "__X\n" +
                        "__X\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(5))
                .setInput(
                    "2\nXXX\n" +
                        "X__\n" +
                        "XXX\n" +
                        "__X\n" +
                        "XXX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(6))
                .setInput(
                    "2\nXXX\n" +
                        "X__\n" +
                        "XXX\n" +
                        "X_X\n" +
                        "XXX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(7))
                .setInput(
                    "2\nXXX\n" +
                        "__X\n" +
                        "__X\n" +
                        "__X\n" +
                        "__X\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(8))
                .setInput(
                    "2\nXXX\n" +
                        "X_X\n" +
                        "XXX\n" +
                        "X_X\n" +
                        "XXX\n"),

            new TestCase<Clue>()
                .setAttach(new Clue(9))
                .setInput(
                    "2\nXXX\n" +
                        "X_X\n" +
                        "XXX\n" +
                        "__X\n" +
                        "XXX\n")

        );
    }

    @Override
    public CheckResult check(String reply, Clue clue) {
        List<String> lines = reply.lines().collect(Collectors.toList());

        if (lines.size() < 1) {
            return CheckResult.wrong("Looks like your output is empty!");
        }

        String lastLine = lines.get(lines.size() - 1);

        String[] lastLineWords = lastLine.split("\\s+");

        List<String> foundedNumbers = new ArrayList<>();
        for (String word : lastLineWords) {
            if (word.matches("[0-9]+")) {
                foundedNumbers.add(word);
            }
        }

        if (foundedNumbers.size() > 1) {
            String numbers = String.join(", ", foundedNumbers);
            return new CheckResult(false,
                "Last line contains several numbers, " +
                    "you should output only one number.\n" +
                    "Numbers found: " + numbers);
        }

        if (foundedNumbers.size() == 0) {
            return new CheckResult(false,
                "Last line in output " +
                    "doesn't contain any numbers.");
        }

        String founded = foundedNumbers.get(0);

        if (!founded.equals(clue.answer)) {
            return new CheckResult(false,
                "Last line contains number " + founded + " " +
                    "but expected to contain number " + clue.answer);
        }

        return CheckResult.correct();
    }
}
