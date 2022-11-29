package com.study.ConstructionCalculatorWeb.service;

import com.study.ConstructionCalculatorWeb.entity.*;

import java.util.List;
import java.util.UUID;

public interface FrameService {
    Frame addFrame(Frame frame);
    List<Material> getMaterials();
    List<Results> getResults(UUID calculationNumber);
    List<Results> doBusiness(Calculation calculation, Frame frame);
    Calculation getCalculation(UUID calculationNumber);
    Calculation addCalculation(Calculation calculation);
    Status getStatusByName(String name);
}
