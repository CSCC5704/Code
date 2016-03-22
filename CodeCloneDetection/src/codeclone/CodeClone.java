package codeclone;

import java.util.List;

public class CodeClone {
	
	public static String filename = "test.java";
	
	public static void getMethodMapping(String filename) {
		ASTParserTool parserTool = new ASTParserTool();
		List<FragmentVector> fragVector = parserTool.parseMethod(parserTool.getCompilationUnit(filename));
		for(int i = 0; i < fragVector.size(); i++) {
			fragVector.get(i).Print();
		}
	}

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub	
		getMethodMapping(filename);
	}
}