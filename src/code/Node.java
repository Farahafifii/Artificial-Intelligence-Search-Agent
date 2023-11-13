package code;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public State state ;
    public Node parentNode;
    public Action action;
    public int depth = 0 ;
    public int delayFood  = 0 ;
    public int delayEnergy = 0 ;
    public int delayMaterials = 0 ;

    public Node(State state, Node parentnode,Action action) {
        this.state = state ;
        this.parentNode = parentnode ;
        if(parentnode==null){
            this.depth= 0 ;
        }
        else{
            this.depth = parentnode.depth + 1 ;
        }
        this.action = action;
}
public List<Node> generateChildNodes() {
    List<Node> children = new ArrayList<>();
    if (this.delayEnergy == 0 && this.delayFood == 0 && this.delayMaterials == 0) {
        for (Action a : Action.values()) {
            if (!(a == Action.WAIT)) {

                State newState = new State( this.state.food, this.state.materials, this.state.energy, this.state.prosperity, this.state.monetary_cost);
                newState = applyAction(newState, a);
                if (newState != null) {
                    Node newNode = new Node(newState, GenericSearch.currNode, a);
                    if (a == Action.REQUEST_FOOD) {
                        newNode.delayFood = GenericSearch.delayRequestFood;
                    } else if (a == Action.REQUEST_ENERGY) {
                        newNode.delayEnergy = GenericSearch.delayRequestEnergy;
                    } else if (a == Action.REQUEST_MATERIALS) {
                        newNode.delayMaterials = GenericSearch.delayRequestMaterials;
                    }
                    children.add(newNode);
                }
            }
        }
    } else if (this.delayEnergy > 0 || this.delayFood > 0 || this.delayMaterials > 0) {
        for (Action a : Action.values()) {
            if (!(a == Action.REQUEST_FOOD) && !(a == Action.REQUEST_ENERGY) && !(a == Action.REQUEST_MATERIALS)) {
                State newState = new State( this.state.food, this.state.materials, this.state.energy, this.state.prosperity, this.state.monetary_cost);
                Node newNode = new Node(newState, this, a);
                if (this.delayFood > 0) {
                    newNode.delayFood = this.delayFood - 1;
                    if (newNode.delayFood == 0) {
                        newState = updateValues(new State(newNode.state.food, newNode.state.materials, newNode.state.energy, newNode.state.prosperity, newNode.state.monetary_cost), Action.REQUEST_FOOD);
                        newNode.state = newState;
                    }
                } else if (this.delayEnergy > 0) {
                    newNode.delayEnergy = this.delayEnergy - 1;
                    if (newNode.delayEnergy == 0) {
                        newState = updateValues(new State(newNode.state.food, newNode.state.materials, newNode.state.energy, newNode.state.prosperity, newNode.state.monetary_cost), Action.REQUEST_ENERGY);
                        newNode.state = newState;
                    }
                } else if (this.delayMaterials > 0) {
                    newNode.delayMaterials = this.delayMaterials - 1;
                    if (newNode.delayMaterials == 0) {
                        newState = updateValues(new State(newNode.state.food, newNode.state.materials, newNode.state.energy, newNode.state.prosperity, newNode.state.monetary_cost), Action.REQUEST_MATERIALS);
                        newNode.state = newState;
                    }
                }
                newState = applyAction(newState, a);
                if(newState!=null) {
                    newNode.state = newState;
                    children.add(newNode);
                }
            }
        }
    }
    return  children;
}


    public State applyAction(State newState , Action currAction){
        if (currAction == Action.REQUEST_ENERGY){
            newState = RequestEnergy(newState);
        } else if (currAction == Action.REQUEST_MATERIALS) {
            newState =RequestMaterials(newState);
        } else if (currAction==Action.REQUEST_FOOD) {
            newState =RequestFood(newState);
        } else if (currAction==Action.BUILD1) {
            newState =Build1(newState);
        } else if (currAction==Action.BUILD2) {
            newState =Build2(newState);
        }
        else{
            newState = Wait(newState);
        }
        return newState;
    }
    public State RequestFood(State s) {
        s.food--;
        s.materials -- ;
        s.energy -- ;
        s.monetary_cost += GenericSearch.unitPriceMaterials + GenericSearch.unitPriceEnergy + GenericSearch.unitPriceFood;
        state.prosperity = Math.min(state.prosperity, 100);
        if (s.food < 0 || s.materials < 0 || s.energy < 0  ) {
            return null ;
        }
        return s;
    }
    public State RequestMaterials(State state) {
        state.materials--;
        state.food -- ;
        state.energy -- ;
        state.monetary_cost += GenericSearch.unitPriceMaterials + GenericSearch.unitPriceEnergy + GenericSearch.unitPriceFood;
        state.prosperity = Math.min(state.prosperity, 100);
        if (state.food < 0 || state.materials < 0 || state.energy < 0  ) {
            return null ;
        }
        return state ;
    }
    public State RequestEnergy(State state) {
        state.food--;
        state.materials -- ;
        state.energy -- ;
        state.monetary_cost += GenericSearch.unitPriceMaterials + GenericSearch.unitPriceEnergy + GenericSearch.unitPriceFood;
        state.prosperity = Math.min(state.prosperity, 100);
        if (state.food < 0 || state.materials < 0 || state.energy < 0  ) {
            return null ;
        }
        return state;
    }
    public State Build1(State state) {
        state.prosperity += GenericSearch.prosperityBUILD1;
        state.energy -= GenericSearch.energyUseBUILD1;
        state.food -= GenericSearch.foodUseBUILD1 ;
        state.materials-=GenericSearch.materialsUseBUILD1;
        state.monetary_cost += GenericSearch.priceBUILD1+
                ((GenericSearch.energyUseBUILD1*GenericSearch.unitPriceEnergy)+
                ( GenericSearch.foodUseBUILD1*GenericSearch.unitPriceFood)+
                (GenericSearch.materialsUseBUILD1*GenericSearch.unitPriceMaterials) );
        state.prosperity = Math.min(state.prosperity, 100);
        if (state.food < 0 || state.materials < 0 || state.energy < 0  ) {
            return null ;
        }
        return state ;
    }
    public State Build2(State state) {
        state.prosperity += GenericSearch.prosperityBUILD2;
        state.energy -= GenericSearch.energyUseBUILD2;
        state.food -= GenericSearch.foodUseBUILD2 ;
        state.materials-=GenericSearch.materialsUseBUILD2;
        state.monetary_cost += GenericSearch.priceBUILD2+
                ((GenericSearch.energyUseBUILD2*GenericSearch.unitPriceEnergy)+
                ( GenericSearch.foodUseBUILD2*GenericSearch.unitPriceFood)+
                (GenericSearch.materialsUseBUILD2*GenericSearch.unitPriceMaterials) );
        state.prosperity = Math.min(state.prosperity, 100);
        if (state.food < 0 || state.materials < 0 || state.energy < 0  ) {
            return null ;
        }
        return state;
    }
    public State Wait(State state) {
        state.food--;
        state.materials -- ;
        state.energy -- ;
        state.monetary_cost += GenericSearch.unitPriceMaterials + GenericSearch.unitPriceEnergy + GenericSearch.unitPriceFood;
        if (state.food < 0 || state.materials < 0 || state.energy < 0  ) {
            return null ;
        }
        return state;
    }
    public State updateValues(State n  , Action a){
        if(a==Action.REQUEST_FOOD)
            n.food += GenericSearch.amountRequestFood;
        else if (a==Action.REQUEST_MATERIALS)
            n.materials += GenericSearch.amountRequestMaterials;
        else if ( a==Action.REQUEST_ENERGY)
            n.energy += GenericSearch.amountRequestEnergy;
        return new State(n.food, n.materials,n.energy,n.prosperity,n.monetary_cost);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-->Node{");
        sb.append("state=").append("State{" +
                "food=" + state.food +
                ", materials=" + state.materials +
                ", energy=" + state.energy +
                ", prosperity=" + state.prosperity +
                ", monetary_cost=" + state.monetary_cost +
                '}');
        sb.append(", action=").append(action);
        sb.append(", depth=").append(depth);
        sb.append("} <--");
        return sb.toString();
    }

}
