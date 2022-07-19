
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TSPSolver {
	final static int POPULATION_CAPACITY = 50;
	final static int GENERATIONS = 1000;
	final static int ELITE = 2;
	final static int BINARY_TOURNAMENT_SELECTION = 2;
	
	public static int numberOfcities;
	
	public static void solve() {
		List<City[]> initPopulation = generatePopulation(generateInitialRoute());
		int generation = 0;
		while (generation < TSPSolver.GENERATIONS) {

			List<City[]> newPopulation = new LinkedList<City[]>();

			List<City[]> tempPopulation = new LinkedList<City[]>();
			tempPopulation = initPopulation;
			
			/*
			 * System.out.print("Init:"); System.out.println();
			 * City.printPopulation(initPopulation);
			 * System.out.print(shortestPath(initPopulation)); System.out.println();
			 */
			
			newPopulation.addAll(TSPSolver.getElite(tempPopulation));

			/*
			 * System.out.print("Elite:"); System.out.println();
			 * City.printPopulation(newPopulation);
			 */
			
			while (newPopulation.size() < TSPSolver.POPULATION_CAPACITY) {
				City[] parent1 = tournamentSelection(initPopulation);
				City[] parent2 = tournamentSelection(initPopulation);
				while (parent2 == parent1) {
					parent2 = tournamentSelection(initPopulation);
				}
				
				/*
				 * System.out.print("parent1:"); System.out.println();
				 * 
				 * City.printRoute(parent1); System.out.println();
				 * 
				 * System.out.print("parent2:"); System.out.println();
				 * 
				 * City.printRoute(parent2); System.out.println();
				 */
				
				Random randomNumber = new Random();
				int index = randomNumber.nextInt(TSPSolver.numberOfcities) + 1;

				/*
				 * System.out.print(index); System.out.println();
				 */
				
				City[] child1 = crossoverFirst(parent1, parent2, index);

				City[] child2 = crossoverSecond(parent1, parent2, index);

				/*
				 * System.out.print("child1Topop:"); System.out.println();
				 * 
				 * City.printRoute(child1); System.out.println();
				 * 
				 * System.out.print("child2Topop:"); System.out.println();
				 * 
				 * City.printRoute(child2); System.out.println();
				 */
				
				child1 = mutate(child1);
				child2 = mutate(child2);
				
				while (TSPSolver.isRouteInPopulation(child1, newPopulation)) {
					child1 = crossoverFirst(parent1, parent2, index);
					child1 = mutate(child1);
				}
				
				newPopulation.add(child1);
				
				if (newPopulation.size() < TSPSolver.POPULATION_CAPACITY) {
					
					while (TSPSolver.isRouteInPopulation(child2, newPopulation)) {
						child2 = crossoverSecond(parent1, parent2, index);
						child2 = mutate(child2);
					}
					
					newPopulation.add(child2);
				}
				
				/*
				 * System.out.print("child1Topop:"); System.out.println();
				 * 
				 * City.printRoute(child1); System.out.println();
				 * 
				 * System.out.print("child2Topop:"); System.out.println();
				 * 
				 * City.printRoute(child2); System.out.println();
				 */

			}
			generation++;
			
			  if (generation == 1 || generation == 10 || generation == 70 || generation == 400 || generation == 500 || generation == 1000) {
			  
			  System.out.print(shortestPath(newPopulation)); 
			  System.out.println();
			  System.out.print(generation); 
			  System.out.println();
			  System.out.print("--------------------------------------------");
			  System.out.println(); 
			  }
			 
			
			/*
			 * City.printPopulation(newPopulation);
			 * System.out.print(shortestPath(newPopulation)); System.out.println();
			 * System.out.print(generation); System.out.println();
			 * System.out.print("--------------------------------------------");
			 * System.out.println();
			 */
			 
			
			/*
			 * City.printPopulation(newPopulation);
			 * System.out.print(shortestPath(newPopulation)); System.out.println();
			 */
			 
			initPopulation = newPopulation;
		}
	}
	
	private static double shortestPath(List<City[]> population) {
		double minPath = Double.MAX_VALUE;
		
		for (City[] route : population) {
			if (City.calculateTotalDistance(route) < minPath) {
				minPath = City.calculateTotalDistance(route);
			}
		}
		return minPath;
	}
	
	private static City[] mutate(City[] child) {
		Random randomNumber = new Random();
		int chanceToMutate = randomNumber.nextInt(3) + 1;
		
		City[] mutatedChild = new City[TSPSolver.numberOfcities];
		
		for (int i = 0 ; i < child.length; i++) {
			mutatedChild[i] = child[i];
		}
		if (chanceToMutate == 1) {
			int indexToSwap1 = randomNumber.nextInt(TSPSolver.numberOfcities);
			int indexToSwap2 = randomNumber.nextInt(TSPSolver.numberOfcities);
			while (indexToSwap1 == indexToSwap2) {
				indexToSwap2 = randomNumber.nextInt(TSPSolver.numberOfcities);
			}
			City temp = mutatedChild[indexToSwap1];
			mutatedChild[indexToSwap1] = mutatedChild[indexToSwap2];
			mutatedChild[indexToSwap2] = temp;
		}
		
		return mutatedChild;
	}
	
	private static City[] crossoverFirst(City[] parent1, City[] parent2, int index) {
		City[] child = new City[TSPSolver.numberOfcities];
		
		for (int i = 0 ; i < index; i++) {
			child[i] = parent1[i];
		}
		int j = index;
		for (int i = index ; i < TSPSolver.numberOfcities; i++) {
			while (isCityInRoute(child, parent2[j]) == true) {
				j++;
				if (j == parent2.length) {
					j = 0;
				}
			}
			child[i] = parent2[j];
		}
		return child;
	}
	
	private static City[] crossoverSecond(City[] parent1, City[] parent2, int index) {
		City[] child = new City[TSPSolver.numberOfcities];
		
		for (int i = 0 ; i < index; i++) {
			child[i] = parent2[i];
		}
		int j = index;
		for (int i = index ; i < TSPSolver.numberOfcities; i++) {
			while (isCityInRoute(child, parent1[j]) == true) {
				j++;
				if (j == parent1.length) {
					j = 0;
				}
			}
			child[i] = parent1[j];
		}
		return child;
	}
	
	private static boolean isCityInRoute(City[] route, City city) {
		for (int i = 0; i < route.length; i++) {
			if (route[i] == city) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean isRouteInPopulation(City[] route, List<City[]> population) {
		for (City[] r : population) {
			if (r == route) {
				return true;
			}
		}
		return false;
	}
	
	private static City[] tournamentSelection(List<City[]> population) {
        City[] betterRoute = null;

        Random randomNumber = new Random();
        
        for(int i = 0; i < BINARY_TOURNAMENT_SELECTION; i++) {
        	int index = randomNumber.nextInt(population.size());
            City[] current = population.get(index);
            if(betterRoute == null || City.getFitness(current) > City.getFitness(betterRoute)) {
                betterRoute = current;
            }
        }
        return betterRoute;
    }
	
	private static List<City[]> getEliteOneByOne(List<City[]> population) {
		
		double MaxFitness = Double.MIN_VALUE ; 
		List<City[]> objects = new LinkedList<City[]>();
		
		for (City[] route : population) {
			if (City.getFitness(route) > MaxFitness) {
				MaxFitness = City.getFitness(route);
				objects.clear();
				objects.add(route);
			} else if (City.getFitness(route) == MaxFitness) {
				objects.add(route);
			}
		}
		
		return objects;
	}
	
	private static List<City[]> getElite(List<City[]> population) {
		int elite = TSPSolver.ELITE;
		List<City[]> setOfElite = new LinkedList<City[]>();
		List<City[]> current = new LinkedList<City[]>();
		while (setOfElite.size() < elite) {
			current = getEliteOneByOne(population);
			
			for(City[] route : current) {
				setOfElite.add(route);
				if (setOfElite.size() == elite) {
					return setOfElite;
				}
			}
			
			for(City[] route : current) {
				population.remove(route);
			}
 		}
		return setOfElite;
		
	}
	
	
	private static City[] generateInitialRoute() {
		City[] initialRoute = new City[TSPSolver.numberOfcities];
		for(int i = 0; i < TSPSolver.numberOfcities; i++) {
			City city = new City();
			if (isCityInRoute(initialRoute, city)) {
				initialRoute[i] = new City();
			} else {
				initialRoute[i] = city;
			}
		}
		return initialRoute;
	}
	
	private static List<City[]> generatePopulation(City[] initialRoute) {
		List<City[]> population = new LinkedList<City[]>();

		City[] nextRoute = new City[TSPSolver.numberOfcities];
		for (int i = 0; i < TSPSolver.numberOfcities; i++) {
			nextRoute[i] = new City(initialRoute[i].getX(), initialRoute[i].getY());
		}
		for (int count = 0; count < TSPSolver.POPULATION_CAPACITY; count++) {
			
			Random randomNumber = new Random();
			for(int i = nextRoute.length - 1; i > 0; i--) {
				int indexToSwap = randomNumber.nextInt(i + 1);
				City temp = nextRoute[indexToSwap];
				nextRoute[indexToSwap] = nextRoute[i];
				nextRoute[i] = temp;
			}
			City[] next = new City[TSPSolver.numberOfcities];
			for (int i = 0; i < nextRoute.length; i++) {
				next[i] = nextRoute[i];
			}
			if(population.contains(next)) {
				count --;
			}
			else {
				population.add(next);
			}
			
		}

		return population;
	}
}