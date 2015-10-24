package bicing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class SuccessorsSA2 implements SuccessorFunction {

	@Override
	public List getSuccessors(Object arg0) {
		Estado e = (Estado) arg0;
		Random r = new Random(GeneraProblema.getSemilla());
		List<Estado> sucesores = new ArrayList<Estado>();
		int f = r.nextInt(e.getvFurgonetas().length);
		int op = r.nextInt(5);
		if (op == 0) sucesores.add(Operadores.cambiarNp1(e, f, r.nextInt(31)));
		else if (op == 1) sucesores.add(Operadores.cambiarNp2(e, f, r.nextInt(31)));
		else {
			int e2 = r.nextInt(e.getvEstaciones().length);
			if (op == 2) sucesores.add(Operadores.modificarP1(e, f, e2));
			else if (op == 3) sucesores.add(Operadores.modificarP2(e, f, e2));
			else {
				int e3 = GeneraProblema.getEstaciones().indexOf(e.getvFurgonetas()[f].getEstacioE());
				sucesores.add(Operadores.intercambiarE(e, e2, e3));
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
