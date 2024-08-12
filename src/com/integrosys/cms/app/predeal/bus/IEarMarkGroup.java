/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * IEarMarkGroup
 *
 * Created on 6:06:32 PM
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

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Apr 2, 2007 Time: 6:06:32 PM
 */
public interface IEarMarkGroup extends Serializable, IValueObject {
	public boolean getBreachInd();

	public void setBreachInd(boolean breachInd);

	public long getCmsActualHolding();

	public void setCmsActualHolding(long cmsActualHolding);

	public Date getDateMaxCapBreach();

	public void setDateMaxCapBreach(Date dateMaxCapBreach);

	public Date getDateQuotaBreach();

	public void setDateQuotaBreach(Date dateQuotaBreach);

	public long getEarMarkCurrent();

	public void setEarMarkCurrent(long earMarkCurrent);

	public Long getEarMarkGroupId();

	public void setEarMarkGroupId(Long earMarkGroupId);

	public long getEarMarkHolding();

	public void setEarMarkHolding(long earMarkHolding);

	public long getFeedId();

	public void setFeedId(long feedId);

	public Date getLastBatchUpdate();

	public void setLastBatchUpdate(Date lastNomineeUpdate);

	public String getSourceSystemId();

	public void setSourceSystemId(String sourceSystemId);

	public Date getLastDateQuotaBreach();

	public void setLastDateQuotaBreach(Date lastDateQuotaBreach);

	public Date getLastDateMaxCapBreach();

	public void setLastDateMaxCapBreach(Date lastDateMaxCapBreach);

	public String getStatus();

	public void setStatus(String status);

	public long getTotalOfUnits();

	public void setTotalOfUnits(long totalOfUnits);

}
