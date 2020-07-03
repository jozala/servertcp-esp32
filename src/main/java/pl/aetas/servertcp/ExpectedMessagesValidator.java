package pl.aetas.servertcp;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ExpectedMessagesValidator {

	private static Logger LOG = LoggerFactory.getLogger(ExpectedMessagesValidator.class);


	private final List<Pattern> expectedMessagesMatches = Arrays.asList(Pattern.compile("1\\. .*"),
																		Pattern.compile("2\\. .*"),
												          				Pattern.compile("3\\. .*"),
												          				Pattern.compile("4\\. .*"),
												          				Pattern.compile("5\\. .*"),
																		Pattern.compile("6\\. .*"),
																		Pattern.compile("7\\. .*")
		);

	private int currentMessageIndex = 0;

	boolean validate(String receivedMessage) {
		Pattern expectedPattern = expectedMessagesMatches.get(currentMessageIndex);
		boolean matches = expectedPattern.matcher(receivedMessage).matches();

		if (matches) {
			currentMessageIndex++;
		} else {
			LOG.error("Message '{}' does not match expected pattern '{}'", receivedMessage, expectedPattern.pattern());
			Optional<Pattern> matchingPattern = expectedMessagesMatches.stream().filter(it -> it.matcher(receivedMessage).matches()).findFirst();
			if(matchingPattern.isPresent()) {
				int newCurrentMessageIndex = expectedMessagesMatches.indexOf(matchingPattern.get());
				LOG.info("Unexpected message matches another pattern. Moving currentMessageIndex to {}", newCurrentMessageIndex);
				currentMessageIndex = newCurrentMessageIndex;
				currentMessageIndex++;
			} else {
				LOG.error("Message does not match any of the specified patterns: '{}'", receivedMessage);
			}

		}
		if (currentMessageIndex >= expectedMessagesMatches.size()) {
			currentMessageIndex = 0;
		}
		return matches;
	}


}
