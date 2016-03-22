package codeclone;

public class TokenVector {
	String TokenName;
	String TokenType;
	int TokenCount;
	
	TokenVector(String name, String type) {
		this.TokenName = name;
		this.TokenType = type;
		this.TokenCount = 1;
	}
}
