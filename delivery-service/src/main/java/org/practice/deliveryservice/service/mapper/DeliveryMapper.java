package org.practice.deliveryservice.service.mapper;

import org.mapstruct.Mapper;
import org.practice.deliveryservice.dto.DeliveryResponseDto;
import org.practice.deliveryservice.dto.UpdatedDeliveryDto;
import org.practice.deliveryservice.model.Delivery;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    DeliveryResponseDto toCreatedDeliveryDto(Delivery delivery);
    UpdatedDeliveryDto toUpdatedDeliveryDto(Delivery delivery);

}
