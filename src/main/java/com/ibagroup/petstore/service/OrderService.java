package com.ibagroup.petstore.service;

import com.ibagroup.petstore.dto.order.OrderDto;
import com.ibagroup.petstore.model.order.Order;
import com.ibagroup.petstore.model.order.OrderStatus;
import com.ibagroup.petstore.repo.OrderRepo;
import com.ibagroup.petstore.repo.PetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepo repo;

    private final PetRepo petRepo;

    @Autowired
    public OrderService(OrderRepo repo, PetRepo petRepo) {
        this.repo = repo;
        this.petRepo = petRepo;
    }

    @Transactional
    public Order createOrder(OrderDto order) {
        Order entity = new Order();
        entity.setQuantity(order.getQuantity());
        entity.setShipDate(order.getShipDate());
        entity.setStatus(OrderStatus.NEW);
        entity.setPet(petRepo.findById(4L).get());

        repo.save(entity);
        if (entity.getStatus().equals(OrderStatus.NEW)) {
            throw new RuntimeException();
        }
        //some logic
        return entity;
    }

    public List<Order> findAll() {
        return repo.findAll();
    }

    public List<Order> findAllByQuantity(Integer quantity) {
        System.out.println(repo.countAllByQuantity(quantity));
        return repo.findAllByQuantityAndStatusPlaced(quantity);
    }

    public Order findById(Long id) {
        repo.findById(id).get();
        return repo.findById(id).get();
    }
}
