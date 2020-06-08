import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;

public class StoreMemoryMachine {

    private HashSet<Character> N ;
    private HashSet<Character> T;
    private static Multimap<Character, char[]> P;
    private Character S;
    private Multimap<Character, Character> FIRST;
    private Multimap<Character, Character> FOLLOW;
    private boolean result;


    public StoreMemoryMachine(HashSet<Character> n, HashSet<Character> t, Multimap<Character, char[]> p, Character s) {
        N = n;
        T = t;
        P = p;
        S = s;
        FIRST = ArrayListMultimap.create();
        FOLLOW = ArrayListMultimap.create();
    }

    public boolean checkIfLL1(){
        buildFIRST();
        buildFOLLOW();
        printSet();
        checkRule();
        return result;
    }


    private void buildFIRST(){

        for (Character character : T) {
            FIRST.put(character,character);
        }

        for (Character firstName :P.keySet()) {

            List<char[]> lastNames = (List<char[]>) P.get(firstName);
            Iterator iterator = lastNames.iterator();
            while (iterator.hasNext()){
                char[] ar = (char[]) iterator.next();
                FIRST.put(firstName, ar[0]);
            }
        }

        boolean flag = true;
        while(flag){
            flag = false;

            Multimap<Character, Character> FIRSTI = ArrayListMultimap.create();
            FIRSTI.putAll(FIRST);

            for (Character firstName :FIRST.keySet()) {

                List<Character> list = (List<Character>) FIRST.get(firstName);
                Iterator i = list.iterator();
                while (i.hasNext()){

                    Character current = (Character) i.next();

                    if(T.contains(current)){
                        continue;
                    }

                    flag=true;
                    List<Character> currentList = (List<Character>) FIRST.get(current);
                    if(!currentList.isEmpty()){
                        FIRSTI.remove(firstName,current);
                        FIRSTI.putAll(firstName ,currentList);
                    }else{
                        FIRSTI.remove(firstName,current);
                    }


                }


            }

            FIRST = FIRSTI;

        }



}

    private void buildFOLLOW(){

        boolean flag = true;
        while(flag){
            flag = false;

            for (Character firstName :P.keySet()) {

                List<char[]> list = (List<char[]>) P.get(firstName);
                Iterator i = list.iterator();
                while (i.hasNext()){

                    char[] current = (char[]) i.next();

                    if(current.length==1){
                        continue;
                    }

                    if(N.contains(current[1])){

                        switch (current.length){
                            case 3:
                                if(FIRST.containsKey(current[1]) && !FIRST.containsEntry(current[1],'e')){

                                    if(!FIRST.containsEntry(current[1],'e')){
                                        FOLLOW.putAll(current[1],FIRST.get(current[2]));
                                        break;
                                    }else if(FOLLOW.containsKey(current[1])){
                                        FOLLOW.putAll(current[1],FOLLOW.get(firstName));
                                        flag = true;
                                    }
                                }

                            case 2:
                                if(FOLLOW.containsKey(current[1])){
                                    FOLLOW.putAll(current[1],FOLLOW.get(firstName));
                                    flag = true;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }


            }


        }
    }


    private void checkRule(){

        result = true;

        for (Character firstName : P.keySet()) {
            List<char[]> lastNames = (List<char[]>) P.get(firstName);
            Iterator iterator = lastNames.iterator();

            Multimap<Integer, Character> set = ArrayListMultimap.create();
            int i = 0;

            while (iterator.hasNext()){

                char[] current = (char[]) iterator.next();
                for (char c : current) {
                    set.put(i,c);
                }
                i++;
            }

            for(int integer : set.keySet()){

                List<Character> c = (List<Character>) set.get(integer);
                if(c.isEmpty()){continue;}

                for(int integer1 : set.keySet()){

                    if(integer==integer1){continue;}

                    List<Character> c1 = (List<Character>) set.get(integer1);
                    if(c1.isEmpty()){continue;}

                    if(c.containsAll(c1) || c1.containsAll(c)){
                        result=false;
                        break;
                    }


                }

            }
        }

    }

    private void printSet(){
        System.out.println("\n____________________________________________");


        for (Character firstName : FIRST.keySet()) {
            List<Character> lastNames = (List<Character>) FIRST.get(firstName);
            Iterator iterator = lastNames.iterator();
            System.out.print("FIRST("+firstName + ") = { ");
            while (iterator.hasNext()){
                System.out.print(iterator.next()+" ");
            }
            System.out.println("}");
        }

        for (Character firstName : FOLLOW.keySet()) {
            List<Character> lastNames = (List<Character>) FOLLOW.get(firstName);
            Iterator iterator = lastNames.iterator();
            System.out.print("FOLLOW("+firstName + ") = { ");
            while (iterator.hasNext()){
                System.out.print(iterator.next()+" ");
            }
            System.out.println("}");
        }
    }

}
