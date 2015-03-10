package com.SOR2.examples.FormTest;

import org.apache.wicket.util.io.IClusterable;
/**
 * Created by Willem on 3/4/2015.
 */
public class Message implements IClusterable{

    private String text;

    public Message(){

    }

    public Message(final Message message){
        text = message.text;
    }

    public void setMessage(String text){
        this.text = text;
    }

    public String getMessage(){
        return this.text;
    }
}
