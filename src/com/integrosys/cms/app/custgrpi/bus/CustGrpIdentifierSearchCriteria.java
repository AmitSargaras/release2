package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.transaction.ITrxContext;


public class CustGrpIdentifierSearchCriteria extends SearchCriteria {

     private String grpNo;

    private String legalID = null;
    private String customerName = null;
    private String leIDType = null;
    private String idNO = null;
    private String all = null;

    private String searchType;
    private String groupName;
    private String grpID;
    private boolean CustomerSeach;
    private boolean masterGroupInd;




    private ILimit[] limits;
    private String lmtProfileType = ICMSConstant.AA_TYPE_BANK;
    private boolean byLimit = false;
    private ITrxContext ctx;
    private boolean checkDAP = false;
    private boolean isExactSearch;
    private boolean forLimitBooking;

    public CustGrpIdentifierSearchCriteria() {
    }

	public boolean getForLimitBooking() {
        return this.forLimitBooking;
    }

    public void setForLimitBooking(boolean forLimitBooking) {
        this.forLimitBooking = forLimitBooking;
    }
	
    public boolean isExactSearch() {
        return this.isExactSearch;
    }

    public void setExactSearch(boolean exactSearch) {
        this.isExactSearch = exactSearch;
    }


    public boolean getMasterGroupInd() {
        return this.masterGroupInd;
    }

    public void setMasterGroupInd(boolean masterGroupInd) {
        this.masterGroupInd = masterGroupInd;
    }


    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
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

    public void setLegalID(String legalID) {
        this.legalID = legalID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ILimit[] getLimits() {
        return limits;
    }

    public void setLimits(ILimit[] limits) {
        this.limits = limits;
    }

    public ITrxContext getCtx() {
        return ctx;
    }

    public void setCtx(ITrxContext ctx) {
        this.ctx = ctx;
    }

    public boolean isCheckDAP() {
        return checkDAP;
    }

    public void setCheckDAP(boolean checkDAP) {
        this.checkDAP = checkDAP;
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

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getLmtProfileType() {
        return lmtProfileType;
    }

    public void setLmtProfileType(String lmtProfileType) {
        this.lmtProfileType = lmtProfileType;
    }

    public boolean isByLimit() {
        return byLimit;
    }

    public void setByLimit(boolean byLimit) {
        this.byLimit = byLimit;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGrpID() {
        return grpID;
    }

    public void setGrpID(String grpID) {
        this.grpID = grpID;
    }

    public String getGrpNo() {
        return grpNo;
    }

    public void setGrpNo(String grpNo) {
        this.grpNo = grpNo;
    }


}
