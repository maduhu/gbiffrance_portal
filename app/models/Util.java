package models;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Util
{
  /* Normalize the search by removing special characters */
  public static String normalize(String string)
  {
    String temp = Normalizer.normalize(string, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	return pattern.matcher(temp).replaceAll("");	
  }
}
