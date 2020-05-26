import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Checker {

    private HashSet<Character> N ;
    private HashSet<Character> T;
    private Multimap<String,Character> P;
    private Character S;
    private HashSet<Character> Ni;
    public boolean result;


    public Checker(HashSet<Character> n, HashSet<Character> t, Multimap<String,Character> p, Character s) {
        N = n;
        T = t;
        P = p;
        S = s;
        result = false;
        Ni = new HashSet<>();
    }

    public void checkContextFreeGrammar(){

        HashSet<Character> newNi = new HashSet<>();
        Multimap<String,Character> newP = ArrayListMultimap.create(P);

        while (!newP.isEmpty()){

            for (String alpha : P.keySet()) {

                if(!newP.containsKey(alpha)){
                    continue;
                }

                boolean flag = true;

                for (char c:alpha.toCharArray()) {
                    if (!Ni.contains(c) && !T.contains(c)) {
                        flag = false;
                        break;
                    }
                }

                if(flag || alpha.equals("e")){
                    List<Character> listA = (List<Character>) P.get(alpha);
                    newP.removeAll(alpha);
                    newNi.addAll(listA);
                }
            }

            System.out.print("N = { ");
            for (Character character : newNi) {
                System.out.print(character+" ");
            }
            System.out.println("}");

            if(!Ni.containsAll(newNi)){
                Ni.addAll(newNi);
                if(Ni.contains(S)){
                    result = Ni.contains(S);
                    return;
                }
            }
        }



    }

}
