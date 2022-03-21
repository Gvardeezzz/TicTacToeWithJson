import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.util.ArrayList;

public class XMLParser implements GameParser{
    private View view;
    private PlayerMark [][] gameField = new PlayerMark[3][3];
    private Player []players = new Player[3];
    private ArrayList<Step> steps = new ArrayList<>();
    private static String win = "Draw!";
    private String fileName;

    @Override
    public void parse(String filename) {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        try{
            XMLEventReader reader = factory.createXMLEventReader(new FileInputStream(fileName));
            while (reader.hasNext()){
                int playerCount = 0;
                int stepCount =0;
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
                        players[playerCount] = player;
                        playerCount++;
                    }
                    else if(startElement.getName().getLocalPart().equals("Step")){
                        event = reader.nextEvent();
                        String stringStep = event.asCharacters().getData();
                        int [] mas = inputDataAdapter(stringStep);
                        Step step = new Step(players[stepCount%2].getMark(), 0, 0);
                        step.setX(mas[0]);
                        step.setY(mas[1]);
                        steps.add(step);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void play(){
        for (int i = 0; i < steps.size(); i++) {
            System.out.println("Saved games:");
            File file = new File("");
            File path = new File(file.getAbsolutePath());
            List<File> files = new ArrayList<>();
            for(File f:path.listFiles()){
                if(f.isFile() && f.getName().endsWith(".xml")) {
                    if(!f.getName().equals("pom.xml")) files.add(f);
                }
            }

            for (int i = 0; i < files.size(); i++) {
                System.out.printf("%d - %s", (i+1), files.get(i).getName());
                System.out.println();
            }
            String result = null;
            System.out.println("Enter number of game:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                result = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            fileName = files.get(Integer.parseInt(result)-1).getName();

            parse(fileName);

            int stepCount = 1;
            for (int i = 0; i < steps.size(); i++) {
                if(stepCount%2 == 1){
                    gameField[steps.get(i).getX()][steps.get(i).getY()] = "X";
                }
                else  gameField[steps.get(i).getX()][steps.get(i).getY()] = "0";
                print(gameField);
                System.out.println();
                stepCount++;
            }
            if(players.size() == 3){
                Player winner = players.get(2);
                System.out.printf("Player %d -> %s is winner as \'%s\'!", winner.getId(),winner.getName(),winner.getSymbol());
            }
            else System.out.println(win);
            view.refresh(gameField);
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
