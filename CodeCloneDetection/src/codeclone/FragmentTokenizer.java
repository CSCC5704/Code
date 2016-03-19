package codeclone;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamTokenizer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FragmentTokenizer{
	
	public Map<String, Integer> visit(String fragmentBody) {
		
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
			int token = st.nextToken();
			while (token != StreamTokenizer.TT_EOF) {
				token = st.nextToken();
				switch (token) {
				case StreamTokenizer.TT_NUMBER:
					// A number was found; the value is in nval
					double num = st.nval;
					System.out.println(num);
					break;
				case StreamTokenizer.TT_WORD:
					// A word was found; the value is in sval
					String word = st.sval;
					System.out.println(word);
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
					System.out.println(ch);
					break;
				}
			}
		} catch (IOException e) {}
		
		return null;
	}
}
