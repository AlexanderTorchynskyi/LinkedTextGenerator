package ua.tor.test.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.springframework.stereotype.Component;
import ua.tor.test.model.InputWord;
import ua.tor.test.model.SearchResponse;
import ua.tor.test.model.InsertWordResponse;
import ua.tor.test.utils.Permutations;

/**
 * 
 * @author Alexander Torchynskyi
 *
 */

@Component
public class WordService {

	/**
	 * this variable is used for skipping parent elements in sequence
	 */
	private static final int PARENT_ELEMENTS = 1;

	private List<String> linkedWords;
	private List<String> criteriadList;
	private Map<InputWord, List<String>> keys = new HashMap<>();
	private List<String> parents = new ArrayList<>();

	public InsertWordResponse generateLinkedList(InputWord word) {
		parents.add(word.getWord());
		InsertWordResponse wordResponse = new InsertWordResponse();
		wordResponse.setBaseWord(word.getWord());
		wordResponse.setGeneratedWords(getPermutations(word));

		keys.put(word, getPermutations(word));

		return wordResponse;
	}

	public SearchResponse search(String searchWord) {
		criteriadList = new ArrayList<>();

		char[] inputArr = searchWord.toCharArray();

		SearchResponse seachResponse = new SearchResponse();

		List<Character> inputSearchedSequence = IntStream.range(0, inputArr.length)
				.mapToObj(i -> inputArr[i]).sorted().collect(Collectors.toList());

		for (String parent : parents) {
			List<Character> parentSequence = IntStream.range(0, parent.length())
					.mapToObj(i -> parent.toCharArray()[i]).sorted().collect(Collectors.toList());
			if (parentSequence.equals(inputSearchedSequence)) {
				criteriadList.add(parent);
			}
		}
		seachResponse.setBaseWords(criteriadList);
		return seachResponse;
	}

	private List<String> getPermutations(InputWord word) {

		linkedWords = new ArrayList<>();
		long permutations = Permutations.factorial(word.getWord().length());

		List<Character> cList = IntStream.range(0, word.getWord().length())
				.mapToObj(i -> word.getWord().toCharArray()[i]).collect(Collectors.toList());

		LongStream.range(0, permutations).skip(PARENT_ELEMENTS).forEach(i -> {
			linkedWords.add(Permutations.permutation(i, cList).stream().map(String::valueOf)
					.collect(Collectors.joining()));
		});

		return linkedWords;
	}
}
