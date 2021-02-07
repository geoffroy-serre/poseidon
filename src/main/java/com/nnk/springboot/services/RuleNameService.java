package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface RuleNameService {
  List<RuleName> findAll();
  void save(RuleName ruleName);
  Optional<RuleName> findById(Integer id);
  void delete(RuleName ruleName);
}
