import java.util.ArrayList;

/**
 * Abstract tournament class.
 *
 */
public abstract class Tournament {

    /**
     * The size of the even pools, if any exists. The possible two uneven pools are either
     * one size smaller or larger and have their own attribute, unevenPoolSize, in the derived class unevenTournament.
     */
    public int poolsize;
    /**
     * The number of pools.
     */
    public int numberOfPools;
    /**
     * Sorted container holding the pool instances of the particular tournament.
     */
    public ArrayList<Pool> tournamentpools = new ArrayList<>();

    /**
     * Tournament Constructor.
     * @param pools the number of pools
     * @param poolsize  the size of the even pool
     */
    public Tournament(int pools, int poolsize) {
        this.numberOfPools = pools;
        this.poolsize = poolsize;
    }

    /**
     * Getter method
     * @return the number of pools
     */
    public int getPools() {
        return numberOfPools;
    }

    /**
     * Getter method
     * @return the size of the even Pools
     */
    public int getPoolsize() {
        return poolsize;
    }

    /**
     * @return tournamentString
     */
    @Override
    public String toString() {
        String tournamentString = "\nTournament\n\n";
        for (Pool pool: tournamentpools) {
            tournamentString += pool.toString();
    }
        return tournamentString;
    }
}