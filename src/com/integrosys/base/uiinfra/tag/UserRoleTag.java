package com.integrosys.base.uiinfra.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;

public class UserRoleTag extends TagSupport {

	protected String role;
	protected String moduleId;
	protected String operation;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public int doStartTag() throws JspException {
		try {
			String retValue = "-";
			ILimitDAO dao = (ILimitDAO) BeanHouse.get("limitJdbcDao");

			if (this.getRole() != null && this.getModuleId() != null && this.getOperation() != null) {
				retValue = dao.checkUserRoleAccess(this.getRole(), this.getModuleId(), this.getOperation());
			} else {
				retValue = "-";
			}

			if(retValue.equalsIgnoreCase("Y")) {
				return EVAL_BODY_INCLUDE;
			}
			//pageContext.getOut().print(retValue);

		} catch (Exception e) {
			e.printStackTrace();
			throw new JspException("IOException");
		}
		return SKIP_BODY;
	}
}