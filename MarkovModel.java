package cs2020;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MarkovModel {

	final char NOCHARACTER = (char) (255);
	private int m_order;
	BufferedReader reader;
	HashMap<String, int[]> hashmap = new HashMap<String, int[]>();
	Random randomizer = new Random();

	MarkovModel(String text, int order) throws IOException {
		m_order = order;

		reader = new BufferedReader(new FileReader(text));
		fillMap();
		reader.close();

	}

	private void fillMap() throws IOException {
		// TODO Auto-generated method stub
		StringBuilder stringBuffer = new StringBuilder(3);

		for (int i = 0; i < m_order; i++) {
			if (!reader.ready()) {
				throw new IOException(
						"Text length is too short to construct a Markov Model of order"
								+ m_order + ".");
			}
			stringBuffer.append((char)reader.read());
		}
		int next = reader.read();
		int[] asciiArray = new int[128];
		asciiArray[next]++;
		
		hashmap.put(stringBuffer.toString(), asciiArray);
		
		while (reader.ready()) {
			
			// Updating stringBuffer
			stringBuffer.deleteCharAt(0);
			stringBuffer.append((char)next);
			
			next = reader.read();
			
			String key = stringBuffer.toString();
			
			if (hashmap.containsKey(key)) {
				asciiArray = hashmap.get(key);
			} else {
				asciiArray = new int[128];
			}
			
			asciiArray[next]++;
			hashmap.put(key, asciiArray);
			
		}

	}

	int order() {
		return m_order;
	}

	int getFrequency(String kgram) throws IllegalArgumentException {
		if (kgram.length() != m_order) {
			throw new IllegalArgumentException(
					"Length of string kgram must be equal to order.");
		}
		
		int[] asciiArray = hashmap.get(kgram);
		
		int output = 0;
		
		if (asciiArray == null) {
			return 0;
		}
		
		for (int i = 0; i < asciiArray.length; i++) {
			output += asciiArray[i];
		}
		return output;
		
	}

	int getFrequency(String kgram, char c) {
		if (kgram.length() != m_order) {
			throw new IllegalArgumentException(
					"Length of string kgram must be equal to order.");
		}

		int[] asciiArray = hashmap.get(kgram);
		
		if (asciiArray == null) {
			return 0;
		}
		
		return asciiArray[c];
		
	}

	char nextCharacter(String kgram) {
		if (kgram.length() != m_order) {
			throw new IllegalArgumentException(
					"Length of string kgram must be equal to order.");
		}
		int[] asciiArray = hashmap.get(kgram);
		if (asciiArray == null) {
			return NOCHARACTER;
		}
		ArrayList<Character> candidates = new ArrayList<Character>();
		for (int i = 0; i < asciiArray.length; i++) {
			while (asciiArray[i] > 0) {
				candidates.add((char) i);
				asciiArray[i]--;
			}
		}
		
		return candidates.get(randomizer.nextInt(candidates.size()));
		
	}

	void setRandomSeed(long s) {
		randomizer.setSeed(s);
	}

}
