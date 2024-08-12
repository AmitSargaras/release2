package com.integrosys.cms.host.stp.bus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Sep 18, 2008
 * Time: 4:43:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractStpTransBusManager implements IStpTransBusManager {
    private IStpTransDao stpTransDao;
    private IStpTransJdbc stpTransJdbc;

    public IStpTransDao getStpTransDao() {
        return stpTransDao;
    }

    public void setStpTransDao(IStpTransDao stpTransDao) {
        this.stpTransDao = stpTransDao;
    }

    public IStpTransJdbc getStpTransJdbc() {
        return stpTransJdbc;
    }

    public void setStpTransJdbc(IStpTransJdbc stpTransJdbc) {
        this.stpTransJdbc = stpTransJdbc;
    }

    public IStpMasterTrans createMasterTrans(IStpMasterTrans masterTrans) {
        return stpTransDao.createMasterTrans(masterTrans);
    }

    public IStpMasterTrans updateMasterTrans(IStpMasterTrans masterTrans) {
        return stpTransDao.updateMasterTrans(masterTrans);
    }

    public IStpMasterTrans getMasterTransByPrimaryKey(Serializable key) {
        return stpTransDao.getMasterTransByPrimaryKey(key);
    }

    public IStpMasterTrans getMasterTransByTransactionId(String trxId) {
        return stpTransDao.getMasterTransByTransactionId(trxId);
    }

    public List getMasterTransByStatus(List statuses) {
        return stpTransDao.getMasterTransByStatus(statuses);
    }

    public IStpTrans createTransHistory(IStpTrans transHistory) {
        return stpTransDao.createTransHistory(transHistory);
    }

    public IStpTrans updateTransHistory(IStpTrans transHistory) {
        return stpTransDao.updateTransHistory(transHistory);
    }

    public IStpTrans getTransHistoryByTrxUID(Long trxUID) {
        return stpTransDao.getTransHistoryByTrxUID(trxUID);
    }

    public IStpTransError createTransError(IStpTransError transError) {
        return stpTransDao.createTransError(transError);
    }

    public List getTransErrorByTrxUID(Long trxUID) {
        return stpTransDao.getTransErrorByTrxUID(trxUID);
    }

    public IStpTransError updateTransError(IStpTransError transError) {
        return stpTransDao.updateTransError(transError);
    }

    public List getInsPolicySequenceByInsPolicyID(Long insPolicyID) {
        return stpTransJdbc.getInsPolicySequenceByInsPolicyID(insPolicyID);
    }

    public List getColLimitMapByID(Long chargeId) {
        return stpTransJdbc.getColLimitMapByID(chargeId);
    }

    public List getLimitChargeMapByID(Long lmtChargeId) {
        return stpTransJdbc.getLimitChargeMapByID(lmtChargeId);
    }

    public List getFacilityOfficerByID(Long id) {
        return stpTransJdbc.getFacilityOfficerByID(id);
    }

    public List getFacilityMultiTierFinanceByID(Long id) {
        return stpTransJdbc.getFacilityMultiTierFinanceByID(id);
    }

    public List getFacilityRelCifNumberByID(Long id) {
        return stpTransJdbc.getFacilityRelCifNumberByID(id);
    }

    public String getSeqNum() {
        return stpTransJdbc.getSeqNum();
    }

    public List getNoResponseStpTrans() {
        return stpTransDao.getNoResponseStpTrans();
    }

    public void deleteObsoleteTransError(Long trxUID) {
        stpTransDao.deleteObsoleteTransError(trxUID);
    }

    public String getTrxRefNum() {
        return stpTransJdbc.getTrxRefNum();
    }

    public List getTotalAmountSold(Long cmsCollateralId) {
        return stpTransJdbc.getTotalAmountSold(cmsCollateralId);
    }

    public String getStpIslamicLoanType(String productType, String facilityCode) {
        return stpTransJdbc.getStpIslamicLoanType(productType, facilityCode);
    }

    public String getCodeCategoryEntry(String categoryCode, String entryCode) {
        return stpTransJdbc.getCodeCategoryEntry(categoryCode, entryCode);
    }

}
