package com.integrosys.cms.ui.facilityNewMaster;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.checklist.bus.ICheckListBusManager;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateBusManagerImpl;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentItem;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.chktemplate.bus.OBTemplate;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.facilityNewMaster.proxy.IFacilityNewMasterProxyManager;
import com.integrosys.cms.app.facilityNewMaster.trx.IFacilityNewMasterTrxValue;
import com.integrosys.cms.app.facilityNewMaster.trx.OBFacilityNewMasterTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * $Author: Abhijit R $
 * Command for checker to approve edit .
 */

public class CheckerApproveEditFacilityNewMasterCmd extends AbstractCommand implements ICommonEventConstant {


	private IFacilityNewMasterProxyManager facilityNewMasterProxy;


	public IFacilityNewMasterProxyManager getFacilityNewMasterProxy() {
		return facilityNewMasterProxy;
	}

	public void setFacilityNewMasterProxy(
			IFacilityNewMasterProxyManager facilityNewMasterProxy) {
		this.facilityNewMasterProxy = facilityNewMasterProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveEditFacilityNewMasterCmd() {
	}

	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"IFacilityNewMasterTrxValue", "com.integrosys.cms.app.facilityNewMaster.trx.IFacilityNewMasterTrxValue", SERVICE_SCOPE},
				{ "itemTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE },
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE},
				{"newFacilityName", "java.lang.String", REQUEST_SCOPE}
		}
		);
	}

	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][]{
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
		}
		);
	}

	/**
	 * This method does the Business operations  with the HashMap and put the results back into
	 * the HashMap.
	 *
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// FacilityNewMaster Trx value
			IFacilityNewMasterTrxValue trxValueIn = (OBFacilityNewMasterTrxValue) map.get("IFacilityNewMasterTrxValue");

			String newFacilityName = trxValueIn.getStagingFacilityNewMaster().getNewFacilityName();
			String oldFacilityName = "";
			
			String newLineNo = trxValueIn.getStagingFacilityNewMaster().getLineNumber();
			String newSystem = trxValueIn.getStagingFacilityNewMaster().getNewFacilitySystem();
			String oldLineNo = "";
			
			boolean isFacilityNameUnique = false;

			/*if( trxValueIn.getFacilityNewMaster() != null ){
				oldFacilityName  = trxValueIn.getFacilityNewMaster().getNewFacilityName();
			
				if(!newFacilityName.equals(oldFacilityName))
					isFacilityNameUnique = getFacilityNewMasterProxy().isFacilityNameUnique(newFacilityName.trim());
				
				if(isFacilityNameUnique != false){
					exceptionMap.put("newFacilityNameError", new ActionMessage("error.string.exist","Facility Name"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
				
				oldLineNo  = trxValueIn.getFacilityNewMaster().getLineNumber();
				
				if( newLineNo != null && !newLineNo.equals("") && !newLineNo.equals(oldLineNo) ){
					boolean isLineNoUnique = getFacilityNewMasterProxy().isUniqueCode(newLineNo.trim(),newSystem.trim());
					
					if ( isLineNoUnique != false ) {
						exceptionMap.put("lineNumberError", new ActionMessage("error.string.exist","Line No."));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
						return returnMap;
						}
					}
			}
			
			
			else{
				isFacilityNameUnique = getFacilityNewMasterProxy().isFacilityNameUnique(newFacilityName);				
			
				if(isFacilityNameUnique != false){
					exceptionMap.put("newFacilityNameError", new ActionMessage("error.string.exist","Facility Name"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			
			if( newLineNo != null && !newLineNo.equals("") ){
				boolean isLineNoUnique = getFacilityNewMasterProxy().isUniqueCode(newLineNo.trim(),newSystem.trim());
				
				if ( isLineNoUnique != false ) {
					exceptionMap.put("lineNumberError", new ActionMessage("error.string.exist","Line No. With Same System "));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
					return returnMap;
					}
				}
			}*/
			
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function  to approve updated FacilityNewMaster Trx
			IFacilityNewMasterTrxValue trxValueOut = getFacilityNewMasterProxy().checkerApproveFacilityNewMaster(ctx, trxValueIn);
			
			
			String newLineCSV = PropertyManager.getValue("scod.linenocode.name");
			
		     String[] elements = newLineCSV.split(",");
		     List<String> newLine = Arrays.asList(elements);
		     boolean attachDoc = false;
		     for(String line : newLine) {
		    	 if(null != newLineNo   && !newLineNo.equals("")) {
		    	 if(newLineNo.equals(line)) {
		    		 attachDoc = true;
		    		 break;
		    	 }
		    	 }
		     }
		     
		     ITemplate template = new OBTemplate();
				IItem item = new OBItem();
//				OBDocumentItem scodDocument = new OBDocumentItem();
				ILimitDAO dao = LimitDAOFactory.getDAO();
				item=(IItem) dao.getSCODDocument();
			
			if(attachDoc && item != null) {
				/*ITemplate template = new OBTemplate();
				IItem item = new OBItem();
				OBDocumentItem scodDocument = new OBDocumentItem();
				ILimitDAO dao = LimitDAOFactory.getDAO();
				item=(IItem) dao.getSCODDocument();*/
				
				template.addItem(item);
				String facCode = trxValueOut.getFacilityNewMaster().getNewFacilityCode();
				template.setCollateralType(facCode);
				template.setCollateralSubType(facCode);
				template.setTemplateType("F");
				
				ITemplateItem temp[] = template.getTemplateItemList();
				if (temp != null) {
					for (int i = 0; i < temp.length; i++) {
							template.getTemplateItemList()[i].setIsMandatoryInd(false);
							template.getTemplateItemList()[i].setIsMandatoryDisplayInd(false);
							template.getTemplateItemList()[i].setWithTitle(false);
							template.getTemplateItemList()[i].setWithoutTitle(false);
							template.getTemplateItemList()[i].setPropertyCompleted(false);
							template.getTemplateItemList()[i].setUnderConstruction(false);
							template.getTemplateItemList()[i].setUsedWithFBR(false);
							template.getTemplateItemList()[i].setUsedWithoutFBR(false);
							template.getTemplateItemList()[i].setNewWithoutFBR(false);
							template.getTemplateItemList()[i].setNewWithFBR(false);
					}
				}
				
				ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory
						.getCheckListTemplateProxyManager();
				

				ITemplateTrxValue tempTrxValue=proxy.makerCreateTemplate(ctx, template);
				
				
				proxy.checkerApproveTemplate(ctx, tempTrxValue);

			}
			
			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (SystemBankException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}



