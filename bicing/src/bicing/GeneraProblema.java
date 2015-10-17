package bicing;
import java.util.HashMap;
import java.lang.Math;
import java.util.Map;
import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;
public class GeneraProblema {

	private static Estaciones es;
	private static Map<DosEst,Integer> dist;
	private static int semilla;
	
	
	
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
	
	public static void CrearProblema(int nest, int nbic,int dem, int seed) {
		es = new Estaciones(nest,nbic,dem,seed);
		setSemilla(seed);
		dist = new HashMap<DosEst,Integer>();
		calculardist();
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
	
}
