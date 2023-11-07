package Code;

import javax.swing.*;

public class Actions extends GenericSearch {

public void RequestFood() {
    this.initialFood--;
    this.monetary_cost= this.unitPriceFood+this.monetary_cost;
}
public void RequestMaterials() {
	this.initialMaterials--;
    this.monetary_cost += this.unitPriceMaterials;
}
public void RequestEnergy() {
    this.initialEnergy--;
	this.monetary_cost += this.unitPriceEnergy;
}
public void Build1() {
    this.monetary_cost += this.priceBUILD1;
    this.prosperity+=prosperityBUILD1;

    for(int i=0; i<this.materialsUseBUILD1;i++){
        RequestMaterials();
    }
    for(int i=0; i<this.energyUseBUILD1;i++){
        RequestEnergy();
    }
    for(int i=0; i<this.foodUseBUILD1;i++){
        RequestFood();
    }
}
    public void Build2() {
        this.monetary_cost += this.priceBUILD2;
        this.prosperity+=prosperityBUILD2;

        for(int i=0; i<this.materialsUseBUILD2;i++){
            RequestMaterials();
        }
        for(int i=0; i<this.energyUseBUILD2;i++){
            RequestEnergy();
        }
        for(int i=0; i<this.foodUseBUILD2;i++){
            RequestFood();
        }
    }
public void Wait() {
	
}

}
