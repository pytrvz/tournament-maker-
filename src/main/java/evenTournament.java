import java.util.ArrayList;

/**
 *  A class for creating a Tournament with all pools of an equal even size.
 */
public class evenTournament extends Tournament {
    public evenTournament(Integer pools, Integer poolsize) {
        super(pools, poolsize);
        this.tournamentpools =  createPools();
    }

    /**
     * Creates all String pool identifiers to create a collection of Pool instances as attribute of the Tournament.
     *
     * A 27 number based system is used to create a String pool identifier with ASCII chars. The fist Pool is "A",
     * the 26th pool is "Z", the 27th pool is "A@".
     *
     * @return Arraylist of tournament pools.
     */
    public ArrayList<Pool> createPools(){

        int base = 27;
        for (int i = 1; i <= numberOfPools; i++) {
            String poolID = "";
            int j = i;
            while (j > 0) {
                poolID = (char) (64+(j%base)) + poolID;
                j /= base;
            }
            Pool p = new Pool(poolsize, poolID);
            this.tournamentpools.add(p);
        }
        return tournamentpools;
    }
}