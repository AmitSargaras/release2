package com.integrosys.cms.app.creditriskparam.bus.internalcreditrating;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 23, 2010
 * Time: 1:51:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IInternalCreditRatingDao {

    public final static String ACTUAL_ENTITY_NAME = "actualInternalCreditRating";
    public final static String STAGING_ENTITY_NAME = "stageInternalCreditRating";

    public List findAll(String entityName);

    public List findByGroupId(String entityName, long groupId);

    public IInternalCreditRating findByPrimaryKey(String entityName, long key);

    public IInternalCreditRating createInternalCreditRating(String entityName, IInternalCreditRating iCR);

    public IInternalCreditRating updateInternalCreditRating(String entityName, IInternalCreditRating iCR);

}