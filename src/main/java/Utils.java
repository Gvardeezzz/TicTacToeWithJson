import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class Utils {
    private String mode;

    public static void printMessage(String string) {
        System.out.println(string);
    }

    public static String readMessage() {
        String result = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            result = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int readInt() {
        String s = null;
        int result = 0;
        while (s == null) {
        s = readMessage();
            try {
                result = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                Utils.printMessage("Enter a number!");
                s = null;
            }
        }
        return result;
    }

    public static void sendStatisticsToFile(ArrayList<String> strings){
        try {
            String title = "|ID        |NAME      |WINS      |DRAWS     |LOSES     |\r\n";
            FileOutputStream fileOutputStream = new FileOutputStream("rating.txt");
            fileOutputStream.write(title.getBytes(StandardCharsets.UTF_8));
            for (int i = 0; i < strings.size(); i++) {
                fileOutputStream.write(strings.get(i).getBytes(StandardCharsets.UTF_8));
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String selectMode(){
        String fileType = null;
        String choise = "";
        while (!(choise.equals("1") || choise.equals("2"))){
            Utils.printMessage("If you want to use XML-mode enter \'1\'");
            Utils.printMessage("If you want to use JSON-mode enter \'2\'");
            choise = Utils.readMessage();
        }
        switch (choise){
            case "1" -> fileType = ".xml";
            case "2" -> fileType = ".json";
        }
        return fileType;
    }

      public static ArrayList<String> makeOutputData(ArrayList<Player> players){
          ArrayList<String> result = new ArrayList<>();
          Comparator<Player> plr = Comparator.comparing(Player::getWins, Comparator.reverseOrder()).thenComparing(Player::getDraws, Comparator.reverseOrder()).thenComparing(Player::getLoses).
                  thenComparing(Player::getName).thenComparing(Player::getId);
      players.sort(plr);
      for(Player player : players){
          StringBuilder sb = new StringBuilder("|");
          sb.append(player.getId());
          int len = 11;
          while (sb.length()<len){
              sb.append(" ");
          }
          sb.append("|");
          len += 11;
          sb.append(player.getName());
          while (sb.length()<len){
              sb.append(" ");
          }
          sb.append("|");
          len += 11;
          sb.append(player.getWins());
          while (sb.length()<len){
              sb.append(" ");
          }
          sb.append("|");
          len += 11;
          sb.append(player.getDraws());
          while (sb.length()<len){
              sb.append(" ");
          }
          sb.append("|");
          len += 11;
          sb.append(player.getLoses());
          while (sb.length()<len){
              sb.append(" ");
          }
          sb.append("|");
          sb.append("\r\n");
          result.add(sb.toString());
      }
        return result;
    }
}
