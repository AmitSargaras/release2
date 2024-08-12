package com.integrosys.cms.app.cci.bus;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.host.eai.core.IEaiConstant;


public class CounterpartySearchCriteria extends SearchCriteria {
    private String _leID = null;
    private String _custName = null;
    private ILimit[] _limits;
    private ITrxContext ctx;
    private boolean checkDAP = false;

    private String leIDType = null;

    private String idNO = null;
    private String all = null;
    private String lmtProfileType = ICMSConstant.AA_TYPE_BANK;
    private boolean byLimit = false;

    private String groupCCINo ;
    private boolean CustomerSeach ;
    
    
    private String dbKey;
    
    private String msgRefNo;
    
    private String status= IEaiConstant.STAT_PROCESSING;
    /**
	 * @return the status
	 */
	public final String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public final void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the msgRefNo
	 */
	public final String getMsgRefNo() {
		return msgRefNo;
	}

	/**
	 * @param msgRefNo the msgRefNo to set
	 */
	public final void setMsgRefNo(String msgRefNo) {
		this.msgRefNo = msgRefNo;
	}

	public boolean getCustomerSeach() {
        return CustomerSeach;
    }

    public void setCustomerSeach(boolean customerSeach) {
        CustomerSeach = customerSeach;
    }


    public CounterpartySearchCriteria()    {}






    public String getGroupCCINo() {
        return groupCCINo;
    }

    public void setGroupCCINo(String groupCCINo) {
        this.groupCCINo = groupCCINo;
    }


    public String getLegalID() {
        return _leID;
    }

    /**
     * Get the customer name
     *
     * @return String
     */
    public String getCustomerName() {
        return _custName;
    }


    public ILimit[] getLimits() {
        return _limits;
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
        _leID = value;
    }

    /**
     * Set the customer name
     *
     * @param value is of type String
     */
    public void setCustomerName(String value) {
        _custName = value;
    }


    public void setLimits(ILimit[] limits) {
        _limits = limits;
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

    public boolean getByLimit() {
    return byLimit;
    }

    public void setByLimit(boolean byLimit) {
    this.byLimit = byLimit;
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

    public String getLeIDType() {
        return leIDType;
    }
    
    public String getCifSource() {
        return leIDType;
    }    

    public void setCifSource(String leIDType) {
        this.leIDType = leIDType;
    }

    public void setLeIDType(String leIDType) {
        this.leIDType = leIDType;
    }

    public String getLmtProfileType() {
        return lmtProfileType;
    }

    public void setLmtProfileType(String lmtProfileType) {
        this.lmtProfileType = lmtProfileType;
    }


    public String toString() {
        return "CounterpartySearchCriteria{" +
                "  leID='" + _leID + '\'' +
                ", custName='" + _custName + '\'' +
                ", leIDType='" + leIDType + '\'' +
                ", idNO='" + idNO + '\'' +
                ", groupCCINo='" + groupCCINo + '\'' +
                ", dbkey='" + this.dbKey + '\'' +
                '}';
    }

	/**
	 * @return the dbKey
	 */
	public final String getDbKey() {
		return dbKey;
	}

	/**
	 * @param dbKey the dbKey to set
	 */
	public final void setDbKey(String dbKey) {
		this.dbKey = dbKey;
	}
}
