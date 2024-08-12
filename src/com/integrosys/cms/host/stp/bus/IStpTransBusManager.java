package com.integrosys.cms.host.stp.bus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Sep 18, 2008
 * Time: 5:46:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IStpTransBusManager {
    /**
     * Method to persist Stp master transaction OB in DB
     *
     * @param masterTrans
     * @return
     */
    IStpMasterTrans createMasterTrans(IStpMasterTrans masterTrans);

    /**
     * Method to update Stp master transaction OB in DB
     *
     * @param masterTrans
     * @return
     */
    IStpMasterTrans updateMasterTrans(IStpMasterTrans masterTrans);

    /**
     * Method to get Stp master transaction OB by primary key
     *
     * @param key
     * @return
     */
    IStpMasterTrans getMasterTransByPrimaryKey(Serializable key);

    /**
     * Method to get Stp master transaction OB by transaction Id
     *
     * @param trxId
     * @return
     */
    IStpMasterTrans getMasterTransByTransactionId(String trxId);

    /**
     * Method to get a list of Stp master transaction OB filter by status
     *
     * @param statuses
     * @return
     */
    List getMasterTransByStatus(List statuses);

    /**
     * Method to persist Stp transasction history OB in DB
     *
     * @param transHistory
     * @return
     */
    IStpTrans createTransHistory(IStpTrans transHistory);

    /**
     * Method to update Stp transaction history OB in DB
     *
     * @param transHistory
     * @return
     */
    IStpTrans updateTransHistory(IStpTrans transHistory);

    /**
     * Method to get Stp transaction history OB by transaction UID
     *
     * @param trxUID
     * @return
     */
    IStpTrans getTransHistoryByTrxUID(Long trxUID);

    /**
     * Method to persist Stp transaction error OB in DB
     *
     * @param transError
     * @return
     */
    IStpTransError createTransError(IStpTransError transError);

    /**
     * Method to get Stp transaction error OB by transaction UID
     *
     * @param trxUID
     * @return
     */
    List getTransErrorByTrxUID(Long trxUID);

    /**
     * Method to update Stp transaction error OB in DB
     *
     * @param transError
     * @return
     */
    IStpTransError updateTransError(IStpTransError transError);

    List getInsPolicySequenceByInsPolicyID(Long insPolicyID);

    List getColLimitMapByID(Long chargeId);

    List getLimitChargeMapByID(Long lmtChargeId);

    List getFacilityOfficerByID(Long id);

    List getFacilityMultiTierFinanceByID(Long id);

    List getFacilityRelCifNumberByID(Long id);

    /**
     * Method to get Stp transaction UID using seq from DB
     *
     * @return Stp transaction UID
     */
    String getSeqNum();

    /**
     * Method to get list of Stp transaction yet to receive response from SIBS
     *
     * @return
     */
    List getNoResponseStpTrans();

    /**
     * Delete obsolete trans error before log transaction new error
     *
     * @param trxUID
     */
    void deleteObsoleteTransError(Long trxUID);

    /**
     * Method to get Stp transaction reference number to use in MBASE HDRNUM field
     *
     * @return trx ref number
     */
    public String getTrxRefNum();

    /**
     * get Total Amount Sold from liquitation table
     *
     * @param cmsCollateralId collateral id
     * @return total amount sold
     */
    List getTotalAmountSold(Long cmsCollateralId);

    /**
     * Method to get Stp Islamic Loan Type From HOST_PRODUCT_TYPE
     *
     * @return Islamic Loan Type
     */
    public String getStpIslamicLoanType(String productType, String facilityCode);

    /**
     * Retrieve common code category entry description irregardless the status
     *
     * @param categoryCode
     * @param entryCode
     * @return
     */
    public String getCodeCategoryEntry(String categoryCode, String entryCode);

}
