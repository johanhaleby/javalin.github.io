{% capture java %}

public class ApplicationService {

    public static void execute(EventStore eventStore, String streamId, Function<Stream<DomainEvent>, Stream<DomainEvent>> functionThatCallsDomainModel) {
        // Read all events from the event store for a particular stream
        EventStream<CloudEvent> eventStream = eventStore.read(streamId);
        Stream<CloudEvent> eventsInStream = eventStream.events();

        // Call a pure function from the domain model which returns a Stream of events  
        Stream<CloudEvent> newEvents = functionThatCallsDomainModel.apply(eventsInStream);

        // Write the new events to the event store  
        eventStore.write(streamId, eventStream.version(), newEvents);
    }
}
{% endcapture %}

{% capture kotlin %}
fun execute(eventStore : EventStore, streamId : String, functionThatCallsDomainModel : (Stream<DomainEvent>) -> Stream<DomainEvent>) {
    // Read all events from the event store for a particular stream
    val eventStream : EventStream<CloudEvent> = eventStore.read(streamId)
    val domainEventsInStream : Stream<CloudEvent> = eventStream.events()

    // Call a pure function from the domain model which returns a Stream of events
    val newEvents = functionThatCallsDomainModel(domainEventsInStream)

    // Write the new events to the event store 
    eventStore.write(streamId, eventStream.version(), newEvents)
}
{% endcapture %}
{% include macros/docsSnippet.html java=java kotlin=kotlin %}
