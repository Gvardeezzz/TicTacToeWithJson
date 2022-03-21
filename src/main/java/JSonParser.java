import java.util.ArrayList;

public class JSonParser implements GameParser{
    private View view;
    private PlayerMark [][] gameField = new PlayerMark[3][3];
    private Player []players = new Player[3];
    private ArrayList<Step> steps = new ArrayList<>();
    private static String win = "Draw!";
    private String fileName;

    @Override
    public void parse(String filename) {

    }

    public void play() {
    }
}
