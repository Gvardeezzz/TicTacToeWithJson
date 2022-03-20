import java.io.File;

public class XMLWriter implements GameWriter {
    private GamePlay gamePlay;

    public XMLWriter(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    @Override
    public void writeToFile(String fileName) {

    }
}
