/*
 * Created on 2007-2-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.businfra.search.SearchCriteria;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LmtColSearchCriteria extends SearchCriteria {
	private String sourceId;

	private String sciSecId;

	private String secSubtype;
	
	private String custName;
	
	private String propSearchId;
	
	private long limitProfId;


	public long getLimitProfId() {
		return limitProfId;
	}

	public void setLimitProfId(long limitProfId) {
		this.limitProfId = limitProfId;
	}

	/**
	 * @return Returns the sourceId.
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId The sourceId to set.
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @return Returns the sciSecId.
	 */
	public String getSciSecId() {
		return sciSecId;
	}

	/**
	 * @param sciSecId The sciSecId to set.
	 */
	public void setSciSecId(String sciSecId) {
		this.sciSecId = sciSecId;
	}

	/**
	 * @return Returns the secSubtype.
	 */
	public String getSecSubtype() {
		return secSubtype;
	}

	/**
	 * @param secSubtype The secSubtype to set.
	 */
	public void setSecSubtype(String secSubtype) {
		this.secSubtype = secSubtype;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPropSearchId() {
		return propSearchId;
	}

	public void setPropSearchId(String propSearchId) {
		this.propSearchId = propSearchId;
	}

}
