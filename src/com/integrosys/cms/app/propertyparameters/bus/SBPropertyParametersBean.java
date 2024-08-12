package com.integrosys.cms.app.propertyparameters.bus;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.EBMFTemplate;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.EBMFTemplateHome;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Jan 30, 2007 Time: 12:36:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class SBPropertyParametersBean implements javax.ejb.SessionBean {

	public static final String EXCLUDE_STATUS = ICMSConstant.STATE_DELETED;

	private SessionContext _context = null;

	public SBPropertyParametersBean() {
	}

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(javax.ejb.SessionContext sc) {
		_context = sc;
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws TradingBookException on errors encountered
	 */
	protected void rollback() throws PropertyParametersException {
		_context.setRollbackOnly();
	}

	public IPropertyParameters createPropertyParameters(IPropertyParameters anIDDN) throws PropertyParametersException {
		try {
			if (anIDDN == null) {
				throw new PropertyParametersException("The IDDN to be created is null !!!");
			}
			EBPropertyParametersHome home = getEBPropertyParametersHome();
			EBPropertyParameters remote = home.create(anIDDN);

			return remote.getValue();
		}
		catch (CreateException ex) {
			throw new PropertyParametersException("Exception in createDDN: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new PropertyParametersException("Exception in createDDN: " + ex.toString());
		}
	}

	public IPropertyParameters getPropertyParameters(long aDDNID) throws PropertyParametersException {
		try {
			EBPropertyParametersHome home = getEBPropertyParametersHome();
			EBPropertyParameters remote = home.findByPrimaryKey(new Long(aDDNID));
			return remote.getValue();
		}
		catch (FinderException ex) {
			throw new PropertyParametersException("Exception in getDDN: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new PropertyParametersException("Exception in getDDN: " + ex.toString());
		}
	}

	public IPropertyParameters updatePropertyParameters(IPropertyParameters anIDDN) throws ConcurrentUpdateException,
			PropertyParametersException {
		try {
			if (anIDDN == null) {
				throw new PropertyParametersException("The IDDN to be updated is null !!!");
			}
			if (anIDDN.getParameterId() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				throw new PropertyParametersException("The DDNID of the DDN to be updated is invalid !!!");
			}
			EBPropertyParametersHome home = getEBPropertyParametersHome();
			EBPropertyParameters remote = home.findByPrimaryKey(new Long(anIDDN.getParameterId()));
			remote.setValue(anIDDN);
			return remote.getValue();
		}
		catch (FinderException ex) {
			throw new PropertyParametersException("Exception in updateDDN: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new PropertyParametersException("Exception in updateDDN: " + ex.toString());
		}
	}

	public List getAllProParameters() throws PropertyParametersException {
		try {
			return getPropertyParaDao().getAllProParameters();
		}
		catch (SearchDAOException ex) {
			throw new PropertyParametersException("Exception in updateDDN: " + ex.toString());
		}
	}

	public boolean allowAutoValParamTrx(String referenceId) throws PropertyParametersException {
		try {
			return getPropertyParaDao().allowAutoValParamTrx(referenceId);
		}
		catch (SearchDAOException ex) {
			throw new PropertyParametersException("Exception in allowAutoValParamTrx: " + ex.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.SBPropertyParameters#listMFTemplate
	 */
	public List listMFTemplate() throws PropertyParametersException {
		try {
			EBMFTemplateHome ejbHome = getEBMFTemplateHome();

			Collection collection = ejbHome.findAll(EXCLUDE_STATUS);

			Iterator i = collection.iterator();
			ArrayList arrList = new ArrayList();

			while (i.hasNext()) {
				EBMFTemplate theEjb = (EBMFTemplate) i.next();
				arrList.add(theEjb.getValue());
			}

			return arrList;
		}
		catch (FinderException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("FinderException caught at listMFTemplate " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("Exception caught at listMFTemplate: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.SBPropertyParameters#getMFTemplate
	 */
	public IMFTemplate getMFTemplate(long mFTemplateID) throws PropertyParametersException {

		EBMFTemplateHome ejbHome = getEBMFTemplateHome();
		try {

			EBMFTemplate theEjb = ejbHome.findByPrimaryKey(new Long(mFTemplateID));

			return theEjb.getValue();
		}
		catch (FinderException e) {

			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("FinderException caught! " + e.toString());
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyParametersException("RemoteException caught! " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.SBPropertyParameters#createMFTemplate
	 */
	public IMFTemplate createMFTemplate(IMFTemplate value) throws PropertyParametersException {
		if (value == null) {
			throw new PropertyParametersException("IMFTemplate is null");
		}

		EBMFTemplateHome ejbHome = getEBMFTemplateHome();

		try {
			EBMFTemplate theEjb = ejbHome.create(value);

			long verTime = theEjb.getVersionTime();
			theEjb.createDependants(value, verTime, theEjb.getMFTemplateID());

			return theEjb.getValue();
		}
		catch (CreateException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new PropertyParametersException("CreateException caught! " + e.toString());
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new PropertyParametersException("RemoteException caught! " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new PropertyParametersException("Caught Exception!", e);
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.SBPropertyParameters#updateMFTemplate
	 */
	public IMFTemplate updateMFTemplate(IMFTemplate value) throws PropertyParametersException {
		if (value == null) {
			throw new PropertyParametersException("IMFTemplate is null");
		}

		EBMFTemplateHome ejbHome = getEBMFTemplateHome();
		try {

			EBMFTemplate theEjb = ejbHome.findByPrimaryKey(new Long(value.getMFTemplateID()));
			theEjb.setValue(value);

			return theEjb.getValue();
		}
		catch (FinderException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new PropertyParametersException("FinderException caught! " + e.toString());
		}
		catch (VersionMismatchException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new PropertyParametersException("VersionMismatchException caught! " + e.toString(),
					new ConcurrentUpdateException(e.getMessage()));
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new PropertyParametersException("RemoteException caught! " + e.toString());
		}

	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.SBPropertyParameters#deleteMFTemplate
	 */
	public IMFTemplate deleteMFTemplate(IMFTemplate value) throws PropertyParametersException {
		if (value == null) {
			throw new PropertyParametersException("IMFTemplate is null");
		}

		EBMFTemplateHome ejbHome = getEBMFTemplateHome();

		try {
			EBMFTemplate theEjb = ejbHome.findByPrimaryKey(new Long(value.getMFTemplateID()));
			IMFTemplate template = theEjb.getValue();

			// do soft delete
			theEjb.delete(value);

			return template;

		}
		catch (FinderException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new PropertyParametersException("FinderException caught! " + e.toString());
		}
		catch (VersionMismatchException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new PropertyParametersException("VersionMismatchException caught! " + e.toString());
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new PropertyParametersException("RemoteException caught! " + e.toString());
		}
	}

	protected EBPropertyParametersHome getEBPropertyParametersHome() {
		EBPropertyParametersHome home = (EBPropertyParametersHome) BeanController.getEJBHome(
				"EBPropertyParametersHome", EBPropertyParametersHome.class.getName());
		return home;
	}

	/**
	 * Get home interface of EBMFTemplate.
	 * 
	 * @return EBMFTemplate home interface
	 * @throws PropertyParametersException on errors encountered
	 */
	protected EBMFTemplateHome getEBMFTemplateHome() throws PropertyParametersException {
		EBMFTemplateHome ejbHome = (EBMFTemplateHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_MF_TEMPLATE_JNDI,
				EBMFTemplateHome.class.getName());

		if (ejbHome == null) {
			throw new PropertyParametersException("EBMFTemplateHome is null!");
		}

		return ejbHome;
	}

	private PropertyParametersDAO getPropertyParaDao() {
		return new PropertyParametersDAO();
	}

}
