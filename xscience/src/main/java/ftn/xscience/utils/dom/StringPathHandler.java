package ftn.xscience.utils.dom;

import java.text.SimpleDateFormat;
import java.util.Date;

import ftn.xscience.model.user.TUser;

public class StringPathHandler {
	
	private static String fileSeparator = System.getProperty("file.separator");
	
	/* @params:
	 * 1 - sender
	 * 2 - receiver
	 * 3 - publication ID
	 * 4 - type of notification
	 * 5 - notification content
	 */
	public static final String EMAIL_TEMPLATE = "FROM: %1$s\nTO: %2$s\n\nAbout: %3$s\n\nType: %4$s\n\n\n%5$s";
	
	public static String handlePathSeparator(String schemaLocation, String contextPath) {
		
		String schemaPath = "";
		
		schemaLocation = schemaLocation.replace("/", fileSeparator);
		
		schemaPath = contextPath + schemaLocation;
		
		return schemaPath;
	}
	
	public static String formatEmailStringForDatabase(String originalEmail) {
		String formatted = "user-" + originalEmail.toLowerCase() + ".xml";
		formatted = formatted.replaceAll("\\@", "-");
		formatted = formatted.replace("-com", ".com");
		return formatted;
	}
	
	public static String formatPublicationNameForDatabase(String originalName) {
		originalName = originalName.replaceAll(" ", "_");
		return originalName;
	}
	
	public static String formatCLNameForDatabase(String original) {
		original = original.replaceAll(" ", "_");
		original = "cl-" + original + ".xml";
		return original;
	}
	
	public static String formatUserEmailForSparqlQuery(String originalEmail) {
		String formatted = formatEmailStringForDatabase(originalEmail);
		formatted = formatted.split(".xml")[0];
		formatted = "@\"" + formatted + "\"";
		return formatted;
	}
	
	public static String formatNameAddXMLInTheEnd(String original) {
		if (original.endsWith(".xml")) {
			return original;
		} else {
			original = original + ".xml";
		}
		return original;
	}
	
	public static String formatCurrentDateToString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		return sdf.format(now);
	}
	
	public static String formatNullDateToString() {
		return "1000-01-01";
	}
	
	public static String generateSenderRecieverForEmail(String firstName, String lastName, String email, String phone) {
		String ret = "";
		ret = lastName + ", " +
				firstName +
				"\n (" + email + ")\n" +
				phone;
		return ret;
	}
	
}
