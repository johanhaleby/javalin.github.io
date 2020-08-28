
<div class="multitab-code dependencies" data-tab="1">
<ul>
    <li data-tab="1">Maven</li>
    <li data-tab="2">Gradle</li>
    <li data-tab="3">SBT</li>
    <li data-tab="4">Grape</li>
    <li data-tab="5">Leiningen</li>
    <li data-tab="6">Buildr</li>
    <li data-tab="7">Ivy</li>
</ul>

<div data-tab="1" markdown="1">
~~~markup
<dependency>
    <groupId>org.occurrent</groupId>
    <artifactId>eventstore-spring-blocking</artifactId>
    <version>{{site.occurrentversion}}</version>
</dependency>
~~~
</div>

<div data-tab="2" markdown="1">
~~~java
compile 'org.occurrent:eventstore-spring-blocking:{{site.occurrentversion}}'
~~~
</div>

<div data-tab="3" markdown="1">
~~~java
libraryDependencies += "org.occurrent" % "eventstore-spring-blocking" % "{{site.occurrentversion}}"
~~~
</div>

<div data-tab="4" markdown="1">
~~~java
@Grab(group='org.occurrent', module='eventstore-spring-blocking', version='{{site.occurrentversion}}') 
~~~
</div>

<div data-tab="5" markdown="1">
~~~java
[org.occurrent/eventstore-spring-blocking "{{site.occurrentversion}}"]
~~~
</div>

<div data-tab="6" markdown="1">
~~~java
'org.occurrent:eventstore-spring-blocking:jar:{{site.occurrentversion}}'
~~~
</div>

<div data-tab="7" markdown="1">
~~~markup
<dependency org="org.occurrent" name="eventstore-spring-blocking" rev="{{site.occurrentversion}}" />
~~~
</div>

</div>


