package com.integrosys.cms.app.partygroup.bus;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.hibernate.Query;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.partygroup.bus.IPartyGroupDao;
import com.integrosys.cms.app.partygroup.bus.OBPartyGroup;

/**
 * @author $Author: Bharat Waghela $<br>
 * 
 *         Dao Implication declares the methods used by Bus manager Implication
 */
public class PartyGroupDaoImpl extends HibernateDaoSupport implements
		IPartyGroupDao {

	public String getEntityName() {
		return IPartyGroupDao.ACTUAL_PARTY_GROUP_NAME;
	}

	/**
	 * @return PartyGroup Object
	 * @param Entity
	 *            Name
	 * @param Key
	 *            This method returns party group Object
	 */
	public IPartyGroup getPartyGroup(String entityName, Serializable key)
			throws PartyGroupException {
		if (!(entityName == null || key == null)) {
			return (IPartyGroup) getHibernateTemplate().get(entityName, key);
		} else {
			throw new PartyGroupException("ERROR- Entity name or key is null ");
		}
	}

	public IPartyGroup getPartyGroupById( Serializable key)
	throws PartyGroupException {
if (!(key == null)) {
	return (IPartyGroup) getHibernateTemplate().get(getEntityName(), key);
} else {
	throw new PartyGroupException("ERROR- Entity name or key is null ");
}
}
	public IPartyGroup deletePartyGroup(String entityName, IPartyGroup item)
			throws PartyGroupException {

		if (!(entityName == null || item == null)) {
			item.setDeprecated("Y");
			item.setStatus("INACTIVE");
			getHibernateTemplate().update(entityName, item);
			return (IPartyGroup) getHibernateTemplate().load(entityName,
					new Long(item.getId()));
		} else {
			throw new PartyGroupException("ERROR-- Entity Name Or Key is null");
		}
	}

	public IPartyGroup enablePartyGroup(String entityName, IPartyGroup item)
			throws PartyGroupException {

		if (!(entityName == null || item == null)) {
			item.setDeprecated("N");
			item.setStatus("ACTIVE");
			getHibernateTemplate().update(entityName, item);
			return (IPartyGroup) getHibernateTemplate().load(entityName,
					new Long(item.getId()));
		} else {
			throw new PartyGroupException("ERROR-- Entity Name Or Key is null");
		}
	}

	/**
	 * @return PartyGroup Object
	 * @param Entity
	 *            Name
	 * @param PartyGroup
	 *            Object This method Updates Party group Object
	 */
	public IPartyGroup updatePartyGroup(String entityName, IPartyGroup item)
			throws PartyGroupException {
		if (!(entityName == null || item == null)) {
			getHibernateTemplate().update(entityName, item);

			return (IPartyGroup) getHibernateTemplate().load(entityName,
					new Long(item.getId()));
		} else {
			throw new PartyGroupException("ERROR- Entity name or key is null ");
		}

	}

	public IPartyGroup createPartyGroup(IPartyGroup PartyGroup)
			throws PartyGroupException {
		IPartyGroup returnObj = new OBPartyGroup();
		try {
			getHibernateTemplate().save(getEntityName(), PartyGroup);
			returnObj = (IPartyGroup) getHibernateTemplate().load(
					getEntityName(), new Long(PartyGroup.getId()));
		} catch (Exception obe) {
			DefaultLogger.error(this,
					"############# error in createPartyGroup ", obe);
			obe.printStackTrace();
			throw new PartyGroupException("Unable to create other bank ");
		}
		return returnObj;
	}

	/**
	 * @return PartyGroup Object
	 * @param Entity
	 *            Name
	 * @param PartyGroup
	 *            Object This method Creates Party group Object
	 */

	public IPartyGroup createPartyGroup(String entityName,
			IPartyGroup partyGroup) throws PartyGroupException {
		if (!(entityName == null || partyGroup == null)) {
			partyGroup.setStatus("ACTIVE");
			partyGroup.setDeprecated("N");
			if( partyGroup.getPartyCode() == null || partyGroup.getPartyCode().equals("")){
				String partyGroupCode=getPartyGroupCode();
				partyGroup.setPartyCode(partyGroupCode);
			}
			Long key = (Long) getHibernateTemplate().save(entityName,
					partyGroup);
			partyGroup.setId(key.longValue());
			return partyGroup;
		} else {
			throw new PartyGroupException("ERROR- Entity name or key is null ");
		}
	}
	
	private String getPartyGroupCode() {
		Query query = currentSession().createSQLQuery("SELECT PARTY_GROUP_SEQ.NEXTVAL FROM dual");
		String sequenceNumber = query.uniqueResult().toString();
		NumberFormat numberFormat = new DecimalFormat("0000000");
		String partyGroupCode = numberFormat.format(Long.parseLong(sequenceNumber));
		partyGroupCode = "PRG" + partyGroupCode;		
		return partyGroupCode;
	}
	
	public String getCustomerIdCode() {
		Query query = currentSession().createSQLQuery("SELECT PARTY_GROUP_SEQ.NEXTVAL FROM dual");
		String sequenceNumber = query.uniqueResult().toString();
		NumberFormat numberFormat = new DecimalFormat("0000000");
		String partyGroupCode = numberFormat.format(Long.parseLong(sequenceNumber));
		partyGroupCode = "CUS" + partyGroupCode;		
		return partyGroupCode;
	}
	
	public boolean isPartyCodeUnique(String partyName){
		String query = "SELECT FROM "+IPartyGroupDao.STAGE_PARTY_GROUP_NAME  +" where UPPER(party_group_name) like '"+partyName.toUpperCase()+"' ";
		ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
		if(resultList.size()>0){
			String query1 = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND UPPER(party_group_name) like '"+partyName.toUpperCase()+"' ";
			ArrayList resultList1 = (ArrayList) getHibernateTemplate().find(query1);
			if(resultList1.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;
		 
	}
	
	public boolean isPartyNameUnique(String cpsId){
		
			String query1 = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND UPPER(cps_id) = '"+cpsId.toUpperCase()+"' ";
			ArrayList resultList1 = (ArrayList) getHibernateTemplate().find(query1);
			if(resultList1.size()>0)
				return true;
			else
				return false;
		
	}
	
	public boolean isCustomerNameUnique(String partyCode){
		String query = "SELECT FROM "+IPartyGroupDao.STAGE_PARTY_GROUP_NAME+" WHERE party_group_code like '"+partyCode+"' ";
		ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
		if(resultList.size()>0){
			String query1 = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND party_group_code like '"+partyCode+"' ";
			ArrayList resultList1 = (ArrayList) getHibernateTemplate().find(query1);
			if(resultList1.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;
		 
	}

	public SearchResult getPartyList(String type, String text) throws PartyGroupException {
		try {
			String query = "";
			if ( text == null || text == "" ) {
				query = "FROM " + getEntityName() + " party ORDER BY UPPER(party_group_code)";
			} else {
				if ( type.equalsIgnoreCase("partyCode") )					
					query = "FROM " + getEntityName() + " party WHERE UPPER(party_group_code) LIKE '" + text.toUpperCase() + "%' order by UPPER(party_group_code)";
				else if ( type.equalsIgnoreCase("partyName") ) 	
					query = "FROM " + getEntityName() + " party WHERE UPPER(party_group_name) LIKE '" + text.toUpperCase() + "%' order by UPPER(party_group_code)";				
			}
			ArrayList partyList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, partyList.size(), partyList.size(),partyList);
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in listParty", obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to get Party");
		}
	}
}
