package com.tadanoluka.project1.security.ldap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.naming.Name;
import lombok.Getter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.util.Assert;

public class CustomLdapUserDetailsImpl extends LdapUserDetailsImpl implements CustomLdapUserDetails {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private String dn;

    private String password;

    private String username;

    @Getter
    private String commonName;

    private Collection<GrantedAuthority> authorities = AuthorityUtils.NO_AUTHORITIES;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

    // PPolicy data
    private int timeBeforeExpiration = Integer.MAX_VALUE;

    private int graceLoginsRemaining = Integer.MAX_VALUE;

    protected CustomLdapUserDetailsImpl() {}

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getDn() {
        return this.dn;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public int getTimeBeforeExpiration() {
        return this.timeBeforeExpiration;
    }

    @Override
    public int getGraceLoginsRemaining() {
        return this.graceLoginsRemaining;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CustomLdapUserDetailsImpl) {
            return this.dn.equals(((CustomLdapUserDetailsImpl) obj).dn);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.dn.hashCode();
    }

    @Override
    public String toString() {
        String sb =
            getClass().getSimpleName() +
            " [" +
            "Dn=" +
            this.dn +
            "; " +
            "Username=" +
            this.username +
            "; " +
            "Password=[PROTECTED]; " +
            "CommonName=" +
            this.commonName +
            "; " +
            "Enabled=" +
            this.enabled +
            "; " +
            "AccountNonExpired=" +
            this.accountNonExpired +
            "; " +
            "CredentialsNonExpired=" +
            this.credentialsNonExpired +
            "; " +
            "AccountNonLocked=" +
            this.accountNonLocked +
            "; " +
            "Granted Authorities=" +
            getAuthorities() +
            "]";
        return sb;
    }

    /**
     * Variation of essence pattern. Used to create mutable intermediate object
     */
    public static class Essence {

        protected CustomLdapUserDetailsImpl instance = createTarget();

        private List<GrantedAuthority> mutableAuthorities = new ArrayList<>();

        public Essence() {}

        public Essence(DirContextOperations ctx) {
            setDn(ctx.getDn());
        }

        public Essence(CustomLdapUserDetails copyMe) {
            setDn(copyMe.getDn());
            setUsername(copyMe.getUsername());
            setPassword(copyMe.getPassword());
            setCommonName(copyMe.getCommonName());
            setEnabled(copyMe.isEnabled());
            setAccountNonExpired(copyMe.isAccountNonExpired());
            setCredentialsNonExpired(copyMe.isCredentialsNonExpired());
            setAccountNonLocked(copyMe.isAccountNonLocked());
            setAuthorities(copyMe.getAuthorities());
        }

        protected CustomLdapUserDetailsImpl createTarget() {
            return new CustomLdapUserDetailsImpl();
        }

        /**
         * Adds the authority to the list, unless it is already there, in which case it is
         * ignored
         */
        public void addAuthority(GrantedAuthority a) {
            if (!hasAuthority(a)) {
                this.mutableAuthorities.add(a);
            }
        }

        private boolean hasAuthority(GrantedAuthority a) {
            for (GrantedAuthority authority : this.mutableAuthorities) {
                if (authority.equals(a)) {
                    return true;
                }
            }
            return false;
        }

        public LdapUserDetails createUserDetails() {
            Assert.notNull(this.instance, "Essence can only be used to create a single instance");
            Assert.notNull(this.instance.username, "username must not be null");
            Assert.notNull(this.instance.getDn(), "Distinguished name must not be null");
            this.instance.authorities = Collections.unmodifiableList(this.mutableAuthorities);
            LdapUserDetails newInstance = this.instance;
            this.instance = null;
            return newInstance;
        }

        public Collection<GrantedAuthority> getGrantedAuthorities() {
            return this.mutableAuthorities;
        }

        public void setAccountNonExpired(boolean accountNonExpired) {
            this.instance.accountNonExpired = accountNonExpired;
        }

        public void setAccountNonLocked(boolean accountNonLocked) {
            this.instance.accountNonLocked = accountNonLocked;
        }

        public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.mutableAuthorities = new ArrayList<>();
            this.mutableAuthorities.addAll(authorities);
        }

        public void setCredentialsNonExpired(boolean credentialsNonExpired) {
            this.instance.credentialsNonExpired = credentialsNonExpired;
        }

        public void setDn(String dn) {
            this.instance.dn = dn;
        }

        public void setDn(Name dn) {
            this.instance.dn = dn.toString();
        }

        public void setEnabled(boolean enabled) {
            this.instance.enabled = enabled;
        }

        public void setPassword(String password) {
            this.instance.password = password;
        }

        public void setUsername(String username) {
            this.instance.username = username;
        }

        public void setCommonName(String commonName) {
            this.instance.commonName = commonName;
        }

        public void setTimeBeforeExpiration(int timeBeforeExpiration) {
            this.instance.timeBeforeExpiration = timeBeforeExpiration;
        }

        public void setGraceLoginsRemaining(int graceLoginsRemaining) {
            this.instance.graceLoginsRemaining = graceLoginsRemaining;
        }
    }
}
