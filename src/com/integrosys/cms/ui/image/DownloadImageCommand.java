package com.integrosys.cms.ui.image;
 
import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;

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
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.contentManager.exception.ContentManagerInitializationException;
import com.integrosys.cms.app.contentManager.factory.ContentManagerFactory;
import com.integrosys.cms.app.contentManager.ibmDb2Cm.connectionPool.DB2ConnectionPool;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.OBImageTagDetails;
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
				//{ "ImageTagMapObj", "com.integrosys.cms.app.imageTag.bus.IImageTagDetails", FORM_SCOPE },
				{ "facNames", "java.lang.String", REQUEST_SCOPE },
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				//{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "ImageUploadAddObj", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", FORM_SCOPE },
				{ "imageId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
//				{ "transactionID", "java.lang.String", REQUEST_SCOPE },
//				{ IGlobalConstant.GLOBAL_TRX_ID, "java.lang.String",GLOBAL_SCOPE },
//				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ,"com.integrosys.cms.app.limit.bus.ILimitProfile",GLOBAL_SCOPE },
//				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,"com.integrosys.cms.app.customer.bus.ICMSCustomer",GLOBAL_SCOPE },
//				{"customerSearchCriteria","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",FORM_SCOPE },
//				{"customerSearchCriteria1","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE },
//				{ "ImageTagAddObj","com.integrosys.cms.app.imageTag.bus.OBImageTagAdd",FORM_SCOPE },
				//{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "custId", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "obImageUploadAddList", "java.util.ArrayList", SERVICE_SCOPE },
//				{IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",GLOBAL_SCOPE },
				{ "frompage", "java.lang.String", REQUEST_SCOPE },
//				{ "from", "java.lang.String", REQUEST_SCOPE },
//				{ "indicator", "java.lang.String", REQUEST_SCOPE }, 
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
				{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
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
			String category=(String) map.get("facNames");
			String startIdx = (String) map.get("startIndex");
			String imageId =(String) map.get("imageId");
			String unCheckId =(String) map.get("unCheckId");
			String event = (String) map.get("event");
			ICollateralTrxValue collateralTrx = (ICollateralTrxValue) map.get(SERVICE_COLLATERAL_OBJ);
			System.out.println("======DownloadImageCommand=>collateralTrx=>"+collateralTrx);
			HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
			if(selectedArrayMap==null){ 
			selectedArrayMap = new HashMap();
			}
			result.put("startIndex", startIdx);
			String custId = (String) map.get("custId");
			String HtcOrDb2cm = PropertyManager.getValue("cms.image.htcOrDB2CM");

			String url=PropertyManager.getValue("hcp.rest.url");
			System.out.println("url............"+url);
			IImageUploadAdd details=(OBImageUploadAdd) map.get("ImageUploadAddObj");
			//String imageId =(String) details.getSelectedArray();
			//String unCheckId =(String)details.getUnCheckArray();
			List obImageUploadAddList=(List) map.get("obImageUploadAddList");
			//String strLimitProfileId=(String) map.get("custLimitProfileID");
			//long limitProfileID=Long.parseLong(strLimitProfileId);
			DefaultLogger.debug(this, "selectedArrayMap.size()============DownloadImageCommand============1====================>"+selectedArrayMap.size());
			try{
			DefaultLogger.debug(this, "DownloadImageCommand====================193========================>"+imageId);
				if(imageId!=null && !imageId.equals("")){
					String[] selected=imageId.split("-");
					if(selected!=null){
					for(int k=0;k<selected.length;k++){
						OBImageUploadAdd uploadImage = new OBImageUploadAdd();
						if(obImageUploadAddList!= null)
						{
					 uploadImage = (OBImageUploadAdd)obImageUploadAddList.get(Integer.parseInt(selected[k])-1);
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
							if(obImageUploadAddList!= null)
							{
						 uploadImage = (OBImageUploadAdd)obImageUploadAddList.get(Integer.parseInt(unchecked[ak])-1);
							}
							selectedArrayMap.remove(String.valueOf(uploadImage.getImgId()));
						}
						}
					}
					
				
			
			}catch (Exception e) {
				System.out.println("Exception image////DownloadImageCommand.java e=>"+e);
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
			//List obImageUploadAddList = new ArrayList();
			//retriving image list available for tagging
			//obImageUploadAddList =(List) map.get("obImageUploadAddList");
			 DKDatastoreICM connection = null;
			 int downloadImageCount =0;
			try {
				// basePath = "";
			if(obImageUploadAddList !=null){
				
				 out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(new File(basePath+"/dmsImages/"+zipFileName))));
				//out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(new File("C:/"+zipFileName))));
				
				 //TODO : Create connection
				 try{
					 DefaultLogger.debug(this,"Opening connection in downloading operation of image" + connection);
					  //connection = (DKDatastoreICM) DB2ConnectionPool.getConnectionPoolInstance().borrowObject();
					  DefaultLogger.debug(this,"connection opened in downloading operation of image" + connection);
					  System.out.println("connection opened in downloading operation of image" + connection);	
				 }catch(Exception e){
					 System.out.println( "Exception for connection opened in downloading operation of image" + connection);
					 e.printStackTrace();
				 }
				 
				 DefaultLogger.debug(this,"db2cm connectin " + connection);
				
				for(int a=0;a<obImageUploadAddList.size();a++){
					 DefaultLogger.debug(this,"inside for Loop---------------->"+ a);
					 System.out.println("inside for Loop---------------->"+ a);
					OBImageUploadAdd obImageUploadAdd= (OBImageUploadAdd) obImageUploadAddList.get(a);
					if(selectedArrayMap.containsKey(String.valueOf(obImageUploadAdd.getImgId()))){
						DefaultLogger.debug(this,"inside if Loop-------------->"+ a);
						 
						String imagePath="";
						ContentManagerFactory contentManagerFactory = (ContentManagerFactory)BeanHouse.get("contentManagerFactory");
						System.out.println("inside if Loop-------------->"+ a);
						
							try {
								Object[] params  = new Object[3];
								params[0] = obImageUploadAdd.getImageFilePath();
								params[1] = obImageUploadAdd.getImgFileName();
								params[2] = String.valueOf(obImageUploadAdd.getStatus()); 
									 if(null == obImageUploadAdd.getHCPStatus() || obImageUploadAdd.getHCPStatus().equals("N")) {
										 DefaultLogger.debug(this,"Retriving image from DB2CM");
										 System.out.println("Retriving image from DB2CM");	
											imagePath=(String)contentManagerFactory.getContentManagerService().retrieveDocumentOnly(params,connection);
											 DefaultLogger.debug(this,"image retrieved from DB2CM");
											 System.out.println("image retrieved from DB2CM");	
											}else {
												System.out.println("inSide HTC..........obImageUploadAdd.getImageFilePath()=>"+obImageUploadAdd.getImageFilePath()+" ** obImageUploadAdd.getImgFileName()=>"+obImageUploadAdd.getImgFileName()+" ** obImageUploadAdd.getHCPFileName()=>"+obImageUploadAdd.getHCPFileName()+" ** obImageUploadAdd.getStatus()=>"+obImageUploadAdd.getStatus());
												imagePath=(String)getContentManagerFactory().getContentManagerService().get(obImageUploadAdd.getHCPFileName(),String.valueOf(obImageUploadAdd.getStatus()));
												System.out.println("imagePath..........imagePath=>"+imagePath);	
											}
								 //	imagePath = "/clims/dms/uploadedImages/CUS0000960/banco.pdf";
								downloadImageCount++;
							} catch (ContentManagerInitializationException e) {
								System.out.println("Exception /image/DownloadImageCommand.java => ContentManagerInitializationException e=>"+e);
								throw new CommandProcessingException(e.getMessage(),e);
							} catch (Exception e) {
								System.out.println("Exception /image/DownloadImageCommand.java =>imagePath=>"+imagePath+" **  Exception e=>"+e);
								throw new CommandProcessingException(e.getMessage(),e);
							}	
							
					//  imagePath="C:\\Documents and Settings\\All Users\\Documents\\My Pictures\\Sample Pictures\\"+obImageUploadAdd.getImgFileName();
							DefaultLogger.debug(this,"imagePath ---" + imagePath);
							System.out.println("imagePath ---" + imagePath);
						//Added by Abhijit R===============Start
						
						
						if(imagePath!=null){
							try {
								
								
								byte[] buffer = new byte[180240];
								out.setLevel(Deflater.DEFAULT_COMPRESSION);
								
								FileInputStream in = new FileInputStream(basePath+imagePath);
								//FileInputStream in = new FileInputStream(imagePath);
								System.out.println("Zipping /image/DownloadImageCommand.java => obImageUploadAdd.getImgFileName()=>"+obImageUploadAdd.getImgFileName()+" ** basePath=>"+basePath+"** imagePath=>"+imagePath);
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
								System.out.println("done");
							} catch (Exception e) {
								System.out.println("Exception in Zipping /image/DownloadImageCommand.java => obImageUploadAdd.getImgFileName() =>"+obImageUploadAdd.getImgFileName()+" ** Exception e=>"+e);
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
				System.out.println("Exceptions image/DownloadImageCommand.java => Exceptions e=>"+e);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				//TODO : Close connection
				try{
					DefaultLogger.debug(this,"Closing connection in downloading operation of image "+ connection);
					System.out.println("Closing connection in downloading operation of image "+ connection);
//					DB2ConnectionPool.getConnectionPoolInstance().returnObject(connection);
					DefaultLogger.debug(this,"connection closed in downloading operation of image "+ connection);
					System.out.println("connection closed in downloading operation of image "+ connection);
				}catch(Exception e){
					System.out.println("Exceptions in finally image/DownloadImageCommand.java => zipFileName image/DownloadImageCommand.java=>"+zipFileName +" Exceptions finally e=>"+e);
					 e.printStackTrace();
					 DefaultLogger.debug(this,e);
				 }
			
					
				DefaultLogger.debug(this,"single connection has retrieved > " + downloadImageCount+" < no of images");
				System.out.println("single connection has retrieved > " + downloadImageCount+" < no of images");
			}
	
			
			try {
//				fileData = IOUtils.toByteArray(new FileInputStream(new File(basePath+"/dmsImages/"+zipFileName)));
				System.out.println("basePath=>"+basePath+" ** /dmsImages/ ** zipFileName=>"+zipFileName);
				fileData = IOUtils.toByteArray(new FileInputStream(new File(basePath+"/dmsImages/"+zipFileName)));
				//fileData = IOUtils.toByteArray(new FileInputStream(new File("C:/"+zipFileName)));
				output.write(fileData);
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("FileNotFoundException image/DownloadImageCommand.java=>zipFileNamed=>"+zipFileName+" **  e=>"+e);
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("IOException image/DownloadImageCommand.java=>zipFileNamed=>"+zipFileName+" ** e=>"+e);
				e.printStackTrace();
			}
			System.out.println("zipFileName image/DownloadImageCommand.java=>"+zipFileName);
			
			result.put("output", output);
			result.put(SERVICE_COLLATERAL_OBJ, collateralTrx);
			result.put("imageName", zipFileName);
			
			//Added by Abhijit R ===============End
			
			//result.put("imagePath", imagePath);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			DefaultLogger.debug(this, "Going out of doExecute()");
			System.out.println("Going out of doExecute()");
			return returnMap;
			}
}
