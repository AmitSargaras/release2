package com.integrosys.cms.ui.imageTag;

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

public class RetriveUntagImageCommand extends AbstractCommand {

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
				{ "imageId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{"ImageTagMapObj","com.integrosys.cms.app.imageTag.bus.OBImageTagDetails",FORM_SCOPE },
				{ "searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "tageedImageList", "java.util.List", SERVICE_SCOPE },
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
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "count", "java.lang.String", REQUEST_SCOPE },
				{"ImageTagMapObj","com.integrosys.cms.app.imageTag.bus.OBImageTagDetails",FORM_SCOPE },
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "tageedImageList", "java.util.List", SERVICE_SCOPE },
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
		List tageedImageList =(List) map.get("tageedImageList");
		//SearchResult searchResult =(SearchResult) map.get("searchResult");
		List imageList =(List) tageedImageList;
		OBImageUploadAdd img = (OBImageUploadAdd) imageList.get(Integer.parseInt(count));
		
		String status=(String) map.get("status");
		String imagePath = "";
		pID= img.getImageFilePath();
		status= String.valueOf(img.getStatus());
		String imageName=(String) img.getImgFileName();
		String HtcOrDb2cm = PropertyManager.getValue("cms.image.htcOrDB2CM");

		String url=PropertyManager.getValue("hcp.rest.url");
		System.out.println("url............"+url);
		
		IImageTagDetails imageTagDetails = (IImageTagDetails) map.get("ImageTagMapObj");
		
		HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
		if(selectedArrayMap==null){ 
		selectedArrayMap = new HashMap();
		}
		String imageId =(String) map.get("imageId");
		String unCheckId =(String) map.get("unCheckId");
		try{
			if(imageTagDetails!=null){
				DefaultLogger.debug(this, "details.getCategory()============================================>"+imageTagDetails.getCategory());
				DefaultLogger.debug(this, "details.getSelectedArray()============================================>"+imageTagDetails.getSelectedArray());
				category=imageTagDetails.getCategory();
				String selectedArrayString=imageTagDetails.getSelectedArray();
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
				
				String unCheckArrayString=imageTagDetails.getUnCheckArray();
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
					//	imagePath=(String)getContentManagerFactory().getContentManagerService().retrieveDocument(params);
						
						if(null == img.getHCPStatus() || img.getHCPStatus().equals("N")) {
							imagePath=(String)getContentManagerFactory().getContentManagerService().retrieveDocument(params);
							}else {
								System.out.println("inSide HTC..........");
								imagePath=(String)getContentManagerFactory().getContentManagerService().get(img.getHCPFileName(),String.valueOf(img.getStatus()));
								System.out.println("imagePath..........imagePath");
							
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
		if(!((imageName.toUpperCase()).endsWith(".PDF"))){
		if(imagePath!=null){
			try {
				String basePath=PropertyManager.getValue("contextPath");
				//String pdfFilePath=basePath+"/dmsImages/"+pID+".pdf";
				String pdfFilePath="";
				if(null == img.getHCPStatus() || img.getHCPStatus().equals("N")) {
					pdfFilePath=basePath+"/dmsImages/"+pID+".pdf";
					}else {
					pdfFilePath=basePath+"/dmsImages/"+img.getHCPFileName().split("[.]")[0]+".pdf";	
					}
				
				//String pdfFilePath="C:/"+imageName+".pdf";
				OutputStream file = new FileOutputStream(new File(pdfFilePath));
				Document document = new Document(PageSize.A4, 0, 0, 0, 0);
				PdfWriter.getInstance(document, file);
				document.open();
				//document.add(new Paragraph("ImageName : "+imageName));
				com.lowagie.text.Image image = com.lowagie.text.Image.getInstance(basePath+imagePath);
			//	com.lowagie.text.Image image = 
			//		com.lowagie.text.Image.getInstance(imagePath);
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
				//String pdfFilePath=basePath+"/dmsImages/downloads/"+pID+".pdf";
				
				String pdfFilePath="";
				if(null == img.getHCPStatus() || img.getHCPStatus().equals("N")) {
					pdfFilePath=basePath+"/dmsImages/downloads/"+pID+".pdf";
					}else {
					pdfFilePath=basePath+"/dmsImages/downloads/"+img.getHCPFileName().split("[.]")[0]+".pdf";	
					}
				DefaultLogger.debug(this,"inside else pdfFilePath:"+pdfFilePath);
				pdfFilePath=pdfFilePath.replace(' ', '_');
				DefaultLogger.debug(this,"inside else pdfFilePath:"+pdfFilePath);
				fileData = IOUtils.toByteArray(new FileInputStream(new File(pdfFilePath)));
				output.write(fileData);
				DefaultLogger.debug(this,"done");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
}
		result.put("selectedArrayMap", selectedArrayMap);
		result.put("output", output);
		//Added by Anil ===============End
		result.put("ImageTagMapObj", imageTagDetails);
		result.put("count", count);
		result.put("status", status);
		result.put("imageName", imageName);
		result.put("event", event);
		result.put("category", category);
		result.put("pID", pID);
		result.put("imagePath", imagePath);
		result.put("custLimitProfileID", custLimitProfileID);
		result.put("tageedImageList", tageedImageList);
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		DefaultLogger.debug(this, "Going out of doExecute()");
		return returnMap;
	}
}
