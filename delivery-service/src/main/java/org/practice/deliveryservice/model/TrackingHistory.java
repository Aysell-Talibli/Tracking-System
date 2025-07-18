package org.practice.deliveryservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.practice.deliveryservice.model.enums.DeliveryStatus;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="tracking_history")
public class TrackingHistory {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private DeliveryStatus status;

    @Column(name="location")
    private String location;

    @Column(name="updatedAt")
    private LocalDateTime updatedAt;

    @Column(name="createdAt")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="delivery_id", nullable=false)
    private Delivery delivery;

}
