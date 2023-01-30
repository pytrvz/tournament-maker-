import java.util.*;


/**
 * Class for creating possible configurations of a tournament given an even number of participating teams.
 */
public class TournamentOptions {

    /**
     * The number of participants.
     */
    private final int numberParticipants;
    /**
     * List that contains all combinations for pools of even and equal size.
     */
    final private List<List<Integer>> evenPoolcombinations = new ArrayList<>();
    /**
     * List that contains all combinations where the last two pools are not even and equal to each other.
     */
    final private List<List<Integer>> unevenPoolcombinations = new ArrayList<>();
    /**
     * List that contains in order: the number of even-pool combinations and uneven-pool combinations.
     */
    protected List<List<Integer>> allOptions = new ArrayList<>();
    /**
     * The total number of possible configurations.
     */
    private final int numberOfTournamentOptions;

    public TournamentOptions(int numberParticipants) {
        this.numberParticipants = numberParticipants;
        getEvenCombinations(this.numberParticipants);
        getUnevenPoolCombinations(this.numberParticipants);
        this.allOptions.addAll(evenPoolcombinations);
        this.allOptions.addAll(unevenPoolcombinations);
        this.numberOfTournamentOptions = allOptions.size();
    }

    /**
     * Factorises a positive integer by using a sieve with O(n**0.5) complexity.
     *
     * @param number of participants.
     * @return the factors of a number
     */
    public static ArrayList<Integer> getPrimefactors(int number) {
        ArrayList<Integer> primeFactors = new ArrayList<>();
        for (int i = 2; i < Math.sqrt(number)+1; i++) {
            while (number % i == 0) {
                primeFactors.add(i);
                number = number / i;
            }
        }
        if (number > 2) {
            primeFactors.add(number);
        }
        return primeFactors;
    }

    /**
     * Method for generating even combinations of pool-size and number of pools.
     *
     * <p>A set is created to get all combinations once and the configuration of having one
     * pool of all participants is added manually.
     *
     * <p>A functional interface is used to support a lambda function that is instrumental in
     * pushing all possible combinations of pool-size and number of pools into the set.
     *
     * <p>The number of participants is prime-factored. This list is concatenated to itself.
     * An on this list moving window is iterated each time in order to create all combinations
     * and these are added to the set.
     *
     * <p>The set is used to create a two-dimensional sorted list of pool combinations. The sorting-key
     * is the number of pools.
     *
     * <p>Tournament method postPoolRounds is subsequently used to add the possible number of rounds after
     * the pool phase is concluded.
     *
     * @param numberParticipants the number of participants.
     */
    private void getEvenCombinations(int numberParticipants) {

        ArrayList<Integer> primeFactors = getPrimefactors(numberParticipants);
        primeFactors.addAll(primeFactors);
        HashSet<List<Integer>> evenPoolCombo = new HashSet<>();
        evenPoolCombo.add(new ArrayList<>(Arrays.asList(1, numberParticipants)));

        Function2 productDivisors = (int i, int j) -> {
            int total = 1;
            for (int k = i; k < j; k++) {
                total *= primeFactors.get(k);
            }
            return total;
        };

        for (int i = 0; i < primeFactors.size() / 2; i++) {
            for (int j = i + 1; j < i + primeFactors.size() / 2; j++) {
                int numberPools = productDivisors.run(i, j);
                int participantsPool = productDivisors.run(j, i + primeFactors.size() / 2);
                List<Integer> a = new ArrayList<>(Arrays.asList(numberPools, participantsPool));
                if ((participantsPool&1)==0){
                    evenPoolCombo.add(a);
                }
            }
        }

        this.evenPoolcombinations.addAll(evenPoolCombo);
        this.evenPoolcombinations.sort(Comparator.comparing(o -> o.get(0)));
        this.evenPoolcombinations.forEach(innerList -> innerList.addAll(postPoolRounds(innerList)));
    }

    /**
     * <p>Method for creating all possible combinations where the last two pools are of uneven size.
     *
     * <p>The option of having only two pools of uneven size is checked first separately.
     *
     * <p>The other combinations where the uneven pools are either one size larger or smaller compared to the pools of
     * even size are checked. This is done brute force with linear time complexity, by checking algebraically derived
     * conditions and filtering out invalid solutions containing one pool of uneven size only.
     *
     * @param numberParticipants the number of participants
     */
    private void getUnevenPoolCombinations(Integer numberParticipants) {

        ArrayList<Integer> primeFactors = getPrimefactors(numberParticipants + 2);
        primeFactors.addAll(primeFactors);

        int counter = 0;
        if ((numberParticipants/2&1)==1){
            counter++;
            this.unevenPoolcombinations.add(new ArrayList<>(Arrays.asList(0, numberParticipants/2, numberParticipants/2)));
            unevenPoolcombinations.get(0).addAll(postPoolRounds(new ArrayList<>(Arrays.asList(2, numberParticipants/2))));
        }

        for (int numberPools = 2; numberPools < numberParticipants; numberPools++) {
            if ((numberParticipants + 2) % (numberPools + 2) == 0) {
                int sizePool = (numberParticipants + 2) / (numberPools + 2);
                if ((sizePool & 1) != 1 && sizePool != 2) {
                    this.unevenPoolcombinations.add(new ArrayList<>(Arrays.asList(numberPools, sizePool, sizePool - 1)));
                }
            }
        }

        for (int numberPools = 2; numberPools < numberParticipants; numberPools++) {
            if ((numberParticipants - 2) % (numberPools + 2) == 0) {
                int sizePool = (numberParticipants - 2) / (numberPools + 2);
                if ((sizePool & 1) != 1 && (numberParticipants <= sizePool * numberPools + (sizePool + 1) * 2)) {
                    this.unevenPoolcombinations.add(new ArrayList<>(Arrays.asList(numberPools, sizePool, sizePool + 1)));
                }
            }
        }
        this.unevenPoolcombinations.subList(counter,
                unevenPoolcombinations.size()).forEach(innerList -> innerList.addAll(postPoolRounds(innerList.subList(0,2))));
    }

    /**
     * Java lambda is used to find the minimum number of post-pool-rounds and the maximum number of post-pool-rounds
     *
     * <p>To find the next power of 2, "<a href="https://www.geeksforgeeks.org/smallest-power-of-2-greater-than-or-equal-to-n/">https://www.geeksforgeeks.org/smallest-power-of-2-greater-than-or-equal-to-n/</a>"
     * by A. Goyal, was consulted. Subsequently these values are converted to log2 values. A simple while loop dividing
     * the number each time would have frankly obtained the same.</p>
     *
     * @param poolDimension Integer list containing the number of pools and the size of the pools.
     * @return Arraylist containing the minimum and maximum number of possible rounds.
     */
    private static List<Integer> postPoolRounds(List<Integer> poolDimension) {

        Integer numberOfPools = poolDimension.get(0);
        Integer lengthOfPools = poolDimension.get(1);
        Function getNextPowerOf2 = (n) -> {
            int p = 1;
            if (n > 0 && (n & (n - 1)) == 0)
                return n;
            while (p < n)
                p <<= 1;
            return p;
        };

        int minimumNextRound =  (int) (Math.log(getNextPowerOf2.run(numberOfPools)) /Math.log(2));
        int maximumNextRound = (int) (Math.log(getNextPowerOf2.run(lengthOfPools * numberOfPools) >> 1)/Math.log(2));

        return new ArrayList<>(Arrays.asList(minimumNextRound, maximumNextRound));
    }

    /**
     * <p>Displays the tournament configuration options. When the number of participants is equal to a power of two, no uneven
     * pool combinations are displayed. Otherwise, first solutions with pools of equal size are presented and subsequently
     * valid combinations with two uneven pools.
     *
     * <p>In order to present the uneven pool combinations, first it has to be established separately if the tournament can
     * be presented by two pools of uneven size.
     *
     * @return String that displays all numbered possible configurations to choose from.
     */
    public String toString() {

        String options = "NUMBER OF PARTICIPANTS: " + numberParticipants + "\n";
        if ((numberParticipants -1 & numberParticipants) == 1) {
            options += "PERFECT COMBINATIONS\n";
        }

        for (int i = 0; i < evenPoolcombinations.size(); i++) {
            List<Integer> pooldata = evenPoolcombinations.get(i);
            options += "\noption " + (i+1) + ": " + pooldata.get(0) + " Pools of size " + pooldata.get(1) +
            ". Possible number of post-pool-rounds: " + pooldata.get(2);
            if (!pooldata.get(2).equals(pooldata.get(3))) {
                options += " to " + pooldata.get(3) + "\n";
            }
        }

        if ((numberParticipants - 1 & numberParticipants) != 0) {

            options += "\n\nWITH TWO UNEVEN POOLS\n";

            int uneven = ((numberParticipants/2&1)==1) ? 1 : 0;
            if (uneven==1) {
                options += "\noption " + (1 + evenPoolcombinations.size()) + ": 2 pools of size " + unevenPoolcombinations.get(0).get(2) +
                        ". Possible number of post-pool-rounds: "+ unevenPoolcombinations.get(0).get(3);
                if (!Objects.equals(unevenPoolcombinations.get(0).get(3), unevenPoolcombinations.get(0).get(4))) {
                    options += " to " + unevenPoolcombinations.get(0).get(4) + "\n";
                }
            }

            for (int i = uneven; i < unevenPoolcombinations.size(); i++) {
                List<Integer> pooldata = unevenPoolcombinations.get(i);
                options += "\noption " + (i + 1 + evenPoolcombinations.size()) + ": " + pooldata.get(0) + " Pools of size " + pooldata.get(1) +
                        " and 2 pools of size " + pooldata.get(2) + ". Possible number of post-pool-rounds: " + pooldata.get(3);
                if (!Objects.equals(pooldata.get(3), pooldata.get(4))) {
                    options += " to " + pooldata.get(4) + "\n";
                }
            }
        }
        return options;
    }
}