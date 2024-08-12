package com.integrosys.cms.ui.collateral.pledgor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: KLYong
 * @version: ${VERSION}
 * @since: Jan 4, 2009 8:31:32 PM
 */
public class UpdatePledgorCommand extends AbstractCommand {
    private final Logger logger = LoggerFactory.getLogger(UpdatePledgorCommand.class);

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                {"pledgorData", "[Lcom.integrosys.cms.app.collateral.bus.ICollateralPledgor;", FORM_SCOPE},
                {"subtype", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE} });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                {"subtype", "java.lang.String", REQUEST_SCOPE},});
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

        ICollateralPledgor[] pledgorList = (ICollateralPledgor[]) map.get("pledgorData"); //Current update/edit pledgor
        /*
        String cifId = ((ICollateralPledgor) pledgorList.get(0)).getLegalID();

        if (iCol != null) {
            if (iCol.getPledgors() != null) {
                ICollateralPledgor[] pledgors = iCol.getPledgors(); //Pledgor list
                for (int i = 0; i < pledgors.length; i++) {
                    if (!StringUtils.equals(cifId, pledgors[i].getLegalID())) //If newly updated pledgor matched old pledgor, then skip
                        pledgorList.add(pledgors[i]);
                }
            }
            Collections.sort(pledgorList, new Comparator() {
                public int compare(Object a, Object b) {
                    ICollateralPledgor pledgor1 = (ICollateralPledgor) a;
                    ICollateralPledgor pledgor2 = (ICollateralPledgor) b;
                    return pledgor1.getPledgorName().compareTo(pledgor2.getPledgorName());
                }
            });
            iCol.setPledgors((ICollateralPledgor[]) pledgorList.toArray(new ICollateralPledgor[0]));
            trxValue.setStagingCollateral(iCol);
        }
        */
        iCol.setPledgors(pledgorList);
        trxValue.setStagingCollateral(iCol);
        
        result.put("serviceColObj", trxValue);

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return returnMap;
    }
}
