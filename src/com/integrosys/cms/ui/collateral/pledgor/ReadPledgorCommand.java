/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/cash/ReadDepositCommand.java,v 1.9 2004/06/04 05:19:57 hltan Exp $
 */

package com.integrosys.cms.ui.collateral.pledgor;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: KLYong
 * @version: ${VERSION}
 * @since: Jan 03, 2009 12:08:53 PM
 */
public class ReadPledgorCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{{"index", "java.lang.String", REQUEST_SCOPE},
                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                {"subtype", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE}});
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{{"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                {"colPledgorObj", "com.integrosys.cms.app.collateral.bus.ICollateralPledgor", REQUEST_SCOPE},
                {"subtype", "java.lang.String", REQUEST_SCOPE}, {"index", "java.lang.String", REQUEST_SCOPE}});
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        String event = (String) map.get("event");
        DefaultLogger.debug(this, "Event: " + event);

        ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");
        ICollateral iCol = trxValue.getStagingCollateral();

        String subtype = (String) map.get("subtype");
        result.put("subtype", subtype);
        String strIndex = (String) map.get("index");
        result.put("index", strIndex);

        if (iCol != null) {
            int index = Integer.parseInt(strIndex);
            ICollateralPledgor[] iCollateralPledgor = iCol.getPledgors();
            result.put("colPledgorObj", iCollateralPledgor[index]);
        }
        result.put("serviceColObj", trxValue);

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return returnMap;
    }
}
