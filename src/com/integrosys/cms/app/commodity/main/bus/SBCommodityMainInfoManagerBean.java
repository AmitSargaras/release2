/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/SBCommodityMainInfoManagerBean.java,v 1.38 2006/10/26 02:29:01 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.CommodityConstant;
import com.integrosys.cms.app.commodity.JNDIConstants;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.common.UOMWrapperFactory;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.price.CommodityPriceDAO;
import com.integrosys.cms.app.commodity.main.bus.price.EBCommodityPriceLocal;
import com.integrosys.cms.app.commodity.main.bus.price.EBCommodityPriceLocalHome;
import com.integrosys.cms.app.commodity.main.bus.price.ICommodityPrice;
import com.integrosys.cms.app.commodity.main.bus.price.OBCommodityPrice;
import com.integrosys.cms.app.commodity.main.bus.profile.EBProfileLocal;
import com.integrosys.cms.app.commodity.main.bus.profile.EBProfileLocalHome;
import com.integrosys.cms.app.commodity.main.bus.profile.IBuyer;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;
import com.integrosys.cms.app.commodity.main.bus.profile.OBProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ProfileDAO;
import com.integrosys.cms.app.commodity.main.bus.profile.ProfileSearchCriteria;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.EBSubLimitTypeLocal;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.OBSubLimitType;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.SubLimitTypeSearchCriteria;
import com.integrosys.cms.app.commodity.main.bus.titledocument.EBTitleDocumentLocal;
import com.integrosys.cms.app.commodity.main.bus.titledocument.EBTitleDocumentLocalHome;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.commodity.main.bus.titledocument.OBTitleDocument;
import com.integrosys.cms.app.commodity.main.bus.titledocument.TitleDocumentSearchCriteria;
import com.integrosys.cms.app.commodity.main.bus.uom.EBUnitofMeasureLocal;
import com.integrosys.cms.app.commodity.main.bus.uom.EBUnitofMeasureLocalHome;
import com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure;
import com.integrosys.cms.app.commodity.main.bus.uom.UnitofMeasureDAO;
import com.integrosys.cms.app.commodity.main.bus.uom.UnitofMeasureSearchCriteria;
import com.integrosys.cms.app.commodity.main.bus.warehouse.EBWarehouseLocal;
import com.integrosys.cms.app.commodity.main.bus.warehouse.EBWarehouseLocalHome;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.commodity.main.bus.warehouse.OBWarehouse;
import com.integrosys.cms.app.commodity.main.bus.warehouse.WarehouseSearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Mar 24, 2004 Time:
 * 3:14:12 PM To change this template use File | Settings | File Templates.
 */
public class SBCommodityMainInfoManagerBean extends AbstractCommodityMainInfoManager implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBCommodityMainInfoManagerBean() {
		isStaging = false;
		_titleDocumentLocalHome = null;
		_warehouseLocalHome = null;
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

	// MaiInfo
	public ICommodityMainInfo getCommodityMainInfoByTrxIDPersist(String trxID, String infoType)
			throws CommodityException {
		return null; // todo
	}

	public ICommodityMainInfo getCommodityMainInfoByIDPersist(long infoObjectID, String infoType)
			throws CommodityException {
		try {
			if (infoType.equals(ICommodityMainInfo.INFO_TYPE_TITLEDOC)) {
				EBTitleDocumentLocal eb = _getTitleDocumentLocalHome().findByPrimaryKey(new Long(infoObjectID));
				return eb.getValue();
			}
			else if (infoType.equals(ICommodityMainInfo.INFO_TYPE_WAREHOUSE)) {
				EBWarehouseLocal eb = _getWarehouseLocalHome().findByPrimaryKey(new Long(infoObjectID));
				return eb.getValue();
			}
			else if (infoType.equals(ICommodityMainInfo.INFO_TYPE_PROFILE)) {
				EBProfileLocal eb = _getProfileLocalHome().findByPrimaryKey(new Long(infoObjectID));
				return eb.getValue();
			}
			else if (infoType.equals(ICommodityMainInfo.INFO_TYPE_UOM)) {
				EBUnitofMeasureLocal eb = getUnitofMeasureLocalHome().findByPrimaryKey(new Long(infoObjectID));
				return eb.getValue();
			}
			else {
				throw new CommodityException("Info type '" + infoType + "' is invalid.");
			}
		}
		catch (FinderException fe) {
			throw new CommodityException(fe);
		}
	}

	public ICommodityMainInfo[] getCommodityMainInfosByGroupIDPersist(String groupID, String infoType)
			throws CommodityException {

		if (infoType.equals(ICommodityMainInfo.INFO_TYPE_TITLEDOC)) {

			TitleDocumentSearchCriteria searchCriteria = new TitleDocumentSearchCriteria();
			searchCriteria.setGroupID(new Long(groupID));
			DefaultLogger.debug(this, "searchCriteria : " + searchCriteria.toString());
			SearchResult result = searchTitleDocumentsPersist(searchCriteria);
			return (ITitleDocument[]) result.getResultList().toArray(new ITitleDocument[0]);

		}
		else if (infoType.equals(ICommodityMainInfo.INFO_TYPE_WAREHOUSE)) {

			WarehouseSearchCriteria searchCriteria = new WarehouseSearchCriteria();
			searchCriteria.setGroupID(new Long(groupID));
			DefaultLogger.debug(this, "searchCriteria : " + searchCriteria.toString());
			SearchResult result = searchWarehousesPersist(searchCriteria);
			return (IWarehouse[]) result.getResultList().toArray(new IWarehouse[0]);

		}
		else if (infoType.equals(ICommodityMainInfo.INFO_TYPE_PROFILE)) {

			ProfileSearchCriteria searchCriteria = new ProfileSearchCriteria();
			searchCriteria.setGroupID(new Long(groupID));
			DefaultLogger.debug(this, "searchCriteria : " + searchCriteria.toString());
			SearchResult result = searchProfilesPersist(searchCriteria);
			return (IProfile[]) result.getResultList().toArray(new IProfile[0]);

		}
		else if (infoType.equals(ICommodityMainInfo.INFO_TYPE_PRICE)) {
			if (groupID != null) {
				return getCommodityPriceByGroupID(Long.parseLong(groupID));
			}
			else {
				return null;
			}
		}
		else if (infoType.equals(ICommodityMainInfo.INFO_TYPE_UOM)) {
			UnitofMeasureSearchCriteria searchCriteria = new UnitofMeasureSearchCriteria();
			searchCriteria.setGroupID(new Long(groupID));
			SearchResult result = searchUnitofMeasure(searchCriteria);
			return (IUnitofMeasure[]) result.getResultList().toArray(new IUnitofMeasure[0]);
		}
		else if (infoType.equals(ICommodityMainInfo.INFO_TYPE_SUBLIMITTYPE)) {
			SubLimitTypeSearchCriteria searchCriteria = new SubLimitTypeSearchCriteria();
			searchCriteria.setGroupID(new Long(groupID));
			SearchResult result = searchSubLimitTypePersist(searchCriteria);
			return (ISubLimitType[]) result.getResultList().toArray(new ISubLimitType[0]);
		}
		else {
			throw new CommodityException("Info type '" + infoType + "' is invalid.");
		}
	}

	/**
	 * Helper method to get commodity prices grouped by the groupID.
	 * 
	 * @param groupID group id
	 * @return a list of commodity price
	 * @throws CommodityException on error getting the commodity price
	 */
	private ICommodityPrice[] getCommodityPriceByGroupID(long groupID) throws CommodityException {
		try {
			EBCommodityPriceLocalHome ejbHome = getCommodityPriceLocalHome();
			Iterator i = ejbHome.findByGroupID(groupID).iterator();
			ArrayList arrList = new ArrayList();

			EBProfileLocalHome profileHome = (EBProfileLocalHome) BeanController.getEJBLocalHome(
					JNDIConstants.EB_PROFILE_LOCAL_BEAN, EBProfileLocalHome.class.getName());

			while (i.hasNext()) {
				EBCommodityPriceLocal theEjb = (EBCommodityPriceLocal) i.next();
				ICommodityPrice price = theEjb.getValue();
				EBProfileLocal profileEjb = profileHome.findByPrimaryKey(new Long(price.getProfileID()));
				price.setCommodityProfile(profileEjb.getValue());
				arrList.add(price);
			}
			return (OBCommodityPrice[]) arrList.toArray(new OBCommodityPrice[0]);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException("Exception at getCommodityPriceByGroupID" + e.toString());
		}
	}

	public ICommodityMainInfo[] getAllPersist(CommodityMainInfoSearchCriteria criteria) throws CommodityException {
		ICommodityMainInfo[] returnInfoArray = null;
		Collection ebCol = new ArrayList();
		if (criteria != null) {
			String infoType = criteria.getInfoType();
			try {
				if (infoType.equals(ICommodityMainInfo.INFO_TYPE_TITLEDOC)) {

					List results = new ArrayList();
					if ((criteria.getSearchBy() != null)
							&& criteria.getSearchBy().equals(CommodityMainInfoSearchCriteria.SEARCH_BY_ID)
							&& (criteria.getInfoID() != null)) {

						results.add(_getTitleDocumentLocalHome().findByPrimaryKey(criteria.getInfoID()).getValue());
						returnInfoArray = (ITitleDocument[]) results.toArray(new ITitleDocument[results.size()]);

					}
					else if ((criteria.getGroupID() != null)
							&& (criteria.getGroupID().longValue() != ICMSConstant.LONG_INVALID_VALUE)) {

						boolean isDeletedIncluded = criteria.isIncludeDeleted();
						results = getTitleDocType(criteria.getGroupID(), isDeletedIncluded);
						returnInfoArray = (ITitleDocument[]) results.toArray(new ITitleDocument[results.size()]);
					}
					else {
						boolean isDeletedIncluded = criteria.isIncludeDeleted();
						String documentType = (criteria instanceof TitleDocumentSearchCriteria) ? ((TitleDocumentSearchCriteria) criteria)
								.getType()
								: null;

						ebCol = (documentType == null) ? _getTitleDocumentLocalHome().findAll()
								: _getTitleDocumentLocalHome().findAllByDocType(documentType);

						if (ebCol != null) {
							Iterator i = ebCol.iterator();
							ArrayList aList = new ArrayList();
							while (i.hasNext()) {
								EBTitleDocumentLocal theEjb = (EBTitleDocumentLocal) i.next();
								ITitleDocument td = theEjb.getValue();
								if (!isDeletedIncluded && (td.getStatus() != null)
										&& td.getStatus().equals(ICMSConstant.STATE_DELETED)) {
									continue;
								}
								aList.add(td);
							}
							returnInfoArray = (ITitleDocument[]) aList.toArray(new ITitleDocument[0]);
						}
					}
				}
				else if (infoType.equals(ICommodityMainInfo.INFO_TYPE_WAREHOUSE)) {

					List results = new ArrayList();
					if ((criteria.getSearchBy() != null)
							&& criteria.getSearchBy().equals(CommodityMainInfoSearchCriteria.SEARCH_BY_ID)
							&& (criteria.getInfoID() != null)) {

						results.add(_getWarehouseLocalHome().findByPrimaryKey(criteria.getInfoID()).getValue());
						returnInfoArray = (IWarehouse[]) results.toArray(new IWarehouse[results.size()]);
					}
					else if ((criteria.getGroupID() != null)
							&& (criteria.getGroupID().longValue() != ICMSConstant.LONG_INVALID_VALUE)) {

						boolean isDeletedIncluded = criteria.isIncludeDeleted();
						results = getWarehouse(criteria.getGroupID(), isDeletedIncluded);
						returnInfoArray = (IWarehouse[]) results.toArray(new IWarehouse[results.size()]);
					}
					else {
						ebCol = _getWarehouseLocalHome().findAll();

						if (ebCol != null) {
							Iterator i = ebCol.iterator();
							ArrayList alist = new ArrayList();
							while (i.hasNext()) {
								EBWarehouseLocal theEjb = (EBWarehouseLocal) i.next();
								IWarehouse whouse = theEjb.getValue();
								if ((whouse.getStatus() != null)
										&& whouse.getStatus().equals(ICMSConstant.STATE_DELETED)) {
									continue;
								}
								alist.add(whouse);
							}
							returnInfoArray = (IWarehouse[]) alist.toArray(new IWarehouse[0]);
						}
					}
				}
				else if (infoType.equals(ICommodityMainInfo.INFO_TYPE_PROFILE)) {

					if ((criteria.getSearchBy() != null)
							&& criteria.getSearchBy().equals(CommodityMainInfoSearchCriteria.SEARCH_BY_ID)
							&& (criteria.getInfoID() != null)) {
						try {
							ebCol.add(_getProfileLocalHome().findByPrimaryKey(criteria.getInfoID()));
						}
						catch (FinderException e) {
							// do nothing. the collection is empty
						}
					}
					else {
						if (criteria.getGroupID() != null) {
							ebCol = _getProfileLocalHome().findByGroupID(criteria.getGroupID());
						}
						else if (criteria.isIncludeDeleted()) {
							ebCol = _getProfileLocalHome().findAll();
						}
						else {
							ebCol = _getProfileLocalHome().findAllNotDeleted();
						}
					}

					if ((ebCol != null) && (ebCol.size() > 0)) {

						EBProfileLocal[] ebArray = new EBProfileLocal[ebCol.size()];

						ebArray = (EBProfileLocal[]) ebCol.toArray(ebArray);

						IProfile[] infoArray = new OBProfile[ebArray.length];
						EBCommodityPriceLocalHome ejbHome = getCommodityPriceLocalHome();

						for (int i = 0; i < ebArray.length; i++) {
							infoArray[i] = ebArray[i].getValue();
							// get the market price and date for the profile
							Iterator iterator = ejbHome.findByProfileID(infoArray[i].getProfileID()).iterator();
							if (iterator.hasNext()) { // supposed to be a
								// Collection of 1 price
								// only.
								EBCommodityPriceLocal theEjb = (EBCommodityPriceLocal) iterator.next();
								ICommodityPrice price = theEjb.getValue();
								infoArray[i].setUnitPrice(price.getClosePrice());
								infoArray[i].setUnitPriceDate(price.getCloseUpdateDate());
							}
						}

						returnInfoArray = infoArray;

					}

				}
				else if (infoType.equals(ICommodityMainInfo.INFO_TYPE_SUBLIMITTYPE)) {
					returnInfoArray = getSubLimitTypes(criteria);
				}
				else {
					throw new CommodityException("Info type '" + infoType + "' is invalid.");
				}

			}
			catch (FinderException fe) {
				throw new CommodityException(fe);
			}

		}
		return returnInfoArray;
	}

	public SearchResult searchCommodityMainInfosPersist(CommodityMainInfoSearchCriteria criteria)
			throws CommodityException {

		CommodityMainInfoSearchResult result = null;
		if (criteria != null) {

			result = new CommodityMainInfoSearchResult();

			if (criteria instanceof TitleDocumentSearchCriteria) {
				return searchTitleDocumentsPersist((TitleDocumentSearchCriteria) criteria);
			}
			else if (criteria instanceof WarehouseSearchCriteria) {
				return searchWarehousesPersist((WarehouseSearchCriteria) criteria);
			}
			else if (criteria instanceof ProfileSearchCriteria) {
				return searchProfilesPersist((ProfileSearchCriteria) criteria);
			}
			else if (criteria instanceof UnitofMeasureSearchCriteria) {
				return searchUnitofMeasure((UnitofMeasureSearchCriteria) criteria);
			}
			else if (criteria instanceof SubLimitTypeSearchCriteria) {
				return searchSubLimitTypePersist((SubLimitTypeSearchCriteria) criteria);
			}
			else {
				throw new CommodityException("Search criteria is invalid.");
			}
		}

		return result;
	}

	/**
	 * Helper method to ask EB to search for title doccument type that fits into
	 * the requirements specified in the search criteria. If matching criteria
	 * is found, all title documents will be returned.
	 * 
	 * @param searchCriteria
	 * @return
	 * @throws CommodityException
	 */
	protected SearchResult searchTitleDocumentsPersist(TitleDocumentSearchCriteria searchCriteria)
			throws CommodityException {
		try {
			DefaultLogger.debug(this, "searchCommodityMainInfosPersist : TitleDocumentSearchCriteria ");

			List results = null;
			if (searchCriteria.getGroupID() != null) {
				// get title doc types in a group
				results = (searchCriteria.getGroupID().longValue() == ICMSConstant.LONG_INVALID_VALUE) ? new ArrayList()
						: getTitleDocType(searchCriteria.getGroupID(), false);
			}
			else {
				// get all title doc types
				results = getTitleDocType(false);
			}

			CommodityMainInfoSearchResult result = new CommodityMainInfoSearchResult();
			result.setResultList(results);

			return result;
		}
		catch (FinderException e) {
			throw new CommodityException(e);
		}
	}

	/**
	 * Helper method to ask EB to search for warehouses that fits into the
	 * requirements specified in the search criteria.
	 * 
	 * @param searchCriteria
	 * @return SearchResult
	 * @throws CommodityException on encountering error when retrieving the set
	 *         of uom
	 */
	protected SearchResult searchWarehousesPersist(WarehouseSearchCriteria searchCriteria) throws CommodityException {
		try {
			DefaultLogger.debug(this, "searchCommodityMainInfosPersist : WarehouseSearchCriteria ");

			List results = null;
			if (searchCriteria.getGroupID() != null) {
				results = (searchCriteria.getGroupID().longValue() == ICMSConstant.LONG_INVALID_VALUE) ? new ArrayList()
						: getWarehouse(searchCriteria.getGroupID(), false);
			}
			else if (searchCriteria.getCountryCode() != null) {
				String countryCode = searchCriteria.getCountryCode();
				long groupID = CommodityMainInfoManagerFactory.getManager().getWarehouseGroupIDByCountryCode(
						countryCode);
				results = (groupID == ICMSConstant.LONG_INVALID_VALUE) ? new ArrayList() : getWarehouse(new Long(
						groupID), false);
			}

			CommodityMainInfoSearchResult result = new CommodityMainInfoSearchResult();
			result.setResultList(results);

			return result;
		}
		catch (FinderException e) {
			throw new CommodityException(e);
		}
	}

	// Begin - SubLimitType
	private ISubLimitType[] getSubLimitTypes(CommodityMainInfoSearchCriteria criteria) throws CommodityException,
			FinderException {
		List results = null;
		if (CommodityMainInfoSearchCriteria.SEARCH_BY_ID.equals(criteria.getSearchBy())
				&& (criteria.getInfoID() != null)) {
			results.add(_getSubLimitTypeLocalHome().findByPrimaryKey(criteria.getInfoID()).getValue());
		}
		else if ((criteria.getGroupID() != null)
				&& (criteria.getGroupID().longValue() != ICMSConstant.LONG_INVALID_VALUE)) {
			boolean isDeletedIncluded = criteria.isIncludeDeleted();
			results = getSubLimitType(criteria.getGroupID(), isDeletedIncluded);
		}
		else {
			DefaultLogger.debug(this, "Find all sub limit types.");
			Collection ebSLT = _getSubLimitTypeLocalHome().findAll();
			if (ebSLT != null) {
				Iterator i = ebSLT.iterator();
				ArrayList alist = new ArrayList();
				while (i.hasNext()) {
					EBSubLimitTypeLocal theEjb = (EBSubLimitTypeLocal) i.next();
					ISubLimitType slt = theEjb.getValue();
					if ((slt.getStatus() != null) && slt.getStatus().equals(ICMSConstant.STATE_DELETED)) {
						continue;
					}
					alist.add(slt);
				}
				results = alist;
			}
		}
		if (results == null) {
			results = new ArrayList();
		}
		ISubLimitType[] sltArray = (ISubLimitType[]) results.toArray(new ISubLimitType[results.size()]);
		DefaultLogger.debug(this, "Num of sub limit type : " + (sltArray == null ? 0 : sltArray.length));
		return sltArray;
	}

	private SearchResult searchSubLimitTypePersist(SubLimitTypeSearchCriteria searchCriteria) throws CommodityException {
		try {
			DefaultLogger.debug(this, " - searchSubLimitTypePersist() - Begin.");
			List results = null;
			Long groupId = searchCriteria.getGroupID();
			DefaultLogger.debug(this, "GroupId: " + (groupId == null ? "null" : groupId.toString()));
			if (groupId == null) {
				// get all sub limit type
				results = getSubLimitType(false);
			}
			else if (ICMSConstant.LONG_INVALID_VALUE == groupId.longValue()) {
				results = new ArrayList();
			}
			else {
				results = getSubLimitType(groupId, false);
			}
			CommodityMainInfoSearchResult result = new CommodityMainInfoSearchResult();
			result.setResultList(results);
			DefaultLogger.debug(this, "Num of Record : " + (results == null ? 0 : results.size()));
			return result;
		}
		catch (FinderException e) {
			throw new CommodityException(e);
		}
		finally {
			DefaultLogger.debug(this, " - searchSubLimitTypePersist() - End.");
		}
	}

	private List getSubLimitType(boolean isDeletedIncluded) throws FinderException, CommodityException {
		Collection typesList = _getSubLimitTypeLocalHome().findAll();
		return getSubLimitType(typesList, isDeletedIncluded);
	}

	private List getSubLimitType(Long groupID, boolean isDeletedIncluded) throws FinderException, CommodityException {
		Collection typesList = _getSubLimitTypeLocalHome().findByGroupID(groupID);
		return getSubLimitType(typesList, isDeletedIncluded);
	}

	private List getSubLimitType(Collection ebSubLimitTypeLocalList, boolean isDeletedIncluded) throws FinderException,
			CommodityException {
		List results = new ArrayList();
		if ((ebSubLimitTypeLocalList != null) && (ebSubLimitTypeLocalList.size() > 0)) {
			for (Iterator iterator = ebSubLimitTypeLocalList.iterator(); iterator.hasNext();) {
				ISubLimitType slt = ((EBSubLimitTypeLocal) iterator.next()).getValue();
				if (isDeletedIncluded || !ICMSConstant.STATE_DELETED.equals(slt.getStatus())) {
					results.add(slt);
				}
			}
		}
		return results;
	}

	private ISubLimitType[] createInfoPersist(ISubLimitType[] sltArray) throws CommodityException {
		try {
			if (sltArray == null) {
				return null;
			}
			ISubLimitType[] returnArray = new OBSubLimitType[sltArray.length];
			for (int i = 0; i < sltArray.length; i++) {
				returnArray[i] = (OBSubLimitType) createInfoPersist(sltArray[i]);
			}
			return returnArray;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught!", e);
			throw new CommodityException(e);
		}
	}

	private ISubLimitType[] updateInfoPersist(ISubLimitType[] sltArray) throws CommodityException {
		DefaultLogger.debug(this, " updateInfoPersist Begin.");
		try {
			if ((sltArray == null) || (sltArray.length == 0)) {
				DefaultLogger.debug(this, "updateInfoPersist - sltArray is empty");
				Collection existingList = _getSubLimitTypeLocalHome().findAll();
				if ((existingList != null) && !existingList.isEmpty()) {
					removeSubLimitType(existingList);
				}
				return new ISubLimitType[0];
			}
			long groupID = sltArray[0].getGroupID();
			DefaultLogger.debug(this, "GroupID : " + groupID);
			Collection existingList = _getSubLimitTypeLocalHome().findByGroupID(new Long(groupID));

			if ((existingList == null) || existingList.isEmpty()) {
				ArrayList updatedList = new ArrayList();
				addSubLimitType(Arrays.asList(sltArray), updatedList);
				return (ISubLimitType[]) updatedList.toArray(new ISubLimitType[updatedList.size()]);
			}

			HashMap existingListMap = new HashMap(existingList.size());
			Iterator existingListIterator = existingList.iterator();
			while (existingListIterator.hasNext()) {
				EBSubLimitTypeLocal subLimitTypeLocal = (EBSubLimitTypeLocal) existingListIterator.next();
				ISubLimitType subLimitType = subLimitTypeLocal.getValue();
				if (!ICMSConstant.STATE_DELETED.equals(subLimitType.getStatus())) {
					existingListMap.put(new Long(subLimitType.getCommonRef()), subLimitTypeLocal);
				}
			}

			ArrayList newList = new ArrayList();
			ArrayList updatedList = new ArrayList();
			for (int i = 0; i < sltArray.length; i++) {
				Long commonRefID = new Long(sltArray[i].getCommonRef());
				EBSubLimitTypeLocal theExistingEjb = (EBSubLimitTypeLocal) existingListMap.get(commonRefID);
				if (theExistingEjb != null) {
					theExistingEjb.setValue(sltArray[i]);
					existingListMap.remove(commonRefID);
					updatedList.add(sltArray[i]);
				}
				else {
					newList.add(sltArray[i]);
				}
			}
			addSubLimitType(newList, updatedList);
			EBSubLimitTypeLocal[] deletedList = (EBSubLimitTypeLocal[]) existingListMap.values().toArray(
					new EBSubLimitTypeLocal[0]);
			removeSubLimitType(Arrays.asList(deletedList));
			return (ISubLimitType[]) updatedList.toArray(new ISubLimitType[updatedList.size()]);
		}
		catch (Exception e) {
			throw new CommodityException(e);
		}
		finally {
			DefaultLogger.debug(this, " updateInfoPersist End.");
		}
	}

	private ISubLimitType createInfoPersist(ISubLimitType newSLT) throws Exception {
		ISubLimitType obSLT = (ISubLimitType) AccessorUtil.deepClone(newSLT);
		long newPK = _generateSubLimitTypePK();
		((OBSubLimitType) obSLT).setSubLimitTypeID(newPK);
		obSLT.setStatus(ICMSConstant.STATE_ACTIVE);
		if (isStaging && (obSLT.getCommonRef() == ICMSConstant.LONG_INVALID_VALUE)) {
			((OBSubLimitType) obSLT).setCommonRef(newPK);
		}
		EBSubLimitTypeLocal theEB = _getSubLimitTypeLocalHome().create(obSLT);
		return theEB.getValue();
	}

	protected long _generateSubLimitTypePK() throws CommodityException {
		return generatePK(CommodityConstant.SEQUENCE_SLT_SEQ);
	}

	private void addSubLimitType(Collection newTypes, Collection resultsList) throws CreateException,
			CommodityException, Exception {
		Iterator i = newTypes.iterator();
		while (i.hasNext()) {
			ISubLimitType slt = (ISubLimitType) i.next();
			resultsList.add(createInfoPersist(slt));
		}
	}

	private void removeSubLimitType(Collection types) throws CommodityException, ConcurrentUpdateException,
			VersionMismatchException {
		Iterator i = types.iterator();
		while (i.hasNext()) {
			EBSubLimitTypeLocal theEjb = (EBSubLimitTypeLocal) i.next();
			removeSubLimitType(theEjb);
		}
	}

	private void removeSubLimitType(EBSubLimitTypeLocal anEjb) throws CommodityException, VersionMismatchException,
			ConcurrentUpdateException {
		ISubLimitType value = anEjb.getValue();
		value.setStatus(ICMSConstant.STATE_DELETED);
		anEjb.setValue(value);
	}

	// End - SubLimitType
	/**
	 * Helper method to ask EB to search for profile that fits into the
	 * requirements specified in the search criteria.
	 * 
	 * @param searchCriteria
	 * @return SearchResult
	 * @throws CommodityException on encountering error when retrieving the set
	 *         of uom
	 */
	protected SearchResult searchProfilesPersist(ProfileSearchCriteria searchCriteria) throws CommodityException {
		try {
			DefaultLogger.debug(this, "searchCommodityMainInfosPersist : ProfileSearchCriteria ");

			List results = null;
			if (searchCriteria.getGroupID() != null) {
				results = (searchCriteria.getGroupID().longValue() == ICMSConstant.LONG_INVALID_VALUE) ? new ArrayList()
						: getProfile(searchCriteria.getGroupID(), false);
			}
			else {
				// get all profiles
				// results = getProfile(false);
				DefaultLogger.debug(this, " Search Profile by Criteria.");
				results = new ProfileDAO().searchProfile(searchCriteria);
			}

			CommodityMainInfoSearchResult result = new CommodityMainInfoSearchResult();
			result.setResultList(results);

			return result;
		}
		catch (FinderException e) {
			throw new CommodityException(e);
		}
	}

	/**
	 * Helper method to get all ITitleDocument.
	 * 
	 * @param isDeletedIncluded
	 * @return
	 * @throws FinderException
	 * @throws CommodityException
	 */
	private List getTitleDocType(boolean isDeletedIncluded) throws FinderException, CommodityException {
		Collection typesList = _getTitleDocumentLocalHome().findAll();
		return getTitleDocType(typesList, isDeletedIncluded);
	}

	/**
	 * Helper method to get list of ITitleDocument given a group ID.
	 * 
	 * @param groupID
	 * @param isDeletedIncluded
	 * @return
	 * @throws FinderException
	 * @throws CommodityException
	 */
	private List getTitleDocType(Long groupID, boolean isDeletedIncluded) throws FinderException, CommodityException {
		Collection typesList = _getTitleDocumentLocalHome().findByGroupID(groupID);
		return getTitleDocType(typesList, isDeletedIncluded);
	}

	/**
	 * Helper method to get list of ITitleDocument given a collection of
	 * EBTitleDocumentLocal.
	 * 
	 * @param eBTitleDocLocalList
	 * @param isDeletedIncluded
	 * @return
	 * @throws FinderException
	 * @throws CommodityException
	 */
	private List getTitleDocType(Collection eBTitleDocLocalList, boolean isDeletedIncluded) throws FinderException,
			CommodityException {
		List results = new ArrayList();
		if ((eBTitleDocLocalList != null) && (eBTitleDocLocalList.size() > 0)) {
			for (Iterator iterator = eBTitleDocLocalList.iterator(); iterator.hasNext();) {
				ITitleDocument type = ((EBTitleDocumentLocal) iterator.next()).getValue();
				if (isDeletedIncluded || !ICMSConstant.STATE_DELETED.equals(type.getStatus())) {
					results.add(type);
				}
			}
		}
		return results;
	}

	/**
	 * Helper method to get list of IWarehouse given a group ID.
	 * 
	 * @param groupID
	 * @param isDeletedIncluded
	 * @return
	 * @throws FinderException
	 * @throws CommodityException
	 */
	private List getWarehouse(Long groupID, boolean isDeletedIncluded) throws FinderException, CommodityException {
		List results = new ArrayList();
		Collection warehouseList = _getWarehouseLocalHome().findByGroupID(groupID);
		if ((warehouseList != null) && (warehouseList.size() > 0)) {
			for (Iterator iterator = warehouseList.iterator(); iterator.hasNext();) {
				IWarehouse warehouse = ((EBWarehouseLocal) iterator.next()).getValue();
				if (isDeletedIncluded || !ICMSConstant.STATE_DELETED.equals(warehouse.getStatus())) {
					results.add(warehouse);
				}
			}
		}
		return results;
	}

	/**
	 * Helper method to get all IProfile.
	 * 
	 * @param isDeletedIncluded
	 * @return
	 * @throws FinderException
	 * @throws CommodityException
	 */
	private List getProfile(boolean isDeletedIncluded) throws FinderException, CommodityException {
		Collection typesList = _getProfileLocalHome().findAll();
		return getProfile(typesList, isDeletedIncluded);
	}

	/**
	 * Helper method to get list of IProfile given a group ID.
	 * 
	 * @param groupID
	 * @param isDeletedIncluded
	 * @return
	 * @throws FinderException
	 * @throws CommodityException
	 */
	private List getProfile(Long groupID, boolean isDeletedIncluded) throws FinderException, CommodityException {
		Collection typesList = _getProfileLocalHome().findByGroupID(groupID);
		return getProfile(typesList, isDeletedIncluded);
	}

	/**
	 * Helper method to get list of IProfile given a group ID.
	 * 
	 * @param eBProfileLocalList
	 * @param isDeletedIncluded
	 * @return
	 * @throws FinderException
	 * @throws CommodityException
	 */
	private List getProfile(Collection eBProfileLocalList, boolean isDeletedIncluded) throws FinderException,
			CommodityException {
		List results = new ArrayList();
		if ((eBProfileLocalList != null) && (eBProfileLocalList.size() > 0)) {
			for (Iterator iterator = eBProfileLocalList.iterator(); iterator.hasNext();) {
				IProfile type = ((EBProfileLocal) iterator.next()).getValue();
				if (isDeletedIncluded || !ICMSConstant.STATE_DELETED.equals(type.getStatus())) {
					results.add(type);
				}
			}
		}
		return results;
	}

	/**
	 * Helper method to get list of IUnitofMeasure given a group ID.
	 * 
	 * @param groupID
	 * @param isDeletedIncluded
	 * @return
	 * @throws FinderException
	 * @throws CommodityException
	 */
	private List getUnitofMeasure(long groupID, boolean isDeletedIncluded) throws FinderException, CommodityException {
		List results = new ArrayList();
		Collection uomList = getUnitofMeasureLocalHome().findByGroupID(groupID);
		if ((uomList != null) && (uomList.size() > 0)) {
			EBProfileLocalHome profileHome = (isStaging) ? super._getProfileLocalHome() : _getProfileLocalHome();
			for (Iterator iterator = uomList.iterator(); iterator.hasNext();) {
				IUnitofMeasure uom = ((EBUnitofMeasureLocal) iterator.next()).getValue();
				if (isDeletedIncluded || !ICMSConstant.STATE_DELETED.equals(uom.getStatus())) {
					EBProfileLocal profileEjb = profileHome.findByPrimaryKey(new Long(uom.getProfileID()));
					uom.setCommodityProfile(profileEjb.getValue());
					results.add(uom);
				}
			}
		}
		return results;
	}

	/**
	 * Gets all unit of measure that can be used for a commodity identified by
	 * the given commodity profile id. Includes the common unit of measure (e.g.
	 * kg, lbs) and the unit of measure set up for the given commodity profile
	 * (e.g. a small bag).
	 * 
	 * @param profileID - id of a commodity profile
	 * @return a list of unit of measure wrapper that can be used for the
	 *         commodity
	 * @return a zero-length list if there are no unit of measure found
	 * @throws CommodityException on error getting the set of unit of measure
	 */
	public UOMWrapper[] getUnitofMeasureByProfileID(long profileID) throws CommodityException {
		ArrayList results = null;
		try {

			DefaultLogger.debug(this, "profileID : " + profileID);

			// TODO : find out if any sorting order required

			// get the user-specified commodity uom for this profile
			if (profileID != ICMSConstant.LONG_INVALID_VALUE) {
				EBUnitofMeasureLocalHome uomHome = getUnitofMeasureLocalHome();
				Iterator i = uomHome.findByProfileID(profileID).iterator();
				results = new ArrayList();
				while (i.hasNext()) {
					EBUnitofMeasureLocal uomEjb = (EBUnitofMeasureLocal) i.next();
					IUnitofMeasure uom = uomEjb.getValue();
					UOMWrapper wrapper = UOMWrapperFactory.getInstance().getUOM(uom);
					results.add(wrapper);
				}
			}

			// Append common uom
			Collection commonUOMs = UOMWrapperFactory.getInstance().getCommonUOM();
			results.addAll(commonUOMs);

			return (UOMWrapper[]) results.toArray(new UOMWrapper[0]);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException(e);
		}
	}

	/**
	 * Gets unit of measure given the unit of measure id.
	 * 
	 * @return unit of measure wrapper
	 * @throws CommodityException on error getting the unit of measure.
	 */
	public UOMWrapper getUnitofMeasureByID(long uomID) throws CommodityException {
		IUnitofMeasure uom = (IUnitofMeasure) getCommodityMainInfoByIDPersist(uomID, ICommodityMainInfo.INFO_TYPE_UOM);
		if (uom == null) {
			return null;
		}
		return UOMWrapperFactory.getInstance().getUOM(uom);
	}

	/**
	 * Helper method to ask EB to search for uoms that fits into the
	 * requirements specified in the search criteria.
	 * 
	 * @param searchCriteria
	 * @return SearchResult
	 * @throws CommodityException on encountering error when retrieving the set
	 *         of uom
	 */
	private SearchResult searchUnitofMeasure(UnitofMeasureSearchCriteria searchCriteria) throws CommodityException {

		List results = null;
		try {
			if (searchCriteria.getGroupID() != null) {

				long groupID = searchCriteria.getGroupID().longValue();
				results = getUnitofMeasure(groupID, false);

			}
			else if (((searchCriteria.getCategoryCode() != null) && (searchCriteria.getCategoryCode().trim().length() > 0))
					&& ((searchCriteria.getProductTypeCode() != null) && (searchCriteria.getProductTypeCode().trim()
							.length() > 0))) {

				String catCode = searchCriteria.getCategoryCode();
				String pdtTypeCode = searchCriteria.getProductTypeCode();
				UnitofMeasureDAO dao = new UnitofMeasureDAO();
				IUnitofMeasure[] uoms = dao.getUnitofMeasureProfile(catCode, pdtTypeCode);
				if (uoms != null) {
					EBUnitofMeasureLocalHome ejbHome = getUnitofMeasureLocalHome();
					results = new ArrayList();
					for (int i = 0; i < uoms.length; i++) {
						if (uoms[i].getUnitofMeasureID() != ICMSConstant.LONG_INVALID_VALUE) {
							EBUnitofMeasureLocal theEjb = ejbHome.findByPrimaryKey(new Long(uoms[i]
									.getUnitofMeasureID()));
							IProfile uomprofile = uoms[i].getCommodityProfile();
							String uomProfileStatus = uoms[i].getStatus();

							uoms[i] = theEjb.getValue();
							uoms[i].setCommodityProfile(uomprofile);
							uoms[i].setStatus(uomProfileStatus); // set
							// profile
							// status to
							// price
							// status
							// for
							// persisting
							// the
							// status to
							// price
							// later on.

							results.add(uoms[i]);
						}
					}
				}
			}
			else {
				// todo : consider if want to get all uom if no criteria
				// specified
				throw new CommodityException("Search criteria for uom is invalid : " + searchCriteria);
			}

			CommodityMainInfoSearchResult result = new CommodityMainInfoSearchResult();
			result.setResultList(results);
			return result;

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException(e);
		}
	}

	/**
	 * Get commodity price given it category and product.
	 * 
	 * @param catCode commodity category code
	 * @param prodCode product type code
	 * @return a list of commodity profile objects
	 * @throws CommodityException on error getting the commodity price
	 */
	public ICommodityPrice[] getCommodityPrice(String catCode, String prodCode, String ricType)
			throws CommodityException {
		try {
			CommodityPriceDAO dao = new CommodityPriceDAO();
			ICommodityPrice[] prices = dao.getCommodityPriceProfile(catCode, prodCode, ricType);
			if ((prices == null) || (prices.length == 0)) {
				return prices;
			}

			EBCommodityPriceLocalHome ejbHome = getCommodityPriceLocalHome();
			for (int i = 0; i < prices.length; i++) {
				if (prices[i].getCommodityPriceID() != ICMSConstant.LONG_INVALID_VALUE) {
					EBCommodityPriceLocal theEjb = ejbHome.findByPrimaryKey(new Long(prices[i].getCommodityPriceID()));
					IProfile profile = prices[i].getCommodityProfile();

					prices[i] = theEjb.getValue();
					prices[i].setCommodityProfile(profile);
					prices[i].setStatus(profile.getStatus()); // set profile
					// status to
					// price status
					// for
					// persisting
					// the status to
					// price later
					// on.
				}
			}
			return prices;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException("Exception at getCommodityPrice" + e.toString());
		}
	}

	/**
	 * Create commodity price history.
	 * 
	 * @param prices of type ICommodityPrice[]
	 * @return ICommodityPrice[]
	 * @throws CommodityException on error creating history of commodity prices
	 */
	public ICommodityPrice[] createCommodityPriceHistory(ICommodityPrice[] prices) throws CommodityException {
		try {
			CommodityPriceDAO dao = new CommodityPriceDAO();
			long feedGroupID = dao.getFeedGroupID(ICMSConstant.COMMODITY_FEED_GROUP_TYPE);
			EBCommodityPriceLocalHome ejbHome = getCommodityPriceHistoryLocalHome();

			for (int i = 0; i < prices.length; i++) {
				ICommodityPrice history = (ICommodityPrice) AccessorUtil.deepClone(prices[i]);
				history.setGroupID(feedGroupID);
				history.setStatus(ICMSConstant.COMMODITY_FEED_GROUP_TYPE);
				ejbHome.create(history);
			}
			return prices;
		}
		catch (Exception e) {
			throw new CommodityException("CreateCommodityPriceHistory Exception: " + e.toString());
		}
	}

	// TODO : this is to be phased out as well
	public ICommodityMainInfo createInfoPersist(ICommodityMainInfo infoObject) throws CommodityException {
		if (infoObject == null) {
			throw new CommodityException("Incoming object is NULL. Value must be not null.");
		}
		try {
			if (infoObject instanceof ITitleDocument) {
				return createInfoPersist((ITitleDocument) infoObject);
			}
			if (infoObject instanceof IWarehouse) {
				return createInfoPersist((IWarehouse) infoObject);
			}
			if (infoObject instanceof IProfile) {
				return createInfoPersist((IProfile) infoObject);
			}
			if (infoObject instanceof ISubLimitType) {
				return createInfoPersist((ISubLimitType) infoObject);
			}
			throw new CommodityException(" ManagerBean : createInfo() : given object type '"
					+ infoObject.getClass().getName() + "' is not valid.");

		}
		catch (CreateException e) {
			e.printStackTrace();
			throw new CommodityException(e);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommodityException(e);
		}
	}

	/**
	 * Create data for commodity maintenance.
	 * 
	 * @param infoObject of type ICommodityMainInfo[]
	 * @return a list of commodity maintenace objects
	 * @throws CommodityException on any errors encountered
	 */
	public ICommodityMainInfo[] createInfoPersist(ICommodityMainInfo[] infoObject) throws CommodityException {
		if (infoObject == null) {
			throw new CommodityException("Commodity Main Info is null!" + infoObject);
		}
		if (infoObject instanceof ITitleDocument[]) {
			return createInfoPersist((ITitleDocument[]) infoObject);
		}
		else if (infoObject instanceof IWarehouse[]) {
			return createInfoPersist((IWarehouse[]) infoObject);
		}
		else if (infoObject instanceof IProfile[]) {
			return createInfoPersist((IProfile[]) infoObject);
		}
		else if (infoObject instanceof ICommodityPrice[]) {
			return createInfoPersist((ICommodityPrice[]) infoObject);
		}
		else if (infoObject instanceof IUnitofMeasure[]) {
			return createInfoPersist((IUnitofMeasure[]) infoObject);
		}
		else if (infoObject instanceof ISubLimitType[]) {
			return createInfoPersist((ISubLimitType[]) infoObject);
		}
		else {
			throw new CommodityException("Unsupported type. No implementation available for the type : "
					+ infoObject.getClass().getName());
		}
	}

	/**
	 * Helper method to create commodity prices.
	 * 
	 * @param prices of type ICommodityPrice[]
	 * @return newly created commodity prices
	 * @throws CommodityException on error creating the commodity price
	 */
	private ICommodityPrice[] createInfoPersist(ICommodityPrice[] prices) throws CommodityException {
		try {
			if (prices == null) {
				return prices;
			}

			ICommodityPrice[] returnArray = new ICommodityPrice[prices.length];

			EBCommodityPriceLocalHome ejbHome = getCommodityPriceLocalHome();
			long groupID = ICMSConstant.LONG_INVALID_VALUE;

			for (int i = 0; i < prices.length; i++) {
				ICommodityPrice price = (ICommodityPrice) AccessorUtil.deepClone(prices[i]);
				price.setGroupID(groupID);
				EBCommodityPriceLocal theEjb = ejbHome.create(price);
				returnArray[i] = theEjb.getValue();
				groupID = returnArray[i].getGroupID();
				returnArray[i].setCommodityProfile(prices[i].getCommodityProfile());
			}
			return returnArray;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException("Exception caught at createCommodityPrice " + e.toString());
		}
	}

	/**
	 * Helper method to create unit of measures. IMPT : groupID is set in the
	 * transaction layer
	 * 
	 * @param uoms
	 * @return
	 * @throws CommodityException
	 */
	private IUnitofMeasure[] createInfoPersist(IUnitofMeasure[] uoms) throws CommodityException {
		try {
			IUnitofMeasure[] results = new IUnitofMeasure[uoms.length];
			for (int i = 0; i < uoms.length; i++) {
				results[i] = createInfoPersist(uoms[i]);
			}
			return results;
		}
		catch (Exception e) {
			throw new CommodityException("Exception caught while creating unit of measure", e);
		}
	}

	/**
	 * Helper method to create one unit of measure.
	 * 
	 * @param newUOM
	 * @return
	 * @throws Exception
	 */
	private IUnitofMeasure createInfoPersist(IUnitofMeasure newUOM) throws Exception {
		EBUnitofMeasureLocalHome ejbHome = getUnitofMeasureLocalHome();
		EBUnitofMeasureLocal theEjb = ejbHome.create(newUOM);
		return theEjb.getValue();
	}

	/**
	 * Helper method to create title document types for commodity.
	 * 
	 * @param titleDocuments of type ITitleDocument[]
	 * @return a list of title document types
	 * @throws CommodityException on any errors encountered
	 */
	private ITitleDocument[] createInfoPersist(ITitleDocument[] titleDocuments) throws CommodityException {
		try {
			ITitleDocument[] results = new ITitleDocument[titleDocuments.length];
			for (int i = 0; i < titleDocuments.length; i++) {
				results[i] = createInfoPersist(titleDocuments[i]);
			}
			return results;
		}
		catch (Exception e) {
			throw new CommodityException("Exception caught while creating title document type", e);
		}
	}

	/**
	 * Helper method to create one profile.
	 * 
	 * @param newType
	 * @return
	 * @throws Exception
	 */
	private ITitleDocument createInfoPersist(ITitleDocument newType) throws Exception {
		EBTitleDocumentLocalHome ejbHome = _getTitleDocumentLocalHome();
		ITitleDocument type = (ITitleDocument) AccessorUtil.deepClone(newType);
		long newPK = _generateWarehousePK();
		((OBTitleDocument) type).setTitleDocumentID(newPK);
		type.setStatus(ICMSConstant.STATE_ACTIVE);
		if (isStaging && (type.getCommonRef() == ICMSConstant.LONG_INVALID_VALUE)) {
			((OBTitleDocument) type).setCommonRef(newPK);
		}
		return ejbHome.create(type).getValue();
	}

	/**
	 * Helper method to create all the warehouse specified in the list.
	 * 
	 * @param warehouses
	 * @return
	 * @throws CommodityException
	 */
	private IWarehouse[] createInfoPersist(IWarehouse[] warehouses) throws CommodityException {
		try {

			if (warehouses == null) {
				return null;
			}

			IWarehouse[] returnArray = new OBWarehouse[warehouses.length];
			for (int i = 0; i < warehouses.length; i++) {
				returnArray[i] = (OBWarehouse) createInfoPersist(warehouses[i]);
			}
			return returnArray;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught!", e);
			throw new CommodityException(e);
		}
	}

	/**
	 * Helper method to create one warehouse.
	 * 
	 * @param newWarehouse
	 * @return
	 * @throws Exception
	 */
	private IWarehouse createInfoPersist(IWarehouse newWarehouse) throws Exception {
		EBWarehouseLocalHome ejbHome = _getWarehouseLocalHome();
		IWarehouse warehouse = (IWarehouse) AccessorUtil.deepClone(newWarehouse);
		long newPK = _generateWarehousePK();
		warehouse.setWarehouseID(newPK);
		warehouse.setStatus(ICMSConstant.STATE_ACTIVE);
		if (isStaging && (warehouse.getCommonRef() == ICMSConstant.LONG_INVALID_VALUE)) {
			warehouse.setCommonRef(newPK);
		}
		return ejbHome.create(warehouse).getValue();
	}

	/**
	 * Helper method to create profile for commodity.
	 * 
	 * @param profiles of type IProfile
	 * @return a list of commodity profile objects
	 * @throws CommodityException on any errors encountered
	 */
	private IProfile[] createInfoPersist(IProfile[] profiles) throws CommodityException {
		try {
			if (profiles == null) {
				return null;
			}

			IProfile[] returnArray = new OBProfile[profiles.length];
			for (int i = 0; i < profiles.length; i++) {
				returnArray[i] = (OBProfile) createInfoPersist(profiles[i]);
			}
			return returnArray;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught!", e);
			throw new CommodityException(e);
		}
	}

	/**
	 * Helper method to create one profile.
	 * 
	 * @param newProfile
	 * @return
	 * @throws Exception
	 */
	private IProfile createInfoPersist(IProfile newProfile) throws Exception {
		EBProfileLocalHome ejbHome = _getProfileLocalHome();
		IProfile profile = (IProfile) AccessorUtil.deepClone(newProfile);
		long newPK = _generateProfilePK();
		((OBProfile) profile).setProfileID(newPK);
		profile.setStatus(ICMSConstant.STATE_ACTIVE);

		if (isStaging && (profile.getCommonRef() == ICMSConstant.LONG_INVALID_VALUE)) {
			((OBProfile) profile).setCommonRef(newPK);
		}

		EBProfileLocal theEB = ejbHome.create(profile);
		return theEB.getValue();
	}

	/**
	 * Helper method to update commodity prices.
	 * 
	 * @param prices a list of ICommodityPrice objects
	 * @return newly updated commodity price
	 * @throws CommodityException on error updating commodity price
	 */
	private ICommodityPrice[] updateInfoPersist(ICommodityPrice[] prices) throws CommodityException {
		try {
			if (prices == null) {
				return prices;
			}

			ICommodityPrice[] returnArray = new ICommodityPrice[prices.length];

			EBCommodityPriceLocalHome ejbHome = getCommodityPriceLocalHome();

			for (int i = 0; i < prices.length; i++) {
				if (prices[i].getCommodityPriceID() != ICMSConstant.LONG_INVALID_VALUE) {
					EBCommodityPriceLocal theEjb = ejbHome.findByPrimaryKey(new Long(prices[i].getCommodityPriceID()));
					theEjb.setValue(prices[i]);
					returnArray[i] = theEjb.getValue();
				}
				else {
					EBCommodityPriceLocal theEjb = ejbHome.create(prices[i]);
					returnArray[i] = theEjb.getValue();
				}
				returnArray[i].setCommodityProfile(prices[i].getCommodityProfile());
			}
			return returnArray;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException("Exception caught at updateInfoPersist " + e.toString());
		}
	}

	public ICommodityMainInfo[] updateInfoPersist(ICommodityMainInfo[] infoObject) throws CommodityException {
		if (infoObject == null) {
			return null;
		}
		if (infoObject instanceof ITitleDocument[]) {
			return updateTitleDocType((ITitleDocument[]) infoObject);
		}
		else if (infoObject instanceof IWarehouse[]) {
			return updateWarehouse((IWarehouse[]) infoObject);
		}
		else if (infoObject instanceof IProfile[]) {
			return updateProfile((IProfile[]) infoObject);
		}
		else if (infoObject instanceof ICommodityPrice[]) {
			return updateInfoPersist((ICommodityPrice[]) infoObject);
		}
		else if (infoObject instanceof IUnitofMeasure[]) {
			return updateUOM((IUnitofMeasure[]) infoObject);
		}
		else if (infoObject instanceof ISubLimitType[]) {
			return updateInfoPersist((ISubLimitType[]) infoObject);
		}
		else {
			throw new CommodityException("Unsupported type. No implementation available for the type : "
					+ infoObject.getClass().getName());
		}

	}

	// TODO : to be phased out eventually
	public ICommodityMainInfo deleteInfoPersist(ICommodityMainInfo infoObject) throws CommodityException {
		if (infoObject == null) {
			throw new CommodityException("Incoming object is NULL. Value must be not null.");
		}
		try {
			ICommodityMainInfo returnObject = null;

			if (infoObject instanceof ITitleDocument) {

				ITitleDocument titleDoc = (ITitleDocument) infoObject;

				EBTitleDocumentLocalHome theHome = _getTitleDocumentLocalHome();

				EBTitleDocumentLocal ejb = theHome.findByPrimaryKey(new Long(titleDoc.getTitleDocumentID()));

				returnObject = ejb.getValue();

				ejb.remove();

			}
			else if (infoObject instanceof IWarehouse) {

				IWarehouse warehouse = (IWarehouse) infoObject;

				EBWarehouseLocalHome theHome = _getWarehouseLocalHome();

				EBWarehouseLocal ejb = theHome.findByPrimaryKey(new Long(warehouse.getWarehouseID()));

				returnObject = ejb.getValue();

				ejb.remove();

			}
			else if (infoObject instanceof IProfile) {

				IProfile value = (IProfile) infoObject;

				EBProfileLocalHome theHome = _getProfileLocalHome();

				EBProfileLocal ejb = theHome.findByPrimaryKey(new Long(value.getProfileID()));

				returnObject = ejb.getValue();

				ejb.remove();

			}
			return returnObject;
		}
		catch (RemoveException e) {
			throw new CommodityException(e);
		}
		catch (FinderException e) {
			throw new CommodityException(e);
		}
	}

	public ICommodityMainInfo[] deleteInfoPersist(ICommodityMainInfo[] infoObject) throws CommodityException {
		if (infoObject == null) {
			return null;
		}
		if (infoObject instanceof ITitleDocument[]) {
			return deleteInfoPersist((ITitleDocument[]) infoObject);
		}
		else if (infoObject instanceof IWarehouse[]) {
			return deleteInfoPersist((IWarehouse[]) infoObject);
		}
		else if (infoObject instanceof IProfile[]) {
			return deleteInfoPersist((IProfile[]) infoObject);
		}
		else {
			throw new CommodityException("Unsupported type. No implementation available for the type : "
					+ infoObject.getClass().getName());
		}

	}

	private ITitleDocument[] deleteInfoPersist(ITitleDocument[] infoObject) throws CommodityException {
		OBTitleDocument[] returnArray = null;
		if (infoObject != null) {

			returnArray = new OBTitleDocument[infoObject.length];

			DefaultLogger.debug(this, "$$$ SBMgr : 1 ");
			for (int i = 0; i < infoObject.length; i++) {
				DefaultLogger.debug(this, "$$$ SBMgr : 1-" + i);
				returnArray[i] = (OBTitleDocument) deleteInfoPersist(infoObject[i]);
			}
			DefaultLogger.debug(this, "$$$ SBMgr : 2");
		}
		DefaultLogger.debug(this, "$$$ SBMgr : 3 size=" + returnArray.length);

		return returnArray;

	}

	private IWarehouse[] deleteInfoPersist(IWarehouse[] infoObject) throws CommodityException {
		OBWarehouse[] returnArray = null;
		if (infoObject != null) {

			returnArray = new OBWarehouse[infoObject.length];

			DefaultLogger.debug(this, "$$$ SBMgr : 1 ");
			for (int i = 0; i < infoObject.length; i++) {
				DefaultLogger.debug(this, "$$$ SBMgr : 1-" + i);
				OBWarehouse iCommodityMainInfo = (OBWarehouse) infoObject[i];
				returnArray[i] = (OBWarehouse) deleteInfoPersist(iCommodityMainInfo);
			}
			DefaultLogger.debug(this, "$$$ SBMgr : 2");

		}
		DefaultLogger.debug(this, "$$$ SBMgr : 3 size=" + returnArray.length);

		return returnArray;

	}

	private IProfile[] deleteInfoPersist(IProfile[] infoObject) throws CommodityException {
		OBProfile[] returnArray = null;
		if (infoObject != null) {

			returnArray = new OBProfile[infoObject.length];

			DefaultLogger.debug(this, "$$$ SBMgr : 1 ");
			for (int i = 0; i < infoObject.length; i++) {
				DefaultLogger.debug(this, "$$$ SBMgr : 1-" + i);
				OBProfile iCommodityMainInfo = (OBProfile) infoObject[i];
				returnArray[i] = (OBProfile) deleteInfoPersist(iCommodityMainInfo);
			}
			DefaultLogger.debug(this, "$$$ SBMgr : 2");

		}
		DefaultLogger.debug(this, "$$$ SBMgr : 3 size=" + returnArray.length);

		return returnArray;

	}

	protected long _generateSupplierPK() throws CommodityException {
		return generatePK(CommodityConstant.SEQUENCE_PROFILE_SUPPLIER_SEQ);
	}

	protected long _generateBuyerPK() throws CommodityException {
		return generatePK(CommodityConstant.SEQUENCE_PROFILE_BUYER_SEQ);
	}

	protected long _generateTitleDocumentPK() throws CommodityException {
		return generatePK(CommodityConstant.SEQUENCE_TITLE_DOCUMENT_SEQ);
	}

	protected long _generateWarehousePK() throws CommodityException {
		return generatePK(CommodityConstant.SEQUENCE_WAREHOUSE_SEQ);
	}

	protected long _generateProfilePK() throws CommodityException {
		return generatePK(CommodityConstant.SEQUENCE_PROFILE_SEQ);
	}

	protected long generateUnitofMeasurePK() throws CommodityException {
		return generatePK(CommodityConstant.SEQUENCE_UOM);
	}

	protected long generatePK(String sequenceName) throws CommodityException {

		SequenceManager seqmgr = new SequenceManager();
		String seq = null;
		try {
			seq = seqmgr.getSeqNum(sequenceName, true);
			return Long.parseLong(seq);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommodityException("Exception in generating Sequence '" + sequenceName
					+ "' \n The exception is : " + e);
		}
	}

	public ISupplier getSupplierByIDPersist(long supplierID) throws CommodityException {
		try {
			return _getSupplierLocalHome().findByPrimaryKey(new Long(supplierID)).getValue();
		}
		catch (FinderException e) {
			e.printStackTrace();
			throw new CommodityException(e);
		}
	}

	public IBuyer getBuyerByIDPersist(long buyerID) throws CommodityException {
		try {
			return _getBuyerLocalHome().findByPrimaryKey(new Long(buyerID)).getValue();
		}
		catch (FinderException e) {
			e.printStackTrace();
			throw new CommodityException(e);
		}
	}

	/**
	 * Updates warehouse. Handles creates, updates and soft deletion of
	 * warehouse.
	 * 
	 * @param warehouses - IWarehouse[]
	 * @throws CommodityException
	 */
	private IWarehouse[] updateWarehouse(IWarehouse[] warehouses) throws CommodityException {

		DefaultLogger.debug(this, "################################");
		DefaultLogger.debug(this, "######## updateWarehouse #########");
		for (int i = 0; i < warehouses.length; i++) {
			DefaultLogger.debug(this, "##### warehouses[" + i + "] : " + warehouses[i]);
		}
		DefaultLogger.debug(this, "################################");
		DefaultLogger.debug(this, "################################");

		ArrayList updatedList = new ArrayList();

		// TODO : should pass in ctry code as well
		// TODO : get grp ID given ctry code
		// TODO : current implementation will not work if all warehouse were
		// deleted
		// TODO : currently, UI prevents all warehouses to be deleted in a
		// country
		if ((warehouses == null) || (warehouses.length == 0)) {
			return new IWarehouse[0];
		}

		// get existing warehouses
		Collection existingList = null;
		try {
			long groupID = warehouses[0].getGroupID();
			DefaultLogger.debug(this, "######## groupID : " + groupID + " #########");
			existingList = _getWarehouseLocalHome().findByGroupID(new Long(groupID));
		}
		catch (FinderException e) {
			e.printStackTrace();
		}

		DefaultLogger.debug(this, "################################");
		DefaultLogger.debug(this, "######## updateWarehouse #########");
		Iterator it = existingList.iterator();
		int count = 0;
		while (it.hasNext()) {
			DefaultLogger.debug(this, "##### existing[" + count++ + "] : " + ((EBWarehouseLocal) it.next()).getValue());
		}
		DefaultLogger.debug(this, "################################");
		DefaultLogger.debug(this, "################################");

		try {
			if (existingList.isEmpty()) {
				// existing list is empty, add all in warehouses
				if ((warehouses != null) && (warehouses.length != 0)) {
					addWarehouse(Arrays.asList(warehouses), updatedList);
					return (updatedList.size() == 0) ? new IWarehouse[0] : (IWarehouse[]) updatedList
							.toArray(new IWarehouse[updatedList.size()]);
				}
			}
			else {
				// existing list is NOT empty
				// if warehouses is empty , remove all existing warehouses
				if ((warehouses == null) || (warehouses.length == 0)) {
					removeWarehouse(existingList);
					return (updatedList.size() == 0) ? new IWarehouse[0] : (IWarehouse[]) updatedList
							.toArray(new IWarehouse[updatedList.size()]);
				}

				// put existing list into hashmap - excludes deleted values
				HashMap existingListMap = new HashMap(existingList.size());
				Iterator existingListIterator = existingList.iterator();
				while (existingListIterator.hasNext()) {
					EBWarehouseLocal warehouseLocal = (EBWarehouseLocal) existingListIterator.next();
					IWarehouse warehouse = warehouseLocal.getValue();
					if ((warehouse.getStatus() == null) || !warehouse.getStatus().equals(ICMSConstant.STATE_DELETED)) {
						existingListMap.put(new Long(warehouse.getCommonRef()), warehouseLocal);
					}
				}

				ArrayList newList = new ArrayList();

				// compare warehouses with existing list
				for (int i = 0; i < warehouses.length; i++) {
					IWarehouse warehouse = warehouses[i];
					Long commonRefID = new Long(warehouse.getCommonRef());
					EBWarehouseLocal theExistingEjb = (EBWarehouseLocal) existingListMap.get(commonRefID);
					if (theExistingEjb != null) {
						DefaultLogger.debug(this, "##### updating : " + warehouse.getWarehouseID() + " - "
								+ warehouse.getName());
						theExistingEjb.setValue(warehouse);
						existingListMap.remove(commonRefID);
						updatedList.add(warehouse);
					}
					else {
						newList.add(warehouse);
					}
				}

				// add new warehouses
				addWarehouse(newList, updatedList);

				// remove deleted warehouses
				EBWarehouseLocal[] deletedList = (EBWarehouseLocal[]) existingListMap.values().toArray(
						new EBWarehouseLocal[0]);
				removeWarehouse(Arrays.asList(deletedList));

				return (updatedList.size() == 0) ? new IWarehouse[0] : (IWarehouse[]) updatedList
						.toArray(new IWarehouse[updatedList.size()]);

			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException("Exception caught at updateInfoPersist " + e.toString());
		}
		return new IWarehouse[0];
	}

	/**
	 * Helper method to add all warehouses specified in the collection.
	 * 
	 * @param newWarehouses
	 * @throws CreateException
	 */
	private void addWarehouse(Collection newWarehouses, Collection resultsList) throws CreateException,
			CommodityException, Exception {
		Iterator i = newWarehouses.iterator();
		while (i.hasNext()) {
			IWarehouse warehouse = (IWarehouse) i.next();
			DefaultLogger.debug(this, "##### adding : " + warehouse.getWarehouseID() + " - " + warehouse.getName());
			resultsList.add(createInfoPersist(warehouse));
		}
	}

	/**
	 * Helper method to remove all warehouses specified in the collection.
	 * 
	 * @param deposits - Collection of warehouses to be removed. Cannot be null.
	 */
	private void removeWarehouse(Collection deposits) throws CommodityException, ConcurrentUpdateException,
			VersionMismatchException {
		Iterator i = deposits.iterator();
		while (i.hasNext()) {
			EBWarehouseLocal theEjb = (EBWarehouseLocal) i.next();
			removeWarehouse(theEjb);
		}
	}

	/**
	 * Helper method to remove the said warehouse.
	 * 
	 * @param anEjb - EBWarehouseLocal
	 */
	private void removeWarehouse(EBWarehouseLocal anEjb) throws CommodityException, VersionMismatchException,
			ConcurrentUpdateException {
		IWarehouse warehouse = anEjb.getValue();
		DefaultLogger.debug(this, "##### soft deleting : " + warehouse.getWarehouseID() + " - " + warehouse.getName());
		warehouse.setStatus(ICMSConstant.STATE_DELETED);
		anEjb.setValue(warehouse);
	}

	/**
	 * Updates uoms. Handles creates, updates and soft deletion of uom.
	 * 
	 * @param uoms - IUnitofMeasure[]
	 * @throws CommodityException
	 */
	private IUnitofMeasure[] updateUOM(IUnitofMeasure[] uoms) throws CommodityException {

		DefaultLogger.debug(this, "################################");
		DefaultLogger.debug(this, "######## updateUOM #########");
		for (int i = 0; i < uoms.length; i++) {
			DefaultLogger.debug(this, "##### uoms[" + i + "] : " + uoms[i]);
		}
		DefaultLogger.debug(this, "################################");
		DefaultLogger.debug(this, "################################");

		ArrayList updatedList = new ArrayList();

		// TODO : should pass in category and product type code as well
		// TODO : get grp ID given category and product type code
		// TODO : current implementation will not work if all uoms were deleted
		// TODO : currently, UI prevents all uoms to be deleted in a country
		if ((uoms == null) || (uoms.length == 0)) {
			return new IUnitofMeasure[0];
		}

		Collection existingList = null;
		try {
			long groupID = uoms[0].getGroupID();
			DefaultLogger.debug(this, "######## groupID : " + groupID + " #########");
			existingList = getUnitofMeasureLocalHome().findByGroupID(groupID);
		}
		catch (FinderException e) {
			e.printStackTrace();
		}

		DefaultLogger.debug(this, "################################");
		DefaultLogger.debug(this, "######## updateUOM #########");
		Iterator it = existingList.iterator();
		int count = 0;
		while (it.hasNext()) {
			DefaultLogger.debug(this, "##### existing[" + count++ + "] : "
					+ ((EBUnitofMeasureLocal) it.next()).getValue());
		}
		DefaultLogger.debug(this, "################################");
		DefaultLogger.debug(this, "################################");

		try {
			if (existingList.isEmpty()) {
				// existing list is empty, add all in uoms
				if ((uoms != null) && (uoms.length != 0)) {
					addUOM(Arrays.asList(uoms), updatedList);
					return (updatedList.size() == 0) ? new IUnitofMeasure[0] : (IUnitofMeasure[]) updatedList
							.toArray(new IUnitofMeasure[updatedList.size()]);
				}
			}
			else {
				// existing list is NOT empty
				// if uoms is empty , remove all existing uo
				if ((uoms == null) || (uoms.length == 0)) {
					removeUOM(existingList);
					return (updatedList.size() == 0) ? new IUnitofMeasure[0] : (IUnitofMeasure[]) updatedList
							.toArray(new IUnitofMeasure[updatedList.size()]);
				}

				// put existing list into hashmap - excludes deleted values
				HashMap existingListMap = new HashMap(existingList.size());
				Iterator existingListIterator = existingList.iterator();
				while (existingListIterator.hasNext()) {
					EBUnitofMeasureLocal uomLocal = (EBUnitofMeasureLocal) existingListIterator.next();
					IUnitofMeasure uom = uomLocal.getValue();
					if ((uom.getStatus() == null) || !uom.getStatus().equals(ICMSConstant.STATE_DELETED)) {
						existingListMap.put(new Long(uom.getCommonReferenceID()), uomLocal);
					}
				}

				ArrayList newList = new ArrayList();

				// compare uoms with existing list
				for (int i = 0; i < uoms.length; i++) {
					IUnitofMeasure uom = uoms[i];
					Long commonRefID = new Long(uom.getCommonReferenceID());
					EBUnitofMeasureLocal theExistingEjb = (EBUnitofMeasureLocal) existingListMap.get(commonRefID);
					if (theExistingEjb != null) {
						DefaultLogger.debug(this, "##### updating : " + uom.getUnitofMeasureID() + " - "
								+ uom.getName());
						theExistingEjb.setValue(uom);
						existingListMap.remove(commonRefID);
						updatedList.add(uom);
					}
					else {
						newList.add(uom);
					}
				}

				// add new uoms
				addUOM(newList, updatedList);

				// remove deleted uoms
				EBUnitofMeasureLocal[] deletedList = (EBUnitofMeasureLocal[]) existingListMap.values().toArray(
						new EBUnitofMeasureLocal[0]);
				removeUOM(Arrays.asList(deletedList));

				return (updatedList.size() == 0) ? new IUnitofMeasure[0] : (IUnitofMeasure[]) updatedList
						.toArray(new IUnitofMeasure[updatedList.size()]);

			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException("Exception caught at updateInfoPersist " + e.toString());
		}
		return new IUnitofMeasure[0];
	}

	/**
	 * Helper method to add all uoms specified in the collection.
	 * 
	 * @param newUOMs
	 * @throws CreateException
	 */
	private void addUOM(Collection newUOMs, Collection resultsList) throws CreateException, CommodityException,
			Exception {
		Iterator i = newUOMs.iterator();
		while (i.hasNext()) {
			IUnitofMeasure uom = (IUnitofMeasure) i.next();
			DefaultLogger.debug(this, "##### adding : " + uom.getUnitofMeasureID() + " - " + uom.getName());
			resultsList.add(createInfoPersist(uom));
		}
	}

	/**
	 * Helper method to remove all uoms specified in the collection.
	 * 
	 * @param uoms - Collection of uoms to be removed. Cannot be null.
	 */
	private void removeUOM(Collection uoms) throws CommodityException, ConcurrentUpdateException,
			VersionMismatchException {
		Iterator i = uoms.iterator();
		while (i.hasNext()) {
			EBUnitofMeasureLocal theEjb = (EBUnitofMeasureLocal) i.next();
			removeUOM(theEjb);
		}
	}

	/**
	 * Helper method to remove the said uom.
	 * 
	 * @param anEjb - EBUnitofMeasureLocal
	 */
	private void removeUOM(EBUnitofMeasureLocal anEjb) throws CommodityException, VersionMismatchException,
			ConcurrentUpdateException {
		IUnitofMeasure uom = anEjb.getValue();
		DefaultLogger.debug(this, "##### soft deleting : " + uom.getUnitofMeasureID() + " - " + uom.getName());
		uom.setStatus(ICMSConstant.STATE_DELETED);
		anEjb.setValue(uom);
	}

	/**
	 * Updates title document types. Handles creates, updates and soft deletion
	 * of title document types.
	 * 
	 * @param titleDocTypes - ITitleDocument[]
	 * @throws CommodityException
	 */
	private ITitleDocument[] updateTitleDocType(ITitleDocument[] titleDocTypes) throws CommodityException {

		DefaultLogger.debug(this, "################################");
		DefaultLogger.debug(this, "######## updateTitleDocType #########");
		for (int i = 0; i < titleDocTypes.length; i++) {
			DefaultLogger.debug(this, "##### titleDocTypes[" + i + "] : " + titleDocTypes[i]);
		}
		DefaultLogger.debug(this, "################################");
		DefaultLogger.debug(this, "################################");

		ArrayList updatedList = new ArrayList();

		// TODO : should pass in category and product type code as well
		// TODO : get grp ID given category and product type code
		// TODO : current implementation will not work if all uoms were deleted
		// TODO : currently, UI prevents all uoms to be deleted in a country
		if ((titleDocTypes == null) || (titleDocTypes.length == 0)) {
			return new ITitleDocument[0];
		}

		// get existing title doc types
		Collection existingList = null;
		try {
			long groupID = titleDocTypes[0].getGroupID();
			DefaultLogger.debug(this, "######## groupID : " + groupID + " #########");
			existingList = _getTitleDocumentLocalHome().findByGroupID(new Long(groupID));
		}
		catch (FinderException e) {
			e.printStackTrace();
		}

		DefaultLogger.debug(this, "################################");
		DefaultLogger.debug(this, "######## updateTitleDocType #########");
		Iterator it = existingList.iterator();
		int count = 0;
		while (it.hasNext()) {
			DefaultLogger.debug(this, "##### existing[" + count++ + "] : "
					+ ((EBTitleDocumentLocal) it.next()).getValue());
		}
		DefaultLogger.debug(this, "################################");
		DefaultLogger.debug(this, "################################");

		try {
			if (existingList.isEmpty()) {
				// existing list is empty, add all in warehouses
				if ((titleDocTypes != null) && (titleDocTypes.length != 0)) {
					addTitleDocType(Arrays.asList(titleDocTypes), updatedList);
					return (updatedList.size() == 0) ? new ITitleDocument[0] : (ITitleDocument[]) updatedList
							.toArray(new ITitleDocument[updatedList.size()]);
				}
			}
			else {
				// existing list is NOT empty
				// if uoms is empty , remove all existing uo
				if ((titleDocTypes == null) || (titleDocTypes.length == 0)) {
					removeTitleDocType(existingList);
					return (updatedList.size() == 0) ? new ITitleDocument[0] : (ITitleDocument[]) updatedList
							.toArray(new ITitleDocument[updatedList.size()]);
				}

				// put existing list into hashmap - excludes deleted values
				HashMap existingListMap = new HashMap(existingList.size());
				Iterator existingListIterator = existingList.iterator();
				while (existingListIterator.hasNext()) {
					EBTitleDocumentLocal titleDocTypeLocal = (EBTitleDocumentLocal) existingListIterator.next();
					ITitleDocument titleDocType = titleDocTypeLocal.getValue();
					if ((titleDocType.getStatus() == null)
							|| !titleDocType.getStatus().equals(ICMSConstant.STATE_DELETED)) {
						existingListMap.put(new Long(titleDocType.getCommonRef()), titleDocTypeLocal);
					}
				}

				ArrayList newList = new ArrayList();

				// compare uoms with existing list
				for (int i = 0; i < titleDocTypes.length; i++) {
					ITitleDocument titleDocType = titleDocTypes[i];
					Long commonRefID = new Long(titleDocType.getCommonRef());
					EBTitleDocumentLocal theExistingEjb = (EBTitleDocumentLocal) existingListMap.get(commonRefID);
					if (theExistingEjb != null) {
						DefaultLogger.debug(this, "##### updating : " + titleDocType.getTitleDocumentID() + " - "
								+ titleDocType.getName());
						theExistingEjb.setValue(titleDocType);
						existingListMap.remove(commonRefID);
						updatedList.add(titleDocType);
					}
					else {
						newList.add(titleDocType);
					}
				}

				// add new uoms
				addTitleDocType(newList, updatedList);

				// remove deleted uoms
				EBTitleDocumentLocal[] deletedList = (EBTitleDocumentLocal[]) existingListMap.values().toArray(
						new EBTitleDocumentLocal[0]);
				removeTitleDocType(Arrays.asList(deletedList));

				return (updatedList.size() == 0) ? new ITitleDocument[0] : (ITitleDocument[]) updatedList
						.toArray(new ITitleDocument[updatedList.size()]);

			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException("Exception caught at updateInfoPersist " + e.toString());
		}
		return new ITitleDocument[0];
	}

	/**
	 * Helper method to add all title document types specified in the
	 * collection.
	 * 
	 * @param newTypes
	 * @throws CreateException
	 */
	private void addTitleDocType(Collection newTypes, Collection resultsList) throws CreateException,
			CommodityException, Exception {
		Iterator i = newTypes.iterator();
		while (i.hasNext()) {
			ITitleDocument type = (ITitleDocument) i.next();
			DefaultLogger.debug(this, "##### adding : " + type.getTitleDocumentID() + " - " + type.getName());
			resultsList.add(createInfoPersist(type));
		}
	}

	/**
	 * Helper method to remove all title document types specified in the
	 * collection.
	 * 
	 * @param types - Collection of title document types to be removed. Cannot
	 *        be null.
	 */
	private void removeTitleDocType(Collection types) throws CommodityException, ConcurrentUpdateException,
			VersionMismatchException {
		Iterator i = types.iterator();
		while (i.hasNext()) {
			EBTitleDocumentLocal theEjb = (EBTitleDocumentLocal) i.next();
			removeTitleDocType(theEjb);
		}
	}

	/**
	 * Helper method to remove the said profile.
	 * 
	 * @param anEjb - EBTitleDocumentLocal
	 */
	private void removeTitleDocType(EBTitleDocumentLocal anEjb) throws CommodityException, VersionMismatchException,
			ConcurrentUpdateException {
		ITitleDocument type = anEjb.getValue();
		DefaultLogger.debug(this, "##### soft deleting : " + type.getTitleDocumentID() + " - " + type.getName());
		type.setStatus(ICMSConstant.STATE_DELETED);
		anEjb.setValue(type);
	}

	/**
	 * Updates profiles. Handles creates, updates and soft deletion of profiles.
	 * 
	 * @param profiles - IProfile[]
	 * @throws CommodityException
	 */
	private IProfile[] updateProfile(IProfile[] profiles) throws CommodityException {

		DefaultLogger.debug(this, "################################");
		DefaultLogger.debug(this, "######## updateProfile #########");
		for (int i = 0; i < profiles.length; i++) {
			DefaultLogger.debug(this, "##### profiles[" + i + "] : " + profiles[i]);
		}
		DefaultLogger.debug(this, "################################");
		DefaultLogger.debug(this, "################################");

		// TODO : should pass in category and product type code as well
		// TODO : get grp ID given category and product type code
		// TODO : current implementation will not work if all uoms were deleted
		// TODO : currently, UI prevents all uoms to be deleted in a country
		if ((profiles == null) || (profiles.length == 0)) {
			return new IProfile[0];
		}

		// get existing title doc types
		Collection existingList = null;
		try {
			long groupID = profiles[0].getGroupID();
			DefaultLogger.debug(this, "######## groupID : " + groupID + " #########");
			existingList = _getProfileLocalHome().findByGroupID(new Long(groupID));
		}
		catch (FinderException e) {
			e.printStackTrace();
		}

		DefaultLogger.debug(this, "################################");
		DefaultLogger.debug(this, "######## updateProfile #########");
		Iterator it = existingList.iterator();
		int count = 0;
		while (it.hasNext()) {
			DefaultLogger.debug(this, "##### existing[" + count++ + "] : " + ((EBProfileLocal) it.next()).getValue());
		}
		DefaultLogger.debug(this, "################################");
		DefaultLogger.debug(this, "################################");

		ArrayList updatedList = new ArrayList();
		try {
			if (existingList.isEmpty()) {
				// existing list is empty, add all in warehouses
				if ((profiles != null) && (profiles.length != 0)) {
					addProfile(Arrays.asList(profiles), updatedList);
					return (updatedList.size() == 0) ? new IProfile[0] : (IProfile[]) updatedList
							.toArray(new IProfile[updatedList.size()]);
				}
			}
			else {
				// existing list is NOT empty
				// if uoms is empty , remove all existing uo
				if ((profiles == null) || (profiles.length == 0)) {
					removeProfile(existingList);
					return (updatedList.size() == 0) ? new IProfile[0] : (IProfile[]) updatedList
							.toArray(new IProfile[updatedList.size()]);
				}

				// put existing list into hashmap - excludes deleted values
				HashMap existingListMap = new HashMap(existingList.size());
				Iterator existingListIterator = existingList.iterator();
				while (existingListIterator.hasNext()) {
					EBProfileLocal profileLocal = (EBProfileLocal) existingListIterator.next();
					IProfile profile = profileLocal.getValue();
					if ((profile.getStatus() == null) || !profile.getStatus().equals(ICMSConstant.STATE_DELETED)) {
						existingListMap.put(new Long(profile.getCommonRef()), profileLocal);
					}
				}

				ArrayList newList = new ArrayList();

				// compare uoms with existing list
				for (int i = 0; i < profiles.length; i++) {
					IProfile profile = profiles[i];
					Long commonRefID = new Long(profile.getCommonRef());
					EBProfileLocal theExistingEjb = (EBProfileLocal) existingListMap.get(commonRefID);
					if (theExistingEjb != null) {
						DefaultLogger.debug(this, "##### updating : " + profile.getProfileID() + " - "
								+ profile.getProductSubType());
						theExistingEjb.setValue(profile);
						existingListMap.remove(commonRefID);
						updatedList.add(profile);
					}
					else {
						newList.add(profile);
					}
				}

				// add new uoms
				addProfile(newList, updatedList);

				// remove deleted uoms
				EBProfileLocal[] deletedList = (EBProfileLocal[]) existingListMap.values().toArray(
						new EBProfileLocal[0]);
				removeProfile(Arrays.asList(deletedList));

				return (updatedList.size() == 0) ? new IProfile[0] : (IProfile[]) updatedList
						.toArray(new IProfile[updatedList.size()]);
			}

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException("Exception caught at updateInfoPersist " + e.toString());
		}
		return new IProfile[0];
	}

	/**
	 * Helper method to add all profiles specified in the collection.
	 * 
	 * @param newTypes
	 * @throws CreateException
	 */
	private void addProfile(Collection newTypes, Collection resultsList) throws CreateException, CommodityException,
			Exception {
		Iterator i = newTypes.iterator();
		while (i.hasNext()) {
			IProfile profile = (IProfile) i.next();
			DefaultLogger.debug(this, "##### adding : " + profile.getProfileID() + " - " + profile.getProductSubType());
			resultsList.add(createInfoPersist(profile));
		}
	}

	/**
	 * Helper method to remove all profiles specified in the collection.
	 * 
	 * @param types - Collection of profiles to be removed. Cannot be null.
	 */
	private void removeProfile(Collection types) throws CommodityException, ConcurrentUpdateException,
			VersionMismatchException {
		Iterator i = types.iterator();
		while (i.hasNext()) {
			EBProfileLocal theEjb = (EBProfileLocal) i.next();
			removeProfile(theEjb);
		}
	}

	/**
	 * Helper method to remove the said profile.
	 * 
	 * @param anEjb - EBProfileLocal
	 */
	private void removeProfile(EBProfileLocal anEjb) throws CommodityException, VersionMismatchException,
			ConcurrentUpdateException {
		IProfile type = anEjb.getValue();
		DefaultLogger.debug(this, "##### soft deleting : " + type.getProfileID() + " - " + type.getProductSubType());
		type.setStatus(ICMSConstant.STATE_DELETED);
		anEjb.setValue(type);
	}
}
