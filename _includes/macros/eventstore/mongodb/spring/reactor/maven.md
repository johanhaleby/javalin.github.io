{% capture maven %}
<dependency>
    <groupId>org.occurrent</groupId>
    <artifactId>eventstore-spring-reactor</artifactId>
    <version>{{site.occurrentversion}}</version>
</dependency>
{% endcapture %}

{% capture gradle %}
compile 'org.occurrent:eventstore-spring-reactor:{{site.occurrentversion}}'
{% endcapture %}

{% capture sbt %}
libraryDependencies += "org.occurrent" % "eventstore-spring-reactor" % "{{site.occurrentversion}}"
{% endcapture %}

{% capture grape %}
@Grab(group='org.occurrent', module='eventstore-spring-reactor', version='{{site.occurrentversion}}') 
{% endcapture %}

{% capture leiningen %}
[org.occurrent/eventstore-spring-reactor "{{site.occurrentversion}}"]
{% endcapture %}

{% capture buildr %}
'org.occurrent:eventstore-spring-reactor:jar:{{site.occurrentversion}}'
{% endcapture %}

{% capture ivy %}
<dependency org="org.occurrent" name="eventstore-spring-reactor" rev="{{site.occurrentversion}}" />
{% endcapture %}
{% include macros/mavenSnippet.html maven=maven gradle=gradle sbt=sbt grape=grape leiningen=leiningen buildr=buildr ivy=ivy%}