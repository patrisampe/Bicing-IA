package bicing;

import IA.Bicing.*;

public class Operadores {
	
	
	
	public static int num = 1;
	

	private static void updateBicisBienFurgoneta(Estado ini, Estado fin, int f) {
		// TODO Auto-generated method stub
		
	}
	
	private static void updateBicisMalFurgoneta(Estado e, Estado ret, int f) {
		// TODO Auto-generated method stub
		
	}
	
	private static void updateCompletoFurgoneta(Estado ini, Estado fi, int f) {
		updateBicisBienFurgoneta(ini,fi,f);
		updateBicisMalFurgoneta(ini,fi,f);
		updateCosteFurgoneta(ini,fi,f);
	}
	
	private static boolean neSePasaria(Estado e, int f) {
		Furgoneta furgo = e.getvFurgonetas()[f];
		int np1 = furgo.getNp1(), np2 = furgo.getNp2();
		return np1+np2+num > furgo.getEstacioE().getNumBicicletasNoUsadas();
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
		int f1 = e.getvEstaciones()[a].getFurg(), f2 = e.getvEstaciones()[b].getFurg();
		if (f1 != -1) updateCompletoFurgoneta(e,ret,f1);
		if (f2 != -1) updateCompletoFurgoneta(e,ret,f2);
		return ret;
	}
	
	public static Estado modificarP1(Estado e, int f, int p) {
		Estado ret = new Estado(e);
		ret.canviarP1(f,p);
		updateCompletoFurgoneta(e,ret,f);
		return ret;
	}

	public static Estado modificarP2(Estado e, int f, int p) {
		Estado ret = new Estado(e);
		ret.canviarP2(f,p);
		updateCompletoFurgoneta(e,ret,f);
		return ret;
	}
	
	public static Estado intercambiarP1P2(Estado e, int f) {
		Estado ret = new Estado(e);
		ret.intercambiarP1P2(f);
		updateCosteFurgoneta(e,ret,f);
		return ret;
	}
	
	
	public static Estado incNeNp1(Estado e, int f) {
		if (neSePasaria(e,f)) return null;
		Estado ret = new Estado(e);
		ret.incrementarNNENP1(num, f);
		updateCompletoFurgoneta(e,ret,f);
		return ret;
	}
	
	public static Estado incNeNp2(Estado e, int f) {
		if (neSePasaria(e,f)) return null;
		Estado ret = new Estado(e);
		ret.incrementarNNENP2(num, f);
		updateCompletoFurgoneta(e,ret,f);
		return ret;
	}
	

	public static Estado decNeNp1(Estado e, int f) {
		Estado ret = new Estado(e);
		if (ret.getvFurgonetas()[f].getNp1() < 1) return null;
		ret.decrementarNNENP1(num, f);
		updateCompletoFurgoneta(e,ret,f);
		return ret;
	}

	public static Estado decNeNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		if (ret.getvFurgonetas()[f].getNp2() < 1) return null;
		ret.decrementarNNENP2(num, f);
		updateCompletoFurgoneta(e,ret,f);
		return ret;
	}

	public static Estado incNp1decNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		ret.decrementarNNENP2(num, f);
		if (ret.getvFurgonetas()[f].getNp2() < 0) return null;
		ret.incrementarNNENP1(num, f);
		updateBicisBienFurgoneta(e,ret,f);
		updateCosteFurgoneta(e,ret,f);
		return ret;
	}
	
	public static Estado decNp1incNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		ret.decrementarNNENP1(num, f);
		if (ret.getvFurgonetas()[f].getNp1() < 0) return null;
		ret.incrementarNNENP2(num, f);
		updateBicisBienFurgoneta(e,ret,f);
		updateCosteFurgoneta(e,ret,f);
		return ret;
	}
	
	
}
