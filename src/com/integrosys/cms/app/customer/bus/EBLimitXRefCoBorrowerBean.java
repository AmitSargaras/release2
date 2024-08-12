package com.integrosys.cms.app.customer.bus;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.batch.common.BatchResourceFactory;
 
public abstract class EBLimitXRefCoBorrowerBean implements EntityBean, ILimitXRefCoBorrower {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_LIMIT_XREF_COBORROWER;
	private static final String[] EXCLUDE_METHOD = new String[] { "getId", "getXRefID" };
	
	protected EntityContext _context = null;

	

	public long getId() {
		if (null != getCoBorrowerLimitPK2()) {
			return getCoBorrowerLimitPK2().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	public void setId(long value) {
		setCoBorrowerLimitPK2(new Long(value));
	}
		
	public long getXRefID() {
		if (null != getCoBorrowerXRefIdFK()) {
			return getCoBorrowerXRefIdFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}
	
	public void setXRefID(long value) {
		setCoBorrowerXRefIdFK(new Long(value));
	}
	
	public ILimitXRefCoBorrower getValue() {
		ILimitXRefCoBorrower value = new OBLimitXRefCoBorrower();
		AccessorUtil.copyValue(this, value);
		return value;
	}
	
	public Long ejbCreate(ILimitXRefCoBorrower value) throws CreateException {
		if (null == value) {
			throw new CreateException("ILimitXRefCoBorrower is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setId(pk);
			return new Long(pk);
		}
		catch (Exception e) {
			e.printStackTrace();
			_context.setRollbackOnly();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}
	
	public void setValue(ILimitXRefCoBorrower value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}
	
	public void ejbPostCreate(ILimitXRefCoBorrower value) {}
	public void ejbActivate() throws EJBException, RemoteException {}
	public void ejbLoad() throws EJBException, RemoteException {}
	public void ejbPassivate() throws EJBException, RemoteException {}
	public void ejbRemove() throws RemoveException, EJBException, RemoteException {}
	public void ejbStore() throws EJBException, RemoteException {}

	public void setEntityContext(EntityContext ctx) {
		_context = ctx;
	}
	public void unsetEntityContext() {
		_context = null;
	}
	
	public abstract Long getCoBorrowerLimitPK2();
	public abstract void setCoBorrowerLimitPK2(Long value);
	
	public abstract Long getCoBorrowerXRefIdFK();
	public abstract void setCoBorrowerXRefIdFK(Long value);
	
   
    public  abstract String getCoBorrowerId();
    public  abstract void setCoBorrowerId(String coBorrowerId);

    
    public  abstract String getCoBorrowerName();
    public  abstract void setCoBorrowerName(String coBorrowerName);
    
    
}
