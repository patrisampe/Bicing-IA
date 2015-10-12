package bicing;

import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class SuccessorsHC implements SuccessorFunction {

	@Override
	public List getSuccessors(Object arg0) {
		// TODO Auto-generated method stub
		Estado e = (Estado) arg0;
		List<Estado> sucesores = new List<Estado>();
		for (int i = 0; i < e.getvFurgonetas().length; ++i) {
			//TODO falta mucho
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
