package code;

import java.util.*;

public abstract class GenericSearch {
    public static Node currNode; //initial State
    public static PriorityQueue<Node> childNodes; //State Space
    public static int unitPriceFood, unitPriceMaterials, unitPriceEnergy , amountRequestFood, delayRequestFood , amountRequestMaterials, delayRequestMaterials, amountRequestEnergy, delayRequestEnergy;
    public static int priceBUILD1, foodUseBUILD1,  materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1;
    public static int priceBUILD2, foodUseBUILD2,materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2, food,prosperity,materials,energy;
    public static int minBuild ;
    public static int maxProperityBuild ;
    public static int minFood ;

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
        minBuild= Math.min(priceBUILD1, priceBUILD2);
        maxProperityBuild= Math.max(prosperityBUILD1, prosperityBUILD2);
        minFood= Math.min(foodUseBUILD1, foodUseBUILD2);
    }
    public static int generateKey(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null.");
        }

        return input.hashCode();
    }
    public static Object[] Search(PriorityQueue<Node> QingFunc ,boolean visualize) {
        QingFunc.add(currNode);
        Set<Integer> explored = new HashSet<>();
        while (!QingFunc.isEmpty()) {
            currNode= QingFunc.poll();
            if (currNode.state.prosperity == 100) {
                System.out.println("Number of Explored Nodes: "+ explored.size());
                return new Object[]{currNode,explored.size()};
            }
            if (explored.contains(currNode.toString2().hashCode())) {
                continue;
            }
            explored.add(currNode.toString2().hashCode());
            childNodes = currNode.generateChildNodes();
            if(visualize){
                visualize(currNode,null);
            }
            for (Node childNode : childNodes) {
                if(visualize)
                    visualize(null,childNode);
                if (childNode != null && !explored.contains(childNode.toString2().hashCode())) {
                    QingFunc.add(childNode);
                }
            }
            childNodes = null;
        }

        return new Object[]{null,explored.size()};
    }
    public static Object[] ids(boolean visualize) {
        int depthLimit = 0;
        Node initialNode = currNode;
        int explored =  0;
        while (true) {
            Object[] result = depthLimitedSearch(initialNode, depthLimit, visualize , explored);
            if (result[0] != null) {
                return result;
            }
            explored += (int) result[1];
            depthLimit++;
        }
    }

    private static Object[] depthLimitedSearch(Node initialNode, int depthLimit, boolean visualize, int exp) {
        Set<Integer> explored = new HashSet<>();
        PriorityQueue<Node> frontier =new PriorityQueue<>(Comparator.comparingInt(node -> -node.depth));
        frontier.add(initialNode);

        while (!frontier.isEmpty()) {
            currNode = frontier.poll();
            if (currNode.state.prosperity == 100) {
                System.out.println("Number of Explored Nodes: "+ (explored.size()+exp));
                return new Object[]{currNode, (explored.size()+exp)};
            }
            if (!explored.contains(currNode.toString2().hashCode()) && currNode.depth <= depthLimit) {
                explored.add(currNode.toString2().hashCode());
                PriorityQueue<Node> childNodes = currNode.generateChildNodes();
                if (visualize) {
                    visualize(currNode, null);
                }
                for (Node childNode : childNodes) {
                    if (visualize) {
                        visualize(null, childNode);
                    }
                    if (childNode != null && !explored.contains(childNode.toString2().hashCode())) {
                        childNode.depth = currNode.depth + 1;
                        frontier.add(childNode);
                    }
                }
            }
        }
        return new Object[]{null, explored.size()};
    }
    public static String Generic(String QingFunc,boolean visualize){
        PriorityQueue<Node> Qing = null;
        if ( QingFunc.equals("BF")){
            Qing = new PriorityQueue<>(Comparator.comparingInt(node -> node.depth));
        }
        else if ( QingFunc.equals("DF")){
            Qing = new PriorityQueue<>(Comparator.comparingInt(node -> -node.depth));
        }
        else if ( QingFunc.equals("ID")){
            Object[] result = ids(visualize);
            if(visualize) {
                Node n = (Node) result[0];
                System.out.println("--->Depth: " + n.depth + n);
                Node z = n;
                for (int i = 0; i < n.depth; i++) {
                    z = z.parentNode;
                    if (z == null) break;
                    System.out.println("--->Depth: " + z.depth + z);
                }
            }
            return GenerateSolution(result);
        }
        else if ( QingFunc.equals("UC")){
            Qing = new PriorityQueue<>(Comparator.comparingInt(node -> currNode.state.monetary_cost));
        }
        else if ( QingFunc.equals("GR1")){
            Qing = new PriorityQueue<>(Comparator.comparingInt(node -> currNode.state.heuristic1));
        }
        else if ( QingFunc.equals("GR2")){
            Qing = new PriorityQueue<>(Comparator.comparingInt(node -> currNode.state.heuristic2));
        }
        else if ( QingFunc.equals("AS1")){
            Qing= new PriorityQueue<>(Comparator.comparingInt(node -> currNode.state.admissible_heuristic1));
        }
        else if ( QingFunc.equals("AS2")){
            Qing= new PriorityQueue<>(Comparator.comparingInt(node -> currNode.state.admissible_heuristic2));
        }
        else return "You didn't enter a valid search algorithm";

        Object[] result = Search(Qing,visualize);
        if(visualize) {
            Node n = (Node) result[0];
            System.out.println("--->Depth: " + n.depth + n);
            Node z = n;
            for (int i = 0; i < n.depth; i++) {
                z = z.parentNode;
                if (z == null) break;
                System.out.println("--->Depth: " + z.depth + z);
            }
        }
        return GenerateSolution(result);
    }
    public static String GenerateSolution(Object[] res ){
        StringBuilder sb = new StringBuilder();
        Node n = (Node) res[0];
        int exp = (int) res[1];
        if(n==null){
            sb.append("NOSOLUTION");
        }
        else {
            String[] rs = new String[n.depth];
            Node z = n;
            for (int i = 0 ; i<n.depth ; i++) {
                z = z.parentNode;
                if (z == null) break;
                String a = z.action==Action.REQUEST_ENERGY? "RequestEnergy" :z.action==Action.REQUEST_MATERIALS?"RequestMaterials":z.action==Action.REQUEST_FOOD?"RequestFood": String.valueOf(z.action);
                rs[i] = a;//sb.append(z.action).append(",");
            }
            for(int i  = rs.length-2 ; i >= 0 ; i -- ){
                sb.append(rs[i] + ",");
            }
            sb.append(n.action).append(";").append(n.state.monetary_cost).append(";").append(exp);
        }
        return sb.toString();
    }
    public static void visualize(Node currNode, Node childNode){
        if(currNode!=null){
            System.out.println("Current: " + currNode.state + " " + currNode.action + " " + currNode.depth +" " + (currNode.parentNode==null? "":
                    " Parent: " + currNode.parentNode.state + " "
                            + currNode.parentNode.action + " " + currNode.parentNode.depth)
                    +(currNode.parentNode==null? "": currNode.parentNode.parentNode==null?"":
                    " GParent: " + currNode.parentNode.parentNode.action + " " + currNode.parentNode.parentNode.depth));

        }
        else if(childNode!=null){
            System.out.println("CHILDREN: " + childNode.state + " " + childNode.action + " " + childNode.depth );
        }
    }
}
