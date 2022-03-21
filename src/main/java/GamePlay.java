import java.util.ArrayList;

public class GamePlay {
    private PlayerMark [][] gameField = new PlayerMark[3][3];
    private ArrayList<Player> playerList = new ArrayList<>();
    private ArrayList<String> rating = new ArrayList<>();
    private View view;
    private Player[] players;
    private ArrayList<Step> steps;
    private String mode;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public GamePlay(View view) {
        this.view = view;
    }

    public static void showReplay(String mode) throws InterruptedException {
        switch (mode) {
            case ".xml" -> {
                XMLParser xmlParser = new XMLParser();
                xmlParser.play();
            }
            case ".json" -> {
                JSonParser jSonParser = new JSonParser();
                jSonParser.play();
            }
        }
    }

    public void mainLoop(){
        clear();
        initGameField();
        initPlayers();
        Player currentPlayer = players[0];
        players[0].setMark(PlayerMark.CROSS);
        players[1].setMark(PlayerMark.ZERO);
        boolean isWinnerFound = false;
        int stepNumber = 1;

        while (hasMotion(gameField)) {
            Utils.printMessage(String.format("%s moves", currentPlayer.getName()));
            steps.add(Step.makeStep(gameField, currentPlayer.getMark()));
            stepNumber++;
            view.refresh(gameField);

            if (isWin(gameField, currentPlayer.getMark())) {
                Utils.printMessage(String.format("Winner is %s!", currentPlayer.getName()));
                players[2] = currentPlayer;
                int winnerIndex = findIndex(currentPlayer.getId(), playerList);
                int loserIndex = findIndex(changePlayer(currentPlayer).getId(), playerList);
                playerList.get(winnerIndex).setWins(currentPlayer.getWins() + 1);
                Player loser = changePlayer(currentPlayer);
                playerList.get(loserIndex).setLoses(loser.getLoses() + 1);
                isWinnerFound = true;
                break;
            }
            currentPlayer = changePlayer(currentPlayer);
        }

        if(!isWinnerFound){
            Utils.printMessage("Draw!");
            int index = findIndex(currentPlayer.getId(), playerList);
            playerList.get(index).setDraws(currentPlayer.getDraws() + 1);
            Player player = changePlayer(currentPlayer);
            index = findIndex(player.getId(),playerList);
            playerList.get(index).setDraws(player.getDraws() + 1);
        }

        rating = Utils.makeOutputData(playerList);
        Utils.sendStatisticsToFile(rating);

        switch (mode){
            case ".xml" -> {
                XMLWriter xmlWriter = new XMLWriter(this);
                xmlWriter.writeToFile();
            }

            case ".json" -> {
                JsonWriter jsonWriter = new JsonWriter(this);
                jsonWriter.writeToFile();
            }
        }


        Utils.printMessage("Do you want to play again?");
        Utils.printMessage("If no, enter word \"exit\". For continue playing enter any symbol");
        String wantToPlay = Utils.readMessage();
        if(!wantToPlay.equalsIgnoreCase("exit")) {
            mainLoop();
        }
    }

    public void clear(){
        players = new Player[3];
        steps = new ArrayList<>();
    }

    public Player changePlayer(Player player){
        if(player == players[0]) return players[1];
        return players[0];
    }

    public void initGameField(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameField[i][j] = PlayerMark.SPACE;
            }
        }
    }

    public boolean isPlayerExists(ArrayList<Player> list, int id){
        if(list.isEmpty()) return false;
        for(Player player : list) {
            if(player.getId() == id) return true;
        }
        return false;
    }
    public void initPlayers(){
        Player player;
        int currentId = -1;
        for (int i = 0; i < 2; i++) {
            Utils.printMessage(String.format("Enter player%d id",i+1));
            int id = Utils.readInt();
            if(id == currentId){
                Utils.printMessage("This user is taking part in current game!");
                i--;
                continue;
            }
            if(!isPlayerExists(playerList, id)){
                Utils.printMessage(String.format("Enter player%d name",i+1));
                String name = Utils.readMessage();
                player = createPlayer(id, name);
                players[i] = player;
                playerList.add(player);
            }
            else {
                int playerIndex = findIndex(id, playerList);
                Utils.printMessage(String.format("Player%d is %s", i+1, playerList.get(playerIndex).getName()));
                player = playerList.get(playerIndex);
                players[i] = player;
            }
            currentId = id;
        }
    }

    public boolean hasMotion(PlayerMark [][] gameField) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j] == PlayerMark.SPACE) return true;
            }
        }
        return false;
    }

    public boolean isWin(PlayerMark [][] gameField, PlayerMark mark){
        for (int i = 0; i < 3; i++) {
            if((gameField[i][0] == mark && gameField[i][1] == mark && gameField[i][2] == mark) ||
                    (gameField[0][i] == mark && gameField[1][i] == mark && gameField[2][i] == mark)){
                return true;
            }

            if(gameField[0][0] == mark && gameField[1][1] == mark && gameField [2][2] == mark) return true;
            if (gameField[0][2] == mark && gameField[1][1] == mark && gameField[2][0] == mark) return true;
        }
        return false;
    }

    public int findIndex(int id, ArrayList<Player> playerList){
        int index = 0;
        for (int i = 0; i < playerList.size(); i++) {
            if(playerList.get(i).getId() == id) index = i;
        }
        return index;
    }
    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public Player createPlayer(int id, String name){
        Player player = new Player(id, name);
        player.setWins(0);
        player.setLoses(0);
        player.setDraws(0);
        return player;
    }

}

