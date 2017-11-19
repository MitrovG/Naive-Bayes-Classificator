package lab3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiscreteParametar extends Parametar {
	
	Map<String,ArrayList<Double>> examples;

	DiscreteParametar() {
		examples = new HashMap<>();
		
	}
	
	@Override
	public void putExample(String value, int c) {
		if (!examples.containsKey(value)) {
			ArrayList<Double> classes = new ArrayList<>(3);
			for (int i=0; i <3; ++i) {
				classes.add(0.0);
			}
			classes.set(c, 1.0);
			examples.put(value, classes);
		}
		else {
			ArrayList<Double> classes = examples.get(value);
			classes.set(c, classes.get(c) + 1);
			examples.put(value, classes);
		}
	}
	
	@Override
	public double getConditionalProbability(String value, int c, int count) {
		return examples.get(value).get(c) * 1.0 / count;
	}
	
	@Override
	public void laPlaceSmoothing(double d) {
		
		for (String s : examples.keySet()) {
			
			ArrayList<Double> list = examples.get(s);
			
			for (int i=0; i < list.size(); ++i) {
				list.set(i, list.get(i) + d);
		
			}
			examples.put(s, list);
			
		}
	}
	
	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n");
		for (String s : examples.keySet()) {
			
			int i=0;
			for (Double d : examples.get(s)) {
				
				sb.append(String.format("%s ima uslovna verojatnost za klasa %d = %.4f\n", s,i,getConditionalProbability(s, i, 6)));
				++i;
			}
		}
		return sb.toString();
	}
}
