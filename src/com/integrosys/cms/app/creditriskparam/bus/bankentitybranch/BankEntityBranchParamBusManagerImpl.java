package com.integrosys.cms.app.creditriskparam.bus.bankentitybranch;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 23, 2010
 * Time: 4:31:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankEntityBranchParamBusManagerImpl extends AbstractBankEntityBranchParamBusManager {

    protected static final Logger logger = LoggerFactory.getLogger(BankEntityBranchParamBusManagerImpl.class);

    public String getBankEntityBranchParamEntityName() {
        return IBankEntityBranchParamDao.ACTUAL_ENTITY_NAME;
    }

    /**
     * Get a list of bank entity branch mapping param.
     */
    public Collection getBankEntityBranchParam()
            throws BankEntityBranchParamException {
        try {

            Collection collection = findAll();
            ArrayList ret = new ArrayList();

            if (collection != null) {
                for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
                    IBankEntityBranchParam entry = (IBankEntityBranchParam) iterator.next();
                    ret.add(entry);
                }
            }

            return ret;
        }
        catch (Exception e) {
            throw new BankEntityBranchParamException("Exception caught at getBankEntityBranchParam: " + e.toString());
        }
    }

    /**
     * Get a list of bank entity branch mapping param by its group id.
     *
     * @param groupID group id
     */
    public Collection getBankEntityBranchParamByGroupID(long groupID)
            throws BankEntityBranchParamException {
        try {
            Collection collection = findByGroupID(groupID);
            ArrayList ret = new ArrayList();

            if (collection != null) {
                for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
                    IBankEntityBranchParam entry = (IBankEntityBranchParam) iterator.next();
                    ret.add(entry);
                }
            }

            return ret;
        }
        catch (Exception e) {
            throw new BankEntityBranchParamException("Exception caught at getBankEntityBranchParamByGroupID " + e.toString());
        }
    }

    /**
     * Updates the input list of bank entity branch mapping.
     */
    public Collection updateBankEntityBranchParam(Collection entityBranch)
            throws BankEntityBranchParamException {

        try {
            Collection collection = findAll();

//            Iterator existI = findAll().iterator();
            Iterator existI = collection == null ? new ArrayList().iterator() : collection.iterator();
            Collection missingList = new ArrayList();
            Collection existList = new ArrayList();
            Collection arrList = new ArrayList();

            while (existI.hasNext()) {
                IBankEntityBranchParam existOB = (IBankEntityBranchParam) existI.next();

                boolean found = false;

                Iterator amendI = entityBranch.iterator();
                while (amendI.hasNext()) {
                    IBankEntityBranchParam amendStageOB = (IBankEntityBranchParam) amendI.next();

                    if (existOB.getCmsRefId() == amendStageOB.getCmsRefId()) {
                        //only set those maintainable fields into actual
                        AccessorUtil.copyValue(amendStageOB, existOB, new String[] {"getBankEntityId", "getEntityTypeDesc", "getBranchCodeSrc", "getStatus", "getVersionTime", "getCmsRefId"});

                        existOB = updateBankEntityBranchParam(existOB);

                        //add the existing OB into array to determine new OB creation
                        existList.add(amendStageOB);
                        arrList.add(existOB);

                        found = true;
                        break;
                    }
                }
                if (!found) {
                    missingList.add(existOB);
                }
            }

            //remove deleted items
            for (Iterator i = missingList.iterator(); i.hasNext();) {
                IBankEntityBranchParam iBEBP = (IBankEntityBranchParam) i.next();
                iBEBP.setStatus(ICMSConstant.STATE_DELETED);
                iBEBP = updateBankEntityBranchParam(iBEBP);
            }

            //remove the existing OB from the array, then create new bean on remainder
            entityBranch.removeAll(existList);
            for (Iterator j = entityBranch.iterator(); j.hasNext();) {
                IBankEntityBranchParam iBEBP = (OBBankEntityBranchParam) j.next();
                iBEBP = createBankEntityBranchParam(iBEBP);

                if (iBEBP.getGroupId().equals(new Long(ICMSConstant.LONG_MIN_VALUE))) {
                    iBEBP.setGroupId(iBEBP.getBankEntityId());
                } else {
                    iBEBP.setGroupId(iBEBP.getGroupId());
                }

                if (iBEBP.getCmsRefId() == ICMSConstant.LONG_INVALID_VALUE) {
                    iBEBP.setCmsRefId(iBEBP.getBankEntityId().longValue());
                } else {
                    iBEBP.setCmsRefId(iBEBP.getCmsRefId());
                }

                iBEBP = updateBankEntityBranchParam(iBEBP);

                arrList.add(iBEBP);
            }

            return arrList;
        } catch (Exception e) {
            throw new BankEntityBranchParamException("FinderException caught at updateBankEntityBranchParam " + e.toString());
        }

    }

    /**
     * Creates a list of bank entity branch param.
     */
    public Collection createBankEntityBranchParam(Collection entityBranch)
            throws BankEntityBranchParamException {
        if (entityBranch == null || entityBranch.size() == 0)
            throw new BankEntityBranchParamException("IBankEntityBranchParam collection is null");

        try {
            ArrayList arrList = new ArrayList();
            Long groupID = new Long(ICMSConstant.LONG_MIN_VALUE);

            Iterator iterator = entityBranch.iterator();
            while (iterator.hasNext()) {
                IBankEntityBranchParam iBEBP = (OBBankEntityBranchParam) iterator.next();
                iBEBP.setGroupId(groupID);
                iBEBP = createBankEntityBranchParam(iBEBP);

                if (iBEBP.getGroupId().equals(new Long(ICMSConstant.LONG_MIN_VALUE))) {
                    iBEBP.setGroupId(iBEBP.getBankEntityId());
                } else {
                    iBEBP.setGroupId(iBEBP.getGroupId());
                }

                if (iBEBP.getCmsRefId() == ICMSConstant.LONG_INVALID_VALUE) {
                    iBEBP.setCmsRefId(iBEBP.getBankEntityId().longValue());
                } else {
                    iBEBP.setCmsRefId(iBEBP.getCmsRefId());
                }

                iBEBP = updateBankEntityBranchParam(iBEBP);

                groupID = iBEBP.getGroupId();

                arrList.add(iBEBP);
            }

            return arrList;
        }
        catch (Exception e) {
            throw new BankEntityBranchParamException("RemoteException caught! " + e.toString());
        }
    }

}