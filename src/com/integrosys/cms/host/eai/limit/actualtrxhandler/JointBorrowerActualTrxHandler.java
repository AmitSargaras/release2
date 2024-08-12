package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.WeakHashMap;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.host.eai.CreditGrade;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;
import com.integrosys.cms.host.eai.customer.bus.SubProfile;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.JointBorrowerAlreadyExistsException;
import com.integrosys.cms.host.eai.limit.NoSuchJointBorrowerException;
import com.integrosys.cms.host.eai.limit.bus.ILimitDao;
import com.integrosys.cms.host.eai.limit.bus.JointBorrower;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;

/**
 * <p>
 * Handler for processing Joint Borrower information and Joint Borrower Credit
 * Grade.
 * <p>
 * No staging table required for Joint Borrower.
 * 
 * @author Chong Jun Yong
 * 
 */
public class JointBorrowerActualTrxHandler extends AbstractCommonActualTrxHandler {

	private ILimitDao limitDao;

	public void setLimitDao(ILimitDao limitDao) {
		this.limitDao = limitDao;
	}

	protected ILimitDao getLimitDao() {
		return this.limitDao;
	}

	public Message persistActualTrx(Message msg) {
		AAMessageBody aaMsgBody = (AAMessageBody) msg.getMsgBody();

		Vector jointBorrowers = aaMsgBody.getJointBorrower();
		if (jointBorrowers != null && !jointBorrowers.isEmpty()) {
			LimitProfile limitProfile = aaMsgBody.getLimitProfile();

			for (Iterator itr = jointBorrowers.iterator(); itr.hasNext();) {
				JointBorrower jointBorrower = (JointBorrower) itr.next();
				jointBorrower.setCmsLimitProfileId(limitProfile.getLimitProfileId());
				storeJointBorrower(jointBorrower, limitProfile.getCIFSource());
			}
		}

		return msg;
	}

	/**
	 * <p>
	 * Process Joint Borrower from the source, such as Insert/Update/Delete.
	 * <p>
	 * If there is no CRUD involved, only the CMS Ids will be updated to the
	 * Joint Borrower instance for further processing in another handler.
	 * @param jointBorrower Joint Borrower to be processed (coming from the
	 *        source)
	 * @param cifSource the source of the CIF for the Joint Borrower
	 * @throws JointBorrowerAlreadyExistsException if Joint Borrower already
	 *         exists but trying to create again
	 * @throws NoSuchJointBorrowerException if update a Joint Borrower not
	 *         exists in CMS
	 * @return a processed joint borrower
	 */
	protected JointBorrower storeJointBorrower(JointBorrower jointBorrower, String cifSource) {
		prepareJointBorrowerRecords(jointBorrower, cifSource);

		jointBorrower.setCifSource(cifSource);

		Map parameters = new WeakHashMap();
		parameters.put("CIFId", jointBorrower.getCIFId());
		parameters.put("AANumber", jointBorrower.getAANumber());

		JointBorrower storedJointBorrower = (JointBorrower) this.limitDao.retrieveNonDeletedObjectByParameters(
				parameters, "updateStatusIndicator", JointBorrower.class);

		if ((jointBorrower.getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
				&& (jointBorrower.getUpdateStatusIndicator().equals(String.valueOf(CREATEINDICATOR)))) {

			if (storedJointBorrower != null) {
				throw new JointBorrowerAlreadyExistsException(jointBorrower.getCIFId(), jointBorrower.getAANumber());
			}

			this.limitDao.store(jointBorrower, jointBorrower.getClass());

		}
		else if ((jointBorrower.getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
				&& ((jointBorrower.getUpdateStatusIndicator().equals(String.valueOf(UPDATEINDICATOR))) || (jointBorrower
						.getUpdateStatusIndicator().equals(String.valueOf(DELETEINDICATOR))))) {

			if (storedJointBorrower == null) {
				throw new NoSuchJointBorrowerException(jointBorrower.getCIFId(), jointBorrower.getAANumber());
			}

			AccessorUtil.copyValue(jointBorrower, storedJointBorrower, new String[] { IEaiConstant.CMSID });
			this.limitDao.update(storedJointBorrower, storedJointBorrower.getClass());

			jointBorrower.setCmsId(storedJointBorrower.getCmsId());
		}
		else {
			// to populate cms keys
			if (storedJointBorrower != null) {
				jointBorrower.setCmsId(storedJointBorrower.getCmsId());
				jointBorrower.setCmsLimitProfileId(storedJointBorrower.getCmsLimitProfileId());
				jointBorrower.setSubProfileId(storedJointBorrower.getCmsSubProfileId());
			}
		}

		return jointBorrower;
	}

	protected JointBorrower storeJointBorrowerCreditGrade(JointBorrower jointBorrower) {
		Vector creditGradeVector = jointBorrower.getCreditGrade();

		if ((creditGradeVector != null) && !creditGradeVector.isEmpty()) {
			Iterator iterator = creditGradeVector.iterator();

			MainProfile mp = jointBorrower.getMainProfile();

			while (iterator.hasNext()) {
				CreditGrade creditGrade = (CreditGrade) iterator.next();

				creditGrade.setCmsMainProfileId(mp.getCmsId());

				if ((creditGrade.getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
						&& (creditGrade.getUpdateStatusIndicator().equals(String.valueOf(CREATEINDICATOR)))) {
					getLimitDao().store(creditGrade, creditGrade.getClass());
				}
				else if ((creditGrade.getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
						&& ((creditGrade.getUpdateStatusIndicator().equals(String.valueOf(UPDATEINDICATOR))) || (creditGrade
								.getUpdateStatusIndicator().equals(String.valueOf(DELETEINDICATOR))))) {
					Map parameters = new WeakHashMap();

					parameters.put("CIFId", creditGrade.getCIFId());
					parameters.put("creditGradeType.standardCodeValue", creditGrade.getCreditGradeType()
							.getStandardCodeValue());
					parameters.put("cmsMainProfileId", new Long(mp.getCmsId()));

					CreditGrade storedCreditGrade = (CreditGrade) getLimitDao().retrieveNonDeletedObjectByParameters(
							parameters, "updateStatusIndicator", CreditGrade.class);

					AccessorUtil.copyValue(creditGrade, storedCreditGrade, new String[] { IEaiConstant.CMSID });
					getLimitDao().update(storedCreditGrade, CreditGrade.class);

					creditGrade.setCmsId(storedCreditGrade.getCmsId());
				}
				else {
					logger.warn("Credit grade not having any indicator that required action. CIF Id ["
							+ creditGrade.getCIFId() + "]");
				}
			}
		}

		return jointBorrower;
	}

	protected void prepareJointBorrowerRecords(JointBorrower jointBorrower, String cifSource) {
		Map parameters = new WeakHashMap();
		parameters.put("CIFId", jointBorrower.getCIFId());
		parameters.put("source", cifSource);

		MainProfile mp = (MainProfile) this.limitDao.retrieveObjectByParameters(parameters, MainProfile.class);
		if (mp == null) {
			throw new NoSuchJointBorrowerException(jointBorrower.getCIFId(), jointBorrower.getAANumber());
		}

		jointBorrower.setMainProfile(mp);

		parameters.clear();
		parameters.put("cifId", jointBorrower.getCIFId());
		parameters.put("subProfileId", new Long(jointBorrower.getSubProfileId()));

		SubProfile temp = (SubProfile) this.limitDao.retrieveObjectByParameters(parameters, SubProfile.class);
		if (temp != null) {
			jointBorrower.setCmsSubProfileId(temp.getCmsId());
		}
	}
}
