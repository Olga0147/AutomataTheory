import Exp.*;

import java.util.*;

public class LexAnalyzer {


    public LexAnalyzer() {}

    public static List<Word> parse(String str) {

        List<Word> internalVar = new ArrayList<>();
        Deque<Character> stack = new ArrayDeque<>();
        boolean flagforeach = true;

        for (char c:str.toCharArray()) {

            if (c == ' ') continue;

            if(c == '(' || c == ')' || c == '&' || c == '|' || c == '*'){

                switch (c){
                    case '(': {
                        stack.push('(');
                        break;}
                    case '&': {
                        if(stack.peek() == '('){stack.push('&'); } else{System.out.println("Неверное выражение: &");flagforeach = false;}
                        break;
                    }
                    case '|':{
                        if(stack.peek() == '('){stack.push('|'); }else{System.out.println("Неверное выражение: |");flagforeach = false;}
                        break;
                    }
                    case '*': {
                        if(stack.peek() == ')'){stack.pop();stack.pop(); } else {System.out.println("Неверное выражение: *1");flagforeach = false;break;}
                        Word w = internalVar.remove(internalVar.size()-1);
                        internalVar.add(new KleeneStar(w));
                        break;
                    }
                    case ')': {
                        if(stack.peek() == '('){stack.push(')');}
                        else if(stack.peek() == '&'){
                            Word w1 = internalVar.remove(internalVar.size()-1);
                            Word w2 = internalVar.remove(internalVar.size()-1);
                            internalVar.add(new Concatination(w2,w1));
                            stack.pop();
                            stack.pop();
                        }
                        else if(stack.peek() == '|'){
                            Word w1 = internalVar.remove(internalVar.size()-1);
                            Word w2 = internalVar.remove(internalVar.size()-1);
                            internalVar.add(new Or(w2,w1));
                            stack.pop();
                            stack.pop();
                        }
                        else {System.out.println("Неверное выражение: )");flagforeach = false;}
                        break;
                    }
                    default:{System.out.println("Неверное выражение");flagforeach = false;}
                }
            }
            else{
                internalVar.add(new Symbol(c));
            }

            if(!flagforeach){
                break;
            }

        }

        if(!stack.isEmpty() || !flagforeach){
            System.out.println("Неверное выражение");
            return null;
        }
        return internalVar;
    }

}


