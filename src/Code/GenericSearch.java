package Code;

import java.util.*;

public abstract class GenericSearch {
    public static Node currNode; //initial State
    public static List<Node> childNodes; //State Space

    public static int unitPriceFood, unitPriceMaterials, unitPriceEnergy;
    public static int amountRequestFood, delayRequestFood;
    public static int amountRequestMaterials, delayRequestMaterials;
    public static int amountRequestEnergy, delayRequestEnergy;
    public static int priceBUILD1, foodUseBUILD1;
    public static int materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1;
    public static int priceBUILD2, foodUseBUILD2;
    public static int materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2;

    public static int food ;
    public static int materials ;
    public static int energy;
    public static int monetary_cost = 0 ;
    public static int prosperity ;
    public boolean isWaiting = false ;
    public boolean isDelivering = false ;

    public static int currDelay ;



    public static void assignInitialVar(String[][] state){

        prosperity = Integer.parseInt(state[0][0]) ;
        food = Integer.parseInt(state[1][0]);
        materials=Integer.parseInt(state[1][1]);
        energy =Integer.parseInt(state[1][2]);
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

    public static Node bfs() {
        Queue<Node> frontier = new LinkedList<>();
        frontier.add(currNode);
        Set<Node> explored = new HashSet<>();
        while (!frontier.isEmpty()) {
            System.out.println(frontier);
            Node node = frontier.remove();
            currNode = node ;
            State state = node.state;
            if (state.prosperity >=100) {
                return node;
            }
            if (!explored.contains(node)) {
                explored.add(node);
                if(currNode.action == Action.REQUEST_ENERGY){
                    currDelay = delayRequestEnergy;
                } else if (currNode.action == Action.REQUEST_FOOD) {
                    currDelay=delayRequestFood;
                } else if (currNode.action==Action.REQUEST_MATERIALS) {
                    currDelay = delayRequestMaterials;
                }
                childNodes = currNode.generateChildNodes();
                frontier.addAll(childNodes);
//                for (Action<T> action : problem.actions(state)) {
//                    T next_state = problem.result(state, action);
//                    frontier.add(new Node<>(next_state, node, action, node.path_cost + problem.path_cost(node.path_cost, state, action, next_state)));
//                }
            }
        }
        return null;
    }
    public static Boolean GoalTest(State state){
        return state.prosperity == 100;
    }
    public static void Generic(String search){
        if ( search == "BF"){
            Node r = bfs();
            System.out.println("HEEEERRREEEE " + r);
            System.out.println("PAAATTTHHH:  " + r.pathToNode);
        }

    }
//    public static Node[] search(Node root, String strategy){
//        return new Node[2];
//    }
}
