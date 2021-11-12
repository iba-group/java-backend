package com.ibagroup.petstore.controller.store;

import com.ibagroup.petstore.dto.order.OrderDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ibagroup.petstore.model.order.Order;
import com.ibagroup.petstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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

  private final OrderService service;

  @Autowired
  public StoreController(OrderService service) {
    this.service = service;
  }
  
  @GetMapping("/all")
  public List<Order> findAll() {
    return service.findAll();
  }

  @GetMapping("/quantity/{quantity}")
  public List<Order> findAllByQuantity(@PathVariable("quantity") Integer quantity) {
    return service.findAllByQuantity(quantity);
  }

  @PostMapping(value = "/order",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createOrder(@RequestBody OrderDto order) {
    Order entity = service.createOrder(order);
    return ResponseEntity.ok(entity);
  }

  @GetMapping(value = "/order/{orderId}")
  public ResponseEntity getOrderById(@PathVariable("orderId") Long id) {
    Order order = service.findById(id);
    System.out.println(order.getPet());
    System.out.println();
    return ResponseEntity.ok(order);
  }

  @DeleteMapping(value = "/order/{orderId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity deleteOrder(@PathVariable Long orderId) {
    Optional<OrderDto> orderDto = Optional.ofNullable(orders.remove(orderId));
    return orderDto.isPresent() ? ResponseEntity.ok(orderDto.get())
        : ResponseEntity.noContent().build();
  }

}
