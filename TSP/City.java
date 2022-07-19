import java.util.List;
import java.util.Random;

public class City {
	private int x;
	private int y;
	
	public City() {
		Random randomNumber = new Random();
		//setX(randomNumber.nextInt(101));
		//setY(randomNumber.nextInt(101));
		setX(randomNumber.nextInt(20 - (-20)) + (-20));
		setY(randomNumber.nextInt(20 - (-20)) + (-20));
	}
	
	public City(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public double calculateDistance(City city) {
		double xDis = Math.abs(this.getX() - city.getX());
		double yDis = Math.abs(this.getY() - city.getY());
		double distance = Math.sqrt((xDis * xDis) + (yDis * yDis));
		
		return distance;
		
	}
	
	public static double calculateDistance(City firstCity, City secondCity) {
		double xDis = Math.abs(firstCity.getX() - secondCity.getX());
		double yDis = Math.abs(firstCity.getY() - secondCity.getY());
		double distance = Math.sqrt((xDis * xDis) + (yDis * yDis));
		
		return distance;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public void printCity() {
		System.out.print(" (" + this.getX() + "," + this.getY() + ") ");
	}
	
	public static void printRoute(City[] route) {
		for (City c : route) {
			c.printCity();
		}
	}
	public static void printPopulation (List<City[]> population) {
		for (City[] r : population) {
			printRoute(r);
			System.out.println();
		}
	}
	
	public void swapCity(City firstCity, City secondCity) {
		int tempX = firstCity.getX();
		firstCity.setX(secondCity.getX());
		secondCity.setX(tempX);
		
		int tempY = firstCity.getY();
		firstCity.setY(secondCity.getY());
		secondCity.setY(tempY);
	}
	
	public void swapCity(City city) {
		int tempX = this.getX();
		this.setX(city.getX());
		city.setX(tempX);
		
		int tempY = this.getY();
		this.setY(city.getY());
		city.setY(tempY);
	}
	
	public static double calculateTotalDistance(City[] route) {
		double distance = 0;
		for (int i = 0 ; i < route.length - 1; i++) {
			for (int j = 1; j < route.length; j++) {
				distance += calculateDistance(route[i], route[j]);
			}
		}
		return distance;
	}
	
	public static double getFitness(City[] route) {
		return 1 / City.calculateTotalDistance(route);
	}
}
