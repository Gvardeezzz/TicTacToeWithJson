import java.io.IOException;

public class Game {

    public static void main(String[] args) throws InterruptedException, IOException {
        View view = new View();
        GamePlay gamePlay = new GamePlay(view);
        String mode = Utils.selectMode();
        gamePlay.setMode(mode);

        String choise = "";
        while (!(choise.equals("1") || choise.equals("2"))){
            Utils.printMessage("If you want to watch replay enter \'1\'");
            Utils.printMessage("If you want to play TicTacToe enter \'2\'");
            choise = Utils.readMessage();
        }

        switch (choise){
            case "1" -> showReplay(mode);
            case "2" -> gamePlay.mainLoop();
        }
    }

    public static void showReplay(String mode) throws InterruptedException, IOException {
        switch (mode) {
            case ".xml" -> new XMLParser().play();
            case ".json" -> new JSonParser().play();
        }
    }
}
