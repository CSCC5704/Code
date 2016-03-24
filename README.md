# Files
CodeClone.java            - the main function to detect cloned code in java files

ASTParserTool.java        - use JDT ASTParser to parse the jave source code into methods

MethodTokenizerTool.java  - tokenize the method body and get the frequency of selected tokens

BiGramSimilarity.java     - calculate the similarity of two strings by using the bi-gram algorithm

MethodSimilarity.java     - calculate the similarity of two methods

MethodList.java           - a list of MethodVector and some local functions

MethodVector.java         - build the structure of each method, including startLineNumber, endLineNumber, methodName, 
                            methodPara, methodType and methodBody

TokenList.java            - a list of TokenVector and some local functions

TokenVector.java          - build the structure of each token, including tokenName, tokenType and tokenCount




