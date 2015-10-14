package bicing;

import java.util.Scanner;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

public class Main {
	
	private static void HC() {
		System.out.println("TSP HillClimbing\n");
		Estado estado = new Estado();
		estado = estado.estadoInicial(numF, nest);
		SuccessorsHC sh = new SuccessorsHC();
		EstadoFinal ef = new EstadoFinal();
		FuncionHeuristica1 h1 = new FuncionHeuristica1();
		Problem problem = new Problem(estado,sh, ef, h1);
		Search search = new HillClimbingSearch();
		Searchagent agent = new SearchAgent(problem, search);
	}
	
	private static void SA() {
		System.out.println("TSP Simulated Annealing\n");
		Estado estado = new Estado();
		estado = estado.estadoInicial(numF, nest);
		SuccessorsSA sa= new SuccessorsSA();
		EstadoFinal ef = new EstadoFinal();
		FuncionHeuristica1 h1 = new FuncionHeuristica1();
		Problem problem = new Problem(estado,sa, ef, h1);
		Search search = new SimulatedAnnealingSearch(itMAX, it, k, lam);
		Searchagent agent = new SearchAgent(problem, search);
	}
	
	public static void main() {
		Scanner s = new Scanner(System.in);
		int nest = s.nextInt();
		int nbic = s.nextInt();
		int dem = s.nextInt();
		int seed = s.nextInt();
		int numF = s.nextInt();
		GeneraProblema a = new GeneraProblema();
		a.CrearProblema(nest, nbic, dem, seed);
		s.close();
	}
}
