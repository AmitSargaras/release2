package com.integrosys.cms.app.creditriskparam.bus.productlimit;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISectorLimitBusManager;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISectorLimitDao;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitException;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class AbstractProductLimitBusManager implements IProductLimitBusManager {
	private IProductLimitDao productLimitDao;

    public IProductLimitDao getProductLimitDao() {
        return productLimitDao;
    }

    public void setProductLimitDao(IProductLimitDao productLimitDao) {
        this.productLimitDao = productLimitDao;
    }

    public abstract String getProductProgramLimitEntityName();

    public abstract String getProductTypeLimitEntityName();

    /*
        check version and make sure no one had update the record concurrently
        set all null status to ACTIVE
        update record
        set all null cmsRefId to ID
     */
    protected IProductProgramLimitParameter updateLimit(String entity, IProductProgramLimitParameter obj){
        try {
            getProductLimitDao().checkVersionMismatch(entity, obj, new Long(obj.getVersionTime()));
        } catch (VersionMismatchException e) {
            throw new ProductLimitException("fail to update, Version mismatch", e);
        }
        preProductLimitProcess(obj, false);

        Long key = getProductLimitDao().updateLimit(entity, obj);

        obj = postProductLimitProcess((IProductProgramLimitParameter) getProductLimitDao().findByPrimaryKey(entity, key));

        return (IProductProgramLimitParameter) getProductLimitDao().findByPrimaryKey(entity, (getProductLimitDao().updateLimit(entity, obj)));
    }

    protected IProductProgramLimitParameter createLimit(String entity, IProductProgramLimitParameter obj){
        obj.setId(null);
        if (obj.getStatus() == null) {
            obj.setStatus(ICMSConstant.STATE_ACTIVE);
        }

        obj = preProductLimitProcess(obj, true);
        obj = getProductLimitDao().createLimit(entity, obj);

        if (obj.getCmsRefId() == ICMSConstant.LONG_INVALID_VALUE) {
            obj.setCmsRefId(obj.getId().longValue());
        }
        obj = postProductLimitProcess(obj);
        
        return (IProductProgramLimitParameter) getProductLimitDao().findByPrimaryKey(entity, (getProductLimitDao().updateLimit(entity, obj)));
    }

    protected Object getLimitById(String entity, long id){
        return getProductLimitDao().findByPrimaryKey(entity, new Long(id));
    }
    protected List getAll(String entity){
        return ProductLimitUtils.convertSetToList((List) getProductLimitDao().findAll(entity), true);
    }

     /* Set all status into active iff null
        Set id to null if is new instance so hibernate will insert as a new object instead treat it as update*/

    private IProductProgramLimitParameter preProductLimitProcess(IProductProgramLimitParameter productLimit, boolean newInstance) {
        Set productTypeSet = productLimit.getProductTypeSet();

        if (productTypeSet != null && productTypeSet.size() > 0) {
            Iterator iterator = productTypeSet.iterator();
            while (iterator.hasNext()) {
                IProductTypeLimitParameter iProductTypeLimitParameter = (IProductTypeLimitParameter) iterator.next();
                if(newInstance){
                    iProductTypeLimitParameter.setId(null); //make sure it is null since it may create a record when close rejected sector limit
                }
                if(iProductTypeLimitParameter.getStatus()==null)
                    iProductTypeLimitParameter.setStatus(ICMSConstant.STATE_ACTIVE);
            }
            productLimit.setProductTypeSet(productTypeSet);
        }else if (productTypeSet != null && productTypeSet.size() == 0) {
            productLimit.setProductTypeSet(null);
        }
        return productLimit;
    }

    /*basically use for after insert an new instance to update cmsRefId to id*/

    private IProductProgramLimitParameter postProductLimitProcess(IProductProgramLimitParameter productLimit) {
        Set productTypeSet = productLimit.getProductTypeSet();

        if (productTypeSet != null && productTypeSet.size() > 0) {
            Iterator iterator = productTypeSet.iterator();
            while (iterator.hasNext()) {
                IProductTypeLimitParameter iProductTypeLimitParameter = (IProductTypeLimitParameter) iterator.next();
                if (iProductTypeLimitParameter.getCmsRefId() == ICMSConstant.LONG_INVALID_VALUE){
                    iProductTypeLimitParameter.setCmsRefId(iProductTypeLimitParameter.getId().longValue());
                }
            }
            productLimit.setProductTypeSet(productTypeSet);
        }else if (productTypeSet != null && productTypeSet.size() == 0) {
            productLimit.setProductTypeSet(null);
        }
        return productLimit;
    }
    protected Object findByPrimaryKey(String entity, Long id){
        return getProductLimitDao().findByPrimaryKey(entity, id);
    }

    protected Object findByRefCode(String entity, String refCode){
        return getProductLimitDao().findByRefCode(entity, refCode);        
    }

}