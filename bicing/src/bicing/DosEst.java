package bicing;

import IA.Bicing.Estacion;

public class DosEst {

	
	private Estacion a;
	private Estacion b;
	 @Override
    public int hashCode() {
		 return a.hashCode()*b.hashCode();
    }
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DosEst other = (DosEst) obj;
		return a == other.a && b == other.b;
	}
	public DosEst(Estacion a, Estacion b) {
		this.a = a;
		this.b = b;
	}
	public Estacion getA() {
		return a;
	}
	public void setA(Estacion a) {
		this.a = a;
	}
	public Estacion getB() {
		return b;
	}
	public void setB(Estacion b) {
		this.b = b;
	}
}
