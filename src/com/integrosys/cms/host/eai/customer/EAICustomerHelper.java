package com.integrosys.cms.host.eai.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.SessionContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.batch.common.BatchJobTrxUtil;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.customer.bus.ICustomerDao;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;
import com.integrosys.cms.host.eai.customer.bus.SubProfile;
import com.integrosys.cms.host.eai.customer.validator.MainProfileValidator;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.mq.EAIMessenger;
import com.integrosys.cms.host.mq.IMQConstant;
import com.integrosys.cms.ui.eventmonitor.EventMonitorClient;

/**
 * Singleton for Customer Enquiry Handling
 * 
 * @author marvin
 * @author allen
 * @author Chong Jun Yong
 * @since 1.1
 */
public class EAICustomerHelper {

	private Log logger = LogFactory.getLog(EAICustomerHelper.class);

	private static EAICustomerHelper instance = null;

	public static final String RENEWALCODE = "39";

	public static final String RENEWALVALUE = "2";

	public static final String CIF_ID = "CIFId";

	// GEMS
	public final static String KEY = "BODY_KEY";

	private ICustomerDao customerDao;

	public ICustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	private EAICustomerHelper() {
	}

	private EAICustomerHelper(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public static EAICustomerHelper getInstance() {
		if (instance == null) {
			synchronized (MainProfileValidator.class) {
				if (instance == null) {
					instance = new EAICustomerHelper();
				}
			}
		}
		return instance;
	}

	public static EAICustomerHelper getInstance(ICustomerDao customerDao) {
		if (instance == null) {
			synchronized (MainProfileValidator.class) {
				if (instance == null) {
					instance = new EAICustomerHelper(customerDao);
				}
			}
		}
		return instance;
	}

	/**
	 * Send CU001 and Create Dummy Customer Records in GCMS , NON Borrower
	 * Indicator set to Y
	 * 
	 * @param cdb
	 * @param cifId
	 * @param cifSource
	 * @return
	 * @throws Exception
	 */
	public MainProfile getMainProfileOrCreateDummy(String cifId, String cifSource) {
		return getMainProfileOrCreateDummy(cifId, cifSource, null, false);
	}

	private static HashMap customerCache = new HashMap();

	public synchronized void resetCustomerCache() {
		customerCache.clear();
	}

	private synchronized void putCustomer(Object key, MainProfile mp) {
		// set Max to 3000
		if (customerCache.size() > 3000) {
			customerCache.clear();
		}

		customerCache.put(key, mp);
	}

	private synchronized MainProfile getCustomer(Object key) {

		return (MainProfile) customerCache.get(key);

	}

	public synchronized MainProfile getMainProfileOrCreateDummyWithDelay(final String cifId, final String cifSource,
			boolean isBorrower) {
		return getMainProfileOrCreateDummyWithDelay(cifId, cifSource, null, isBorrower);
	}

	/**
	 * Use for SI Only .
	 * 
	 * @param cdb
	 * @param cifId
	 * @param cifSource
	 * @param isBorrower - NON Borrower Indicator set to N
	 * @return
	 */
	public synchronized MainProfile getMainProfileOrCreateDummyWithDelay(final String cifId, final String cifSource,
			MainProfile mirrorMainProfile, boolean isBorrower) {
		MainProfile tmpMainProfile = null;
		String cacheKey = cifId + "_" + cifSource;
		// Implements Caching
		tmpMainProfile = getCustomer(cacheKey);

		// Retriving Customer Main Profile
		if (tmpMainProfile != null) {
			logger.debug("** Customer Cache Hit :" + cacheKey);

			return tmpMainProfile;
		}
		else {

			// Check Is Customer Exists
			Map parameters = new HashMap();
			parameters.put("CIFId", cifId);
			parameters.put("source", cifSource);

			tmpMainProfile = (MainProfile) getCustomerDao().retrieveNonDeletedObjectByParameters(parameters,
					"updateStatusIndicator", MainProfile.class);

			if (tmpMainProfile != null) {
				// Retrieve SubProfile Data
				parameters.put("cmsMainProfileId", new Long(tmpMainProfile.getCmsId()));

				SubProfile tmpsb = (SubProfile) getCustomerDao().retrieveObjectByParameters(parameters,
						SubProfile.class);

				if (tmpsb == null) {
					throw new IllegalStateException("Customer not create yet in persistent storage CIF id [" + cifId
							+ "]");
				}

				tmpMainProfile.setSubProfilePrimaryKey(tmpsb.getCmsId());

				// Update Borrower Indicator
				if (isBorrower) {
					tmpsb.setNonBorrowerIndicator(ICMSConstant.FALSE_VALUE.charAt(0));
				}
				else {
					// do nothing
				}

				getCustomerDao().update(tmpMainProfile, MainProfile.class);
				getCustomerDao().update(tmpsb, SubProfile.class);
			}

		}

		if (tmpMainProfile == null) {
			// CreateDummy Record

			String pendingName = null;

			if (mirrorMainProfile != null) {
				pendingName = mirrorMainProfile.getCustomerNameShort();
			}

			tmpMainProfile = createDummyCustomerRecord(cifId, cifSource, mirrorMainProfile, isBorrower, pendingName);

			// Send External SI
			new Thread() {

				public void run() {
					super.run();
					logger.debug("** Trigger External Customer SI - Run**");
					try {

						// Delay for N seconds before sending out SI message.
						long millis = PropertyManager.getLong("CU001.dummy.delay.ms", 12000);

						Thread.sleep(millis);
						triggerExternalCustomerDetailSI(cifId, cifSource);
					}
					catch (Exception e) {
						logger.error("encounter error when triggering external customer detail SI, CIF Id [" + cifId
								+ "]");
					}
				}

				public synchronized void start() {
					super.start();
					logger.debug("** Trigger External Customer SI - Start**");

				}

			}.start();

		}

		if (tmpMainProfile != null) {
			putCustomer(cacheKey, tmpMainProfile);
		}

		return tmpMainProfile;
	}

	/**
	 * @param customerHelper
	 * @param sourceId
	 * @param cifNumber
	 * @param dbKey
	 * @param idNumber
	 * @return
	 * @throws Exception
	 */
	public final String searchCustomerMultiple(String sourceId, String cifNumber, String dbKey, String idNumber,
			String customerName) throws UnableToSearchException {
		return searchCustomerMultiple(sourceId, cifNumber, dbKey, customerName, idNumber, "", "", "", "1", "10", "1",
				"10");
	}

	// Main Profile Details
	public MainProfileDetails retrieveMainProfileDetail(EAIMessage msg) {
		return ((CustomerMessageBody) msg.getMsgBody()).getMainProfileDetails();
	}

	/**
	 * To retrieve Search Status .
	 * 
	 * For Search Status , refer to
	 * com.integrosys.cms.host.message.castor.eai.customer.CustomerDAO constant
	 * STAT_PROCESSING = "P"; STAT_SUCCESS = "S"; STAT_EXCEPTION = "E";
	 * 
	 * @param msgRefNo SID/EAI Message Reference No
	 * @return SearchHeader
	 * @throws SearchCustomerException
	 */
	public SearchHeader getSearchCustomerMultipleHeader(String msgRefNo) throws SearchCustomerException {

		SearchHeader searchHeader = (SearchHeader) getCustomerDao().retrieve(msgRefNo, SearchHeader.class);

		if (searchHeader == null) {
			throw new SearchCustomerException("not able to find search header with msg ref no [" + msgRefNo + "]");
		}

		return searchHeader;
	}

	/**
	 * @param msgRefNo
	 * @return
	 * @throws SearchCustomerException
	 */
	public SearchDetailResult[] getSearchCustomerMultipleResults(String msgRefNo) throws SearchCustomerException {

		Map parameters = new HashMap();
		parameters.put("SID", msgRefNo);

		List searchDetailResult = getCustomerDao().retrieveObjectListByParameters(parameters, SearchDetailResult.class);

		return (SearchDetailResult[]) searchDetailResult.toArray(new SearchDetailResult[0]);

	}

	public final class SearchCustomerException extends RuntimeException {
		public SearchCustomerException(String s) {
			super(s);
		}
	}

	/**
	 * @param sourceId
	 * @param cifNumber
	 * @param dbKey
	 * @param customerName Name
	 * @param idNumber
	 * @param idNumber2
	 * @param gender
	 * @param birthdate
	 * @param page
	 * @param pageSize
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public String searchCustomerMultiple(String sourceId, String cifNumber, String dbKey, String customerName,
			String idNumber, String idNumber2, String gender, String birthdate, String page, String pageSize,
			String start, String end) throws UnableToSearchException {
		try {
			// Debug Mode , Return Static Search ID
			boolean isDebug = PropertyManager.getBoolean("customer.search.debug", false);

			boolean isSearchCIFNoInternal = PropertyManager.getBoolean("customer.search.internal.cifno", true);

			if (StringUtils.isNotEmpty(cifNumber) && isSearchCIFNoInternal) {
				// Perform Internal CIFNo Search
			}
			else if (isDebug) {
				return "TEST10001";
			}

			String messageType = IEAIHeaderConstant.CUSTOMER_TYPE;
			String messageId = IEAIHeaderConstant.CUSTOMER_CU002;

			HashMap customerHeader = new HashMap();
			customerHeader.put(IEAIHeaderConstant.EAIHDR_SOURCE, IEAIHeaderConstant.GCMS_SOURCE_ID);
			customerHeader.put(IEAIHeaderConstant.EAIHDR_DEST, sourceId);

			// Put Message MsgRefNo
			// String msgRefNo=sourceId + EAIHeaderHelper.createEAISequence();

			// <Appl Fmt (3char)><YYYYMMDD><9 digit seqno>

			String msgRefNo = createMsgRefNo();

			customerHeader.put(IEAIHeaderConstant.EAIHDR_MESSAGE_REF_NO, msgRefNo);

			HashMap customerEnquiryMap = new HashMap();

			// GEMS POJO
			SearchEnquiry search = new SearchEnquiry();
			search.setCustomerName(customerName);
			search.setSourceId(sourceId);

			search.setCIFId(cifNumber);
			search.setDBKey(StringUtils.defaultString(dbKey));
			// search.setBirthDate(birthdate);
			search.setIDNumber(idNumber);
			search.setPage(page);
			search.setPageSize(pageSize);
			customerEnquiryMap.put(KEY, search);

			String xml = null;

			CustomerJdbcImpl dao = new CustomerJdbcImpl();

			// Search by CIFID , IF CifID Found , return result , else continue
			// search

			boolean resultFound = false;

			if (StringUtils.isNotEmpty(cifNumber) && StringUtils.isNotEmpty(sourceId) && isSearchCIFNoInternal) {
				resultFound = dao.performSearchByCIF(msgRefNo, cifNumber, sourceId);
			}

			// Search By ID , If ID Found return result , else continue search

			if (resultFound) {
			//	dao.insertSearch(msgRefNo, IEaiConstant.CUST_MULTILE, "", IEaiConstant.STAT_SUCCESS);
				return msgRefNo;
			}
			else {

				// Perform External Search
				try {
					xml = EAIMessenger.sendMessage(IMQConstant.FROM_GCMS, IMQConstant.INQUIRY_TYPE, messageType,
							messageId, customerHeader, customerEnquiryMap);
				}
				catch (Exception e) {
					logger.error("encounter error when sending message, type [" + messageType + "] id [" + messageId
							+ "].", e);
				}

			}

			// Take first 2000 chars only
			xml = StringUtils.left(StringUtils.defaultString(xml), 2000);

			// Log Search Header
			if ("[ERROR]".equals(StringUtils.left(xml, 5))) {
			//	dao.insertSearch(msgRefNo, IEaiConstant.CUST_MULTILE, xml, IEaiConstant.STAT_EXCEPTION);
			}
			else {
			//	dao.insertSearch(msgRefNo, IEaiConstant.CUST_MULTILE, xml);
			}
			// return MsgRefNo for UI
			return msgRefNo;
		}
		catch (Exception e) {
			logger.error("encounter error when search customer multiple", e);
			throw new UnableToSearchException(e.getMessage());
		}
	}

	/**
	 * Create EAI MsgRefNo
	 * 
	 * @return
	 * @throws Exception
	 */
	private String createMsgRefNo() throws Exception {
		// Prepare MsgRefNo according to EAI Format
		String seq = StringUtils.leftPad((new SequenceManager()).getSeqNum("SEARCH_SEQ", false), 9, "0");

		String msgRefNo = "GCM" + DateUtil.formatTime(DateUtil.getDate(), "yyyyMMdd") + seq;
		return msgRefNo;
	}

	/**
	 * Used for customer manual input, will fire inquiry customer message to
	 * update into GCMS. Message send asyn and not expecting direct reply.
	 * Customer polling required.
	 * 
	 * @param cifNumber
	 * @param sourceId
	 * @return messageRefNo / SID ( Refer to Table SCI_SEARCH )
	 * @throws UnableToSearchException
	 */
	public String getCustomerByCIFNumber(String cifNumber, String sourceId) throws UnableToSearchException {
		try {

			// Debug Mode , Return Static Search ID
			boolean isDebug = PropertyManager.getBoolean("customer.search.debug", false);

			if (isDebug) {
				return "TEST10002";
			}

			String messageType = IEAIHeaderConstant.CUSTOMER_TYPE;
			// String messageId = IEAIHeaderConstant.CUSTOMER_CU002;
			String messageId = IEAIHeaderConstant.CUSTOMER_CU001;

			HashMap customerHeader = new HashMap();
			customerHeader.put(IEAIHeaderConstant.EAIHDR_SOURCE, IEAIHeaderConstant.GCMS_SOURCE_ID);
			customerHeader.put(IEAIHeaderConstant.EAIHDR_DEST, sourceId);
			HashMap customerEnquiryMap = new HashMap();
			if(cifNumber!=null)
			customerEnquiryMap.put(CIF_ID, new String(cifNumber));

			String msgRefNo = createMsgRefNo();

			customerHeader.put(IEAIHeaderConstant.EAIHDR_MESSAGE_REF_NO, msgRefNo);

			CustomerJdbcImpl dao = new CustomerJdbcImpl();
			try {

				String xml = EAIMessenger.sendMessage(IMQConstant.FROM_GCMS, IMQConstant.INQUIRY_TYPE, messageType,
						messageId, customerHeader, customerEnquiryMap);

				// Take first 2000 chars only
				xml = StringUtils.left(StringUtils.defaultString(xml), 2000);

				if ("[ERROR]".equals(StringUtils.left(xml, 7))) {
					// Insert Search Header with Exception Status
				//	dao.insertSearch(msgRefNo, IEaiConstant.CUST_SINGLE, xml, IEaiConstant.STAT_EXCEPTION);
				}
				else {
					// Insert Search Header with Processing Status
				//	dao.insertSearch(msgRefNo, IEaiConstant.CUST_SINGLE, xml);
				}
				logger.debug(" ** Send Message MsgRefNo : " + msgRefNo);

			}
			catch (Throwable e) {
				e.printStackTrace();
				//dao.insertSearch(msgRefNo, IEaiConstant.CUST_SINGLE, e.getMessage(), IEaiConstant.STAT_EXCEPTION);

				// dao.updateSearch(msgRefNo, 0l, CustomerDAO.STAT_EXCEPTION, e
				// .getMessage(), "");
			}

			return msgRefNo;
		}
		catch (Exception e) {
			logger.error("encounter error when getting customer by CIF Id [" + cifNumber + "] and source [" + sourceId
					+ "]", e);
			throw new UnableToSearchException(e.getMessage());
		}
	}

	/**
	 * @deprecated user getMainProfileOrCreateDummy instead
	 * @param cdb
	 * @param cifId
	 * @param cifSource
	 * @param pendingName
	 * @return
	 * @throws Exception
	 */
	public MainProfile getMainProfileOrCreateDummyOld(String cifId, String cifSource, String pendingName)
			throws Exception {

		Map parameters = new HashMap();
		parameters.put("CIFId", cifId);
		parameters.put("source", cifSource);

		MainProfile tmpmp = (MainProfile) getCustomerDao().retrieveNonDeletedObjectByParameters(parameters,
				"updateStatusIndicator", MainProfile.class);

		if (tmpmp != null) {
			return tmpmp;
		}
		else {
			logger.debug("Customer not found ! Trying to retrieve from source later.");
			logger.debug("Creating dummy date for customer with CIF id [" + cifId + "] and CIF source [" + cifSource
					+ "]");

			OBCMSCustomerTrxValue trxValue = new OBCMSCustomerTrxValue();
			SubProfile subProfile = new SubProfile();
			MainProfile mp = new MainProfile();

			// mp.setCmsId(Long.parseLong(seq));
			mp.setCustomerNameLong("Pending update from CIF Source.");
			mp.setCustomerNameShort("Pending update from CIF Source.");
			mp.setIncorporatedCountry("");
			mp.setSource(cifSource);
			mp.setCIFId(cifId);

			// M = Dummyew 
			mp.setChangeIndicator(new Character( IEaiConstant.DUMMYINDICATOR));
			mp.setUpdateStatusIndicator(new Character( IEaiConstant.CREATEINDICATOR));

			Long mpId = (Long) getCustomerDao().store(mp, MainProfile.class);
			mp.setCmsId(mpId.longValue());

			mp = (MainProfile) AccessorUtil.deepClone(mp);

			// Store sub profile

			subProfile.setCifId(mp.getCIFId());
			subProfile.setSubProfileName(mp.getCustomerNameShort());
			subProfile.setCmsMainProfileId(mp.getCmsId());
			subProfile.setSubProfileId(Long.parseLong(IEaiConstant.SUBPROFILE_ID));
			subProfile.setUpdateStatusIndicator(new Character( IEaiConstant.CREATEINDICATOR));
			subProfile.setNonBorrowerIndicator(ICMSConstant.TRUE_VALUE.charAt(0));

			Long spId = (Long) getCustomerDao().store(subProfile, SubProfile.class);

			mp.setSubProfilePrimaryKey(spId.longValue());

			getCustomerDao().update(mp, MainProfile.class);

			subProfile = (SubProfile) AccessorUtil.deepClone(subProfile);

			ICMSCustomer customerOB = CustomerProxyFactory.getProxy().getCustomerByCIFSourceFromDB(cifId, cifSource);

			trxValue.setStagingCustomer(customerOB);
			trxValue.setCustomer(customerOB);

			logger.debug("Now requesting customer data with cif id [ " + cifId + " ] and cif source [ " + cifSource
					+ " ].");

			CustomerProxyFactory.getProxy().createCustomer(trxValue);

			EAICustomerHelper.getInstance().getCustomerByCIFNumber(cifId, cifSource);

			return mp;
		}
	}

	/**
	 * NON Borrower Indicator set to Y
	 * 
	 * @param cdb
	 * @param cifId
	 * @param cifSource
	 * @param pendingName
	 * @return
	 * @throws Exception
	 */
	public MainProfile getMainProfileOrCreateDummy(String cifId, String cifSource, MainProfile mirrorMainProfile)
			throws Exception {
		return getMainProfileOrCreateDummy(cifId, cifSource, mirrorMainProfile, false);
	}

	/**
	 * To Retrieve MainProfile from Database or Create a Dummy One + Send out SI
	 * Enquiry
	 * 
	 * The Dummy Records created is keeping Customer Name
	 * 
	 * @param cdb
	 * @param cifId
	 * @param cifSource
	 * @param pendingName
	 * @param isBorrower to Set NON Borrower Indicator
	 * @return
	 * @throws Exception
	 */
	public MainProfile getMainProfileOrCreateDummy(String cifId, String cifSource, MainProfile mirrorMainProfile,
			boolean isBorrower) {
		String pendingName = null;

		if (mirrorMainProfile != null) {
			pendingName = mirrorMainProfile.getCustomerNameShort();
		}
		logger.debug("** getMainProfileOrCreateDummy, cifId [" + cifId + "] cifSource [" + cifSource
				+ "] pendingName [ " + pendingName + "]");

		if (StringUtils.isEmpty(pendingName)) {
			pendingName = "Pending update from CIF Source.";
		}

		Map parameters = new HashMap();

		parameters.put("CIFId", cifId);
		parameters.put("source", cifSource);

		MainProfile tmpmp = (MainProfile) getCustomerDao().retrieveNonDeletedObjectByParameters(parameters,
				"updateStatusIndicator", MainProfile.class);

		// 1. Customer Found
		// - Retrieve SubProfile

		if (tmpmp != null) {

			logger.debug("** MainProfile Found  -Return ** [" + tmpmp.getCmsId() + "]");

			// Retrieve SubProfile Data
			parameters.clear();
			parameters.put("cmsMainProfileId", new Long(tmpmp.getCmsId()));

			SubProfile tmpsb = (SubProfile) getCustomerDao().retrieveObjectByParameters(parameters, SubProfile.class);

			// Set SubProfile Primary Key
			tmpmp.setSubProfilePrimaryKey(tmpsb.getCmsId());

			// Update Borrower Indicator
			if (isBorrower) {
				tmpsb.setNonBorrowerIndicator(ICMSConstant.FALSE_VALUE.charAt(0));
			}
			else {
				// do nothing
			}

			if (tmpsb == null) {
				throw new IllegalStateException("not able to find customer CIF id [" + tmpmp.getCIFId() + "]");
			}

			getCustomerDao().update(tmpsb, SubProfile.class);
			getCustomerDao().update(tmpmp, MainProfile.class);

			return tmpmp;
		}
		else {
			// 2. Customer Not Found , Send CU001 and Create Dummy Records

			logger.debug("**[ Customer not found ! Trying to retrieve from source later. ]**");
			logger.debug("Creating dummy record for customer with CIF id [" + cifId + "] and CIF source [" + cifSource
					+ "]");

			MainProfile mp = createDummyCustomerRecord(cifId, cifSource, mirrorMainProfile, isBorrower, pendingName);

			// Step 2 . create External Message
			String msgRefNo = triggerExternalCustomerDetailSI(cifId, cifSource);

			mp.setSciSearchId(msgRefNo);

			return mp;
		}

	}

	/**
	 * @param cifId
	 * @param cifSource
	 * @return
	 * @throws UnableToSearchException
	 */
	public String triggerExternalCustomerDetailSI(String cifId, String cifSource) throws UnableToSearchException {

		EventMonitorClient.main(new String[] { CustomerSIMonitor.class.getName(), cifId, cifSource });
		String msgRefNo = "";

		return msgRefNo;
	}

	/**
	 * Create Customer Records in Background
	 * 
	 * @author allen
	 * 
	 */
	public class CustomerSIMonitor extends com.integrosys.cms.app.eventmonitor.AbstractMonitorAdapter {

		public void start(String[] params, SessionContext context) throws EventMonitorException {

			super.start(params, context);

			String cifId = params[0];
			String cifSource = params[1];

			logger.debug("CustomerSIMonitor - Params Length [" + params.length + "] cifId [" + cifId + "] cifSource ["
					+ cifSource + "]");

			BatchJobTrxUtil trxUtil = new BatchJobTrxUtil(context);
			try {
				trxUtil.beginUserTrx();
				trxUtil.commitUserTrx();
			}
			catch (Exception e) {
				logger.error("encounter error when begin user trx and commit user trx", e);
				trxUtil.rollbackUserTrx();
			}
			finally {

			}
		}

	}

	/**
	 * @param cdb
	 * @param cifId
	 * @param cifSource
	 * @param mirrorMainProfile
	 * @param isBorrower
	 * @param pendingName
	 * @return
	 */
	public synchronized MainProfile createDummyCustomerRecord(String cifId, String cifSource,
			MainProfile mirrorMainProfile, boolean isBorrower, String pendingName) {
		// Step 1 . Create Dummy Main Profile

		if (StringUtils.isEmpty(pendingName)) {
			pendingName = "Pending update from CIF Source.";
		}

		MainProfile mp = new MainProfile();

		// Copy Value from
		if (mirrorMainProfile != null) {
			AccessorUtil.copyValue(mirrorMainProfile, mp);
		}

		SubProfile subProfile = new SubProfile();

		// mp.setCmsId(Long.parseLong(seq));
		mp.setCustomerNameLong(pendingName);
		mp.setCustomerNameShort(pendingName);

		if (mp.getIncorporatedCountry() == null) {
			mp.setIncorporatedCountry("");
		}

		mp.setSource(cifSource);
		mp.setCIFId(cifId);
		mp.setChangeIndicator(new Character( IEaiConstant.DUMMYINDICATOR));
		mp.setUpdateStatusIndicator(new Character( IEaiConstant.CREATEINDICATOR));

		Long mpId = (Long) getCustomerDao().store(mp, MainProfile.class);

		// Store sub profile

		subProfile.setCifId(mp.getCIFId());
		subProfile.setSubProfileName(mp.getCustomerNameShort());
		subProfile.setCmsMainProfileId(mpId.longValue());
		subProfile.setSubProfileId(Long.parseLong(IEaiConstant.SUBPROFILE_ID));
		subProfile.setUpdateStatusIndicator(new Character (IEaiConstant.CREATEINDICATOR));
		subProfile.setChangeIndicator(new Character(IEaiConstant.DUMMYINDICATOR));

		if (isBorrower) {
			subProfile.setNonBorrowerIndicator(ICMSConstant.FALSE_VALUE.charAt(0));
		}
		else {
			subProfile.setNonBorrowerIndicator(ICMSConstant.TRUE_VALUE.charAt(0));
		}

		Long spId = (Long) getCustomerDao().store(subProfile, SubProfile.class);

		try {

			ICMSCustomer customerOB = CustomerProxyFactory.getProxy().getCustomer(subProfile.getCmsId());

			OBCMSCustomerTrxValue trxValue = new OBCMSCustomerTrxValue();

			// Set Keys
			trxValue.setCustomerID(mp.getSubProfilePrimaryKey());
			trxValue.setLegalID("" + mp.getCmsId());
			trxValue.setLegalName(mp.getCustomerNameShort());
			trxValue.setCustomerName(mp.getCustomerNameLong());

			trxValue.setCustomer(customerOB);
			trxValue.setStagingCustomer(customerOB);

			logger.debug("** Create Customer Staging / Transaction Records ** ");

			CustomerProxyFactory.getProxy().createCustomer(trxValue);
			// ut.commit();

		}
		catch (Throwable t) {
			logger.error("encounter error when getting and creating customer by workflow engine, CIF Id [" + cifId
					+ "]", t);
		}

		// ---------------------------------------------------------------------

		mp.setSubProfilePrimaryKey(spId.longValue());
		mp.setCmsId(mpId.longValue());

		getCustomerDao().update(mp, MainProfile.class);

		try {
			subProfile = (SubProfile) AccessorUtil.deepClone(subProfile);
			mp = (MainProfile) AccessorUtil.deepClone(mp);
		}
		catch (Throwable t) {
			logger.error("encounter error when cloning the object", t);
			IllegalStateException isex = new IllegalStateException("encounter error when cloning the object"
					+ t.getMessage());
			isex.initCause(t);

			throw isex;
		}

		return mp;
	}

	/**
	 * Unable to perform Search Exception
	 * 
	 * @author allen
	 * 
	 */
	public class UnableToSearchException extends RuntimeException {
		public UnableToSearchException(String message) {
			super(message);
		}
	}

}
