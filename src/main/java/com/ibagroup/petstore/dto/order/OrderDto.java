package com.ibagroup.petstore.dto.order;

import com.ibagroup.petstore.model.order.OrderStatus;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OrderDto {

  private Long id;
  private Long petId;
  private Integer quantity;
  private LocalDateTime shipDate;
  private OrderStatus status;
  private boolean complete;

}
