package lab3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContinuousParametar extends Parametar {
	
	Map<Integer,ArrayList<Double>> examples;

	ContinuousParametar() {
		examples = new HashMap<>();
	}
	
	@Override
	public void putExample(String value, int c) {
		double num = Double.parseDouble(value);
		
		if (!examples.containsKey(c)) {
			ArrayList<Double> exm = new ArrayList<>();
			exm.add(num);
			examples.put(c, exm);
		}
		else {
			ArrayList<Double> exm = examples.get(c);
			exm.add(num);
			examples.put(c, exm);
		}
		
	}
	
	@Override
	public double getConditionalProbability(String value, int c, int count) {

		double num = Double.parseDouble(value);
		ArrayList<Double> list = examples.get(c);
		double mean = getMean(list);
		double variance = getVariance(list,mean);
		
		double firstTerm = 1 / (Math.sqrt(variance * Math.PI * 2));
		double secondTerm = Math.exp((-(Math.pow(num - mean,2)))/ (2 * variance));
		return firstTerm * secondTerm;
	}
	
	private double getMean(ArrayList<Double> list) {
	
		return list.stream().mapToDouble(el -> el.doubleValue())
				.average().orElse(0);
		
	}
	
	private double getVariance(ArrayList<Double> list, double mean) {
		double temp = 0;
		double size = list.stream().count();
		for (Double el : list) {
			temp += ((el - mean) * (el - mean));
		}
		return temp / (size - 1);
	}
	
}
