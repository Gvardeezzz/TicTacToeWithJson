import java.util.Comparator;

public class Player {
    private int id;
    private String name;
    private PlayerMark mark;

    private int wins;
    private int draws;
    private int loses;

    public Player(){
        this.id = id;
        this.name = name;
        this.mark = mark;
        this.wins = wins;
        this.draws = draws;
        this.loses = loses;
    }
    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerMark getMark() {
        return mark;
    }

    public void setMark(PlayerMark mark) {
        this.mark = mark;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }
}
