package edu.gatech.seclass;

public class FaultyClass {
	public static int method1(int x){
		int result = x;
		if(x > 0)
		{
			result = x;
		}
		if(x == (-1) || x > 2)
		{
			result = (1 + x)/(1 + x);
		}
		return result;
	}
	
	
	public static void method2(){
		/*
		 * No such method can be created because 
		 * path coverage subsumes branch coverage,.
		 * in other words if we achieve 100% of path coverage, 
		 * we have already achieved 100% of branch coverage.
		 */
	}	
	
	public static int method3(int x){
	/*
	 * 	For simplicity I used method which contains unreachable code
	 */
		final int ZERO = 0;
		int result;
		if (x > 0) {
			result = x;
		} else if  ( x <= 0){
			result= -x;
		} else {
			//this is "dead code" and cannot be executed.
			//it cannot be reached by a test suite
			result=  x / ZERO;
		}
		return result;
	}
	
	public static void method4(){
		/*
		 * No such method can be created because 
		 * branch coverage statement coverage,
		 * in other words if we achieve 100% of branch coverage, 
		 * we have already achieved 100% of statement coverage.
		 */
	}
	
}
