package com.integrosys.cms.app.cci.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 19, 2007
 * Time: 5:30:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBCCICounterpartyDetails  implements ICCICounterpartyDetails{

    private boolean deletedInd ;
    private String deleteCustomerID ;
    private long groupCCINo = ICMSConstant.LONG_INVALID_VALUE;
    private String leID = "11111111" ;
    private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

    private long groupCCINoRef = ICMSConstant.LONG_INVALID_VALUE;
    private long stagingGroupCCINoRef = ICMSConstant.LONG_INVALID_VALUE;

    private long versionTime = 0;



    private long legalID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    private long subProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

    private String leIDType = "";
    private String idNO = "";

    private String customerName = "";

    private String legalName = "";
    private String sourceID = "";
    private String lmpLeID = "";


    private ICCICounterparty[]   ICCICounterparty   ;


    public long getStagingGroupCCINoRef() {
        return stagingGroupCCINoRef;
    }

    public void setStagingGroupCCINoRef(long stagingGroupCCINoRef) {
        this.stagingGroupCCINoRef = stagingGroupCCINoRef;
    }


    public long getGroupCCINoRef() {
        return groupCCINoRef;
    }

    public void setGroupCCINoRef(long groupCCINoRef) {
        this.groupCCINoRef = groupCCINoRef;
    }

    public boolean getDeletedInd() {
        return deletedInd;
    }

    public void setDeletedInd(boolean deletedInd) {
        this.deletedInd = deletedInd;
    }

    public long getGroupCCINo() {
        return groupCCINo;
    }

    public void setGroupCCINo(long groupCCINo) {
        this.groupCCINo = groupCCINo;
    }

    public String getLeID() {
        return leID;
    }

    public void setLeID(String leID) {
        this.leID = leID;
    }

    public long getLimitProfileID() {
        return limitProfileID;
    }

    public void setLimitProfileID(long limitProfileID) {
        this.limitProfileID = limitProfileID;
    }

    public long getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(long versionTime) {
        this.versionTime = versionTime;
    }


    public long getLegalID() {
        return legalID;
    }

    public void setLegalID(long legalID) {
        this.legalID = legalID;
    }

    public long getSubProfileID() {
        return subProfileID;
    }

    public void setSubProfileID(long subProfileID) {
        this.subProfileID = subProfileID;
    }

    public String getLeIDType() {
        return leIDType;
    }

    public void setLeIDType(String leIDType) {
        this.leIDType = leIDType;
    }

    public String getIdNO() {
        return idNO;
    }

    public void setIdNO(String idNO) {
        this.idNO = idNO;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public String getLmpLeID() {
        return lmpLeID;
    }

    public void setLmpLeID(String lmpLeID) {
        this.lmpLeID = lmpLeID;
    }

    public String getDeleteCustomerID() {
        return deleteCustomerID;
    }

    public void setDeleteCustomerID(String deleteCustomerID) {
        this.deleteCustomerID = deleteCustomerID;
    }

    public ICCICounterparty[] getICCICounterparty() {
        return ICCICounterparty;
    }

    public void setICCICounterparty(ICCICounterparty[] ICCICounterparty) {
        this.ICCICounterparty = ICCICounterparty;
    }


}
