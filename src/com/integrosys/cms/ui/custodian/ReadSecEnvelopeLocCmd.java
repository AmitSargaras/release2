package com.integrosys.cms.ui.custodian;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelopeItem;
import com.integrosys.cms.app.custodian.proxy.ICustodianProxyManager;
import com.integrosys.cms.app.custodian.proxy.CustodianProxyManagerFactory;
import com.integrosys.cms.ui.securityenvelope.SecEnvelopeCmd;
                                                                         
import java.util.*;

/**
 * @author Erene Wong
 * @since 17 March 2010
 */
public class ReadSecEnvelopeLocCmd extends AbstractCommand implements ICommonEventConstant {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                { "docItemSecEnvBarcode", "java.lang.String", REQUEST_SCOPE}
    });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"ISecEnvelopeItem", "com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelopeItem", REQUEST_SCOPE}
        });
    }


    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        try {

            String secEnvelopeBarcode = (String) map.get("docItemSecEnvBarcode");

            ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
            ISecEnvelopeItem secEnvItemLoc = proxy.getSecEnvItemLoc(secEnvelopeBarcode);
            result.put("ISecEnvelopeItem", secEnvItemLoc);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw (new CommandProcessingException(ex.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

}
