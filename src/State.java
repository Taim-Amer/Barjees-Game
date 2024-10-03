import java.util.ArrayList;
import java.util.Objects;

public class State {
    ArrayList<Player> Players;
    int value;
    public String[] board;

    public State(){
        board=new String[84];
        for (int I = 0; I < 84; I++) {
            board[I] ="_|";
        }
        squares =new ArrayList<>();
        for(int i = 0 ; i <= 83 ; i++ ){
            this.squares.add(new Square(i));
        }
        this.Players=new ArrayList<>();
        this.Players.add(new Player("@"));
        this.Players.add(new Player("#"));
    }
    public void refreshBoard(){
        for(int i = 0 ; i <= 83 ; i++ ){
            String ll="";
            if(!this.Players.get(0).indexes[i].playerHere.equals("| ")) ll+=this.Players.get(0).indexes[i].playerHere;
            if(!this.Players.get(1).indexes[i].playerHere.equals("| ")) ll+=this.Players.get(1).indexes[i].playerHere;
            if(!ll.equals("")){
                this.board[i]=ll+"_|";
            }
        }
    }
    public void draw(){

        for(String item : this.board){
            System.out.printf(item);
        }
        System.out.println();
    }
    public boolean Final(){
        return Players.get(0).checkFinal() || Players.get(1).checkFinal();
    }

    public State Copy(){
        State copy=new State();
        for(int i = 0 ; i < 83 ; i++ ){
            this.squares.add(this.squares.get(i).Copy());
        }
        copy.Players=new ArrayList<>();
        copy.setValue(this.value);
        copy.Players.add(this.Players.get(0).Copy());
        copy.Players.add(this.Players.get(1).Copy());
        return copy;
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    ArrayList<Square> squares;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Players.get(0).equals(state.Players.get(0)) && Players.get(1).equals(state.Players.get(1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(squares, Players);
    }
}
