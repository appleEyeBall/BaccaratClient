import java.io.Serializable;
import java.util.ArrayList;

public class Packet implements Serializable {

    private String ipAddress;
    private int portNum;
    private PlayerDetails playerDetails;
    private String winnerMsg;
    enum GameResults
    {
        PLAYER, BANKER, DRAW;
    }

    public class PlayerDetails implements Serializable{

        private String playerName;
        private ArrayList<Card> hand;
        private int bidAmount;
        private GameResults betChoice;

//        public PlayerDetails() {
//
//        }
//
//        public PlayerDetails(String playerName) {
//            this.playerName = playerName;
//        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public ArrayList<Card> getHand() {
            return hand;
        }

        public void setHand(ArrayList<Card> hand) {
            this.hand = hand;
        }

        public int getBidAmount() {
            return bidAmount;
        }

        public void setBidAmount(int bidAmount) {
            this.bidAmount = bidAmount;
        }

        public GameResults getBetChoice() {
            return betChoice;
        }

        public void setBetChoice(GameResults betChoice) {
            this.betChoice = betChoice;
        }
    }


    public Packet(String ipAddress, int portNum) {
        this.ipAddress = ipAddress;
        this.portNum = portNum;
        this.playerDetails = getPlayerDetails();
    }

    public ArrayList<Card> giveHand(ArrayList<Card> hand){
        return null;
    }

    public String getIpAddress(){
        return ipAddress;
    }

    public int getPortNum() {
        return portNum;
    }

    public PlayerDetails getPlayerDetails() {
        return playerDetails;
    }

    public void setPlayerDetails(PlayerDetails playerDetails) {
        this.playerDetails = playerDetails;
    }


    public String getWinnerMsg() {
        return winnerMsg;
    }

    public void setWinnerMsg(String winnerMsg) {
        this.winnerMsg = winnerMsg;
    }


}

