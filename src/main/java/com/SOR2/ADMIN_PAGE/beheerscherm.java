package com.SOR2.ADMIN_PAGE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.SOR2.hibernate.HibernateMain;

public class beheerscherm extends WebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List currentData;
	private DefaultDataTable table;
	private SortableDataProvider userProvider;

	public beheerscherm(final PageParameters parameters) {
		super(parameters);

		currentData = retrieveInformation();
		setInformation(currentData);

	}

	// voor tables: http://wicket.apache.org/guide/guide/repeaters.html
	// deze link beschrijft hoe je html tables kunt vullen met wicket
	public void setInformation(List data) {

		// Een nieuwe ArrayList
		List<String[]> dataList = new ArrayList<String[]>();
		// We vormen de bestaande List in een List met String arrays
		// Deze zijn gemakkelijker aan een tabel toe te voegen
		for (int i = 0; i < data.size(); i++) {
			// object in een Map stoppen
			Map row = (Map) data.get(i);
			// Nieuwe String array maken
			String[] textRow = new String[4];
			// Alle data uit de map in de String array stoppen
			textRow[0] = row.get("message_ID").toString();
			textRow[1] = row.get("sender").toString();
			textRow[2] = row.get("subject").toString();
			textRow[3] = row.get("message").toString();
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
		// add(new PagingNavigator("pagingNavigator", dataView));
	}

	// Vraagt de gegevens uit de database of via de facade
	public List retrieveInformation() {
		String colom = "date, message_ID, sender, subject, message";
		String table = "messages";
		String whereClause = "1=1 ORDER BY date DESC";
		List result = HibernateMain.getSpecificSelection(colom, table,
				whereClause);
		return result;
	}
}
