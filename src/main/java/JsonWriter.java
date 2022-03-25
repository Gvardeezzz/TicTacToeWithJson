import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JsonWriter implements GameWriter{
    private final Model model;
    String fileName = null;
    ObjectMapper objectMapper = new ObjectMapper();


    public JsonWriter(Model model) {
        this.model = model;
    }

    @Override
    public void writeToFile() throws IOException {
        Player player1 = model.getPlayers()[0];
        Player player2 = model.getPlayers()[1];

        fileName = String.format("%s_vs_%s_%s.json", player1.getName(), player2.getName(), new SimpleDateFormat("ddMMyyyy_HHmmss").
                format(Calendar.getInstance().getTime()));
        var steps = model.getSteps();
        for (int i = 0; i < steps.size(); i++) {
           steps.get(i).setNum(i+1);
        }

        Game game = new Game(steps);

        GameResult gameResult = new GameResult();

        Gameplay gameplay = new Gameplay();
        gameplay.setGame(game);
        gameplay.setPlayers(model.getPlayers());
        gameResult.setWinner(model.getWinner());
        gameplay.setGameResult(gameResult);

       // objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName),gameplay);
    }
}
