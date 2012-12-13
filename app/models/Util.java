package models;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * The Util class gathers diverse useful methods
 * @author Michael Akbaraly
 *
 */
public class Util
{
  /**
   * Normalize the search by removing special characters 
   * @return string without accents
   */
  public static String normalize(String string)
  {
	if (string != null)
	{
	  String temp = Normalizer.normalize(string, Normalizer.Form.NFD);
	  Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	  return pattern.matcher(temp).replaceAll("");	
	}
	else return "";
  }
}
