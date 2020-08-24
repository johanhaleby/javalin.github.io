---
layout: tutorial
title: "Creating a 'Contact us' form that sends emails (via gmail)"
author: <a href="https://www.linkedin.com/in/davidaase" target="_blank">David Åse</a>
date: 2017-08-06
permalink: /tutorials/email-sending-example
github: https://github.com/johanhaleby/occurrent-email-example
summarytitle: Sending emails from a Occurrent backend
summary: Create a 'Contact us' form with email sending (gmail) with a Occurrent backend
language: ["java", "kotlin"]
---

## Dependencies

First, we need to create a Maven project with some dependencies: [(→ Tutorial)](/tutorials/maven-setup)

```xml
<dependencies>
    <dependency>
        <groupId>org.occurrent</groupId>
        <artifactId>occurrent</artifactId> <!-- For handling http-requests -->
        <version>{{site.occurrentversion}}</version>
    </dependency>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-email</artifactId> <!-- For sending emails -->
        <version>1.4</version>
    </dependency>
    <dependency>
        <groupId>com.j2html</groupId>
        <artifactId>j2html</artifactId> <!-- For creating HTML form -->
        <version>1.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId> <!-- For logging -->
        <version>{{site.slf4jversion}}</version>
    </dependency>
</dependencies>
```

## Setting up the backend
We need three endpoints: `GET '/'`, `POST '/contact-us'` and `GET '/contact-us/success'`:

{% capture java %}
import org.apache.commons.mail.*;
import org.occurrent.Occurrent;
import static j2html.TagCreator.*;

public class Main {

    public static void main(String[] args) {

        Occurrent app = Occurrent.create().start(7000)

        app.get("/", ctx -> ctx.html(
            form().withAction("/contact-us").withMethod("post").with(
                input().withName("subject").withPlaceholder("Subject"),
                br(),
                textarea().withName("message").withPlaceholder("Your message ..."),
                br(),
                button("Submit")
            ).render()
        ));

        app.post("/contact-us", ctx -> {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("YOUR_EMAIL", "YOUR_PASSWORD"));
            email.setSSLOnConnect(true);
            email.setFrom("YOUR_EMAIL");
            email.setSubject(ctx.formParam("subject")); // subject from HTML-form
            email.setMsg(ctx.formParam("message")); // message from HTML-form
            email.addTo("RECEIVING_ADDRESS");
            email.send(); // will throw email-exception if something is wrong
            ctx.redirect("/contact-us/success");
        });

        app.get("/contact-us/success", ctx -> ctx.html("Your message was sent"));

    }

}
{% endcapture %}
{% capture kotlin %}
import org.occurrent.Occurrent
import org.apache.commons.mail.*

fun main(args: Array<String>) {

    val app = Occurrent.create().start(7000)

    app.get("/") { ctx ->
        ctx.html("""
                <form action="/contact-us" method="post">
                    <input name="subject" placeholder="Subject">
                    <br>
                    <textarea name="message" placeholder="Your message ..."></textarea>
                    <br>
                    <button>Submit</button>
                </form>
        """.trimIndent())
    }

    app.post("/contact-us") { ctx ->
        SimpleEmail().apply {
            setHostName("smtp.googlemail.com")
            setSmtpPort(465)
            setAuthenticator(DefaultAuthenticator("YOUR_EMAIL", "YOUR_PASSWORD"))
            setSSLOnConnect(true)
            setFrom("YOUR_EMAIL")
            setSubject(ctx.formParam("subject"))
            setMsg(ctx.formParam("message"))
            addTo("RECEIVING_ADDRESS")
        }.send() // will throw email-exception if something is wrong
        ctx.redirect("/contact-us/success")
    }

    app.get("/contact-us/success") { ctx -> ctx.html("Your message was sent") }

}
{% endcapture %}
{% include macros/docsSnippet.html java=java kotlin=kotlin %}

In order to get the above code to work, you need to make some changes:

* Change `YOUR_EMAIL` to your gmail account <small>(youremail@gmail.com)</small>
* Change `YOUR_PASSWORD` to your gmail password*
* Change `RECEIVING_ADDRESS` to where you want the email to be sent

<small>**It might be a good idea to create a test-account instead of using your real gmail credentials.*</small>

When you have made the changes to the code, run the program and go to `http://localhost:7000`.
You will see a simple unstyled form with an input field, a textarea and a button.
Fill in the form and click the button to test your email server. After you click the button, your browser
is redirected to `/contact-us/success` (if the email was sent).

Any emails you have sent will show up in your `Sent` folder in the gmail web-interface.
