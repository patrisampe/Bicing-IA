package bicing;

import IA.Bicing.*;

public class Operadores {
	
	
	
	public static int num = 1;
	
	private static boolean neSePasa(Estado e, int f) {
		Furgoneta furgo = e.getvFurgonetas()[f];
		int np1 = furgo.getNp1(), np2 = furgo.getNp2();
		return np1+np2 > furgo.getEstacioE().getNumBicicletasNoUsadas();
	}
	
	private static void updateCosteFurgoneta (Estado inicial, Estado sucesor, int f) {
		double ahorro = costeTransporte(inicial,f);
		double gasto = costeTransporte(sucesor,f);
		sucesor.setCosteGasolina(inicial.getCosteGasolina()+gasto-ahorro);
	}
	
	private static double costeTransporte(Estacion p1, Estacion p2, int nb) {
		return ((nb + 9)/ 10)*GeneraProblema.distancia(p1, p2);
	}
	
	private static double costeTransporte(Estado e, int f) {
		Furgoneta furgo = e.getvFurgonetas()[f];
		Estacion E = furgo.getEstacioE(), P1 = furgo.getEstacioP1(), P2 = furgo.getEstacioP2(); 
		int np1 = furgo.getNp1(), np2 = furgo.getNp2();
		double coste = costeTransporte(E,P1,np1+np2) + costeTransporte(P1,P2,np2);
		return coste;
	}

	
	public static Estado intercambiarE(Estado e, int a, int b) {
		Estado ret = new Estado(e);
		e.intercambiarE(a, b);
		//TODO recálculo cosas
		return ret;
	}
	
	public static Estado modificarP1(Estado e, int f, int p) {
		Estado ret = new Estado(e);
		ret.canviarP1(f,p);
		//TODO recálculo cosas
		return ret;
	}
	
	public static Estado modificarP2(Estado e, int f, int p) {
		Estado ret = new Estado(e);
		ret.canviarP2(f,p);
		//TODO recálculo cosas
		return ret;
	}
	
	public static Estado intercambiarP1P2(Estado e, int f) {
		Estado ret = new Estado(e);
		ret.intercambiarP1P2(f);
		//TODO, si se no se intercambia también el número hay que recalcular las bicis bien colocadas
		updateCosteFurgoneta(e,ret,f);
		return ret;
	}
	
	
	public static Estado incNeNp1(Estado e, int f) {
		Estado ret = new Estado(e);
		ret.incrementarNNENP1(num, f);
		if (neSePasa(ret,f)) return null;
		//TODO recálculo cosas
		updateCosteFurgoneta(e,ret,f);
		return ret;
	}
	
	public static Estado incNeNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		ret.incrementarNNENP2(num, f);
		if (neSePasa(ret,f)) return null;
		//TODO recálculo cosas
		updateCosteFurgoneta(e,ret,f);
		return ret;
	}
	

	public static Estado decNeNp1(Estado e, int f) {
		Estado ret = new Estado(e);
		ret.decrementarNNENP1(num, f);
		if (ret.getvFurgonetas()[f].getNp1() < 0) return null;
		//TODO recálculo cosas
		updateCosteFurgoneta(e,ret,f);
		return ret;
	}

	public static Estado decNeNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		ret.decrementarNNENP2(num, f);
		if (ret.getvFurgonetas()[f].getNp2() < 0) return null;
		//TODO recálculo cosas
		return ret;
	}
	
	public static Estado incNp1decNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		ret.decrementarNNENP2(num, f);
		if (ret.getvFurgonetas()[f].getNp2() < 0) return null;
		ret.incrementarNNENP1(num, f);
		//TODO recálculo cosas
		return ret;
	}
	
	public static Estado decNp1incNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		ret.decrementarNNENP1(num, f);
		if (ret.getvFurgonetas()[f].getNp1() < 0) return null;
		ret.incrementarNNENP2(num, f);
		//TODO recálculo cosas
		return ret;
	}
	
	
}
