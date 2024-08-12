package com.integrosys.cms.app.creditriskparam.bus.internalcreditrating;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.custgrpi.bus.*;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 23, 2010
 * Time: 4:31:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class InternalCreditRatingBusManagerImpl extends AbstractInternalCreditRatingBusManager {

    protected static final Logger logger = LoggerFactory.getLogger(InternalCreditRatingBusManagerImpl.class);

    public String getInternalCreditRatingxEntityName() {
        return IInternalCreditRatingDao.ACTUAL_ENTITY_NAME;
    }

    public List getAllInternalCreditRating() {
        List returnList = null;

        try {
            returnList = findAll();
        }
        catch (Exception e) {
            throw new InternalCreditRatingException(e);
        }

        return returnList;
    }

    public List getInternalCreditRatingByGroupId(long groupId) {
        List returnList = null;

        try {
            returnList = findByGroupId(groupId);
        } catch (Exception e) {
            throw new InternalCreditRatingException(e);
        }

        return returnList;
    }

    public List createInternalCreditRating(List iCRList) {
        List returnList = new ArrayList();

        if (iCRList == null && iCRList.size() == 0) {
            return returnList;
        }

        try {
            long groupId = ICMSConstant.LONG_MIN_VALUE;

            for (int index = 0; index < iCRList.size(); index++) {

                IInternalCreditRating iCR = new OBInternalCreditRating((IInternalCreditRating) iCRList.get(index));
                iCR.setGroupId(groupId);
                iCR = createInternalCreditRating(iCR);

                if (iCR.getGroupId() == ICMSConstant.LONG_MIN_VALUE) {
                    iCR.setGroupId(iCR.getIntCredRatId().longValue());
                }

                if (iCR.getRefId() == ICMSConstant.LONG_INVALID_VALUE) {
                    iCR.setRefId(iCR.getIntCredRatId().longValue());
                } else {
                    iCR.setRefId(iCR.getRefId());
                }

                iCR = updateInternalCreditRating(iCR);

                groupId = iCR.getGroupId();
                returnList.add(iCR);

            }

        } catch (Exception e) {
            throw new InternalCreditRatingException(e);
        }

        return returnList;
    }

    public List updateInternalCreditRating(List iCRList) {
        List returnList = new ArrayList();
        List updateList = new ArrayList();

        if (iCRList == null && iCRList.size() == 0) {
            return returnList;
        }

        try {
            for (int index = 0; index < iCRList.size(); index++) {

                IInternalCreditRating iCR = (IInternalCreditRating) iCRList.get(index);

                if (iCR.getIntCredRatId().longValue() == ICMSConstant.LONG_INVALID_VALUE) {
                    DefaultLogger.debug(this, " Create Internal Credit Rating for: " + iCR.getIntCredRatId());
                    IInternalCreditRating updateCR = createInternalCreditRating(iCR);
                    if (updateCR.getGroupId() == ICMSConstant.LONG_MIN_VALUE) {
                        updateCR.setGroupId(updateCR.getIntCredRatId().longValue());
                    }

                    if (updateCR.getRefId() == ICMSConstant.LONG_INVALID_VALUE) {
                        updateCR.setRefId(updateCR.getIntCredRatId().longValue());
                    } else {
                        updateCR.setRefId(updateCR.getRefId());
                    }

                    updateCR = updateInternalCreditRating(updateCR);
                    returnList.add(updateCR);
                    updateList.add(updateCR);
                } else if (iCR.getStatus().equals(ICMSConstant.STATE_DELETED)) {
                    //do soft delete
                    DefaultLogger.debug(this, " Delete Internal Credit Rating for: " + iCR.getIntCredRatId());
                    IInternalCreditRating updateCR = findByPrimaryKey(iCR.getIntCredRatId().longValue());
                    updateCR.setStatus(ICMSConstant.STATE_DELETED);
                    updateCR.setIntCredRatLmtAmt(0);
                    updateCR = updateInternalCreditRating(updateCR);
                    updateList.add(updateCR);
                } else {
                    DefaultLogger.debug(this, " Update Internal Credit Rating for: " + iCR.getIntCredRatId());
                    IInternalCreditRating updateCR = findByPrimaryKey(iCR.getIntCredRatId().longValue());

                    AccessorUtil.copyValue(iCR, updateCR, new String[] {"getIntCredRatId"});
                    updateCR = updateInternalCreditRating(updateCR);

                    returnList.add(updateCR);
                    updateList.add(updateCR);
                }

            }
            //update group limit amount after checker approve the credit rating trx
            updateGroupLimitAmount(updateList);

        } catch (Exception e) {
            throw new InternalCreditRatingException(e);
        }

        return returnList;
    }

    /**
     * Changes in internal credit grade (all grades) should be reflected to groups that are using "By Internal Credit Rating" as BGEL amount
     *
     * @param creditRatingList List of IInternalCreditRating
     * @throws InternalCreditRatingException on errors encountered
     */
    private void updateGroupLimitAmount(List creditRatingList) throws InternalCreditRatingException {

        try {
            HashMap ratingMap = new HashMap();
            if (creditRatingList != null) {

                for (int i = 0; i < creditRatingList.size(); i++) {

                    IInternalCreditRating crRating = (IInternalCreditRating) creditRatingList.get(i);

                    ratingMap.put(crRating.getIntCredRatCode(),
                            new Amount(new BigDecimal(crRating.getIntCredRatLmtAmt()), new CurrencyCode(crRating.getIntCredRatLmtAmtCurCode())));
                }

                ICustGrpIdentifierBusManager mgr = CustGrpIdentifierBusManagerFactory.getActualCustGrpIdentifierBusManager();
                ICustGrpIdentifierBusManager stgMgr = CustGrpIdentifierBusManagerFactory.getStagingCustGrpIdentifierBusManager();

                ICustGrpIdentifier[] actualGrpForCreditRating = mgr.getCustGrpByInternalLimitType(CustGroupUIHelper.INT_LMT_CREDIT_RATE);

                List actualGrpIDList = new ArrayList();
                List stagingGrpIDList = new ArrayList();
                HashMap stagingGrpIDMap = new HashMap();
                String actualRefID = null;
                String stagingRefID = null;
                if (actualGrpForCreditRating != null && actualGrpForCreditRating.length > 0) {
                    for (int i = 0; i < actualGrpForCreditRating.length; i++) {

                        Amount newGrpAmt = (Amount) ratingMap.get(actualGrpForCreditRating[i].getGroupCreditGrade()[0].getRatingCD());

                        if (newGrpAmt != null) {

                            actualGrpForCreditRating[i].setGroupLmt(newGrpAmt);
                            actualRefID = String.valueOf((actualGrpForCreditRating[i]).getGrpID());
                            actualGrpIDList.add(actualGrpForCreditRating[i]);

                            if (actualRefID != null) {
                                DefaultLogger.debug(this, "************ group id/ actualRefID" + actualRefID);

                                try {
                                    ICMSTrxValue cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(actualRefID, ICMSConstant.INSTANCE_CUST_GRP_IDENTIFIER);

                                    stagingRefID = cmsTrxValue.getStagingReferenceID();
                                    DefaultLogger.debug(this, "************ group id/ stagingRefID" + stagingRefID);

                                    ICustGrpIdentifier stgCustGrp = stgMgr.getCustGrpIdentifierByGrpID(new Long(stagingRefID));
                                    stgCustGrp.setGroupLmt(newGrpAmt);
                                    stagingGrpIDMap.put(stagingRefID, stgCustGrp);

                                }
                                catch (Exception e) {
                                    // do nothing here coz the the first col CountryLimits created without trx
                                }
                            }
                        }
                    }
                }

                //retrieve working group transaction to update staging record
                ICMSTrxValue[] cmsTrxValueList = getTrxManager().getWorkingTrxListByTrxType(ICMSConstant.INSTANCE_CUST_GRP_IDENTIFIER);

                if (cmsTrxValueList != null && cmsTrxValueList.length > 0) {

                    for (int i = 0; i < cmsTrxValueList.length; i++) {

                        stagingRefID = cmsTrxValueList[i].getStagingReferenceID();

                        ICustGrpIdentifier stgCustGrp = stgMgr.getCustGrpIdentifierByGrpID(new Long(stagingRefID));

                        if (CustGroupUIHelper.INT_LMT_CREDIT_RATE.equals(stgCustGrp.getInternalLmt())) {
                            Amount newGrpAmt = (Amount) ratingMap.get(stgCustGrp.getGroupCreditGrade()[0].getRatingCD());

                            if (newGrpAmt != null) {

                                stgCustGrp.setGroupLmt(newGrpAmt);
                                stagingGrpIDMap.put(stagingRefID, stgCustGrp);
                            }
                        }

                    }//end for
                }//end if


                if (!stagingGrpIDMap.isEmpty()) {
                    Collection stagingGrpIDCol = stagingGrpIDMap.values();

                    for (Iterator iterator = stagingGrpIDCol.iterator(); iterator.hasNext();) {
                        stagingGrpIDList.add((ICustGrpIdentifier) iterator.next());
                    }
                }


                if (!actualGrpIDList.isEmpty()) {
                    mgr.updateCustGrpLimitAmount((ICustGrpIdentifier[]) actualGrpIDList.toArray(new OBCustGrpIdentifier[0]));

                }
                if (!stagingGrpIDList.isEmpty()) {
                    stgMgr.updateCustGrpLimitAmount((ICustGrpIdentifier[]) stagingGrpIDList.toArray(new OBCustGrpIdentifier[0]));
                }

            }

        }
        catch (CustGrpIdentifierException e) {
            throw new InternalCreditRatingException(
                    "CustGrpIdentifierException in updateGroupLimitAmount(): " + e.toString());
        }
        catch (Exception e) {
            throw new InternalCreditRatingException(
                    "Exception in updateGroupLimitAmount(): " + e.toString());
        }
    }

    protected SBCMSTrxManager getTrxManager() throws TrxOperationException {
        SBCMSTrxManager mgr = (SBCMSTrxManager) BeanController.getEJB(ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME, SBCMSTrxManagerHome.class.getName());
        if (null == mgr) {
            throw new TrxOperationException("SBCMSTrxManager is null!");
        } else {
            return mgr;
        }
    }

}