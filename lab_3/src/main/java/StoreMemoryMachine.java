import com.google.common.collect.Multimap;

import java.util.*;

public class StoreMemoryMachine {

    private HashSet<Character> N ;
    private HashSet<Character> T;
    private Multimap<String,Character> P;
    private Character S;
    private HashSet<TreeNode> treeLevel;
    private boolean result;


    public StoreMemoryMachine(HashSet<Character> n, HashSet<Character> t, Multimap<String,Character> p, Character s) {
        N = n;
        T = t;
        P = p;
        S = s;
        treeLevel = new HashSet<>();
    }

    public boolean syntaxAnalyzer(String str){
        treeLevel.add(new TreeNode(str));
        printTreeLevel();
        while (!checkTreeLevel()){
            step();
            printTreeLevel();
        }
        return result;
    }


    private void step(){
        HashSet<TreeNode> newTreeLevel = new HashSet<>();

        for (TreeNode treeNode:treeLevel) {
            Deque<Character> word = new ArrayDeque<>(treeNode.getWord());
            String stack  = treeNode.getStack();

            //правило перемещения
            if(!word.isEmpty()) {
                Character c = word.getFirst();
                Deque<Character> newWord = new ArrayDeque<>(word);
                newWord.removeFirst();
                TreeNode newTreeNode = new TreeNode(newWord,stack.concat(String.valueOf(c)));
                newTreeLevel.add(newTreeNode);
            }

            //правило замещения
            for (String s : P.keySet()) {
                List<Character> list = (List<Character>) P.get(s);

                if(s.equals("e")){
                    for (Character character : list) {

                        int fromIndex = 0;
                        int Index = stack.length();
                        while(fromIndex < Index-1){
                            String newStack= stack.substring(0,fromIndex+1)
                                    +character
                                    +stack.substring(fromIndex+1,fromIndex+2);

                            TreeNode newTreeNode = new TreeNode(word,newStack);
                            newTreeLevel.add(newTreeNode);

                            fromIndex++;
                        }

                        String newStack= stack+character;
                        TreeNode newTreeNode = new TreeNode(word,newStack);
                        newTreeLevel.add(newTreeNode);

                    }
                }

                if(stack.contains(s)){
                    for (Character character : list) {

                        int fromIndex = 0;
                        int Index = stack.indexOf(s,fromIndex);
                        while(-1 != Index){
                            String newStack = stack.replaceFirst(s, String.valueOf(character));
                            TreeNode newTreeNode = new TreeNode(word,newStack);
                            newTreeLevel.add(newTreeNode);

                            fromIndex=Index+1;
                            Index=stack.indexOf(s,fromIndex);
                        }

                    }
                }

            }

            //если не то и не то, то пришли к тупику и просто ноду выкинули
        }
    treeLevel = newTreeLevel;
    }

    private boolean checkTreeLevel(){
        boolean flag = false;

        if(treeLevel.isEmpty()){
            result = false;
            return true;
        }

        for (TreeNode treeNode : treeLevel) {
            if(treeNode.getWord().isEmpty() &&
                    treeNode.getStack().equals(String.valueOf(S))){
                flag = true;
                result = true;
                break;
            }
        }
    return flag;
    }

    private void printTreeLevel(){
        System.out.println("\n____________________________________________");

        for (TreeNode treeNode:treeLevel){

            System.out.println(treeNode);

        }
    }

}
