package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BidListServiceImplTest {

  @Mock
  BidListRepository bidListRepository;

  @InjectMocks
  BidListService bidListService = new BidListServiceImpl();

  List<BidList> bidlists = new ArrayList<>();

  @Test
  void findAll() {
    when(bidListRepository.findAll()).thenReturn(bidlists);
    bidListService.findAll();
    Mockito.verify(bidListRepository, Mockito.times(1)).findAll();
    assertEquals(bidListService.findAll(), bidlists);
  }

  @Test
  void save() {
    BidList bidList = new BidList("account", "type", 10.0);
    when(bidListRepository.save(bidList)).thenReturn(bidList);
    bidListService.save(bidList);
    Mockito.verify(bidListRepository, Mockito.times(1)).save(bidList);
    assertDoesNotThrow(() -> bidListService.save(bidList));
  }

  @Test
  void findById() {
    BidList bidList = new BidList("account", "type", 10.0);
    bidList.setId(1);
    Optional<BidList> bidLists = Optional.of(bidList);

    when(bidListRepository.findById(1)).thenReturn(bidLists);
    bidListService.findById(1);
    Mockito.verify(bidListRepository, Mockito.times(1)).findById(1);
    assertEquals(bidListService.findById(1), bidLists);

  }

  @Test
  void delete() {
    BidList bidList = new BidList("account", "type", 10.0);
    bidList.setId(1);
    bidListService.delete(bidList);
    Mockito.verify(bidListRepository, Mockito.times(1)).delete(bidList);
    assertDoesNotThrow(() -> bidListRepository.delete(bidList));
  }
}
