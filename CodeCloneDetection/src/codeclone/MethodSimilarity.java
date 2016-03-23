package codeclone;

import java.util.HashMap;
import java.util.Map;

public class MethodSimilarity {

	public double simMethodPara, simMethodType;
	public double simTokenType, simTokenKeyword, simTokenOtherStr;
	public double simTokenMarker, simTokenOperator, simTokenOtherChar;
	public double w1 = 0.1, w2 = 0.1, w3 = 0.15, w4 = 0.15, w5 = 0.3, w6 = 0.05, w7 = 0.1, w8 = 0.05;
	public double methodSimilarity;
	public double detectThreshold = 0.5;
	
	public static int countID = 1;
	
	public String str1, str2;
	public TokenList tList1, tList2;
	
	// calculate the similarity of both TokenLists
	public double tokenListSim(TokenList tList1, TokenList tList2) {
		double tokenListDis = 0;
		Map<String, Integer> tVector1 = new HashMap<String, Integer>();
		Map<String, Integer> tVector2 = new HashMap<String, Integer>();
		
		// put list into map
		for(int index1 = 0; index1 < tList1.size(); index1++)
			tVector1.put(tList1.getTokenVector(index1).TokenName, tList1.getTokenVector(index1).TokenCount);
		for(int index2 = 0; index2 < tList2.size(); index2++)
			tVector2.put(tList2.getTokenVector(index2).TokenName, tList2.getTokenVector(index2).TokenCount);
		
		for (Map.Entry<String, Integer> entry1 : tVector1.entrySet()) {
			if(tVector2.containsKey(entry1.getKey()))
				// if list1 and list2 have the same tokenName, then calculate (tokenCount1-tokenCount2)^2
				tokenListDis += Math.pow((entry1.getValue() - tVector2.get(entry1.getKey())), 2);
			else
				// if list2 does not contain the tokenName of list1, then calculate (tokenCount1 - 0)^2
				tokenListDis += entry1.getValue() * entry1.getValue();
		}
		for (Map.Entry<String, Integer> entry2 : tVector2.entrySet()) {
			if(!tVector1.containsKey(entry2.getKey()))
				// if list1 does not contain the tokenName of list2, then calculate (tokenCount2 - 0)^2
				tokenListDis += entry2.getValue() * entry2.getValue();
		}
		return 1.0 / (1 + Math.sqrt(tokenListDis));
	}
	
	public double methodVectorSim(MethodVector mVector1, MethodVector mVector2) {
		// calculate methodPara's similarity 
		BiGramSimilarity biGramSim = new BiGramSimilarity();
		str1 = mVector1.methodPara;
		str2 = mVector2.methodPara;
		simMethodPara = biGramSim.simScore(biGramSim.bigram(str1), biGramSim.bigram(str2));
		
		// calculate methodType's similarity 
		str1 = mVector1.methodType;
		str2 = mVector2.methodType;
		if(str1.equals(str2))
			simMethodType = 1;
		else
			simMethodType = 0;
		
		// calculate token_Type's similarity 
		tList1 = mVector1.methodTokenList.getListByType("Type");
		tList2 = mVector2.methodTokenList.getListByType("Type");
		simTokenType = tokenListSim(tList1, tList2);
		
		// calculate token_Keyword's similarity 
		tList1 = mVector1.methodTokenList.getListByType("Keyword");
		tList2 = mVector2.methodTokenList.getListByType("Keyword");
		simTokenKeyword = tokenListSim(tList1, tList2);
		
		// calculate token_OtherStr's similarity 
		tList1 = mVector1.methodTokenList.getListByType("OtherStr");
		tList2 = mVector2.methodTokenList.getListByType("OtherStr");
		simTokenOtherStr = tokenListSim(tList1, tList2);
		
		// calculate token_Marker's similarity 
		tList1 = mVector1.methodTokenList.getListByType("Marker");
		tList2 = mVector2.methodTokenList.getListByType("Marker");
		simTokenMarker = tokenListSim(tList1, tList2);
		
		// calculate token_Operator's similarity 
		tList1 = mVector1.methodTokenList.getListByType("Operator");
		tList2 = mVector2.methodTokenList.getListByType("Operator");
		simTokenOperator = tokenListSim(tList1, tList2);
		
		// calculate token_OtherChar's similarity 
		tList1 = mVector1.methodTokenList.getListByType("OtherChar");
		tList2 = mVector2.methodTokenList.getListByType("OtherChar");
		simTokenOtherChar = tokenListSim(tList1, tList2);
		
		// calculate the similarity between two methods
		return simMethodPara * w1 + simMethodType * w2 + simTokenType * w3 +
				simTokenKeyword * w4 + simTokenOtherStr * w5 + simTokenMarker * w6 +
				simTokenOperator * w7 + simTokenOtherChar * w8;
		/*
		System.out.printf("%15s%15s%15.3f\n", 
				mList.getMethodVector(index1).methodName, 
				mList.getMethodVector(index2).methodName, 
				methodSimilarity);
				*/
	}
	
	// code clone detector for a single java file
	public void simDetector(MethodList mList) {
		for(int index1 = 0; index1 < mList.size() - 1; index1++) {
			for(int index2 = index1 + 1; index2 < mList.size(); index2++) {
				methodSimilarity = methodVectorSim(mList.getMethodVector(index1), mList.getMethodVector(index2));
				// output clone group
				if(methodSimilarity >= detectThreshold) {
					System.out.println("Clone Group " + countID + " --> " +
							"Similarity :" + String.format("%.4f", methodSimilarity));
					System.out.printf("%15s%15d%15d\n", 
							mList.getMethodVector(index1).methodName,
							mList.getMethodVector(index1).startLineNumber,
							mList.getMethodVector(index1).endLineNumber);
					System.out.printf("%15s%15d%15d\n", 
							mList.getMethodVector(index2).methodName,
							mList.getMethodVector(index2).startLineNumber,
							mList.getMethodVector(index2).endLineNumber);
					countID++;
				}
			}
		}
	}
	
	// code clone detector for two java files
	public void simDetector(MethodList mList1, MethodList mList2) {
		for(int index1 = 0; index1 < mList1.size(); index1++) {
			for(int index2 = 0; index2 < mList2.size(); index2++) {
				methodSimilarity = methodVectorSim(mList1.getMethodVector(index1), mList2.getMethodVector(index2));
				// output clone group
				if(methodSimilarity >= detectThreshold) {
					System.out.println("Clone Group " + countID + " --> " +
							"Similarity :" + String.format("%.4f", methodSimilarity));
					System.out.printf("%15s%15d%15d\n", 
							mList1.getMethodVector(index1).methodName,
							mList1.getMethodVector(index1).startLineNumber,
							mList1.getMethodVector(index1).endLineNumber);
					System.out.printf("%15s%15d%15d\n", 
							mList2.getMethodVector(index2).methodName,
							mList2.getMethodVector(index2).startLineNumber,
							mList2.getMethodVector(index2).endLineNumber);
					countID++;
				}
			}
		}
	}
}
