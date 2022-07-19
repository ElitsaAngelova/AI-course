
public class Weight {

	private int id;
	public static int weightIds = 0;
	private double weightValue;
	
	private final Neuron left;
	private final Neuron right;

	public Weight(Neuron left, Neuron right) {
		this.left = left;
		this.right = right;
		this.id = weightIds;
		weightIds++;
	}

	public int getId() {
		return id;
	}
	
	public double getWeightValue() {
		return weightValue;
	}

	public void setWeightValue(double weightValue) {
		this.weightValue = weightValue;
	}
	public Neuron getLeft() {
		return left;
	}
	public Neuron getRight() {
		return right;
	}

}
