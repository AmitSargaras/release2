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
//import com.integrosys.cms.app.manualinput.customerinfo.proxy.ICustomerInfoProxyManager;
import com.integrosys.cms.app.partygroup.trx.IPartyGroupTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.limitsOfAuthorityMaster.LimitsOfAuthorityMasterHelper;
import com.integrosys.cms.ui.manualinput.customer.bankingmethod.IBankingMethodDAO;

	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 17-03-2011
	 *
	 */

public class ManualInputEditDraftCustomerCommand extends AbstractCommand{
	
/*	private ICustomerInfoProxyManager customerProxy;
	
	public ICustomerInfoProxyManager getCustomerProxy() {
		return customerProxy;
	}

	public void setCustomerProxy(ICustomerInfoProxyManager customerProxy) {
		this.customerProxy = customerProxy;
	}*/

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{"ICMSCustomerTrxValue", "com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue", SERVICE_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "systemList", "java.util.List", SERVICE_SCOPE },
				{ "partyGrpList", "java.util.List", SERVICE_SCOPE },
				{ "branchList", "java.util.List", SERVICE_SCOPE },
				{ "ifscBranchList", "java.util.List", SERVICE_SCOPE },
				{ "directorList", "java.util.List", SERVICE_SCOPE },
				{ "vendorList", "java.util.List", SERVICE_SCOPE },
				{ "facList", "java.util.List", SERVICE_SCOPE },
                {"remarks", "java.lang.String", REQUEST_SCOPE}

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
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
				{"ICMSCustomerTrxValue", "com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue", SERVICE_SCOPE},
				{ "systemList", "java.util.List", SERVICE_SCOPE },
				{ "branchList", "java.util.List", SERVICE_SCOPE },
				{ "partyGrpList", "java.util.List", SERVICE_SCOPE },
				//{ "validate", "java.lang.String", SERVICE_SCOPE },
				{ "directorList", "java.util.List", SERVICE_SCOPE },
				{ "vendorList", "java.util.List", SERVICE_SCOPE },
				{ "facList", "java.util.List", SERVICE_SCOPE },
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {		
		DefaultLogger.debug(this, " doExecute : ManualInputEditCustomerCommand");
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
			
		OBCMSCustomer customer = (OBCMSCustomer) map.get("OBCMSCustomer");
		String event = (String) map.get("event");
		
		
		
		
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICMSCustomerTrxValue trxValueIn = (OBCMSCustomerTrxValue) map.get("ICMSCustomerTrxValue");
		
		ISystem system[] = new ISystem[50];
		List list = (List) map.get("systemList");
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				ISystem sysObj = new OBSystem();
				sysObj = (ISystem) list.get(i);
				system[i] = sysObj;

			}
		}
		
		IDirector director[] = new IDirector[50];
		List dir = (List) map.get("directorList");
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
		
		List facList = (List) map.get("facList");
		ICriFac criFac[] = new ICriFac[facList.size()];
		
		if (facList != null) {
			for (int i = 0; i < facList.size(); i++) {
				ICriFac criObj = new OBCriFac();
				criObj = (ICriFac) facList.get(i);
				criFac[i] = criObj;

			}
		}
		
		ISubline subline[] = new ISubline[50];
		List sublineList = (List) map.get("partyGrpList");
		if (sublineList != null) {
			for (int i = 0; i < sublineList.size(); i++) {
				ISubline sysObj = new OBSubline();
				sysObj = (ISubline) sublineList.get(i);
				subline[i] = sysObj;

			}
		}
		
		List bankList = (List) map.get("branchList");
		IBankingMethod bankingMethod[] = new IBankingMethod[bankList.size()];
		
		List ifscBranchList = (List) map.get("ifscBranchList");
		IIfscMethod ifscList[] = null;
		
		String source = (String) map.get("legalSource");
	
		/*if (customer.getBankingMethod().equals("MULTIPLE")
				|| customer.getBankingMethod().equals("CONSORTIUM")
				|| customer.getBankingMethod().equals("OUTSIDEMULTIPLE")
				|| customer.getBankingMethod().equals("OUTSIDECONSORTIUM")) {*/
		
		if (!"SOLE-SOLE".equals(customer.getBankingMethod())
				&& !"SOLE".equals(customer.getBankingMethod())) {
		
		IBankingMethod[] banking = customer.getCMSLegalEntity().getBankList();
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
        
        String[] newArrayIfsc = customer.getRevisedEmailIdsArrayList();
		String[] newArrayIfsc1 = null;
		
		String newStringIfsc = "";
		String[] newStringSplitIfsc = null;
		
		String bankIdsIfsc = "";
		String bankTypedIfsc = "";
		String revisedEmailIdValIfsc = "";

        
        
        
        
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
 								&& (customer.getBankingMethod().equals("MULTIPLE") || customer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
 							sysObj.setNodal("Y");
 						} else if (sysObj.getIfscCode().equals(bankId)
 								&& (customer.getBankingMethod().equals("CONSORTIUM") || customer.getBankingMethod().equals("OUTSIDECONSORTIUM"))) {
 							sysObj.setLead("Y");
 						}
 					}
 				} else if (sysObj.getBankType()!= null && sysObj.getBankType().equals("S")) {
 					//long id = Long.parseLong(bankId);
 					if (sflag) {
 						if (sysObj.getIfscCode().equals(bankId)
 								&& (customer.getBankingMethod().equals("MULTIPLE") || customer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
 							sysObj.setNodal("Y");
 						} else if (sysObj.getIfscCode().equals(bankId)
 								&& (customer.getBankingMethod().equals("CONSORTIUM") || customer.getBankingMethod().equals("OUTSIDECONSORTIUM"))) {
 							sysObj.setLead("Y");
 						}
 					}
 				}
 				ifscList[i] = sysObj;
 			}
 		}*/
		
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
								&& (customer.getBankingMethod().contains("CONSORTIUM-CONSORTIUM") || customer.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
										|| customer.getBankingMethod().equals("CONSORTIUM") || customer.getBankingMethod().equals("OUTSIDECONSORTIUM") 
										|| customer.getBankingMethod().contains("MULTIPLE-MULTIPLE") || customer.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
										|| customer.getBankingMethod().equals("MULTIPLE") || customer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setNodal("Y");
						} 
					}
					
					if (oflagLead) {
						if (sysObj.getIfscCode().equals(bankIdLead)
								&& (customer.getBankingMethod().contains("CONSORTIUM-CONSORTIUM") || customer.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
										|| customer.getBankingMethod().equals("CONSORTIUM") || customer.getBankingMethod().equals("OUTSIDECONSORTIUM") 
										|| customer.getBankingMethod().contains("MULTIPLE-MULTIPLE") || customer.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
										|| customer.getBankingMethod().equals("MULTIPLE") || customer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setLead("Y");
						}
					}
					
				} else if (sysObj.getBankType()!= null && sysObj.getBankType().equals("S")) {
					if (sflag) {
						if (sysObj.getIfscCode().equals(bankId)
								&& (customer.getBankingMethod().contains("CONSORTIUM-CONSORTIUM") || customer.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
										|| customer.getBankingMethod().equals("CONSORTIUM") || customer.getBankingMethod().equals("OUTSIDECONSORTIUM") 
										|| customer.getBankingMethod().contains("MULTIPLE-MULTIPLE") || customer.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
										|| customer.getBankingMethod().equals("MULTIPLE") || customer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setNodal("Y");
						} 
					}
					if (sflagLead) {
						if (sysObj.getIfscCode().equals(bankIdLead)
								&& (customer.getBankingMethod().contains("CONSORTIUM-CONSORTIUM") || customer.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
										|| customer.getBankingMethod().equals("CONSORTIUM") || customer.getBankingMethod().equals("OUTSIDECONSORTIUM") 
										|| customer.getBankingMethod().contains("MULTIPLE-MULTIPLE") || customer.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
										|| customer.getBankingMethod().equals("MULTIPLE") || customer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setLead("Y");
						}
					}
				}
				
				for (int k = 0; k < newArrayIfsc1.length; k++) {
					if (newArrayIfsc1[k] != null) {
						newStringIfsc = newArrayIfsc1[k];
						System.out.println("newStringIfsc "+newStringIfsc);
						newStringSplitIfsc = newStringIfsc.split("-");
						
						System.out.println("newStringSplitIfsc length"+newStringSplitIfsc.length);
                        if(newStringSplitIfsc.length >= 3){
						bankIdsIfsc = newStringSplitIfsc[3];
						bankTypedIfsc = newStringSplitIfsc[2];
						revisedEmailIdValIfsc = newStringSplitIfsc[1];
                        }
						

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
								&& customer.getBankingMethod().equals("MULTIPLE") || customer.getBankingMethod().equals("OUTSIDEMULTIPLE")) {
							sysObj.setNodal("Y");
						} else if (Long.toString(sysObj.getBankId()).equals(bankId)
								&& (customer.getBankingMethod().equals("CONSORTIUM") || customer.getBankingMethod().equals("OUTSIDECONSORTIUM"))) {
							sysObj.setLead("Y");
						}
					}
				} else if (sysObj.getBankType()!= null && sysObj.getBankType().equals("S")) {
					//long id = Long.parseLong(bankId);
					if (sflag) {
						if (Long.toString(sysObj.getBankId()).equals(bankId)
								&& (customer.getBankingMethod().equals("MULTIPLE") || customer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setNodal("Y");
						} else if (Long.toString(sysObj.getBankId()).equals(bankId)
								&& (customer.getBankingMethod().equals("CONSORTIUM") || customer.getBankingMethod().equals("OUTSIDECONSORTIUM"))) {
							sysObj.setLead("Y");
						}
					}
				}
			
				bankingMethod[i] = sysObj;
			}
		}*/
		
		
		String[] newArray = customer.getRevisedEmailIdsArrayList();
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
								&& (customer.getBankingMethod().contains("CONSORTIUM-CONSORTIUM") || customer.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
										|| customer.getBankingMethod().equals("CONSORTIUM") || customer.getBankingMethod().equals("OUTSIDECONSORTIUM") 
										|| customer.getBankingMethod().contains("MULTIPLE-MULTIPLE") || customer.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
										|| customer.getBankingMethod().equals("MULTIPLE") || customer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setNodal("Y");
						} 
					}
					
					if (oflagLead) {
						if (Long.toString(sysObj.getBankId()).equals(bankIdLead)
								&& (customer.getBankingMethod().contains("CONSORTIUM-CONSORTIUM") || customer.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
										|| customer.getBankingMethod().equals("CONSORTIUM") || customer.getBankingMethod().equals("OUTSIDECONSORTIUM") 
										|| customer.getBankingMethod().contains("MULTIPLE-MULTIPLE") || customer.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
										|| customer.getBankingMethod().equals("MULTIPLE") || customer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setLead("Y");
						}
					}
					
				} else if (sysObj.getBankType()!= null && sysObj.getBankType().equals("S")) {
					if (sflag) {
						if (Long.toString(sysObj.getBankId()).equals(bankId)
								&& (customer.getBankingMethod().contains("CONSORTIUM-CONSORTIUM") || customer.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
										|| customer.getBankingMethod().equals("CONSORTIUM") || customer.getBankingMethod().equals("OUTSIDECONSORTIUM") 
										|| customer.getBankingMethod().contains("MULTIPLE-MULTIPLE") || customer.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
										|| customer.getBankingMethod().equals("MULTIPLE") || customer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setNodal("Y");
						} 
					}
					
					if (sflagLead) {
						if (Long.toString(sysObj.getBankId()).equals(bankIdLead)
								&& (customer.getBankingMethod().contains("CONSORTIUM-CONSORTIUM") || customer.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")
										|| customer.getBankingMethod().equals("CONSORTIUM") || customer.getBankingMethod().equals("OUTSIDECONSORTIUM") 
										|| customer.getBankingMethod().contains("MULTIPLE-MULTIPLE") || customer.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
										|| customer.getBankingMethod().equals("MULTIPLE") || customer.getBankingMethod().equals("OUTSIDEMULTIPLE"))) {
							sysObj.setLead("Y");
						}
					}
				}
				
				for (int k = 0; k < newArray1.length; k++) {
					if (newArray1[k] != null) {
						newString = newArray1[k];
						System.out.println("newString "+newString);
						newStringSplit = newString.split("-");

						System.out.println("newStringSplit length"+newStringSplit.length);
						if(newStringSplit.length>=3){
						bankIds = newStringSplit[3];
						bankTyped = newStringSplit[2];
						revisedEmailIdVal = newStringSplit[1];
						}
						

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
		
	}else if(customer.getBankingMethod().equals("SOLE")){
			if (bankList != null) {
				for (int i = 0; i < bankList.size(); i++) {
				IBankingMethod sysObj = (IBankingMethod) bankList.get(i);
				sysObj.setLead("");
				sysObj.setNodal("");
				bankingMethod[i] = sysObj;
				}
			}
		}
		
		customer.getCMSLegalEntity().setOtherSystem(system);
		customer.getCMSLegalEntity().setSublineParty(subline);
		customer.getCMSLegalEntity().setBankList(bankingMethod);
		customer.getCMSLegalEntity().setDirector(director);
		customer.getCMSLegalEntity().setVendor(vendor);
		customer.getCMSLegalEntity().setCriFacList(criFac);
		
		/*boolean isPartyCodeUnique = false;
	    HashMap exceptionMap = new HashMap();
	    String oldCustomerName = ""; 
		String newCustomerName = (String) customer.getCustomerName();
		
		boolean validate = false;
	    if ( event.equals("update_draft_customer") ){
	    	
	    	oldCustomerName = trxValueIn.getStagingCustomer().getCustomerName();
			validate = true;
		}
		if( validate ){
			if(!newCustomerName.equals(oldCustomerName))
			{
				ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
		    isPartyCodeUnique = customerDAO.isCustomerNameUnique(customer.getCustomerName());
			}
			if(isPartyCodeUnique != false){
				exceptionMap.put("dupCustomerNameError", new ActionMessage("error.string.customerName.exist"));
				IPartyGroupTrxValue partyGroupTrxValue = null;
				resultMap.put("request.ITrxValue", partyGroupTrxValue);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}
		}*/
		String bankingMethodss = customer.getBankingMethod();
		String minGrade = LimitsOfAuthorityMasterHelper.getMinimumEmployeeGradeForLOA(null,customer,null);
		trxValueIn.setMinEmployeeGrade(minGrade);
		
		ICMSTrxResult trxValueOut = new OBCMSTrxResult();
		try {
			trxValueOut = (ICMSTrxResult)CustomerProxyFactory.getProxy().makerUpdateDraftCustomer(ctx,trxValueIn,customer);
		} catch (CustomerException e) {
			CommandProcessingException cpe = new CommandProcessingException(e.getMessage());
			cpe.initCause(e);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}
		ICMSCustomerTrxValue trx = (ICMSCustomerTrxValue) trxValueOut.getTrxValue();
		//insert IFSC data to stage table
		if(null!=ifscList) {
			IIfscMethod ifscStageList[] = new IIfscMethod[ifscBranchList.size()];
			IIfscCodeDao ifscCodeDao = (IIfscCodeDao) BeanHouse.get("ifscCodeDao");
			for(int i=0;i<ifscList.length;i++) {
				IIfscMethod ifscObj=ifscList[i];
				//set stage cust id here 
				ifscObj.setCustomerId(Long.parseLong(trx.getStagingReferenceID()));
				if(!"DISABLE".equals(ifscObj.getStatus())) {
					ifscObj.setStatus("ACTIVE");
				}
				IIfscMethod obj = ifscCodeDao.createStageIfscCode(ifscObj);
				ifscStageList[i]=obj;
			}
		}
		
		
		IBankingMethodDAO bankingMethodDAOImpl = (IBankingMethodDAO)BeanHouse.get("bankingMethodDAO");
//		String bankingMethodss = obCustomer.getBankingMethod();
		if(bankingMethodss == null || "".equals(bankingMethodss)) {
			bankingMethodss = customer.getFinalBankMethodList();
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
			obj.setCustomerIDForBankingMethod(customer.getCifId());
			obj.setStatus("ACTIVE");
			bankingMethodDAOImpl.insertBankingMethodCustStage(obj);				
			
		}
		}
		
		resultMap.put("request.ITrxValue", trx);
		resultMap.put("ICMSCustomerTrxValue", trxValueIn);
	//	resultMap.put("validate","N");
		DefaultLogger.debug(this, " -------- Edit Successfull customer.getCustomerName()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);		
		return returnMap;
	}
}
