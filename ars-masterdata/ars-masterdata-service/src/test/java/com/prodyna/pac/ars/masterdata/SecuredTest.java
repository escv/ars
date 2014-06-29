package com.prodyna.pac.ars.masterdata;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;

import org.junit.After;
import org.junit.Before;

public class SecuredTest {

	protected LoginContext loginContext;
	
	@Before
	public void login() throws Exception {
        CallbackHandler cbh = new SecuredTest.NamePasswordCallbackHandler("john", "secret");
        //Configuration config = new JBossJaasConfiguration(configurationName);
        //new LoginContext("demo", new Subject(), cbh, new JBossJaasConfiguration("demo"));
        loginContext =  new LoginContext("demo", new Subject(), cbh, new JBossJaasConfiguration("demo"));
		//loginContext = JBossLoginContextFactory.createLoginContext("john", "secret");   
	    loginContext.login();
	}
	
	@After
	public void logout() throws Exception {
		loginContext.logout();
	}
	
	static class NamePasswordCallbackHandler implements CallbackHandler {
        private final String username;
        private final String password;

        private NamePasswordCallbackHandler(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
            for (Callback current : callbacks) {
                if (current instanceof NameCallback) {
                    ((NameCallback) current).setName(username);
                } else if (current instanceof PasswordCallback) {
                    ((PasswordCallback) current).setPassword(password.toCharArray());
                } else {
                    throw new UnsupportedCallbackException(current);
                }
            }
        }
    }
	
	static class JBossJaasConfiguration extends Configuration {
        private final String configurationName;

        JBossJaasConfiguration(String configurationName) {
            this.configurationName = configurationName;
        }

        @Override
        public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
            if (!configurationName.equals(name)) {
                throw new IllegalArgumentException("Unexpected configuration name '" + name + "'");
            }

            return new AppConfigurationEntry[] {

            createUsersRolesLoginModuleConfigEntry(),

            createClientLoginModuleConfigEntry(),

            };
        }

        /**
         * The {@link org.jboss.security.auth.spi.UsersRolesLoginModule} creates the association between users and
         * roles.
         * 
         * @return
         */
        private AppConfigurationEntry createUsersRolesLoginModuleConfigEntry() {
            Map<String, String> options = new HashMap<String, String>();
            return new AppConfigurationEntry("org.jboss.security.auth.spi.UsersRolesLoginModule",
                    AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, options);
        }

        /**
         * The {@link org.jboss.security.ClientLoginModule} associates the user credentials with the
         * {@link org.jboss.security.SecurityContext} where the JBoss security runtime can find it.
         * 
         * @return
         */
        private AppConfigurationEntry createClientLoginModuleConfigEntry() {
            Map<String, String> options = new HashMap<String, String>();
            options.put("multi-threaded", "true");
            options.put("restore-login-identity", "true");

            return new AppConfigurationEntry("org.jboss.security.ClientLoginModule",
                    AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, options);
        }
    }

	
	
}
