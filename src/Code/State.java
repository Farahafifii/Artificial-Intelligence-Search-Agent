package Code;
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

    // Getters and setters for each attribute

    public int getFoodLevel() {
        return food;
    }

    public void setFoodLevel(int foodLevel) {
        this.food = foodLevel;
    }

    public int getMaterialsLevel() {
        return materials;
    }

    public void setMaterialsLevel(int materialsLevel) {
        this. materials = materialsLevel;
    }

    public int getEnergyLevel() {
        return  energy;
    }

    public void setEnergyLevel(int energyLevel) {
        this.energy = energyLevel;
    }

    public int getProsperityLevel() {
        return prosperity;
    }

    public void setProsperityLevel(int prosperityLevel) {
        this.prosperity = prosperityLevel;
    }
    public int getMonetary_cost() {
        return monetary_cost;
    }

    public void setMonetary_cost(int money_spent) {
        this.monetary_cost = money_spent;
    }

}

