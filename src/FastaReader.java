import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

    // read in fasta from filepath
    public void readInFasta() throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(">")) {
                    Sequence currentSequence = new Sequence(line.substring(1));
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

    public ArrayList<Sequence> getMySequences() {
        return mySequences;
    }

    public void setMySequences(ArrayList<Sequence> mySequences) {
        this.mySequences = mySequences;
    }

    // Print out fasta into command line
    public ArrayList<String> printFasta(ArrayList<Boolean> bools) {

        Boolean isHeaders = bools.get(0);
        Boolean isSequences = bools.get(1);
        Boolean isNumbering = bools.get(2);


        ArrayList<String> myOutput = new ArrayList<String>();

        int maxDescription = 0;
        int maxSequence = 0;
        for (Sequence currentSequence : mySequences) {
            maxDescription = Math.max(maxDescription, currentSequence.descriptionSize());
            maxSequence = Math.max(maxSequence, currentSequence.sequenceSize());
        }
        int outputBlocks = maxSequence / lineBreak;
        for (int i = 0; i <= outputBlocks; i++) {

            if (isNumbering) {
                Integer first = new Integer(1 + i * lineBreak);
                Integer second = new Integer(Math.min(i * lineBreak + lineBreak, maxSequence));

                String firstLine = repeat(" ", maxDescription + space) + first;
                firstLine += repeat(" ", (second - first.toString().length() - second.toString().length()) % lineBreak) + second.toString();
                myOutput.add(firstLine);
            }

            for (Sequence currentSequence : mySequences) {
                String lines;
                if (!isHeaders) {
                    lines = repeat(" ", maxDescription + space );
                } else {
                    lines = currentSequence.getDescription() + repeat(" ", maxDescription + space - currentSequence.descriptionSize());
                }
                if (isSequences) {
                    lines += currentSequence.toSubstring(i * lineBreak, i * lineBreak + lineBreak);
                }
                myOutput.add(lines);

            }
            myOutput.add("");
        }

        ArrayList<String> result = new ArrayList<String>();
        for (String s : myOutput) {
            result.add(s + "\n");
        }

        return result;


    }

    public ArrayList<String> printFasta() {

        return printFasta(new ArrayList<Boolean>(Arrays.asList(new Boolean[]{true, true, true})));
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // concatenates a String n times
    public static String repeat(String s, int i) {
        String result = "";
        for (int n = 0; n < i; n++) {
            result += s;
        }
        return result;
    }
}
