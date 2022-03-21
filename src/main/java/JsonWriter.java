import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JsonWriter implements GameWriter{
    private final GamePlay gamePlay;
    String fileName = null;
    ObjectMapper objectMapper = new ObjectMapper();


    public JsonWriter(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    @Override
    public void writeToFile() throws IOException {
        Player player1 = gamePlay.getPlayers()[0];
        Player player2 = gamePlay.getPlayers()[1];

        String fileName = String.format("%s_vs_%s_%s.json", player1.getName(), player2.getName(), new SimpleDateFormat("ddMMyyyy_HHmmss").
                format(Calendar.getInstance().getTime()));

        DataForSaving dataForSaving = new DataForSaving();
        dataForSaving.setPlayers(gamePlay.getPlayers());
        dataForSaving.setSteps(gamePlay.getSteps());
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName),dataForSaving);
    }
}
