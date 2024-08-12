package com.integrosys.cms.batch.contentManager.schedular;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
public class HTPUtils {
	
	
	 
	
	
public static Properties  readPropertyFile() {
	Properties p=new Properties(); 
		FileReader reader;
		try {
			reader = new FileReader("serverfile.properties");
			p.load(reader);
		    
		      
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (Exception e) {
			 System.out.println(e.getMessage());
			 e.printStackTrace();
		}
		
		  return p;
}

	 public static byte[] fileToByteArray(File file) throws IOException{
	        byte []buffer = new byte[(int) file.length()];
	        InputStream ios = null;
	        try {
	            ios = new FileInputStream(file);
	            if ( ios.read(buffer) == -1 ) {
	                throw new IOException("EOF reached while trying to read the whole file");
	            }
	        } finally {
	            try {
	                if ( ios != null )
	                    ios.close();
	            } catch ( IOException e) {
	            }
	        }
	        return buffer;
	    }

	    public static String getBase64Value(String input){
	        /*Base64.Encoder encoder = Base64.getEncoder();
	        String encodedString = encoder.encodeToString(input.getBytes(StandardCharsets.UTF_8) );
	        return encodedString;*/
	        
	        byte[] encoded = Base64.encodeBase64(input.getBytes());     
	               
	        System.out.println("Base64 Encoded String : " + new String(encoded));
	        return new String(encoded); 
	        
	        
	    }

	    public static String getMD5Value(String input){
	        String md5 = "";
	        try{
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            md.update(input.getBytes());
	            byte[] digest = md.digest();
	            StringBuffer sb = new StringBuffer();
	            for (byte b : digest) {
	                sb.append(String.format("%02x", b & 0xff));
	            }

	            md5 = sb.toString();
	            }
	        catch (NoSuchAlgorithmException e) {
	            System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
	        }
	        return md5;
	    }
	}