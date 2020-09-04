{% capture java %}
public class WordGuessingGame {
	public static Stream<DomainEvent> startNewGame(String gameId, String wordToGuess) {	
		...
	}

	public static Stream<DomainEvent> guessWord(Stream<DomainEvent> eventStream, String word) {
		...
	}
}
{% endcapture %}  
{% capture kotlin %}
// Note that the functions could might as well be placed directly in a package.
// You might also want to use a Kotlin Sequence or List instead of a Stream
 object WordGuessingGame {
    fun startNewGame(gameId : String, wordToGuess : String) : Stream<CloudEvent> = ...	
 
    fun guessWord(eventStream : Stream<DomainEvent>, word : String) : Stream<DomainEvent> = ...
 }
{% endcapture %}
{% include macros/docsSnippet.html java=java kotlin=kotlin %}