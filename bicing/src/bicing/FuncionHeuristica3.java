package bicing;

import IA.Bicing.Estacion;
import aima.search.framework.HeuristicFunction;

public class FuncionHeuristica3 implements HeuristicFunction {

	public double getHeuristicValue(Object state) {
		Estado actual = (Estado) state;
		double h1 = actual.getBicisBienColocadas() - actual.getBicisMalColocadas();
		double h2 = 0.0, h3 = 0.0;
		for (Furgoneta f : actual.getvFurgonetas()) {
			Estacion e = f.getEstacioE();
			double d1 = Math.min(e.getNumBicicletasNoUsadas(),Math.max(0,e.getNumBicicletasNext()-e.getDemanda()));
			Estacion e1 = f.getEstacioP1();
			Estacion e2 = f.getEstacioP2();
			int m1 = e1.getDemanda() - e1.getNumBicicletasNext();
			if (m1 < 0) m1 = 0;
			int m2 = e2.getDemanda() - e2.getNumBicicletasNext();
			if (m2 < 0) m2 = 0;
			double d2 = m1+m2;
			h2 += (Math.min(d1,d2) - (f.getNp1() + f.getNp2()))/(double)10;
			h3 += (f.getNp1() + f.getNp2())/(double)5;
		
		}
		Double dif= ((h1+h2+h3));
		return -(dif-actual.getCosteGasolina()/1000);
	}
	
	public boolean equals(Object obj) {
        boolean retValue;
        
        retValue = super.equals(obj);
        return retValue;
    }
}