import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class for pools of any Tournament.
 */
public class Pool {

    /**
     * Represents the size of the pool. If the pool is of uneven size, the actual pool size is either one larger or one
     * smaller. if the pool is even, it is equal to this number.
     */
    public int poolsize;

    /**
     * The actual size of the pool if the pool is of uneven size.
     */
    public int unevenPoolSize;

    /**
     * Pool identifier.
     */
    public String poolID;

    /**
     * Identifier for the sister-pool of a pool with uneven size.
     */
    public String idSisterPool;

    /**
     * The number of rounds of matches a pool has. Currently, teams will only meet one time maximally.
     * The rounds are tied to the (even) pool size.
     */
    public int numberOfRounds;

    /**
     * The number of pool-matches. The number of matches is equal to the triangular number of the (even) poolsize attribute.
     */
    public int numberOfMatches;

    /**
     * A map between the pool-round and container of matches of that round.
     */
    public HashMap<Integer, ArrayList<Match>> roundMatches = new HashMap<>();

    /**
     * Constructor for a pool of even size.
     *
     * @param evenPoolSize number of participants
     * @param id Pool identifier
     */
    public Pool(int evenPoolSize, String id) {
        this.poolsize = evenPoolSize;
        this.poolID = id;
        this.numberOfMatches = (poolsize * (poolsize - 1) / 2);
        this.numberOfRounds =  poolsize - 1;
        getPoolMatches();
    }

    /**
     * Constructor overloading for a pool of uneven size.
     *
     * @param evenpoolsize number of participants
     * @param id Pool identifier
     * @param idSisterPool Pool identifier of the sister-pool (either next or previous in order)
     */
    public Pool(int evenpoolsize,int unevenpoolsize, String id, String idSisterPool) {
        this.poolsize = evenpoolsize;
        this.unevenPoolSize = unevenpoolsize;
        this.poolID = id;
        this.idSisterPool = idSisterPool;
        this.numberOfRounds =  evenpoolsize - 1;
        this.numberOfMatches = ((poolsize+1) * (poolsize/ 2));
        getUnevenPoolMatches();
    }

    /**
     * Returns the size of the pool
     */
    public int getParticipants() {
        return poolsize;
    }

    /**
     * Returns the ID of the pool
     */
    public String getId() {
        return poolID;
    }

    /**
     * <p>Method for creating Match objects that belong to a particular pool-round.<p/>
     * A Match-ID required for this, consisting of the pool-ID, round-number and order in this round, is created</p>
     * @param teamup possible configuration to team up
     * @param round the round the teaming up is occurring in
     * @return A list containing Match objects belonging to a pool-round.
     */
    public ArrayList<Match> makePairs(int[] teamup, int round) {
        ArrayList<Match> pairs = new ArrayList<>();
        int matchNR = 1;
        for (int i = 0; i < poolsize / 2; i++) {
            String matchID = poolID + "." + round + "." + matchNR;
            pairs.add(new Match(poolID + teamup[i], poolID + teamup[teamup.length - i - 1], matchID));
            matchNR++;
            }
        return pairs;
        }

    /**
     * <p>Method for creating Match objects that belong to a particular pool-round.<p/>
     * A Match-ID required for this, consisting of the pool-ID, round-number and order in this round, is created One
     * team will be tied to a team in a sister-pool. Currently both these pools will contain this identical match</p>
     * @param teamup the teaming up configuration
     * @param round the pool-round
     * @return A list containing Match objects belonging to a pool-round.
     */
    public ArrayList<Match> makePairsUnevenPool(int[] teamup, int round) {
        ArrayList<Match> pairs = new ArrayList<>();
        int matchNR = 1;
        for (int i = 0; i < (unevenPoolSize+1)/2; i++){
            String matchID = poolID + "." + round + "." + matchNR;
            if (!(teamup[i] == -1 || teamup[teamup.length-i-1]==-1)){
                pairs.add(new Match( poolID + teamup[i], poolID + teamup[teamup.length-i-1], matchID));
            } else {
                int teamID = Math.max(teamup[i], teamup[teamup.length-i-1]);
                pairs.add(new Match( poolID + teamID, idSisterPool + teamID, idSisterPool + "." + matchID));
            }
            matchNR++;
        }
        return pairs;
    }

    /**
     * <p>Method for creating pool matches within the pool-rounds<p/>
     *
     * <p>Made an implementation of round-robin to create rounds where every pool member meets a new opponent:
     * <a href="https://en.wikipedia.org/wiki/Round-robin_tournament">https://en.wikipedia.org/wiki/Round-robin_tournament</a><p/>
     *
     * Implementation: an int-array is created starting with (team-id) 1, that is incremented to the half of the array. The
     * second half of the array starts with the pool size and is decremented by one. Example: [1,2,3,6,5,4].
     *
     * Next, the numbers are rotated while 1 at index 0 remains fixed, in order to create unique and complete pairs each round.
     *
     * The int-array is used to create corresponding Match objects. These matches are stored in a hashmap where the
     * round-number is mapped to an arraylist containing the Match objects of that round.
     */
    public void getPoolMatches() {

        int[] teams1 = new int[poolsize/2];
        for (int i=0; i < poolsize/2; i++) {
            teams1[i] = i+1;
        }
        int[] teams2 = new int[poolsize/2];
        for (int i=0; i<poolsize/2; i++) {
            teams2[i] = poolsize-i;
        }
        int[] teams = new int[teams1.length + teams2.length];
        System.arraycopy(teams1, 0, teams, 0, teams1.length);
        System.arraycopy(teams2, 0, teams, teams1.length, teams2.length);

        for (int round=1; round<=numberOfRounds; round++) {
            for (int i=1; i<poolsize-1; i++) {
                int temp = teams[i+1];
                teams[i+1] = teams[i];
                teams[i] = temp;
            }
            try {
                this.roundMatches.put(round,makePairs(teams, round));
            } catch (Exception e) {
                System.out.println("Exception => " + e.getMessage());
            }
        }
        for (Integer round : roundMatches.keySet()) {
            int i = 1;
            for (Match match : roundMatches.get(round)) {
                i++;
            }
        }
    }

    /**
     * <p>Method for adding Match instances to the container attribute of a pool of uneven size.<p/>
     *
     * The pool is extended artificially by one in size. This spot is filled with team-id -1. Teaming up with -1 results
     * in being teamed up with your sister-pool counterpart instead.
     *
     * Each round one remaining team will be matched to its counterpart in the sister-pool.
     */
    private void getUnevenPoolMatches() {

        int[] teams1 = new int[1+unevenPoolSize/2];
        for (int i=0; i <= unevenPoolSize/2; i++) {
            teams1[i] = i+1;
        }

        teams1[unevenPoolSize/2] = -1;

        int[] teams2 = new int[1+unevenPoolSize/2];
        for (int i=0; i<(unevenPoolSize+1)/2; i++) {
            teams2[i] = unevenPoolSize-i;
        }

        int[] teams = new int[teams1.length + teams2.length];
        System.arraycopy(teams1, 0, teams, 0, teams1.length);
        System.arraycopy(teams2, 0, teams, teams1.length, teams2.length);

        for (int round=1; round<=numberOfRounds; round++) {
            for (int i=1; i<unevenPoolSize; i++) {
                int temp = teams[i+1];
                teams[i+1] = teams[i];
                teams[i] = temp;
            }
            try {
                this.roundMatches.put(round, makePairsUnevenPool(teams, round));
            } catch (Exception e) {
                System.out.println("Exception => " + e.getMessage());
            }
        }
    }

    /**
     * A method for displaying the matches of a pool.
     * @return String displaying the pool-rounds and matches within those rounds.
     */
    public String toString() {

        String poolString = "\nPOOL " + poolID + "\n";
        for (Integer round : roundMatches.keySet()) {

            poolString = poolString.concat(("round " + poolID + round+"\n").toUpperCase());
            for (Match match : roundMatches.get(round)) {
                poolString = poolString.concat("match " + match.toString() + "\n");
            }
        }
        return poolString;
    }
}