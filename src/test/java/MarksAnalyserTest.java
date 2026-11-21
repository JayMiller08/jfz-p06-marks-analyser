import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MarksAnalyserTest {

    private static final double CLOSE_ENOUGH = 0.001;

    @Test
    @DisplayName("average adds up the marks and divides by how many there are")
    void average() {
        assertEquals(66.5, MarksAnalyser.average(new int[] {72, 45, 88, 61}), CLOSE_ENOUGH,
                "72 + 45 + 88 + 61 = 266, and 266 / 4 = 66.5. Cast to double before dividing.");
        assertEquals(50.0, MarksAnalyser.average(new int[] {50}), CLOSE_ENOUGH, "One mark is its own average.");
        assertEquals(0.0, MarksAnalyser.average(new int[] {}), CLOSE_ENOUGH,
                "With no marks, return 0.0 instead of dividing by zero.");
    }

    @Test
    @DisplayName("highest and lowest find the extremes, wherever they are")
    void highestAndLowest() {
        int[] marks = {61, 45, 88, 72, 31};
        assertEquals(88, MarksAnalyser.highest(marks), "88 is the highest. It's in the middle of the array.");
        assertEquals(31, MarksAnalyser.lowest(marks), "31 is the lowest. It's the last element: check your loop reaches it.");
        assertEquals(12, MarksAnalyser.highest(new int[] {12}), "With one mark, it's the highest.");
        assertEquals(12, MarksAnalyser.lowest(new int[] {12}), "With one mark, it's also the lowest.");
    }

    @Test
    @DisplayName("highest works when the first mark is the biggest, lowest when it's the smallest")
    void extremesAtTheStart() {
        assertEquals(95, MarksAnalyser.highest(new int[] {95, 40, 60}), "Start from the first mark, not from 0.");
        assertEquals(5, MarksAnalyser.lowest(new int[] {5, 40, 60}),
                "Start from the first mark. Starting the lowest at 0 would give 0, which isn't a mark here.");
    }

    @Test
    @DisplayName("passRate is the percentage of marks of 50 or more")
    void passRate() {
        assertEquals(60.0, MarksAnalyser.passRate(new int[] {72, 45, 88, 61, 39}), CLOSE_ENOUGH,
                "3 of 5 passed: 3 * 100.0 / 5 = 60.0.");
        assertEquals(50.0, MarksAnalyser.passRate(new int[] {50, 49}), CLOSE_ENOUGH,
                "50 is a pass and 49 isn't, so it's 1 of 2.");
        assertEquals(0.0, MarksAnalyser.passRate(new int[] {}), CLOSE_ENOUGH, "With no marks, return 0.0.");
    }

    @Test
    @DisplayName("symbolFor: the first and last mark of every symbol")
    void symbolBoundaries() {
        assertEquals("A", MarksAnalyser.symbolFor(100), "100 is an A.");
        assertEquals("A", MarksAnalyser.symbolFor(80), "80 is the lowest A.");
        assertEquals("B", MarksAnalyser.symbolFor(79), "79 is the highest B.");
        assertEquals("B", MarksAnalyser.symbolFor(70), "70 is the lowest B.");
        assertEquals("C", MarksAnalyser.symbolFor(69), "69 is the highest C.");
        assertEquals("C", MarksAnalyser.symbolFor(60), "60 is the lowest C.");
        assertEquals("D", MarksAnalyser.symbolFor(59), "59 is the highest D.");
        assertEquals("D", MarksAnalyser.symbolFor(50), "50 is the lowest D.");
        assertEquals("E", MarksAnalyser.symbolFor(49), "49 is the highest E.");
        assertEquals("E", MarksAnalyser.symbolFor(40), "40 is the lowest E.");
        assertEquals("F", MarksAnalyser.symbolFor(39), "39 is an F.");
        assertEquals("F", MarksAnalyser.symbolFor(0), "0 is an F.");
    }

    @Test
    @DisplayName("symbolCounts counts each symbol, in the order A to F")
    void symbolCounts() {
        assertArrayEquals(new int[] {2, 1, 2, 1, 1, 1},
                MarksAnalyser.symbolCounts(new int[] {85, 72, 64, 64, 55, 41, 12, 90}),
                "85 and 90 are A, 72 is B, both 64s are C, 55 is D, 41 is E and 12 is F.");
        assertArrayEquals(new int[] {0, 0, 0, 0, 0, 3},
                MarksAnalyser.symbolCounts(new int[] {10, 20, 30}),
                "Always return 6 counts, even when most of them are 0.");
    }

    @Test
    @DisplayName("histogram draws one line per symbol")
    void histogram() {
        assertEquals("A | **\nB | *\nC | **\nD | *\nE | *\nF | *\n",
                MarksAnalyser.histogram(new int[] {85, 72, 64, 64, 55, 41, 12, 90}),
                "Each line is the symbol, a space, a bar, a space and one * per learner, then a new line.");
    }

    @Test
    @DisplayName("histogram: a symbol nobody got has nothing after the bar")
    void histogramEmptyLines() {
        assertEquals("A |\nB |\nC |\nD | ***\nE |\nF |\n",
                MarksAnalyser.histogram(new int[] {50, 55, 59}),
                "With a count of 0, the line is the symbol and the bar, with no space after the bar.");
    }

    @Test
    @DisplayName("No method changes the array it's given")
    void arrayUnchanged() {
        int[] marks = {72, 45, 88, 61};
        MarksAnalyser.average(marks);
        MarksAnalyser.highest(marks);
        MarksAnalyser.lowest(marks);
        MarksAnalyser.passRate(marks);
        MarksAnalyser.symbolCounts(marks);
        MarksAnalyser.histogram(marks);
        assertArrayEquals(new int[] {72, 45, 88, 61}, marks,
                "The caller's array must stay as it was. A method that sorts its parameter changes the caller's array too.");
    }

    @Test
    @DisplayName("main: reads the marks and prints the report")
    void mainReport() throws Exception {
        String output = ConsoleRunner.run(MarksAnalyser::main, "4\n72\n45\n88\n61\n");
        assertEquals("Number of learners (1-50): Mark 1: Mark 2: Mark 3: Mark 4: "
                        + "Average: 66.5\nHighest: 88\nLowest: 45\nPass rate: 75.0%\n"
                        + "A | *\nB | *\nC | *\nD |\nE | *\nF |\n", output,
                "Compare with the example run in the brief, line by line.");
    }

    @Test
    @DisplayName("main: asks again for numbers outside their range")
    void mainValidation() throws Exception {
        String output = ConsoleRunner.run(MarksAnalyser::main, "0\n51\n2\n101\n-1\n50\n49\n");
        assertTrue(output.startsWith("Number of learners (1-50): Enter a number from 1 to 50.\n"
                        + "Number of learners (1-50): Enter a number from 1 to 50.\n"
                        + "Number of learners (1-50): Mark 1: Enter a mark from 0 to 100.\n"
                        + "Mark 1: Enter a mark from 0 to 100.\nMark 1: Mark 2: "),
                "0 and 51 learners are rejected, then 101 and -1 as marks. Got: " + output);
        assertTrue(output.contains("Pass rate: 50.0%\n"), "With 50 and 49, the pass rate is 50.0%. Got: " + output);
    }
}
