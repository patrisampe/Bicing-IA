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
		Random r = new Random();
		List<Estado> sucesores = new ArrayList<Estado>();
		int f = r.nextInt(e.getvFurgonetas().length);
		int op = r.nextInt(10);
		int nums[] = {1,2,7};
		if (op < 6) Operadores.setNum(nums[r.nextInt(3)]);
		if (op == 0) sucesores.add(Operadores.decNeNp1(e, f));
		else if (op == 1) sucesores.add(Operadores.incNeNp1(e, f));
		else if (op == 2) sucesores.add(Operadores.decNeNp2(e, f));
		else if (op == 3) sucesores.add(Operadores.incNeNp2(e, f));
		else if (op == 4) sucesores.add(Operadores.incNp1decNp2(e, f));
		else if (op == 5) sucesores.add(Operadores.decNp1incNp2(e, f));
		else if (op == 6) sucesores.add(Operadores.intercambiarP1P2(e, f));
		else {
			int e2 = r.nextInt(e.getvEstaciones().length);
			if (op == 7) sucesores.add(Operadores.modificarP1(e, f, e2));
			else if (op == 8) sucesores.add(Operadores.modificarP2(e, f, e2));
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
