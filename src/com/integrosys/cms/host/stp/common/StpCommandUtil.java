package com.integrosys.cms.host.stp.common;

import com.integrosys.cms.host.stp.bus.IStpMasterTrans;
import com.integrosys.cms.host.stp.bus.OBStpField;
import com.integrosys.cms.host.stp.bus.OBStpTrans;
import com.integrosys.cms.app.limit.bus.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Oct 27, 2008
 * Time: 1:08:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class StpCommandUtil {

    public static Map getTrxMap(IStpMasterTrans stpMasterTrans) {
        //store stp trans key value pair
        Map stpTransMap = new HashMap();
        for (Iterator iterator = stpMasterTrans.getTrxEntriesSet().iterator(); iterator.hasNext();) {
            OBStpTrans curTrans = (OBStpTrans) iterator.next();
            stpTransMap.put(curTrans.getTrxType() + "&" + curTrans.getReferenceId(), curTrans);
        }
        return stpTransMap;
    }

    /**
     * Method to get a map of Stp response message field-value pair
     *
     * @param returnOBStpFieldList
     * @return
     */
    public static Map getResponseMsgMap(List returnOBStpFieldList) {
        //store response message field id-value pair
        Map responseMap = new HashMap();
        for (Iterator iterator = returnOBStpFieldList.iterator(); iterator.hasNext();) {
            OBStpField obStpField = (OBStpField) iterator.next();
            responseMap.put(obStpField.getFieldID(), obStpField.getValue());
        }
        return responseMap;
    }

    /**
     * Method to get response tag in stp field xml
     *
     * @param transType
     * @return
     */
    public static String getResponseXmlTag(String transType) {
        return "response-" + transType;
    }

    /**
     * Method to get request tag in stp field xml
     *
     * @param transType
     * @return
     */
    public static String getRequestXmlTag(String transType) {
        return "request-" + transType;
    }

    /**
     * Method to convert percentage ~ 100% to 0 - 1 range
     * @param percent in Bigdecimal or Double type
     * @return string with 0 - 1 range
     */
    public static String convertPercentToDecimalPoint(Object percent) {
        if (percent != null) {
            if (percent instanceof BigDecimal) {
                BigDecimal bigDecimal = (BigDecimal) percent;
                return (bigDecimal.setScale(9, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP)).toString();
            } else if (percent instanceof Double) {
                Double aDouble = (Double) percent;
                return (new BigDecimal(aDouble.doubleValue()).setScale(9, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP)).toString();
            }
        }
        return "0";
    }
}
