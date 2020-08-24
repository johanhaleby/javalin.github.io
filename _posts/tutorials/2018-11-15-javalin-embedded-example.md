---
layout: tutorial
title: "Embed Occurrent Into Servlet Container"
author: <a href="https://github.com/mvysny" target="_blank">Martin Vyšný</a>
github: https://github.com/johanhaleby/occurrent-tomcat-embed-example
date: 2018-11-15
summarytitle: Embed Occurrent Into Servlet Container
summary: Running Occurrent Embedded In Tomcat Without Jetty
language: kotlin
---

## What You'll Create
A WAR application which will contain Occurrent without Jetty. You can drop
this WAR file into any Servlet 3.0 container.

## Getting Started

The easiest way to get started is to clone the [occurrent-tomcat-embed-example](https://github.com/johanhaleby/occurrent-tomcat-embed-example)
example application:

```bash
git clone https://github.com/johanhaleby/occurrent-tomcat-embed-example
cd occurrent-tomcat-embed-example
./gradlew clean appRun
```

This will run Gradle Gretty plugin which in turn launches this WAR app in Tomcat.
When the server boots, you can access the REST endpoint simply by typing
this in your terminal, or opening http://localhost:8080/rest :

```bash
curl localhost:8080/rest/
```

## Looking At The Sources

The project is using Gradle to do standard stuff: declare the project as WAR and
uses the [Gradle Gretty Plugin](https://github.com/gretty-gradle-plugin/gretty)
to easily launch the WAR app in Tomcat (using the `appRun` task).

The interesting bit is the `dependencies` stanza which includes Occurrent but omits
the Jetty dependency:

```kotlin
dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.occurrent:occurrent:3.7.0") {
        exclude(mapOf("group" to "org.eclipse.jetty"))
        exclude(mapOf("group" to "org.eclipse.jetty.websocket"))
    }
    compile("org.slf4j:slf4j-simple:1.7.30")
}
```

The servlet itself is very simple:

```kotlin
@WebServlet(urlPatterns = ["/rest/*"], name = "MyRestServlet", asyncSupported = false)
class MyRestServlet : HttpServlet() {
    val occurrent: OccurrentServlet = Occurrent.createStandalone()
            .get("/rest") { ctx -> ctx.result("Hello!") }
            .servlet()

    override fun service(req: HttpServletRequest, resp: HttpServletResponse) {
        occurrent.service(req, resp)
    }
}
```

> Note: You must remember to use the `createStandalone()` function, which has been carefully
designed to make Occurrent not to depend on Jetty. Using `Occurrent.create()`
will make the WAR app fail to start with `java.lang.ClassNotFoundException: org.eclipse.jetty.server.Server`.
 

The Servlet container will automatically auto-discover the servlet (since it's annotated with `@WebServlet`);
any requests to the servlet will be directed straight to Occurrent which will then take care
of handling the request properly. Annotate your class with @MultipartConfig in order to populate UploadedFile or UploadedFiles servlet-request getters.
