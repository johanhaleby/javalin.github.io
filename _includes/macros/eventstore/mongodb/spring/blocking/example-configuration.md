{% capture java %}
MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
MongoTransactionManager mongoTransactionManager = new MongoTransactionManager(new SimpleMongoClientDatabaseFactory(mongoClient, "database"));

MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "database");
EventStoreConfig eventStoreConfig = new EventStoreConfig.Builder()
                                                         // The collection where all events will be stored 
                                                        .eventStoreCollectionName("events")
                                                        .transactionConfig(mongoTransactionManager)
                                                        // How the CloudEvent "time" property will be serialized in MongoDB! !!Important!! 
                                                        .timeRepresentation(TimeRepresentation.RFC_3339_STRING)
                                                        .build();

SpringBlockingMongoEventStore eventStore = new SpringBlockingMongoEventStore(mongoTemplate, eventStoreConfig);
{% endcapture %}

{% capture kotlin %}
val mongoClient = MongoClients.create("mongodb://localhost:27017")
val mongoTransactionManager = MongoTransactionManager(SimpleMongoClientDatabaseFactory(mongoClient, "database"))

val mongoTemplate = MongoTemplate(mongoClient, "database")
val eventStoreConfig = EventStoreConfig.Builder()
                                        // The collection where all events will be stored
                                       .eventStoreCollectionName("events")
                                       .transactionConfig(mongoTransactionManager)
                                        // How the CloudEvent "time" property will be serialized in MongoDB! !!Important!!
                                       .timeRepresentation(TimeRepresentation.RFC_3339_STRING)
                                       .build()

val eventStore = SpringBlockingMongoEventStore(mongoTemplate, eventStoreConfig)
{% endcapture %}
{% include macros/docsSnippet.html java=java kotlin=kotlin %}