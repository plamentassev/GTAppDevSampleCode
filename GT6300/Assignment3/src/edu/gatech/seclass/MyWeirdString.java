package edu.gatech.seclass;

public class MyWeirdString implements MyWeirdStringInterface {
	private String string;
	
	@Override
	public void setWeirdString(String string) {
		if (string == null){
			this.string = "";
		} else {
			this.string = string;
		}
	}

	@Override
	public String getWeirdString() {
		return this.string;
	}

	@Override
	public String getEvenCharacters() {
		StringBuilder buildResult = new StringBuilder();
		for (int i = 1; i < string.length() ; i += 2){
			buildResult.append(string.charAt(i));
		}
		return buildResult.toString();
	}

	@Override
	public String getOddCharacters() {
		StringBuilder buildResult = new StringBuilder();
		for (int i = 0; i < string.length() ; i += 2){
			buildResult.append(string.charAt(i));
		}
		return buildResult.toString();
	}

	@Override
	public int countDigits() {
		String numbers = string.replaceAll("[^0-9]+", "");
		return numbers.length();
	}

	@Override
	public void convertDigitsToRomanNumeralsInSubstring(int startPosition, int endPosition) 
								throws MyIndexOutOfBoundsException,	IllegalArgumentException {
			
		StringBuilder converted = new StringBuilder();
		
		if ((startPosition < 1 || startPosition > string.length()) || (endPosition < 1 || endPosition > string.length())){
			throw new MyIndexOutOfBoundsException();
		}else if (startPosition > endPosition){
			throw new IllegalArgumentException();
		}else{
			converted.append(string.substring(0, startPosition-1));
			for (int i = startPosition-1; i < endPosition; i++){
				char currentChar = string.charAt(i);
				//is where I check if it is number
					switch(currentChar){
						case '1' : converted.append("I");
							break;
						case '2' : converted.append("II");
							break;
						case '3' : converted.append("III");
							break;
						case '4' : converted.append("IV");
							break;
						case '5' : converted.append("V");
							break;
						case '6' : converted.append("VI");
							break;
						case '7' : converted.append("VII");
							break;
						case '8' : converted.append("VIII");
							break;
						case '9' : converted.append("IX");
							break;
						default: converted.append(currentChar);
					}//end switch
			}//end for loop
			converted.append(string.substring(endPosition, string.length()));
			string = converted.toString();
		}//end else block
	}
}
