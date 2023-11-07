package Code;

import javax.swing.*;

public class Actions extends GenericSearch {

public void RequestFood() {
    this.initialFood--;
    this.monetary_cost= this.unitPriceFood+this.monetary_cost;
}
public void RequestMaterials() {
	this.initialMaterials--;
    this.monetary_cost= this.unitPriceMaterials+this.monetary_cost;
}
public void RequestEnergy() {
    this.initialEnergy--;
	this.monetary_cost= this.unitPriceEnergy+this.monetary_cost;
}
public void Build1() {
	
}
public void Wait() {
	
}
public void Build2() {
	
}
}
