package edu.gatech.seclass;

import static org.junit.Assert.*;

import org.junit.Test;

public class FaultyClassTest {
	/*
	 * All if conditions are true and all statements are executed
	 */
	@Test
	public void method1SC() {
		System.out.println(FaultyClass.method1(3));
		assertEquals(1, FaultyClass.method1(3));
	}
	
	/*
	 * 1st assert all if statements are false, 
	 * 2nd assert all if statements are true 
	 * the 2 assert statements cover the branches in the code 
	 */
	@Test
	public void method1BC() {
		assertEquals(-2, FaultyClass.method1(-2));
		assertEquals(1, FaultyClass.method1(3));
	}
	
	/*
	 * because there are only 2 if statements in the code the possible pats are 
	 * 2^2 = 4. Therefore path test should cover all 4 possible paths.
	 * The test below illustrates the 2 paths not covered by path coverage
	 */
 	@Test
 	public void method1PC() {
 		assertEquals(-2, FaultyClass.method1(-2));
		assertEquals(1, FaultyClass.method1(3));
		
		//test 1st IF TRUE, 2nd IF FALSE
		assertEquals(1, FaultyClass.method1(3));;
		
		//test 1st IF FALSE, 2nd IF TRUE
		//Arithmetic Exception and 100 path coverage here
		FaultyClass.method1(-1);
    }
}
