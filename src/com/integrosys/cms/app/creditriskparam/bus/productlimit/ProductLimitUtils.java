package com.integrosys.cms.app.creditriskparam.bus.productlimit;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IEcoSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * @author KC Chin
 * @since Aug 10 2010
 */
public abstract class ProductLimitUtils {
    protected static final Logger logger = LoggerFactory.getLogger(ProductLimitUtils.class);

    public static IProductProgramLimitParameter convertSetToList(IProductProgramLimitParameter productProgram, boolean filterDelete) {
        List productTypeList;
        productTypeList = new ArrayList();
        if(productProgram.getProductTypeSet() != null){
            for(Iterator s = productProgram.getProductTypeSet().iterator(); s.hasNext();){
                IProductTypeLimitParameter progType = (IProductTypeLimitParameter) s.next();
                if(filterDelete && progType.getStatus() != null && progType.getStatus().equals(ICMSConstant.STATE_DELETED)){
                    continue;
                }
                productTypeList.add(progType);
            }
        }
        Collections.sort(productTypeList, new AlphabeticComparator());
        productProgram.setProductTypeList(productTypeList);
        return productProgram;
    }

    public static Object convertSetToList(Object obj, boolean filterDelete) {
        if(obj instanceof IProductProgramLimitParameter)
            return  convertSetToList((IProductProgramLimitParameter) obj, filterDelete);
        else
            return obj; //no convertion
    }

    public static List convertSetToList(List instance, boolean filterDelete) {
        List mainList = new ArrayList();
        if(instance != null && instance.size() > 0){
            for(Iterator m = instance.iterator(); m.hasNext();){
                Object obj = convertSetToList(m.next(), filterDelete);
                mainList.add(obj);
            }
        }
        return mainList;
    }

    /*
        compare old set and new set, any object not appear in new set should be mark as status delete in old set
     */
    public static Set setProductTypeParameterDeleteStatus(Set oldSet, Set newSet){
        boolean found;
        if(oldSet != null && oldSet.size() > 0){
            for(Iterator b = oldSet.iterator(); b.hasNext();){
                IProductTypeLimitParameter oldProductType = (IProductTypeLimitParameter) b.next();
                found = false;
                if(oldProductType != null && oldProductType.getStatus() != null && oldProductType.getStatus() != ICMSConstant.STATE_DELETED){
                    if(newSet != null){
                        for(Iterator r = newSet.iterator(); r.hasNext();){
                            IProductTypeLimitParameter replicateSubSector = (IProductTypeLimitParameter) r.next();
                            if(replicateSubSector != null && replicateSubSector.getCmsRefId() == oldProductType.getCmsRefId()){
                                found = true;
                                break;
                            }
                        }
                    }
                    if(!found){
                        oldProductType.setStatus(ICMSConstant.STATE_DELETED);
                    }
                }
            }
        }
        return oldSet;
    }

    /*
        to set all product type status into defined status
     */
    public static IProductProgramLimitParameter setAllProductTypeStatus(IProductProgramLimitParameter iproductProgram, String status) {
        Set productTypeSet = iproductProgram.getProductTypeSet();

        if (productTypeSet != null && productTypeSet.size() > 0) {
            Iterator iterator = productTypeSet.iterator();
            while (iterator.hasNext()) {
                IProductTypeLimitParameter productTypeLimitParameter = (IProductTypeLimitParameter) iterator.next();
                productTypeLimitParameter.setStatus(status);
            }
            iproductProgram.setProductTypeSet(productTypeSet);
        }else if (productTypeSet != null && productTypeSet.size() == 0) {
            iproductProgram.setProductTypeSet(null);
        }
        return iproductProgram;
    }
    
}
class AlphabeticComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        IProductTypeLimitParameter s1 = (IProductTypeLimitParameter) o1;
        IProductTypeLimitParameter s2 = (IProductTypeLimitParameter) o2;
        return s1.getProductTypeDesc().compareTo(s2.getProductTypeDesc());
    }
}