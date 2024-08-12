package com.integrosys.cms.ui.creditApproval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.creditApproval.bus.OBCreditApproval;
import com.integrosys.cms.app.creditApproval.proxy.ICreditApprovalProxy;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.creditApproval.trx.OBCreditApprovalTrxValue;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;


/**
 *@author Govind.Sahu $
 *Command for maker to read Credit Approval Trx
 */
public class MakerReadCreditApprovalCommand extends AbstractCommand implements ICommonEventConstant {
	
	private ICreditApprovalProxy creditApprovalProxy;
	IRelationshipMgrProxyManager relationshipMgrProxyManager;


	/**
	 * @return the relationshipMgrProxyManager
	 */
	public IRelationshipMgrProxyManager getRelationshipMgrProxyManager() {
		return relationshipMgrProxyManager;
	}

	/**
	 * @param relationshipMgrProxyManager the relationshipMgrProxyManager to set
	 */
	public void setRelationshipMgrProxyManager(
			IRelationshipMgrProxyManager relationshipMgrProxyManager) {
		this.relationshipMgrProxyManager = relationshipMgrProxyManager;
	}

	/**
	 * @return the creditApprovalProxy
	 */
	public ICreditApprovalProxy getCreditApprovalProxy() {
		return creditApprovalProxy;
	}

	/**
	 * @param creditApprovalProxy the creditApprovalProxy to set
	 */
	public void setCreditApprovalProxy(ICreditApprovalProxy creditApprovalProxy) {
		this.creditApprovalProxy = creditApprovalProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerReadCreditApprovalCommand() {
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
				 {"event", "java.lang.String", REQUEST_SCOPE},
				 {"TrxId", "java.lang.String", REQUEST_SCOPE}
				 
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
				{ "oBCreditApproval", "com.integrosys.cms.app.creditApproval.bus.OBCreditApproval", REQUEST_SCOPE },
				{ "oBCreditApproval", "com.integrosys.cms.app.creditApproval.bus.OBCreditApproval", SERVICE_SCOPE },
				{"creditApprovalTrxValue", "com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue", REQUEST_SCOPE},
				{"creditApprovalTrxValue", "com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue", SERVICE_SCOPE},
				{"TrxId", "java.lang.String", REQUEST_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"regionList", "java.util.List",REQUEST_SCOPE },
				{"regionList", "java.util.List",SERVICE_SCOPE }
				 });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			String event ="";
			event = (String) map.get("event");
			String creditAppId=(String) (map.get("creditAppId"));
			ICreditApproval creditOb;
			ICreditApprovalTrxValue trxValue=null;
			String trxID = (String) (map.get("TrxId"));
			if(trxID!=null){
				if(!trxID.trim().equals(""))
				{
			    trxValue = (OBCreditApprovalTrxValue) getCreditApprovalProxy().getCreditApprovalByTrxID(Long.parseLong(trxID));
			    }
				else
				{
					throw new CreditApprovalException("TrxId id is blank");
				}
			}else
			{
				if(creditAppId!=null && !creditAppId.trim().equals(""))
				{
				trxValue = (ICreditApprovalTrxValue) getCreditApprovalProxy().getCreditApprovalTrxValue(Long.parseLong(creditAppId));
				}
				else
				{
					throw new CreditApprovalException("Credit id is blank");
				}
			}
			
		    creditOb = (OBCreditApproval) trxValue.getStagingCreditApproval();
				
		    resultMap.put("regionList", getRegionList());
			
			DefaultLogger.debug(this, "after getting CreditApproval from proxy.");
			
			resultMap.put("TrxId", trxID);
			resultMap.put("event", event);
			resultMap.put("creditApprovalTrxValue", trxValue);
			resultMap.put("oBCreditApproval", creditOb);
		}catch (CreditApprovalException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		catch (TransactionException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	private List getRegionList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getRelationshipMgrProxyManager().getRegionList("IN");				
		
			for (int i = 0; i < idList.size(); i++) {
				IRegion region = (IRegion)idList.get(i);
				String id = Long.toString(region.getIdRegion());
				String val = region.getRegionName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
