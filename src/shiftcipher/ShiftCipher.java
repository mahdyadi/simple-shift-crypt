package shiftcipher;

import java.io.*;
import java.util.*;



public class ShiftCipher {
	
	public static void main (String[] args){
		String plaintext;
		ShiftCipherClass sc = new ShiftCipherClass();
		BufferedReader brEnc;
		BufferedReader brDec;
		BufferedReader brKey;
		BufferedWriter bwCiph;
		BufferedWriter bwKey;
		BufferedWriter bwEncTime;
		BufferedWriter bwDecTime;
		BufferedWriter bwPlain;
		try{
			brEnc = new BufferedReader(new FileReader("./Sample_Plaintext_Input.txt"));
			bwCiph = new BufferedWriter(new FileWriter(new File("./CipherResult.txt"),false));
			bwEncTime = new BufferedWriter(new FileWriter(new File("./EncTimeResult.txt"),false));
			bwKey = new BufferedWriter(new FileWriter(new File("./KeyResult.txt"),false));
			String curln;
			long startTime;
			long stopTime;
			long elapsedTime;
			
			while((curln = brEnc.readLine()) != null){
				startTime = System.nanoTime();
                String encryptResult = new String(sc.encrypt(curln));
                stopTime = System.nanoTime();
                elapsedTime = stopTime - startTime;
				bwCiph.write(encryptResult);
				bwCiph.newLine();
				bwEncTime.write(Double.toString((double)elapsedTime/1000000.0));
				bwEncTime.newLine();
				bwKey.write(sc.getKey());
				bwKey.newLine();
			}
			brEnc.close();
			bwCiph.close();
			bwEncTime.close();
			bwKey.close();
			
			brDec = new BufferedReader(new FileReader("./CipherResult.txt"));
			brKey = new BufferedReader(new FileReader("./KeyResult.txt"));
			bwDecTime = new BufferedWriter(new FileWriter(new File("./DecTimeResult.txt"),false));
			bwPlain = new BufferedWriter(new FileWriter(new File("./DecPlainResult.txt"),false));
			
			while((curln = brDec.readLine()) != null){
				String keyFromFile = brKey.readLine();
				sc.setKeyFromString(keyFromFile);
				startTime = System.nanoTime();
                String decryptResult = new String(sc.decrypt(curln));
                stopTime = System.nanoTime();
                elapsedTime = stopTime - startTime;
				bwPlain.write(decryptResult);
				bwPlain.newLine();
				bwDecTime.write(Double.toString((double)elapsedTime/1000000.0));
				bwDecTime.newLine();
			}
			
			brDec.close();
			brKey.close();
			bwDecTime.close();
			bwPlain.close();
			
		} catch(Exception e){
			System.out.println("Failed: "+e.getMessage());	
		}
		System.out.println("done");
	}
}

class ShiftCipherClass{
		
	private int lowerBound = 0;
	private int upperBound = 25;
	private ArrayList<Integer> key = new ArrayList<Integer>();
	
	 public String getInput() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = br.readLine();
            br.close();
            return input;
    }

    public int[] generateKey(int length){
            Random randomGenerator = new Random();
            int[] keysGenerated = randomGenerator.ints((long)length,lowerBound,upperBound+1).toArray();
            return keysGenerated;
    }

    public void inputChecker(String input) throws Exception{
            if(input.length() == 0){
                 throw new java.lang.Exception("input character cannot be empty");
            }
            for(int i = 0; i < input.length();i++){
                    if(input.charAt(i)<97 || input.charAt(i)>122 ){
                            throw new java.lang.Exception("character must be a-z");
                    }
            }
    }

    public char[] encrypt(String plaintext){

            int[] temp = generateKey(plaintext.length());
            char[] encrypted = new char[plaintext.length()];
            key.clear();
            for(int i = 0;i < temp.length;i++){
                    key.add(temp[i]);
                    int remainder = (plaintext.charAt(i) - 97  + temp[i]) % 26;
                    encrypted[i] = (char)( (remainder < 0 ? remainder+26:remainder) + 97);
            }
            return encrypted;	
    }
    
    public char[] encryptWithoutGeneratingKey(String plaintext){
            char[] encrypted = new char[plaintext.length()];
            for(int i = 0;i < key.size();i++){
                    int remainder = (plaintext.charAt(i) - 97  + key.get(i)) % 26;
                    encrypted[i] = (char)( (remainder < 0 ? remainder+26:remainder) + 97);
            }
            return encrypted;	
    }

    public char[] decrypt(String ciphertext){

            char[] decrypted = new char[ciphertext.length()];
            for(int i = 0;i < ciphertext.length();i++){
                    int remainder = (ciphertext.charAt(i) - 97 - key.get(i)) % 26;
                    decrypted[i] = (char)( (remainder < 0 ? remainder+26:remainder) + 97);
            }
            return decrypted;	
    }

    public String getKey(){
            if (key.size() > 0){
                    char[] result = new char[key.size()];
                    for(int i = 0; i < result.length;i++){
                            int temp = (int)key.get(i) + 97;
                            result[i] = (char) temp;
                    }
                    return new String(result);
            } else {
                    return "Key not exist";
            }
    }
    
    public void setKey(int[] keyInput){
            key.clear();
            for(int i = 0;i < keyInput.length;i++){
                    key.add(keyInput[i]);
            }
    }
	
	public void setKeyFromString(String keyInput){
			key.clear();
			char[] keyChars = keyInput.toCharArray();
			for(int i = 0;i < keyChars.length;i++){
					key.add((int)keyChars[i] - 97);
			}
	}
    
    public void clearKey(){
            key.clear();
    }
}