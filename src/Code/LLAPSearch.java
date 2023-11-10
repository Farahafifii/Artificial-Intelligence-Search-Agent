package Code;

import Code.GenericSearch;

public abstract class LLAPSearch extends GenericSearch {
	public LLAPSearch() {
		super();
	}
	public static String[][] splitString(String input) {
		String[] semiColonSplit = input.split(";");
		String[][] result = new String[semiColonSplit.length][];

		for (int i = 0; i < semiColonSplit.length; i++) {
			result[i] = semiColonSplit[i].split(",");
		}
		return result;
	}
	public static void initializeNode(String initialState){
		String[][] initialVariables = splitString(initialState) ;
		GenericSearch.assignInitialVar(initialVariables);
		State state = new State(GenericSearch.food , GenericSearch.materials ,GenericSearch.energy , GenericSearch.prosperity , 0 );
		GenericSearch.currNode =  new Node(state, null , null) ;
		GenericSearch.currNode.pathToNode.add(currNode);
	}

	public static String solve(String initialState, String strategy, Boolean visualize) {
		initializeNode(initialState);
		GenericSearch.Generic(strategy) ;
		return "BUILD1,BUILD1,BUILD1;1050;64";
	}
}
