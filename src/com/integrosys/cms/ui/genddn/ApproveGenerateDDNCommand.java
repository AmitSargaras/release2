/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genddn;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.ddn.bus.IDDNItem;
import com.integrosys.cms.app.ddn.bus.OBDDNItem;
import com.integrosys.cms.app.ddn.proxy.DDNProxyManagerFactory;
import com.integrosys.cms.app.ddn.proxy.IDDNProxyManager;
import com.integrosys.cms.app.ddn.trx.IDDNTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: htli $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/06/08 06:44:49 $ Tag: $Name: $
 */
public class ApproveGenerateDDNCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ApproveGenerateDDNCommand() {
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
						GLOBAL_SCOPE }, { "certTrxVal", "com.integrosys.cms.app.ddn.trx.IDDNTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
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
			IDDNTrxValue certTrxVal = (IDDNTrxValue) map.get("certTrxVal");
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			IDDNProxyManager proxy = DDNProxyManagerFactory.getDDNProxyManager();
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
//			ctx.setTrxCountryOrigin(limit.getOriginatingLocation().getCountryCode());
			// ctx.setTrxOrganisationOrigin(limit.getOriginatingLocation().
			// getOrganisationCode());

			if ((certTrxVal.getDDN() != null) && (certTrxVal.getStagingDDN() != null)) {
				IDDNItem[] actual = certTrxVal.getDDN().getDDNItemList();
				IDDNItem[] stage = certTrxVal.getStagingDDN().getDDNItemList();
				ArrayList list = new ArrayList();

				if ((actual != null) && (stage != null)) {
					for (int i = 0; i < stage.length; i++) {
						list.add(stage[i]);
					}

					for (int i = 0; i < actual.length; i++) {
						boolean found = false;
						for (int j = 0; j < stage.length; j++) {
							//if (actual[i].getLimitType().equals(stage[j].getLimitType())
							//		&& (actual[i].getLimitID() == stage[j].getLimitID())) {
                            if (actual[i].getDocNumber() == stage[j].getDocNumber()) {
								found = true;
								break;
							}
						}
						if (!found) {
                            //from actual but not found in staging...so most probably..this is deleted item
                            ((IDDNItem)actual[i]).setIsDeletedInd(true);
							list.add(actual[i]);
						}
					}
				}
				if (list.size() != 0) {
					certTrxVal.getStagingDDN().setDDNItemList((IDDNItem[]) list.toArray(new OBDDNItem[0]));
				}
			}

			certTrxVal = proxy.checkerApproveGenerateDDN(ctx, certTrxVal);
			resultMap.put("request.ITrxValue", certTrxVal);
			// DefaultLogger.debug(this, "checkListTrxVal after approve " +
			// certTrxVal);
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
