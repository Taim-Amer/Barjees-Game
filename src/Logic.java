import java.util.ArrayList;
import java.util.Scanner;

public class Logic {

    public static void win(Piece piece,Player player){    //For one Piece
        piece.setCooked(true);
        piece.setOnBoard(false);
        piece.square = new Square(84);
        player.pieces.remove(piece);
        player.isCooked.add(piece);
    }
    public static void move(int moves, Piece piece, State state,Player player) {
        Piece stone = new Piece();
        for (Piece i : player.pieces) {
            if (i.equals(piece)) {
                stone = i;
                break;
            }
        }
        if (check(piece, moves, state,player)) {

            if (player.who.equals("@")) {
                int old = stone.square.getIndex();
                int target = stone.square.getIndex() + moves;
                if (target > 83) return;

                if (target == 83) {
                    win(piece,player);
                }
                if (!state.Players.get(1).indexes[target].playerHere.equals("| ") && !player.indexes[target].isSafe() && !player.indexes[target].special ) {
                    kill(target,state.Players.get(1));
                }
                player.indexes[old].clearOne();
                player.indexes[target].addOne(player.who);

                stone.setIndex(target);
            } else {
                int old = stone.square.getIndex();
                int target = stone.square.getIndex() + moves;
                if (target > 83) return;

                if (target == 83) {
                    win(piece,player);
                    player.indexes[old].clearOne();
                }

                if (!state.Players.get(0).indexes[target].playerHere.equals("| ") && !player.indexes[target].isSafe() && !player.indexes[target].special ) {
                    kill(target,state.Players.get(0));
                }

                player.indexes[old].clearOne();
                player.indexes[target].addOne(player.who);

                stone.setIndex(target);

            }
        }
        state.refreshBoard();
    }
    public static void addPiece(Player player){
        for(Piece item: player.pieces) {
            if (!item.isOnBoard() && !item.isCooked()) {
                item.square = new Square(0);
                item.setOnBoard(true);
                player.piecesOut --;
                player.indexes[0].addOne(player.who);
                break;
            }
        }
    }
    public static void kill(int index,Player player){
        for(Piece item : player.pieces){
            if(item.isOnBoard() && item.square.getIndex()== index){
                item.setOnBoard(false);
                player.piecesOut ++;
                player.indexes[index].setPlayerHere("| ");
                item.square =new Square(100);
            }
        }
    }
    static boolean check(Piece piece, int moves, State state,Player player){
        if(!piece.isOnBoard())
            return false;
        int target = piece.square.getIndex() + moves;
        if(target > 83)
            return false;
        if(target == 83)
            return true;

        if( player.who.equals("@")) {
            return !state.Players.get(1).indexes[target].getPlayerHere().equals(state.Players.get(1).who) || !player.indexes[target].isSafe();
        }
        else
            return !state.Players.get(0).indexes[target].getPlayerHere().equals(state.Players.get(0).who) || !player.indexes[target].isSafe();
    }
    public static ArrayList<State> NextState(String who, State state , Roll roll) {
        ArrayList<State> result = new ArrayList<>();
        if (who.equals("@")) {
            State state1;
            for (Piece item : state.Players.get(0).getPieces()) {
                if (item.isOnBoard()) {
                    if (check(item, roll.steps , state , state.Players.get(0))) {
                        state1 = Move(0, state , Item.DOU.steps , item, Item.DOU);
                        result.add(state1);
                    }
                }
            }
            if(roll.Khal) {
                if (state.Players.get(0).piecesOut > 0) {
                    state1 = state.Copy();
                    addPiece(state1.Players.get(0));
                    State test = state1.Copy();
                    for (Piece item3 : state1.Players.get(0).getPieces()) {
                        if (item3.isOnBoard()) {
                            test = Move(0, state1, roll.steps, item3, Item.BANJ);
                            if (!test.equals(state1))
                                result.add(test);

                        }
                    }
                }

                for (Piece item2 : state.Players.get(0).getPieces()) {
                    if (item2.isOnBoard() && check(item2, 1, state,state.Players.get(0))) {
                        state1 = (Move(0, state, 1, item2, Item.BANJ));
                        result.add(state1);
                        State test = state1.Copy();
                        for (Piece item3 : state1.Players.get(0).getPieces()) {
                            if (item3.isOnBoard()) {
                                test = Move(0, state1, roll.steps, item3, Item.BANJ);
                                if (!test.equals(state1))
                                    result.add(test);
                            }
                        }
                    }

                }
            }
        }

        else if (who.equals("#"))
        {
            State state1;
            for (Piece item : state.Players.get(1).getPieces()) {
                if (item.isOnBoard()) {
                    if (check(item, roll.steps, state,state.Players.get(1))) {
                        state1 = Move(1, state, Item.DOU.steps, item, Item.DOU);
                        result.add(state1);
                    }
                }
            }
            if(roll.Khal) {
                if (state.Players.get(1).piecesOut > 1) {
                    state1 = state.Copy();
                    addPiece(state1.Players.get(1));
                    State test = state1.Copy();
                    for (Piece item3 : state1.Players.get(1).getPieces()) {
                        if (item3.isOnBoard()) {
                            test = Move(1, state1, roll.steps, item3, Item.BANJ);
                            if (!test.equals(state1))
                                result.add(test);

                        }
                    }
                }

                for (Piece item2 : state.Players.get(1).getPieces()) {
                    if (item2.isOnBoard() && check(item2, 1, state,state.Players.get(1))) {
                        state1 = (Move(1, state, 1, item2, Item.BANJ));
                        result.add(state1);
                        State test = state1.Copy();
                        for (Piece item3 : state1.Players.get(1).getPieces()) {
                            if (item3.isOnBoard()) {
                                test = Move(1, state1, roll.steps, item3, Item.BANJ);
                                if (!test.equals(state1))
                                    result.add(test);
                            }
                        }
                    }

                }
            }

        }


        return result;
    }
    public static State Move(int who, State state, int Moves, Piece stone , Roll roll) {
        State newState = state.Copy();
        if(who == 0)
            move(Moves, stone, newState, newState.Players.get(0));
        else
            move(Moves, stone, newState, newState.Players.get(1));
        return newState;
    }
    public static void playWithComputer(State state) {
        Scanner in = new Scanner(System.in);
        Roll roll = new Roll();
        String who = "#";
        while (true) {
            state.draw();
            System.out.println("steps:" + roll.steps);

            if (state.Final()) {
                state.draw();
                System.out.printf("YOU NEED TO PRACTICE MORE");
                break;
            }
            if (who.equals("@")) {

                if (state.Players.get(0).piecesOut + state.Players.get(0).isCooked.size() == 4 && !roll.isKhal()) {
                    who = "#";
                    roll = roll.rollDice();
                    continue;
                }
                if (roll.isKhal()) {
                    System.out.println("1-enter a new rock");
                    System.out.println("2-move a rock?");
                    String input = in.next();
                    if (input.equalsIgnoreCase("1")) {

                        addPiece(state.Players.get(0));
                    } else {
                        for (Piece item : state.Players.get(0).getPieces()) {
                            if (item.isOnBoard() && item.square.getIndex() != 83)
                                System.out.println(item);
                        }

                        System.out.println();
                        System.out.println("index :");
                        int index = in.nextInt();
                        Piece rock = null;
                        for (Piece item : state.Players.get(0).getPieces()) {
                            if (item.isOnBoard() && item.square.getIndex() == index) {
                                rock = item;
                            }
                        }
                        if (rock == null) {
                            System.out.printf("Invalid index");
                            continue;
                        }

                        state = Move(0, state, 1, rock, roll);
                        if (state.Final()) break;

                    }
                }
                for (Piece item : state.Players.get(0).getPieces()) {
                    if (item.isOnBoard() && item.square.getIndex() != 83)
                        System.out.println(item);
                }
                System.out.println();

                System.out.println("index");
                int index = in.nextInt();
                Piece rock = null;
                for (Piece item : state.Players.get(0).getPieces()) {
                    if (item.isOnBoard())
                        if (item.square.getIndex() == index) {
                            rock = item;
                        }
                }

                if (rock == null) {
                    System.out.println("Invalid index");
                    continue;
                }

                state = Move(0, state, roll.getSteps(), rock, roll);
                if (state.Final()) {
                    state.draw();
                    System.out.printf("USER WINS!!");
                    break;
                }

                if (roll.isAgain())
                    who = "#";

                roll = roll.rollDice();
            }
            else {
                if (state.Players.get(1).piecesOut + state.Players.get(1).isCooked.size() == 4 && !roll.isKhal()) {
                    who = "@";
                    roll = roll.rollDice();
                    continue;
                }
                System.out.println("Computer Plays");
                state = ExpectiMinMax.expectiminimax(state, 3, true, roll);

                if (state.Final()) {
                    state.draw();
                    System.out.printf("Computer WIN!");
                    break;
                }

                if (roll.isAgain())
                    who = "@";

                roll = roll.rollDice();

            }
        }

    }

}
