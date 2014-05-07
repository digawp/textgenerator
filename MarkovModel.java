package cs2020;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * The class MarkovModel to generate the MarkovModel of a sequence of characters
 * of order k (passed to the constructor as argument)
 * 
 * @author Diga W
 * 
 */
public class MarkovModel {

	// A placeholder for the method nextCharacter() to inform that there is no
	// next character available for the specified sequence of characters
	final char NOCHARACTER = (char) (255);
	// The order of the Markov Model
	private int m_order;
	// The text
	private StringReader text;
	// The hash table to map a sequence of characters to its possible next
	// characters
	HashMap<String, int[]> hashmap = new HashMap<String, int[]>();
	// The randomizer for nextCharacter() method to randomly select the next
	// character
	Random randomizer = new Random();

	/**
	 * Constructor
	 * 
	 * @param text
	 *            text that the Markov Model is based on
	 * @param order
	 *            the order of the Markov Model, which is the number of
	 *            characters the next character prediciton is based on
	 * @throws IOException
	 */
	MarkovModel(String text, int order) throws IOException {
		m_order = order;
		if (text.length() < order) {
			throw new IOException("Text is too short to construct a Markov Model of order " + m_order + ".");
		}
		this.text = new StringReader(text);
		// See the method definition just below
		fillMap();

	}

	/**
	 * Fills the hash map with the sequence of characters and its possible set
	 * of next character
	 * 
	 * @throws IOException
	 */
	private void fillMap() throws IOException {

		// Uses StringBuilder as the stringBuffer as it is said to be more
		// efficient
		StringBuilder stringBuffer = new StringBuilder(3);

		// Fills the buffer with the first k characters of the text, where k is
		// the order of the Markov Model
		for (int i = 0; i < m_order; i++) {
			// TODO edit until here!			

			stringBuffer.append((char) text.read());

		}

		// The next character following the sequence of k characters
		int next = text.read();

		// Store the possible set of next character in an array of integer of
		// size 128 (the number of ASCII characters)
		int[] asciiArray = new int[128];

		// Increment the element that corresponds to that character
		asciiArray[next]++;

		// put the array into the hash map, mapped to the sequence of character
		// stored as string
		hashmap.put(stringBuffer.toString(), asciiArray);

		// For the rest of the text
		while (true) {

			// Updating stringBuffer, using some sort of 'rolling hash' (or is
			// it
			// indeed rolling hash?)
			stringBuffer.deleteCharAt(0);
			stringBuffer.append((char) next);

			// The next character that follows the sequence of k characters
			next = text.read();

			// Error checking on my side only, because some of the text sample I
			// used contains some characters that is outside the 128 ASCII
			// character, for whatever reason
			if (next > 127) {
				continue;
			} else if (next < 0) {
				break;
			}

			// store the string form of the buffer to avoid rebuilding the
			// string for the next few checks
			String key = stringBuffer.toString();

			if (hashmap.containsKey(key)) {
				// If the hash map already contain the key, retrieve the array
				asciiArray = hashmap.get(key);
			} else {
				// Else, create a new one
				asciiArray = new int[128];
			}

			// Increment the appropriate character in the array
			asciiArray[next]++;
			// Put into the hash map
			hashmap.put(key, asciiArray);

		}

	}

	/**
	 * Retrieves the order of the Markov Model and return it
	 * 
	 * @return
	 */
	int order() {
		return m_order;
	}

	/**
	 * Counts the frequency that the specified String appeared in the sample
	 * text supplied and return it
	 * 
	 * @param kgram
	 *            String to be counted
	 * @return
	 * @throws IllegalArgumentException
	 */
	int getFrequency(String kgram) throws IllegalArgumentException {

		// Error check
		if (kgram.length() != m_order) {
			throw new IllegalArgumentException(
					"Length of string kgram must be equal to order.");
		}

		// Retrieve the array of the specified character sequence in string form
		int[] asciiArray = hashmap.get(kgram);

		// Initialize the output
		int output = 0;

		// If the hashmap retrieves nothing, means the specified string does not
		// appear in the text
		if (asciiArray == null) {
			return 0;
		}

		// Count the number of times the string kgram appears
		for (int i = 0; i < asciiArray.length; i++) {
			output += asciiArray[i];
		}

		return output;

	}

	/**
	 * Counts the number of times the specified character follows the specified
	 * character sequence given in string form kgram
	 * 
	 * @param kgram
	 * @param c
	 * @return
	 */
	int getFrequency(String kgram, char c) {

		// Error check
		if (kgram.length() != m_order) {
			throw new IllegalArgumentException(
					"Length of string kgram must be equal to order.");
		}

		// Retrieve the array of the specified character sequence in string form
		int[] asciiArray = hashmap.get(kgram);

		// If the hashmap retrieves nothing, means the specified string does not
		// appear in the text
		if (asciiArray == null) {
			return 0;
		}

		return asciiArray[c];

	}

	/**
	 * Generates the next character from the given sequence of characters given
	 * as a string kgram
	 * 
	 * @param kgram
	 * @return
	 */
	char nextCharacter(String kgram) {

		// Error check
		if (kgram.length() != m_order) {
			throw new IllegalArgumentException(
					"Length of string kgram must be equal to order.");
		}

		// If the hashmap obtains null from the given string, means there is no
		// possible next character for the given string
		if (hashmap.get(kgram) == null) {
			// Returns the placeholder for no character
			return NOCHARACTER;
		}

		// Creates a clone of the array as I am going to modify it for deciding
		// the next character
		int[] asciiArray = hashmap.get(kgram).clone();

		// Initialize a list of candidate characters
		ArrayList<Character> candidates = new ArrayList<Character>();

		// Add the characters into the list according to the number of times it
		// appears after the given string in the text
		for (int i = 0; i < asciiArray.length; i++) {
			while (asciiArray[i] > 0) {
				candidates.add((char) i);
				asciiArray[i]--;
			}
		}

		// Generate a random number between 0 inclusive and the size of the candidates list exclusive
		int get = randomizer.nextInt(candidates.size());
		return candidates.get(get);

	}

	/**
	 * Sets a seed for the randomizer, for debugging purposes
	 * @param s
	 */
	void setRandomSeed(long s) {
		randomizer.setSeed(s);
	}

}
