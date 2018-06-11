package ua.tor.test;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.tor.test.model.InputWord;
import ua.tor.test.model.InsertWordResponse;
import ua.tor.test.model.SearchResponse;
import ua.tor.test.service.WordService;
import ua.tor.test.utils.Permutations;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

	@Autowired
	private WordService wordService;

	/**
	 * The method checks if the input word has length so the number of possible combinations should
	 * me 120;
	 */
	@Test
	public void whenWordLengthFive_thenFactorialOneTwenty() {
		String inputWord = "words";
		assertEquals(Permutations.factorial(inputWord.length()), 120L);
	}


	/**
	 * Array of linked texts should have 5 combinations 3! - 1 where 1 is parent combination;
	 */
	@Test
	public void whenInputABC_thenOutPutFiveCombinations() {
		String inputWord = "abc";

		InputWord word = new InputWord();
		word.setWord(inputWord);
		InsertWordResponse insertWordRepsone = wordService.generateLinkedList(word);

		assertEquals(insertWordRepsone.getGeneratedWords().size(), 5);
	}

	/**
	 * When the input method gets combinations of "abc" Array of linked texts should have 5
	 * combinations 3! - 1 where 1 is parent combination. Then we sort arrays and compare them;
	 */
	@Test
	public void whenInputABC_thenCheckAllCombinations() {
		String inputWord = "abc";

		InputWord word = new InputWord();
		word.setWord(inputWord);
		InsertWordResponse insertWordRepsone = wordService.generateLinkedList(word);
		List<String> allPossibleCombinationsOfABC =
				Arrays.asList("acb", "bac", "bca", "cab", "cba");
		Collections.sort(insertWordRepsone.getGeneratedWords());
		Collections.sort(allPossibleCombinationsOfABC);

		assertEquals(insertWordRepsone.getGeneratedWords(), allPossibleCombinationsOfABC);
	}

	/**
	 * When we insert parent word as "abc" then search in linked test for the word cab the it should
	 * return the parent "abc";
	 * 
	 */
	@Test
	public void whenInputABC_thenSearchForCAB_ReturnABC() {
		String inputWord = "abc";
		InputWord word = new InputWord();
		word.setWord(inputWord);
		InsertWordResponse insertWordRepsone = wordService.generateLinkedList(word);
		SearchResponse searchResponse = wordService.search("cab");
		assertEquals(insertWordRepsone.getBaseWord(), searchResponse.getBaseWords().get(0));
	}
}
