package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Use Jpa:
 * List<CurvePoint> findAll();
 * void save(CurvePoint curvePoint);
 * Optional<CurvePoint> findById(Integer id);
 * void delete(CurvePoint curvePoint);
 * void deleteAll();
 */
@Service
public interface CurvePointService {
  List<CurvePoint> findAll();

  void save(CurvePoint curvePoint);

  Optional<CurvePoint> findById(Integer id);

  void delete(CurvePoint curvePoint);

  void deleteAll();
}
