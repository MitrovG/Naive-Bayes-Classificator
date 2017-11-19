package lab3;

public abstract class Parametar {

	public Parametar() {
		
	}
	
	public abstract double getConditionalProbability(String value, int c ,int count);
	public abstract void putExample(String value, int c);
	public void laPlaceSmoothing(double d) {};
	
	
}
