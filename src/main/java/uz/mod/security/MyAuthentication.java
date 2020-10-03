package uz.mod.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MyAuthentication extends AbstractAuthenticationToken {

    private Object credentials;
    private Object principal;

    public MyAuthentication(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public void setCredentials(Object credentials) {
        this.credentials = credentials;
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }
}
