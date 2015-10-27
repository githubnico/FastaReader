import java.io.IOException;
import java.util.List;

/**
 * Created by Deviltech on 20.10.2015.
 */
public class CommandLine {

    public static void main(String[] args) throws Exception {
        for (String s: args){
            FastaReader myFasta = new FastaReader(s);
            myFasta.readInFasta();
            List<String> result = myFasta.printFasta();
            for(String x: result){
                System.out.println(x);
            }
        }



    }
}
