/**
 * A class for creating Match instances. It shows how the class could be utilized later.
 * For present purposes only the toString() method is used and tested.
 */
public class Match {

    /**
     * String identifying team one.
     */
    private String teamOne;
    /**
     * String identifying team two.
     */
    private String teamTwo;
    /**
     * Boolean that is True when the match has been played, otherwise False.
     */
    private boolean play;
    /**
     * String to identify the match. The round and position in that round of certain pool is displayed.
     */
    private String matchID;
    /**
     * An instance of Score initialized at the start of the match.
     */
    private Score score = null;

    public Match(String teamOne, String teamTwo, String matchID) {
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        this.matchID = matchID;
        this.play = false;
    }

    /**
     * A method for starting a match
     */
    public void startMatch() {
        if (this.play == false) {
            Score score = new Score(0, 0);
            this.play = true;
        }
    }

    /**
     * A method for changing the score of a match.
     * @param one the score for team One
     * @param two the score for team Two
     */
    public void changeScore(int one, int two) {
        if (this.play == false) {
            score.changeScore(new Score(one, two));
        }
    }

    /**
     * An inner private class not further used or tested. Used to update the score.
     */
    private class Score {
        private int scoreTeamOne;
        private int scoreTeamTwo;
        public Score(int i, int j) {
            this.scoreTeamOne = i;
            this.scoreTeamTwo = j;
        }
        public void changeScore(Score other) {
            this.scoreTeamOne += other.scoreTeamOne;
            this.scoreTeamTwo += other.scoreTeamTwo;
        }
    }
    /**
     * @return String displaying the teams involved and the Match ID.
     */
    @Override
    public String toString() {
        return String.format("%s - %s vs %s",matchID, teamOne, teamTwo);
    }
}