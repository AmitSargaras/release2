package com.integrosys.cms.app.discrepency.bus;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;

	/**
	 * 
	 * @author sandiip.shinde
	 * @since 01-06-2011
	 */

public interface IDiscrepencyDAO {
	
	public final static String ACTUAL_ENTITY_NAME_DISCREPENCY = "actualDiscrepency";
	
	public final static String STAGING_DISCREPENCY_ENTITY_NAME = "stagingDiscrepency";

	public IDiscrepency createDiscrepency(String entityName,IDiscrepency discrepency ) throws NoSuchDiscrepencyException;
		
	public SearchResult listDiscrepency(long customerId) throws NoSuchDiscrepencyException;
	
	public IDiscrepency deleteDiscrepency(IDiscrepency discrepency ) throws NoSuchDiscrepencyException,TrxParameterException,TransactionException;
	
	IDiscrepency getDiscrepencyById(long id) throws NoSuchDiscrepencyException,TrxParameterException,TransactionException;
	
	IDiscrepency getDiscrepency(String entity,long id) throws NoSuchDiscrepencyException,TrxParameterException,TransactionException;
	
	IDiscrepency updateDiscrepency(IDiscrepency discrepency ) throws NoSuchDiscrepencyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	IDiscrepency updateStageDiscrepency(IDiscrepency discrepency ) throws NoSuchDiscrepencyException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	IDiscrepency createDiscrepency(IDiscrepency discrepency )throws NoSuchDiscrepencyException;
	
	public IDiscrepencyFacilityList createDiscrepencyFacilityList(String entityName,IDiscrepencyFacilityList anDiscrepency)throws NoSuchDiscrepencyException;
	
	public SearchResult listDiscrepencyFacility( String entityName,long discrepencyId)throws NoSuchDiscrepencyException ;
	
	
}