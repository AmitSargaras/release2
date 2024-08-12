package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.IIfscMethod;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBIfscMethod;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.otherbranch.bus.OBOtherBranch;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.app.systemBank.proxy.ISystemBankProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * Class created by
 * 
 * @author sandiip.shinde
 * @since 17-03-2011
 * 
 */

public class AddOtherBankCommand extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	private IOtherBankProxyManager otherBankProxyManager;

	private ISystemBankProxyManager systemBankProxy;

	public ISystemBankProxyManager getSystemBankProxy() {
		return systemBankProxy;
	}

	public void setSystemBankProxy(ISystemBankProxyManager systemBankProxy) {
		this.systemBankProxy = systemBankProxy;
	}

	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "otherBranchList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "branchList", "java.util.List", SERVICE_SCOPE },
				{ "ifscBranchList", "java.util.List", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "checkboxIsNBFC", "java.lang.String", REQUEST_SCOPE },
				{ "ifscList", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "BranchId", "java.lang.String", REQUEST_SCOPE },
				{ "bank", "java.util.List", REQUEST_SCOPE },
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
				

				{ "branchList", "java.util.List", SERVICE_SCOPE },
				{ "ifscBranchList", "java.util.List", SERVICE_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
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
		String BranchId = (String) map.get("BranchId");
		String checkboxIsNBFC = (String) map.get("checkboxIsNBFC");
		//ArrayList<OBOtherBank> ifscList =(ArrayList<OBOtherBank>) map.get("ifscList");
		String ifscStringObj =(String) map.get("ifscList");
		List ifscBranchList = (List) map.get("ifscBranchList");
		if(ifscBranchList==null ){
			ifscBranchList= new ArrayList();
		}

		DefaultLogger.debug(this,
				"Inside doExecute() ManualInputCreateCustomerCommand " + event);

		List system = null;

		ICMSCustomer obCustomer = (OBCMSCustomer) map.get("OBCMSCustomer");
		SearchResult bank = (SearchResult) map.get("otherBranchList");
		List branchList = (List) map.get("branchList");
		
		if(branchList==null )
		{
			branchList= new ArrayList();
		}
		ArrayList systemBankList = new ArrayList();
		List bankList = new ArrayList();
		String[] array = BranchId.split(",");
		OBBankingMethod sysBanking = null;
		/*Collection resultList = null;
		int totalCount = 0;
		int listSize = 0;
		
		if (bank != null) {
			resultList = bank.getResultList();
			totalCount = bank.getNItems();
			listSize = resultList.size();
			
		}
		if (resultList == null) {
			resultList = new ArrayList();
			
		}*/
		try {
			boolean flag = true;
			if(branchList!= null)
			{
				for(int i = 0;i<branchList.size();i++){
					OBBankingMethod bnk = (OBBankingMethod)branchList.get(i);
					if(bnk.getBankType()!=null && bnk.getBankType()=="S")
					{
						flag = false;
					}
				}
			}
				if(flag){
			systemBankList = (ArrayList) getSystemBankProxy().getAllActual();
			OBSystemBank hdfc = (OBSystemBank) systemBankList.get(0);
			sysBanking = new OBBankingMethod();
			sysBanking.setSystembank(hdfc);
			sysBanking.setBankType("S");	
			sysBanking.setStatus("ACTIVE");
			branchList.add(sysBanking);
			}

		} catch (TrxParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//for ifsc only
		String[] ifscStringArry =null;
		if("false".equals(checkboxIsNBFC)) {
			ifscStringArry = ifscStringObj.split("~"); 
		}
		
		for (int i = 0; i < array.length; i++) {
			OBBankingMethod banking = null;
		
			try {
				if("false".equals(checkboxIsNBFC)) {
					String ifscCode=array[i];
					IIfscMethod ifscBranch = new OBIfscMethod();
					
					if(null!=ifscStringArry) {
						for(int j=0;j<ifscStringArry.length;j++) {
							String[] ifscObj =ifscStringArry[j].split("\\|");
							if(ifscCode.equals(ifscObj[0])) {
								ifscBranch.setIfscCode(ifscCode);
								ifscBranch.setBankName(ifscObj[1]);
								ifscBranch.setBranchName(ifscObj[2]);
								ifscBranch.setBranchNameAddress(ifscObj[3]);
								ifscBranch.setBankType("O");
								ifscBranch.setStatus("ACTIVE");
								ifscBranchList.add(ifscBranch);
							}
						}
					}
				}else {
					IOtherBranch otherBranch = new OBOtherBranch();
					otherBranch.setId(Long.parseLong(array[i]));
					banking = new OBBankingMethod();
					banking.setOtherbranch(otherBranch);
					banking.setBankType("O");
					banking.setStatus("ACTIVE");
					branchList.add(banking);
				}

			} catch (OtherBankException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		resultMap.put("branchList", branchList);
		resultMap.put("ifscBranchList", ifscBranchList);
		resultMap.put("OBCMSCustomer", obCustomer);

		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
