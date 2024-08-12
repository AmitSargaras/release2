/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for staging collateral asset life entity.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBCollateralAssetLifeStagingBean extends EBCollateralAssetLifeBean {
	public abstract String getSecuritySubTypeSEQ();

	public abstract void setSecuritySubTypeSEQ(String securitySubTypeSEQ);

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
	 * @param assetLife of type ICollateralAssetLife
	 * @throws CreateException on error creating the entity object
	 */
	public String ejbCreate(ICollateralAssetLife assetLife) throws CreateException {
		try {
			String id = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COL_ASSETLIFE, true);
			AccessorUtil.copyValue(assetLife, this);
			setSecuritySubTypeSEQ(id);
			if (assetLife.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
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