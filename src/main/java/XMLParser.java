import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XMLParser implements GameParser{
    private View view;
    private PlayerMark [][] gameField;
    private Player []players;
    private Player winner;
    private ArrayList<Step> steps;
    private static String win = "Draw!";
    private String fileName;

    @Override
    public void parse(String filename) {
        view = new View();
        gameField = new PlayerMark[3][3];
        players = new Player[2];
        steps = new ArrayList<>();
        winner = null;

        XMLInputFactory factory = XMLInputFactory.newInstance();
        int playerCount = 0;
        int stepCount =0;
        try{
            XMLEventReader reader = factory.createXMLEventReader(new FileInputStream(fileName));
            while (reader.hasNext()){
                XMLEvent event = reader.nextEvent();
                if(event.isStartElement()){
                    StartElement startElement = event.asStartElement();

                    if(startElement.getName().getLocalPart().equals("Player")){
                        Attribute attribute = startElement.getAttributeByName(new QName("id"));
                        String id = attribute.getValue();

                        attribute = startElement.getAttributeByName(new QName("name"));
                        String name = attribute.getValue();

                        attribute = startElement.getAttributeByName(new QName("symbol"));
                        String symbol = attribute.getValue();
                        PlayerMark mark = symbol.equals("X") ? PlayerMark.CROSS : PlayerMark.ZERO;

                        Player player = new Player(Integer.parseInt(id),name);
                        player.setMark(mark);
                        if (playerCount == 2) winner = player;
                       else { players[playerCount] = player;
                        playerCount++;}
                    }
                    else if(startElement.getName().getLocalPart().equals("Step")){
                        event = reader.nextEvent();
                        String stringStep = event.asCharacters().getData();
                        int [] mas = inputDataAdapter(stringStep);
                        Step step = new Step(players[stepCount%2].getMark(), 0, 0);
                        step.setX(mas[0]);
                        step.setY(mas[1]);
                        step.setNum(stepCount);
                        steps.add(step);
                        stepCount++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void play() throws InterruptedException {
        int playerNumber = 0;
        System.out.println("Saved games:");
        File file = new File("");
        File path = new File(file.getAbsolutePath());
        List<File> files = new ArrayList<>();
        for(File f:path.listFiles()){
            if(f.isFile() && f.getName().endsWith(".xml")) {
                if(!f.getName().equals("pom.xml")) files.add(f);
            }
        }
        for (int j = 0; j < files.size(); j++) {
            System.out.printf("%d - %s", (j + 1), files.get(j).getName());
            System.out.println();
        }

        int result = Utils.fileNumberRequest(files);

        fileName = files.get(result).getName();

        parse(fileName);

        initGameField();

        for (int i = 0; i < steps.size(); i++) {
            for (int j = 0; j < steps.size(); j++) {
                int x = steps.get(j).getX();
                int y = steps.get(j).getY();
                PlayerMark mark = playerNumber % 2 == 0 ? PlayerMark.CROSS : PlayerMark.ZERO;
                gameField[x][y] = mark;
                view.refresh(gameField);
                Thread.sleep(1000);
                playerNumber++;
            }
            if(winner != null){
                String symbol = winner.getMark() == PlayerMark.CROSS ? "X" : "0";
                System.out.printf("Player %d -> %s is winner as \'%s\'!", winner.getId(), winner.getName(),symbol);
                break;
            }
            else Utils.printMessage("Draw!");
            break;
        }

        Utils.printMessage("");
        Utils.printMessage("Do you want continue watching?");
        Utils.printMessage("If no, enter word \"exit\". For continue watching enter any symbol");
        String wantToWatch = Utils.readMessage();
        if(!wantToWatch.equalsIgnoreCase("exit")) {
            play();
        }
    }

    public void initGameField(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameField[i][j] = PlayerMark.SPACE;
            }
        }
    }

    public static int[] inputDataAdapter(String step) {
        int [] coordinates = new int[2];
        int len = step.length();
        switch (len) {
            case 1 -> {
                int num = Integer.parseInt(step) - 1;
                coordinates[0] = num / 3;
                coordinates[1] = num % 3;
            }
            case 2 ->{
                char[] s = step.toCharArray();
                coordinates[0] = Integer.parseInt(String.valueOf(s[0]));
                coordinates[1] = Integer.parseInt(String.valueOf(s[1]));
            }

            case 3 -> {
                String []s = step.split(" ");
                coordinates[0] = Integer.parseInt(s[0]);
                coordinates[1] = Integer.parseInt(s[1]);
            }
        }
        return coordinates;
    }

}
