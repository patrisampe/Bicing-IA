package bicing;

import aima.search.framework.HeuristicFunction;

public class FuncionHeuristica4 implements HeuristicFunction {

	public double getHeuristicValue(Object state) {
		estado actual = (Estado) state;
		return (actual.getHeuristic4());
	}
	
	public boolean equals(Object obj) {
        boolean retValue;
        
        retValue = super.equals(obj);
        return retValue;
    }
}