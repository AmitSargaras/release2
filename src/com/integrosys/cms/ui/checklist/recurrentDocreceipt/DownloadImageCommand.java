package com.integrosys.cms.ui.checklist.recurrentDocreceipt;
 
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

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
		DefaultLogger.debug(this, "******** getParameterDescriptor Call: ");
		return (new String[][] {
				{ "pID", "java.lang.String", REQUEST_SCOPE },
				{ "imageName", "java.lang.String", REQUEST_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

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
			CommandValidationException {
		HashMap result = new HashMap();
		HashMap returnMap = new HashMap();
		DefaultLogger.debug(this, "Enter in doExecute()");
		String pID=(String) map.get("pID");
		String imageName=(String) map.get("imageName");
		String status=(String) map.get("status");
		DefaultLogger.debug(this,"doExecute ---" + pID + "    " + imageName + "      " + status);
		ContentManagerFactory contentManagerFactory = (ContentManagerFactory)BeanHouse.get("contentManagerFactory");
		String imagePath="";
			try {
				Object[] params  = new Object[3];
				params[0] = pID;
				params[1] = imageName;
				params[2] = status;
				imagePath=(String)contentManagerFactory.getContentManagerService().retrieveDocument(params);
			} catch (ContentManagerInitializationException e) {
				throw new CommandProcessingException(e.getMessage(),e);
			} catch (Exception e) {
				throw new CommandProcessingException(e.getMessage(),e);
			}		
		//imagePath="C:\\Documents and Settings\\All Users\\Documents\\My Pictures\\Sample Pictures"+"\\"+imageName;
			DefaultLogger.debug(this,"imagePath ---" + imagePath);
		//Added by Anil===============Start
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] fileData;
		if(imagePath!=null){
			try {
				String basePath=PropertyManager.getValue("contextPath");
				//String pdfFilePath=basePath+"/dmsImages/"+pID+".pdf";
				String pdfFilePath="C://"+imageName;
			
				
				/*OutputStream file = new FileOutputStream(new File(pdfFilePath));
				//Document document = new Document();
				//PdfWriter.getInstance(document, file);
				//document.open();
				//document.add(new Paragraph("ImageName : "+imageName));
				//com.lowagie.text.Image image = com.lowagie.text.Image.getInstance(basePath+imagePath);
				com.lowagie.text.Image image = com.lowagie.text.Image.getInstance(imagePath);
				image.scalePercent(24f);
				//document.add(image);
				//document.close();
				file.write(image);
				file.close();*/
				
				fileData = IOUtils.toByteArray(new FileInputStream(new File(basePath+imagePath)));
				output.write(fileData);
				
				DefaultLogger.debug(this,"done");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		result.put("output", output);
		result.put("imageName", imageName);
		//Added by Anil ===============End
		
		result.put("imagePath", imagePath);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		DefaultLogger.debug(this, "Going out of doExecute()");
		return returnMap;
	}
}
