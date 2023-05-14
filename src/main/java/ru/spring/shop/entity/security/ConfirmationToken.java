package ru.spring.shop.entity.security;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "confirmation_token")
@EntityListeners(AuditingEntityListener.class)
public class ConfirmationToken{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "created_date")
    private Date created_date;

    @OneToOne(targetEntity = AccountUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private AccountUser accountUser;

    public ConfirmationToken(AccountUser accountUser) {
        this.token = UUID.randomUUID().toString();
        this.created_date = new Date();
        this.accountUser = accountUser;
    }
}
