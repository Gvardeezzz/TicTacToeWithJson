import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSonParser implements GameParser {
    private final View view = new View();
    private PlayerMark[][] gameField = new PlayerMark[3][3];
    private Player[] players = new Player[3];
    private ArrayList<Step> steps = new ArrayList<>();
    private final static String win = "Draw!";
    private String fileName;
    ObjectMapper objectMapper = new ObjectMapper();
    DataForSaving data;

    @Override
    public void parse(String filename) throws IOException {
        File instance = new File(fileName);
        data = objectMapper.readValue(instance, DataForSaving.class);
    }

    public void play() throws IOException, InterruptedException {
        System.out.println("Saved games:");
        File file = new File("");
        File path = new File(file.getAbsolutePath());
        List<File> files = new ArrayList<>();
        for (File f : path.listFiles()) {
            if (f.isFile() && f.getName().endsWith(".json")) {
                files.add(f);
            }
        }
        for (int j = 0; j < files.size(); j++) {
            System.out.printf("%d - %s", (j + 1), files.get(j).getName());
            System.out.println();
        }

        int result = Utils.fileNumberRequest(files);

        fileName = files.get(result).getName();

        parse(fileName);

        players = data.getPlayers();
        steps = data.getSteps();

        initGameField();

        for (int i = 0; i < steps.size(); i++) {
            Step step = steps.get(i);
            gameField[step.getX()][step.getY()] = step.getMark();
            view.refresh(gameField);
            Thread.sleep(1000);
        }

        if (players[2] != null) {
            Player winner = players[2];
            String symbol = winner.getMark() == PlayerMark.CROSS ? "X" : "0";
            System.out.printf("Player %d -> %s is winner as '%s'!", winner.getId(), winner.getName(), symbol);
        } else System.out.println(win);

        Utils.printMessage("");
        Utils.printMessage("Do you want continue watching?");
        Utils.printMessage("If no, enter word \"exit\". For continue watching enter any symbol");
        String wantToWatch = Utils.readMessage();
        if (!wantToWatch.equalsIgnoreCase("exit")) play();
    }

        public void initGameField () {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    gameField[i][j] = PlayerMark.SPACE;
                }
            }
        }
    }
