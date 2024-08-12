/*
* Copyright Integro Technologies Pte Ltd
* $Header$
*/
package com.integrosys.cms.app.limitbooking.proxy;

import javax.ejb.SessionContext;
import javax.ejb.SessionBean;
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.commodity.common.AmountConversionException;

import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitBusManager;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimitBusManager;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameterBusManager;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.*;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.*;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

import com.integrosys.cms.app.transaction.*;
import com.integrosys.base.businfra.transaction.*;

import com.integrosys.cms.app.limitbooking.trx.LimitBookingTrxControllerFactory;
import com.integrosys.cms.app.limitbooking.trx.ILimitBookingTrxValue;
import com.integrosys.cms.app.limitbooking.trx.OBLimitBookingTrxValue;
import com.integrosys.cms.app.limitbooking.bus.*;

import com.integrosys.cms.app.limitbooking.bus.LimitBookingException;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingDAO;
import com.integrosys.cms.app.customer.bus.CustomerDAO;


//import com.integrosys.cms.app.creditriskparam.bus.countrylimit.SBCountryLimitBusManager;
//import com.integrosys.cms.app.creditriskparam.bus.countrylimit.CountryLimitBusManagerFactory;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimit;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.CountryLimitException;

import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.SBExemptedInstBusManager;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.ExemptedInstBusManagerFactory;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.ExemptedInstException;

//import com.integrosys.cms.app.creditriskparam.bus.entitylimit.SBEntityLimitBusManager;
//import com.integrosys.cms.app.creditriskparam.bus.entitylimit.EntityLimitBusManagerFactory;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.EntityLimitException;

import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.InternalLimitException;

import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifierBusManager;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierBusManagerFactory;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.IGroupOtrLimit;
import com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierException;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierDAO;

import com.integrosys.cms.app.cci.bus.CCICustomerDAO;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import com.integrosys.cms.ui.custgrpi.CustGrpIdentifierUIHelper;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.math.BigDecimal;

/**
* This session bean provides the implementation of
* the AbstractLimitBookingProxy, wrapped in an EJB mechanism.
*
* @author $Author: jychong $
* @version $Revision: 1.66 $
* @since $Date: 2006/11/23 08:12:05 $
* Tag: $Name:  $
*/
public class SBLimitBookingProxyBean extends AbstractLimitBookingProxy implements SessionBean {
	
	public static final int SCALE = 4;
	public static final int ROUNDING_MODE = ICMSConstant.LIMIT_BOOKING_ROUNDING_MODE;
	private static final long serialVersionUID = 1L;

    SectorLimitParameterJDBCImpl sectorLimitJDBC;
    ISectorLimitBusManager sectorLimitBusManager;

    IProductLimitBusManager productLimitBusManager;
    ProductLimitParameterJDBCImpl productLimitJDBC;

    protected IProductLimitBusManager getProductLimitBusManager() {
         return (IProductLimitBusManager) BeanHouse.get("productLimitParameterBusManager");
        //return productLimitBusManager;
    }

    public void setProductLimitBusManager(IProductLimitBusManager productLimitBusManager) {
        this.productLimitBusManager = productLimitBusManager;
    }

    protected ProductLimitParameterJDBCImpl getProductLimitJDBC() {
         return (ProductLimitParameterJDBCImpl) BeanHouse.get("ProductLimitParameterJDBC");
        //return productLimitJDBC;
    }

    public void setProductLimitJDBC(ProductLimitParameterJDBCImpl productLimitJDBC) {
        this.productLimitJDBC = productLimitJDBC;
    }

    protected SectorLimitParameterJDBCImpl getSectorLimitJDBC() {
        return (SectorLimitParameterJDBCImpl) BeanHouse.get("SectorLimitParameterJDBC");
        //return sectorLimitJDBC;
    }

    public void setSectorLimitJDBC(SectorLimitParameterJDBCImpl sectorLimitJDBC) {
        this.sectorLimitJDBC = sectorLimitJDBC;
    }

    protected ISectorLimitBusManager getSectorLimitBusManager() {
        return (ISectorLimitBusManager) BeanHouse.get("sectorLimitParameterBusManager");
        //return sectorLimitBusManager;
    }

    public void setSectorLimitBusManager(ISectorLimitBusManager sectorLimitBusManager) {

        this.sectorLimitBusManager = sectorLimitBusManager;
    }

    /**
    * SessionContext object
    */
    private SessionContext _context = null;
    /**
    * Default Constructor
    */
    public SBLimitBookingProxyBean() {}

    public ILimitBooking retrieveLimitBookingResult(ILimitBooking limitBooking) throws LimitBookingException, AmountConversionException {
        try {
            DefaultLogger.debug (this, "retrieveLimitBookingResult limitBooking.getLimitBookingID: " + limitBooking.getLimitBookingID());
            DefaultLogger.debug (this, "retrieveLimitBookingResult limitBooking.getSubProfileID: " + limitBooking.getSubProfileID());
			LimitBookingDAO dao = new LimitBookingDAO();
			Long lmtBookingID = null;
			if( limitBooking.getLimitBookingID() != ICMSConstant.LONG_INVALID_VALUE ) {
				lmtBookingID = new Long( limitBooking.getLimitBookingID() );			
			}
			
			Amount yourBooking = limitBooking.getBkgAmount();
			Amount convertedCurrBookAmt = LimitBookingHelper.convertBaseAmount( yourBooking );
            ArrayList bookResultList = new ArrayList();
			HashMap cciMap = null;
			
			//CustomerDAO custDao = new CustomerDAO();
			//limitBooking.setSubProfileID( custDao.searchCustomerByIDNumber( limitBooking.getBkgIDNo() ) );
			
			if( limitBooking.getSubProfileID() != null ) {
				CCICustomerDAO cciDao = new CCICustomerDAO();
				cciMap = cciDao.getCommonCustomer( limitBooking.getSubProfileID().longValue() );
			}
			
			String isExempt = getIsExemptedInst( limitBooking.getSubProfileID() );

            // 1. Country
			ILimitBookingDetail lmtBookDtls = createCountryLimit( limitBooking.getBkgCountry(), convertedCurrBookAmt );
			//country limit is set
			if( lmtBookDtls != null ) {
				bookResultList.addAll( dao.retrieveCountryBookingResult( lmtBookingID, lmtBookDtls ) );
            }
			
			 //2. POL - sectoral limit
			if( limitBooking.getLoanSectorList() != null && !limitBooking.getLoanSectorList().isEmpty() ) {
				
				List productProgramList = createProductProgramList( limitBooking.getLoanSectorList());
				if( productProgramList != null && !productProgramList.isEmpty() ) {
					bookResultList.addAll( dao.retrieveProductProgramBookingResult(lmtBookingID, productProgramList) );
				}
				else if (productProgramList == null) {
					List ecoLoanSectorList = createEcoSectoralList( limitBooking.getLoanSectorList(), ICMSConstant.BANKING_GROUP_ENTRY_CODE);
						if( ecoLoanSectorList != null && !ecoLoanSectorList.isEmpty() ) {
							bookResultList.addAll( dao.retrieveEcoLoanSectorBookingResult(lmtBookingID, ecoLoanSectorList, ICMSConstant.BANKING_GROUP_ENTRY_CODE) );
					}
				
					List subLoanSectorList = createSubSectoralList( limitBooking.getLoanSectorList(), ICMSConstant.BANKING_GROUP_ENTRY_CODE);
						if( subLoanSectorList != null && !subLoanSectorList.isEmpty() ) {
							bookResultList.addAll( dao.retrieveSubLoanSectorBookingResult(lmtBookingID, subLoanSectorList, ICMSConstant.BANKING_GROUP_ENTRY_CODE) );
					}
				
					List mainLoanSectorList = createMainSectoralLimit( limitBooking.getLoanSectorList(),  ICMSConstant.BANKING_GROUP_ENTRY_CODE);
						if( mainLoanSectorList != null && !mainLoanSectorList.isEmpty() ) {
							bookResultList.addAll( dao.retrieveMainLoanSectorBookingResult(lmtBookingID, mainLoanSectorList, ICMSConstant.BANKING_GROUP_ENTRY_CODE) );
					}
				
					List ecoLoanSectorListByBankEntity = createEcoSectoralList( limitBooking.getLoanSectorList(), limitBooking.getBkgBankEntity());
						if( ecoLoanSectorListByBankEntity != null && !ecoLoanSectorListByBankEntity.isEmpty() ) {
							bookResultList.addAll( dao.retrieveEcoLoanSectorBookingResult(lmtBookingID, ecoLoanSectorListByBankEntity, limitBooking.getBkgBankEntity()) );
					}
				
					List subLoanSectorListByBankEntity = createSubSectoralList( limitBooking.getLoanSectorList(), limitBooking.getBkgBankEntity() );
						if( subLoanSectorListByBankEntity != null && !subLoanSectorListByBankEntity.isEmpty() ) {
							bookResultList.addAll( dao.retrieveSubLoanSectorBookingResult(lmtBookingID, subLoanSectorListByBankEntity, limitBooking.getBkgBankEntity()) );
					}
				
					List parentLoanSectorListByBankEntity = createMainSectoralLimit( limitBooking.getLoanSectorList(), limitBooking.getBkgBankEntity() );
						if( parentLoanSectorListByBankEntity != null && !parentLoanSectorListByBankEntity.isEmpty() ) {
							bookResultList.addAll( dao.retrieveMainLoanSectorBookingResult(lmtBookingID, parentLoanSectorListByBankEntity, limitBooking.getBkgBankEntity()) );
					}
				}
			}
            
			//3 Entity	
			//no need to show Entity limit if the customer is exempted institution
			if( limitBooking.getSubProfileID() != null && isExempt.equals("N") ) {
				ILimitBookingDetail entityLmtBookDtls = createEntityLimit( limitBooking, cciMap, convertedCurrBookAmt );
				if( entityLmtBookDtls != null ) {
					bookResultList.addAll( dao.retrieveEntityBookingResult( lmtBookingID, entityLmtBookDtls, cciMap ) );
				}
			}
			
			//4 Bank Entity
			if( limitBooking.getBankGroupList() != null && !limitBooking.getBankGroupList().isEmpty() ) {
				List bankEntityList = createBankEntityLimit( limitBooking.getBankGroupList(), limitBooking.getBkgBankEntity(), convertedCurrBookAmt );
				if( bankEntityList != null && !bankEntityList.isEmpty() ) {
					bookResultList.addAll( dao.retrieveBankEntityBookingResult( lmtBookingID, bankEntityList ) );
				}
			}
			
			//Group
			if( limitBooking.getBankGroupList() != null && !limitBooking.getBankGroupList().isEmpty() ) {
				String isFI = limitBooking.getIsFinancialInstitution();							
				
				List bankEntityList = createGroupLimit( limitBooking.getBankGroupList(), isFI, isExempt, convertedCurrBookAmt );
				if( bankEntityList != null && !bankEntityList.isEmpty() ) {
					bookResultList.addAll( dao.retrieveGroupBookingResult( lmtBookingID, bankEntityList, isFI, isExempt ) );
				}
			}	
			
			//Bank Wide Banking Group Customer
			
			List bankWideBankingGroupCustomerList = createBankWideCustomerLimit(limitBooking.getSubProfileID(), ICMSConstant.BANKING_GROUP_ENTRY_CODE, cciMap, convertedCurrBookAmt, limitBooking.getBkgName());
			
			if(bankWideBankingGroupCustomerList != null && !bankWideBankingGroupCustomerList.isEmpty()) {
				
				bookResultList.addAll(dao.retrieveBankWideCustomerBookingResult(limitBooking, limitBooking.getSubProfileID(), cciMap, bankWideBankingGroupCustomerList, ICMSConstant.BANKING_GROUP_ENTRY_CODE));
			}
			
			//Bank Wide Bank Entity Customer
			
			List bankWideBankEntityCustomerList = createBankWideCustomerLimit(limitBooking.getSubProfileID(), limitBooking.getBkgBankEntity(), cciMap, convertedCurrBookAmt, limitBooking.getBkgName());
			
			if(bankWideBankEntityCustomerList != null && !bankWideBankEntityCustomerList.isEmpty()) {
				
				bookResultList.addAll(dao.retrieveBankWideCustomerBookingResult(limitBooking, limitBooking.getSubProfileID(), cciMap, bankWideBankEntityCustomerList, limitBooking.getBkgBankEntity()));
			}
			
			//Bank Wide Banking Group Customer Group
			if( limitBooking.getBankGroupList() != null && !limitBooking.getBankGroupList().isEmpty()) {
				
				List bankWideBankingGroupCustomerGroupList = createBankWideBankEntityCustomerGroupLimit(limitBooking.getBankGroupList(), ICMSConstant.BANKING_GROUP_ENTRY_CODE, convertedCurrBookAmt);
				
				if(bankWideBankingGroupCustomerGroupList != null && !bankWideBankingGroupCustomerGroupList.isEmpty() ) {
					
					bookResultList.addAll(dao.retrieveBankEntityBookingResult(lmtBookingID, bankWideBankingGroupCustomerGroupList));
				}
			}
			
			//Bank Wide Bank Entity Customer Group
			if( limitBooking.getBankGroupList() != null && !limitBooking.getBankGroupList().isEmpty()) {
				
				List bankWideBankEntityCustomerGroupList = createBankWideBankEntityCustomerGroupLimit(limitBooking.getBankGroupList(), limitBooking.getBkgBankEntity(), convertedCurrBookAmt);
				
				if(bankWideBankEntityCustomerGroupList != null && !bankWideBankEntityCustomerGroupList.isEmpty() ) {
					
					bookResultList.addAll(dao.retrieveBankEntityBookingResult(lmtBookingID, bankWideBankEntityCustomerGroupList));
				}
			}
			
							
			List currBooking =  limitBooking.getAllBkgs();
			if( currBooking != null ) {
				currBooking.clear();
			}
			else {
				currBooking = new ArrayList();
			}	
	       
	        currBooking.addAll(bookResultList);
	        limitBooking.setAllBkgs(currBooking);
           			
            // Change to Base currency
            limitBooking = LimitBookingHelper.computeBookingResults(limitBooking, "MYR");
											                   
			return limitBooking;
	
		
		} catch(AmountConversionException e) {        	
			_context.setRollbackOnly();		
            throw e;	
        }       
        catch(Exception e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }
    
    public Map getProductTypeCodeMap() throws ProductLimitException   
    {
		try{
			Map productTypeCodeMap = new HashMap();
			productTypeCodeMap = getProductLimitJDBC().retrieveProductTypeCodeMap();
			return productTypeCodeMap;
    	}  catch(ProductLimitException e) {           
        e.printStackTrace();
        	throw new ProductLimitException("Caught Exception!", e);
    	} 
	}
    
    public Map getEcoSectorCodeMap() throws SectorLimitException   
    {
		try{
			Map ecoSectorCodeMap = new HashMap();
			ecoSectorCodeMap = getSectorLimitJDBC().retrieveEcoSectorCodeMap();
			return ecoSectorCodeMap;
    	}  catch(SectorLimitException e) {           
        e.printStackTrace();
        	throw new SectorLimitException("Caught Exception!", e);
    	} 
	}
	
	
	private Amount getCountryLimit(String countryCode) throws LimitBookingException 
    {
		try {
			if( countryCode != null && countryCode.trim().length() != 0 ) {
//				SBCountryLimitBusManager mgr = CountryLimitBusManagerFactory.getActualCountryLimitBusManager();
                ICountryLimitBusManager mgr = getCountryLimitBusManager();
				ICountryLimit ctryLimit = mgr.getCountryLimitByCountryCode( countryCode );
				if( ctryLimit != null ) {
					Amount lmtAmt = ctryLimit.getCountryLimitAmount();
					return lmtAmt;
				}
			}
			return null;
		} catch(CountryLimitException e) {           
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        
		}
//        catch(RemoteException e) {
//            e.printStackTrace();
//            throw new LimitBookingException("Caught Exception!", e);
//        }
	}
	
	private ILimitBookingDetail createCountryLimit(String ctryCode, Amount currBookAmt) throws LimitBookingException, AmountConversionException{
        
		ILimitBookingDetail countryDtl = null;
		Amount ctryLmtAmt = getCountryLimit( ctryCode );
		//country limit is set
		if( !LimitBookingHelper.isEmptyAmount( ctryLmtAmt ) ) {	
        
			countryDtl = new OBLimitBookingDetail();
			countryDtl.setBkgType(ICMSConstant.BKG_TYPE_COUNTRY);
	        countryDtl.setBkgTypeCode( ctryCode );
	        countryDtl.setBkgTypeDesc(CountryList.getInstance().getCountryName( ctryCode ));
			
			countryDtl.setBkgAmount( currBookAmt );
			countryDtl.setBkgBaseAmount( currBookAmt );
			
			//DefaultLogger.debug (this, "createCountryLimit ctryLmtAmt: " + ctryLmtAmt);

			countryDtl.setLimitAmount( LimitBookingHelper.convertBaseAmount( ctryLmtAmt ) );
			//DefaultLogger.debug (this, "createCountryLimit after convert ctryLmtAmt: " + LimitBookingHelper.convertBaseAmount( ctryLmtAmt ) );

        }
		
        return countryDtl;
    }
	
	private ILimitBookingDetail createEntityLimit(ILimitBooking limitBooking, HashMap cciMap, Amount currBookAmt) throws LimitBookingException, AmountConversionException{
        		
		ILimitBookingDetail entityDtl = null;
		Amount entityLmtAmt = getEntityLimit( limitBooking.getSubProfileID(), cciMap );
		if( !LimitBookingHelper.isEmptyAmount( entityLmtAmt ) ) {
                
			entityDtl = new OBLimitBookingDetail();
			entityDtl.setBkgType(ICMSConstant.BKG_TYPE_ENTITY);
	        entityDtl.setBkgTypeCode(String.valueOf( limitBooking.getSubProfileID() ));
	        entityDtl.setBkgTypeDesc( limitBooking.getBkgName() );
			
	        entityDtl.setBkgAmount( currBookAmt );
			entityDtl.setBkgBaseAmount( currBookAmt );
			entityDtl.setLimitAmount( LimitBookingHelper.convertBaseAmount( entityLmtAmt ) );
		
        }
		
        return entityDtl;
    }
	
	private List createProductProgramList(List loanSectorList) throws LimitBookingException, AmountConversionException
    {
		try {
			List col = null;
			if( loanSectorList != null && !loanSectorList.isEmpty() ) {
			
				for (Iterator iterator = loanSectorList.iterator(); iterator.hasNext();) {
					ILoanSectorDetail loanSector = (OBLoanSectorDetail) iterator.next();
				
					IProductTypeLimitParameter productLimit = getProductLimitBusManager().getProductTypeLimitParameterByRefCode( loanSector.getBkgProdTypeCode() );

					String productProgramRefCode = getProductLimitJDBC().retrieveProductProgRefCode(loanSector.getBkgProdTypeCode());
					
					if (productLimit != null && productProgramRefCode != null) {
						
						IProductProgramLimitParameter productProgramLimit = getProductLimitBusManager().getProductProgramLimitParameterByRefCode(productProgramRefCode);

						col = new ArrayList();
						
						if (productProgramLimit != null && productProgramLimit.getLimitAmount() != null) {
							
							ILimitBookingDetail bookResult = new OBLimitBookingDetail( loanSector );
							
							bookResult.setBkgType(ICMSConstant.BKG_TYPE_PROD_PROG);
							
							bookResult.setLimitAmount( LimitBookingHelper.convertBaseAmount( productProgramLimit.getLimitAmount()) );
							bookResult.setBkgAmount( loanSector.getBkgAmount() );
							bookResult.setBkgBaseAmount( LimitBookingHelper.convertBaseAmount( loanSector.getBkgAmount() ) );
							col.add( bookResult );
						}
					}
				}//end for
			}	
			return col;
			
		} catch(ProductLimitException e) {           
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
	}
	
	private List createEcoSectoralList(List loanSectorList, String entityType) throws LimitBookingException, AmountConversionException
    {
		try {
			List col = new ArrayList();
			if( loanSectorList != null && !loanSectorList.isEmpty() ) {
			
				Amount entityLimit = getInternalLimit( entityType );
				if( entityLimit == null ) {
					return null;
				}

				for (Iterator iterator = loanSectorList.iterator(); iterator.hasNext();) {
					ILoanSectorDetail loanSector = (OBLoanSectorDetail) iterator.next();
				
					IEcoSectorLimitParameter sectorLimit = getSectorLimitBusManager().getEcoSectorLimitBySectorCode( loanSector.getBkgTypeCode() );
					
					if (sectorLimit != null) {

						ILimitBookingDetail bookResult = new OBLimitBookingDetail( loanSector );
						Double lmtPercent = sectorLimit.getLimitPercentage();
						
						if (lmtPercent != null) {
						//calculate
							BigDecimal result = new BigDecimal( lmtPercent.doubleValue() ).divide(new BigDecimal( 100 ), SCALE, ROUNDING_MODE).multiply( entityLimit.getAmountAsBigDecimal() );
											
							bookResult.setBkgType(ICMSConstant.BKG_TYPE_ECO_POL);
							bookResult.setBkgSubType(entityType);

							bookResult.setLimitAmount( LimitBookingHelper.convertBaseAmount( new Amount( result, new CurrencyCode( entityLimit.getCurrencyCode() ) ) ) );
							bookResult.setBkgAmount( loanSector.getBkgAmount() );
							bookResult.setBkgBaseAmount( LimitBookingHelper.convertBaseAmount( loanSector.getBkgAmount() ) );
							col.add( bookResult );
						}
					}	
					
				}//end for
			}	
			return col;
			
		} catch(SectorLimitException e) {           
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        
		/*} catch(RemoteException e) {
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);*/
        }
	}
	
	
	private List createSubSectoralList(List loanSectorList, String entityType) throws LimitBookingException, AmountConversionException
    {
		try {
			List col = new ArrayList();
			if( loanSectorList != null && !loanSectorList.isEmpty() ) {
			
				Amount entityLimit = getInternalLimit( entityType );
				if( entityLimit == null ) {
					return null;
				}
               
				for (Iterator iterator = loanSectorList.iterator(); iterator.hasNext();) {
					ILoanSectorDetail loanSector = (OBLoanSectorDetail) iterator.next();

						String subLoanSectorCode = getSectorLimitJDBC().retrieveSubSectorCode(loanSector.getBkgTypeCode());
						
						if (subLoanSectorCode != null) {
						
							ISubSectorLimitParameter subSectorLimit = getSectorLimitBusManager().getSubSectorLimitBySectorCode( subLoanSectorCode );
							
							ILimitBookingDetail subBookResult = new OBLimitBookingDetail( loanSector );
								
							subBookResult.setBkgTypeCode(subLoanSectorCode);
								
							subBookResult.setBkgTypeDesc(subSectorLimit.getLoanPurposeCode());
								
							Double subLmtPercent = subSectorLimit.getLimitPercentage();
							
							if (subLmtPercent != null) {
								
								BigDecimal subResult = new BigDecimal( subLmtPercent.doubleValue() ).divide(new BigDecimal( 100 ), SCALE, ROUNDING_MODE).multiply( entityLimit.getAmountAsBigDecimal() );
													
								subBookResult.setBkgType(ICMSConstant.BKG_TYPE_SUB_POL);
								subBookResult.setBkgSubType(entityType);

								subBookResult.setLimitAmount( LimitBookingHelper.convertBaseAmount( new Amount( subResult, new CurrencyCode( entityLimit.getCurrencyCode() ) ) ) );
								subBookResult.setBkgAmount( loanSector.getBkgAmount() );
								subBookResult.setBkgBaseAmount( LimitBookingHelper.convertBaseAmount( loanSector.getBkgAmount() ) );
								col.add( subBookResult );
								
							}
						}
				}//end for
			}	
			return col;
			
		} catch(SectorLimitException e) {           
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        
		/*} catch(RemoteException e) {
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);*/
        }
	}
	
		
	private List createMainSectoralLimit(List loanSectorList, String entityType) throws LimitBookingException, AmountConversionException
    {
		try {
			List col = new ArrayList();
			if( loanSectorList != null && !loanSectorList.isEmpty() ) {
			
				Amount entityLimit = getInternalLimit( entityType );
				if( entityLimit == null ) {
					return null;
				}
				for (Iterator iterator = loanSectorList.iterator(); iterator.hasNext();) {
					ILoanSectorDetail loanSector = (OBLoanSectorDetail) iterator.next();

					String subLoanSectorCode = getSectorLimitJDBC().retrieveSubSectorCode(loanSector.getBkgTypeCode());
					
					if (subLoanSectorCode != null) {
						
						String mainLoanSectorCode = getSectorLimitJDBC().retrieveMainSectorCode(subLoanSectorCode);
						
						if (mainLoanSectorCode != null) {
						
							IMainSectorLimitParameter mainSectorLimit = getSectorLimitBusManager().getMainSectorLimitBySectorCode( mainLoanSectorCode );
							
							ILimitBookingDetail mainBookResult = new OBLimitBookingDetail( loanSector );
								
							mainBookResult.setBkgTypeCode(mainLoanSectorCode);
							mainBookResult.setBkgSubType(entityType);
								
							mainBookResult.setBkgTypeDesc(mainSectorLimit.getLoanPurposeCode());
								
							Double parentLmtPercent = mainSectorLimit.getLimitPercentage();
							
							if (parentLmtPercent != null) {
								
								BigDecimal mainResult = new BigDecimal( parentLmtPercent.doubleValue() ).divide(new BigDecimal( 100 ), SCALE, ROUNDING_MODE).multiply( entityLimit.getAmountAsBigDecimal() );
													
								mainBookResult.setBkgType(ICMSConstant.BKG_TYPE_MAIN_POL);

								mainBookResult.setLimitAmount( LimitBookingHelper.convertBaseAmount( new Amount( mainResult, new CurrencyCode( entityLimit.getCurrencyCode() ) ) ) );
								mainBookResult.setBkgAmount( loanSector.getBkgAmount() );
								mainBookResult.setBkgBaseAmount( LimitBookingHelper.convertBaseAmount( loanSector.getBkgAmount() ) );
								col.add( mainBookResult );
							}
						}
					}			
							
				}//end for
			}	
			return col;
			
		} catch(SectorLimitException e) {           
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        
		/*} catch(RemoteException e) {
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);*/
        }
		catch(Exception e) {           
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        
		}
	}
	
	
	
	private List createBankEntityLimit(List bankEntityList, String entityType, Amount currBookAmt) throws LimitBookingException, AmountConversionException 
    {
		try {
			List col = new ArrayList();
			if( bankEntityList != null && !bankEntityList.isEmpty() ) {
							
				for (Iterator iterator = bankEntityList.iterator(); iterator.hasNext();) {
					OBBankGroupDetail bankGrp = (OBBankGroupDetail) iterator.next();
			    
					//BGEL limit is set 
					if( !LimitBookingHelper.isEmptyAmount( bankGrp.getLimitAmount() ) ) {
						ILimitBookingDetail bookResult = new OBLimitBookingDetail( bankGrp );
						bookResult.setBkgType(ICMSConstant.BKG_TYPE_BGEL);
						bookResult.setBkgSubType( ICMSConstant.BKG_SUB_TYPE_BANK );                       
						//bookResult.setBkgSubTypeCode();                      
						//bookResult.setBkgSubTypeDesc();         
						bookResult.setLimitAmount( LimitBookingHelper.convertBaseAmount( bankGrp.getLimitAmount() ) );
						bookResult.setBkgAmount(currBookAmt);
						bookResult.setBkgBaseAmount(currBookAmt);
						col.add( bookResult );						
	
					}
					
					if( !LimitBookingHelper.isEmptyAmount( bankGrp.getLimitConvAmount() ) && entityType.equals( ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE ) ) {
						ILimitBookingDetail bookResult = new OBLimitBookingDetail( bankGrp );
						bookResult.setBkgType(ICMSConstant.BKG_TYPE_BGEL);
						bookResult.setBkgSubType( ICMSConstant.BKG_SUB_TYPE_CONV );                       
						//bookResult.setBkgSubTypeCode();                      
						//bookResult.setBkgSubTypeDesc();                     
						bookResult.setLimitAmount( LimitBookingHelper.convertBaseAmount( bankGrp.getLimitConvAmount() ) );
						bookResult.setBkgAmount(currBookAmt);
						bookResult.setBkgBaseAmount(currBookAmt);
						col.add( bookResult );	
					}
					if( !LimitBookingHelper.isEmptyAmount( bankGrp.getLimitIslamAmount() ) && entityType.equals( ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE ) ) {
						ILimitBookingDetail bookResult = new OBLimitBookingDetail( bankGrp );
						bookResult.setBkgType(ICMSConstant.BKG_TYPE_BGEL);
						bookResult.setBkgSubType( ICMSConstant.BKG_SUB_TYPE_ISLM );                       
						//bookResult.setBkgSubTypeCode();                      
						//bookResult.setBkgSubTypeDesc();                     
						bookResult.setLimitAmount( LimitBookingHelper.convertBaseAmount( bankGrp.getLimitIslamAmount() ) );
						bookResult.setBkgAmount(currBookAmt);
						bookResult.setBkgBaseAmount(currBookAmt);
						col.add( bookResult );	
					}
					if( !LimitBookingHelper.isEmptyAmount( bankGrp.getLimitInvAmount() ) && entityType.equals( ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE ) ) {
						ILimitBookingDetail bookResult = new OBLimitBookingDetail( bankGrp );
						bookResult.setBkgType(ICMSConstant.BKG_TYPE_BGEL);
						bookResult.setBkgSubType( ICMSConstant.BKG_SUB_TYPE_INV );                       
						//bookResult.setBkgSubTypeCode();                      
						//bookResult.setBkgSubTypeDesc();        
						bookResult.setLimitAmount( LimitBookingHelper.convertBaseAmount( bankGrp.getLimitInvAmount() ) );
						bookResult.setBkgAmount(currBookAmt);
						bookResult.setBkgBaseAmount(currBookAmt);
						col.add( bookResult );						
	
					}							
					
				}//end for
			}	
			return col;
			
		} catch(AmountConversionException e) {        
            throw e;
        
		} catch(Exception e) {           
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
	}
	
	
	private List createBankWideCustomerLimit(Long subProfileId, String entityType, HashMap cciMap, Amount currBookAmt, String customerName) throws LimitBookingException, AmountConversionException 
    {
		try {
				List col = new ArrayList();
			
				Amount grpGp5Lmt = CustGrpIdentifierUIHelper.getGroupLimit(CustGroupUIHelper.INT_LMT_GP5_REQ, entityType);
				
				Amount grpIntLmtPercentage = CustGrpIdentifierUIHelper.getGroupLimit(CustGroupUIHelper.INT_LMT_CAP_FUND_PERCENT, entityType);
				
				if (entityType.equals(ICMSConstant.BANKING_GROUP_ENTRY_CODE)) {
					
					ILimitBookingDetail bookResultGp5Lmt = new OBLimitBookingDetail();
					bookResultGp5Lmt.setBkgType(ICMSConstant.BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER);
					bookResultGp5Lmt.setBkgSubType(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_BG);   
					bookResultGp5Lmt.setBkgTypeCode(String.valueOf(subProfileId));
					bookResultGp5Lmt.setBkgTypeDesc(customerName);
					bookResultGp5Lmt.setLimitAmount(LimitBookingHelper.convertBaseAmount(grpGp5Lmt));
					bookResultGp5Lmt.setBkgAmount(currBookAmt);
					bookResultGp5Lmt.setBkgBaseAmount(currBookAmt);
					col.add(bookResultGp5Lmt);
					
					ILimitBookingDetail bookResultIntLmtPercentage = new OBLimitBookingDetail();
					bookResultIntLmtPercentage.setBkgType(ICMSConstant.BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER);
					bookResultIntLmtPercentage.setBkgSubType(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_BG);  
					bookResultIntLmtPercentage.setBkgTypeCode(String.valueOf(subProfileId));
					bookResultIntLmtPercentage.setBkgTypeDesc(customerName);
					bookResultIntLmtPercentage.setLimitAmount(LimitBookingHelper.convertBaseAmount(grpIntLmtPercentage));
					bookResultIntLmtPercentage.setBkgAmount(currBookAmt);
					bookResultIntLmtPercentage.setBkgBaseAmount(currBookAmt);
					col.add(bookResultIntLmtPercentage);
					
				}
				
				else if (entityType.equals(ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE)) {

					ILimitBookingDetail bookResultGp5Lmt = new OBLimitBookingDetail();
					bookResultGp5Lmt.setBkgType(ICMSConstant.BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER);
					bookResultGp5Lmt.setBkgSubType(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_CONV);   
					bookResultGp5Lmt.setBkgTypeCode(String.valueOf(subProfileId));
					bookResultGp5Lmt.setBkgTypeDesc(customerName);
					bookResultGp5Lmt.setLimitAmount(LimitBookingHelper.convertBaseAmount(grpGp5Lmt));
					bookResultGp5Lmt.setBkgAmount(currBookAmt);
					bookResultGp5Lmt.setBkgBaseAmount(currBookAmt);
					col.add(bookResultGp5Lmt);
					
					ILimitBookingDetail bookResultIntLmtPercentage = new OBLimitBookingDetail();
					bookResultIntLmtPercentage.setBkgType(ICMSConstant.BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER);
					bookResultIntLmtPercentage.setBkgSubType(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_CONV);
					bookResultIntLmtPercentage.setBkgTypeCode(String.valueOf(subProfileId));
					bookResultIntLmtPercentage.setBkgTypeDesc(customerName);
					bookResultIntLmtPercentage.setLimitAmount(LimitBookingHelper.convertBaseAmount(grpIntLmtPercentage));
					bookResultIntLmtPercentage.setBkgAmount(currBookAmt);
					bookResultIntLmtPercentage.setBkgBaseAmount(currBookAmt);
					col.add(bookResultIntLmtPercentage);	
					
				}
				
				else if (entityType.equals(ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE)) {
					
					ILimitBookingDetail bookResultGp5Lmt = new OBLimitBookingDetail();
					bookResultGp5Lmt.setBkgType(ICMSConstant.BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER);
					bookResultGp5Lmt.setBkgSubType(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_ISLM); 
					bookResultGp5Lmt.setBkgTypeCode(String.valueOf(subProfileId));
					bookResultGp5Lmt.setBkgTypeDesc(customerName);
					bookResultGp5Lmt.setLimitAmount(LimitBookingHelper.convertBaseAmount(grpGp5Lmt));
					bookResultGp5Lmt.setBkgAmount(currBookAmt);
					bookResultGp5Lmt.setBkgBaseAmount(currBookAmt);
					col.add(bookResultGp5Lmt);
					
					ILimitBookingDetail bookResultIntLmtPercentage = new OBLimitBookingDetail();
					bookResultIntLmtPercentage.setBkgType(ICMSConstant.BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER);
					bookResultIntLmtPercentage.setBkgSubType(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_ISLM);
					bookResultIntLmtPercentage.setBkgTypeCode(String.valueOf(subProfileId));
					bookResultIntLmtPercentage.setBkgTypeDesc(customerName);
					bookResultIntLmtPercentage.setLimitAmount(LimitBookingHelper.convertBaseAmount(grpIntLmtPercentage));
					bookResultIntLmtPercentage.setBkgAmount(currBookAmt);
					bookResultIntLmtPercentage.setBkgBaseAmount(currBookAmt);
					col.add(bookResultIntLmtPercentage);	
					
				}
				
				else if ( entityType.equals(ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE)) {
					
					ILimitBookingDetail bookResultGp5Lmt = new OBLimitBookingDetail();
					bookResultGp5Lmt.setBkgType(ICMSConstant.BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER);
					bookResultGp5Lmt.setBkgSubType(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_INV);  
					bookResultGp5Lmt.setBkgTypeCode(String.valueOf(subProfileId));
					bookResultGp5Lmt.setBkgTypeDesc(customerName);
					bookResultGp5Lmt.setLimitAmount(LimitBookingHelper.convertBaseAmount(grpGp5Lmt));
					bookResultGp5Lmt.setBkgAmount(currBookAmt);
					bookResultGp5Lmt.setBkgBaseAmount(currBookAmt);
					col.add(bookResultGp5Lmt);
					
					ILimitBookingDetail bookResultIntLmtPercentage = new OBLimitBookingDetail();
					bookResultIntLmtPercentage.setBkgType(ICMSConstant.BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER);
					bookResultIntLmtPercentage.setBkgSubType(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_INV);
					bookResultIntLmtPercentage.setBkgTypeCode(String.valueOf(subProfileId));
					bookResultIntLmtPercentage.setBkgTypeDesc(customerName);
					bookResultIntLmtPercentage.setLimitAmount(LimitBookingHelper.convertBaseAmount(grpIntLmtPercentage));
					bookResultIntLmtPercentage.setBkgAmount(currBookAmt);
					bookResultIntLmtPercentage.setBkgBaseAmount(currBookAmt);
					col.add(bookResultIntLmtPercentage);	
					
				}						
			
			return col;
			
		} catch(AmountConversionException e) {        
            throw e;
        
		} catch(Exception e) {           
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
	}
	
	
	private List createBankWideBankEntityCustomerGroupLimit(List groupList, String entityType, Amount currBookAmt) throws LimitBookingException, AmountConversionException 
    {
		try {
			List col = new ArrayList();
			
			Amount grpGp5Lmt = CustGrpIdentifierUIHelper.getGroupLimit(CustGroupUIHelper.INT_LMT_GP5_REQ, entityType);
			
			Amount grpIntLmtPercentage = CustGrpIdentifierUIHelper.getGroupLimit(CustGroupUIHelper.INT_LMT_CAP_FUND_PERCENT, entityType);
			
			for (Iterator iterator = groupList.iterator(); iterator.hasNext();) {
				
				OBBankGroupDetail bankGrp = (OBBankGroupDetail) iterator.next();
					
				if (entityType.equals(ICMSConstant.BANKING_GROUP_ENTRY_CODE)) {
					
					ILimitBookingDetail bookResultGp5Lmt = new OBLimitBookingDetail(bankGrp);
					bookResultGp5Lmt.setBkgType(ICMSConstant.BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER_GROUP);
					bookResultGp5Lmt.setBkgSubType(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_BG);                       
					bookResultGp5Lmt.setLimitAmount(LimitBookingHelper.convertBaseAmount(grpGp5Lmt));
					bookResultGp5Lmt.setBkgAmount(currBookAmt);
					bookResultGp5Lmt.setBkgBaseAmount(currBookAmt);
					col.add(bookResultGp5Lmt);
					
					ILimitBookingDetail bookResultIntLmtPercentage = new OBLimitBookingDetail(bankGrp);
					bookResultIntLmtPercentage.setBkgType(ICMSConstant.BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER_GROUP);
					bookResultIntLmtPercentage.setBkgSubType(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_BG);                       
					bookResultIntLmtPercentage.setLimitAmount(LimitBookingHelper.convertBaseAmount(grpIntLmtPercentage));
					bookResultIntLmtPercentage.setBkgAmount(currBookAmt);
					bookResultIntLmtPercentage.setBkgBaseAmount(currBookAmt);
					col.add(bookResultIntLmtPercentage);
					
				}
				
				else if(entityType.equals( ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE)) {
					
					ILimitBookingDetail bookResultGp5Lmt = new OBLimitBookingDetail(bankGrp);
					bookResultGp5Lmt.setBkgType(ICMSConstant.BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER_GROUP);
					bookResultGp5Lmt.setBkgSubType(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_CONV);                       
					bookResultGp5Lmt.setLimitAmount(LimitBookingHelper.convertBaseAmount(grpGp5Lmt));
					bookResultGp5Lmt.setBkgAmount(currBookAmt);
					bookResultGp5Lmt.setBkgBaseAmount(currBookAmt);
					col.add(bookResultGp5Lmt);

					ILimitBookingDetail bookResultIntLmtPercentage = new OBLimitBookingDetail(bankGrp);
					bookResultIntLmtPercentage.setBkgType(ICMSConstant.BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER_GROUP);
					bookResultIntLmtPercentage.setBkgSubType(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_CONV);                       
					bookResultIntLmtPercentage.setLimitAmount(LimitBookingHelper.convertBaseAmount(grpIntLmtPercentage));
					bookResultIntLmtPercentage.setBkgAmount(currBookAmt);
					bookResultIntLmtPercentage.setBkgBaseAmount(currBookAmt);
					col.add(bookResultIntLmtPercentage);	
					
				}
				
				else if(entityType.equals( ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE)) {
				
					ILimitBookingDetail bookResultGp5Lmt = new OBLimitBookingDetail(bankGrp);
					bookResultGp5Lmt.setBkgType(ICMSConstant.BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER_GROUP);
					bookResultGp5Lmt.setBkgSubType(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_ISLM);                      
					bookResultGp5Lmt.setLimitAmount(LimitBookingHelper.convertBaseAmount(grpGp5Lmt));
					bookResultGp5Lmt.setBkgAmount(currBookAmt);
					bookResultGp5Lmt.setBkgBaseAmount(currBookAmt);
					col.add(bookResultGp5Lmt);
					
					ILimitBookingDetail bookResultIntLmtPercentage = new OBLimitBookingDetail(bankGrp);
					bookResultIntLmtPercentage.setBkgType(ICMSConstant.BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER_GROUP);
					bookResultIntLmtPercentage.setBkgSubType(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_ISLM);
					bookResultIntLmtPercentage.setLimitAmount(LimitBookingHelper.convertBaseAmount(grpIntLmtPercentage));
					bookResultIntLmtPercentage.setBkgAmount(currBookAmt);
					bookResultIntLmtPercentage.setBkgBaseAmount(currBookAmt);
					col.add(bookResultIntLmtPercentage);	
				
				}
				
				else if( entityType.equals( ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE)) {
					
					ILimitBookingDetail bookResultGp5Lmt = new OBLimitBookingDetail(bankGrp);
					bookResultGp5Lmt.setBkgType(ICMSConstant.BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER_GROUP);
					bookResultGp5Lmt.setBkgSubType(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_INV);                      
					bookResultGp5Lmt.setLimitAmount(LimitBookingHelper.convertBaseAmount(grpGp5Lmt));
					bookResultGp5Lmt.setBkgAmount(currBookAmt);
					bookResultGp5Lmt.setBkgBaseAmount(currBookAmt);
					col.add(bookResultGp5Lmt);
				
					ILimitBookingDetail bookResultIntLmtPercentage = new OBLimitBookingDetail(bankGrp);
					bookResultIntLmtPercentage.setBkgType(ICMSConstant.BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER_GROUP);
					bookResultIntLmtPercentage.setBkgSubType(ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_INV);
					bookResultIntLmtPercentage.setLimitAmount(LimitBookingHelper.convertBaseAmount(grpIntLmtPercentage));
					bookResultIntLmtPercentage.setBkgAmount(currBookAmt);
					bookResultIntLmtPercentage.setBkgBaseAmount(currBookAmt);
					col.add(bookResultIntLmtPercentage);	
				
				}						
			}
			return col;
			
		} catch(AmountConversionException e) {        
            throw e;
        
		} catch(Exception e) {           
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
	}
	
	
	private List createGroupLimit(List groupList, String isFI, String isExempt, Amount currBookAmt) throws LimitBookingException, AmountConversionException 
    {

		try {
			List col = new ArrayList();					
				DefaultLogger.debug (this, "createGroupLimit isFI: " + isFI );
				DefaultLogger.debug (this, "createGroupLimit isExempt: " + isExempt );
									 
			ICustGrpIdentifierBusManager mgr = CustGrpIdentifierBusManagerFactory.getActualCustGrpIdentifierBusManager();

			for (Iterator iterator = groupList.iterator(); iterator.hasNext();) {
				OBBankGroupDetail bankGrp = (OBBankGroupDetail) iterator.next();
				DefaultLogger.debug (this, "createGroupLimit getCustGrpIdentifierByTrxIDRef: " + Long.parseLong( bankGrp.getBkgTypeCode() ));
				
				ICustGrpIdentifier grpInfo = mgr.getCustGrpIdentifierByTrxIDRef( Long.parseLong( bankGrp.getBkgTypeCode() ) );
				
				DefaultLogger.debug (this, "createGroupLimit grpInfo: " + grpInfo);
				if( grpInfo != null ) {
					Amount bgelLimit = grpInfo.getGroupLmt();	
					DefaultLogger.debug (this, "createGroupLimit bgelLimit: " + bgelLimit);
					//1
					if( !LimitBookingHelper.isEmptyAmount( bgelLimit ) && isFI.equals("N") ) {
						ILimitBookingDetail bookResult = new OBLimitBookingDetail( bankGrp );
						bookResult.setBkgType(ICMSConstant.BKG_TYPE_BGEL);
						bookResult.setBkgSubType( ICMSConstant.BKG_SUB_TYPE_BANK_NONFI );                       
						//bookResult.setBkgSubTypeCode();                      
						//bookResult.setBkgSubTypeDesc();         
						bookResult.setLimitAmount( LimitBookingHelper.convertBaseAmount( bgelLimit ) );

						bookResult.setBkgAmount(currBookAmt);
						bookResult.setBkgBaseAmount(currBookAmt);
						col.add( bookResult );	
					}						
					else if( isFI.equals("Y") && isExempt.equals("Y") ) {
						IGroupOtrLimit[] othLimitList = grpInfo.getGroupOtrLimit();
						
						if(othLimitList != null && othLimitList.length > 0 ) {
							DefaultLogger.debug (this, " othLimitList length: " + othLimitList.length);

							for (int i=0; i<othLimitList.length; i++) {
								DefaultLogger.debug (this, " othLimitList : " + othLimitList[i]);

								if( othLimitList[i].getOtrLimitTypeCD().equals ( ICMSConstant.OTHER_LIMIT_TYPE_INTER_EXEMPT_ENTRY_CODE ) ) {
																		
									ILimitBookingDetail bookResult = new OBLimitBookingDetail( bankGrp );
									bookResult.setBkgType(ICMSConstant.BKG_TYPE_BGEL);
									bookResult.setBkgSubType( ICMSConstant.BKG_SUB_TYPE_EXEMPT );                       
									//bookResult.setBkgSubTypeCode();                      
									//bookResult.setBkgSubTypeDesc();         
									DefaultLogger.debug (this, "createGroupLimit other limit: " + othLimitList[i].getLimitAmt());
									bookResult.setLimitAmount( LimitBookingHelper.convertBaseAmount( othLimitList[i].getLimitAmt() ) );
									bookResult.setBkgAmount(currBookAmt);
									bookResult.setBkgBaseAmount(currBookAmt);
									col.add( bookResult );	
									break;
								}
								
							}
						}	//end if othLimitList != null			
					
					}	
					else if( isFI.equals("Y") && isExempt.equals("N") ) {
						
						IGroupSubLimit[] subLimitList = grpInfo.getGroupSubLimit();						

						if(subLimitList != null && subLimitList.length > 0 ) {
							DefaultLogger.debug (this, " subLimitList length: " + subLimitList.length);

							for (int i=0; i<subLimitList.length; i++) {
								DefaultLogger.debug (this, " subLimitList : " + subLimitList[i]);
								if( subLimitList[i].getSubLimitTypeCD().equals ( ICMSConstant.SUB_LIMIT_TYPE_INTER_NON_EXEMPT_ENTRY_CODE ) &&
										subLimitList[i].getDescription().equals ( ICMSConstant.SUB_LIMIT_DESC_INTER_LIMIT_ENTRY_CODE ) 	) {
									ILimitBookingDetail bookResult = new OBLimitBookingDetail( bankGrp );
									bookResult.setBkgType(ICMSConstant.BKG_TYPE_BGEL);
									bookResult.setBkgSubType( ICMSConstant.BKG_SUB_TYPE_NON_EXEMPT );                       
									//bookResult.setBkgSubTypeCode();                      
									//bookResult.setBkgSubTypeDesc();         
									DefaultLogger.debug (this, "createGroupLimit sub limit: " + subLimitList[i].getLimitAmt());
									bookResult.setLimitAmount( LimitBookingHelper.convertBaseAmount( subLimitList[i].getLimitAmt() ) );
									bookResult.setBkgAmount(currBookAmt);
									bookResult.setBkgBaseAmount(currBookAmt);
									col.add( bookResult );	
									break;
								}
							}//end for
						}//end if subLimitList != null
						
					}
					
				}//grpInfo != null 
			}//end for
			
			return col;
			
		} catch(CustGrpIdentifierException e) {           
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);        		
		
		} catch(AmountConversionException e) {        
            throw e;
        		
		} catch(Exception e) {           
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
	}
	
	private Amount getInternalLimit(String entityType) throws LimitBookingException 
    {
		try {
			if( entityType != null && entityType.trim().length() != 0 ) {
			
//				SBInternalLimitParameterBusManager mgr = getSBInternalLimitParameterManager();
                IInternalLimitParameterBusManager mgr = (IInternalLimitParameterBusManager)BeanHouse.get("internalLimitParameterBusManager");
				IInternalLimitParameter intLimit = mgr.getInternalLimitParameterByEntityType( entityType );				

				if( intLimit != null ) {
					String ccy = intLimit.getTotalLoanAdvanceAmountCurrencyCode();
					double lmtAmt = intLimit.getTotalLoanAdvanceAmount();
					
					if( lmtAmt != 0 && ccy != null ) {

			        	return new Amount( new BigDecimal( lmtAmt ), new CurrencyCode( ccy ) );
			        }		
					
				}
			}
			return null;
		} catch(InternalLimitException e) {           
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        
//		} catch(RemoteException e) {           
//            e.printStackTrace();
//            throw new LimitBookingException("Caught Exception!", e);
        }
	}
	
//	protected SBInternalLimitParameterBusManager getSBInternalLimitParameterManager() {
//		return (SBInternalLimitParameterBusManager) BeanController.getEJB(
//				ICMSJNDIConstant.SB_ACTUAL_INTERNAL_LIMIT_JNDI,
//				SBInternalLimitParameterBusManagerHome.class.getName());
//	}
	
	private Amount getEntityLimit(Long subprofileID, HashMap cciMap) throws LimitBookingException 
    {
		try {
//			SBEntityLimitBusManager mgr = EntityLimitBusManagerFactory.getActualEntityLimitBusManager();
            IEntityLimitBusManager mgr = getEntityLimitBusManager();

			if( subprofileID != null ) {
				IEntityLimit entityLimit = mgr.getEntityLimitBySubProfileID( subprofileID.longValue() );
				if( entityLimit != null ) {
					Amount lmtAmt = entityLimit.getLimitAmount();
					return lmtAmt;
				}
			}
			
			if ( cciMap != null ) {
				
				Long foundSubProfileId = (Long) cciMap.get( ICMSConstant.SOURCE_ID_ARBS );
				if( foundSubProfileId != null )
				{
					IEntityLimit entityLimit = mgr.getEntityLimitBySubProfileID( foundSubProfileId.longValue() );
					if( entityLimit != null ) {
						Amount lmtAmt = entityLimit.getLimitAmount();
						return lmtAmt;
					}
				}
				foundSubProfileId = (Long) cciMap.get( ICMSConstant.SOURCE_ID_SEMA );
				if( foundSubProfileId != null )
				{
					IEntityLimit entityLimit = mgr.getEntityLimitBySubProfileID( foundSubProfileId.longValue() );
					if( entityLimit != null ) {
						Amount lmtAmt = entityLimit.getLimitAmount();
						return lmtAmt;
					}
				}
				foundSubProfileId = (Long) cciMap.get( ICMSConstant.SOURCE_ID_BOST );
				if( foundSubProfileId != null )
				{
					IEntityLimit entityLimit = mgr.getEntityLimitBySubProfileID( foundSubProfileId.longValue() );
					if( entityLimit != null ) {
						Amount lmtAmt = entityLimit.getLimitAmount();
						return lmtAmt;
					}
				}
			
			}
			return null;
		} catch(EntityLimitException e) {
           
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        
		}
//        catch(RemoteException e) {
//            e.printStackTrace();
//            throw new LimitBookingException("Caught Exception!", e);
//        }
	}
	
	private String getIsExemptedInst(Long subprofileID) throws LimitBookingException 
    {
		try {
			if( subprofileID != null ) {
				SBExemptedInstBusManager mgr = ExemptedInstBusManagerFactory.getActualExemptedInstBusManager();
				IExemptedInst exemptedInst = mgr.getExemptedInstBySubProfileID( subprofileID.longValue() );
				if( exemptedInst != null ) {
					return "Y";
				}
			}
			return "N";
			
		} catch(ExemptedInstException e) {
           
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        } catch(RemoteException e) {           
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
	}
	
	public ILimitBookingTrxValue createLimitBooking(ITrxContext ctx, ILimitBookingTrxValue trxVal, ILimitBooking limitBooking) throws LimitBookingException {
        try {
            OBCMSTrxParameter param = new OBCMSTrxParameter();
            param.setAction(ICMSConstant.ACTION_MAKER_CREATE_APPROVE_LIMIT_BOOKING);
			if( trxVal == null )
			{
				trxVal = new OBLimitBookingTrxValue();			
			}														
			//DefaultLogger.debug (this, " limitBooking.getAllBkgs: " + limitBooking.getAllBkgs());
			LimitBookingHelper.updateBookingDetails(limitBooking);
			trxVal.setStagingLimitBooking (limitBooking);
           
			return (ILimitBookingTrxValue) operate (constructTrxValue (ctx, trxVal), param);

        }       
        catch(Exception e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }  
    
    
	/**
	* @see com.integrosys.cms.app.limitbooking.proxy.ILimitBookingProxy#makerUpdateLimitBooking
	*/
	public ILimitBookingTrxValue makerUpdateLimitBooking (ITrxContext ctx,
		ILimitBookingTrxValue trxVal, ILimitBooking value)
		throws LimitBookingException
	{
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_MAKER_UPDATE_APPROVE_LIMIT_BOOKING);
		trxVal.setStagingLimitBooking (value);
		
		return (ILimitBookingTrxValue) operate (constructTrxValue (ctx, trxVal), param);
	}
	
	/**
	* @see com.integrosys.cms.app.limitbooking.proxy.ILimitBookingProxy#getLimitBookingTrxValue
	*/
	public ILimitBookingTrxValue getLimitBookingTrxValue (ITrxContext ctx,
			long limitBookingID )
		throws LimitBookingException
	{
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_READ_LIMIT_BOOKING);
		OBLimitBookingTrxValue trxValue = new OBLimitBookingTrxValue();
		trxValue.setReferenceID (String.valueOf(limitBookingID));
		return (ILimitBookingTrxValue) operate (constructTrxValue (ctx, trxValue), param);
	}
	
	/**
	* @see com.integrosys.cms.app.limitbooking.proxy.ILimitBookingProxy#getLimitBookingTrxValueByTrxID
	*/
	public ILimitBookingTrxValue getLimitBookingTrxValueByTrxID (ITrxContext ctx, String trxID)
		throws LimitBookingException
	{
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_READ_LIMIT_BOOKING_BY_TRXID);
		OBLimitBookingTrxValue trxValue = new OBLimitBookingTrxValue();
		trxValue.setTransactionID (trxID);
		return (ILimitBookingTrxValue) operate (constructTrxValue (ctx, trxValue), param);
	}

	
	/**
	* @see com.integrosys.cms.app.limitbooking.proxy.ILimitBookingProxy#makerSuccessLimitBooking
	*/
	public ILimitBookingTrxValue makerSuccessLimitBooking (ITrxContext ctx,
		ILimitBookingTrxValue trxVal, String lastModBy)
		throws LimitBookingException
	{
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_MAKER_SUCCESS_APPROVE_LIMIT_BOOKING);
		trxVal.getLimitBooking().setLastModifiedBy (lastModBy);
		trxVal.getStagingLimitBooking().setLastModifiedBy (lastModBy);
		
		return (ILimitBookingTrxValue) operate (constructTrxValue (ctx, trxVal), param);
	}

	/**
	* @see com.integrosys.cms.app.limitbooking.proxy.ILimitBookingProxy#makerDeleteLimitBooking
	*/
    public ILimitBookingTrxValue makerDeleteLimitBooking (ITrxContext ctx,
           ILimitBookingTrxValue trxVal, String lastModBy)
		throws LimitBookingException
	{
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_APPROVE_LIMIT_BOOKING);
		trxVal.getLimitBooking().setLastModifiedBy (lastModBy);
		trxVal.getStagingLimitBooking().setLastModifiedBy (lastModBy);
		
		return (ILimitBookingTrxValue) operate (constructTrxValue (ctx, trxVal), param);
	}
	   
    public Long getSubProfileIDByIDNumber(String idNo) throws LimitBookingException{
        try{
            CustomerDAO custDao = new CustomerDAO();
			return custDao.searchCustomerByIDNumber( idNo );
        }catch(Exception e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }
	
	public List retrieveBGELGroup(Long subprofileID) throws LimitBookingException{
        try{
			if( subprofileID == null ) {
				return null;
			}
            CustGrpIdentifierDAO dao = new CustGrpIdentifierDAO();
            return dao.retrieveBGELGroup(subprofileID.longValue());
			
			
        }catch(SearchDAOException e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        
		}catch(Exception e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }
	
	public SearchResult searchBooking(LimitBookingSearchCriteria searchCriteria) throws LimitBookingException{
        try{
            LimitBookingDAO dao = new LimitBookingDAO();
            return dao.searchBooking(searchCriteria);
        }catch(Exception e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }
	
	public List retrieveMasterGroupBySubGroupID(List subgroupIDList) throws LimitBookingException{
        try{
			
            CustGrpIdentifierDAO dao = new CustGrpIdentifierDAO();
            return dao.retrieveMasterGroupBySubGroupID( subgroupIDList );
			
			
        }catch(SearchDAOException e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        
		}catch(Exception e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }

    protected IEntityLimitBusManager getEntityLimitBusManager() {
        return (IEntityLimitBusManager) BeanHouse.get("entityLimitBusManager");
    }

    protected ICountryLimitBusManager getCountryLimitBusManager() {
        return (ICountryLimitBusManager) BeanHouse.get("countryLimitBusManager");
    }
	
	private ITrxValue constructTrxValue(ITrxContext ctx, ITrxValue trxValue)
    {
        if (trxValue instanceof ILimitBookingTrxValue)
        {
            ILimitBookingTrxValue colTrx = (ILimitBookingTrxValue) trxValue;
			colTrx.setTransactionType(ICMSConstant.INSTANCE_LIMIT_BOOKING);			
            colTrx.setTrxContext (ctx);

        }
        else {
            ((ICMSTrxValue)trxValue).setTrxContext(ctx);
        }
        return trxValue;
    }

	/**
	 * Helper method to operate transactions.
	 *
	 * @param trxVal is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @throws LimitBookingException on errors encountered
	 */
	private ITrxValue operate(ITrxValue trxVal, ITrxParameter param) throws LimitBookingException
	{
		if (trxVal == null) {
			throw new LimitBookingException("ITrxValue is null!");
		}

		try {
			ITrxController controller = null;

			if (trxVal instanceof ILimitBookingTrxValue) {
				controller = (new LimitBookingTrxControllerFactory()).getController(trxVal, param);
			} 

			if (controller == null) {
				throw new LimitBookingException("ITrxController is null!");
			}

			ITrxResult result = controller.operate(trxVal, param);
			ITrxValue obj = result.getTrxValue();
			
			return obj;
		}
		catch (LimitBookingException e) {
			e.printStackTrace();
			rollback();
			throw e;
		}
		catch (TransactionException e) {
			e.printStackTrace();
			rollback();
			throw new LimitBookingException("TransactionException caught! " + e.toString(), e);
		}
		catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new LimitBookingException("Exception caught! " + e.toString(), e);
		}
	}	 
	
    protected void rollback() throws LimitBookingException
    {
        try {
            _context.setRollbackOnly();
        }
        catch (Exception e) {
            throw new LimitBookingException (e.toString());
        }
    }


    //************* EJB Methods *****************

    /* EJB Methods */
    /**
    * Called by the container to create a session bean instance. Its parameters typically
    * contain the information the client uses to customize the bean instance for its use.
    * It requires a matching pair in the bean class and its home interface.
    */
    public void ejbCreate    ()
    {
    }
    /**
    * A container invokes this method before it ends the life of the session object. This
    * happens as a result of a client's invoking a remove operation, or when a container
    * decides to terminate the session object after a timeout. This method is called with
    * no transaction context.
    */
    public void ejbRemove    ()
    {

    }

    /**
    * The activate method is called when the instance is activated from its 'passive' state.
    * The instance should acquire any resource that it has released earlier in the ejbPassivate()
    * method. This method is called with no transaction context.
    */
    public void ejbActivate    ()
    {

    }

    /**
    * The passivate method is called before the instance enters the 'passive' state. The
    * instance should release any resources that it can re-acquire later in the ejbActivate()
    * method. After the passivate method completes, the instance must be in a state that
    * allows the container to use the Java Serialization protocol to externalize and store
    * away the instance's state. This method is called with no transaction context.
    */
    public void ejbPassivate    ()
    {

    }
    /**
    * Set the associated session context. The container calls this method after the instance
    * creation. The enterprise Bean instance should store the reference to the context
    * object in an instance variable. This method is called with no transaction context.
    */
    public void setSessionContext    (SessionContext sc)
    {
        _context = sc;
    }
}