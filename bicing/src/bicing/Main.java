package bicing;

import java.util.Scanner;

public class Main {
	
	public static void main() {
		Scanner s = new Scanner(System.in);
		int nest = s.nextInt();
		int nbic = s.nextInt();
		int dem = s.nextInt();
		int seed = s.nextInt();
		new GeneraProblema(nest, nbic, dem, seed);
		s.close();
	}
}
