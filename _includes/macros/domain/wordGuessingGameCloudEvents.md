{% capture java %}
public class WordGuessingGame {
	public static Stream<CloudEvent> startNewGame(String gameId, String wordToGuess) {	
		...
	}

	public static Stream<CloudEvent> guessWord(Stream<CloudEvent> eventStream, String word) {
		...
	}
}
{% endcapture %}  
{% capture kotlin %}
// Note that the functions could might as well be placed directly in a package 
 object WordGuessingGame {
    fun startNewGame(gameId : String, wordToGuess : String) : Stream<CloudEvent> = ...	
 
    fun guessWord(eventStream : Stream<CloudEvent>, word : String) : Stream<CloudEvent> = ...
 }
{% endcapture %}
{% include macros/docsSnippet.html java=java kotlin=kotlin %}