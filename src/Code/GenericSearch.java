package Code;

public class GenericSearch {

    public int initialProsperity;
    public int initialFood, initialMaterials, initialEnergy;
    public int unitPriceFood, unitPriceMaterials, unitPriceEnergy;
    public int amountRequestFood, delayRequestFood;
    public int amountRequestMaterials, delayRequestMaterials;
    public int amountRequestEnergy, delayRequestEnergy;
    public int priceBUILD1, foodUseBUILD1;
    public int materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1;
    public int priceBUILD2, foodUseBUILD2;
    public int materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2;
    public int monetary_cost = 0 ;

    public boolean isWaiting = false ;
    public boolean isDelivering = false ;

    public static String[][] splitString(String input) {
        String[] semiColonSplit = input.split(";");
        String[][] result = new String[semiColonSplit.length][];

        for (int i = 0; i < semiColonSplit.length; i++) {
            result[i] = semiColonSplit[i].split(",");
        }

        return result;
    }
    public void assignVar(String[][] state){
        this.initialProsperity = Integer.parseInt(state[0][0]) ;
        this.initialFood = Integer.parseInt(state[1][0]);
        initialMaterials=Integer.parseInt(state[1][1]);
        initialEnergy =Integer.parseInt(state[1][2]);
        unitPriceFood = Integer.parseInt(state[2][0]);
        unitPriceMaterials = Integer.parseInt(state[2][1]);
        unitPriceEnergy= Integer.parseInt(state[2][2]);
        amountRequestFood = Integer.parseInt(state[3][0]);
        delayRequestFood = Integer.parseInt(state[3][1]);
        amountRequestMaterials = Integer.parseInt(state[4][0]);
        delayRequestMaterials = Integer.parseInt(state[4][1]);
        amountRequestEnergy = Integer.parseInt(state[5][0]);
        delayRequestEnergy=Integer.parseInt(state[5][1]);
        priceBUILD1 = Integer.parseInt(state[6][0]);
        foodUseBUILD1 = Integer.parseInt(state[6][1]);
        materialsUseBUILD1 = Integer.parseInt(state[6][2]);
        energyUseBUILD1 = Integer.parseInt(state[6][3]);
        prosperityBUILD1 = Integer.parseInt(state[6][4]);
        priceBUILD2 = Integer.parseInt(state[7][0]);
        foodUseBUILD2 = Integer.parseInt(state[7][1]);
        materialsUseBUILD2= Integer.parseInt(state[7][2]);
        energyUseBUILD2 = Integer.parseInt(state[7][3]);
        prosperityBUILD2 = Integer.parseInt(state[7][4]);
    }
    public void BreadthFirst(String problem){

    }
    public Boolean GoalTest(){
        return initialProsperity ==100;
    }
    public void Generic(String search ,String problem ){
        if ( search == "BF"){
            BreadthFirst(problem);
        }

    }
}
