/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.securityenvelope.bus.*;

import java.util.HashMap;


/**
 * @author Administrator
 *         <p/>
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecEnvelopeItemMapper extends AbstractCommonMapper {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"SecEnvelopeTrxObj", "com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue", SERVICE_SCOPE}
        });
    }

    /* (non-Javadoc)
      * @see com.integrosys.base.uiinfra.common.IMapper#mapOBToForm(com.integrosys.base.uiinfra.common.CommonForm, java.lang.Object, java.util.HashMap)
      */
     public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs)
            throws MapperException {
        try {
            SecEnvelopeUIHelper helper = new SecEnvelopeUIHelper();
            OBSecEnvelopeItem item = (OBSecEnvelopeItem) obj;
            SecEnvelopeItemForm itemForm = (SecEnvelopeItemForm) commonForm;

            itemForm.setSecEnvelopeItemAddr(item.getSecEnvelopeItemAddr());
            itemForm.setSecEnvelopeItemCab(item.getSecEnvelopeItemCab());
            itemForm.setSecEnvelopeItemDrw(item.getSecEnvelopeItemDrw());
            itemForm.setSecEnvelopeItemBarcode(item.getSecEnvelopeItemBarcode());
            itemForm.setSecEnvelopeRefId(String.valueOf(item.getSecEnvelopeRefId()));

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
        try {
            SecEnvelopeItemForm itemForm = (SecEnvelopeItemForm) commonForm;
            String idxValueRegex = "[0-9]{1,9}\\.[0-9]{1,6}|[0-9]{1,4}";
            ISecEnvelopeItem item = new OBSecEnvelopeItem();
          
            item.setSecEnvelopeItemAddr(itemForm.getSecEnvelopeItemAddr());
            item.setSecEnvelopeItemCab(itemForm.getSecEnvelopeItemCab());
            item.setSecEnvelopeItemDrw(itemForm.getSecEnvelopeItemDrw());
            item.setSecEnvelopeItemBarcode(itemForm.getSecEnvelopeItemBarcode());
            if (itemForm.getSecEnvelopeRefId() != null && !itemForm.getSecEnvelopeRefId().equals("")) {
                item.setSecEnvelopeRefId(Long.parseLong(itemForm.getSecEnvelopeRefId()));
            }
            item.setStatus(ICMSConstant.STATE_ACTIVE);                    
            return item;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new MapperException();
        }
    }
}
