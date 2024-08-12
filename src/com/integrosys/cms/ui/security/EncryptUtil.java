package com.integrosys.cms.ui.security;

//package com.mbb.cms.security;

/**
 * Command line utilty class to encrypt and decrypt the given text..
 * User: ravi
 * Date: Sep 30, 2003
 * Time: 10:39:36 AM */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.integrosys.base.businfra.login.PasswordUtil;
import com.integrosys.base.techinfra.crypto.CryptoManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

public class EncryptUtil {

	public EncryptUtil() {
	}

	public static void main(String args[]) {

		DefaultLogger.debug("!!", "" + PropertyManager.getValue("dbconfig.batch.password"));
		DefaultLogger.debug("Encryption Provider is ", "" + PropertyManager.getValue("encryption.provider"));

		if (args.length < 3) {
			System.out.println("WRONG NUMBER OF PARAMETERS");
			System.out.println("syntax is:   java Command {e-encrypt} {u-user-id/p-password} {input}");
			return;
		}
		if ((args[1] == null) || (!args[0].equals("e") && !args[0].equals("dey") && !args[0].equals("eh") && !args[0].equals("tt"))) {
			System.out.println("wrong usage of Command: ");
			return;
		}
		if ((args[1] == null) || (!args[1].equals("u") && !args[1].equals("p"))) {
			System.out.println("wrong usage of Command: ");
			return;
		}
		if (args[0].equals("e")) {
			String clearText = args[2];
			CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
			System.out.println("BYTE ARRAY LENGTH BEFORE ENCRYPT  : >>>" + clearText.getBytes().length + "<<<");
			String result = sec.encrypt(clearText);
			String fileName = (args[1].equals("u")) ? "encrypted.dbuid.file" : "encrypted.dbpwd.file";
			writeFile(PropertyManager.getValue(fileName), result);
			System.out.println("Encrpted data in the following line:");
			System.out.println(result);
		}
		else if (args[0].equals("dey")) {
			String cipherText = args[2];
			System.out.println("Decrypting : " + cipherText);
			CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
			String result = sec.decrypt(cipherText);
			System.out.println("Decrpted data in the following line:");
			System.out.println(result);
		}
		else if (args[0].equals("tt")) {
			String cipherText = args[2];
			//System.out.println("Decrypting : " + cipherText);
			CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
			System.out.println("sec     :<<<" + sec + ">>>");
			System.out.println("cipherText:"+cipherText);
			String encryptedString = sec.encrypt(cipherText.getBytes()); // perform
																			// encryption
			System.out.println("Encrypt     :<<<" + encryptedString + ">>>");
			String DecryptedString = sec.decrypt(encryptedString); // perform
																	// decryption
			System.out.println("Decrypt     : >>>" + DecryptedString + "<<<");
		}
		else if (args[0].equals("eh")) {
			String clearText = args[2];
			CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
			System.out.println("BYTE ARRAY LENGTH BEFORE ENCRYPT  : >>>" + clearText.getBytes().length + "<<<");
			String result = clearText;
			// System.out.println("Encrpted data in the following line:");
			System.out.println(result);
			result = PasswordUtil.getPasswordHash(result);
			// String fileName=(args[1].equals("p"))?"encrypted.dbuid.file":
			// "encrypted.dbpwd.file";
			// writeFile(PropertyManager.getValue(fileName),result);
			System.out.println("Encrpted and hashed data in the following line:");
			System.out.println(result);
		}
	}

	private static void writeFile(String fileName, String data) {
		DefaultLogger.debug("FIle Name is ", fileName);
		try {
			File f = null;
			f = new File(fileName);
			boolean createNewFile = f.createNewFile();
			 if(createNewFile==false) {
					System.out.println("Error while creating new file:"+f.getPath());	
				      }
			if (f.canWrite()) {
				FileWriter fw = new FileWriter(f);
				fw.write(data);
				fw.flush();
			}
			else {
				DefaultLogger.debug("genarte file ", " Could not open encrypted file " + fileName);
			}
		}
		catch (IOException e) {
			DefaultLogger.debug("genarte file", "" + e.getMessage());
		}
	}
}
