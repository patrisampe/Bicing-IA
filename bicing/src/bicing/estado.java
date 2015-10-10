package bicing;

import IA.Bicing.Estaciones;

import IA.Bicing.Estacion;

public class estado {


	private Furgoneta[] vfugorneta;
	private Integer[] vEstaciones;
	private Integer bicisBenColocades;
	private Integer biciMalColocades;
	private Integer costGasolina;
	private Double heuristic4;
	
	public estado(Furgoneta[] vfugorneta, Integer[] vEstaciones, Integer bicisBenColocades, Integer biciMalColocades,
			Integer costGasolina, Double heuristic4) {
		this.vfugorneta = vfugorneta;
		this.vEstaciones = vEstaciones;
		this.bicisBenColocades = bicisBenColocades;
		this.biciMalColocades = biciMalColocades;
		this.costGasolina = costGasolina;
		this.heuristic4 = heuristic4;
	}
	public Furgoneta[] getVfugorneta() {
		return vfugorneta;
	}
	public void setVfugorneta(Furgoneta[] vfugorneta) {
		this.vfugorneta = vfugorneta;
	}
	public Integer[] getvEstaciones() {
		return vEstaciones;
	}
	public void setvEstaciones(Integer[] vEstaciones) {
		this.vEstaciones = vEstaciones;
	}
	public Integer getBicisBenColocades() {
		return bicisBenColocades;
	}
	public void setBicisBenColocades(Integer bicisBenColocades) {
		this.bicisBenColocades = bicisBenColocades;
	}
	public Integer getBiciMalColocades() {
		return biciMalColocades;
	}
	public void setBiciMalColocades(Integer biciMalColocades) {
		this.biciMalColocades = biciMalColocades;
	}
	public Integer getCostGasolina() {
		return costGasolina;
	}
	public void setCostGasolina(Integer costGasolina) {
		this.costGasolina = costGasolina;
	}
	public Double getHeuristic4() {
		return heuristic4;
	}
	public void setHeuristic4(Double heuristic4) {
		this.heuristic4 = heuristic4;
	}
	
	public void incrementarNNENP1(Integer n, Integer numFurgoneta){
		vfugorneta[numFurgoneta].setNp1(vfugorneta[numFurgoneta].getNp1()+n);
	}
	public void incrementarNNENP2(Integer n, Integer numFurgoneta){
		vfugorneta[numFurgoneta].setNp2(vfugorneta[numFurgoneta].getNp2()+n);
	}
	public void incrementarNNP1decrementarNP2(Integer n, Integer numFurgoneta){
		vfugorneta[numFurgoneta].setNp2(vfugorneta[numFurgoneta].getNp2()-n);
		vfugorneta[numFurgoneta].setNp1(vfugorneta[numFurgoneta].getNp1()+n);
	}
	
	public void incrementarNNP2decrementarNP1(Integer n, Integer numFurgoneta){
		vfugorneta[numFurgoneta].setNp2(vfugorneta[numFurgoneta].getNp2()+n);
		vfugorneta[numFurgoneta].setNp1(vfugorneta[numFurgoneta].getNp1()-n);
	}
	public void decrementarNNENP2(Integer n, Integer numFurgoneta){
		vfugorneta[numFurgoneta].setNp2(vfugorneta[numFurgoneta].getNp2()-n);
	}
	public void decrementarNNENP1(Integer n, Integer numFurgoneta){
		vfugorneta[numFurgoneta].setNp1(vfugorneta[numFurgoneta].getNp1()-n);
	}
	public void intercambiarP1P2(Integer numFurgoneta){
		Estacion P1=vfugorneta[numFurgoneta].getEstacioP1();
		vfugorneta[numFurgoneta].setEstacioP1(vfugorneta[numFurgoneta].getEstacioP2());
		vfugorneta[numFurgoneta].setEstacioP2(P1);
	}
	public void canviarP1(Integer numFurgoneta, Estacion EstacioP1){
		vfugorneta[numFurgoneta].setEstacioP1(EstacioP1);
	}
	public void canviarP2(Integer numFurgoneta, Estacion EstacioP2){
		vfugorneta[numFurgoneta].setEstacioP2(EstacioP2);
	}
	public void intercambiarEs(Integer numFurgoneta1, Integer numFurgoneta2){
		
		Estacion EF1=vfugorneta[numFurgoneta1].getEstacioE();
		Estacion EF2=vfugorneta[numFurgoneta2].getEstacioE();
		vfugorneta[numFurgoneta1].setEstacioE(EF1);
		vfugorneta[numFurgoneta2].setEstacioE(EF2);
		Estaciones es=GeneraProblema.getEstaciones();
		if(EF1 != null){
			int index1=es.indexOf(EF1);
			if()
		}
		if(EF2 != null){

			int index2=es.indexOf(EF2);
			
		}
		
		
		
	}
	
	public void intercambiarE(Integer numFurgoneta, Integer numEstacio){
		
		Integer EF1=vfugorneta[numFurgoneta].getEstacioE();
		Integer novaF=vEstacions[numEstacio];
		vEstacions[numEstacio]=numFurgoneta;
		vEstacions[EF1]=novaF;
		if(novaF != null){
			
			vfugorneta[novaF].setEstacioE(numEstacio);
			if(vfugorneta[novaF].getNp1()+vfugorneta[novaF].getNp2()>)
			if()
		}	
	}
	
}
