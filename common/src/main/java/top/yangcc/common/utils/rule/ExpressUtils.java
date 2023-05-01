package top.yangcc.common.utils.rule;

import top.yangcc.common.utils.constant.CharConstant;

import java.util.*;

/**
 * 算术表达式转换为逆波兰表达式
 * a&&b -> ab&
 * (1||2)&&3 -> 12|3&
 */
@SuppressWarnings("unused")
public class ExpressUtils {


    public static void main(String[] args) {
        System.out.println(parseExp("(1||2)&&3"));
    }

    public static Deque<String> parseExp(String exp){
        //符号栈
        Stack<Character> opStack = new Stack<>();
        //表达式队列
        Deque<String> numList = new LinkedList<>();

        //获取表达式符号
        List<String> ops = Arrays.stream(OperateEnum.values()).map(OperateEnum::getCode).toList();

        try {
            for (int i = 0; i < exp.length(); i++) {
                char c = exp.charAt(i);
                //如果是值
                if (String.valueOf(c).matches(CharConstant.BACKSLASH_W)){
                    StringBuilder sb = new StringBuilder();
                    //一直取到不是符号为止
                    do {
                        sb.append(c);
                        ++i;
                        if (i>=exp.length()){
                            break;
                        }
                        c = exp.charAt(i);
                    }while (String.valueOf(c).matches(CharConstant.BACKSLASH_W));
                    i--;
                    numList.offer(sb.toString());
                }else if (c =='('){
                    //左符号入栈
                    opStack.push(c);
                }else if (c ==')'){
                    //右符号出栈
                    Character pop;
                    while ((pop = opStack.pop())!='('){
                        numList.offer(pop.toString());
                    }
                }else if (ops.contains(String.valueOf(c))){
                    //普通符号
                    char nextOps = exp.charAt(++i);
                    if (nextOps != c){
                        throw new RuntimeException("表达式错误");
                    }
                    //如果为空则入栈
                    if (opStack.isEmpty()){
                        opStack.push(c);
                    }else {
                        Character pop;
                        boolean b;
                        Stack<Character> temp= new Stack<>();
                        do {
                            pop = opStack.pop();
                            b = compareOperate(c,pop);
                            if (b){
                                temp.push(pop);
                            }
                        }while (b && !opStack.isEmpty());

                        if (!b){
                            opStack.push(pop);
                        }
                        opStack.push(c);

                        while (!temp.isEmpty()){
                            opStack.push(temp.pop());
                        }
                    }
                }
            }
            while (!opStack.isEmpty()){
                numList.offer(opStack.pop().toString());
            }
            return numList;
        }catch (Exception e){
            return null;
        }
    }


    /**
     * 判断优先级
     *  1 2 -> false
     *  2 1 -> true
     */
    public static  boolean compareOperate(Character now ,Character left){
        OperateEnum nowEnum = OperateEnum.parse(String.valueOf(now));
        OperateEnum leftEnum = OperateEnum.parse(String.valueOf(left));
        if (nowEnum==null|| leftEnum ==null){
            throw new RuntimeException("表达式错误");
        }
        return nowEnum.getPriority().compareTo(leftEnum.getPriority())>0;
    }

}
