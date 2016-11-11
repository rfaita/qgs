package com.qgs.util.security;

import com.qgs.model.Usuario;
import com.qgs.model.UsuarioProvedor;
import com.qgs.service.UsuarioProvedorService;
import com.qgs.service.UsuarioService;
import java.io.IOException;
import java.security.Principal;
import java.security.acl.Group;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import org.jboss.security.SimpleGroup;
import org.jboss.security.auth.spi.AbstractServerLoginModule;

/**
 *
 * @author rafael
 */
public class QGSLoginModule extends AbstractServerLoginModule {

    private static final Logger LOG = Logger.getLogger(QGSLoginModule.class.getName());
    private UsuarioProvedorService usuarioProvedorService;
    private UsuarioService usuarioService;
    private Principal identity;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {

        LOG.log(Level.INFO, "QGSLoginModule.initialize()");
        super.initialize(subject, callbackHandler, sharedState, options);
        try {
            this.usuarioProvedorService = (UsuarioProvedorService) InitialContext.doLookup("java:module/UsuarioProvedorService");
            this.usuarioService = (UsuarioService) InitialContext.doLookup("java:module/UsuarioService");
        } catch (NamingException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public boolean login() throws LoginException {
        LOG.log(Level.INFO, "QGSLoginModule.login()");
        loginOk = false;
        // Setup default callback handlers.
        Callback[] callbacks = new Callback[]{new NameCallback("Username: "), new PasswordCallback("Password: ", false)};

        try {
            callbackHandler.handle(callbacks);
        } catch (IOException | UnsupportedCallbackException ex) {
            LoginException le = new LoginException("Problemas na carga dos paramêtros de login.");
            le.setStackTrace(ex.getStackTrace());
            throw le;
        }

        String usuario = ((NameCallback) callbacks[0]).getName();
        String senha = new String(((PasswordCallback) callbacks[1]).getPassword());

        Usuario us = usuarioService.findUsuarioByUsuario(usuario);
        if (us != null) {
            if (!us.getSenha().toUpperCase().equals(senha.toUpperCase())) {
                throw new LoginException("Usuário e/ou senha inválidos.");
            } else {
                try {
                    identity = new QGSPrincipal(us);
                } catch (Exception e) {
                    LOG.log(Level.SEVERE, e.getMessage(), e);
                    throw new LoginException("Impossível realizar o login.");
                }
                loginOk = true;
                return true;
            }
        } else {
            UsuarioProvedor u = usuarioProvedorService.findUsuarioProvedorByUsuario(usuario);

            if (u == null || !u.getSenha().toUpperCase().equals(senha.toUpperCase())) {
                throw new LoginException("Usuário e/ou senha inválidos.");
            }

            try {
                identity = new QGSEmpresaPrincipal(u);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, e.getMessage(), e);
                throw new LoginException("Impossível realizar o login.");
            }
            loginOk = true;
            return true;
        }

    }

    @Override
    public boolean commit() throws LoginException {
        LOG.log(Level.INFO, "QGSLoginModule.commit()");
        return super.commit();
    }

    @Override
    public boolean abort() throws LoginException {
        LOG.log(Level.INFO, "QGSLoginModule.abort()");
        logout();
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        LOG.log(Level.INFO, "QGSLoginModule.logout()");
        return super.logout();
    }

    @Override
    protected Principal getIdentity() {
        return identity;
    }

    @Override
    protected Group[] getRoleSets() throws LoginException {
        if (getIdentity() instanceof QGSEmpresaPrincipal) {
            return new Group[]{new SimpleGroup(((QGSEmpresaPrincipal) getIdentity()).getUsuarioProvedor().getRole())};
        } else {
            return new Group[]{new SimpleGroup(((QGSPrincipal) getIdentity()).getUsuario().getRole())};
        }
    }

}
