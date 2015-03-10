package com.SOR2.examples.FormTest;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class MessageBoard extends WebPage{

    public static final List<Message> messageList = Collections.synchronizedList(new ArrayList<Message>());

    public MessageBoard() {
        add(new CommentForm("commentForm"));

        add(new PropertyListView<Message>("comments", messageList) {
            @Override
            public void populateItem(final ListItem<Message> listItem){
                listItem.add(new Label("text"));
            }
        }).setVersioned(false);
    }
}

    final class CommentForm extends Form<ValueMap>{

    public CommentForm(final String id) {

        // Constructor for form
        super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
        setMarkupId("CommentForm");
        add(new TextArea<String>("text").setType(String.class));
        add(new TextField<String>("comment").setType(String.class));
    }

    @Override
    public final void onSubmit(){
        ValueMap values = getModelObject();

        Message message = new Message();
        message.setMessage((String)values.get("text"));
        MessageBoard.messageList.add(0, message);

        values.put("text", "");
    }
}
