package com.integrosys.cms.app.securityenvelope.proxy;

import java.util.List;
import java.util.Set;

import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.bus.SecEnvelopeException;
import com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Title: CLIMS 
 * Description: 
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Erene Wong
 * Date: Jan 28, 2010
 */

public interface ISecEnvelopeProxyManager {

	public List getAllActual() throws SecEnvelopeException;

    public List getAllEnvItemStaging() throws SecEnvelopeException;

    public ISecEnvelopeTrxValue getActualMasterSecEnvelope(long lspLmtProfileId) throws SecEnvelopeException;

    public ISecEnvelopeTrxValue getSecEnvelopeByTrxID(String aTrxID) throws SecEnvelopeException;

    public int getNumDocItemInEnvelope(String envBarcode)throws SecEnvelopeException;

	public ISecEnvelopeTrxValue getSecEnvelopeTrxValue(long aSecEnvelopeId) throws SecEnvelopeException;

	public ISecEnvelopeTrxValue makerCreateSecEnvelope(ITrxContext anITrxContext, ISecEnvelope anICCSecEnvelope)
        throws SecEnvelopeException;

	public ISecEnvelopeTrxValue makerUpdateSecEnvelope(ITrxContext anITrxContext, ISecEnvelopeTrxValue anICCSecEnvelopeTrxValue, ISecEnvelope anICCSecEnvelope)
		throws SecEnvelopeException;

	public ISecEnvelopeTrxValue makerDeleteSecEnvelope(ITrxContext anITrxContext, ISecEnvelopeTrxValue anICCSecEnvelopeTrxValue, ISecEnvelope anICCSecEnvelope)
		throws SecEnvelopeException;

	public ISecEnvelopeTrxValue checkerApproveSecEnvelope(ITrxContext anITrxContext, ISecEnvelopeTrxValue anISecEnvelopeTrxValue) throws SecEnvelopeException;

	public ISecEnvelopeTrxValue checkerRejectSecEnvelope(ITrxContext anITrxContext, ISecEnvelopeTrxValue anISecEnvelopeTrxValue) throws SecEnvelopeException;

	public ISecEnvelopeTrxValue makerEditRejectedSecEnvelope(ITrxContext anITrxContext, ISecEnvelopeTrxValue anISecEnvelopeTrxValue, ISecEnvelope anSecEnvelope) throws SecEnvelopeException;

	public ISecEnvelopeTrxValue makerCloseRejectedSecEnvelope(ITrxContext anITrxContext, ISecEnvelopeTrxValue anISecEnvelopeTrxValue) throws SecEnvelopeException;

	public List getEnvelopeItemByLimitProfileId(long lspLmtProfileId);
}
