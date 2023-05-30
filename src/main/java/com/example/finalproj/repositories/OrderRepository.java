package com.example.finalproj.repositories;

import com.example.finalproj.models.Order;
import com.example.finalproj.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByPerson(Person person);
    List<Order> findByNumber(String number);

    List<Order> findByNumberContainingOrderByDateTimeDesc(String number);
    List<Order> findAllByOrderByDateTimeDesc();
}
