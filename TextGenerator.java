package cs2020;

import java.io.*;

public class TextGenerator {

	final char NOCHARACTER = (char) (255);
	BufferedReader reader;
	MarkovModel model;
	int limit;
	StringBuilder stringBuffer;
	String start;
	
	TextGenerator(int k, int n, String text) throws IOException {
			model = new MarkovModel(text, k);
			reader = new BufferedReader(new FileReader(text));
			limit = n;
			
			for (int i = 0; i < k; i++) {
				stringBuffer.append(reader.read());
			}
			start = stringBuffer.toString();
	}
	
	String run() {
		int current_length = model.order();
		
		while (current_length <= limit) {
			char next = model.nextCharacter(stringBuffer.toString());
			if (next == NOCHARACTER) {
				next = model.nextCharacter(start);
			}
			stringBuffer.append(next);
		}
		return stringBuffer.toString();
	}
	
	public static void main(String[] args) {
		
	}
	
}
