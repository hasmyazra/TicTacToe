package tictactoe;

public class InGame {

    private final String[] CHOSEN;
    private String playerTurn;
    private int gameCount;

    public InGame() {
        CHOSEN = new String[9];
        playerTurn = "X";
        gameCount = 0;
    }

    public void setChoose(int index, String xOrO) {
        CHOSEN[index] = xOrO;
    }

    public String[] getChoose() {
        return CHOSEN;
    }

    public void setPlayerTurn(String xOrO) {
        playerTurn = xOrO;
    }

    public String getPlayerTurn() {
        return playerTurn;
    }

    public void setGameCount() {
        gameCount++;
    }

    public int getGameCount() {
        return gameCount;
    }
}
