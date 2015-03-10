package com.SOR2.examples.FormTest;


import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class FormApplication extends WebApplication{

    public FormApplication(){
    }

    @Override
    public Class<? extends Page> getHomePage(){
        return MessageBoard.class;
    }
}
