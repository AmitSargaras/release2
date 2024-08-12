/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genlad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.lad.bus.GeneratePartyLADDao;
import com.integrosys.cms.app.lad.proxy.ILADProxyManager;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/11/15 12:50:06 $ Tag: $Name: $
 */
public class GenerateLADPartyCommand extends AbstractCommand implements
		ICommonEventConstant {

	private ILADProxyManager ladProxy;

	List<String> filesListInDir = new ArrayList<String>();

	public ILADProxyManager getLadProxy() {
		return ladProxy;
	}

	public void setLadProxy(ILADProxyManager ladProxy) {
		this.ladProxy = ladProxy;
	}

	/**
	 * Default Constructor
	 */
	public GenerateLADPartyCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ,
						"com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						GLOBAL_SCOPE },
				{ "contextPath", "java.lang.String", GLOBAL_SCOPE },
				{ "OBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "generateLadFormObj",
						"com.integrosys.cms.ui.genlad.OBFilter", FORM_SCOPE },
				{ "ladName", "java.lang.String", REQUEST_SCOPE },
				{ "relationshipManagerId", "java.lang.String", REQUEST_SCOPE },
				{ "relationshipManagerName", "java.lang.String", REQUEST_SCOPE },
				{"USER_LOGIN_ID", "java.lang.String",GLOBAL_SCOPE }

		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {

				{ "resultList", "java.util.List", SERVICE_SCOPE },
				{ "isRecordAvailable", "java.lang.String", SERVICE_SCOPE },
				{ "dateValue", "java.lang.String", SERVICE_SCOPE },
				{ "recordAlreadyPresent", "java.lang.String", SERVICE_SCOPE },
				{ "filePath", "java.lang.String", SERVICE_SCOPE },
				{ "custDetail",
						"com.integrosys.cms.app.ddn.bus.IDDNCustomerDetail",
						SERVICE_SCOPE },
				{ "certTrxVal", "com.integrosys.cms.app.ddn.trx.IDDNTrxValue",
						SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.ddn.bus.IDDN", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.ddn.bus.IDDN", FORM_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE },
				{ "error", "java.lang.String", REQUEST_SCOPE },
				{ "bflFinalIssueDate", "java.lang.String", REQUEST_SCOPE },
				{ "fileName", "java.lang.String", REQUEST_SCOPE },
				{ "reportfile", "java.lang.String", SERVICE_SCOPE },
				{ "customerObject", "java.util.List", SERVICE_SCOPE },
				{ "docsHeldMap", "java.util.HashMap", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	@SuppressWarnings("unchecked")
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {

		GeneratePartyLADDao partyLadDao = (GeneratePartyLADDao) BeanHouse
				.get("ladPartyDao");
		List resultList = null;

		Map resultMap = new HashMap();

		HashMap returnMap = new HashMap();

		OBFilter filter = (OBFilter) map.get("generateLadFormObj");

//		String relationshipMgrId = filter.getRelationshipMgrId();
//		String relationshipMgr = filter.getRelationshipMgr();
//		if (relationshipMgrId == null || "".equals(relationshipMgrId)) {
//			relationshipMgrId = "ALL";
//			relationshipMgr = "ALL";
//		}
		
		String relationshipMgrId=(String)map.get("relationshipManagerId");
		String relationshipMgr=(String)map.get("relationshipManagerName");
		if (relationshipMgrId == null || "".equals(relationshipMgrId)) {
			relationshipMgrId = "ALL";
			relationshipMgr = "ALL";
		}
		
		String party = filter.getParty();
		String partyId = filter.getPartyId();
		if ((partyId == null || "".equals(partyId))
				&& (party == null || "".equals(party))) {
			partyId = "ALL";
			party = "ALL";
		}
		String dueMonth = filter.getDueMonth();
		String dueYear = filter.getDueYear();
		String segment = filter.getSegment();

		if (segment == null || "".equals(segment)) {
			segment = "ALL";
			segment = "ALL";
		}

		
		DefaultLogger.debug(this,
				  "Checking if filter is present or no ---------------");
		int isFilterPresent = partyLadDao.isFilterAlreadyPresent(
				relationshipMgrId, partyId, dueMonth, dueYear, segment);
		
		DefaultLogger.debug(this,
				  "Checking if filter is present completed ---------------");

		if (isFilterPresent == 0) {
			DefaultLogger.debug(this,
					  "Stored procedure begin ---------------");
			partyLadDao.runProcedure(relationshipMgr, relationshipMgrId, party,
					partyId, dueMonth, dueYear, segment);
			
			DefaultLogger.debug(this,
					  "Stored procedure end ---------------");
			
			
			
			DefaultLogger.debug(this,
					  "resultlist begin ---------------");

			resultList = partyLadDao.getData(relationshipMgrId, partyId,
					dueMonth, dueYear, segment);
			
			DefaultLogger.debug(this,
					  "resultlist end ---------------");

			if (resultList != null && !resultList.isEmpty()) {
				// String dirName=relationshipMgrId
				// + partyId + dueMonth + dueYear + segment;

				Date date = new Date();

				String dirName = new SimpleDateFormat("ddMMyyyyHHmmssSS")
						.format(date);

				String reportGenerationDate = new SimpleDateFormat(
						"MM/dd/yyy HH:mm:ss a").format(date);
				
				
				String dir = PropertyManager.getValue("lad.generate.path")
						+ "/" + dirName;

				File directory = new File(dir);

				String zipPath = dir + ".zip";
				
				
				
	
				

//				Runnable documentBuilder = new DocumentBuilder.Builder()
//						.relationshipMgrId(relationshipMgrId).partyId(partyId)
//						.dueMonth(dueMonth).dueYear(dueYear).segment(segment)
//						.dirName(dirName)
//						.reportGenerationDate(reportGenerationDate)
//						.resultList(resultList)
//						.directory(directory)
//						.zipPath(zipPath)
//						.build();
				
				Runnable documentBuilder = new DynamicDocumentBuilder.Builder().relationshipMgrId(relationshipMgrId).partyId(partyId).dueMonth(dueMonth).dueYear(dueYear).segment(segment).dirName(dirName).reportGenerationDate(reportGenerationDate).resultList(resultList).directory(directory).zipPath(zipPath).build();
				
				Thread t1=new Thread(documentBuilder);
				t1.start();

				

		

	

				String filePath = PropertyManager.getValue("lad.generate.path")
						+ "/" + zipPath;
				resultMap.put("filePath", filePath);

			}
		}
		
		String userId=(String)map.get("USER_LOGIN_ID");
		List allData = partyLadDao.getAllData(isRMUser(userId),relationshipMgrId);

		if (resultList == null) {
			resultMap.put("isRecordAvailable", "Y");
			resultMap.put("recordAlreadyPresent", "Y");

		} else if (allData.isEmpty() || resultList != null
				&& resultList.isEmpty()) {

			resultMap.put("isRecordAvailable", "N");
			resultMap.put("recordAlreadyPresent", "N");
		} else {
			resultMap.put("isRecordAvailable", "Y");
			resultMap.put("recordAlreadyPresent", "N");

		}

		resultMap.put("resultList", allData);
		resultMap.put("customerObject", null);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}

	private void zipDirectory(File dir, String zipDirName) {
		try {
			populateFilesList(dir);
			// now zip files one by one
			// create ZipOutputStream to write to the zip file
			FileOutputStream fos = new FileOutputStream(zipDirName);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (String filePath : filesListInDir) {
				System.out.println("Zipping " + filePath);
				// for ZipEntry we need to keep only relative file path, so we
				// used substring on absolute path
				ZipEntry ze = new ZipEntry(filePath.substring(dir
						.getAbsolutePath().length() + 1, filePath.length()));
				zos.putNextEntry(ze);
				// read the file and write to ZipOutputStream
				FileInputStream fis = new FileInputStream(filePath);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				zos.closeEntry();
				fis.close();
			}
			zos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method populates all the files in a directory to a List
	 * 
	 * @param dir
	 * @throws IOException
	 */
	private void populateFilesList(File dir) throws IOException {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile())
				filesListInDir.add(file.getAbsolutePath());
			else
				populateFilesList(file);
		}
	}
	private boolean isRMUser(String employeeCode) {
		IRelationshipMgrDAO relationshipmgr = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
		String	strRelationshipmgr = "";
		if(employeeCode != null){
			strRelationshipmgr = relationshipmgr.getRelationshipMgrByEmployeeCode(employeeCode).getRelationshipMgrName();
		}
		
		return strRelationshipmgr==null?false:true;
	}
}
