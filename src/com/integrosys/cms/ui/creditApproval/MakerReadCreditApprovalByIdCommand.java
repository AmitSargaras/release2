

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
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.app.limit.bus.LimitDAO;

/**
 *@author govind.sahu $
 *Command for maker to read Credit Approval Trx
 */
public class MakerReadCreditApprovalByIdCommand extends AbstractCommand implements ICommonEventConstant {
	
	
	private ICreditApprovalProxy creditApprovalProxy;
	IRelationshipMgrProxyManager relationshipMgrProxyManager;


	
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
	public MakerReadCreditApprovalByIdCommand() {
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
			 {"TrxId", "java.lang.String", REQUEST_SCOPE},
			 { "approvalCode", "java.lang.String", REQUEST_SCOPE},
             { "approvalName", "java.lang.String", REQUEST_SCOPE},
			 { "startIndex", "java.lang.String", REQUEST_SCOPE },
			 {"creditAppId", "java.lang.String", REQUEST_SCOPE}
			 
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
			{"creditApprovalTrxValue", "com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue", SERVICE_SCOPE},
			{"creditApprovalTrxValue", "com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue", REQUEST_SCOPE},
			{"event", "java.lang.String", REQUEST_SCOPE},
			{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
			 { "approvalCode", "java.lang.String", REQUEST_SCOPE},
             { "approvalName", "java.lang.String", REQUEST_SCOPE},
			{"TrxId", "java.lang.String", REQUEST_SCOPE},
			{ "startIndex", "java.lang.String", REQUEST_SCOPE },
			{ "regionList", "java.util.List",REQUEST_SCOPE }
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
			String event =(String) map.get("event");
			String trxId =(String) map.get("TrxId");
			String approvalCode="";
			String approvalName="";
			approvalCode=(String) map.get("approvalCode");
			approvalName=(String) map.get("approvalName");
			ICreditApproval creditOb = null;
			ICreditApprovalTrxValue trxValue=null;
			String creditAppId=(String) (map.get("creditAppId"));
			String startIndex = (String) map.get("startIndex");
		
			
			if(event.equals(CreditApprovalAction.EVENT_RESUBMIT_EDIT_VIEW)|| event.equals(CreditApprovalAction.MAKER_DRAFT_CLOSE_PROCESS) || event.equals(CreditApprovalAction.PREPARE_MAKER_SUBMIT_EDIT) ||event.equals(CreditApprovalAction.MAKER_UPDATE_SAVE_PROCESS))
			{
				if(trxId!=null && trxId.trim()!="" && !("null").equalsIgnoreCase(trxId))

				{
				trxValue = (ICreditApprovalTrxValue) getCreditApprovalProxy().getCreditApprovalByTrxID(Long.parseLong(trxId));
				creditOb = (OBCreditApproval) trxValue.getStagingCreditApproval();
				}
				else
				{
					if(event.equals(CreditApprovalAction.PREPARE_MAKER_SUBMIT_EDIT)){
						if(creditAppId!=null && creditAppId.trim()!=""){
							trxValue = (ICreditApprovalTrxValue) getCreditApprovalProxy().getCreditApprovalTrxValue(Long.parseLong(creditAppId));
							if (trxValue.getStatus().equals("PENDING_UPDATE") || trxValue.getStatus().equals("PENDING_DELETE")||trxValue.getStatus().equals("REJECTED")||trxValue.getStatus().equals("DRAFT")) {
								resultMap.put("wip", "wip");
							}
							}
							else
							{
								throw new CreditApprovalException("Credit Approval Id is blank");
							}
							creditOb = (OBCreditApproval) trxValue.getCreditApproval();
					}
					
				}
				
			}
			else
			{
			if(creditAppId!=null && creditAppId.trim()!=""){
			trxValue = (ICreditApprovalTrxValue) getCreditApprovalProxy().getCreditApprovalTrxValue(Long.parseLong(creditAppId));
			if (trxValue.getStatus().equals("PENDING_UPDATE") || trxValue.getStatus().equals("PENDING_DELETE")||trxValue.getStatus().equals("REJECTED")||trxValue.getStatus().equals("DRAFT")) {
				resultMap.put("wip", "wip");
			}
			}
			else
			{
				throw new CreditApprovalException("Credit Approval Id is blank");
			}
			creditOb = (OBCreditApproval) trxValue.getCreditApproval();
			
			}


			LimitDAO limitDao = new LimitDAO();
					try {
					String migratedFlag = "N";	
					boolean status = false;	
					 status = limitDao.getCAMMigreted("CMS_CREDIT_APPROVAL",Long.parseLong(creditAppId),"ID");
					
					if(status)
					{
						migratedFlag= "Y";
					}
					resultMap.put("migratedFlag", migratedFlag);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			resultMap.put("regionList", getRegionList());
			resultMap.put("event", event);
			resultMap.put("creditApprovalTrxValue", trxValue);
			resultMap.put("oBCreditApproval", creditOb);
			resultMap.put("startIndex", startIndex);
			resultMap.put("approvalCode", approvalCode);            
            resultMap.put("approvalName", approvalName);
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

}
