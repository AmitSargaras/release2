package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.IBankingMethod;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICriFac;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.IDirector;
import com.integrosys.cms.app.customer.bus.IIfscMethod;
import com.integrosys.cms.app.customer.bus.ISubline;
import com.integrosys.cms.app.customer.bus.ISystem;
import com.integrosys.cms.app.customer.bus.IVendor;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCriFac;
import com.integrosys.cms.app.customer.bus.OBDirector;
import com.integrosys.cms.app.customer.bus.OBSubline;
import com.integrosys.cms.app.customer.bus.OBSystem;
import com.integrosys.cms.app.customer.bus.OBVendor;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.manualinput.party.IIfscCodeDao;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.partygroup.trx.IPartyGroupTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.customer.bankingmethod.IBankingMethodDAO;

/**
 * Class created by
 * 
 * @author sandiip.shinde
 * @since 17-03-2011
 * 
 */

public class ManualInputResubmitCreateCustomerCommand extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {

				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "systemList", "java.util.List", SERVICE_SCOPE },
				{ "partyGrpList", "java.util.List", SERVICE_SCOPE },
				{ "directorList", "java.util.List", SERVICE_SCOPE },
				{ "vendorList", "java.util.List", SERVICE_SCOPE },
				{ "branchList", "java.util.List", SERVICE_SCOPE },
				{ "ifscBranchList", "java.util.List", SERVICE_SCOPE },
				{ "facList", "java.util.List", SERVICE_SCOPE },
				{"ICMSCustomerTrxValue", "com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue", SERVICE_SCOPE},
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "legalId", "java.lang.String", REQUEST_SCOPE },
				{ "legalSource", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						GLOBAL_SCOPE } });
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
				{ "customerOb",
						"com.integrosys.cms.app.customer.bus.OBCMSCustomer",
						REQUEST_SCOPE },
				{ "systemList", "java.util.List", SERVICE_SCOPE },
				{ "branchList", "java.util.List", SERVICE_SCOPE },
				{ "directorList", "java.util.List", SERVICE_SCOPE },
				{ "vendorList", "java.util.List", SERVICE_SCOPE },
				{ "partyGrpList", "java.util.List", SERVICE_SCOPE },
				{ "validate", "java.lang.String", SERVICE_SCOPE },
				{ "facList", "java.util.List", SERVICE_SCOPE },
				{ "request.ITrxValue",
						"com.integrosys.cms.app.transaction.ICMSTrxValue",
						REQUEST_SCOPE },
				{
						com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
						"java.util.Locale", GLOBAL_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		String event = (String) map.get("event");

		
		List list = (List) map.get("systemList");
		ISystem system[] = new ISystem[list.size()];
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				ISystem sysObj = new OBSystem();
				sysObj = (ISystem) list.get(i);
				system[i] = sysObj;

			}
		}

		
		List sublineList = (List) map.get("partyGrpList");
		ISubline subline[] = new ISubline[sublineList.size()];
		if (sublineList != null) {
			for (int i = 0; i < sublineList.size(); i++) {
				ISubline sysObj = new OBSubline();
				sysObj = (ISubline) sublineList.get(i);
				subline[i] = sysObj;

			}
		}
		
		List dir = (List) map.get("directorList");
		IDirector director[] = new IDirector[dir.size()];
		if (list != null) {
			for (int i = 0; i < dir.size(); i++) {
				IDirector sysObj = new OBDirector();
				sysObj = (IDirector) dir.get(i);
				director[i] = sysObj;

			}
		}
		
		List name = (List) map.get("vendorList");		
		IVendor vendor[] = new IVendor[name.size()];
		if (name != null) {
			for (int i = 0; i < name.size(); i++) {
				IVendor sysObj = new OBVendor();
				sysObj = (IVendor) name.get(i);
				vendor[i] = sysObj;

			}
		}
		
		List criFac = (List) map.get("facList");
		ICriFac criFacility[] = new ICriFac[criFac.size()];
		if (list != null) {
			for (int i = 0; i < criFac.size(); i++) {
				ICriFac criObj = new OBCriFac();
				criObj = (ICriFac) criFac.get(i);
				criFacility[i] = criObj;

			}
		}
		
		List bankList = (List) map.get("branchList");
		IBankingMethod bankingMethod[] = new IBankingMethod[bankList.size()];
		
		List ifscBranchList = (List) map.get("ifscBranchList");
		IIfscMethod ifscList[] = null;
		
		String source = (String) map.get("legalSource");
		ICMSCustomer obCustomer = (OBCMSCustomer) map.get("OBCMSCustomer");
		
/*		boolean isPartyCodeUnique = true;
		ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
	    isPartyCodeUnique = customerDAO.isCustomerNameUnique(obCustomer.getCustomerName());
	    HashMap exceptionMap = new HashMap();

		if(isPartyCodeUnique != false){
			exceptionMap.put("dupCustomerNameError", new ActionMessage("error.string.customerName.exist"));
			IPartyGroupTrxValue partyGroupTrxValue = null;
			resultMap.put("request.ITrxValue", partyGroupTrxValue);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return returnMap;
		}*/
		
		
		/*if (obCustomer.getBankingMethod().equals("MULTIPLE")
				|| obCustomer.getBankingMethod().equals("CONSORTIUM") || obCustomer.getBankingMethod().equals("OUTSIDEMULTIPLE")
				|| obCustomer.getBankingMethod().equals("OUTSIDECONSORTIUM")) {*/
		if (!"SOLE-SOLE".equals(obCustomer.getBankingMethod())
				&& !"SOLE".equals(obCustomer.getBankingMethod())) {
		IBankingMethod[] banking = obCustomer.getCMSLegalEntity().getBankList();
		OBBankingMethod bank = (OBBankingMethod) banking[0];
		String nodalLead = bank.getNodal();
		boolean sflag = false;
		boolean oflag = false;
		String bankId = "";
        if(nodalLead != null && !nodalLead.equals(""))
        {
		if (nodalLead.startsWith("s")) {
			String[] array = nodalLead.split(",");
			bankId = array[1];
			sflag = true;
		} else if (nodalLead.startsWith("o")) {
			String[] array = nodalLead.split(",");
			bankId = array[1];
			oflag = true;
		}
        }
        
        
        String leadOnly = bank.getLead();
        boolean sflagLead = false;
		boolean oflagLead = false;
		String bankIdLead = "";
        if(leadOnly != null && !leadOnly.equals(""))
        {
		if (leadOnly.startsWith("s")) {
			String[] array = leadOnly.split(",");
			bankIdLead = array[1];
			sflagLead = true;
		} else if (leadOnly.startsWith("o")) {
			String[] array = leadOnly.split(",");
			bankIdLead = array[1];
			oflagLead = true;
		}
        }
        
        
        
        /*if (ifscBranchList != null) {
        	ifscList = new IIfscMethod[ifscBranchList.size()];
     			for (int i = 0; i < ifscBranchList.size(); i++) {
     				IIfscMethod sysObj = (IIfscMethod) ifscBranchList.get(i);
     				sysObj.setLead("");
     				sysObj.setNodal("");
     				if (sysObj.getBankType()!= null && sysObj.getBankType().equals("O")) {
     					//long id = Long.parseLong(bankId);
     					if (oflag) {
     						if (sysObj.getIfscCode().equals(bankId)
     								&& (obCustomer.getBankingMethod().equals("MULTIPLE") || obCustomer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
     							sysObj.setNodal("Y");
     						} else if (sysObj.getIfscCode().equals(bankId)
     								&& (obCustomer.getBankingMethod().equals("CONSORTIUM") || obCustomer.getBankingMethod().equals("OUTSIDECONSORTIUM"))) {
     							sysObj.setLead("Y");
     						}
     					}
     				} else if (sysObj.getBankType()!= null && sysObj.getBankType().equals("S")) {
     					//long id = Long.parseLong(bankId);
     					if (sflag) {
     						if (sysObj.getIfscCode().equals(bankId)
     								&& (obCustomer.getBankingMethod().equals("MULTIPLE") || obCustomer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
     							sysObj.setNodal("Y");
     						} else if (sysObj.getIfscCode().equals(bankId)
     								&& (obCustomer.getBankingMethod().equals("CONSORTIUM") || obCustomer.getBankingMethod().equals("OUTSIDECONSORTIUM"))) {
     							sysObj.setLead("Y");
     						}
     					}
     				}
     				ifscList[i] = sysObj;
     			}
     		}*/
        
        String[] newArrayIfsc = obCustomer.getRevisedEmailIdsArrayList();
		String[] newArrayIfsc1 = null;
		
		String newStringIfsc = "";
		String[] newStringSplitIfsc = null;
		
		String bankIdsIfsc = "";
		String bankTypedIfsc = "";
		String revisedEmailIdValIfsc = "";
        
        if ((ifscBranchList != null && !ifscBranchList.isEmpty()) && (newArrayIfsc != null && newArrayIfsc.length != 0)) {
        	
        	newArrayIfsc1 = new String[newArrayIfsc.length];
			
			for(int k=0;k<newArrayIfsc.length;k++) {
				if(!newArrayIfsc[k].startsWith("Ljava.lang.String")) {
					newArrayIfsc1[k] = newArrayIfsc[k];
     		}
			}
        
        	
        	ifscList = new IIfscMethod[ifscBranchList.size()];
			for (int i = 0; i < ifscBranchList.size(); i++) {
				IIfscMethod sysObj = (IIfscMethod) ifscBranchList.get(i);
				sysObj.setLead("");
				sysObj.setNodal("");
				if (sysObj.getBankType()!= null && sysObj.getBankType().equals("O")) {
					if (oflag) {
						if (sysObj.getIfscCode().equals(bankId)
								&& (obCustomer.getBankingMethod().contains("CONSORTIUM-CONSORTIUM") || obCustomer.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
										|| obCustomer.getBankingMethod().equals("CONSORTIUM") || obCustomer.getBankingMethod().equals("OUTSIDECONSORTIUM") 
										|| obCustomer.getBankingMethod().contains("MULTIPLE-MULTIPLE") || obCustomer.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
										|| obCustomer.getBankingMethod().equals("MULTIPLE") || obCustomer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setNodal("Y");
						} 
					}
					
					if (oflagLead) {
						if (sysObj.getIfscCode().equals(bankIdLead)
								&& (obCustomer.getBankingMethod().contains("CONSORTIUM-CONSORTIUM") || obCustomer.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
										|| obCustomer.getBankingMethod().equals("CONSORTIUM") || obCustomer.getBankingMethod().equals("OUTSIDECONSORTIUM") 
										|| obCustomer.getBankingMethod().contains("MULTIPLE-MULTIPLE") || obCustomer.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
										|| obCustomer.getBankingMethod().equals("MULTIPLE") || obCustomer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setLead("Y");
						}
					}
					
				} else if (sysObj.getBankType()!= null && sysObj.getBankType().equals("S")) {
					if (sflag) {
						if (sysObj.getIfscCode().equals(bankId)
								&& (obCustomer.getBankingMethod().contains("CONSORTIUM-CONSORTIUM") || obCustomer.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
										|| obCustomer.getBankingMethod().equals("CONSORTIUM") || obCustomer.getBankingMethod().equals("OUTSIDECONSORTIUM") 
										|| obCustomer.getBankingMethod().contains("MULTIPLE-MULTIPLE") || obCustomer.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
										|| obCustomer.getBankingMethod().equals("MULTIPLE") || obCustomer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setNodal("Y");
						} 
					}
					if (sflagLead) {
						if (sysObj.getIfscCode().equals(bankIdLead)
								&& (obCustomer.getBankingMethod().contains("CONSORTIUM-CONSORTIUM") || obCustomer.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
										|| obCustomer.getBankingMethod().equals("CONSORTIUM") || obCustomer.getBankingMethod().equals("OUTSIDECONSORTIUM") 
										|| obCustomer.getBankingMethod().contains("MULTIPLE-MULTIPLE") || obCustomer.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
										|| obCustomer.getBankingMethod().equals("MULTIPLE") || obCustomer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setLead("Y");
						}
					}
				}
				
				for (int k = 0; k < newArrayIfsc1.length; k++) {
					if (newArrayIfsc1[k] != null) {
						newStringIfsc = newArrayIfsc1[k];
						newStringSplitIfsc = newStringIfsc.split("-");

						bankIdsIfsc = newStringSplitIfsc[3];
						bankTypedIfsc = newStringSplitIfsc[2];
						revisedEmailIdValIfsc = newStringSplitIfsc[1];

						if (sysObj.getIfscCode().equals(bankIdsIfsc)
								&& sysObj.getBankType().equalsIgnoreCase(bankTypedIfsc)) {
							sysObj.setRevisedEmailID(revisedEmailIdValIfsc);
							break;
						}
					}
				}
				
				ifscList[i] = sysObj;
			}
		}
        
		/*if (bankList != null) {
			for (int i = 0; i < bankList.size(); i++) {
				IBankingMethod sysObj = (IBankingMethod) bankList.get(i);
				sysObj.setLead("");
				sysObj.setNodal("");
				if (sysObj.getBankType()!= null && sysObj.getBankType().equals("O")) {
					//long id = Long.parseLong(bankId);
					if (oflag) {
						if (Long.toString(sysObj.getBankId()).equals(bankId)
								&& (obCustomer.getBankingMethod().equals("MULTIPLE") || obCustomer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setNodal("Y");
						} else if (Long.toString(sysObj.getBankId()).equals(bankId)
								&& (obCustomer.getBankingMethod().equals("CONSORTIUM") || obCustomer.getBankingMethod().equals("OUTSIDECONSORTIUM"))) {
							sysObj.setLead("Y");
						}
					}
				} else if (sysObj.getBankType()!= null && sysObj.getBankType().equals("S")) {
					//long id = Long.parseLong(bankId);
					if (sflag) {
						if (Long.toString(sysObj.getBankId()).equals(bankId)
								&& (obCustomer.getBankingMethod().equals("MULTIPLE") || obCustomer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setNodal("Y");
						} else if (Long.toString(sysObj.getBankId()).equals(bankId)
								&& (obCustomer.getBankingMethod().equals("CONSORTIUM") || obCustomer.getBankingMethod().equals("OUTSIDECONSORTIUM"))) {
							sysObj.setLead("Y");
						}
					}
				}
				bankingMethod[i] = sysObj;
			}
		}*/
        
        
        String[] newArray = obCustomer.getRevisedEmailIdsArrayList();
		String[] newArray1 = null;
		
		String newString = "";
		String[] newStringSplit = null;
		
		String bankIds = "";
		String bankTyped = "";
		String revisedEmailIdVal = "";
		
        
		if ((bankList != null && !bankList.isEmpty()) && (newArray != null && newArray.length != 0)) {		
        	
        	newArray1 = new String[newArray.length];
			
			for(int k=0;k<newArray.length;k++) {
				if(!newArray[k].startsWith("Ljava.lang.String")) {
					newArray1[k] = newArray[k];
		}
			}
		
        	
			for (int i = 0; i < bankList.size(); i++) {
				IBankingMethod sysObj = (IBankingMethod) bankList.get(i);
				sysObj.setLead("");
				sysObj.setNodal("");
				if (sysObj.getBankType()!= null && sysObj.getBankType().equals("O")) {
					if (oflag) {
						if (Long.toString(sysObj.getBankId()).equals(bankId)
								&& (obCustomer.getBankingMethod().contains("CONSORTIUM-CONSORTIUM") || obCustomer.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
										|| obCustomer.getBankingMethod().equals("CONSORTIUM") || obCustomer.getBankingMethod().equals("OUTSIDECONSORTIUM") 
										|| obCustomer.getBankingMethod().contains("MULTIPLE-MULTIPLE") || obCustomer.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
										|| obCustomer.getBankingMethod().equals("MULTIPLE") || obCustomer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setNodal("Y");
						} 
					}
					
					if (oflagLead) {
						if (Long.toString(sysObj.getBankId()).equals(bankIdLead)
								&& (obCustomer.getBankingMethod().contains("CONSORTIUM-CONSORTIUM") || obCustomer.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
										|| obCustomer.getBankingMethod().equals("CONSORTIUM") || obCustomer.getBankingMethod().equals("OUTSIDECONSORTIUM") 
										|| obCustomer.getBankingMethod().contains("MULTIPLE-MULTIPLE") || obCustomer.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
										|| obCustomer.getBankingMethod().equals("MULTIPLE") || obCustomer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setLead("Y");
						}
					}
					
				} else if (sysObj.getBankType()!= null && sysObj.getBankType().equals("S")) {
					if (sflag) {
						if (Long.toString(sysObj.getBankId()).equals(bankId)
								&& (obCustomer.getBankingMethod().contains("CONSORTIUM-CONSORTIUM") || obCustomer.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
										|| obCustomer.getBankingMethod().equals("CONSORTIUM") || obCustomer.getBankingMethod().equals("OUTSIDECONSORTIUM") 
										|| obCustomer.getBankingMethod().contains("MULTIPLE-MULTIPLE") || obCustomer.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
										|| obCustomer.getBankingMethod().equals("MULTIPLE") || obCustomer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setNodal("Y");
						} 
					}
					
					if (sflagLead) {
						if (Long.toString(sysObj.getBankId()).equals(bankIdLead)
								&& (obCustomer.getBankingMethod().contains("CONSORTIUM-CONSORTIUM") || obCustomer.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
										|| obCustomer.getBankingMethod().equals("CONSORTIUM") || obCustomer.getBankingMethod().equals("OUTSIDECONSORTIUM") 
										|| obCustomer.getBankingMethod().contains("MULTIPLE-MULTIPLE") || obCustomer.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
										|| obCustomer.getBankingMethod().equals("MULTIPLE") || obCustomer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setLead("Y");
						}
					}
				}
				
				for (int k = 0; k < newArray1.length; k++) {
					if (newArray1[k] != null) {
						newString = newArray1[k];
						newStringSplit = newString.split("-");

						bankIds = newStringSplit[3];
						bankTyped = newStringSplit[2];
						revisedEmailIdVal = newStringSplit[1];

						if (Long.toString(sysObj.getBankId()).equals(bankIds)
								&& sysObj.getBankType().equalsIgnoreCase(bankTyped)) {
							sysObj.setRevisedEmailIds(revisedEmailIdVal);
							break;
						}
					}
				}
				
				bankingMethod[i] = sysObj;
			}
		}
		
		}else	if(obCustomer.getBankingMethod().equals("SOLE") || "SOLE-SOLE".equals(obCustomer.getBankingMethod())){
			if (bankList != null) {
				for (int i = 0; i < bankList.size(); i++) {
				IBankingMethod sysObj = (IBankingMethod) bankList.get(i);
				sysObj.setLead("");
				sysObj.setNodal("");
				bankingMethod[i] = sysObj;
				}
			}
		}

		DefaultLogger.debug(this,
				"Inside doExecute() ManualInputCreateCustomerCommand " + event);

		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICMSCustomerTrxValue trxValueIn = (OBCMSCustomerTrxValue) map.get("ICMSCustomerTrxValue");
		String loginUser = String.valueOf(ctx.getUser().getTeamTypeMembership().getMembershipID());
		String bankingMethodss = obCustomer.getBankingMethod();
		
		//obCustomer.setStatus("ACTIVE");

		obCustomer.getCMSLegalEntity().setOtherSystem(system);
		obCustomer.getCMSLegalEntity().setSublineParty(subline);
		obCustomer.getCMSLegalEntity().setBankList(bankingMethod);
		obCustomer.getCMSLegalEntity().setDirector(director);
		obCustomer.getCMSLegalEntity().setVendor(vendor);
		obCustomer.getCMSLegalEntity().setCriFacList(criFacility);
		
		obCustomer.setBankingMethod("");
		obCustomer.getCMSLegalEntity().setBankingMethod("");
		
		trxValueIn.setTrxContext(ctx);
		trxValueIn.setStagingCustomer(obCustomer);
		trxValueIn.setLegalName(obCustomer.getLegalEntity().getLegalName());
		trxValueIn.setCustomerName(obCustomer.getCustomerName());
		trxValueIn.setLimitProfileReferenceNumber(obCustomer.getCifId());
		String oldCustomerName = "";
		 HashMap exceptionMap = new HashMap();
		 
		/*String newCustomerName = (String) obCustomer.getCustomerName();
		boolean isPartyCodeUnique = false;
		 if(trxValueIn.getFromState().equals("PENDING_PERFECTION")){
				oldCustomerName = trxValueIn.getStagingCustomer().getCustomerName();
				
				if(!newCustomerName.equals(oldCustomerName))
				{
					ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
			    isPartyCodeUnique = customerDAO.isCustomerNameUnique(obCustomer.getCustomerName());
				}
				if(isPartyCodeUnique != false){
					exceptionMap.put("dupCustomerNameError", new ActionMessage("error.string.customerName.exist"));
					IPartyGroupTrxValue partyGroupTrxValue = null;
					resultMap.put("request.ITrxValue", partyGroupTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
				
				//trxValueOut = getCollateralNewMasterProxy().makerUpdateSaveCreateCollateralNewMaster(ctx, trxValueIn, collateralNewMaster);
			}*/
	
	
	//	DefaultLogger.debug(this, " -------- Before Create : " + obCustomer);
		try {
			ICMSCustomerTrxValue trx;
			if(loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_MAKER))
					|| loginUser.equals(String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_MAKER_WFH)))
			{
			 trx = CustomerProxyFactory.getProxy().makerResubmitCreateCustomerBrmaker(trxValueIn);
			}else{
				 trx = CustomerProxyFactory.getProxy().makerResubmitCreateCustomer(trxValueIn);
			}
			
			//insert IFSC data to stage table
			if(null!=ifscList) {
				IIfscMethod ifscStageList[] = new IIfscMethod[ifscBranchList.size()];
				IIfscCodeDao ifscCodeDao = (IIfscCodeDao) BeanHouse.get("ifscCodeDao");
				for(int i=0;i<ifscList.length;i++) {
					IIfscMethod ifscObj=ifscList[i];
					//set stage cust id here 
					ifscObj.setCustomerId(Long.parseLong(trx.getStagingReferenceID()));
					ifscObj.setStatus("ACTIVE");
					IIfscMethod obj = ifscCodeDao.createStageIfscCode(ifscObj);
					ifscStageList[i]=obj;
				}
			}
			
			IBankingMethodDAO bankingMethodDAOImpl = (IBankingMethodDAO)BeanHouse.get("bankingMethodDAO");
//			String bankingMethodss = obCustomer.getBankingMethod();
			if(bankingMethodss == null || "".equals(bankingMethodss)) {
				bankingMethodss = obCustomer.getFinalBankMethodList();
			}
			if(bankingMethodss != null && !"".equals(bankingMethodss)) {
			String[] bankMethodArr = bankingMethodss.split(",");
			ArrayList bankMethList = new ArrayList();
			for(int i=0; i<bankMethodArr.length;i++) {
				String[] bankMethodArr1 =bankMethodArr[i].split("-");
				bankMethList.add(bankMethodArr1[0]);
			}
			
			for(int i=0;i<bankMethList.size();i++) {
				OBBankingMethod obj = new OBBankingMethod();
				obj.setBankType((String)bankMethList.get(i));
				obj.setLEID(Long.parseLong(trx.getStagingReferenceID()));
				obj.setCustomerIDForBankingMethod(obCustomer.getCifId());
				obj.setStatus("ACTIVE");
				bankingMethodDAOImpl.insertBankingMethodCustStage(obj);				
				
			}
			}
			resultMap.put("customerOb", trx);
			resultMap.put("request.ITrxValue", trx);
		} catch (CustomerException e) {
			CommandProcessingException cpe = new CommandProcessingException(e
					.getMessage());
			cpe.initCause(e);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}
		resultMap.put("validate","N");
		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
