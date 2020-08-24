---
layout: default
title: Migration guide, v1 to v2
rightmenu: false
permalink: /migration-guide-occurrent-1-to-2
---

<h1 class="no-margin-top">Occurrent 1 to 2 migration guide</h1>

## Package structure
Occurrent 2 has some changes to the package structure:

* `org.occurrent.embeddedserver.jetty.websocket` `->` `org.occurrent.websocket`
* `org.occurrent.embeddedserver.Location` `->` `org.occurrent.staticfiles.Location`
* `org.occurrent.translator.json.OccurrentJsonPlugin` `->` `org.occurrent.json.OccurrentJson`
* `org.occurrent.translator.json.OccurrentJacksonPlugin` `->` `org.occurrent.json.OccurrentJackson`
* `org.occurrent.translator.template.OccurrentXyzPlugin` `->` `org.occurrent.rendering.OccurrentXyz`
* `org.occurrent.security.Role.roles` `->` `org.occurrent.security.SecurityUtil.roles`
* `org.occurrent.ApiBuilder` `->` `org.occurrent.apibuilder.ApiBuilder`
* `org.occurrent.ApiBuilder.EndpointGrooup` `->` `org.occurrent.apibuilder.EndpointGrooup`

## Server customization/defaults
```java
app.embeddedServer(new EmbeddedJettyFactory(() -> new Server())) // v1
app.server(() -> new Server()) // v2
```
* The static method `Occurrent.start(port)` has been removed. `Occurrent.create().start(0);` is now required.
* Dynamic gzip is now enabled by default, turn it off with `disableDynamicGzip()`
* Request-caching is now limited to 4kb by default
* Server now has a `LowResourceMonitor` attached by default
* `defaultCharset()` method has been removed
* URLs are now case-insensitive by default, meaning Occurrent will treat `/path` and `/Path` as the same URL.
  This can be disabled with `app.enableCaseSensitiveUrls()`.

## WebSockets
It was possible to defined WebSockets using Jetty annotations in v1 of Occurrent.
These Jetty WebSockets have limited functionality compared to the Occurrent lambda WebSockets,
which is why they have been removed.

## AccessManager
* Use `Set` instead of `List`
* The AccessManager now runs for every single request, but the default-implementation does nothing. This might break some implementations that relied on un-managed routes.

## Context
* The `CookieBuilder` class has been removed, use `Cookie` directly.
* `ctx.uri()` has been removed, it was a duplicate of `ctx.path()`
* Things that used to return `Array<T>` now return `List<T>`
* Things that used to return nullable collections now return empty collections instead
* `ctx.param()` is now `ctx.pathParam()`
* `ctx.xyzOrDefault("key")` methods have been change into `ctx.xyz("key", "default")`
* `ctx.next()` has been removed
* Kotlin users can now do `ctx.body<MyClass>()` to deserialize json
* `ctx.request()` is now `ctx.req`
* `ctx.response()` is now `ctx.res`
* All `ctx.renderXyz` methods are now just `ctx.render()` (correct engine is chosen based on extension)
* `ctx.charset(charset)` has been removed

## Events
* Event handlers no longer take `Event` as an argument (they now take nothing)
* `org.occurrent.event.EventType` is now `org.occurrent.OccurrentEvent`

## Misc
* Reverse routing has been removed (will come back, but better)
* `HaltException` has been removed and replaced with `HttpResponseException`. Some common responses are included:
  * RedirectResponse
  * BadRequestResponse
  * UnauthorizedResponse
  * ForbiddenResponse
  * NotFoundResponse
  * MethodNotAllowedResponse
  * InternalServerErrorResponse
  * ServiceUnavailableResponse
