package com.cominvi.app.oauth.services;

public interface IAESEncryptionDecryption {

	void prepareSecreteKey(String myKey);
	String encrypt(String strToEncrypt, String secret);
	String decrypt(String strToDecrypt, String secret);
	
}
