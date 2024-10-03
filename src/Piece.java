import java.util.Objects;

public class Piece {
    private String who;
    private boolean onBoard;
    private boolean isCooked;
    public Square square;

    public boolean isOnBoard() {
        return onBoard;
    }
    public void setOnBoard(boolean onBoard) {
        this.onBoard = onBoard;
    }
    public Piece(){
        this.onBoard = false;
        this.isCooked = false;
        this.square = null;
    }
    public boolean isCooked() {
        return isCooked;
    }
    public void setCooked(boolean cooked) {
        isCooked = cooked;
    }

    public Piece Copy(){
        Piece piece = new Piece();
        if (this.onBoard && !this.isCooked)
            piece.square = this.square.Copy();
        piece.isCooked = this.isCooked;
        piece.onBoard  = this.onBoard ;
        piece.who = this.who;
        return piece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        if (this.square == null || piece.square == null) return false;
        return onBoard == piece.onBoard && isCooked == piece.isCooked && Objects.equals(who, piece.who) && Objects.equals(square, piece.square);
    }

    @Override
    public int hashCode() {
        return Objects.hash(who, onBoard, isCooked, square);
    }

    public void setIndex(int target) {
        this.square.setIndex(target);
    }

    @Override
    public String toString() {
        return this.square.getIndex() +
                "- \n";
    }
}
