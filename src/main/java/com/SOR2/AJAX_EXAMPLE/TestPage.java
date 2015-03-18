package com.SOR2.AJAX_EXAMPLE;

import javax.swing.text.SimpleAttributeSet;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import com.SOR2.*;


public class TestPage extends WebPage {

	private WebMarkupContainer textParent;
	private Label textLabel;
	private Label changePage;
	private AbstractDefaultAjaxBehavior onClickMethod;	
	private AbstractDefaultAjaxBehavior onClickMethod2;	
	private WebMarkupContainer clickItem;
	private String textVariabele = "dit is een test text";

	public TestPage(){
		
		// WebMarkupContainer element voor de parent <div> met textParent
		textParent = new WebMarkupContainer("textParent"){
			@Override
			protected void onComponentTag(ComponentTag tag) {
			}
		};
		
		// We maken een label aan voor het <p> element
		textLabel = new Label("text", new PropertyModel<String>(this, "textVariabele"));
		
		// het element is vindbaar via ajax
		textLabel.setOutputMarkupId(true);
		textParent.setOutputMarkupId(true);

		
		// We voegen het <p> label toe aan de <div> markupcontainer
		textParent.add(textLabel);
		
		//We voegen de WebMarkupContainer toe aan de pagina
		add(textParent);
		
		// Een methode die wordt aangeroepen op onclick van clickItem
		onClickMethod = new AbstractDefaultAjaxBehavior() {
			@Override
			protected void respond(AjaxRequestTarget target) {
			// We gebruiken de x en y parameters uit de requestheader en zetten deze in het label	
				// haal de parameters op
				IRequestParameters requestParameters = RequestCycle.get().getRequest().getRequestParameters();
				// haal de waarden van individuele parameters op
				String x = requestParameters.getParameterValue("x").toString();
				String y = requestParameters.getParameterValue("y").toString();
				// verander de text in het label
				textVariabele = "Position clicked =  X:"+ x + " _ Y:" + y;
				textLabel.setDefaultModelObject(textVariabele);
				
				// voeg opnieuw toe
				//target.add(textLabel);				
				// verander de achtergrondkleur van de parent
				textParent.add(new AttributeModifier("style", "background-color:green;"));
				// voeg het label opnieuw toe aan de parent
				textParent.add(textLabel);
				//replace de parent
				target.add(textParent);
			}	
			
		};
		add(onClickMethod);
		
		// WebMarkupContainer van de <div> met clickId
		clickItem = new WebMarkupContainer("clickId"){
			@Override
			protected void onComponentTag(ComponentTag tag) {
				clickItem.setOutputMarkupId(true);
				tag.put("style", "width:1000px;height:1000px;background-color:black;");
				// de jquery methode wicket.ajax.get van wicket
				tag.put("onMouseDown", "Wicket.Ajax.get({'u':'"+ onClickMethod.getCallbackUrl() +"&x='+event.pageX+' &y='+event.pageY+''});");
			}
			
		};
		clickItem.setOutputMarkupId(true);
		add(clickItem);
		
		// Een methode die wordt aangeroepen op onclick van changePage
		onClickMethod2 = new AbstractDefaultAjaxBehavior() {
			@Override
			protected void respond(AjaxRequestTarget target) {
				// We veranderen de tekst van de label (omdat het kan)
				changePage.setDefaultModelObject("changed");
				target.add(changePage);
				
				// voeg een stukje javascript toe aan het begin van de bestaande javascript
				target.prependJavaScript("window.location.href='"+ urlFor(HomePage.class, null) +"';");
				

			}	
			
		};
		add(onClickMethod2);
		
		// Een label met onClickMethod2
		changePage = new Label("changePage", "Klik hier om naar de HomePage te gaan"){
			@Override
			protected void onComponentTag(ComponentTag tag) {
				changePage.setOutputMarkupId(true);
				tag.put("onMouseDown", "Wicket.Ajax.get({'u':'"+ onClickMethod2.getCallbackUrl() +"'});");	
			}
		};
		changePage.setOutputMarkupId(true);
		add(changePage);
		
	}

}
