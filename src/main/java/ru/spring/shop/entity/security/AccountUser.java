package ru.spring.shop.entity.security;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import ru.spring.shop.entity.common.InfoEntity;
import ru.spring.shop.entity.enums.AccountStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Validated
@Table(name = "account_user")
@EntityListeners(AuditingEntityListener.class)
public class AccountUser extends InfoEntity implements UserDetails {

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;

    @Singular
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<AccountRole> roles;

    //todo менять при введении кода активации на true (при регитсрации false)
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired ;
    private boolean enabled;


    public AccountUser() {
    }

    @Builder
    public AccountUser(Long id, int version, String created_by, LocalDateTime created_date, String last_modified_by,
                       LocalDateTime last_modified_date, String username, String password,
                       String firstname, String lastname, String email, String phone, AccountStatus status,
                       Set<AccountRole> roles, boolean accountNonExpired, boolean accountNonLocked,
                       boolean credentialsNonExpired, boolean enabled) {
        super(id, version, created_by, created_date, last_modified_by, last_modified_date);
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.roles = roles;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }


    @Override
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = roles.stream()
                .map(AccountRole::getAuthorities)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        authorities.addAll(roles.stream()
                .map(role ->new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList()));
        return authorities;
    }


}
