package Mastermind;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kai-w on 03/04/17.
 */
// My naive strategy class
public class Naive {
    // I store all of the possible combinations in a list so that I can easily get a new combination
    private List<String> allCombinations;

    // My constructor calls a method to generate all 1296 combinations and then copies the list
    public Naive() {
        // I initialize the list and generate all the combinations
        this.allCombinations = new ArrayList<>();
        generateAllCombos("012345".toCharArray(), 4, new char[4], 0);
    }

    // To get the next guess I simple return a guess from the list at an index related to the guess number
    // I perform some multiplication on the guess number so that there is more variation in the guesses
    public String getNextGuess(int guessNumber) {
        return allCombinations.get((guessNumber+6*guessNumber*guessNumber)%this.allCombinations.size());
    }

    // Short recursive method to generate all combinations of a given length from a given set
    // Code taken from http://stackoverflow.com/questions/5504008/all-possible-words
    // I changed the for-loops to the simpler foreach loop
    private void generateAllCombos(char[] chars, int len, char[] build, int pos) {
        if (pos == len) {
            this.allCombinations.add(new String(build));
            return;
        }

        for (char c :chars) {
            build[pos] = c;
            generateAllCombos(chars, len, build, pos+1);
        }
    }
}
