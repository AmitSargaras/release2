package com.integrosys.cms.host.stp.bus;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 17, 2008
 * Time: 12:21:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IStpTransJdbc {
    /**
     * Retrieve Insurance Policy Sequence by insurance policy primery key
     *
     * @param insPolicyID insurance policy primery key
     * @return List
     *
     */
    public List getInsPolicySequenceByInsPolicyID(Long insPolicyID);

    /**
     * Retrieve List by charge id
     *
     * @param chargeId CMS_LIMIT_SECURITY_MAP primery key
     * @return List
     *
     */
    public List getColLimitMapByID(Long chargeId);

    /**
     * Retrieve List by limit charge id
     *
     * @param lmtChargeId CMS_LIMIT_CHARGE_MAP primery key
     * @return List
     *
     */
    public List getLimitChargeMapByID(Long lmtChargeId);

    /**
     * Retrieve officer sequence number by id
     *
     * @param id CMS_FAC_INSURANCE primery key
     * @return List
     *
     */
    public List getFacilityOfficerByID(Long id);

    /**
     * Retrieve multi tier finance sequence number by id
     *
     * @param id CMS_FAC_MULTI_TIER_FINANCING primery key
     * @return List
     *
     */
    public List getFacilityMultiTierFinanceByID(Long id);

    /**
     * Retrieve relationship cif number by id
     *
     * @param id CMS_FAC_INSURANCE primery key
     * @return List
     *
     */
    public List getFacilityRelCifNumberByID(Long id);

    /**
     * Method to get Stp transaction UID using seq from DB
     * @return Stp transaction UID
     */
    public String getSeqNum();

    /**
     * Method to get Stp transaction reference number to use in MBASE HDRNUM field
     * @return trx ref number
     */
    public String getTrxRefNum();

    /**
     * get Total Amount Sold from liquitation table
     *
     * @param cmsCollateralId collateral id
     * @return total amount sold
     */
    public List getTotalAmountSold(Long cmsCollateralId);

    /**
     * get Stp Biz Error Code From STP_COMMON_ERROR_CODE
     *
     * @return biz error code
     */
     public Map getStpBizErrorCode();

    /**
     * get Stp Error Message From STP_COMMON_ERROR_CODE
     *
     * @return biz error code
     */
     public List getErrorMessage(String transactionId);

    /**
     * get Stp Islamic Loan Type From HOST_PRODUCT_TYPE
     * @param productType
     * @return Islamic Loan Type
     */
    public String getStpIslamicLoanType(String productType, String facilityCode);

    /**
     * Retrieve common code category entry description irregardless the status
     * @param categoryCode
     * @param entryCode
     * @return
     */
    public String getCodeCategoryEntry(String categoryCode, String entryCode);
}
