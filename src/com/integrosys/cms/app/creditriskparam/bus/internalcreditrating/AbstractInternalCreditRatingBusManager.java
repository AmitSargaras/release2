/**
 *
 */
package com.integrosys.cms.app.creditriskparam.bus.internalcreditrating;

import java.util.List;

/**
 * @author priya
 */
public abstract class AbstractInternalCreditRatingBusManager implements IInternalCreditRatingBusManager {

    private IInternalCreditRatingDao internalCreditRatingDao;

    public IInternalCreditRatingDao getInternalCreditRatingDao() {
        return internalCreditRatingDao;
    }

    public void setInternalCreditRatingDao(IInternalCreditRatingDao internalCreditRatingDao) {
        this.internalCreditRatingDao = internalCreditRatingDao;
    }

    public List findAll() {
        return getInternalCreditRatingDao().findAll(getInternalCreditRatingxEntityName());
    }

    public List findByGroupId(long groupId) {
        return getInternalCreditRatingDao().findByGroupId(getInternalCreditRatingxEntityName(), groupId);
    }

    public IInternalCreditRating findByPrimaryKey(long key) {
        return getInternalCreditRatingDao().findByPrimaryKey(getInternalCreditRatingxEntityName(), key);
    }

    public IInternalCreditRating createInternalCreditRating(IInternalCreditRating iCR) {
        return getInternalCreditRatingDao().createInternalCreditRating(getInternalCreditRatingxEntityName(), iCR);
    }

    public IInternalCreditRating updateInternalCreditRating(IInternalCreditRating iCR) {
        return getInternalCreditRatingDao().updateInternalCreditRating(getInternalCreditRatingxEntityName(), iCR);
    }

    public abstract String getInternalCreditRatingxEntityName();

}
