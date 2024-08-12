package com.integrosys.cms.app.pincodemapping.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.IStateDAO;

public class PincodeMappingDaoImpl extends HibernateDaoSupport implements
IPincodeMappingDao{
	
	public String getEntityName() {
		return IPincodeMappingDao.ACTUAL_PINCODE_MAPPING_NAME;
	}
	
	public IPincodeMapping getPincodeMappingById( Serializable key)
	throws PincodeMappingException {
		if (!(key == null)) {
	return (IPincodeMapping) getHibernateTemplate().get(getEntityName(), key);
		} else {
	throw new PincodeMappingException("ERROR- Entity name or key is null ");
		}
	}
	
	public IPincodeMapping createPincodeMapping(IPincodeMapping pincodeMapping)
			throws PincodeMappingException {
		IPincodeMapping returnObj = new OBPincodeMapping();
		try {
			getHibernateTemplate().save(getEntityName(), pincodeMapping);
			returnObj = (IPincodeMapping) getHibernateTemplate().load(
					getEntityName(), new Long(pincodeMapping.getId()));
		} catch (Exception obe) {
			DefaultLogger.error(this,
					"############# error in createPincodeMapping ", obe);
			obe.printStackTrace();
			throw new PincodeMappingException("Unable to create PincodeMapping ");
		}
		return returnObj;
	}
	
	public IPincodeMapping createPincodeMapping(String entityName,
			IPincodeMapping pincodeMapping) throws PincodeMappingException {
		if (!(entityName == null || pincodeMapping == null)) {
			pincodeMapping.setStatus("ACTIVE");
			pincodeMapping.setDeprecated("N");
			
			Long key = (Long) getHibernateTemplate().save(entityName,
					pincodeMapping);
			pincodeMapping.setId(key.longValue());
			return pincodeMapping;
		} else {
			throw new PincodeMappingException("ERROR- Entity name or key is null ");
		}
	}
	/**
	 * @return PincodeMapping Object
	 * @param Entity
	 *            Name
	 * @param Key
	 *            This method returns pincode mapping Object
	 */
	public IPincodeMapping getPincodeMapping(String entityName, Serializable key)
			throws PincodeMappingException {
		if (!(entityName == null || key == null)) {
			return (IPincodeMapping) getHibernateTemplate().get(entityName, key);
		} else {
			throw new PincodeMappingException("ERROR- Entity name or key is null ");
		}
	}
	
	public SearchResult getPincodeMappingList(String type, String text) throws PincodeMappingException {
		try {
			String query = "";
			ArrayList pincodeMappingList =new ArrayList();
			if ( text == null || text == "" ) {
				query = "FROM " + getEntityName() + " where status='ACTIVE' ORDER BY UPPER(pincode) ";
			} else {
				if ( type.equalsIgnoreCase("pincode") )					
					query = "FROM " + getEntityName() + " WHERE UPPER(pincode) LIKE '" + text.toUpperCase() + "%' and status='ACTIVE' order by UPPER(pincode) ";
				else if ( type.equalsIgnoreCase("stateId") )
					if( text != null || !(text.trim().equals("")) ){
						String stateQuery = "FROM actualState state WHERE UPPER(state.stateName) LIKE '" + text.toUpperCase() + "%'";
						ArrayList stateList = (ArrayList) getHibernateTemplate().find(stateQuery);
						Iterator stateItor = stateList.iterator();
						while( stateItor.hasNext() ){
							IState state = (IState) stateItor.next();
							long stateId = state.getIdState();
							query = "FROM "+getEntityName()+" pincode WHERE pincode.stateId = " + new Long(stateId);
							ArrayList stateNameList = (ArrayList) getHibernateTemplate().find(query);
							pincodeMappingList.addAll(stateNameList);
							
						}
						return new SearchResult(0, pincodeMappingList.size(), pincodeMappingList.size(),pincodeMappingList);
					}
					
			}
			pincodeMappingList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, pincodeMappingList.size(), pincodeMappingList.size(),pincodeMappingList);
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in listPincodeMapping", obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to get PincodeMapping");
		}
	}
	/**
	 * @return PincodeMapping Object
	 * @param Entity
	 *            Name
	 * @param PincodeMapping
	 *            Object This method Updates PincodeMapping Object
	 */
	public IPincodeMapping updatePincodeMapping(String entityName, IPincodeMapping item)
			throws PincodeMappingException {
		if (!(entityName == null || item == null)) {
			getHibernateTemplate().update(entityName, item);

			return (IPincodeMapping) getHibernateTemplate().load(entityName,
					new Long(item.getId()));
		} else {
			throw new PincodeMappingException("ERROR- Entity name or key is null ");
		}

	}

	public IPincodeMapping deletePincodeMapping(String entityName, IPincodeMapping item)
			throws PincodeMappingException {

		if (!(entityName == null || item == null)) {
			item.setDeprecated("Y");
			item.setStatus("INACTIVE");
			getHibernateTemplate().update(entityName, item);
			return (IPincodeMapping) getHibernateTemplate().load(entityName,
					new Long(item.getId()));
		} else {
			throw new PincodeMappingException("ERROR-- Entity Name Or Key is null");
		}
	}

	
	public boolean isPincodeMappingValid(String pincode, String stateId) {
		
		boolean statePinMappingFound = false;
		String query = "SELECT pincodeMapping.stateId FROM " + getEntityName() + " pincodeMapping WHERE pincode LIKE '" + pincode.toUpperCase() + "' and pincodeMapping.status='ACTIVE'";
		ArrayList list=(ArrayList) getHibernateTemplate().find(query);
		if(list!=null && list.size()>0){
			Iterator stateItor = list.iterator();
			while( stateItor.hasNext() ){
				IState state = (IState) stateItor.next();
				long actualStateId = state.getIdState();
				if(!"".equals(actualStateId) && actualStateId != 0 && actualStateId == (Long.parseLong(stateId))){
					statePinMappingFound = true;
					return statePinMappingFound;
				}
			}
			
		}
		return statePinMappingFound;
	}
	public String getStateCodeFromId(String entityName, String stateId){
		
		String stateCode=null;
		String query="SELECT state.stateCode FROM "+ entityName +" state where state.idState='" + stateId + "' and state.status='ACTIVE'";
		
		ArrayList list=(ArrayList) getHibernateTemplate().find(query);
		if(list!=null && list.size()>0){
			stateCode=(String) list.get(0);
		}
		return stateCode;
	}
	
	public boolean isStateIdUnique(String stateId,String pincode){
		String query = "SELECT FROM "+IPincodeMappingDao.STAGE_PINCODE_MAPPING_NAME  +" where UPPER(state_id) like '"+stateId.toUpperCase()+"' and UPPER(pincode) like '"+pincode.toUpperCase()+"' ";
		ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
		if(resultList.size()>0){
			String query1 = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND UPPER(state_id) like '"+stateId.toUpperCase()+"' AND UPPER(pincode) like '"+pincode.toUpperCase()+"' ";
			ArrayList resultList1 = (ArrayList) getHibernateTemplate().find(query1);
			if(resultList1.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;
		 
	}
	public List getStateList(){
		List stateList = new ArrayList();
		try{
			
			String query = "SELECT FROM "+IStateDAO.ACTUAL_ENTITY_NAME_STATE+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' ";
			stateList = (List) getHibernateTemplate().find(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return stateList;
	}
	
}
