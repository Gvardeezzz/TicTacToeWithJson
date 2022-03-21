import java.io.File;

public class JsonWriter implements GameWriter{
    private GamePlay gamePlay;

    public JsonWriter(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    @Override
    public void writeToFile() {
    }
}
