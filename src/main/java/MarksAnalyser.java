import java.util.Scanner;

public class MarksAnalyser {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // TODO 8: read the number of learners and each mark (use readNumber),
        //         store the marks in an array, then print the report from the brief.
    }

    /** The average mark, or 0.0 if there are no marks. */
    public static double average(int[] marks) {
        // TODO 1
        return 0.0;
    }

    /** The highest mark. There's always at least one. */
    public static int highest(int[] marks) {
        // TODO 2
        return 0;
    }

    /** The lowest mark. There's always at least one. */
    public static int lowest(int[] marks) {
        // TODO 3
        return 0;
    }

    /** The percentage of marks that are 50 or more, or 0.0 if there are no marks. */
    public static double passRate(int[] marks) {
        // TODO 4
        return 0.0;
    }

    /** The symbol for one mark: A (80-100), B (70-79), C (60-69), D (50-59), E (40-49) or F (0-39). */
    public static String symbolFor(int mark) {
        // TODO 5
        return "";
    }

    /** How many learners got each symbol, in the order A, B, C, D, E, F. */
    public static int[] symbolCounts(int[] marks) {
        // TODO 6: return an array of 6 counts. Use symbolFor.
        return new int[6];
    }

    /** One line per symbol, like "A | ***", each ending with a new line. */
    public static String histogram(int[] marks) {
        // TODO 7: build the six lines from symbolCounts. See the brief for the format.
        return "";
    }

    /** Asks until the number is from min to max (P05's readPercent, made general). */
    public static int readNumber(Scanner input, String prompt, int min, int max, String error) {
        // TODO 8: print the prompt, read a number, and keep asking until it's
        //         from min to max, printing the error after each bad one.
        return 0;
    }
}
