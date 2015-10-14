package bicing;

public class Struct {



	private Integer furg;
	private Integer bicisColocades;
	
	public Struct(Integer furg, Integer bicisColocades) {
		super();
		this.furg = furg;
		this.bicisColocades = bicisColocades;
	}
	
	public Struct(Struct S){
		furg=S.furg;
		bicisColocades=S.bicisColocades;
	}
	
	public Integer getFurg() {
		return furg;
	}
	public void setFurg(Integer furg) {
		this.furg = furg;
	}
	public Integer getBicisColocades() {
		return bicisColocades;
	}
	public void setBicisColocades(Integer bicisColocades) {
		this.bicisColocades = bicisColocades;
	}
	
	public void sumaNBicis(Integer n){
		bicisColocades = bicisColocades+n;
	}
	public void restaNBicis(Integer n){
		bicisColocades = bicisColocades-n;
	}

}

