package com.nnk.springboot.repository;

import com.nnk.springboot.domain.BidList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BidListRepositoryTest {

  @Autowired
  private com.nnk.springboot.repositories.BidListRepository bidListRepository;

  @Test
  public void bidListTest() {
    BidList bid = new BidList("Account Test", "Type Test", 10d);

    // Save
    bid = bidListRepository.save(bid);
    Assertions.assertNotNull(bid.getId());
    Assertions.assertEquals(bid.getBidQuantity(), 10d, 10d);

    // Update
    bid.setBidQuantity(20d);
    bid = bidListRepository.save(bid);
    Assertions.assertEquals(bid.getBidQuantity(), 20d, 20d);

    // Find
    List<BidList> listResult = bidListRepository.findAll();
    Assertions.assertTrue(listResult.size() > 0);

    // Delete
    Integer id = bid.getId();
    bidListRepository.delete(bid);
    Optional<BidList> bidList = bidListRepository.findById(id);
    Assertions.assertFalse(bidList.isPresent());
  }
}
