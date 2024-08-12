package com.integrosys.cms.app.creditriskparam.bus;

import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * ICreditRiskParam Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface ICreditRiskParam extends Serializable, IValueObject {

	public long getParameterId();

	public void setParameterId(long parameterId);

	public String getParameterType();

	public void setParameterType(String parameterType);

	public double getMarginOfAdvance();

	public void setMarginOfAdvance(double marginOfAdvance);

	public double getMaximumCap();

	public void setMaximumCap(double maximumCap);

	public String getIsIntSuspend();

	public void setIsIntSuspend(String isIntSuspend);

	public String getStatus();

	public void setStatus(String status);

	public String getIsLiquid();

	public void setIsLiquid(String isLiquid);

	public long getFeedId();

	public void setFeedId(long feedId);

	public long getParameterRef();

	public void setParameterRef(long parameterRef);

	public void setIsFi(String isFi);

	public String getIsFi();

	public String getParamBoardType();

	public void setParamBoardType(String paramBoardType);

	public String getParamShareStatus();

	public void setParamShareStatus(String paramShareStatus);

	public String getIsAcceptable();

	public void setIsAcceptable(String isAcceptable);

}