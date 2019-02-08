package tictactoe;

public class Player {

    private String[] playerName;
    private int[] winCount;

    public Player() {
        playerName = new String[2];
        winCount = new int[3];
    }

    public void resetPlayerName() {
        playerName = new String[2];
    }

    public void setPlayerName(int player, String who) {
        playerName[player] = who;
    }

    public String[] getPlayerName() {
        return playerName;
    }

    public void resetWinCount() {
        winCount = new int[3];
    }

    public void setWinCount(int player) {
        winCount[player] += 1;
    }

    public int[] getWinCount() {
        return winCount;
    }

    public String getWinner() {
        String msg = "Draw";
        if (winCount[0] > winCount[2]) {
            msg = playerName[0] + " is the winner..!!";
        } else if (winCount[0] < winCount[2]) {
            msg = playerName[1] + " is the winner..!!";
        }
        return msg;
    }
}
