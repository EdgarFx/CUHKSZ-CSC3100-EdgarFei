package csc3100hw1;
import java.util.*;

public class Solution {
    private Stack<String> suffixStack = new Stack<String>();
    private Stack<String> opStack = new Stack<String>();

    public static double getSolution(String str) {
        double result;
        Solution solution = new Solution();
        try {
            str = removeBlank(str);
            if (str.equals("invalid")) {return 0.0/0;}
            str = changeMinus(str);
            result = solution.solve(str);
        } catch (Exception e) {return 0.0/0;}
        return result;
    }

    private static String changeMinus(String str) {
        char[] arr = str.toCharArray();
        for (int i=0; i<arr.length; i++) {
            if (arr[i]=='-'){
                if (i!=0) {
                    char ch = arr[i-1];
                    if (ch=='+' || ch=='-' || ch=='/' || ch=='(' || ch=='*') {
                        arr[i] = '~';
                    }
                } else {arr[i]='~';}
            }
        }
        if (arr[0]=='~'||arr[1]=='(') {arr[0]='-';return "0"+new String(arr);} else {return new String(arr);}
    }

    private static String removeBlank(String str) {
        char[] arr = str.toCharArray();
        int status = 1;
        for (int i=0; i<arr.length; i++){
            if (arr[i]=='s') {
                if (!str.substring(i, i+4).equals("sqrt")&&!str.substring(i, i+3).equals("sin")&&!str.substring(i-2,i+1).equals("cos")){
                    return "invalid";
                }
            }
            if (arr[i]=='t') {
                if (!str.substring(i, i+3).equals("tan")&&!str.substring(i-3, i+1).equals("sqrt")) {return "invalid";}
            }
            if (arr[i]=='.') {
                if (i==0) {return "invalid";} else{
                    if (arr[i-1]==' '||arr[i+1]==' ') {return "invalid";}
                }
            }
            if (Character.isDigit(arr[i])&&i<arr.length-2) {
                if (arr[i+1]==' '){
                    for (int j=i+2;j<arr.length;j++) {
                        if (Character.isDigit(arr[j])) {status=0; break;}
                        if (arr[j]==' ') {continue;}
                        else {break;}
                    }
                }
            }
        }
        if (status==0) {return "invalid";}
        str = str.replace(" ", "");
        char[] arr1 = str.toCharArray();
        for (int i=0; i<arr1.length-1; i++) {
            if ((Character.isLowerCase(arr1[i+1])||arr1[i+1]=='(')&&Character.isDigit(arr1[i])) {return "invalid";}
            if ((arr1[i+1]=='('||Character.isLowerCase(arr1[i+1]))&&arr1[i]==')') {return "invalid";}
        }
        return str;
    }

    public double solve(String str) {
        Stack<String> resultStack = new Stack<String>();
        changeForm(str);
        Collections.reverse(suffixStack);
        String v1,v2,cv;
        while (!suffixStack.isEmpty()) {
            cv = suffixStack.pop();
            if (!isOperator(cv)) {
                cv=cv.replace('~','-');
                resultStack.push(cv);
            } else if (cv.equals("+")||cv.equals("-")||cv.equals("*")||cv.equals("/")){
                v2 = resultStack.pop();
                v1 = resultStack.pop();
                v1 = v1.replace('~','-');
                v2 = v2.replace('~','-');
                String midValue = solve(v1,v2,cv);
                resultStack.push(midValue);
            } else if (cv.equals("sqrt")||cv.equals("sin")||cv.equals("cos")||cv.equals("tan")) {
                v1 = resultStack.pop();
                v1 = v1.replace('~','-');
                String midValue = solve(v1,cv);
                if (midValue.equals("invalid")) {
                    return 0.0/0;
                }
                resultStack.push(midValue);
            } else if (cv.equals("(")) {return 0.0/0;}
        }
        return Double.valueOf(resultStack.pop());
    }

    public void changeForm(String str) {
        opStack.push("#");
        char[] arr = str.toCharArray();
        int cIndex = 0;
        int interval = 0;
        String cOperator, peekOperator;
        for (int i=0; i<arr.length; i++){
            cOperator = String.valueOf(arr[i]);
            if (cOperator.equals("c")) {cOperator="cos"; i+=2;}
            else if (cOperator.equals("s")) {
                if (arr[i+1]=='q') {cOperator = "sqrt"; i+=3;} else {cOperator = "sin"; i+=2;}
            }
            else if (cOperator.equals("t")) {cOperator="tan"; i+=2;}
            if (isOperator(cOperator)) {
                if (interval>0) {suffixStack.push(new String(arr, cIndex, interval));}
                peekOperator = String.valueOf(opStack.peek());
                if (cOperator.equals(")")){
                    while (!opStack.peek().equals("(")) {
                        suffixStack.push(String.valueOf(opStack.pop()));
                    }
                    opStack.pop();
                } else{
                    while (!cOperator.equals("(") && !peekOperator.equals("#") && compare(cOperator, peekOperator)) {
                        suffixStack.push(String.valueOf(opStack.pop()));
                        peekOperator = String.valueOf(opStack.peek());
                    }
                    opStack.push(cOperator);
                }
                interval=0;
                cIndex=i+1;
            } else {interval++;}
        }
        if (interval>1 || (interval==1 && !isOperator(String.valueOf(arr[cIndex])))) {suffixStack.push(new String(arr, cIndex, interval));}
        while (!opStack.peek().equals("#")){suffixStack.push(String.valueOf(opStack.pop()));}
    }

    private static boolean isOperator(String op) {
        return op.equals("+")||op.equals("-")||op.equals("*")||op.equals("/")||op.equals("(")||op.equals(")")||op.equals("sqrt")||op.equals("sin")||op.equals("cos")||op.equals("tan");
    }

    private int priority(String str){
        if (str.equals("(")) {return 0;}
        else if (str.equals("+") || str.equals("-")) {return 1;}
        else if (str.equals("*") || str.equals("/")) {return 2;}
        else {return 3;}
    }

    private boolean compare(String cOperator, String peekOperator) {
        if (priority(peekOperator)>=priority(cOperator)){return true;}
        return false;
    }

    private String solve(String v1, String v2, String cOperator) {
        String result="";
        switch (cOperator) {
            case "+": result = String.valueOf(BasicOperation.add(v1,v2));break;
            case "-": result = String.valueOf(BasicOperation.sub(v1,v2));break;
            case "*": result = String.valueOf(BasicOperation.mul(v1,v2));break;
            case "/": result = String.valueOf(BasicOperation.div(v1,v2));break;
        }
        return result;
    }

    private String solve(String v1, String cOperator) {
        String result="";
        switch (cOperator) {
            case "sqrt":double num1 = BasicOperation.sqrt(v1); if (!Double.isNaN(num1)) {result = String.valueOf(num1);} else {return "invalid";}break;
            case "sin": double num2 = BasicOperation.sin(v1); if (!Double.isNaN(num2)) {result = String.valueOf(num2);} else {return "invalid";}break;
            case "cos": double num3 = BasicOperation.cos(v1); if (!Double.isNaN(num3)) {result = String.valueOf(num3);} else {return "invalid";}break;
            case "tan": double num4 = BasicOperation.tan(v1); if (!Double.isNaN(num4)) {result = String.valueOf(num4);} else {return "invalid";}break;
        }
        return result;
    }
}
