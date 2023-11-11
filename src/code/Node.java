package code;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public State state ;
    public Node parentNode;
    public Action action;
    public int depth = 0 ;
    public int pathCost;
    public int currDelay  = 0 ;
    public Action currActionDelay = null;
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
//    public List<Node> generateChildNodes(){
//        List<Node> children = new ArrayList<>();
//        if(this.state.food==0 || this.state.materials==0 || this.state.energy==0 || this.state.monetary_cost>100000){
//            return null;
//        }
//        if((this.action == Action.REQUEST_FOOD && this.currDelay == 0) || (this.action == Action.REQUEST_ENERGY && this.currDelay == 0) || (this.action == Action.REQUEST_MATERIALS && this.currDelay== 0)){
//            for (Action a : Action.values()){
//                if(a == Action.BUILD1 || a==Action.BUILD2 || a==Action.WAIT){
//                    State newState = new State(this.state.food ,this.state.materials , this.state.energy , this.state.prosperity , this.state.monetary_cost);
//                    newState = applyAction(newState , a);
//                    if (newState!=null) {
//                        if (this.currDelay == 1 || this.currDelay==0) {
//                            newState = updateValues(newState);
//                            Node newNode = new Node(newState, GenericSearch.currNode, a);
//                            newNode.currDelay = 0;
//                            newNode.currActionDelay=null ;
//                            children.add(newNode);
//                        }
//                        else{
//                            Node newNode = new Node(newState, GenericSearch.currNode, a);
//                            newNode.currDelay = this.currDelay <= 0 ? 0 : this.currDelay - 1;
//                            children.add(newNode);
//                        }
//
//                    }
//                }
//            }
//        }
//        else if (this.currDelay ==0){
//            for (Action a : Action.values()){
//                if(!(a == Action.WAIT)){
//                    State newState = new State(this.state.food ,this.state.materials , this.state.energy , this.state.prosperity , this.state.monetary_cost);
//                    newState = applyAction(newState , a);
//                    if (newState!=null) {
//                        Node newNode = new Node(newState, GenericSearch.currNode, a);
//                        if (a == Action.REQUEST_FOOD) {
//                            newNode.currDelay = GenericSearch.delayRequestFood + 1;
//                            newNode.currActionDelay = Action.REQUEST_FOOD;
//                        } else if (a == Action.REQUEST_ENERGY) {
//                            newNode.currDelay = GenericSearch.delayRequestEnergy + 1;
//                            newNode.currActionDelay = Action.REQUEST_ENERGY;
//                        } else if (a == Action.REQUEST_MATERIALS) {
//                            newNode.currDelay = GenericSearch.delayRequestMaterials + 1;
//                            newNode.currActionDelay = Action.REQUEST_MATERIALS;
//                        }
//                        children.add(newNode);
//                    }
//                }
//            }
//        }
//        else {
//            if ( this.currDelay >= 1 ){
//                for (Action a : Action.values()){
//                    if(!(a == Action.REQUEST_FOOD) && !(a==Action.REQUEST_ENERGY) && !(a==Action.REQUEST_MATERIALS)){
//                        State newState = new State(this.state.food ,this.state.materials , this.state.energy , this.state.prosperity , this.state.monetary_cost);
//                        newState = applyAction(newState , a);
//                        if (newState!=null) {
//                            if (this.currDelay == 1) {
//                                newState = updateValues(newState);
//                                Node newNode = new Node(newState, GenericSearch.currNode, a);
//                                newNode.currDelay = 0;
//                                newNode.currActionDelay=null ;
//                                children.add(newNode);
//                            }
//                            else{
//                                Node newNode = new Node(newState, GenericSearch.currNode, a);
//                                newNode.currDelay = this.currDelay <= 0 ? 0 : this.currDelay - 1;
//                                children.add(newNode);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return children;
//    }
public List<Node> generateChildNodes() {
    List<Node> children = new ArrayList<>();
    Object[] d = isDelay();
    boolean isDelay = (boolean) d[1];
    Action delayAction = (Action) d[0];
    if(isDelay){
        for (Action a : Action.values()) {
            if (!(a == Action.REQUEST_FOOD) && !(a == Action.REQUEST_ENERGY) && !(a == Action.REQUEST_MATERIALS)) {
                State newState = new State(this.state.food, this.state.materials, this.state.energy, this.state.prosperity, this.state.monetary_cost);
                newState = applyAction(newState, a);
                if (newState != null) {
                    Node newNode = new Node(newState, this, a);
//                    newNode.currDelay = this.currDelay <= 0 ? 0 : this.currDelay - 1;
                    children.add(newNode);
                }
            }
        }
    }
    else{
        for( Action a : Action.values()){
            if(!(a==Action.WAIT)){
                State newState = new State(this.state.food, this.state.materials, this.state.energy, this.state.prosperity, this.state.monetary_cost);
                newState = applyAction(newState, a);
                if (newState != null) {
                    newState = updateValues(newState,delayAction);
                    Node newNode = new Node(newState, this, a);
                    children.add(newNode);
                }
            }
        }

    }
    return children;
}
private Object[] isDelay(){
        int count = 0;
        Node n = this ;
        boolean flag = false;
        if (n.action == Action.REQUEST_ENERGY || n.action==Action.REQUEST_FOOD || n.action==Action.REQUEST_MATERIALS){
             flag = true;
        }
        else {
            for (int i = 0; i < this.depth; i++) {
                n = n.parentNode;
                if (n == null) break;
                if (n.action == Action.REQUEST_ENERGY || n.action == Action.REQUEST_FOOD || n.action == Action.REQUEST_MATERIALS) {
                    count++;
                    break;
                }
                count++;
            }
        }
        if(n.action == Action.REQUEST_ENERGY){
            flag =  count < GenericSearch.delayRequestEnergy;
            System.out.println("----------------------->Flag Energy " + flag + " count: " + count +" Delay " + GenericSearch.delayRequestEnergy );
        }
        if(n.action == Action.REQUEST_FOOD){
            flag =  count < GenericSearch.delayRequestFood;
            System.out.println("----------------------->Flag Food " + flag + " count: " + count +" Delay " + GenericSearch.delayRequestFood );
        }
        if(n.action == Action.REQUEST_MATERIALS){
            flag =  count < GenericSearch.delayRequestMaterials;
            System.out.println("----------------------->Flag Materials " + flag + " count: " + count +" Delay " + GenericSearch.delayRequestMaterials );
        }

    return new Object[]{n.action,flag};
}
    private int getActionDelay(Action action) {
        // Define the delay for each specific action
        if (action == Action.REQUEST_FOOD) {
            return GenericSearch.delayRequestFood + 1;
        } else if (action == Action.REQUEST_ENERGY) {
            return GenericSearch.delayRequestEnergy + 1;
        } else if (action == Action.REQUEST_MATERIALS) {
            return GenericSearch.delayRequestMaterials + 1;
        } else {
            return 0;  // Default delay for other actions
        }
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
