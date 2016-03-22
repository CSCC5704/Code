package codeclone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TokenList{
	
	public List<TokenVector> tokenList = new ArrayList<TokenVector>();
	
	public void addTokenVector(TokenVector tv) {
		tokenList.add(tv);
	}
	
	public int getIndexByName(String name) {
		for(int i = 0; i < tokenList.size(); i++) {
			if(tokenList.get(i).TokenName.equals(name))
				return i;
		}
		return -1;
	}
	
	public void setValueByIndex(int index) {
		tokenList.get(index).TokenCount++;
	}
	
	public void sortListByName() {
		Collections.sort(tokenList, new Comparator<TokenVector>() {
	        public int compare(TokenVector arg0, TokenVector arg1) {
	            return arg0.TokenName.compareTo(arg1.TokenName);
	        }
	    });
	}
	
	public void sortListByType() {
		Collections.sort(tokenList, new Comparator<TokenVector>() {
	        public int compare(TokenVector arg0, TokenVector arg1) {
	            return arg0.TokenType.compareTo(arg1.TokenType);
	        }
	    });
	}
	
	public void sortListByCount() {
		Collections.sort(tokenList, new Comparator<TokenVector>() {
	        public int compare(TokenVector arg0, TokenVector arg1) {
	            return arg1.TokenCount - arg0.TokenCount;
	        }
	    });
	}
	
	public void printList() {
		for(int i = 0; i < tokenList.size(); i++) {
			System.out.printf("%15s%15s%15d\n", 
					tokenList.get(i).TokenName, 
					tokenList.get(i).TokenType, 
					tokenList.get(i).TokenCount);
		}
	}
}
