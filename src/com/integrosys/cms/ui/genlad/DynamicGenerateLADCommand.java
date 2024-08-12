package com.integrosys.cms.ui.genlad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.corpthirdparty.OBCorporateThirdParty;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.government.OBGovernment;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.personal.OBPersonal;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.ICityDAO;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.bus.ICountryDAO;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.IStateDAO;
import com.integrosys.cms.app.lad.bus.GeneratePartyLADDao;
import com.integrosys.cms.app.lad.bus.ILAD;
import com.integrosys.cms.app.lad.bus.ILADDao;
import com.integrosys.cms.app.lad.bus.ILADItem;
import com.integrosys.cms.app.lad.bus.ILADSubItem;
import com.integrosys.cms.app.lad.bus.OBLADItem;
import com.integrosys.cms.app.lad.bus.OBLADSubItem;
import com.integrosys.cms.app.lad.proxy.ILADProxyManager;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/11/15 12:50:06 $ Tag: $Name: $
 */
public class DynamicGenerateLADCommand extends AbstractCommand implements
		ICommonEventConstant {

	private ILADProxyManager ladProxy;

	public ILADProxyManager getLadProxy() {
		return ladProxy;
	}

	public void setLadProxy(ILADProxyManager ladProxy) {
		this.ladProxy = ladProxy;
	}

	/**
	 * Default Constructor
	 */
	public DynamicGenerateLADCommand() {
	}
	
	protected static ResourceBundle bundle;
	
	static{
		bundle = ResourceBundle.getBundle("ApplicationResources-generatelad",Locale.getDefault());
	}
	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ,
						"com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						GLOBAL_SCOPE },
				{ "contextPath", "java.lang.String", GLOBAL_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "ladName", "java.lang.String", REQUEST_SCOPE },

		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "custDetail",
						"com.integrosys.cms.app.ddn.bus.IDDNCustomerDetail",
						SERVICE_SCOPE },
				{ "certTrxVal", "com.integrosys.cms.app.ddn.trx.IDDNTrxValue",
						SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.ddn.bus.IDDN", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.ddn.bus.IDDN", FORM_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE },
				{ "error", "java.lang.String", REQUEST_SCOPE },
				{ "bflFinalIssueDate", "java.lang.String", REQUEST_SCOPE },
				{ "fileName", "java.lang.String", REQUEST_SCOPE },
				{ "reportfile", "java.lang.String", SERVICE_SCOPE },
				{ "docsHeldMap", "java.util.HashMap", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		DefaultLogger.debug(this,
				"Inside GenerateLADCommand");
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			boolean error = false;
			List listDate = new ArrayList();
			String ladName = "";

			ICheckListProxyManager proxy = CheckListProxyManagerFactory
					.getCheckListProxyManager();
			ICMSCustomer customer = (ICMSCustomer) map
					.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			ILimitProfile limit = (ILimitProfile) map
					.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			ICheckList[] checkLists = proxy.getAllCheckList(limit);
			ICheckList[] finalCheckLists = new ICheckList[checkLists.length];
			ArrayList expList = new ArrayList();
			ILADDao iladDao = (ILADDao) BeanHouse.get("ladDao");

			List ladList = iladDao.getLADNormal(limitProfileID);
			if (ladList.size() != 0) {
				ILAD ilad = (ILAD) ladList.get(0);
				ladName = ilad.getLad_name();

			}

			if (checkLists != null) {
				int a = 0;
				for (int y = 0; y < checkLists.length; y++) {
					if (!checkLists[y].getCheckListType().equals("CAM")) {

						ICheckListItem[] curLadList = (ICheckListItem[]) checkLists[y]
								.getCheckListItemList();
						if (curLadList != null) {
							ArrayList expList2 = new ArrayList();
							for (int z = 0; z < curLadList.length; z++) {

								ICheckListItem item = (ICheckListItem) curLadList[z];
								if (item != null) {
									if (!(item.getItemStatus().equals("WAIVED"))) {
										if (item.getExpiryDate() != null) {
											expList2.add(item.getExpiryDate());
										}
									}
								}
							}
							if (expList2.size() > 0) {
								finalCheckLists[a] = checkLists[y];
								a++;
							}
						}
					}
				}
			}

			boolean generateLad = false;

			if (finalCheckLists != null) {
				for (int k = 0; k < finalCheckLists.length; k++) {
					if (finalCheckLists[k] != null) {
						if (finalCheckLists[k].getCheckListItemList() != null) {
							ICheckListItem[] checkListItems = finalCheckLists[k]
									.getCheckListItemList();

							for (int m = 0; m < checkListItems.length; m++) {
								if (checkListItems[m].getExpiryDate() != null) {
									if (!(checkListItems[m].getItemStatus()
											.equals("WAIVED"))) {
										if (checkListItems[m].getExpiryDate() != null) {
											listDate.add(checkListItems[m]
													.getExpiryDate());
											generateLad = true;
										}
									}
								}
							}
							Collections.sort(listDate);
						}
					}
				}
			}

			String basePath = "/cms";
			// String basePath=(String) map.get("contextPath");
			DefaultLogger.debug(this,
					"There is a basePath  to generating a zip archive: "
							+ basePath);

			String filePath = PropertyManager.getValue("lad.generate.path");
			
			
			DefaultLogger.debug(this,
					filePath);



			// String filePath =
			// "D:\\clims_hdfc\\HDFC_CLIMS_ORACLE\\public_html\\ladDocument";
			// createJavaDoc(ladName,limit,customer,filePath);
			DefaultLogger.debug(this,
					"There is a filePath  to generating a zip archive: "
							+ filePath);
			
			DefaultLogger.debug(this,
					"before Doc creation");
			
			long currentTimeMillis = System.currentTimeMillis();
			String cifId = customer.getCifId();
			ladName=cifId+"_"+currentTimeMillis;
			
			createJavaDocNew(ladName, limit, customer, filePath);

			DefaultLogger.debug(this,
					"After generating LAd this is  basePath  to generating a zip archive: "
							+ basePath);
			// String filePath =
			// PropertyManager.getValue(IReportConstants.BASE_PATH);lad.generate.path
			
			String fileName = filePath + "/" + ladName + ".zip";
			resultMap.put("fileName", fileName);
			// file.delete();
			resultMap.put("reportfile", fileName);

		} catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private void updateLAD() {
		HashMap updateLad = new HashMap();
		try {
			List listDate = new ArrayList();
			IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse
					.get("generalParamDao");
			IGeneralParamGroup generalParamGroup = generalParamDao
					.getGeneralParamGroupByGroupType("actualGeneralParamGroup",
							"GENERAL_PARAM");
			IGeneralParamEntry[] generalParamEntries = generalParamGroup
					.getFeedEntries();
			Date dateApplication = new Date();
			long ladGenIndicator = 0l;
			for (int i = 0; i < generalParamEntries.length; i++) {
				if (generalParamEntries[i].getParamCode().equals(
						"APPLICATION_DATE")) {
					dateApplication = new Date(
							generalParamEntries[i].getParamValue());
				} else if (generalParamEntries[i].getParamCode().equals(
						"LAD_GEN_INDICATOR")) {
					ladGenIndicator = Long.parseLong(generalParamEntries[i]
							.getParamValue());
				}
			}
			ILADDao iladDao = (ILADDao) BeanHouse.get("ladDao");
			List ladList = iladDao.getAllLAD();
			if (ladList != null) {
				int ind = 0;
				for (int i = 0; i < ladList.size(); i++) {
					ILAD ilad = (ILAD) ladList.get(i);
					if (ilad.getLad_due_date() != null) {
						long dateDiff = CommonUtil.dateDiff(
								ilad.getLad_due_date(), dateApplication,
								Calendar.MONTH);

						if (dateDiff <= ladGenIndicator) {
							ind++;
							updateLad.put(String.valueOf(ind),
									ilad.getLimit_profile_id());
						}
					}
				}
			}
			if (updateLad.size() != 0) {
				for (int b = 0; b < updateLad.size(); b++) {
					long limitProfileId = (Long) updateLad.get(b);
					ILimitProxy limitProxy = LimitProxyFactory.getProxy();
					ILimitProfile limit = limitProxy
							.getLimitProfile(limitProfileId);

					ICheckListProxyManager proxy = CheckListProxyManagerFactory
							.getCheckListProxyManager();
					ICheckList[] checkLists = proxy.getAllCheckList(limit);
					ICheckList[] finalCheckLists = new ICheckList[checkLists.length];
					ArrayList expList = new ArrayList();

					if (checkLists != null) {
						int a = 0;
						for (int y = 0; y < checkLists.length; y++) {
							if (checkLists[y].getCheckListType().equals("F")
									|| checkLists[y].getCheckListType().equals(
											"S")) {

								ICheckListItem[] curLadList = (ICheckListItem[]) checkLists[y]
										.getCheckListItemList();
								if (curLadList != null) {
									ArrayList expList2 = new ArrayList();
									for (int z = 0; z < curLadList.length; z++) {

										ICheckListItem item = (ICheckListItem) curLadList[z];
										if (item != null) {
											if (item.getItemStatus().equals(
													"RECEIVED")) {
												if (item.getExpiryDate() != null) {
													expList2.add(item
															.getExpiryDate());
												}
											}
										}
									}
									if (expList2.size() > 0) {
										finalCheckLists[a] = checkLists[y];
										a++;
									}
								}
							}
						}
					}

					boolean generateLad = false;

					if (finalCheckLists != null) {
						for (int k = 0; k < finalCheckLists.length; k++) {
							if (finalCheckLists[k] != null) {
								if (finalCheckLists[k].getCheckListItemList() != null) {
									ICheckListItem[] checkListItems = finalCheckLists[k]
											.getCheckListItemList();

									for (int m = 0; m < checkListItems.length; m++) {
										if (checkListItems[m].getExpiryDate() != null) {
											if (checkListItems[m]
													.getItemStatus().equals(
															"RECEIVED")) {
												if (checkListItems[m]
														.getExpiryDate() != null) {
													listDate.add(checkListItems[m]
															.getExpiryDate());
													generateLad = true;
												}
											}
										}
									}
									Collections.sort(listDate);
								}
							}
						}
					}

					if (finalCheckLists != null) {
						ILAD ilad = (ILAD) iladDao.getLADNormal(limitProfileId)
								.get(0);
						ilad.setIsOperationAllowed("Y");
						Date changedDueDate = null;
						Date date = (Date) listDate.get(0);
						changedDueDate = CommonUtil.rollUpDateByYears(date, 3);
						ilad.setLad_due_date(changedDueDate);
						ilad = getLadProxy().updateLAD(ilad);
						for (int i = 0; i < finalCheckLists.length; i++) {
							ILADItem iladItem = new OBLADItem();
							if (finalCheckLists[i] != null) {
								iladItem.setCategory(finalCheckLists[i]
										.getCheckListType());
								iladItem.setLad_id(ilad.getLad_id());
								iladItem = getLadProxy()
										.createLADItem(iladItem);

								if (finalCheckLists[i].getCheckListItemList() != null) {
									ICheckListItem[] checkListItems = finalCheckLists[i]
											.getCheckListItemList();
									for (int j = 0; j < checkListItems.length; j++) {
										if (checkListItems[j].getExpiryDate() != null) {
											if (checkListItems[j]
													.getItemStatus().equals(
															"RECEIVED")) {
												ILADSubItem iladSubItem = new OBLADSubItem();
												iladSubItem
														.setDoc_item_id(iladItem
																.getDoc_item_id());
												iladSubItem
														.setCategory(finalCheckLists[i]
																.getCheckListType());
												iladSubItem
														.setDoc_description(checkListItems[j]
																.getItemDesc());
												iladSubItem
														.setExpiry_date(checkListItems[j]
																.getExpiryDate());
												getLadProxy().createLADSubItem(
														iladSubItem);
											}
										}
									}
								}
							}

						}

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createJavaDocNew(String ladName, ILimitProfile limit,
			ICMSCustomer customer, String basePath) throws LimitException,
			RemoteException {
		
		DefaultLogger.debug(this,
				" Doc creation started");

		ILimit[] limits = limit.getLimits();
		
		DefaultLogger.debug(this,
				ladName);
		
		
		DefaultLogger.debug(this,
				basePath);


		GeneratePartyLADDao partyLadDao = (GeneratePartyLADDao) BeanHouse
				.get("ladPartyDao");

		List<String []> ladResult = partyLadDao.getLimitsForLadReport(limit
				.getLimitProfileID());
		FileOutputStream fileOutputStream = null;
	
		try {
			IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse
					.get("generalParamDao");
			IGeneralParamGroup generalParamGroup = generalParamDao
					.getGeneralParamGroupByGroupType("actualGeneralParamGroup",
							"GENERAL_PARAM");
			IGeneralParamEntry[] generalParamEntries = generalParamGroup
					.getFeedEntries();
			String date = "";
			for (int i = 0; i < generalParamEntries.length; i++) {
				if (generalParamEntries[i].getParamCode().equals(
						"APPLICATION_DATE")) {
					date = generalParamEntries[i].getParamValue();

				}
			}
		//	List<String> gaurantor = new ArrayList<String>();
			Set<String> gaurantor = new HashSet<String>();

			/*
			 * OBSubline subline[] =(OBSubline[])
			 * customer.getCMSLegalEntity().getSublineParty();
			 * if(subline!=null){ for(int ij=0;ij<subline.length;ij++){
			 * gaurantor[ij]=subline[ij].getPartyGroup().getCustomerName(); } }
			 */

			String address1 = "";
			String address2 = "";
			String address3 = "";
			String city = "";
			String state = "";
			String country = "";
			String pinCode = "";
			String previousDate = "";
			String previousDate2 = "";
			
			IContact address[] = customer.getCMSLegalEntity()
					.getRegisteredAddress();
			IContact obContactRegOff = null;
			if (address != null) {
				for (int i = 0; i < address.length; i++) {
					if (address[i].getContactType() != null
							&& address[i].getContactType().equals(
									ICMSConstant.REGISTERED)) {
						obContactRegOff = address[i];
					}

				}
				if (obContactRegOff != null) {
					if (obContactRegOff.getAddressLine1() != null) {
						address1 = obContactRegOff.getAddressLine1();
					}
					if (obContactRegOff.getAddressLine2() != null) {
						address2 = obContactRegOff.getAddressLine2();
					}
					if (obContactRegOff.getAddressLine3() != null) {
						address3 = obContactRegOff.getAddressLine3();
					}
					if (obContactRegOff.getCity() != null) {
						ICityDAO cityDAO = (ICityDAO) BeanHouse.get("cityDAO");
						ICity city2 = cityDAO.getCityById(Long
								.parseLong(obContactRegOff.getCity().trim()));
						city = city2.getCityName();
					}
					if (obContactRegOff.getState() != null) {
						IStateDAO stateDAO = (IStateDAO) BeanHouse
								.get("stateDAO");
						IState state2 = stateDAO.getStateById(Long
								.parseLong(obContactRegOff.getState().trim()));
						state = state2.getStateName();
					}
					if (obContactRegOff.getCountryCode() != null) {
						ICountryDAO countryDAO = (ICountryDAO) BeanHouse
								.get("countryDAO");
						ICountry country2 = countryDAO.getCountryById(Long
								.parseLong(obContactRegOff.getCountryCode()
										.trim()));
						country = country2.getCountryName();
					}
					if (obContactRegOff.getPostalCode() != null) {

						pinCode = obContactRegOff.getPostalCode();
					}

				}

			}

			int ladResultSize=ladResult.size();
			String[] facilityName = new String[ladResultSize];
			String[] releasableAmount = new String[ladResultSize];
			String[] releasedAmount = new String[ladResultSize];
			String[] outstandingAmount = new String[ladResultSize];

			BigDecimal totalLimit = new BigDecimal("0");
			BigDecimal totalOutStanding = new BigDecimal("0");
			BigDecimal totalReleasableLimit = new BigDecimal("0");

			String sublimitString="*";
			String openBracket="(";
			String closeBracket=")";
		
			if (limits != null) {

				for (int abc = 0; abc < limits.length; abc++) {

					ICollateralAllocation[] allocation = limits[abc]
							.getCollateralAllocations();
					if (allocation != null) {
						int c2 = 0;
						for (int c1 = 0; c1 < allocation.length; c1++) {
							//int c2 = 0;
							if(!("D".equals(allocation[c1].getHostStatus()))){
							if (allocation[c1].getCollateral() instanceof OBCorporateThirdParty) {
								OBCorporateThirdParty collateral = (OBCorporateThirdParty) allocation[c1]
										.getCollateral();
								OBCorporateThirdParty collateral2 = (OBCorporateThirdParty) CollateralProxyFactory
										.getProxy().getCollateral(
												collateral.getCollateralID(),
												true);
								if (collateral2.getGuarantersName() != null) {
									c2++;
									if (c2 <= 10)
										gaurantor.add(collateral2
												.getGuarantersName());
								}

							} else if (allocation[c1].getCollateral() instanceof OBGovernment) {
								OBGovernment collateral = (OBGovernment) allocation[c1]
										.getCollateral();
								OBGovernment collateral2 = (OBGovernment) CollateralProxyFactory
										.getProxy().getCollateral(
												collateral.getCollateralID(),
												true);
								if (collateral2.getGuarantersName() != null) {
									c2++;
									if (c2 <= 10)
										gaurantor.add(collateral2
												.getGuarantersName());
								}

							} else if (allocation[c1].getCollateral() instanceof OBPersonal) {
								OBPersonal collateral = (OBPersonal) allocation[c1]
										.getCollateral();
								OBPersonal collateral2 = (OBPersonal) CollateralProxyFactory
										.getProxy().getCollateral(
												collateral.getCollateralID(),
												true);
								if (collateral2.getGuarantersName() != null) {
									c2++;
									if (c2 <= 10)
										gaurantor.add(collateral2
												.getGuarantersName());
								}

							}
							}
						}
					}

				}

				int count = 0;
				for (String[] arr : ladResult) {

					String facilityCategory = arr[0];
					String totalReleasedAmount = arr[1];
					String totalReleasableAmount = arr[2];
					String totalUtilizedAmount = arr[3];

					if (facilityCategory != null && ! "-".equals(facilityCategory )) {
						facilityName[count] = facilityCategory;
					} else {
						facilityName[count] = "-";
					}

					if (totalReleasedAmount != null && !"-".equals(totalReleasedAmount )) {
						releasedAmount[count] = totalReleasedAmount;
						if(null !=facilityCategory  && !facilityCategory.endsWith(sublimitString)){
						totalLimit = totalLimit.add(new BigDecimal(
								totalReleasedAmount));
						}
					} else {
						releasedAmount[count] = "-";
					}

					if (totalReleasableAmount != null && !"-".equals(totalReleasableAmount )) {
						releasableAmount[count] = totalReleasableAmount;
						if(null !=facilityCategory  && !facilityCategory.endsWith(sublimitString)){
						totalReleasableLimit = totalReleasableLimit
								.add(new BigDecimal(totalReleasableAmount));
						}
					} else {
						 releasableAmount[count] = "NIL";
					}

					if (totalUtilizedAmount != null && !"-".equals(totalUtilizedAmount )) {

						outstandingAmount[count] = totalUtilizedAmount;
						if(null !=facilityCategory  && !facilityCategory.endsWith(sublimitString)){
							totalOutStanding = totalOutStanding
									.add(new BigDecimal(totalUtilizedAmount));
						}	
					} else {
						outstandingAmount[count] = "0.00";
					}

					count++;

				}


			} 

			/*
			 * replacing base path for local testing replace while giving
			 */
			// POI apparently can't create a document from scratch,
			// so we need an existing empty dummy document
			DefaultLogger.debug(this,
					"There is a filePath  while inputstream of file : "
							+ basePath);
//			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
//					basePath + "/LADFormat.doc"));

//			HWPFDocument doc = new HWPFDocument(fs);
            XWPFDocument document = new XWPFDocument();
         //   ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources-generatelad");
            
        //    System.out.println("text.generatelad.acknowledgeAmount:"+bundle.getString("text.generatelad.acknowledgeAmount"));
			if (date != null) {
				runParagraphText(document,bundle.getString("text.generatelad.date")+date, false,1,ParagraphAlignment.RIGHT,false);
				
				runParagraphText(document,bundle.getString("text.generatelad.to"), false,0,ParagraphAlignment.LEFT,false);
				
				GregorianCalendar gc = new GregorianCalendar();
				DateFormat df = new SimpleDateFormat(
						"dd-MMM-yyyy");
				Date startDate = df.parse(date);
				gc.setTime(startDate);
				int dayBefore = gc.get(Calendar.DAY_OF_YEAR);
				gc.roll(Calendar.DAY_OF_YEAR, -1);
				int dayAfter = gc.get(Calendar.DAY_OF_YEAR);
				if (dayAfter > dayBefore) {
					gc.roll(Calendar.YEAR, -1);
				}
				gc.get(Calendar.DATE);
				java.util.Date yesterday = gc.getTime();

				Calendar calendar = Calendar
						.getInstance(TimeZone.getDefault());
				calendar.setTime(yesterday);
				String year = Integer.valueOf(
						calendar.get(Calendar.YEAR)).toString();

				String day = UIUtil.convertToOrdinal(calendar
						.get(Calendar.DAY_OF_MONTH));
				String month = new SimpleDateFormat("MMMM")
						.format(calendar.getTime());

				previousDate = day + " " + month + " " + year;
				previousDate2 = day + " day of " + month + " " + year;
				// z++;
			}
		
			runParagraphTextWithoutLineSpace(document,customer.getCustomerName(), false,0,ParagraphAlignment.LEFT,false);
			runParagraphTextWithoutLineSpace(document,address1, false,0,ParagraphAlignment.LEFT,false);
			if(null!=address2 && !"".equalsIgnoreCase(address2))
				runParagraphTextWithoutLineSpace(document,address2, false,0,ParagraphAlignment.LEFT,false);
			if(null!=address3 && !"".equalsIgnoreCase(address3))
				runParagraphTextWithoutLineSpace(document,address3, false,0,ParagraphAlignment.LEFT,false);
			runParagraphTextWithoutLineSpace(document,city, false,0,ParagraphAlignment.LEFT,false);
			runParagraphTextWithoutLineSpace(document,state, false,0,ParagraphAlignment.LEFT,false);
			runParagraphTextWithoutLineSpace(document,country, false,0,ParagraphAlignment.LEFT,false);
			if(null!=pinCode && !"".equalsIgnoreCase(pinCode))
				runParagraphTextWithoutLineSpace(document,pinCode, false,2,ParagraphAlignment.LEFT,false);
			
	
			runParagraphText(document,bundle.getString("text.generatelad.dearSirs"), false,0,ParagraphAlignment.LEFT,false);
			runParagraphText(document,bundle.getString("text.generatelad.creditFacility"), true, 1,ParagraphAlignment.CENTER,false);
			
			XWPFParagraph createParagraph1 = document.createParagraph();
			createParagraph1.setAlignment(ParagraphAlignment.LEFT);
			createRunText(createParagraph1,bundle.getString("text.generatelad.amountOutStanding1"),false);
			createRunText(createParagraph1," "+previousDate,true);
			createRunText(createParagraph1,bundle.getString("text.generatelad.amountOutStanding2"),false);

			XWPFTable table = document.createTable();

			// create first row
			XWPFTableRow tableHeader = table.getRow(0);
			tableHeader.setHeight(10);
			tableHeader.getCell(0).setParagraph(
					getParagraph(bundle.getString("text.generatelad.natureOfFacility"),true));
			tableHeader.getCell(0).setVerticalAlignment(XWPFVertAlign.CENTER);

			tableHeader.addNewTableCell();
			tableHeader.getCell(1).setParagraph(
					getParagraph(bundle.getString("text.generatelad.amountOfDocumentExecuted"),true));
			tableHeader.getCell(1).setVerticalAlignment(XWPFVertAlign.CENTER);

			tableHeader.addNewTableCell();
			tableHeader.getCell(2)
					.setParagraph(getParagraph(bundle.getString("text.generatelad.limitsInForce"),true));
			tableHeader.getCell(2).setVerticalAlignment(XWPFVertAlign.CENTER);

			tableHeader.addNewTableCell();
			tableHeader.getCell(3)
					.setParagraph(getParagraph(bundle.getString("text.generatelad.amountOutstandingInRupees"),true));
			tableHeader.getCell(3).setVerticalAlignment(XWPFVertAlign.CENTER);
			

			for (int i = 0; i < ladResultSize; i++) {
				XWPFTableRow valueRow = table.createRow();
				
                valueRow.getCell(0).setText(facilityName[i]);
				valueRow.getCell(0).setVerticalAlignment(XWPFVertAlign.CENTER);
					
				if(null !=facilityName[i] && facilityName[i].endsWith(sublimitString)){
					valueRow.getCell(1).setText(openBracket+UIUtil
							.formatWithCommaAndDecimal(releasableAmount[i])+closeBracket);
					valueRow.getCell(2).setText(openBracket+UIUtil
							.formatWithCommaAndDecimal(releasedAmount[i])+closeBracket);
					
					
					valueRow.getCell(3).setText(openBracket+UIUtil
							.formatWithCommaAndDecimal(outstandingAmount[i])+closeBracket);
					
					}else{
						valueRow.getCell(1).setText(UIUtil
								.formatWithCommaAndDecimal(releasableAmount[i]));	
						valueRow.getCell(2).setText(UIUtil
								.formatWithCommaAndDecimal(releasedAmount[i]));
						
						valueRow.getCell(3).setText(UIUtil
								.formatWithCommaAndDecimal(outstandingAmount[i]));
					}	
				
				valueRow.getCell(1).setVerticalAlignment(XWPFVertAlign.CENTER);
					
				valueRow.getCell(2).setVerticalAlignment(XWPFVertAlign.CENTER);
					
				
				valueRow.getCell(3).setVerticalAlignment(XWPFVertAlign.CENTER);
			}
			   XWPFTableRow valueRow = table.createRow();
			
               valueRow.getCell(0).setParagraph(getParagraph(bundle.getString("text.generatelad.total"), true));
			   valueRow.getCell(0).setVerticalAlignment(XWPFVertAlign.CENTER);
				
			   valueRow.getCell(1).setParagraph(getParagraph(UIUtil.formatWithCommaAndDecimal(String.valueOf(totalReleasableLimit)),true));
			   valueRow.getCell(1).setVerticalAlignment(XWPFVertAlign.CENTER);
				
			   valueRow.getCell(2).setParagraph(getParagraph(UIUtil.formatWithCommaAndDecimal(String.valueOf(totalLimit)),true));
			   valueRow.getCell(2).setVerticalAlignment(XWPFVertAlign.CENTER);
				
			   valueRow.getCell(3).setParagraph(getParagraph(UIUtil.formatWithCommaAndDecimal(String.valueOf(totalOutStanding)),true));
			   valueRow.getCell(3).setVerticalAlignment(XWPFVertAlign.CENTER);
			   
			   runParagraphText(document,bundle.getString("text.generatelad.sublimit"), false,0,ParagraphAlignment.LEFT,false);
			   
			   if("0".equals(String.valueOf(totalOutStanding))){
				   runParagraphText(document,"(Rupees Zero only)", true,0,ParagraphAlignment.RIGHT,false);	   
			   }else{
				   runParagraphText(document,"("+ UIUtil.numberToWordsIndia(String.valueOf(totalOutStanding)).trim()+" only)", true,0,ParagraphAlignment.RIGHT,false);
			   }

			   runParagraphText(document,bundle.getString("text.generatelad.acknowledgeConfirm"), false,0,ParagraphAlignment.LEFT,false);
			//   int gaurantorLength = gaurantor.size();
			   
//			   for(int i=0;i<gaurantorLength;i++){
//				   runParagraphText(document,gaurantor.get(i), true,0,ParagraphAlignment.LEFT,false);
//			   }
			   
			   Iterator<String> iterator1 = gaurantor.iterator();
				while(iterator1.hasNext()){
					runParagraphText(document,iterator1.next(), true,0,ParagraphAlignment.LEFT,false);
				}

			    runParagraphText(document,bundle.getString("text.generatelad.gaurantorDebt"), false,0,ParagraphAlignment.LEFT,false);
				runParagraphText(document,bundle.getString("text.generatelad.yoursSincerely"), false,0,ParagraphAlignment.LEFT,false);
				runParagraphText(document,bundle.getString("text.generatelad.forHdfcBank"), true,3,ParagraphAlignment.LEFT,false);
				runParagraphText(document,bundle.getString("text.generatelad.authorizedSignatory"), false,0,ParagraphAlignment.LEFT,false);
				runParagraphTextWithoutLineSpace(document,bundle.getString("text.generatelad.theManager"), false,0,ParagraphAlignment.LEFT,true);
				runParagraphTextWithoutLineSpace(document,bundle.getString("text.generatelad.hDFCBankLtd"), false,0,ParagraphAlignment.LEFT,false);
				runParagraphTextWithoutLineSpace(document,bundle.getString("text.generatelad.sandozHouse"), false,0,ParagraphAlignment.LEFT,false);
				runParagraphTextWithoutLineSpace(document,bundle.getString("text.generatelad.drAnnieBesantRd"), false,0,ParagraphAlignment.LEFT,false);
				runParagraphTextWithoutLineSpace(document,bundle.getString("text.generatelad.worli"), false,1,ParagraphAlignment.LEFT,false);
				runParagraphText(document,bundle.getString("text.generatelad.dearSir"), false,0,ParagraphAlignment.LEFT,false);
				
				XWPFParagraph createParagraph = document.createParagraph();
				
				createParagraph.setAlignment(ParagraphAlignment.LEFT);
				createRunText(createParagraph,bundle.getString("text.generatelad.acknowledgeAmount1"),false);
				createRunText(createParagraph," "+previousDate2+" ",true);
				createRunText(createParagraph,bundle.getString("text.generatelad.acknowledgeAmount2"),false);
				createRunText(createParagraph," "+UIUtil.formatWithCommaAndDecimal(String.valueOf(totalOutStanding))+" ",true);
				if("0".equals(String.valueOf(totalOutStanding))){
					createRunText(createParagraph,"(Rupees Zero only) ", true);
				}else{
					createRunText(createParagraph,"("+UIUtil.numberToWordsIndia(String.valueOf(totalOutStanding)).trim()+" only) ", true);
				}
				createRunText(createParagraph,bundle.getString("text.generatelad.acknowledgeAmount3"),false);
				
				runParagraphText(document,bundle.getString("text.generatelad.furtherAcknowledge"), false,1,ParagraphAlignment.LEFT,false);
				runParagraphText(document,bundle.getString("text.generatelad.for"), false,3,ParagraphAlignment.LEFT,false);
				runParagraphText(document,bundle.getString("text.generatelad.authorizedSignatory"), false,1,ParagraphAlignment.LEFT,false);
				
			/*	String datePlace = MessageFormat.format(bundle.getString("text.generatelad.datePlace"),date,country);
				runParagraphText(document,datePlace, false,0,ParagraphAlignment.LEFT,false); */
				
				runParagraphTextWithoutLineSpace(document,bundle.getString("text.generatelad.date"), false,0,ParagraphAlignment.LEFT,false);
				runParagraphTextWithoutLineSpace(document,bundle.getString("text.generatelad.datePlace"), false,1,ParagraphAlignment.LEFT,false);
				
				if(gaurantor.size()>0){
				runParagraphText(document,bundle.getString("text.generatelad.foregoingConfirmation"), false,0,ParagraphAlignment.LEFT,false);
				//String datedPlace = MessageFormat.format(bundle.getString("text.generatelad.datedPlace"),date,country);
				//runParagraphText(document,bundle.getString("text.generatelad.datedPlace"), false,0,ParagraphAlignment.LEFT,false);
				}
				
//				for(int i=0;i<gaurantorLength;i++){
//					runParagraphText(document,gaurantor.get(i), true,0,ParagraphAlignment.LEFT,false);
//				   }
				Iterator<String> iterator2 = gaurantor.iterator();
				while(iterator2.hasNext()){
					runParagraphText(document,iterator2.next(), true,0,ParagraphAlignment.LEFT,false);
				}

			DefaultLogger.debug(this,
					"There is a filePath  while output stream of file : "
							+ basePath);

			File file = new File(basePath + "/" + ladName + ".doc");

			if (file.exists()) {
				boolean delete = file.delete();
				if(delete==false) {
					System.out.println("file  deletion failed for file:"+file.getPath());	
				}
			}

			fileOutputStream = new FileOutputStream(basePath + "/" + ladName
					+ ".doc", false);

			document.write(fileOutputStream);
			zipDirectory(basePath + "/"  + ladName+ ".doc",basePath  + "/"  +  ladName+".zip");
			
			if (file.exists()) {
				boolean delete = file.delete();
				if(delete==false) {
					System.out.println("file  deletion failed for file:"+file.getPath());	
				}
			}
			fileOutputStream.close();

/*			File file2 = new File(hotdeployPath + ladName + ".doc");

			if (file2.exists()) {
				file2.delete();
			}

			fileOutputStream2 = new FileOutputStream(hotdeployPath + ladName
					+ ".doc", false);
			doc.write(fileOutputStream2);*/

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Cannot generate Lad Document", e);
		} finally {
			try {
				if(null!=fileOutputStream){
				fileOutputStream.close();
				}
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private void createRunText(XWPFParagraph createParagraph, String runText, boolean isBold) {
		   XWPFRun createRun = createParagraph.createRun();
		   createRun.setText(runText);
		   createRun.setBold(isBold);
	}

	private  void runParagraphText(XWPFDocument document, String text,
			boolean isBold, int noOfBreaks, ParagraphAlignment allignment,boolean pageBreak) {
		XWPFParagraph paragraph = document.createParagraph();
		 paragraph.setAlignment(allignment);
		 paragraph.setPageBreak(pageBreak);
		XWPFRun run = paragraph.createRun();
		run.setText(text);
		run.setBold(isBold);
		for (int i = 0; i < noOfBreaks; i++) {
			run.addBreak();

		}

	}
	
	
	private  void addBreak(XWPFDocument document,int noOfBreaks) {
		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();
		for (int i = 0; i < noOfBreaks; i++) {
			run.addBreak();
		}
	}

	/**
	 * @param document
	 * @param string
	 * @return
	 */
	private  XWPFParagraph getParagraph(String text,boolean isBold) {
		XWPFDocument document = new XWPFDocument();
		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun paragraphOneRunOne = paragraph.createRun();
		paragraphOneRunOne.setBold(isBold);
		paragraphOneRunOne.setText(text);
		return paragraph;
	}
	
	private void zipDirectory(String filePath, String zipDirName) {
		try {

			DefaultLogger.debug(this, "File zipping Begin---------------");
		//	populateFilesList(dir);
			// now zip files one by one
			// create ZipOutputStream to write to the zip file
			FileOutputStream fos = new FileOutputStream(zipDirName);
			ZipOutputStream zos = new ZipOutputStream(fos);
			
				System.out.println("Zipping " + filePath);
				// for ZipEntry we need to keep only relative file path, so we
				// used substring on absolute path
				ZipEntry ze = new ZipEntry(filePath.substring(filePath.lastIndexOf("/")+1));
				zos.putNextEntry(ze);
				// read the file and write to ZipOutputStream
				FileInputStream fis = new FileInputStream(filePath);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				zos.closeEntry();
				fis.close();
			
			zos.close();
			fos.close();

			DefaultLogger.debug(this, "File zipping end---------------");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private static void runParagraphTextWithoutLineSpace(XWPFDocument document, String text,
			boolean isBold, int noOfBreaks, ParagraphAlignment allignment,boolean pageBreak) {
		XWPFParagraph paragraph = document.createParagraph();
		
		paragraph.setSpacingAfter(0);
		paragraph.setSpacingAfterLines(0);
		paragraph.setSpacingBefore(0);
		paragraph.setSpacingBeforeLines(0);
	    paragraph.setAlignment(allignment);
	    paragraph.setPageBreak(pageBreak);
		XWPFRun run = paragraph.createRun();
		run.setText(text);
		run.setBold(isBold);
		
		for (int i = 0; i < noOfBreaks; i++) {
			run.addBreak();

		}

	}
	

}