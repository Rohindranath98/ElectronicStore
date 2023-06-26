package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.entites.Order;
import com.lcwd.electronic.store.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,String> {

   List<Order> findByUser(User user);
}
