public class Square {
    private int index ;
    public boolean special;
    public String playerHere = "| ";
    private boolean isSafe;
    int pieceNum = 0;

    public Square(int index){
        this.index = index;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public String getPlayerHere() {
        return playerHere;
    }
    public void setPlayerHere(String playerHere) {
        this.playerHere = playerHere;
    }
    public boolean isSafe() {
        return isSafe;
    }
    public Square Copy(){
        Square copy=new Square(this.index);
        copy.playerHere = this.playerHere;
        copy.special = this.special;
        copy.isSafe = this.isSafe;
        copy.pieceNum = this.pieceNum;
        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Square square = (Square) obj;
        return index == square.index && pieceNum == square.pieceNum;
    }
    public void clearOne(){
        this.pieceNum--;
        if(this.pieceNum == 0)
            this.playerHere = "| ";
    }
    public void addOne(String who){
        this.pieceNum++;
        this.playerHere = who;
    }

    @Override
    public String toString() {
        return playerHere + index;
    }
}
