package com.nnk.springboot.services;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
class TradeServiceImplTest {

  @Mock
  TradeRepository tradeRepository;

  @InjectMocks
  TradeService tradeService = new TradeServiceImpl();

  Trade trade = new Trade();

  @Test
  void findAll() {
    List<Trade> trades = new ArrayList<>();
    when(tradeRepository.findAll()).thenReturn(trades);
    tradeService.findAll();
    Mockito.verify(tradeRepository, Mockito.times(1)).findAll();
    assertEquals(tradeService.findAll(), trades);
  }

  @Test
  void save() {

    when(tradeRepository.save(trade)).thenReturn(trade);
    tradeService.save(trade);
    Mockito.verify(tradeRepository, Mockito.times(1)).save(trade);
    assertDoesNotThrow(() -> tradeService.save(trade));
  }

  @Test
  void findById() {

    trade.setId(1);
    Optional<Trade> trades = Optional.of(trade);

    when(tradeRepository.findById(1)).thenReturn(trades);
    tradeService.findById(1);
    Mockito.verify(tradeRepository, Mockito.times(1)).findById(1);
    assertEquals(tradeService.findById(1), trades);

  }

  @Test
  void delete() {
    tradeService.delete(trade);
    Mockito.verify(tradeRepository, Mockito.times(1)).delete(trade);
    assertDoesNotThrow(() -> tradeService.delete(trade));
  }
}
