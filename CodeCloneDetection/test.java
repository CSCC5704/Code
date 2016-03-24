public class BinaryConverter {
		
    public static void main(String[] args){
        for(int i = -5; i < 33; i++){
            System.out.println(i + ": " + toBinary(i));
            System.out.println(i);
            //always another way
            System.out.println(i + ": " + Integer.toBinaryString(i));
        }
    }
	
    /*
     * pre: none
     * post: returns a String with base10Num in base 2
     */
	public static String toBinary(int base10Num){
		boolean isNeg = base10Num < 0;
        base10Num = Math.abs(base10Num);        
        String result = "";
        
        while(base10Num > 1){
            result = (base10Num % 2) + result;
            base10Num /= 2;
        }
        assert base10Num == 0 || base10Num == 1 : "value is not <= 1: " + base10Num;
        
        result = base10Num + result;
        assert all0sAnd1s(result);
        
        if( isNeg )
            result = "-" + result;
        return result;
    }
	
	public static String toBinary1(int baseNum){
		boolean isneg = baseNum < 0;
		baseNum = Math.abs(baseNum);        
        String result = "";
        
        while(baseNum > 1){
            result = baseNum % 2 + result;
            baseNum = baseNum / 2;
        }
		        
        result = baseNum + result;
        
        if( isneg )
            result = "-" + result;
        return result;
    }
	
	public static String toBinary2(int num){
		boolean isNeg=num<0;	//Comments
        num=Math.abs(num);	//Comments
        String result="";
        
        while(num>1){
            result=num % 2 + result;
            num=num/2;
        }
		        
        result = num + result;
        
        if( isNeg )
            result = "-" + result;
        return result;
    }
    
    /*
     * pre: cal != null
     * post: return true if val consists only of characters 1 and 0, false otherwise
     */
    public static boolean all0sAnd1s(String val){
        assert val != null : "Failed precondition all0sAnd1s. parameter cannot be null";
        boolean all = true;
        int i = 0;
        char c;
        
        while(all && i < val.length()){
            c = val.charAt(i);
            all = c == '0' || c == '1';
            i++;
        }
        return all;
    }
}