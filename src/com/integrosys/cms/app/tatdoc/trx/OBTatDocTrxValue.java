package com.integrosys.cms.app.tatdoc.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.tatdoc.bus.ITatDoc;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 5:41:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBTatDocTrxValue extends OBCMSTrxValue implements ITatDocTrxValue {

    private ITatDoc tatDoc = null;
    private ITatDoc stageTatDoc = null;


    /**
     * Default Constructor
     */
    public OBTatDocTrxValue() {
    }

    /**
     * Construct the object based on the tat doc object
     * @param obj - ITatDoc
     */
    public OBTatDocTrxValue(ITatDoc obj) {
        AccessorUtil.copyValue(obj, this);
    }

    /**
     * Construct the object based on its parent info
     * @param anICMSTrxValue - ICMSTrxValue
     */
    public OBTatDocTrxValue(ICMSTrxValue anICMSTrxValue) {
        AccessorUtil.copyValue(anICMSTrxValue, this);
    }


    public ITatDoc getTatDoc() {
        return tatDoc;
    }

    public void setTatDoc(ITatDoc tatDoc) {
        this.tatDoc = tatDoc;
    }

    public ITatDoc getStagingTatDoc() {
        return stageTatDoc;
    }

    public void setStagingTatDoc(ITatDoc stageTatDoc) {
        this.stageTatDoc = stageTatDoc;
    }
    
}
