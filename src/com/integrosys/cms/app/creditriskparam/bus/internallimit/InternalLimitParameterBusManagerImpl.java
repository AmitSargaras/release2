package com.integrosys.cms.app.creditriskparam.bus.internallimit;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.*;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.AbstractCountryLimitTrxOperation;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierBusManagerFactory;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierException;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifierBusManager;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Aug 10, 2010
 * Time: 4:16:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class InternalLimitParameterBusManagerImpl extends AbstractInternalLimitParameterBusManager {

    private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getId", "setId", "getGroupID", "setGroupID", "getCommonRef", "setCommonRef" };
    public static final int SCALE = 4;
	public static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

    public String getInternalLimitParameterEntityName() {
        return IInternalLimitParameterDao.ACTUAL_ENTITY_NAME;
    }

    protected ICountryLimitBusManager getCountryLimitBusManager() {
        return (ICountryLimitBusManager) BeanHouse.get("countryLimitBusManager");
    }

    protected ICountryLimitBusManager getStagingCountryLimitBusManager() {
        return (ICountryLimitBusManager) BeanHouse.get("stagingCountryLimitBusManager");
    }

    public List getALLInternalLimitParameter() throws InternalLimitException {

		List actualList;

		try {
			actualList = findAll();
        } catch (Exception e) {
			throw new InternalLimitException(e);
		}

        if (actualList == null) {
            return new ArrayList();
        } else {
            List returnList = new ArrayList(actualList);
            Collections.copy(returnList, actualList);
            return returnList;
        }
	}

    public List getInternalLimitParameterByGroupID(long groupID) throws InternalLimitException {

        List actualList;

		try {
			actualList = findByGroupId(groupID);
        } catch (Exception e) {
			throw new InternalLimitException(e);
		}

        if (actualList == null) {
            return new ArrayList();
        } else {
            List returnList = new ArrayList(actualList);
            Collections.copy(returnList, actualList);
            return returnList;
        }
	}

	public IInternalLimitParameter getInternalLimitParameterByEntityType(String type) throws InternalLimitException {
		try {
			IInternalLimitParameter internalLimitParameter = findByEntityType(type);
            IInternalLimitParameter returnILP = new OBInternalLimitParameter();

            AccessorUtil.copyValue(internalLimitParameter,returnILP);
			return returnILP;
            
		} catch (Exception e) {
			throw new InternalLimitException(e);
		}
	}

    public List createInternalListParameter(List ilpList) throws InternalLimitException {

        List returnList = new ArrayList();
		if (ilpList == null && ilpList.size() == 0) {
			return returnList;
		}

		try {
			long groupID = ICMSConstant.LONG_MIN_VALUE;


			for (int index = 0; index < ilpList.size(); index++) {

				IInternalLimitParameter ilP = new OBInternalLimitParameter((IInternalLimitParameter) ilpList.get(index));
				ilP.setGroupID(groupID);
                long id = ilP.getId();

                ilP = create(ilP);

                if (ilP.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
                    ilP.setGroupID (ilP.getId());
                }
                ilP.setCommonRef(id);
                ilP = update(ilP);

                groupID = ilP.getGroupID ();

                IInternalLimitParameter returnILP = new OBInternalLimitParameter();
                AccessorUtil.copyValue(ilP, returnILP);
				returnList.add(returnILP);
			}
		} catch (Exception e) {
			throw new InternalLimitException(e);
		}

		return returnList;
    }

    public List updateInternalListParameter(List ilPList) throws InternalLimitException {
        List returnList = new ArrayList();
		IInternalLimitParameter bankIntLimit = null;
       
//		if (ilPList == null && ilPList.size() == 0) {
        if (ilPList == null || ilPList.size() == 0) {
			return returnList;
		}
		try {
			for (int index = 0; index < ilPList.size(); index++) {
				IInternalLimitParameter ilP = (IInternalLimitParameter) ilPList.get(index);
                IInternalLimitParameter local = findByPrimaryKey(new Long(ilP.getCommonRef()));
                
                AccessorUtil.copyValue(ilP, local, EXCLUDE_METHOD_UPDATE);
                local = update(local);

                IInternalLimitParameter intLimit = new OBInternalLimitParameter();
                AccessorUtil.copyValue(local, intLimit);

				returnList.add(intLimit);

				if( intLimit.getDescriptionCode().equals(ICMSConstant.BANKING_GROUP_CODE) && intLimit.getStatus().equals(ICMSConstant.STATE_ACTIVE)) {
					bankIntLimit = intLimit;
				}
			}

            //update country limit amount after checker approve the internal limit trx
			updateCountryLimitAmount(bankIntLimit);

			updateGroupLimitAmount(bankIntLimit);

		}
		catch (InternalLimitException e) {
            DefaultLogger.error (this, "", e);
            throw e;
        }
		return returnList;
    }

    private void updateCountryLimitAmount (IInternalLimitParameter bankIntLimit) throws InternalLimitException {

		try {

			if( bankIntLimit != null ) {

				double capFundAmt = bankIntLimit.getCapitalFundAmount();

//				SBCountryLimitBusManager mgr = CountryLimitBusManagerFactory.getActualCountryLimitBusManager();
//				SBCountryLimitBusManager stgMgr = CountryLimitBusManagerFactory.getStagingCountryLimitBusManager();
                ICountryLimitBusManager mgr = getCountryLimitBusManager();
                ICountryLimitBusManager stgMgr = getStagingCountryLimitBusManager();

				HashMap ratingMap = new HashMap();
				ICountryLimitParam stageCountryLimitParam = null;
				ICountryLimitParam actualCountryLimitParam = null;
				ICountryLimit[] actualCtryLimit = null;
				ICountryLimit[] stagingCtryLimit = null;

				ICountryRating[] actualCountryRating = mgr.getCountryRating ();

				String actualRefID = null;
				String stagingRefID = null;
				if (actualCountryRating != null && actualCountryRating.length > 0)
				{
					actualRefID = String.valueOf ( (actualCountryRating[0]).getGroupID() );

					if (actualRefID != null) {
						DefaultLogger.debug (this,"************ group id/ actualRefID"+actualRefID);

						try {
							ICMSTrxValue cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType (actualRefID, ICMSConstant.INSTANCE_COUNTRY_LIMIT);

							stagingRefID = cmsTrxValue.getStagingReferenceID();
						}
						catch (Exception e) {
							// do nothing here coz the the first col CountryLimits created without trx
						}
					}
				}

				if (actualRefID != null) {
					// get actual Country Limit
					actualCountryLimitParam = mgr.getCountryLimitParamByGroupID (Long.parseLong (actualRefID));
					actualCountryRating = actualCountryLimitParam.getCountryRatingList();
					actualCtryLimit = actualCountryLimitParam.getCountryLimitList();
				}

				if(stagingRefID!=null)	{
					// get staging Country Limit
					stageCountryLimitParam = stgMgr.getCountryLimitParamByGroupID (Long.parseLong (stagingRefID));
					stagingCtryLimit = stageCountryLimitParam.getCountryLimitList();
				}

				//pupulate country rating info
				if( actualCountryRating != null && actualCountryRating.length > 0 ) {
					for (int i = 0; i < actualCountryRating.length; i++) {
						ratingMap.put( actualCountryRating[i].getCountryRatingCode(), actualCountryRating[i] );
					}
				}

				if( actualCtryLimit != null ) {
					AbstractCountryLimitTrxOperation.calcCountryLimitAmount( actualCtryLimit, ratingMap, new BigDecimal( capFundAmt ) );
					mgr.updateCountryLimitAmount ( actualCtryLimit );

				}

				if( stagingCtryLimit != null ) {
					AbstractCountryLimitTrxOperation.calcCountryLimitAmount( stagingCtryLimit, ratingMap, new BigDecimal( capFundAmt ) );
					stgMgr.updateCountryLimitAmount ( stagingCtryLimit );
				}

			}

		}
		catch (CountryLimitException e) {
			throw new InternalLimitException(
			"CountryLimitException in updateCountryLimitAmount(): " + e.toString());
		}
		catch (Exception e) {
			throw new InternalLimitException(
			"Exception in updateCountryLimitAmount(): " + e.toString());
		}
	}

/**
	     * Changes in Internal limit parameter should be reflected to groups that are using "By GP5 Requirement" and "By % Capital Fund" as BGEL amount
	     *
	     * @param bankIntLimit of type IInternalLimitParameter
	     * @throws InternalLimitException on errors encountered
	     */
	private void updateGroupLimitAmount ( IInternalLimitParameter bankIntLimit ) throws InternalLimitException {

		try {

			if( bankIntLimit != null ) {

				double capFundAmt = bankIntLimit.getCapitalFundAmount();
				double gp5LimitPercent = bankIntLimit.getGp5LimitPercentage();
				double intLimitPercent = bankIntLimit.getInternalLimitPercentage();
				String capFundCcy = bankIntLimit.getCapitalFundAmountCurrencyCode();

				//cap fund
				//capFundAmt * gp5LimitPercent / 100 * intLimitPercent / 100
				BigDecimal capFundResult = new BigDecimal( capFundAmt ).
												multiply( new BigDecimal( gp5LimitPercent ) ).divide(new BigDecimal( 100 ), SCALE, ROUNDING_MODE).
												multiply( new BigDecimal( intLimitPercent ) ).divide(new BigDecimal( 100 ), SCALE, ROUNDING_MODE);

				//gp5
				//capFundAmt * gp5LimitPercent / 100
				BigDecimal gp5Result = new BigDecimal( capFundAmt ).
												multiply( new BigDecimal( gp5LimitPercent ) ).divide(new BigDecimal( 100 ), SCALE, ROUNDING_MODE);
				//Amount capAmt = new Amount( capFundResult.doubleValue(), capFundCcy );
		  	    //Amount gp5Amt = new Amount( gp5Result.doubleValue(), capFundCcy );
                Amount capAmt = new Amount( capFundResult,new CurrencyCode(capFundCcy) );
                Amount gp5Amt = new Amount( gp5Result, new CurrencyCode(capFundCcy ) );


				ICustGrpIdentifierBusManager mgr = CustGrpIdentifierBusManagerFactory.getActualCustGrpIdentifierBusManager();
				ICustGrpIdentifierBusManager stgMgr = CustGrpIdentifierBusManagerFactory.getStagingCustGrpIdentifierBusManager();

				ICustGrpIdentifier[] actualGrpForGP5Req = mgr.getCustGrpByInternalLimitType ( CustGroupUIHelper.INT_LMT_GP5_REQ );
				ICustGrpIdentifier[] actualGrpForCapFund = mgr.getCustGrpByInternalLimitType ( CustGroupUIHelper.INT_LMT_CAP_FUND_PERCENT );

				List stagingGrpIDList = new ArrayList();
				String actualRefID = null;
				String stagingRefID = null;
				if (actualGrpForCapFund != null && actualGrpForCapFund.length > 0)
				{
					for (int i = 0; i < actualGrpForCapFund.length; i++) {

						actualGrpForCapFund[i].setGroupLmt( capAmt );
						actualRefID = String.valueOf ( (actualGrpForCapFund[i]).getGrpID() );

						if (actualRefID != null) {
							DefaultLogger.debug (this,"************ group id/ actualRefID"+actualRefID);

							try {
								ICMSTrxValue cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType (actualRefID, ICMSConstant.INSTANCE_CUST_GRP_IDENTIFIER);

								stagingRefID = cmsTrxValue.getStagingReferenceID();
								DefaultLogger.debug (this,"************ group id/ stagingRefID"+stagingRefID);
								stagingGrpIDList.add( stagingRefID );

							}
							catch (Exception e) {
								// do nothing here
							}
						}
					}
				}


				if( actualGrpForCapFund != null && actualGrpForCapFund.length > 0 ) {
					mgr.updateCustGrpLimitAmount ( actualGrpForCapFund );

				}
				if( !stagingGrpIDList.isEmpty() ) {
					stgMgr.updateCustGrpLimitAmount ( stagingGrpIDList, capAmt );
				}

				stagingGrpIDList = new ArrayList();
				actualRefID = null;
				stagingRefID = null;
				if (actualGrpForGP5Req != null && actualGrpForGP5Req.length > 0)
				{
					for (int i = 0; i < actualGrpForGP5Req.length; i++) {

						actualGrpForGP5Req[i].setGroupLmt( gp5Amt );
						actualRefID = String.valueOf ( (actualGrpForGP5Req[i]).getGrpID() );

						if (actualRefID != null) {
							DefaultLogger.debug (this,"************ group id/ actualRefID"+actualRefID);

							try {
								ICMSTrxValue cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType (actualRefID, ICMSConstant.INSTANCE_CUST_GRP_IDENTIFIER);

								stagingRefID = cmsTrxValue.getStagingReferenceID();
								DefaultLogger.debug (this,"************ group id/ stagingRefID"+stagingRefID);
								stagingGrpIDList.add( stagingRefID );

							}
							catch (Exception e) {
								// do nothing here
							}
						}
					}
				}


				if( actualGrpForGP5Req != null && actualGrpForGP5Req.length > 0 ) {
					mgr.updateCustGrpLimitAmount ( actualGrpForGP5Req );

				}

				if( !stagingGrpIDList.isEmpty() ) {
					stgMgr.updateCustGrpLimitAmount ( stagingGrpIDList, gp5Amt );
				}

			}

		}
		catch (CustGrpIdentifierException e) {
			throw new InternalLimitException(
			"CustGrpIdentifierException in updateGroupLimitAmount(): " + e.toString());
		}
		catch (Exception e) {
			throw new InternalLimitException(
			"Exception in updateGroupLimitAmount(): " + e.toString());
		}
	}

    protected SBCMSTrxManager getTrxManager() throws TrxOperationException
    {
        SBCMSTrxManager mgr = (SBCMSTrxManager) BeanController.getEJB(ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME, SBCMSTrxManagerHome.class.getName());
        if(null == mgr) {
            throw new TrxOperationException("SBCMSTrxManager is null!");
        }
        else {
            return mgr;
        }
    }
}
