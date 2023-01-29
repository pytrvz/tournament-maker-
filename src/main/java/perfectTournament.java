/**
 * <p>A derived class for a tournament containing a number of participants that is equal to a power of two.<p/>
 * This type of tournament differs from other tournaments with an even and equal number of pools in having an obvious
 * and predetermined way of creating matches for the post-pool-phase. This phase is currently not addressed.
 */
public class perfectTournament extends evenTournament {

    /**
     * <p>Constructor for a perfect Tournament containing a number of participants that is equal to a power of two.<p/>
     * @param pools the number of pools in the poolphase of the tournament
     * @param poolsize the size of the pool
     */
    public perfectTournament(Integer pools, Integer poolsize) {

        super(pools, poolsize);
    }

    /**
     * An unimplemented method unique to perfect tournaments. These tournaments have an obvious way of creating post-Pool
     * brackets before the start of the Tournament. Other types of tournament have to account for an unequal number
     * of delegates for the post-pool-phase and this requires actual pool performance to be taken into account.
     */
    private void createPostPoolBrackets() {

        throw new UnsupportedOperationException("not implemented yet");
    }
}