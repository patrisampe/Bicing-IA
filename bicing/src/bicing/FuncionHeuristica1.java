package bicing;

import aima.search.framework.HeuristicFunction;
import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;

/**
 * 
 * Clase que define el heuristico 1
 * 
 * @author Octavi
 *
 */
public class FuncionHeuristica1 implements HeuristicFunction {

	public double getHeuristicValue(Object state) {
		Estado actual = (Estado) state;
		
		double no_usadas = 0.0;
		double sum = 0.0;
		
		Estaciones estaciones = GeneraProblema.getEstaciones();
		Struct[] vEstaciones = actual.getvEstaciones();
		for (int i = 0; i < vEstaciones.length; ++i) {
			Struct est = vEstaciones[i];
			int delta = est.getBicisColocades() - est.getBicisAgafen();
			Estacion Est = estaciones.get(i);
			double aux = Est.getDemanda() - delta - Est.getNumBicicletasNext();
			sum += aux*aux;
			no_usadas += Est.getNumBicicletasNoUsadas();
		}
		return (sum - no_usadas);
	}
	
	public boolean equals(Object obj) {
        boolean retValue;
        retValue = super.equals(obj);
        return retValue;
    }
}