import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TournamentOptionsGetPrimefactorsTest {

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
    void OptionsTest() {
        int number = 10;
        List<List<Integer>> output = Arrays.asList(
                Arrays.asList(1, 10, 0, 3),
                Arrays.asList(5, 2, 3, 3),
                Arrays.asList(0, 5, 5, 1, 3),
                Arrays.asList(2, 2, 3, 1, 1));

        TournamentOptions Options = new TournamentOptions(number);
        assertTrue(output.equals(Options.allOptions));
    }
}