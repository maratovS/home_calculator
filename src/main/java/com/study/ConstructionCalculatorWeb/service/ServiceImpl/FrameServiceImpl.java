package com.study.ConstructionCalculatorWeb.service.ServiceImpl;

import com.study.ConstructionCalculatorWeb.entity.*;
import com.study.ConstructionCalculatorWeb.repo.*;
import com.study.ConstructionCalculatorWeb.service.FrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FrameServiceImpl implements FrameService {
    @Autowired
    private ResultsRepository resultsRepository;
    @Autowired
    private FrameRepository frameRepository;
    @Autowired
    private CalculationRepository calculationRepository;
    @Autowired
    private MaterialCharacteristicsRepository materialCharacteristicsRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private ApertureRepository apertureRepository;
    @Autowired
    private AperturesInFramesRepository aperturesInFramesRepository;

    @Override
    public List<Material> getMaterials() {
        return materialRepository.findAll();
    }

    @Override
    public Calculation addCalculation(Long id, Calculation calculation) {
        Calculation calculationSaved = calculationRepository.save(calculation);
        Customer customer = customerRepository.findById(id).get();
        List<Calculation> calculations = customer.getCalculations();
        if (calculations == null || calculations.isEmpty()){
            calculations = new ArrayList<>();
            calculations.add(calculationSaved);
        }
        else
            calculations.add(calculationSaved);
        customer.setCalculations(calculations);
        customerRepository.save(customer);
        return calculationSaved;
    }

    @Override
    public Status getStatusByName(String name) {
        return statusRepository.findByStatusName(name);
    }

    @Override
    public Calculation getCalculation(UUID id) {
        return calculationRepository.findByNumber(id);
    }

    @Override
    public List<Results> getResults(UUID calculationNumber) {
        return calculationRepository.findByNumber(calculationNumber).getResults();
    }

    @Override
    public List<Calculation> getCalculations(Long id) {
        Optional<Customer> calculations = customerRepository.findById(id);
        return calculations.map(Customer::getCalculations).orElse(null);
    }

    @Override
    public Frame addFrame(Frame frame) {
        List<Aperture> apertures = apertureRepository.saveAll(
                frame
                .getApertures()
                .stream()
                .map(AperturesInFrames::getAperture).toList());
        List<AperturesInFrames> aperturesInFrames = frame.getApertures();
        frame.setApertures(null);
        frame = frameRepository.save(frame);
        for (int i = 0; i < apertures.size(); i++){
            aperturesInFrames.get(i).setId(new aperturesInFramesKey(frame.getId(), apertures.get(i).getId()));
            aperturesInFrames.get(i).setAperture(apertures.get(i));
        }
        frame.setApertures(aperturesInFramesRepository.saveAll(aperturesInFrames));
        return frameRepository.save(frame);
    }

    @Override
    public List<Results> doBusiness(Calculation calculation, Frame frame) {
        List<Results> results = new ArrayList<>();
        double perimeter = frame.getOuterWallPerimeter();
        List<AperturesInFrames> apertures = frame.getApertures();
        double thickness = frame.getExternalWallThickness();
        double height = frame.getFloorHeight();
        double width = 0.05;
        int amountOfRacks = (int)Math.round(perimeter / 0.6); // внешние стойки
        amountOfRacks += (int)Math.round(perimeter * 2 / 3);
        double aperturesPerimeter = 0.0;
        double aperturesArea = 0.0;
        for (AperturesInFrames current : apertures) {
            aperturesArea += current.getAperture().getHeight() * current.getAperture().getWeight();
            aperturesPerimeter += current.getAmount() *
                    2 * (current.getAperture().getHeight() + current.getAperture().getWeight());
        }
        amountOfRacks += (int)Math.round(aperturesPerimeter * 2 / 3);
        MaterialCharacteristics materialCharacteristics = materialCharacteristicsRepository.findByWidthAndThicknessAndLength(width, thickness, height);
        results.add( new Results(
                null,
                "Внешние стены",
                materialCharacteristics.getName(),
                amountOfRacks * materialCharacteristics.getVolume(),
                materialCharacteristics.getUnit().getName(),
                materialCharacteristics.getPriceList().getSellingPrice(),
                amountOfRacks * materialCharacteristics.getVolume() * materialCharacteristics.getPriceList().getSellingPrice(),
                null,
                frame,
                materialCharacteristics
        ));
        materialCharacteristics = materialCharacteristicsRepository.findByName(frame.getOSBExternalWall());
        results.add( new Results(
                null,
                "Внешние стены",
                materialCharacteristics.getName(),
                perimeter * height * 2 * 1.15 * materialCharacteristics.getVolume(),
                materialCharacteristics.getUnit().getName(),
                materialCharacteristics.getPriceList().getSellingPrice(),
                perimeter * height * 2 * 1.15 * materialCharacteristics.getVolume() * materialCharacteristics.getPriceList().getSellingPrice(),
                null,
                frame,
                materialCharacteristics
        ));
        materialCharacteristics = materialCharacteristicsRepository.findByName(frame.getSteamWaterproofingExternalWall());
        results.add( new Results(
                null,
                "Внешние стены",
                materialCharacteristics.getName(),
                perimeter * height  * 1.15 * materialCharacteristics.getVolume(),
                materialCharacteristics.getUnit().getName(),
                materialCharacteristics.getPriceList().getSellingPrice(),
                perimeter * height * 1.15 * materialCharacteristics.getVolume() * materialCharacteristics.getPriceList().getSellingPrice(),
                null,
                frame,
                materialCharacteristics
        ));
        materialCharacteristics = materialCharacteristicsRepository.findByName(frame.getWindscreenExternalWall());
        results.add( new Results(
                null,
                "Внешние стены",
                materialCharacteristics.getName(),
                perimeter * height  * 1.15 * materialCharacteristics.getVolume(),
                materialCharacteristics.getUnit().getName(),
                materialCharacteristics.getPriceList().getSellingPrice(),
                perimeter * height * 1.15 * materialCharacteristics.getVolume() * materialCharacteristics.getPriceList().getSellingPrice(),
                null,
                frame,
                materialCharacteristics
        ));
        materialCharacteristics = materialCharacteristicsRepository.findByName(frame.getInsulationExternalWall());
        results.add( new Results(
                null,
                "Внешние стены",
                materialCharacteristics.getName(),
                (perimeter * height - aperturesArea)  * 1.10 * materialCharacteristics.getVolume(),
                materialCharacteristics.getUnit().getName(),
                materialCharacteristics.getPriceList().getSellingPrice(),
                (perimeter * height - aperturesArea) * 1.10 * materialCharacteristics.getVolume() * materialCharacteristics.getPriceList().getSellingPrice(),
                null,
                frame,
                materialCharacteristics
        ));


        // ВНУТРЕННИЕ СТЕНЫ
        int amountOfRacksInternalWalls = (int)Math.round(frame.getInternalWallLength() / 0.6);
        int amountOfRacksApertures = (int)Math.round(aperturesPerimeter / 3);
        materialCharacteristics =
                materialCharacteristicsRepository.findByWidthAndThicknessAndLength(width, frame.getInternalWallThickness(), height);
        double racksVolume = (amountOfRacksApertures + amountOfRacksInternalWalls) * materialCharacteristics.getVolume();
        results.add(new Results(
                null,
                "Внутренние стены",
                materialCharacteristics.getName(),
                racksVolume,
                materialCharacteristics.getUnit().getName(),
                materialCharacteristics.getPriceList().getSellingPrice(),
                racksVolume * materialCharacteristics.getPriceList().getSellingPrice(),
                null,
                frame,
                materialCharacteristics
        ));

        materialCharacteristics = materialCharacteristicsRepository.findByName(frame.getOSBInternalWall());
        double areaOfInternalWalls = frame.getInternalWallLength() * height;
        results.add(new Results(
                null,
                "Внутренние стены",
                materialCharacteristics.getName(),
                areaOfInternalWalls * 2 * 1.15,
                materialCharacteristics.getUnit().getName(),
                materialCharacteristics.getPriceList().getSellingPrice(),
                areaOfInternalWalls * 2 * 1.15 * materialCharacteristics.getPriceList().getSellingPrice(),
                null,
                frame,
                materialCharacteristics
        ));

        // ПЕРЕКРЫТИЯ
        int amountOfRacksBase = (int)Math.round(frame.getBaseArea() / 0.7);
        materialCharacteristics =
                materialCharacteristicsRepository.findByWidthAndThicknessAndLength(width, frame.getOverlapThickness(), 6.0);
        double volumeOfRacksOverlap = amountOfRacksBase * 2 * materialCharacteristics.getVolume();
        results.add(new Results(
                null,
                "Перекрытия",
                materialCharacteristics.getName(),
                volumeOfRacksOverlap,
                materialCharacteristics.getUnit().getName(),
                materialCharacteristics.getPriceList().getSellingPrice(),
                volumeOfRacksOverlap * materialCharacteristics.getPriceList().getSellingPrice(),
                null,
                frame,
                materialCharacteristics
        ));

        materialCharacteristics = materialCharacteristicsRepository.findByName(frame.getOSBThickness());
        results.add(new Results(
                null,
                "Перекрытия",
                materialCharacteristics.getName(),
                frame.getBaseArea() * 2 * 2 * 1.15,
                materialCharacteristics.getUnit().getName(),
                materialCharacteristics.getPriceList().getSellingPrice(),
                frame.getBaseArea() * 2 * 2 * 1.15 * materialCharacteristics.getPriceList().getSellingPrice(),
                null,
                frame,
                materialCharacteristics
        ));


        materialCharacteristics = materialCharacteristicsRepository.findByName(frame.getSteamWaterproofingThickness());
        results.add(new Results(
                null,
                "Перекрытия",
                materialCharacteristics.getName(),
                frame.getBaseArea() * 1.15,
                materialCharacteristics.getUnit().getName(),
                materialCharacteristics.getPriceList().getSellingPrice(),
                frame.getBaseArea() * 1.15 * materialCharacteristics.getPriceList().getSellingPrice(),
                null,
                frame,
                materialCharacteristics
        ));

        materialCharacteristics = materialCharacteristicsRepository.findByName(frame.getWindscreenProtectionThickness());
        results.add(new Results(
                null,
                "Перекрытия",
                materialCharacteristics.getName(),
                frame.getBaseArea() * 1.15,
                materialCharacteristics.getUnit().getName(),
                materialCharacteristics.getPriceList().getSellingPrice(),
                frame.getBaseArea() * 1.15 * materialCharacteristics.getPriceList().getSellingPrice(),
                null,
                frame,
                materialCharacteristics
        ));

        materialCharacteristics = materialCharacteristicsRepository.findByName(frame.getInsulationThickness());
        double areaOfInsulation = frame.getBaseArea() * 1.1;
        if(frame.getFloorNumber() == 1){
            areaOfInsulation *= 2;
        }

        results.add(new Results(
                null,
                "Перекрытия",
                materialCharacteristics.getName(),
                areaOfInsulation * materialCharacteristics.getVolume(),
                materialCharacteristics.getUnit().getName(),
                materialCharacteristics.getPriceList().getSellingPrice(),
                areaOfInsulation * materialCharacteristics.getVolume() * materialCharacteristics.getPriceList().getSellingPrice(),
                null,
                frame,
                materialCharacteristics
        ));



        results = resultsRepository.saveAll(results);
        calculation.setResults(results);
        calculationRepository.save(calculation);
        return results;
    }
}
