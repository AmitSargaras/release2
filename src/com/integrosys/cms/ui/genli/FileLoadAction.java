package com.integrosys.cms.ui.genli;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.generateli.proxy.GenerateLiDocProxyManagerFactory;
import com.integrosys.cms.app.generateli.proxy.IGenerateLiDocProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.login.ui.GlobalSessionConstant;
import com.integrosys.component.login.ui.IPageReferences;
import com.integrosys.component.user.app.bus.OBCommonUser;

/**
 * Web controller to interface with force password change module or event.
 *
 * @author Khoo Chang Yew
 * @since 12 Nov 2008
 */
public class FileLoadAction extends DownloadAction implements IPageReferences {

	public static final String SAVE_VELOCITY_FLAG = "velocity.data.filesaving.enable";
	public static final String VELOCITY_FILE_PATH = "velocity.data.filesaving.path";

	public static final String LI_LETTER_INSURER = "Letter of Instruction to Insurers";
	public static final String LI_LETTER_SOLICITOR = "Letter of Instruction to Solicitors";
	public static final String LI_LETTER_VALUER = "Letter of Instruction to Valuers";

	public static final String TEMPLATE_INSURER = "LI_INSURER";
	public static final String TEMPLATE_SOLICITOR = "LI_SOLICITOR";
	public static final String TEMPLATE_VALUER = "LI_VALUER";



	String contentType = "text/plain";
	String rtffileName = null;
	boolean saveVelocityFlag = PropertyManager.getBoolean(this.SAVE_VELOCITY_FLAG);
	String velocityFilePath = PropertyManager.getValue(this.VELOCITY_FILE_PATH) + "/";



	protected StreamInfo getStreamInfo(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{


		String checklistId = request.getParameter("checklistId");
		IGenerateLiDocProxyManager proxy = GenerateLiDocProxyManagerFactory.getProxyManager();
		ICheckListProxyManager checkListProxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		ICheckList checkList = checkListProxy.getCheckListByID(new Long(checklistId).longValue());

		if(checkList != null)
		{

			IPropertyCollateral collateral = null;
			HttpSession session = request.getSession(true);
			//HashMap lmtcolmap = new HashMap();
			OBCommonUser curUser = (OBCommonUser) session.getAttribute(GlobalSessionConstant.LOS_USER);
			OBCMSCustomer cmsUser = (OBCMSCustomer) session.getAttribute("global."+IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			ILimitProfile limitProfileOB = (ILimitProfile) session.getAttribute("global."+IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			//lmtcolmap = limitProxy.getCollateralLimitMap(limitProfileOB);
			String liTemplateName = getLetterTemplateName(request.getParameter("template"), limitProfileOB);

			if(rtffileName!= null)
			{
				String fileLocation = velocityFilePath+request.getParameter("checklistId")+rtffileName;


				ICollateralProxy collateralProxy = CollateralProxyFactory.getProxy();
				OBCollateralCheckListOwner chklistOwner = (OBCollateralCheckListOwner) checkList.getCheckListOwner();
				ICollateral aCollateral = collateralProxy.getCollateral(chklistOwner.getCollateralID(), false);
				if(aCollateral instanceof IPropertyCollateral)
				{
					collateral = (IPropertyCollateral)aCollateral;
				}

				byte result[] = generateVelocityRtf(checklistId, cmsUser , limitProfileOB , checkList , collateral);
				proxy.saveOrUpdateDocItem(checklistId, fileLocation, liTemplateName, curUser.getLoginID());

				if(!saveVelocityFlag)
				{
					// If saveVelocityFlag flag is turned off , it will save the file on the designated directory
			        contentType = "application/rtf";
			        response.setHeader("Content-disposition", "attachment; filename=" + rtffileName);
			        return new BI(contentType,result);
				}
				else
				{
					// If saveVelocityFlag flag is turned on , it will save the file on the designated directory
					File aFile = new File(velocityFilePath+"success.txt");
					OutputStream f0 = new FileOutputStream(velocityFilePath+request.getParameter("checklistId")+rtffileName);
					f0.write(result);
					f0.close();
					return new SI(contentType,aFile);
				}
			}
			else
			{
				File aFile = new File(velocityFilePath+"fail.txt");
				return new SI(contentType,aFile);
			}
		}

		return null;

	}


	private byte[] generateVelocityRtf(String checklistId , OBCMSCustomer cmsUser , ILimitProfile limitProfileOB , ICheckList checkList , IPropertyCollateral collateral)
	{
        IContact[] contactList = (IContact[]) cmsUser.getOfficialAddresses();

        VelocityEngine velocityEngine = (VelocityEngine) BeanHouse.get("velocityEngine");
        VelocityContext ctx = new VelocityContext();

        ctx.put("cmsUser", cmsUser);
        ctx.put("idNo", cmsUser.getCMSLegalEntity().getLegalRegNumber() == null ? "-" : cmsUser.getCMSLegalEntity().getLegalRegNumber());

        String originalLocation = "-";
        if (cmsUser.getOriginatingLocation() != null) {
            originalLocation = CommonCodeList.getInstance(null, ICMSConstant.CATEGORY_CODE_BKGLOC).getCommonCodeLabel(cmsUser.getOriginatingLocation().getOrganisationCode());            
        }
        ctx.put("oriLocation", originalLocation);
        
        if (collateral != null) {
            ctx.put("colSubType", collateral.getCollateralSubType().getSubTypeName() == null ? "-" : collateral.getCollateralSubType().getSubTypeName());
            ctx.put("colAddr", collateral.getPropertyAddress() == null ? "-" : collateral.getPropertyAddress());
            ctx.put("colDevName", collateral.getDeveloperName() == null ? "-" : collateral.getDeveloperName());
            ctx.put("titleNo", collateral.getTitleNumber() == null ? "-" : collateral.getTitleNumber());
            ctx.put("titleType", collateral.getTitleType() == null ? "-" : collateral.getTitleType());
            ctx.put("landArea", new Double(collateral.getLandArea()).toString());
            ctx.put("landAreaUOM", collateral.getLandAreaUOM() == null ? "-" : collateral.getLandAreaUOM());
        } else {
            ctx.put("colSubType", "-");
            ctx.put("colAddr", "-");
            ctx.put("colDevName", "-");
            ctx.put("titleNo", "-");
            ctx.put("titleType", "-");
            ctx.put("landArea", "-");
            ctx.put("landAreaUOM", "-");
        }        

        ctx.put("checkList", checkList);
        
        if (contactList != null) {
            ctx.put("contactNo", contactList[0].getTelephoneNumer());
            ctx.put("addrLine1", contactList[0].getAddressLine1());
            ctx.put("addrLine2", contactList[0].getAddressLine2());
        } else {
            ctx.put("contactNo", "-");
            ctx.put("addrLine1", "-");
            ctx.put("addrLine2", "-");
        }
        
        ctx.put("currentTime", DateUtil.convertToDisplayDate(new Date()));

        StringWriter sw = new StringWriter();
        try {
            velocityEngine.mergeTemplate(rtffileName, ctx, sw);
        } catch (Exception e) {
        	DefaultLogger.debug(this,"Error cause : " + e.getCause());
        }

        return sw.toString().getBytes();
	}

	private String getLetterTemplateName(String template , ILimitProfile limitProfileOB)
	{
		if(template.equalsIgnoreCase(TEMPLATE_INSURER))
		{

			if(ICMSConstant.APPLICATION_TYPE_MO.equalsIgnoreCase(limitProfileOB.getApplicationType()))
			{
				if(ICMSConstant.AA_LAW_TYPE_ISLAM.equalsIgnoreCase(limitProfileOB.getApplicationLawType()))
					this.rtffileName = "LI-Insurer-Islamic(ML).rtf";
				else if(ICMSConstant.AA_LAW_TYPE_CONVENTIONAL.equalsIgnoreCase(limitProfileOB.getApplicationLawType()))
					this.rtffileName = "LI-Insurer-Conventional(ML).rtf";
			}
			else if(ICMSConstant.APPLICATION_TYPE_CO.equalsIgnoreCase(limitProfileOB.getApplicationType()) || ICMSConstant.APPLICATION_TYPE_COM.equalsIgnoreCase(limitProfileOB.getApplicationType()))
			{
				if(ICMSConstant.AA_LAW_TYPE_CONVENTIONAL.equalsIgnoreCase(limitProfileOB.getApplicationLawType()) || ICMSConstant.AA_LAW_TYPE_ISLAM.equalsIgnoreCase(limitProfileOB.getApplicationLawType()))
					this.rtffileName = "LI-Insurer-Conv(C&C).rtf";
			}

			//this.rtffileName = "LI-Valuer-Conv(Comm).rtf";


			return LI_LETTER_INSURER;
		}
		if(template.equalsIgnoreCase(TEMPLATE_SOLICITOR))
		{
			if(ICMSConstant.APPLICATION_TYPE_MO.equalsIgnoreCase(limitProfileOB.getApplicationType()))
			{
				if(ICMSConstant.AA_LAW_TYPE_ISLAM.equalsIgnoreCase(limitProfileOB.getApplicationLawType()))
					this.rtffileName = "LI-Solicitor-Islamic(ML).rtf";
				else if(ICMSConstant.AA_LAW_TYPE_CONVENTIONAL.equalsIgnoreCase(limitProfileOB.getApplicationLawType()))
					this.rtffileName = "LI-Solicitor-Conv(ML).rtf";
			}
			else if(ICMSConstant.APPLICATION_TYPE_CO.equalsIgnoreCase(limitProfileOB.getApplicationType()) || ICMSConstant.APPLICATION_TYPE_COM.equalsIgnoreCase(limitProfileOB.getApplicationType()))
			{
				if(ICMSConstant.AA_LAW_TYPE_ISLAM.equalsIgnoreCase(limitProfileOB.getApplicationLawType()))
					this.rtffileName = "LI-Solicitor-Islamic(C&C).rtf";
				else if(ICMSConstant.AA_LAW_TYPE_CONVENTIONAL.equalsIgnoreCase(limitProfileOB.getApplicationLawType()))
					this.rtffileName = "LI-Solicitor-Conv(C&C).rtf";
			}

			//this.rtffileName = "LI-Valuer-Conv(Comm).rtf";


			return LI_LETTER_SOLICITOR;
		}

		if(template.equalsIgnoreCase(TEMPLATE_VALUER))
		{
			if(ICMSConstant.APPLICATION_TYPE_MO.equalsIgnoreCase(limitProfileOB.getApplicationType()))
			{
				if(ICMSConstant.AA_LAW_TYPE_ISLAM.equalsIgnoreCase(limitProfileOB.getApplicationLawType()))
					this.rtffileName = "LI-Valuer-Islamic(ML).rtf";
				else if(ICMSConstant.AA_LAW_TYPE_CONVENTIONAL.equalsIgnoreCase(limitProfileOB.getApplicationLawType()))
					this.rtffileName = "LI-Valuer-Conv(ML).rtf";
			}
			else if(ICMSConstant.APPLICATION_TYPE_CO.equalsIgnoreCase(limitProfileOB.getApplicationType()))
			{
				if(ICMSConstant.AA_LAW_TYPE_CONVENTIONAL.equalsIgnoreCase(limitProfileOB.getApplicationLawType()) || ICMSConstant.AA_LAW_TYPE_ISLAM.equalsIgnoreCase(limitProfileOB.getApplicationLawType()))
					this.rtffileName = "LI-Valuer-Conv(Corp).rtf";
			}
			else if(ICMSConstant.APPLICATION_TYPE_COM.equalsIgnoreCase(limitProfileOB.getApplicationType()))
			{
				if(ICMSConstant.AA_LAW_TYPE_CONVENTIONAL.equalsIgnoreCase(limitProfileOB.getApplicationLawType()) || ICMSConstant.AA_LAW_TYPE_ISLAM.equalsIgnoreCase(limitProfileOB.getApplicationLawType()))
					this.rtffileName = "LI-Valuer-Conv(Comm).rtf";
			}

			//this.rtffileName = "LI-Valuer-Conv(Comm).rtf";

			return LI_LETTER_VALUER;
		}


		return null;
	}








}
