/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.custrelationship.bus;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.InvalidStatementTypeException;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.commodity.common.AmountConversionException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.cci.bus.CCICustomerDAO;
import com.integrosys.base.businfra.search.SearchDAOException;


/**
 * DAO for customer relationship.
 *
 * @author  $Author$<br>
 * @version $Revision$
 * @since   $Date$
 * Tag:     $Name$
 */
public class CustRelationshipDAO{
    private DBUtil dbUtil;

    /**
     * Default Constructor
     */
    public CustRelationshipDAO() {
    }

	protected static final String QUERY_FROM_CUST_RELN =
	        "      FROM SCI_LE_REL where \n" +
		    "	   STATUS = '" + ICMSConstant.ACTIVE + "' \n" +
			"      AND ( ( REL_VALUE = '" + ICMSConstant.RELATIONSHIP_SHAREHOLDER_ENTRY_CODE + "' AND PERCENT_OWN >= 20 ) \n" +
			" 	   OR REL_VALUE = '" + ICMSConstant.RELATIONSHIP_DIRECTOR_ENTRY_CODE + "' \n" +
			"      OR REL_VALUE = '" + ICMSConstant.RELATIONSHIP_KEY_MANAGEMENT_ENTRY_CODE + "' ) \n";	
			
	protected static final String QUERY_SELECT_PARENT_CUST_RELN =
	        "      SELECT PARENT_SUB_PROFILE_ID \n" + 
			QUERY_FROM_CUST_RELN;
		   
	protected static final String QUERY_SELECT_CUST_RELN =
	        "      SELECT CMS_LE_SUB_PROFILE_ID \n" + 
			QUERY_FROM_CUST_RELN;
				
	/**
	 * Used for group svc, getting the customer relationship 
	 */
	protected static final String SELECT_CUSTOMERID_RELATIONSHIP_BY_PARENT_ID_SQL = 
	    " SELECT CMS_LE_SUB_PROFILE_ID, \n" +
	    " REL_VALUE, \n" +
	    " 	(SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'ENT_REL' \n" + 
		" 	AND ENTRY_CODE = REL_VALUE) AS RELATION_NAME, \n" +
		"   ( SELECT LSP_SHORT_NAME FROM SCI_LE_SUB_PROFILE WHERE CMS_LE_SUB_PROFILE_ID = PARENT_SUB_PROFILE_ID) \n" +
		"	AS PARENT_CUST_NAME \n" + 
		" FROM SCI_LE_REL \n" +
		" WHERE PARENT_SUB_PROFILE_ID = ? \n" + 
		" AND STATUS = '" + ICMSConstant.ACTIVE + "' \n" +
		" AND ( ( REL_VALUE = '" + ICMSConstant.RELATIONSHIP_SHAREHOLDER_ENTRY_CODE + "' AND PERCENT_OWN >= 20 ) \n" +
		" OR REL_VALUE = '" + ICMSConstant.RELATIONSHIP_DIRECTOR_ENTRY_CODE + "' \n" +
		" OR REL_VALUE = '" + ICMSConstant.RELATIONSHIP_KEY_MANAGEMENT_ENTRY_CODE + "' ) \n";
	
	/**	    
	     * Get all the customer which has the relationship director, shareholder (with >= 20% holding) and key management of the specific customer.             
	     *
	     * @param subprofileID of type long
	     * @param List of type Long, parent sub profile ID
	     * @throws CustRelationshipException on error when retrieving
	     */		
	public List retrieveShareholderDirector (long subprofileID) throws CustRelationshipException {
		
		List subprofileList = new ArrayList();
		
		subprofileList.add( new Long( subprofileID ) );
		
		return retrieveShareholderDirector( subprofileList, false );
	
	}	
	
	/**
	     * Get all the customer is a director, shareholder (with >= 20% holding) and key management of the specific customer.	   
	     *
	     * @param subprofileID of type long
	     * @param List of type Long, parent sub profile ID
	     * @throws CustRelationshipException on error when retrieving
	     */		
	public List retrieveShareholderDirectorOf (List subprofileList) throws CustRelationshipException {

		return retrieveShareholderDirector( subprofileList, true );
	
	}	
	
	private List retrieveShareholderDirector (List subprofileList, boolean isDownSearch) throws CustRelationshipException {

		boolean found = true;
		
		List memberList = new ArrayList();

		HashMap memberMap = new HashMap();
		HashMap prevSubprofileList = new HashMap();
		HashMap workingSubprofileList = new HashMap();
		
		for (Iterator iterator = subprofileList.iterator(); iterator.hasNext();) {
			Long subprofileID = (Long) iterator.next();			
			workingSubprofileList.put( subprofileID, subprofileID );					
			memberMap.put( subprofileID, subprofileID );					
		}
		
		CCICustomerDAO cciDao = new CCICustomerDAO();
		
		try {
			while( found ) {				
				
				List subprofileIDList = new ArrayList();
				
				if( !workingSubprofileList.isEmpty() ) {
					Collection subprofileIDCol = workingSubprofileList.values();
					
					for (Iterator iterator = subprofileIDCol.iterator(); iterator.hasNext();) {
						Long workingSubprofileID = (Long) iterator.next();
						//get CCI if any
						HashMap cciMap = cciDao.getCommonCustomer( workingSubprofileID.longValue() );
						
						prevSubprofileList.put( workingSubprofileID, workingSubprofileID );
						subprofileIDList.add( workingSubprofileID );
						log("\n put in subprofileIDList = " + workingSubprofileID);
						
						if( !cciMap.isEmpty() ) {
							Collection ccisubprofileIDCol = cciMap.values();
							
							for (Iterator iterator2 = ccisubprofileIDCol.iterator(); iterator2.hasNext();) {
								Long cciSubprofileId = (Long) iterator2.next();
								subprofileIDList.add( cciSubprofileId );
								prevSubprofileList.put( cciSubprofileId, cciSubprofileId );

							}//end for
							
						}//end if ccimap is empty
						
					}//end for
					
				}
				
				
				if( subprofileIDList.size() == 0 ) {
				
					found = false;
					break;
				}
				List relatedSubProfileList = retrieveRelatedSubProfileList( subprofileIDList, isDownSearch );
				
				workingSubprofileList = new HashMap();
				if( relatedSubProfileList != null ) {
					
					for (Iterator iterator = relatedSubProfileList.iterator(); iterator.hasNext();) {
						Long relatedSubProfileId = (Long) iterator.next();
						//not found in prev list
						if( prevSubprofileList.get( relatedSubProfileId ) == null ){
							log("\n put in workingSubprofileList = " + relatedSubProfileId);
							workingSubprofileList.put( relatedSubProfileId, relatedSubProfileId );
						}
						prevSubprofileList.put( relatedSubProfileId, relatedSubProfileId );

						memberMap.put( relatedSubProfileId, relatedSubProfileId );
					}
				}
				else {
					found = false;
				}			
				
			}
			
			if( !memberMap.isEmpty() ) {
				Collection subprofileIDCol = memberMap.values();
				
				for (Iterator iterator = subprofileIDCol.iterator(); iterator.hasNext();) {
					Long workingSubprofileID = (Long) iterator.next();
					memberList.add( workingSubprofileID );
				}
			}
			return memberList;
		
		} catch (SearchDAOException e) {
            DefaultLogger.error(this, "", e);
            throw new CustRelationshipException("CustRelationshipDAO Error in retrieveParentSubProfileList ", e);
        }
	
	}	
	
    private List retrieveRelatedSubProfileList (List subprofileList, boolean isDownSearch) throws CustRelationshipException {
        
		List subprofileIDList = new ArrayList();
		ResultSet rs = null;
        StringBuffer strBuf = new StringBuffer();
        StringBuffer andCondition = new StringBuffer();
        String fieldName = "";
		
		if( isDownSearch ) {
		
			strBuf.append( QUERY_SELECT_CUST_RELN );
					
			andCondition.append(" AND PARENT_SUB_PROFILE_ID ");
			
			fieldName = "CMS_LE_SUB_PROFILE_ID";
			
		} else {
			strBuf.append( QUERY_SELECT_PARENT_CUST_RELN );
					
			andCondition.append(" AND CMS_LE_SUB_PROFILE_ID ");
				
			fieldName = "PARENT_SUB_PROFILE_ID";
		}
		
        ArrayList params = new ArrayList();
        try {
            dbUtil = new DBUtil();
            CommonUtil.buildSQLInList(subprofileList, andCondition, params);
            log("\nparams = " + params);
            log("\nsubprofileList = " + subprofileList);
            strBuf.append(andCondition);
            log("\nstrBuf = " + strBuf);
						
            dbUtil.setSQL(strBuf.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();

			while (rs.next()){			
				
	            long parentId = rs.getLong(fieldName);
				
				subprofileIDList.add( new Long( parentId ) );
			}
			
			return subprofileIDList;
			
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new CustRelationshipException("CustRelationshipDAO Error in retrieveRelatedSubProfileList ", e);
        } finally {
            finalize(dbUtil);
            finalize(rs);
        }
    }	
    	
	/**
       * Helper method to clean up database resources.
       *
       * @param dbUtil database utility object
       */
    private static void finalize(DBUtil dbUtil, ResultSet rs) {
          try {
              if (null != rs) {
                  rs.close();
              }
          } catch (Exception e1) {
          }

          try {
              if (dbUtil != null) {
                  dbUtil.close();
              }
          }   catch (Exception e2) {
          }
    }
	 
    private void finalize(DBUtil dbUtil)
            throws CustRelationshipException {
        try {
            if (dbUtil != null)
                dbUtil.close();
        } catch (Exception e) {
            throw new CustRelationshipException("Error in cleaning up DB resources: " + e.toString());
        }
    }
    private void finalize(ResultSet rs)
            throws CustRelationshipException {
        try {
            if (rs != null)
                rs.close();
        } catch (Exception e) {
            throw new CustRelationshipException("Error in cleaning up DB resources: " + e.toString());
        }
    }
    
    private void log(String str){
        DefaultLogger.debug(this, str);
    }

    
	/**
	 * Get customer ids that belongs to the parent sub profile ids
	 * in terms of share holder, directors or key management
	 * @param parentSubProfileId
	 * @return list of customer id
	 */
	public List getCustomerRelationshipIds(long parentSubProfileId) {

		ResultSet rs = null;
		DBUtil dbUtil = null;
		List customerIdList = null;

		StringBuffer querySQL = new StringBuffer();
		SQLParameter params = SQLParameter.getInstance();
		querySQL.append(SELECT_CUSTOMERID_RELATIONSHIP_BY_PARENT_ID_SQL);

		log(querySQL.toString());
		params.addLong(new Long(parentSubProfileId));

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(querySQL.toString());
			CommonUtil.setSQLParams(params, dbUtil);
			rs = dbUtil.executeQuery();
			customerIdList = this.processCustomerRelationshipIds(rs);
		} catch (Exception e) {
			DefaultLogger.error(this, "", e);
		} finally {
			finalize(dbUtil, rs);
		}

		return customerIdList;
	}

	/**
	 * Capture the customer ids that relate to the parent sub profile id
	 * @param rs resultset
	 * @return list of captured customer id
	 * @see com.integrosys.cms.app.custexposure.bus.group.GroupExposureDAO#getCustomerRelationshipIds
	 */
	private List processCustomerRelationshipIds(ResultSet rs) {

		List list = new ArrayList();

		try {
			while (rs.next()) {
				List innerList = new ArrayList();
				innerList.add(new Long(rs.getLong("cms_le_sub_profile_id")));
				innerList.add(rs.getString("RELATION_NAME"));
				innerList.add(rs.getString("PARENT_CUST_NAME"));
				
				list.add(innerList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		log("processCustomerIds list size :  " + list.size());
		return list;
	}
}

