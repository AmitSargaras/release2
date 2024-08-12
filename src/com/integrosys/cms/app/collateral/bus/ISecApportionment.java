/*
 * Created on Jun 19, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface ISecApportionment extends Serializable {
	public long getSecApportionmentID();

	public void setSecApportionmentID(long id);

	public int getPriorityRanking();

	public void setPriorityRanking(int rank);

	public String getCurrencyCode();

	public void setCurrencyCode(String code);

	public double getPriorityRankingAmount();

	public void setPriorityRankingAmount(double amt);

	public double getApportionAmount();

	public void setApportionAmount(double amt);

	public long getLimitID();

	public void setLimitID(long limitId);

	public long getRefID();

	public void setRefID(long refId);

	public String getPercAmtInd();

	public void setPercAmtInd(String ind);

	public double getByAbsoluteAmt();

	public void setByAbsoluteAmt(double amt);

	public double getByPercentage();

	public void setByPercentage(double perc);

	public String getMinPercAmtInd();

	public void setMinPercAmtInd(String ind);

	public double getMinAbsoluteAmt();

	public void setMinAbsoluteAmt(double amt);

	public double getMinPercentage();

	public void setMinPercentage(double perc);

	public String getMaxPercAmtInd();

	public void setMaxPercAmtInd(String ind);

	public double getMaxAbsoluteAmt();

	public void setMaxAbsoluteAmt(double amt);

	public double getMaxPercentage();

	public void setMaxPercentage(double perc);

	public long getChargeDetailId();

	public void setChargeDetailId(long chargeDetailId);
}
