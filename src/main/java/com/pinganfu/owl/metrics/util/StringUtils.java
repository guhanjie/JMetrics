/** 
 * Project Name:		owl.metrics 
 * Package Name:	org.apache.hadoop.metrics2.util 
 * File Name:			StringUtils.java 
 * Create Date:		2016年10月9日 下午1:50:25 
 * Copyright (c) 2008-2016, 平安集团-平安付 All Rights Reserved.
 */  
package com.pinganfu.owl.metrics.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

import com.google.common.base.Preconditions;

/**
 * General string utils
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class StringUtils {
	  /**
	   * Converts all of the characters in this String to lower case with
	   * Locale.ENGLISH.
	   *
	   * @param str  string to be converted
	   * @return     the str, converted to lowercase.
	   */
	  public static String toLowerCase(String str) {
	    return str.toLowerCase(Locale.ENGLISH);
	  }

	  /**
	   * Converts all of the characters in this String to upper case with
	   * Locale.ENGLISH.
	   *
	   * @param str  string to be converted
	   * @return     the str, converted to uppercase.
	   */
	  public static String toUpperCase(String str) {
	    return str.toUpperCase(Locale.ENGLISH);
	  }

	  /**
	   * Compare strings locale-freely by using String#equalsIgnoreCase.
	   *
	   * @param s1  Non-null string to be converted
	   * @param s2  string to be converted
	   * @return     the str, converted to uppercase.
	   */
	  public static boolean equalsIgnoreCase(String s1, String s2) {
	    Preconditions.checkNotNull(s1);
	    // don't check non-null against s2 to make the semantics same as
	    // s1.equals(s2)
	    return s1.equalsIgnoreCase(s2);
	  }

	  /** The same as String.format(Locale.ENGLISH, format, objects). */
	  public static String format(final String format, final Object... objects) {
	    return String.format(Locale.ENGLISH, format, objects);
	  }

	  /**
	   * Format a percentage for presentation to the user.
	   * @param fraction the percentage as a fraction, e.g. 0.1 = 10%
	   * @param decimalPlaces the number of decimal places
	   * @return a string representation of the percentage
	   */
	  public static String formatPercent(double fraction, int decimalPlaces) {
	    return format("%." + decimalPlaces + "f%%", fraction*100);
	  }
	  
	  /**
	   * Given an array of strings, return a comma-separated list of its elements.
	   * @param strs Array of strings
	   * @return Empty string if strs.length is 0, comma separated list of strings
	   * otherwise
	   */
	  public static String arrayToString(String[] strs) {
	    if (strs.length == 0) { return ""; }
	    StringBuilder sbuf = new StringBuilder();
	    sbuf.append(strs[0]);
	    for (int idx = 1; idx < strs.length; idx++) {
	      sbuf.append(",");
	      sbuf.append(strs[idx]);
	    }
	    return sbuf.toString();
	  }

	  /**
	   * Given an array of bytes it will convert the bytes to a hex string
	   * representation of the bytes
	   * @param bytes
	   * @param start start index, inclusively
	   * @param end end index, exclusively
	   * @return hex string representation of the byte array
	   */
	  public static String byteToHexString(byte[] bytes, int start, int end) {
	    if (bytes == null) {
	      throw new IllegalArgumentException("bytes == null");
	    }
	    StringBuilder s = new StringBuilder(); 
	    for(int i = start; i < end; i++) {
	      s.append(format("%02x", bytes[i]));
	    }
	    return s.toString();
	  }

	  /** Same as byteToHexString(bytes, 0, bytes.length). */
	  public static String byteToHexString(byte bytes[]) {
	    return byteToHexString(bytes, 0, bytes.length);
	  }

	  /**
	   * Given a hexstring this will return the byte array corresponding to the
	   * string
	   * @param hex the hex String array
	   * @return a byte array that is a hex string representation of the given
	   *         string. The size of the byte array is therefore hex.length/2
	   */
	  public static byte[] hexStringToByte(String hex) {
	    byte[] bts = new byte[hex.length() / 2];
	    for (int i = 0; i < bts.length; i++) {
	      bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	    }
	    return bts;
	  }
	  /**
	   * 
	   * @param uris
	   */
	  public static String uriToString(URI[] uris){
	    if (uris == null) {
	      return null;
	    }
	    StringBuilder ret = new StringBuilder(uris[0].toString());
	    for(int i = 1; i < uris.length;i++){
	      ret.append(",");
	      ret.append(uris[i].toString());
	    }
	    return ret.toString();
	  }
	  
	  /**
	   * @param str
	   *          The string array to be parsed into an URI array.
	   * @return <tt>null</tt> if str is <tt>null</tt>, else the URI array
	   *         equivalent to str.
	   * @throws IllegalArgumentException
	   *           If any string in str violates RFC&nbsp;2396.
	   */
	  public static URI[] stringToURI(String[] str){
	    if (str == null) 
	      return null;
	    URI[] uris = new URI[str.length];
	    for (int i = 0; i < str.length;i++){
	      try{
	        uris[i] = new URI(str[i]);
	      }catch(URISyntaxException ur){
	        throw new IllegalArgumentException(
	            "Failed to create uri for " + str[i], ur);
	      }
	    }
	    return uris;
	  }
	  /**
	   * 
	   * Given a finish and start time in long milliseconds, returns a 
	   * String in the format Xhrs, Ymins, Z sec, for the time difference between two times. 
	   * If finish time comes before start time then negative valeus of X, Y and Z wil return. 
	   * 
	   * @param finishTime finish time
	   * @param startTime start time
	   */
	  public static String formatTimeDiff(long finishTime, long startTime){
	    long timeDiff = finishTime - startTime; 
	    return formatTime(timeDiff); 
	  }
	  
	  /**
	   * 
	   * Given the time in long milliseconds, returns a 
	   * String in the format Xhrs, Ymins, Z sec. 
	   * 
	   * @param timeDiff The time difference to format
	   */
	  public static String formatTime(long timeDiff){
	    StringBuilder buf = new StringBuilder();
	    long hours = timeDiff / (60*60*1000);
	    long rem = (timeDiff % (60*60*1000));
	    long minutes =  rem / (60*1000);
	    rem = rem % (60*1000);
	    long seconds = rem / 1000;
	    
	    if (hours != 0){
	      buf.append(hours);
	      buf.append("hrs, ");
	    }
	    if (minutes != 0){
	      buf.append(minutes);
	      buf.append("mins, ");
	    }
	    // return "0sec if no difference
	    buf.append(seconds);
	    buf.append("sec");
	    return buf.toString(); 
	  }
	  /**
	   * Formats time in ms and appends difference (finishTime - startTime) 
	   * as returned by formatTimeDiff().
	   * If finish time is 0, empty string is returned, if start time is 0 
	   * then difference is not appended to return value. 
	   * @param dateFormat date format to use
	   * @param finishTime fnish time
	   * @param startTime start time
	   * @return formatted value. 
	   */
	  public static String getFormattedTimeWithDiff(DateFormat dateFormat, 
	                                                long finishTime, long startTime){
	    StringBuilder buf = new StringBuilder();
	    if (0 != finishTime) {
	      buf.append(dateFormat.format(new Date(finishTime)));
	      if (0 != startTime){
	        buf.append(" (" + formatTimeDiff(finishTime , startTime) + ")");
	      }
	    }
	    return buf.toString();
	  }
	  
	  /**
	   * Returns an arraylist of strings.
	   * @param str the comma seperated string values
	   * @return the arraylist of the comma seperated string values
	   */
	  public static String[] getStrings(String str){
	    Collection<String> values = getStringCollection(str);
	    if(values.size() == 0) {
	      return null;
	    }
	    return values.toArray(new String[values.size()]);
	  }

	  /**
	   * Returns a collection of strings.
	   * @param str comma seperated string values
	   * @return an <code>ArrayList</code> of string values
	   */
	  public static Collection<String> getStringCollection(String str){
	    String delim = ",";
	    return getStringCollection(str, delim);
	  }

	  /**
	   * Returns a collection of strings.
	   * 
	   * @param str
	   *          String to parse
	   * @param delim
	   *          delimiter to separate the values
	   * @return Collection of parsed elements.
	   */
	  public static Collection<String> getStringCollection(String str, String delim) {
	    List<String> values = new ArrayList<String>();
	    if (str == null)
	      return values;
	    StringTokenizer tokenizer = new StringTokenizer(str, delim);
	    while (tokenizer.hasMoreTokens()) {
	      values.add(tokenizer.nextToken());
	    }
	    return values;
	  }

	  /**
	   * Splits a comma separated value <code>String</code>, trimming leading and trailing whitespace on each value.
	   * Duplicate and empty values are removed.
	   * @param str a comma separated <String> with values
	   * @return a <code>Collection</code> of <code>String</code> values
	   */
	  public static Collection<String> getTrimmedStringCollection(String str){
	    Set<String> set = new LinkedHashSet<String>(
	      Arrays.asList(getTrimmedStrings(str)));
	    set.remove("");
	    return set;
	  }
	  
	  /**
	   * Splits a comma separated value <code>String</code>, trimming leading and trailing whitespace on each value.
	   * @param str a comma separated <String> with values
	   * @return an array of <code>String</code> values
	   */
	  public static String[] getTrimmedStrings(String str){
	    if (null == str || str.trim().isEmpty()) {
	      return emptyStringArray;
	    }

	    return str.trim().split("\\s*,\\s*");
	  }

	  /**
	   * Trims all the strings in a Collection<String> and returns a Set<String>.
	   * @param strings
	   * @return set of string
	   */
	  public static Set<String> getTrimmedStrings(Collection<String> strings) {
	    Set<String> trimmedStrings = new HashSet<String>();
	    for (String string: strings) {
	      trimmedStrings.add(string.trim());
	    }
	    return trimmedStrings;
	  }

	  final public static String[] emptyStringArray = {};
	  final public static char COMMA = ',';
	  final public static String COMMA_STR = ",";
	  final public static char ESCAPE_CHAR = '\\';
	  
	  /**
	   * Split a string using the default separator
	   * @param str a string that may have escaped separator
	   * @return an array of strings
	   */
	  public static String[] split(String str) {
	    return split(str, ESCAPE_CHAR, COMMA);
	  }
	  
	  /**
	   * Split a string using the given separator
	   * @param str a string that may have escaped separator
	   * @param escapeChar a char that be used to escape the separator
	   * @param separator a separator char
	   * @return an array of strings
	   */
	  public static String[] split(
	      String str, char escapeChar, char separator) {
	    if (str==null) {
	      return null;
	    }
	    ArrayList<String> strList = new ArrayList<String>();
	    StringBuilder split = new StringBuilder();
	    int index = 0;
	    while ((index = findNext(str, separator, escapeChar, index, split)) >= 0) {
	      ++index; // move over the separator for next search
	      strList.add(split.toString());
	      split.setLength(0); // reset the buffer 
	    }
	    strList.add(split.toString());
	    // remove trailing empty split(s)
	    int last = strList.size(); // last split
	    while (--last>=0 && "".equals(strList.get(last))) {
	      strList.remove(last);
	    }
	    return strList.toArray(new String[strList.size()]);
	  }

	  /**
	   * Split a string using the given separator, with no escaping performed.
	   * @param str a string to be split. Note that this may not be null.
	   * @param separator a separator char
	   * @return an array of strings
	   */
	  public static String[] split(
	      String str, char separator) {
	    // String.split returns a single empty result for splitting the empty
	    // string.
	    if (str.isEmpty()) {
	      return new String[]{""};
	    }
	    ArrayList<String> strList = new ArrayList<String>();
	    int startIndex = 0;
	    int nextIndex = 0;
	    while ((nextIndex = str.indexOf(separator, startIndex)) != -1) {
	      strList.add(str.substring(startIndex, nextIndex));
	      startIndex = nextIndex + 1;
	    }
	    strList.add(str.substring(startIndex));
	    // remove trailing empty split(s)
	    int last = strList.size(); // last split
	    while (--last>=0 && "".equals(strList.get(last))) {
	      strList.remove(last);
	    }
	    return strList.toArray(new String[strList.size()]);
	  }
	  
	  /**
	   * Finds the first occurrence of the separator character ignoring the escaped
	   * separators starting from the index. Note the substring between the index
	   * and the position of the separator is passed.
	   * @param str the source string
	   * @param separator the character to find
	   * @param escapeChar character used to escape
	   * @param start from where to search
	   * @param split used to pass back the extracted string
	   */
	  public static int findNext(String str, char separator, char escapeChar, 
	                             int start, StringBuilder split) {
	    int numPreEscapes = 0;
	    for (int i = start; i < str.length(); i++) {
	      char curChar = str.charAt(i);
	      if (numPreEscapes == 0 && curChar == separator) { // separator 
	        return i;
	      } else {
	        split.append(curChar);
	        numPreEscapes = (curChar == escapeChar)
	                        ? (++numPreEscapes) % 2
	                        : 0;
	      }
	    }
	    return -1;
	  }
	  
	  /**
	   * Escape commas in the string using the default escape char
	   * @param str a string
	   * @return an escaped string
	   */
	  public static String escapeString(String str) {
	    return escapeString(str, ESCAPE_CHAR, COMMA);
	  }
	  
	  /**
	   * Escape <code>charToEscape</code> in the string 
	   * with the escape char <code>escapeChar</code>
	   * 
	   * @param str string
	   * @param escapeChar escape char
	   * @param charToEscape the char to be escaped
	   * @return an escaped string
	   */
	  public static String escapeString(
	      String str, char escapeChar, char charToEscape) {
	    return escapeString(str, escapeChar, new char[] {charToEscape});
	  }
	  
	  // check if the character array has the character 
	  private static boolean hasChar(char[] chars, char character) {
	    for (char target : chars) {
	      if (character == target) {
	        return true;
	      }
	    }
	    return false;
	  }
	  
	  /**
	   * @param charsToEscape array of characters to be escaped
	   */
	  public static String escapeString(String str, char escapeChar, 
	                                    char[] charsToEscape) {
	    if (str == null) {
	      return null;
	    }
	    StringBuilder result = new StringBuilder();
	    for (int i=0; i<str.length(); i++) {
	      char curChar = str.charAt(i);
	      if (curChar == escapeChar || hasChar(charsToEscape, curChar)) {
	        // special char
	        result.append(escapeChar);
	      }
	      result.append(curChar);
	    }
	    return result.toString();
	  }
	  
	  /**
	   * Unescape commas in the string using the default escape char
	   * @param str a string
	   * @return an unescaped string
	   */
	  public static String unEscapeString(String str) {
	    return unEscapeString(str, ESCAPE_CHAR, COMMA);
	  }
	  
	  /**
	   * Unescape <code>charToEscape</code> in the string 
	   * with the escape char <code>escapeChar</code>
	   * 
	   * @param str string
	   * @param escapeChar escape char
	   * @param charToEscape the escaped char
	   * @return an unescaped string
	   */
	  public static String unEscapeString(
	      String str, char escapeChar, char charToEscape) {
	    return unEscapeString(str, escapeChar, new char[] {charToEscape});
	  }
	  
	  /**
	   * @param charsToEscape array of characters to unescape
	   */
	  public static String unEscapeString(String str, char escapeChar, 
	                                      char[] charsToEscape) {
	    if (str == null) {
	      return null;
	    }
	    StringBuilder result = new StringBuilder(str.length());
	    boolean hasPreEscape = false;
	    for (int i=0; i<str.length(); i++) {
	      char curChar = str.charAt(i);
	      if (hasPreEscape) {
	        if (curChar != escapeChar && !hasChar(charsToEscape, curChar)) {
	          // no special char
	          throw new IllegalArgumentException("Illegal escaped string " + str + 
	              " unescaped " + escapeChar + " at " + (i-1));
	        } 
	        // otherwise discard the escape char
	        result.append(curChar);
	        hasPreEscape = false;
	      } else {
	        if (hasChar(charsToEscape, curChar)) {
	          throw new IllegalArgumentException("Illegal escaped string " + str + 
	              " unescaped " + curChar + " at " + i);
	        } else if (curChar == escapeChar) {
	          hasPreEscape = true;
	        } else {
	          result.append(curChar);
	        }
	      }
	    }
	    if (hasPreEscape ) {
	      throw new IllegalArgumentException("Illegal escaped string " + str + 
	          ", not expecting " + escapeChar + " in the end." );
	    }
	    return result.toString();
	  }

	  /**
	   * Concatenates strings, using a separator.
	   *
	   * @param separator Separator to join with.
	   * @param strings Strings to join.
	   */
	  public static String join(CharSequence separator, Iterable<?> strings) {
	    Iterator<?> i = strings.iterator();
	    if (!i.hasNext()) {
	      return "";
	    }
	    StringBuilder sb = new StringBuilder(i.next().toString());
	    while (i.hasNext()) {
	      sb.append(separator);
	      sb.append(i.next().toString());
	    }
	    return sb.toString();
	  }

	  /**
	   * Concatenates strings, using a separator.
	   *
	   * @param separator to join with
	   * @param strings to join
	   * @return  the joined string
	   */
	  public static String join(CharSequence separator, String[] strings) {
	    // Ideally we don't have to duplicate the code here if array is iterable.
	    StringBuilder sb = new StringBuilder();
	    boolean first = true;
	    for (String s : strings) {
	      if (first) {
	        first = false;
	      } else {
	        sb.append(separator);
	      }
	      sb.append(s);
	    }
	    return sb.toString();
	  }

	  /**
	   * Convert SOME_STUFF to SomeStuff
	   *
	   * @param s input string
	   * @return camelized string
	   */
	  public static String camelize(String s) {
	    StringBuilder sb = new StringBuilder();
	    String[] words = split(StringUtils.toLowerCase(s), ESCAPE_CHAR,  '_');

	    for (String word : words)
	      sb.append(org.apache.commons.lang.StringUtils.capitalize(word));

	    return sb.toString();
	  }

	  /**
	   * Matches a template string against a pattern, replaces matched tokens with
	   * the supplied replacements, and returns the result.  The regular expression
	   * must use a capturing group.  The value of the first capturing group is used
	   * to look up the replacement.  If no replacement is found for the token, then
	   * it is replaced with the empty string.
	   * 
	   * For example, assume template is "%foo%_%bar%_%baz%", pattern is "%(.*?)%",
	   * and replacements contains 2 entries, mapping "foo" to "zoo" and "baz" to
	   * "zaz".  The result returned would be "zoo__zaz".
	   * 
	   * @param template String template to receive replacements
	   * @param pattern Pattern to match for identifying tokens, must use a capturing
	   *   group
	   * @param replacements Map<String, String> mapping tokens identified by the
	   *   capturing group to their replacement values
	   * @return String template with replacements
	   */
	  public static String replaceTokens(String template, Pattern pattern,
	      Map<String, String> replacements) {
	    StringBuffer sb = new StringBuffer();
	    Matcher matcher = pattern.matcher(template);
	    while (matcher.find()) {
	      String replacement = replacements.get(matcher.group(1));
	      if (replacement == null) {
	        replacement = "";
	      }
	      matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
	    }
	    matcher.appendTail(sb);
	    return sb.toString();
	  }
	  
	  /**
	   * Get stack trace for a given thread.
	   */
	  public static String getStackTrace(Thread t) {
	    final StackTraceElement[] stackTrace = t.getStackTrace();
	    StringBuilder str = new StringBuilder();
	    for (StackTraceElement e : stackTrace) {
	      str.append(e.toString() + "\n");
	    }
	    return str.toString();
	  }
}
