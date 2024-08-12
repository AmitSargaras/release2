package com.integrosys.cms.batch.sibs.parameter.obj;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 8, 2008
 * Time: 10:54:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class OBCommonCodeEntryCombineRefEntryWrapper extends OBCommonCodeEntryWrapper{

	private String refEntryCode_1;
    private String refEntryCode_2;

    public String getRefEntryCode_1() {
        return refEntryCode_1;
    }

    public void setRefEntryCode_1(String refEntryCode_1) {
        this.refEntryCode_1 = refEntryCode_1;
    }

    public String getRefEntryCode_2() {
        return refEntryCode_2;
    }

    public void setRefEntryCode_2(String refEntryCode_2) {
        this.refEntryCode_2 = refEntryCode_2;
    }

//    public void setCombineRefEntryCode() {
//        if (getRefEntryCode_1() != null && getRefEntryCode_2() != null) {
//            setRefEntryCode(getRefEntryCode_1().trim() + "|" + getRefEntryCode_2().trim());
//        }
//    }

}