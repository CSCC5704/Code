package codeclone;

import java.io.BufferedInputStream;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ASTParserTool {
	
	public List<FragmentVector> fragInfoVector = new ArrayList<FragmentVector>();
	
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
		
		ASTParser astParser = ASTParser.newParser(AST.JLS3);
		
		Map options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_5, options);
		astParser.setCompilerOptions(options);
		
		astParser.setSource(new String(input).toCharArray());
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		
		CompilationUnit result = (CompilationUnit) (astParser.createAST(null));
		
		return result;
	}
	
	public List<FragmentVector> parseMethod(CompilationUnit result) {
		
		List types = result.types();
		TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
		
		MethodDeclaration fragmentDec[] = typeDec.getMethods();
		
		for (MethodDeclaration fragment : fragmentDec) {
			visitMethod(result, fragment);
		}
		
		return fragInfoVector;
	}
	
	public void visitMethod(CompilationUnit result, MethodDeclaration fragment) {
		//get method line #
		int startLineNumber = result.getLineNumber(fragment.getStartPosition());
		//System.out.println("fragment start line #:" + startLineNumber);
		
		int endLineNumber = result.getLineNumber(fragment.getStartPosition() + fragment.getLength()) - 1;
		//System.out.println("fragment end line #:" + endLineNumber);		
				
		//get method name
		String fragmentName = fragment.getName().toString();
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
		TokenList fragTokenInfo = fragTokenizer.visit(fragmentBody);
		
		FragmentVector fragVector = new FragmentVector(startLineNumber, endLineNumber, fragmentName,
												fragmentPara, fragmentType, fragTokenInfo);
		fragInfoVector.add(fragVector);
    }
}
