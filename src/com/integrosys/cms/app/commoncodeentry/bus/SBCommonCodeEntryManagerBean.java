/**
 * SBCommonCodeParamManagerBean.java
 *
 * Created on January 30, 2007, 10:46 AM
 *
 * Purpose: The session bean that managers and all the maker and checker operations
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.bus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryConstant;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;

public class SBCommonCodeEntryManagerBean implements SessionBean {

	private static final long serialVersionUID = 1L;

	SessionContext session;

	public void setSessionContext(SessionContext sessionContext) throws EJBException {
		session = sessionContext;
	}

	public void ejbRemove() throws EJBException {
	}

	public void ejbPassivate() throws EJBException {
	}

	public void ejbActivate() throws EJBException {
	}

	public void ejbCreate() throws CreateException {

	}

	public void ejbPostCreate() {

	}

	public Collection getEditableCodes() {

		return null;
	}

	public Collection getCommonCodeEntries(String categoryCodeId) throws CommonCodeEntriesException {
		try {
			DefaultLogger.debug(this, "Searching for entries with category code id : " + categoryCodeId);

			ArrayList list = new ArrayList();
			EBCommonCodeEntryHome home = getEBCommonCodeEntryHome();
			Collection entityList = home.findByCategoryCodeId(new Long(categoryCodeId));
			Iterator iter = entityList.iterator();

			DefaultLogger.debug(this, "Entries found , number of entries : " + entityList.size());

			while (iter.hasNext()) {
				EBCommonCodeEntry entity = (EBCommonCodeEntry) iter.next();
				OBCommonCodeEntry ob = entity.getOBCommonCodeEntry();

				list.add(ob);
			}

			return list;
		}
		catch (Exception e) {
			throw new CommonCodeEntriesException("",e);
		}
	}
	
	public Collection getEntryValues(String categoryCodeId,String desc,String value) throws CommonCodeEntriesException {
		try {
			DefaultLogger.debug(this, "Searching for entries with category code id : " + categoryCodeId);

			ArrayList list = new ArrayList();
			CommonCodeEntryDAO codeDao=null;
			Collection entityList = codeDao.findByEntryValues(new Long(categoryCodeId),desc,value);
			Iterator iter = entityList.iterator();

			DefaultLogger.debug(this, "Entries found , number of entries : " + entityList.size());

			while (iter.hasNext()) {
				EBCommonCodeEntry entity = (EBCommonCodeEntry) iter.next();
				OBCommonCodeEntry ob = entity.getOBCommonCodeEntry();

				list.add(ob);
			}

			return list;
		}
		catch (Exception e) {
			throw new CommonCodeEntriesException("",e);
		}
	}


	public OBCommonCodeEntry getCommonCodeEntry(String categoryCodeId, String entryCode, String country)
			throws CommonCodeEntriesException {
		try {
			DefaultLogger.debug(this, "Now retrieving data");
			DefaultLogger.debug(this, "Category Code Id : " + categoryCodeId);
			DefaultLogger.debug(this, "Entry Code : " + entryCode);

			EBCommonCodeEntryHome home = getEBCommonCodeEntryHome();
			EBCommonCodeEntry entity = home.findByCategoryAndEntryCode(new Long(categoryCodeId), entryCode, country);
			OBCommonCodeEntry ob = entity.getOBCommonCodeEntry();

			DefaultLogger.debug(this, "Data retrieved.");

			return ob;
		}
		catch (Exception e) {
			throw new CommonCodeEntriesException("",e);
		}
	}

	public Collection getCommonCodeCategories() {
		ArrayList list = new ArrayList();

		// List not retrieved from here

		return list;
	}

	public OBCommonCodeEntries setStaging(ICommonCodeEntries value, Long stageRefId) throws CommonCodeEntriesException {

		try {
			OBCommonCodeEntries entries = (OBCommonCodeEntries) value;
			OBCommonCodeEntry ob[] = entries.getObArray();
			ArrayList list = new ArrayList();

			long catCode = entries.getCategoryCodeId();
			if (stageRefId == null) {
				stageRefId = new Long(new SequenceManager().getSeqNum(ICMSConstant.SEQUENCE_COMMON_CODE_ENTRY_STAGEREF,
						true));
			}
			entries.setStagingReferenceID(String.valueOf(stageRefId));
			
			for (int loop = 0; loop < ob.length; loop++) {
				ob[loop].setCategoryCodeId(catCode);
				
				if ('I' == ob[loop].getUpdateFlag()||'U' == ob[loop].getUpdateFlag()) {
					ob[loop].setStageRefId(stageRefId);
					updateStageCC(ob[loop]);
				}
					
				list.add(ob[loop]);
			}
			entries.setEntries(list);
			return entries;

		}
		catch (Exception ex) {
			ex.printStackTrace();
			session.setRollbackOnly();
			throw new CommonCodeEntriesException("",ex);
		}
	}
	
	private void updateStageCC(OBCommonCodeEntry entry) throws Exception{
		/* I : only insert one record for the 1st time , subsequently, we should always update.  
		 * U : should be always updating db except that the entry is create from SI, no entry in stage. 
		 * For a active case, stage data have not been loaded, we just replace with the actual data, the stage_id is not loaded.
		 */
		if((entry.getStageId()==null||entry.getStageId().longValue() == ICMSConstant.LONG_INVALID_VALUE)){
			try {
				if(entry.getEntryId()== ICMSConstant.LONG_INVALID_VALUE){
					entry = getEBCommonCodeEntryStageHome().create(entry).getOBCommonCodeEntryStage();
				}else{
					getEBCommonCodeEntryStageHome().findByEntryId(new Long(entry.getEntryId())).updateCommonCodeEntry(entry);
				}
				DefaultLogger.debug(this, "Updated entry via entryID"); 
			} catch (ObjectNotFoundException e) {
				DefaultLogger.debug(this, "Create a new entry as can't find via entryID"); 
				entry = getEBCommonCodeEntryStageHome().create(entry).getOBCommonCodeEntryStage();						
			}
		}else{
			DefaultLogger.debug(this, "Updated entry via PK"); 
			getEBCommonCodeEntryStageHome().findByPrimaryKey(entry.getStageId()).updateCommonCodeEntry(entry);
		}
	}
	
	public ICommonCodeEntries getStagingData(String stageId) throws CommonCodeEntriesException {
		try {
			EBCommonCodeEntryStageHome home = getEBCommonCodeEntryStageHome();
			Collection collection = home.findByStagingRefId(new Long(stageId));
			Iterator iter = collection.iterator();
			ArrayList list = new ArrayList();

			while (iter.hasNext()) {
				EBCommonCodeEntryStage stage = (EBCommonCodeEntryStage) iter.next();
				OBCommonCodeEntry stageData = stage.getOBCommonCodeEntryStage();

				list.add(stageData);
			}

			OBCommonCodeEntries entries = new OBCommonCodeEntries();

			entries.setEntries(list);
			entries.setStagingReferenceID(stageId);

			return entries;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommonCodeEntriesException("",e);
		}
	}

	public ICommonCodeEntries updateCommonCodeEntries(ICommonCodeEntries entries) throws CommonCodeEntriesException {
		ArrayList list = new ArrayList();
		Collection coll = entries.getEntries();
		Iterator iter = coll.iterator();
		
		try {
			IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamEntry generalParamEntry = generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			String applicationDate = generalParamEntry.getParamValue();
			Date appDate = new Date(applicationDate);
			EBCommonCodeEntryHome home = getEBCommonCodeEntryHome();
			EBCommonCodeEntryStageHome sHome = getEBCommonCodeEntryStageHome();
			SequenceManager manager = new SequenceManager();
			Long stageRefId = null;
			while (iter.hasNext()) {
				OBCommonCodeEntry ob = (OBCommonCodeEntry) iter.next();
				String entryId = null;
				if (stageRefId == null) {
					stageRefId = ob.getStageRefId();
				}
				if ((ob.getEntryId() == ICMSConstant.LONG_INVALID_VALUE) || ('I' == ob.getUpdateFlag())) {
					ob.setUpdateFlag('N');
					ob.setCreationDate(appDate);
					ob.setLastUpdateDate(appDate);
					entryId = manager.getSeqNum(ICMSConstant.SEQUENCE_COMMON_CODE_ENTRY, true);
					ob.setEntryId(Long.parseLong(entryId));
					home.create(ob);
					sHome.findByPrimaryKey(ob.getStageId()).updateCommonCodeEntry(ob);
				}
				else if ('U' == ob.getUpdateFlag()) {
					DefaultLogger.debug(this, "Update - Actual- entryID:" + ob.getEntryId());
					ob.setUpdateFlag('N');
					ob.setLastUpdateDate(appDate);
					EBCommonCodeEntry eb = home.findByPrimaryKey(new Long(ob.getEntryId()));
					eb.updateCommonCodeEntry(ob);
				}
				list.add(ob);
			}
			resetStageCommonCodeEntryUpdateFlag(stageRefId);
			entries.setEntries(list);

			return entries;
		}
		catch (Exception e) {
			e.printStackTrace();
			session.setRollbackOnly();
			throw new CommonCodeEntriesException("",e);
		}
	}

	public void resetStageCommonCodeEntryUpdateFlag(Long stageRefId) throws CommonCodeEntriesException {
		try {
			new CommonCodeEntryDAO().clearUpdateFlag(stageRefId);
		}
		catch (Exception e) {
			e.printStackTrace();
			session.setRollbackOnly();
			throw new CommonCodeEntriesException("",e);
		}
	}

	public void restoreStageCommonCodeEntry(Long stageRefId) throws CommonCodeEntriesException {
		try {
			new CommonCodeEntryDAO().revertToActual(stageRefId);
		}
		catch (Exception e) {
			e.printStackTrace();
			session.setRollbackOnly();
			throw new CommonCodeEntriesException("",e);
		}
	}

	public Collection getOBCollectionForCodeAndReference(String categoryCode, String refCode) {
		Collection coll = new ArrayList();

		try {
			EBCommonCodeEntryHome home = getEBCommonCodeEntryHome();
			Collection collEjb = home.findByCategoryCodeAndRefEntryCode(categoryCode, refCode);
			Iterator iter = collEjb.iterator();

			while (iter.hasNext()) {
				EBCommonCodeEntry entry = (EBCommonCodeEntry) iter.next();
				OBCommonCodeEntry ob = entry.getOBCommonCodeEntry();

				coll.add(ob);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error retreving data, returning an empty collection.", e);
		}

		DefaultLogger.debug(this, "Collection size is : " + coll.size());

		return coll;
	}

	public Collection getCommonCodeEntriesByCode(String categoryCode) {
		Collection coll = new ArrayList();

		try {
			EBCommonCodeEntryHome home = getEBCommonCodeEntryHome();
			Collection collEjb = home.findByCategoryCode(categoryCode);
			Iterator iter = collEjb.iterator();

			while (iter.hasNext()) {
				EBCommonCodeEntry entry = (EBCommonCodeEntry) iter.next();
				OBCommonCodeEntry ob = entry.getOBCommonCodeEntry();

				coll.add(ob);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error retreving data, returning an empty collection.", e);
		}

		DefaultLogger.debug(this, "Collection size is : " + coll.size());

		return coll;
	}

	public HashMap getValuesAndLabelsForCodeAndReference(String categoryCode, String refCode) {
		HashMap map = new HashMap();

		DefaultLogger.debug(this, "Retrieving entries for category code : " + categoryCode);
		DefaultLogger.debug(this, "Retrieving entries for reference code : " + refCode);

		try {
			EBCommonCodeEntryHome home = getEBCommonCodeEntryHome();
			Collection coll = home.findByCategoryCodeAndRefEntryCode(categoryCode, refCode);
			Iterator iter = coll.iterator();

			while (iter.hasNext()) {
				EBCommonCodeEntry entry = (EBCommonCodeEntry) iter.next();
				OBCommonCodeEntry ob = entry.getOBCommonCodeEntry();

				map.put(ob.getEntryCode(), ob.getEntryName());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error retreving data, returning an empty hash map", e);
		}

		DefaultLogger.debug(this, "Hash map size is : " + map.size());

		return map;
	}

	public HashMap getValuesAndLabelsForCountry() {
		HashMap map = new HashMap();

		try {
			EBCommonCodeEntryHome home = getEBCommonCodeEntryHome();
			Collection coll = home.findByCategoryCode(CommonCodeEntryConstant.COUNTRY_CATEGORY_CODE);
			Iterator iter = coll.iterator();

			while (iter.hasNext()) {
				EBCommonCodeEntry entry = (EBCommonCodeEntry) iter.next();
				OBCommonCodeEntry ob = entry.getOBCommonCodeEntry();

				map.put(ob.getEntryCode(), ob.getEntryName());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error retreving states",e);
		}

		return map;
	}

	public HashMap getValuesAndLabelsForStates(String codes[]) {
		return getValuesAndLabels(CommonCodeEntryConstant.STATE_CATEGORY_CODE, codes);
	}

	public HashMap getValuesAndLabelsForDistrict(String codes[]) {
		return getValuesAndLabels(CommonCodeEntryConstant.DISTRICT_CATEGORY_CODE, codes);
	}

	public HashMap getValuesAndLabelsForMukim(String codes[]) {
		return getValuesAndLabels(CommonCodeEntryConstant.MUKIM_CATEGORY_CODE, codes);
	}

	private HashMap getValuesAndLabels(String categoryCode, String codes[]) {
		HashMap map = new HashMap();

		for (int loop = 0; loop < codes.length; loop++) {
			// get the values and labels for each individual code and append it
			// to the hash map
			map.putAll(getValuesAndLabels(categoryCode, codes[loop]));
		}

		return map;
	}

	/**
	 * the actually process of getting entry data from the db
	 * 
	 * @param categoryCode - the category code
	 * @param code - the code
	 * @return HashMap - a hash map contain the values and labels using the
	 *         values as the keys
	 */
	private HashMap getValuesAndLabels(String categoryCode, String code) {
		HashMap map = new HashMap();

		DefaultLogger.debug(this, "now search for data in entries");
		DefaultLogger.debug(this, "Category code is : " + categoryCode);
		DefaultLogger.debug(this, "Code is : " + code);

		try {
			EBCommonCodeEntryHome home = getEBCommonCodeEntryHome();
			Collection coll = home.findByCategoryCodeAndRefEntryCode(categoryCode, code);
			Iterator iter = coll.iterator();

			while (iter.hasNext()) {
				EBCommonCodeEntry entry = (EBCommonCodeEntry) iter.next();
				OBCommonCodeEntry ob = entry.getOBCommonCodeEntry();

				map.put(ob.getEntryCode(), ob.getEntryName());
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error retreving data",e);
			DefaultLogger.error(this, "Returning empty hash map");
		}

		DefaultLogger.debug(this, "Size of data found : " + map.size());

		return map;
	}

	private final EBCommonCodeEntryHome getEBCommonCodeEntryHome() throws CommonCodeEntriesException {
		Object obj = BeanController.getEJBHome(ICMSJNDIConstant.EB_COMMON_CODE_ENTRY_HOME, EBCommonCodeEntryHome.class
				.getName());
		if (obj == null) {
			throw new CommonCodeEntriesException("Can not locate EBCommonCodeEntryHome!");
		}
		return (EBCommonCodeEntryHome) obj;
	}

	private final EBCommonCodeEntryStageHome getEBCommonCodeEntryStageHome() throws CommonCodeEntriesException {
		Object obj = BeanController.getEJBHome(ICMSJNDIConstant.EB_COMMON_CODE_ENTRY_STAGE_HOME,
				EBCommonCodeEntryStageHome.class.getName());

		if (obj == null) {
			throw new CommonCodeEntriesException("Can not locate EBCommonCodeEntryStageHome!");
		}
		return (EBCommonCodeEntryStageHome) obj;
	}
}
