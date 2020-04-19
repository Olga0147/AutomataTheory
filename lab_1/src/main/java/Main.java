import Exp.Word;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Регулярное выражение может  состоять из:\n" +
                "1)Цифр 0-9\n" +
                "2)Букв a-z,A-Z\n" +
                "3)Символов (,),|,*,&\n" +
                "Правила:\n"+
                "1) (w1&w2)\n"+
                "2) (w1|w2)\n"+
                "3) (w)*\n"+
                "Пример1: ((a&c)|(b)*)\n" +
                "Цепочки ac , b , bb , bbb , bbbb ...\n"+
                "Пример2: (((((a&b)&c)|d)&(e)*)|((s&g)&a))\n"+
                "Цепочки abc , abce , abcee , abceee ... , d , de , dee , deee ... , sga \n"+
                "Пример3: (((((a&b))*|e)&((c&d))*)|g)\n" +
                "Цепочки ab , cd , abcd , ababcd , abcdcd  ... , e , ecd , ecdcd ... , g\n");

        System.out.println("Введите регулярное выражение:");

        Scanner scanIn = new Scanner(System.in);
        String str;

        while (true){
                str = scanIn.nextLine();
                String newStr = str.replaceAll("[a-zA-Z0-9*()|&]", "");
                if ( 0 < newStr.length()) {
                    System.out.println("Использован неверный символ, попробуйте еще раз");
                }
                else{
                    break;
                }
        }

        //1)parse
        List<Word> lw = LexAnalyzer.parse(str);
        if(lw == null){return;}
        System.out.println("\nСтрока, прошедшая через лексер:");
        for (Word w:lw) { System.out.println(w+" ;"); }

        //2) make NFA
        NFAMaker nfaMaker = new NFAMaker();
        nfaMaker.makeNFA(lw.get(0));

        //3)
        StringBuilder alphabet = new StringBuilder();
        for (Character c:nfaMaker.getAlphabet() ) {
            alphabet.append(" ").append(c);
        }
        System.out.println("\nВведите строку для распознавания из символов: {"+alphabet+" }");

        while (true){
            str = scanIn.nextLine();
            String newStr = str.replaceAll("["+alphabet+"]", "");
            if ( 0 < newStr.length()) {
                System.out.println("Использован неверный символ, попробуйте еще раз");
            }
            else{
                break;
            }
        }

        System.out.println("\nПроцесс разпознавания:");

        boolean finish = SyntaxAnalyzer.analyze(nfaMaker,str);
        if (!finish){System.out.println("Цепочка не была распознана");}
        else {System.out.println("Цепочка распознана, ура!!!");}
    }


}
