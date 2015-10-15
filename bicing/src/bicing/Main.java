package bicing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;
import java.util.Iterator;
import java.util.Properties;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

public class Main {
	
	public static void main(String[] args) {
		Path path = Paths.get(System.getProperty("user.dir"), args[0]);
	    Charset charset = Charset.forName("ISO-8859-1");
		try {
			List<String> lines = Files.readAllLines(path, charset);
			String alg = getString(lines.get(9));
			if (alg == "HC") HC(lines);
			else SA(lines);
			
			int nbicis = getNum(lines.get(4));
			int nest = getNum(lines.get(5));
			int nFurg = getNum(lines.get(6));
			String dem = getString(lines.get(7)); //AIXO NO HO TINC CLAR

		      for (String line : lines) {
		    	  int n = getNum(line);
		    	  System.out.println(String.valueOf(n));
		      }
		    } catch (IOException e) {
		      System.out.println(e);
		    }		
	}
	
	private static void HC(List<String> lines) {
		System.out.println("HillClimbing\n");
		//Leemos datos necesarios para HC
		int numB = getNum(lines.get(4));
		int numE = getNum(lines.get(5));
		int numF = getNum(lines.get(6));
		String dem = getString(lines.get(7)); //AIXO NO HO TINC CLAR
		
		Estado estado = Estado.estadoInicial(numF, numE);
		SuccessorsHC succ = new SuccessorsHC();
		EstadoFinal ef = new EstadoFinal();
		int numh = getNum(lines.get(22));
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
		SearchAgent agent = new SearchAgent(problem, search);
	}
	
	private static void SA(List<String> lines) {
		System.out.println("Simulated Annealing\n");
		Estado estado = new Estado();
		estado = estado.estadoInicial(numF, nest);
		SuccessorsSA sa= new SuccessorsSA();
		EstadoFinal ef = new EstadoFinal();
		FuncionHeuristica1 h1 = new FuncionHeuristica1();
		Problem problem = new Problem(estado,sa, ef, h1);
		Search search = new SimulatedAnnealingSearch(itMAX, it, k, lam);
		Searchagent agent = new SearchAgent(problem, search);
	}
	
	
	private static int getNum(String s) {
		int i = s.indexOf(':');
		String num = s.substring(i+2);
		return Integer.parseInt(num);
	}
	
	private static String getString(String s) {
		int i = s.indexOf(':');
		return s.substring(i+2);
	}
	/*
	public static void main(String args[]) {
		int nest = s.nextInt();
		int nbic = s.nextInt();
		int dem = s.nextInt();
		int seed = s.nextInt();
		int numF = s.nextInt();
		GeneraProblema a = new GeneraProblema();
		a.CrearProblema(nest, nbic, dem, seed);
		s.close();
	}
	*/
	
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
