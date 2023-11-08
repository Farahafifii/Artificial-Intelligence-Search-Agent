package Code;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public State state ;
    //public String node;
    public Node parentNode;
    public Action action;
    public int depth = 0 ;
    public int pathCost;
    public List<Node> generatedNodes;
    int counter=0;
    boolean onDelievery= false;

    public List<Node> pathToNode ;
    public Node(State state, Node parentnode,Action action) {
        this.state = state ;
        this.parentNode = parentnode ;
        if(parentnode==null){
            this.depth= 0 ;
        }
        else{
            this.depth = parentnode.depth ++ ;
        }
        this.pathCost = 0 ;
        this.action = action;
        this.pathToNode = new ArrayList<Node>();
        this.generatedNodes = new ArrayList<Node>();
}
    public List<Node> generateChildNodes() {
        List<Node> childNodes = new ArrayList<>();
        if (this.action==Action.REQUEST_ENERGY || this.action==Action.REQUEST_FOOD || this.action==Action.REQUEST_MATERIALS){
            for (Action actions : Action.values()){
                if(actions==Action.BUILD1 || actions==Action.BUILD2 || actions==Action.WAIT ){
                    State newState = applyAction(this.state , actions);
                    Node newNode = new Node(newState , this, actions);
                    newNode.pathToNode = this.pathToNode;
                    newNode.pathToNode.add(newNode);
                    childNodes.add(newNode);
                }
            }
            //make into loop
        }
        else if(GenericSearch.currDelay >0){
            for(Action actions : Action.values()){
                if(actions == Action.REQUEST_ENERGY || actions == Action.REQUEST_FOOD || actions == Action.REQUEST_MATERIALS ){
                    continue;
                }
                else {
                    State newState = applyAction(this.state , actions);
                    Node newNode = new Node(newState , this, actions);
                    newNode.pathToNode = this.pathToNode;
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
                State newState = applyAction(this.state , actions);
                Node newNode = new Node(newState , this, actions);
                newNode.pathToNode = this.pathToNode;
                newNode.pathToNode.add(newNode);
                childNodes.add(newNode);
            }
        }
        return childNodes;
    }
    public State applyAction(State state , Action currAction){
        State newState= state;
        if (currAction == Action.REQUEST_ENERGY){
            newState = RequestEnergy(state);
        } else if (currAction == Action.REQUEST_MATERIALS) {
            newState =RequestMaterials(state);
        } else if (currAction==Action.REQUEST_FOOD) {
            newState =RequestFood(state);
        } else if (currAction==Action.BUILD1) {
            newState =Build1(state);
        } else if (currAction==Action.BUILD2) {
            newState =Build2(state);
        }
        else{
            Wait(state);
        }
        return newState;
    }
    public State RequestFood(State state) {
        if(!onDelievery) {
            state.food--;
            state.monetary_cost += GenericSearch.unitPriceFood;
            // if the delivery is pending
            onDelievery = true;
//    if(isWaiting&&isDelivering){
//        this.food += this.amountRequestFood;
//    }
            counter = 0;
        }
        return state;
    }
    public State RequestMaterials(State state) {

        if(!onDelievery)
        {
            state.materials--;
            state.monetary_cost += GenericSearch.unitPriceMaterials;
            // if the delivery is pending
            onDelievery=true;
//    if(isWaiting&&isDelivering){
//        this.materials += this.amountRequestMaterials;
//    }
            counter = 0;
        }
        return state ;
    }
    public State RequestEnergy(State state) {
        if(!onDelievery) {
            state.energy--;
            state.monetary_cost += GenericSearch.unitPriceEnergy;
            // if the delivery is pending
            onDelievery = true;
//    if(isWaiting&&isDelivering){
//        this.energy += this.amountRequestEnergy;
//    }
            counter = 0;
        }
        return state;
    }
    public State Build1(State state) {
        if(!onDelievery) {
            state.monetary_cost += GenericSearch.priceBUILD1;
            state.prosperity += GenericSearch.prosperityBUILD1;

            for (int i = 0; i < GenericSearch.materialsUseBUILD1; i++) {
                state = RequestMaterials(state);
            }
            for (int i = 0; i < GenericSearch.energyUseBUILD1; i++) {
                state = RequestEnergy(state);
            }
            for (int i = 0; i < GenericSearch.foodUseBUILD1; i++) {
                state = RequestFood(state);
            }
            counter += 1;
            if (counter == 2) {
                state.materials += GenericSearch.amountRequestMaterials;
            }
        }
        return state ;
    }
    public State Build2(State state) {
        if(!onDelievery){
            state.monetary_cost += GenericSearch.priceBUILD2;
            state.prosperity += GenericSearch.prosperityBUILD2;

            for (int i = 0; i < GenericSearch.materialsUseBUILD2; i++) {
                state = RequestMaterials(state);
            }
            for (int i = 0; i < GenericSearch.energyUseBUILD2; i++) {
                state = RequestEnergy(state);
            }
            for (int i = 0; i < GenericSearch.foodUseBUILD2; i++) {
                state = RequestFood(state);
            }
            GenericSearch.currDelay -- ;
            if (GenericSearch.currDelay == 0 ) {
                state.materials += GenericSearch.amountRequestMaterials;
            }
        }
        return state;
    }
    public State Wait(State state) {
        GenericSearch.currDelay-- ;
        if(GenericSearch.currDelay== 0 ){
            state.food += GenericSearch.amountRequestFood;
            state.materials += GenericSearch.amountRequestMaterials;
            state.energy += GenericSearch.amountRequestEnergy;
        }
        onDelievery=false;
        return state ;
    }


    public String toString() {
        return "Node{" +
                "State{" +
                "food=" + state.food +
                ", materials=" + state.materials +
                ", energy=" + state.energy +
                ", prosperity=" + state.prosperity +
                ", monetary_cost=" + state.monetary_cost +
                '}'+
//                ", parentNode=" + parentNode +
                ", action=" + action +
                ", depth=" + depth +
                ", pathCost=" + pathCost +
//                ", generatedNodes=" + generatedNodes +
                ", counter=" + counter +
                ", onDelivery=" + onDelievery +
//                ", pathToNode=" + pathToNode +
                '}';
    }

}
