package bicing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class SuccessorsSA implements SuccessorFunction {

	@Override
	public List getSuccessors(Object arg0) {
		Estado e = (Estado) arg0;
		Random r = GeneraProblema.getRandom();
		List<Estado> sucesores = new ArrayList<Estado>();
		int f = r.nextInt(e.getvFurgonetas().length);
		int nums[] = {1,2,7};
		int op = r.nextInt(62+e.getvEstaciones().length*3);
		if (op <= 30) sucesores.add(Operadores.cambiarNp1(e, f, op));
		else {
			op -= 31;
			if (op <= 30) sucesores.add(Operadores.cambiarNp2(e, f, op));
			else {
				op -= 31;
				if (op < e.getvEstaciones().length) sucesores.add(Operadores.modificarP1(e, f, op));
				else {
					op -= e.getvEstaciones().length;
					if (op < e.getvEstaciones().length) sucesores.add(Operadores.modificarP2(e, f, op));
					else {
						op -= e.getvEstaciones().length;
						int b = GeneraProblema.getEstaciones().indexOf(e.getvFurgonetas()[f].getEstacioE());
						sucesores.add(Operadores.intercambiarE(e, op, b));
					}
				}
			}
		}
		if (sucesores.get(0) == null) return getSuccessors(e); 
		List ret = new ArrayList();
		for (Estado cosa : sucesores) {
			ret.add(new Successor("",cosa));
		}
		return ret;
	}

}
