package com.nnk.springboot.repository;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class RuleRepositoryTest {

  @Autowired
  private RuleNameRepository ruleNameRepository;

  @Test
  public void ruleTest() {
    RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

    // Save
    rule = ruleNameRepository.save(rule);
    Assertions.assertNotNull(rule.getId());
    Assertions.assertTrue(rule.getName().equals("Rule Name"));

    // Update
    rule.setName("Rule Name Update");
    rule = ruleNameRepository.save(rule);
    Assertions.assertTrue(rule.getName().equals("Rule Name Update"));

    // Find
    List<RuleName> listResult = ruleNameRepository.findAll();
    Assertions.assertTrue(listResult.size() > 0);

    // Delete
    Integer id = rule.getId();
    ruleNameRepository.delete(rule);
    Optional<RuleName> ruleList = ruleNameRepository.findById(id);
    Assertions.assertFalse(ruleList.isPresent());
  }
}
