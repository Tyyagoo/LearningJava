import org.hyperskill.hstest.exception.outcomes.WrongAnswer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Generation {

    private static final Pattern pattern = Pattern.compile("alive:(\\s+)?(\\d+)", Pattern.MULTILINE);
    private final boolean[][] cells;
    private final int size;


    public Generation(List<String> lines) {

        size = lines.size();
        cells = new boolean[size][size];

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.size(); j++) {
                cells[i][j] = lines.get(i).charAt(j) == 'o';
            }
        }
    }

    public static List<Generation> getGenerations(String[] generationsFromOutput, int correctSize) {

        List<Generation> generations = new ArrayList<>();

        for (int i = 1; i < generationsFromOutput.length; i++) {
            String generation = generationsFromOutput[i].toLowerCase();

            Matcher matcher = pattern.matcher(generation);

            if (!matcher.find()) {
                throw new WrongAnswer("Can't find number of Alive cells in the generation with number "
                        + (i + 1) + ".\nYour output should contain 'Alive: DDD', where D is a digit!");
            }

            int aliveFromOutput = Integer.parseInt(matcher.group(2));

            List<String> lines = Arrays.stream(generation.split("\n"))
                    .filter(line -> !line.contains("alive") && !line.contains("#") && !line.isEmpty())
                    .collect(Collectors.toList());

            if (lines.size() != correctSize) {
                throw new WrongAnswer("Generation #" + (i + 1) + " map size is wrong!\nYour size: " + lines.size() + "\n" +
                        "Expected size: " + correctSize);
            }

            int activeCellsInGeneration = (int) lines.stream().map(line -> line.split("")).flatMap(cells -> Arrays.stream(cells.clone())).filter(cell -> cell.equals("o")).count();

            if (activeCellsInGeneration != aliveFromOutput) {
                throw new WrongAnswer("Active cells in the generation #" + (i + 1) + ": " + activeCellsInGeneration + "\n" +
                        "Your output: 'Alive: " + aliveFromOutput + "'");
            }

            for (String line : lines) {
                if (line.length() != correctSize) {
                    throw new WrongAnswer("Generation map size is wrong!\nYour size: " + lines.size() + "\n" +
                            "Expected size: " + correctSize + "\nMake sure you don't print extra spaces at the end of the lines!");
                }
            }

            generations.add(new Generation(lines));
        }

        return generations;
    }

    public boolean isCorrectNextGeneration(Generation generation) {

        if (generation.size != size) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                int numberOfNeighbours = 0;

                int up = i - 1 < 0 ? size - 1 : i - 1;
                int down = i + 1 == size ? 0 : i + 1;
                int right = j + 1 == size ? 0 : j + 1;
                int left = j - 1 < 0 ? size - 1 : j - 1;

                if (cells[up][left]) numberOfNeighbours++;
                if (cells[up][j]) numberOfNeighbours++;
                if (cells[up][right]) numberOfNeighbours++;
                if (cells[i][left]) numberOfNeighbours++;
                if (cells[i][right]) numberOfNeighbours++;
                if (cells[down][left]) numberOfNeighbours++;
                if (cells[down][j]) numberOfNeighbours++;
                if (cells[down][right]) numberOfNeighbours++;

                if ((numberOfNeighbours < 2 || numberOfNeighbours > 3) && generation.cells[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


    public static void isCorrectGenerationsList(List<Generation> generations) {

        if (generations.size() <= 1) {
            return;
        }

        for (int i = 1; i < generations.size(); i++) {
            if (!generations.get(i - 1).isCorrectNextGeneration(generations.get(i))) {
                throw new WrongAnswer("Generation #" + (i + 1) + " is wrong!");
            }
        }
    }
}
