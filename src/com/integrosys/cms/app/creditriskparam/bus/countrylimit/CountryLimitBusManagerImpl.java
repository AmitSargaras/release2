package com.integrosys.cms.app.creditriskparam.bus.countrylimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 23, 2010
 * Time: 4:31:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class CountryLimitBusManagerImpl extends AbstractCountryLimitBusManager {

    protected static final Logger logger = LoggerFactory.getLogger(CountryLimitBusManagerImpl.class);

    public String getCountryLimitEntityName() {
        return ICountryLimitDao.ACTUAL_ENTITY_NAME;
    }

    public String getCountryRatingEntityName() {
        return ICountryRatingDao.ACTUAL_ENTITY_NAME;
    }

    public ICountryRating[] getCountryRating()
            throws CountryLimitException {
        try {
            List list = findAll_CountryRating();
            list = list == null ? new ArrayList() : list;

            Iterator i = list.iterator();

            ArrayList arrList = new ArrayList();

            while (i.hasNext()) {
                ICountryRating iCR = (ICountryRating) i.next();
                arrList.add(iCR);
            }
            return (ICountryRating[]) arrList.toArray(new OBCountryRating[0]);
        }
        catch (Exception e) {
            throw new CountryLimitException("Exception caught at getCountryRating " + e.toString());
        }
    }

    public ICountryLimit getCountryLimitByCountryCode(String countryCode)
            throws CountryLimitException {
        try {
            return findByCountryCode(countryCode);
        }
        catch (Exception e) {
            throw new CountryLimitException("Exception caught at getCountryLimitByCountryCode " + e.toString());
        }
    }

    public ICountryLimitParam getCountryLimitParamByGroupID(long groupID)
            throws CountryLimitException {
        try {
            ICountryLimitParam ctryLimitParam = new OBCountryLimitParam();

            List limitList = findByGroupId_CountryLimit(groupID);
            limitList = limitList == null ? new ArrayList() : limitList;

            Iterator i = limitList.iterator();

            ArrayList arrList = new ArrayList();

            while (i.hasNext()) {
                ICountryLimit iCL = (ICountryLimit) i.next();
                arrList.add(iCL);
            }
            ctryLimitParam.setCountryLimitList((ICountryLimit[]) arrList.toArray(new OBCountryLimit[0]));

            arrList = new ArrayList();
            List ratingList = findByGroupId_CountryRating(groupID);
            ratingList = ratingList == null ? new ArrayList() : ratingList;
            i = ratingList.iterator();
            while (i.hasNext()) {
                ICountryRating iCR = (ICountryRating) i.next();
                arrList.add(iCR);
            }

            ctryLimitParam.setCountryRatingList((ICountryRating[]) arrList.toArray(new OBCountryRating[0]));
            ctryLimitParam.setGroupID(groupID);
            return ctryLimitParam;
        }
        catch (Exception e) {
            throw new CountryLimitException("Exception caught at getCountryLimitParamByGroupID " + e.toString());
        }
    }

    public ICountryLimitParam createCountryLimitParam(ICountryLimitParam value)
            throws CountryLimitException {
        if (value == null)
            throw new CountryLimitException("ICountryLimitParam is null");

        ICountryLimit[] ctryLimitList = value.getCountryLimitList();
        ICountryRating[] ctryRatingList = value.getCountryRatingList();
        long groupID = ICMSConstant.LONG_MIN_VALUE;
        try {
            if (ctryLimitList != null && ctryLimitList.length > 0) {

                ArrayList arrList = new ArrayList();

                for (int i = 0; i < ctryLimitList.length; i++) {
                    ICountryLimit countryLimit = new OBCountryLimit(ctryLimitList[i]);

                    countryLimit.setGroupID(groupID);

                    countryLimit = createCountryLimit_CountryLimit(countryLimit);

                    if (countryLimit.getRefID() == ICMSConstant.LONG_INVALID_VALUE) {
                        countryLimit.setRefID(countryLimit.getCountryLimitID());
                    } else {
                        // else maintain this reference id.
                        countryLimit.setRefID(countryLimit.getRefID());
                    }

                    if (countryLimit.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
                        countryLimit.setGroupID(countryLimit.getCountryLimitID());
                    }

                    countryLimit = updateCountryLimit_CountryLimit(countryLimit);

                    groupID = countryLimit.getGroupID();

                    arrList.add(countryLimit);
                }
                value.setCountryLimitList((ICountryLimit[]) arrList.toArray(new OBCountryLimit[0]));
            }

            if (ctryRatingList != null && ctryRatingList.length > 0) {

                ArrayList arrList = new ArrayList();

                for (int i = 0; i < ctryRatingList.length; i++) {
                    ICountryRating countryRating = new OBCountryRating(ctryRatingList[i]);

                    countryRating.setGroupID(groupID);

                    countryRating = createCountryLimit_CountryRating(countryRating);

                    if (countryRating.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
                        countryRating.setGroupID(countryRating.getCountryRatingID());
                    }

                    countryRating = updateCountryLimit_CountryRating(countryRating);

                    groupID = countryRating.getGroupID();

                    arrList.add(countryRating);
                }

                value.setCountryRatingList((ICountryRating[]) arrList.toArray(new OBCountryRating[0]));
            }

            value.setGroupID(groupID);

            return value;
        }
        catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new CountryLimitException("RemoteException caught! " + e.toString());
        }
    }

    public ICountryLimit[] updateCountryLimitAmount(ICountryLimit[] value)
            throws CountryLimitException {
        try {

            ArrayList arrList = new ArrayList();

            for (int i = 0; i < value.length; i++) {
                ICountryLimit countryLimit = value[i];

                //DefaultLogger.debug (this, " processing countryLimit: " + countryLimit);

                countryLimit = findByPrimaryKey_CountryLimit(value[i].getCountryLimitID());

                countryLimit.setCountryLimitAmount(value[i].getCountryLimitAmount());
                countryLimit = updateCountryLimit_CountryLimit(countryLimit);

                arrList.add(countryLimit);

            }

            return (ICountryLimit[]) arrList.toArray(new OBCountryLimit[0]);

        }
        catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new CountryLimitException("RemoteException caught! " + e.toString());
        }
    }

    public ICountryLimitParam updateCountryLimitParam(ICountryLimitParam value)
            throws CountryLimitException {
        if (value == null)
            throw new CountryLimitException("ICountryLimitParam is null");

        ICountryLimit[] ctryLimitList = value.getCountryLimitList();
        ICountryRating[] ctryRatingList = value.getCountryRatingList();
        long groupID = ICMSConstant.LONG_MIN_VALUE;

        try {
            if (ctryLimitList != null && ctryLimitList.length > 0) {
                value.setCountryLimitList(updateCountryLimit(ctryLimitList));
            }

            if (ctryRatingList != null && ctryRatingList.length > 0) {
                ICountryRating[] newCtryRatingList = updateCountryRating(ctryRatingList);
                value.setCountryRatingList(newCtryRatingList);
                groupID = newCtryRatingList[0].getGroupID();
            }

            value.setGroupID(groupID);
            return value;
        }
        catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new CountryLimitException("RemoteException caught! " + e.toString());
        }
    }

    private ICountryLimit[] updateCountryLimit(ICountryLimit[] value)
            throws CountryLimitException {
        try {

            ArrayList arrList = new ArrayList();

            for (int i = 0; i < value.length; i++) {
                ICountryLimit countryLimit = value[i];

                DefaultLogger.debug(this, " processing countryLimit: " + countryLimit);

                if (ICMSConstant.LONG_INVALID_VALUE == countryLimit.getCountryLimitID()) {
                    DefaultLogger.debug(this, " Create Country Limit for: " + countryLimit.getCountryLimitID());

                    ICountryLimit iCL = createCountryLimit_CountryLimit(countryLimit);

                    if (iCL.getRefID() == ICMSConstant.LONG_INVALID_VALUE) {
                        iCL.setRefID(iCL.getCountryLimitID());
                    } else {
                        // else maintain this reference id.
                        iCL.setRefID(iCL.getRefID());
                    }

                    if (iCL.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
                        iCL.setGroupID(iCL.getCountryLimitID());
                    }

                    iCL = updateCountryLimit_CountryLimit(iCL);

                    arrList.add(iCL);
                } else if (countryLimit.getStatus().equals(ICMSConstant.STATE_DELETED)) {
                    DefaultLogger.debug(this, " Delete Country Limit for: " + value[i].getCountryLimitID());
                    ICountryLimit iCL = findByPrimaryKey_CountryLimit(countryLimit.getCountryLimitID());
                    iCL.setStatus(ICMSConstant.STATE_DELETED);
                    iCL = updateCountryLimit_CountryLimit(iCL);
                } else {
                    DefaultLogger.debug(this, " Update Country Limit for: " + value[i].getCountryLimitID());
                    ICountryLimit iCL = findByPrimaryKey_CountryLimit(countryLimit.getCountryLimitID());
                    AccessorUtil.copyValue(countryLimit, iCL, new String[]{"getCountryLimitID", "getRefID"});
                    iCL = updateCountryLimit_CountryLimit(iCL);
                }
            }

            return (ICountryLimit[]) arrList.toArray(new OBCountryLimit[0]);

        }
        catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new CountryLimitException("RemoteException caught! " + e.toString());
        }
    }

    private ICountryRating[] updateCountryRating(ICountryRating[] value)
            throws CountryLimitException {
        try {

            ArrayList arrList = new ArrayList();

            for (int i = 0; i < value.length; i++) {
                ICountryRating countryRating = value[i];

                DefaultLogger.debug(this, " processing countryRating: " + countryRating);

                if (ICMSConstant.LONG_INVALID_VALUE == countryRating.getCountryRatingID()) {
                    DefaultLogger.debug(this, " Create Country Rating for: " + countryRating.getCountryRatingID());

                    ICountryRating iCR = createCountryLimit_CountryRating(countryRating);

                    if (iCR.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
                        iCR.setGroupID(iCR.getCountryRatingID());
                    }

                    iCR = updateCountryLimit_CountryRating(iCR);

                    arrList.add(iCR);
                } else {
                    DefaultLogger.debug(this, " Update Country Rating for: " + value[i].getCountryRatingID());
                    ICountryRating iCR = findByPrimaryKey_CountryRating(countryRating.getCountryRatingID());
                    AccessorUtil.copyValue(countryRating, iCR, new String[]{"getCountryRatingID"});
                    iCR = updateCountryLimit_CountryRating(iCR);

                    arrList.add(iCR);
                }
            }

            return (ICountryRating[]) arrList.toArray(new OBCountryRating[0]);
        }
        catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new CountryLimitException("RemoteException caught! " + e.toString());
        }
    }

}