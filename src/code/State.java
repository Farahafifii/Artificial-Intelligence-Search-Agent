package code;
import java.util.Random;
public class State {
    public int food;
    public int materials;
    public int energy;
    public int prosperity;
    public int monetary_cost;
    public int heuristic1;
    public int heuristic2;
    public int admissible_heuristic1;
    public int admissible_heuristic2;
    // Constructor


    public State( int foodLevel, int materialsLevel, int energyLevel, int prosperityLevel, int monetary_cost) {

        this.food = foodLevel;
        this.materials = materialsLevel;
        this.energy = energyLevel;
        this.prosperity = prosperityLevel;
        this.monetary_cost = monetary_cost ;

        this.heuristic1 =(int) Math.ceil( (100-this.prosperity) /GenericSearch.maxProperityBuild )*GenericSearch.minBuild;
        this.heuristic2 = (int) Math.ceil((100-this.prosperity) /GenericSearch.maxProperityBuild )*(GenericSearch.minFood+GenericSearch.delayRequestFood)*(GenericSearch.unitPriceFood);
        this.admissible_heuristic1= this.heuristic1*this.monetary_cost;
        this.admissible_heuristic2= this.heuristic2*this.monetary_cost;


    }
    public String toString(){
        return "State{" +
                "food=" + this.food +
                ", materials=" + this.materials +
                ", energy=" + this.energy +
                ", prosperity=" + this.prosperity +
                ", monetary_cost=" + this.monetary_cost +
                '}';
    }

}

