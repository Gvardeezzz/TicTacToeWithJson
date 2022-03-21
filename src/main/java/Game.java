import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class Game {

    public static void main(String[] args) throws InterruptedException {
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
           // case "1" -> gamePlay.showReplay(mode);
            case "1" -> {
                XMLParser xmlParser = new XMLParser();
                xmlParser.play();
            }
            case "2" -> gamePlay.mainLoop();
        }
    }
/*

        ObjectMapper o = new ObjectMapper();
        SomeData someData = new SomeData();
        someData.setParam1(100);
        someData.setParam2(false);
        someData.setParam3("Zalupa");

        String result = o.writeValueAsString(someData);
        System.out.println(result);

    }

    public void sendSatisticsToFile(String fileName){

    }

*/

}
