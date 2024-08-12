package com.integrosys.cms.app.commoncode.bus;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class SBCommonCodeTypeBusManagerBean implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBCommonCodeTypeBusManagerBean() {
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
	 * Create a Common Code Type
	 * 
	 * @param anICommonCodeType of ICommonCodeType type
	 * @return ICommonCodeType - the CommonCodeType created
	 * @throws CommonCodeTypeException on errors
	 */
	public ICommonCodeType createCommonCodeType(ICommonCodeType anICommonCodeType) throws CommonCodeTypeException {
		try {
			if (anICommonCodeType == null) {
				throw new CommonCodeTypeException("The Common Code Type to be created is null !!!");
			}
			EBCommonCodeTypeHome home = getEBCommonCodeTypeHome();
			EBCommonCodeType remote = home.create(anICommonCodeType);
			return remote.getValue();
		}
		catch (CreateException ex) {
			ex.printStackTrace();
			throw new CommonCodeTypeException("Exception in createCommonCodeType: " + ex.toString());
		}
		catch (RemoteException ex) {
			ex.printStackTrace();
			throw new CommonCodeTypeException("Exception in createCommonCodeType: " + ex.toString());
		}
	}

	/**
	 * @param categoryCode
	 * @return
	 * @throws CommonCodeTypeException
	 */
	public List getCategoryListByType(int categoryCode) throws CommonCodeTypeException {

		Collection collection = null;
		List list = new ArrayList();
		try {

			EBCommonCodeTypeHome home = getEBCommonCodeTypeHome();
			collection = home.findByCategoryListByType(categoryCode);
			Iterator iter = collection.iterator();
			while (iter.hasNext()) {
				EBCommonCodeType remote = (EBCommonCodeType) iter.next();
				ICommonCodeType commonCodeType = remote.getValue();
				list.add(commonCodeType);
			}
		}
		catch (FinderException ex) {
			ex.printStackTrace();
			throw new CommonCodeTypeException("Exception in getCategoryListByType: " + ex.toString());
		}
		catch (RemoteException ex) {
			ex.printStackTrace();
			throw new CommonCodeTypeException("Exception in getCategoryListByType: " + ex.toString());
		}

		return list;
	}

	public SearchResult getCategoryListByType(CommonCodeTypeSearchCriteria commonCodeTypeSearchCriteria)
			throws CommonCodeTypeException {

		try {

			return new CommonCodeTypeDAO().searchCommonCategoryList(commonCodeTypeSearchCriteria);

		}
		catch (SearchDAOException se) {
			throw new CommonCodeTypeException("Exception in getCategoryListByType", se);
		}

	}

	/**
	 * @param categoryId
	 * @return
	 * @throws CommonCodeTypeException
	 */
	public ICommonCodeType getCategoryById(long categoryId) throws CommonCodeTypeException {

		ICommonCodeType iCommonCodeType = null;
		try {

			EBCommonCodeTypeHome home = getEBCommonCodeTypeHome();
			EBCommonCodeType ebCommonCodeType = home.findByPrimaryKey(new Long(categoryId));
			iCommonCodeType = ebCommonCodeType.getValue();
		}
		catch (FinderException ex) {
			ex.printStackTrace();
			throw new CommonCodeTypeException("Exception in getCategoryById: " + ex.toString());
		}
		catch (RemoteException ex) {
			ex.printStackTrace();
			throw new CommonCodeTypeException("Exception in getCategoryById: " + ex.toString());
		}

		// list.addAll(collection);
		return iCommonCodeType;
	}

	/**
	 * Update a CommonCodeType
	 * @param anICommonCodeType - ICommonCodeType
	 * @return ICommonCodeType - the checkList being updated
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 * @throws CommonCodeTypeException
	 */
	public ICommonCodeType update(ICommonCodeType anICommonCodeType) throws ConcurrentUpdateException,
			CommonCodeTypeException {
		try {
			if (anICommonCodeType == null) {
				throw new CommonCodeTypeException("ICommonCodeType is null!!!");
			}
			Long pk = new Long(anICommonCodeType.getCommonCategoryId());
			EBCommonCodeType remoteItem = getEBCommonCodeTypeHome().findByPrimaryKey(pk);
			remoteItem.setValue(anICommonCodeType);
			return remoteItem.getValue();
		}
		catch (CommonCodeTypeException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new CommonCodeTypeException("FinderException enctr at update: " + ex.toString());
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new CommonCodeTypeException("RemoteException enctr at update: " + ex.toString());
		}

	}

	/**
	 * To get the home handler for the Common Code Type Entity Bean
	 * 
	 * @return EBCommonCodeTypeHome - the home handler for the Common Code Type
	 *         entity bean
	 */
	protected EBCommonCodeTypeHome getEBCommonCodeTypeHome() {
		EBCommonCodeTypeHome ejbHome = (EBCommonCodeTypeHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_COMMON_CODE_TYPE_JNDI, EBCommonCodeTypeHome.class.getName());
		return ejbHome;
	}
}
