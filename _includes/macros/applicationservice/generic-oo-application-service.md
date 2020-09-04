{% capture java %}
public class ApplicationService {

    private final EventStore eventStore;
    private final Function<CloudEvent, DomainEvent> convertCloudEventToDomainEvent;
    private final Function<DomainEvent, CloudEvent> convertDomainEventToCloudEvent;

    public ApplicationService(EventStore eventStore, 
                              Function<CloudEvent, DomainEvent> convertCloudEventToDomainEvent, 
                              Function<DomainEvent, CloudEvent> convertDomainEventToCloudEvent) {
        this.eventStore = eventStore;
        this.convertCloudEventToDomainEvent = convertCloudEventToDomainEvent;
        this.convertDomainEventToCloudEvent = convertDomainEventToCloudEvent;
    }

    public void execute(String streamId, Function<Stream<DomainEvent>, Stream<DomainEvent>> functionThatCallsDomainModel) {
        // Read all events from the event store for a particular stream
        EventStream<CloudEvent> eventStream = eventStore.read(streamId);
        // Convert the cloud events into domain events
        Stream<DomainEvent> domainEventsInStream = eventStream.events().map(convertCloudEventToDomainEvent);

        // Call a pure function from the domain model which returns a Stream of domain events  
        Stream<DomainEvent> newDomainEvents = functionThatCallsDomainModel.apply(domainEventsInStream);

        // Convert domain events to cloud events and write them to the event store  
        eventStore.write(streamId, eventStream.version(), newDomainEvents.map(convertDomainEventToCloudEvent));
    }
}
{% endcapture %}

{% capture kotlin %}
class ApplicationService constructor (val eventStore : EventStore, 
                                      val convertCloudEventToDomainEvent : (CloudEvent) -> DomainEvent, 
                                      val convertDomainEventToCloudEvent : (DomainEvent) -> CloudEvent) {

    fun execute(streamId : String, functionThatCallsDomainModel : (Stream<DomainEvent>) -> Stream<DomainEvent>) {
        // Read all events from the event store for a particular stream
        val  eventStream : EventStream<CloudEvent> = eventStore.read(streamId)
        // Convert the cloud events into domain events
        val domainEventsInStream : Stream<DomainEvent> = eventStream.events().map(convertCloudEventToDomainEvent)

        // Call a pure function from the domain model which returns a Stream of domain events
        val newDomainEvents = functionThatCallsDomainModel(domainEventsInStream)

        // Convert domain events to cloud events and write them to the event store
        eventStore.write(streamId, eventStream.version(), newDomainEvents.map(convertDomainEventToCloudEvent))
    }
}
{% endcapture %}
{% include macros/docsSnippet.html java=java kotlin=kotlin %}
