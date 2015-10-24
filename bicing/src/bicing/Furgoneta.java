package bicing;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;

public class Furgoneta {

	private Integer estacioE;
	private Integer estacioP1;
	private Integer estacioP2;
	private Integer np1;
	private Integer np2;
	
	public Furgoneta(Integer estacioE, Integer estacioP1, Integer estacioP2, Integer np1, Integer np2) {
		super();
		this.estacioE = estacioE;
		this.estacioP1 = estacioP1;
		this.estacioP2 = estacioP2;
		this.np1 = np1;
		this.np2 = np2;
	}
	public Furgoneta() {
		this.estacioE = -1;
		this.estacioP1 = -1;
		this.estacioP2 = -1;
		this.np1 = 0;
		this.np2 = 0;
	}
	
	public Furgoneta(Furgoneta F1){
		
		estacioE=F1.estacioE;
		estacioP1=F1.estacioP1;
		estacioP2=F1.estacioP2;
		np1=F1.np1;
		np2=F1.np2;
	}
	
	public Integer getindexEstacioE() {
		return estacioE;
	}
	public void setindexEstacioE(Integer estacioE) {
		this.estacioE = estacioE;
	}
	public Integer getindexEstacioP1() {
		return estacioP1;
	}
	public void setindexEstacioP1(Integer estacioP1) {
		this.estacioP1 = estacioP1;
	}
	public Integer getindexEstacioP2() {
		return estacioP2;
	}
	public void setindexEstacioP2(Integer estacioP2) {
		this.estacioP2 = estacioP2;
	}
	
	public Estacion getEstacioE() {
		return GeneraProblema.getEstacion(estacioE);
	}
	public void setEstacioE(Estacion estacioE) {
		this.estacioE = GeneraProblema.getIndex(estacioE);
	}
	public Estacion getEstacioP1() {
		return GeneraProblema.getEstacion(estacioP1);
	}
	public void setEstacioP1(Estacion estacioP1) {
		this.estacioP1 = GeneraProblema.getIndex(estacioP1);
	}
	public Estacion getEstacioP2() {
		return GeneraProblema.getEstacion(estacioP2);
	}
	public void setEstacioP2(Estacion estacioP2) {
		this.estacioP2 = GeneraProblema.getIndex(estacioP2);
	}
	
	
	public Integer getNp1() {
		return np1;
	}
	public void setNp1(Integer np1) {
		this.np1 = np1;
	}
	public Integer getNp2() {
		return np2;
	}
	public void setNp2(Integer np2) {
		this.np2 = np2;
	}
	public Integer demandaE(){
		Estaciones est=GeneraProblema.getEstaciones();
		return est.get(estacioE).getDemanda();
	}
	
	public Integer demandaP1(){
		Estaciones est=GeneraProblema.getEstaciones();
		return est.get(estacioP1).getDemanda();
	}
	public Integer demandaP2(){
		Estaciones est=GeneraProblema.getEstaciones();
		return est.get(estacioP2).getDemanda();
	}
	
	public Integer bicisNextE(){
		Estaciones est=GeneraProblema.getEstaciones();
		return est.get(estacioE).getNumBicicletasNext();
	}
	
	public Integer bicisNextP1(){
		Estaciones est=GeneraProblema.getEstaciones();
		return est.get(estacioP1).getNumBicicletasNext();
	}
	public Integer bicisNextP2(){
		Estaciones est=GeneraProblema.getEstaciones();
		return est.get(estacioP2).getNumBicicletasNext();
	}
	
	
}
