package bicing;

public class Operadores {
	
	public static int num = 1;
	public static Estado intercambiarE(Estado e, int f) {
		Estado ret = new Estado(e);
		//TODO stub 
		return ret;
	}
	
	public static Estado modificarP1(Estado e, int f) {
		Estado ret = new Estado(e);
		//TODO stub 
		return ret;
	}
	
	public static Estado modificarP2(Estado e, int f) {
		Estado ret = new Estado(e);
		//TODO stub 
		return ret;
	}
	
	public static Estado intercambiarP1P2(Estado e, int f) {
		Estado ret = new Estado(e);
		//TODO stub 
		return ret;
	}
	
	public static Estado incNeNp1(Estado e, int f) {
		Estado ret = new Estado(e);
		//TODO stub 
		return ret;
	}
	
	public static Estado incNeNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		//TODO stub 
		return ret;
	}
	
	public static Estado decNeNp1(Estado e, int f) {
		Estado ret = new Estado(e);
		//TODO stub 
		return ret;
	}
	
	public static Estado decNeNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		//TODO stub 
		return ret;
	}
	
	public static Estado incNp1decNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		//TODO stub 
		return ret;
	}
	
	public static Estado decNp1incNp2(Estado e, int f) {
		Estado ret = new Estado(e);
		ret.decrementarNNENP1(num, f);
		ret.incrementarNNENP2(num, f);
		return ret;
	}
	
	
}
