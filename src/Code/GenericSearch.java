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
    public static Action currActionDelay;

    public static int currDelay = 0 ;


    public static void updateDelay(){
        if(currDelay > 0)
            currDelay -- ;
        if (currDelay<=0) {
            currDelay = 0;
            currActionDelay = null;
        }
    }

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
            Node node = frontier.poll();
            currNode = node ;
            State state = currNode.state;
            System.out.println("Current: " + currNode.state + " " + currNode.action + " " + currNode.depth +" "+  currNode.currDelay +" " + (currNode.parentNode==null? "":
                    " Parent: " + currNode.parentNode.state + " "
                            + currNode.parentNode.action + " " + currNode.parentNode.depth + " " +
                            currNode.currDelay)
                    +(currNode.parentNode==null? "": currNode.parentNode.parentNode==null?"":
                    " GParent: " + currNode.parentNode.parentNode.action + " " + currNode.parentNode.parentNode.depth + " " +
                            currNode.parentNode.parentNode.currDelay));
            if (state.prosperity >=100) {
                System.out.println("Number of Explored Nodes: "+ explored.size());
                return node;
            }
            if (!explored.contains(node)) {
                explored.add(node);
                childNodes = currNode.generateChildNodes();
                for (Node childNode : childNodes) {
                    System.out.println("CHILDREN: " + childNode.state + " " + childNode.action + " " + childNode.depth );
                    if (childNode != null && !explored.contains(childNode)) {
                        if(currNode.action==Action.REQUEST_FOOD || currNode.action==Action.REQUEST_ENERGY || currNode.action==Action.REQUEST_MATERIALS)
                            childNode.currActionDelay=currNode.action;
                        frontier.add(childNode);
                    }
                }
            }
        }
        System.out.println("siize: "+ explored.size());
        return null;
    }

    public static void Generic(String search){
        if ( search == "BF"){
            Node r = bfs();
            if(r==null)
                System.out.println("NOSOLUTION");
           else {
                Node z = r ;
                System.out.println("Depth " + r.depth + r);
                for(int i  = 0 ; i< r.depth ; i -- ){
                   z = z.parentNode ;
                   if(z==null) break;
                   System.out.println("Depth " + z.depth + z);
               }

//                System.out.println("PAAATTTHHH:  " + r.pathToNode);
            }

        }
//        return "RequestFood,RequestEnergy;20;20";

    }
    public boolean GoalTest(Node n ){
        if(n.state.prosperity>=100){
            return true;
        }
        return false ;
    }

}
