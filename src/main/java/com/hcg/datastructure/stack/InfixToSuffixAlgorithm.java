package com.hcg.datastructure.stack;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class InfixToSuffixAlgorithm {

    private static String doTransform(String infixExpression) {
        String suffixExpresson = "";

        // 定义符号和优先级
        String[] priorityLow = {"+", "-"};
        String[] priorityMedium = {"*", "/"};
        String priorityHigh = "(";
        String endSymbol = ")";

        // 构造List易于操作
        List<String> priorityLowList = Arrays.asList(priorityLow);
        List<String> priorityMediumList = Arrays.asList(priorityMedium);
        char[] infixCharArr = infixExpression.toCharArray();
        Stack<String> transformStack = new Stack<>();

        for (char c : infixCharArr) {
            String inSymbol = String.valueOf(c);

            // 如果不是操作符则直接输出
            if (!priorityLowList.contains(inSymbol)
                    && !priorityMediumList.contains(inSymbol)
                    && !priorityHigh.equals(inSymbol)
                    && !endSymbol.equals(inSymbol)) {
                suffixExpresson = suffixExpresson.concat(inSymbol);
                continue;
            }

            // 栈为空，操作符直接入栈
            if (transformStack.empty()) {
                transformStack.push(inSymbol);
                continue;
            }

            // 最低优先级操作符，加法或减法
            if (priorityLowList.contains(inSymbol)) {
                // 使用循环的目的是处理一直碰到比读入运算符更高优先级的运算符，直到碰到相等优先级的运算符才进行弹出和压入
                while (true) {
                    // 上一个运算符同一优先级，弹出上个运算符并把当前运算符压入
                    if (priorityLowList.contains(transformStack.peek())) {
                        suffixExpresson = suffixExpresson.concat(transformStack.pop());
                        transformStack.push(inSymbol);
                        break;
                    } else if (priorityMediumList.contains(transformStack.peek())) {
                        // 上一个是优先级更高的运算符，直接弹出
                        suffixExpresson = suffixExpresson.concat(transformStack.pop());
                    } else if (priorityHigh.equals(transformStack.peek())) {
                        // 上一个是左括号，直接压入
                        transformStack.push(inSymbol);
                        break;
                    } else if (transformStack.empty()) {
                        // 如果之前的运算符都是高优先级，就会出现栈为空的情况，这个时候直接压入
                        transformStack.push(inSymbol);
                        break;
                    }
                }
            } else if (priorityMediumList.contains(inSymbol)) {
                // 高优先级直接入栈
                transformStack.push(inSymbol);
            } else if (priorityHigh.equals(inSymbol)) {
                // 左括号直接入栈
                transformStack.push(inSymbol);
            } else if (endSymbol.equals(inSymbol)) {
                // 右括号时弹出栈中元素，直到碰到左括号
                while (!priorityHigh.equals(transformStack.peek())) {
                    suffixExpresson = suffixExpresson.concat(transformStack.pop());
                }
                // 弹出左括号
                transformStack.pop();
            }

        }

        // 栈非空，全部弹出
        while (!transformStack.empty()) {
            suffixExpresson = suffixExpresson.concat(transformStack.pop());
        }

        return suffixExpresson;
    }

    public static void main(String[] args) {
        System.out.println(doTransform("a+b*c+(d*e+f)*g"));
    }
}
