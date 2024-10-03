
import java.util.List;

public class ExpectiMinMax {

    private static int evaluateState(State state) {
        int val = 0;

        val += evaluatePlayerPieces(state.Players.get(1).getPieces(), true); //Player1
        val += evaluatePlayerPieces(state.Players.get(0).getPieces(), false); //Player2

        state.setValue(val);
        return val;
    }
    private static int evaluatePlayerPieces(List<Piece> pieces, boolean max) {
        int val = 0;

        for (Piece item : pieces) {

            if (item.isOnBoard() && item.square.isSafe()) {
                val += 2;
            }

            if (!item.isOnBoard()) {
                val += 5;
            }

            if (max && item.isCooked()) {
                val += 10;
            }
        }
        return val;
    }
    private static void printDepthValue(int depth, int value, String type) {
        System.out.println("Depth: " + depth + ", " + type + " Value: " + value);
    }
    public static State expectiminimax(State state, int depth, boolean isMaxPlayer, Roll roll) {
        if (depth == 0 || state.Final()) {
            printDepthValue(depth, evaluateState(state), "Leaf");
            return state;
        }

        if (isMaxPlayer) {
            return maxPlayerMove(state, depth, roll);
        } else {
            return minPlayerMove(state, depth, roll);
        }
    }
    private static State maxPlayerMove(State state, int depth, Roll roll) {
        State bestState = state.Copy();
        int bestValue = Integer.MIN_VALUE;
        List<State> children = Logic.NextState("#", state, roll);

        for (State child : children) {
            State evaluatedChild = expectiminimax(child, depth - 1, false, roll);
            int value = evaluateState(child);

            if (value > bestValue) {
                bestValue = value;
                bestState = evaluatedChild;
            }
        }

        state.setValue(bestValue);
        printDepthValue(depth, bestValue, "Max");
        return bestState;
    }
    private static State minPlayerMove(State state, int depth, Roll roll) {
        int totalValue = 0;
        List<State> children = Logic.NextState("@", state, roll);

        if (children.isEmpty())
            return state;

        for (State child : children) {
            State evaluatedChild = expectiminimax(child, depth - 1, true, roll);
            int value = evaluatedChild.getValue();
            totalValue += value;
        }

        state.setValue(totalValue / children.size());
        printDepthValue(depth, totalValue / children.size(), "Min");
        return state;
    }
}
