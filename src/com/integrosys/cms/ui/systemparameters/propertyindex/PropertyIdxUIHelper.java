package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.*;
import com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue;
import com.integrosys.cms.ui.common.SecuritySubTypeList;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import org.apache.struts.util.LabelValueBean;

import java.util.*;

/**
 * @author Andy Wong
 * @since 16 Sep 2008
 */
public class PropertyIdxUIHelper {

    //Property Index in sequential
    protected static List propertyIndexValType = new ArrayList();

    static {
        propertyIndexValType.add(ICMSConstant.PROP_VAL_DESCR_ISTP);
        propertyIndexValType.add(ICMSConstant.PROP_VAL_DESCR_ITP);
        propertyIndexValType.add(ICMSConstant.PROP_VAL_DESCR_ID);
        propertyIndexValType.add(ICMSConstant.PROP_VAL_DESCR_IS);
        propertyIndexValType.add(ICMSConstant.PROP_VAL_DESCR_IRH);

    }

    public static List getValDescList() {
        List lbValList = new ArrayList();
        HashMap map = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.PROP_VAL_DESCR);

        for (int i = 0; i < propertyIndexValType.size(); i++) {
            String propValType = (String) propertyIndexValType.get(i);
            LabelValueBean lvBean = new LabelValueBean(map.get(propValType).toString(), propValType);
            lbValList.add(lvBean);

        }
        return lbValList;
    }

    public boolean isItemExist(Set existingItemList, IPropertyIdxItem currItem) {
        boolean found = false;
        boolean foundP = false;
        boolean foundM = false;
        boolean foundD = false;
        if (existingItemList == null || existingItemList.isEmpty()) {
            return found;
        }
        for (Iterator existItr = existingItemList.iterator(); existItr.hasNext();) {

            IPropertyIdxItem existPropIndex = (IPropertyIdxItem) existItr.next();
            found = false;

            if (currItem.getIdxYear() != null &&
                    existPropIndex.getIdxYear() != null &&
                    !currItem.getIdxYear().equals(existPropIndex.getIdxYear())) {

                continue;
            }
            if (currItem.getStateCode() != null &&
                    existPropIndex.getStateCode() != null &&
                    !currItem.getStateCode().equals(existPropIndex.getStateCode())) {

                continue;
            }

            if (currItem.getIdxType() != null &&
                    existPropIndex.getIdxType() != null &&
                    !currItem.getIdxType().equals(existPropIndex.getIdxType())) {

                continue;
            }

            if (isEmpty(existPropIndex.getPropertyTypeList()) && isEmpty(currItem.getPropertyTypeList())) {
                foundP = true;
            } else if (currItem.getPropertyTypeList() != null &&
                    existPropIndex.getPropertyTypeList() != null) {

                for (Iterator itr = currItem.getPropertyTypeList().iterator(); itr.hasNext();) {

                    IPropertyIdxPropertyType item = (IPropertyIdxPropertyType) itr.next();

                    if (existPropIndex.getPropertyTypeList().contains(item)) {
                        foundP = true;
                        //System.out.println("@@@@@@@@@@@ found property type");
                        break;
                    }
                }
                if (!foundP) {
                    continue;
                }
            }

            if (isEmpty(existPropIndex.getDistrictList()) && isEmpty(currItem.getDistrictList())) {
                foundD = true;
            } else if (currItem.getDistrictList() != null &&
                    existPropIndex.getDistrictList() != null) {

                for (Iterator itr = currItem.getDistrictList().iterator(); itr.hasNext();) {

                    IPropertyIdxDistrict item = (IPropertyIdxDistrict) itr.next();
                    if (existPropIndex.getDistrictList().contains(item)) {
                        foundD = true;
                        //System.out.println("@@@@@@@@@@@ found district");
                        break;
                    }
                }
                if (!foundD) {

                    continue;
                }
            } else {
                //not found
                foundD = false;
                continue;

            }

            if (isEmpty(existPropIndex.getMukimList()) && isEmpty(currItem.getMukimList())) {
                foundM = true;
            } else if (currItem.getMukimList() != null &&
                    existPropIndex.getMukimList() != null) {

                for (Iterator itr = currItem.getMukimList().iterator(); itr.hasNext();) {

                    IPropertyIdxMukim item = (IPropertyIdxMukim) itr.next();
                    if (existPropIndex.getMukimList().contains(item)) {
                        foundM = true;
                        //System.out.println("@@@@@@@@@@@ found mukim");
                        break;
                    }
                }
                if (!foundM) {
                    continue;
                }
            } else {
                //not found
                foundM = false;
                continue;
            }

            //found in the existing items
            if (foundP && foundD && foundM) {
                found = true;
                break;
            } else {
                found = false;
            }
        }//end for
        return found;
    }

    public boolean isEmpty(Collection list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }

    public List deleteItem(List itemOrig, String[] deleteInd) {
        if (itemOrig != null && deleteInd != null) {
            List tempList = new ArrayList();
            for (int i = 0; i < deleteInd.length; i++) {
                int nextDelInd = Integer.parseInt(deleteInd[i]);
                tempList.add(itemOrig.get(nextDelInd));
            }
            itemOrig.removeAll(tempList);
            tempList.clear();
        }
        return itemOrig;
    }

    public IPropertyIdxItem getCurWorkingPropertyIdxItem(String event, String fromEvent, int index, IPropertyIdxTrxValue trxValue) {
        IPropertyIdx template = null;
        if ("read".equals(fromEvent)) {
            template = trxValue.getPrIdx();
        } else {
            template = trxValue.getStagingPrIdx();
        }
        if (template != null) {
            IPropertyIdxItem[] item = (IPropertyIdxItem[]) template.getPropertyIdxItemList().toArray();
            if (item != null) {
                return item[index];
            }
        }
        return null;
    }

    public String getSecurityTypeDesc(String secCode) {
        return CommonDataSingleton.getCodeCategoryLabelByValue("31", secCode);
    }

    public void setSecuritySubTypeToList(ArrayList listValue, ArrayList listLabel, Locale locale)
            throws CollateralException {
        try {
            SecuritySubTypeList subTypeList = SecuritySubTypeList.getInstance();
            Collection fullList = subTypeList.getSecuritySubTypeProperty();

            // filter out sub type for property type only
            Iterator iter = fullList.iterator();
            HashMap subTypeLabelValMap = new HashMap();
            while (iter.hasNext()) {
                String subType = (String) iter.next();
                if (subType != null && (ICMSConstant.SECURITY_TYPE_PROPERTY).equalsIgnoreCase(subType.substring(0, 2))) {
                    subTypeLabelValMap.put(subTypeList.getSecuritySubTypeValue(subType, locale), subType);
                }
            }

            // sort the list by label
            String[] subTypeLabel = (String[]) subTypeLabelValMap.keySet().toArray(new String[0]);
            Arrays.sort(subTypeLabel);

            listLabel.addAll(new ArrayList(Arrays.asList(subTypeLabel)));
            for (int i = 0; i < subTypeLabel.length; i++) {
                listValue.add(subTypeLabelValMap.get(subTypeLabel[i]));
            }
        }
        catch (CollateralException cex) {
            DefaultLogger.error(this, "", cex);
            throw cex;
        }
    }

}
