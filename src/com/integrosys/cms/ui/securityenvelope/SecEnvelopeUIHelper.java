package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelopeItem;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue;
import com.integrosys.component.commondata.app.CommonDataSingleton;

import java.util.*;

/**
 * @author Erene Wong
 * @since 28 Jan 2010
 */
public class SecEnvelopeUIHelper extends SecEnvelopeCmd{
       
    public boolean isEmpty(Collection list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }

    /*
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
    } */

    public ISecEnvelopeItem getCurWorkingSecEnvelopeItem(String event, String fromEvent, int index, ISecEnvelopeTrxValue trxValue) {
        ISecEnvelope template = null;
        if ("read".equals(fromEvent)) {
            template = trxValue.getSecEnvelope();
        } else {
            template = trxValue.getStagingSecEnvelope();
        }
        if (template != null) {
            ISecEnvelopeItem[] item = (ISecEnvelopeItem[]) template.getSecEnvelopeItemList().toArray();
            if (item != null) {
                return item[index];
            }
        }
        return null;
    }

}
