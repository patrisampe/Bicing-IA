package bicing;

import IA.Bicing.*;

public class Operadores {
	
	
	
	public static int num = 1;

	private static boolean neSePasaria(Estado e, int f) {
		Furgoneta furgo = e.getvFurgonetas()[f];
		int np1 = furgo.getNp1(), np2 = furgo.getNp2();
		int nuevoNe = np1+np2+num;
		return nuevoNe > 30 || nuevoNe > furgo.getEstacioE().getNumBicicletasNoUsadas();
	}
	
	
	
	private static void updateCosteFurgoneta (Estado inicial, Estado sucesor, int f) {
		//System.out.println("ALBERT ");
		double ahorro = costeTransporte(inicial,f);
		//System.out.println("ALBERT 2");
		double gasto = costeTransporte(sucesor,f);
		//System.out.println("ALBERT 3");
		sucesor.setCosteGasolina(sucesor.getCosteGasolina()+gasto-ahorro);
	}
	
	private static int costeTransporte(Estacion p1, Estacion p2, int nb) {
		
		return ((nb + 9)/ 10)*GeneraProblema.distancia(p1, p2);
	}
	
	private static double costeTransporte(Estado e, int f) {
		//System.out.println("lililil");
		Furgoneta furgo = e.getvFurgonetas()[f];
		//System.out.println("lololol");
		if(furgo.getindexEstacioE() == -1)return 0.0;
		
		Estacion E = furgo.getEstacioE();
		int np1 = furgo.getNp1(), np2 = furgo.getNp2();
		double d1= 0.0;
		double d2=0.0;
		if(furgo.getindexEstacioP1() != -1){
			Estacion P1 = furgo.getEstacioP1();
			d1=costeTransporte(E,P1,np1+np2);
			if(furgo.getindexEstacioP2() != -1){
				Estacion P2 = furgo.getEstacioP2(); 
				d2=costeTransporte(P1,P2,np2);
			}
			
		}
		else {
			if(furgo.getindexEstacioP2() != -1){
				Estacion P2 = furgo.getEstacioP2(); 
				d2=costeTransporte(E,P2,np2);
			}
		}
		//System.out.println("HIII");
		
		double coste = d1+d2;
		
		return coste;
	}

	
	public static Estado intercambiarE(Estado e, int a, int b) {
		Estado ret = new Estado(e);
		//System.out.println("podemooos");
		ret.intercambiarE(a, b);
		//System.out.println("");
		int f1 = -1;
		int f2 = -1;
		
		if(a != -1)e.getvEstaciones()[a].getFurg();
		if(b != -1)f2 = e.getvEstaciones()[b].getFurg();
		if (f1 != -1) updateCosteFurgoneta(e,ret,f1);
		if (f2 != -1) updateCosteFurgoneta(e,ret,f2);
		return ret;
	}
	
	public static Estado modificarP1(Estado e, int f, int p) {
		Estado ret = new Estado(e);
		//System.out.println("podemooos");
		ret.canviarP1(f,p);
		//System.out.println("uuusssooo");
		updateCosteFurgoneta(e,ret,f);
		//System.out.println("uuusssddddooo");
		return ret;
	}

	public static Estado modificarP2(Estado e, int f, int p) {
		Estado ret = new Estado(e);
		ret.canviarP2(f,p);
		updateCosteFurgoneta(e,ret,f);
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
		updateCosteFurgoneta(e,ret,f);
		return ret;
	}
	
	public static Estado incNeNp2(Estado e, int f) {
		if (neSePasaria(e,f)) return null;
		Estado ret = new Estado(e);
		ret.incrementarNNENP2(num, f);
		updateCosteFurgoneta(e,ret,f);
		return ret;
	}
	

	public static Estado decNeNp1(Estado e, int f) {
		Estado ret = new Estado(e);
		if (ret.getvFurgonetas()[f].getNp1() < num) return null;
		ret.decrementarNNENP1(num, f);
		updateCosteFurgoneta(e,ret,f);
		return ret;
	}

	public static Estado decNeNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		if (ret.getvFurgonetas()[f].getNp2() < num) return null;
		ret.decrementarNNENP2(num, f);
		updateCosteFurgoneta(e,ret,f);
		return ret;
	}

	public static Estado incNp1decNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		ret.decrementarNNENP2(num, f);
		if (ret.getvFurgonetas()[f].getNp2() < num) return null;
		ret.incrementarNNENP1(num, f);
		updateCosteFurgoneta(e,ret,f);
		return ret;
	}
	
	public static Estado decNp1incNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		ret.decrementarNNENP1(num, f);
		if (ret.getvFurgonetas()[f].getNp1() < 0) return null;
		ret.incrementarNNENP2(num, f);
		updateCosteFurgoneta(e,ret,f);
		return ret;
	}
	public static Estado cambiarNp1(Estado e, int f, int n) {
		Estado ret = new Estado(e);
		Furgoneta furgo = ret.getvFurgonetas()[f];
		if(furgo.getindexEstacioE() == -1)return null;
		int nuevoNe = furgo.getNp2()+n;
		if (nuevoNe > 30 || nuevoNe > furgo.getEstacioE().getNumBicicletasNoUsadas()) return null;
		ret.decrementarNNENP1(furgo.getNp1(), f);
		ret.incrementarNNENP1(n, f);
		updateCosteFurgoneta(e,ret,f);
		return ret;
	}
	
	public static Estado cambiarNp2(Estado e, int f, int n) {
		//System.out.println("holiita soc la patri");
		Estado ret = new Estado(e);
		Furgoneta furgo = ret.getvFurgonetas()[f];
		int nuevoNe = furgo.getNp1()+n;
		if(furgo.getindexEstacioE() == -1)return null;
		if (nuevoNe > 30 || nuevoNe > furgo.getEstacioE().getNumBicicletasNoUsadas()) return null;
		ret.decrementarNNENP2(furgo.getNp2(), f);
		ret.incrementarNNENP2(n, f);
		updateCosteFurgoneta(e,ret,f);
		//System.out.println("soc la patri");
		return ret;
	}

	public static void setNum(int n) {
		num = n;	
	}
	
	
}
