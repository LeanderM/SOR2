package com.SOR2.examples.helloWorld;


import com.SOR2.examples.helloWorld.HelloWorld;
import org.apache.wicket.protocol.http.WebApplication;


public class HelloWorldApplication extends WebApplication{

    public HelloWorldApplication() {

    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class getHomePage (){
        return HelloWorld.class;
    }
}
