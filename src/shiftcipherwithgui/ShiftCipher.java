/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shiftcipherwithgui;

import java.io.*;
import java.util.*;
import java.security.*;
import java.lang.*;

/**
 *
 * @author Drianka Mahdy
 */
public class ShiftCipher {
    
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
    
    public void clearKey(){
            key.clear();
    }
}
