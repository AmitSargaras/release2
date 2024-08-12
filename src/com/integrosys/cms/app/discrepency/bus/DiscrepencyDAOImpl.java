package com.integrosys.cms.app.discrepency.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;

/** 
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class DiscrepencyDAOImpl extends HibernateDaoSupport  implements IDiscrepencyDAO{

	/**
	 * @return String entity name
	 */
	
	public String getEntityName(){
		return IDiscrepencyDAO.ACTUAL_ENTITY_NAME_DISCREPENCY; 
	}
	
	public String getStageEntityName(){
		return IDiscrepencyDAO.STAGING_DISCREPENCY_ENTITY_NAME; 
	}
	
	public IDiscrepency getDiscrepencyById(long id) throws NoSuchDiscrepencyException,
			TrxParameterException, TransactionException {
		IDiscrepency discrepency = new OBDiscrepency();
		try{
			discrepency = (IDiscrepency)getHibernateTemplate().load(getEntityName(), new Long(id));
			return discrepency;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in IDiscrepency ",e);
			e.printStackTrace();
			throw new NoSuchDiscrepencyException("Unable to find Discrepency with id ["+id+"]");
		}		
	}

	public IDiscrepency getDiscrepency(String entity,long id) throws NoSuchDiscrepencyException,
			TrxParameterException, TransactionException {
		IDiscrepency discrepency = new OBDiscrepency();
		try{
			discrepency = (IDiscrepency)getHibernateTemplate().load(entity, new Long(id));
			return discrepency;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in IDiscrepency ",e);
			e.printStackTrace();
			throw new NoSuchDiscrepencyException("Unable to find Discrepency with id ["+id+"]");
		}		
	}
	public SearchResult listDiscrepencyFacility( String entityName,long discrepencyId)throws NoSuchDiscrepencyException {
		try{
			String query = "FROM " + entityName + " discrepency where DISCREPENCY_ID="+discrepencyId;
			ArrayList discrepencyList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, discrepencyList.size(), discrepencyList.size(),discrepencyList);
			
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in listDiscrepency",e);
			e.printStackTrace();
			throw new NoSuchDiscrepencyException("Unable to List the Discrepency");
		}		
	}
	
	public SearchResult listDiscrepency(long customerId)throws NoSuchDiscrepencyException {
		try{
			//String query = "FROM " + getEntityName() + " discrepency where status!='CLOSED' and  CUSTOMER_ID="+customerId;
			
			String query = "FROM " + getEntityName() + " discrepency where   CUSTOMER_ID="+customerId +" order by decode(status, 'ACTIVE', 1, 'DEFERED', 2, 'CLOSED', 3, 'WAIVED', 4, 9)";
			ArrayList discrepencyList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, discrepencyList.size(), discrepencyList.size(),discrepencyList);
			
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in listDiscrepency",e);
			e.printStackTrace();
			throw new NoSuchDiscrepencyException("Unable to List the Discrepency");
		}		
	}
	
	public IDiscrepency createDiscrepency(IDiscrepency anDiscrepency)throws NoSuchDiscrepencyException {		// Approve
		try{
			getHibernateTemplate().save(getEntityName(), anDiscrepency);
		}catch (Exception e) {
			DefaultLogger.error(this, "error in createDiscrepency ",e);
			e.printStackTrace();
			throw new NoSuchDiscrepencyException("Unable to create Discrepency");
		}
		return anDiscrepency;	
	}
	public IDiscrepencyFacilityList createDiscrepencyFacilityList(String entityName,IDiscrepencyFacilityList anDiscrepency)throws NoSuchDiscrepencyException {		// Approve
		try{
			getHibernateTemplate().save(entityName, anDiscrepency);
		}catch (Exception e) {
			DefaultLogger.error(this, "error in createDiscrepency ",e);
			e.printStackTrace();
			throw new NoSuchDiscrepencyException("Unable to create Discrepency");
		}
		return anDiscrepency;	
	}

	public IDiscrepency deleteDiscrepency(IDiscrepency discrepency)throws NoSuchDiscrepencyException, TrxParameterException,TransactionException {
		try{
			getHibernateTemplate().saveOrUpdate(getEntityName(), discrepency);
		return discrepency;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in updateDiscrepency ",e);
			e.printStackTrace();
			throw new NoSuchDiscrepencyException("Unable to update discrepency with id ["+discrepency.getId()+"]");
		}		
	}

	public IDiscrepency updateDiscrepency(IDiscrepency discrepency)throws NoSuchDiscrepencyException, TrxParameterException,TransactionException, ConcurrentUpdateException {
		try{
			System.out.println("IN updateDiscrepency of DiscrepencyDAOImpl" );
			getHibernateTemplate().flush();
			getHibernateTemplate().clear();
			getHibernateTemplate().saveOrUpdate(getEntityName(), discrepency);
			getHibernateTemplate().flush();
			getHibernateTemplate().clear();
			System.out.println("Out updateDiscrepency of DiscrepencyDAOImpl" );
			return discrepency;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in updateDiscrepency ",e);
			e.printStackTrace();
			throw new NoSuchDiscrepencyException("Unable to update discrepency with id ["+discrepency.getId()+"]");
		}
	}
	
	public IDiscrepency updateStageDiscrepency(IDiscrepency discrepency)throws NoSuchDiscrepencyException, TrxParameterException,TransactionException, ConcurrentUpdateException {
		try{
			System.out.println("IN updateStageDiscrepency of DiscrepencyDAOImpl" );
			getHibernateTemplate().flush();
			getHibernateTemplate().clear();
			getHibernateTemplate().saveOrUpdate(getStageEntityName(), discrepency);
			getHibernateTemplate().flush();
			getHibernateTemplate().clear();
			System.out.println("Out updateStageDiscrepency of DiscrepencyDAOImpl" );
			return discrepency;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in updateDiscrepency ",e);
			e.printStackTrace();
			throw new NoSuchDiscrepencyException("Unable to update stage discrepency with id ["+discrepency.getId()+"]");
		}
	}
	
	public IDiscrepency createDiscrepency(String entity, IDiscrepency discrepency)throws NoSuchDiscrepencyException {		//	Create/Edit
		try{
			
			//long counter =1;
			//discrepency.setCounter(counter);
			System.out.println("IN createDiscrepency of DiscrepencyDAOImpl" );
			getHibernateTemplate().flush();
			getHibernateTemplate().clear();
			getHibernateTemplate().save(entity, discrepency);
			getHibernateTemplate().flush();
			getHibernateTemplate().clear();
			System.out.println("Out createDiscrepency of DiscrepencyDAOImpl" );
		}catch (Exception e) {
			e.getCause();
			e.printStackTrace();
			DefaultLogger.error(this, "############# error in createDiscrepency ",e);
			e.printStackTrace();
			throw new NoSuchDiscrepencyException("Unable to create Discrepency ");
		}
		return discrepency;		
	}
	
	
	

}