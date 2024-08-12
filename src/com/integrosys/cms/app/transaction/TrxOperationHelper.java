/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/TrxOperationHelper.java,v 1.5 2003/08/29 13:42:25 kllee Exp $
 */
package com.integrosys.cms.app.transaction;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Provides common helper methods.
 * 
 * @author Alfred Lee
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/29 13:42:25 $ Tag: $Name: $
 */
public class TrxOperationHelper {
	/**
	 * Maps Trx Context into TrxValue
	 * 
	 * @param trxContext is of type ITrxContext
	 * @param value is of type ITrxValue
	 * @return ITrxValue
	 * @throws TransactionException on error
	 */
	public static ITrxValue mapTrxContext(ITrxContext trxContext, ITrxValue value) throws TransactionException {
		try {
			if (null == value) {
				throw new TransactionException("ITrxValue is null!");
			}
			ICMSTrxValue trxValue = (ICMSTrxValue) value;
			if (null != trxContext) {
				ICommonUser user = trxContext.getUser();

				ITeam team = trxContext.getTeam();
				if (null != user) {
					trxValue.setUID(user.getUserID());
					// Change this if you want to display more information.
					trxValue.setUserInfo(user.getUserName() + " ( " + user.getLoginID() + " )");
					trxValue.setLoginId(user.getLoginID());
				}

				if (null != team) {
					trxValue.setTeamID(team.getTeamID());
					trxValue.setTeamTypeID(team.getTeamType().getTeamTypeID());
				}

				trxValue.setRemarks(trxContext.getRemarks());
				trxValue.setTeamMembershipID(trxContext.getTeamMembershipID());

				trxValue.setOriginatingCountry(trxContext.getTrxCountryOrigin());
				trxValue.setOriginatingOrganisation(trxContext.getTrxOrganisationOrigin());
				ICMSCustomer customer = trxContext.getCustomer();

				if (null != customer) {
					ICMSLegalEntity entity = customer.getCMSLegalEntity();
					if (null != entity) {
						trxValue.setLegalName(entity.getLegalName());
						trxValue.setLegalID(String.valueOf(entity.getLEID()));
					}
					trxValue.setCustomerName(customer.getCustomerName());
					if (customer.getCustomerID() > 0) {
						trxValue.setCustomerID(customer.getCustomerID());
					}
				}

				ILimitProfile profile = trxContext.getLimitProfile();
				if (null != profile) {
					if (profile.getLimitProfileID() > 0) {
						trxValue.setLimitProfileID(profile.getLimitProfileID());
						trxValue.setLimitProfileReferenceNumber(profile.getBCAReference());
					}
				}
			}
			trxValue.setTrxContext(trxContext); // map context into trx value

			return trxValue;
		}
		catch (TransactionException e) {
			throw e;
		}
		catch (RuntimeException e) {
			throw new TransactionException("failed to map trx context to trx value", e);
		}
	}
}