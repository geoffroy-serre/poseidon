package com.nnk.springboot.services;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
class RuleNameServiceImplTest {

  @Mock
  RuleNameRepository ruleNameRepository;

  @InjectMocks
  RuleNameService ruleNameService = new RuleNameServiceImpl();

  RuleName ruleName = new RuleName();

  @Test
  void findAll() {
    List<RuleName> ruleNames = new ArrayList<>();
    when(ruleNameRepository.findAll()).thenReturn(ruleNames);
    ruleNameService.findAll();
    Mockito.verify(ruleNameRepository, Mockito.times(1)).findAll();
    assertEquals(ruleNameService.findAll(), ruleNames);
  }

  @Test
  void save() {

    when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
    ruleNameService.save(ruleName);
    Mockito.verify(ruleNameRepository, Mockito.times(1)).save(ruleName);
    assertDoesNotThrow(() -> ruleNameService.save(ruleName));
  }

  @Test
  void findById() {

    ruleName.setId(1);
    Optional<RuleName> ruleNames = Optional.of(ruleName);

    when(ruleNameRepository.findById(1)).thenReturn(ruleNames);
    ruleNameService.findById(1);
    Mockito.verify(ruleNameRepository, Mockito.times(1)).findById(1);
    assertEquals(ruleNameService.findById(1), ruleNames);

  }

  @Test
  void delete() {
    ruleNameService.delete(ruleName);
    Mockito.verify(ruleNameRepository, Mockito.times(1)).delete(ruleName);
    assertDoesNotThrow(() -> ruleNameService.delete(ruleName));
  }

  @Test
  void deleteById() {
    ruleNameService.deleteById(ruleName.getId());
    Mockito.verify(ruleNameRepository, Mockito.times(1)).deleteById(ruleName.getId());
    assertDoesNotThrow(() -> ruleNameService.deleteById(ruleName.getId()));
  }
}
