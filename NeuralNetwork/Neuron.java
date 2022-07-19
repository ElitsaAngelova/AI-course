import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Neuron {
	
	private int id;
	public static int neuronIds = 0;
	
	private double output;
	private List<Weight> Weights = new ArrayList<Weight>();
	private Map<Integer, Weight> weightsTable = new HashMap<Integer, Weight>();
	
	public Neuron() {
		this.id = neuronIds;
		neuronIds++;
	}

	public int getId() {
		return id;
	}
	
	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}
	
	public List<Weight> getWeights() {
		return Weights;
	}

	public void setWeights(List<Weight> weights) {
		Weights = weights;
	}

	public void createWeights(List<Neuron> inNeurons){
        for(Neuron n: inNeurons){
            Weight w = new Weight(n,this);
            Weights.add(w);
            weightsTable.put(n.getId(), w);
        }
    }
	
	public Weight getWeightFromTable(int index) {
		return weightsTable.get(index);
	}
	
	public void calculateOutput() {
		double sum = 0;
		
		for (Weight w : Weights) {
			sum = sum + (w.getWeightValue() * w.getLeft().getOutput());
		}
		
		setOutput(sigmoidActivationFunction(sum));
	}
	
	public double sigmoidActivationFunction(double x) {
        return 1.0 / (1.0 +  (Math.exp(-x)));
    }
}
