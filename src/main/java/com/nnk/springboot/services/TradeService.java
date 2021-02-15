package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * use jpa:
 * List<Trade> findAll();
 * void save(Trade trade);
 * Optional<Trade> findById(Integer id);
 * void delete(Trade trade);
 * void deleteAll();
 */
@Service
public interface TradeService {
  List<Trade> findAll();

  void save(Trade trade);

  Optional<Trade> findById(Integer id);

  void delete(Trade trade);

  void deleteAll();
}
