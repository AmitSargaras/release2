package com.integrosys.cms.host.stp.trade.bus;

import java.io.Serializable;
import java.util.List;

/**
 * @author Andy Wong
 * @author Chin Kok Cheong
 *
 */

public class AbstractTradeTransBusManager implements ITradeTransBusManager {
    private ITradeTransDao tradeTransDao;
    private ITradeTransJdbc tradeTransJdbc;

    public ITradeTransDao getTradeTransDao() {
        return tradeTransDao;
    }

    public void setTradeTransDao(ITradeTransDao tradeTransDao) {
        this.tradeTransDao = tradeTransDao;
    }

    public ITradeTransJdbc getTradeTransJdbc() {
        return tradeTransJdbc;
    }

    public void setTradeTransJdbc(ITradeTransJdbc tradeTransJdbc) {
        this.tradeTransJdbc = tradeTransJdbc;
    }

    public ITradeMasterTrans createMasterTrans(ITradeMasterTrans masterTrans) {
        return tradeTransDao.createMasterTrans(masterTrans);
    }

    public ITradeMasterTrans updateMasterTrans(ITradeMasterTrans masterTrans) {
        return tradeTransDao.updateMasterTrans(masterTrans);
    }

    public ITradeMasterTrans getMasterTransByPrimaryKey(Serializable key) {
        return tradeTransDao.getMasterTransByPrimaryKey(key);
    }

    public ITradeMasterTrans getMasterTransByTransactionId(String trxId) {
        return tradeTransDao.getMasterTransByTransactionId(trxId);
    }

    public List getMasterTransByStatus(List statuses) {
        return tradeTransDao.getMasterTransByStatus(statuses);

    }

    public ITradeTrans createTransHistory(ITradeTrans transHistory) {
        return tradeTransDao.createTransHistory(transHistory);
    }

    public ITradeTrans updateTransHistory(ITradeTrans transHistory) {
        return tradeTransDao.updateTransHistory(transHistory);
    }

    public ITradeTrans getTransHistoryByTrxUID(Long trxUID) {
        return tradeTransDao.getTransHistoryByTrxUID(trxUID);
    }

    public ITradeTransError createTransError(ITradeTransError transError) {
        return tradeTransDao.createTransError(transError);
    }

    public List getTransErrorByTrxUID(Long trxUID) {
        return tradeTransDao.getTransErrorByTrxUID(trxUID);
    }

    public ITradeTransError updateTransError(ITradeTransError transError) {
        return tradeTransDao.updateTransError(transError);
    }

    public String getSeqNum() {
        return tradeTransJdbc.getSeqNum();
    }

    public List getNoResponseTradeTrans() {
        return tradeTransDao.getNoResponseTradeTrans();
    }

    public void deleteObsoleteTransError(Long trxUID) {
        tradeTransDao.deleteObsoleteTransError(trxUID);
    }

    public String getTrxRefNum() {
        return tradeTransJdbc.getTrxRefNum();
    }

    public String getCodeCategoryEntry(String categoryCode, String entryCode) {
        return tradeTransJdbc.getCodeCategoryEntry(categoryCode, entryCode);
    }

}