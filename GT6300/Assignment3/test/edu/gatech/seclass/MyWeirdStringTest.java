package edu.gatech.seclass;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyWeirdStringTest {

	private MyWeirdStringInterface myweirdstring;

	@Before
	public void setUp() throws Exception {
		myweirdstring = new MyWeirdString();
	}

	@After
	public void tearDown() throws Exception {
		myweirdstring = null;
	}

	@Test
	public void testCountDigits1() {
		myweirdstring.setWeirdString("I'd better put s0me d1gits in this 5tr1n9, right?");
		assertEquals(5, myweirdstring.countDigits());
	}
	
	/**
	 * checks method countDigits
	 * 
	 * returns 0 for the digit count if an empty string  with value "" is passed
	 * 
	 * returns 0 for the digit count if an empty string  with value null is passed
	 * 
	 */
	@Test
	public void testCountDigits2() {
		//passing "" string
		myweirdstring.setWeirdString("");
		assertEquals(0, myweirdstring.countDigits());
		
		//passing null
		myweirdstring.setWeirdString(null);
		assertEquals(0, myweirdstring.countDigits());
	}
	
	/**
	 * checks method countDigits
	 * 
	 * returns 0 for the digit count if null string is passed as argument
	 */
	@Test
	public void testCountDigits3() {
		myweirdstring.setWeirdString(null);
		assertEquals(0, myweirdstring.countDigits());
	}
	
	/**
	 * checks method countDigits
	 * returns 0 for the digit count if string with no digits is passed
	 */
	@Test
	public void testCountDigits4() {
		myweirdstring.setWeirdString("no digits at all in this string !@#$%^&*()");
		assertEquals(0, myweirdstring.countDigits());
	}
	
	@Test
	public void testGetEvenCharacters1() {
		myweirdstring.setWeirdString("I'd better put s0me d1gits in this 5tr1n9, right?");
		assertEquals("' etrptsm 1isi hs5rn,rgt", myweirdstring.getEvenCharacters());
	}

	/**
	 * checks method getEvenCharacters
	 * 
	 * returns empty string "" when getEvenChars()is called and an empty string  with value "" is passed
	 * 
	 * returns empty string "" when getEvenChars()is called and an empty string  with value null is passed
	 * 
	 */
	@Test
	public void testGetEvenCharacters2() {
		//passing "" string
		myweirdstring.setWeirdString("");
		assertEquals("", myweirdstring.getEvenCharacters());
		
		//passing null
		myweirdstring.setWeirdString(null);
		assertEquals("", myweirdstring.getEvenCharacters());
	}

	/**
	 * checks method getEvenCharacters
	 * 
	 * returns string with 1 char equal to the second char of the string when the value passed has length 2 and
	 * returns the same result if the string we pass had length 3 and the second char of the string is same as the second char in
	 * previous method call:
	 * Example : if string = "abc" returns "b" and if string = "ab" the result from the method is "b"
	 * 
	 */
	@Test
	public void testGetEvenCharacters3() {
		//call with length 2 second char b
		myweirdstring.setWeirdString("ab");
		String one = myweirdstring.getEvenCharacters();
		
		//call with length 3 second char b
		myweirdstring.setWeirdString("abc");
		String two = myweirdstring.getEvenCharacters();
		
		assertTrue(one.equals(two));
	}
	
	/**
	 * checks method getEvenCharacters
	 * 
	 * returns an empty string (with length 0) if string length is 1
	 */
	@Test
	public void testGetEvenCharacters4() {
		myweirdstring.setWeirdString("a");
		String str = myweirdstring.getEvenCharacters();
		
		assertTrue(str.equals(""));
		assertTrue(str.length() == 0 );
	}

	@Test
	public void testGetOddCharacters1() {
		myweirdstring.setWeirdString("I'd better put s0me d1gits in this 5tr1n9, right?");
		assertEquals("Idbte u 0edgt nti t19 ih?", myweirdstring.getOddCharacters());
	}

	/**
	 * checks method getOddCharacters
	 * 
	 * returns empty string "" when getEvenChars()is called and an empty string  with value "" is passed
	 * 
	 * returns empty string "" when getEvenChars()is called and an empty string  with value null is passed
	 * 
	 */
	@Test
	public void testGetOddCharacters2() {
		//passing "" string
		myweirdstring.setWeirdString("");
		assertEquals("", myweirdstring.getOddCharacters());
		
		//passing null
		myweirdstring.setWeirdString(null);
		assertEquals("", myweirdstring.getOddCharacters());
	}
	
	/**
	 * checks method getOddCharacters
	 * 
	 * returns string with 1 char equal to the first char of the string when the value passed has length 1 and
	 * returns the same result if the string we pass had length 2 and the first char of the string is same as the first char in
	 * previous method call:
	 * Example : if string = "a" returns "a" and if string = "ab" the result from the method is "a"
	 * 
	 */ 
	@Test
	public void testGetOddCharacters3() {
		//call with length 1 first char a
		myweirdstring.setWeirdString("a");
		String one = myweirdstring.getOddCharacters();
		
		//call with length 2 first char a
		myweirdstring.setWeirdString("ab");
		String two = myweirdstring.getOddCharacters();
		
		assertTrue(one.equals(two));
	}

	/**
	 * checks method getOddCharacters
	 * 
	 * returns the first char as string (with length 1) if string length is 1
	 */
	@Test
	public void testGetOddCharacters4() {
		myweirdstring.setWeirdString("a");
		String str = myweirdstring.getOddCharacters();
		
		assertTrue(str.equals("a"));
		assertTrue(str.length() == 1 );
	}

	@Test
	public void testConvertDigitsToRomanNumeralsInSubstring1() {
		myweirdstring.setWeirdString("I'd better put s0me d1gits in this 5tr1n9, right?");
		myweirdstring.convertDigitsToRomanNumeralsInSubstring(40, 45);
		assertEquals("I'd better put s0me d1gits in this 5tr1nIX, right?", myweirdstring.getWeirdString());
	}
	
	/**
	 * checks whether method convertDigitsToRomanNumeralsInSubstring 
	 * converts a digit placed at the end of the string to Roman
	 */
	@Test
	public void testConvertDigitsToRomanNumeralsInSubstring2() {
		myweirdstring.setWeirdString("1abc");
		myweirdstring.convertDigitsToRomanNumeralsInSubstring(1, 1);
		assertEquals("Iabc", myweirdstring.getWeirdString());
	}
	
	/**
	 * checks whether method convertDigitsToRomanNumeralsInSubstring 
	 * converts a digit placed at the end of the string to Roman
	 */
	@Test
	public void testConvertDigitsToRomanNumeralsInSubstring3() {
		myweirdstring.setWeirdString("abc9");
		myweirdstring.convertDigitsToRomanNumeralsInSubstring(4, 4);
		assertEquals("abcIX", myweirdstring.getWeirdString());
	}
	
	/**
	 * checks whether method convertDigitsToRomanNumeralsInSubstring 
	 * can properly convert a string of length 1 with one digit to Roman
	 */
	@Test
	public void testConvertDigitsToRomanNumeralsInSubstring4() {
		myweirdstring.setWeirdString("8");
		myweirdstring.convertDigitsToRomanNumeralsInSubstring(1, 1);
		assertEquals("VIII", myweirdstring.getWeirdString());
	}
	
	/**
	 * checks whether method convertDigitsToRomanNumeralsInSubstring 
	 * converts all digits all (0-9) digits to Roman
	 */
	@Test
	public void testConvertDigitsToRomanNumeralsInSubstring5() {
		myweirdstring.setWeirdString("0123456789");
		myweirdstring.convertDigitsToRomanNumeralsInSubstring(1, 10);
		assertEquals("0IIIIIIIVVVIVIIVIIIIX", myweirdstring.getWeirdString());
	}
	
	/**
	* checks whether method convertDigitsToRomanNumeralsInSubstring 
	* throws an MyIndexOutOfBoundsException if end point is greater than the string length
	*/
	@Test(expected=MyIndexOutOfBoundsException.class)
	public void testConvertDigitsToRomanNumeralsInSubstring6(){
		myweirdstring.setWeirdString("0");
		myweirdstring.convertDigitsToRomanNumeralsInSubstring(1, 10);
	}
	
	/**
	* checks whether method convertDigitsToRomanNumeralsInSubstring 
	* throws an IllegalArgumentException if  startPosition is greater than endPosition
	*/
	@Test(expected=IllegalArgumentException.class)
	public void testConvertDigitsToRomanNumeralsInSubstring7(){
		myweirdstring.setWeirdString("test7");
		myweirdstring.convertDigitsToRomanNumeralsInSubstring(2, 1);
	}
}
