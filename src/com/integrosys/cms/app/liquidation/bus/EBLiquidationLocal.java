package com.integrosys.cms.app.liquidation.bus;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;

/**
 * Local interface to EBLiquidationBean.
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBLiquidationLocal extends javax.ejb.EJBLocalObject {

	/**
	 * Get the ILiquidation business object.
	 * 
	 * @return Liquidation object
	 */
	public ILiquidation getValue();

	/**
	 * Set the Liquidation to this entity.
	 * 
	 * @param liquidation is of type ILiquidation
	 * @throws com.integrosys.base.businfra.common.exception.VersionMismatchException
	 *         if the ILiquidation is invalid
	 */
	public void setValue(ILiquidation liquidation) throws VersionMismatchException;
}
