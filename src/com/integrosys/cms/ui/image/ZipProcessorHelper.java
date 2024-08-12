package com.integrosys.cms.ui.image;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.struts.upload.FormFile;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;


/**
 * This class contains processing for a zip upload file.
 * 
 * This code consists of 2 flows
 * 		1. Unzip the file and place the files in the path defined by the property 'image.upload.basepath'
 * 		2. Update the database table 'CMS_TEMP_IMAGE_UPLOAD' and redirect to image listing page.
 * 	
 * The code to update the database is replicated from ProcessUploadRequestServlet.java
 * 
 * Make sure that any changes made to this file will be replicated accordingly in ProcessUploadRequestServlet.java and vice versa.
 * 
 * @author pradeep.bhat
 *
 */

public class ZipProcessorHelper {
	
	private static String UPLOAD_BASEPATH="image.upload.basepath"; 
	private static String basePath = PropertyManager.getValue(UPLOAD_BASEPATH);
	
	public IImageUploadAdd processZipFile(IImageUploadAdd upload, long createdBy) {
		
		ZipInputStream zis = null;
		IImageUploadProxyManager imageUploadProxyManager = (IImageUploadProxyManager) BeanHouse.get("imageUploadProxy");
		OBImageUploadAdd imageUploadAdd = new com.integrosys.cms.app.image.bus.OBImageUploadAdd();
		String fromServer=PropertyManager.getValue("integrosys.server.identification","APP1");
		FileOutputStream fos;
		
		// Directory to store all the uploaded files
		// If directory is not present, it will be created.
	    String custBasePath= basePath + upload.getCustId();
	    if("Y".equals(upload.getHasSubfolder())) {
	    	custBasePath=custBasePath+System.getProperty("file.separator")+upload.getSubfolderName();
	    }
	    
	    File dir = new File(custBasePath);
	    if(!dir.exists()) {
	    	dir.mkdirs();
		}
	    custBasePath=custBasePath+System.getProperty("file.separator");
	    
	    // Read image formats to be processed.
	    String imageFormats = PropertyManager.getValue("image.upload.formats","jpeg, tif"); 
	    String[] imageFormatArray = imageFormats.split(",");
	    String[] a = new String[] {};
	    List imgStringFormats = Arrays.asList(imageFormatArray);
	    DefaultLogger.debug(this, "Zip Processing Start -- ");
	    DefaultLogger.debug(this, "Directory to upload  -- "+custBasePath);
	    DefaultLogger.debug(this, "Processing file formats  -- "+imageFormatArray);
	    String extn3, extn4;
		if (upload != null) {
			try {
				FormFile file = upload.getImageFile();
				if (file != null) {
					DefaultLogger.debug(this, "File Details (Name : "+file.getFileName()+" , Size: "+file.getFileSize()+" )");
					ZipEntry entry;
					InputStream fis = file.getInputStream();
					zis = new ZipInputStream(new BufferedInputStream(fis));
			
					byte[] byteBuffer;
					// For every file within the zip file
					while ((entry = zis.getNextEntry()) != null) {
						File file2 = new File(custBasePath+entry.getName());
						if (entry.getName().endsWith("/") || entry.getName().endsWith("\\")) {
							file2.mkdirs();
							continue;
						}
						// 3 characters or 4 characters of extensions
						extn3 = entry.getName().toUpperCase().substring(entry.getName().length() - 3, entry.getName().length());
						extn4 = entry.getName().toUpperCase().substring(entry.getName().length() - 4, entry.getName().length());
						// Not all images to be processed.
						if(!imgStringFormats.contains(extn3) && !imgStringFormats.contains(extn4)) 
							continue;
					
						// Copying file on to the system
						byteBuffer = new byte[1024];
						fos = new FileOutputStream(file2);
						int bytesRead;
						while ((bytesRead = zis.read(byteBuffer)) > 0) {
							fos.write(byteBuffer, 0, bytesRead);
						}
						fos.close();
						
						imageUploadAdd.setImgFileName(file2.getName());
						imageUploadAdd.setImageFilePath(file2.getAbsolutePath());
						imageUploadAdd.setImgSize(entry.getSize());
						imageUploadAdd.setCustId(upload.getCustId());
						imageUploadAdd.setCustName(upload.getCustName());
						imageUploadAdd.setCategory(upload.getCategory());
						imageUploadAdd.setHasSubfolder(upload.getHasSubfolder());
						imageUploadAdd.setSubfolderName(upload.getSubfolderName());
						imageUploadAdd.setImgDepricated("N");
						imageUploadAdd.setSendForAppFlag("N");
						imageUploadAdd.setCreateBy(createdBy);
						imageUploadAdd.setCreationDate(new Date());
						imageUploadAdd.setDocumentName(upload.getDocumentName());
						imageUploadAdd.setFromServer(fromServer);
						imageUploadProxyManager.createImageUploadAdd(imageUploadAdd);
					}
				}
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			finally {
				if (zis != null) {
					try {
						zis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		return upload;
	}
}
