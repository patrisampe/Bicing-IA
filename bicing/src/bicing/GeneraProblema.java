package bicing;
import java.util.HashMap;
import java.lang.Math;
import java.util.Map;
import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;
public class GeneraProblema {

	private static Estaciones es;
	private static Map<DosEst,Double> dist;
	
	private static Double calcDistancia(Estacion A, Estacion B){
		Double x=Math.pow(A.getCoordX()-B.getCoordX(), 2);
		Double y=Math.pow(A.getCoordY()-B.getCoordY(), 2);
		return Math.sqrt(x+y);
	}
	
	
	private static void calculardist(){
		
		for(int i=0; i<es.size();++i){
			for(int j=i+1;j<es.size();++j){
					Estacion a=es.get(i);
					Estacion b=es.get(j);
					DosEst aux = new DosEst(a,b);
					DosEst auxi = new DosEst(b,a);
					Double d=calcDistancia(a,b);
					dist.put(aux,d);
					dist.put(auxi,d);
			}
		}
	}
	
	public static void CrearProblema(int nest, int nbic,int dem, int seed) {
		es = new Estaciones(nest,nbic,dem,seed);
		dist = new HashMap<DosEst,Double>();
		calculardist();
	}
	
	public static Estaciones getEstaciones() {
		return es;
	}
	
	public static Double distancia(Estacion a, Estacion b){
		DosEst aux = new DosEst(a,b);
		return dist.get(aux);
	}
	
}
