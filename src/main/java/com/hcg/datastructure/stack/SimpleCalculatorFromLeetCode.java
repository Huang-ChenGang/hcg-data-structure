package com.hcg.datastructure.stack;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 实现一个基本的计算器来计算一个简单的字符串表达式
 * 将中缀表达式转换为后缀表达式并计算其值
 */
public class SimpleCalculatorFromLeetCode {

    public static void main(String[] args) {
        SimpleCalculatorFromLeetCode calculator = new SimpleCalculatorFromLeetCode();
        int resulet = calculator.doCalculate("8*9+((9-4)*34/10)-23");
        System.out.println("result = " + resulet);
    }

    public int doCalculate(String infixExpression) {
        return calculateSuffix(infixToSuffix(infixExpression));
    }

    private int calculateSuffix(Queue<String> suffixQueue) {
        Stack<Integer> stack = new Stack<>();

        while (!suffixQueue.isEmpty()) {
            String s = suffixQueue.poll();

            // 如果时数字，则弹出入栈
            if (isDigital(s.charAt(0))) {
                stack.push(Integer.valueOf(s));
            } else if (isOperator(s.charAt(0))) {
                // 如果是运算符则从栈中弹出两个元素进行运算
                char operator = s.charAt(0);
                int number1 = stack.pop();
                int number2 = stack.pop();

                // 因为入了一次栈，所以用后出栈的对先出栈的进行操作
                switch (operator) {
                    case '+':
                        stack.push(number2 + number1);
                        break;
                    case '-':
                        stack.push(number2 - number1);
                        break;
                    case '*':
                        stack.push(number2 * number1);
                        break;
                    case '/':
                        stack.push(number2 / number1);
                        break;
                    default:
                        break;
                }
            }
        }

        // 栈中组后一个元素即为计算结果，进行返回
        return stack.pop();
    }

    private Queue<String> infixToSuffix(String infixExpression) {
        Queue<String> suffixQueue = new LinkedList<>();
        Stack<Character> stack = new Stack<>();

        int index = 0;
        while (index < infixExpression.length()){
            char c = infixExpression.charAt(index);

            // 如果是数字就直接入队
            if (isDigital(c)) {
                // 入队的时候要判断后面是否还有剩余的数字，要把整个数字入队列，而不是一个数字字符
                int p = index;
                while (p < infixExpression.length() && isDigital(infixExpression.charAt(p))) {
                    p++;
                }
                suffixQueue.add(infixExpression.substring(index, p));
                index = p;
                continue;
            } else if ('(' == c) {
                // 左括号直接入栈
                stack.push(c);
            } else if (')' == c) {
                // 如果是右括号，就弹出栈中的元素，直到遇到左括号为止。左右括号均不入队列
                while ('(' != stack.peek()) {
                    suffixQueue.add(stack.pop() + "");
                }
                // 弹出左括号
                stack.pop();
            } else if (isOperator(c)) {
                // 操作符分情况处理
                if (stack.empty()) {
                    stack.push(c);
                } else if ('(' == stack.peek()) {
                    // 栈顶是左括号，直接入栈
                    stack.push(c);
                } else if (calculatePriority(c) > calculatePriority(stack.peek())) {
                    // 读入的运算符比栈顶的运算符优先级更高，直接入栈
                    stack.push(c);
                } else if (calculatePriority(c) <= calculatePriority(stack.peek())) {
                    // 读入的运算符优先级小于等于栈顶运算符优先级，弹出栈顶运算符，读入的运算符入栈
                    suffixQueue.add(stack.pop() + "");
                    stack.push(c);
                }
            }

            index++;
        }

        // 栈不为空，栈中元素全部弹出
        while (!stack.empty()) {
            suffixQueue.add(stack.pop() + "");
        }

        return suffixQueue;
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c =='/';
    }

    private boolean isDigital(char c) {
        return c >= '0' && c <= '9';
    }

    private int calculatePriority(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                throw new RuntimeException("Illegal operator: " + c);
        }
    }
}
