package com.ibagroup.petstore.repo;

import com.ibagroup.petstore.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {

    @Query(value = "select o from Order o where o.quantity = :quantity and o.status = 'PLACED'")
    List<Order> findAllByQuantityAndStatusPlaced(Integer quantity);

    long countAllByQuantity(Integer quantity);

}
