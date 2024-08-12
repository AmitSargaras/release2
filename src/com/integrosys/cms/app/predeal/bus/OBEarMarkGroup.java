/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * OBEarMarkGroup
 *
 * Created on 6:08:00 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.bus;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Apr 2, 2007 Time: 6:08:00 PM
 */
public class OBEarMarkGroup implements IEarMarkGroup {
	private Long earMarkGroupId;

	private long feedId;

	private String sourceSystemId;

	private long cmsActualHolding;

	private long earMarkHolding;

	private long earMarkCurrent;

	private boolean breachInd;

	private Date dateQuotaBreach;

	private Date dateMaxCapBreach;

	private Date lastBatchUpdate;

	private Date lastDateQuotaBreach;

	private Date lastDateMaxCapBreach;

	private long versionTime;

	private String status;

	private long totalOfUnits;

	public boolean getBreachInd() {
		return isBreachInd();
	}

	public void setBreachInd(boolean breachInd) {
		this.breachInd = breachInd;
	}

	public long getCmsActualHolding() {
		return cmsActualHolding;
	}

	public void setCmsActualHolding(long cmsActualHolding) {
		this.cmsActualHolding = cmsActualHolding;
	}

	public Date getDateMaxCapBreach() {
		return dateMaxCapBreach;
	}

	public void setDateMaxCapBreach(Date dateMaxCapBreach) {
		this.dateMaxCapBreach = dateMaxCapBreach;
	}

	public Date getDateQuotaBreach() {
		return dateQuotaBreach;
	}

	public void setDateQuotaBreach(Date dateQuotaBreach) {
		this.dateQuotaBreach = dateQuotaBreach;
	}

	public long getEarMarkCurrent() {
		return earMarkCurrent;
	}

	public void setEarMarkCurrent(long earMarkCurrent) {
		this.earMarkCurrent = earMarkCurrent;
	}

	public Long getEarMarkGroupId() {
		return earMarkGroupId;
	}

	public void setEarMarkGroupId(Long earMarkGroupId) {
		this.earMarkGroupId = earMarkGroupId;
	}

	public long getEarMarkHolding() {
		return earMarkHolding;
	}

	public void setEarMarkHolding(long earMarkHolding) {
		this.earMarkHolding = earMarkHolding;
	}

	public long getFeedId() {
		return feedId;
	}

	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}

	public Date getLastBatchUpdate() {
		return lastBatchUpdate;
	}

	public void setLastBatchUpdate(Date lastBatchUpdate) {
		this.lastBatchUpdate = lastBatchUpdate;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String getSourceSystemId() {
		return sourceSystemId;
	}

	public void setSourceSystemId(String sourceSystemId) {
		this.sourceSystemId = sourceSystemId;
	}

	public boolean isBreachInd() {
		return breachInd;
	}

	public Date getLastDateQuotaBreach() {
		return lastDateQuotaBreach;
	}

	public void setLastDateQuotaBreach(Date lastDateQuotaBreach) {
		this.lastDateQuotaBreach = lastDateQuotaBreach;
	}

	public Date getLastDateMaxCapBreach() {
		return lastDateMaxCapBreach;
	}

	public void setLastDateMaxCapBreach(Date lastDateMaxCapBreach) {
		this.lastDateMaxCapBreach = lastDateMaxCapBreach;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the totalOfUnits
	 */
	public long getTotalOfUnits() {
		return totalOfUnits;
	}

	/**
	 * @param totalOfUnits the totalOfUnits to set
	 */
	public void setTotalOfUnits(long totalOfUnits) {
		this.totalOfUnits = totalOfUnits;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
