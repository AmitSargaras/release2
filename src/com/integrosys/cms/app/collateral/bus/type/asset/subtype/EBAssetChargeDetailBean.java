package com.integrosys.cms.app.collateral.bus.type.asset.subtype;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetail;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetailBean;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetailHome;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

public abstract class EBAssetChargeDetailBean extends EBCollateralDetailBean {
	/**
	 * Get collateral ID.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return getEBCollateralID().longValue();
	}

	/**
	 * set collateral ID.
	 * 
	 * @param collateralID is of type long
	 */
	public void setCollateralID(long collateralID) {
		setEBCollateralID(new Long(collateralID));
	}

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public ICollateral getValue(ICollateral collateral) {
		try {
			EBCollateralDetailHome detHome = getChargeEJBHome();
			EBCollateralDetail detEjb = detHome.findByPrimaryKey(new Long(collateral.getCollateralID()));
			ICollateral chargeCol = new OBChargeCommon((IChargeCommon) detEjb.getValue(collateral));

			ICollateral newCol = (ICollateral) collateral.getClass().newInstance();
			newCol = super.getValue(collateral);
			AccessorUtil.copyValue(chargeCol, newCol);

			return newCol;
		}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
	 * Set the property collateral type to this entity.
	 * 
	 * @param collateral is of type ICollateral
	 */
	public void setValue(ICollateral collateral) {
		try {
			super.setValue(collateral);

			EBCollateralDetailHome detHome = getChargeEJBHome();
			EBCollateralDetail detEjb = detHome.findByPrimaryKey(new Long(collateral.getCollateralID()));
			detEjb.setValue(collateral);
		}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param collateral of type ICollateral
	 * @throws javax.ejb.CreateException on error creating the entity object
	 */
	public Long ejbCreate(ICollateral collateral) throws CreateException {
		try {
			EBCollateralDetailHome detHome = getChargeEJBHome();
			detHome.create(collateral);

			super.ejbCreate(collateral);
			return null;
		}
		catch (Exception e) {
			throw new CreateException();
		}
	}

	public EBCollateralDetailHome getChargeEJBHome() throws CollateralException {
		return (EBCollateralDetailHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_ASSET_CHARGE_JNDI,
				EBCollateralDetailHome.class.getName());
	}

}
