package ua.tor.test.model;

import java.util.List;

/**
 * 
 * @author Alexander Torchynskyi
 *
 */
public class InsertWordResponse {

	private String baseWord;
	List<String> generatedWords;

	public String getBaseWord() {
		return baseWord;
	}

	public void setBaseWord(String baseWord) {
		this.baseWord = baseWord;
	}

	public List<String> getGeneratedWords() {
		return generatedWords;
	}

	public void setGeneratedWords(List<String> generatedWords) {
		this.generatedWords = generatedWords;
	}
}
