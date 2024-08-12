
package com.integrosys.cms.app.collateral.bus;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;


public abstract class EBSecurityCoverageBean implements ISecurityCoverage, EntityBean {

	private static final long serialVersionUID = 1L;
	private static final String SEQUENCE = ICMSConstant.SEQUENCE_SECURITY_COVERAGE;
	private static final String[] EXCLUDE_METHODS = new String[] {"getId", "getCollateralId"};
	
	protected EntityContext _context = null;
	
	public long getId() {
		return (getSecurityCoverageId() == null) ? ICMSConstant.LONG_INVALID_VALUE : getSecurityCoverageId();
	}
	
	public void setId(long id) {
		setSecurityCoverageId(Long.valueOf(id));
	}
	
	public abstract Long getSecurityCoverageId();

	public abstract void setSecurityCoverageId(Long id);

	public long getCollateralId() {
		return (getCollateralIdFK()==null) ? ICMSConstant.LONG_INVALID_VALUE : getCollateralIdFK().longValue();
	}
	
	public void setCollateralId(long collateralId) {
		setCollateralIdFK(collateralId);
	}

	public abstract Long getCollateralIdFK();
	
	public abstract void setCollateralIdFK(Long collateralId);
	
	public abstract BigDecimal getCoverageAmount();

	public abstract void setCoverageAmount(BigDecimal coverageAmount);
	
	public abstract BigDecimal getAdHocCoverageAmount();
	public abstract void setAdHocCoverageAmount(BigDecimal adHocCoverageAmount);

	public abstract Double getCoveragePercentage();

	public abstract void setCoveragePercentage(Double coveragePercentage);


	
	public ISecurityCoverage getValue() {
		ISecurityCoverage value = new OBSecurityCoverage();
		AccessorUtil.copyValue(this, value);
		return value;
	}
	
	public void setValue(ISecurityCoverage value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHODS);
	}
	
	public Long ejbCreate(ISecurityCoverage stock) throws CreateException {
		if(stock == null)
			throw new CreateException("ISecurityCoverage is null");
		long primayKey = 0;
		try {
			primayKey = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE));
			AccessorUtil.copyValue(stock, this, EXCLUDE_METHODS);
			setSecurityCoverageId(primayKey);
		} catch (NumberFormatException e) {
			DefaultLogger.error(this, "Exception while converting sequence", e);
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception on create", e);
			e.printStackTrace();
		}
		DefaultLogger.info(this, "Object created with id: "+primayKey);
		
		return Long.valueOf(primayKey);
	}

	public void ejbPostCreate(ISecurityCoverage stock) throws CreateException {
		
	}
	
	public void ejbActivate() throws EJBException, RemoteException {
		
	}

	public void ejbLoad() throws EJBException, RemoteException{
		
	}

	public void ejbPassivate() throws EJBException, RemoteException{
		
	}

	public void ejbRemove() throws RemoveException, EJBException, RemoteException {
		
	}

	public void ejbStore() throws EJBException, RemoteException {
		
	}

	public void setEntityContext(EntityContext ctx) throws EJBException, RemoteException {
		_context = ctx;
	}

	public void unsetEntityContext() throws EJBException, RemoteException {
		_context = null;
	}

}