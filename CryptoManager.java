
/*
 * Programmer: Ryan Richards
 */

public class CryptoManager {
	
	private static final char LOWER_BOUND = ' ';
	private static final char UPPER_BOUND = '_';
	private static final char BELL_LOWER_BOUND=36;
	private static final char BELL_UPPER_BOUND = 98;
	// the RANGE variable but as a char, Java's only unsigned data type
	private static final char RANGE_C = UPPER_BOUND-LOWER_BOUND;
	
	private static final int RANGE = UPPER_BOUND - LOWER_BOUND+1;
	
	private static final int B_RANGE = BELL_UPPER_BOUND - BELL_LOWER_BOUND;
	/**
	 * This method determines if a string is within the allowable bounds of ASCII codes 
	 * according to the LOWER_BOUND and UPPER_BOUND characters
	 * @param plainText a string to be encrypted, if it is within the allowable bounds
	 * @return true if all characters are within the allowable bounds, false if any character is outside
	 */
	public static boolean stringInBounds (String plainText) {
		char ch;
		int length=plainText.length();
		for (int i = 0; i<length;i++) {
			
			/*
			 * drops the lower bound of 'ch' to NULL, 
			 * so i only need to check for upper bounds.
			 * This way, if the character at 'i' is less than 'LOWER_BOUND', 
			 * it'll wrap back around so its above the upper value.
			 * 
			 * Thus, only the 'UPPER_BOUND' needs to be checked
			 */
			ch = (char) (plainText.charAt(i) - LOWER_BOUND);
			if (ch>RANGE_C) return false;
		}
		return true;
	}

	/**
	 * Encrypts a string according to the Caesar Cipher.  The integer key specifies an offset
	 * and each character in plainText is replaced by the character \"offset\" away from it 
	 * @param plainText an uppercase string to be encrypted.
	 * @param key an integer that specifies the offset of each character
	 * @return the encrypted string 'en'
	 */
	public static String encryptCaesar(String plainText, int key) {
		int length = plainText.length();
		String en="";
		char ch;
		for (int i=0; i<length; i++) {
			
			// Drops the character in 'plainText' at spot 'i' down by
			// the 'LOWER_BOUND', then adds the value of 'key' to it
			ch = (char)(plainText.charAt(i++)-LOWER_BOUND+key);
			
			/*
			* Checks to make sure 'key' doesn't move the value of 'ch' outside
			* the intended range. 
			* 
			* If 'key' does move the result outside the 
			* given range, its pulled back into the intended range with 
			* the given equation.
			*/
			if (ch>RANGE_C) ch-=(char)(RANGE*(ch/RANGE));
			
			// Adds the lower bound back onto 'ch'
			ch += LOWER_BOUND;
			
			//Adds 'ch' to the front of string 'en'
			en+=ch;
		}
		return en;
	}
	
	/**
	 * Encrypts a string according the Bellaso Cipher.  Each character in plainText is offset 
	 * according to the ASCII value of the corresponding character in bellasoStr, which is repeated
	 * to correspond to the length of plainText
	 * @param plainText an uppercase string to be encrypted.
	 * @param bellasoStr an uppercase string that specifies the offsets, character by character.
	 * @return the encrypted string
	 */
	public static String encryptBellaso(String plainText, String bellasoStr) {
		
		/*
		 * Declares length of 'plainText' and the bellaso key to minimize
		 * calls
		 */
		int length = plainText.length();
		int bellLength = bellasoStr.length();
		
		String en="";	// Holds string that will be returned
		
		
		char ch;			// Holds cyphered/decyphered character
		char bell;			// Holds character in use by bellaso key
		
		for (int i=0; i<length;) {
			for (int j=0; j<bellLength; j++) {
				
				/*
				 * takes the chatacter in 'plainText' at spot 'i'
				 * and adds the chatacter at spot 'j' in the key
				 * to it.
				 * 
				 * The result is then assigned to 'ch'
				 */
				bell = bellasoStr.charAt(j);
				
				// Drops the character in 'plainText' at spot 'i' down by
				// the 'LOWER_BOUND', then adds the value of 'bell' to it
				ch = (char)(plainText.charAt(i++)-LOWER_BOUND+bell);
				
				/*
				* Checks to make sure 'bell' doesn't move the value of 'ch'
				* out of the intended range. 
				* 
				* If 'key' does move the result outside the given range, 
				* its pulled back into the intended range with the given 
				* equation.
				*/
				if (ch>RANGE_C) ch-=(char)(RANGE*(ch/RANGE));
				ch += LOWER_BOUND;
				
				// Adds the character 'ch' onto the end of 'en'
				en+=ch;
				
				/*
				 * if 'i' reaches 'length' while going through the key,
				 * the string is returned as is.
				 */
				if (i==length) return en;
			}
		}
	
			
		return en;
	}
	
	/**
	 * Decrypts a string according to the Caesar Cipher.  The integer key specifies an offset
	 * and each character in encryptedText is replaced by the character \"offset\" characters before it.
	 * This is the inverse of the encryptCaesar method.
	 * @param encryptedText an encrypted string to be decrypted.
	 * @param key an integer that specifies the offset of each character
	 * @return the plain text string
	 */
	public static String decryptCaesar(String encryptedText, int key) {
		int length = encryptedText.length();
		String plain="";
		char ch;
		for (int i=0; i<length; i++) {
			
			// Drops the character in 'plainText' at spot 'i' down by
			// the 'LOWER_BOUND', then drops it again by the value of 'key'
			ch = (char)(encryptedText.charAt(i)-LOWER_BOUND-key);
			
			/*
			* Checks to make sure 'key' doesn't move the value of 'ch' outside
			* the intended range. 
			* 
			* If 'key' does move the result outside the 
			* given range, its pulled back into the intended range with 
			* the given equation.
			*/
			if (ch>RANGE_C) ch-=(char)(RANGE_C*(ch/RANGE_C));
			
			// Adds the lower bound back onto 'ch'
			ch += LOWER_BOUND;
			
			// Adds the character 'ch' onto the end of 'plain'
			plain+=ch;
		}
		return plain;
	}
	
	/**
	 * Decrypts a string according the Bellaso Cipher.  Each character in encryptedText is replaced by
	 * the character corresponding to the character in bellasoStr, which is repeated
	 * to correspond to the length of plainText.  This is the inverse of the encryptBellaso method.
	 * @param encryptedText an uppercase string to be encrypted.
	 * @param bellasoStr an uppercase string that specifies the offsets, character by character.
	 * @return the decrypted string
	 */
	public static String decryptBellaso(String encryptedText, String bellasoStr) {
		int length = encryptedText.length();
		int bellLength = bellasoStr.length();
		String plain="";
		char ch;
		char bell;
		
		for (int i=0; i<length;) {
			for (int j=0; j<bellLength; j++) {
				
				/*
				 * takes the chatacter in 'plainText' at spot 'i'
				 * and adds the chatacter at spot 'j' in the key
				 * to it.
				 * 
				 * The result is then assigned to 'ch'
				 */
				bell = bellasoStr.charAt(j);
				
				// Drops the character in 'plainText' at spot 'i' down by
				// the 'LOWER_BOUND', then adds the value of 'key' to it
				ch = (char)(encryptedText.charAt(i++)-LOWER_BOUND-bell);
				
				/*
				* Checks to make sure 'bell' doesn't move the value of 'ch'
				* out of the intended range. 
				* 
				* If 'key' does move the result outside the given range, 
				* its pulled back into the intended range with the given 
				* equation.
				*/
				if (ch>RANGE_C) ch-=(char)(RANGE*(ch/RANGE));
				ch+=LOWER_BOUND;
				
				// Adds the character 'ch' onto the end of 'plain'
				plain+=ch;
				
				if (i==length) return plain;
			}
		}
		return plain;
	}
}
		}
		return en;
	}
	
	/**
	 * Encrypts a string according the Bellaso Cipher.  Each character in plainText is offset 
	 * according to the ASCII value of the corresponding character in bellasoStr, which is repeated
	 * to correspond to the length of plainText
	 * @param plainText an uppercase string to be encrypted.
	 * @param bellasoStr an uppercase string that specifies the offsets, character by character.
	 * @return the encrypted string
	 */
	public static String encryptBellaso(String plainText, String bellasoStr) {
		int length = plainText.length();
		int bellLength = bellasoStr.length();
		String plain="";
		char ch;
		char bell;
		for (int j=0; j<length;) {
			for (int i=0; i<bellLength; i++) {
				
				if (i==length) return plain;
				
				bell = bellasoStr.charAt(i);
				ch = (char)(plainText.charAt(j++)+bell);
				
				plain+=ch;
			}
		}
		return plain;
	}
	
	/**
	 * Decrypts a string according to the Caesar Cipher.  The integer key specifies an offset
	 * and each character in encryptedText is replaced by the character \"offset\" characters before it.
	 * This is the inverse of the encryptCaesar method.
	 * @param encryptedText an encrypted string to be decrypted.
	 * @param key an integer that specifies the offset of each character
	 * @return the plain text string
	 */
	public static String decryptCaesar(String encryptedText, int key) {
		int length = encryptedText.length();
		String plain="";
		char ch;
		for (int i=0; i<length; i++) {
			ch = (char)(encryptedText.charAt(i)-LOWER_BOUND-key);
			if (ch>RANGE) ch-=RANGE*(ch/RANGE);
			ch += LOWER_BOUND;
			plain+=ch;
		}
		return plain;
	}
	
	/**
	 * Decrypts a string according the Bellaso Cipher.  Each character in encryptedText is replaced by
	 * the character corresponding to the character in bellasoStr, which is repeated
	 * to correspond to the length of plainText.  This is the inverse of the encryptBellaso method.
	 * @param encryptedText an uppercase string to be encrypted.
	 * @param bellasoStr an uppercase string that specifies the offsets, character by character.
	 * @return the decrypted string
	 */
	public static String decryptBellaso(String encryptedText, String bellasoStr) {
		int length = encryptedText.length();
		int bellLength = bellasoStr.length();
		String plain="";
		char ch;
		char bell;
		for (int j=0; j<length;) {
			for (int i=0; i<bellLength; i++) {
				
				if (i==length) return plain;
				
				bell = bellasoStr.charAt(i);
				ch = (char)(encryptedText.charAt(j++)-LOWER_BOUND-bell);
				if (ch>RANGE) ch-=RANGE*(ch/RANGE);
				ch += LOWER_BOUND;
				
				plain+=ch;
			}
		}
		return plain;
	}
}
