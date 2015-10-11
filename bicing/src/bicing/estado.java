package bicing;

import java.util.Random;
import IA.Bicing.Estaciones;

import IA.Bicing.Estacion;
/**
 * Projecto de IA- B�squeda local
 * 
 * Es la classe que define el estado del problema.
 * 
 * 
 * @author Patricia
 *
 */
public class estado {


	private Furgoneta[] vfugorneta;
	private Integer[] vEstaciones;
	private Integer bicisBenColocades;
	private Integer biciMalColocades;
	private Double costGasolina;
	private Double heuristic4;
	/**
	 * Constructor de la clase
	 * @param vfugorneta -> vector de la clase Furgoneta, donde cada posicion es una furgoneta diferente
	 * @param vEstaciones -> vector de enteros, cada posicion es una estacion y el valor de cada posicion el entero que representa cada furgoneta
	 * @param bicisBenColocades -> numero de bicicletas que te hacen ganar 1 euro
	 * @param biciMalColocades -> numero de bicicletas que te hacen perder 1 euro
	 * @param costGasolina -> coste de la gasolina
	 * @param heuristic4 -> valor del heuristico4
	 */
	public estado(Furgoneta[] vfugorneta, Integer[] vEstaciones, Integer bicisBenColocades, Integer biciMalColocades,
			Double costGasolina, Double heuristic4) {
		this.vfugorneta = vfugorneta;
		this.vEstaciones = vEstaciones;
		this.bicisBenColocades = bicisBenColocades;
		this.biciMalColocades = biciMalColocades;
		this.costGasolina = costGasolina;
		this.heuristic4 = heuristic4;
	}
	/**
	 * 
	 * @return el vector de las furgonetas
	 */
	public Furgoneta[] getVfugorneta() {
		return vfugorneta;
	}
	/**
	 * 
	 * @param vfugorneta
	 */
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
	public Double getCostGasolina() {
		return costGasolina;
	}
	public void setCostGasolina(Double costGasolina) {
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
	
	private void adjustarNumero(Integer numFurgoneta){
		if(vfugorneta[numFurgoneta].getEstacioE().getNumBicicletasNoUsadas()<vfugorneta[numFurgoneta].getNp1()+vfugorneta[numFurgoneta].getNp2()){
			int diff=-vfugorneta[numFurgoneta].getEstacioE().getNumBicicletasNoUsadas()+vfugorneta[numFurgoneta].getNp1()+vfugorneta[numFurgoneta].getNp2();
			int mig=diff/2;
			vfugorneta[numFurgoneta].setNp1(vfugorneta[numFurgoneta].getNp1()-mig);
			vfugorneta[numFurgoneta].setNp1(vfugorneta[numFurgoneta].getEstacioE().getNumBicicletasNoUsadas()-vfugorneta[numFurgoneta].getNp1());
		}
		
	}
	
	public void intercambiarEs(Integer numFurgoneta1, Integer numFurgoneta2){
		
		Estacion EF1=vfugorneta[numFurgoneta1].getEstacioE();
		Estacion EF2=vfugorneta[numFurgoneta2].getEstacioE();
		vfugorneta[numFurgoneta1].setEstacioE(EF2);
		
		vfugorneta[numFurgoneta2].setEstacioE(EF1);
		Estaciones es=GeneraProblema.getEstaciones();
		if(EF1 != null){
			int index1=es.indexOf(EF1);
			vEstaciones[index1]=numFurgoneta2;
			adjustarNumero(numFurgoneta2);
		}
		if(EF2 != null){
			int index2=es.indexOf(EF2);
			vEstaciones[index2]=numFurgoneta1;
			adjustarNumero(numFurgoneta1);
		}
	}
	
	public void intercambiarE(Estacion numEstacion1, Estacion numEstacion2){
		Estaciones es=GeneraProblema.getEstaciones();
		int index1=es.indexOf(numEstacion1);
		int index2=es.indexOf(numEstacion2);
		int fnum1=vEstaciones[index1];
		int fnum2=vEstaciones[index2];
		vEstaciones[index1]=fnum2;
		vEstaciones[index2]=fnum1;
		if(fnum1 != -1){
			vfugorneta[fnum1].setEstacioE(numEstacion2);
			adjustarNumero(fnum1);
		}
		
		if(fnum2 != -1){
			vfugorneta[fnum2].setEstacioE(numEstacion1);
			adjustarNumero(fnum2);
		}
	}
	
	private int minim(int a,int b){
		
		if(a>b)return a;
		else return b;
		
	}
	
	
	
	public estado estadoInicial(int numF, int numE){
		
		int min= minim(numF,numE);
		
		Integer BSuman=0;
		Integer Brestan=0;
		Double g=0.0;
		Double h=0.0;
		Furgoneta[] vfurg= new Furgoneta[numF];
		Integer[] vEst= new Integer[numE];
		Random rnd= new Random();
		Estaciones est=GeneraProblema.getEstaciones();
		java.util.ArrayList<Integer> ve = new java.util.ArrayList<Integer>();
		java.util.ArrayList<Integer> vp = new java.util.ArrayList<Integer>();
		for(int i=0; i < numE; ++i)
		{
			ve.add(i);
			vp.add(i);
			vp.add(i);
		}

		
		for(int i=0;i<min;++i){
			int te = rnd.nextInt(ve.size());
			int re = ve.get(te);
			ve.remove(te);
			int tp1 = rnd.nextInt(vp.size());
			int rp1 = ve.get(tp1);
			vp.remove(tp1);
			int tp2 = rnd.nextInt(vp.size());
			int rp2 = ve.get(tp2);
			vp.remove(tp2);
			Estacion e=est.get(re);
			Estacion p1=est.get(rp1);
			Estacion p2=est.get(rp2);
			Double auxi=rnd.nextDouble();
			Double n= auxi*e.getNumBicicletasNoUsadas();
			int np1=n.intValue()/2;
			int np2=n.intValue()-np1;
			vEst[re]=i;
			Furgoneta aux=new Furgoneta(e,p1,p2,np1,np2);
			vfurg[i]=aux;
			Integer nsobre=e.getDemanda()-e.getNumBicicletasNext();
			Integer t= np1+np2;
			Integer Brau=0;
			Integer Bsu=0;
			Double gau=0.0;
			if (nsobre <0)Brau=Brau+t;
			else if(t>nsobre) Brau=Brau+t-nsobre;
			Integer falten1= p1.getNumBicicletasNext()-p1.getDemanda();
			if(falten1>0)Bsu=Bsu+minim(falten1,np1);
			Integer falten2= p2.getNumBicicletasNext()-p2.getDemanda();
			if(falten2>0)Bsu=Bsu+minim(falten2,np2);
			Double km1= GeneraProblema.distancia(e,p1);
			Double km2= GeneraProblema.distancia(p1,p2);
			Integer d1=(t+9)/10;
			Integer d2=(np2+9)/10;
			gau=d1*km1+d2*(km1+km2);
			h=h+(Bsu-Brau)/gau;
			BSuman=BSuman+Brau;
			Brestan=Brestan+Brau;
			g=g+gau;
			
		}
		for (int i=min;i<numF;++i){
			vfurg[i]=new Furgoneta();
		}
		
		for (int i=min;i<numE;++i){
			vEst[i]=-1;
		}
		return new estado(vfurg,vEst,BSuman,Brestan,g,h);
	}
	
	
}
