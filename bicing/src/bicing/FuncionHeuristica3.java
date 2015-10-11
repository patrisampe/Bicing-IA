package bicing;

import aima.search.framework.HeuristicFunction;

public class FuncionHeuristica3 implements HeuristicFunction {

	public double getHeuristicValue(Object state) {
		estado actual = (Estado) state;
		double dif = actual.getBicisBenColocades() - actual.getBiciMalColocades();
		return (dif/actual.getCostGasolina());
	}
	
	public boolean equals(Object obj) {
        boolean retValue;
        
        retValue = super.equals(obj);
        return retValue;
    }
}