package codeclone;

public class FragmentVector {
	int startLineNumber;
	int endLineNumber;
	String fragmentName;
	String fragmentPara;
	String fragmentType;
	TokenList tokenList;
	
	FragmentVector(int start, int end, String name, String para, 
					String type, TokenList tokenlist) {
		this.startLineNumber = start;
		this.endLineNumber = end;
		this.fragmentName = name;
		this.fragmentPara = para;
		this.fragmentType = type;
		this.tokenList = tokenlist;
	}
	
	public void Print() {
		System.out.println("Fragment Name: " + fragmentName);
		System.out.println("Start #: " + startLineNumber);
		System.out.println("End #: " + endLineNumber);
		System.out.println("Fragment Para: " + fragmentPara);
		System.out.println("Fragment Type: " + fragmentType);
		
		System.out.println("Token Frequency:");
		tokenList.sortListByCount();
		tokenList.printList();
		System.out.println();
	}
}
