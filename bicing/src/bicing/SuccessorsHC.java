package bicing;

import java.util.ArrayList;
import java.util.List;

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
			
			int b = GeneraProblema.getEstaciones().indexOf(e.getvFurgonetas()[i].getEstacioE());
			for (int p= 0; p < e.getvEstaciones().length; ++p) {
				sucesores.add(Operadores.intercambiarE(e, p, b));
			}
			
			//sucesores.add(Operadores.intercambiarP1P2(e, i));
			/*int nums[] = {1,2,5,7,13};
			for (int index = 0; index < nums.length; ++index) {
				Operadores.setNum(nums[index]);
				sucesores.add(Operadores.decNeNp1(e, i));
				sucesores.add(Operadores.incNeNp1(e, i));
				sucesores.add(Operadores.decNeNp2(e, i));
				sucesores.add(Operadores.incNeNp2(e, i));
				sucesores.add(Operadores.incNp1decNp2(e, i));
				sucesores.add(Operadores.decNp1incNp2(e, i));
			}*/
			for (int n = 1; n <= 30; ++n) {
				sucesores.add(Operadores.cambiarNp1(e, i, n));
				sucesores.add(Operadores.cambiarNp2(e, i, n));
			}
			
		}
		GeneraProblema.incRealRamification(sucesores.size());
		for (int i = 0; i < sucesores.size(); ++i) {
			if (sucesores.get(i) == null) {
				sucesores.remove(i);
				--i;
			}
		}
		GeneraProblema.incRamification(sucesores.size());
		List ret = new ArrayList();
		for (Estado cosa : sucesores) {
			/**
			System.out.println("contamos ");
			System.out.println(cosa.getBicisBienColocadas());
			System.out.println(cosa.getBicisMalColocadas());
			System.out.println("fin ");
			*/
			ret.add(new Successor("",cosa));
		}
		return ret;
	}

}
