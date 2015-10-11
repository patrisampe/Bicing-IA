package bicing;

import aima.search.framework.HeuristicFunction;

public class FuncionHeuristica2 implements HeuristicFunction {

	public double getHeuristicValue(Object state) {
		estado actual = (Estado) state;
		double dif = actual.getBicisBenColocades() - actual.getBiciMalColocades() - actual.getCostGasolina();
		return (dif);
	}
	
	public boolean equals(Object obj) {
        boolean retValue;
        
        retValue = super.equals(obj);
        return retValue;
    }
}