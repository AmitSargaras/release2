package com.integrosys.cms.ui.creditriskparam.sectorlimit;

import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IEcoSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter;
import com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing;

import java.io.Serializable;
import java.util.Comparator;

/**
 * <p>
 * Comparator to use to compare items of
 * <tt>OBMainSectorLimitParameter, OBSubSectorLimitParameter, OBEconSectorLimitParameter by loan purpose code</tt>.
 * <p>
 * </ol>
 * @author Chong Jun Yong
 * Author: KC Chin
 *
 */
public class SectorLimitComparator implements Comparator, Serializable {

	private static final long serialVersionUID = -893534730276632900L;

    public int compare(Object o1, Object o2) {
        if(o1.equals(o2)){
            return 0;
        }else if(o1 instanceof IMainSectorLimitParameter && o2 instanceof IMainSectorLimitParameter){
            IMainSectorLimitParameter s1 = (IMainSectorLimitParameter) o1;
            IMainSectorLimitParameter s2 = (IMainSectorLimitParameter) o2;
            return s1.getLoanPurposeCode().compareTo(s2.getLoanPurposeCode());
        }else if(o1 instanceof ISubSectorLimitParameter && o2 instanceof ISubSectorLimitParameter){
            ISubSectorLimitParameter s1 = (ISubSectorLimitParameter) o1;
            ISubSectorLimitParameter s2 = (ISubSectorLimitParameter) o2;
            return s1.getLoanPurposeCode().compareTo(s2.getLoanPurposeCode());
        }else{
            IEcoSectorLimitParameter s1 = (IEcoSectorLimitParameter) o1;
            IEcoSectorLimitParameter s2 = (IEcoSectorLimitParameter) o2;
            return s1.getLoanPurposeCode().compareTo(s2.getLoanPurposeCode());
        }
    }
}