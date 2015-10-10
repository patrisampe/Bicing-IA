package bicing;

import IA.Bicing.Estacion;

public class DosEst {

	
	private Estacion a;
	private Estacion b;
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DosEst other = (DosEst) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		return true;
	}
	public DosEst(Estacion a, Estacion b) {
		super();
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
