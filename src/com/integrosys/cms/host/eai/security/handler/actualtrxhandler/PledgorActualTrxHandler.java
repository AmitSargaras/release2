package com.integrosys.cms.host.eai.security.handler.actualtrxhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.customer.NoSuchCustomerException;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;
import com.integrosys.cms.host.eai.customer.bus.SubProfile;
import com.integrosys.cms.host.eai.security.PledgorActualHelper;
import com.integrosys.cms.host.eai.security.SCISecurityHelper;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.ISecurityDao;
import com.integrosys.cms.host.eai.security.bus.Pledgor;
import com.integrosys.cms.host.eai.security.bus.PledgorCreditGrade;
import com.integrosys.cms.host.eai.security.bus.SecurityPledgorMap;
import com.integrosys.cms.host.eai.security.bus.SecurityValuation;
import com.integrosys.cms.host.eai.security.bus.StagePledgor;
import com.integrosys.cms.host.eai.security.bus.StageSecurityPledgorMap;
import com.integrosys.cms.host.eai.support.EAIMessageSynchronizationManager;
import com.integrosys.cms.host.eai.support.VariationPropertiesKey;

/**
 * @author Chong Jun Yong
 * @since 1.0
 */
public class PledgorActualTrxHandler extends AbstractCommonActualTrxHandler {
	private PledgorActualHelper helper = new PledgorActualHelper();

	private SCISecurityHelper securityHelper = SCISecurityHelper.getInstance();

	private String[] PCG_EXCLUDE_METHOD = new String[] { "getCmsId", "getCmsPledgorId" };

	private ISecurityDao securityDao;

	private Map variationProperties;

	/**
	 * @return the variationProperties
	 */
	public Map getVariationProperties() {
		return variationProperties;
	}

	/**
	 * @param variationProperties the variationProperties to set
	 */
	public void setVariationProperties(Map variationProperties) {
		this.variationProperties = variationProperties;
	}

	public ISecurityDao getSecurityDao() {
		return securityDao;
	}

	public void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}

	public Message persistActualTrx(Message msg) {

		SecurityMessageBody secMessageBody = ((SecurityMessageBody) msg.getMsgBody());
		String publishType = msg.getMsgHeader().getPublishType();
		long cmsSecurityId = secMessageBody.getSecurityDetail().getCMSSecurityId();
		Vector vecP = secMessageBody.getPledgor();
		boolean isVariation = secMessageBody.isVariation();
		boolean isShareSecurity = securityHelper.isShareSecurity(secMessageBody.getSecurityDetail());

		// store PLedgor
		if (vecP == null) {
			vecP = new Vector();
		}

		if (!isShareSecurity) {
			vecP = storePledgor(vecP, publishType, cmsSecurityId, EAIMessageSynchronizationManager.getMessageSource(),
					isVariation);
		}
		secMessageBody.setPledgor(vecP);

		return msg;
	}

	public Message persistStagingTrx(Message msg, Object trxValue) {

		// There is no transaction for Pledgor in Manual input, thus this is
		// only staging table persistence
		SecurityMessageBody s1 = ((SecurityMessageBody) msg.getMsgBody());
		long cmsSecurityId = s1.getSecurityDetail().getCMSSecurityId();
		boolean isVariation = s1.isVariation();
		String losSecurityId = s1.getSecurityDetail().getLOSSecurityId();
		Vector vecP = null;

		boolean isShareSecurity = securityHelper.isShareSecurity(s1.getSecurityDetail());

		// vecP = s1.getStagePledgor();
		vecP = s1.getPledgor();
		// store PLedgor
		if (vecP == null) {
			vecP = new Vector();
		}

		// Only persist for pledgor if it is belongs to the not-shared security
		// (owner of the security)
		if (!isShareSecurity) {
			vecP = storeStagingPledgor(vecP, cmsSecurityId, losSecurityId, EAIMessageSynchronizationManager
					.getMessageSource(), isVariation);
		}
		s1.setStagePledgor(vecP);

		return msg;
	}

	/**
	 * To retrieve CIF Detail from CIF source , if - PledgorCIFSource Available
	 * - PledgorCIFID available
	 * 
	 * TODO: Reject the Security/Pledgor if PledgorCIF ID is not found
	 * 
	 * @param pldgr
	 */
	private void processPledgorCIF(Pledgor pldgr) {
		if (pldgr != null) {
			String cifId = pldgr.getPledgorId();
			String cifSource = pldgr.getPledgorCIFSource();
			if (StringUtils.isNotEmpty(cifId) && StringUtils.isNotEmpty(cifSource)) {

				Map parameters = new HashMap();
				parameters.put("CIFId", cifId);
				parameters.put("source", cifSource);

				MainProfile mp = (MainProfile) getSecurityDao().retrieveObjectByParameters(parameters,
						MainProfile.class);

				logger.info("pledgor CIF id [" + cifId + "], Source [" + cifSource + "], is mp found ? ["
						+ (mp != null) + "] ");
			}
		}
	}

	private void processPledgorLegalType(Pledgor pldgr) throws NoSuchCustomerException {
		if (pldgr != null) {
			String cifId = pldgr.getCIF();
			String cifSource = pldgr.getPledgorCIFSource();
			if (StringUtils.isNotEmpty(cifId) && StringUtils.isNotEmpty(cifSource)) {

				Map parameters = new HashMap();
				parameters.put("CIFId", cifId);
				parameters.put("source", cifSource);

				MainProfile mp = (MainProfile) getSecurityDao().retrieveObjectByParameters(parameters,
						MainProfile.class);

				if (mp == null) {
					throw new NoSuchCustomerException(cifId, cifSource);
				}

				pldgr.setLegalType(mp.getCustomerClass());

			}
		}
	}

	/**
	 * Store pledgor into persistence. For every pledgor, system need to
	 * implicitly create security pledgor linkage as source system is not
	 * sending the linkage.
	 * 
	 * @param vecP a list of Pledgor objects
	 * @param publishType
	 * @param cmsSecurityId
	 * @return a list of Pledgor objects
	 */
	private Vector storePledgor(Vector vecP, String publishType, long cmsSecurityId, String sourceId,
			boolean isVariation) {

		// remove existing linkage in SecurityPledgorMap

		Map parameters = new HashMap();
		parameters.put("CMSSecurityId", new Long(cmsSecurityId));

		List securityPledgorMapList = getSecurityDao().retrieveObjectsListByParameters(parameters,
				SecurityPledgorMap.class);

		for (Iterator itr = securityPledgorMapList.iterator(); itr.hasNext();) {
			SecurityPledgorMap securityPledgorMap = (SecurityPledgorMap) itr.next();
			getSecurityDao().remove(securityPledgorMap, SecurityPledgorMap.class);
		}

		for (int i = 0; i < vecP.size(); i++) {
			Pledgor pldgr = new Pledgor();
			pldgr = (Pledgor) vecP.elementAt(i);

			// To retrieve CIF from CIF System if Pledgor CIFSource and CIFno
			// are available .
			processPledgorCIF(pldgr);

			if (IEaiConstant.REPUBLISH_INDICATOR.equals(publishType)) {
				pldgr.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
				pldgr.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));
			}

			if (isPledgorChanged(pldgr)) // True for republish
			{
				// store Pledgor
				Pledgor tmpPledgor = null;

				tmpPledgor = getPledgorForProcessing(pldgr, isVariation);

				if (tmpPledgor == null) {
					tmpPledgor = loadProfileInfo(pldgr);

					pldgr.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));
					pldgr.setSourceID(sourceId);
					processPledgorLegalType(pldgr);
					getSecurityDao().store(pldgr, Pledgor.class);
				}
				else {
					if (!isDeletePledgor(pldgr)) {
						pldgr.setUpdateStatusIndicator(String.valueOf(UPDATEINDICATOR));
					}
					else {
						pldgr.setUpdateStatusIndicator(String.valueOf(DELETEINDICATOR));
					}

					if (isVariation) {
						List copyingProperties = (List) getVariationProperties().get(
								new VariationPropertiesKey(sourceId, Pledgor.class.getName()));
						helper.copyVariationProperties(pldgr, tmpPledgor, copyingProperties);
					}
					else {
						AccessorUtil.copyValue(pldgr, tmpPledgor, new String[] { IEaiConstant.CMSID });
					}
					tmpPledgor.setSourceID(sourceId);
					processPledgorLegalType(tmpPledgor);
					getSecurityDao().update(tmpPledgor, Pledgor.class);

					try {
						pldgr = (Pledgor) AccessorUtil.deepClone(tmpPledgor);
					}
					catch (Throwable t) {
						IllegalStateException isex = new IllegalStateException("encounter error " + t.getMessage()
								+ ", error cloning tmpCreditGrade, pledgor CIF id [" + tmpPledgor.getPledgorId() + "]");
						isex.initCause(t);

						throw isex;
					}
				}

				// store SecurityPledgorMap
				if (!isDeletePledgor(pldgr)) {
					// Load linkage if already there, else create
					SecurityPledgorMap tmpSecurityPledgorMap = null;
					parameters.clear();
					parameters.put("CMSSecurityId", new Long(cmsSecurityId));
					parameters.put("CMSPledgorId", new Long(pldgr.getCmsId()));
					parameters.put("pledgorRelTypeValue", pldgr.getPledgorRelTypeValue());

					tmpSecurityPledgorMap = (SecurityPledgorMap) getSecurityDao().retrieveObjectByParameters(
							parameters, SecurityPledgorMap.class);

					if (tmpSecurityPledgorMap == null) {
						tmpSecurityPledgorMap = new SecurityPledgorMap();
						tmpSecurityPledgorMap.setCMSSecurityId(new Long(cmsSecurityId));
						tmpSecurityPledgorMap.setCMSPledgorId(new Long(pldgr.getCmsId()));
						tmpSecurityPledgorMap.setPledgorRelTypeNumber(pldgr.getRelationship().getStandardCodeNumber());
						tmpSecurityPledgorMap.setPledgorRelTypeValue(pldgr.getRelationship().getStandardCodeValue());
						tmpSecurityPledgorMap.setChangeIndicator(String.valueOf(CHANGEINDICATOR));
						tmpSecurityPledgorMap.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));

						Long id = (Long) getSecurityDao().store(tmpSecurityPledgorMap, SecurityPledgorMap.class);
						tmpSecurityPledgorMap.setSecurityPledgorMapId(id);
						getSecurityDao().update(tmpSecurityPledgorMap, SecurityPledgorMap.class);

					}
					else {
						tmpSecurityPledgorMap.setPledgorRelTypeValue(pldgr.getPledgorRelTypeValue());
						tmpSecurityPledgorMap.setUpdateStatusIndicator("" + UPDATEINDICATOR);

						getSecurityDao().update(tmpSecurityPledgorMap, SecurityPledgorMap.class);
					}

				}
				else {
					// Delete action
					SecurityPledgorMap tmpSecurityPledgorMap = null;

					parameters.clear();
					parameters.put("CMSSecurityId", new Long(cmsSecurityId));
					parameters.put("CMSPledgorId", new Long(pldgr.getCmsId()));
					parameters.put("pledgorRelTypeValue", pldgr.getPledgorRelTypeValue());

					tmpSecurityPledgorMap = (SecurityPledgorMap) getSecurityDao().retrieveObjectByParameters(
							parameters, SecurityPledgorMap.class);

					if (tmpSecurityPledgorMap == null) {
						logger.warn("Not deleting linkage, linkage is not found, for Pledgor CIF id ["
								+ pldgr.getPledgorId() + "]");
					}
					else {
						tmpSecurityPledgorMap.setPledgorRelTypeValue(pldgr.getPledgorRelTypeValue());
						tmpSecurityPledgorMap.setUpdateStatusIndicator("" + DELETEINDICATOR);

						getSecurityDao().update(tmpSecurityPledgorMap, SecurityPledgorMap.class);
					}

				}

				Vector pcg = pldgr.getCreditGrade();
				if (pcg == null) {
					pcg = new Vector();
				}
				pcg = storePledgorCreditGrade(pcg, pldgr, publishType, isVariation);
				pldgr.setCreditGrade(pcg);

			}

			vecP.setElementAt(pldgr, i);
		}
		return vecP;
	}

	/**
	 * Store staging pledgor into persistence.
	 * 
	 * @param cdb of type CastorDb
	 * @param vecP a list of Pledgor objects
	 * @param cmsSecurityId long
	 * @return a list of Pledgor objects
	 */
	protected Vector storeStagingPledgor(Vector vecP, long cmsSecurityId, String losSecurityID, String sourceId,
			boolean isVariation) {

		// remove existing linkage in StageSecurityPledgorMap

		HashMap parameters = new HashMap();
		parameters.put("CMSSecurityId", new Long(cmsSecurityId));

		List stageSecurityPledgorMapList = getSecurityDao().retrieveObjectsListByParameters(parameters,
				StageSecurityPledgorMap.class);

		for (Iterator itr = stageSecurityPledgorMapList.iterator(); itr.hasNext();) {
			StageSecurityPledgorMap stgSecurityPledgorMap = (StageSecurityPledgorMap) itr.next();
			getSecurityDao().remove(stgSecurityPledgorMap, StageSecurityPledgorMap.class);
		}

		for (int i = 0; i < vecP.size(); i++) {

			StagePledgor stagingPledgor = new StagePledgor();
			Pledgor actualPledgor = (Pledgor) vecP.elementAt(i);
			AccessorUtil.copyValue((Pledgor) vecP.elementAt(i), stagingPledgor);
			// DefaultLogger.debug(this,
			// "stagingPledgor.getPledgorRelTypeValue() ****** " + i + " ~ " +
			// stagingPledgor.getPledgorRelTypeValue());

			if (isPledgorChanged(stagingPledgor)) // True for republish
			{

				Vector pcg = stagingPledgor.getCreditGrade();
				Pledgor tmpPledgor = null;

				tmpPledgor = loadPledgor(stagingPledgor, isVariation);

				if (tmpPledgor == null) {

					stagingPledgor.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));
					stagingPledgor.setSourceID(sourceId);
					processPledgorLegalType(stagingPledgor);
					getSecurityDao().store(stagingPledgor, StagePledgor.class);
				}
				else {
					// Update staging pledgor
					stagingPledgor.setUpdateStatusIndicator(String.valueOf(UPDATEINDICATOR));
					if (isVariation) {
						List copyingProperties = (List) getVariationProperties().get(
								new VariationPropertiesKey(sourceId, SecurityValuation.class.getName()));
						helper.copyVariationProperties(stagingPledgor, tmpPledgor, copyingProperties);
					}
					else
						AccessorUtil.copyValue(stagingPledgor, tmpPledgor, new String[] { IEaiConstant.CMSID });

					tmpPledgor.setSourceID(sourceId);
					processPledgorLegalType(tmpPledgor);
					getSecurityDao().update(tmpPledgor, StagePledgor.class);

					stagingPledgor.setCmsId(tmpPledgor.getCmsId());
					try {
						stagingPledgor = (StagePledgor) AccessorUtil.deepClone(tmpPledgor);
					}
					catch (Throwable t) {
						IllegalStateException isex = new IllegalStateException("encounter error " + t.getMessage()
								+ ", error cloning tmpCreditGrade, pledgor CIF id [" + tmpPledgor.getPledgorId() + "]");
						isex.initCause(t);

						throw isex;
					}
				}

				if (!isDeletePledgor(stagingPledgor)) {
					// Load linkage if already there, else create
					StageSecurityPledgorMap tmpSecurityPledgorMap = null;
					// Long[] stageCompoundkey = new Long[] { new
					// Long(cmsSecurityId), new
					// Long(stagingPledgor.getCmsId()) };
					// tmpSecurityPledgorMap = (StageSecurityPledgorMap)
					// cdb.load(StageSecurityPledgorMap.class,
					// stageCompoundkey);

					parameters.clear();
					parameters.put("CMSSecurityId", new Long(cmsSecurityId));
					parameters.put("CMSPledgorId", new Long(stagingPledgor.getCmsId()));
					parameters.put("pledgorRelTypeValue", stagingPledgor.getPledgorRelTypeValue());

					tmpSecurityPledgorMap = (StageSecurityPledgorMap) getSecurityDao().retrieveObjectByParameters(
							parameters, StageSecurityPledgorMap.class);

					if (tmpSecurityPledgorMap == null) {
						// String mapSeq = (new
						// SequenceManager()).getSeqNum(ICMSConstant
						// .SEQUENCE_PLEDGOR_MAP, true);
						tmpSecurityPledgorMap = new StageSecurityPledgorMap();
						// tmpSecurityPledgorMap.setCMSSecurityPledgorMapId(Long.
						// parseLong(mapSeq));
						tmpSecurityPledgorMap.setCMSSecurityId(new Long(cmsSecurityId));
						tmpSecurityPledgorMap.setCMSPledgorId(new Long(stagingPledgor.getCmsId()));
						tmpSecurityPledgorMap.setPledgorRelTypeValue(stagingPledgor.getPledgorRelTypeValue());
						tmpSecurityPledgorMap.setSecurityPledgorMapId(getActSecurityPledgorMapId(losSecurityID,
								actualPledgor.getCmsId()));
						tmpSecurityPledgorMap.setChangeIndicator("" + CHANGEINDICATOR);
						tmpSecurityPledgorMap.setUpdateStatusIndicator("" + CREATEINDICATOR);

						getSecurityDao().store(tmpSecurityPledgorMap, StageSecurityPledgorMap.class);
					}
					else {
						tmpSecurityPledgorMap.setPledgorRelTypeValue(stagingPledgor.getPledgorRelTypeValue());
						tmpSecurityPledgorMap.setUpdateStatusIndicator("" + UPDATEINDICATOR);

						getSecurityDao().update(tmpSecurityPledgorMap, tmpSecurityPledgorMap.getClass());
					}

				}
				else {
					// delete action
					StageSecurityPledgorMap tmpSecurityPledgorMap = null;

					parameters.clear();
					parameters.put("CMSSecurityId", new Long(cmsSecurityId));
					parameters.put("CMSPledgorId", new Long(stagingPledgor.getCmsId()));
					parameters.put("pledgorRelTypeValue", stagingPledgor.getPledgorRelTypeValue());

					tmpSecurityPledgorMap = (StageSecurityPledgorMap) getSecurityDao().retrieveObjectByParameters(
							parameters, StageSecurityPledgorMap.class);

					if (tmpSecurityPledgorMap == null) {
						logger.warn("Not deleting linkage, linkage is not found for pledgor CIF id ["
								+ stagingPledgor.getPledgorId() + "]");
					}
					else {
						tmpSecurityPledgorMap.setPledgorRelTypeValue(stagingPledgor.getPledgorRelTypeValue());
						tmpSecurityPledgorMap.setUpdateStatusIndicator("" + DELETEINDICATOR);

						getSecurityDao().update(tmpSecurityPledgorMap, tmpSecurityPledgorMap.getClass());
					}
				}

				stagingPledgor.setCreditGrade(pcg);
			}
			vecP.setElementAt(stagingPledgor, i);
		}
		return vecP;
	}

	/**
	 * Store pledgor credit grades into persistence using JDO mapping.
	 * 
	 * @param cdb of CastorDb
	 * @param vecPCG a list of PledgorCreditGrade objects
	 * @param publishType
	 * @return a list of PledgorCreditGrade objects
	 */
	public Vector storePledgorCreditGrade(Vector vecPCG, Pledgor pledgor, String publishType, boolean isVariation) {

		for (int i = 0; i < vecPCG.size(); i++) {
			logger.debug("*****" + vecPCG.elementAt(i).getClass());
			PledgorCreditGrade pcg = (PledgorCreditGrade) vecPCG.elementAt(i);

			if (IEaiConstant.REPUBLISH_INDICATOR.equals(publishType)) {
				pcg.setChangeIndicator(String.valueOf(CHANGEINDICATOR)); // Default
				// change
				// indicator to 'Y'
				pcg.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR)); // Default
				// update status
				// indicator to
				// 'I'
			}

			if (isPledgorCreditGradeChanged(pcg)) {
				pcg.setCmsPledgorId(pledgor.getCmsId());
				PledgorCreditGrade tmpCreditGrade = getPledgorCreditGradeForProcessing(pcg);

				if (tmpCreditGrade == null) {
					// String seq = (new
					// SequenceManager()).getSeqNum(ICMSConstant
					// .SEQUENCE_PLEDGOR_CREDIT_GRADE, true);

					if (pledgor.getCmsId() == ICMSConstant.LONG_INVALID_VALUE) {
						pledgor = loadPledgor(pledgor, isVariation);
					}
					// pcg.setCmsId(Long.parseLong(seq));
					pcg.setCmsPledgorId(pledgor.getCmsId());
					pcg.setUpdateStatusIndicator(String.valueOf(CREATEINDICATOR));
					logger.debug("storePledgorCreditGrade: CMS Pledgor credit grade id is : [" + pcg.getCmsId()
							+ "] CMS pledgor id: [" + pcg.getCmsPledgorId() + "]");

					getSecurityDao().store(pcg, pcg.getClass());

				}
				else {
					if (isDeletePledgorCreditGrade(pcg)) {
						pcg.setUpdateStatusIndicator(String.valueOf(DELETEINDICATOR));
					}
					else {
						pcg.setUpdateStatusIndicator(String.valueOf(UPDATEINDICATOR));
					}

					AccessorUtil.copyValue(pcg, tmpCreditGrade, PCG_EXCLUDE_METHOD);

					getSecurityDao().update(tmpCreditGrade, tmpCreditGrade.getClass());

					try {
						pcg = (PledgorCreditGrade) AccessorUtil.deepClone(tmpCreditGrade);
					}
					catch (Throwable t) {
						IllegalStateException isex = new IllegalStateException("IOException encounter "
								+ t.getMessage() + ", error cloning tmpCreditGrade, pledgor CIF id ["
								+ pledgor.getPledgorId() + "]");
						isex.initCause(t);

						throw isex;
					}
				}

				vecPCG.setElementAt(pcg, i);
			}
		}
		return vecPCG;
	}

	/**
	 * Get pledgor for processing.
	 * 
	 * @param cdb of type CastorDb
	 * @param pledgor of type Pledgor
	 * @return a pledgor
	 */
	protected Pledgor getPledgorForProcessing(Pledgor pledgor, boolean isVariation) {
		if (helper.isCreatePledgor(pledgor) || helper.isUpdatePledgor(pledgor)) {
			return loadPledgor(pledgor, isVariation);
		}
		else if (helper.isDeletePledgor(pledgor)) {
			return loadPledgor(pledgor, isVariation);
		}
		else {
			throw new IllegalStateException("Invalid Delete Indicator! Pledgor CIF id: [" + pledgor.getPledgorId()
					+ "] Indicator: [" + pledgor.getUpdateStatusIndicator() + "]");
		}
	}

	/**
	 * Load security pledgor from database.
	 * 
	 * @param cdb of type CastorDb
	 * @param pledgor of type Pledgor
	 * @return pledgor
	 */
	protected Pledgor loadPledgor(Pledgor pledgor, boolean isVariation) {

		if (isVariation && pledgor instanceof Pledgor) {
			Map parameters = new HashMap();
			parameters.put("cmsId", new Long(pledgor.getCMSPledgorId()));
			return (Pledgor) getSecurityDao().retrieveObjectByParameters(parameters, pledgor.getClass());
		}

		else if (!StringUtils.isEmpty(pledgor.getPledgorId()) && !StringUtils.isEmpty(pledgor.getPledgorCIFSource())) {
			Map parameters = new HashMap();
			parameters.put("pledgorId", pledgor.getPledgorId());
			parameters.put("pledgorCIFSource", pledgor.getPledgorCIFSource());

			return (Pledgor) getSecurityDao().retrieveObjectByParameters(parameters, pledgor.getClass());
		}
		else if (!StringUtils.isEmpty(pledgor.getCIF()) && !StringUtils.isEmpty(pledgor.getPledgorCIFSource())) {

			Map parameters = new HashMap();
			parameters.put("CIF", pledgor.getCIF());
			parameters.put("pledgorCIFSource", pledgor.getPledgorCIFSource());

			return (Pledgor) getSecurityDao().retrieveObjectByParameters(parameters, pledgor.getClass());
		}
		else if (!StringUtils.isEmpty(pledgor.getIncorporatedCountry())
				&& !StringUtils.isEmpty(pledgor.getIdType().getStandardCodeValue())
				&& !StringUtils.isEmpty(pledgor.getIncorporationNumber())
				&& !StringUtils.isEmpty(pledgor.getPledgorCIFSource())) {

			Map parameters = new HashMap();
			parameters.put("incorporatedCountry", pledgor.getIncorporatedCountry());
			parameters.put("idType.standardCodeValue", pledgor.getIdType().getStandardCodeValue());
			parameters.put("incorporationNumber", pledgor.getIncorporationNumber());
			parameters.put("pledgorCIFSource", pledgor.getPledgorCIFSource());

			return (Pledgor) getSecurityDao().retrieveObjectByParameters(parameters, pledgor.getClass());
		}
		else {
			return null;
		}
	}

	/**
	 * Check if the pledgor is changed.
	 * 
	 * @param pledgor of type Pledgor
	 * @return boolean
	 */
	protected boolean isPledgorChanged(Pledgor pledgor) {
		return helper.isPledgorChanged(pledgor);
	}

	/**
	 * Check if it is to delete the pledgor.
	 * 
	 * @param pledgor of type Pledgor
	 * @return boolean
	 */
	protected boolean isDeletePledgor(Pledgor pledgor) {
		return helper.isDeletePledgor(pledgor);
	}

	/**
	 * Get pledgor credit grade for processing.
	 * 
	 * @param cdb of type CastorDb
	 * @param creditGrade of type PledgorCreditGrade
	 * @return a pledgor credit grade
	 */
	protected PledgorCreditGrade getPledgorCreditGradeForProcessing(PledgorCreditGrade creditGrade) {
		if (helper.isCreatePledgorCreditGrade(creditGrade) || helper.isUpdatePledgorCreditGrade(creditGrade)) {
			return loadPledgorCreditGrade(creditGrade);
		}
		else if (isDeletePledgorCreditGrade(creditGrade)) {
			return loadPledgorCreditGrade(creditGrade);
		}
		else {
			throw new IllegalStateException("Invalid Delete Indicator! Pledgor  id: [" + creditGrade.getCmsPledgorId()
					+ "] credit grade type: [" + creditGrade.getCreditGradeType().getStandardCodeValue()
					+ "] Indicator: [" + creditGrade.getUpdateStatusIndicator() + "]");
		}
	}

	/**
	 * Load security pledgor credit grade from database.
	 * 
	 * @param cdb of type CastorDb
	 * @param creditGrade of type PledgorCreditGrade
	 * @return pledgor credit grade
	 */
	protected PledgorCreditGrade loadPledgorCreditGrade(PledgorCreditGrade creditGrade) {
		Map parameters = new HashMap();
		parameters.put("cmsPledgorId", new Long(creditGrade.getCmsPledgorId()));
		parameters.put("creditGradeType.standardCodeValue", creditGrade.getCreditGradeType().getStandardCodeValue());

		return (PledgorCreditGrade) getSecurityDao().retrieveObjectByParameters(parameters, creditGrade.getClass());
	}

	/**
	 * Check if the pledgor credit grade is changed.
	 * 
	 * @param creditGrade of type PledgorCreditGrade
	 * @return boolean
	 */
	protected boolean isPledgorCreditGradeChanged(PledgorCreditGrade creditGrade) {
		return helper.isPledgorCreditGradeChanged(creditGrade);
	}

	/**
	 * Check if it is to delete the pledgor credit grade.
	 * 
	 * @param creditGrade of type PledgorCreditGrade
	 * @return boolean
	 */
	protected boolean isDeletePledgorCreditGrade(PledgorCreditGrade creditGrade) {
		return helper.isDeletePledgorCreditGrade(creditGrade);
	}

	private Pledgor loadProfileInfo(Pledgor pledgor) {
		Map parameters = new HashMap();
		if (pledgor.getCIF() != null) {
			parameters.put("cifId", pledgor.getCIF());
			SubProfile sp = (SubProfile) getSecurityDao().retrieveObjectByParameters(parameters, SubProfile.class);
			if (sp != null) {
				pledgor.setOriginatingCountry(sp.getOriginatingCountry());

				pledgor.setOriginatingOrganisation(sp.getOriginatingOrganisation());
			}
		}

		return pledgor;

	}

	private Long getActSecurityPledgorMapId(String losSecurityId, long actualPledgorId) {
		long cmsSecuriyId;
		HashMap parameters = new HashMap();
		parameters.put("LOSSecurityId", losSecurityId);
		ApprovedSecurity security = (ApprovedSecurity) getSecurityDao().retrieveObjectByParameters(parameters,
				ApprovedSecurity.class);
		cmsSecuriyId = security.getCMSSecurityId();

		parameters.clear();
		parameters.put("CMSSecurityId", new Long(cmsSecuriyId));
		parameters.put("CMSPledgorId", new Long(actualPledgorId));

		SecurityPledgorMap map = (SecurityPledgorMap) getSecurityDao().retrieveObjectByParameters(parameters,
				SecurityPledgorMap.class);
		if (map != null) {
			return map.getSecurityPledgorMapId();
		}

		return null;
	}

}
