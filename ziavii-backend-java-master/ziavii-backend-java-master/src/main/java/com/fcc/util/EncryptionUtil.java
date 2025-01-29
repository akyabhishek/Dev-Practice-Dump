package com.fcc.util;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class EncryptionUtil {

	private static Log logger = LogFactory.getLog(EncryptionUtil.class);
	private static final String ENCRYPTION_KEY = "vGkf6Fw5egHcRT9y5Hud6Bb4";
	private static final byte[] SALT = {(byte) 0x10, (byte) 0x1B, (byte) 0x12, (byte) 0x21, (byte) 0xba, (byte) 0x5e, (byte) 0x99, (byte) 0x12};
	private static final int ITER_COUNT = 2;
	private static final String UNICODE_FORMAT = "UTF8";
	private static SecretKey secretKey = null;
	
	static {
		try {
			secretKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec(ENCRYPTION_KEY.toCharArray(), SALT, ITER_COUNT));
		} catch (Exception ex) {
			logger.error("Error while initializing encryption util", ex);
			
		}
	}

	public static String encrypt(String stringToEncrypt) throws Exception {
		if(secretKey == null) {
			throw new Exception("Error while initializing encryption util");
		}
		try {
			Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, ITER_COUNT);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
			return new Base64().encodeToString(cipher.doFinal(stringToEncrypt.getBytes(UNICODE_FORMAT))).trim();
		} catch (Exception e) {
			logger.error("could not encrypt string:" + stringToEncrypt, e);
			throw new Exception("encryption error");
		}
	}
	
	public static String decrypt(String encryptedString) throws Exception {
		if(secretKey == null) {
			throw new Exception("Error while initializing encryption util");
		}
		try {
			Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, ITER_COUNT);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			return new String(cipher.doFinal(new Base64().decode(encryptedString)));
		} catch (Exception e) {
			logger.error("could not decrypt string:" + encryptedString, e);
			throw new Exception("Invalid encrypted string");
		}
	}
	
	public static void main(String[] args) throws Exception {
		//System.out.print(URLDecoder.decode("58cf3837afe5afc31de13a805c31842c", "UTF-8"));
		//System.out.print(EncryptionUtil.decrypt("Xdu5qKwR340VMYNyJ+z5OiNn+/pLFXke"));
//		System.out.print(EncryptionUtil.encrypt("1292,6A4500EF-0D28-42B7-B5AF-1677A5AB45E0," + System.currentTimeMillis()/1000));
		System.out.print(EncryptionUtil.encrypt("1,,VEDNOR," + System.currentTimeMillis()/1000));
	}
}