package top.yangcc.common.utils;

import java.util.List;
import java.util.Stack;

/**
 * 算术表达式转换为逆波兰表达式
 */
public class ArithmeticConverterReversePolish {

    public static List<String> converter(String Arithmetic) {

        String reversePolishExpress = null;


        return reversePolishExpress;
    }


    static Stack<Character> op = new Stack<>();

    public static Float getv(char op, Float f1, Float f2){
        if(op == '+') return f2 + f1;
        else if(op == '-') return f2 - f1;
        else if(op  == '*') return f2 * f1;
        else if(op == '/') return f2 / f1;
        else return 0F;
    }
    public static float calrp(String rp){
        Stack<Float> v = new Stack<>();
        char[] arr = rp.toCharArray();
        for (Character ch : arr) {
            if (ch >= '0' && ch <= '9') {
                v.push((float) (ch - '0'));
            } else v.push(getv(ch, v.pop(), v.pop()));
        }
        return v.pop();
    }

    public static String getrp(String s){
        char[] arr = s.toCharArray();
        StringBuilder out = new StringBuilder();

        for (char ch : arr) {
            if (ch == ' ') {
                continue;
            }
            if (ch >= '0' && ch <= '9') {
                out.append(ch);
                continue;
            }
            if (ch == '(') {
                op.push(ch);
            }
            if (ch == '+' || ch == '-') {
                while (!op.empty() && (op.peek() != '('))
                    out.append(op.pop());
                op.push(ch);
                continue;
            }
            if (ch == '*' || ch == '/') {
                while (!op.empty() && (op.peek() == '*' || op.peek() == '/'))
                    out.append(op.pop());
                op.push(ch);
                continue;
            }
            if (ch == ')') {
                while (!op.empty() && op.peek() != '(')
                    out.append(op.pop());
                op.pop();
                continue;
            }
        }
        while(!op.empty()) out.append(op.pop());
        return out.toString();
    }
}
