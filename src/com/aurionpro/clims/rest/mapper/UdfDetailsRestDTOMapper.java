package com.aurionpro.clims.rest.mapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.aurionpro.clims.rest.dto.CamDetailsRestRequestDTO;
import com.aurionpro.clims.rest.dto.FacilityLineDetailRestRequestDTO;
import com.aurionpro.clims.rest.dto.PartyDetailsRestRequestDTO;
import com.aurionpro.clims.rest.dto.UdfRestRequestDTO;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.app.customer.bus.ICMSCustomerUdf;
import com.integrosys.cms.app.customer.bus.ILimitXRefUdf;
import com.integrosys.cms.app.customer.bus.ILimitXRefUdf2;
import com.integrosys.cms.app.customer.bus.OBCMSCustomerUdf;
import com.integrosys.cms.app.customer.bus.OBLimitXRefUdf;
import com.integrosys.cms.app.customer.bus.OBLimitXRefUdf2;
import com.integrosys.cms.app.limit.bus.ILimitProfileUdf;
import com.integrosys.cms.app.limit.bus.OBLimitProfileUdf;
import com.integrosys.cms.app.udf.bus.OBUdf;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.ui.manualinput.aa.AADetailForm;
import com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerInfoForm;
import com.integrosys.cms.ui.manualinput.limit.XRefDetailForm;

public class UdfDetailsRestDTOMapper {

	public List<UdfRestRequestDTO> getUdfRequestDTOWithActualValues(Object requestUdf, String event, ActionErrors errors) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		dateFormat.setLenient(false);
		
		MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
		if (event.equals("Rest_create_customer") || event.equals("Rest_update_customer")) {
			PartyDetailsRestRequestDTO requestDTO = (PartyDetailsRestRequestDTO) requestUdf;
			List<UdfRestRequestDTO> udfList = new LinkedList<UdfRestRequestDTO>();
			if (requestDTO.getBodyDetails().get(0).getUdfList() != null && !requestDTO.getBodyDetails().get(0).getUdfList().isEmpty()) {
				for (UdfRestRequestDTO udfRestRequestDTO : requestDTO.getBodyDetails().get(0).getUdfList()) {
					UdfRestRequestDTO udfDetailsRestRequestDTO = new UdfRestRequestDTO();
					if (udfRestRequestDTO.getModuleId()!=null && udfRestRequestDTO.getModuleId().equals("1")) {
						if (udfRestRequestDTO.getUdfSequance() != null
								&& !udfRestRequestDTO.getUdfSequance().trim().isEmpty()) {
							Object obj = masterObj.getObjectByEntityNameAndSequenceForRest("actualUdf",
									udfRestRequestDTO.getModuleId(), udfRestRequestDTO.getUdfSequance(),
									"udf".concat(udfRestRequestDTO.getUdfSequance()), errors);
							List<String> al = new ArrayList<String>();
							if (!(obj instanceof ActionErrors)) {
								OBUdf obudf = (OBUdf) obj;
								if(udfRestRequestDTO.getUdfValue()!=null && udfRestRequestDTO.getUdfValue().trim().isEmpty()) {
									if(udfRestRequestDTO.getUdfSequance()!=null)
										errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValue"),new ActionMessage("udf value is mandatory"));
								}else {	
									if (obudf.getOptions() != null) {
										String str = obudf.getOptions();
										String strr[] = str.split(",");
										al = Arrays.asList(strr);
										if (al.contains(udfRestRequestDTO.getUdfValue())) {
											if (!(obj instanceof ActionErrors)) {
												udfDetailsRestRequestDTO.setUdfSequance(udfRestRequestDTO.getUdfSequance());
												udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
												udfDetailsRestRequestDTO.setUdfLabel(udfRestRequestDTO.getUdfLabel());
											}else {
												errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValue"),new ActionMessage("udf value is invalid"));
											}
										} else {
											errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValue"),new ActionMessage("udf value is invalid"));
										}
									} else {
										if(obudf.getFieldTypeId()==1 || obudf.getFieldTypeId()==2 || obudf.getFieldTypeId()==7) {
											if(udfRestRequestDTO.getUdfValue()!=null && obudf.getNumericLength()!=null) {												
												if(udfRestRequestDTO.getUdfValue().length() > Integer.parseInt(obudf.getNumericLength())) {
													errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValue"), new ActionMessage("error.string.field.length.exceeded"));
												}else {
													udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
												}
											}else if(udfRestRequestDTO.getUdfValue()!=null && udfRestRequestDTO.getUdfValue().length() > 2000){
												errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValue"), new ActionMessage("error.string.field.length.exceeded"));
											}else {
												udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
											}
										}else if(obudf.getFieldTypeId()==6){
											try {
												dateFormat.parse(udfRestRequestDTO.getUdfValue());
												udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
											} catch (ParseException e) {
												errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValue"), new ActionMessage("error.party.date.format"));
											}
										}else {
											udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
										}
										udfDetailsRestRequestDTO.setUdfSequance(udfRestRequestDTO.getUdfSequance());
										udfDetailsRestRequestDTO.setUdfLabel(udfRestRequestDTO.getUdfLabel());
									}
								}
							}
						} else {
							errors.add("udfSequence", new ActionMessage("error.sequence.mandatory"));
						}
					} else {
						errors.add("moduleId", new ActionMessage("error.invalid.module.id"));
					}
					udfList.add(udfDetailsRestRequestDTO);
				}
			}
			return (List<UdfRestRequestDTO>) udfList;
			
		} else if (event.equals("REST_CAM_CREATE") || event.equals("REST_CAM_UPDATE")) {
			CamDetailsRestRequestDTO requestDTO = (CamDetailsRestRequestDTO) requestUdf;
			List<UdfRestRequestDTO> udfList = new LinkedList<UdfRestRequestDTO>();
			if (requestDTO.getUdfList() != null && !requestDTO.getUdfList().isEmpty()) {
				for (UdfRestRequestDTO udfRestRequestDTO : requestDTO.getUdfList()) {
					UdfRestRequestDTO udfDetailsRestRequestDTO = new UdfRestRequestDTO();
					if (udfRestRequestDTO.getModuleId()!=null && udfRestRequestDTO.getModuleId().equals("2")) {
						if (udfRestRequestDTO.getUdfSequance() != null
								&& !udfRestRequestDTO.getUdfSequance().trim().isEmpty()) {
							Object obj = masterObj.getObjectByEntityNameAndSequenceForRest("actualUdf",
									udfRestRequestDTO.getModuleId(), udfRestRequestDTO.getUdfSequance(),
									"udf".concat(udfRestRequestDTO.getUdfSequance()), errors);
							List<String> al = new ArrayList<String>();
							if (!(obj instanceof ActionErrors)) {
								OBUdf obudf = (OBUdf) obj;
								if(udfRestRequestDTO.getUdfValue()!=null && udfRestRequestDTO.getUdfValue().trim().isEmpty()) {
									if(udfRestRequestDTO.getUdfSequance()!=null)
										errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValue"),new ActionMessage("udf value is mandatory"));
								}else {	
									if (obudf.getOptions() != null) {
										String str = obudf.getOptions();
										String strr[] = str.split(",");
										al = Arrays.asList(strr);
										if (al.contains(udfRestRequestDTO.getUdfValue())) {
											if (!(obj instanceof ActionErrors)) {
												udfDetailsRestRequestDTO.setUdfSequance(udfRestRequestDTO.getUdfSequance());
												udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
												udfDetailsRestRequestDTO.setUdfLabel(udfRestRequestDTO.getUdfLabel());
											}else {
												errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValue"),new ActionMessage("udf value is invalid"));
											}
										} else {
											errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValue"),new ActionMessage("udf value is invalid"));
										}
									} else {	
										if(obudf.getFieldTypeId()==1 || obudf.getFieldTypeId()==2 || obudf.getFieldTypeId()==7) {
											if(udfRestRequestDTO.getUdfValue()!=null && obudf.getNumericLength()!=null) {												
												if(udfRestRequestDTO.getUdfValue().length() > Integer.parseInt(obudf.getNumericLength())) {
													errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValue"), new ActionMessage("error.string.field.length.exceeded"));
												}else {
													udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
												}
											}else if(udfRestRequestDTO.getUdfValue()!=null && udfRestRequestDTO.getUdfValue().length() > 2000){
												errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValue"), new ActionMessage("error.string.field.length.exceeded"));
											}else {
												udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
											}
										}else if(obudf.getFieldTypeId()==6){
											try {
												dateFormat.parse(udfRestRequestDTO.getUdfValue());
												udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
											} catch (ParseException e) {
												errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValue"), new ActionMessage("error.date.format"));
											}
										}else {
											udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
										}

										udfDetailsRestRequestDTO.setUdfSequance(udfRestRequestDTO.getUdfSequance());
										udfDetailsRestRequestDTO.setUdfLabel(udfRestRequestDTO.getUdfLabel());
									}
								}
							}
						} else {
							errors.add("udfSequence", new ActionMessage("error.sequence.mandatory"));
					}
				}else {
						errors.add("moduleId", new ActionMessage("error.invalid.module.id"));
				}
					udfList.add(udfDetailsRestRequestDTO);
				}
			}
			return (List<UdfRestRequestDTO>) udfList;
		}
		
		else if (event.equals("WS_LINE_CREATE_REST_UDF1") || event.equals("WS_LINE_UPDATE_REST_UDF1")) {
			FacilityLineDetailRestRequestDTO requestDTO = (FacilityLineDetailRestRequestDTO) requestUdf;
			List<UdfRestRequestDTO> udfList = new LinkedList<UdfRestRequestDTO>();
			if (requestDTO.getUdfList() != null && !requestDTO.getUdfList().isEmpty()) {
				for (UdfRestRequestDTO udfRestRequestDTO : requestDTO.getUdfList()) {
					UdfRestRequestDTO udfDetailsRestRequestDTO = new UdfRestRequestDTO();
					if(null != udfRestRequestDTO.getModuleId() && !udfRestRequestDTO.getModuleId().isEmpty()) {
					if (udfRestRequestDTO.getModuleId().equals("3") ) {
						if (udfRestRequestDTO.getUdfSequance() != null
								&& !udfRestRequestDTO.getUdfSequance().trim().isEmpty()) {
							Object obj = masterObj.getObjectByEntityNameAndSequenceForRest("actualUdf",
									udfRestRequestDTO.getModuleId(), udfRestRequestDTO.getUdfSequance(),
									"udfList ".concat(udfRestRequestDTO.getUdfSequance()), errors);
							List<String> al = new ArrayList<String>();
							if (!(obj instanceof ActionErrors)) {
								OBUdf obudf = (OBUdf) obj;
								if(udfRestRequestDTO.getUdfValue()!=null && udfRestRequestDTO.getUdfValue().trim().isEmpty()) {
									if(udfRestRequestDTO.getUdfSequance()!=null)
										errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValue"),new ActionMessage("udf value is mandatory"));
								}else {	
									if (obudf.getOptions() != null) {
										String str = obudf.getOptions();
										String strr[] = str.split(",");
										al = Arrays.asList(strr);
										if(null != udfRestRequestDTO.getUdfValue() && !udfRestRequestDTO.getUdfValue().isEmpty()) {
											if (al.contains(udfRestRequestDTO.getUdfValue())) {
												if (!(obj instanceof ActionErrors)) {
													udfDetailsRestRequestDTO.setUdfSequance(udfRestRequestDTO.getUdfSequance());
													udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
													udfDetailsRestRequestDTO.setUdfLabel(udfRestRequestDTO.getUdfLabel());
												}else {
													errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValueError"),new ActionMessage("udf value is invalid"));
												}
											} else {
												errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValue"),new ActionMessage("udf value is invalid"));
											}
										}else {
											errors.add("udfList_udfValue", new ActionMessage("error.mandatory"));
										}
									} else {
										if(obudf.getFieldTypeId()==1 || obudf.getFieldTypeId()==2 || obudf.getFieldTypeId()==7) {
											if(udfRestRequestDTO.getUdfValue()!=null && obudf.getNumericLength()!=null) {												
												if(udfRestRequestDTO.getUdfValue().length() > Integer.parseInt(obudf.getNumericLength())) {
													errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValueError"), new ActionMessage("error.string.field.length.exceeded"));
												}else {
													udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
												}
											}else if(udfRestRequestDTO.getUdfValue()!=null && udfRestRequestDTO.getUdfValue().length() > 2000){
												errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValueError"), new ActionMessage("error.string.field.length.exceeded"));
											}else {
												udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
											}
										}else if(obudf.getFieldTypeId()==6){
											try {
												dateFormat.parse(udfRestRequestDTO.getUdfValue());
												udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
											} catch (ParseException e) {
												errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValueError"), new ActionMessage("error.date.format"));
											}
										}else {
											udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
										}
										udfDetailsRestRequestDTO.setUdfSequance(udfRestRequestDTO.getUdfSequance());
										udfDetailsRestRequestDTO.setUdfLabel(udfRestRequestDTO.getUdfLabel());
									}
								}
							}
						} else {
							errors.add("udfList_udfSequance", new ActionMessage("error.sequence.mandatory"));
						}
					} else {
						errors.add("udfList_moduleId", new ActionMessage("error.invalid.module.id"));
					}
				}else {
					errors.add("udfList_moduleId", new ActionMessage("error.mandatory"));
				}
					udfList.add(udfDetailsRestRequestDTO);
				}
			}
			return (List<UdfRestRequestDTO>) udfList;
		}
		
		else if (event.equals("WS_LINE_CREATE_REST_UDF2") || event.equals("WS_LINE_UPDATE_REST_UDF2")) {
			FacilityLineDetailRestRequestDTO requestDTO = (FacilityLineDetailRestRequestDTO) requestUdf;
			List<UdfRestRequestDTO> udfList = new LinkedList<UdfRestRequestDTO>();
			if (requestDTO.getUdf2List() != null && !requestDTO.getUdf2List().isEmpty()) {
				for (UdfRestRequestDTO udfRestRequestDTO : requestDTO.getUdf2List()) {
					UdfRestRequestDTO udfDetailsRestRequestDTO = new UdfRestRequestDTO();
					if (null != udfRestRequestDTO.getModuleId() && !udfRestRequestDTO.getModuleId().isEmpty()) {
					if (udfRestRequestDTO.getModuleId().equals("4")) {
						if (udfRestRequestDTO.getUdfSequance() != null
								&& !udfRestRequestDTO.getUdfSequance().trim().isEmpty()) {
							Object obj = masterObj.getObjectByEntityNameAndSequenceForRest("actualUdf",
									udfRestRequestDTO.getModuleId(), udfRestRequestDTO.getUdfSequance(),
									"udf2List ".concat(udfRestRequestDTO.getUdfSequance()), errors);
							List<String> al = new ArrayList<String>();
							if (!(obj instanceof ActionErrors)) {
								OBUdf obudf = (OBUdf) obj;
								if(udfRestRequestDTO.getUdfValue()!=null && udfRestRequestDTO.getUdfValue().trim().isEmpty()) {
									if(udfRestRequestDTO.getUdfSequance()!=null)
										errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValue"),new ActionMessage("udf value is mandatory"));
								}else {	
									if (obudf.getOptions() != null) {
										String str = obudf.getOptions();
										String strr[] = str.split(",");
										al = Arrays.asList(strr);
										if(null != udfRestRequestDTO.getUdfValue() && !udfRestRequestDTO.getUdfValue().isEmpty()) {
											if (al.contains(udfRestRequestDTO.getUdfValue())) {
												if (!(obj instanceof ActionErrors)) {
													udfDetailsRestRequestDTO.setUdfSequance(udfRestRequestDTO.getUdfSequance());
													udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
													udfDetailsRestRequestDTO.setUdfLabel(udfRestRequestDTO.getUdfLabel());
												}else {
													errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValueError"),new ActionMessage("udf value is invalid"));
												}
											} else {
												errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValue"),new ActionMessage("udf value is invalid"));
											}
										} else {
											errors.add("udf2List_udfValue", new ActionMessage("error.mandatory"));
										}
									}else {
										if(obudf.getFieldTypeId()==1 || obudf.getFieldTypeId()==2 || obudf.getFieldTypeId()==7) {
											if(udfRestRequestDTO.getUdfValue()!=null && obudf.getNumericLength()!=null) {												
												if(udfRestRequestDTO.getUdfValue().length() > Integer.parseInt(obudf.getNumericLength())) {
													errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValueError"), new ActionMessage("error.string.field.length.exceeded"));
												}else {
													udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
												}
											}else if(udfRestRequestDTO.getUdfValue()!=null && udfRestRequestDTO.getUdfValue().length() > 2000){
												errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValueError"), new ActionMessage("error.string.field.length.exceeded"));
											}else {
												udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
											}
										}else if(obudf.getFieldTypeId()==6){
											try {
												dateFormat.parse(udfRestRequestDTO.getUdfValue());
												udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
											} catch (ParseException e) {
												errors.add(udfRestRequestDTO.getUdfSequance().toString().concat("udfValueError"), new ActionMessage("error.date.format"));
											}
										}else {
											udfDetailsRestRequestDTO.setUdfValue(udfRestRequestDTO.getUdfValue().trim());
										}
										udfDetailsRestRequestDTO.setUdfSequance(udfRestRequestDTO.getUdfSequance());
										udfDetailsRestRequestDTO.setUdfLabel(udfRestRequestDTO.getUdfLabel());
									}
								}
							}
						} else {
							errors.add("udf2list_udfSequance", new ActionMessage("error.sequence.mandatory"));
						}
					} else {
						errors.add("udf2list_moduleId", new ActionMessage("error.invalid.module.id"));
					}
				}else {
					errors.add("udf2list_moduleId", new ActionMessage("error.mandatory"));
				}
					udfList.add(udfDetailsRestRequestDTO);
				}
			}
			return (List<UdfRestRequestDTO>) udfList;
		}
		
		return null;
	}

	public void getUdfFormFromDTO(Object requestUdf, CommonForm cForm, String event) {

		if (event.equals("Rest_create_customer") || event.equals("Rest_update_customer")) {
			PartyDetailsRestRequestDTO dto = (PartyDetailsRestRequestDTO) requestUdf;
			ManualInputCustomerInfoForm form = (ManualInputCustomerInfoForm) cForm;
			Class classObj = form.getClass();
			Method[] allMethods = classObj.getDeclaredMethods();
			if (dto.getBodyDetails().get(0).getUdfList() != null && !dto.getBodyDetails().get(0).getUdfList().isEmpty()) {
				for (UdfRestRequestDTO udfRestRequestDTO : dto.getBodyDetails().get(0).getUdfList()) {
					if(udfRestRequestDTO.getUdfSequance()!=null) {	
						String method = "setUdf".concat(udfRestRequestDTO.getUdfSequance());
						for (Method m : allMethods) {
							String methodName = m.getName();
							if (methodName.equals(method)) {
								try {
									if(udfRestRequestDTO.getUdfValue()!=null) {
										m.invoke(form, udfRestRequestDTO.getUdfValue());
									}
								} catch (InvocationTargetException e) {
									DefaultLogger.error(this, "Error in getUdfFormFromDTO method ->: "+e);
								} catch (IllegalAccessException e) {
									DefaultLogger.error(this, "Error in getUdfFormFromDTO method ->: "+e);								
								} catch (IllegalArgumentException e) {
									DefaultLogger.error(this, "Error in getUdfFormFromDTO method ->: "+e);	
								}
							}	
						}
					}
				}
			}			
		} else if (event.equals("REST_CAM_CREATE") || event.equals("REST_CAM_UPDATE")) {
			CamDetailsRestRequestDTO dto = (CamDetailsRestRequestDTO) requestUdf;
			AADetailForm form = (AADetailForm) cForm;
			Class classObj = form.getClass();
			Method[] allMethods = classObj.getDeclaredMethods();
			if (dto.getUdfList() != null && !dto.getUdfList().isEmpty()) {
				for (UdfRestRequestDTO udfRestRequestDTO : dto.getUdfList()) {
					if(udfRestRequestDTO.getUdfSequance()!=null) {
						String method = "setUdf".concat(udfRestRequestDTO.getUdfSequance());
						for (Method m : allMethods) {
							String methodName = m.getName();
							if (methodName.equals(method)) {
								try {
									if(udfRestRequestDTO.getUdfValue()!=null) {
										m.invoke(form, udfRestRequestDTO.getUdfValue());
									}
								} catch (InvocationTargetException e) {
									DefaultLogger.error(this, "Error in getUdfFormFromDTO method ->: "+e);
								} catch (IllegalAccessException e) {
									DefaultLogger.error(this, "Error in getUdfFormFromDTO method ->: "+e);								
								} catch (IllegalArgumentException e) {
									DefaultLogger.error(this, "Error in getUdfFormFromDTO method ->: "+e);	
								}
							}	
						}
					}
				}
			}
		}
		
		else if (event.equals("WS_LINE_CREATE_REST_UDF1") || event.equals("WS_LINE_UPDATE_REST_UDF1")) {
			FacilityLineDetailRestRequestDTO dto = (FacilityLineDetailRestRequestDTO) requestUdf;
			XRefDetailForm form = (XRefDetailForm) cForm;
			Class classObj = form.getClass();
			Method[] allMethods = classObj.getDeclaredMethods();
			if (dto.getUdfList() != null && !dto.getUdfList().isEmpty()) {
				for (UdfRestRequestDTO udfRestRequestDTO : dto.getUdfList()) {
					if(udfRestRequestDTO.getUdfSequance()!=null) {
						String method = "setUdf".concat(udfRestRequestDTO.getUdfSequance());
						for (Method m : allMethods) {
							String methodName = m.getName();
							//System.out.println("methodName 1st"+methodName);

							if (methodName.equals(method)) {
								try {
									if(udfRestRequestDTO.getUdfValue()!=null) {
										m.invoke(form, udfRestRequestDTO.getUdfValue());
									}
								} catch (InvocationTargetException e) {
									DefaultLogger.error(this, "Error in getUdfFormFromDTO method ->: "+e);
								} catch (IllegalAccessException e) {
									DefaultLogger.error(this, "Error in getUdfFormFromDTO method ->: "+e);								
								} catch (IllegalArgumentException e) {
									DefaultLogger.error(this, "Error in getUdfFormFromDTO method ->: "+e);	
								}
							}	
						}
					}
				}
			}
		}
	}

	public Object getUdfActualFromDTO(Object requestUdf, String event) {

		if (event.equals("Rest_create_customer") || event.equals("Rest_update_customer")) {
			PartyDetailsRestRequestDTO dto = (PartyDetailsRestRequestDTO) requestUdf;
			ICMSCustomerUdf[] udfList = new OBCMSCustomerUdf[1];
			ICMSCustomerUdf udf = new OBCMSCustomerUdf();
			Class classObj = udf.getClass();
			Method[] allMethods = classObj.getDeclaredMethods();
			if (dto.getBodyDetails().get(0).getUdfList() != null && !dto.getBodyDetails().get(0).getUdfList().isEmpty()) {
				for (UdfRestRequestDTO udfRestRequestDTO : dto.getBodyDetails().get(0).getUdfList()) {
					if(udfRestRequestDTO.getUdfSequance()!=null) {
						String method = "setUdf".concat(udfRestRequestDTO.getUdfSequance());
						for (Method m : allMethods) {
							String methodName = m.getName();
							if (methodName.equals(method)) {
								try {
									if(udfRestRequestDTO.getUdfValue()!=null) {
										m.invoke(udf, udfRestRequestDTO.getUdfValue());
									}
								} catch (InvocationTargetException e) {
									DefaultLogger.error(this, "Error in getUdfActualFromDTO method ->: "+e);								
								} catch (IllegalAccessException e) {
									DefaultLogger.error(this, "Error in getUdfActualFromDTO method ->: "+e);								
								} catch (IllegalArgumentException e) {
									DefaultLogger.error(this, "Error in getUdfActualFromDTO method ->: "+e);								
								}
							}
						}
					}					
				}
				udfList[0] = udf;
			}
			return(Object)udfList; 

		} else if (event.equals("REST_CAM_CREATE") || event.equals("REST_CAM_UPDATE")) {
			CamDetailsRestRequestDTO dto = (CamDetailsRestRequestDTO) requestUdf;
			ILimitProfileUdf[] udfList = new OBLimitProfileUdf[1];
			ILimitProfileUdf udf = new OBLimitProfileUdf();
			Class classObj = udf.getClass();
			Method[] allMethods = classObj.getDeclaredMethods();
			if (dto.getUdfList() != null && !dto.getUdfList().isEmpty()) {
				for (UdfRestRequestDTO udfRestRequestDTO : dto.getUdfList()) {
					if(udfRestRequestDTO.getUdfSequance()!=null) {
						String method = "setUdf".concat(udfRestRequestDTO.getUdfSequance());
						for (Method m : allMethods) {
							String methodName = m.getName();
							if (methodName.equals(method)) {
								try {
									if(udfRestRequestDTO.getUdfValue()!=null) {
										m.invoke(udf, udfRestRequestDTO.getUdfValue());
									}
								} catch (InvocationTargetException e) {
									DefaultLogger.error(this, "Error in getUdfActualFromDTO method ->: "+e);								
								} catch (IllegalAccessException e) {
									DefaultLogger.error(this, "Error in getUdfActualFromDTO method ->: "+e);								
								} catch (IllegalArgumentException e) {
									DefaultLogger.error(this, "Error in getUdfActualFromDTO method ->: "+e);								
								}	
							}
						}
					}	
				}
				udfList[0] = udf;
			}
			return(Object)udfList; 
		}
		
		else if (event.equals("WS_LINE_CREATE_REST_UDF1") || event.equals("WS_LINE_UPDATE_REST_UDF1")) {
			FacilityLineDetailRestRequestDTO dto = (FacilityLineDetailRestRequestDTO) requestUdf;
			ILimitXRefUdf[] udfList = new OBLimitXRefUdf[1];
			ILimitXRefUdf udf = new OBLimitXRefUdf();
			Class classObj = udf.getClass();
			Method[] allMethods = classObj.getDeclaredMethods();
			if (dto.getUdfList() != null && !dto.getUdfList().isEmpty()) {
				for (UdfRestRequestDTO udfRestRequestDTO : dto.getUdfList()) {
					if(udfRestRequestDTO.getUdfSequance()!=null) {
						String method = "setUdf".concat(udfRestRequestDTO.getUdfSequance()).concat("_Value");
						for (Method m : allMethods) {
							String methodName = m.getName();
							
							//System.out.println("methodName inside"+methodName);
							if (methodName.equals(method)) {
								try {
									if(udfRestRequestDTO.getUdfValue()!=null) {
										m.invoke(udf, udfRestRequestDTO.getUdfValue());
									}
									
								} catch (InvocationTargetException e) {
									DefaultLogger.error(this, "Error in getUdfActualFromDTO method ->: "+e);								
								} catch (IllegalAccessException e) {
									DefaultLogger.error(this, "Error in getUdfActualFromDTO method ->: "+e);								
								} catch (IllegalArgumentException e) {
									DefaultLogger.error(this, "Error in getUdfActualFromDTO method ->: "+e);								
								}	
							}
						}
					}	
				}
				udfList[0] = udf;
			}
			return(Object)udfList; 
		}
		
		else if (event.equals("WS_LINE_CREATE_REST_UDF2") || event.equals("WS_LINE_UPDATE_REST_UDF2")) {
			FacilityLineDetailRestRequestDTO dto = (FacilityLineDetailRestRequestDTO) requestUdf;
			ILimitXRefUdf2[] udfList = new OBLimitXRefUdf2[1];
			ILimitXRefUdf2 udf = new OBLimitXRefUdf2();
			Class classObj = udf.getClass();
			Method[] allMethods = classObj.getDeclaredMethods();
			if (dto.getUdf2List() != null && !dto.getUdf2List().isEmpty()) {
				for (UdfRestRequestDTO udfRestRequestDTO : dto.getUdf2List()) {
					if(udfRestRequestDTO.getUdfSequance()!=null) {
						int seqnum= Integer.parseInt(udfRestRequestDTO.getUdfSequance());
						seqnum=seqnum+115;
						String method = "setUdf".concat(String.valueOf(seqnum)).concat("_Value");
						for (Method m : allMethods) {
							String methodName = m.getName();
							
							//System.out.println("methodName inside"+methodName);
							if (methodName.equals(method)) {
								try {
									if(udfRestRequestDTO.getUdfValue()!=null) {
										m.invoke(udf, udfRestRequestDTO.getUdfValue());
									}
									
								} catch (InvocationTargetException e) {
									DefaultLogger.error(this, "Error in getUdfActualFromDTO method ->: "+e);								
								} catch (IllegalAccessException e) {
									DefaultLogger.error(this, "Error in getUdfActualFromDTO method ->: "+e);								
								} catch (IllegalArgumentException e) {
									DefaultLogger.error(this, "Error in getUdfActualFromDTO method ->: "+e);								
								}	
							}
						}
					}	
				}
				udfList[0] = udf;
			}
			return(Object)udfList; 
		}
		return null;
	}
}