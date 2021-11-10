package com.ibagroup.petstore.controller.store;

import com.ibagroup.petstore.dto.order.OrderDto;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v2/store")
public class StoreController {

  private Map<Long, OrderDto> orders = new HashMap<>();

  @PostMapping(value = "/order",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createOrder(@RequestBody OrderDto order) {
    long size = orders.size();
    orders.put(size, order);
    order.setId(size);
    return ResponseEntity.ok(order);
  }

  @GetMapping(value = "/order/{orderId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity getOrderById(@PathVariable Long orderId) {
    OrderDto orderDto = orders.get(orderId);
    return ResponseEntity.ok(orderDto);
  }

  @DeleteMapping(value = "/order/{orderId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity deleteOrder(@PathVariable Long orderId) {
    Optional<OrderDto> orderDto = Optional.ofNullable(orders.remove(orderId));
    return orderDto.isPresent() ? ResponseEntity.ok(orderDto.get())
        : ResponseEntity.noContent().build();
  }

}
