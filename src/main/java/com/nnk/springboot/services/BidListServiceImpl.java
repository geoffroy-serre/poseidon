package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidListServiceImpl implements BidListService {

  @Autowired
  BidListRepository bidListRepository;

  @Override
  public List<BidList> findAll() {
    return bidListRepository.findAll();
  }

  @Override
  public void save(BidList bid) {
    bidListRepository.save(bid);
  }

  @Override
  public Optional<BidList> findById(Integer id) {
    return bidListRepository.findById(id);
  }

  @Override
  public void delete(BidList bid) {
    bidListRepository.delete(bid);
  }
}
