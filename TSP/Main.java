import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		int numberOfCities = scan.nextInt();
		
		TSPSolver.numberOfcities = numberOfCities;
		TSPSolver.solve();
		scan.close();
	}

}
