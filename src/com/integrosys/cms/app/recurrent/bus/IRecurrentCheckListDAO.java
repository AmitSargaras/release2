/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/IRecurrentCheckListDAO.java,v 1.7 2005/01/24 02:12:51 ckchua Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;

/**
 * This interface defines the constant specific to the recurrent checklist table
 * and the methods required by the checklist
 * 
 * @author $Author: ckchua $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/01/24 02:12:51 $ Tag: $Name: $
 */
public interface IRecurrentCheckListDAO extends IRecurrentCheckListTableConstants {
	/**
	 * To get the list of recurrent checklist item history based on the item
	 * reference
	 * @param anItemReference of long type
	 * @return IRecurrentCheckListItem[] - the list of recurrent checklist items
	 * @throw SearchDAOException on errors
	 */
	public IRecurrentCheckListItem[] getRecurrentItemHistory(long anItemReference) throws SearchDAOException;

	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param aLimitProfileID of long type
	 * @parama SubProfileID of long type
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
	 * @parama SubProfileID of long type
	 * @param aStatusList of String[] type
	 * @return RecurrentSearchResult[] - the list of checklist result
	 * @throws SearchDAOException
	 */
	public RecurrentSearchResult[] getCheckListByAnnexureId(long aLimitProfileID, long aSubProfileID, String[] aStatusList, String annexureId)
			throws SearchDAOException;

	public IRecurrentCheckListItem getRecurrentCheckListItem(long anItemReference) throws SearchDAOException;

	// cr26
	public IConvenant[] getConvenantHistory(long anItemReference) throws SearchDAOException;

	public IConvenant getConvenant(long anItemReference) throws SearchDAOException;
	
	public long getRecurrentDocId(long limitProfileId, long subProfileId)throws SearchDAOException,RemoteException;
	
	public String getBankingType(long limitProfileId, long subProfileId)throws RecurrentException,RemoteException;
	
	public void insertAnnexures(ILimitProfile limitProfile)throws RecurrentException,RemoteException;
	
	//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
	public List getRecurrentDocIdDesc(long recurrentDocId, String docType);
	public IRecurrentCheckListSubItem getRecurrentDocStatusDate(long recurrentItemId);
	public String getRecurrentDocDesc(long recurrentItemId,String docType);
	//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
}
