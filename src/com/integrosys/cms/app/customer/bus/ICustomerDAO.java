/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/ICustomerDAO.java,v 1.5 2006/08/01 02:55:49 jzhai Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.aurionpro.clims.rest.dto.CoBorrowerDetailsRestRequestDTO;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;

import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.ws.dto.CoBorrowerDetailsRequestDTO;

/**
 * DAO for customer
 * @author $Author: jzhai $
 * @version $Revision: 1.5 $
 * @since $Date: 2006/08/01 02:55:49 $ Tag: $Name: $
 */

public interface ICustomerDAO {
	/**
	 * Search for a list of customer documents based on the criteria
	 * @param criteria is of type CustomerSearchCriteria
	 * @return SearchResult - the object that contains a list of
	 *         ICustomerSearchResult objects or null if no records are found.
	 * @throws SearchDAOException if errors
	 */
	public SearchResult searchCustomer(CustomerSearchCriteria criteria) throws SearchDAOException;

     /**
	 * Retrieve the CMS Customer ID, given the SCI LE ID and SCI SubProfile ID
	 * 
	 * @return long
	 * @throws SearchDAOException if no records found
	 * @throws CustomerException on errors
	 */
	public long searchCustomerID(long leid, long subProfileID) throws CustomerException, SearchDAOException;

	/**
	 * Retrieve the CMS Customer ID, given the CIF number and CIF source id
	 * 
	 * @return long
	 * @throws SearchDAOException if no records found
	 * @throws CustomerException on errors
	 */
	public long searchCustomerByCIFNumber(String cifNumber, String sourceSystemId) throws CustomerException,
			SearchDAOException;

	
	
	
	public List searchCustomerByCIFNumber(String cifNumber, String sourceSystemId,String partyName, String partyId) throws CustomerException,
	SearchDAOException;
	
	public List searchCustomerByCIFNumber(String cifNumber) throws CustomerException,
	SearchDAOException;
	
	public List searchCustomerByCustomerId(String cifNumber) throws CustomerException,
	SearchDAOException;

	public List getAllSystemAndSystemId() throws CustomerException,
	SearchDAOException;
	
	public List getvendorList(String custId) throws SearchDAOException;
	/**
    * Retrieve the CMS Customer ID, given the ID number
    *
   * @param idNumber of type String
    * @return Long
    * @throws SearchDAOException if no records found
    * @throws CustomerException on errors
    */
    public Long searchCustomerByIDNumber(String idNumber) throws CustomerException, SearchDAOException;

	public ArrayList getMainBorrowerListByCoBorrowerLeId(long cbLeId) throws CustomerException, SearchDAOException;

    public ArrayList getMBlistByCBleId (long cbLeId) throws CustomerException,SearchDAOException;
	/**
	 * Get list of mailing details for a list of limit profile IDs. One official
	 * address per limit profile ID if available.
	 * 
	 * @param sciLimitProfileIDList - List
	 * @return Map - (limitProfileID, OBCustomerMailingDetails)
	 * @throws SearchDAOException if errors
	 */
	public Map getCustomerMailingDetails(List sciLimitProfileIDList) throws SearchDAOException;

	public Map getFamcodeCustNameByCustomer(List securityLimitMapList) throws SearchDAOException;

	public Collection getMBInfoByLimitIDList(Collection limitIDList) throws SearchDAOException;

	public Collection getCBInfoByLimitIDList(Collection limitIDList) throws SearchDAOException;

 	/**
     * Get list of customer main details for a list of sub profile IDs.
     *
     * @param subProfileIDList List of sub profile ID (Long)
     * @return Map - (subProfileID (Long), ICMSCustomer)
     * @throws SearchDAOException if errors
     */
    public Map getCustomerMainDetails(List subProfileIDList) throws SearchDAOException;
    
    public boolean isCustomerNameUnique(String partyName);
    
    public String searchPartyIDBySysID(String cifNumber, String sourceSystemId) throws CustomerException,
	SearchDAOException;
    
	public List getTransactionHistoryList(String transactionId) throws SearchDAOException;

	//Uma Khot:start:Added to update wrong data of security 10000 records
	public void updateGcInsurance() throws Exception;
	//Uma Khot:end:Added to update wrong data of security 10000 records
// Added By Dayananda Laishram : For CR Bill As colleteral validation on 05-May-2015 | Starts
	public Date getCamStartDate(String llPLeIdForCam);
// Added By Dayananda Laishram : For CR Bill As colleteral validation on 05-May-2015 | Ends
	
	public Boolean checkSystemExists(String systemName,String systemId) throws SearchDAOException, DBConnectionException, SQLException;
	
	public Boolean checkVendorExists(String vendorName) throws SQLException;
	
	public Boolean checkSystemExistsInOtherParty(String systemName,String systemId,String partyId) throws SearchDAOException, DBConnectionException, SQLException;
	
	public Boolean checkVendorExistsInSameParty(String vendorName,String partyId) throws SQLException ;
	
	public List getCollateralMappedCustomerLimitIdList(Long collateralId) throws Exception;

	public List<String> getPanDetails(String partyId,String pan)throws SearchDAOException, DBConnectionException, SQLException;
	
	public List<String> checkIfPANExistsInOtherParty(String partyId,String pan) throws SearchDAOException, DBConnectionException, SQLException;
	
	public String getPartySegment(String limitProfileId);
	public String getPartyCapitalMarExp(String limitProfileId);
	public List<LabelValueBean> getMainLineCode(String limitProfileId,String subPartyName, String facilitySystem,String systemId);
	
	public List getTransactionCAMHistoryList(String transactionID, String string);
	
	
	public List getAllRamratingDocumentlist();
	public String getRamYear(String llpLeId);
	public String getChecklistId(long limitProfileID);
	public String getLimitProfileID(String partyID);
	public void updateRamDueDate(String checkListId,String ramDueDate,String status);

	public String getSegment1(String partySegment);
	public String getPartyId(String limitProfileIDStr);
	
	public String getIfscCodeList(String table,String stageID);
	public SearchResult searchCustomerImageUpload(CustomerSearchCriteria criteria) throws SearchDAOException;

	public int checkPanStatus (String partyId,String panNo,String partyNameAsPerPan, String dateOfIncorporation);
	
	public int checkLeiStatus (String partyId,String leiCode);

	
	public  void updatePartyRMDetails(String empCode, IRegion region);


	void updateSanctionedAmountUpdatedFlag(String partyId, String updateFlag);
	String getSanctionedAmtUpdatedFlag(long customerId);
	String getCustomerSegmentByLimitProfileId(long limitProfileId);
	String getCustomerSegmentByCustomerId(long customerId);
	
	public List<String> getCoBorrowerLiabId(String partyId);	

	public List<CoBorrowerDetailsRequestDTO> getCoBorrowerListWS(String partyId);
	public List<CoBorrowerDetailsRestRequestDTO> getCoBorrowerListWSRest(String partyId);
	
	public Date getCamExpiryORExtensionDate(int i, String partyId);
	
	public List getScfAndEcbfStatusById(String module, String type, String id);

	public List getUpdatedResReqUsingLineId(String lineId);

	public int getLineCount(String lineId);

	public int getSIDCount(long sID);

	public String getDocumentCount();

	public String getfileuploadidFromSeq();

}