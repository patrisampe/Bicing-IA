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
		Random r = new Random();
		List<Estado> sucesores = new ArrayList<Estado>();
		int f = r.nextInt(e.getvFurgonetas().length);
		int op = r.nextInt(7+e.getvEstaciones().length*2+(e.getvEstaciones().length)*(e.getvEstaciones().length-1)/2);
		if (op == 0) sucesores.add(Operadores.intercambiarP1P2(e, f));
		if (op == 1) sucesores.add(Operadores.decNeNp1(e, f));
		if (op == 2) sucesores.add(Operadores.incNeNp1(e, f));
		if (op == 3) sucesores.add(Operadores.decNeNp2(e, f));
		if (op == 4) sucesores.add(Operadores.incNeNp2(e, f));
		if (op == 5) sucesores.add(Operadores.incNp1decNp2(e, f));
		if (op == 6) sucesores.add(Operadores.decNp1incNp2(e, f));
		if (op >= 7) {
			op -= 7;
			if (op < e.getvEstaciones().length) sucesores.add(Operadores.modificarP1(e, f, op));
			else {
				op -= e.getvEstaciones().length;
				if (op < e.getvEstaciones().length) sucesores.add(Operadores.modificarP2(e, f, op));
				else {
					op -= e.getvEstaciones().length;
					int a = 0;
					for (int i = e.getvEstaciones().length-1; i < op; --i) { //seguro que se puede hacer mejor
						op -= i;
						++a;
					}
					int b = op;
					sucesores.add(Operadores.intercambiarE(e, f, a, b));
				}
			}
		}
		if (sucesores.get(0) == null) return getSuccessors(e); 
		return sucesores;
	}

}
