package com.promotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.promotions.entities.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

}
