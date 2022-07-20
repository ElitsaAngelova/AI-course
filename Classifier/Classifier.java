import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Classifier {
	public static final File file = new File("src/resources/house-votes-84.data");
	
	private int totalRepublican = 0;
	private int totalDemocrat = 0;
	private int yesFrequencyForRepublican = 0;
	private int noFrequencyForRepublican = 0;
	private int yesFrequencyForDemocrat = 0;
	private int noFrequencyForDemocrat = 0;
	
	public static List<Double> YesProbabilityIfDemocrat = new ArrayList<Double>();
	public static List<Double> YesProbabilityIfRepublican = new ArrayList<Double>();
	public static List<Double> NoProbabilityIfDemocrat = new ArrayList<Double>();
	public static List<Double> NoProbabilityIfRepublican = new ArrayList<Double>();

	public double probabilityForDemocratIfYes() {
		return (double) yesFrequencyForDemocrat / (double) totalDemocrat;
	}
	
	public double probabilityForDemocratIfNo() {
		return (double) noFrequencyForDemocrat / (double) totalDemocrat;
	}
	
	public double probabilityForRepublicanIfYes() {
		return (double) yesFrequencyForRepublican / (double) totalRepublican;
	}
	
	public double probabilityForRepublicanIfNo() {
		return (double) noFrequencyForRepublican / (double) totalRepublican;
	}
	
	public List<Person> readData() {
		List<Person> allData = new ArrayList<Person>();
		try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
			String line;
			while((line = buffer.readLine()) != null) {
				
				String[] data = line.split(",");
				
				String classname = data[0];
				String[] attributes = new String[16];
				for (int i = 1; i <= 16; i++) {
					attributes[i-1] = data[i];
				}
				allData.add(new Person(classname, attributes));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allData;
	}
	
	public void train(List<Person> training) {
		for (Person p : training) {
			if (p.getClassname().equals("republican")) {
				totalRepublican++;
			}
			if (p.getClassname().equals("democrat")) {
				totalDemocrat++;
			}	
		}
		for (int i = 0; i < 16; i++) {
			for (Person p : training) {
				if (p.getClassname().equals("republican")) {
					if (p.getAttributes()[i].equals("y")) {
						yesFrequencyForRepublican++;
					} else if (p.getAttributes()[i].equals("n")) {
						noFrequencyForRepublican++;
					}
				}	
			}
			YesProbabilityIfRepublican.add(probabilityForRepublicanIfYes());
			NoProbabilityIfRepublican.add(probabilityForRepublicanIfNo());
			
			yesFrequencyForRepublican = 0;
			noFrequencyForRepublican = 0;
			
			for (Person p : training) {
				if (p.getClassname().equals("democrat")) {
					if (p.getAttributes()[i].equals("y")) {
						yesFrequencyForDemocrat++;
					} else if (p.getAttributes()[i].equals("n")) {
						noFrequencyForDemocrat++;
					}
				}
			}
			
			YesProbabilityIfDemocrat.add(probabilityForDemocratIfYes());
			NoProbabilityIfDemocrat.add(probabilityForDemocratIfNo());
			
			yesFrequencyForDemocrat = 0;
			noFrequencyForDemocrat = 0;
		}
	}
	
	public String test(Person testing) {
		double probabilityForDemocrat = 1.0;
		double probabilityForRepublican = 1.0;

		// Democrat
		for (int i = 0; i < 16; i++) {
			if (testing.getAttributes()[i].equals("y")) {
				probabilityForDemocrat = probabilityForDemocrat * YesProbabilityIfDemocrat.get(i);
			} else if (testing.getAttributes()[i].equals("n")) {
				probabilityForDemocrat = probabilityForDemocrat * NoProbabilityIfDemocrat.get(i);
			}
		}
		
		probabilityForDemocrat = probabilityForDemocrat * ( (double) totalDemocrat / (double) (totalDemocrat + totalRepublican));

		// Republican
		for (int i = 0; i < 16; i++) {
			if (testing.getAttributes()[i].equals("y")) {
				probabilityForRepublican = probabilityForRepublican * YesProbabilityIfRepublican.get(i);
			} else if (testing.getAttributes()[i].equals("n")) {
				probabilityForRepublican = probabilityForRepublican * NoProbabilityIfRepublican.get(i);
			}
		}
		
		probabilityForRepublican = probabilityForRepublican * ( (double) totalRepublican / (double) (totalDemocrat + totalRepublican));
		
		if (probabilityForRepublican > probabilityForDemocrat) {
			return "republican";
		} else {
			return "democrat";
		}
	}
	
	public void classify() {
		List<Person> data = new ArrayList<Person>();
		data = readData();
		
		double[] accuracy = new double[10];
		int correct = 0;
		
		int size = data.size() / 10;
		shuffleList(data);

		List<Person> testSet = new ArrayList<Person>();
		List<Person> trainingSet = new ArrayList<Person>();
		
		int begin = 0;
		int end = size;
		
		for (int i = 0 ; i < 10 ; i++) {
			for (int j = begin; j < end; j++) {
				testSet.add(data.get(j));
			}
			trainingSet.addAll(data);
			trainingSet.removeAll(testSet);
			
			begin = end;
			end = end + size;
			
			train(trainingSet);
			
			for (Person p : testSet) {
				if (test(p).equals(p.getClassname())) {
					correct++;
				} 
			}
			
			accuracy[i] = (double) correct / (double) testSet.size();
			int count = i + 1;
			
			System.out.print("Test: " + count + " - " + accuracy[i]);
			System.out.println();
			System.out.print("------------------------------------");
			System.out.println();
			
			testSet.clear();
			trainingSet.clear();
			YesProbabilityIfDemocrat.clear();
			NoProbabilityIfDemocrat.clear();
			NoProbabilityIfRepublican.clear();
			YesProbabilityIfRepublican.clear();
			correct = 0;
			
		}
		
		double sum = 0;
		for (int i = 0; i < 10; i++) {
			sum = sum + accuracy[i];
		}
		System.out.print((double) sum / 10);
		System.out.println();
		
	}

	public void shuffleList(List<Person> a) {
		int n = a.size();
		Random random = new Random();
		random.nextInt();
		for (int i = 0; i < n; i++) {
			int change = i + random.nextInt(n - i);
			swap(a, i, change);
		}
	}

	private void swap(List<Person> a, int i, int change) {
		Person helper = a.get(i);
		a.set(i, a.get(change));
		a.set(change, helper);
	}

}
