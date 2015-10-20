import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by Deviltech on 20.10.2015.
 */
public class FastaReader {

    private ArrayList<Sequence> mySequences;
    private String filePath;
    private int lineBreak;
    private int space;

    public FastaReader(String myPath) {
        this.filePath = myPath;
        this.mySequences = new ArrayList<Sequence>();
        this.lineBreak = 60;
        this.space = 2;
    }

    public void readInFasta() throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(">")) {
                    Sequence currentSequence = new Sequence(line);
                    String sequenceString = "";
                    while (((line = br.readLine()) != null) && (!line.startsWith(">"))) {
                        sequenceString += line;
                    }
                    currentSequence.stringToSequence(sequenceString);
                    mySequences.add(currentSequence);
                }

            }

        }

    }

    public void printFasta() {

        ArrayList<String> myOutput = new ArrayList<String>();

        int maxDescription = 0;
        int maxSequence = 0;
        for (Sequence currentSequence : mySequences) {
            maxDescription= Math.max(maxDescription, currentSequence.descriptionSize());
            maxSequence = Math.max(maxSequence, currentSequence.sequenceSize());
        }
        int outputBlocks = maxSequence/lineBreak;
        for(int i = 0; i<=outputBlocks; i++){
            myOutput.add(StringUtils.repeat(" ", maxDescription + space) + new Integer(1 + i*lineBreak));
            for(Sequence currentSequence: mySequences){
                myOutput.add(currentSequence.getDescription() + StringUtils.repeat(" ", maxDescription + space - currentSequence.descriptionSize()) + currentSequence.toSubstring(i*lineBreak, i*lineBreak+lineBreak));
            }
            myOutput.add("");
        }

        for(String s : myOutput){
            System.out.println(s);
        }

    }

}
