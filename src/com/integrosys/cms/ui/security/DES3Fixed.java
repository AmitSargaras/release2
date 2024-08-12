package com.integrosys.cms.ui.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.integrosys.base.techinfra.crypto.CryptoSvcProvider;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.PropertyUtil;

/**
 * This class use Sun JCE to perform DES3FIXED encryption and decryption.
 * @author Ravi
 * @version 1.0
 */
public class DES3Fixed implements CryptoSvcProvider {
	// private static String sKey = PropertyManager.getValue("db.security.key");
	private static String sKey = "123456789012345678901234";
	private static final String PROVIDER = BouncyCastleProvider.PROVIDER_NAME;
	/*************************************************************
	 * actual key to be formed in done by SHA take the last 24 chars from
	 * digested byte array to Hex String
	 **************************************************************/
	static {
		try {
			Security.addProvider(new BouncyCastleProvider());
			MessageDigest shaMessageDigest = MessageDigest.getInstance("SHA");
			shaMessageDigest.update(sKey.getBytes());
			byte[] shaBytes = shaMessageDigest.digest();
			sKey = byteArrayToHexString(shaBytes);
			sKey = sKey.substring(8, 32);
		}
		catch (java.security.NoSuchAlgorithmException e) {
			DefaultLogger.debug("DES3Fixed", "exception inside stactic block of DES3Fixed:"+e.getMessage());
			e.printStackTrace();
		}
	}

	Cipher cipher = null;

	SecretKeySpec skeySpec = null;

	/** Creates new DES3Fixed */
	public DES3Fixed() throws InstantiationException {
		try {
			//SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes(), "DESede");
			//cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			//security observation by securebay
			
			String encryptAlgorithm = PropertyUtil.getInstance("/ofa_env.properties").getProperty("encryption.algorithm");
			String encryptAlgorithmMethod = PropertyUtil.getInstance("/ofa_env.properties").getProperty("encryption.algorithm.method");
			DefaultLogger.debug(this, "encryption.algorithm: " + encryptAlgorithm + ", encryption.algorithm.method: "
					+ encryptAlgorithmMethod);
			
			SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes(), encryptAlgorithm);
			cipher = Cipher.getInstance(encryptAlgorithmMethod, PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "exception inside DES3Fixed method of DES3Fixed:"+e.getMessage(), e);
			throw new InstantiationException(e.toString());
		}
	}

	/**
	 * Encryption method.
	 */
	public byte[] encrypt(byte[] cleartext) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes(), PropertyUtil.getInstance("/ofa_env.properties").getProperty("encryption.algorithm"));
			DefaultLogger.debug(this,"encryption.algorithm inside encypt:"+PropertyUtil.getInstance("/ofa_env.properties").getProperty("encryption.algorithm"));
		
			 String s1 = "12345678";
		     byte[] bytes = s1.getBytes();
		     final IvParameterSpec iv = new IvParameterSpec(bytes);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec,iv);
			SealedObject so = new SealedObject(cleartext, cipher);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(so);
			return bos.toByteArray();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "exception inside encrypt method of DES3Fixed:"+e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Decryption method.
	 */
	public byte[] decrypt(byte[] ciphertext) {
		try {
			//SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes(), "DESede");
			
			SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes(), PropertyUtil.getInstance("/ofa_env.properties").getProperty("encryption.algorithm"));
			DefaultLogger.debug(this, "SecretKeySpec:"+skeySpec);
			 String s1 = "12345678";
		        byte[] bytes = s1.getBytes();
		        final IvParameterSpec iv = new IvParameterSpec(bytes);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec,iv);
			// DefaultLogger.debug(this,
			// "Initializing CHiper for Decrypting with key :" +
			// cipher.getParameters());
			ByteArrayInputStream in = new ByteArrayInputStream(ciphertext);
			DefaultLogger.debug(this, "ciphertext:"+ciphertext+" in:"+in);
			ObjectInputStream iis = new ObjectInputStream(in);
			DefaultLogger.debug(this, "iis:"+iis);
			SealedObject so = (SealedObject) iis.readObject();
			if(so!=null) {
				DefaultLogger.debug(this, "so.getAlgorithm():"+so.getAlgorithm()+" skeySpec.getAlgorithm():"+skeySpec.getAlgorithm()+"skeySpec.getFormat():"+skeySpec.getFormat()+
						"skeySpec.getEncoded():"+skeySpec.getEncoded());	
			}
			Object object = so.getObject(cipher);
			/*Object object = so.getObject(skeySpec,PROVIDER);*/
			DefaultLogger.debug(this, "object:"+object);
			byte[] result = (byte[]) object;
			DefaultLogger.debug(this, "result:"+result);
			return result;
		}
		catch (IOException e) {
			DefaultLogger.debug(this, "IO exception inside decrypt method of DES3Fixed:"+e.getMessage(), e);
			e.printStackTrace();
			return null;
		}catch (ClassNotFoundException e) {
			DefaultLogger.debug(this, " ClassNotFoundExceptionexception inside decrypt method of DES3Fixed:"+e.getMessage(), e);
			e.printStackTrace();
			return null;
		}catch (InvalidKeyException e) {
			DefaultLogger.debug(this, "InvalidKeyException exception inside decrypt method of DES3Fixed:"+e.getMessage(), e);
			e.printStackTrace();
			return null;
		}catch (Exception e) {
			DefaultLogger.debug(this, "exception inside decrypt method of DES3Fixed:"+e.getMessage(), e);
			e.printStackTrace();
			return null;
		}
	}

	private static String byteArrayToHexString(byte[] byteArray) {
		String hexChars = "0123456789ABCDEF";

		StringBuffer buf = new StringBuffer(300);
		for (int i = 0; i < byteArray.length; i++) {
			int x = byteArray[i];

			if (x < 0) {
				x += 256;
			}

			buf.append(hexChars.charAt(x / 16));
			buf.append(hexChars.charAt(x % 16));
		}
		return buf.toString();
	}
}
