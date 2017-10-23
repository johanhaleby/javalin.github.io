---
layout: default
category: misc
permalink: /kotlin-web-framework-comparison
date: 2017-10-22
title: Javalin and Ktor example comparison
---

<link rel="stylesheet" href="/css/framework-comparison.css">
<script src="/js/vue.min.js"></script>

<div id="bias-disclaimer">
    This page shows how to achieve basic web-server tasks in different Kotlin web frameworks.
    This purpose of this page is not to show that javalin is "better" than the other frameworks,
    it's just a tool to give you an impression syntax and application structure.
    <br><br>
    We rely on other people to provide the code for the non-javalin examples,
    so please create a pull-request if something seems wrong.
</div>
<div id="framework-comparison-vue" v-cloak>
    <div id="framework-toggle">
        <label class="tn-toggle">
            <input class="tn-toggle__checkbox" type="checkbox" v-model="show.javalin">
            <span class="tn-toggle__text">Javalin</span>
        </label>
        <label class="tn-toggle">
            <input class="tn-toggle__checkbox" type="checkbox" v-model="show.ktor">
            <span class="tn-toggle__text">Ktor</span>
        </label>
        <label class="tn-toggle">
            <input class="tn-toggle__checkbox" type="checkbox" v-model="show.http4k">
            <span class="tn-toggle__text">Http4k</span>
        </label>
    </div>
    <div id="code-examples">
        <div v-show="show.javalin || show.ktor || show.http4k">
            {% include framework-comparison/comparisonSnippet.html title="Hello World" filename="helloWorld" %}
            {% include framework-comparison/comparisonSnippet.html title="WehSocket echo server" filename="webSockets" %}
            {% include framework-comparison/comparisonSnippet.html title="Static files" filename="staticFiles" %}
            {% include framework-comparison/comparisonSnippet.html title="Filters" filename="filters" %}
            {% include framework-comparison/comparisonSnippet.html title="File uploads" filename="uploads" %}
        </div>
        <h3 v-show="!show.javalin && !show.javalin && !show.javalin">
            This feels a little pointless...
        </h3>
    </div>
</div>
<script>
    new Vue({
        el: "#framework-comparison-vue",
        data: {
            show: {
                javalin: true,
                ktor: false,
                http4k: false,
            }
        }
    })
</script>


