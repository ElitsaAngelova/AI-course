import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NeuralNetwork {
	
	private static final double LEARNING_RATE = 0.1;
	private static final int EPOCH = 100000;
	
	private final double input[][] = { { 1, 1 }, { 1, 0 }, { 0, 1 }, { 0, 0 } };
	private double resultOutputs[][] = { { -1 }, { -1 }, { -1 }, { -1 } };
	private double output[];
	
	//AND
	private final static double expectedOutputsAND[][] = { { 1 }, { 0 }, { 0 }, { 0 } };
	//OR
	private final static double expectedOutputsOR[][] = { { 1 }, { 1 }, { 1 }, { 0 } };
	//XOR
	private final static double expectedOutputsXOR[][] = { { 0 }, { 1 }, { 1 }, { 0 } };
	
	private final List<Neuron> inputLayer = new ArrayList<Neuron>();
	private final List<Neuron> hiddenLayer = new ArrayList<Neuron>();
	private final List<Neuron> outputLayer = new ArrayList<Neuron>();
	int[] layers;
	
	
	public NeuralNetwork(int inputsCount, int hiddenCount, int outputCount) {
		this.layers = new int[] {inputsCount, hiddenCount, outputCount};
		
		for (int i = 0; i < layers.length; i++) {
			if (i == 0) {
				for (int j = 0; j < layers[i]; j++) {
					Neuron n = new Neuron();				
					inputLayer.add(n);
				}
			} else if (i == 1) {
				for (int j = 0; j < layers[i]; j++) {
					Neuron n = new Neuron();
					n.createWeights(inputLayer);
					hiddenLayer.add(n);
				}
			}

			else if (i == 2) {
				for (int j = 0; j < layers[i]; j++) {
					Neuron n = new Neuron();
					n.createWeights(hiddenLayer);
					outputLayer.add(n);
				}
			}
		}

		for (Neuron n : hiddenLayer) {
			List<Weight> weights = n.getWeights();
			for (Weight w : weights) {
				double newWeight = Math.random() - 0.5;
				w.setWeightValue(newWeight);
			}
		}

		for (Neuron n : outputLayer) {
			List<Weight> weights = n.getWeights();
			for (Weight w : weights) {
				double newWeight = Math.random() - 0.5;
				w.setWeightValue(newWeight);
			}
		}
        Neuron.neuronIds = 0;
        Weight.weightIds = 0;
        
        
	}
	
	public void setInput(double inputs[]) {
        for (int i = 0; i < inputLayer.size(); i++) {
            inputLayer.get(i).setOutput(inputs[i]);
        }
    }
 
    public double[] getOutput() {
        double[] outputs = new double[outputLayer.size()];
        for (int i = 0; i < outputLayer.size(); i++)
            outputs[i] = outputLayer.get(i).getOutput();
        return outputs;
    }
    
    public void backpropagation(double expectedOutputs[]) {
		int i = 0;
		for (Neuron n : outputLayer) {
			List<Weight> weights = n.getWeights();
			for (Weight w : weights) {
				double givenOutput = n.getOutput();
				double previousOutput = w.getLeft().getOutput();
				double desiredOutput = expectedOutputs[i];
				
				double error = givenOutput * (1 - givenOutput) * (desiredOutput - givenOutput);

				double newWeight = w.getWeightValue() + LEARNING_RATE * error * previousOutput;
				
				w.setWeightValue(newWeight);
				
			}
			i++;
		}
		for (Neuron n : hiddenLayer) {
			List<Weight> weights = n.getWeights();
			for (Weight w : weights) {
				double givenOutput = n.getOutput();
				double previousOutput = w.getLeft().getOutput();
				double sumOutputs = 0;
				int j = 0;
				for (Neuron out_neu : outputLayer) {
					double wjk = out_neu.getWeightFromTable(n.getId()).getWeightValue();
					double desiredOutput = expectedOutputs[j];
					double outGivenOutput = out_neu.getOutput();
					j++;
					double error = outGivenOutput * (1 - outGivenOutput) * (desiredOutput - outGivenOutput);
					sumOutputs = sumOutputs + (error * wjk);
				}

				double hiddenError = givenOutput * (1 - givenOutput) * sumOutputs;
				double newWeight = w.getWeightValue() + LEARNING_RATE * hiddenError * previousOutput;
				w.setWeightValue(newWeight);

			}
		}

    }
    
    public void printResult(double[][] expectedOutputs)
    {
        
        for (int p = 0; p < input.length; p++) {
            System.out.print("INPUTS: ");
            for (int x = 0; x < layers[0]; x++) {
                System.out.print(input[p][x] + " ");
            }
 
            System.out.print("EXPECTED: ");
            for (int x = 0; x < layers[2]; x++) {
                System.out.print(expectedOutputs[p][x] + " ");
            }
 
            System.out.print("ACTUAL: ");
            for (int x = 0; x < layers[2]; x++) {
                System.out.print(resultOutputs[p][x] + " ");
            }
            
            System.out.println();
        }
        System.out.println();
    }
    
    
	public void train(NeuralNetwork network, double[][] expectedOutputs ) {
		
		
		for (int i = 0; i <= EPOCH; i++) {
            for (int p = 0; p < input.length; p++) {
                setInput(input[p]);
 
				for (Neuron n : hiddenLayer) {
					n.calculateOutput();
				}
					
				for (Neuron n : outputLayer) {
					n.calculateOutput();
				}
					
                output = getOutput();
                resultOutputs[p] = output;

 
                backpropagation(expectedOutputs[p]);
            }
            if((i == 0) || (i == 1) || (i == 5000) || (i == 10000) || (i == 15000) || (i == 20000) || (i == 100000) ) {
            	System.out.print("Epoch " + i);
            	System.out.println();
                printResult(expectedOutputs);

            }
        }
		
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Choose operation: ");
		String operation = scan.nextLine();
		NeuralNetwork network = new NeuralNetwork(2, 4, 1);
		double expectedOutputs[][] = { { -1 }, { -1 }, { -1 }, { -1 } };
		if ((operation.equals("and")) || (operation.equals("AND")) || (operation.equals("And")) || (operation.equals("&&"))
				|| (operation.equals("&"))) {
			expectedOutputs = expectedOutputsAND;
		}

		else if ((operation.equals("or")) || (operation.equals("OR")) || (operation.equals("Or")) || (operation.equals("||"))
				|| (operation.equals("|"))) {
			expectedOutputs = expectedOutputsOR;
		}

		else if ((operation.equals("xor")) || (operation.equals("XOR")) || (operation.equals("^"))
				|| (operation.equals("Xor"))) {
			expectedOutputs = expectedOutputsXOR;
		}
		else {
			System.out.print("Wrong input");
			scan.close();
			return;
		}
		network.train(network, expectedOutputs);
		
		scan.close();
	}
}
