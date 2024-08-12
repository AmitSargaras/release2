/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBRecurrentCheckListLocalHome.java,v 1.1 2003/08/07 02:35:31 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

//javax

import com.integrosys.base.businfra.search.SearchDAOException;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Home interface for the recurrent checklist entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/07 02:35:31 $ Tag: $Name: $
 */

public interface EBRecurrentCheckListLocalHome extends EJBLocalHome {
	/**
	 * Create a recurrent checklist
	 * @param anIRecurrentCheckList - IRecurrentCheckList
	 * @return EBRecurrentCheckListLocal - the local handler for the created
	 *         recurrent checklist
	 * @throws CreateException if creation fails
	 */
	public EBRecurrentCheckListLocal create(IRecurrentCheckList anIRecurrentCheckList) throws CreateException;

	/**
	 * Find by primary Key, the recurrent checklist ID
	 * @param aPK - Long
	 * @return EBRecurrentCheckList - the local handler for the recurrent
	 *         checklist that has the PK as specified
	 * @throws FinderException
	 */
	public EBRecurrentCheckListLocal findByPrimaryKey(Long aPK) throws FinderException;

	/**
	 * Find by limit profile ID which is the unique key
	 * @param aLimitProfileID of Long type
	 * @param aSubProfileID of Long type
	 * @return EBRecurrentCheckListLocal - the local handler for the recurrent
	 *         checklist that has the limit profile ID as specified
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public EBRecurrentCheckListLocal findByLimitProfileAndSubProfile(Long aLimitProfileID, Long aSubProfileID)
			throws FinderException;

	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @param aStatusList of String[] type
	 * @return RecurrentSearchResult[] - the list of checklist result
	 * @throws SearchDAOException
	 */
	public RecurrentSearchResult[] getCheckList(long aLimitProfileID, long aSubProfileID, String[] aStatusList)
			throws SearchDAOException;
	
	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @param aStatusList of String[] type
	 * @param annexureId of String type
	 * @return RecurrentSearchResult[] - the list of checklist result
	 * @throws SearchDAOException
	 */
	public RecurrentSearchResult[] getCheckListByAnnexureId(long aLimitProfileID, long aSubProfileID, String[] aStatusList, String annexureId)
			throws SearchDAOException;

}