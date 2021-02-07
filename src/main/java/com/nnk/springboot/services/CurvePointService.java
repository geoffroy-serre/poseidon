package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface CurvePointService {
  List<CurvePoint> findAll();
  public void save(CurvePoint curvePoint);

  Optional<CurvePoint> findById(Integer id);

  void delete(CurvePoint curvePoint);
}
