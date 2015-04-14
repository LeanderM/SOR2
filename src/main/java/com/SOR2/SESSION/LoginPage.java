package com.SOR2.SESSION;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

/**
 * Log in pagina
 * 
 * @author Leander
 * @version 0.2.0
 *
 */
public class LoginPage extends WebPage {

	private static final long serialVersionUID = 1L;

	/**
	 * Defineer de 2 elementen van de login pagina: een feedback paneel en een
	 * login formulier.
	 */
	public LoginPage() {
		add(new FeedbackPanel("feedback"));
		add(new SignInForm("signInForm"));
	}

	/**
	 * Interne klasse die het inlog formulier defineerd
	 * 
	 * @author Leander
	 * @version 1.0.0
	 *
	 */
	public final class SignInForm extends Form<Void> {

		/**
		 * constante met de ID van het veld waar de gebruikersnaam in komt.
		 */
		private static final String USERNAME = "username";

		/**
		 * Contstante met de ID van het veld waar het wachtworod in komt.
		 */
		private static final String PASSWORD = "password";

		/**
		 * Bevat de ingevulde gegevens.
		 */
		private final ValueMap properties = new ValueMap();

		/**
		 * Voeg een veld toe voor de gebruikersnaam en het wachtwoord.
		 * 
		 * @param id
		 */
		public SignInForm(final String id) {
			super(id);

			add(new TextField<String>(USERNAME, new PropertyModel<String>(
					properties, USERNAME)));
			add(new PasswordTextField(PASSWORD, new PropertyModel<String>(
					properties, PASSWORD)));
		}

		/**
		 * Methode die uitgevoerd wordt als er op de submit knop gedrukt wordt.
		 */
		@Override
		public final void onSubmit() {
			// haal de huidige sessie op.
			BackendSession session = getMySession();

			// als het inloggen lukt...
			if (session.signIn(getUsername(), getPassword())) {

				// ga dan door naar de locatie waar de gebruiker heen probeerde
				// te gaan
				continueToOriginalDestination();

			} else {
				// inloggen mislukt. Zet error in de feedback panel
				String errormsg = getString("loginError", null,
						"Gebruikersnaam of wachtwoord incorrect");

				error(errormsg);
			}
		}

		/**
		 * Haal de ID van het wachtwoord veld op (niet inhoud)
		 * 
		 * @return ID van het wachtwoord veld
		 */
		private String getPassword() {
			return properties.getString(PASSWORD);
		}

		/**
		 * Haal de ID van het gebruikersnaam veld op (niet inhoud)
		 * 
		 * @return ID van het gebruikersnaam veld
		 */
		private String getUsername() {
			return properties.getString(USERNAME);
		}

		/**
		 * Haal de sessie op van de huidige gebruiker, ingelogd of niet.
		 * 
		 * @return De huidige sessie.
		 */
		private BackendSession getMySession() {
			return (BackendSession) getSession();
		}
	}
}
