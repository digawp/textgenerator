package cs2020;

import java.io.*;

/**
 * A class that generates a string of n characters from the given text file to
 * be used as the Markov Model
 * 
 * @author Diga W
 * 
 */
public class TextGenerator {

	// The file reader
	BufferedReader reader;
	// The file writer
	BufferedWriter writer;
	// The Markov Model
	MarkovModel model;
	// The number of characters to generate
	int limit;
	// The string buffer
	StringBuilder stringBuffer = new StringBuilder(3);
	// The starting string, to be used in case there is no next character
	// available in the model
	String start;

	/**
	 * Constructor
	 * 
	 * @param k
	 *            the order of the Markov Model
	 * @param n
	 *            the number of characters to generate
	 * @param text
	 *            the text file the Markov Model is based on
	 * @throws IOException
	 */
	TextGenerator(int k, int n, String text) throws IOException {
		model = new MarkovModel(text, k);
		try {
			reader = new BufferedReader(new FileReader(text));
		} catch (IOException e) {
			throw new IOException("File not found. Please ensure the name of the file you wrote is correct.");
		}
		
		writer = new BufferedWriter(new FileWriter("output.txt"));
		limit = n;

		// Initialize the string buffer
		for (int i = 0; i < k; i++) {
			stringBuffer.append((char) reader.read());
		}
		reader.close();
		// Initialize the starting string.
		start = stringBuffer.toString();
	}

	/**
	 * Runs the Text Generator and returns the output string
	 * 
	 * @return
	 * @throws IOException 
	 */
	String run() throws IOException {

		// Initialize the output
		// Using the StringBuilder as it is said to be more efficient than keeps
		// concantenating characters to a String, which will keep creating a new
		// String each time it is concatenated
		StringBuilder output = new StringBuilder(start);

		// keeps track of the current length of the output
		int currentLength = model.order();

		// Repeat until the length of output is satisfied
		while (currentLength <= limit) {
			
			// The next character to append
			char next = model.nextCharacter(stringBuffer.toString());

			// Checks if it is a NOCHARACTER
			if (next == model.NOCHARACTER) {
				// If it is, starts from the beginning
				output.append(start);
				next = model.nextCharacter(start);
			}
			
			// Appends the next character to the StringBuilder output
			output.append(next);
			
			// Update the string buffer using 'rolling hash' method
			stringBuffer.append(next);
			stringBuffer.deleteCharAt(0);
			
			// increment current length
			currentLength++;
		}
		writer.write(output.toString());
		return output.toString();
	}

	public static void main(String[] args) throws IOException, IllegalArgumentException {
		// test.txt contains texts taken from http://obamaspeeches.com/
		if (args.length != 3) {
			throw new IllegalArgumentException("Wrong number of arguments.");
		}

		TextGenerator textgen = new TextGenerator(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2]);
		textgen.model.setRandomSeed(1);
		System.out.println(textgen.run());
	}

}
