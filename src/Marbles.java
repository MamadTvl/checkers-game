public class Marbles {
    public String Color;
    public boolean King;
    private int BoardCode;

    Marbles(String color, int boardCode) {
        Color = color;
        King = false;
        BoardCode = boardCode;
    }

    public int getBoardCode() {
        return BoardCode;
    }

    public void GoLeftUp() {
        BoardCode += 7;
    }

    public void GoLeftDown() {
        BoardCode -= 9;
    }

    public void GoRightUp() {
        BoardCode += 9;
    }

    public void GoRightDown() {
        BoardCode -= 7;
    }

    public void setKing(){
        King = true;
    }

}