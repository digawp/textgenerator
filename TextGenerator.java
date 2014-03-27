package cs2020;

import java.io.*;

public class TextGenerator {

	final char NOCHARACTER = (char) (255);
	BufferedReader reader;
	MarkovModel model;
	int limit;
	StringBuilder stringBuffer = new StringBuilder(3);
	String start;
	
	TextGenerator(int k, int n, String text) throws IOException {
			model = new MarkovModel(text, k);
			reader = new BufferedReader(new FileReader(text));
			limit = n;
			
			for (int i = 0; i < k; i++) {
				stringBuffer.append((char)reader.read());
			}
			start = stringBuffer.toString();
	}
	
	String run() {
		int currentLength = model.order();
		StringBuilder output = new StringBuilder(start);
		while (currentLength <= limit) {
			char next = model.nextCharacter(stringBuffer.toString());
			if (next == NOCHARACTER) {
				next = model.nextCharacter(start);
			}
			output.append(next);
			stringBuffer.append(next);
			stringBuffer.deleteCharAt(0);
			currentLength++;
		}
		return stringBuffer.toString();
	}
	
	public static void main(String[] args) throws IOException {
		TextGenerator textgen = new TextGenerator(4, 20, "test.txt");
		System.out.println(textgen.run());
	}
	
}
