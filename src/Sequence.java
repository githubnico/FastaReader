import java.util.ArrayList;

/**
 * Created by Deviltech on 20.10.2015.
 */
public class Sequence {

    private String description;
    private ArrayList<Nucleotide> mySequence;

    // Constructor
    public Sequence(String description, ArrayList<Nucleotide> mySequence) {
        this.description = description;
        this.mySequence = mySequence;
    }

    // Constructor without existing sequence
    public Sequence(String description) {
        this.description = description;
        this.mySequence = new ArrayList<Nucleotide>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Nucleotide> getMySequence() {
        return mySequence;
    }

    public void setMySequence(ArrayList<Nucleotide> mySequence) {
        this.mySequence = mySequence;
    }

    // Adds a single nucleotide to the Sequence
    public void expand(Nucleotide n) {
        mySequence.add(n);
    }

    // Generates a nucleotide sequence from string
    public void stringToSequence(String s) {
        for (int i = 0; i < s.length(); i++) {
            mySequence.add(new Nucleotide(s.charAt(i)));
        }
    }

    // Returns the string of the nucleotide list
    public String toString() {
        String myString = "";
        for (Nucleotide myNucleotide : mySequence) {
            myString += myNucleotide.getSymbol();
        }
        return myString;
    }

    // Returns the substring of the nucleotide list in index range
    public String toSubstring(int start, int end) {
        String myString = "";
        // check for boundaries
        int lowest = Math.min(Math.max(start, 0), mySequence.size());
        int highest = Math.min(Math.max(end, 0), mySequence.size());
        for (int i = lowest; i < highest; i++) {
            myString += mySequence.get(i).getSymbol();
        }
        return myString;
    }

    public int descriptionSize() {
        return description.length();
    }

    public int sequenceSize() {
        return mySequence.size();
    }

}
