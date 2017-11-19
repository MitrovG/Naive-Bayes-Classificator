package lab3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Classificator {
	
	public static final int NA_VREME = 0;
	public static final int SO_ZADOCNUVANJE = 1;
	public static final int NE_DIPLOMIRAL = 2;
	public static final int USPEH = 0;
	public static final int SREDNO_OBRAZOVANIE = 1;
	public static final int PROSEK = 2;
	public static final int BROJ_PREDMETI = 3;
	
	private ArrayList<Class> classes;
	private ArrayList<Parametar> parametars;
	private int trainingExamples;
	
	public Classificator() {
		
		trainingExamples = 0;
	
		classes = new ArrayList<>();
		for (int i=0; i<3; ++i)
			classes.add(new Class());
		
		parametars = new ArrayList<>();
		for (int i=0; i<2; ++i) {
			parametars.add(new ContinuousParametar());
			parametars.add(new DiscreteParametar());
		}
		initializeClassificator();
		LaPlaceSmoothing();
	}
	
	private void initializeClassificator() {
		
		try
		(BufferedReader reader = new BufferedReader(new FileReader("./src/lab3/TrainingSet.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				
				String parts[] = line.split(",");
				setExample(parts);
				++trainingExamples;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void setExample (String [] parts) {
		String klasa = parts[0];
		int klas = -1;
		if (klasa.compareTo("na vreme") == 0)
			klas = NA_VREME;
		if (klasa.compareTo("so zadocnuvanje") == 0)
			klas = SO_ZADOCNUVANJE;
		if (klasa.compareTo("ne diplomiral") == 0)
			klas = NE_DIPLOMIRAL;
		for (int i=1; i < parts.length; ++i) {
			parametars.get(i-1).putExample(parts[i], klas);
		}
		classes.get(klas).increment();
	}
	
	private void LaPlaceSmoothing() {
	
		trainingExamples +=3;
		for (Class c : classes)
			c.increment();
		parametars.get(SREDNO_OBRAZOVANIE).laPlaceSmoothing(0.5);
		parametars.get(BROJ_PREDMETI).laPlaceSmoothing(0.25);
	}
	
	public void classify(double uspeh, String obrazovanie, double prosek, String predmeti) {
		
		System.out.print(String.format("Za primerokot (%.2f, %s, %.2f, %s) najverojatno e da pripaga na klasata:\n"
				, uspeh, obrazovanie, prosek, predmeti));
		double posteriorNaVreme = posterior(uspeh,obrazovanie,prosek,predmeti,NA_VREME);
		double posteriorSoZadocnuvanje = posterior(uspeh,obrazovanie,prosek,predmeti,SO_ZADOCNUVANJE);
		double posteriorNeDiplomiral = posterior(uspeh,obrazovanie,prosek,predmeti,NE_DIPLOMIRAL);
		
		//System.out.println(posteriorNaVreme);
		//System.out.println(posteriorSoZadocnuvanje);
		//System.out.println(posteriorNeDiplomiral);
		
		if (posteriorNaVreme > posteriorSoZadocnuvanje) {
			
			if (posteriorNaVreme > posteriorNeDiplomiral) 
				System.out.println("NA VREME");
			else 
				System.out.println("NE DIPLOMIRAL");
		}
		else {
			if (posteriorSoZadocnuvanje > posteriorNeDiplomiral) 
				System.out.println("SO ZADOCNUVANJE");
			else
				System.out.println("NE DIPLOMIRAL");
		}
		
	}
	
	
	private double posterior(double uspeh, String obrazovanie, double prosek, String predmeti,int klasa) {
		
		int divider = classes.get(klasa).getExamples();
		double prior = classes.get(klasa).getProbability(trainingExamples);
		double cptUspeh = parametars.get(USPEH)
				.getConditionalProbability("" + uspeh, klasa, divider);
		double cptObrazovanie = parametars.get(SREDNO_OBRAZOVANIE)
				.getConditionalProbability(obrazovanie, klasa, divider);
		double cptProsek = parametars.get(PROSEK)
				.getConditionalProbability("" + prosek, klasa, divider);
		double cptPredmeti = parametars.get(BROJ_PREDMETI)
				.getConditionalProbability(predmeti, klasa, divider);
		
		return prior * cptUspeh * cptObrazovanie * cptProsek * cptPredmeti;
	}
	
	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		for (Parametar p : parametars) {
			sb.append(p.toString());
			sb.append("\n");
		}
		return sb.toString();
	}


}
