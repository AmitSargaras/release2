package com.integrosys.cms.ui.custgrpi.groupmember;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.custgrpi.bus.IGroupMember;
import com.integrosys.cms.app.custgrpi.bus.OBGroupMember;
import com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.unittrust.OBUnitTrustFeedGroupTrxValue;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedEntry;
import com.integrosys.cms.app.feed.bus.unittrust.OBUnitTrustFeedEntry;
import com.integrosys.cms.app.feed.bus.unittrust.OBUnitTrustFeedGroup;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import com.integrosys.cms.ui.feed.unittrust.list.UnitTrustListForm;
import com.integrosys.cms.ui.feed.unittrust.list.UnitTrustListAction;

import java.util.HashMap;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;

public class EntitySelectedIDMapper extends AbstractCommonMapper {


    public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap) throws MapperException {

        DefaultLogger.debug(this, "entering mapOBToForm(...).");
        GroupMemberForm form = (GroupMemberForm) aForm;
        return form;
    }


    public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

        DefaultLogger.debug(this, "entering mapFormToOB(...).");

        GroupMemberForm form = (GroupMemberForm) aForm;
        String event = form.getEvent();

        List list = new ArrayList();
        String[] entityCheckBoxID = form.getEntityCheckBoxID();
        if (entityCheckBoxID != null && entityCheckBoxID.length >0){
            for (int i = 0; i < entityCheckBoxID.length; i++) {
                list.add(entityCheckBoxID[i]);
            }
        }
        return list;


    }


    private void extractForDisplay(int offset, int length, GroupMemberForm form, IUnitTrustFeedGroup group) {

        if (group == null) {
            // Do nothing when there is no group.
            return;
        }

        IUnitTrustFeedEntry[] entries = group.getFeedEntries();
        DefaultLogger.debug(this, "number of feed entries = " + entries.length);

        int limit = offset + length;
        if (limit > entries.length) {
            DefaultLogger.debug(this, "offset " + offset + " + length " + length + " > entries.length " + entries.length);
            limit = entries.length;
        }

        String[] updatedUnitPricesArr = new String[limit - offset ];
        for (int i = offset; i < limit; i++) {
            updatedUnitPricesArr[i - offset] = String.valueOf(entries[i].getUnitPrice());
        }

        form.setEntityCheckBoxID(updatedUnitPricesArr);
    }


    public static int adjustOffset(int offset, int length, int maxSize) {

        int adjustedOffset = offset;

        if (offset >= maxSize) {
            if (maxSize == 0) {
                // Do not reduce offset further.
                adjustedOffset = 0;
            } else if (offset == maxSize) {
                // Reduce.
                adjustedOffset = offset - length;
            } else {
                // Rely on "/" = Integer division.
                // Go to the start of the last strip.
                adjustedOffset = maxSize / length * length;
            }
        }

        return adjustedOffset;
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
        return new String[][]{
                {"offset", "java.lang.Integer", SERVICE_SCOPE},
                {"length", "java.lang.Integer", SERVICE_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
        };
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
