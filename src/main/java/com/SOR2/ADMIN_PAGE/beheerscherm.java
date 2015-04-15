package com.SOR2.ADMIN_PAGE;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.SOR2.SESSION.AuthenticatedWebPage;
import com.SOR2.hibernate.HibernateMain;
import com.SOR2.hibernate.Messages;

/**
 * Dit is een wicket WebPage klasse die als serverside controller voor het
 * beheerscherm dient
 * 
 * @author Jesse
 * @version 0.1.0
 *
 */
public class beheerscherm extends WebPage implements AuthenticatedWebPage {

	private static final long serialVersionUID = 1L;

	public beheerscherm(final PageParameters parameters) {
		super(parameters);

		// haal gegevens op om de tabel mee te vullen
		List currentData = retrieveInformation();

		// voer de methode uit die de tabel bouwt
		setInformation(currentData);
		
		/*
		 * 
		 * Refresh button
		 * 
		 */
		// Ajax call voor de refresh button
		final AbstractDefaultAjaxBehavior refreshClickMethod = new AbstractDefaultAjaxBehavior() {
			@Override
			protected void respond(AjaxRequestTarget target) {
				// de pagina opnieuw renderen bij een refresh
				renderPage();
			}
		};
		add(refreshClickMethod);

		// De refresh button
		Button refresh = new Button("refresh") {
			@Override
			protected void onComponentTag(ComponentTag tag) {
				// voeg de ajax call toe aan de component tag om uit te voeren bij onMouseDown
				tag.put("onMouseDown", "Wicket.Ajax.get({'u':'"+ refreshClickMethod.getCallbackUrl() +"'});");
				super.onComponentTag(tag);
			}
		};
		refresh.setOutputMarkupId(true);
		add(refresh);
		
		/*
		 * 
		 * Logout button
		 * 
		 */
		final AbstractDefaultAjaxBehavior logoutClickMethod = new AbstractDefaultAjaxBehavior() {
			@Override
			protected void respond(AjaxRequestTarget target) {
				getSession().invalidate();
		        throw new RestartResponseException(beheerscherm.class);
			}
		};
		add(logoutClickMethod);
		
		Button logout = new Button("logout") {
			@Override
			protected void onComponentTag(ComponentTag tag) {
				// voeg de ajax call toe aan de component tag om uit te voeren bij onMouseDown
				tag.put("onMouseDown", "Wicket.Ajax.get({'u':'"+ logoutClickMethod.getCallbackUrl() +"'});");
				super.onComponentTag(tag);
			}
		};
		logout.setOutputMarkupId(true);
		add(logout);
	}

	/**
	 * Een methode die een list met messages wegschrijft naar een html table
	 * element
	 */
	public void setInformation(List data) {

		// Een nieuwe ArrayList
		List<String[]> dataList = new ArrayList<String[]>();
		// We vormen de bestaande List in een List met String arrays
		// Deze zijn gemakkelijker aan een tabel toe te voegen
		for (int i = 0; i < data.size(); i++) {
			// Messages object ophalen
			Messages row = (Messages) data.get(i);
			// Nieuwe String array maken
			String[] textRow = new String[6];
			// Alle data uit de map in de String array stoppen
			Object message_Id = row.getMessage_ID();
			textRow[0] = message_Id.toString();
			textRow[1] = row.getSender();
			textRow[2] = row.getSubject();
			textRow[3] = row.getMessage();
			textRow[4] = row.getReceiver();
			textRow[5] = row.getDate();
			// Voeg de String array toe aan de dataList
			dataList.add(textRow);
		}

		// DatalistProvider object met de dataList
		ListDataProvider<String[]> listDataProvider = new ListDataProvider<String[]>(
				dataList);

		// De table met de wicketid van de table rows
		DataView<String[]> dataView = new DataView<String[]>("rows",
				listDataProvider) {

			// Geeft aan hoe iedere rij moet worden gepopuleerd
			@Override
			protected void populateItem(Item<String[]> item) {
				String[] messagesArray = item.getModelObject();
				// Een rij
				RepeatingView repeatingView = new RepeatingView("dataRow");
				// Een loop om cellen aan de rij toe te voegen
				for (int i = 0; i < messagesArray.length; i++) {
					repeatingView.add(new Label(repeatingView.newChildId(),
							messagesArray[i]));
				}
				item.add(repeatingView);
			}
		};

		// Geeft aan hoeveel items er per page moeten worden weergegeven
		dataView.setItemsPerPage(15);

		add(dataView);
		add(new PagingNavigator("pagingNavigator", dataView));
	}

	/**
	 * Een methode die gegevens uit de database ophaald via de HibernateMain
	 * facade
	 */
	public List retrieveInformation() {
		List result = HibernateMain.getAllMail();
		return result;
	}

	/*
	 * Als de page al eens is gerendered zal de pagina helemaal opnieuw worden
	 * opgebouwt.
	 */
	@Override
	public void renderPage() {
		// TODO Auto-generated method stub
		if (hasBeenRendered()) {
			setResponsePage(getPageClass(), getPageParameters());
		} else {
			super.renderPage();
		}
	}
}