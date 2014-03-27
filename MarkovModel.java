package cs2020;

public class MarkovModel {

	final char NOCHARACTER = (char)(255);
	private int m_order;
	long seed;
	
	MarkovModel (String text, int order) {
		m_order = order;
		
	}
	
	int order() {
		return m_order;
	}
	
	int getFrequency(String kgram) throws IllegalArgumentException {
		if (kgram.length() != m_order) {
			throw new IllegalArgumentException("Length of string kgram must be equal to order.");
		}
		return 0;
	}
	
	int getFrequenct(String kgram, char c) {
		if (kgram.length() != m_order) {
			throw new IllegalArgumentException("Length of string kgram must be equal to order.");
		}
		return 0;
	}
	
	char nextCharacter(String kgram) {
		if (kgram.length() != m_order) {
			throw new IllegalArgumentException("Length of string kgram must be equal to order.");
		}
		return NOCHARACTER;
	}
	
	void setRandomSeed(long s) {
		seed = s;
	}
	
}
