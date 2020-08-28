<div class="multitab-code dependencies" data-tab="1">
<ul>
    <li data-tab="1">Java</li>
    <li data-tab="2">Kotlin</li>
</ul>

<div data-tab="1" markdown="1">
~~~java
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
~~~
</div>

<div data-tab="2" markdown="1">
~~~kotlin
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
~~~
</div>
</div>
