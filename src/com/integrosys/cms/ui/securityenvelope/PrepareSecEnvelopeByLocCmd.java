package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelope;
import com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

import java.util.*;

/**
 * Describe this class.
 * Purpose: for Maker to create new Security Envelope by Location
 * Description: command that help the Maker to create new Security Envelope by Location
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 *        Tag: $Name$
 */

public class PrepareSecEnvelopeByLocCmd extends SecEnvelopeCmd implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public PrepareSecEnvelopeByLocCmd() {

    }

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"fromEvent", "java.lang.String", REQUEST_SCOPE},
                {"ISecEnvelopeTrxValue", "com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue", SERVICE_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE}
        }
        );
    }

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"SecurityEnvelope", "com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelope", FORM_SCOPE},
                {"fromEvent", "java.lang.String", REQUEST_SCOPE}
        }
        );
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();

        try {
            String event = (String) (map.get("event"));
            String fromEvent = (String) (map.get("fromEvent"));
            ISecEnvelope obParam = new OBSecEnvelope();
           
            ISecEnvelopeTrxValue SecEnvelopeTrxObj = (ISecEnvelopeTrxValue) (map.get("ISecEnvelopeTrxValue"));
            obParam = this.getSecEnvelope(SecEnvelopeTrxObj, event);

            resultMap.put("SecurityEnvelope", obParam);
        } catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            throw (new CommandProcessingException(e.getMessage()));
        }
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return returnMap;
    }

    private ISecEnvelope getSecEnvelope(ISecEnvelopeTrxValue trxValue, String event) {
        if (trxValue == null) {
            return new OBSecEnvelope();
        }
        if ("read".equals(event)) {
            return trxValue.getSecEnvelope();
        } else {
            ISecEnvelope curTemplate = trxValue.getStagingSecEnvelope();
            if (curTemplate == null) {
                curTemplate = new OBSecEnvelope();
            }
            return curTemplate;
        }
    }
}



