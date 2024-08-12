package com.integrosys.cms.batch.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.batch.eod.IEodSyncConstants;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: 2007-2-28 Time: 18:12:34 To
 * change this template use File | Settings | File Templates.
 */
public class BatchResourceFactory {

	private String FD_RECEIVE_FILE_DIR = "batch.mars.fd.receive.dir";

	private String FD_RECEIVE_FILE_NAME = "batch.mars.fd.receive.filename";

	private String FD_SEND_FILE_DIR = "batch.mars.fd.send.dir";

	private String FD_SEND_FILE_NAME = "batch.mars.fd.send.filename";

	private String FD_RESPONSE_FILE_DIR = "batch.mars.fd.response.dir";

	private String FD_RESPONSE_FILE_NAME = "batch.mars.fd.response.filename";

	private String OD_SEND_FILE_DIR = "batch.mars.od.send.dir";

	private String OD_SEND_FILE_NAME = "batch.mars.od.send.filename";

	private String TL_SEND_FILE_DIR = "batch.mars.tl.send.dir";

	private String TL_SEND_FILE_NAME = "batch.mars.tl.send.filename";

	private String OD_RECEIVE_FILE_DIR = "batch.mars.od.receive.dir";

	private String OD_RECEIVE_FILE_NAME = "batch.mars.od.receive.filename";

	private String OD_RESPONSE_FILE_DIR = "batch.mars.od.response.dir";

	private String OD_RESPONSE_FILE_NAME = "batch.mars.od.response.filename";

	private String TL_RECEIVE_FILE_DIR = "batch.mars.tl.receive.dir";

	private String TL_RECEIVE_FILE_NAME = "batch.mars.tl.receive.filename";

	private String TL_RESPONSE_FILE_DIR = "batch.mars.tl.response.dir";

	private String TL_RESPONSE_FILE_NAME = "batch.mars.tl.response.filename";

	private String NPL_RECEIVE_FILE_DIR = "batch.mars.npl.receive.dir";

	private String NPL_RECEIVE_FILE_NAME = "batch.mars.npl.receive.filename";

	private String NPL_RESPONSE_FILE_DIR = "batch.mars.npl.response.dir";

	private String NPL_RESPONSE_FILE_NAME = "batch.mars.npl.response.filename";

	private String NRV_SEND_FILE_DIR = "batch.mars.nrv.send.dir";

	private String NRV_SEND_FILE_NAME = "batch.mars.nrv.send.filename";

	private String NOMINEES_COUNTER_RECEIVE_FILE_DIR = "batch.nominess.counter.receive.dir";

	private String NOMINEES_COUNTER_RECEIVE_FILE_NAME = "batch.nominess.counter.receive.filename";

	private String NOMINEES_CUSTOMER_RECEIVE_FILE_DIR = "batch.nominess.customer.receive.dir";

	private String NOMINEES_CUSTOMER_RECEIVE_FILE_NAME = "batch.nominess.customer.receive.filename";

	private String Batch_FILE_BACKUP_DIR = "batch.backup.dir";

	private String PROBANK_CUSTOMER_RECEIVE_FILE_DIR = "batch.probank.customer.receive.dir";

	private String PROBANK_CUSTOMER_RECEIVE_FILE_NAME = "batch.probank.customer.receive.filename";

	private String PROBANK_NPL_RECEIVE_FILE_DIR = "batch.probank.npl.receive.dir";

	private String PROBANK_NPL_RECEIVE_FILE_NAME = "batch.probank.npl.receive.filename";

	private String PROBANK_ODANDTL_SEND_FILE_DIR = "batch.probank.odandtl.send.dir";

	private String PROBANK_ODANDTL_SEND_FILE_NAME = "batch.probank.odandtl.send.filename";

	private String PROBANK_OD_RECEIVE_FILE_DIR = "batch.probank.od.receive.dir";

	private String PROBANK_OD_RECEIVE_FILE_NAME = "batch.probank.od.receive.filename";

	private String PROBANK_TL_RECEIVE_FILE_DIR = "batch.probank.tl.receive.dir";

	private String PROBANK_TL_RECEIVE_FILE_NAME = "batch.probank.tl.receive.filename";

	private String SIBS_CPF_RECEIVE_FILE_DIR = "batch.sibs.cpf.receive.dir";

	private String SIBS_CPF_RECEIVE_FILE_NAME = "batch.sibs.cpf.receive.filename";

	private String SIBS_FD_RECEIVE_FILE_DIR = "batch.sibs.fd.receive.dir";

	private String SIBS_FD_RECEIVE_FILE_NAME = "batch.sibs.fd.receive.filename";

	private String SIBS_NPL_RECEIVE_FILE_DIR = "batch.sibs.npl.receive.dir";

	private String SIBS_NPL_RECEIVE_FILE_NAME = "batch.sibs.npl.receive.filename";

	private String SIBS_OD_RECEIVE_FILE_DIR = "batch.sibs.od.receive.dir";

	private String SIBS_OD_RECEIVE_FILE_NAME = "batch.sibs.od.receive.filename";

	private String SIBS_TL_RECEIVE_FILE_DIR = "batch.sibs.tl.receive.dir";

	private String SIBS_TL_RECEIVE_FILE_NAME = "batch.sibs.tl.receive.filename";

	private String MARSHA_COUNTER_RECEIVE_FILE_DIR = "batch.marsha.counter.receive.dir";

	private String MARSHA_COUNTER_RECEIVE_FILE_NAME = "batch.marsha.counter.receive.filename";

	private String MARSHA_CUSTOMER_RECEIVE_FILE_DIR = "batch.marsha.customer.receive.dir";

	private String MARSHA_CUSTOMER_RECEIVE_FILE_NAME = "batch.marsha.customer.receive.filename";

	private String HPMARS_NPL_RECEIVE_FILE_DIR = "batch.hpmars.npl.receive.dir";

	private String HPMARS_NPL_RECEIVE_FILE_NAME = "batch.hpmars.npl.receive.filename";

	private String HPMARS_TL_SEND_FILE_DIR = "batch.hpmars.tl.send.dir";

	private String HPMARS_TL_SEND_FILE_NAME = "batch.hpmars.tl.send.filename";

	private String HPMARS_TL_RECEIVE_FILE_DIR = "batch.hpmars.tl.receive.dir";

	private String HPMARS_TL_RECEIVE_FILE_NAME = "batch.hpmars.tl.receive.filename";

	private String HPMARS_TL_RESPONSE_FILE_DIR = "batch.hpmars.tl.response.dir";

	private String HPMARS_TL_RESPONSE_FILE_NAME = "batch.hpmars.tl.response.filename";

	private static final String BATCH_PROPERTY_FILE_DIR = "batch.property.dir";
	
	//Added by archana for template xmls
	private static final String UPLOAD_TEMPLATE_XML_DIR = "upload.template.xml.dir";
	
	private static final String APP_SERVER_TYPE = "clims.application.server.type";

	private static final String REF_CCY_CODE = "refCcy";

	private String getValueFromConfigFile(String fileName) {
		// default from ofa_env.properties
		return PropertyManager.getValue(fileName);
	}

	public String getFdReceiveFileName() {
		if ((getValueFromConfigFile(FD_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(FD_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(FD_RECEIVE_FILE_DIR) + getValueFromConfigFile(FD_RECEIVE_FILE_NAME);
		}
		return null;
	}

	public String getFdSendFileName() {
		if ((getValueFromConfigFile(FD_SEND_FILE_DIR) != null) && (getValueFromConfigFile(FD_SEND_FILE_NAME) != null)) {
			return getValueFromConfigFile(FD_SEND_FILE_DIR) + getValueFromConfigFile(FD_SEND_FILE_NAME);
		}

		return null;
	}

	public String getFdResponseFileName() {
		if ((getValueFromConfigFile(FD_RESPONSE_FILE_DIR) != null)
				&& (getValueFromConfigFile(FD_RESPONSE_FILE_NAME) != null)) {
			return getValueFromConfigFile(FD_RESPONSE_FILE_DIR) + getValueFromConfigFile(FD_RESPONSE_FILE_NAME);
		}
		return null;
	}

	public String getOdReceiveFileName() {
		if ((getValueFromConfigFile(OD_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(OD_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(OD_RECEIVE_FILE_DIR) + getValueFromConfigFile(OD_RECEIVE_FILE_NAME);
		}
		return null;
	}

	public String getTlReceiveFileName() {
		if ((getValueFromConfigFile(TL_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(TL_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(TL_RECEIVE_FILE_DIR) + getValueFromConfigFile(TL_RECEIVE_FILE_NAME);
		}
		return null;
	}

	public String getOdSendFileName() {
		if ((getValueFromConfigFile(OD_SEND_FILE_DIR) != null) && (getValueFromConfigFile(OD_SEND_FILE_NAME) != null)) {
			return getValueFromConfigFile(OD_SEND_FILE_DIR) + getValueFromConfigFile(OD_SEND_FILE_NAME);
		}
		return null;
	}

	public String getTlSendFileName() {
		if ((getValueFromConfigFile(TL_SEND_FILE_DIR) != null) && (getValueFromConfigFile(TL_SEND_FILE_NAME) != null)) {
			return getValueFromConfigFile(TL_SEND_FILE_DIR) + getValueFromConfigFile(TL_SEND_FILE_NAME);
		}
		return null;
	}

	public String getOdResponseFileName() {
		if ((getValueFromConfigFile(OD_RESPONSE_FILE_DIR) != null)
				&& (getValueFromConfigFile(OD_RESPONSE_FILE_NAME) != null)) {
			return getValueFromConfigFile(OD_RESPONSE_FILE_DIR) + getValueFromConfigFile(OD_RESPONSE_FILE_NAME);
		}
		return null;
	}

	public String getTlResponseFileName() {
		if ((getValueFromConfigFile(TL_RESPONSE_FILE_DIR) != null)
				&& (getValueFromConfigFile(TL_RESPONSE_FILE_NAME) != null)) {
			return getValueFromConfigFile(TL_RESPONSE_FILE_DIR) + getValueFromConfigFile(TL_RESPONSE_FILE_NAME);
		}
		return null;
	}

	public String getNplReceiveFileName() {
		if ((getValueFromConfigFile(NPL_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(NPL_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(NPL_RECEIVE_FILE_DIR) + getValueFromConfigFile(NPL_RECEIVE_FILE_NAME);
		}
		return null;
	}

	public String getNplResponseFileName() {
		if ((getValueFromConfigFile(NPL_RESPONSE_FILE_DIR) != null)
				&& (getValueFromConfigFile(NPL_RESPONSE_FILE_NAME) != null)) {
			return getValueFromConfigFile(NPL_RESPONSE_FILE_DIR) + getValueFromConfigFile(NPL_RESPONSE_FILE_NAME);
		}
		return null;
	}

	public String getNrvSendFileName() {
		if ((getValueFromConfigFile(NRV_SEND_FILE_DIR) != null) && (getValueFromConfigFile(NRV_SEND_FILE_NAME) != null)) {
			return getValueFromConfigFile(NRV_SEND_FILE_DIR) + getValueFromConfigFile(NRV_SEND_FILE_NAME);
		}

		return null;
	}

	public String getNomineesCounterLevelReceiveFileName() {
		if ((getValueFromConfigFile(NOMINEES_COUNTER_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(NOMINEES_COUNTER_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(NOMINEES_COUNTER_RECEIVE_FILE_DIR)
					+ getValueFromConfigFile(NOMINEES_COUNTER_RECEIVE_FILE_NAME);
		}
		return null;
	}

	public String getNomineesCustomerLevelReceiveFileName() {
		if ((getValueFromConfigFile(NOMINEES_CUSTOMER_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(NOMINEES_CUSTOMER_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(NOMINEES_CUSTOMER_RECEIVE_FILE_DIR)
					+ getValueFromConfigFile(NOMINEES_CUSTOMER_RECEIVE_FILE_NAME);
		}
		return null;
	}

	public String getBatchBackUpDir() {
		if (getValueFromConfigFile(Batch_FILE_BACKUP_DIR) != null) {
			String dateString = getDateString();
			String backUpDir = getValueFromConfigFile(Batch_FILE_BACKUP_DIR) + dateString;
			return backUpDir;
		}
		return null;

	}

	public String getProbankCustomerReceiveFileName() {
		if ((getValueFromConfigFile(PROBANK_CUSTOMER_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(PROBANK_CUSTOMER_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(PROBANK_CUSTOMER_RECEIVE_FILE_DIR)
					+ getValueFromConfigFile(PROBANK_CUSTOMER_RECEIVE_FILE_NAME);
		}
		return null;
	}

	public String getProbankNplReceiveFileName() {
		if ((getValueFromConfigFile(PROBANK_NPL_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(PROBANK_NPL_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(PROBANK_NPL_RECEIVE_FILE_DIR)
					+ getValueFromConfigFile(PROBANK_NPL_RECEIVE_FILE_NAME);

		}
		return null;

	}

	public String getProbankOdAndTlSendFileName() {
		if ((getValueFromConfigFile(PROBANK_ODANDTL_SEND_FILE_DIR) != null)
				&& (getValueFromConfigFile(PROBANK_ODANDTL_SEND_FILE_NAME) != null)) {
			return getValueFromConfigFile(PROBANK_ODANDTL_SEND_FILE_DIR)
					+ getValueFromConfigFile(PROBANK_ODANDTL_SEND_FILE_NAME);
		}
		return null;
	}

	public String getProbankOdReceiveFileName() {
		if ((getValueFromConfigFile(PROBANK_OD_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(PROBANK_OD_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(PROBANK_OD_RECEIVE_FILE_DIR)
					+ getValueFromConfigFile(PROBANK_OD_RECEIVE_FILE_NAME);
		}
		return null;

	}

	public String getProbankTlReceiveFileName() {
		if ((getValueFromConfigFile(PROBANK_TL_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(PROBANK_TL_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(PROBANK_TL_RECEIVE_FILE_DIR)
					+ getValueFromConfigFile(PROBANK_TL_RECEIVE_FILE_NAME);

		}
		return null;

	}

	public String getSibsCpfReceiveFileName() {
		if ((getValueFromConfigFile(SIBS_CPF_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(SIBS_CPF_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(SIBS_CPF_RECEIVE_FILE_DIR)
					+ getValueFromConfigFile(SIBS_CPF_RECEIVE_FILE_NAME);
		}
		return null;
	}

	public String getSibsFdReceiveFileName() {
		if ((getValueFromConfigFile(SIBS_FD_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(SIBS_FD_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(SIBS_FD_RECEIVE_FILE_DIR) + getValueFromConfigFile(SIBS_FD_RECEIVE_FILE_NAME);
		}
		return null;
	}

	public String getSibsNplReceiveFileName() {
		if ((getValueFromConfigFile(SIBS_NPL_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(SIBS_NPL_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(SIBS_NPL_RECEIVE_FILE_DIR)
					+ getValueFromConfigFile(SIBS_NPL_RECEIVE_FILE_NAME);
		}
		return null;
	}

	public String getSibsOdReceiveFileName() {
		if ((getValueFromConfigFile(SIBS_OD_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(SIBS_OD_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(SIBS_OD_RECEIVE_FILE_DIR) + getValueFromConfigFile(SIBS_OD_RECEIVE_FILE_NAME);
		}
		return null;
	}

	public String getSibsTlReceiveFileName() {
		if ((getValueFromConfigFile(SIBS_TL_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(SIBS_TL_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(SIBS_TL_RECEIVE_FILE_DIR) + getValueFromConfigFile(SIBS_TL_RECEIVE_FILE_NAME);
		}
		return null;
	}

	public String getMarshaCounterLevelReceiveFileName() {
		if ((getValueFromConfigFile(MARSHA_COUNTER_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(MARSHA_COUNTER_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(MARSHA_COUNTER_RECEIVE_FILE_DIR)
					+ getValueFromConfigFile(MARSHA_COUNTER_RECEIVE_FILE_NAME);
		}
		return null;
	}

	public String getMarshaCustomerLevelReceiveFileName() {
		if ((getValueFromConfigFile(MARSHA_CUSTOMER_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(MARSHA_CUSTOMER_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(MARSHA_CUSTOMER_RECEIVE_FILE_DIR)
					+ getValueFromConfigFile(MARSHA_CUSTOMER_RECEIVE_FILE_NAME);
		}
		return null;
	}

	public String getHpmarsNplReceiveFileName() {
		if ((getValueFromConfigFile(HPMARS_NPL_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(HPMARS_NPL_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(HPMARS_NPL_RECEIVE_FILE_DIR)
					+ getValueFromConfigFile(HPMARS_NPL_RECEIVE_FILE_NAME);
		}
		return null;
	}

	public String getHpmarsTlSendFileName() {
		if ((getValueFromConfigFile(HPMARS_TL_SEND_FILE_DIR) != null)
				&& (getValueFromConfigFile(HPMARS_TL_SEND_FILE_NAME) != null)) {
			return getValueFromConfigFile(HPMARS_TL_SEND_FILE_DIR) + getValueFromConfigFile(HPMARS_TL_SEND_FILE_NAME);
		}
		return null;
	}

	public String getHpmarsTlReceiveFileName() {
		if ((getValueFromConfigFile(HPMARS_TL_RECEIVE_FILE_DIR) != null)
				&& (getValueFromConfigFile(HPMARS_TL_RECEIVE_FILE_NAME) != null)) {
			return getValueFromConfigFile(HPMARS_TL_RECEIVE_FILE_DIR)
					+ getValueFromConfigFile(HPMARS_TL_RECEIVE_FILE_NAME);

		}
		return null;

	}

	public String getHpmarsTlResponseFileName() {
		if ((getValueFromConfigFile(HPMARS_TL_RESPONSE_FILE_DIR) != null)
				&& (getValueFromConfigFile(HPMARS_TL_RESPONSE_FILE_NAME) != null)) {
			return getValueFromConfigFile(HPMARS_TL_RESPONSE_FILE_DIR)
					+ getValueFromConfigFile(HPMARS_TL_RESPONSE_FILE_NAME);
		}
		return null;
	}

	public String getPropertyFileName(String fileName) {
		if (getValueFromConfigFile(BATCH_PROPERTY_FILE_DIR) != null) {
			return getValueFromConfigFile(BATCH_PROPERTY_FILE_DIR) + fileName;
		}
		return null;
	}

	public String getReferenceCurrencyCode() {
		if (getValueFromConfigFile(REF_CCY_CODE) != null) {
			return getValueFromConfigFile(REF_CCY_CODE);
		}
		return null;
	}

	private String getDateString() {
		SimpleDateFormat f1 = new SimpleDateFormat("yyyyMMdd");
		String dateString = f1.format(Calendar.getInstance().getTime());
		return dateString;
	}
	
	//Added by archana for template xmls
	public String getTemplateXmlFileName(String fileName) {
		if (getValueFromConfigFile(UPLOAD_TEMPLATE_XML_DIR) != null) {
			return getValueFromConfigFile(UPLOAD_TEMPLATE_XML_DIR) + fileName+"Template.xml";
		}
		return null;
	}
	
	public String getAppServerType()
	{
		if (getValueFromConfigFile(APP_SERVER_TYPE) != null) {
			return getValueFromConfigFile(APP_SERVER_TYPE);
		}
		return null;
	}
	//Added by Anil for template xmls
	public String getSyncMasterTemplateXmlFileName(String fileName) {
		if (getValueFromConfigFile(IEodSyncConstants.EOD_SYNC_CPSTOCLIMS_TEMPLATE_BASE_PATH) != null) {
			return getValueFromConfigFile(IEodSyncConstants.EOD_SYNC_CPSTOCLIMS_TEMPLATE_BASE_PATH) + fileName+"Template.xml";
		}
		return null;
	}
}
