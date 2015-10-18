package bicing;

import aima.search.framework.HeuristicFunction;

public class FuncionHeuristica2 implements HeuristicFunction {
	/*
	public double getHeuristicValue(Object state) {
		Estado actual = (Estado) state;
		double dif = actual.getBicisBienColocadas() - actual.getBicisMalColocadas() - actual.getCosteGasolina();
		return (-dif);
	}
	*/
	
	public double getHeuristicValue(Object state) {
		Estado actual = (Estado) state;
		double dif = actual.getBicisBienColocadas() - actual.getBicisMalColocadas();
		return (-dif);
	}
	
	public boolean equals(Object obj) {
        boolean retValue;
        
        retValue = super.equals(obj);
        return retValue;
    }
}