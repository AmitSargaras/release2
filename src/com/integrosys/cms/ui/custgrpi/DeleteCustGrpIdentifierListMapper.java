package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DeleteCustGrpIdentifierListMapper extends AbstractCommonMapper {

    public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap) throws MapperException {

        DefaultLogger.debug(this, "entering mapOBToForm(...).");

        CustGrpIdentifierForm form = (CustGrpIdentifierForm) aForm;
        String event = form.getEvent();

        DefaultLogger.debug(this, "event is " + event);

        return form;
    }


    public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

        DefaultLogger.debug(this, "entering mapFormToOB(...).");

        CustGrpIdentifierForm form = (CustGrpIdentifierForm) aForm;
        String event = form.getEvent();

        if (CustGrpIdentifierAction.EVENT_REMOVE.equals(event)) {
            // Will return a List of feedGroup OB, String[].
            List returnList = new ArrayList();
            return returnList;

        }


        return null;
    }


    /**
     * Defines an two dimensional array with
     * the parameter list to be passed to the mapFormToOB method by a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return new String[][]{{"offset", "java.lang.Integer", SERVICE_SCOPE}, {
                "length", "java.lang.Integer", SERVICE_SCOPE}, {
                com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
                "java.util.Locale", GLOBAL_SCOPE}};
    }


    /**
     * Helper method to return true if integer is one of the array elements in
     * the integer array.
     *
     * @param target
     * @param arr
     * @return boolean
     */
    public static boolean inArray(int target, int[] arr) {

        if (arr == null) {
            return false;
        }

        for (int i = 0; i < arr.length; i++) {
            if (target == arr[i]) {
                return true;
            }
        }

        return false;
    }
}
