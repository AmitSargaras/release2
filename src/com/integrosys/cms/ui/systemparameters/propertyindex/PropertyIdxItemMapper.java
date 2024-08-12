/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


/**
 * @author Administrator
 *         <p/>
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PropertyIdxItemMapper extends AbstractCommonMapper {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"PropertyIdxTrxObj", "com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue", SERVICE_SCOPE},
                //{"curPropertyIdxItem", "com.integrosys.cms.app.propertyindex.bus.OBPropertyIdxItem", SERVICE_SCOPE},
        });
    }

    /* (non-Javadoc)
      * @see com.integrosys.base.uiinfra.common.IMapper#mapOBToForm(com.integrosys.base.uiinfra.common.CommonForm, java.lang.Object, java.util.HashMap)
      */
    public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs)
            throws MapperException {
        try {
            PropertyIdxUIHelper helper = new PropertyIdxUIHelper();
            OBPropertyIdxItem item = (OBPropertyIdxItem) obj;
            PropertyIdxItemForm itemForm = (PropertyIdxItemForm) commonForm;

            itemForm.setIdxYear(item.getIdxYear().toString());
            itemForm.setIdxType(item.getIdxType());
            itemForm.setIdxValue(item.getIdxValue().toString());

            itemForm.setState(item.getStateCode());

            if (item.getPropertyTypeList() != null) {
                String[] tmp = new String[item.getPropertyTypeList().size()];
                int i = 0;
                for (Iterator iter = item.getPropertyTypeList().iterator(); iter.hasNext();) {
                    IPropertyIdxPropertyType element = (IPropertyIdxPropertyType) iter.next();
                    tmp[i] = element.getPropertyTypeId();
                    i++;
                }

                itemForm.setSelectedPropertyType(tmp);
            }

            if (item.getDistrictList() != null) {
                String[] tmp = new String[item.getDistrictList().size()];
                int i = 0;
                for (Iterator iter = item.getDistrictList().iterator(); iter.hasNext();) {
                    IPropertyIdxDistrict element = (IPropertyIdxDistrict) iter.next();
                    tmp[i] = element.getDistrictCode();
                    i++;
                }

                itemForm.setSelectedDistrict(tmp);
            }

            if (item.getMukimList() != null) {
                String[] tmp = new String[item.getMukimList().size()];
                DefaultLogger.debug(this, "OB to Form,getSelectedDistrict" + item.getMukimList());
                int i = 0;
                for (Iterator iter = item.getMukimList().iterator(); iter.hasNext();) {
                    IPropertyIdxMukim element = (IPropertyIdxMukim) iter.next();
                    tmp[i] = element.getMukimCode();
                    i++;
                }

                itemForm.setSelectedMukim(tmp);
            }
            //DefaultLogger.debug(this,"OB to Form,getPropertyIdxMukimList" + aForm.getSelectedMukim());

            DefaultLogger.debug(this, "OBTOFORM:" + itemForm);
            return itemForm;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new MapperException();
        }
    }

    /* (non-Javadoc)
      * @see com.integrosys.base.uiinfra.common.IMapper#mapFormToOB(com.integrosys.base.uiinfra.common.CommonForm, java.util.HashMap)
      */
    public Object mapFormToOB(CommonForm commonForm, HashMap inputs)
            throws MapperException {
        // TODO Auto-generated method stub
        // String event = (String)(inputs.get("event"));
        try {
            PropertyIdxItemForm itemForm = (PropertyIdxItemForm) commonForm;
            String idxValueRegex = "[0-9]{1,9}\\.[0-9]{1,6}|[0-9]{1,4}";
            IPropertyIdxItem item = new OBPropertyIdxItem();

            if (itemForm.getIdxYear() != null && !itemForm.getIdxYear().equals("")) {
                BigDecimal year = new BigDecimal(itemForm.getIdxYear());
                item.setIdxYear(year);
            }
            item.setIdxType(itemForm.getIdxType());

            if (itemForm.getIdxValue() != null && !itemForm.getIdxValue().equals("") && Validator.checkPattern(itemForm.getIdxValue(), idxValueRegex)) {
                BigDecimal propertyValue = new BigDecimal(itemForm.getIdxValue());
                item.setIdxValue(propertyValue);
            }

            item.setStatus(ICMSConstant.STATE_ACTIVE);
            item.setStateCode(itemForm.getState());

            if (itemForm.getSelectedPropertyType() != null && itemForm.getSelectedPropertyType().length > 0) {
                ArrayList list = new ArrayList();
                for (int i = 0; i < itemForm.getSelectedPropertyType().length; i++) {
                    IPropertyIdxPropertyType idxPropertyType = new OBPropertyIdxPropertyType();
                    idxPropertyType.setPropertyTypeId(itemForm.getSelectedPropertyType()[i]);
                    idxPropertyType.setStatus(ICMSConstant.STATE_ACTIVE);
                    list.add(idxPropertyType);
                }
                item.setPropertyTypeList(new HashSet(list));
            }

            if (itemForm.getSelectedDistrict() != null && itemForm.getSelectedDistrict().length > 0) {
                ArrayList list = new ArrayList();
                for (int i = 0; i < itemForm.getSelectedDistrict().length; i++) {
                    IPropertyIdxDistrict idxDistrict = new OBPropertyIdxDistrict();
                    idxDistrict.setDistrictCode(itemForm.getSelectedDistrict()[i]);
                    idxDistrict.setStatus(ICMSConstant.STATE_ACTIVE);
                    list.add(idxDistrict);
                }
                item.setDistrictList(new HashSet(list));
            }

            if (itemForm.getSelectedMukim() != null && itemForm.getSelectedMukim().length > 0) {
                ArrayList list = new ArrayList();
                for (int i = 0; i < itemForm.getSelectedMukim().length; i++) {
                    IPropertyIdxMukim idxMukim = new OBPropertyIdxMukim();
                    idxMukim.setMukimCode(itemForm.getSelectedMukim()[i]);
                    idxMukim.setStatus(ICMSConstant.STATE_ACTIVE);
                    list.add(idxMukim);
                }
                item.setMukimList(new HashSet(list));
            }
            return item;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new MapperException();
        }
    }

}
