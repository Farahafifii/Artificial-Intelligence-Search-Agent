package Code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Node {
    public State state ;
    public Node parentNode;
    public Action action;
    public int depth = 0 ;
    public int pathCost;
    boolean onDelivery = false;
    public List<Node> pathToNode ;
    public Node(State state, Node parentnode,Action action) {
        this.state = state ;
        this.parentNode = parentnode ;
        if(parentnode==null){
            this.depth= 0 ;
        }
        else{
            this.depth = parentnode.depth + 1 ;
        }
        this.pathCost = 0 ;
        this.action = action;
        this.pathToNode = new ArrayList<Node>();
}

    public List<Node> generateChildNodes() {
        List<Node> childNodes = new ArrayList<>();
        List<Node> nodesPath = this.pathToNode;
        if (this.action==Action.REQUEST_ENERGY || this.action==Action.REQUEST_FOOD || this.action==Action.REQUEST_MATERIALS){
            for (Action actions : Action.values()){
                if(actions==Action.BUILD1 || actions==Action.BUILD2 || actions==Action.WAIT ){
                    State newState = new State(this.state.food ,this.state.materials , this.state.energy , this.state.prosperity , this.state.monetary_cost);
                    newState = applyAction(newState , actions);
                    Node newNode = new Node(newState , GenericSearch.currNode, actions);
                    newNode.pathToNode = nodesPath;
                    newNode.pathToNode.add(newNode);
                    childNodes.add(newNode);
                }
            }
        }
        else if(GenericSearch.currDelay >0){
            for(Action actions : Action.values()){
                if(actions == Action.REQUEST_ENERGY || actions == Action.REQUEST_FOOD || actions == Action.REQUEST_MATERIALS ){
                    continue;
                }
                else {
                    State newState = new State(this.state.food ,this.state.materials , this.state.energy , this.state.prosperity , this.state.monetary_cost);
                    newState = applyAction(newState , actions);
                    Node newNode = new Node(newState , GenericSearch.currNode, actions);
                    newNode.pathToNode = nodesPath;
                    newNode.pathToNode.add(newNode);
                    childNodes.add(newNode);
                }
            }
        }
        else if (GenericSearch.currDelay==0){
            for(Action actions : Action.values()){
                if(actions == Action.WAIT){
                    continue;
                }
                State newState = new State(this.state.food ,this.state.materials , this.state.energy , this.state.prosperity , this.state.monetary_cost);
                newState = applyAction(newState , actions);
                Node newNode = new Node(newState , GenericSearch.currNode, actions);
                newNode.pathToNode = nodesPath;
                newNode.pathToNode.add(newNode);
                childNodes.add(newNode);
            }
        }
        return childNodes;
    }

    public State applyAction(State s , Action currAction){
        State newState = s;
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
            Wait(newState);
        }
        return newState;
    }
    public State RequestFood(State s) {
        s.food--;
        s.materials -- ;
        s.energy -- ;
        s.monetary_cost += GenericSearch.unitPriceFood;
        onDelivery = true;
        GenericSearch.currDelay = GenericSearch.delayRequestFood;
        GenericSearch.currActionDelay = Action.REQUEST_FOOD;
        return state;
    }
    public State RequestMaterials(State state) {
        state.materials--;
        state.food -- ;
        state.energy -- ;
        state.monetary_cost += GenericSearch.unitPriceMaterials;
        GenericSearch.currDelay = GenericSearch.delayRequestMaterials;
        GenericSearch.currActionDelay = Action.REQUEST_MATERIALS;
        return state ;
    }
    public State RequestEnergy(State state) {
        state.food--;
        state.materials -- ;
        state.energy -- ;
        state.monetary_cost += GenericSearch.unitPriceEnergy;
        GenericSearch.currDelay = GenericSearch.delayRequestEnergy;
        GenericSearch.currActionDelay = Action.REQUEST_ENERGY;
        return state;
    }
    public State Build1(State state) {
        state.monetary_cost += GenericSearch.priceBUILD1;
        state.prosperity += GenericSearch.prosperityBUILD1;
        state.energy -= GenericSearch.energyUseBUILD1;
        state.food -= GenericSearch.foodUseBUILD1 ;
        state.materials-=GenericSearch.materialsUseBUILD1;
        if(GenericSearch.currDelay>0 )
            GenericSearch.currDelay-- ;
        return state ;
    }
    public State Build2(State state) {
        state.monetary_cost += GenericSearch.priceBUILD2;
        state.prosperity += GenericSearch.prosperityBUILD2;
        state.energy -= GenericSearch.energyUseBUILD2;
        state.food -= GenericSearch.foodUseBUILD2 ;
        state.materials-=GenericSearch.materialsUseBUILD2;
        if (GenericSearch.currDelay == 0 ) {
            if(GenericSearch.currActionDelay == Action.REQUEST_ENERGY)
                state.materials+=GenericSearch.amountRequestEnergy;
            else if (GenericSearch.currActionDelay ==  Action.REQUEST_MATERIALS)
                state.materials += GenericSearch.amountRequestMaterials;
            else if (GenericSearch.currActionDelay == Action.REQUEST_FOOD)
                state.food+=GenericSearch.amountRequestFood;
            GenericSearch.currActionDelay = null;
        }
        else if(GenericSearch.currDelay>0 )
            GenericSearch.updateDelay();
        return state;
    }
    public State Wait(State state) {
        if(GenericSearch.currDelay <= 0 ){
            state.food += GenericSearch.amountRequestFood;
            state.materials += GenericSearch.amountRequestMaterials;
            state.energy += GenericSearch.amountRequestEnergy;
            onDelivery =false;
        }
        GenericSearch.updateDelay();

        return state ;
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
