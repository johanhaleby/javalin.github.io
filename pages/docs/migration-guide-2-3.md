---
layout: default
title: Migration guide, v2 to v3
rightmenu: false
permalink: /migration-guide-occurrent-2-to-3
---

<h1 class="no-margin-top">Occurrent 2 to 3 migration guide</h1>

## Configuration
One of the biggest new things in Occurrent 3 is the configuration setup.
Server config has been moved away from `Occurrent` and into a `OccurrentConfig` class, which is
available inside `Occurrent.create()`:

```java
Occurrent.create(config -> {
    config.addStaticFiles(directory)
    config.autogenerateEtags = true/false
    config.dynamicGzip = true/false
    config.enableDevLogging()
    config.enforceSsl = true/false
    config.requestCacheSize = sizeInBytes
    config.sessionHandler { ... }
    config.server { ... }
    config.accessManager { ... }
}).start()
```

A full list of the config options can be found [in the docs](/documentation#configuration).

## WebSockets
WebSockets used to have 5 separate interfaces with different signatures.
To make the WebSocket API more like the HTTP API, we've introduced `WsContext` in Occurrent 3.
Each WebSocket event now takes a `WsContext` (ctx), which also has access to the underlying `Context`
which was used to upgrade from HTTP to WebSocket. We've also added `wsBefore`, `wsAfter`, and `wsException`, and made the
`AccessManager` aware of WebSocket upgrade requests.

You can read more about the new WebSocket API [in the docs](/documentation#websockets).

## Events
Events have been changed from Enum to Config object:

```java
// BEFORE
Occurrent app = Occurrent.create()
    .event(OccurrentEvent.SERVER_STARTING, () -> { ... })
    .event(OccurrentEvent.SERVER_STARTED, () -> { ... })
    .event(OccurrentEvent.SERVER_START_FAILED, () -> { ... })
// AFTER
Occurrent app = Occurrent.create().events(event -> {
    event.serverStarting(() -> { ... });
    event.serverStarted(() -> { ... });
    event.serverStartFailed(() -> { ... });
});
```

## Package structure
Occurrent 3 has some changes to the package structure. The only class that's still on root (`org.occurrent`) is `Occurrent`.
Most of what used to be on the root has been moved into `org.occurrent.http` (this includes pacakges such as `staticfiles` and `serversentevents`)
Everything related to WebSockets has been moved into `org.occurrent.websocket`.
The `org.occurrent.security` package has been moved into `org.occurrent.core.security`. Plugins have been moved into `org.occurrent.plugin`.
