package bicing;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;
import java.util.Iterator;
import java.util.Properties;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

public class Main {

	public static void main(String[] args) throws Exception {
		// args[0] nombre del fichero de entrada
		//Path path = Paths.get(System.getProperty("user.dir"), args[0]);
		Path path = Paths.get(System.getProperty("user.dir"), "file/exemple.txt");
		Charset charset = Charset.forName("ISO-8859-1");
		List<String> lines = Files.readAllLines(path, charset);
		String alg = getString(lines.get(10));
		HC(lines);
	}
	
	private static void HC(List<String> lines) throws Exception {
		FileWriter fichero1 = new FileWriter("exp4.txt");
		int dem = Estaciones.EQUILIBRIUM;
		int iteraciones = 6;
		int repeticiones = 8;
		int semillas[] = {1234,242359,986,5437,72463,97856,7105,3189,13594,4};
		for (int j = 0; j < semillas.length; ++j) {
			int seed = semillas[j];
			for (int i = 1; i <= iteraciones; ++i) {
				int numB = i*1250;
				int numE = i*25;
				int numF = i*5;
				GeneraProblema.CrearProblema(numE, numB, dem, seed);
				Estado estado = Estado.estadoInicial_v1(numF, numE);
				//System.out.println ("HIII");
				//System.out.println("Distancia total recorrida: "+ calcDistTotal(estado)/(double)1000);
				//System.out.println("Gasolina Total: "+ calcGasolinaTotal(estado)/1000);
				//System.out.println("Gasolina Total Inc: "+ estado.getCosteGasolina()/1000);
				SuccessorsHC succ = new SuccessorsHC();
				//System.out.println("GGGG " );
				EstadoFinal ef = new EstadoFinal();
				//System.out.println("GGGGDD " );
				Problem problem = new Problem(estado, succ, ef, new FuncionHeuristica2());
				double time = 0;
				for (int k = 0; k < repeticiones-i; ++k) {
					//System.out.println("lll" );
					Search search = new HillClimbingSearch();
					//System.out.println("ooooooOOO" );
					long startTime = System.currentTimeMillis();
					//System.out.println("ooooooIIIII" );
					SearchAgent agent = new SearchAgent(problem, search);
					//System.out.println("oooPPPPPPP" );
					long endTime = System.currentTimeMillis();
					//System.out.println("oo754ERGFX" );
					time +=endTime-startTime;
				}
				time /= repeticiones;
				fichero1.write(seed + " " + numE + " " + time/1000 + "\n");
				System.out.println(seed + " " + numE + " " + time/1000 );
			}
		}
		fichero1.close();
	}
	
	public static double calcGasolinaTotal(Estado result) {
		double gasolina = 0;
		for (Furgoneta furgo : result.getvFurgonetas()) {
		if(furgo.getindexEstacioE() != -1 && furgo.getindexEstacioP1() != -1 && furgo.getindexEstacioP2() != -1)
		{
		int distancia1 = GeneraProblema.distancia(furgo.getEstacioE(), furgo.getEstacioP1());
		int distancia2 = GeneraProblema.distancia(furgo.getEstacioP1(), furgo.getEstacioP2());
		gasolina += ((furgo.getNp1()+furgo.getNp2() + 9)/ 10)*distancia1;
		gasolina += ((furgo.getNp2() + 9)/ 10)*(distancia2);
		}
		
		}
		return gasolina;
		}
		
		public static int calcDistTotal(Estado result) {
		int distancia = 0;
		for (Furgoneta furgo : result.getvFurgonetas()) {
		//System.out.println("BUUUUUUUU ");
		if(furgo.getindexEstacioE() != -1 && furgo.getindexEstacioP1() != -1 && furgo.getindexEstacioP2()!= -1) distancia += GeneraProblema.distancia(furgo.getEstacioE(), furgo.getEstacioP1()) + GeneraProblema.distancia(furgo.getEstacioP1(), furgo.getEstacioP2());
		//System.out.println("lulululul");
		}
		//System.out.println("BUUUUUUUU dududu");
		return distancia;
		}	
	//Funcions auxiliars
	
	private static int getNum(String s) {
		int i = s.indexOf(':');
		String num = s.substring(i+2);
		return Integer.parseInt(num);
	}
	
	private static String getString(String s) {
		int i = s.indexOf(':');
		return s.substring(i+2);
	}
	
	private static double getDouble(String s) {
		int i = s.indexOf(':');
		String num = s.substring(i+2);
		return Double.parseDouble(num);
	}
	
	private static int readDemanda(String linia) {
		int dem = Estaciones.RUSH_HOUR;
		String demS = getString(linia);
		if (demS.equals("EQUILIBRIUM")) dem = Estaciones.EQUILIBRIUM;
		return dem;
	}
	
	
	private static void printEstado(Estado result, boolean inicial) {
		if (inicial) System.out.println("Estado inicial");
		else System.out.println("Estado final");
		int bien = result.getBicisBienColocadas();
		int mal = result.getBicisMalColocadas();
		System.out.println("Beneficios: " + (bien-mal));
		result.print();
	}
	
	private static void printEstaciones(int numF) {
		Estaciones Est = GeneraProblema.getEstaciones();
		System.out.println("Estaciones (NoUsadas, Prevision Demanda)");
		int beneficiosMax = 0;
		int faltan = 0;
		int sobran = 0;
		for (int i = 0; i < Est.size(); ++i) {
			Estacion est = Est.get(i);
			int dif = est.getDemanda() - est.getNumBicicletasNext();
			if (dif < 0) {dif = 0; sobran += -dif;}
			if (dif > 0) faltan += dif;
			beneficiosMax += dif;
			System.out.println("Estacion " + i);
			System.out.println(est.getNumBicicletasNoUsadas() + " " +  est.getNumBicicletasNext() + " " + est.getDemanda());
		}
		int aux = numF*30;
		if (aux < beneficiosMax) beneficiosMax = aux;
		System.out.println("Beneficios Maximos: " + beneficiosMax);
		System.out.println("Min(sobran,faltan): " + sobran + " " + faltan);
		System.out.println();
	}
	
	private static int min(int a, int b) {
		if (a < b) return a;
		else return b;
	}
	
	private static void calculBestia(Estado result) {
		Struct[] v = result.getvEstaciones();
		Estaciones Est = GeneraProblema.getEstaciones();
		int beneficios = 0;
		System.out.println("Entren Surten");
		for (int i = 0; i < v.length; ++i) {
			Struct s = v[i];
			Estacion est = Est.get(i);
			int dif = est.getDemanda() - est.getNumBicicletasNext();
			int delta = s.getBicisColocades() - s.getBicisAgafen();
			if (delta < 0) {
				if (dif > 0) beneficios += delta;
				if (dif <= 0){
					if(dif>delta)beneficios += delta - dif;
				}
			}
			else {
				if (dif > 0) beneficios += min(delta, dif);
			}
			System.out.println(i + " -> " +  s.getBicisColocades() + " " +  s.getBicisAgafen());
		}
		System.out.println("Beneficios REALES: " + beneficios);
	}
	
	private static void printInstrumentation(Properties properties) {
	  Iterator keys = properties.keySet().iterator();
	  while (keys.hasNext()) {
	      String key = (String) keys.next();
	      String property = properties.getProperty(key);
	      System.out.println(key + " : " + property);
	  }
	}
	
	private static void printActions(List actions) {
		System.out.println( "ACTIIIIOONSS ");
		for (int i = 0; i < actions.size(); i++) {
			String action = (String) actions.get(i);
			System.out.println(action);
		}
	}
}