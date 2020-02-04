package ftn.xscience.utils.dom;

public class StringPathHandler {
	
	private static String fileSeparator = System.getProperty("file.separator");
	
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
}
