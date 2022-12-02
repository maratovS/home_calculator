package com.study.ConstructionCalculatorWeb.controller;


import com.study.ConstructionCalculatorWeb.entity.Calculation;
import com.study.ConstructionCalculatorWeb.entity.Frame;
import com.study.ConstructionCalculatorWeb.entity.Material;
import com.study.ConstructionCalculatorWeb.entity.Results;
import com.study.ConstructionCalculatorWeb.service.FrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/business")
public class CustomerController {
    @Autowired
    private FrameService frameService;

    @GetMapping("/getMaterials")
    public List<Material> getAllMaterials(){
        return frameService.getMaterials();
    }

    @GetMapping("/getResults")
    List<Results> getResults(@RequestParam UUID calculationNumber){
        return frameService.getResults(calculationNumber);
    }

    @PostMapping("/addCalculation")
    Calculation addCalculation(@RequestBody Calculation calculation){
        calculation.setStatus(frameService.getStatusByName("Актуален"));
        calculation.setNumber(UUID.randomUUID());
        return frameService.addCalculation(calculation);
    }

    @GetMapping("/getCalculation")
    List<Calculation> getCalculationsByCustomer(@RequestParam Long id){
        return frameService.getCalculations(id);
    }

    @PostMapping("/addFrame")
    List<Results> addFrame(@RequestBody Frame frame, @RequestParam UUID calculationNumber){
        Frame frameSaved = frameService.addFrame(frame);
        return frameService.doBusiness(frameService.getCalculation(calculationNumber), frameSaved);
    }


}
