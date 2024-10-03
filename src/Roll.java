import java.util.Objects;
import java.util.Random;

public class Roll {
    int steps;
    boolean Khal;
    boolean again;

    public Roll(int steps , boolean Khal , boolean again){
        this.steps = steps;
        this.Khal = Khal;
        this.again = again;
    }
    public Roll (){
        Roll roll = rollDice();
        this.steps = roll.steps;
        this.again = roll.again;
        this.Khal = roll.Khal;
    }
    public boolean isAgain() {
        return again;
    }
    public int getSteps() {
        return steps;
    }
    public boolean isKhal() {
        return Khal;
    }
    public Roll rollDice(){
        Random random = new Random();
        int chance = random.nextInt(27);
        Roll roll = null;
        switch (chance){
            case 0 :
            case 1 :
            case 2 :
                roll = Item.DUST;
                break;
            case 3 :
            case 4 :
            case 5 :
            case 6 :
                roll = Item.BANJ;
                break;
            case 7 :
                roll = Item.SHAKKA;
                break;
            case 8 :
            case 9 :
                roll = Item.PARA;
                break;
            case 10 :
            case 11 :
            case 12 :
            case 13 :
            case 14 :
                roll = Item.FOUR;
                break;
            case 15 :
            case 16 :
            case 17 :
            case 18 :
            case 19 :
            case 20 :
            case 21 :
                roll = Item.THREE;
                break;
            case 22 :
            case 23 :
            case 24 :
            case 25 :
            case 26 :
            case 27 :
                roll = Item.DOU;
                break;
        }
        return roll;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Roll roll = (Roll) obj;
        return steps == roll.steps && Khal == roll.Khal && again == roll.again;
    }

    @Override
    public int hashCode() {
        return Objects.hash(steps, Khal,again);
    }

}
