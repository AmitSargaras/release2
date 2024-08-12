package com.integrosys.cms.ui.login;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import com.integrosys.base.businfra.login.LDAPConnection;
import com.integrosys.base.businfra.login.LDAPConstant;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

public class WinLDAPConnection extends LDAPConnection {

	private String _principal;
	private String _passwd;
	private String _host;
	private String _ldaproot;
	private String _port;
	private String _url;
	private String _initctx;
	private String _authenticationMode;
	
	public WinLDAPConnection() {
		super();
	}

	public WinLDAPConnection(String principal, String passwd) {
		_principal = principal;
		_passwd = passwd;
		_host = PropertyManager.getValue(LDAPConstant.HOST);
		_port = PropertyManager.getValue(LDAPConstant.PORT);
		_ldaproot = PropertyManager.getValue(LDAPConstant.LDAP_ROOT);
		_url = "ldap://" + _host + ":" + _port;// + "/" + _ldaproot;
		_initctx = PropertyManager.getValue(LDAPConstant.INIT_CTX);
		_authenticationMode = PropertyManager.getValue("integrosys.login.ldap.authMode","simple");
	}

	public DirContext getDirContext() throws NamingException {
		DirContext ctx = null;
		try {
			if (_principal == null) {
				_principal = "";
			}
			Hashtable env = new Hashtable();
		    env.put(Context.INITIAL_CONTEXT_FACTORY, _initctx);
		    env.put(Context.PROVIDER_URL, _url);
		    env.put(Context.SECURITY_AUTHENTICATION,_authenticationMode);
		    env.put(Context.SECURITY_PRINCIPAL,_principal.trim()+_ldaproot);
		    env.put(Context.SECURITY_CREDENTIALS, _passwd);
		    ctx = new InitialDirContext(env);
		    DefaultLogger.debug(this,ctx);
		    ctx.close();
		  } 
		catch(NamingException ne) {
			throw ne;
		}
		DefaultLogger.debug(this,"User "+_principal+"has been sucessfully logged in");
		return ctx;
	}
	
	
}
