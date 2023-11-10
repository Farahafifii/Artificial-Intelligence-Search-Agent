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
    public int currDelay  = 0 ;
    public Action currActionDelay;
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
    public List<Node> generateChildNodes(){
        List<Node> children = new ArrayList<>();
        if((this.action == Action.REQUEST_FOOD && this.currDelay > 0) || (this.action == Action.REQUEST_ENERGY && this.currDelay > 0) ||(this.action == Action.REQUEST_MATERIALS && this.currDelay > 0)){
            for (Action a : Action.values()){
                if(a == Action.BUILD1 || a==Action.BUILD2 || a==Action.WAIT ){
                    State newState = new State(this.state.food ,this.state.materials , this.state.energy , this.state.prosperity , this.state.monetary_cost);
                    newState = applyAction(newState , a);
                    if (newState!=null) {
                        Node newNode = new Node(newState, GenericSearch.currNode, a);
                        newNode.currDelay = this.currDelay < 0 ? 0 : this.currDelay - 1;
                        if (this.currDelay == 1) {
                            Node n1 = updateValues(newNode);
                            newNode.currActionDelay = null;
                            children.add(n1);
                        } else
                            children.add(newNode);
                    }
                }
            }
        }
        else if (this.currDelay ==0){
            for (Action a : Action.values()){
                if(!(a == Action.WAIT)){
                    State newState = new State(this.state.food ,this.state.materials , this.state.energy , this.state.prosperity , this.state.monetary_cost);
                    newState = applyAction(newState , a);
                    if (newState!=null) {
                        Node newNode = new Node(newState, GenericSearch.currNode, a);
                        if (a == Action.REQUEST_FOOD) {
                            newNode.currDelay = GenericSearch.delayRequestFood + 1;
                            newNode.currActionDelay = Action.REQUEST_FOOD;
                        } else if (a == Action.REQUEST_ENERGY) {
                            newNode.currDelay = GenericSearch.delayRequestEnergy + 1;
                            newNode.currActionDelay = Action.REQUEST_ENERGY;
                        } else if (a == Action.REQUEST_MATERIALS) {
                            newNode.currDelay = GenericSearch.delayRequestMaterials + 1;
                            newNode.currActionDelay = Action.REQUEST_MATERIALS;
                        }
                        children.add(newNode);
                    }
                }
            }
        }
        else {
            if ( this.currDelay > 0 ){
                for (Action a : Action.values()){
                    if(!(a == Action.REQUEST_FOOD) && !(a==Action.REQUEST_ENERGY) && !(a==Action.REQUEST_MATERIALS)){
                        State newState = new State(this.state.food ,this.state.materials , this.state.energy , this.state.prosperity , this.state.monetary_cost);
                        newState = applyAction(newState , a);
                        if (newState!=null) {
                            Node newNode = new Node(newState, GenericSearch.currNode, a);
                            newNode.currDelay = this.currDelay <= 0 ? 0 : this.currDelay - 1;
                            if (this.currDelay <= 1) {
                                Node n1 = updateValues(newNode);
                                newNode.currActionDelay = null;
                                children.add(n1);
                            } else
                                children.add(newNode);
                        }
                    }
                }
            }
        }
        return children;
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
        s.monetary_cost += GenericSearch.unitPriceMaterials + GenericSearch.unitPriceEnergy + GenericSearch.unitPriceFood;
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
        if (state.food < 0 || state.materials < 0 || state.energy < 0  ) {
            return null ;
        }
        return state ;
    }
    public State Build2(State state) {
//        state.monetary_cost += GenericSearch.priceBUILD2;
        state.prosperity += GenericSearch.prosperityBUILD2;
        state.energy -= GenericSearch.energyUseBUILD2;
        state.food -= GenericSearch.foodUseBUILD2 ;
        state.materials-=GenericSearch.materialsUseBUILD2;
        state.monetary_cost += GenericSearch.priceBUILD2+
                ((GenericSearch.energyUseBUILD2*GenericSearch.unitPriceEnergy)+
                ( GenericSearch.foodUseBUILD2*GenericSearch.unitPriceFood)+
                (GenericSearch.materialsUseBUILD2*GenericSearch.unitPriceMaterials) );
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
        return state ;
    }
//    public void updateDelay(){
//        if(currDelay > 0)
//            currDelay -- ;
//        if (currDelay<=0) {
//            currDelay = 0;
//            currActionDelay = null;
//        }
//    }
//    public void setCurrDelay(Action a){
//        if(a== Action.REQUEST_ENERGY){
//            currDelay = GenericSearch.delayRequestEnergy;
//            currActionDelay= Action.REQUEST_ENERGY;
//        } else if (a == Action.REQUEST_FOOD) {
//            currDelay=GenericSearch.delayRequestFood;
//            currActionDelay = Action.REQUEST_FOOD;
//        } else if (a==Action.REQUEST_MATERIALS) {
//            currDelay = GenericSearch.delayRequestMaterials;
//            currActionDelay = Action.REQUEST_MATERIALS;
//        }
//    }

    public List<Node> getPathToNode() {
        return pathToNode;
    }

    public void setPathToNode(List<Node> pathToNode) {
        this.pathToNode = pathToNode;
    }
    public Node updateValues(Node n ){
        if(n.currActionDelay==Action.REQUEST_FOOD)
            n.state.food += GenericSearch.amountRequestFood;
        else if (n.currActionDelay==Action.REQUEST_MATERIALS)
            n.state.materials += GenericSearch.amountRequestMaterials;
        else
            n.state.energy += GenericSearch.amountRequestEnergy;
        return n ;
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
