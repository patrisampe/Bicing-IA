package bicing;

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
	
	public static void main(String[] args) {
		// args[0] nombre del fichero de entrada
		//Path path = Paths.get(System.getProperty("user.dir"), args[0]);
		Path path = Paths.get(System.getProperty("user.dir"), "file/exemple.txt");
		Charset charset = Charset.forName("ISO-8859-1");
		try {
			List<String> lines = Files.readAllLines(path, charset);
			String alg = getString(lines.get(10));
			if (alg.equals("HC")) HC(lines);
			else SA(lines);
		    } catch (Exception e) {
		      System.out.println(e);
		    }		
	}
	
	private static void HC(List<String> lines) throws Exception {
		System.out.println("HillClimbing\n");
		//Leemos datos necesarios para HC
		int numB = getNum(lines.get(4));
		int numE = getNum(lines.get(5));
		int numF = getNum(lines.get(6));
		int dem = readDemanda(lines.get(7));
		int seed = getNum(lines.get(8));
		GeneraProblema.CrearProblema(numE, numB, dem, seed);
		printEstaciones(numF);
		int estadoIni = getNum(lines.get(26));
		Estado estado = null;
		if (estadoIni == 1) estado = Estado.estadoInicial_v1(numF, numE);
		else if (estadoIni == 2) estado = Estado.estadoInicial_v2(numF, numE);

		calculBestia(estado);
		printEstado(estado, true);
		System.out.println("Distancia total recorrida: "+ calcDistTotal(estado)/(double)1000);
		System.out.println("Gasolina Total: "+ calcGasolinaTotal(estado)/1000);
		System.out.println("Gasolina Total Inc: "+ estado.getCosteGasolina()/1000);
		SuccessorsHC succ = new SuccessorsHC();
		EstadoFinal ef = new EstadoFinal();
		int numh = getNum(lines.get(23));
		Problem problem = null;
		switch (numh) {
			case 1: 
				problem = new Problem(estado, succ, ef, new FuncionHeuristica1()); 
				break;
			case 2: 
				problem = new Problem(estado, succ, ef, new FuncionHeuristica2()); 
				break;
			default: 
				problem = new Problem(estado, succ, ef, new FuncionHeuristica3()); 
				break;			
		}
		Search search = new HillClimbingSearch();
		long startTime = System.currentTimeMillis();
		SearchAgent agent = new SearchAgent(problem, search);
		long endTime = System.currentTimeMillis();
		Estado result = (Estado) search.getGoalState();
		calculBestia(result);
		printEstado(result, false);
		System.out.println("Distancia total recorrida: "+ calcDistTotal(result)/(double)1000);
		System.out.println("Gasolina Total: "+ calcGasolinaTotal(result)/1000);
		System.out.println("Gasolina Total Inc: "+ result.getCosteGasolina()/1000);
		if (getString(lines.get(13)).equals("S")) printActions(agent.getActions());
		System.out.println("Sucesores generados " + GeneraProblema.getRamification());
	}
	
	public static double calcGasolinaTotal(Estado result) {
		double gasolina = 0;
		for (Furgoneta furgo : result.getvFurgonetas()) {
			int distancia1 = GeneraProblema.distancia(furgo.getEstacioE(), furgo.getEstacioP1());
			int distancia2 = GeneraProblema.distancia(furgo.getEstacioP1(), furgo.getEstacioP2());
			gasolina += ((furgo.getNp1()+furgo.getNp2() + 9)/ 10)*distancia1;
			gasolina += ((furgo.getNp2() + 9)/ 10)*(distancia2);
		    
		}
		return gasolina;
	}

	public static int calcDistTotal(Estado result) {
		int distancia = 0;
		for (Furgoneta furgo : result.getvFurgonetas()) {
			distancia += GeneraProblema.distancia(furgo.getEstacioE(), furgo.getEstacioP1()) + GeneraProblema.distancia(furgo.getEstacioP1(), furgo.getEstacioP2());
		}
		return distancia;
	}

	private static void SA(List<String> lines) throws Exception {
		System.out.println("Simulated Annealing\n");
		//Leemos datos necesarios para SA
		int numB = getNum(lines.get(4));
		int numE = getNum(lines.get(5));
		int numF = getNum(lines.get(6));
		int dem = readDemanda(lines.get(7));
		int seed = getNum(lines.get(8));
		int itMAX = getNum(lines.get(17));
		int it = getNum(lines.get(18));
		int k = getNum(lines.get(19));
		double lam = getDouble(lines.get(20));
		
		GeneraProblema.CrearProblema(numE, numB, dem, seed);
		int estadoIni = getNum(lines.get(26));
		Estado estado = null;
		if (estadoIni == 1) estado = Estado.estadoInicial_v1(numF, numE);
		else if (estadoIni == 2) estado = Estado.estadoInicial_v2(numF, numE);

		printEstado(estado, true);
		String alg = getString(lines.get(10));
		EstadoFinal ef = new EstadoFinal();
		int numh = getNum(lines.get(23));
		Problem problem = null;
		if (alg.equals("SA")) {
			SuccessorsSA succ = new SuccessorsSA();
			switch (numh) {
				case 1: 
					problem = new Problem(estado, succ, ef, new FuncionHeuristica1()); 
					break;
				case 2: 
					problem = new Problem(estado, succ, ef, new FuncionHeuristica2()); 
					break;
				default: 
					problem = new Problem(estado, succ, ef, new FuncionHeuristica3()); 
					break;			
			}
		}
		else {
			SuccessorsSA2 succ = new SuccessorsSA2();
			switch (numh) {
				case 1: 
					problem = new Problem(estado, succ, ef, new FuncionHeuristica1()); 
					break;
				case 2: 
					problem = new Problem(estado, succ, ef, new FuncionHeuristica2()); 
					break;
				default: 
					problem = new Problem(estado, succ, ef, new FuncionHeuristica3()); 
					break;			
			}			
		}
		Search search = new SimulatedAnnealingSearch(itMAX, it, k, lam);
		long startTime = System.currentTimeMillis();
		SearchAgent agent = new SearchAgent(problem, search);
		long endTime = System.currentTimeMillis();
		Estado result = (Estado) search.getGoalState();
		printEstado(result, false);
		if (getString(lines.get(13)).equals("S")) printActions(agent.getActions());
		if (getString(lines.get(14)).equals("S")) printInstrumentation(agent.getInstrumentation());
		System.out.println("SA ha tardado " + (endTime - startTime) + " ms");
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
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
}