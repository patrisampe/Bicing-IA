package bicing;

import java.util.Arrays;
import java.util.Random;
import IA.Bicing.Estaciones;
import IA.Bicing.Estacion;

/**
 * Proyecto de IA- Busqueda local
 * 
 * Clase que define el estado del problema.
 * 
 * 
 * @author Patricia
 *
 */
public class Estado {






	private Furgoneta[] vFurgonetas;
	private Struct[] vEstaciones;
   
	private Double costeGasolina;
	
	private Integer BicisBienColocadas;
	private Integer BicisMalColocadas;
	

	
	public Estado(Furgoneta[] vFurgonetas, Struct[] vEstaciones, Double costeGasolina, Integer bicisBenColocades,
			Integer bicisMalColocades) {
		super();
		this.vFurgonetas = vFurgonetas;
		this.vEstaciones = vEstaciones;
		this.costeGasolina = costeGasolina;
		BicisBienColocadas = bicisBenColocades;
		BicisMalColocadas = bicisMalColocades;
	}



	private Furgoneta[] copyvFurg(Furgoneta[] vFurg){
		Furgoneta[] newe = new Furgoneta[vFurg.length];
		for(int i=0; i<vFurg.length;++i){
			newe[i]= new Furgoneta(vFurg[i]);
		}
		return newe;
	}
	
	

	public Integer getBicisBienColocadas() {
		return BicisBienColocadas;
	}

	public void setBicisBienColocadas(Integer bicisBienColocades) {
		BicisBienColocadas = bicisBienColocades;
	}

	public Integer getBicisMalColocadas() {
		return BicisMalColocadas;
	}

	public void setBicisMalColocadas(Integer bicisMalColocades) {
		BicisMalColocadas = bicisMalColocades;
	}

	
	private Struct[] copyvStruct(Struct[] vSt){
		Struct[] newe = new Struct[vSt.length];
		for(int i=0; i<vSt.length;++i){
			newe[i]= new Struct(vSt[i]);
		}
		return newe;
	}
	
	public Estado(Estado E1){
		vFurgonetas=copyvFurg(E1.vFurgonetas);
		vEstaciones=copyvStruct(E1.vEstaciones);
		costeGasolina=E1.costeGasolina;	
		BicisBienColocadas=E1.BicisBienColocadas;
		BicisMalColocadas=E1.BicisMalColocadas;
	}
	public Furgoneta[] getvFurgonetas() {
		return vFurgonetas;
	}
	public void setvFurgonetas(Furgoneta[] vFurgonetas) {
		this.vFurgonetas = vFurgonetas;
	}
	public Struct[] getvEstaciones() {
		return vEstaciones;
	}
	public void setvEstaciones(Struct[] vEstaciones) {
		this.vEstaciones = vEstaciones;
	}
	
	public Double getCosteGasolina() {
		return costeGasolina;
	}
	public void setCosteGasolina(Double costeGasolina) {
		this.costeGasolina = costeGasolina;
	}
	private Integer bicisBienColocadesEstacionE(Integer n,Integer falten){
		
		if(falten>0)return minim(falten,n);
		return 0;
	}
	
	private void RecalcularBicisBien(Estacion e, Integer abans, Integer ara){
		Integer falten= e.getNumBicicletasNext()-e.getDemanda();
		Integer b=bicisBienColocadesEstacionE(abans,falten);
		Integer c=bicisBienColocadesEstacionE(ara,falten);
		BicisBienColocadas = BicisBienColocadas-b+c;
		
	}
	
	private Integer bicisMalColocadas(Integer np1,Integer np2,Integer nsobre){
		Integer t= np1+np2;
		if (nsobre <0)return t;
		else if(t>nsobre) return (t-nsobre);
		return 0;
		
	}
	
	private void RecalcularBicisMal(Estacion e, Integer abansnpc, Integer aranpc,Integer nes){
		
		Integer nsobre=e.getDemanda()-e.getNumBicicletasNext();

		Integer falten= e.getNumBicicletasNext()-e.getDemanda();
		Integer b=bicisMalColocadas(abansnpc,nes,nsobre);
		Integer c=bicisMalColocadas(aranpc,nes,nsobre);
		BicisMalColocadas = BicisMalColocadas-b+c;
		
	}
	
	public void incrementarNNENP1(Integer n, Integer numFurgoneta){
		Estacion P=vFurgonetas[numFurgoneta].getEstacioP1();
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()+n);
		Estaciones vest=GeneraProblema.getEstaciones();
		vEstaciones[vest.indexOf(P)].sumaNBicis(n);
		vEstaciones[vest.indexOf(vFurgonetas[numFurgoneta].getEstacioE())].sumaNBicis(n);
		RecalcularBicisBien(P,vFurgonetas[numFurgoneta].getNp1(),vFurgonetas[numFurgoneta].getNp1()+n);
	}
	public void incrementarNNENP2(Integer n, Integer numFurgoneta){
		Estacion P=vFurgonetas[numFurgoneta].getEstacioP2();
		vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getNp2()+n);
		Estaciones vest=GeneraProblema.getEstaciones();
		vEstaciones[vest.indexOf(P)].sumaNBicis(n);
	}
	public void incrementarNNP1decrementarNP2(Integer n, Integer numFurgoneta){
		Estacion P1=vFurgonetas[numFurgoneta].getEstacioP1();
		Estacion P2=vFurgonetas[numFurgoneta].getEstacioP2();
		Estaciones vest=GeneraProblema.getEstaciones();
		vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getNp2()-n);
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()+n);
		vEstaciones[vest.indexOf(P1)].sumaNBicis(n);
		vEstaciones[vest.indexOf(P2)].restaNBicis(n);
	}
	
	public void incrementarNNP2decrementarNP1(Integer n, Integer numFurgoneta){
		Estacion P1=vFurgonetas[numFurgoneta].getEstacioP1();
		Estacion P2=vFurgonetas[numFurgoneta].getEstacioP2();
		Estaciones vest=GeneraProblema.getEstaciones();
		vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getNp2()+n);
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()-n);
		vEstaciones[vest.indexOf(P2)].sumaNBicis(n);
		vEstaciones[vest.indexOf(P1)].restaNBicis(n);
	}
	public void decrementarNNENP2(Integer n, Integer numFurgoneta){
		vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getNp2()-n);
		Estacion P2=vFurgonetas[numFurgoneta].getEstacioP2();
		Estaciones vest=GeneraProblema.getEstaciones();
		vEstaciones[vest.indexOf(P2)].restaNBicis(n);
	}
	public void decrementarNNENP1(Integer n, Integer numFurgoneta){
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()-n);
		Estaciones vest=GeneraProblema.getEstaciones();
		Estacion P1=vFurgonetas[numFurgoneta].getEstacioP1();
		vEstaciones[vest.indexOf(P1)].restaNBicis(n);
	}
	public void intercambiarP1P2(Integer numFurgoneta){
		Estacion P1 = vFurgonetas[numFurgoneta].getEstacioP1();
		vFurgonetas[numFurgoneta].setEstacioP1(vFurgonetas[numFurgoneta].getEstacioP2());
		vFurgonetas[numFurgoneta].setEstacioP2(P1);
		Integer n=vFurgonetas[numFurgoneta].getNp1();
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp2());
		vFurgonetas[numFurgoneta].setNp2(n);
	}
	public void canviarP1(Integer numFurgoneta, Estacion EstacioP1){
		Estacion P=vFurgonetas[numFurgoneta].getEstacioP1();
		vFurgonetas[numFurgoneta].setEstacioP1(EstacioP1);
		Estaciones vest=GeneraProblema.getEstaciones();
		vEstaciones[vest.indexOf(EstacioP1)].restaNBicis(vFurgonetas[numFurgoneta].getNp1());
		vEstaciones[vest.indexOf(P)].sumaNBicis(vFurgonetas[numFurgoneta].getNp1());
	}
	public void canviarP2(Integer numFurgoneta, Estacion EstacioP2){
		vFurgonetas[numFurgoneta].setEstacioP2(EstacioP2);
		Estacion P=vFurgonetas[numFurgoneta].getEstacioP2();
		Estaciones vest=GeneraProblema.getEstaciones();
		vEstaciones[vest.indexOf(EstacioP2)].restaNBicis(vFurgonetas[numFurgoneta].getNp1());
		vEstaciones[vest.indexOf(P)].sumaNBicis(vFurgonetas[numFurgoneta].getNp1());
	}
	
	public void canviarP1(Integer numFurgoneta, Integer EstacioP1){
		Estaciones est=GeneraProblema.getEstaciones();
		canviarP1(numFurgoneta,est.get(EstacioP1));
	}
	public void canviarP2(Integer numFurgoneta, Integer EstacioP2){
		Estaciones est=GeneraProblema.getEstaciones();
		canviarP2(numFurgoneta,est.get(EstacioP2));
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
	
	private void intercambiarprivate(Estacion numEstacion1,Estacion numEstacion2, Integer index1,Integer index2){
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
	
	public void intercambiarE(Estacion numEstacion1, Estacion numEstacion2){
		Estaciones es=GeneraProblema.getEstaciones();
		int index1=es.indexOf(numEstacion1);
		int index2=es.indexOf(numEstacion2);
		intercambiarprivate(numEstacion1,numEstacion2,index1,index2);
	}
	
	
	
	
	public void intercambiarE(Integer numEstacion1, Integer numEstacion2){
		Estaciones es=GeneraProblema.getEstaciones();
		Estacion e1=es.get(numEstacion1);
		Estacion e2=es.get(numEstacion2);
		intercambiarprivate(e1,e2,numEstacion1,numEstacion2);
	}
	
	private int minim(int a,int b){
		
		if(a>b)return a;
		else return b;
		
	}
	
	
	
	public Estado estadoInicial(int numF, int numE){
		
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
		return new Estado(vfurg,vEst,BSuman,Brestan,g,h);
	}
	
	
}
