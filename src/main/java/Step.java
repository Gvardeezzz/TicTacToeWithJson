public class Step {
    private PlayerMark mark;
    private int x;
    private int y;

    public PlayerMark getMark() {
        return mark;
    }

    public void setMark(PlayerMark mark) {
        this.mark = mark;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Step(PlayerMark mark, int x, int y) {
        this.mark = mark;
        this.x = x;
        this.y = y;
    }

    public static Step makeStep (PlayerMark[][] playerMarks,PlayerMark mark) {
        int x = -1;
        int y = -1;
        Step step = null;
        while (x < 0) {
            Utils.printMessage("Enter number of row");
            try {
                x = Utils.readInt();
                if (x > 2) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                x = -1;
                Utils.printMessage("This row not exists! Please, enter number between 0 and 2!");
            }
        }
        while (y < 0) {
            Utils.printMessage("Enter number of column");
            try {
                y = Utils.readInt();
                if (y > 2) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                y = -1;
                Utils.printMessage("This column not exists! Please, enter number between 0 and 2!");
            }
            if(playerMarks[x][y] == PlayerMark.SPACE){
                playerMarks[x][y] = mark;
                step = new Step(mark, x, y);
            }
            else {
                Utils.printMessage("This cell is not free! Please, try again!");
                makeStep(playerMarks,mark);
            }
        }
        return step;
    }
}
