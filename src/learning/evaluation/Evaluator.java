package learning.evaluation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Evaluator {
	double correct = 0;
	double miss = 0;
	double correctAction = 0;
	double missAction = 0;
	String outputFile = null;
	
	HashMap<Double, HashMap<Double, Integer>> confusionMatrix;
	ArrayList<Integer> missClassified;
	
	public Evaluator(){
		confusionMatrix = new HashMap<Double, HashMap<Double, Integer>>();
		missClassified = new ArrayList<Integer>();
	}
	
	public Evaluator(String outputFile){
		confusionMatrix = new HashMap<Double, HashMap<Double, Integer>>();
		missClassified = new ArrayList<Integer>();
		this.outputFile = outputFile;
	}
	
	public void evaluate(String testFormattedFile, String predictionFile) throws Exception{
		BufferedReader testReader = new BufferedReader(new FileReader(testFormattedFile));
		BufferedReader predictionReader = new BufferedReader(new FileReader(predictionFile));
		
		String testLine = "";
		String predictionLine = "";
		
		int lineIndex = 1;
		boolean isActionCorrect = true;
		
		while((testLine = testReader.readLine()) != null){
			predictionLine = predictionReader.readLine();
			
			if(testLine.equals(""))
				continue;
			
			double real = Double.parseDouble(testLine.split("\\s+")[0]);
			double predicted = Double.parseDouble(predictionLine);
			
			if(real == predicted)
				correct++;
			else{
				miss++;
				missClassified.add(lineIndex);
				isActionCorrect = false;
			}
			
			if(confusionMatrix.containsKey(real)){
				if(confusionMatrix.get(real).containsKey(predicted))
					confusionMatrix.get(real).put(predicted, confusionMatrix.get(real).get(predicted) + 1);
				else
					confusionMatrix.get(real).put(predicted, 1);
			}
			else{
				HashMap<Double, Integer> missPred = new HashMap<Double, Integer>();
				missPred.put(predicted, 1);
				confusionMatrix.put(real, missPred);
			}
			
			if(real == 0){
				if(isActionCorrect)
					correctAction++;
				else
					missAction++;
				
				isActionCorrect = true;
			}
			
			lineIndex++;
		}
		
		testReader.close();
		predictionReader.close();
	}
	
	public double atomicActionAccuracy(){
		return correct/(correct+miss);
	}
	
	public double actionAccuracy(){
		return correctAction/(correctAction+missAction);
	}
	
	public void printAtomicActionAccuracy(){
		if(this.outputFile == null){
			System.out.println("Atomic Action Accuracy: " + correct/(correct+miss) + " (" 
					+ (int)correct + "/" + (int)(correct + miss) + ")");}
		else{
			try{
				BufferedWriter writer = new BufferedWriter(new FileWriter(this.outputFile, true));
				writer.append("Atomic Action Accuracy: ");
				writer.append(Double.toString(correct/(correct+miss)));
				writer.append(" (");
				writer.append(Integer.toString((int)correct));
				writer.append("/");
				writer.append(Integer.toString((int)(correct + miss)));
				writer.append(")\n").flush();
				writer.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	public void printActionAccuracy(){
		if(this.outputFile == null){
			System.out.println("Action Accuracy: " 
					+ correctAction/(correctAction+missAction) + " (" 
					+ (int)correctAction + "/" + (int)(correctAction + missAction) + ")");
		}else{
			try{
				BufferedWriter writer = new BufferedWriter(new FileWriter(this.outputFile, true));
				writer.append("Action Accuracy: ");
				writer.append(Double.toString(correctAction/(correctAction+missAction)));
				writer.append(" (");
				writer.append(Integer.toString((int)correctAction));
				writer.append("/");
				writer.append(Integer.toString((int)(correctAction + missAction)));
				writer.append(")\n").flush();
				writer.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	public void printConfusionMatrix(){
		if(this.outputFile == null){
			System.out.println("Confusion Matrix: ");
			System.out.println("\t0\t1\t2\t3");
		
			for(double i = 0; i < 4; i++){
				System.out.print(i + "\t");
				if(confusionMatrix.containsKey(i)){
					for(double j = 0; j < 4; j++){
						if(confusionMatrix.get(i).containsKey(j))
							System.out.print(confusionMatrix.get(i).get(j) + "\t");
						else
							System.out.print("0\t");
					}
				}
				System.out.println();
			}
		}else{
			try{
				BufferedWriter writer = new BufferedWriter(new FileWriter(this.outputFile, true));
				writer.append("Confusion Matrix: \n");
				writer.append("\t0\t1\t2\t3\n");
				for(double i = 0; i < 4; i++){
					writer.append(i + "\t");
					if(confusionMatrix.containsKey(i)){
						for(double j = 0; j < 4; j++){
							if(confusionMatrix.get(i).containsKey(j))
								writer.append(Integer.toString(confusionMatrix.get(i).get(j)) + "\t");
							else
								writer.append("0\t");
						}
					}
					writer.append("\n").flush();
				}
				writer.close();
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	public void printMissClassifiedInstances(){
		System.out.println("Indexes of missclassified instances: ");
		for(int index : missClassified)
			System.out.println(index);
	}

}
