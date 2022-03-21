import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class XMLWriter implements GameWriter {
    private final GamePlay gamePlay;
    private static Document document;
    public XMLWriter(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }
    String fileName = null;


    @Override
    public void writeToFile() {
        document = createDocument();
        Element gameplay = document.createElement("Gameplay");
        document.appendChild(gameplay);

        Player player1 = gamePlay.getPlayers()[0];
        Player player2 = gamePlay.getPlayers()[1];
        Player winner = gamePlay.getPlayers()[2];

        gameplay.appendChild(addPlayer(player1));
        gameplay.appendChild(addPlayer(player2));
        Element game = document.createElement("Game");
        gameplay.appendChild(game);

        ArrayList<Step> steps = gamePlay.getSteps();
        for (int i = 0; i < steps.size(); i++) {
            Element step = document.createElement("Step");
            step.setAttribute("num", String.valueOf(i+1));
            Player currentPlayer = i % 2 ==0 ? player1 : player2;
            step.setAttribute("playerId", String.valueOf(currentPlayer.getId()));
            String cell = steps.get(i).getX() + " " + steps.get(i).getY();
            step.setTextContent(cell);
            game.appendChild(step);
        }

        Element gameResult = document.createElement("GameResult");
        if(winner == null) {
            gameResult.setTextContent("Draw!");
        }
        else gameResult.appendChild(addPlayer(winner));
        gameplay.appendChild(gameResult);
        fileName = String.format("%s_vs_%s_%s.xml", player1.getName(), player2.getName(), new SimpleDateFormat("ddMMyyyy_HHmmss").
                format(Calendar.getInstance().getTime()));
        saveAsFile(fileName);
    }

    public Document createDocument () {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = builder.newDocument();
        return document;
    }

    public Element addPlayer(Player p) {
        String symbol = p.getMark()==PlayerMark.CROSS?"X":"0";
        Element player = document.createElement("Player");
        player.setAttribute("id", String.valueOf(p.getId()));
        player.setAttribute("name", p.getName());
        player.setAttribute("symbol", symbol);
        return player;
    }

    public static void saveAsFile(String fileName){
        try{
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(fileName);
            StreamResult result = new StreamResult(fos);
            tr.transform(source,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
