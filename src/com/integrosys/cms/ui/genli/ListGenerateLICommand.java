package com.integrosys.cms.ui.genli;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.trx.IDocumentItemTrxValue;
import com.integrosys.cms.app.collateral.trx.assetlife.ICollateralAssetLifeTrxValue;
import com.integrosys.cms.app.collateral.trx.assetlife.ReadCollateralAssetLifeByTrxIDOperation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.documentlocation.proxy.DocumentItemProxyManagerFactory;
import com.integrosys.cms.app.generateli.proxy.IGenerateLiDocProxyManager;
import com.integrosys.cms.app.generateli.proxy.GenerateLiDocProxyManagerFactory;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author $Author: Chang Yew $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2010/03/24 04:11:36 $ Tag: $Name: $
 */
public class ListGenerateLICommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListGenerateLICommand() {
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
			{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
					GLOBAL_SCOPE },
			{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }
				
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
				{ "colChkLst", "java.util.List", REQUEST_SCOPE },
				{ "genList", "java.util.List", REQUEST_SCOPE } });
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
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			boolean maintainedCheckList = true;
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			HashMap checkListMap = proxy.getAllCollateralCheckListSummaryList(theOBTrxContext, limitProfileID);
			if (checkListMap != null) {
				CollateralCheckListSummary[] colChkLst = (CollateralCheckListSummary[]) checkListMap
						.get(ICMSConstant.NORMAL_LIST);
				if (colChkLst != null) {
					for (int jj = 0; jj < colChkLst.length; jj++)
					{
						CollateralCheckListSummary summary = colChkLst[jj];
						if(summary.getCheckListID() == -999999999)
						{
							maintainedCheckList = false;
						}
					}
					
				}
				
				if(maintainedCheckList)
				{
					List l = Arrays.asList(colChkLst);
					resultMap.put("colChkLst", l);
				}
			}
			
			String docTrxID = (String) map.get("docTrxID");
			DefaultLogger.debug(this, "docTrxID" + docTrxID);
			IGenerateLiDocProxyManager docProxy = GenerateLiDocProxyManagerFactory.getProxyManager();
			resultMap.put("genList", docProxy.getAllLiDocList());
			}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
