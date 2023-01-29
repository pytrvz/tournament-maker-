import java.util.ArrayList;

/**
 * Class for generating an instance of a Tournament with two teams of uneven size.
 */
public class unevenTournament extends Tournament {
    /**
     * The class attribute specifying the size of the uneven pool. Eiter one size smaller or larger than the pools
     * of even size. Having solely two pools of uneven size is a possible tournament-configuration.
     */
    Integer unevenPoolSize;
    public unevenTournament(Integer pools, Integer poolsize, Integer unevenPoolSize) {
        super(pools, poolsize);
        this.unevenPoolSize = unevenPoolSize;
        this.tournamentpools = createPools();
    }

    /**
     * <p>Method that creates Pool instances of a Tournament instance. The last two pools are of uneven size.<p/>
     *
     * <p>Each pool is initialized with a String identifier. The String is constructed by a 27 base system using
     * ASCII-order 64 (@) to 90 (Z). The first pool is pool A, the 27th pool is A@.
     *
     * <p>There is the potential issue currently of treating a match between two teams in the sister-pools as two
     * seperate mathces.
     *
     * @return An Arraylist containing Pool objects in order. The last two pools are of uneven size.
     */
    public ArrayList<Pool> createPools(){

            int base = 27;
            for (int i = 1; i <= numberOfPools; i++) {
                String poolID = "";
                int j = i;
                while (j > 0) {
                    poolID = (char) (64+(j%base)) + poolID;;
                    j /= base;
                }
                Pool p = new Pool(poolsize, poolID);
                this.tournamentpools.add(p);
            }

            String[] unevenPoolIDs = new String[2];
            for (int i = numberOfPools+1; i <= numberOfPools+2; i++) {
                String poolID = "";
                int j = i;
                while (j > 0) {
                    poolID = (char) (64+(j%base)) + poolID;;
                    j /= base;
                }
                int index = numberOfPools+2==i ? 1 : 0;
                unevenPoolIDs[index] = poolID;
            }

            Pool p1 = new Pool(poolsize, unevenPoolSize, unevenPoolIDs[0], unevenPoolIDs[1]);
            tournamentpools.add(p1);
            Pool p2 = new Pool(poolsize, unevenPoolSize, unevenPoolIDs[1], unevenPoolIDs[0]);
            tournamentpools.add(p2);
            return tournamentpools;
    }
}