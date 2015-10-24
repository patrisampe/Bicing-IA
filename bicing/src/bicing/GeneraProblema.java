package bicing;
import java.util.HashMap;
import java.util.Random;
import java.lang.Math;
import java.util.Map;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;
public class GeneraProblema {

	private static Estaciones es;
	private static Map<DosEst,Integer> dist;
	private static Random r;
	private static int semilla;
	private static int ramification; 
	private static int realramification; 
	
	public GeneraProblema() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static Integer calcDistancia(Estacion A, Estacion B){
		Integer x=Math.abs(A.getCoordX()-B.getCoordX());
		Integer y=Math.abs(A.getCoordY()-B.getCoordY());
		return x+y;
	}
	
	private static void calculardist(){
		
		for(int i=0; i<es.size();++i){
			for(int j=i;j<es.size();++j){
					Estacion a=es.get(i);
					Estacion b=es.get(j);
					DosEst aux = new DosEst(a,b);
					DosEst auxi = new DosEst(b,a);
					Integer d=calcDistancia(a,b);
					dist.put(aux,d);
					dist.put(auxi,d);
			}
		}
		
	}
	
	public static DosEst estmesaprop(Integer e){
		
		Estacion ed=es.get(e);
		Estacion p1=null;
		Estacion p2=null;
		Integer a=-2;
		Integer b=-2;
		for(int i=0; i<es.size();++i){
			Estacion eu=es.get(i);
			Integer d=calcDistancia(ed,eu);
			System.out.println(a);
			System.out.println(b);
			System.out.println(d);
			if(d>=a){
				if(a>b){
					System.out.println("dins");
					b=d;
					p2=eu;
				}
				else{
					System.out.println("dins2");
					a=d;
					p1=eu;
				}
			}
			else if(d>=b){
				
				if(b>a){
					System.out.println("disn3");
					a=d;
					p1=eu;
				}
				else{
					System.out.println("disn4");
					b=d;
					p2=eu;
				}
			}
			
		}
		
		System.out.println(a);
		System.out.println(b);

		DosEst aux=new DosEst(p1,p2);
		return aux;
		
	}
	
	public static void CrearProblema(int nest, int nbic,int dem, int seed) {
		es = new Estaciones(nest,nbic,dem,seed);
		r  = new Random(seed);
		semilla = seed;
		dist = new HashMap<DosEst,Integer>();
		calculardist();
		ramification = 0;
	}
	
	public static Estaciones getEstaciones() {
		return es;
	}
	
	public static Estacion getEstacion(Integer index){
		return  es.get(index);
	}
	
	public static Integer getIndex(Estacion esta){
		return  es.indexOf(esta);
	}
	
	public static Integer distancia(Estacion a, Estacion b){
		DosEst aux = new DosEst(a,b);
		return dist.get(aux);
	}

	public static int getSemilla() {
		return semilla;
	}
	
	public static Random getRandom() {
		return r;
	}

	public static int getRamification() {
		return ramification;
	}

	
	public static void incRamification(int ramification) {
		GeneraProblema.ramification += ramification;
	}
	public static int getRealRamification() {
		return realramification;
	}

	
	public static void incRealRamification(int ramification) {
		GeneraProblema.realramification += ramification;
	}
}
