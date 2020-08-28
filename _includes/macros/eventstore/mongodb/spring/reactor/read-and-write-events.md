{% capture java %}
CloudEvent event = CloudEventBuilder.v1()
                    .withId("eventId")
                    .withSource("urn:mydomain")
                    .withType("HelloWorld")
                    .withTime(LocalDateTime.now().atZone(ZoneOffset.UTC))
                    .withSubject("subject")
                    .withDataContentType("application/json")
                    .withData("{ \"message\" : \"hello\" }")
                    .build();

// Write                    
Mono<Void> mono = eventStore.write("streamId", Flux.just(event));

// Read
Mono<EventStream<CloudEvent>> eventStream = eventStore.read("streamId");
{% endcapture %}

{% capture kotlin %}
val event = CloudEventBuilder.v1()
                    .withId("eventId")
                    .withSource("urn:mydomain")
                    .withType("HelloWorld")
                    .withTime(LocalDateTime.now().atZone(ZoneOffset.UTC))
                    .withSubject("subject")
                    .withDataContentType("application/json")
                    .withData("{ \"message\" : \"hello\" }")
                    .build();

// Write                    
val mono = eventStore.write("streamId", Flux.just(event))

// Read
val eventStream : Mono<EventStream<CloudEvent>> = eventStore.read("streamId")
{% endcapture %}
{% include macros/docsSnippet.html java=java kotlin=kotlin %}