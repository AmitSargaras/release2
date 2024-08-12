/*
 * Created on Mar 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.sharesecurity.bus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.EBCollateralHome;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SBShareSecurityManagerBean implements SessionBean {
	protected SessionContext _context = null;

	public IShareSecurity createShareSecurity(IShareSecurity shareSec) throws ShareSecurityException {
		try {
			EBShareSecurityLocalHome home = getEBShareSecurityLocalHome();
			EBShareSecurityLocal ebShareSec = home.create(shareSec);
			return ebShareSec.getValue();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			_context.setRollbackOnly();
			throw new ShareSecurityException(ex);
		}
	}

	public Collection getShareSecurityForCollateral(Long colId) throws ShareSecurityException {
		try {
			ArrayList res = new ArrayList();
			EBShareSecurityLocalHome home = getEBShareSecurityLocalHome();
			Collection col = home.findByCollateralId(colId);
			Iterator iter = col.iterator();
			while (iter.hasNext()) {
				EBShareSecurityLocal ssLocal = (EBShareSecurityLocal) (iter.next());
				res.add(ssLocal.getValue());
			}
			return res;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new ShareSecurityException(ex);
		}
	}

	public Map getSharedSecNameForCollaterals(List colIdList) throws ShareSecurityException {
		try {
			return new ShareSecurityDAO().getSharedSecNameForCollaterals(colIdList);
		}
		catch (Exception ex) {
			throw new ShareSecurityException(ex);
		}
	}

	public void createShareSecForGCMS(long secId, String sourceSecId) throws ShareSecurityException {
		try {
			// EBCollateralHome colHome = getEBCollateralHome();
			OBShareSecurity shareSec = new OBShareSecurity();
			shareSec.setCmsCollateralId(secId);
			shareSec.setSourceSecurityId(sourceSecId);
			shareSec.setStatus("ACTIVE");
			shareSec.setSourceId(ICMSConstant.SOURCE_SYSTEM_CMS);
			shareSec.setLastUpdatedTime(new Date());
			ShareSecurityJdbcDao dao=(ShareSecurityJdbcDao) BeanHouse.get("shareSecurityJdbcDao");
			dao.createSharedSecurity(shareSec);
			// colHome.findByPrimaryKey(new
			// Long(secId)).createShareSecurity(shareSec);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new ShareSecurityException(ex);
		}
	}

	public Collection getSharedSecForColSource(Long colId, String sourceId) throws ShareSecurityException {
		try {
			ArrayList res = new ArrayList();
			EBShareSecurityLocalHome home = getEBShareSecurityLocalHome();
			Collection col = home.findByColAndSource(colId, sourceId);
			Iterator iter = col.iterator();
			while (iter.hasNext()) {
				EBShareSecurityLocal ssLocal = (EBShareSecurityLocal) (iter.next());
				res.add(ssLocal.getValue());
			}
			return res;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new ShareSecurityException(ex);
		}

	}

	public List getSharedSecNameForCollateral(Long colId) throws SearchDAOException {
		return new ShareSecurityDAO().getSharedSecNameForCollateral(colId);
	}

	protected EBShareSecurityLocalHome getEBShareSecurityLocalHome() throws Exception {
		return (EBShareSecurityLocalHome) BeanController.getEJBLocalHome(ICMSJNDIConstant.EB_SHARE_SECURITY_LOCAL_JNDI,
				EBShareSecurityLocalHome.class.getName());
	}

	protected EBCollateralHome getEBCollateralHome() throws CollateralException {
		EBCollateralHome ejbHome = (EBCollateralHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_COLLATERAL_JNDI,
				EBCollateralHome.class.getName());

		if (ejbHome == null) {
			throw new CollateralException("EBCollateralHome is null!");
		}

		return ejbHome;
	}

	public Vector getSharedSecurityValidationResult(OBShareSecurityValidation obShareSecurityValidation)
			throws ShareSecurityValidationException {
		return new ShareSecurityValidationDAO().getSharedSecurityValidationResult(obShareSecurityValidation);
	}

	/**
	 * Called by the container to create a session bean instance. Its parameters
	 * typically contain the information the client uses to customize the bean
	 * instance for its use. It requires a matching pair in the bean class and
	 * its home interface.
	 */
	public void ejbCreate() {
	}

	/**
	 * A container invokes this method before it ends the life of the session
	 * object. This happens as a result of a client's invoking a remove
	 * operation, or when a container decides to terminate the session object
	 * after a timeout. This method is called with no transaction context.
	 */
	public void ejbRemove() {

	}

	/**
	 * The activate method is called when the instance is activated from its
	 * 'passive' state. The instance should acquire any resource that it has
	 * released earlier in the ejbPassivate() method. This method is called with
	 * no transaction context.
	 */
	public void ejbActivate() {
	}

	/**
	 * The passivate method is called before the instance enters the 'passive'
	 * state. The instance should release any resources that it can re-acquire
	 * later in the ejbActivate() method. After the passivate method completes,
	 * the instance must be in a state that allows the container to use the Java
	 * Serialization protocol to externalize and store away the instance's
	 * state. This method is called with no transaction context.
	 */
	public void ejbPassivate() {
	}

	public void setSessionContext(SessionContext ctx) {
		_context = ctx;
	}

}
