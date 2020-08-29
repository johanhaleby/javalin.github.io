{% capture maven %}
<dependency>
    <groupId>org.occurrent</groupId>
    <artifactId>eventstore-mongodb-native</artifactId>
    <version>{{site.occurrentversion}}</version>
</dependency>
{% endcapture %}

{% capture gradle %}
compile 'org.occurrent:eventstore-mongodb-native:{{site.occurrentversion}}'
{% endcapture %}

{% capture sbt %}
libraryDependencies += "org.occurrent" % "eventstore-mongodb-native" % "{{site.occurrentversion}}"
{% endcapture %}

{% capture grape %}
@Grab(group='org.occurrent', module='eventstore-mongodb-native', version='{{site.occurrentversion}}') 
{% endcapture %}

{% capture leiningen %}
[org.occurrent/eventstore-mongodb-native "{{site.occurrentversion}}"]
{% endcapture %}

{% capture buildr %}
'org.occurrent:eventstore-mongodb-native:jar:{{site.occurrentversion}}'
{% endcapture %}

{% capture ivy %}
<dependency org="org.occurrent" name="eventstore-mongodb-native" rev="{{site.occurrentversion}}" />
{% endcapture %}
{% include macros/mavenSnippet.html maven=maven gradle=gradle sbt=sbt grape=grape leiningen=leiningen buildr=buildr ivy=ivy%}