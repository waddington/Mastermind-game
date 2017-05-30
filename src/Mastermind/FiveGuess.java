package Mastermind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FiveGuess {
    // Lists of all the possible combinations, and the combinations that the users combination could possibly be
    private List<String> allPossibleCombinations;
    private List<String> remainingCombinations;

    // My constructor calls a method to generate all 1296 combinations and then copies the list
    public FiveGuess() {
        this.allPossibleCombinations = new ArrayList<>();
        generateAllCombos("012345".toCharArray(), 4, new char[4], 0);
        this.remainingCombinations = new ArrayList<>(this.allPossibleCombinations);
    }

    // I used the following explanation of how this part of the algorithm works: http://cs.stackexchange.com/a/18756
    // It is much clearer than the following: https://en.wikipedia.org/wiki/Mastermind_(board_game)#Five-guess_algorithm
    public String getNextGuess() {
        // An int array that will store the highest overall of the lowest of each combination
        // I fill this array with the smallest value each int can hold
        int[] maxOfMinimums = new int[2];
        Arrays.fill(maxOfMinimums, Integer.MIN_VALUE);

        // Looping through all possible combinations
        for (String currentS: this.allPossibleCombinations) {
            // I create a new int array that will store the combination and index of the index with the least "goodness"
            // I fill the array with the largest values an int can hold
            int[] min = new int[2];
            Arrays.fill(min, Integer.MAX_VALUE);

            // Looping through all remaining possible combinations
            for (int j = 0; j<this.remainingCombinations.size(); j++) {
                // I get the feedback between the two combinations
                int[] feedback = getFeedback(currentS, this.remainingCombinations.get(j));
                // I get the "goodness" of this combination by counting how many other combinations from the remaining combinations this combination would eliminate if it were the guess
                // I call a method to do this
                int goodness = combinationElimination(this.remainingCombinations.get(j), feedback, false);
                // If the "goodness" of this combination is less than the current minimum I set the array to store the combination and its index
                if (goodness < min[0]) {
                    min[0] = goodness;
                    min[1] = j;
                }
            }
            // If the "goodness" of the lowest from that loop is higher than our overall highest, I store it
            if (min[0] > maxOfMinimums[0]) maxOfMinimums = min;
        }
        // I return the string that the index we stored points to
        // This is the next guess
        return this.remainingCombinations.get(maxOfMinimums[1]);
    }

    // Helper function that I will use to change characters in a string
    private String replaceCharInString(String s, int i, char c) {
        // It uses StringBuilder to easily change a specific character in a string
        StringBuilder tempString = new StringBuilder(s);
        tempString.setCharAt(i, c);
        // I return the new string
        return tempString.toString();
    }

    // This function will return a string array in the format:
    // Number of black blocks, string a, string b
    // This takes a boolean value indicating whether the method should change characters that it has already compared
    // It is used in this way by the getWhites() function
    private String[] getBlacks(String a, String b, boolean change) {
        int numBlacks = 0;
        // Loop through the strings and compare the characters at each index
        for (int i=0; i<a.length(); i++) {
            if (a.charAt(i) == b.charAt(i)) {
                numBlacks++;
                // If we want to change the characters we call a method to do so
                // We replace any characters in the first string with a '+' and the second string with a '-'
                if (change) {
                    a = replaceCharInString(a, i, '+');
                    b = replaceCharInString(b, i, '-');
                }
            }
        }
        // I return the data as a string array
        return new String[]{Integer.toString(numBlacks), a, b};
    }

    // This function will return a string array in the format:
    // Number of white blocks, string a, string b
    private String[] getWhites(String a, String b) {
        // We don't want to include any matches that are in the same position as they should return black blocks
        // So we remove those numbers from the string
        String[] changeBlacks = getBlacks(a, b, true);
        a = changeBlacks[1];
        b = changeBlacks[2];

        int numWhites = 0;
        // Double loop to compare every block to every other block
        for (int i=0; i<a.length(); i++) {
            for (int j=0; j<a.length(); j++) {
                if (a.charAt(i) == b.charAt(j)) {
                    // Change the blocks we have already compared so that we don't count them twice
                    a = replaceCharInString(a, i, '+');
                    b = replaceCharInString(b, j, '-');
                    // Increment the counter keeping track of how many white blocks there are
                    numWhites++;
                }
            }
        }
        // Return the data as a string array
        return new String[]{Integer.toString(numWhites), a, b};
    }

    // A method that gets the black and white feedback and collates it into an int array
    public int[] getFeedback(String a, String b) {
        // Wrapper to get b&w feedback
        return new int[]{Integer.parseInt(getBlacks(a,b,false)[0]), Integer.parseInt(getWhites(a,b)[0])};
    }

    // A method to evaluate whether two sets of feedback are different
    private boolean feedbackDifferent(int[] a, int[] b) {
        return (a[1] != b[1] || a[0] != b[0]);
    }

    // This method counts and removes (if required) how many other combinations would be removed based on the feedback between another combination and a guess
    public int combinationElimination(String realGuess, int[] realFeedback, boolean remove) {
        int eliminationCount = 0;
        int numberRemaining = this.remainingCombinations.size();
        // Looping through the remaining possible combinations
        for (int i=0; i<numberRemaining; i++) {
            // Getting feedback between the combination provided and the current combination
            int[] newFeedback = getFeedback(realGuess, this.remainingCombinations.get(i));
            // Checking if the new feedback is different to the feedback provided
            // If it is then incremenet the counter
            if (feedbackDifferent(realFeedback, newFeedback)) {
                eliminationCount++;
                // If we want to remove the combination from the remaining combinations we do so
                if (remove) this.remainingCombinations.remove(i--);
            }
            // Re-calculate the size of the list in case we removed an element so that our loop stays on track
            numberRemaining = this.remainingCombinations.size();
        }
        // Return the number of combinations eliminated
        return eliminationCount;
    }

    // Short recursive method to generate all combinations of a given length from a given set
    // Code taken from http://stackoverflow.com/questions/5504008/all-possible-words
    // I changed the for-loops to the simpler foreach loop
    private void generateAllCombos(char[] chars, int len, char[] build, int pos) {
        if (pos == len) {
            this.allPossibleCombinations.add(new String(build));
            return;
        }

        for (char c :chars) {
            build[pos] = c;
            generateAllCombos(chars, len, build, pos+1);
        }
    }
}
