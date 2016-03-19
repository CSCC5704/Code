package codeclone;

import java.io.BufferedInputStream;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ASTParserTool {
	
	public CompilationUnit getCompilationUnit(String javaFilePath){  
		byte[] input = null;
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(javaFilePath));
			input = new byte[bufferedInputStream.available()];
			bufferedInputStream.read(input);
			bufferedInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();  
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		@SuppressWarnings("deprecation")
		ASTParser astParser = ASTParser.newParser(AST.JLS3);
		astParser.setSource(new String(input).toCharArray());
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		
		CompilationUnit result = (CompilationUnit) (astParser.createAST(null));
		
		return result;
	}
	
	public void getMethod(CompilationUnit result) {
		List types = result.types();
		TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
		
		MethodDeclaration fragmentDec[] = typeDec.getMethods();
		System.out.println("Method:");
		for (MethodDeclaration fragment : fragmentDec) {
			visitMethod(result, fragment);
		}
	}
	
	public void visitMethod(CompilationUnit result, MethodDeclaration fragment) {
		//get method line #
		int startLineNumber = result.getLineNumber(fragment.getStartPosition());
		//System.out.println("fragment start line #:" + startLineNumber);
		
		int endLineNumber = result.getLineNumber(fragment.getStartPosition() + fragment.getLength()) - 1;
		//System.out.println("fragment end line #:" + endLineNumber);		
				
		//get method name
		SimpleName fragmentName = fragment.getName();
		//System.out.println("fragment name:" + fragmentName);
		
		//get method parameters
		String fragmentPara = fragment.parameters().toString();
		//System.out.println("fragment parameters:" + fragmentPara);
		
		//get method return type
		String fragmentType = fragment.getReturnType2().toString();
		//System.out.println("fragment type:" + fragmentType);
		
		//get method body
		String fragmentBody = fragment.getBody().toString();
		//System.out.println("fragment body:" + fragmentBody);
		
		FragmentTokenizer fragTokenizer = new FragmentTokenizer();
		Map<String, Integer> fragTokenFreq = fragTokenizer.visit(fragmentBody);
    }
}