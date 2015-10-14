package bicing;
import java.util.HashMap;
import java.util.Map;
import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;
public class GeneraProblema {

	private static Estaciones es;
	private static Map<DosEst,Double> dist;
	
	/**
	 * Constructor del Problema
	 * @param nest Número de estaciones
	 * @param nbic Número de bicis
	 * @param dem Tipo de demanda EQUILIBRIUM/RUSH_HOUR
	 * @param seed Semilla
	 */
	public GeneraProblema(int nest, int nbic,int dem, int seed) {
		es = new Estaciones(nest,nbic,dem,seed);
		dist = new HashMap<DosEst,Double>();
	}
	
	public static Estaciones getEstaciones() {
		return es;
	}
	
	public static Double distancia(Estacion a, Estacion b){
		DosEst aux = new DosEst(a,b);
		return dist.get(aux);
	}
	
}
