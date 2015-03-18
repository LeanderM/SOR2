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
	public void setInformation(List data) {

		List<String[]> dataList = new ArrayList<String[]>();

		for (int i = 0; i < data.size(); i++) {
			Map row = (Map) data.get(i);
			String[] textRow = new String[4];
			textRow[0] = row.get("message_ID").toString();
			textRow[1] = row.get("sender").toString();
			textRow[2] = row.get("subject").toString();
			textRow[3] = row.get("message").toString();
			dataList.add(textRow);
		}

		// System.out.println(dataList.toString());
		// method loadCountriesFromCsv is defined elsewhere in the class.
		// It reads countries data from a csv file and returns each row as an
		// array of Strings.

		ListDataProvider<String[]> listDataProvider = new ListDataProvider<String[]>(
				dataList);

		// de TR van de table
		DataView<String[]> dataView = new DataView<String[]>("rows",
				listDataProvider) {

			@Override
			protected void populateItem(Item<String[]> item) {
				String[] messagesArray = item.getModelObject();
				// De TD's van de table
				RepeatingView repeatingView = new RepeatingView("dataRow");

				for (int i = 0; i < messagesArray.length; i++) {
					repeatingView.add(new Label(repeatingView.newChildId(),
							messagesArray[i]));
				}
				item.add(repeatingView);
			}
		};

		dataView.setItemsPerPage(15);

		add(dataView);
		// add(new PagingNavigator("pagingNavigator", dataView));
	}

	public List retrieveInformation() {

		String colom = "date, message_ID, sender, subject, message";
		String table = "messages";
		String whereClause = "1=1 ORDER BY date DESC";
		List result = HibernateMain.getSpecificSelection(colom, table,
				whereClause);
		return result;
	}
}
