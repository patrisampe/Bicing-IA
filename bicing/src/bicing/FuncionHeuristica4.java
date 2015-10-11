package bicing;

import aima.search.framework.HeuristicFunction;

public class FuncionHeuristica4 implements HeuristicFunction {

	public double getHeuristicValue(Object state) {
		Estado actual = (Estado) state;
		return (actual.getHeuristico4());
	}
	
	public boolean equals(Object obj) {
        boolean retValue;
        
        retValue = super.equals(obj);
        return retValue;
    }
}