import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class Tests {

    @Test
    void factor28() {
        assertEquals(Arrays.asList(2, 2, 7), TournamentOptions.getPrimefactors(28));
    }

    @Test
    void manualGetPrimefactorsTest() {
        for (int i = 3; i < 1000; i++) {
            Integer product = 1;
            for (int num : TournamentOptions.getPrimefactors(i)) {
                product *= num;
            }
            assertEquals(product, i);
        }
    }

    @Test
    void OptionsTest1_10() {
        int number = 10;
        List<List<Integer>> output = Arrays.asList(
                Arrays.asList(1, 10, 0, 3),
                Arrays.asList(5, 2, 3, 3),
                Arrays.asList(0, 5, 5, 1, 3),
                Arrays.asList(2, 2, 3, 1, 1));

        TournamentOptions Options = new TournamentOptions(number);
        assertTrue(output.equals(Options.allOptions));
    }


    /**
     * If the number of participants is equal to a number of 2, the code only presents the even options. It throws an
     * exception upon entering uneven pool options. This has been tested manually. Here the toSting() output is tested.
     */
    @Test
    void OptionsTest2_2() {
        int number = 2;
        TournamentOptions Options = new TournamentOptions(number);
        System.out.println(Options.toString());
        String output = "option 1: 1 Pools of size 2. Possible number of post-pool-rounds: 0";
        assertFalse(output.equals(Options.toString()));
    }

    @Test
    void OptionsTest3_4() {
        int number = 4;
        List<List<Integer>> output = Arrays.asList(
                Arrays.asList(1, 4, 0, 1),
                Arrays.asList(2, 2, 1, 1));

        TournamentOptions Options = new TournamentOptions(number);
        System.out.println(Options.allOptions);
        assertTrue(output.equals(Options.allOptions));
    }

    @Test
    void OptionsTest4_6() {
        int number = 6;
        List<List<Integer>> output = Arrays.asList(
                Arrays.asList(1, 6, 0, 2),
                Arrays.asList(3, 2, 2, 2),
                Arrays.asList(0, 3, 3, 1, 2));
        TournamentOptions Options = new TournamentOptions(number);
        assertTrue(output.equals(Options.allOptions));
    }

    @Test
    void OptionsTest7_250() {
        int number = 250;
        List<List<Integer>> output = Arrays.asList(
                Arrays.asList(1, 250, 0, 7), Arrays.asList(5, 50, 3, 7), Arrays.asList(25, 10, 5, 7), Arrays.asList(125, 2, 7, 7),
                Arrays.asList(0, 125, 125, 1, 7),Arrays.asList(4, 42, 41, 2, 7), Arrays.asList(5, 36, 35, 3, 7), Arrays.asList(7, 28, 27, 3, 7),
                Arrays.asList(12, 18, 17, 4, 7), Arrays.asList(16, 14, 13, 4, 7), Arrays.asList(19, 12, 11, 5, 7), Arrays.asList(40, 6, 5, 6, 7),
                Arrays.asList(61, 4, 3, 6, 7), Arrays.asList(2, 62, 63, 1, 6), Arrays.asList(29, 8, 9, 5, 7), Arrays.asList(60, 4, 5, 6, 7),
                Arrays.asList(122, 2, 3, 7, 7));

        TournamentOptions Options = new TournamentOptions(number);
        assertTrue(output.equals(Options.allOptions));
    }
}