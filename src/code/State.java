package code;
public class State {
    public int food;
    public int materials;
    public int energy;
    public int prosperity;
    public int monetary_cost;

    // Constructor
    public State(int foodLevel, int materialsLevel, int energyLevel, int prosperityLevel, int monetary_cost) {
        this.food = foodLevel;
        this.materials = materialsLevel;
        this.energy = energyLevel;
        this.prosperity = prosperityLevel;
        this.monetary_cost = monetary_cost ;
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

