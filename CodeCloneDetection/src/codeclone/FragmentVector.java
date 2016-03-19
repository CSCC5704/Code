package codeclone;

import java.util.Map;

public class FragmentVector {
	int startLineNumber;
	int endLineNumber;
	String fragmentName;
	String fragmentPara;
	String fragmentType;
	Map<String, Integer> tokenFrequency;
	
	FragmentVector(int start, int end, String name, String para, String type, Map<String, Integer> tokenfreq) {
		this.startLineNumber = start;
		this.endLineNumber = end;
		this.fragmentName = name;
		this.fragmentPara = para;
		this.fragmentType = type;
		this.tokenFrequency = tokenfreq;
	}
}
