package bicing;

import IA.Bicing.Estacion;

public class Operadores {
	
	public static int num = 1;
	public static Estado intercambiarE(Estado e, int f, Estacion a, Estacion b) {
		Estado ret = new Estado(e);
		e.intercambiarE(a, b);
		//TODO recálculo cosas
		return ret;
	}
	
	public static Estado modificarP1(Estado e, int f, Estacion p) {
		Estado ret = new Estado(e);
		ret.canviarP1(f,p);
		//TODO recálculo cosas
		return ret;
	}
	
	public static Estado modificarP2(Estado e, int f, Estacion p) {
		Estado ret = new Estado(e);
		ret.canviarP2(f,p);
		//TODO recálculo cosas
		return ret;
	}
	
	public static Estado intercambiarP1P2(Estado e, int f) {
		Estado ret = new Estado(e);
		//TODO condición de aplicabilidad
		ret.intercambiarP1P2(f);
		//TODO recálculo cosas
		return ret;
	}
	
	public static Estado incNeNp1(Estado e, int f) {
		Estado ret = new Estado(e);
		//TODO condición de aplicabilidad
		ret.incrementarNNENP1(num, f);
		//TODO recálculo cosas
		return ret;
	}
	
	public static Estado incNeNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		//TODO condición de aplicabilidad
		ret.incrementarNNENP2(num, f);
		//TODO recálculo cosas
		return ret;
	}
	
	public static Estado decNeNp1(Estado e, int f) {
		Estado ret = new Estado(e);
		//TODO condición de aplicabilidad
		ret.decrementarNNENP1(num, f);
		//TODO recálculo cosas
		return ret;
	}
	
	public static Estado decNeNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		//TODO condición de aplicabilidad
		ret.decrementarNNENP2(num, f);
		//TODO recálculo cosas
		return ret;
	}
	
	public static Estado incNp1decNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		//TODO condición de aplicabilidad
		ret.incrementarNNENP1(num, f);
		ret.decrementarNNENP2(num, f);
		//TODO recálculo cosas
		return ret;
	}
	
	public static Estado decNp1incNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		//TODO condición de aplicabilidad
		ret.decrementarNNENP1(num, f);
		ret.incrementarNNENP2(num, f);
		//TODO recálculo cosas
		return ret;
	}
	
	
}
