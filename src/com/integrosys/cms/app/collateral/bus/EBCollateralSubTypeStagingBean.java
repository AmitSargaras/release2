/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralSubTypeStagingBean.java,v 1.2 2003/08/15 06:00:31 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for staging collateral subtype entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/15 06:00:31 $ Tag: $Name: $
 */
public abstract class EBCollateralSubTypeStagingBean extends EBCollateralSubTypeBean {
	public abstract String getSecuritySubTypeSEQ();

	public abstract void setSecuritySubTypeSEQ(String securitySubTypeSEQ);

	public abstract String getSubTypeStandardisedApproachStr();

	public abstract String getSubTypeFoundationIRBStr();

	public abstract String getSubTypeAdvancedIRBStr();

	public abstract void setSubTypeStandardisedApproachStr(String subTypeStandardisedApproachStr);

	public abstract void setSubTypeFoundationIRBStr(String subTypeFoundationIRBStr);

	public abstract void setSubTypeAdvancedIRBStr(String subTypeAdvancedIRBStr);

	/**
	 * Helper method to get the invault indicator
	 * @return boolean - true if it is to be invault and false otherwise
	 */
	public boolean getSubTypeStandardisedApproach() {
		if ((getSubTypeStandardisedApproachStr() != null)
				&& getSubTypeStandardisedApproachStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to set invault indicator
	 * @param subTypeStandardisedApproach - boolean
	 */
	public void setSubTypeStandardisedApproach(boolean subTypeStandardisedApproach) {
		if (subTypeStandardisedApproach) {
			setSubTypeStandardisedApproachStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setSubTypeStandardisedApproachStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Helper method to get the invault indicator
	 * @return boolean - true if it is to be invault and false otherwise
	 */
	public boolean getSubTypeFoundationIRB() {
		if ((getSubTypeFoundationIRBStr() != null) && getSubTypeFoundationIRBStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to set invault indicator
	 * @param subTypeFoundationIRB - boolean
	 */
	public void setSubTypeFoundationIRB(boolean subTypeFoundationIRB) {
		if (subTypeFoundationIRB) {
			setSubTypeFoundationIRBStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setSubTypeFoundationIRBStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Helper method to get the invault indicator
	 * @return boolean - true if it is to be invault and false otherwise
	 */
	public boolean getSubTypeAdvancedIRB() {
		if ((getSubTypeAdvancedIRBStr() != null) && getSubTypeAdvancedIRBStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to set invault indicator
	 * @param setSubTypeAdvancedIRB - boolean
	 */
	public void setSubTypeAdvancedIRB(boolean subTypeAdvancedIRB) {
		if (subTypeAdvancedIRB) {
			setSubTypeAdvancedIRBStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setSubTypeAdvancedIRBStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Get DAO implementation for collateral dao.
	 * 
	 * @return ICollateralDAO
	 */
	protected ICollateralDAO getCollateralDAO() {
		return CollateralDAOFactory.getStagingDAO();
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param colType of type ICollateralSubType
	 * @throws CreateException on error creating the entity object
	 */
	public String ejbCreate(ICollateralSubType colType) throws CreateException {
		try {
			String id = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COL_SUBTYPE, true);
			AccessorUtil.copyValue(colType, this);
			setSecuritySubTypeSEQ(id);
			if (colType.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
				setGroupID(Long.parseLong(id));
			}
			setVersionTime(VersionGenerator.getVersionNumber());
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}
}