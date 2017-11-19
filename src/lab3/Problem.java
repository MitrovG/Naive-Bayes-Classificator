package lab3;

public class Problem {

	public static void main(String[] args) {
		Classificator classificator = new Classificator();
		
		classificator.classify(4.7, "gimnazisko", 9, "od 1-2");
		classificator.classify(3, "gimnazisko", 9, "od 1-2");
		classificator.classify(3, "strucno", 8, "poveke od 5");
		//System.out.println(classificator);
	}
}
