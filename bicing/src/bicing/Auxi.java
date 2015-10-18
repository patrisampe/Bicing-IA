package bicing;

public class Auxi {
	Integer index;
	Integer sobren;
	public Auxi(Integer index, Integer sobren) {
		super();
		this.index = index;
		this.sobren = sobren;
	}
	
	public Auxi() {
		super();
		this.index = index;
		this.sobren = sobren;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Integer getSobren() {
		return sobren;
	}
	public void setSobren(Integer sobren) {
		this.sobren = sobren;
	}
	
	public int compare(Auxi m1, Auxi m2){

	    return (m1.sobren-m2.sobren);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((sobren == null) ? 0 : sobren.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Auxi other = (Auxi) obj;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (sobren == null) {
			if (other.sobren != null)
				return false;
		} else if (!sobren.equals(other.sobren))
			return false;
		return true;
	}


	
}
