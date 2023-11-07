package Code;

import javax.swing.*;

public class Actions extends GenericSearch {

public void RequestFood() {
    this.food--;
    this.monetary_cost += this.unitPriceFood;
    if(isWaiting&&isDelivering){
        this.food += this.amountRequestFood;
    }
}
public void RequestMaterials() {
	this.materials--;
    this.monetary_cost += this.unitPriceMaterials;
    if(isWaiting&&isDelivering){
        this.materials += this.amountRequestMaterials;
    }
}
public void RequestEnergy() {
    this.energy --;
	this.monetary_cost += this.unitPriceEnergy;
    if(isWaiting&&isDelivering){
        this.energy += this.amountRequestEnergy;
    }
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
