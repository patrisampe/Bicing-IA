package bicing;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
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
		int repeticiones = 5;
		int numB = 1250;
		int numE = 25;
		int numF = 5;
		int dem = Estaciones.EQUILIBRIUM;
		int itMAX = 5000;
		int semillas[] = {1234,242359,986,5437,72463,97856,7105,3189,13594,4};
		int it = 10;
		int k = 1;
		double lam = 0.1;
		FileWriter fichero1 = new FileWriter("exp5.txt");
		fichero1.write("Semilla Algoritmo Heuristica Estado Beneficio Distancia Gasolina BeneficioReal Tiempo\n");
		System.out.print("Semilla Algoritmo Heuristica Estado Beneficio Distancia Gasolina BeneficioReal Tiempo\n");
		for (int j = 0; j < semillas.length; ++j) {
			int seed = semillas[j];
			GeneraProblema.CrearProblema(numE, numB, dem, seed);
			Estado estado = Estado.estadoInicial_v1(numF, numE);
			SuccessorsSA2 succSA = new SuccessorsSA2();
			SuccessorsHC succHC = new SuccessorsHC();
			EstadoFinal ef = new EstadoFinal();
			Problem[] problems = {new Problem(estado, succSA, ef, new FuncionHeuristica2()),new Problem(estado, succSA, ef, new FuncionHeuristica3()), new Problem(estado, succHC, ef, new FuncionHeuristica2()),new Problem(estado, succHC, ef, new FuncionHeuristica3())};
			String[] texti = {"SA H2 E1 ", "SA H3 E1 ", "HC H2 E1 ", "HC H3 E1 "};
			for (int i = 0; i < problems.length; ++i) {
				double money = 0;
				double time = 0;
				double gas = 0;
				double dist = 0;
				for (int r = 0; r < repeticiones; ++r) {
					Search search;
					if (i < 2) search= new SimulatedAnnealingSearch(itMAX, it, k, lam);
					else search = new HillClimbingSearch();
					long startTime = System.currentTimeMillis();
					SearchAgent agent = new SearchAgent(problems[i], search);
					long endTime = System.currentTimeMillis();
					Estado result = (Estado) search.getGoalState();
					money += result.getBicisBienColocadas()-result.getBicisMalColocadas();
					time +=endTime-startTime;
					gas += result.getCosteGasolina()/1000;
					dist += calcDistTotal(result)/1000;
				}
				money /= repeticiones;	
				time /= repeticiones;	
				gas /= repeticiones;
				dist /= repeticiones;
				fichero1.write(seed + " " + texti[i] + money + " " + dist + " " + gas + " " + (money - gas) + " " + time + "\n");
				System.out.print(seed + " " + texti[i] + money + " " + dist + " " + gas + " " + (money - gas) + " " + time + "\n");
			}
			estado = Estado.estadoInicial_v2(numF, numE);
			Problem[] problems2 = {new Problem(estado, succSA, ef, new FuncionHeuristica2()),new Problem(estado, succSA, ef, new FuncionHeuristica3()), new Problem(estado, succHC, ef, new FuncionHeuristica2()),new Problem(estado, succHC, ef, new FuncionHeuristica3())};
			String[] texti2 = {"SA H2 E2 ", "SA H3 E2 ", "HC H2 E2 ", "HC H3 E2 "};
			
			for (int i = 0; i < problems2.length; ++i) {
				double money = 0;
				double time = 0;
				double gas = 0;
				double dist = 0;
				for (int r = 0; r < repeticiones; ++r) {
					Search search;
					if (i < 2) search= new SimulatedAnnealingSearch(itMAX, it, k, lam);
					else search = new HillClimbingSearch();
					long startTime = System.currentTimeMillis();
					SearchAgent agent = new SearchAgent(problems2[i], search);
					long endTime = System.currentTimeMillis();
					Estado result = (Estado) search.getGoalState();
					money += result.getBicisBienColocadas()-result.getBicisMalColocadas();
					time +=endTime-startTime;
					gas += result.getCosteGasolina()/1000;
					dist += calcDistTotal(result)/1000;
				}
				money /= repeticiones;	
				time /= repeticiones;	
				gas /= repeticiones;
				dist /= repeticiones;
				fichero1.write(seed + " " + texti2[i] + money + " " + dist + " " + gas + " " + (money - gas) + " " + time + "\n");
				System.out.print(seed + " " + texti2[i] + money + " " + dist + " " + gas + " " + (money - gas) + " " + time + "\n");
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
    	System.out.println( "ACTIIIIOONSS ");
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
}