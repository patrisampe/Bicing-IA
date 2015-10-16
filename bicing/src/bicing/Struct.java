package bicing;

public class Struct {



	private Integer furg;
	private Integer bicisColocades;
	private Integer bicisAgafen;
	public Struct(Integer furg, Integer bicisColocades,Integer bicisAgafen) {
		super();
		this.furg = furg;
		this.bicisColocades = bicisColocades;
		this.bicisAgafen=bicisAgafen;
	}
	
	public Struct(){
		furg=-1;
		bicisColocades=0;
		bicisAgafen=0;
		
	}
	
	public Struct(Struct S){
		furg=S.furg;
		bicisColocades=S.bicisColocades;
		bicisAgafen=S.bicisAgafen;
	}
	
	public Integer getBicisAgafen() {
		return bicisAgafen;
	}

	public void setBicisAgafen(Integer bicisAgafen) {
		this.bicisAgafen = bicisAgafen;
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

