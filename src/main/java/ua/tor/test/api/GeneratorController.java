package ua.tor.test.api;

import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.tor.test.model.InputWord;
import ua.tor.test.service.WordService;

/**
 * 
 * @author Alexander Torchynskyi
 *
 */
@RestController
@RequestMapping(value = "/api")
public class GeneratorController {

	private static Logger logger = LoggerFactory.getLogger(GeneratorController.class);

	@Autowired
	private WordService wordService;

	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody @Valid InputWord word) {
		logger.info("creating new parent word {}", word);
		return Optional.of(wordService.generateLinkedList(word)).map(k -> ResponseEntity.ok(k))
				.orElseThrow(() -> new IllegalArgumentException());
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<?> search(@RequestParam String word) {
		logger.info("getting parents by the search word  {}", word);
		return Optional.of(wordService.search(word)).map(k -> ResponseEntity.ok(k))
				.orElseThrow(() -> new IllegalArgumentException());
	}
}
