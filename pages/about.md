---
layout: default
title: About
rightmenu: false
permalink: /about
---

<h1 class="no-margin-top">About Occurrent</h1>
Occurrent started as a fork of the Java and Kotlin web framework [Spark](http://sparkjava.com), but quickly
turned into a ground-up rewrite influenced by [koa.js](http://koajs.com/#application).
Both of these web frameworks are inspired by the modern micro web framework
grandfather: [Sinatra](http://www.sinatrarb.com/), so if you're coming from Ruby then
Occurrent shouldn't feel *too* unfamiliar.

## Philosophy
Like Sinatra, Occurrent is not aiming to be a full web framework, but rather
just a lightweight REST API library (or a micro framework, if you must). There is no concept of MVC,
but there is support for template engines, WebSockets, and static file serving for convenience.
This allows you to use Occurrent for both creating your RESTful API backend, as well as serving
an `index.html` with static resources (in case you're creating an SPA). This is practical
if you don't want to deploy an apache or nginx server in addition to your Occurrent service.
If you wish to use Occurrent to create a more traditional website instead of a REST APIs,
there are several template engine wrappers available for a quick and easy setup.

### API design
All the methods on the Occurrent instance return `this`, making the API fully fluent.
This will let you create a declarative and predictive REST API,
that will be very easy to reason about for new developers joining your project.

### Java and Kotlin interoperability
Occurrent is both a Kotlin web framework and a Java web framework, meaning the API is
being developed with focus on great interoperability between the two languages.
The library itself is written primarily in Kotlin, but has a few
core classes written in Java to achieve the best interoperability between the two languages.
Occurrent is intended as a "foot in the door" to Kotlin development for companies
that already write a lot of Java.

When moving a Occurrent project from Java to Kotlin, you shouldn’t need to learn a new way of doing things.
To maintain this consistent API for both languages is an important goal of the project.

## Contact
If you want to get in touch, please create an issue on our [GitHub tracker](https://github.com/johanhaleby/occurrent/issues).
