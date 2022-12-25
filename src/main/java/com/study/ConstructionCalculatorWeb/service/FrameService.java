package com.study.ConstructionCalculatorWeb.service;

import com.study.ConstructionCalculatorWeb.entity.*;

import java.util.List;
import java.util.UUID;

public interface FrameService {
    Frame addFrame(Frame frame);
    List<Results> updateFrame(Frame frame, UUID calculationNumber);
    List<Material> getMaterials();
    List<Results> getResults(UUID calculationNumber);
    List<Results> doBusiness(Calculation calculation, Frame frame);
    List<Calculation> getCalculations(Long id);
    Calculation getCalculation(UUID calculationNumber);
    Calculation addCalculation(Long id, Calculation calculation);
    void deleteCalculation(UUID calculationNumber);
    Status getStatusByName(String name);
}
