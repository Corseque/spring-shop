package ru.spring.shop.entity.security;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="authority")
@EntityListeners(AuditingEntityListener.class)
public class Authority implements GrantedAuthority {

    static final long serialVersionUID = -2724480675420854356L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission")
    private String permission;

    @ManyToMany(mappedBy = "authorities")
    private Set<AccountRole> role;

    @Override
    public String getAuthority() {
        return this.permission;
    }
}
