package com.nnk.springboot.repository;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class CurvePointRepositoryTest {

  @Autowired
  private CurvePointRepository curvePointRepository;

  @Test
  public void curvePointTest() {
    CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

    // Save
    curvePoint = curvePointRepository.save(curvePoint);
    Assertions.assertNotNull(curvePoint.getId());
    Assertions.assertTrue(curvePoint.getCurveId() == 10);

    // Update
    CurvePoint update = curvePointRepository.findAll().get(0);
    update.setCurveId(20);
    curvePointRepository.save(update);
    Assertions.assertTrue(update.getCurveId() == 20);

    // Find
    List<CurvePoint> listResult = curvePointRepository.findAll();
    Assertions.assertTrue(listResult.size() > 0);

    // Delete
    Integer id = update.getId();
    curvePointRepository.delete(update);
    Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
    Assertions.assertFalse(curvePointList.isPresent());
  }

}
