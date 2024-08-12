package com.integrosys.cms.app.propertyparameters.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Jan 30, 2007 Time: 12:25:49 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class EBPropertyParametersBean implements EntityBean, IPropertyParameters {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getParameterId" };

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getParameterId" };

	public Long ejbCreate(IPropertyParameters anICCTask) throws CreateException {
		if (anICCTask == null) {
			throw new CreateException("ICCTask is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			DefaultLogger.debug(this, "Creating CC task with ID: " + pk);
			setParameterId(pk);
			AccessorUtil.copyValue(anICCTask, this, EXCLUDE_METHOD_CREATE);

//			System.out.println("pk");
//			System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");

			setVersionTime(VersionGenerator.getVersionNumber());
			return null;
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CreateException("Exception in ejbCreate : " + ex.toString());
		}
	}

	public void setValue(IPropertyParameters aIPropertyParameters) throws ConcurrentUpdateException,
			PropertyParametersException {
		try {
			if (getVersionTime() != aIPropertyParameters.getVersionTime()) {
				throw new ConcurrentUpdateException("Mismatch timestamp");
			}
			AccessorUtil.copyValue(aIPropertyParameters, this, EXCLUDE_METHOD_UPDATE);

			setVersionTime(VersionGenerator.getVersionNumber());
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new PropertyParametersException("Exception in setValue: " + ex.toString());
		}
	}

	public IPropertyParameters getValue() {
		IPropertyParameters value = new OBPropertyParameters();
		AccessorUtil.copyValue(this, value, null);
		return value;
	}

	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_CMS_VALUATION_PARAMETERS_SEQ;
	}

	/**
	 * Default Constructor
	 */
	public EBPropertyParametersBean() {
	}

	public abstract Long getEBparameterId();

	public abstract void setEBparameterId(Long parameterId);

	public abstract Long getEBlandAreaValueFrom();

	public abstract void setEBlandAreaValueFrom(Long landAreaValueFrom);

	public abstract Long getEBlandAreaValueTo();

	public abstract void setEBlandAreaValueTo(Long landareaValueTo);

	public abstract Long getEBbuildupAreaValueFrom();

	public abstract void setEBbuildupAreaValueFrom(Long buildupareaValueFrom);

	public abstract Long getEBbuildupAreaValueTo();

	public abstract void setEBbuildupAreaValueTo(Long buildupAreaValueTo);

	public abstract Double getEBminimumCurrentOmv();

	public abstract void setEBminimumCurrentOmv(Double minimumCurrentOmv);

	public abstract Long getEBvariationOMV();

	public abstract void setEBvariationOMV(Long variationOMV);

	public long getParameterId() {
		if (getEBparameterId() != null) {
			return getEBparameterId().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	}

	public void setParameterId(long parameterId) {
		setEBparameterId(new Long(parameterId));
	}

	public long getLandAreaValueFrom() {
		if (getEBlandAreaValueFrom() != null) {
			return getEBlandAreaValueFrom().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	}

	public void setLandAreaValueFrom(long landAreaValueFrom) {
		setEBlandAreaValueFrom(new Long(landAreaValueFrom));
	}

	public long getLandAreaValueTo() {

		if (getEBlandAreaValueTo() != null) {
			return getEBlandAreaValueTo().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public void setLandAreaValueTo(long landareaValueTo) {
		setEBlandAreaValueTo(new Long(landareaValueTo));
	}

	public long getBuildupAreaValueFrom() {
		if (getEBbuildupAreaValueFrom() != null) {
			return getEBbuildupAreaValueFrom().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public void setBuildupAreaValueFrom(long buildupareaValueFrom) {
		setEBbuildupAreaValueFrom(new Long(buildupareaValueFrom));
	}

	public long getBuildupAreaValueTo() {
		if (getEBbuildupAreaValueTo() != null) {
			return getEBbuildupAreaValueTo().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public void setBuildupAreaValueTo(long buildupAreaValueTo) {
		setEBbuildupAreaValueTo(new Long(buildupAreaValueTo));
	}

	public double getMinimumCurrentOmv() {
		if (getEBminimumCurrentOmv() != null) {
			return getEBminimumCurrentOmv().doubleValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.DOUBLE_INVALID_VALUE;
	}

	public void setMinimumCurrentOmv(double minimumCurrentOmv) {
		setEBminimumCurrentOmv(new Double(minimumCurrentOmv));

	}

	public long getVariationOMV() {
		if (getEBvariationOMV() != null) {
			return getEBvariationOMV().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public void setVariationOMV(long variationOMV) {
		setEBvariationOMV(new Long(variationOMV));
	}

	public void ejbPostCreate(IPropertyParameters anICCTask) throws CreateException {
	}

	public void ejbActivate() throws EJBException, RemoteException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	public void unsetEntityContext() throws EJBException, RemoteException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	public void setEntityContext(EntityContext entityContext) throws EJBException, RemoteException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	public void ejbStore() throws EJBException, RemoteException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	public void ejbRemove() throws RemoveException, EJBException, RemoteException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	public void ejbLoad() throws EJBException, RemoteException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

    public abstract String getPropertyType();

    public abstract void setPropertyType(String propertyType);

    public abstract String getCollateralSubType();

    public abstract void setCollateralSubType(String collateralSubType);

    public abstract String getCountryCode();

    public abstract void setCountryCode(String countryCode);

    public abstract String getStateCode();

    public abstract void setStateCode(String stateCode);

    public abstract String getDistrictCode();

    public abstract void setDistrictCode(String districtCode);

    public abstract String getMukimCode();

    public abstract void setMukimCode(String mukimCode);

    public abstract String getPostcode();

    public abstract void setPostcode(String postcode);

    public abstract String getLandAreaUnitFrom();

    public abstract void setLandAreaUnitFrom(String landAreaUnitFrom);

    public abstract String getLandAreaUnitTo();

    public abstract void setLandAreaUnitTo(String landareaUnitTo);

    public abstract String getBuildupAreaUnitFrom();

    public abstract void setBuildupAreaUnitFrom(String buildupareaUnitFrom);

    public abstract String getBuildupAreaUnitTo();

    public abstract void setBuildupAreaUnitTo(String buildupAreaUnitTo);

    public abstract String getOmvType();

    public abstract void setOmvType(String omvType);

    public abstract String getValuationDescription();

    public abstract void setValuationDescription(String valuationDescription);

    public abstract long getVersionTime();

    public abstract void setVersionTime(long l);
}
