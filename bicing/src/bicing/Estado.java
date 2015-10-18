package bicing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
	
	
	private Integer bicisBienColocadasIndexE(Integer e){
		Integer falten=-GeneraProblema.getEstacion(e).getNumBicicletasNext()+GeneraProblema.getEstacion(e).getDemanda()+vEstaciones[e].getBicisAgafen();
		if(falten>0)return minim(falten,vEstaciones[e].getBicisColocades());
		return 0;
		
	}
	
	
	private Integer bicisMalColocadasIndexE(Integer e){
		Estacion Es=GeneraProblema.getEstacion(e);
		Integer mal=0;
		Integer hay=Es.getNumBicicletasNext();
		Integer colocamos=vEstaciones[e].getBicisColocades();
		Integer queremos= Es.getDemanda();
		Integer quitamos=vEstaciones[e].getBicisAgafen(); 
		if(quitamos>colocamos){
			quitamos=quitamos-colocamos;
			Integer sobran=hay-queremos;
			if(sobran > 0 & sobran < quitamos){
				mal=quitamos-sobran;
			}
			else{
				mal=quitamos;
			}
			
		}
		
		
		return mal;
		
	}
	
	private static Integer bicisBienColocadasIndexE(Integer e,Struct[] vEst){
		Integer falten=-GeneraProblema.getEstacion(e).getNumBicicletasNext()+GeneraProblema.getEstacion(e).getDemanda()+vEst[e].getBicisAgafen();
		if(falten>0)return minim(falten,vEst[e].getBicisColocades());
		return 0;
		
	}
	
	
	private static Integer bicisMalColocadasIndexE(Integer e, Struct[] vEst){
		Estacion Es=GeneraProblema.getEstacion(e);
		Integer mal=0;
		Integer hay=Es.getNumBicicletasNext();
		Integer colocamos=vEst[e].getBicisColocades();
		Integer queremos= Es.getDemanda();
		Integer quitamos=vEst[e].getBicisAgafen(); 
		if(quitamos>colocamos){
			quitamos=quitamos-colocamos;
			Integer sobran=hay-queremos;
			if(sobran > 0 & sobran < quitamos){
				mal=quitamos-sobran;
			}
			else{
				mal=quitamos;
			}
			
		}
		
		
		return mal;
		
	}
	
	
	public void incrementarNNENP1(Integer n, Integer numFurgoneta){
		//System.out.println(" incrementarNNENP1");
		
		Integer iP=vFurgonetas[numFurgoneta].getindexEstacioP1();
		Integer iE=vFurgonetas[numFurgoneta].getindexEstacioE();
		Integer bienP1=bicisBienColocadasIndexE(iP);
		Integer malE=bicisMalColocadasIndexE(iE);
		
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()+n);
		
		vEstaciones[iP].sumaNBicis(n);
		vEstaciones[iE].sumaNBicisAg(n);
		Integer bienP1v2=bicisBienColocadasIndexE(iP);
		Integer malEv2=bicisMalColocadasIndexE(iE);
		
		BicisBienColocadas= BicisBienColocadas-bienP1+bienP1v2;
		BicisMalColocadas= BicisMalColocadas-malE+malEv2;
		
		
	}
	
	public void incrementarNNP1decrementarNP2(Integer n, Integer numFurgoneta){
		//System.out.println(" incrementarNNP1decrementarNP2");
		Integer P1=vFurgonetas[numFurgoneta].getindexEstacioP1();
		Integer P2=vFurgonetas[numFurgoneta].getindexEstacioP2();
		Integer bienP1=bicisBienColocadasIndexE(P1);
		Integer bienP2=bicisBienColocadasIndexE(P2);

		vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getNp2()-n);
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()+n);
		
		
		Integer bienP1v2=bicisBienColocadasIndexE(P1);
		Integer bienP2v2=bicisBienColocadasIndexE(P2);
		
		BicisBienColocadas= BicisBienColocadas-bienP1-bienP2+bienP1v2+bienP2v2;

	}
	
	public void incrementarNNP2decrementarNP1(Integer n, Integer numFurgoneta){
		//System.out.println(" incrementarNNP2decrementarNP1");
		Integer P1=vFurgonetas[numFurgoneta].getindexEstacioP1();
		Integer P2=vFurgonetas[numFurgoneta].getindexEstacioP2();

		Integer bienP1=bicisBienColocadasIndexE(P1);
		Integer bienP2=bicisBienColocadasIndexE(P2);

		
		vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getNp2()+n);
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()-n);
		
		
		Integer bienP1v2=bicisBienColocadasIndexE(P1);
		Integer bienP2v2=bicisBienColocadasIndexE(P2);
		
		BicisBienColocadas= BicisBienColocadas-bienP1-bienP2+bienP1v2+bienP2v2;

	}
	public void incrementarNNENP2(Integer n, Integer numFurgoneta){
		//System.out.println(" incrementarNNENP2");
		Integer iP=vFurgonetas[numFurgoneta].getindexEstacioP2();
		Integer iE=vFurgonetas[numFurgoneta].getindexEstacioE();
		Integer bienP2=bicisBienColocadasIndexE(iP);
		Integer malE=bicisMalColocadasIndexE(iE);
		
		vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getNp2()+n);
		
		vEstaciones[iP].sumaNBicis(n);
		vEstaciones[vFurgonetas[numFurgoneta].getindexEstacioE()].sumaNBicisAg(n);
		
		Integer bienP2v2=bicisBienColocadasIndexE(iP);
		Integer malEv2=bicisMalColocadasIndexE(iE);
		
		BicisBienColocadas= BicisBienColocadas-bienP2+bienP2v2;
		BicisMalColocadas= BicisMalColocadas-malE+malEv2;
		
	}
	public void decrementarNNENP2(Integer n, Integer numFurgoneta){
		//System.out.println(" decrementarNNENP2");
		Integer iP=vFurgonetas[numFurgoneta].getindexEstacioP2();
		Integer iE=vFurgonetas[numFurgoneta].getindexEstacioE();
		
		Integer bienP2=bicisBienColocadasIndexE(iP);
		Integer malE=bicisMalColocadasIndexE(iE);
		
		vFurgonetas[numFurgoneta].setNp2(vFurgonetas[numFurgoneta].getNp2()-n);

		vEstaciones[iP].restaNBicis(n);
		vEstaciones[vFurgonetas[numFurgoneta].getindexEstacioE()].restaNBicisAg(n);
		Integer bienP2v2=bicisBienColocadasIndexE(iP);
		Integer malEv2=bicisMalColocadasIndexE(iE);
		
		BicisBienColocadas=BicisBienColocadas-bienP2+bienP2v2;
		BicisMalColocadas=BicisMalColocadas-malE+malEv2;
		
	}
	public void decrementarNNENP1(Integer n, Integer numFurgoneta){
		//System.out.println("  decrementarNNENP1");
		
		Integer iP=vFurgonetas[numFurgoneta].getindexEstacioP1();
		Integer iE=vFurgonetas[numFurgoneta].getindexEstacioE();
		
		Integer bienP1=bicisBienColocadasIndexE(iP);
		Integer malE=bicisMalColocadasIndexE(iE);
		
		vFurgonetas[numFurgoneta].setNp1(vFurgonetas[numFurgoneta].getNp1()-n);
		vEstaciones[iP].restaNBicis(n);
		vEstaciones[vFurgonetas[numFurgoneta].getindexEstacioE()].restaNBicisAg(n);
		
		Integer bienP1v2=bicisBienColocadasIndexE(iP);
		Integer malEv2=bicisMalColocadasIndexE(iE);
		
		BicisBienColocadas=BicisBienColocadas-bienP1+bienP1v2;
		BicisMalColocadas=BicisMalColocadas-malE+malEv2;
		
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
	/**
	 * PRE: EstacioP1 t� una o menys estacions assignades
	 * @param numFurgoneta
	 * @param EstacioP1
	 */
	public void canviarP1(Integer numFurgoneta, Integer EstacioP1){
		
		Integer P=vFurgonetas[numFurgoneta].getindexEstacioP1();
		Integer nP1=vFurgonetas[numFurgoneta].getNp1();
		
		Integer bienP1=bicisBienColocadasIndexE(P);
		Integer bienEP1=bicisBienColocadasIndexE(EstacioP1);
		
		
		vEstaciones[EstacioP1].sumaNBicis(nP1);
		vEstaciones[P].restaNBicis(nP1);
		// NO CANVIO LA FURGONETA PERQUE ES P1
		vFurgonetas[numFurgoneta].setindexEstacioP1(EstacioP1);
		
		
		
		Integer bienP1v2=bicisBienColocadasIndexE(P);
		Integer bienEP1v2=bicisBienColocadasIndexE(EstacioP1);
		
		BicisBienColocadas= BicisBienColocadas-bienP1-bienEP1+bienP1v2+bienEP1v2;
		
	}
	public void canviarP2(Integer numFurgoneta, Integer EstacioP2){
		Integer P2=vFurgonetas[numFurgoneta].getindexEstacioP2();
		Integer nP2=vFurgonetas[numFurgoneta].getNp2();
		
		Integer bienP2=bicisBienColocadasIndexE(P2);
		Integer bienEP2=bicisBienColocadasIndexE(EstacioP2);
		
		
		vEstaciones[EstacioP2].sumaNBicis(nP2);
		vEstaciones[P2].restaNBicis(nP2);
		// NO CANVIO LA FURGONETA PERQUE ES P2
		vFurgonetas[numFurgoneta].setindexEstacioP2(EstacioP2);
		
		
		
		Integer bienP2v2=bicisBienColocadasIndexE(P2);
		Integer bienEP2v2=bicisBienColocadasIndexE(EstacioP2);
		
		BicisBienColocadas= BicisBienColocadas-bienP2-bienEP2+bienP2v2+bienEP2v2;
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
				vEstaciones[vFurgonetas[numFurgoneta].getindexEstacioP1()].setBicisColocades(0);
				vEstaciones[vFurgonetas[numFurgoneta].getindexEstacioP2()].setBicisColocades(0);
			}
			else{
				int diff=(vFurgonetas[numFurgoneta].getNp1()+vFurgonetas[numFurgoneta].getNp2())% (vFurgonetas[numFurgoneta].getEstacioE().getNumBicicletasNoUsadas()) ;
				int mig=diff/2;
				Integer P1=vFurgonetas[numFurgoneta].getindexEstacioP1();
				Integer P2=vFurgonetas[numFurgoneta].getindexEstacioP2();
				
				
				Integer np1=vFurgonetas[numFurgoneta].getNp1();
				Integer np2=vFurgonetas[numFurgoneta].getNp2();
				vFurgonetas[numFurgoneta].setNp1(mig);
				vFurgonetas[numFurgoneta].setNp2(diff-vFurgonetas[numFurgoneta].getNp1());
				
				
				vEstaciones[P1].setBicisColocades(vEstaciones[P1].getBicisColocades()-np1+mig);		
				vEstaciones[P2].setBicisColocades(vEstaciones[P2].getBicisColocades()-np2+vFurgonetas[numFurgoneta].getNp2());
				vEstaciones[vFurgonetas[numFurgoneta].getindexEstacioE()].setBicisAgafen(diff);
			}
		}
	}
	/**
	public void intercambiarEs(Integer numFurgoneta1, Integer numFurgoneta2){
		//System.out.println("  NOOOO");
		Integer EF1=vFurgonetas[numFurgoneta1].getindexEstacioE();
		Integer EF2=vFurgonetas[numFurgoneta2].getindexEstacioE();
		
		Integer bicisMalEF1bef=0;
		Integer bicisMalEF2bef=0;
		Integer bicisMalEF1now=0;
		Integer bicisMalEF2now=0;
		Integer neEF1=vFurgonetas[numFurgoneta1].getNp1()+vFurgonetas[numFurgoneta1].getNp2();
		Integer neEF2=vFurgonetas[numFurgoneta2].getNp1()+vFurgonetas[numFurgoneta2].getNp2();
		vFurgonetas[numFurgoneta1].setindexEstacioE(EF2);
		vFurgonetas[numFurgoneta2].setindexEstacioE(EF1);
		if(EF1 != -1){
			vEstaciones[EF1].setFurg(numFurgoneta2);
			bicisMalEF1bef=bicisMalColocadas(neEF1,EF1.getDemanda()-EF1.getNumBicicletasNext());
			
			ajustarNumero(numFurgoneta2);
			bicisMalEF1now=bicisMalColocadas(vFurgonetas[numFurgoneta2].getNp1()+vFurgonetas[numFurgoneta2].getNp2(),EF1.getDemanda()-EF1.getNumBicicletasNext());
		}
		if(EF2 != -1){
			int index2=es.indexOf(EF2);
			vEstaciones[index2].setFurg(numFurgoneta1);
			bicisMalEF2bef=bicisMalColocadas(neEF2,EF2.getDemanda()-EF2.getNumBicicletasNext());
			
			ajustarNumero(numFurgoneta1);
			bicisMalEF2now=bicisMalColocadas(vFurgonetas[numFurgoneta1].getNp1()+vFurgonetas[numFurgoneta1].getNp2(),EF2.getDemanda()-EF2.getNumBicicletasNext());
		}
		BicisMalColocadas= BicisMalColocadas+bicisMalEF2now+bicisMalEF1now-bicisMalEF1bef-bicisMalEF2bef;
	}
	**/
	
	private void intercambiarprivate(Estacion numEstacion1,Estacion numEstacion2, Integer index1,Integer index2){

		//System.out.println("  NOOOO");
		int fnum1=vEstaciones[index1].getFurg();
		int fnum2=vEstaciones[index2].getFurg();
		
		Integer male1=bicisMalColocadasIndexE(index1);
		Integer male2=bicisMalColocadasIndexE(index2);
		
		
		Integer f1ag=vEstaciones[index1].getBicisAgafen();
		Integer f2ag=vEstaciones[index2].getBicisAgafen();
		
		vEstaciones[index1].setFurg(fnum2);
		vEstaciones[index2].setFurg(fnum1);
		vEstaciones[index1].setBicisAgafen(f2ag);
		vEstaciones[index2].setBicisAgafen(f1ag);
		
		if(fnum1 != -1){
			vFurgonetas[fnum1].setEstacioE(numEstacion2);
			ajustarNumero(fnum1);
		}
		if(fnum2 != -1){
			vFurgonetas[fnum2].setEstacioE(numEstacion1);
			ajustarNumero(fnum2);
		}
		

		
		Integer male1v2=bicisMalColocadasIndexE(index1);
		Integer male2v2=bicisMalColocadasIndexE(index2);
		
		BicisMalColocadas= BicisMalColocadas-male1-male2+male1v2+male2v2;
		
	}
	/**
	 * Como m�nimo una de las dos estaciones tiene de tener una Furgoneta assignada
	 * @param numEstacion1
	 * @param numEstacion2
	 */
	public void intercambiarE(Estacion numEstacion1, Estacion numEstacion2){
		Estaciones es=GeneraProblema.getEstaciones();
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
		Estacion e1=GeneraProblema.getEstacion(numEstacion1);
		Estacion e2=GeneraProblema.getEstacion(numEstacion2);
		intercambiarprivate(e1,e2,numEstacion1,numEstacion2);
	}
	
	private static int minim(int a,int b){
		
		if(a<b)return a;
		else return b;
		
	}
	
	
	/**
	 * 
	 * @param numF
	 * @param numE
	 * @param v--> [1-4]
	 * @param v2 -->[1-2]
	 * @param v3 -->[1-2]
	 * @return
	 */
	private static Estado estadoinicial(int numF,int numE, int v,int v2,int v3){
		
		int min= minim(numF,numE);
		
		Integer BSuman=0;
		Integer Brestan=0;
		Double g=0.0;
		Furgoneta[] vfurg= new Furgoneta[numF];
		Struct[] vEst= new Struct[numE];
		Random rnd = new Random(GeneraProblema.getSemilla());
		
		Estaciones est=GeneraProblema.getEstaciones();
		java.util.ArrayList<Integer> ve = new java.util.ArrayList<Integer>();
		java.util.ArrayList<Integer> vp = new java.util.ArrayList<Integer>();
		
		
		//System.out.println("Jose");
		for(int i=0; i < numE; ++i)
		{
			vEst[i]= new Struct();
			if(v2==1)ve.add(i);
			vp.add(i);
			vp.add(i);
		}
		auxiclass[] nova= new auxiclass[numE];
		if(v2==2){
			for(int i=0; i<numE;++i){
				nova[i].setBicisnousades(est.get(i).getNumBicicletasNoUsadas());
				nova[i].setIndexestacions(i);
			}
			Arrays.sort(nova);
		}
		for(int i=0;i<min;++i){
			int re,rp1,rp2;
			if(v2==2){
				re= nova[i].getIndexestacions();
			}
			else{

				int te = rnd.nextInt(ve.size()-1);
				//System.out.println("Vector");
				re = ve.get(te);
				
				ve.remove(te);
			}
			int tp1=rnd.nextInt(vp.size()-1);
			rp1 = vp.get(tp1);
					
			if(v3==2){
				while(rp1==re){
					tp1=rnd.nextInt(vp.size()-1);
					rp1 = vp.get(tp1);
				}
			}
			vp.remove(tp1);
				
				
			int tp2=rnd.nextInt(vp.size()-1);
			rp2 = vp.get(tp2);
					
			if(v3==2){
				while(rp2==re|rp2==rp1){
					tp2=rnd.nextInt(vp.size()-1);
					rp2 = vp.get(tp2);
				}
			}
			vp.remove(tp2);
	

			Estacion e=GeneraProblema.getEstacion(re);
			Estacion p1=GeneraProblema.getEstacion(rp1);
			Estacion p2=GeneraProblema.getEstacion(rp2);
			
			
			

			//Double n= 
			Double n;
			if(v==1)n= 0.6*e.getNumBicicletasNoUsadas();
			else if(v==2) n=0.6*e.getNumBicicletasNoUsadas();
			else if(v==3) {
				Double auxi=rnd.nextDouble();
				if(auxi<0.0)auxi=auxi*(-1);
				n= auxi*e.getNumBicicletasNext();
			}
			else{
				Double auxi=rnd.nextDouble();
				if(auxi<0.0)auxi=auxi*(-1);
				n= auxi*e.getNumBicicletasNoUsadas();
			}
			
			
			//System.out.println(n);
			Integer ne=n.intValue();
			ne=minim(30,ne);
			//System.out.println(ne);
			int np1=ne/2;
			//System.out.println(np1);
			int np2=ne-np1;
			//System.out.println(np2);
			
			
			
			
			vEst[rp1].sumaNBicis(np1);
			vEst[rp2].sumaNBicis(np2);
			
			vEst[re].setFurg(i);
			vEst[re].setBicisAgafen(ne);
			Furgoneta aux=new Furgoneta(re,rp1,rp2,np1,np2);
			
			vfurg[i]=aux;
			
			
			
			//System.out.println("Vector9");
			Integer km1= GeneraProblema.distancia(e,p1);
			Integer km2= GeneraProblema.distancia(p1,p2);
			//System.out.println(km1);
			//System.out.println(km2);
			//System.out.println("Vector9.5");
			Integer d1=(ne+9)/10;
			Integer d2=(np2+9)/10;
			//System.out.println("Vector9.6");
			Double gau=(double) d1*km1+d2*(km1+km2);
			//System.out.println("Vector9.7");
			
			//System.out.println("Vector9.8");
			g=g+gau;
			//System.out.println("Vector10");
			
		}
		for (int i=min;i<numF;++i){
			vfurg[i]=new Furgoneta();
		}
		
		for(int i=0; i<numE;++i){
			
			BSuman=BSuman+bicisBienColocadasIndexE(i,vEst);
		}
		for(int i=0; i<numF;++i){
			int re=vfurg[i].getindexEstacioE();
			Brestan =Brestan+bicisMalColocadasIndexE(re,vEst);
		}
		

		
		//System.out.println("restan");
		//System.out.println(Brestan);
		

		return new Estado(vfurg,vEst,g,BSuman,Brestan);
		
	}
	
	
	public static Estado estadoInicial_v1(int numF, int numE){
		return estadoinicial(numF,numE,1,1,1);

	}
	
	public static Estado estadoInicial_v2(int numF, int numE){
		return estadoinicial(numF,numE,2,1,1);

	}
	
	public static Estado estadoInicial_v3(int numF, int numE){
		return estadoinicial(numF,numE,3,1,1);

	}
	
	public static Estado estadoInicial_v4(int numF, int numE){
		return estadoinicial(numF,numE,4,1,1);

	}

	public static Estado estadoInicial_v5(int numF, int numE){
		return estadoinicial(numF,numE,1,2,1);

	}
	
	public static Estado estadoInicial_v6(int numF, int numE){
		return estadoinicial(numF,numE,2,2,1);

	}
	
	public static Estado estadoInicial_v7(int numF, int numE){
		return estadoinicial(numF,numE,3,2,1);

	}
	
	public static Estado estadoInicial_v8(int numF, int numE){
		return estadoinicial(numF,numE,4,2,1);

	}
	
	
	
	public static Estado estadoInicial_v9(int numF, int numE){
		return estadoinicial(numF,numE,1,1,2);

	}
	
	public static Estado estadoInicial_v10(int numF, int numE){
		return estadoinicial(numF,numE,2,1,2);

	}
	
	public static Estado estadoInicial_v11(int numF, int numE){
		return estadoinicial(numF,numE,3,1,2);

	}
	
	public static Estado estadoInicial_v12(int numF, int numE){
		return estadoinicial(numF,numE,4,1,2);

	}

	public static Estado estadoInicial_v13(int numF, int numE){
		return estadoinicial(numF,numE,1,2,2);

	}
	
	public static Estado estadoInicial_v14(int numF, int numE){
		return estadoinicial(numF,numE,2,2,2);

	}
	
	public static Estado estadoInicial_v15(int numF, int numE){
		return estadoinicial(numF,numE,3,2,2);

	}
	
	public static Estado estadoInicial_v16(int numF, int numE){
		return estadoinicial(numF,numE,4,2,2);

	}
	
	
/**
	public static Estado estadoInicial_v2(int numF, int numE){
		
		int min= minim(numF,numE);
		
		Integer BSuman=0;
		Integer Brestan=0;
		Double g=0.0;
		Furgoneta[] vfurg= new Furgoneta[numF];
		Struct[] vEst= new Struct[numE];
		Random rnd= new Random();
		Estaciones est=GeneraProblema.getEstaciones();
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
			Furgoneta aux=new Furgoneta(re,rp1,rp2,np1,np2);
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
			Integer km1= GeneraProblema.distancia(e,p1);
			Integer km2= GeneraProblema.distancia(p1,p2);
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




**/

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
/**
public static Estado estadoInicial_v3(int numF, int numE){
		
		int min= minim(numF,numE);
		
		Integer BSuman=0;
		Integer Brestan=0;
		Double g=0.0;
		Furgoneta[] vfurg= new Furgoneta[numF];
		Struct[] vEst= new Struct[numE];
		Random rnd= new Random();
		Estaciones est=GeneraProblema.getEstaciones();
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
			Furgoneta aux=new Furgoneta(re,rp1,rp2,np1,np2);
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
			Integer km1= GeneraProblema.distancia(e,p1);
			Integer km2= GeneraProblema.distancia(p1,p2);
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

	**/
	
	public void print() {
		
		System.out.println("Furgonetas:");
		for (Furgoneta f : vFurgonetas) {
			Integer E = f.getindexEstacioE(), P1 = f.getindexEstacioP1(), P2 = f.getindexEstacioP2(); 
			int np1 = f.getNp1(), np2 = f.getNp2();
			Estaciones es=GeneraProblema.getEstaciones();
			System.out.println("Inicial:" + E + " " + (np1+np2) + " P1: " + P1+ " " + np1 + " P2: " + P2+ " " + np2);
		}
		System.out.println("");
		
	}
	
	
}
