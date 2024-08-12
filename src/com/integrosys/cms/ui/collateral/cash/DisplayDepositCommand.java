/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/cash/ReadDepositCommand.java,v 1.9 2004/06/04 05:19:57 hltan Exp $
 */

package com.integrosys.cms.ui.collateral.cash;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod;
import com.integrosys.cms.app.collateral.bus.type.cash.OBLien;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.ui.collateral.CollateralStpValidateUtils;
import com.integrosys.cms.ui.collateral.CollateralStpValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

import org.apache.struts.action.ActionErrors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2004/06/04 05:19:57 $ Tag: $Name: $
 */
public class DisplayDepositCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{{"index", "java.lang.String", REQUEST_SCOPE},
                {"subtype", "java.lang.String", REQUEST_SCOPE},
                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                
                { "trxID", "java.lang.String", REQUEST_SCOPE },
                { "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
			//	{ "OBLien","com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod",FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },

				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,"com.integrosys.cms.app.customer.bus.ICMSCustomer",GLOBAL_SCOPE }
        
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
        return (new String[][]{{"form.depositObject", "java.lang.Object", FORM_SCOPE},
                {"actualDeposit", "com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit", REQUEST_SCOPE},
                {"stageDeposit", "com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit", REQUEST_SCOPE},
                {"depositObj", "com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit", SERVICE_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
             //   { "OBLien", "com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod", FORM_SCOPE },
               // { "lienOb","com.integrosys.cms.app.collateral.bus.type.cash.OBLien",REQUEST_SCOPE },
				
                { "trxID", "java.lang.String", REQUEST_SCOPE },
                {"index", "java.lang.String", REQUEST_SCOPE},
			{ "request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE },
			{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE } });
}
    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here reading for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        ICashCollateral iCash;
        
        String event = (String) map.get("event");

		DefaultLogger.debug(this,
				"Inside doExecute() DisplayDepositCommand " + event);

		//ILienMethod obLien = (OBLien) map.get("OBLien");
		long index = 0;
if( map.get("index") != null)
{
         index = Long.parseLong((String) map.get("index"));
}
        // ICashDeposit iCashDep = (ICashDeposit) map.get("form.depositObject");
        String from_event = (String) map.get("from_event");
        ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");

        if ((from_event != null) && from_event.equals("read")) {
            iCash = (ICashCollateral) itrxValue.getCollateral();
        } else {
            iCash = (ICashCollateral) itrxValue.getStagingCollateral();
            if (ICMSConstant.STATE_PENDING_PERFECTION.equals(itrxValue.getStatus())) {
                Map context = new HashMap();
                //Andy Wong: set CMV to staging if actual got value but staging blank, used for pre Stp valuation validation
                if(itrxValue.getCollateral()!=null && itrxValue.getCollateral().getCMV()!=null
                        && (itrxValue.getStagingCollateral().getCMV()==null || itrxValue.getStagingCollateral().getCMV().getAmount() <= 0)){
                    itrxValue.getStagingCollateral().setCMV(itrxValue.getCollateral().getCMV());
                }
                context.put(CollateralStpValidator.COL_OB, iCash);
                context.put(CollateralStpValidator.TRX_STATUS, itrxValue.getStatus());
                context.put(CollateralStpValidator.COL_TRX_VALUE, itrxValue);
                ActionErrors errors = CollateralStpValidateUtils.validateAndAccumulate(context);
                if (!errors.isEmpty()) {
                    temp.put(MESSAGE_LIST, errors);
                }
            }

            if ((from_event != null) && from_event.equals("process")) {
                ICashDeposit actualDep = null;
                if (itrxValue.getCollateral() != null) {
                    actualDep = getItem(((ICashCollateral) itrxValue.getCollateral()).getDepositInfo(), index);
                }
                ICashDeposit stageDep = getItem(iCash.getDepositInfo(), index);
                result.put("actualDeposit", actualDep);
                result.put("stageDeposit", stageDep);
            }
        }
        ICashDeposit dep;
        if (from_event != null) {
            dep = getItem(iCash.getDepositInfo(), index);
            if ((dep == null) && from_event.equals("process")) {
                if (itrxValue.getCollateral() != null) {
                    dep = getItem(((ICashCollateral) itrxValue.getCollateral()).getDepositInfo(), index);
                }
            }
        } else {
        	//index = 0;
            dep = iCash.getDepositInfo()[(int) index];
        }
        
      //  String event = (String) map.get("event");
        List list = (List)map.get("lienList");
		if(list==null)
		{
		list = new ArrayList();
		}
		ILienMethod[] ilean = dep.getLien();
		if(ilean != null)
        {
        for(int i=0;i<ilean.length;i++)
        {
        	OBLien lien = new OBLien();
        	lien.setCashDepositID(ilean[i].getCashDepositID());
        	lien.setLienAmount(ilean[i].getLienAmount());
        	lien.setLienNumber(ilean[i].getLienNumber());
        	lien.setLienID(ilean[i].getLienID());
        	lien.setSerialNo(ilean[i].getSerialNo());
        	lien.setRemark(ilean[i].getRemark());
        	list.add(lien);
        
        }
        }
        result.put("form.depositObject", dep);
        result.put("depositObj", dep);
      //  result.put("OBLien", obLien);
        result.put("indexID", map.get("indexID"));
        result.put("subtype", map.get("subtype"));
        result.put("from_event", from_event);
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

    private ICashDeposit getItem(ICashDeposit temp[], long itemRef) {
        ICashDeposit item = null;
        if (temp == null) {
            return item;
        }
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].getRefID() == itemRef) {
                item = temp[i];
            } else {
				continue;
			}
		}
		return item;
	}

}
