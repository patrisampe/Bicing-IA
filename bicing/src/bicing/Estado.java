package bicing;

import java.util.Random;
import IA.Bicing.Estaciones;

import IA.Bicing.Estacion;
/**
 * Projecto de IA- Busqueda local
 * 
 * Clase que define el estado del problema.
 * 
 * 
 * @author Patricia
 *
 */
public class estado {

	private Furgoneta[] vFurgonetas;
	private Integer[] vEstaciones;
	private Integer bicisBienColocadas;
	private Integer bicisMalColocadas;
	private Double costeGasolina;
	private Double heuristico4;
	/**
	 * Constructor de la clase
	 * @param vFurgonetas -> vector de la clase Furgoneta, donde cada posicion es una furgoneta diferente
	 * @param vEstaciones -> vector de enteros, cada posicion es una estacion y el valor de cada posicion el entero que representa cada furgoneta
	 * @param bicisBienColocadas -> numero de bicicletas que te hacen ganar 1 euro
	 * @param bicisMalColocadas -> numero de bicicletas que te hacen perder 1 euro
	 * @param costeGasolina -> coste de la gasolina
	 * @param heuristico4 -> valor del heuristico 4
	 */
	public estado(Furgoneta[] vFurgonetas, Integer[] vEstaciones, Integer bicisBienColocadas, Integer bicisMalColocadas,
			Double costeGasolina, Double heuristico4) {
		this.vFurgonetas = vFurgonetas;
		this.vEstaciones = vEstaciones;
		this.bicisBienColocadas = bicisBienColocadas;
		this.bicisMalColocadas = bicisMalColocadas;
		this.costeGasolina = costeGasolina;
		this.heuristico4 = heuristico4;
	}
	public estado(estado E1){
		
		vEstaciones=E1.vEstaciones;
		vFurgonetas=E1.vFurgonetas;
		bicisBienColocadas=E1.bicisBienColocadas;
		bicisMalColocadas=E1.bicisMalColocadas;
		costeGasolina=E1.costeGasolina;
		heuristico4=E1.heuristico4;
		
	}
	
	/**
	 * 
	 * @return el vector de las furgonetas
	 */
	public Furgoneta[] getvFurgonetas() {
		return vFurgonetas;
	}
	/**
	 * 
	 * @param vFurgonetas
	 */
	public void setvFurgonetas(Furgoneta[] vFurgonetas) {
		this.vFurgonetas = vFurgonetas;
	}
	public Integer[] getvEstaciones() {
		return vEstaciones;
	}
	public void setvEstaciones(Integer[] vEstaciones) {
		this.vEstaciones = vEstaciones;
	}
	public Integer getBicisBienColocadas() {
		return bicisBienColocadas;
	}
	public void setBicisBienColocadas(Integer bicisBienColocadas) {
		this.bicisBienColocadas = bicisBienColocadas;
	}
	public Integer getBicisMalColocadas() {
		return bicisMalColocadas;
	}
	public void setBicisMalColocadas(Integer bicisMalColocadas) {
		this.bicisMalColocadas = bicisMalColocadas;
	}
	public Double getCosteGasolina() {
		return costeGasolina;
	}
	public void setCosteGasolina(Double costeGasolina) {
		this.costeGasolina = costeGasolina;
	}
	public Double getHeuristico4() {
		return heuristico4;
	}
	public void setHeuristico4(Double heuristico4) {
		this.heuristico4 = heuristico4;
	}
	
	public void incrementarNNENP1(Integer n, Integer numFurgoneta){
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()+n);
	}
	public void incrementarNNENP2(Integer n, Integer numFurgoneta){
		vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getNp2()+n);
	}
	public void incrementarNNP1decrementarNP2(Integer n, Integer numFurgoneta){
		vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getNp2()-n);
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()+n);
	}
	
	public void incrementarNNP2decrementarNP1(Integer n, Integer numFurgoneta){
		vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getNp2()+n);
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()-n);
	}
	public void decrementarNNENP2(Integer n, Integer numFurgoneta){
		vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getNp2()-n);
	}
	public void decrementarNNENP1(Integer n, Integer numFurgoneta){
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()-n);
	}
	public void intercambiarP1P2(Integer numFurgoneta){
		Estacion P1 = vFurgonetas[numFurgoneta].getEstacioP1();
		vFurgonetas[numFurgoneta].setEstacioP1(vFurgonetas[numFurgoneta].getEstacioP2());
		vFurgonetas[numFurgoneta].setEstacioP2(P1);
	}
	public void canviarP1(Integer numFurgoneta, Estacion EstacioP1){
		vFurgonetas[numFurgoneta].setEstacioP1(EstacioP1);
	}
	public void canviarP2(Integer numFurgoneta, Estacion EstacioP2){
		vFurgonetas[numFurgoneta].setEstacioP2(EstacioP2);
	}
	
	private void ajustarNumero(Integer numFurgoneta){
		if(vFurgonetas[numFurgoneta].getEstacioE().getNumBicicletasNoUsadas()<vFurgonetas[numFurgoneta].getNp1()+vFurgonetas[numFurgoneta].getNp2()){
			int diff=-vFurgonetas[numFurgoneta].getEstacioE().getNumBicicletasNoUsadas()+vFurgonetas[numFurgoneta].getNp1()+vFurgonetas[numFurgoneta].getNp2();
			int mig=diff/2;
			vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()-mig);
			vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getEstacioE().getNumBicicletasNoUsadas()-vFurgonetas[numFurgoneta].getNp1());
		}
		
	}
	
	public void intercambiarEs(Integer numFurgoneta1, Integer numFurgoneta2){
		
		Estacion EF1=vFurgonetas[numFurgoneta1].getEstacioE();
		Estacion EF2=vFurgonetas[numFurgoneta2].getEstacioE();
		vFurgonetas[numFurgoneta1].setEstacioE(EF2);
		
		vFurgonetas[numFurgoneta2].setEstacioE(EF1);
		Estaciones es=GeneraProblema.getEstaciones();
		if(EF1 != null){
			int index1=es.indexOf(EF1);
			vEstaciones[index1]=numFurgoneta2;
			ajustarNumero(numFurgoneta2);
		}
		if(EF2 != null){
			int index2=es.indexOf(EF2);
			vEstaciones[index2]=numFurgoneta1;
			ajustarNumero(numFurgoneta1);
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
			vFurgonetas[fnum1].setEstacioE(numEstacion2);
			ajustarNumero(fnum1);
		}
		
		if(fnum2 != -1){
			vFurgonetas[fnum2].setEstacioE(numEstacion1);
			ajustarNumero(fnum2);
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
