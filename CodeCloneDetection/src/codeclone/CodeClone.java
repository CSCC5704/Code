package codeclone;

public class CodeClone {
	
	public static String filename = "test.java";
	
	public static ASTParserTool parserTool = new ASTParserTool();

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub	
		MethodList methodVectorList = parserTool.parseMethod(parserTool.getCompilationUnit(filename));
		
		MethodSimilarity methodSim = new MethodSimilarity();
		methodSim.simDetector(methodVectorList);
	}
}