package codeclone;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamTokenizer;
import java.nio.charset.StandardCharsets;

public class FragmentTokenizer{
	
	private String[] types = {"int", "byte", "short", "long", "char", "float", "double", "boolean",
			"null", "true", "false"};
	private String[] keywords = {"private", "protected", "public", "abstract", "class", "extends",
			"final", "implements", "interface", "native", "new", "static", "strictfp", "synchronized",
			"transient", "volatile", "break", "continue", "return", "do", "while", "if", "else", "for",
			"instanceof", "switch", "case", "default", "try", "cathc", "throw", "throws", "super",
			"this", "void", "goto", "const"};
	
	private char[] ignores = {' ', ';'};
	private char[] markers = {'{', '}', '[', ']', '(', ')'};
	private char[] operators = {'+', '-', '*', '/', '%', '^', '<', '>', '!', '&', '|', '=', '~'};
	
	public TokenList tokenList = new TokenList();
	
	public TokenList visit(String fragmentBody) {
		try {
			// Create the tokenizer to read from fragmentBody
			InputStream iStream = new ByteArrayInputStream(fragmentBody.getBytes(StandardCharsets.UTF_8));
			StreamTokenizer st = new StreamTokenizer(iStream);
			
			// Prepare the tokenizer for Java-style tokenizing rules
			st.parseNumbers();
			st.wordChars('_', '_');
			st.eolIsSignificant(true);
			
			// If whitespace is not to be discarded, make this call
			//st.ordinaryChars(0, ' ');

			// If dot is not to be discarded, make this call
			st.ordinaryChars(0, '.');
			
			// These calls caused comments to be discarded
			st.slashSlashComments(true);
			st.slashStarComments(true);
			
			// Parse the file
			TokenVector tokenVector;
			int index;
			int token = st.nextToken();
			while (token != StreamTokenizer.TT_EOF) {
				token = st.nextToken();
				switch (token) {
				case StreamTokenizer.TT_NUMBER:
					// A number was found; the value is in nval
					double num = st.nval;
					
					String numstr = Double.toString(num);
					index = tokenList.getIndexByName(numstr);
					if(index != -1)
						tokenList.setValueByIndex(index);
					else {
						tokenVector = new TokenVector(numstr, "Num");
						tokenList.addTokenVector(tokenVector);
					}
					
					//System.out.println(num);
					break;
				case StreamTokenizer.TT_WORD:
					// A word was found; the value is in sval
					String word = st.sval;
					
					index = tokenList.getIndexByName(word);
					if(index != -1)
						tokenList.setValueByIndex(index);
					else {
						if(isStringElement(word, types))
							tokenVector = new TokenVector(word, "Type");
						else if(isStringElement(word, keywords))
							tokenVector = new TokenVector(word, "Keyword");
						else
							tokenVector = new TokenVector(word, "OtherStr");
						tokenList.addTokenVector(tokenVector);
					}
					
					//System.out.println(word);
					break;
				case '"':
					// A double-quoted string was found; sval contains the contents
					String dquoteVal = st.sval;
					break;
				case '\'':
					// A single-quoted string was found; sval contains the contents
					String squoteVal = st.sval;
					break;
				case StreamTokenizer.TT_EOL:
					// End of line character found
					break;
				case StreamTokenizer.TT_EOF:
					// End of file has been reached
					break;
				default:
					// A regular character was found; the value is the token itself
					char ch = (char)st.ttype;
					
					index = tokenList.getIndexByName(Character.toString(ch));
					if(index != -1)
						tokenList.setValueByIndex(index);
					else {
						if (isCharElement(ch, ignores))
							break;
						else if(isCharElement(ch, markers))
							tokenVector = new TokenVector(Character.toString(ch), "Marker");
						else if(isCharElement(ch, operators))
							tokenVector = new TokenVector(Character.toString(ch), "Operator");
						else
							tokenVector = new TokenVector(Character.toString(ch), "OtherChar");
						tokenList.addTokenVector(tokenVector);
					}
						
					//System.out.println(ch);
					break;
				}
			}
		} catch (IOException e) {}

		return tokenList;
	}
	
	public boolean isCharElement(char ch, char[] charList) {
		for(int i = 0; i < charList.length ; i++) {
			if (ch == charList[i])
				return true;
		}
		return false;
	}
	
	public boolean isStringElement(String str, String[] strList) {
		for(int i = 0; i < strList.length ; i++) {
			if (str.equals(strList[i]))
				return true;
		}
		return false;
	}
}
