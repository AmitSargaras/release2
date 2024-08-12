/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.exception.ChainedException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.commodity.common.AmountConversionException;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierDAO;
import com.integrosys.cms.app.custrelationship.bus.CustRelationshipDAO;
import com.integrosys.cms.app.custrelationship.bus.CustRelationshipException;
import com.integrosys.base.businfra.search.SearchDAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;


/**
 * DAO for limit booking.
 *
 * @author  $Author$<br>
 * @version $Revision$
 * @since   $Date$
 * Tag:     $Name$
 */
public class LimitBookingDAO{
    private DBUtil dbUtil;

    /**
     * Default Constructor
     */
    public LimitBookingDAO() {
    }

	
	private static final String SEARCH_BOOKING_SQL =
                             "SELECT bk.LMT_BK_ID, TICKET_NO, AA_NO, AA_SOURCE, BK_NAME, BK_ID_NO,\n" +
                                     "       BK.BK_CURR, BK.BK_AMT, BK_CTRY, BK_BUS_UNIT, BK_STATUS, DATE_CREATED,\n" +
                                     "       EXPIRY_DATE, LAST_MODIFIED_BY, OVERALL_RESULT, GRP_NAME\n" +
                                     "  FROM cms_lmt_book bk,\n" +
                                     "       cms_lmt_book_dtl dtl LEFT OUTER JOIN cms_cust_grp\n" +
                                     "       ON (char (cms_cust_grp.GRP_ID) = dtl.BK_TYPE_CODE \n" +
                                     "         AND dtl.bk_type_cat ='" + ICMSConstant.BKG_TYPE_BGEL + "')\n" +
                                     " WHERE bk.LMT_BK_ID = dtl.LMT_BK_ID\n" +
                                     //"   AND bk_status <> '" + ICMSConstant.STATUS_LIMIT_BOOKING_DELETED + "'\n" +
                                     "   AND dtl.LMT_BK_DETAIL_ID \n" +
                                     "       IN (SELECT max (dtl1.LMT_BK_DETAIL_ID)\n" +
                                     "            FROM cms_lmt_book_dtl dtl1\n" +
                                     "           WHERE dtl1.LMT_BK_ID = dtl.LMT_BK_ID\n" +							 
                                     "           AND dtl1.BK_TYPE_CAT ='" + ICMSConstant.BKG_TYPE_BGEL + "' \n" ;									 

									 
	private static final String SEARCH_BOOKING_SQL2 =                             
									 "SELECT bk.LMT_BK_ID, TICKET_NO, AA_NO, AA_SOURCE, BK_NAME, BK_ID_NO,\n" +
                                     "       BK.BK_CURR, BK.BK_AMT, BK_CTRY, BK_BUS_UNIT, BK_STATUS, DATE_CREATED,\n" +
                                     "       EXPIRY_DATE, LAST_MODIFIED_BY, OVERALL_RESULT, '' GRP_NAME\n" +
                                     "  FROM cms_lmt_book bk \n" +
									 "  WHERE " +
									 //"  bk_status <> '" + ICMSConstant.STATUS_LIMIT_BOOKING_DELETED + "'\n" +
									 "  NOT EXISTS (select '1' from cms_lmt_book_dtl \n" +
									 "  WHERE bk.LMT_BK_ID = LMT_BK_ID AND BK_TYPE_CAT = '" + ICMSConstant.BKG_TYPE_BGEL + "') \n";

	private static final String SEARCH_GROUP_EXISTS_IN_BOOKING =                             
									 "SELECT count(*) \n" +
                                     "  FROM cms_lmt_book bk, cms_lmt_book_dtl dt \n" +
									 "  WHERE " +
									 "  bk_status = '" + ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED + "'\n" +
									 "  AND bk.LMT_BK_ID = dt.LMT_BK_ID ";

    private static final String SEARCH_BOOKING_ORDER_BY_SQL = "ORDER BY TICKET_NO DESC "  ;
	 
	private static final String QUERY_EXPOSURE_COUNTRY =
    	 "select exposure_amt_curr, exposure_amt \n" +
                " from cms_aggr_exp_ctr \n" +
                " where ctr = ? ";
	
	private static final String QUERY_EXPOSURE_ENTITY = 
		" select sum(EXPOSURE_AMT) as EXPOSURE_AMT, EXPOSURE_AMT_CURR from CMS_AGGR_EXP_ENTITY \n " +
				"  where CMS_LE_SUB_PROFILE_ID = ? \n " +                         
				"  group by EXPOSURE_AMT_CURR ";   
				
	/*private static final String QUERY_LIMIT_BOOKED_COUNTRY =
    	 "SELECT b.LMT_BK_ID, b.TICKET_NO, b.AA_NO, b.AA_SOURCE, b.BK_NAME, b.BK_BUS_UNIT, " +
		 "b.DATE_CREATED, b.LAST_MODIFIED_BY, b.BK_CURR, b.BK_AMT " +
                "from CMS_LMT_BOOK b, CMS_LMT_BOOK_DTL d " +
                "where b.LMT_BK_ID = d.LMT_BK_ID and " +
				"d.BK_TYPE_CAT = '" + ICMSConstant.BKG_TYPE_COUNTRY + 
				"' and b.BK_STATUS = '" + ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED + "' " +
				"and d.BK_TYPE_CODE = ? ";
			*/	
			
	private static final String QUERY_SELECT_LIMIT_BOOKED =
		"SELECT LMT_BK_ID, TICKET_NO, AA_NO, AA_SOURCE, BK_NAME, BK_BUS_UNIT, " +
		"DATE_CREATED, LAST_MODIFIED_BY, BK_CURR, BK_AMT " +
		"from CMS_LMT_BOOK b " +
		"where " +
		"BK_STATUS = '" + ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED + "' ";
								
    private static final String QUERY_SELECT_LIMIT_BOOKED_2 =
        "SELECT CMS_LE_SUB_PROFILE_ID, LMT_BK_ID " +
        "from CMS_LMT_BOOK b " +
        "where " +
        "BK_STATUS = '" + ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED + "' ";

	private static HashMap bankEntityBkgSubTypeMap = new HashMap();

	static
	{
		bankEntityBkgSubTypeMap.put(ICMSConstant.BKG_SUB_TYPE_CONV, ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE);
		bankEntityBkgSubTypeMap.put(ICMSConstant.BKG_SUB_TYPE_ISLM, ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE);
		bankEntityBkgSubTypeMap.put(ICMSConstant.BKG_SUB_TYPE_INV, ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE);
		bankEntityBkgSubTypeMap.put(ICMSConstant.BKG_SUB_TYPE_BANK, ICMSConstant.BANKING_GROUP_ENTRY_CODE);
		bankEntityBkgSubTypeMap.put(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_CONV, ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE);
		bankEntityBkgSubTypeMap.put(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_ISLM, ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE);
		bankEntityBkgSubTypeMap.put(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_INV, ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE);
		bankEntityBkgSubTypeMap.put(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_BG, ICMSConstant.BANKING_GROUP_ENTRY_CODE);
		bankEntityBkgSubTypeMap.put(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_CONV, ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE);
		bankEntityBkgSubTypeMap.put(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_ISLM, ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE);
		bankEntityBkgSubTypeMap.put(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_INV, ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE);
		bankEntityBkgSubTypeMap.put(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_BG, ICMSConstant.BANKING_GROUP_ENTRY_CODE);
	}
	
	private static List bankEntityBkgSubType = new ArrayList();

	static
	{
		bankEntityBkgSubType.add( ICMSConstant.BKG_SUB_TYPE_BANK );
		bankEntityBkgSubType.add( ICMSConstant.BKG_SUB_TYPE_CONV );
		bankEntityBkgSubType.add( ICMSConstant.BKG_SUB_TYPE_ISLM );
		bankEntityBkgSubType.add( ICMSConstant.BKG_SUB_TYPE_INV );
		bankEntityBkgSubType.add( ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_BG );
		bankEntityBkgSubType.add( ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_CONV );
		bankEntityBkgSubType.add( ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_ISLM );
		bankEntityBkgSubType.add( ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_INV );
		bankEntityBkgSubType.add( ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_BG );
		bankEntityBkgSubType.add( ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_CONV );
		bankEntityBkgSubType.add( ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_ISLM );
		bankEntityBkgSubType.add( ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_INV );
	}			
		
			
	/**
	 * Country Limit Dimension.
	 *
	 */			
	public List retrieveCountryBookingResult(Long lmtBookingID, ILimitBookingDetail lmtBookDtls) throws LimitBookingException, AmountConversionException {
        
		ArrayList col = new ArrayList();
		Object[] exposureCountry = getExposureCountry( lmtBookDtls.getBkgTypeCode() );
		Object[] totalBookCountry = getTotalBookedCountry( lmtBookingID, lmtBookDtls.getBkgTypeCode() );
		
		if( exposureCountry != null ) {
			lmtBookDtls.setCurrentExposure( LimitBookingHelper.convertBaseAmount( (Amount)exposureCountry[0] ) );
        }
		else {
			lmtBookDtls.setCurrentExposure(null);
		}
		if( totalBookCountry != null ) {
			lmtBookDtls.setTotalBookedAmount( LimitBookingHelper.convertBaseAmount( (Amount)totalBookCountry[0] ) );        
	        lmtBookDtls.setBookedLimitList( (List)totalBookCountry[1] ); 
            log("\n totalBookCountry in retrieveCountryBookingResult =  "+(List)totalBookCountry[1]  );
		}
		else {
			lmtBookDtls.setTotalBookedAmount(null);        
	        lmtBookDtls.setBookedLimitList(null); 
		}
		
        col.add(lmtBookDtls);
		
        return col;
    }
	
	//internal method
	private Object[] getExposureCountry (String country) throws LimitBookingException, AmountConversionException {
        return retrieveCountryLimit( null, country, true );
    }
	
	private Object[] getTotalBookedCountry (Long lmtBookingID, String country) throws LimitBookingException, AmountConversionException {
        return retrieveCountryLimit( lmtBookingID, country, false );
    }
    
    /**
	 * 
	 *
	 */
    public List retrieveBankWideCustomerBookingResult(ILimitBooking lmtBooking, Long customerSubProfileID, Map cciMap, List bankWideCustomer, String bankEntity) throws LimitBookingException, AmountConversionException {
        
    	HashMap exposureBwBe = getExposureBankWideCustomer(customerSubProfileID, cciMap, bankEntity);
    	HashMap totalBookBwBe = getTotalBookedBankWideCustomer(lmtBooking, customerSubProfileID, cciMap, bankEntity);
		 
		for (Iterator itr = bankWideCustomer.iterator(); itr.hasNext();) {
			OBLimitBookingDetail bwBeDtl = (OBLimitBookingDetail) itr.next();

			//get exposure
			
			if (exposureBwBe != null) {
				Amount amt = (Amount) exposureBwBe.get( customerSubProfileID );
				
				if( cciMap != null && !cciMap.isEmpty() ) {
					Collection cciSubprofileList = cciMap.values(); 	

					for (Iterator itr1 = cciSubprofileList.iterator(); itr1.hasNext();) {		
						Long cciSubprofileID = (Long) itr1.next();
						amt = CommonUtil.addAmount(amt, (Amount) exposureBwBe.get( cciSubprofileID ));
					}						
				}
				
				if ( amt != null ){						
					bwBeDtl.setCurrentExposure( LimitBookingHelper.convertBaseAmount( amt ) );                  
				}
				else {
					bwBeDtl.setCurrentExposure( null ); 
				}
			}
			
			//get booked result
			
			if (totalBookBwBe != null) {
				Object[] bookResult = (Object[]) totalBookBwBe.get( bwBeDtl.getBkgTypeCode() );
				
				if ( bookResult != null ){
					bwBeDtl.setTotalBookedAmount( LimitBookingHelper.convertBaseAmount( (Amount)bookResult[0] ) );
					bwBeDtl.setBookedLimitList( (List)bookResult[1] );
				}
				else {
					bwBeDtl.setTotalBookedAmount( null );
					bwBeDtl.setBookedLimitList( null );
				}
			}
			
		} //end for          
        				
		return bankWideCustomer;        
    }
    
	private Object[] retrieveCountryLimit (Long lmtBookingID, String country, boolean isExposure) throws LimitBookingException, AmountConversionException {
        
		Object[] result = null;
		ResultSet rs = null;
        StringBuffer strBuf = new StringBuffer();
		
		if( isExposure ) {
			strBuf.append( QUERY_EXPOSURE_COUNTRY );

		} else {
	        strBuf.append( QUERY_SELECT_LIMIT_BOOKED );
	        strBuf.append(" AND BK_CTRY = ? ");
			
			if( lmtBookingID != null ) {
				strBuf.append(" AND LMT_BK_ID <> ? ");			
			}
	        
		}		
				
        try {
            dbUtil = new DBUtil();
						
            log("\n country =  "+country +" \n selectSQL = " + strBuf.toString() );
            dbUtil.setSQL( strBuf.toString() );
            dbUtil.setString(1, country);
			
			if( lmtBookingID != null ) {
				dbUtil.setLong(2, lmtBookingID.longValue());
			}
			
            rs = dbUtil.executeQuery();
			
			if( isExposure ) {
				Amount amt = processExposure(rs);
				result = new Object[]{ amt };
				
			}
			else {
				result = processBookedLimit( rs );
			}

            return result;
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new LimitBookingException("LimitBookingDAO Error in retrieveCountryLimit ", e);
        } finally {
            finalize(dbUtil);
            finalize(rs);
        }
    }
	
public List retrieveProductProgramBookingResult(Long lmtBookingID, List polBooking) throws LimitBookingException, AmountConversionException {
        
		List productProgram = getProductProgramList(polBooking);
		
		HashMap ecoExposurePOL = getProductProgramExposureList(productProgram);
		
		HashMap ecoTotalBookPOL = getProductProgramTotalBookedList( lmtBookingID, productProgram);
		
		for (Iterator itr = polBooking.iterator(); itr.hasNext();) {
			OBLimitBookingDetail polDtl = (OBLimitBookingDetail) itr.next();

			//get exposure
			Amount ecoAmt = (Amount) ecoExposurePOL.get( polDtl.getBkgProdTypeCode() );
			
			if (ecoAmt != null && polDtl.getBkgType().equals(ICMSConstant.BKG_TYPE_PROD_PROG)){						
				polDtl.setCurrentExposure( LimitBookingHelper.convertBaseAmount( ecoAmt ) );                  
			}
			else {
				polDtl.setCurrentExposure( null ); 
			}
			
			//get booked result
			Object[] childBookResult = (Object[]) ecoTotalBookPOL.get( polDtl.getBkgProdTypeCode() );
			
			if (childBookResult != null && polDtl.getBkgType().equals(ICMSConstant.BKG_TYPE_PROD_PROG)){
				polDtl.setTotalBookedAmount( LimitBookingHelper.convertBaseAmount( (Amount)childBookResult[0] ) );
				polDtl.setBookedLimitList( (List)childBookResult[1] );
			}
			else {
				polDtl.setTotalBookedAmount( null );
				polDtl.setBookedLimitList( null );
			}

		} //end for          
        				
		return polBooking;        
    } 
	
	/**
	 * Loan Sector Dimension.
	 *
	 */
    public List retrieveEcoLoanSectorBookingResult(Long lmtBookingID, List polBooking, String bankEntity) throws LimitBookingException, AmountConversionException {
        
		List ecoPol = getEcoPOLList(polBooking);
		
		HashMap ecoExposurePOL = getEcoExposurePOL( ecoPol, bankEntity );
		
		HashMap ecoTotalBookPOL = getEcoTotalBookedPOL( lmtBookingID, ecoPol, bankEntity );
		
		for (Iterator itr = polBooking.iterator(); itr.hasNext();) {
			OBLimitBookingDetail polDtl = (OBLimitBookingDetail) itr.next();

			//get exposure
			Amount ecoAmt = (Amount) ecoExposurePOL.get( polDtl.getBkgTypeCode() );
			
			if (ecoAmt != null && polDtl.getBkgType().equals(ICMSConstant.BKG_TYPE_ECO_POL)){						
				polDtl.setCurrentExposure( LimitBookingHelper.convertBaseAmount( ecoAmt ) );                  
			}
			else {
				polDtl.setCurrentExposure( null ); 
			}
			
			//get booked result
			Object[] childBookResult = (Object[]) ecoTotalBookPOL.get( polDtl.getBkgTypeCode() );
			
			if (childBookResult != null && polDtl.getBkgType().equals(ICMSConstant.BKG_TYPE_ECO_POL)){
				polDtl.setTotalBookedAmount( LimitBookingHelper.convertBaseAmount( (Amount)childBookResult[0] ) );
				polDtl.setBookedLimitList( (List)childBookResult[1] );
			}
			else {
				polDtl.setTotalBookedAmount( null );
				polDtl.setBookedLimitList( null );
			}

		} //end for          
        				
		return polBooking;        
    } 
	 
	/**
	 * Loan Sector Dimension.
	 *
	 */
    public List retrieveSubLoanSectorBookingResult(Long lmtBookingID, List polBooking, String bankEntity) throws LimitBookingException, AmountConversionException {
        
    	List subPol = getSubPOLList(polBooking);
		
		HashMap subExposurePOL = getSubExposurePOL( subPol, bankEntity );
		
		HashMap subTotalBookPOL = getSubTotalBookedPOL( lmtBookingID, subPol, bankEntity );
        
		for (Iterator itr = polBooking.iterator(); itr.hasNext();) {
			OBLimitBookingDetail polDtl = (OBLimitBookingDetail) itr.next();

			//get exposure
			Amount subAmt = (Amount) subExposurePOL.get( polDtl.getBkgTypeCode() );
			
			if (subAmt != null && polDtl.getBkgType().equals(ICMSConstant.BKG_TYPE_SUB_POL)){						
				polDtl.setCurrentExposure( LimitBookingHelper.convertBaseAmount( subAmt ) );                  
			}
			else {
				polDtl.setCurrentExposure( null ); 
			}
			
			//get booked result
			Object[] parentBookResult = (Object[]) subTotalBookPOL.get( polDtl.getBkgTypeCode() );
			
			if (parentBookResult != null && polDtl.getBkgType().equals(ICMSConstant.BKG_TYPE_SUB_POL)){
				polDtl.setTotalBookedAmount( LimitBookingHelper.convertBaseAmount( (Amount)parentBookResult[0] ) );
				polDtl.setBookedLimitList( (List)parentBookResult[1] );
			}
			else {
				polDtl.setTotalBookedAmount( null );
				polDtl.setBookedLimitList( null );
			}

		} //end for          
        				
		return polBooking;          
    } 
      
    /**
	 * Loan Sector Dimension.
	 *
	 */
    public List retrieveMainLoanSectorBookingResult(Long lmtBookingID, List polBooking, String bankEntity) throws LimitBookingException, AmountConversionException {
        
		List mainPol = getMainPOLList(polBooking);
		
		HashMap mainExposurePOL = getMainExposurePOL( mainPol, bankEntity );
		
		HashMap mainTotalBookPOL = getMainTotalBookedPOL( lmtBookingID, mainPol, bankEntity );
        
		for (Iterator itr = polBooking.iterator(); itr.hasNext();) {
			OBLimitBookingDetail polDtl = (OBLimitBookingDetail) itr.next();

			//get exposure
			Amount mainAmt = (Amount) mainExposurePOL.get( polDtl.getBkgTypeCode() );
			
			if (mainAmt != null && polDtl.getBkgType().equals(ICMSConstant.BKG_TYPE_MAIN_POL)){						
				polDtl.setCurrentExposure( LimitBookingHelper.convertBaseAmount( mainAmt ) );                  
			}
			else {
				polDtl.setCurrentExposure( null ); 
			}
			
			//get booked result
			Object[] mainBookResult = (Object[]) mainTotalBookPOL.get( polDtl.getBkgTypeCode() );
			
			if (mainBookResult != null && polDtl.getBkgType().equals(ICMSConstant.BKG_TYPE_MAIN_POL)){
				polDtl.setTotalBookedAmount( LimitBookingHelper.convertBaseAmount( (Amount)mainBookResult[0] ) );
				polDtl.setBookedLimitList( (List)mainBookResult[1] );
			}
			else {
				polDtl.setTotalBookedAmount( null );
				polDtl.setBookedLimitList( null );
			}

		} //end for          
        				
		return polBooking;        
    }
    
    private List getProductProgramList(List pol)
    {
        List list = new ArrayList();
        
        if (pol != null){
	        for (Iterator iterator = pol.iterator(); iterator.hasNext();) {
	            OBLimitBookingDetail dtl = (OBLimitBookingDetail) iterator.next();
	            if (dtl != null && dtl.getBkgType().equals(ICMSConstant.BKG_TYPE_PROD_PROG)){
	                // Get the list of Product Program to get Exposure
	                String polCode = dtl.getBkgProdTypeCode();
	                list.add(polCode);
	            }
	        }
		}
        return list;
    }
    	
	private List getMainPOLList(List pol)
    {
        List list = new ArrayList();
        
        if (pol != null){
	        for (Iterator iterator = pol.iterator(); iterator.hasNext();) {
	            OBLimitBookingDetail dtl = (OBLimitBookingDetail) iterator.next();
	            if (dtl != null && dtl.getBkgType().equals(ICMSConstant.BKG_TYPE_MAIN_POL)){
	                // Get the list of POL to get Exposure
	                String polCode = dtl.getBkgTypeCode();
	                list.add(polCode);
	            }
	        }
		}
        return list;
    }
	
	private List getEcoPOLList(List pol)
    {
        List list = new ArrayList();
        
        if (pol != null){
	        for (Iterator iterator = pol.iterator(); iterator.hasNext();) {
	            OBLimitBookingDetail dtl = (OBLimitBookingDetail) iterator.next();
	            if (dtl != null && dtl.getBkgType().equals(ICMSConstant.BKG_TYPE_ECO_POL)){
	                // Get the list of POL to get Exposure
	                String polCode = dtl.getBkgTypeCode();
	                list.add(polCode);
	            }
	        }
		}
        return list;
    }
	
	private List getSubPOLList(List pol)
    {
        List list = new ArrayList();
        
        if (pol != null){
	        for (Iterator iterator = pol.iterator(); iterator.hasNext();) {
	            OBLimitBookingDetail dtl = (OBLimitBookingDetail) iterator.next();
	            if (dtl != null && dtl.getBkgType().equals(ICMSConstant.BKG_TYPE_SUB_POL)){
	                // Get the list of POL to get Exposure
	                String polCode = dtl.getBkgTypeCode();
	                list.add(polCode);
	            }
	        }
		}
        return list;
    }
	
	private HashMap getProductProgramExposureList (List pol) throws LimitBookingException, AmountConversionException {
        return retrieveProductProgramLimit( null, pol, true);
    }
	
	private HashMap getEcoExposurePOL (List pol, String bankEntity) throws LimitBookingException, AmountConversionException {
        return retrieveEcoSectoralLimit( null, pol, bankEntity, true);
    }
	
	private HashMap getSubExposurePOL (List pol, String bankEntity) throws LimitBookingException, AmountConversionException {
        return retrieveSubSectoralLimit( null, pol, bankEntity, true);
    }
	
	private HashMap getMainExposurePOL (List pol, String bankEntity) throws LimitBookingException, AmountConversionException {
        return retrieveMainSectoralLimit( null, pol, bankEntity, true);
    }
	
	private HashMap getProductProgramTotalBookedList (Long lmtBookingID, List pol) throws LimitBookingException, AmountConversionException {
        return retrieveProductProgramLimit( lmtBookingID, pol, false);
    }

	private HashMap getEcoTotalBookedPOL (Long lmtBookingID, List pol, String bankEntity) throws LimitBookingException, AmountConversionException {
        return retrieveEcoSectoralLimit( lmtBookingID, pol, bankEntity, false);
    }
	
	private HashMap getSubTotalBookedPOL (Long lmtBookingID, List pol, String bankEntity) throws LimitBookingException, AmountConversionException {
        return retrieveSubSectoralLimit( lmtBookingID, pol, bankEntity, false);
    }
	
	private HashMap getMainTotalBookedPOL (Long lmtBookingID, List pol, String bankEntity) throws LimitBookingException, AmountConversionException {
        return retrieveMainSectoralLimit( lmtBookingID, pol, bankEntity, false);
    }
	
	private HashMap retrieveProductProgramLimit (Long lmtBookingID, List pol, boolean isExposure) throws LimitBookingException, AmountConversionException {
        HashMap result = null;
		ResultSet rs = null;
        StringBuffer strBuf = new StringBuffer();
        StringBuffer andCondition = new StringBuffer();
        
        ArrayList params = new ArrayList();
		
		if( isExposure ) {
			
			strBuf.append(" select pt.reference_code as pol, 'MYR' as exposure_amt_curr, sum(convert_amt(exposure_amt, exposure_amt_curr, 'MYR')) as exposure_amt \n" );
	        strBuf.append(" from cms_aggr_exp_prod_type apt join cms_product_type pt on pt.reference_code = apt.prod_type " );
	        strBuf.append(" join cms_product_program pg on pt.product_program_id = pg.product_program_id " );
	        strBuf.append(" where 1 = 1  ");
	     
	        andCondition.append(" and prod_type ");
		
		} else {
		
	        strBuf.append(" SELECT b.LMT_BK_ID, b.TICKET_NO, b.AA_NO, b.AA_SOURCE, b.BK_NAME, b.BK_BUS_UNIT, \n" );
	        strBuf.append(" b.DATE_CREATED, b.LAST_MODIFIED_BY, d.BK_CURR, d.BK_AMT, d.BK_PROD_TYPE_CODE \n" );
	        strBuf.append(" from CMS_LMT_BOOK b, CMS_LMT_BOOK_DTL d \n" );
	        strBuf.append(" where 1 = 1  \n");
	        strBuf.append(" and b.LMT_BK_ID = d.LMT_BK_ID\n");
	        strBuf.append(" and d.BK_TYPE_CAT = '");
			strBuf.append( ICMSConstant.BKG_TYPE_PROD_PROG );
			strBuf.append("' \n");
			strBuf.append(" and b.BK_STATUS = '");
			strBuf.append( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );
			strBuf.append("' \n");
			strBuf.append(" and d.STATUS = '");
			strBuf.append( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );
			strBuf.append("' \n");
			
			if( lmtBookingID != null ) {
				strBuf.append(" AND b.LMT_BK_ID <> ? ");			
			}
			
	        andCondition.append(" and d.BK_PROD_TYPE_CODE ");
		}
		
       
		
		if( lmtBookingID != null ) {
			params.add( lmtBookingID );			
		}
			
        try {
            dbUtil = new DBUtil();
			CommonUtil.buildSQLInList(pol, andCondition, params);

            log("\nparams = " + params);
            log("\npol = " + pol);
            strBuf.append(andCondition);
            
			if( !isExposure ) {
				strBuf.append(" ORDER BY BK_PROD_TYPE_CODE");
			}
			else {
				strBuf.append(" GROUP BY pt.reference_code");
			}
			
			log("\nstrBuf = " + strBuf);
			
            dbUtil.setSQL(strBuf.toString());
            CommonUtil.setSQLParams(params, dbUtil);		
						
            rs = dbUtil.executeQuery();

			if( isExposure ) {
				result = processExposurePol(rs);
				
			}
			else {
				result = processProductBookedLimitMap( rs );
			}
			
            return result;
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new LimitBookingException("LimitBookingDAO Error in retrieveEcoSectoralLimit ", e);
        } finally {
            finalize(dbUtil);
            finalize(rs);
        }
    }
	
	private HashMap retrieveEcoSectoralLimit (Long lmtBookingID, List pol, String bankEntity, boolean isExposure) throws LimitBookingException, AmountConversionException {
        HashMap result = null;
		ResultSet rs = null;
        StringBuffer strBuf = new StringBuffer();
        StringBuffer andCondition = new StringBuffer();
        
        ArrayList params = new ArrayList();
		
		if( isExposure ) {
			
			strBuf.append(" select pol, exposure_amt_curr, exposure_amt \n" );
	        strBuf.append(" from cms_aggr_exp_pol " );
	        strBuf.append(" where 1 = 1  ");
	        
	        if (! (bankEntity.equals(ICMSConstant.BANKING_GROUP_ENTRY_CODE))) {
	        strBuf.append(" and BANK_ENTITY = '");
			strBuf.append(bankEntity);
			strBuf.append("' ");
	        }
	        
	        andCondition.append(" and pol ");
		
		} else {
		
	        strBuf.append(" SELECT b.LMT_BK_ID, b.TICKET_NO, b.AA_NO, b.AA_SOURCE, b.BK_NAME, b.BK_BUS_UNIT, \n" );
	        strBuf.append(" b.DATE_CREATED, b.LAST_MODIFIED_BY, d.BK_CURR, d.BK_AMT, d.BK_TYPE_CODE \n" );
	        strBuf.append(" from CMS_LMT_BOOK b, CMS_LMT_BOOK_DTL d \n" );
	        strBuf.append(" where 1 = 1  \n");
	        strBuf.append(" and b.LMT_BK_ID = d.LMT_BK_ID\n");
	        strBuf.append(" and d.BK_TYPE_CAT = '");
			strBuf.append( ICMSConstant.BKG_TYPE_ECO_POL );
			strBuf.append("' \n");
			strBuf.append(" and b.BK_STATUS = '");
			strBuf.append( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );
			strBuf.append("' \n");
			strBuf.append(" and d.STATUS = '");
			strBuf.append( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );
			strBuf.append("' \n");

			if (! (bankEntity.equals(ICMSConstant.BANKING_GROUP_ENTRY_CODE))) {
				strBuf.append(" and b.BK_BANK_ENTITY = '");
				strBuf.append( bankEntity );
				strBuf.append("' \n");
			}
			
			if( lmtBookingID != null ) {
				strBuf.append(" AND b.LMT_BK_ID <> ? ");			
			}
			
	        andCondition.append(" and d.BK_TYPE_CODE ");
		}
		
       
		
		if( lmtBookingID != null ) {
			params.add( lmtBookingID );			
		}
			
        try {
            dbUtil = new DBUtil();
			CommonUtil.buildSQLInList(pol, andCondition, params);

            log("\nparams = " + params);
            log("\npol = " + pol);
            strBuf.append(andCondition);
            
			if( !isExposure ) {
				strBuf.append(" ORDER BY BK_TYPE_CODE");
			}
			log("\nstrBuf = " + strBuf);
			
            dbUtil.setSQL(strBuf.toString());
            CommonUtil.setSQLParams(params, dbUtil);		
						
            rs = dbUtil.executeQuery();

			if( isExposure ) {
				result = processExposurePol(rs);
				
			}
			else {
				result = processBookedLimitMap( rs );
			}
			
            return result;
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new LimitBookingException("LimitBookingDAO Error in retrieveEcoSectoralLimit ", e);
        } finally {
            finalize(dbUtil);
            finalize(rs);
        }
    }
	
	private HashMap retrieveSubSectoralLimit (Long lmtBookingID, List pol, String bankEntity, boolean isExposure) throws LimitBookingException, AmountConversionException {
        HashMap result = null;
		ResultSet rs = null;
        StringBuffer strBuf = new StringBuffer();
        StringBuffer andCondition = new StringBuffer();
        
        ArrayList params = new ArrayList();
		
		if( isExposure ) {
	        
	    	strBuf.append(" select 'MYR', sum(convert_amt(exposure_amt, exposure_amt_curr, 'MYR')), pol \n" );
	        strBuf.append(" from cms_aggr_exp_pol c join cms_eco_sector_limit e on c.pol = e.sector_code" );
	        strBuf.append(" join cms_sub_sector_limit s on s.sub_sector_limit_id = e.sub_sector_limit_id where"); 
	        
	        if (! (bankEntity.equals(ICMSConstant.BANKING_GROUP_ENTRY_CODE))) {
	        	strBuf.append(" BANK_ENTITY = '");
	        	strBuf.append(bankEntity);
	        	strBuf.append("' and ");
	        }
			
	        andCondition.append(" s.loan_purpose_code_value ");
		
		} else {
		
	        strBuf.append(" SELECT b.LMT_BK_ID, b.TICKET_NO, b.AA_NO, b.AA_SOURCE, b.BK_NAME, b.BK_BUS_UNIT, \n" );
	        strBuf.append(" b.DATE_CREATED, b.LAST_MODIFIED_BY, d.BK_CURR, d.BK_AMT, d.BK_TYPE_CODE \n" );
	        strBuf.append(" from CMS_LMT_BOOK b, CMS_LMT_BOOK_DTL d \n" );
	        strBuf.append(" where 1 = 1  \n");
	        strBuf.append(" and b.LMT_BK_ID = d.LMT_BK_ID\n");
	        strBuf.append(" and d.BK_TYPE_CAT = '");
			strBuf.append( ICMSConstant.BKG_TYPE_SUB_POL );
			strBuf.append("' \n");
			strBuf.append(" and b.BK_STATUS = '");
			strBuf.append( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );
			strBuf.append("' \n");
			strBuf.append(" and d.STATUS = '");
			strBuf.append( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );
			strBuf.append("' \n");

			if (! (bankEntity.equals(ICMSConstant.BANKING_GROUP_ENTRY_CODE))) {
				strBuf.append(" and b.BK_BANK_ENTITY = '");
				strBuf.append( bankEntity );
				strBuf.append("' \n");
			}
			
			if( lmtBookingID != null ) {
				strBuf.append(" AND b.LMT_BK_ID <> ? ");			
			}
			
	        andCondition.append(" and d.BK_TYPE_CODE ");
		}
		
       
		
		if( lmtBookingID != null ) {
			params.add( lmtBookingID );			
		}
			
        try {
            dbUtil = new DBUtil();
			CommonUtil.buildSQLInList(pol, andCondition, params);

            log("\nparams = " + params);
            log("\npol = " + pol);
            strBuf.append(andCondition);
            
			if( !isExposure ) {
				strBuf.append(" ORDER BY BK_TYPE_CODE");
			}
			else {
				strBuf.append(" GROUP BY POL");
			}
			
			log("\nstrBuf = " + strBuf);
			
            dbUtil.setSQL(strBuf.toString());
            CommonUtil.setSQLParams(params, dbUtil);		
						
            rs = dbUtil.executeQuery();

			if( isExposure ) {
				result = processExposurePol(rs);
				
			}
			else {
				result = processBookedLimitMap( rs );
			}
			
            return result;
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new LimitBookingException("LimitBookingDAO Error in retrieveSubSectoralLimit ", e);
        } finally {
            finalize(dbUtil);
            finalize(rs);
        }
    }
	
	
	private HashMap retrieveMainSectoralLimit (Long lmtBookingID, List pol, String bankEntity, boolean isExposure) throws LimitBookingException, AmountConversionException {
        HashMap result = null;
		ResultSet rs = null;
        StringBuffer strBuf = new StringBuffer();
        StringBuffer andCondition = new StringBuffer();
        
        ArrayList params = new ArrayList();
		
		if( isExposure ) {
	        
			strBuf.append(" select 'MYR', sum(convert_amt(exposure_amt, exposure_amt_curr, 'MYR')), pol \n" );
	        strBuf.append(" from cms_aggr_exp_pol c join cms_eco_sector_limit e on c.pol = e.sector_code" );
	        strBuf.append(" join cms_sub_sector_limit s on s.sub_sector_limit_id = e.sub_sector_limit_id");
	        strBuf.append(" join cms_main_sector_limit m on s.main_sector_limit_id = m.main_sector_limit_id where"); 
	        
	        if (! (bankEntity.equals(ICMSConstant.BANKING_GROUP_ENTRY_CODE))) {
	        	strBuf.append(" BANK_ENTITY = '");
	        	strBuf.append(bankEntity);
	        	strBuf.append("' and ");
	        }
			
	        andCondition.append(" m.loan_purpose_code_value ");
		
		} else {
		
	        strBuf.append(" SELECT b.LMT_BK_ID, b.TICKET_NO, b.AA_NO, b.AA_SOURCE, b.BK_NAME, b.BK_BUS_UNIT, \n" );
	        strBuf.append(" b.DATE_CREATED, b.LAST_MODIFIED_BY, d.BK_CURR, d.BK_AMT, d.BK_TYPE_CODE \n" );
	        strBuf.append(" from CMS_LMT_BOOK b, CMS_LMT_BOOK_DTL d \n" );
	        strBuf.append(" where 1 = 1  \n");
	        strBuf.append(" and b.LMT_BK_ID = d.LMT_BK_ID\n");
	        strBuf.append(" and d.BK_TYPE_CAT = '");
			strBuf.append( ICMSConstant.BKG_TYPE_MAIN_POL );
			strBuf.append("' \n");
			strBuf.append(" and b.BK_STATUS = '");
			strBuf.append( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );
			strBuf.append("' \n");
			strBuf.append(" and d.STATUS = '");
			strBuf.append( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );
			strBuf.append("' \n");

			if (! (bankEntity.equals(ICMSConstant.BANKING_GROUP_ENTRY_CODE))) {
				strBuf.append(" and b.BK_BANK_ENTITY = '");
				strBuf.append( bankEntity );
				strBuf.append("' \n");
			}
			
			if( lmtBookingID != null ) {
				strBuf.append(" AND b.LMT_BK_ID <> ? ");			
			}
			
	        andCondition.append(" and d.BK_TYPE_CODE ");
		}
		
       
		
		if( lmtBookingID != null ) {
			params.add( lmtBookingID );			
		}
			
        try {
            dbUtil = new DBUtil();
			CommonUtil.buildSQLInList(pol, andCondition, params);

            log("\nparams = " + params);
            log("\npol = " + pol);
            strBuf.append(andCondition);
            
			if( !isExposure ) {
				strBuf.append(" ORDER BY BK_TYPE_CODE");
			}
			else {
				strBuf.append(" GROUP BY POL");
			}
			
			log("\nstrBuf = " + strBuf);
			
            dbUtil.setSQL(strBuf.toString());
            CommonUtil.setSQLParams(params, dbUtil);		
						
            rs = dbUtil.executeQuery();

			if( isExposure ) {
				result = processExposurePol(rs);
				
			}
			else {
				result = processBookedLimitMap( rs );
			}
			
            return result;
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new LimitBookingException("LimitBookingDAO Error in retrieveMainSectoralLimit ", e);
        } finally {
            finalize(dbUtil);
            finalize(rs);
        }
    }
		 
	/**
	 * Entity Dimension.
	 *
	 */
    public List retrieveEntityBookingResult( Long lmtBookingID, ILimitBookingDetail lmtBookDtls, HashMap cciMap ) throws LimitBookingException, AmountConversionException {
        ArrayList col = new ArrayList();
		
		Object[] exposureEntity = getExposureEntity( new Long( lmtBookDtls.getBkgTypeCode() ) );
		Object[] totalBookEntity = getTotalBookedEntity( lmtBookingID, new Long( lmtBookDtls.getBkgTypeCode() ), cciMap );
       	
		if( exposureEntity != null ) {
			lmtBookDtls.setCurrentExposure( LimitBookingHelper.convertBaseAmount( (Amount)exposureEntity[0] ) );
        }
		else {
			lmtBookDtls.setCurrentExposure(null);
		}
		if( totalBookEntity != null ) {
			lmtBookDtls.setTotalBookedAmount( LimitBookingHelper.convertBaseAmount( (Amount)totalBookEntity[0] ));        
			lmtBookDtls.setBookedLimitList( (List)totalBookEntity[1] );        
		}
		else {
			lmtBookDtls.setTotalBookedAmount(null);        
			lmtBookDtls.setBookedLimitList(null);  
		}
		
        col.add( lmtBookDtls );
		
		return col;
    }
	
	private Object[] getExposureEntity (Long subprofileID) throws LimitBookingException, AmountConversionException {
        return retrieveEntityLimit( null, subprofileID, true, null );
    }
	
	private Object[] getTotalBookedEntity (Long lmtBookingID, Long subprofileID, HashMap cciMap) throws LimitBookingException, AmountConversionException {
        return retrieveEntityLimit( lmtBookingID, subprofileID, false, cciMap );
    }		
	
    private Object[] retrieveEntityLimit (Long lmtBookingID, Long subprofileID, boolean isExposure, HashMap cciMap) throws LimitBookingException, AmountConversionException {
        
		Object[] result = null;
		ResultSet rs = null;
       
        StringBuffer strBuf = new StringBuffer();
        StringBuffer andCondition = new StringBuffer();

        try {
            dbUtil = new DBUtil();            
           
            if ( subprofileID != null )
            {
				if( isExposure ) {
					strBuf.append( QUERY_EXPOSURE_ENTITY );
					
					dbUtil.setSQL( strBuf.toString() );
					dbUtil.setLong(1, subprofileID.longValue());

				} else {
			        strBuf.append( QUERY_SELECT_LIMIT_BOOKED );
			        //strBuf.append(" AND CMS_LE_SUB_PROFILE_ID = ? ");
					
					if( lmtBookingID != null ) {
						strBuf.append(" AND LMT_BK_ID <> ? ");			
					}
					
					//exclude exempted customer for entity limit total book 
					strBuf.append(" and ( CMS_LE_SUB_PROFILE_ID is null or ( CMS_LE_SUB_PROFILE_ID is not null " );
					strBuf.append(" AND CMS_LE_SUB_PROFILE_ID not in ( select CMS_LE_SUB_PROFILE_ID from CMS_EXEMPTED_INST_GP5 where STATUS <> 'DELETED') ) ) ");

					andCondition.append(" AND CMS_LE_SUB_PROFILE_ID ");
			        
					List subprofileList = new ArrayList();
					if( cciMap != null && !cciMap.isEmpty() ) {
						Collection cciSubprofileList = cciMap.values(); 	

						for (Iterator itr = cciSubprofileList.iterator(); itr.hasNext();) {		
							Long cciSubprofileID = (Long) itr.next();
							subprofileList.add( cciSubprofileID );
						}						
					}
					subprofileList.add( subprofileID );
				
					ArrayList params = new ArrayList();	   
					
					if( lmtBookingID != null ) {
						params.add( lmtBookingID );			
					}
					
		            CommonUtil.buildSQLInList(subprofileList, andCondition, params);
		            log("\nparams = " + params);
		            log("\nsubprofileList = " + subprofileList);
		            strBuf.append(andCondition);
		            log("\nstrBuf = " + strBuf);
										
		            dbUtil.setSQL(strBuf.toString());
		            CommonUtil.setSQLParams(params, dbUtil);
				
				
				}		
				
					
                rs = dbUtil.executeQuery();
                
				if( isExposure ) {
					Amount amt = processExposure(rs);
					result = new Object[]{ amt };
					
				}
				else {
					result = processBookedLimit( rs );
				}
            }            
            
            return result;
			
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new LimitBookingException("LimitBookingDAO Error in getExposureCountry ", e);
        } finally {
            finalize(dbUtil);
            finalize(rs);        
        }
    }	
	
		
	/**
	 * Bank Entity Dimension.
	 *
	 */
    public List retrieveBankEntityBookingResult(Long lmtBookingID, List bankEntityList) 
		throws LimitBookingException, AmountConversionException{
		
		try {
			//get member llist
			CustGrpIdentifierDAO cgrpDao = new CustGrpIdentifierDAO();
			
			List allGrplist = getGroupIDList( bankEntityList, null );
			HashMap memberMap = cgrpDao.retrieveMemberByGroupID ( allGrplist );
			HashMap subGroupMap = cgrpDao.retrieveSubGroupByGroupID ( allGrplist );
			log("\nmemberMap = " + memberMap);
			log("\nsubGroupMap = " + subGroupMap);			
								
			for( Iterator i = bankEntityBkgSubType.iterator(); i.hasNext(); ) {
			
		        String bkgSubType = (String) i.next();
				List grplist = getGroupIDList( bankEntityList, bkgSubType );
				
				if( !grplist.isEmpty() ) {													
			
					HashMap exposureResult = getExposureBankEntity( grplist, (String) bankEntityBkgSubTypeMap.get( bkgSubType ) );
					HashMap totalBookResult = getTotalBookedBankEntity( lmtBookingID, grplist, 
																			(String) bankEntityBkgSubTypeMap.get( bkgSubType ), null );
							
					for (Iterator itr = bankEntityList.iterator(); itr.hasNext();) {
			            OBLimitBookingDetail lmtBookDtl = (OBLimitBookingDetail) itr.next();
					
						if ( bkgSubType.equals( lmtBookDtl.getBkgSubType() ) ) {
							//get exposure
							Amount amt = (Amount) exposureResult.get( lmtBookDtl.getBkgTypeCode() );
							if ( amt != null ){						
								lmtBookDtl.setCurrentExposure( LimitBookingHelper.convertBaseAmount( amt ) );                  
								
							}
							else {
								lmtBookDtl.setCurrentExposure( null );  
							}
							//get booked result
							Object[] bookResult = (Object[]) totalBookResult.get( lmtBookDtl.getBkgTypeCode() );
							
							//get total book result
							Object[] allBookResult = processTotalBookForRelatedMember( lmtBookingID, new Long( lmtBookDtl.getBkgTypeCode() ), 
																				(List)subGroupMap.get( lmtBookDtl.getBkgTypeCode() ),
																				memberMap, lmtBookDtl, bookResult, 
																				(String) bankEntityBkgSubTypeMap.get( bkgSubType ), null, null );
							
							lmtBookDtl.setTotalBookedAmount( LimitBookingHelper.convertBaseAmount( (Amount)allBookResult[0] ) );
							lmtBookDtl.setBookedLimitList( (List)allBookResult[1] );
							//log("\n allBookResult retrieveBankEntityBookingResult = " + (List)allBookResult[1]);	
							//if ( bookResult != null ){
							//	lmtBookDtl.setTotalBookedAmount( LimitBookingHelper.convertBaseAmount( (Amount)bookResult[0] ) );
							//	lmtBookDtl.setBookedLimitList( (List)bookResult[1] );
							//}
						}
					} //end for  		
				}
			
			}
			
	        return bankEntityList;
		} catch (SearchDAOException e) {
            DefaultLogger.error(this, "", e);
            throw new LimitBookingException("LimitBookingDAO Error in retrieveBankEntityBookingResult ", e);
        }
    }
	
	private HashMap getExposureBankWideCustomer (Long customerSubProfileID, Map cciMap, String bankEntity) throws LimitBookingException, AmountConversionException {
        return retrieveBankWideCustomerLimit( null, customerSubProfileID, cciMap, bankEntity, true);
    }

	private HashMap getTotalBookedBankWideCustomer (ILimitBooking lmtBooking, Long customerSubProfileID, Map cciMap, String bankEntity) throws LimitBookingException, AmountConversionException {
        return retrieveBankWideCustomerLimit( lmtBooking, customerSubProfileID, cciMap, bankEntity, false);
    }
	
	private HashMap retrieveBankWideCustomerLimit (ILimitBooking lmtBooking, Long customerSubProfileID, Map cciMap, String bankEntity, boolean isExposure) throws LimitBookingException, AmountConversionException {
		HashMap result = null;
		ResultSet rs = null;
        StringBuffer strBuf = new StringBuffer();
        StringBuffer andCondition = new StringBuffer();
        Long lmtBookingID = null;
        
        if (lmtBooking != null) {
        	lmtBookingID = new Long(lmtBooking.getLimitBookingID());
        }
        
        
        try {
        	
        dbUtil = new DBUtil();
        	
        if ( customerSubProfileID != null )
        {
			if( isExposure ) {
				strBuf.append(" select cms_le_sub_profile_id, exposure_amt_curr, exposure_amt \n" );
		        strBuf.append("  from cms_aggr_exp_bw_be_cust " );
		        strBuf.append(" where 1 = 1  ");
		        if (!bankEntity.equals(ICMSConstant.BANKING_GROUP_ENTRY_CODE)) {
		        	strBuf.append(" and BANK_ENTITY = '");
					strBuf.append(bankEntity);
					strBuf.append("' ");
		        }
				
				andCondition.append(" and CMS_LE_SUB_PROFILE_ID ");
				
				List subprofileList = new ArrayList();
				if( cciMap != null && !cciMap.isEmpty() ) {
					Collection cciSubprofileList = cciMap.values(); 	

					for (Iterator itr = cciSubprofileList.iterator(); itr.hasNext();) {		
						Long cciSubprofileID = (Long) itr.next();
						subprofileList.add( cciSubprofileID );
					}						
				}
				subprofileList.add( customerSubProfileID );
				
				ArrayList params = new ArrayList();	 
				
				CommonUtil.buildSQLInList(subprofileList, andCondition, params);
	            log("\nparams = " + params);
	            log("\nsubprofileList = " + subprofileList);
	            strBuf.append(andCondition);
	            log("\nstrBuf = " + strBuf);
				
				dbUtil.setSQL(strBuf.toString());
				CommonUtil.setSQLParams(params, dbUtil);

			} else {
				strBuf.append(" SELECT DISTINCT b.LMT_BK_ID, b.TICKET_NO, b.AA_NO, b.AA_SOURCE, b.BK_NAME, b.BK_BUS_UNIT, \n" );
		        strBuf.append(" b.DATE_CREATED, b.LAST_MODIFIED_BY, b.BK_CURR, b.BK_AMT, d.BK_TYPE_CODE, b.BK_BANK_ENTITY \n" );	        
		        strBuf.append(" FROM CMS_LMT_BOOK b, CMS_LMT_BOOK_DTL d \n" );
		        strBuf.append(" WHERE 1 = 1  ");
		        strBuf.append(" AND b.LMT_BK_ID = d.LMT_BK_ID \n");
		        strBuf.append(" AND b.BK_STATUS = '");
				strBuf.append( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );
				strBuf.append("' ");
				strBuf.append(" AND d.STATUS = '");
				strBuf.append( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );
				strBuf.append("' ");
		        
		        if (!bankEntity.equals(ICMSConstant.BANKING_GROUP_ENTRY_CODE)) {
					strBuf.append(" and b.BK_BANK_ENTITY = '");
					strBuf.append( bankEntity );
					strBuf.append("' ");
		        }
				strBuf.append(" and CMS_LE_SUB_PROFILE_ID = ");
				strBuf.append(customerSubProfileID);
				
				if( lmtBooking != null ) {
					strBuf.append(" AND b.LMT_BK_ID <> ? ");			
				}
				
				//exclude exempted customer for entity limit total book 
				strBuf.append(" and ( CMS_LE_SUB_PROFILE_ID is null or ( CMS_LE_SUB_PROFILE_ID is not null " );
				strBuf.append(" AND CMS_LE_SUB_PROFILE_ID not in ( select CMS_LE_SUB_PROFILE_ID from CMS_EXEMPTED_INST_GP5 where STATUS <> 'DELETED') ) ) ");

				andCondition.append(" AND CMS_LE_SUB_PROFILE_ID ");
		        
				List subprofileList = new ArrayList();
				if( cciMap != null && !cciMap.isEmpty() ) {
					Collection cciSubprofileList = cciMap.values(); 	

					for (Iterator itr = cciSubprofileList.iterator(); itr.hasNext();) {		
						Long cciSubprofileID = (Long) itr.next();
						subprofileList.add( cciSubprofileID );
					}						
				}
				subprofileList.add( customerSubProfileID );
			
				ArrayList params = new ArrayList();	   
				
				if( lmtBooking != null ) {
					params.add( lmtBookingID );			
				}
				
	            CommonUtil.buildSQLInList(subprofileList, andCondition, params);
	            log("\nparams = " + params);
	            log("\nsubprofileList = " + subprofileList);
	            strBuf.append(andCondition);
	            log("\nstrBuf = " + strBuf);
	            
	            dbUtil.setSQL(strBuf.toString());
	            CommonUtil.setSQLParams(params, dbUtil);
									
			}		
			
			log("\nstrBuf = " + strBuf);
			
            rs = dbUtil.executeQuery();

			if( isExposure ) {
				result = processExposureCustomer(rs);
				
			}
			else {
				result = processBookedLimitMap( rs );
			}
			
        }
        
        else {
        	
        	if (!isExposure) {
				strBuf.append(" SELECT DISTINCT b.LMT_BK_ID, b.TICKET_NO, b.AA_NO, b.AA_SOURCE, b.BK_NAME, b.BK_BUS_UNIT, \n" );
		        strBuf.append(" b.DATE_CREATED, b.LAST_MODIFIED_BY, b.BK_CURR, b.BK_AMT, d.BK_TYPE_CODE, b.BK_BANK_ENTITY \n" );	        
		        strBuf.append(" FROM CMS_LMT_BOOK b, CMS_LMT_BOOK_DTL d \n" );
		        strBuf.append(" WHERE 1 = 1  ");
		        strBuf.append(" AND b.LMT_BK_ID = d.LMT_BK_ID \n");
		        strBuf.append(" AND b.BK_STATUS = '");
				strBuf.append( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );
				strBuf.append("' ");
				strBuf.append(" AND d.STATUS = '");
				strBuf.append( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );
				strBuf.append("' ");
		        
		        if (!bankEntity.equals(ICMSConstant.BANKING_GROUP_ENTRY_CODE)) {
					strBuf.append(" and b.BK_BANK_ENTITY = '");
					strBuf.append( bankEntity );
					strBuf.append("' ");
		        }
				strBuf.append(" and BK_ID_NO = '");
				strBuf.append(lmtBooking.getBkgIDNo());
				strBuf.append("'");
				
				if( lmtBookingID != null ) {
					strBuf.append(" AND b.LMT_BK_ID <> ? ");			
				}
			
				ArrayList params = new ArrayList();	   
				
				if( lmtBookingID != null ) {
					params.add( lmtBookingID );			
				}
				
	            log("\nparams = " + params);
	            strBuf.append(andCondition);
	            log("\nstrBuf = " + strBuf);
	            
	            dbUtil.setSQL(strBuf.toString());
	            CommonUtil.setSQLParams(params, dbUtil);
	            
	            log("\nstrBuf = " + strBuf);
				
	            rs = dbUtil.executeQuery();
	            
				result = processBookedLimitMap( rs );
        	}					
        	
        }
		
            return result;
        }
         catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new LimitBookingException("LimitBookingDAO Error in getExposureBankWideBankEntity ", e);
        } finally {
            finalize(dbUtil);
            finalize(rs);
        }
    }
	
	/**
	 * This is to check whether the group and sub group ID passing in have already booked in limit booking
	 *
	 * @param grpIDList the list contains group ID and sub group ID of type Long
	* @return true if the group ID in the listing has been booked, else false
	* @throws LimitBookingException on errors encountered
	 */
	public boolean groupHasLimitBooking(List grpIDList) 
		throws LimitBookingException {
		
		boolean hasBooked = false;		
		
		ResultSet rs = null;
        StringBuffer strBuf = new StringBuffer();
		strBuf.append( SEARCH_GROUP_EXISTS_IN_BOOKING );	
		
        StringBuffer andCondition = new StringBuffer();    			
	    andCondition.append(" AND BK_TYPE_CODE ");		
	
        ArrayList params = new ArrayList();			
		
        try {
            dbUtil = new DBUtil();
            CommonUtil.buildSQLInList(grpIDList, andCondition, params);
            log("\nparams = " + params);
            log("\ngrpIDList = " + grpIDList);
            strBuf.append(andCondition);			
			
            log("\nstrBuf groupHasLimitBooking = " + strBuf);

            dbUtil.setSQL(strBuf.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();

			while (rs.next()){
				int count = rs.getInt(1);
				if( count > 0 ) {
					hasBooked = true;
				}		
			}
			
            return hasBooked;
			
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new LimitBookingException("LimitBookingDAO Error in groupHasLimitBooking ", e);
        } finally {
            finalize(dbUtil);
            finalize(rs);
        }					
		
	}

    public List retrieveLimitBookedCustomer(List customerIdList) throws LimitBookingException {
        List returnList = new ArrayList();

        ResultSet rs = null;
        StringBuffer strBuf = new StringBuffer();
        strBuf.append( QUERY_SELECT_LIMIT_BOOKED_2 );

        StringBuffer andCondition = new StringBuffer();
        andCondition.append(" AND CMS_LE_SUB_PROFILE_ID ");

        ArrayList params = new ArrayList();

        try {
            dbUtil = new DBUtil();
            CommonUtil.buildSQLInList(customerIdList, andCondition, params);
            log("\nparams = " + params);
            log("\ncustomerIdList = " + customerIdList);
            strBuf.append(andCondition);

            log("\nstrBuf retrieveLimitBookedCustomer = " + strBuf);

            dbUtil.setSQL(strBuf.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();

//            Map map = null;
            while (rs.next()){
//                map = new HashMap();
//                map.put("custId",new Long(rs.getLong(1)));
//                map.put("bkId",new Long(rs.getLong(2)));
                returnList.add(new Long(rs.getLong(1)));
            }

            return returnList;

        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new LimitBookingException("LimitBookingDAO Error in retrieveLimitBookedCustomer ", e);
        } finally {
            finalize(dbUtil);
            finalize(rs);
        }
    }

    /**
	 * This is to retrieve the group total book amount by bank entity.
	 *
	 * @param grpID the group ID to retrieve of type Long
	* @param bankEntityList list of bank entity type (of String), values are ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE, BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE and BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE
	* @return HashMap  of key is the bank entity type (of String) , value is the amount booked for the bank entity (of type Amount) in base currency code
	* @throws LimitBookingException on errors encountered
	* @throws AmountConversionException on cannot find currency conversion code encountered
	 */
	public HashMap retrieveGroupTotalBookByBankEntity(Long grpID, List bankEntityList) 
		throws LimitBookingException, AmountConversionException{
		
		try {
			HashMap resultMap = new HashMap();
			//get member llist
			CustGrpIdentifierDAO cgrpDao = new CustGrpIdentifierDAO();
			
			List grplist = new ArrayList();
			grplist.add( grpID.toString() );
			HashMap memberMap = cgrpDao.retrieveMemberByGroupID ( grplist );
			HashMap subGroupMap = cgrpDao.retrieveSubGroupByGroupID ( grplist );
			log("\nmemberMap = " + memberMap);
			log("\nsubGroupMap = " + subGroupMap);			
					
			for( Iterator i = bankEntityList.iterator(); i.hasNext(); ) {
				
				Amount totalBookedAmount = null;
		        String bankEntity = (String) i.next();															
			
				HashMap totalBookResult = getTotalBookedBankEntity( null, grplist, bankEntity, null );
																
				//get booked result
				if( !totalBookResult.isEmpty() ) {
					Collection totalBookResultCol = totalBookResult.values();
					
					for (Iterator iterator = totalBookResultCol.iterator(); iterator.hasNext();) {
						Object[] bookResult = (Object[]) iterator.next();
						
						
						if( totalBookedAmount != null ) {
							totalBookedAmount = CommonUtil.addAmount( totalBookedAmount, (Amount)bookResult[0] );
						}
						else {
							totalBookedAmount = (Amount)bookResult[0];
						}
						
				
					}//end for
				}
				List subGrpIDList = (List)subGroupMap.get( grpID.toString() );
				Object[] relBookResult = processTotalBookForRelatedMember( null, grpID, 																				
																			subGrpIDList,
																			memberMap, bankEntity, null, null );
				if( relBookResult != null ) {
					
					if( totalBookedAmount != null ) {
						totalBookedAmount = CommonUtil.addAmount( totalBookedAmount, (Amount)relBookResult[0] );
					}
					else {
						totalBookedAmount = (Amount)relBookResult[0];
					}
				}
				
				if( subGrpIDList != null ) {
					HashMap subTotalBookResult = new HashMap();
					
					subTotalBookResult = getTotalBookedBankEntity( null, subGrpIDList, bankEntity, grpID );
					
					if( !subTotalBookResult.isEmpty() ) {
						for (Iterator itr = subGrpIDList.iterator(); itr.hasNext();) {
							String subgroupID = (String) itr.next();							
							Object[] subBookResult = (Object[]) subTotalBookResult.get( subgroupID );
							if( subBookResult != null ) {
								if( totalBookedAmount != null ) {
									totalBookedAmount = CommonUtil.addAmount( totalBookedAmount, (Amount)subBookResult[0] );
								}
								else {
									totalBookedAmount = (Amount)subBookResult[0];
								}
							}
						}//end for
					}
				}//end if not null
				
				resultMap.put( bankEntity, LimitBookingHelper.convertBaseAmount( totalBookedAmount ) );
					
				
			}					
			
	        return resultMap;
			
		} catch (SearchDAOException e) {
            DefaultLogger.error(this, "", e);
            throw new LimitBookingException("LimitBookingDAO Error in retrieveBankEntityBookingResult ", e);
        }
    }
	
	/**
	 * Group Dimension.
	 *
	 */
	public List retrieveGroupBookingResult(Long lmtBookingID, List bankEntityList, String isFI, String isExemptedInst) 
		throws LimitBookingException, AmountConversionException{
        
		try {
			List grplist = getGroupIDList( bankEntityList, null );
			
			//get member llist
			CustGrpIdentifierDAO cgrpDao = new CustGrpIdentifierDAO();
		
			HashMap memberMap = cgrpDao.retrieveMemberByGroupID ( grplist );
			HashMap subGroupMap = cgrpDao.retrieveSubGroupByGroupID ( grplist );
			log("\nmemberMap = " + memberMap);
			log("\nsubGroupMap = " + subGroupMap);
						
			HashMap exposureResult = getExposureGroup( grplist, isFI, isExemptedInst );
			HashMap totalBookResult = getTotalBookedGroup( lmtBookingID, grplist, isFI, isExemptedInst, null );
			
			for (Iterator itr = bankEntityList.iterator(); itr.hasNext();) {
	            OBLimitBookingDetail lmtBookDtl = (OBLimitBookingDetail) itr.next();

				//get exposure
				Amount amt = (Amount) exposureResult.get( lmtBookDtl.getBkgTypeCode() );
				if ( amt != null ){						
					lmtBookDtl.setCurrentExposure( LimitBookingHelper.convertBaseAmount( amt ) );                  
				}
				else {
					lmtBookDtl.setCurrentExposure(null);  
				}
				//get booked result						
				Object[] bookResult = (Object[]) totalBookResult.get( lmtBookDtl.getBkgTypeCode() );			
												
				//get total book result
				Object[] allBookResult = processTotalBookForRelatedMember( lmtBookingID, new Long( lmtBookDtl.getBkgTypeCode() ), 
																				(List)subGroupMap.get( lmtBookDtl.getBkgTypeCode() ),
																				memberMap, lmtBookDtl, bookResult, 																				  
																				null, isFI, isExemptedInst );
				
				lmtBookDtl.setTotalBookedAmount( LimitBookingHelper.convertBaseAmount( (Amount)allBookResult[0] ) );
				lmtBookDtl.setBookedLimitList( (List)allBookResult[1] );
				//log("\n allBookResult retrieveGroupBookingResult = " + (List)allBookResult[1]);	

			} //end for  
			
	        
			return bankEntityList;
			
		} catch (SearchDAOException e) {
            DefaultLogger.error(this, "", e);
            throw new LimitBookingException("LimitBookingDAO Error in retrieveBankEntityBookingResult ", e);
        }
    }		
		
	private Object[] processTotalBookForRelatedMember(Long lmtBookingID, Long masterGrpID, List subGrpIDList, 
														Map memberMap, OBLimitBookingDetail lmtBookDtl, Object[] bookResult, 
														String bankEntity, String isFI, String isExemptedInst) 
		throws LimitBookingException, AmountConversionException {					
		
		Amount totalBookedAmount = null;
		List allBookResult = new ArrayList();			
		
		//process related member
		Object[] relatedBookResult = processTotalBookForRelatedMember(lmtBookingID, masterGrpID, subGrpIDList,
													memberMap, bankEntity, isFI, isExemptedInst );
		
		
		if( relatedBookResult != null ) {
			
			totalBookedAmount = (Amount)relatedBookResult[0];        
			allBookResult.addAll( (List)relatedBookResult[1] );       	
		
		}			
		//process group itself
		if ( bookResult != null ){			
			
			if( totalBookedAmount != null ) {
				totalBookedAmount = CommonUtil.addAmount( totalBookedAmount, (Amount)bookResult[0] );
			}
			else {
				totalBookedAmount = (Amount)bookResult[0];
			}
			allBookResult.addAll( (List)bookResult[1] );
		}
		
		//process sub group ID
		if( subGrpIDList != null ) {
			HashMap subTotalBookResult = new HashMap();
			if( bankEntity == null ) {
				subTotalBookResult = getTotalBookedGroup( lmtBookingID, subGrpIDList, isFI, isExemptedInst, masterGrpID );
			} else {
				subTotalBookResult = getTotalBookedBankEntity( lmtBookingID, subGrpIDList, bankEntity, masterGrpID );
			}
			if( !subTotalBookResult.isEmpty() ) {
				for (Iterator itr = subGrpIDList.iterator(); itr.hasNext();) {
					String subgroupID = (String) itr.next();							
					Object[] subBookResult = (Object[]) subTotalBookResult.get( subgroupID );
					if( subBookResult != null ) {
						if( totalBookedAmount != null ) {
							totalBookedAmount = CommonUtil.addAmount( totalBookedAmount, (Amount)subBookResult[0] );
						}
						else {
							totalBookedAmount = (Amount)subBookResult[0];
						}
						allBookResult.addAll( (List)subBookResult[1] );
					}
				}//end for
			}
		}//end if not null
				
		return new Object[] {totalBookedAmount,allBookResult};				
		
	}
	
	private Object[] processTotalBookForRelatedMember(Long lmtBookingID, Long masterGrpID, List subGrpIDList, 
														Map memberMap, String bankEntity, String isFI, String isExemptedInst) 
		throws LimitBookingException, AmountConversionException {
		
		try {
			CustRelationshipDAO crelnDao = new CustRelationshipDAO();			
			Object[] totalBookResult = null;
			List memberList = (List) memberMap.get( masterGrpID );
			List relatedMemberList = new ArrayList();
			
			if( memberList != null ) {
			
				relatedMemberList = crelnDao.retrieveShareholderDirectorOf ( memberList );
				//log("\nmemberList = " + memberList);
				log("\nrelatedMemberList = " + relatedMemberList);
				
			}//end if	memberList != null
		
			
			if( !relatedMemberList.isEmpty() ) {
				totalBookResult = retrieveTotalBookForRelatedMember( lmtBookingID, masterGrpID, subGrpIDList, relatedMemberList, 
																					bankEntity, isFI, isExemptedInst );		
				
			
			}			
			return totalBookResult;	
			
			
		//throw AmountConversionException to UI and show an error message to user	
		//} catch (AmountConversionException e) {
        //    DefaultLogger.error(this, "", e);
        //    throw new LimitBookingException("LimitBookingDAO Error in getExposureCountry ", e);
        } catch (CustRelationshipException e) {
            DefaultLogger.error(this, "", e);
            throw new LimitBookingException("LimitBookingDAO Error in processTotalBookForGroupMember ", e);
        }
		finally {
            //finalize(dbUtil);
            //finalize(rs);        
        }
	}
	
	private List getGroupIDList(List bankEntityList, String bkgSubType)
    {
        List list = new ArrayList();
        if (bankEntityList != null){
	        for (Iterator iterator = bankEntityList.iterator(); iterator.hasNext();) {
	            OBLimitBookingDetail dtl = (OBLimitBookingDetail) iterator.next();
	            if ( ( bkgSubType != null && 
						bkgSubType.equals( dtl.getBkgSubType() ) ) || bkgSubType == null ) {
				
	                // Get the list of Group IDs to get Exposure
	                String groupID = dtl.getBkgTypeCode();
	                list.add(groupID);
	            }
	        }
		}
        return list;
    }		

	private HashMap getExposureBankEntity (List grpID, String bankEntity) throws LimitBookingException, AmountConversionException {
       
		return retrieveExposureGroup( null, grpID, bankEntity, null, null, true, null );	   
	}
	
    private HashMap getTotalBookedBankEntity (Long lmtBookingID, List grpID, String bankEntity, Long masterGrpID) throws LimitBookingException, AmountConversionException {
        
		return retrieveExposureGroup( lmtBookingID, grpID, bankEntity, null, null, false, masterGrpID );	   
    }
	
	private HashMap getExposureGroup (List grpID, String fiInd, String isExemptedInst) throws LimitBookingException, AmountConversionException {
       
	    return retrieveExposureGroup( null, grpID, null, fiInd, isExemptedInst, true, null );	   
	}
	
    private HashMap getTotalBookedGroup (Long lmtBookingID, List grpID, String fiInd, String isExemptedInst, Long masterGrpID) throws LimitBookingException, AmountConversionException {
	
        return retrieveExposureGroup( lmtBookingID, grpID, null, fiInd, isExemptedInst, false, masterGrpID );			
    }
    	
	
    private HashMap retrieveExposureGroup (Long lmtBookingID, List grpID, 
											String bankEntity, String fiInd, 
											String isExemptedInst, boolean isExposure, Long masterGrpID ) 
		throws LimitBookingException, AmountConversionException {
        
		HashMap result = new HashMap();
		ResultSet rs = null;
        StringBuffer strBuf = new StringBuffer();
        StringBuffer andCondition = new StringBuffer();
       
		
		if( isExposure ) {
			strBuf.append(" select exposure_amt_curr, exposure_amt, group_id, BANK_ENTITY, FI_INDICATOR, IS_EXEMPTED_INST \n");
	        strBuf.append("  from cms_aggr_exp_grp \n" );
	        strBuf.append(" where 1 = 1 ");
			if( bankEntity == null ) {
		        strBuf.append(" AND BANK_ENTITY IS NULL ");
		        
				//this is only applicable for bank entity is null
				if( fiInd == null ) {
					strBuf.append(" AND FI_INDICATOR IS NULL ");		        
				}
				else {
					strBuf.append(" AND FI_INDICATOR = '");
					strBuf.append(fiInd);
					strBuf.append("' ");
				}
				
				if( isExemptedInst == null ) {
					strBuf.append(" AND IS_EXEMPTED_INST IS NULL ");		        
				}
				
				
			}
			else if( bankEntity != null ) {
				   
		        strBuf.append(" AND ( FI_INDICATOR IS NULL AND IS_EXEMPTED_INST = 'N' ");
		        
		        if (!bankEntity.equals(ICMSConstant.BANKING_GROUP_ENTRY_CODE)) {
		        	strBuf.append(" AND BANK_ENTITY = '");
		        	strBuf.append(bankEntity);
		        	strBuf.append("'");
		        }
		        
		        strBuf.append(" ) ");
		        
			}
			
			if( fiInd != null && fiInd.equals("N") ) {
				strBuf.append(" and IS_EXEMPTED_INST = 'N' ");
				
			} else {
				if( isExemptedInst != null ) {
					strBuf.append(" and IS_EXEMPTED_INST = '");
					strBuf.append(isExemptedInst);
					strBuf.append("' ");
				}
			}
				
			andCondition.append(" and group_id  ");
			
			
		} else {
	        
			strBuf.append(" SELECT DISTINCT b.LMT_BK_ID, b.TICKET_NO, b.AA_NO, b.AA_SOURCE, b.BK_NAME, b.BK_BUS_UNIT, \n" );
	        strBuf.append(" b.DATE_CREATED, b.LAST_MODIFIED_BY, b.BK_CURR, b.BK_AMT, d.BK_TYPE_CODE, b.BK_BANK_ENTITY \n" );	        
	        strBuf.append(" FROM CMS_LMT_BOOK b, CMS_LMT_BOOK_DTL d \n" );
	        strBuf.append(" WHERE 1 = 1  ");
	        strBuf.append(" AND b.LMT_BK_ID = d.LMT_BK_ID \n");
	        strBuf.append(" AND d.BK_TYPE_CAT = '");
			strBuf.append( ICMSConstant.BKG_TYPE_BGEL );
			strBuf.append("' ");
	        strBuf.append(" AND b.BK_STATUS = '");
			strBuf.append( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );
			strBuf.append("' ");
			strBuf.append(" AND d.STATUS = '");
			strBuf.append( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );
			strBuf.append("' ");
			
			if( bankEntity != null ) {
				
				if (!bankEntity.equals(ICMSConstant.BANKING_GROUP_ENTRY_CODE)) {
					strBuf.append(" AND b.BK_BANK_ENTITY = '");
					strBuf.append( bankEntity ).append("' ");
				}
				
			}
			
			if( isExemptedInst != null && isExemptedInst.equals("Y") ) {				
				
				strBuf.append(" and b.CMS_LE_SUB_PROFILE_ID in ( select CMS_LE_SUB_PROFILE_ID from CMS_EXEMPTED_INST_GP5 where STATUS <> 'DELETED') \n");

			} 
			//else if( isExemptedInst != null && isExemptedInst.equals("N") ) {				
			//exclude exempted customer for group total limit
			
			strBuf.append(" and ( b.CMS_LE_SUB_PROFILE_ID is null or ( b.CMS_LE_SUB_PROFILE_ID is not null " );
			strBuf.append(" and b.CMS_LE_SUB_PROFILE_ID not in ( select CMS_LE_SUB_PROFILE_ID from CMS_EXEMPTED_INST_GP5 where STATUS <> 'DELETED') ) ) \n");
												
			//}
			
			if( fiInd != null ) {				
				strBuf.append(" and b.IS_FINANCIAL_INSTITUTION = '");
				strBuf.append( fiInd );
				strBuf.append("' ");
			}								
	       	if( lmtBookingID != null ) {
				strBuf.append(" AND b.LMT_BK_ID <> ? ");			
			}
			
			if( masterGrpID != null )  {
				//not exists in the master group itself, this is for to query subgroup total book
				strBuf.append(" AND NOT EXISTS (select '1' from CMS_LMT_BOOK_DTL where b.LMT_BK_ID = LMT_BK_ID \n" );
				strBuf.append(" AND BK_TYPE_CAT = '");
				strBuf.append( ICMSConstant.BKG_TYPE_BGEL );
				strBuf.append("' ");
				strBuf.append(" AND STATUS = '");
				strBuf.append( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );
				strBuf.append("' ");
				strBuf.append(" AND BK_TYPE_CODE = ? ) ");
			
			}
	        andCondition.append(" and d.BK_TYPE_CODE ");
		}
		
        ArrayList params = new ArrayList();
		
		if( lmtBookingID != null ) {
			params.add( lmtBookingID );			
		}
		if( masterGrpID != null )  {	
			params.add( masterGrpID );			
		}
		
        try {
            dbUtil = new DBUtil();
            CommonUtil.buildSQLInList(grpID, andCondition, params);
            log("\nparams = " + params);
            log("\ngrpID = " + grpID);
            log("\nmasterGrpID = " + masterGrpID);
            strBuf.append(andCondition);
			
			if( !isExposure ) {
				strBuf.append(" ORDER BY BK_TYPE_CODE");
			}
            log("\nstrBuf retrieveExposureGroup = " + strBuf);

            dbUtil.setSQL(strBuf.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();

			if( isExposure ) {
				result = processExposureGroup( rs );
				
			}
			else {
				result = processBookedLimitMap( rs );
			}
			
            return result;
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new LimitBookingException("LimitBookingDAO Error in getExposureGroup ", e);
        } finally {
            finalize(dbUtil);
            finalize(rs);
        }
    }	
	
	private Object[] retrieveTotalBookForRelatedMember (Long lmtBookingID,  Long masterGrpID, List subGrpIDList, 
														List subprofileIDList, String bankEntity, 
														String fiInd, String isExemptedInst) 
		throws LimitBookingException, AmountConversionException {
        
		ResultSet rs = null;
        StringBuffer strBuf = new StringBuffer();
        StringBuffer andCondition = new StringBuffer();       				
	    
		strBuf.append( QUERY_SELECT_LIMIT_BOOKED );
		
		
		if( bankEntity != null  && !(bankEntity.equals(ICMSConstant.BANKING_GROUP_ENTRY_CODE))) {
			strBuf.append(" AND BK_BANK_ENTITY = '");
			strBuf.append( bankEntity ).append("' ");
			
		}
		
		if( isExemptedInst != null && isExemptedInst.equals("Y") ) {				
			
			strBuf.append(" and CMS_LE_SUB_PROFILE_ID in ( select CMS_LE_SUB_PROFILE_ID from CMS_EXEMPTED_INST_GP5 where STATUS <> 'DELETED')");

		}// else if( isExemptedInst != null && isExemptedInst.equals("N") ) {				
			
			strBuf.append(" and ( CMS_LE_SUB_PROFILE_ID is null or ( CMS_LE_SUB_PROFILE_ID is not null " );
			strBuf.append(" and CMS_LE_SUB_PROFILE_ID not in ( select CMS_LE_SUB_PROFILE_ID from CMS_EXEMPTED_INST_GP5 where STATUS <> 'DELETED') ) ) ");
											
		//}
		
		if( fiInd != null ) {
			
			strBuf.append(" and IS_FINANCIAL_INSTITUTION = '");
			strBuf.append( fiInd );
			strBuf.append("' ");
		}								
		if( lmtBookingID != null ) {
			strBuf.append(" AND LMT_BK_ID <> ? ");			
		}
		//not exists in the group itself
		strBuf.append(" AND NOT EXISTS (select '1' from CMS_LMT_BOOK_DTL where b.LMT_BK_ID = LMT_BK_ID " );
		strBuf.append(" AND BK_TYPE_CAT = '");
		strBuf.append( ICMSConstant.BKG_TYPE_BGEL );
		strBuf.append("' ");
		strBuf.append(" AND STATUS = '");
		strBuf.append( ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED );
		strBuf.append("' ");
		//strBuf.append(" AND BK_TYPE_CODE = ? ) ");
		
		//andCondition.append(" AND CMS_LE_SUB_PROFILE_ID ");
		
        ArrayList params = new ArrayList();
		
		if( lmtBookingID != null ) {
			params.add( lmtBookingID );			
		}
		//params.add( groupID );			
        try {
            dbUtil = new DBUtil();			
			
			andCondition.append(" AND BK_TYPE_CODE ");
			
			ArrayList masterSubGroupID = new ArrayList();
			masterSubGroupID.add( masterGrpID );
			if( subGrpIDList != null ) {
				masterSubGroupID.addAll( subGrpIDList );
			}
            CommonUtil.buildSQLInList(masterSubGroupID, andCondition, params);
            log("\nmasterSubGroupID= " + params);
            strBuf.append(andCondition);			
			
			andCondition = new StringBuffer();
			andCondition.append(" ) AND CMS_LE_SUB_PROFILE_ID ");
			
            CommonUtil.buildSQLInList(subprofileIDList, andCondition, params);
            log("\nparams = " + params);
            log("\nsubprofileIDList = " + subprofileIDList);
            strBuf.append(andCondition);
            log("\nstrBuf retrieveTotalBookForRelatedMember = " + strBuf);
						
            dbUtil.setSQL(strBuf.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();

			return processBookedLimit( rs );
						
            
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new LimitBookingException("LimitBookingDAO Error in getExposureGroup ", e);
        } finally {
            finalize(dbUtil);
            finalize(rs);
        }
    }	
	
    private Amount processExposure (ResultSet rs) throws SQLException{
        Amount amt = null;
         while (rs.next()){
             String curr = rs.getString("exposure_amt_curr");
             BigDecimal value = rs.getBigDecimal("exposure_amt");
			
			 value.setScale(ICMSConstant.LIMIT_BOOKING_DECIMAL_PLACES_2, ICMSConstant.LIMIT_BOOKING_ROUNDING_MODE);
             if (curr != null && value != null)
                 amt = new Amount(value,new CurrencyCode(curr));
         }
         return amt;
     }
	
	private HashMap processExposurePol (ResultSet rs) throws SQLException{
        HashMap polExp = new HashMap();
         while (rs.next()){
             Amount amt = new Amount();
             String polCode = rs.getString("pol");
             String curr = rs.getString("exposure_amt_curr");
             BigDecimal value = rs.getBigDecimal("exposure_amt");
             value.setScale(ICMSConstant.LIMIT_BOOKING_DECIMAL_PLACES_2, ICMSConstant.LIMIT_BOOKING_ROUNDING_MODE);
             if (curr != null && value != null){
                amt = new Amount(value,new CurrencyCode(curr));         
                polExp.put( polCode, amt );
             }
         }
         return polExp;
     }
	
	private HashMap processExposureCustomer (ResultSet rs) throws SQLException, LimitBookingException{
        HashMap customerExp = new HashMap();
         while (rs.next()){
             Amount amt = new Amount();
             Long customerId = new Long(rs.getLong("cms_le_sub_profile_id"));
             String curr = rs.getString("exposure_amt_curr");
             BigDecimal value = rs.getBigDecimal("exposure_amt");
             value.setScale(ICMSConstant.LIMIT_BOOKING_DECIMAL_PLACES_2, ICMSConstant.LIMIT_BOOKING_ROUNDING_MODE);
             if (curr != null && value != null){
                amt = new Amount(value,new CurrencyCode(curr)); 
                
                if (customerExp.containsKey(customerId)) {
                	Amount temp = (Amount) customerExp.get(customerId);
                	try {
                		amt = amt.add(temp);
                	}
                	catch (ChainedException e) {
                		DefaultLogger.error(this, "", e);
                        throw new LimitBookingException("LimitBookingDAO Error in processExposureCustomer ", e);
                	}
                }
                
                customerExp.put( customerId, amt );
             }
         }
         return customerExp;
     }
	
    
    private HashMap processExposureGroup (ResultSet rs) throws SQLException, LimitBookingException{
        HashMap grpExp = new HashMap();
        Amount amt = null;
         while (rs.next()){
            
             String groupId = rs.getString("group_id");
			 
             String curr = rs.getString("exposure_amt_curr");
             BigDecimal value = rs.getBigDecimal("exposure_amt");             
			 value.setScale(ICMSConstant.LIMIT_BOOKING_DECIMAL_PLACES_2, ICMSConstant.LIMIT_BOOKING_ROUNDING_MODE);
			 
			 if (curr != null && value != null) {
                amt = new Amount(value,new CurrencyCode(curr));		
                
                if (grpExp.containsKey(groupId)) {
                	Amount temp = (Amount) grpExp.get(groupId);
                	try {
                		amt = amt.add(temp);
                	}
                	catch (ChainedException e) {
                		DefaultLogger.error(this, "", e);
                        throw new LimitBookingException("LimitBookingDAO Error in processExposureGroup ", e);
                	}
                }
				
                grpExp.put( groupId, amt );
				 
			}
         }
         return grpExp;
     }
	
	private Object[] processBookedLimit (ResultSet rs) throws SQLException, AmountConversionException{
        
		List bookedResult = new ArrayList();
		Amount totalBookedAmt = null;		
		
        while (rs.next()){
            ILimitBooking lmtBooked = processCreateLimitBooking( rs );
						
			if ( lmtBooked.getBkgAmount() != null ) {
				Amount amt = lmtBooked.getBkgAmount();
				if( totalBookedAmt == null ) {
					totalBookedAmt	= amt;
				}		
				else {
					totalBookedAmt = CommonUtil.addAmount( totalBookedAmt, amt );
				}
			}			
			
			bookedResult.add( lmtBooked );
            
        }
        return new Object[] { totalBookedAmt, bookedResult };
     }
	 
	private HashMap processBookedLimitMap (ResultSet rs) throws SQLException, AmountConversionException{
	
		HashMap bookedResultMap = new HashMap();
		List bookedResult = new ArrayList();
		Amount totalBookedAmt = null;		
		String prevTypeCode = null;
		
        while (rs.next()){

            String typeCode = rs.getString("BK_TYPE_CODE");	 			
			
			ILimitBooking lmtBooked = processCreateLimitBooking( rs );			
			
			if ( lmtBooked.getBkgAmount() != null ) {
				Amount amt = lmtBooked.getBkgAmount();
				if( totalBookedAmt == null ) {
					totalBookedAmt	= amt;
				}		
				else {
					totalBookedAmt = CommonUtil.addAmount( totalBookedAmt, amt );
				}
			}
			
			bookedResult.add( lmtBooked );
			
			 if( prevTypeCode != null &&  !prevTypeCode.equals( typeCode ) ) {
				bookedResultMap.put( prevTypeCode, new Object[] { totalBookedAmt, bookedResult } );
				totalBookedAmt = null;						
				bookedResult = new ArrayList();
			 }
			prevTypeCode = typeCode;
            
         }
         if( prevTypeCode != null ) {
			bookedResultMap.put( prevTypeCode, new Object[] { totalBookedAmt, bookedResult } );
		}
		 return bookedResultMap;
	}
	
	
	private HashMap processProductBookedLimitMap (ResultSet rs) throws SQLException, AmountConversionException{
		
		HashMap bookedResultMap = new HashMap();
		List bookedResult = new ArrayList();
		Amount totalBookedAmt = null;		
		String prevTypeCode = null;
		
        while (rs.next()){

            String typeCode = rs.getString("BK_PROD_TYPE_CODE");	 			
			
			ILimitBooking lmtBooked = processCreateLimitBooking( rs );			
			
			if ( lmtBooked.getBkgAmount() != null ) {
				Amount amt = lmtBooked.getBkgAmount();
				if( totalBookedAmt == null ) {
					totalBookedAmt	= amt;
				}		
				else {
					totalBookedAmt = CommonUtil.addAmount( totalBookedAmt, amt );
				}
			}
			
			bookedResult.add( lmtBooked );
			
			 if( prevTypeCode != null &&  !prevTypeCode.equals( typeCode ) ) {
				bookedResultMap.put( prevTypeCode, new Object[] { totalBookedAmt, bookedResult } );
				totalBookedAmt = null;						
				bookedResult = new ArrayList();
			 }
			prevTypeCode = typeCode;
            
         }
         if( prevTypeCode != null ) {
			bookedResultMap.put( prevTypeCode, new Object[] { totalBookedAmt, bookedResult } );
		}
		 return bookedResultMap;
	}
	
	/*private HashMap processBookedLimitGroup (ResultSet rs, boolean isBankEntity) throws SQLException, AmountConversionException{
	
		HashMap bookedResultMap = new HashMap();
		List bookedResult = new ArrayList();
		Amount totalBookedAmt = null;		
		String prevKey = null;
		
        while (rs.next()){
            String typeCode = rs.getString("BK_TYPE_CODE");			 			              
			String bankEntity = rs.getString("BANK_ENTITY");
			String key = isBankEntity ? typeCode.concat( bankEntity ) : typeCode;

			ILimitBooking lmtBooked = processCreateLimitBooking( rs );			
			
			if ( lmtBooked.getBkgAmount() != null ) {
				Amount amt = lmtBooked.getBkgAmount();
				if( totalBookedAmt == null ) {
					totalBookedAmt	= amt;
				}		
				else {
					totalBookedAmt = CommonUtil.addAmount( totalBookedAmt, amt );
				}
			}
			
			bookedResult.add( lmtBooked );						

			if( prevKey != null &&  !prevKey.equals( key ) ) {				

				bookedResultMap.put( prevKey, new Object[] { totalBookedAmt, bookedResult } );
				totalBookedAmt = null;		
			}
			prevKey = key;

            
         }
         if( prevKey != null ) {		 		

				bookedResultMap.put( prevKey, new Object[] { totalBookedAmt, bookedResult } );
		}
		 return bookedResultMap;
	}*/
	
	private ILimitBooking processCreateLimitBooking (ResultSet rs) throws SQLException{
						 			 		
		long bkId = rs.getLong("LMT_BK_ID");
		String ticketNo = rs.getString("TICKET_NO");
		String aaNo = rs.getString("AA_NO");
		String aaSrc = rs.getString("AA_SOURCE");
		String bkName = rs.getString("BK_NAME");
		String busUnit = rs.getString("BK_BUS_UNIT");
		Date dateCreate = rs.getDate("DATE_CREATED");
		String lastModBy = rs.getString("LAST_MODIFIED_BY");
		String curr = rs.getString("BK_CURR");
		BigDecimal value = rs.getBigDecimal("BK_AMT");
		value.setScale(ICMSConstant.LIMIT_BOOKING_DECIMAL_PLACES_2, ICMSConstant.LIMIT_BOOKING_ROUNDING_MODE);
		
		log("\n processCreateLimitBooking.bkId="+bkId);
		log("\n processCreateLimitBooking.value="+value);

		ILimitBooking lmtBooked = new OBLimitBooking();		
		lmtBooked.setLimitBookingID( bkId );
		lmtBooked.setTicketNo( ticketNo );
		lmtBooked.setAaNo( aaNo );
		lmtBooked.setAaSource( aaSrc );
		lmtBooked.setBkgName( bkName );
		
		if (curr != null && value != null) {
			lmtBooked.setBkgAmount( new Amount(value, new CurrencyCode(curr)) );
		}		
		lmtBooked.setBkgBusUnit( busUnit );
		lmtBooked.setCreateDate( dateCreate );
		lmtBooked.setLastModifiedBy( lastModBy );
			 
		return lmtBooked;
	}			 	


	 /**
     *  Used to get booking based on the search Criteria
     * @param searchCriteria
     * @return
     * @throws LimitBookingException
     */

    public SearchResult searchBooking(LimitBookingSearchCriteria searchCriteria) throws LimitBookingException {
        ResultSet rs = null;
        SQLParameter params = SQLParameter.getInstance();
        StringBuffer theSQL = new StringBuffer();
        StringBuffer theSQL2 = new StringBuffer();
        theSQL.append(SEARCH_BOOKING_SQL);
		if("3".equals(searchCriteria.getGobuttonBooking())){
			theSQL.append(" AND char (cms_cust_grp.GRP_ID) = dtl1.BK_TYPE_CODE )\n" );								 
		}
		else {
			theSQL.append(" )\n" );	
		}
        
        try {
            dbUtil = new DBUtil();
            theSQL = formulateQuery(theSQL, searchCriteria, params, false);
            
			if(!"3".equals(searchCriteria.getGobuttonBooking())){
				theSQL2.append(SEARCH_BOOKING_SQL2);
				
				theSQL2 = formulateQuery(theSQL2, searchCriteria, params, true);
				
				theSQL.append(" UNION ").append( theSQL2.toString() );
            }
			
			theSQL.append(SEARCH_BOOKING_ORDER_BY_SQL);

			log("\n"+theSQL.toString());
			
            dbUtil.setSQL( theSQL.toString() );
            CommonUtil.setSQLParams(params, dbUtil);
			
            rs = dbUtil.executeQuery();
            SearchResult searchResult = processResultSet(rs, searchCriteria);
            return searchResult;
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new LimitBookingException("Error in getting limit bookings: ", e);
        } finally {
            finalize(dbUtil, rs);
        }
    }

    /*private DBUtil setParams(DBUtil dbUtil, LimitBookingSearchCriteria searchCriteria) throws SQLException, InvalidStatementTypeException, NoSQLStatementException {
        if ("1".equals(searchCriteria.getGobuttonBooking())){
            dbUtil.setDate(1, new java.sql.Date(searchCriteria.getSearchFromDate().getTime()));
            dbUtil.setDate(2, new java.sql.Date(searchCriteria.getSearchToDate().getTime()));
        } else if ("2".equals(searchCriteria.getGobuttonBooking())){
            dbUtil.setString(1, searchCriteria.getTicketNo());
        } else if ("3".equals(searchCriteria.getGobuttonBooking())){
            dbUtil.setString(1, searchCriteria.getGroupName());
        } else if ("4".equals(searchCriteria.getGobuttonBooking())){
            dbUtil.setString(1, searchCriteria.getCustomerName());
        } else if ("5".equals(searchCriteria.getGobuttonBooking())){
            dbUtil.setString(1, searchCriteria.getIdNo());
        }
        return dbUtil;
    }*/

    private StringBuffer formulateQuery(StringBuffer theSQL, LimitBookingSearchCriteria searchCriteria, SQLParameter params, boolean isLmtBookOnly) throws LimitBookingException {
        if (searchCriteria == null)
            throw new LimitBookingException("Search Criteria is null");
        if ("1".equals(searchCriteria.getGobuttonBooking())){
             theSQL.append(" AND (DATE_CREATED >= ? AND DATE_CREATED <= ? ) \n") ;
             params.addDate(new java.sql.Date(searchCriteria.getSearchFromDate().getTime()));
             params.addDate(new java.sql.Date(searchCriteria.getSearchToDate().getTime()));
        } else if ("2".equals(searchCriteria.getGobuttonBooking())){
             theSQL.append(" AND TICKET_NO = ?  \n");
             params.addString(searchCriteria.getTicketNo());
        } else if ("3".equals(searchCriteria.getGobuttonBooking()) && !isLmtBookOnly ){
            theSQL.append(" and UPPER(cms_cust_grp.GRP_NAME) BETWEEN ? AND ? \n");
            //params.addString(searchCriteria.getGroupName().trim().toUpperCase());
			params.addString(StringUtils.rightPad(searchCriteria.getGroupName().trim().toUpperCase(), 100, ' '));
			// append the customer name to 100 character depends on db column size
			params.addString(StringUtils.rightPad(searchCriteria.getGroupName().trim().toUpperCase(), 100, 'Z'));
			
        } else if ("4".equals(searchCriteria.getGobuttonBooking()) || "6".equals(searchCriteria.getGobuttonBooking())){
            theSQL.append(" AND UPPER(BK_NAME) BETWEEN ? AND ? \n");
            //params.addString(searchCriteria.getCustomerName().trim().toUpperCase());
			
			params.addString(StringUtils.rightPad(searchCriteria.getCustomerName().trim().toUpperCase(), 25, ' '));
			// append the customer name to 100 character depends on db column size
			params.addString(StringUtils.rightPad(searchCriteria.getCustomerName().trim().toUpperCase(), 25, 'Z'));
				
        } else if ("5".equals(searchCriteria.getGobuttonBooking())){
            theSQL.append(" AND BK_ID_NO like ? \n");
            params.addString(searchCriteria.getIdNo());
        }
        return theSQL;
    }
    
    private SearchResult processResultSet(ResultSet rs, LimitBookingSearchCriteria criteria) throws SQLException {
        List resultList = new ArrayList();

        //// weiling - find out when to terminate based on the paging criteria
        int startIndex = criteria.getStartIndex();
        int requiredItems = criteria.getNItems();
        int maxItems = requiredItems * 10; // 10 pages
        if (startIndex >= maxItems)
            maxItems = ((int) Math.ceil(startIndex + 1 / maxItems)) * maxItems;
        int endIndex = startIndex + 10;
        int count = 0;

        DefaultLogger.debug(this, ">>>>>>>> processResultSet - startIndex : " + startIndex);
        DefaultLogger.debug(this, ">>>>>>>> processResultSet - requiredItems : " + requiredItems);
        DefaultLogger.debug(this, ">>>>>>>> processResultSet - endIndex : " + endIndex);

        while (rs.next() && (count <= maxItems)) {
            //// weiling : forward the rs pointer to the startIndex
            //if (count < startIndex) {
            //continue;
            //}

            //// weiling : only instantiate the nitems required
            if (startIndex <= count) {
                if (count < endIndex) {
                	LimitBookingSearchResult result = processResultSet(rs);
                    resultList.add(result);
                }
            }
            count++;
        }
        DefaultLogger.debug(this, "*********************RESULT SET SIZE********" + resultList.size());

        return (count == 0) ? null : new SearchResult(criteria.getStartIndex(), resultList.size(), count, resultList);
    }


    private LimitBookingSearchResult processResultSet(ResultSet rs) throws SQLException {
        
            LimitBookingSearchResult sr = new LimitBookingSearchResult();
            sr.setLimitBookingID(rs.getLong("LMT_BK_ID"));
            sr.setTicketNo(rs.getString("TICKET_NO"));
            sr.setCustomerName(rs.getString("BK_NAME"));
            sr.setSourceSystem(rs.getString("AA_SOURCE"));
            sr.setAaNumber(rs.getString("AA_NO"));
            sr.setCountry(rs.getString("BK_CTRY"));
            sr.setBusinessUnit(rs.getString("BK_BUS_UNIT"));
            sr.setGroupName(rs.getString("GRP_NAME"));
            String currency = rs.getString("BK_CURR");
            if (currency != null && currency != null && !"".equals(currency)){
                sr.setBookedAmt(new Amount(rs.getDouble("BK_AMT"), currency));
            }
            sr.setOverallBkgResult(rs.getString("OVERALL_RESULT"));
            sr.setDateLimitBooked(rs.getDate("DATE_CREATED"));
            sr.setLastUpdatedBy(rs.getString("LAST_MODIFIED_BY"));
            sr.setBkgStatus(rs.getString("BK_STATUS"));
            sr.setExpiryDate(rs.getDate("EXPIRY_DATE"));

           return sr; 
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
            throws LimitBookingException {
        try {
            if (dbUtil != null)
                dbUtil.close();
        } catch (Exception e) {
            throw new LimitBookingException("Error in cleaning up DB resources: " + e.toString());
        }
    }
    private void finalize(ResultSet rs)
            throws LimitBookingException {
        try {
            if (rs != null)
                rs.close();
        } catch (Exception e) {
            throw new LimitBookingException("Error in cleaning up DB resources: " + e.toString());
        }
    }
    
    private void log(String str){
        DefaultLogger.debug(this, str);
    }

}

