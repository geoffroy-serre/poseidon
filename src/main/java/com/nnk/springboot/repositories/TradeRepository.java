package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * No method declared. Extends JpaRepository<Trade, Integer>
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
