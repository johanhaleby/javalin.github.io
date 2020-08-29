---
layout: default
title: Documentation
rightmenu: false
permalink: /documentation
---

{% include notificationBanner.html %}

<div id="spy-nav" class="left-menu" markdown="1">
* [Introduction](#introduction)
* [Getting Started](#getting-started)
* [Choosing An EventStore](#choosing-an-eventstore)
* * [MongoDB](#mongodb)
* * * [Native Driver](#eventstore-with-mongodb-native-driver)
* * * [Spring (Blocking)](#eventstore-with-spring-mongotemplate-blocking) 
* * * [Spring (Reactive)](#eventstore-with-spring-reactivemongotemplate-reactive) 
* * [In-Memory](#in-memory)
* [HTTP Handlers](#handlers)
* * [Before](#before-handlers)
</div>

<h1 class="no-margin-top">Documentation</h1>

The documentation on this page is always for the latest version of Occurrent, currently `{{site.occurrentversion}}`.

<div class="notification star-us">
    <div>
        <span id="starUsLong">If you like Occurrent, please consider starring us on GitHub:</span>
        <span id="starUsShort">Like Occurrent? Star us on GitHub:</span>
    </div>
    <iframe id="starFrame" class="githubStar"
            src="https://ghbtns.com/github-btn.html?user=johanhaleby&amp;repo=occurrent&amp;type=star&amp;count=true&size=large"
            frameborder="0" scrolling="0" width="150px" height="30px">
    </iframe>
</div>

# Introduction

Occurrent is an event sourcing library, or if you wish, a set of event sourcing utilities for the JVM, created by [Johan Haleby](https://code.haleby.se/).
There are many options for doing event sourcing in Java already so why build something yourself? There are a few reasons for this besides the
intrinsic joy of doing something yourself: 
 
* You should be able to design your domain model without _any_ dependencies to Occurrent or any other library. Your domain model can be expressed with pure functions that returns events. Use Occurrent to store these events.
  This is a very important design decision! Many people talk about doing this, but I find it rare in practise, and some existing event sourcing frameworks makes this difficult or non-idiomatic.
* Simple: Pick only the libraries you need, no need for an all or nothing solution. If you don't need subscriptions, then don't use them!
* Occurrent is not a database by itself. The goal is to be a thing wrapper around existing commodity databases that you may already be familiar with.  
* Events are stored in a standard format ([cloud events](https://cloudevents.io/)). You are responsible for serializing/deserializing the cloud events "body" (data) yourself.
  While this may seem like a limitation at first, why not just serialize your POJO directly to arbitrary JSON that you're used to, it really enables a lot of use cases and piece of mind. For example:
  * It should be possible to hook in various standard components into Occurrent that understands cloud events. For example a component could visualize a distributed tracing graph from the cloud events
    if using the [distributed tracing cloud event extension](https://github.com/cloudevents/spec/blob/master/extensions/distributed-tracing.md).
  * Since the current idea is to be as close as possible to the specification even in the database,  
    you can use the database to your advantage. For example, you can create custom indexes used for fast and fully consistent domain queries directly on an event stream (or even multiple streams).
* Composable: Function composition and pipes are encouraged. For example pipe the event stream to a rehydration function (any function that converts a stream of events to current state) before calling your domain model.
* Pragmatic: Need consistent projections? You can decide to write projections and events transactionally using tools you already know (such as Spring `@Transactional`)! 
* Interopable/Portable: Cloud events is a [CNCF](https://www.cncf.io/) specification for describing event data in a common way. CloudEvents seeks to dramatically simplify event declaration and delivery across services, platforms, and beyond!
* Use the Occurrent components as lego bricks to compose your own pipelines. Components are designed to be small so that you should be able to re-write them tailored to your own needs if required. 
  Missing a component? You should be able to write one yourself and hook into the rest of the eco-system. Write your own problem/domain specific layer on-top of Occurrent.

# Getting started

Getting started with Occurrent involves these steps:
<div class="comment">It's recommended to read up on <a href="https://cloudevents.io/">CloudEvent's</a> and its <a href="https://github.com/cloudevents/spec/blob/v1.0/spec.md">specification</a> so that you're familiar with the structure and schema of a CloudEvent.</div>

1. Choose an underlying datastore for an [event store](#choosing-an-eventstore). Luckily there are only two choices at the moment, MongoDB and an in-memory implementation. Hopefully this will be a more difficult decision in the future :)
1. Once a datastore has been decided it's time to [choose an EventStore implementation](#choosing-an-eventstore) for this datastore since there may be more than one.
1. If you need subscriptions (i.e. the ability to subscribe to changes from an EventStore) then you need to pick a library that implements this for the datastore that you've chosen. 
   Again, there may be several implementations to choose from.
1. If a subscriber needs to be able to continue from where it left off on application restart, it's worth looking into a so called "position storage" library. 
   These libraries provide means to automatically (or selectively) store the position for a subscriber to a datastore. Note that the datastore that stores this position
   can be a different datastore than the one used as EventStore. For example, you can use MongoDB as EventStore but store subscription positions in Redis. 

# Choosing An EventStore

There are currently two different datastores to choose from, [MongoDB](#mongodb) and [In-Memory](#in-memory). 

## MongoDB

Uses MongoDB, version 4.2 or above, as  the underlying datastore for the CloudEvents. All implementations use transactions to guarantee consistent writes (see WriteCondition).
Each EventStore will automatically create a few indexes (TODO describe these) on startup to allow for fast consistent writes, optimistic concurrency and to avoid duplicated events.
These indexes can also be used in queries against the EventStore (see EventStoreQueries). 
 
There are three different MongoDB EventStore implementations to choose from:
* [Native Driver](#eventstore-with-mongodb-native-driver)
* [Spring (Blocking)](#eventstore-with-spring-mongotemplate-blocking) 
* [Spring (Reactive)](#eventstore-with-spring-reactivemongotemplate-reactive) 

### EventStore with MongoDB Native Driver

#### What is it?
An EventStore implementation that uses the "native" Java MongoDB synchronous driver (see [website](https://docs.mongodb.com/drivers/java)) to read and write
[CloudEvent's](https://cloudevents.io/) to MongoDB.

#### When to use?
Use when you don't need Spring support and want to use MongoDB as the underlying datastore.

#### Dependencies

{% include macros/eventstore/mongodb/native/maven.md %}

#### Getting Started

Once you've imported the dependencies you create a new instance of `org.occurrent.eventstore.mongodb.nativedriver.MongoEventStore`.
It takes four arguments, a [MongoClient](https://mongodb.github.io/mongo-java-driver/3.12/javadoc/com/mongodb/client/MongoClient.html), 
the "database" and "event collection "that the EventStore will use to store events as well as an `org.occurrent.eventstore.mongodb.nativedriver.EventStoreConfig`.

For example:  

{% include macros/eventstore/mongodb/native/example-configuration.md %}


Now you can start reading and writing events to the EventStore:

{% include macros/eventstore/mongodb/native/read-and-write-events.md %}

#### Examples

| Name  | Description  | 
|:----|:-----|  
| [Number&nbsp;Guessing&nbsp;Game](https://github.com/johanhaleby/occurrent/tree/master/example/domain/number-guessing-game/mongodb/native) | A simple game implemented using a pure domain model and stores events in MongoDB using `MongoEventStore`. It also generates integration events and publishes these to RabbitMQ. |


### EventStore with Spring MongoTemplate (Blocking)  

#### What is it?
An implementation that uses Spring's [MongoTemplate](https://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/core/MongoTemplate.html)
to read and write events to/from MongoDB.     

#### When to use?
If you're already using Spring and you don't need reactive support then this is a good choice. You can make use of the `@Transactional` annotation to write events and views in the same tx (but make sure you understand what you're going before attempting this).

#### Dependencies

{% include macros/eventstore/mongodb/spring/blocking/maven.md %}

#### Getting Started

Once you've imported the dependencies you create a new instance of `org.occurrent.eventstore.mongodb.spring.blocking.SpringBlockingMongoEventStore`.
It takes two arguments, a [MongoTemplate](https://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/core/MongoTemplate.html) and 
an `org.occurrent.eventstore.mongodb.spring.blocking.EventStoreConfig`.

For example:  

{% include macros/eventstore/mongodb/spring/blocking/example-configuration.md %}

Now you can start reading and writing events to the EventStore:

{% include macros/eventstore/mongodb/spring/blocking/read-and-write-events.md %}

#### Examples

| Name  | Description  | 
|:----|:-----|  
| [Number&nbsp;Guessing&nbsp;Game](https://github.com/johanhaleby/occurrent/tree/master/example/domain/number-guessing-game/mongodb/spring/blocking) | A simple game implemented using a pure domain model and stores events in MongoDB using `SpringBlockingMongoEventStore` and Spring Boot. It also generates integration events and publishes these to RabbitMQ. |
| [Subscription&nbsp;View](https://github.com/johanhaleby/occurrent/tree/master/example/projection/spring-subscription-based-mongodb-projections/src/main/java/org/occurrent/example/eventstore/mongodb/spring/subscriptionprojections) | An example showing how to create a subscription that listens to certain events stored in the EventStore and updates a view/projection from these events. |
| [Transactional&nbsp;View](https://github.com/johanhaleby/occurrent/tree/master/example/projection/spring-transactional-projection-mongodb/src/main/java/org/occurrent/example/eventstore/mongodb/spring/transactional) | An example showing how to combine writing events to the `SpringBlockingMongoEventStore` and update a view transactionally using the `@Transactional` annotation. | 
| [Custom&nbsp;Aggregation&nbsp;View](https://github.com/johanhaleby/occurrent/tree/master/example/projection/spring-adhoc-evenstore-mongodb-queries/src/main/java/org/occurrent/example/eventstore/mongodb/spring/projections/adhoc) | Example demonstrating that you can query the `SpringBlockingMongoEventStore` using custom MongoDB aggregations. |

### EventStore with Spring ReactiveMongoTemplate (Reactive)
  
#### What is it?
An implementation that uses Spring's [ReactiveMongoTemplate](https://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/core/ReactiveMongoTemplate.html)
to read and write events to/from MongoDB.     

#### When to use?
If you're already using Spring and want to use the reactive driver ([project reactor](https://projectreactor.io/)) then this is a good choice. It uses the `ReactiveMongoTemplate` to write events to MongoDB. You can make use of the `@Transactional` annotation to write events and views in the same tx (but make sure that you understand what you're going before attempting this).

#### Dependencies

{% include macros/eventstore/mongodb/spring/reactor/maven.md %}

#### Getting Started

Once you've imported the dependencies you create a new instance of `org.occurrent.eventstore.mongodb.spring.blocking.SpringBlockingMongoEventStore`.
It takes two arguments, a [MongoTemplate](https://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/core/MongoTemplate.html) and 
an `org.occurrent.eventstore.mongodb.spring.blocking.EventStoreConfig`.

For example:  

{% include macros/eventstore/mongodb/spring/reactor/example-configuration.md %}

Now you can start reading and writing events to the EventStore:

{% include macros/eventstore/mongodb/spring/reactor/read-and-write-events.md %}

#### Examples

| Name  | Description  | 
|:----|:-----|  
| [Custom&nbsp;Aggregation&nbsp;View](https://github.com/johanhaleby/occurrent/tree/master/example/projection/spring-adhoc-evenstore-mongodb-queries/src/main/java/org/occurrent/example/eventstore/mongodb/spring/projections/adhoc) | Example demonstrating that you can query the `SpringBlockingMongoEventStore` using custom MongoDB aggregations. |

## Handlers
Occurrent has three main handler types: before-handlers, endpoint-handlers, and after-handlers.
(There are also exception-handlers and error-handlers, but we'll get to them later).
The before-, endpoint- and after-handlers require three parts:

* A verb, one of: `before`, `get`, `post`, `put`, `patch`, `delete`, `after` <small>(... `head`, `options`, `trace`, `connect`)</small>
* A path, ex: `/`, `/hello-world`, `/hello/:name`
* A handler implementation `ctx -> { ... }`

The `Handler` interface has a void return type. You use `ctx.result()` to set the response which will be returned to the user.

### Before handlers
Before-handlers are matched before every request (including static files, if you enable those).
<div class="comment">You might know before-handlers as filters, interceptors, or middleware from other libraries.</div>

{% capture java %}
app.before(ctx -> {
    // runs before all requests
});
app.before("/path/*", ctx -> {
    // runs before request to /path/*
});
{% endcapture %}
{% capture kotlin %}
app.before { ctx ->
    // runs before all requests
}
app.before("/path/*") { ctx ->
    // runs before request to /path/*
}
{% endcapture %}
{% include macros/docsSnippet.html java=java kotlin=kotlin %}

### Endpoint handlers
Endpoint-handlers are matched in the order they are defined.
<div class="comment">You might know endpoint-handlers as routes or middleware from other libraries.</div>

{% capture java %}
app.get("/", ctx -> {
    // some code
    ctx.json(object);
});

app.post("/", ctx -> {
    // some code
    ctx.status(201);
});
{% endcapture %}
{% capture kotlin %}
app.get("/") { ctx ->
    // some code
    ctx.json(object)
}

app.post("/") { ctx ->
    // some code
    ctx.status(201)
}
{% endcapture %}
{% include macros/docsSnippet.html java=java kotlin=kotlin %}

Handler paths can include path-parameters. These are available via `ctx.pathParam("key")`:
{% capture java %}
app.get("/hello/:name", ctx -> {
    ctx.result("Hello: " + ctx.pathParam("name"));
});
{% endcapture %}
{% capture kotlin %}
app.get("/hello/:name") { ctx ->
    ctx.result("Hello: " + ctx.pathParam("name"))
}
{% endcapture %}
{% include macros/docsSnippet.html java=java kotlin=kotlin %}

Handler-paths can also include wildcard parameters, which are like unnamed path-parameters:

{% capture java %}
app.get("/hello/*", ctx -> {
    // capture all request to sub-paths of /hello/
});
{% endcapture %}
{% capture kotlin %}
app.get("/hello/*") { ctx ->
    // capture all request to sub-paths of /hello/
}
{% endcapture %}
{% include macros/docsSnippet.html java=java kotlin=kotlin %}