package org.mlorenzo.apifirst.apifirstserver.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    // Para que se almacene como un String en vez de como un dato binario
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "char(36)", updatable = false, nullable = false)
    private UUID id;

    @Size(min=3,max=255)
    private String email;

    @Size(min=7,max=15)
    private String phone;

    @CreationTimestamp
    private OffsetDateTime dateCreated;

    @UpdateTimestamp
    private OffsetDateTime dateUpdated;

    @NotNull
    @Embedded
    private Name name;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Address shipToAddress;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Address billToAddress;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<PaymentMethod> paymentMethods;

    @PrePersist
    public void prePersist() {
        if(this.paymentMethods != null && !this.paymentMethods.isEmpty())
            this.paymentMethods.forEach(paymentMethod -> paymentMethod.setCustomer(this));
    }
}
