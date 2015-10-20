import java.io.IOException;

/**
 * Created by Deviltech on 20.10.2015.
 */
public class CommandLine {

    public static void main(String[] args) throws IOException {
        for (String s: args){
            FastaReader myFasta = new FastaReader(s);
            myFasta.readInFasta();
            myFasta.printFasta();
        }


    }
}
