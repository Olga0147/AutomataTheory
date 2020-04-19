
import java.util.Deque;
import java.util.HashSet;


public class SyntaxAnalyzer {

    public static boolean analyze(NFAMaker nfa, String str){

        HashSet<CurrentCondition> treeLevel = new HashSet<>();
        CurrentCondition step0 = new CurrentCondition(0,str);
        treeLevel.add(step0);
        while(!checkTree(treeLevel)){
            for (CurrentCondition c:treeLevel) {
                System.out.println("( "+c.getFrom()+" , "+c.getS()+" )");
            }
            System.out.println("----------------------------------------");
            treeLevel = step(nfa.getNfa(),treeLevel);
        }

        if(treeLevel.isEmpty()){
            for (CurrentCondition c:treeLevel) {System.out.println("( "+c.getFrom()+" , "+c.getS()+" )"); }
            return false;
        }
        else{
            for (CurrentCondition c:treeLevel) {
                if(c.getS().equals("")){System.out.println("( "+c.getFrom()+" , ε )   == Прочитали цепочку");}
                else{System.out.println("( "+c.getFrom()+" , "+c.getS()+" )");}
            }
            return true;
        }
    }

    private static HashSet<CurrentCondition> step(Deque<Path> nfa,HashSet<CurrentCondition> currentTreeLevel){

        HashSet<CurrentCondition> nextTreeLavel = new HashSet<>();

        for (CurrentCondition cc: currentTreeLevel) {
            if(cc.getS().length() == 0){continue;}
            char currentChar = cc.getS().charAt(0);

            for (Path p:nfa) {
                if(p.getFrom() == cc.getFrom()) {
                    if (p.getW() == currentChar) {

                        CurrentCondition nextCondition;
                        if(cc.getS().length()>1){
                            nextCondition = new CurrentCondition(p.getTo(), cc.getS().substring(1));
                        }
                        else{
                            nextCondition = new CurrentCondition(p.getTo(), "");
                        }
                        nextTreeLavel.add(nextCondition);

                    }
                    else if (p.getW() == '\0'){
                        CurrentCondition nextCondition = new CurrentCondition(p.getTo(), cc.getS());
                        nextTreeLavel.add(nextCondition);
                    }
                }
            }
        }

        return nextTreeLavel;
    }

    private static boolean checkTree(HashSet<CurrentCondition> currentTreeLevel){


        for (CurrentCondition cc: currentTreeLevel) {
                if(cc.getS().equals("")){
                    return true;
                }
        }

        return currentTreeLevel.isEmpty();
    }

}
