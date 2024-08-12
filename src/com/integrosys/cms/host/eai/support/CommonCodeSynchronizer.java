package com.integrosys.cms.host.eai.support;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.commoncode.bus.EBCommonCodeType;
import com.integrosys.cms.app.commoncode.bus.EBCommonCodeTypeHome;
import com.integrosys.cms.app.commoncode.bus.OBCommonCodeType;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryUtil;
import com.integrosys.cms.app.commoncodeentry.bus.EBCommonCodeEntry;
import com.integrosys.cms.app.commoncodeentry.bus.EBCommonCodeEntryHome;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.component.commondata.app.CommonDataSingleton;

public abstract class CommonCodeSynchronizer {
	/**
	 * To perform insert or update for the standard code supplied based on the
	 * insert and update indicator.
	 * 
	 * @param c the standard code to be updated or inserted
	 * @param source the source of the message
	 * @param country the country of the message
	 * @param insertRequired indicate whether insert is required
	 * @param updateRequired indicate where update is required
	 */
	public static synchronized void performInsertUpdate(final StandardCode c, final String source,
			final String country, final boolean insertRequired, final boolean updateRequired) {

		Thread t = new Thread() {

			private synchronized void execute() throws Exception {
				if (insertRequired) {
					// If Description is Empty , set the Standard Code Number .
					if (StringUtils.isEmpty(c.getStandardCodeDescription())) {
						c.setStandardCodeDescription(c.getStandardCodeValue());
					}

					// Do insert
					EBCommonCodeType ebCommonCodeType = getEBCommonCodeTypeHome().findByCategoryCode(
							c.getStandardCodeNumber());
					OBCommonCodeType obCommonCodeType = (OBCommonCodeType) ebCommonCodeType.getValue();
					OBCommonCodeEntry obCommonCodeEntry = prepareCommonCodeEntry(obCommonCodeType, c, source, country);

					getEBCommonCodeEntryHome().create(obCommonCodeEntry);

					String categoryCode = c.getStandardCodeNumber();

					// Refresh the particular category code
					DefaultLogger.debug(this, "Category Code to refresh :'" + categoryCode + "'");

					CommonCodeEntryUtil.synchronizeCommonCode(categoryCode);

				}
				else if (updateRequired) {
					// Do update
					EBCommonCodeType ebCommonCodeType = getEBCommonCodeTypeHome().findByCategoryCode(
							c.getStandardCodeNumber());
					OBCommonCodeType obCommonCodeType = (OBCommonCodeType) ebCommonCodeType.getValue();
					EBCommonCodeEntry ebCommonCodeEntry = getEBCommonCodeEntryHome()
							.findByCategoryAndEntryCodeAndSourceId(new Long(obCommonCodeType.getCommonCategoryId()),
									c.getStandardCodeValue(), country, source);
					OBCommonCodeEntry obCommonCodeEntry = (OBCommonCodeEntry) ebCommonCodeEntry.getOBCommonCodeEntry();
					obCommonCodeEntry.setEntryName(c.getStandardCodeDescription());
					ebCommonCodeEntry.updateCommonCodeEntry(obCommonCodeEntry);

					// CommonDataSingleton.refresh();
					try {
						// CommonDataSingleton.getInstance ().refresh (
						// c.getStandardCodeNumber () );
						// CommonDataSingleton.getInstance ().refresh (
						// c.getStandardCodeNumber () ) ;
						String categoryCode = c.getStandardCodeNumber();

						// Refresh the particular category code
						DefaultLogger.debug(this, "Category Code to refresh :'" + categoryCode + "'");
						// CommonDataSingleton.getInstance().refresh(categoryCode
						// );

						boolean syncEnabled = PropertyManager.getBoolean("commoncode.sync.enabled", false);

						if (syncEnabled) {
							CommonCodeEntryUtil.synchronizeCommonCode(categoryCode);
						}
						else {
							// Only refresh Current Server Common Code List
							synchronized (this) {
								CommonDataSingleton.refresh(categoryCode);
							}
						}
					}
					catch (Exception e) {
						DefaultLogger.error(this, "Error in refreshing Category 3 Standard Code data.", e);
					}
				}
			}

			public void run() {

				try {
					execute();
				}
				catch (Exception e) {
					DefaultLogger.error(this, e);
				}

			}

		};

		if (PropertyManager.getBoolean("eai.thread.commoncode.enable", true)) {
			t.start();
		}
		else {
			t.run();
		}
	}

	/**
	 * To get the home handler for the Common Code Type Entity Bean
	 * 
	 * @return EBCommonCodeTypeHome - the home handler for the Common Code Type
	 *         entity bean
	 */
	protected static EBCommonCodeTypeHome getEBCommonCodeTypeHome() {
		EBCommonCodeTypeHome commonCodeTypeHome = (EBCommonCodeTypeHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_COMMON_CODE_TYPE_JNDI, EBCommonCodeTypeHome.class.getName());

		Validate.notNull(commonCodeTypeHome, "'commonCodeEntryHome' cannot be found from the context using jndi name ["
				+ ICMSJNDIConstant.EB_COMMON_CODE_TYPE_JNDI + "]");

		return commonCodeTypeHome;

	}

	/**
	 * @return EBCommonCodeEntryHome - return the home interface for manuplating
	 *         the data in the COMMON_CODE_CATEGORY_ENTRY table
	 * @throws java.rmi.RemoteException
	 */
	protected static EBCommonCodeEntryHome getEBCommonCodeEntryHome() {
		EBCommonCodeEntryHome commonCodeEntryHome = (EBCommonCodeEntryHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_COMMON_CODE_ENTRY_HOME, EBCommonCodeEntryHome.class.getName());

		Validate.notNull(commonCodeEntryHome,
				"'commonCodeEntryHome' cannot be found from the context using jndi name ["
						+ ICMSJNDIConstant.EB_COMMON_CODE_ENTRY_HOME + "]");

		return commonCodeEntryHome;
	}

	private static OBCommonCodeEntry prepareCommonCodeEntry(OBCommonCodeType obCommonCodeType, StandardCode c,
			String source, String country) {
		OBCommonCodeEntry obCommonCodeEntry = new OBCommonCodeEntry();

		obCommonCodeEntry.setActiveStatus(true);
		obCommonCodeEntry.setCategoryCode(obCommonCodeType.getCommonCategoryCode());
		obCommonCodeEntry.setCategoryCodeId(obCommonCodeType.getCommonCategoryId());
		obCommonCodeEntry.setEntryCode(c.getStandardCodeValue());
		obCommonCodeEntry.setEntryName(c.getStandardCodeDescription());
		obCommonCodeEntry.setEntrySource(source);
		obCommonCodeEntry.setCountry(country);
		obCommonCodeEntry.setVersionTime(System.currentTimeMillis());

		return obCommonCodeEntry;
	}
}
