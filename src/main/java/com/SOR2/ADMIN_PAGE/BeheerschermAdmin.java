package com.SOR2.ADMIN_PAGE;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.SOR2.SESSION.AuthenticatedWebPage;
import com.SOR2.hibernate.HibernateMain;
import com.SOR2.hibernate.InvallidMessage;
import com.SOR2.hibernate.Messages;

/**
 * Dit is een wicket WebPage klasse die als serverside controller voor het
 * beheerscherm dient
 * 
 * @author Jesse
 * @version 0.1.0
 *
 */
public class BeheerschermAdmin extends BeheerschermSjabloon implements
		AuthenticatedWebPage {

	private static final long serialVersionUID = 1L;
	private DataView<String[]> dataView;
	private AjaxPagingNavigator navigation;
	private WebMarkupContainer dataViewContainer;

	public BeheerschermAdmin(final PageParameters parameters) {
		super(parameters);

		// haal gegevens op om de tabel mee te vullen
		List currentData = retrieveInformation();

		// voer de methode uit die de tabel bouwt
		setInformation(currentData);
	}

	// Override the onclick voor de invalidmessagesButton
	@Override
	public void onClickInvalid(AjaxRequestTarget target) {
		// Methode die de elementen gaat aanpassen
		processInvalidMessageCall();

		// Voeg de dataViewContainer toe aan via de handler
		target.add(dataViewContainer);
	}

	// Override the onclick voor de validmessagesButton
	@Override
	public void onClickValid(AjaxRequestTarget target) {
		// haal gegevens op om de tabel mee te vullen
		List currentData = retrieveInformation();

		// voer de methode uit die de tabel bouwt
		setInformation(currentData);

		// Voeg de dataViewContainer toe aan via de handler
		target.add(dataViewContainer);
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

			// TODO dit kan sneller door éénmaal alle statussen op te halen
			textRow[3] = HibernateMain.getStatusWithStatus_ID(row.getStatus());
			textRow[4] = row.getReceiver();
			textRow[5] = row.getDate();
			// Voeg de String array toe aan de dataList
			dataList.add(textRow);
		}

		// DatalistProvider object met de dataList
		ListDataProvider<String[]> listDataProvider = new ListDataProvider<String[]>(
				dataList);

		// De table met de wicketid van de table rows
		dataView = new DataView<String[]>("rows", listDataProvider) {

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

		// PagingNavigator die gebruik maakt van ajax
		navigation = new AjaxPagingNavigator("pagingNavigator", dataView) {
			@Override
			protected void onAjaxEvent(AjaxRequestTarget target) {
				// Geeft opnieuw de dataViewContainer mee aan de target
				target.add(dataViewContainer);
			}
		};

		// check of de table al gerendered is
		if (dataViewContainer == null) {
			// Div die de tabel omringt
			dataViewContainer = new WebMarkupContainer("dataViewContainer");
			dataViewContainer.setOutputMarkupId(true);

			// Voeg de dataView aan de container toe
			dataViewContainer.add(dataView);
			// Voeg de navigatie toe aan de dataViewContainer
			dataViewContainer.add(navigation);
			// Voeg de dataViewContainer toe aan de pagina
			add(dataViewContainer);
		} else {
			dataViewContainer.replace(dataView);
			dataViewContainer.replace(navigation);
		}
	}

	/**
	 * Een methode die gegevens uit de database ophaald via de HibernateMain
	 * facade
	 */
	public List retrieveInformation() {
		List result = HibernateMain.getLazyMessages();
		return result;
	}

	private void processInvalidMessageCall() {
		setInformationInvalid(HibernateMain.getAllInvallidMessages());

	}

	private void setInformationInvalid(List data) {
		// Een nieuwe ArrayList
		List<String[]> dataList = new ArrayList<String[]>();

		// We vormen de bestaande List in een List met String arrays
		// Deze zijn gemakkelijker aan een tabel toe te voegen

		System.out.println(data.size());

		for (int i = 0; i < data.size(); i++) {
			// Messages object ophalen
			InvallidMessage row = (InvallidMessage) data.get(i);
			// Nieuwe String array maken
			String[] textRow = new String[6];
			// Alle data uit de map in de String array stoppen
			Object message_Id = row.getInvallidMessage_ID();

			textRow[0] = message_Id.toString();
			textRow[1] = row.getSender();
			textRow[2] = row.getSubject();
			textRow[3] = HibernateMain.getStatusWithStatus_ID(row.getStatus());
			textRow[4] = row.getReceiver();
			textRow[5] = row.getDate();
			// Voeg de String array toe aan de dataList
			dataList.add(textRow);
		}

		// DatalistProvider object met de dataList
		ListDataProvider<String[]> listDataProvider = new ListDataProvider<String[]>(
				dataList);

		// De table met de wicketid van de table rows
		dataView = new DataView<String[]>("rows", listDataProvider) {

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
		dataViewContainer.replace(dataView);

		// Opnieuw navigatie aanmaken voor de nieuw dataView
		navigation = new AjaxPagingNavigator("pagingNavigator", dataView) {
			@Override
			protected void onAjaxEvent(AjaxRequestTarget target) {
				// Geeft opnieuw de dataViewContainer mee aan de target
				target.add(dataViewContainer);
			}
		};

		dataViewContainer.replace(navigation);
	}

}
