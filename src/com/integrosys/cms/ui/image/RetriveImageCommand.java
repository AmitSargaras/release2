package com.integrosys.cms.ui.image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.contentManager.exception.ContentManagerInitializationException;
import com.integrosys.cms.app.contentManager.factory.ContentManagerFactory;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

/**
 * This command creates a Image Tag
 * 
 * 
 * 
 */

public class RetriveImageCommand extends AbstractCommand {

	private ContentManagerFactory contentManagerFactory;

	public ContentManagerFactory getContentManagerFactory() {
		return contentManagerFactory;
	}

	public void setContentManagerFactory(ContentManagerFactory contentManagerFactory) {
		this.contentManagerFactory = contentManagerFactory;
	}

	public String[][] getParameterDescriptor() {
		DefaultLogger.debug(this, "******** getParameterDescriptor Call: ");
		return (new String[][] {
				{ "pID", "java.lang.String", REQUEST_SCOPE },
				{ "category", "java.lang.String", REQUEST_SCOPE },
				{ "imageName", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{"ImageUploadAddObj", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", FORM_SCOPE},
				{ "ImageUploadAddObjSession", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", SERVICE_SCOPE},
				
				{ "searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "obImageUploadAddList", "java.util.List", SERVICE_SCOPE },
				{ "imageId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "customerName","java.lang.String",REQUEST_SCOPE },
				{ "legalName","java.lang.String",REQUEST_SCOPE },
				
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
				{ "count", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		DefaultLogger.debug(this, "********  getResultDescriptor Call: ");
		return (new String[][] { 
				{ "imagePath", "java.lang.String", REQUEST_SCOPE },
				{ "pID", "java.lang.String", REQUEST_SCOPE },
				{ "category", "java.lang.String", REQUEST_SCOPE },
				{ "imageName", "java.lang.String", REQUEST_SCOPE },
				{ "count", "java.lang.String", REQUEST_SCOPE },
				{"ImageUploadAddObj", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", FORM_SCOPE},
				{ "ImageUploadAddObjSession", "com.integrosys.cms.app.image.bus.OBImageUploadAdd", SERVICE_SCOPE},
				
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "customerName","java.lang.String",REQUEST_SCOPE },
				{ "legalName","java.lang.String",REQUEST_SCOPE },
				
				{ "searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "obImageUploadAddList", "java.util.List", SERVICE_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "output", "java.io.ByteArrayOutputStream", REQUEST_SCOPE },
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
	CommandValidationException {
		HashMap result = new HashMap();
		HashMap returnMap = new HashMap();
		DefaultLogger.debug(this, "Enter in doExecute()");
		String pID=(String) map.get("pID");
		String category=(String) map.get("category");
		String count=(String) map.get("count");
		String custLimitProfileID=(String) map.get("custLimitProfileID");
		String event=(String) map.get("event");
		String custId= (String) map.get("legalName");
		String custName=(String) map.get("customerName");
		List obImageUploadAddList =(List) map.get("obImageUploadAddList");
		IImageUploadAdd imageUploadAdd = (IImageUploadAdd) map.get("ImageUploadAddObjSession");
		String HtcOrDb2cm = PropertyManager.getValue("cms.image.htcOrDB2CM");

		IImageUploadAdd imageUploaded = (IImageUploadAdd) map.get("ImageUploadAddObj");
		String status=(String) map.get("status");
		OBImageUploadAdd img = (OBImageUploadAdd) obImageUploadAddList.get(Integer.parseInt(count));
		pID= img.getImageFilePath();
		status= String.valueOf(img.getStatus());
		String imageName=(String) img.getImgFileName();
		
		String imagePath = "";
			
		HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
		if(selectedArrayMap==null){ 
		selectedArrayMap = new HashMap();
		}
		String imageId =(String) map.get("imageId");
		String unCheckId =(String) map.get("unCheckId");
		try{
			if(imageUploaded!=null){
				DefaultLogger.debug(this, "details.getCategory()============================================>"+imageUploaded.getCategory());
				DefaultLogger.debug(this, "details.getSelectedArray()============================================>"+imageUploaded.getSelectedArray());
				category=imageUploaded.getCategory();
				String selectedArrayString=imageUploaded.getSelectedArray();
				if(selectedArrayString!=null && !selectedArrayString.equals("") ){
				String[] selected=selectedArrayString.split("-");
				if(selected!=null){
				for(int k=0;k<selected.length;k++){
				selectedArrayMap.put(selected[k], selected[k]);
				}
				}
				}else{
					DefaultLogger.debug(this, "ImageTagResultCommand====================193========================>"+imageId);
				if(imageId!=null && !imageId.equals("")){
					String[] selected=imageId.split("-");
					if(selected!=null){
					for(int k=0;k<selected.length;k++){
					selectedArrayMap.put(selected[k], selected[k]);
					}
					}
				}
					
				}
				
				String unCheckArrayString=imageUploaded.getUnCheckArray();
				if(unCheckArrayString!=null && !unCheckArrayString.equals("")){
					String[] unchecked=unCheckArrayString.split("-");
					if(unchecked!=null){
						for(int ak=0;ak<unchecked.length;ak++){
						selectedArrayMap.remove(unchecked[ak]);
						}
						}
				}else{
					DefaultLogger.debug(this, "ImageTagResultCommand====================214========================>"+unCheckId);
					if(unCheckId!=null && !unCheckId.equals("")){
					String[] unchecked=unCheckId.split("-");
					if(unchecked!=null){
						for(int ak=0;ak<unchecked.length;ak++){
						selectedArrayMap.remove(unchecked[ak]);
						}
						}
					}
					
				}
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		
		DefaultLogger.debug(this,"doExecute ---" + pID + "    " + imageName + "      " + status);
		/*if(count!=null && !count.equals("")&& Integer.parseInt(count)%2==0){
			imagePath="C:\\Documents and Settings\\All Users\\Documents\\My Pictures\\Sample Pictures"+"\\"+imageName;
		}
		else{
			imagePath="C:\\Documents and Settings\\All Users\\Documents\\My Pictures\\Sample Pictures"+"\\"+imageName;
		}*/
					try {
						Object[] params  = new Object[3];
					params[0] = pID;
						params[1] = imageName;
						params[2] = status;
						String url=PropertyManager.getValue("hcp.rest.url");
						System.out.println("url............"+url);
						if(null == img.getHCPStatus() || img.getHCPStatus().equals("N")) {
						imagePath=(String)getContentManagerFactory().getContentManagerService().retrieveDocument(params);
						}else {
							System.out.println("inSide HTC..........");
							System.out.println("inSide HTC img.getHCPFileName().........."+img.getHCPFileName());
							imagePath=(String)getContentManagerFactory().getContentManagerService().get(img.getHCPFileName(),String.valueOf(img.getStatus()));
							System.out.println("imagePath.........."+imagePath);
						
						}
					} catch (ContentManagerInitializationException e) {
						throw new CommandProcessingException(e.getMessage(),e);
					} catch (Exception e) {
						throw new CommandProcessingException(e.getMessage(),e);
					}	
		DefaultLogger.debug(this,"imagePath ---" + imagePath);
		//Added by Anil===============Start
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] fileData;
		DefaultLogger.debug(this,"before if imagename: ");
		if(!((imageName.toUpperCase()).endsWith(".PDF"))){
			DefaultLogger.debug(this,"inside if imagename");
		if(imagePath!=null){
			try {
				String basePath=PropertyManager.getValue("contextPath");
				String pdfFilePath= "";
				if(null == img.getHCPStatus() || img.getHCPStatus().equals("N")) {
				pdfFilePath=basePath+"/dmsImages/"+pID+".pdf";
				}else {
				System.out.println("img.getHCPStatus()img.getHCPStatus().........."+img.getHCPStatus());
				System.out.println("pdfFilePath.........."+pdfFilePath);
				pdfFilePath=basePath+"/dmsImages/"+img.getHCPFileName().split("[.]")[0]+".pdf";
				System.out.println("img.getHCPFileName().split(\"[.]\")[0]............."+img.getHCPFileName().split("[.]")[0]);
				System.out.println("pdfFilePath.........."+pdfFilePath);
				}
				//String pdfFilePath="C:/"+imageName+".pdf";
				OutputStream file = new FileOutputStream(new File(pdfFilePath));
				Document document = new Document(PageSize.A4, 0, 0, 0, 0);
				PdfWriter.getInstance(document, file);
				document.open();
				//document.add(new Paragraph("ImageName : "+imageName));
				com.lowagie.text.Image image = 
				com.lowagie.text.Image.getInstance(basePath+imagePath);
				//com.lowagie.text.Image image = 
				//com.lowagie.text.Image.getInstance(imagePath);
				//float scaledHeight =Float.parseFloat(PropertyManager.getValue("integrosys.image.scaledHeight"));	
				//float scaledWidth = Float.parseFloat(PropertyManager.getValue("integrosys.image.scaledWidth"));	
				float scaledHeight =new Float(842.0);	
				float scaledWidth = new Float(595.0);
				if(image.getScaledHeight()> scaledHeight && image.getScaledWidth()>scaledWidth)
				{
				image.scalePercent(24f);
				image.scaleAbsoluteHeight(PageSize.A4.getHeight());
				image.scaleAbsoluteWidth(PageSize.A4.getWidth());
				image.setAbsolutePosition(0, 0);
				}
				document.add(image);
				document.close();
				file.close();
				fileData = IOUtils.toByteArray(new FileInputStream(new File(pdfFilePath)));
				output.write(fileData);
				DefaultLogger.debug(this,"done");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		}
		else {
			try{
				DefaultLogger.debug(this,"inside else imagename");
				String basePath=PropertyManager.getValue("contextPath");
				String pdfFilePath="";
				if(null == img.getHCPStatus() || img.getHCPStatus().equals("N")) {
					pdfFilePath=basePath+"/dmsImages/downloads/"+pID+".pdf";
					}else {
					pdfFilePath=basePath+"/dmsImages/downloads/"+img.getHCPFileName().split("[.]")[0]+".pdf";	
					System.out.println("In else...pdfFilePath.............."+pdfFilePath);
					}
				DefaultLogger.debug(this,"inside else pdfFilePath:"+pdfFilePath);
				pdfFilePath=pdfFilePath.replace(' ', '_');
				DefaultLogger.debug(this,"inside else pdfFilePath:"+pdfFilePath);
				fileData = IOUtils.toByteArray(new FileInputStream(new File(pdfFilePath)));
				DefaultLogger.debug(this,"inside else fileData:"+fileData);
				output.write(fileData);
				DefaultLogger.debug(this,"done");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
}
		result.put("output", output);
		//Added by Anil ===============End
		result.put("ImageTagMapObj", imageUploaded);
		result.put("selectedArrayMap", selectedArrayMap);
		result.put("count", count);
		result.put("status", status);
		result.put("imageName", imageName);
		result.put("event", event);
		result.put("category", category);
		result.put("pID", pID);
		result.put("imagePath", imagePath);
		result.put("custLimitProfileID", custLimitProfileID);
		result.put("obImageUploadAddList", obImageUploadAddList);
		result.put("ImageUploadAddObjSession", null);
		result.put("legalName", custId);
		result.put("customerName", custName);
		//result.put("imageTrxObj", trxValue);
		//Added by Anil for mapping the ob to form
		result.put("ImageUploadAddObj", imageUploadAdd);
		
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		DefaultLogger.debug(this, "Going out of doExecute()");
		return returnMap;
	}
}
