package com.integrosys.cms.ui.customer;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.contentManager.exception.ContentManagerInitializationException;
import com.integrosys.cms.app.contentManager.factory.ContentManagerFactory;
import com.integrosys.cms.app.contentManager.ibmDb2Cm.connectionPool.DB2ConnectionPool;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.ibm.mm.sdk.server.DKDatastoreICM;

/**
 * This command creates a Image Tag
 * 
 * 
 * 
 */

public class DownloadImageCommand extends AbstractCommand {
	
	private ContentManagerFactory contentManagerFactory;

	public ContentManagerFactory getContentManagerFactory() {
		return contentManagerFactory;
	}

	public void setContentManagerFactory(ContentManagerFactory contentManagerFactory) {
		this.contentManagerFactory = contentManagerFactory;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "tagDetailList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "imageId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "custId", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "frompage", "java.lang.String", REQUEST_SCOPE },
				});
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		DefaultLogger.debug(this, "********  getResultDescriptor Call: ");
		return (new String[][] { 
				{ "imagePath", "java.lang.String", REQUEST_SCOPE },
				{ "output", "java.io.ByteArrayOutputStream", REQUEST_SCOPE },
				{ "imageName", "java.lang.String", REQUEST_SCOPE },
				   });

	}
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @throws CommandProcessingException
	 *             on errors
	 * @throws CommandValidationException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {HashMap result = new HashMap();
			HashMap returnMap = new HashMap();
			DefaultLogger.debug(this, "Enter in doExecute()");
			//String pID=(String) map.get("pID");
			//String imageName=(String) map.get("imageName");
			//String status=(String) map.get("status");
			//DefaultLogger.debug(this,"doExecute ---" + pID + "    " + imageName + "      " + status);
			String startIdx = (String) map.get("startIndex");
			String imageId =(String) map.get("imageId");
			String unCheckId =(String) map.get("unCheckId");
			String event = (String) map.get("event");
			HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
			if(selectedArrayMap==null){
			selectedArrayMap = new HashMap();
			}
			List obImageTagAddList = new ArrayList();
			obImageTagAddList =(List) map.get("tagDetailList");
			result.put("startIndex", startIdx);
			String custId = (String) map.get("custId");
			//IImageTagDetails details=(OBImageTagDetails) map.get("ImageTagMapObj");
			//details.setCustId(custId);
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			String HtcOrDb2cm = PropertyManager.getValue("cms.image.htcOrDB2CM");

			String url=PropertyManager.getValue("hcp.rest.url");
			System.out.println("url............"+url);
	 
			
			DefaultLogger.debug(this, "selectedArrayMap.size()============DownloadImageCommand============1====================>"+selectedArrayMap.size());
			try{
					DefaultLogger.debug(this, "DownloadImageCommand====================193========================>"+imageId);
				if(imageId!=null && !imageId.equals("")){
					
					String[] selected=imageId.split("-");
					if(selected!=null){
					for(int k=0;k<selected.length;k++){
						OBImageUploadAdd uploadImage = new OBImageUploadAdd();
						if(obImageTagAddList!= null)
						{
					 uploadImage = (OBImageUploadAdd)obImageTagAddList.get(Integer.parseInt(selected[k])-1);
						}
					selectedArrayMap.put(String.valueOf(uploadImage.getImgId()),String.valueOf(uploadImage.getImgId()));
					}
					}
				}
				DefaultLogger.debug(this, "DownloadImageCommand====================214========================>"+unCheckId);
				if(unCheckId!=null && !unCheckId.equals("")){
				String[] unchecked=unCheckId.split("-");
				if(unchecked!=null){
					for(int ak=0;ak<unchecked.length;ak++){
						OBImageUploadAdd uploadImage = new OBImageUploadAdd();
						if(obImageTagAddList!= null)
						{
					 uploadImage = (OBImageUploadAdd)obImageTagAddList.get(Integer.parseInt(unchecked[ak])-1);
						}
						selectedArrayMap.remove(String.valueOf(uploadImage.getImgId()));
					}
					}
				}
				
					
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			DefaultLogger.debug(this, "selectedArrayMap.size()============DownloadImageCommand=============2===================>"+selectedArrayMap.size());
			result.put("selectedArrayMap", selectedArrayMap);
			Date date= new Date();
			String basePath=PropertyManager.getValue("contextPath");
			ZipOutputStream out =null;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] fileData;
			String zipFileName=custId+"-"+date.getTime()+".zip";
			
			//retriving image list available for tagging
			 DKDatastoreICM connection = null;
			 int downloadImageCount =0;
			try {
				
			if(obImageTagAddList !=null){
				
				 out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(new File(basePath+"/dmsImages/"+zipFileName))));
				
				 //TODO : Create connection
				 try{
					 DefaultLogger.debug(this,"Opening connection in downloading operation of image" + connection);
					  //connection = (DKDatastoreICM) DB2ConnectionPool.getConnectionPoolInstance().borrowObject();
					  DefaultLogger.debug(this,"connection opened in downloading operation of image" + connection);
				 }catch(Exception e){
					 e.printStackTrace();
				 }
				 
				 DefaultLogger.debug(this,"db2cm connectin " + connection);
				
				for(int a=0;a<obImageTagAddList.size();a++){
					 DefaultLogger.debug(this,"inside for Loop---------------->"+ a);
					OBImageUploadAdd obImageUploadAdd= (OBImageUploadAdd) obImageTagAddList.get(a);
					if(selectedArrayMap.containsKey(String.valueOf(obImageUploadAdd.getImgId()))){
						DefaultLogger.debug(this,"inside if Loop-------------->"+ a);
						ContentManagerFactory contentManagerFactory = (ContentManagerFactory)BeanHouse.get("contentManagerFactory");
						String imagePath="";
							try {
								Object[] params  = new Object[3];
								params[0] = obImageUploadAdd.getImageFilePath();
								params[1] = obImageUploadAdd.getImgFileName();
								params[2] = String.valueOf(obImageUploadAdd.getStatus());
								/* DefaultLogger.debug(this,"Retriving image from DB2CM");
									imagePath=(String)contentManagerFactory.getContentManagerService().retrieveDocumentOnly(params,connection);
									 DefaultLogger.debug(this,"image retrieved from DB2CM");*/
									 
									 if(null == obImageUploadAdd.getHCPStatus() || obImageUploadAdd.getHCPStatus().equals("N")) {
										 DefaultLogger.debug(this,"Retriving image from DB2CM");
											imagePath=(String)contentManagerFactory.getContentManagerService().retrieveDocumentOnly(params,connection);
											 DefaultLogger.debug(this,"image retrieved from DB2CM");
											}else {
												System.out.println("inSide HTC..........");
												imagePath=(String)getContentManagerFactory().getContentManagerService().get(obImageUploadAdd.getHCPFileName(),String.valueOf(obImageUploadAdd.getStatus()));
												System.out.println("imagePath..........imagePath");
											
											}
									 
								downloadImageCount++;
							} catch (ContentManagerInitializationException e) {
								throw new CommandProcessingException(e.getMessage(),e);
							} catch (Exception e) {
								throw new CommandProcessingException(e.getMessage(),e);
							}		
							
							//imagePath="C:\\Documents and Settings\\All Users\\Documents\\My Pictures\\Sample Pictures\\"+obImageUploadAdd.getImgFileName();
							DefaultLogger.debug(this,"imagePath ---" + imagePath);
						//Added by Abhijit R===============Start
						
						
						if(imagePath!=null){
							try {
								
								
								byte[] buffer = new byte[180240];
								out.setLevel(Deflater.DEFAULT_COMPRESSION);
								
								FileInputStream in = new FileInputStream(basePath+imagePath);
								out.putNextEntry(new ZipEntry(obImageUploadAdd.getImgFileName()));
								int len;
								while ((len = in.read(buffer)) > 0){
								out.write(buffer, 0, len);
								}
								out.closeEntry();
								out.flush();
								in.close();
								/*ZipEntry entry=new ZipEntry(basePath+imagePath);
								out.putNextEntry(entry);*/
					           
								
								
								DefaultLogger.debug(this,"done");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
					}
					
					if(downloadImageCount == selectedArrayMap.size()){
						break;
					}
				}
				out.flush();
					out.close();
				
					
				
			}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				//TODO : Close connection
				try{
					DefaultLogger.debug(this,"Closing connection in downloading operation of image "+ connection);
					DB2ConnectionPool.getConnectionPoolInstance().returnObject(connection);
					DefaultLogger.debug(this,"connection closed in downloading operation of image "+ connection);
				}catch(Exception e){
					 e.printStackTrace();
					 DefaultLogger.debug(this,e);
				 }
			
					
				DefaultLogger.debug(this,"single connection has retrieved > " + downloadImageCount+" < no of images");
			}
	
			
			try {
				fileData = IOUtils.toByteArray(new FileInputStream(new File(basePath+"/dmsImages/"+zipFileName)));
				output.write(fileData);
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			result.put("output", output);
			result.put("imageName", zipFileName);
			//Added by Abhijit R ===============End
			
			//result.put("imagePath", imagePath);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			DefaultLogger.debug(this, "Going out of doExecute()");
			return returnMap;
			}
}
