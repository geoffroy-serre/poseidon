package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Override method:
 * List<BidList> findAll();
 * void save(BidList bid);
 * Optional<BidList> findById(Integer id);
 * void delete(BidList bid);
 */
@Service
public interface BidListService {
  List<BidList> findAll();

  void save(BidList bid);

  Optional<BidList> findById(Integer id);

  void delete(BidList bid);
}
