---
layout: default
splash: true
permalink: /
---

<style>{% include landing.css %}</style>

<div class="landing bluepart">
    <h1>- Occurrent -<br>Event Sourcing Utilities <br>for the JVM</h1>
    <div class="center">
            <div class="whitetext">Based on the <a href="https://cloudevents.io/">cloud events</a> specification</div>
    </div>
    {% include macros/gettingStarted.md %}
    <div class="center">
        <a class="landing-btn" href="/documentation">Show me the docs</a>
    </div>
</div>

<div class="landing whitepart">
    <h1>What is Occurrent?</h1>
    <div class="boxes">
        <div class="box">
            <h3>Non-intrusive</h3>
            <p>
                You should be able to design your domain model without <i>any</i> dependencies to Occurrent or any other library. 
                Your domain model can be expressed with pure functions that returns events. Use Occurrent to store these events
            </p>
        </div>
        <div class="box">
            <h3>Simple</h3>
            <p>
                Pick only the libraries you need, no need for an all or nothing solution.
            </p>
        </div>
        <div class="box">
            <h3>Control</h3>
            <p>
                You should be in control! Magic is kept to a minimum and data is stored in a standard format (<a href="https://cloudevents.io/">cloud events</a>). 
                You are responsible for serializing/deserializing the cloud events "body" (data) yourself.
            </p>
        </div>
        <div class="box">
            <h3>Composable</h3>
            <p>
                Use the Occurrent components as lego bricks to compose your own pipelines. Components are designed to be small so that you should be able to re-write them tailored to your own needs if required. 
                Write your own problem/domain specific layer on-top of Occurrent.
            </p>
        </div>
        <div class="box">
            <h3>Library</h3>
            <p>
                Designed to be used as a library and not a framework to the greatest extent possible.
            </p>
        </div>
        <div class="box">
            <h3>Pragmatic</h3>
            <p>
                Need consistent projections? You can decide to write projections and events transactionally using tools you already know (such as Spring <code>@Transactional</code>)!
            </p>
        </div>
        <div class="box">
            <h3>Interoperable</h3>
            <p>
                Cloud events is a <a href="https://www.cncf.io/">CNCF</a> specification for describing event data in a common way. CloudEvents seeks to dramatically simplify event declaration and delivery across services, platforms, and beyond!
            </p>
        </div>
        <div class="box">
            <h3>Data Format</h3>
            <p>
                Since you know that events are stored as Cloud Events even in the database you can use the database to your advantage. For example you can create custom indexes used for fast and fully consistent domain queries directly on an event stream (or even multiple streams).
            </p>
        </div>
    </div>
</div>