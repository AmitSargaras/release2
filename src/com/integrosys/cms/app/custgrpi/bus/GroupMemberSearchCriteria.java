package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;

public class GroupMemberSearchCriteria extends CounterpartySearchCriteria {



    public GroupMemberSearchCriteria()    {}

    private String grpNo;
    private String grpID;
    private String groupName = "";

    private String searchType = null;
    private String sourceType = null;
    private String legalID = null;
    private String customerName = null;

    private boolean checkDAP = false;



    private String idNO = null;
    private String all = null;

    private boolean CustomerSeach ;
    private boolean masterGroupInd;


    private ITrxContext ctx;

    private String lmtProfileType ;
    private boolean byLimit ;


    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }


    public boolean getMasterGroupInd() {
        return masterGroupInd;
    }

    public void setMasterGroupInd(boolean masterGroupInd) {
        this.masterGroupInd = masterGroupInd;
    }


    public String getGrpNo() {
        return grpNo;
    }

    public void setGrpNo(String grpNo) {
        this.grpNo = grpNo;
    }


    public String getGrpID() {
        return grpID;
    }

    public void setGrpID(String grpID) {
        this.grpID = grpID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public boolean getByLimit() {
        return byLimit;
    }

    public void setByLimit(boolean byLimit) {
        this.byLimit = byLimit;
    }


    public String getLmtProfileType() {
        return lmtProfileType;
    }

    public void setLmtProfileType(String lmtProfileType) {
        this.lmtProfileType = lmtProfileType;
    }


    public boolean getCustomerSeach() {
        return CustomerSeach;
    }
    public void setCustomerSeach(boolean customerSeach) {
        CustomerSeach = customerSeach;
    }


    public String getLegalID() {
        return legalID;
    }


    public String getCustomerName() {
        return customerName;
    }


    /**
     * Get business transaction context.
     *
     * @return ITrxContext
     */
    public ITrxContext getCtx() {
        return ctx;
    }

    /**
     * Check if the data access must be protected.
     *
     * @return boolean
     */
     //// weiling : to check if this is still being used
     public boolean getCheckDAP() {
       return checkDAP;
     }


    public void setLegalID(String value) {
        legalID = value;
    }

    /**
     * Set the customer name
     *
     * @param value is of type String
     */
    public void setCustomerName(String value) {
        customerName = value;
    }



    /**
     * Set business transaction context.
     *
     * @param ctx of type ITrxContext
     */
    public void setCtx(ITrxContext ctx) {
        this.ctx = ctx;
    }


    public String getAll() {
    return all;
    }

    public void setAll(String all) {
    this.all = all;
    }



    public void setCheckDAP(boolean checkDAP) {
        this.checkDAP = checkDAP;
    }


    public String getIdNO() {
        return idNO;
    }

    public void setIdNO(String idNO) {
        this.idNO = idNO;
    }


    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }


    public String toString() {
        return "GroupMemberSearchCriteria{" +
                ", sourceType='" + sourceType + '\'' +
                ", customerName='" + customerName + '\'' +
                ", legalID='" + legalID + '\'' +
                ", idNO='" + idNO + '\'' +
                ", ctx=" + ctx +
                ", checkDAP=" + checkDAP +
                ", all='" + all + '\'' +
                ", IsCustomerSeach=" + CustomerSeach +
                '}';
    }
}
