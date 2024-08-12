package com.integrosys.cms.app.creditriskparam.bus.productlimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.*;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ProductLimitBusManagerImpl extends AbstractProductLimitBusManager  {

	public String getProductProgramLimitEntityName() {
		return IProductLimitDao.PRODUCT_PROGRAM_LIMIT_PARAM;
	}

    public String getProductTypeLimitEntityName() {
		return IProductLimitDao.PRODUCT_TYPE_LIMIT_PARAM;
	}

    public List getAll() {
        return getAll(getProductProgramLimitEntityName());
    }

    public List getAllChild() {
        return getAll(getProductTypeLimitEntityName());
    }

    public Object getLimitById(long id) {
        return  ProductLimitUtils.convertSetToList(getLimitById(getProductProgramLimitEntityName(), id), true);
    }

    public IProductProgramLimitParameter createLimit(IProductProgramLimitParameter obj) {
        Set updateProductTypeSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				obj.getProductTypeSet(), new String[] { "id" });

        IProductProgramLimitParameter newProgramLimit = (IProductProgramLimitParameter)
                ReplicateUtils.replicateObject(obj, new String[] { "id" });

        newProgramLimit.setProductTypeSet(updateProductTypeSet);

        return createLimit(getProductProgramLimitEntityName(), newProgramLimit);
    }

    /*
        if status in image is delete, set working copy and all its child to delete status
        else perform normal update base on image copy by
            step 1, replicate image product type to make sure did not change its association
            step 2, set pointer to working copy product type set
            step 3, copy attribute from image to working copy
            step 4, set product type set status to delete if the item not exist in replicated set
            step 5, copy attribute from image to working copy for product type set
            step 6, filter mergedProductTypeSet by take out any existing productTypeSet inside, so left over those new added productTypeSet
            step 7, add new productTypeSet to workingCopy
            Step 8, update to actual table
     */
    public IProductProgramLimitParameter updateToWorkingCopy(IProductProgramLimitParameter workingCopy, IProductProgramLimitParameter imageCopy)
			throws ProductLimitException {

		if (ICMSConstant.STATE_DELETED.equals(imageCopy.getStatus())) {
            workingCopy.setStatus(ICMSConstant.STATE_DELETED);
            workingCopy = ProductLimitUtils.setAllProductTypeStatus(workingCopy, ICMSConstant.STATE_DELETED);
        }else{
             Set replicatedProductTypeSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(imageCopy.getProductTypeSet(),
                new String[] {"productProgramId", "id" });

             Set workingCopyProductTypeSet = workingCopy.getProductTypeSet();

             AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "id", "cmsRefId", "versionTime", "productTypeSet" });

             workingCopy.setProductTypeSet(ProductLimitUtils.setProductTypeParameterDeleteStatus(workingCopyProductTypeSet, replicatedProductTypeSet));

		     Set mergedProductTypeSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties( workingCopy.getProductTypeSet(), replicatedProductTypeSet,
                new String[] { "cmsRefId" }, new String[] {"productProgramId", "id"});

             Set duplicateProductTypeSet = (Set) EntityAssociationUtils.retrieveRemovedObjectsCollection(workingCopy.getProductTypeSet(), mergedProductTypeSet, new String[] { "cmsRefId"});

             if(duplicateProductTypeSet != null && duplicateProductTypeSet.size() > 0)
                mergedProductTypeSet.removeAll(duplicateProductTypeSet);

             if (mergedProductTypeSet != null && mergedProductTypeSet.size() > 0) {
                workingCopy.getProductTypeSet().addAll(mergedProductTypeSet);
             }
        }
        
        return updateLimit(getProductProgramLimitEntityName(), workingCopy);
	}

    public IProductProgramLimitParameter findByPrimaryKey(IProductProgramLimitParameter obj) {
        if(obj != null){
            return (IProductProgramLimitParameter) findByPrimaryKey(getProductProgramLimitEntityName(), obj.getId());
        }else
            return null;
    }

    public IProductTypeLimitParameter getProductTypeLimitParameterByRefCode(String refCode) {
        return (IProductTypeLimitParameter) findByRefCode(getProductTypeLimitEntityName(), refCode);
    }

    public IProductProgramLimitParameter getProductProgramLimitParameterByRefCode(String refCode) {
        return (IProductProgramLimitParameter) findByRefCode(getProductProgramLimitEntityName(), refCode);
    }
}