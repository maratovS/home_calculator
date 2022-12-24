package com.study.ConstructionCalculatorWeb.controller;


import com.study.ConstructionCalculatorWeb.entity.Calculation;
import com.study.ConstructionCalculatorWeb.entity.Frame;
import com.study.ConstructionCalculatorWeb.entity.Material;
import com.study.ConstructionCalculatorWeb.entity.Results;
import com.study.ConstructionCalculatorWeb.service.FrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    Calculation addCalculation(@RequestParam Long id, @RequestBody Calculation calculation){
        calculation.setStatus(frameService.getStatusByName("Актуален"));
        calculation.setNumber(UUID.randomUUID());
        return frameService.addCalculation(id, calculation);
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

    @PostMapping("/deleteCalculation")
    ResponseEntity<?> addCalculation(@RequestParam UUID calculationNumber){
        try {
            frameService.deleteCalculation(calculationNumber);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (RuntimeException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PatchMapping("/updateFrame")
    List<Results> updateFrame(@RequestBody Frame frame, @RequestParam UUID calculationNumber, @RequestParam Long id){
        Calculation calculation = frameService.getCalculation(calculationNumber);
        frameService.deleteCalculation(calculationNumber);
        calculation.setResults(null);
        calculation = frameService.addCalculation(id, calculation);
        Frame frameSaved = frameService.addFrame(frame);
        return frameService.doBusiness(calculation, frameSaved);
    }
}
