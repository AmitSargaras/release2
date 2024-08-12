package com.integrosys.cms.host.eai.document.bus;

import java.util.Vector;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.host.eai.customer.bus.SubProfile;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.Pledgor;

/**
 * Jdbc call to persistent storage to deal with Document Input Checklist module
 * 
 * @author Iwan Satria
 * @since 13.08.2008
 */
public interface IDocumentDaoJdbc {
    public boolean isValidCIFNumberByMainBorrower(String aaNumber, String cifNo) throws SearchDAOException ;
    
    public boolean isValidCIFNumberByCoBorrower(String aaNumber, String cifNo) throws SearchDAOException ;
    
    public boolean isValidCIFNumberByJointBorrower(String aaNumber, String cifNo) throws SearchDAOException ;
    
    public boolean isValidCIFNumberByPledgor(String aaNumber, String cifNo) throws SearchDAOException;
    
	public String getApplicationTypeByAANumber(String aaNumber) throws SearchDAOException;
	
	public String getOrigCountryByAANumber(String aaNumber, String checklistType) throws SearchDAOException;
	
	public String getOrigOrganisationByAANumber(String aaNumber, String checklistType) throws SearchDAOException;
	
	public Vector retrieveCheckListItemsByCheckListID(long checklistID) throws SearchDAOException;
	
	public ApprovedSecurity getCollateralByLOSSecurityID(String losSecurityID) throws SearchDAOException;
	
	public ApprovedSecurity getCollateralByID(long id) throws SearchDAOException;

	public LimitProfile retrieveLimitByAANumber(String aaNumber) throws SearchDAOException;
	
	public CheckListItem retrieveCheckListItemByDocNo(long docNo) throws SearchDAOException;
	
	public CheckListItem[] retrieveNonDeletedCheckListItemByChecklistID(long checklistID) throws SearchDAOException;
	
	public SubProfile retrieveSubProfileByCIFNumber(String cifNumber) throws SearchDAOException;
	
	public long retrieveCheckListIDByMsgCheckList(CheckList checklist) throws SearchDAOException;
	
	public Pledgor retrievePledgorByAAAndCIF(String aaNumber, String cifNumber) throws SearchDAOException;
}
