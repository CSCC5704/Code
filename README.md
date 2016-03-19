# Files
CodeClone.java - The main code to detect cloned code fragments in java source codes

ASTParserTool - Use JDT ASTParser to parse the jave source code into fragments

FragmentVector.java - Store the structure of each fragment, including startlinenumber, endlinenubmer, fragmentname, fragmentPara, fragmentType and fragmentBody in \<String, Integer\> Form

FragmentTokenizer - Tokenize the fragment body and calculate the frequency of selected tokens

