/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/IExemptFacilityGroupTrxValue.java,v 1.2 2003/08/06 05:42:09 btchng Exp $
 */
package com.integrosys.cms.app.creditriskparam.trx.exemptFacility;

import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacilityGroup;

/**
 * This interface represents a ExemptFacility trx value.
 *
 * @author $Author$
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/06 05:42:09 $
 * Tag: $Name:  $
 */
public interface IExemptFacilityGroupTrxValue
        extends ICMSTrxValue {

    public IExemptFacilityGroup getExemptFacilityGroup();

    public IExemptFacilityGroup getStagingExemptFacilityGroup();

    public void setExemptFacilityGroup(IExemptFacilityGroup value);

    public void setStagingExemptFacilityGroup(IExemptFacilityGroup value);
}