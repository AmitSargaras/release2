package com.integrosys.cms.app.cci.proxy;

import com.integrosys.cms.app.cci.bus.CCICounterpartyDetailsException;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

public interface ICCICounterpartyDetailsProxy extends java.io.Serializable {

    ICCICounterpartyDetailsTrxValue  makerSubmitUnitTrustFeedGroup(
            ITrxContext anITrxContext, ICCICounterpartyDetailsTrxValue aTrxValue,
            ICCICounterpartyDetails aFeedGroup)
            throws CCICounterpartyDetailsException;



    public static final String NO_FEED_GROUP = "no.feed.group";

}
