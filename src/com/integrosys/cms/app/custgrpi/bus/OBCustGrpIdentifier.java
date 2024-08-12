package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 15, 2007
 * Time: 11:54:18 AM
 * To change this template use File | Settings | File Templates.
 */


public class OBCustGrpIdentifier implements ICustGrpIdentifier {

    private long grpNo = ICMSConstant.LONG_INVALID_VALUE;
    private long grpIDRef = ICMSConstant.LONG_INVALID_VALUE;
    private long grpID = ICMSConstant.LONG_INVALID_VALUE;
    private String accountMgmt = "";
    private String groupName = "";
    private String groupAccountMgrName = "";
    private long groupAccountMgrID = ICMSConstant.LONG_INVALID_VALUE;
    private String groupCounty = "";
    private String groupCurrency = "";
    private String businessUnit = "";
    private Date lastReviewDt;
    private String groupRemarks = "";
    private String internalLmt;
    private Amount groupLmt;
    private String approvedBy = "";
    private long versionTime = 0;
    private boolean masterGroupInd = false;
    private String groupType = "";
    private boolean isBGEL = false;
    private String groupAccountMgrCode = "";
    private long masterGroupEntityID = ICMSConstant.LONG_INVALID_VALUE;


    private IGroupCreditGrade[] groupCreditGrade;
    private IGroupSubLimit[] groupSubLimit;
    private IGroupMember[] groupMember;
    private IGroupOtrLimit[] groupOtrLimit;

    public boolean getIsBGEL() {
        return isBGEL;
    }

    public void setIsBGEL(boolean BGEL) {
        isBGEL = BGEL;
    }

    public String getGroupAccountMgrCode() {
        return groupAccountMgrCode;
    }

    public void setGroupAccountMgrCode(String groupAccountMgrCode) {
        this.groupAccountMgrCode = groupAccountMgrCode;
    }

    public IGroupOtrLimit[] getGroupOtrLimit() {
        return groupOtrLimit;
    }

    public void setGroupOtrLimit(IGroupOtrLimit[] groupOtrLimit) {
        this.groupOtrLimit = groupOtrLimit;
    }

    public long getGrpNo() {
        return grpNo;
    }

    public void setGrpNo(long grpNo) {
        this.grpNo = grpNo;
    }


    public long getMasterGroupEntityID() {
        if (masterGroupEntityID == ICMSConstant.LONG_INVALID_VALUE) {
            return grpID;
        } else {
            return masterGroupEntityID;
        }
    }

    public void setMasterGroupEntityID(long masterGroupEntityID) {
        this.masterGroupEntityID = masterGroupEntityID;
    }


    public IGroupMember[] getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(IGroupMember[] groupMember) {
        this.groupMember = groupMember;
    }


    public IGroupCreditGrade[] getGroupCreditGrade() {
        return groupCreditGrade;
    }

    public void setGroupCreditGrade(IGroupCreditGrade[] aIGroupCreditGrade) {
        this.groupCreditGrade = aIGroupCreditGrade;
    }


    public IGroupSubLimit[] getGroupSubLimit() {
        return groupSubLimit;
    }

    public void setGroupSubLimit(IGroupSubLimit[] groupSubLimit) {
        this.groupSubLimit = groupSubLimit;
    }


    public long getGrpIDRef() {
        return grpIDRef;
    }

    public void setGrpIDRef(long grpIDRef) {
        this.grpIDRef = grpIDRef;
    }

    public long getGrpID() {
        return grpID;
    }

    public void setGrpID(long grpID) {
        this.grpID = grpID;
    }

    public boolean getMasterGroupInd() {
        return masterGroupInd;
    }

    public void setMasterGroupInd(boolean masterGroupInd) {
        this.masterGroupInd = masterGroupInd;
    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getGroupAccountMgrID() {
        return groupAccountMgrID;
    }

    public void setGroupAccountMgrID(long groupAccountMgrID) {
        this.groupAccountMgrID = groupAccountMgrID;
    }


    public String getGroupAccountMgrName() {
        return groupAccountMgrName;
    }

    public void setGroupAccountMgrName(String groupAccountMgrName) {
        this.groupAccountMgrName = groupAccountMgrName;
    }

    public Date getLastReviewDt() {
        return lastReviewDt;
    }

    public void setLastReviewDt(Date lastReviewDt) {
        this.lastReviewDt = lastReviewDt;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getAccountMgmt() {
        return accountMgmt;
    }

    public void setAccountMgmt(String accountMgmt) {
        this.accountMgmt = accountMgmt;
    }

    public String getGroupCounty() {
        return groupCounty;
    }

    public void setGroupCounty(String groupCounty) {
        this.groupCounty = groupCounty;
    }

    public String getGroupCurrency() {
        return groupCurrency;
    }

    public void setGroupCurrency(String groupCurrency) {
        this.groupCurrency = groupCurrency;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getGroupRemarks() {
        return groupRemarks;
    }

    public void setGroupRemarks(String groupRemarks) {
        this.groupRemarks = groupRemarks;
    }

    public String getInternalLmt() {
        return internalLmt;
    }

    public void setInternalLmt(String internalLmt) {
        this.internalLmt = internalLmt;
    }

    public boolean isBGEL() {
        return isBGEL;
    }

    public void setBGEL(boolean BGEL) {
        isBGEL = BGEL;
    }

    public Amount getGroupLmt() {
        return groupLmt;
    }

    public void setGroupLmt(Amount groupLmt) {
        this.groupLmt = groupLmt;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    // // Customer Info
    private long groupCCIMapID = ICMSConstant.LONG_INVALID_VALUE;
    // private boolean deletedInd ;
    private String status;
    private String deleteCustomerID;
    private long groupCCINo = ICMSConstant.LONG_INVALID_VALUE;

    private long groupCCINoRef = ICMSConstant.LONG_INVALID_VALUE;


    private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;
    private long legalID = ICMSConstant.LONG_INVALID_VALUE;
    private long subProfileID = ICMSConstant.LONG_INVALID_VALUE;
    private String lmpLeID;

    private String leIDType = "";
    private String idNO = "";

    private String customerName = "";

    private String legalName = "";
    private String sourceID = "";
    private String leID = "11111111";


    public long getGroupCCINoRef() {
        return groupCCINoRef;
    }

    public void setGroupCCINoRef(long groupCCINoRef) {
        this.groupCCINoRef = groupCCINoRef;
    }


    public long getGroupCCIMapID() {
        return groupCCIMapID;
    }

    public void setGroupCCIMapID(long groupCCIMapID) {
        this.groupCCIMapID = groupCCIMapID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getDeleteCustomerID() {
        return deleteCustomerID;
    }

    public void setDeleteCustomerID(String deleteCustomerID) {
        this.deleteCustomerID = deleteCustomerID;
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


    public String toString() {
        return "OBCustGrpIdentifier{" +
                "grpIDRef=" + grpIDRef +
                ", grpID=" + grpID +
                ", masterGroupInd=" + masterGroupInd +
                ", groupType='" + groupType + '\'' +
                ", accountMgmt='" + accountMgmt + '\'' +
                ", groupName='" + groupName + '\'' +
                ", groupAccountMgrName='" + groupAccountMgrName + '\'' +
                ", groupAccountMgrID=" + groupAccountMgrID +
                ", groupCounty='" + groupCounty + '\'' +
                ", groupCurrency='" + groupCurrency + '\'' +
                ", businessUnit='" + businessUnit + '\'' +
                ", lastReviewDt=" + lastReviewDt +
                ", groupRemarks='" + groupRemarks + '\'' +
                ", internalLmt=" + internalLmt +
                ", groupLmt=" + groupLmt +
                ", approvedBy='" + approvedBy + '\'' +
                ", versionTime=" + versionTime +
                ", groupCreditGrade=" + (groupCreditGrade == null ? null : Arrays.asList(groupCreditGrade)) +
                ", groupSubLimit=" + (groupSubLimit == null ? null : Arrays.asList(groupSubLimit)) +
                ", groupMember=" + (groupMember == null ? null : Arrays.asList(groupMember)) +
                ", groupCCIMapID=" + groupCCIMapID +
                ", status='" + status + '\'' +
                ", deleteCustomerID='" + deleteCustomerID + '\'' +
                ", groupCCINo=" + groupCCINo +
                ", groupCCINoRef=" + groupCCINoRef +
                ", limitProfileID=" + limitProfileID +
                ", legalID=" + legalID +
                ", subProfileID=" + subProfileID +
                ", lmpLeID='" + lmpLeID + '\'' +
                ", leIDType='" + leIDType + '\'' +
                ", idNO='" + idNO + '\'' +
                ", customerName='" + customerName + '\'' +
                ", legalName='" + legalName + '\'' +
                ", sourceID='" + sourceID + '\'' +
                ", leID='" + leID + '\'' +
                ", isBGEL=" + isBGEL +
                ", groupAccountMgrCode='" + groupAccountMgrCode + '\'' +
                '}';
    }
}
