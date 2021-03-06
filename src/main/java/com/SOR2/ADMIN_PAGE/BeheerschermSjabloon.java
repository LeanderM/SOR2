package com.SOR2.ADMIN_PAGE;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.SOR2.SESSION.AuthenticatedWebPage;

/**
 * Het sjabloon voor het beheerscherm
 * 
 * @author Jesse
 * @version 0.1.0
 *
 */
public abstract class BeheerschermSjabloon extends WebPage implements
		AuthenticatedWebPage {

	public BeheerschermSjabloon(final PageParameters parameters) {
		super(parameters);

		// Ajax call voor de refresh button
		final AbstractDefaultAjaxBehavior refreshClickMethod = new AbstractDefaultAjaxBehavior() {
			@Override
			protected void respond(AjaxRequestTarget target) {
				// de pagina opnieuw renderen bij een refresh
				onClickRefresh(target);
			}
		};
		add(refreshClickMethod);

		// De refresh button
		Button refresh = new Button("refresh") {
			@Override
			protected void onComponentTag(ComponentTag tag) {
				// voeg de ajax call toe aan de component tag om uit te voeren
				// bij onMouseDown
				tag.put("onMouseDown", "Wicket.Ajax.get({'u':'"
						+ refreshClickMethod.getCallbackUrl() + "'});");
				super.onComponentTag(tag);
			}
		};
		refresh.setOutputMarkupId(true);
		add(refresh);

		/*
		 * 
		 * Logout button
		 */
		final AbstractDefaultAjaxBehavior logoutClickMethod = new AbstractDefaultAjaxBehavior() {
			@Override
			protected void respond(AjaxRequestTarget target) {
				onClickLogout(target);
			}
		};
		add(logoutClickMethod);

		Button logout = new Button("logout") {
			@Override
			protected void onComponentTag(ComponentTag tag) {
				// voeg de ajax call toe aan de component tag om uit te voeren
				// bij onMouseDown
				tag.put("onMouseDown", "Wicket.Ajax.get({'u':'"
						+ logoutClickMethod.getCallbackUrl() + "'});");
				super.onComponentTag(tag);
			}
		};
		logout.setOutputMarkupId(true);
		add(logout);

		/*
		 * 
		 * invalidMessages button
		 */
		final AbstractDefaultAjaxBehavior InvalidMessageClickMethod = new AbstractDefaultAjaxBehavior() {

			@Override
			protected void respond(AjaxRequestTarget target) {
				onClickInvalid(target);
			}

		};
		add(InvalidMessageClickMethod);

		Button invalidMessage = new Button("Invalid Messages") {

			@Override
			protected void onComponentTag(ComponentTag tag) {
				// voeg de ajax call toe aan de component tag om uit te voeren
				// bij onMouseDown
				tag.put("onMouseDown", "Wicket.Ajax.get({'u':'"
						+ InvalidMessageClickMethod.getCallbackUrl() + "'});");
				super.onComponentTag(tag);
			}
		};
		invalidMessage.setOutputMarkupId(true);
		add(invalidMessage);

		/*
		 * 
		 * validMessages button
		 */
		final AbstractDefaultAjaxBehavior validMessageClickMethod = new AbstractDefaultAjaxBehavior() {

			@Override
			protected void respond(AjaxRequestTarget target) {
				onClickValid(target);
			}

		};
		add(validMessageClickMethod);

		Button validMessage = new Button("Valid Messages") {

			@Override
			protected void onComponentTag(ComponentTag tag) {
				// voeg de ajax call toe aan de component tag om uit te voeren
				// bij onMouseDown
				tag.put("onMouseDown", "Wicket.Ajax.get({'u':'"
						+ validMessageClickMethod.getCallbackUrl() + "'});");
				super.onComponentTag(tag);
			}
		};
		validMessage.setOutputMarkupId(true);
		add(validMessage);
	}

	// Onclick voor de refreshButton
	public void onClickRefresh(AjaxRequestTarget target) {
		renderPage();
	}

	// Onclick voor de logoutButton
	public void onClickLogout(AjaxRequestTarget target) {
		getSession().invalidate();
		throw new RestartResponseException(BeheerschermRedirect.class);
	}

	// Onclick voor de invalidmessagesButton
	public abstract void onClickInvalid(AjaxRequestTarget target);

	// Onclick voor de validMessagesButton
	public abstract void onClickValid(AjaxRequestTarget target);

	/*
	 * Als de page al eens is gerendered zal de pagina helemaal opnieuw worden
	 * opgebouwt.
	 */
	@Override
	public void renderPage() {
		if (hasBeenRendered()) {
			setResponsePage(getPageClass(), getPageParameters());
		} else {
			super.renderPage();
		}
	}
}
