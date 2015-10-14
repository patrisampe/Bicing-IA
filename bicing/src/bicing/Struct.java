package bicing;

public class Struct {

	private Integer furg;
	private Integer bicisBenColocadas;
	private Integer bicisMalColocadas;
	public Struct(Integer furg, Integer bicisBenColocadas, Integer bicisMalColocadas) {
		super();
		this.furg = furg;
		this.bicisBenColocadas = bicisBenColocadas;
		this.bicisMalColocadas = bicisMalColocadas;
	}
	public Struct(Struct S1){
		furg=S1.furg;
		bicisBenColocadas=S1.bicisBenColocadas;
		bicisMalColocadas=S1.bicisMalColocadas;
	}
	
	public Integer getFurg() {
		return furg;
	}
	public void setFurg(Integer furg) {
		this.furg = furg;
	}
	public Integer getBicisBenColocadas() {
		return bicisBenColocadas;
	}
	public void setBicisBenColocadas(Integer bicisBenColocadas) {
		this.bicisBenColocadas = bicisBenColocadas;
	}
	public Integer getBicisMalColocadas() {
		return bicisMalColocadas;
	}
	public void setBicisMalColocadas(Integer bicisMalColocadas) {
		this.bicisMalColocadas = bicisMalColocadas;
	}
	
	@Override
	public Object clone(Object obj) {
		
		
		
	}
}

