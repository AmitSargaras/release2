/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.securityenvelope.bus.*;
import com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue;

import java.util.*;

/**
 * Title: CLIMS
 * Description: Map the form to OB or OB to form for security envelope
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Erene Wong
 * Date: Jan 28, 2010
 */

public class SecEnvelopeMapper extends AbstractCommonMapper {
    /**
     * Default Construtor
     */
    public SecEnvelopeMapper() {
    }

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"ISecEnvelopeTrxValue", "com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue", SERVICE_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
        });
    }

    /**
     * This method is used to map the Form values into Corresponding OB Values and returns the same.
     *
     * @param cForm is of type CommonForm
     * @return Object
     */
    public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
        String event = (String) (map.get("event"));
        try {
            ISecEnvelope obParam = null;
            if ("maker_confirm_create".equals(event)) {
                obParam = new OBSecEnvelope();
            } else {
                ISecEnvelopeTrxValue trxValue = (ISecEnvelopeTrxValue) (map.get("ISecEnvelopeTrxValue"));
                DefaultLogger.debug(this, "security envelope,ISecEnvelopeTrxValue:: " + trxValue);
                obParam = trxValue.getStagingSecEnvelope();
            }
            if (obParam == null) {
                obParam = new OBSecEnvelope();
            }

            SecEnvelopeForm aForm = (SecEnvelopeForm) cForm;
            obParam.setSecLspLmtProfileId(Long.parseLong(aForm.getSecLspLmtProfileId()));
            obParam.setStatus(ICMSConstant.STATE_ACTIVE);
            if (aForm.getSecEnvelopeItemList() != null) {
                DefaultLogger.debug(this, "FormToOB B4 Sec Envelope Item::" + aForm.getSecEnvelopeItemList());
                obParam.setSecEnvelopeItemList(new HashSet(aForm.getSecEnvelopeItemList()));
            }

            if ("delete_items".equals(event) || "delete_edit_items".equals(event) || "delete_resubmit_edit_items".equals(event)) {
                //deleteItem(obParam, aForm);
                obParam.setDeletedItemList(aForm.getDeletedItemList());
            }
            return obParam;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new MapperException();
        }

    }

    /**
     * This method is used to map data from OB to the form and to return the form.
     *
     * @param cForm is of type CommonForm
     * @param obj   is of type Object
     * @return Object
     */
    public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
        DefaultLogger.debug(this, "inside mapOb to form ");
        try {
            String event = (String) (map.get("event"));
            DefaultLogger.debug(this, "OB to Form,event here:::" + event);
            ISecEnvelopeTrxValue SecEnvelopeTrxObj = (ISecEnvelopeTrxValue) (map.get("ISecEnvelopeTrxValue"));
            SecEnvelopeForm aForm = (SecEnvelopeForm) cForm;

            if (obj != null) {
                OBSecEnvelope tempOb = (OBSecEnvelope) obj;

                if (!"checker_process_create".equals(event) && !"checker_process_edit".equals(event) && !"checker_process_delete".equals(event)) {
                    DefaultLogger.debug(this, "OB to Form,Inside security envelope item::");

                    if (tempOb.getSecEnvelopeItemList() != null) {
                        for (int i = 0; i < tempOb.getSecEnvelopeItemList().size(); i++) {
                            aForm.setSecEnvelopeItemList(new ArrayList(tempOb.getSecEnvelopeItemList()));
                        }
                    } else {
                        aForm.setSecEnvelopeItemList(new ArrayList());
                    }
                    List compareList = aForm.getSecEnvelopeItemList();
                    if (compareList != null) {
                        aForm.setSecEnvelopeItemList(compareList);
                    }
                } else {
                    renderCompareItem(SecEnvelopeTrxObj.getSecEnvelope(), SecEnvelopeTrxObj.getStagingSecEnvelope(), aForm);
                }

               String secLspLmtProfileIdStr = String.valueOf(tempOb.getSecLspLmtProfileId());

               if (secLspLmtProfileIdStr != null && !("").equals(secLspLmtProfileIdStr)) {
                    aForm.setSecLspLmtProfileId(secLspLmtProfileIdStr);
               }
               aForm.setSecLspLmtProfileId(String.valueOf(tempOb.getSecLspLmtProfileId()));
               aForm.setDeletedItemList(new String[0]);
            }
            DefaultLogger.debug(this, "Going out of mapOb to form ");
            DefaultLogger.debug(this, "Document object in Mapper" + aForm);
            return aForm;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new MapperException();
        }
    }

    private static void renderCompareItem(ISecEnvelope origEnvelope, ISecEnvelope stagingEnvelope, SecEnvelopeForm aForm) throws Exception {
        ISecEnvelopeItem[] secEnvelopeItemOldLocRef = null;
        ISecEnvelopeItem[] secEnvelopeItemNewLocRef = null;

        if (origEnvelope != null) {
            ArrayList secEnvelopeItemCompareItemList = new ArrayList(origEnvelope.getSecEnvelopeItemList());
            secEnvelopeItemOldLocRef = (ISecEnvelopeItem[]) secEnvelopeItemCompareItemList.toArray(new ISecEnvelopeItem[secEnvelopeItemCompareItemList.size()]);
        }
        if (stagingEnvelope != null) {
            ArrayList secEnvelopeItemCompareStagingItemList = new ArrayList(stagingEnvelope.getSecEnvelopeItemList());
            secEnvelopeItemNewLocRef = (ISecEnvelopeItem[]) secEnvelopeItemCompareStagingItemList.toArray(new ISecEnvelopeItem[secEnvelopeItemCompareStagingItemList.size()]);
        }

        if (secEnvelopeItemOldLocRef == null) {
            secEnvelopeItemOldLocRef = new ISecEnvelopeItem[0];
        }
        if (secEnvelopeItemNewLocRef == null) {
            secEnvelopeItemNewLocRef = new ISecEnvelopeItem[0];
        }

        List compareList = CompareOBUtil.compOBArray(secEnvelopeItemNewLocRef, secEnvelopeItemOldLocRef);
        if (compareList != null) {
            aForm.setSecEnvelopeItemList(compareList);
        }
    }

    /*
    private void deleteItem(ISecEnvelope obParam, SecEnvelopeForm aForm) {

        SecEnvelopeUIHelper helper = new SecEnvelopeUIHelper();
        ArrayList itemList = new ArrayList(obParam.getSecEnvelopeItemList());
        OBSecEnvelopeItem[] item = (OBSecEnvelopeItem[]) itemList.toArray(new OBSecEnvelopeItem[itemList.size()]);

        String[] deletedInd = aForm.getDeletedItemList();

        if (item != null && deletedInd != null && deletedInd.length > 0) {
            List tempList = new ArrayList();
            DefaultLogger.debug(this, "...delete item length:: " + item.length);
            for (int i = 0; i < item.length; i++) {
                tempList.add(item[i]);
            }

            List res = helper.deleteItem(tempList, deletedInd);
            obParam.setSecEnvelopeItemList(new HashSet(res));
        }

    }   */
}

