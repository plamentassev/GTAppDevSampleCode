package edu.gatech.seclass;

/**
 * This is an interface for a simple class that represents a string, defined
 * as a sequence of characters.
 * 
 * @author Rufus
 * 
 */
public interface MyWeirdStringInterface {

	/**
	 * Sets the value of the current string.
	 * 
	 * @param string
	 *            The value to be set
	 */
	void setWeirdString(String string);

	/**
	 * Returns the current string
	 * 
	 * @return Current string
	 */
	String getWeirdString();

	/**
	 * Returns a string that consists of all and only the characters
	 * in even positions (i.e., second, fourth, sixth, and so on) in
	 * the current string, in the same order and with the same case as
	 * in the current string. The first character in the string is
	 * considered to be in Position 1.
	 * 
	 * @return String made of characters in even positions in the
	 * current string
	 */
	String getEvenCharacters();

	/**
	 * Returns a string that consists of all and only the characters
	 * in odd positions (i.e., first, third, fifth, and so on) in the 
	 * current string, in the same order and with the same case as in 
	 * the current string. The first character in the string is
	 * considered to be in Position 1.
	 * 
	 * @return String made of characters in odd positions in the
	 * current string
	 */
	String getOddCharacters();

	/**
	 * Returns the number of digits in the current string
	 * 
	 * @return Number of digits in the current string
	 */
	int countDigits();
	
	/**
	 * Replace the _individual_ digits in the current string, between
	 * startPosition and endPosition (included), with the corresponding
	 * Roman numeral symbol(s). The first character in the string is
	 * considered to be in Position 1. Digits are converted individually,
	 * even if contiguous, and digit "0" is not converted (e.g., 460 is
	 * converted to IVVI0). In case you are not familiar with Roman
	 * numerals, see https://en.wikipedia.org/wiki/Roman_numerals
	 * 
	 * @param startPosition
	 *            Position of the first character to consider
	 * @param endPosition
	 *            Position of the last character to consider
	 * @return
	 * @throws MyIndexOutOfBoundsException
	 *            If either "startPosition" or "endPosition" are out of
	 *            bounds (i.e., either less than 1 or greater then the
	 *            length of the string)
	 * @throws IllegalArgumentException
	 *            If "startPosition" > "endPosition" (but both are
	 *            within bounds)
	 */
	void convertDigitsToRomanNumeralsInSubstring(int startPosition, int endPosition)
			throws MyIndexOutOfBoundsException, IllegalArgumentException;
}
