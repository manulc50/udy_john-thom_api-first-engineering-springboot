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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    // Para que se almacene como un String en vez de como un dato binario
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "char(36)", updatable = false, nullable = false)
    private UUID id;

    @Size(min=1,max=255)
    private String shipmentInfo;

    @NotNull
    // Indicamos a Lombok que utilice esta inicialización para su patrón Builder
    @Builder.Default
    // Para que en la base de datos se almacene el nombre del valor de la enumeración en vez de la posición(opción por defecto)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.NEW;

    @CreationTimestamp
    private OffsetDateTime dateCreated;

    @UpdateTimestamp
    private OffsetDateTime dateUpdated;

    @NotNull
    @ManyToOne
    private Customer customer;

    @NotNull
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> orderLines;

    @ManyToOne
    private PaymentMethod selectedPaymentMethod;
}
