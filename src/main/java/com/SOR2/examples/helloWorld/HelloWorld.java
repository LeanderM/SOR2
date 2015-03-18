package com.SOR2.examples.helloWorld;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Created by Willem on 3/4/2015.
 */
public class HelloWorld extends WebPage{

    public HelloWorld(){
        add(new Label("message", "Hello World"));
    }
}
