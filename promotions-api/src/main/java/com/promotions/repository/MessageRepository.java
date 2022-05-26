package com.promotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.promotions.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}
