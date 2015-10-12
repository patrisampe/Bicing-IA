package bicing;

import java.util.ArrayList;
import java.util.List;

import IA.Bicing.Estacion;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class SuccessorsHC implements SuccessorFunction {

	@Override
	public List getSuccessors(Object arg0) {
		// TODO Auto-generated method stub
		Estado e = (Estado) arg0;
		List<Estado> sucesores = new ArrayList<Estado>();
		for (int i = 0; i < e.getvFurgonetas().length; ++i) {
			for (int p= 0; p < e.getvEstaciones().length; ++p) {
				sucesores.add(Operadores.modificarP1(e, i, p));
			}
			for (int p= 0; p < e.getvEstaciones().length; ++p) {
				sucesores.add(Operadores.modificarP2(e, i, p));
			}
			for (int a = 0; a < e.getvEstaciones().length; ++a) {
				for (int b = a+1; b < e.getvEstaciones().length; ++b) {
					sucesores.add(Operadores.intercambiarE(e, i, a, b));
				}
			}
			sucesores.add(Operadores.intercambiarP1P2(e, i));
			sucesores.add(Operadores.decNeNp1(e, i));
			sucesores.add(Operadores.incNeNp1(e, i));
			sucesores.add(Operadores.decNeNp2(e, i));
			sucesores.add(Operadores.incNeNp2(e, i));
			sucesores.add(Operadores.incNp1decNp2(e, i));
			sucesores.add(Operadores.decNp1incNp2(e, i));
		}
		return sucesores;
	}

}
