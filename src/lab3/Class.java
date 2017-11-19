package lab3;

public class Class {
	
	private int examples;
	
	public Class() {
		examples = 0;
	}
	
	public int getExamples() {
		return examples;
	}
	
	public void increment() {
		++examples;
	}
	
	public double getProbability(int count) {
		return examples * 1.0 / count;
	}
}
