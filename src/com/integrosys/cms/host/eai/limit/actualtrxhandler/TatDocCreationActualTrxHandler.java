package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.tatdoc.bus.ITatDoc;
import com.integrosys.cms.app.tatdoc.bus.ITatDocBusManager;
import com.integrosys.cms.app.tatdoc.bus.OBTatDoc;
import com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue;
import com.integrosys.cms.app.tatdoc.trx.OBTatDocTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.host.eai.EAIProcessFailedException;
import com.integrosys.cms.host.eai.EAITransactionException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.FacilityMaster;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * <p>
 * Handler to create TAT doc for new AA. And also do update TAT solicitor
 * instruction date (in TAT and Facility Master) upon update of AA
 * <p>
 * Currently, create or update TAT can be toggled based on the source of the
 * message, all these are controlled by properties
 * {@link #sourceIdsRequiredCreateTat} and {@link #sourceIdsRequiredUpdateTat}
 * respectively.
 * @author Chong Jun Yong
 * 
 */
public class TatDocCreationActualTrxHandler extends AbstractCommonActualTrxHandler {

	private static final String OPS_DESC_SYSTEM_CREATE_TAT = "SYSTEM_CREATE_TAT";

	private ITatDocBusManager actualTatDocBusManager;

	private ITatDocBusManager stagingTatDocBusManager;

	private SBCMSTrxManager workflowManager;

	private String[] sourceIdsRequiredCreateTat;

	private String[] sourceIdsRequiredUpdateTat;

	private boolean requiredUpdateFacilityMasterInstructedDateUponUpdate;

	public void setActualTatDocBusManager(ITatDocBusManager actualTatDocBusManager) {
		this.actualTatDocBusManager = actualTatDocBusManager;
	}

	public void setStagingTatDocBusManager(ITatDocBusManager stagingTatDocBusManager) {
		this.stagingTatDocBusManager = stagingTatDocBusManager;
	}

	public void setWorkflowManager(SBCMSTrxManager workflowManager) {
		this.workflowManager = workflowManager;
	}

	public void setSourceIdsRequiredCreateTat(String[] sourceIdsRequiredCreateTat) {
		this.sourceIdsRequiredCreateTat = sourceIdsRequiredCreateTat;
	}

	public void setSourceIdsRequiredUpdateTat(String[] sourceIdsRequiredUpdateTat) {
		this.sourceIdsRequiredUpdateTat = sourceIdsRequiredUpdateTat;
	}

	public void setRequiredUpdateFacilityMasterInstructedDateUponUpdate(
			boolean requiredUpdateFacilityMasterInstructedDateUponUpdate) {
		this.requiredUpdateFacilityMasterInstructedDateUponUpdate = requiredUpdateFacilityMasterInstructedDateUponUpdate;
	}

	public Message persistActualTrx(Message msg) {
		AAMessageBody aaMsgBody = (AAMessageBody) msg.getMsgBody();
		String source = msg.getMsgHeader().getSource();
		LimitProfile limitProfile = aaMsgBody.getLimitProfile();

		ITatDoc storedTatDoc = this.actualTatDocBusManager.getTatDocByLimitProfileID(limitProfile.getLimitProfileId());

		if (String.valueOf(CHANGEINDICATOR).equals(limitProfile.getChangeIndicator())
				&& String.valueOf(CREATEINDICATOR).equals(limitProfile.getUpdateStatusIndicator())) {
			if (storedTatDoc == null && ArrayUtils.contains(this.sourceIdsRequiredCreateTat, source)) {
				createTat(limitProfile);
			}
		}
		else if (String.valueOf(CHANGEINDICATOR).equals(limitProfile.getChangeIndicator())
				&& String.valueOf(UPDATEINDICATOR).equals(limitProfile.getUpdateStatusIndicator())) {
			if (storedTatDoc == null && ArrayUtils.contains(this.sourceIdsRequiredCreateTat, source)) {
				createTat(limitProfile);
			}
			else if (storedTatDoc != null && ArrayUtils.contains(this.sourceIdsRequiredUpdateTat, source)) {
				storedTatDoc.setSolicitorInstructionDate(MessageDate.getInstance().getDate(
						limitProfile.getSolicitorInstructionDate()));
				this.actualTatDocBusManager.update(storedTatDoc);

				try {
					ICMSTrxValue tatTrxValue = this.workflowManager.findTrxByRefIDAndTrxType(String
							.valueOf(storedTatDoc.getTatDocID()), ICMSConstant.INSTANCE_TAT_DOC);

					ITatDoc storedStageTatDoc = this.stagingTatDocBusManager.getTatDocByID(Long.parseLong(tatTrxValue
							.getStagingReferenceID()));
					storedStageTatDoc.setSolicitorInstructionDate(MessageDate.getInstance().getDate(
							limitProfile.getSolicitorInstructionDate()));
					this.stagingTatDocBusManager.update(storedStageTatDoc);
				}
				catch (TransactionException ex) {
					logger.warn("failed to retrieve staging TAT doc using actual TAT id '" + storedTatDoc.getTatDocID()
							+ "' and 'TAT_DOC' workflow type, please verify", ex);
				}
				catch (RemoteException ex) {
					logger.warn("failed to retrieve staging TAT doc using actual TAT id '" + storedTatDoc.getTatDocID()
							+ "' and 'TAT_DOC' workflow type for unknown reason, please verify", ex.getCause());
				}

				if (this.requiredUpdateFacilityMasterInstructedDateUponUpdate) {
					Vector limits = aaMsgBody.getLimits();
					if (limits != null) {
						for (Iterator itr = limits.iterator(); itr.hasNext();) {
							Limits limit = (Limits) itr.next();
							FacilityMaster facility = limit.getFacilityMaster();
							if (facility != null) {
								facility.setJDOInstructedDate(MessageDate.getInstance().getDate(
										limitProfile.getSolicitorInstructionDate()));
							}
						}
					}
				}
			}
		}

		return msg;
	}

	protected void createTat(LimitProfile limitProfile) {
		ITatDoc stagingTatDoc = new OBTatDoc();
		stagingTatDoc.setLimitProfileID(limitProfile.getLimitProfileId());
		stagingTatDoc.setSolicitorInstructionDate(MessageDate.getInstance().getDate(
				limitProfile.getSolicitorInstructionDate()));
		stagingTatDoc.setIsPAOrSolInvolvementReq(limitProfile.getPASolicitorInvolvementRequiredInd());

		ITatDoc createdStagingTatDoc = this.stagingTatDocBusManager.create(stagingTatDoc);

		ITatDoc actualTatDoc = new OBTatDoc();
		actualTatDoc.setLimitProfileID(limitProfile.getLimitProfileId());
		actualTatDoc.setSolicitorInstructionDate(MessageDate.getInstance().getDate(
				limitProfile.getSolicitorInstructionDate()));
		actualTatDoc.setIsPAOrSolInvolvementReq(limitProfile.getPASolicitorInvolvementRequiredInd());

		ITatDoc createdActualTatDoc = this.actualTatDocBusManager.create(actualTatDoc);

		ITatDocTrxValue trxValue = new OBTatDocTrxValue();
		trxValue.setInstanceName(ICMSConstant.INSTANCE_TAT_DOC);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_TAT_DOC);
		trxValue.setReferenceID(String.valueOf(createdActualTatDoc.getTatDocID()));
		trxValue.setStagingReferenceID(String.valueOf(createdStagingTatDoc.getTatDocID()));
		trxValue.setCreateDate(new Date());
		trxValue.setFromState(ICMSConstant.STATE_ND);
		trxValue.setToState(ICMSConstant.STATE_ACTIVE);
		trxValue.setOpDesc(OPS_DESC_SYSTEM_CREATE_TAT);
		trxValue.setLimitProfileID(limitProfile.getLimitProfileId());
		trxValue.setLimitProfileReferenceNumber(limitProfile.getHostAANumber());

		if (limitProfile.getOriginatingLocation() != null) {
			trxValue.setOriginatingCountry(limitProfile.getOriginatingLocation().getOriginatingLocationCountry());
			trxValue.setOriginatingOrganisation(limitProfile.getOriginatingLocation()
					.getOriginatingLocationOrganisation());
		}

		if (limitProfile.getMainProfile() != null) {
			trxValue.setLegalName(limitProfile.getMainProfile().getCustomerNameLong());
			trxValue.setLegalID(String.valueOf(limitProfile.getMainProfile().getCmsId()));
			trxValue.setCustomerID(limitProfile.getMainProfile().getSubProfilePrimaryKey());
		}

		try {
			this.workflowManager.createTransaction(trxValue);
		}
		catch (TransactionException ex) {
			throw new EAITransactionException("failed to create workflow values for reference id ["
					+ createdActualTatDoc.getTatDocID() + "] trx type [" + ICMSConstant.INSTANCE_TAT_DOC + "]", ex);
		}
		catch (RemoteException ex) {
			throw new EAIProcessFailedException("failed to create workflow values for reference id ["
					+ createdActualTatDoc.getTatDocID() + "] trx type [" + ICMSConstant.INSTANCE_TAT_DOC + "]", ex
					.getCause());
		}

		ILimitProfile cmsLimitProfile = new OBLimitProfile();
		cmsLimitProfile.setLimitProfileID(limitProfile.getLimitProfileId());
		cmsLimitProfile.setLosLimitProfileReference(limitProfile.getLOSAANumber());
		cmsLimitProfile.setBCAReference(limitProfile.getHostAANumber());
		cmsLimitProfile.setCustomerID(limitProfile.getCmsSubProfileId());
		cmsLimitProfile.setOriginatingLocation(new OBBookingLocation(limitProfile.getOriginatingLocation()
				.getOriginatingLocationCountry(), limitProfile.getOriginatingLocation()
				.getOriginatingLocationOrganisation()));
		cmsLimitProfile.setApplicationType(limitProfile.getAAType());
		cmsLimitProfile.setLEReference(limitProfile.getCIFId());
		cmsLimitProfile.setBCAStatus(limitProfile.getAAStatus());

		this.actualTatDocBusManager.insertOrRemovePendingPerfectionCreditFolder(createdActualTatDoc, cmsLimitProfile);
	}
}
