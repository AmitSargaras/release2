package com.integrosys.cms.app.commoncode.bus;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public interface SBCommonCodeTypeBusManager extends EJBObject {

	public ICommonCodeType createCommonCodeType(ICommonCodeType anICommonCodeType) throws CommonCodeTypeException,
			RemoteException;

	public List getCategoryListByType(int categoryCode) throws CommonCodeTypeException, RemoteException;

	public SearchResult getCategoryListByType(CommonCodeTypeSearchCriteria commonCodeTypeSearchCriteria)
			throws CommonCodeTypeException, RemoteException;

	public ICommonCodeType getCategoryById(long categoryId) throws CommonCodeTypeException, RemoteException;

	public ICommonCodeType update(ICommonCodeType anICommonCodeType) throws ConcurrentUpdateException,
			CommonCodeTypeException, RemoteException;

}
