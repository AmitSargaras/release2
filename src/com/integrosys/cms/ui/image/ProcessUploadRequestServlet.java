/**
 * 
 */
package com.integrosys.cms.ui.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;

/**
 * @author anil.pandey
 *
 */
public class ProcessUploadRequestServlet extends HttpServlet {
	private static String UPLOAD_BASEPATH="image.upload.basepath"; 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		IImageUploadAdd ob= retrieveImageUploadDetailsFromSession(request);
		
		long createBy= retrieveUserFromSession(request);
		DefaultLogger.debug(this, "Entering in ProcessUploadRequestServlet");
		String fromServer=PropertyManager.getValue("integrosys.server.identification","app1");
		DefaultLogger.debug(this, "====================================on server====================================================="+fromServer);
		//============================================================================================================
		OBImageUploadAdd imageUploadAdd = new com.integrosys.cms.app.image.bus.OBImageUploadAdd();
		IImageUploadProxyManager imageUploadProxyManager = (IImageUploadProxyManager) BeanHouse.get("imageUploadProxy");
		String legalName ="";
		legalName = ob.getCustId();
		String custName = ob.getCustName();
		String strFileUploadSize = "50000000";
		long longFileUploadSize = Long.parseLong(strFileUploadSize);
		//*****
		byte[] cr = {13}; 
		byte[] lf = {10}; 
		String CR = new String(cr);
		String LF = new String(lf);
		String CRLF = CR + LF;
		DefaultLogger.debug(this,"Before a LF=chr(10)" + LF 
			+ "Before a CR=chr(13)" + CR 
			+ "Before a CRLF" + CRLF); 


	  //Initialization for chunk management.
	  boolean bLastChunk = false;
	  int numChunk = 0;
	  
	  //CAN BE OVERRIDEN BY THE postURL PARAMETER: if error=true is passed as a parameter on the URL
	  boolean generateError = false;  

	  response.setContentType("text/plain");
	  try{
	    // Get URL Parameters.
	    Enumeration paraNames = request.getParameterNames();
	    DefaultLogger.debug(this,"[ProcessUploadRequestServlet]  Parameters: ");
	    String pname;
	    String pvalue;
	    while (paraNames.hasMoreElements()) {
	      pname = (String)paraNames.nextElement();
	      pvalue = request.getParameter(pname);
//	      DefaultLogger.debug(this,"[ProcessUploadRequestServlet] " + pname + " = " + pvalue);
	      if (pname.equals("jufinal")) {
	      	bLastChunk = pvalue.equals("1");
	      } else if (pname.equals("jupart")) {
	      	numChunk = Integer.parseInt(pvalue);
	      }
	      //For debug convenience, putting error=true as a URL parameter, will generate an error
	      //in this class.
	      if (pname.equals("error") && pvalue.equals("true")) {
	      	generateError = true;
	      }
	       
	    }
	    DefaultLogger.debug(this,"[ProcessUploadRequestServlet]  ------------------------------ ");

	    // Directory to store all the uploaded files
	    String basePath = getUploadBasePath();
	    String custBasePath= basePath+ob.getCustId();
	    if("Y".equals(ob.getHasSubfolder())){
	    	custBasePath=custBasePath+System.getProperty("file.separator")+ob.getSubfolderName();
	    }
	    
	    File file = new File(custBasePath);
	    if(!file.exists()) {
	    	file.mkdirs();
		}
	    custBasePath=custBasePath+System.getProperty("file.separator");
	    
	    int ourMaxMemorySize  = 10000000;
	    int ourMaxRequestSize = 2000000000;

		///////////////////////////////////////////////////////////////////////////////////////////////////////
		//The code below is directly taken from the jakarta fileupload common classes
		//All informations, and download, available here : http://jakarta.apache.org/commons/fileupload/
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		// Set factory constraints
		factory.setSizeThreshold(ourMaxMemorySize);
		factory.setRepository(new File(custBasePath));
		
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		// Set overall request size constraint
		upload.setSizeMax(ourMaxRequestSize);
		
		// Parse the request
		if (! request.getContentType().startsWith("multipart/form-data")) {
			DefaultLogger.debug(this,"[ProcessUploadRequestServlet] No parsing of uploaded file: content type is " + request.getContentType()); 
		} else { 
			List /* FileItem */ items = upload.parseRequest(request);
			// Process the uploaded items
			Iterator iter = items.iterator();
			FileItem fileItem;
		    File fout;
		    DefaultLogger.debug(this,"[ProcessUploadRequestServlet]  Let's read input files ...");
			while (iter.hasNext()) {
			    fileItem = (FileItem) iter.next();
			    if (fileItem.isFormField()) {
			    	DefaultLogger.debug(this,"[ProcessUploadRequestServlet] Inside if line no. 151");
			    	DefaultLogger.debug(this,"[ProcessUploadRequestServlet] (form field) " + fileItem.getFieldName() + " = " + fileItem.getString());
			    	DefaultLogger.debug(this,"[ProcessUploadRequestServlet] Line No. 153 => File Name: " + fileItem.getName());
			        //If we receive the md5sum parameter, we've read finished to read the current file. It's not
			        //a very good (end of file) signal. Will be better in the future ... probably !
			        //Let's put a separator, to make System.output easier to read.
			        if (fileItem.getFieldName().equals("md5sum[]")) { 
			        	DefaultLogger.debug(this,"[ProcessUploadRequestServlet] line no. 158 ------------------------------ ");
					}
			    } else {
			        //Ok, we've got a file. Let's process it.
			        //Again, for all informations of what is exactly a FileItem, please
			        //have a look to http://jakarta.apache.org/commons/fileupload/
			        //
			    	DefaultLogger.debug(this,"[ProcessUploadRequestServlet] Inside else line no. 165");
			    	DefaultLogger.debug(this,"[ProcessUploadRequestServlet] FieldName: " + fileItem.getFieldName());
			    	DefaultLogger.debug(this,"[ProcessUploadRequestServlet] File Name: " + fileItem.getName());
			    	DefaultLogger.debug(this,"[ProcessUploadRequestServlet] ContentType: " + fileItem.getContentType());
			    	DefaultLogger.debug(this,"[ProcessUploadRequestServlet] Size (Bytes): " + fileItem.getSize());
			        //If we are in chunk mode, we add ".partN" at the end of the file, where N is the chunk number.
			        String uploadedFilename = fileItem.getName() + ( numChunk>0 ? ".part"+numChunk : "") ;
			        DefaultLogger.debug(this,"[ProcessUploadRequestServlet] Line No. 172 => File uploadedFilename: " + uploadedFilename+" ** custBasePath=>"+custBasePath);
			        String fileName = extractFileName(uploadedFilename);
			        fout = new File(custBasePath + fileName);
			        DefaultLogger.debug(this,"[ProcessUploadRequestServlet]  Line No. 174 => File System.out   fout: " + fout.toString());
			        // write the file
			        fileItem.write(fout);	        
			        DefaultLogger.debug(this,"[ProcessUploadRequestServlet] After fileItem.write(fout) Line No. 177 ");
			        //////////////////////////////////////////////////////////////////////////////////////
			        //Chunk management: if it was the last chunk, let's recover the complete file
			        //by concatenating all chunk parts.
			        //
			       
			        if (bLastChunk) {	
			        	DefaultLogger.debug(this," bLastChunk => Inside If Line No. 184");
			        	//*******Add by Govind S
					    imageUploadAdd.setImgFileName(fileName);
						imageUploadAdd.setImageFilePath(custBasePath + fileName);
						longFileUploadSize = (longFileUploadSize*(numChunk-1))+fileItem.getSize();
						imageUploadAdd.setImgSize(longFileUploadSize);
						imageUploadAdd.setCustId(ob.getCustId());
						imageUploadAdd.setCustName(ob.getCustName());
						imageUploadAdd.setCategory(ob.getCategory());
						//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
						imageUploadAdd.setBank(ob.getBank());
						//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
						imageUploadAdd.setFacilityName(ob.getFacilityName());
						imageUploadAdd.setFacilityDocName(ob.getFacilityDocName());
						imageUploadAdd.setOtherDocName(ob.getOtherDocName());
						imageUploadAdd.setSecurityNameId(ob.getSecurityNameId());
						imageUploadAdd.setSecurityDocName(ob.getSecurityDocName());
						imageUploadAdd.setOtherSecDocName(ob.getOtherSecDocName());
						imageUploadAdd.setTypeOfDocument(ob.getTypeOfDocument());
						imageUploadAdd.setHasFacility(ob.getHasFacility());
						imageUploadAdd.setHasSecurity(ob.getHasSecurity());
						imageUploadAdd.setHasCam(ob.getHasCam());
						imageUploadAdd.setCamDocName(ob.getCamDocName());
						imageUploadAdd.setStatementTyped(ob.getStatementTyped());
						imageUploadAdd.setStatementDocName(ob.getStatementDocName());
						imageUploadAdd.setOthersDocsName(ob.getOthersDocsName());
						imageUploadAdd.setSecurityIdi(ob.getSecurityIdi());
						imageUploadAdd.setSubTypeSecurity(ob.getSubTypeSecurity());
						//Added By Prachit
						imageUploadAdd.setHasSubfolder(ob.getHasSubfolder());
						imageUploadAdd.setSubfolderName(ob.getSubfolderName());
						imageUploadAdd.setImgDepricated("N");
						imageUploadAdd.setSendForAppFlag("N");
						imageUploadAdd.setCreateBy(createBy);
						imageUploadAdd.setCreationDate(new Date());
						imageUploadAdd.setDocumentName(ob.getDocumentName());
						imageUploadAdd.setFromServer(fromServer);
						imageUploadProxyManager.createImageUploadAdd(imageUploadAdd, false);
						System.out.println("ProcessUploadRequestServlet.java if=> imageUploadAdd.setSecurityIdi = "+ob.getSecurityIdi()+"  imageUploadAdd.setSecurityNameId = "+ob.getSecurityIdi());
					    //********govind s	
						DefaultLogger.debug(this,"[ProcessUploadRequestServlet]  Last chunk received: let's rebuild the complete file (" + fileItem.getName() + ")");
				        //First: construct the final filename.
				        FileInputStream fis;
				        FileOutputStream fos = new FileOutputStream(custBasePath+ fileName);
				        int nbBytes;
				        byte[] byteBuff = new byte[1024];
				        String filename;
				        for (int i=1; i<=numChunk; i+=1) {
				        	filename = fileName + ".part" + i;
				        	DefaultLogger.debug(this,"[ProcessUploadRequestServlet] " + "  Concatenating " + filename);
				        	fis = new FileInputStream(custBasePath+ filename);
				        	while ( (nbBytes = fis.read(byteBuff)) >= 0) {
				        		//DefaultLogger.debug(this,"[ProcessUploadRequestServlet] " + "     Nb bytes read: " + nbBytes);
				        		fos.write(byteBuff, 0, nbBytes);
				        	}	

				        	fis.close();
				        }
				        
				    	
				        fos.close();
			        }else
			        {
			        	DefaultLogger.debug(this," bLastChunk => Inside else Line No. 247");
			        	if(numChunk==0) {	
			        	//*******Add by Govind S
					    imageUploadAdd.setImgFileName(fileName);
						imageUploadAdd.setImageFilePath(custBasePath+fileName);
						imageUploadAdd.setImgSize(fileItem.getSize());
						imageUploadAdd.setCustId(ob.getCustId());
						imageUploadAdd.setCustName(ob.getCustName());
						imageUploadAdd.setCategory(ob.getCategory());
						//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
						imageUploadAdd.setBank(ob.getBank());
						//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
						imageUploadAdd.setFacilityName(ob.getFacilityName());
						imageUploadAdd.setFacilityDocName(ob.getFacilityDocName());
						imageUploadAdd.setOtherDocName(ob.getOtherDocName());
						imageUploadAdd.setSecurityNameId(ob.getSecurityNameId());
						imageUploadAdd.setSecurityDocName(ob.getSecurityDocName());
						imageUploadAdd.setOtherSecDocName(ob.getOtherSecDocName());
						imageUploadAdd.setTypeOfDocument(ob.getTypeOfDocument());
						imageUploadAdd.setHasFacility(ob.getHasFacility());
						imageUploadAdd.setHasSecurity(ob.getHasSecurity());
						imageUploadAdd.setHasCam(ob.getHasCam());
						imageUploadAdd.setCamDocName(ob.getCamDocName());
						imageUploadAdd.setStatementTyped(ob.getStatementTyped());
						imageUploadAdd.setStatementDocName(ob.getStatementDocName());
						imageUploadAdd.setOthersDocsName(ob.getOthersDocsName());
						imageUploadAdd.setSecurityIdi(ob.getSecurityIdi());
						imageUploadAdd.setSubTypeSecurity(ob.getSubTypeSecurity());
						//Added By Prachit
						imageUploadAdd.setHasSubfolder(ob.getHasSubfolder());
						imageUploadAdd.setSubfolderName(ob.getSubfolderName());
						imageUploadAdd.setImgDepricated("N");
						imageUploadAdd.setSendForAppFlag("N");
						imageUploadAdd.setCreateBy(createBy);
						imageUploadAdd.setCreationDate(new Date());
						imageUploadAdd.setDocumentName(ob.getDocumentName());
						imageUploadAdd.setFromServer(fromServer);
						System.out.println("ProcessUploadRequestServlet.java else=> imageUploadAdd.setSecurityIdi = "+ob.getSecurityIdi()+"  imageUploadAdd.setSecurityNameId = "+ob.getSecurityIdi());
//						DefaultLogger.debug(this,"------------------------=====22222222222=========>");
						imageUploadProxyManager.createImageUploadAdd(imageUploadAdd, false);
//						DefaultLogger.debug(this,"------------------------====3333333333==========>");

					    //********govind s	
			        	}
			        }
			        
			        
			        // End of chunk management
			        //////////////////////////////////////////////////////////////////////////////////////
			        fileItem.delete();
			    }	    
			}//while
		}
		
		DefaultLogger.debug(this,"[ProcessUploadRequestServlet] " + "Let's write a status, to finish the server response :");
	    //DefaultLogger.debug(this,"WARNING: just a warning message.\\nOn two lines!");
	  	
	    //Do you want to test a successful upload, or the way the applet reacts to an error ?
	    if (generateError) { 
	    	DefaultLogger.debug(this,"ERROR: this is a test error (forced in /wwwroot/pages/ProcessUploadRequestServlet).\\nHere is a second line!");
	    } else {
	    	DefaultLogger.debug(this,"SUCCESS");
	    }

	    DefaultLogger.debug(this,"[ProcessUploadRequestServlet] " + "End of server treatment ");

	  }catch(Exception e){
		  DefaultLogger.debug(this,"");
		  DefaultLogger.debug(this,"ERROR: Exception e = " + e.toString());
		  DefaultLogger.debug(this,"");
	  }
		DefaultLogger.debug(this, "Going out of ProcessUploadRequestServlet...............");
	
		getServletContext().getRequestDispatcher("/image/redirect_upload_images.jsp").forward(
				request, response);
	}
	private IImageUploadAdd retrieveImageUploadDetailsFromSession(
			HttpServletRequest request) {
//		IImageUploadAdd ob = (IImageUploadAdd) request.getSession().getAttribute("com.integrosys.cms.ui.image.ImageUploadAction.ImageUploadAddObjSession");
//		return ob;
		//userID & CustId & typeOfDoc & facName & facDocName & otherFacDocName & secName & secDocName & othersecDocName & camNum
		IImageUploadAdd ob = new OBImageUploadAdd();
//		String CustId=request.getParameter("CustId");
////		String CustName=request.getParameter("CustName");
//		String Catg=request.getParameter("Catg");
//		String HasSF=request.getParameter("HasSF");
//		String sfName=request.getParameter("sfName");
//		String docName=request.getParameter("docName");
//		//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
//		String bankName=request.getParameter("bankName");
		//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
		
		String CustId=request.getParameter("CustId");
		String typeOfDoc=request.getParameter("typeOfDoc");
		String Catg=request.getParameter("Catg");
		String facName=request.getParameter("facName");
		String facDocName=request.getParameter("facDocName");
		String otherFacDocName=request.getParameter("otherFacDocName");
		String secName=request.getParameter("secName");
		String secDocName=request.getParameter("secDocName");
		String othersecDocName=request.getParameter("othersecDocName");
		String camNum=request.getParameter("camNum");
		String statementdocName=request.getParameter("statementdocName");
		String statementTyped=request.getParameter("statementType");
		String camDocName=request.getParameter("camDocName");
		String otherDocName=request.getParameter("otherDocName");
		String bankName=request.getParameter("bankName");
		String securityIdi=request.getParameter("securityIdi");
		String subTypeSecurity=request.getParameter("subTypeSecurity");
		
		String customerName ="";
		ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
		List customerList = customerDAO.searchCustomerByCIFNumber(CustId);
		if(customerList!=null){
			OBCMSCustomer customer=(OBCMSCustomer)customerList.get(0);
			customerName=customer.getCustomerName();
		}

		ob.setTypeOfDocument(typeOfDoc);
		ob.setFacilityName(facName);
		ob.setFacilityDocName(facDocName);
		ob.setOtherDocName(otherFacDocName);
		ob.setSecurityNameId(secName);
		ob.setSecurityDocName(secDocName);
		ob.setOtherSecDocName(othersecDocName);
		ob.setHasCam(camNum);
		
		ob.setCustId(CustId);
		ob.setCustName(customerName);
		ob.setCategory(Catg);
		ob.setBank(bankName);
		ob.setCamDocName(camDocName);
		ob.setStatementDocName(statementdocName);
		ob.setStatementTyped(statementTyped);
		ob.setOthersDocsName(otherDocName);
		ob.setSecurityIdi(securityIdi);
		ob.setSubTypeSecurity(subTypeSecurity);
		System.out.println("ProcessUploadRequestServlet.java => ob.setSecurityIdi(securityIdi) = "+securityIdi+"  ob.setSecurityNameId(secName) = "+secName);
//		//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
//		ob.setBank(bankName);
//		//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
//		
//		if("on".equals(HasSF)){
//			ob.setHasSubfolder("Y");
//		}else{
//			ob.setHasSubfolder("N");
//		}
////		ob.setHasSubfolder(HasSF);
//		ob.setSubfolderName(sfName);
//		ob.setDocumentName(docName);
		
		return ob;
	}	

	private long retrieveUserFromSession(
			HttpServletRequest request) {
		//OBCommonUser user = (OBCommonUser) request.getSession().getAttribute("global.ILosUser");
//		return user.getUserID();
		String userID=request.getParameter("userID");
		long usrId=0;
		if(userID!=null)
			usrId= Long.parseLong(userID);
		
		return usrId;
	}
	
	private String getUploadBasePath(){
		return PropertyManager.getValue(UPLOAD_BASEPATH);
	}
	
	private static String extractFileName(String filePathName) {
		if (filePathName == null)
			return null;
		int slashPos = filePathName.lastIndexOf('\\');
		if (slashPos == -1)
			slashPos = filePathName.lastIndexOf('/');
		return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0, filePathName.length());
	}
}
