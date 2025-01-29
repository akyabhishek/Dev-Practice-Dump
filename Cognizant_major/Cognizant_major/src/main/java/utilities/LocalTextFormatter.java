package utilities;


public class LocalTextFormatter {
	// Fomating the duration text
	public static String formatDuration(String value) {
		String[] values = value.split("·");
		String newValue = values[values.length - 1].trim();
		return newValue;

	}
	//Formatting of search result text, returns number of result as string
	public static String formatSearchResult(String value) {
		String[] values = value.split(" ");
		String newValue = values[0].trim();
		return newValue;
	}


}
