{% capture java %}
MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
// How the CloudEvent "time" property will be serialized in MongoDB! !!Important!!
EventStoreConfig config = new EventStoreConfig(TimeRepresentation.RFC_3339_STRING);
String mongoDatabase = "database";
String mongoEventCollection = "events";

MongoEventStore eventStore = new MongoEventStore(mongoClient, mongoDatabase, mongoEventCollection, config);
{% endcapture %}

{% capture kotlin %}
val mongoClient = MongoClients.create("mongodb://localhost:27017")
// How the CloudEvent "time" property will be serialized in MongoDB! !!Important!!
val config = EventStoreConfig(TimeRepresentation.RFC_3339_STRING)
val mongoDatabase = "database"
val mongoEventCollection = "events"

val eventStore = MongoEventStore(mongoClient, mongoDatabase, mongoEventCollection, config)
{% endcapture %}
{% include macros/docsSnippet.html java=java kotlin=kotlin %}