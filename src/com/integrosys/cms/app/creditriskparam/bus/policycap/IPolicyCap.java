package com.integrosys.cms.app.creditriskparam.bus.policycap;

import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public interface IPolicyCap extends IValueObject, Serializable {

	public long getPolicyCapID();

	public void setPolicyCapID(long id);

	public String getBoard();

	public void setBoard(String board);

	public float getMaxTradeCapNonFI();

	public void setMaxTradeCapNonFI(float cap);

	public float getMaxCollateralCapNonFI();

	public void setMaxCollateralCapNonFI(float cap);

	public float getQuotaCollateralCapNonFI();

	public void setQuotaCollateralCapNonFI(float cap);

	public float getMaxCollateralCapFI();

	public void setMaxCollateralCapFI(float cap);

	public float getQuotaCollateralCapFI();

	public void setQuotaCollateralCapFI(float cap);

	public float getLiquidMOA();

	public void setLiquidMOA(float moa);

	public float getIlliquidMOA();

	public void setIlliquidMOA(float moa);

	public Amount getPriceCap();

	public void setPriceCap(Amount priceCap);

	/**
	 * Get cms group id.
	 * 
	 * @return long
	 */
	public long getGroupID();

	/**
	 * Set cms group id.
	 * 
	 * @param groupID of type long
	 */
	public void setGroupID(long groupID);

	/**
	 * Get cms common reference id across actual and staging tables.
	 * 
	 * @return long
	 */
	public long getCommonRef();

	/**
	 * Set cms common reference id across actual and staging tables.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef);
}
