package bicing;

import java.util.Arrays;
import java.util.Random;
import IA.Bicing.Estaciones;
import aima.datastructures.PriorityQueue;
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
		this.vFurgonetas = vFurgonetas;
		this.vEstaciones = vEstaciones;
		this.costeGasolina = costeGasolina;
		BicisBienColocadas = bicisBenColocades;
		BicisMalColocadas = bicisMalColocades;
	}


	public Estado(){
		vFurgonetas=null;
		vEstaciones=null;
		costeGasolina=0.0;
		BicisBienColocadas=0;
		BicisMalColocadas=0;
		
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
	private static Integer bicisBienColocadesEstacionE(Integer n,Integer falten){
		
		if(falten>0)return minim(falten,n);
		return 0;
	}
	
	private void RecalcularBicisBien(Integer e, Integer abans, Integer ara){
		//System.out.println("DEMANDA ");
		//System.out.println(e.getDemanda());
		//System.out.println("BICICLETAS NEXT");
		//System.out.println(e.getNumBicicletasNext());
		
		Integer falten= -GeneraProblema.getEstacion(e).getNumBicicletasNext()+GeneraProblema.getEstacion(e).getDemanda()-vEstaciones[e].getBicisAgafen();
		
		
		//System.out.println("falten");
		//System.out.println(falten);
		/**
		System.out.println("abans");
		System.out.println(abans);
		System.out.println("ara");
		System.out.println(ara);
		**/
		Integer b=bicisBienColocadesEstacionE(abans,falten);
		
		
		
		Integer c=bicisBienColocadesEstacionE(ara,falten);
		//System.out.println("empiezo a ahora ");
		//System.out.println("bicis bien colocadas antes ");
		//System.out.println(b);
		//System.out.println("bicis bien colocadas ahora ");
		//System.out.println(c);
		//System.out.println("BICIS BIEN ");
		//System.out.println(BicisBienColocadas);
		BicisBienColocadas = BicisBienColocadas-b+c;
		//System.out.println(BicisBienColocadas);
		
	}
	
	
	
	private static Integer bicisMalColocadas(Integer t,Integer nsobre){
		
		if (nsobre <0)return t;
		else if(t>nsobre) return (t-nsobre);
		return 0;
		
	}
	private Integer bicisMalColocadas(Integer np1,Integer np2,Integer nsobre){
		return bicisMalColocadas(np1+np2,nsobre);
	}
	private void RecalcularBicisMal(Integer e, Integer abansnpc, Integer aranpc,Integer nes){
		
		Integer nsobre=-GeneraProblema.getEstacion(e).getNumBicicletasNext()+GeneraProblema.getEstacion(e).getDemanda();

		Integer b=bicisMalColocadas(abansnpc,nes,nsobre);
		
		Integer c=bicisMalColocadas(aranpc,nes,nsobre);
		//System.out.println("empiezo a ahora ");
		//System.out.println("bicis mal colocadas antes ");
		//System.out.println(b);
		//System.out.println("bicis mal colocadas ahora ");
		//System.out.println(c);
		
		//System.out.println("BICIS MAL ");
		//System.out.println(BicisMalColocadas);
		BicisMalColocadas = BicisMalColocadas-b+c;
		
	}
	
	public void incrementarNNENP1(Integer n, Integer numFurgoneta){
		//System.out.println(" incrementarNNENP1");
		Integer iP=vFurgonetas[numFurgoneta].getindexEstacioP1();
		Integer npd=vFurgonetas[numFurgoneta].getNp1();
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()+n);
		Integer ne=vEstaciones[iP].getBicisColocades();
		RecalcularBicisBien(iP,ne,ne+n);
		RecalcularBicisMal(vFurgonetas[numFurgoneta].getindexEstacioE(),npd,npd+n,vFurgonetas[numFurgoneta].getNp2());
		vEstaciones[iP].sumaNBicis(n);
		vEstaciones[vFurgonetas[numFurgoneta].getindexEstacioE()].sumaNBicisAg(n);

	}
	public void incrementarNNENP2(Integer n, Integer numFurgoneta){
		//System.out.println(" incrementarNNENP2");
		Integer iP=vFurgonetas[numFurgoneta].getindexEstacioP2();
		Integer npd=vFurgonetas[numFurgoneta].getNp2();
		vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getNp2()+n);
		Integer ne=vEstaciones[iP].getBicisColocades();
		RecalcularBicisBien(iP,ne,ne+n);
		RecalcularBicisMal(vFurgonetas[numFurgoneta].getindexEstacioE(),npd,npd+n,vFurgonetas[numFurgoneta].getNp1());
		vEstaciones[iP].sumaNBicis(n);
		vEstaciones[vFurgonetas[numFurgoneta].getindexEstacioE()].sumaNBicisAg(n);
		
	}
	public void incrementarNNP1decrementarNP2(Integer n, Integer numFurgoneta){
		//System.out.println(" incrementarNNP1decrementarNP2");
		Integer P1=vFurgonetas[numFurgoneta].getindexEstacioP1();
		Integer P2=vFurgonetas[numFurgoneta].getindexEstacioP2();
		Integer np2=vEstaciones[P2].getBicisColocades();
		Integer np1=vEstaciones[P1].getBicisColocades();
		vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getNp2()-n);
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()+n);
		RecalcularBicisBien(P1,np1,np1+n);
		RecalcularBicisBien(P2,np2,np2-n);
		vEstaciones[P1].sumaNBicis(n);
		vEstaciones[P2].restaNBicis(n);

	}
	
	public void incrementarNNP2decrementarNP1(Integer n, Integer numFurgoneta){
		//System.out.println(" incrementarNNP2decrementarNP1");
		Integer P1=vFurgonetas[numFurgoneta].getindexEstacioP1();
		Integer P2=vFurgonetas[numFurgoneta].getindexEstacioP2();
		Integer np2=vEstaciones[P2].getBicisColocades();
		Integer np1=vEstaciones[P1].getBicisColocades();
		vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getNp2()+n);
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()-n);
		RecalcularBicisBien(P1,np2,np2+n);
		RecalcularBicisBien(P2,np1,np1-n);
		vEstaciones[P2].sumaNBicis(n);
		vEstaciones[P1].restaNBicis(n);

	}
	public void decrementarNNENP2(Integer n, Integer numFurgoneta){
		//System.out.println(" decrementarNNENP2");
		Integer npd=vFurgonetas[numFurgoneta].getNp2();
		Integer iP=vFurgonetas[numFurgoneta].getindexEstacioP2();
		vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getNp2()-n);
		Integer P=vFurgonetas[numFurgoneta].getindexEstacioP2();

		Integer ne=vEstaciones[iP].getBicisColocades();
		RecalcularBicisBien(P,ne,ne-n);
		RecalcularBicisMal(vFurgonetas[numFurgoneta].getindexEstacioE(),npd,npd-n,vFurgonetas[numFurgoneta].getNp1());
		vEstaciones[iP].restaNBicis(n);
		vEstaciones[vFurgonetas[numFurgoneta].getindexEstacioE()].restaNBicisAg(n);

		
	}
	public void decrementarNNENP1(Integer n, Integer numFurgoneta){
		//System.out.println("  decrementarNNENP1");
		Integer npd=vFurgonetas[numFurgoneta].getNp1();
		vFurgonetas[numFurgoneta].setNp1(npd-n);
		Integer P=vFurgonetas[numFurgoneta].getindexEstacioP1();
		Integer ne=vEstaciones[P].getBicisColocades();
		RecalcularBicisBien(P,ne,ne-n);
		RecalcularBicisMal(vFurgonetas[numFurgoneta].getindexEstacioE(),npd,npd-n,vFurgonetas[numFurgoneta].getNp2());
		vEstaciones[P].restaNBicis(n);
		vEstaciones[vFurgonetas[numFurgoneta].getindexEstacioE()].restaNBicisAg(n);
		
	}
	public void intercambiarP1P2(Integer numFurgoneta){
		//System.out.println("  intercambiarP1P2");
		Estacion P1 = vFurgonetas[numFurgoneta].getEstacioP1();
		vFurgonetas[numFurgoneta].setEstacioP1(vFurgonetas[numFurgoneta].getEstacioP2());
		vFurgonetas[numFurgoneta].setEstacioP2(P1);
		Integer n=vFurgonetas[numFurgoneta].getNp1();
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp2());
		vFurgonetas[numFurgoneta].setNp2(n);
		
	}
	
	public void canviarP1(Integer numFurgoneta, Integer EstacioP1){
		Integer npd=vFurgonetas[numFurgoneta].getNp1();
		Integer P=vFurgonetas[numFurgoneta].getindexEstacioP1();
		vFurgonetas[numFurgoneta].setindexEstacioP1(EstacioP1);
		Integer ne=vEstaciones[P].getBicisColocades();
		Integer nd=vEstaciones[EstacioP1].getBicisColocades();
		RecalcularBicisBien(P,ne,ne-npd);
		RecalcularBicisBien(EstacioP1,nd,nd+npd);
		vEstaciones[EstacioP1].sumaNBicis(npd);
		vEstaciones[P].restaNBicis(npd);
	}
	public void canviarP2(Integer numFurgoneta, Integer EstacioP2){
		Integer npd=vFurgonetas[numFurgoneta].getNp2();
		Integer P=vFurgonetas[numFurgoneta].getindexEstacioP2();
		vFurgonetas[numFurgoneta].setindexEstacioP2(EstacioP2);
		Integer ne=vEstaciones[P].getBicisColocades();
		Integer nd=vEstaciones[EstacioP2].getBicisColocades();
		RecalcularBicisBien(P,ne,ne-npd);
		RecalcularBicisBien(EstacioP2,nd,nd+npd);
		vEstaciones[EstacioP2].restaNBicis(npd);
		vEstaciones[P].sumaNBicis(npd);
	}
	
	
	
	public void canviarP1(Integer numFurgoneta, Estacion EstacioP1){
		//System.out.println("  canviarP1");
		//System.out.println(EstacioP1.getDemanda());
		Integer e1=GeneraProblema.getIndex(EstacioP1);
		canviarP1(numFurgoneta,e1);
		
		//System.out.println( " bicis bien");
		//System.out.println(BicisBienColocadas);
		//System.out.println(BicisMalColocadas);
		
	}
	public void canviarP2(Integer numFurgoneta, Estacion EstacioP2){
		//System.out.println("  canviarP2");
		Integer e2=GeneraProblema.getIndex(EstacioP2);
		canviarP2(numFurgoneta,e2);

	}
	

	private void ajustarNumero(Integer numFurgoneta){
		
		if(vFurgonetas[numFurgoneta].getEstacioE().getNumBicicletasNoUsadas()<vFurgonetas[numFurgoneta].getNp1()+vFurgonetas[numFurgoneta].getNp2()){
			if((vFurgonetas[numFurgoneta].getEstacioE().getNumBicicletasNoUsadas())==0){
				vFurgonetas[numFurgoneta].setNp2(0);
				vFurgonetas[numFurgoneta].setNp1(0);
			}
			else{
				int diff=(vFurgonetas[numFurgoneta].getNp1()+vFurgonetas[numFurgoneta].getNp2())% (vFurgonetas[numFurgoneta].getEstacioE().getNumBicicletasNoUsadas()) ;
				int mig=diff/2;
				Integer P1=vFurgonetas[numFurgoneta].getindexEstacioP1();
				Integer P2=vFurgonetas[numFurgoneta].getindexEstacioP2();
				Integer ne1=vEstaciones[P1].getBicisColocades();
				Integer ne2=vEstaciones[P2].getBicisColocades();
				vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()-mig);
				Integer np2=vFurgonetas[numFurgoneta].getNp2();
				vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getEstacioE().getNumBicicletasNoUsadas()-vFurgonetas[numFurgoneta].getNp1());
				RecalcularBicisBien(P1,ne1,ne1-mig);
				RecalcularBicisBien(P2,ne2,ne2-(np2-vFurgonetas[numFurgoneta].getNp2()));
				vEstaciones[P1].restaNBicis(mig);
				vEstaciones[P2].restaNBicis(np2-vFurgonetas[numFurgoneta].getNp2());
				vEstaciones[vFurgonetas[numFurgoneta].getindexEstacioE()].restaNBicisAg(diff);
			}
		}
	}
	
	public void intercambiarEs(Integer numFurgoneta1, Integer numFurgoneta2){
		//System.out.println("  NOOOO");
		Estacion EF1=vFurgonetas[numFurgoneta1].getEstacioE();
		Estacion EF2=vFurgonetas[numFurgoneta2].getEstacioE();
		
		Integer bicisMalEF1bef=0;
		Integer bicisMalEF2bef=0;
		Integer bicisMalEF1now=0;
		Integer bicisMalEF2now=0;
		Integer neEF1=vFurgonetas[numFurgoneta1].getNp1()+vFurgonetas[numFurgoneta1].getNp2();
		Integer neEF2=vFurgonetas[numFurgoneta2].getNp1()+vFurgonetas[numFurgoneta2].getNp2();
		vFurgonetas[numFurgoneta1].setEstacioE(EF2);
		vFurgonetas[numFurgoneta2].setEstacioE(EF1);
		Estaciones es=oblema.getEstaciones();
		if(EF1 != null){
			int index1=es.indexOf(EF1);
			vEstaciones[index1].setFurg(numFurgoneta2);
			bicisMalEF1bef=bicisMalColocadas(neEF1,EF1.getDemanda()-EF1.getNumBicicletasNext());
			
			ajustarNumero(numFurgoneta2);
			bicisMalEF1now=bicisMalColocadas(vFurgonetas[numFurgoneta2].getNp1()+vFurgonetas[numFurgoneta2].getNp2(),EF1.getDemanda()-EF1.getNumBicicletasNext());
		}
		if(EF2 != null){
			int index2=es.indexOf(EF2);
			vEstaciones[index2].setFurg(numFurgoneta1);
			bicisMalEF2bef=bicisMalColocadas(neEF2,EF2.getDemanda()-EF2.getNumBicicletasNext());
			
			ajustarNumero(numFurgoneta1);
			bicisMalEF2now=bicisMalColocadas(vFurgonetas[numFurgoneta1].getNp1()+vFurgonetas[numFurgoneta1].getNp2(),EF2.getDemanda()-EF2.getNumBicicletasNext());
		}
		BicisMalColocadas= BicisMalColocadas+bicisMalEF2now+bicisMalEF1now-bicisMalEF1bef-bicisMalEF2bef;
	}
	
	private void intercambiarprivate(Estacion numEstacion1,Estacion numEstacion2, Integer index1,Integer index2){
		//System.out.println("  NOOOO");
		int fnum1=vEstaciones[index1].getFurg();
		int fnum2=vEstaciones[index2].getFurg();
		vEstaciones[index1].setFurg(fnum2);
		vEstaciones[index2].setFurg(fnum1);;
		if(fnum1 != -1){
			
			Integer ant=bicisMalColocadas(vFurgonetas[fnum1].getNp1()+vFurgonetas[fnum1].getNp2(),numEstacion2.getDemanda()-numEstacion2.getNumBicicletasNext());
			vFurgonetas[fnum1].setEstacioE(numEstacion2);
			ajustarNumero(fnum1);
			Integer des=bicisMalColocadas(vFurgonetas[fnum1].getNp1()+vFurgonetas[fnum1].getNp2(),numEstacion2.getDemanda()-numEstacion2.getNumBicicletasNext());
			BicisMalColocadas=BicisMalColocadas-ant+des;
		}
		if(fnum2 != -1){
			Integer ant=bicisMalColocadas(vFurgonetas[fnum2].getNp1()+vFurgonetas[fnum2].getNp2(),numEstacion1.getDemanda()-numEstacion1.getNumBicicletasNext());
			vFurgonetas[fnum2].setEstacioE(numEstacion1);
			ajustarNumero(fnum2);
			Integer des=bicisMalColocadas(vFurgonetas[fnum2].getNp1()+vFurgonetas[fnum2].getNp2(),numEstacion1.getDemanda()-numEstacion1.getNumBicicletasNext());
			BicisMalColocadas=BicisMalColocadas-ant+des;
		}
		
		
	}
	/**
	 * Como m�nimo una de las dos estaciones tiene de tener una Furgoneta assignada
	 * @param numEstacion1
	 * @param numEstacion2
	 */
	public void intercambiarE(Estacion numEstacion1, Estacion numEstacion2){
		Estaciones es=oblema.getEstaciones();
		int index1=es.indexOf(numEstacion1);
		int index2=es.indexOf(numEstacion2);
		intercambiarprivate(numEstacion1,numEstacion2,index1,index2);
	}
	
	
	
	/**
	 * Como m�nimo una de las dos estaciones tiene de tener una Furgoneta assignada
	 * @param numEstacion1
	 * @param numEstacion2
	 */
	public void intercambiarE(Integer numEstacion1, Integer numEstacion2){
		Estaciones es=oblema.getEstaciones();
		Estacion e1=es.get(numEstacion1);
		Estacion e2=es.get(numEstacion2);
		intercambiarprivate(e1,e2,numEstacion1,numEstacion2);
	}
	
	private static int minim(int a,int b){
		
		if(a<b)return a;
		else return b;
		
	}
	
	
	
	public static Estado estadoInicial_v1(int numF, int numE){
		
		int min= minim(numF,numE);
		
		Integer BSuman=0;
		Integer Brestan=0;
		Double g=0.0;
		Furgoneta[] vfurg= new Furgoneta[numF];
		Struct[] vEst= new Struct[numE];
		Random rnd= new Random();
		Estaciones est=oblema.getEstaciones();
		java.util.ArrayList<Integer> ve = new java.util.ArrayList<Integer>();
		java.util.ArrayList<Integer> vp = new java.util.ArrayList<Integer>();
		//System.out.println("Jose");
		for(int i=0; i < numE; ++i)
		{
			vEst[i]= new Struct();
			ve.add(i);
			vp.add(i);
			vp.add(i);
		}

		
		for(int i=0;i<min;++i){
			int te = rnd.nextInt(ve.size()-1);
			//System.out.println("Vector");
			int pe = ve.get(te);
			Integer re=(Integer) pe;
			ve.remove(te);
			int tp1 = rnd.nextInt(vp.size()-1);
			while(tp1==te)tp1 = rnd.nextInt(vp.size()-1);
			//System.out.println("Vector2");
			int rp1 = vp.get(tp1);
			vp.remove(tp1);
			int tp2 = rnd.nextInt(vp.size()-1);
			while(tp2==te)tp2 = rnd.nextInt(vp.size()-1);
			//System.out.println("Vector3");
			int rp2 = vp.get(tp2);
			vp.remove(tp2);

			Estacion e=est.get(re);
			//System.out.println("Vector4");
			Estacion p1=est.get(rp1);
			//System.out.println("Vector5");
			Estacion p2=est.get(rp2);
			//System.out.println("Vector6");
			Double auxi=rnd.nextDouble();
			//System.out.println(auxi);

			//Double n= 0.6*e.getNumBicicletasNoUsadas();
			Double n= 0.6*e.getNumBicicletasNext();
			//System.out.println(n);
			Integer ne=n.intValue();
			ne=minim(30,ne);
			//System.out.println(ne);
			int np1=ne/2;
			//System.out.println(np1);
			int np2=ne-np1;
			//System.out.println(np2);
			vEst[re].setFurg(i);
			vEst[re].setBicisAgafen(ne);
			Integer arp1=vEst[rp1].getBicisColocades();
			Integer arp2=vEst[rp2].getBicisColocades();
			Furgoneta aux=new Furgoneta(e,p1,p2,np1,np2);
			vfurg[i]=aux;
			//System.out.println("Vector7");
			Integer nsobre=-e.getDemanda()+e.getNumBicicletasNext()-ne;
			Integer ns=-e.getDemanda()+e.getNumBicicletasNext();
			Integer t= np1+np2;
			Integer Brau=0;
			Double gau=0.0;
			if (nsobre <0){
				if(ns>0){
					if(ne>0)Brau=Brau+minim(ns,ne);
				}
			}
			//System.out.println("Vector8");
			
			Integer falten= -p1.getNumBicicletasNext()+p1.getDemanda()+vEst[rp1].getBicisAgafen();
			Integer b=bicisBienColocadesEstacionE(arp1,falten);
			Integer c=bicisBienColocadesEstacionE(arp1+np1,falten);
			vEst[rp1].setBicisColocades(arp1+np1);
			
			
			Integer falten2= -p2.getNumBicicletasNext()+p2.getDemanda()+vEst[rp2].getBicisAgafen();
			Integer b2=bicisBienColocadesEstacionE(arp2,falten2);
			Integer c2=bicisBienColocadesEstacionE(arp2+np2,falten2);
			vEst[rp2].setBicisColocades(arp2+np2);
			
			//System.out.println("Vector9");
			Integer km1= oblema.distancia(e,p1);
			Integer km2= oblema.distancia(p1,p2);
			//System.out.println(km1);
			//System.out.println(km2);
			//System.out.println("Vector9.5");
			Integer d1=(t+9)/10;
			Integer d2=(np2+9)/10;
			//System.out.println("Vector9.6");
			gau=(double) d1*km1+d2*(km1+km2);
			//System.out.println("Vector9.7");
			BSuman=BSuman-b+c-b2+c2;
			//System.out.println("Vector9.8");
			Brestan=Brestan+Brau;
			g=g+gau;
			//System.out.println("Vector10");
			
		}
		for (int i=min;i<numF;++i){
			vfurg[i]=new Furgoneta();
		}
		//System.out.println("restan");
		//System.out.println(Brestan);
		return new Estado(vfurg,vEst,g,BSuman,Brestan);
	}


	public static Estado estadoInicial_v2(int numF, int numE){
		
		int min= minim(numF,numE);
		
		Integer BSuman=0;
		Integer Brestan=0;
		Double g=0.0;
		Furgoneta[] vfurg= new Furgoneta[numF];
		Struct[] vEst= new Struct[numE];
		Random rnd= new Random();
		Estaciones est=oblema.getEstaciones();
		java.util.ArrayList<Integer> ve = new java.util.ArrayList<Integer>();
		java.util.ArrayList<Integer> vp = new java.util.ArrayList<Integer>();
		//System.out.println("Jose");
		for(int i=0; i < numE; ++i)
		{
			vEst[i]= new Struct();
			ve.add(i);
			vp.add(i);
			vp.add(i);
		}

		
		for(int i=0;i<min;++i){
			int te = rnd.nextInt(ve.size()-1);
			//System.out.println("Vector");
			int pe = ve.get(te);
			Integer re=(Integer) pe;
			ve.remove(te);
			int tp1 = rnd.nextInt(vp.size()-1);
			while(tp1==te)tp1 = rnd.nextInt(vp.size()-1);
			//System.out.println("Vector2");
			int rp1 = vp.get(tp1);
			vp.remove(tp1);
			int tp2 = rnd.nextInt(vp.size()-1);
			while(tp2==te)tp2 = rnd.nextInt(vp.size()-1);
			//System.out.println("Vector3");
			int rp2 = vp.get(tp2);
			vp.remove(tp2);

			Estacion e=est.get(re);
			//System.out.println("Vector4");
			Estacion p1=est.get(rp1);
			//System.out.println("Vector5");
			Estacion p2=est.get(rp2);
			//System.out.println("Vector6");
			Double auxi=rnd.nextDouble();
			//System.out.println(auxi);

			Double n= 0.6*e.getNumBicicletasNoUsadas();
			//Double n= 0.6*e.getNumBicicletasNext();
			//System.out.println(n);
			Integer ne=n.intValue();
			ne=minim(30,ne);
			//System.out.println(ne);
			int np1=ne/2;
			//System.out.println(np1);
			int np2=ne-np1;
			//System.out.println(np2);
			vEst[re].setFurg(i);
			vEst[re].setBicisAgafen(ne);
			Integer arp1=vEst[rp1].getBicisColocades();
			Integer arp2=vEst[rp2].getBicisColocades();
			Furgoneta aux=new Furgoneta(e,p1,p2,np1,np2);
			vfurg[i]=aux;
			//System.out.println("Vector7");
			Integer nsobre=-e.getDemanda()+e.getNumBicicletasNext()-ne;
			Integer ns=-e.getDemanda()+e.getNumBicicletasNext();
			Integer t= np1+np2;
			Integer Brau=0;
			Double gau=0.0;
			if (nsobre <0){
				if(ns>0){
					if(ne>0)Brau=Brau+minim(ns,ne);
				}
			}
			//System.out.println("Vector8");
			
			Integer falten= -p1.getNumBicicletasNext()+p1.getDemanda()+vEst[rp1].getBicisAgafen();
			Integer b=bicisBienColocadesEstacionE(arp1,falten);
			Integer c=bicisBienColocadesEstacionE(arp1+np1,falten);
			vEst[rp1].setBicisColocades(arp1+np1);
			
			
			Integer falten2= -p2.getNumBicicletasNext()+p2.getDemanda()+vEst[rp2].getBicisAgafen();
			Integer b2=bicisBienColocadesEstacionE(arp2,falten2);
			Integer c2=bicisBienColocadesEstacionE(arp2+np2,falten2);
			vEst[rp2].setBicisColocades(arp2+np2);
			
			//System.out.println("Vector9");
			Integer km1= oblema.distancia(e,p1);
			Integer km2= oblema.distancia(p1,p2);
			//System.out.println(km1);
			//System.out.println(km2);
			//System.out.println("Vector9.5");
			Integer d1=(t+9)/10;
			Integer d2=(np2+9)/10;
			//System.out.println("Vector9.6");
			gau=(double) d1*km1+d2*(km1+km2);
			//System.out.println("Vector9.7");
			BSuman=BSuman-b+c-b2+c2;
			//System.out.println("Vector9.8");
			Brestan=Brestan+Brau;
			g=g+gau;
			//System.out.println("Vector10");
			
		}
		for (int i=min;i<numF;++i){
			vfurg[i]=new Furgoneta();
		}
		//System.out.println("restan");
		//System.out.println(Brestan);
		return new Estado(vfurg,vEst,g,BSuman,Brestan);
	}






private class auxiclass implements Comparable<auxiclass> {

	private Integer bicisnousades;
	private Integer indexestacions;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getOuterType().hashCode();
		result = prime * result + ((bicisnousades == null) ? 0 : bicisnousades.hashCode());
		result = prime * result + ((indexestacions == null) ? 0 : indexestacions.hashCode());
		return result;
	}

	public auxiclass(Integer bicisnousades, Integer indexestacions) {
		super();
		this.bicisnousades = bicisnousades;
		this.indexestacions = indexestacions;
	}
	
	public auxiclass(){
		bicisnousades=0;
		indexestacions=-1;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		auxiclass other = (auxiclass) obj;
		if (!getOuterType().equals(other.getOuterType()))
			return false;
		if (bicisnousades == null) {
			if (other.bicisnousades != null)
				return false;
		} else if (!bicisnousades.equals(other.bicisnousades))
			return false;
		if (indexestacions == null) {
			if (other.indexestacions != null)
				return false;
		} else if (!indexestacions.equals(other.indexestacions))
			return false;
		return true;
	}

	public Integer getBicisnousades() {
		return bicisnousades;
	}

	public void setBicisnousades(Integer bicisnousades) {
		this.bicisnousades = bicisnousades;
	}

	public Integer getIndexestacions() {
		return indexestacions;
	}

	public void setIndexestacions(Integer indexestacions) {
		this.indexestacions = indexestacions;
	}


	  @Override
	  public int compareTo(auxiclass other) {
		  return Integer.valueOf(bicisnousades).compareTo(other.bicisnousades);
	  }
	private Estado getOuterType() {
		return Estado.this;
	}

	  // also implement equals() and hashCode()
	}

public static Estado estadoInicial_v3(int numF, int numE){
		
		int min= minim(numF,numE);
		
		Integer BSuman=0;
		Integer Brestan=0;
		Double g=0.0;
		Furgoneta[] vfurg= new Furgoneta[numF];
		Struct[] vEst= new Struct[numE];
		Random rnd= new Random();
		Estaciones est=oblema.getEstaciones();
		java.util.ArrayList<Integer> vp = new java.util.ArrayList<Integer>();
		//System.out.println("Jose");
		for(int i=0; i < numE; ++i)
		{
			vEst[i]= new Struct();
			vp.add(i);
			vp.add(i);
		}

		auxiclass[] nova= new auxiclass[numE];
		for(int i=0; i<numE;++i){
			nova[i].setBicisnousades(est.get(i).getNumBicicletasNoUsadas());
			nova[i].setIndexestacions(i);
		}
		Arrays.sort(nova);
		
		for(int i=0;i<min;++i){
			int re= nova[i].getIndexestacions();
			int tp1 = rnd.nextInt(vp.size()-1);
			while(tp1==(int)re)tp1 = rnd.nextInt(vp.size()-1);
			//System.out.println("Vector2");
			int rp1 = vp.get(tp1);
			vp.remove(tp1);
			int tp2 = rnd.nextInt(vp.size()-1);
			while(tp2==(int)re)tp2 = rnd.nextInt(vp.size()-1);
			//System.out.println("Vector3");
			int rp2 = vp.get(tp2);
			vp.remove(tp2);

			Estacion e=est.get(re);
			//System.out.println("Vector4");
			Estacion p1=est.get(rp1);
			//System.out.println("Vector5");
			Estacion p2=est.get(rp2);
			//System.out.println("Vector6");
			Double auxi=rnd.nextDouble();
			//System.out.println(auxi);

			Double n= 0.6*e.getNumBicicletasNoUsadas();
			//Double n= 0.6*e.getNumBicicletasNext();
			//System.out.println(n);
			Integer ne=n.intValue();
			ne=minim(30,ne);
			//System.out.println(ne);
			int np1=ne/2;
			//System.out.println(np1);
			int np2=ne-np1;
			//System.out.println(np2);
			vEst[re].setFurg(i);
			vEst[re].setBicisAgafen(ne);
			Integer arp1=vEst[rp1].getBicisColocades();
			Integer arp2=vEst[rp2].getBicisColocades();
			Furgoneta aux=new Furgoneta(e,p1,p2,np1,np2);
			vfurg[i]=aux;
			//System.out.println("Vector7");
			Integer nsobre=-e.getDemanda()+e.getNumBicicletasNext()-ne;
			Integer ns=-e.getDemanda()+e.getNumBicicletasNext();
			Integer t= np1+np2;
			Integer Brau=0;
			Double gau=0.0;
			if (nsobre <0){
				if(ns>0){
					if(ne>0)Brau=Brau+minim(ns,ne);
				}
			}
			//System.out.println("Vector8");
			
			Integer falten= -p1.getNumBicicletasNext()+p1.getDemanda()+vEst[rp1].getBicisAgafen();
			Integer b=bicisBienColocadesEstacionE(arp1,falten);
			Integer c=bicisBienColocadesEstacionE(arp1+np1,falten);
			vEst[rp1].setBicisColocades(arp1+np1);
			
			
			Integer falten2= -p2.getNumBicicletasNext()+p2.getDemanda()+vEst[rp2].getBicisAgafen();
			Integer b2=bicisBienColocadesEstacionE(arp2,falten2);
			Integer c2=bicisBienColocadesEstacionE(arp2+np2,falten2);
			vEst[rp2].setBicisColocades(arp2+np2);
			
			//System.out.println("Vector9");
			Integer km1= oblema.distancia(e,p1);
			Integer km2= oblema.distancia(p1,p2);
			//System.out.println(km1);
			//System.out.println(km2);
			//System.out.println("Vector9.5");
			Integer d1=(t+9)/10;
			Integer d2=(np2+9)/10;
			//System.out.println("Vector9.6");
			gau=(double) d1*km1+d2*(km1+km2);
			//System.out.println("Vector9.7");
			BSuman=BSuman-b+c-b2+c2;
			//System.out.println("Vector9.8");
			Brestan=Brestan+Brau;
			g=g+gau;
			//System.out.println("Vector10");
			
		}
		for (int i=min;i<numF;++i){
			vfurg[i]=new Furgoneta();
		}
		//System.out.println("restan");
		//System.out.println(Brestan);
		return new Estado(vfurg,vEst,g,BSuman,Brestan);
	}

	
	
	public void print() {
		
		System.out.println("Estaciones:");
		System.out.println("Furgonetas:");
		for (Furgoneta f : vFurgonetas) {
			Estacion E = f.getEstacioE(), P1 = f.getEstacioP1(), P2 = f.getEstacioP2(); 
			int np1 = f.getNp1(), np2 = f.getNp2();
			Estaciones es=oblema.getEstaciones();
			System.out.println("Inicial:" + es.indexOf(E) + " " + (np1+np2) + " P1: " + es.indexOf(f.getEstacioP1())+ " " + np1 + " P2: " + es.indexOf(f.getEstacioP2())+ " " + np2);
		}
		System.out.println("");
		
	}
	
	
}
