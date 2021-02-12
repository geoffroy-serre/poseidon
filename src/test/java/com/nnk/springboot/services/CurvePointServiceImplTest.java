package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
class CurvePointServiceImplTest {

  @Mock
  CurvePointRepository curvePointRepository;

  @InjectMocks
  CurvePointService curvePointService = new CurvePointServiceImpl();


  @Test
  void findAll() {
    List<CurvePoint> curvePoints = new ArrayList<>();
    when(curvePointRepository.findAll()).thenReturn(curvePoints);
    curvePointService.findAll();
    Mockito.verify(curvePointRepository, Mockito.times(1)).findAll();
    assertEquals(curvePointService.findAll(), curvePoints);
  }

  @Test
  void save() {
    CurvePoint curvePoint = new CurvePoint(1, 20.2, 52.1);
    when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);
    curvePointService.save(curvePoint);
    Mockito.verify(curvePointRepository, Mockito.times(1)).save(curvePoint);
    assertDoesNotThrow(() -> curvePointService.save(curvePoint));
  }

  @Test
  void findById() {
    CurvePoint curvePoint = new CurvePoint(1, 20.2, 52.1);
    curvePoint.setId(1);
    Optional<CurvePoint> curvePoints = Optional.of(curvePoint);

    when(curvePointRepository.findById(1)).thenReturn(curvePoints);
    curvePointService.findById(1);
    Mockito.verify(curvePointRepository, Mockito.times(1)).findById(1);
    assertEquals(curvePointService.findById(1), curvePoints);

  }

  @Test
  void delete() {
    CurvePoint curvePoint = new CurvePoint(1, 20.2, 52.1);
    curvePoint.setId(1);
    curvePointService.delete(curvePoint);
    Mockito.verify(curvePointRepository, Mockito.times(1)).delete(curvePoint);
    assertDoesNotThrow(() -> curvePointService.delete(curvePoint));
  }
}
