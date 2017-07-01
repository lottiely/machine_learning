
import java.util.ArrayList;

public class classifier {

	// ------------------
	// variables
	// ------------------

	// variables for ANN

	private static ArrayList<String> trainingSet = new ArrayList<String>();
	private static ArrayList<String> classificationSet = new ArrayList<String>();
	private static ArrayList<String> spam = new ArrayList<String>();
	private static ArrayList<String> nonspam = new ArrayList<String>();
	private static ArrayList<Integer> isSpamT = new ArrayList<Integer>();
	// training set spam list. This is aligned with training set and will tell
	// whether the object at the index number is supposed to be spam or not. spam = 1,
	// nonspam = -1
	private static ArrayList<Integer> isSpamC = new ArrayList<Integer>();
	// classification set spam list. This is aligned with training set and will
	// tell whether the object at the index number is supposed to be spam or not. spam = 1,
	// nonspam = -1
	
	private static ArrayList<Double> spamProbabilities1 = new ArrayList<Double>();
	//size = 334
	//Holds the probabilities of all the features that are spam in the training set
	private static ArrayList<Double> nonSpamProbabilities1 = new ArrayList<Double>();
	//size = 334
	//Holds the probabilities of all the features that are not spam in the training set
	private static ArrayList<Double> spamProbabilities0 = new ArrayList<Double>();
	//size = 334
	//Holds the probabilities of all the features that are spam in the training set
	private static ArrayList<Double> nonSpamProbabilities0 = new ArrayList<Double>();
	//size = 334
	//Holds the probabilities of all the features that are not spam in the training set

	// -------------------------------------
	// initializeData
	// ---------------------------------------
	public static void initializeData() {
		readFile.initializeData();
		spam = readFile.getSpam();
		nonspam = readFile.getNonSpam();
	}
	
	public static void splitData(double training){
		//pass in the percentage of the datalist that will go into the training set 
		//the rest will go into the classification set
		
		//-----------------------
		//	Sorting the data into 
		//	training and classification
		//	sets
		//-----------------------
		
		double spamSize = training*spam.size();
		double nonspamSize = training*nonspam.size();
		
				//spam training and classification sets
				for(int i = 0; i < spamSize; i++){
					trainingSet.add(spam.get(i));
					isSpamT.add(1);
				}
				for(int i = spam.size()-1; i>=spamSize; i--){
					classificationSet.add(spam.get(i));
					isSpamC.add(1);
				}
				
				//nonspam training and classification sets
				for(int i = 0; i < nonspamSize; i++){
					trainingSet.add(nonspam.get(i));
					isSpamT.add(-1);
				}
				for(int i = nonspam.size()-1; i>=nonspamSize; i--){
					classificationSet.add(nonspam.get(i));
					isSpamC.add(-1);
				}
	}
	
	public static void train(){
		int[] spamCount = new int[334]; //keeps count of the features of emails considered spam
		int[] nonSpamCount = new int[334]; //keeps count of the features of emails considered not spam
		int[] spamCount0 = new int[334];
		int[] nonSpamCount0 = new int[334];
		double totalSpam = 0;
		double totalNonSpam = 0;
		
		for(int i = 0; i < spamCount.length; i++){
			//initializes the arrays to 0
			spamCount[i] = 0;
			nonSpamCount[i] = 0;
			spamCount0[i] = 0;
			nonSpamCount0[i] = 0;
		}
		
		for(int i = 0; i < trainingSet.size(); i++){ //loops through emails
			if(isSpamT.get(i) == 1) //is Spam
			{
				totalSpam++;
				for(int j = 0; j < trainingSet.get(i).length(); j++)//loops through all features
				{
					if(trainingSet.get(i).charAt(j) == '1')
						spamCount[j]++;
					else
						spamCount0[j]++;
				}
			}
			else
			{
				totalNonSpam++;
				for(int j = 0; j < trainingSet.get(i).length(); j++)//loops through all features
				{
					if(trainingSet.get(i).charAt(j) == '1')
						nonSpamCount[j]++;
					else
						nonSpamCount0[j]++;
				}
			}
		}
		
		//storing all the probabilities in the array list
		for(int i = 0; i < spamCount.length; i++){
			spamProbabilities1.add(calculateProbability(spamCount[i], totalSpam));
			nonSpamProbabilities1.add(calculateProbability(nonSpamCount[i], totalNonSpam));
			spamProbabilities0.add(calculateProbability(spamCount0[i], totalSpam));
			nonSpamProbabilities0.add(calculateProbability(nonSpamCount0[i], totalNonSpam));
		}
	}
	
	public static void classify(){
		//probability sums
		double spamSum = 0;
		double nonSpamSum = 0;
		double spamProb;
		double nonSpamProb;
		
		double tP = 0;
		double fP = 0;
		double tN = 0;
		double totalP = 0;
		
		double precision;
		double accuracy;
		double recall;
		double fPRate;
		double tPRate;
		
		for(int i = 0; i < classificationSet.size(); i++){
			for(int j = 0; j < classificationSet.get(i).length(); j++){
				//adding up all the log probabilities
				if(classificationSet.get(i).charAt(j) == '1') //feature exists
				{
					spamSum += spamProbabilities1.get(j);
					nonSpamSum += nonSpamProbabilities1.get(j);
				}
				else{ //feature does not exist
					spamSum += spamProbabilities0.get(j);
					nonSpamSum += nonSpamProbabilities0.get(j);
				}
			}
			//finished analyzing all 334 features for the email
			spamProb = Math.exp(spamSum);
			nonSpamProb = Math.exp(nonSpamSum);
			
			if(spamProb > nonSpamProb) //classified spam
			{
				if(isSpamC.get(i) == 1){
					totalP++;
					//correct classification
					tP++;
				}else{
					//incorrect classification
					fP++;
				}
			}else{ //classified nonSpam
				if(isSpamC.get(i) == -1){
					//correct classification
					tN++;
				}else{
					//false classification
					totalP++;
				}
			}
			
			//reset values
			spamSum = 0;
			nonSpamSum = 0;
		}
		//finished looping through classification set
		precision = (tP/(tP+fP)); 
		accuracy = ((tP+tN)/classificationSet.size());
		recall = tP/totalP;
		fPRate = fP/classificationSet.size();
		tPRate = tP/totalP;
		
		System.out.println("Precision: " + precision*100);
		System.out.println("Accuracy: " + accuracy*100);
		System.out.println("Recall: " + recall*100);
		System.out.println("FP Rate: " + fPRate*100);
		System.out.println("TP Rate: " + tPRate*100);
	}
	
	public static double calculateProbability(double count, double total){
		if(count ==0)
			return 0;
		else{
			double division = count/total;
			double num = Math.log(division);
			return num;
		}
	}

	// ----------------------------
	// Main class that calls everything
	// ----------------------------
	public static void main(String[] args) {

		double percentage = 30;

		initializeData(); // initialize data
		for(int i = 0; i < 20; i++){
			System.out.println("Iteration " + (i+1) + ": ");
			System.out.println("Training: " + (percentage) + "%" + "   Classification: " + (100-percentage) + "%");
			splitData(percentage/100); //split training/classification
			train();
			classify();
			percentage += 2;
			System.out.println();
		}
	}
}
