package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurvePointServiceImpl implements  CurvePointService{

  @Autowired
  CurvePointRepository curvePointRepository;

  @Override
  public List<CurvePoint> findAll() {
    return curvePointRepository.findAll();
  }

  @Override
  public void save(CurvePoint curvePoint) {
    curvePointRepository.save(curvePoint);
  }

  @Override
  public Optional<CurvePoint> findById(Integer id) {
    return curvePointRepository.findById(id);
  }

  @Override
  public void delete(CurvePoint curvePoint) {
    curvePointRepository.delete(curvePoint);
  }
}
