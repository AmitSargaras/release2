package com.integrosys.cms.ui.feed.bond.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.feed.bus.bond.BondFeedGroupException;
import com.integrosys.cms.app.feed.proxy.bond.IBondFeedProxy;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.bond.OBBondFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.ui.feed.exchangerate.item.ExchangeRateItemForm;

/**
 *@author $Govind: Sahu$
 *Command for checker to read Credit Approval Trx value
 */
public class CheckerReadFileInsertListCommand extends AbstractCommand implements ICommonEventConstant {
	
	


	private IBondFeedProxy bondFeedProxy;


	
	/**
	 * Default Constructor
	 */
	public CheckerReadFileInsertListCommand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE},
				{ "loginId", "java.lang.String", REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				
				
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
				
				{ BondItemForm.MAPPER, "com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry", FORM_SCOPE },
				{ "bondFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue",SERVICE_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE},
				{ "bondFeedEntryList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE},
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,CreditApprovalException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IFileMapperId bondFeedEntry;
			IBondFeedGroupTrxValue trxValue=null;
			String transId=(String) (map.get("TrxId"));
			String event = (String) map.get("event");

			String startIndex =(String) map.get("startIndex");
			SearchResult bondFeedEntryList=null;
			
			String login = (String)map.get("loginId");
			
			if(startIndex == null){
				startIndex ="0"; 
			}
			
			if(login==null){
				login="";
			}
			List result = new ArrayList();
			
			
			// function to get Credit approval Trx value
			trxValue = (OBBondFeedGroupTrxValue) getBondFeedProxy().getInsertFileByTrxID(transId);
	

			result = (List)  getBondFeedProxy().getAllStage(transId,login);
			
			// function to get stging value of Credit Approval trx value
			bondFeedEntry = (OBFileMapperID) trxValue.getStagingFileMapperID();
			
			bondFeedEntryList = new SearchResult(Integer.parseInt(startIndex), 10, result.size(), result);
			
			resultMap.put("bondFeedEntryList",bondFeedEntryList);
			resultMap.put("bondFeedGroupTrxValue", trxValue);
			resultMap.put(ExchangeRateItemForm.MAPPER, bondFeedEntry);
			resultMap.put("event", event);
			resultMap.put("startIndex", startIndex);
			resultMap.put("TrxId", transId);
		} catch (BondFeedGroupException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
//		catch (TransactionException e) {
//			e.printStackTrace();
//			throw (new CommandProcessingException(e.getMessage()));
//		}
		catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	/**
	 * @return the bondFeedProxy
	 */
	public IBondFeedProxy getBondFeedProxy() {
		return bondFeedProxy;
	}

	/**
	 * @param bondFeedProxy the bondFeedProxy to set
	 */
	public void setBondFeedProxy(IBondFeedProxy bondFeedProxy) {
		this.bondFeedProxy = bondFeedProxy;
	}



	
}
