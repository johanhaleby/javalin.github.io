{% capture java %}
public class GameApplicationService {

    private final EventStore eventStore;
    private final Converter converter;

    public GameApplicationService(EventStore eventStore, Converter converter) {
        this.eventStore = eventStore;
        this.converter = converter;
    }

    public void play(UUID gameId, Function<Stream<GameEvent>, Stream<GameEvent>> functionThatCallsDomainModel) {
        // Read all events from the event store for a particular stream
        EventStream<CloudEvent> eventStream = eventStore.read(gameId.toString());
        // Convert the cloud events into domain events
        Stream<GameEvent> persistedGameEvents = eventStream.events().map(converter::toDomainEvent);

        // Call a pure function from the domain model which returns a Stream of domain events  
        Stream<GameEvent> newGameEvents = functionThatCallsDomainModel.apply(persistedGameEvents);

        // Convert domain events to cloud events and write them to the event store  
        eventStore.write(gameId.toString(), eventStream.version(), newGameEvents.map(converter::toCloudEvent));
    }
}
{% endcapture %}

{% capture kotlin %}
class GameApplicationService constructor (val eventStore : EventStore, val converter : Converter) {

    fun play(gameId : UUID, functionThatCallsDomainModel : (Stream<GameEvent>) -> Stream<GameEvent>) {
        // Read all events from the event store for a particular stream
        val  eventStream : EventStream<CloudEvent> = eventStore.read(gameId.toString())
        // Convert the cloud events into domain events
        val persistedGameEvents : Stream<GameEvent> = eventStream.events().map(converter::toDomainEvent)

        // Call a pure function from the domain model which returns a Stream of domain events
        val newGameEvents = functionThatCallsDomainModel(persistedGameEvents)

        // Convert domain events to cloud events and write them to the event store
        eventStore.write(gameId.toString(), eventStream.version(), newGameEvents.map(converter::toCloudEvent))
    }
}
{% endcapture %}
{% include macros/docsSnippet.html java=java kotlin=kotlin %}
