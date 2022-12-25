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
        Customer customer = customerRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Customer not found");
        });
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
        if (frame.getApertures() != null) {
            List<Aperture> apertures = apertureRepository.saveAll(
                    frame
                            .getApertures()
                            .stream()
                            .map(AperturesInFrames::getAperture).toList());
            List<AperturesInFrames> aperturesInFrames = frame.getApertures();
            frame.setApertures(null);
            frame = frameRepository.save(frame);
            for (int i = 0; i < apertures.size(); i++) {
                aperturesInFrames.get(i).setId(new aperturesInFramesKey(frame.getId(), apertures.get(i).getId()));
                aperturesInFrames.get(i).setAperture(apertures.get(i));
            }
            frame.setApertures(aperturesInFramesRepository.saveAll(aperturesInFrames));
        }
        return frameRepository.save(frame);
    }


    @Override
    public List<Results> updateFrame(Frame frame, UUID calculationNumber) {
        Calculation calculation = this.getCalculation(calculationNumber);
        Frame finalFrame = frame;
        List<Results> resultsToReplace = calculation
                .getResults()
                .stream()
                .filter(s ->
                        Objects.equals(
                                s.getFrame().getId(), finalFrame.getId()
                        )).toList();
        resultsRepository.deleteAll(resultsToReplace);
        frame.setId(null);
        frame.getApertures().forEach(aperturesInFrames -> {
            aperturesInFrames.setId(new aperturesInFramesKey(null, null));
            aperturesInFrames.getAperture().setId(null);
        });
        frame = this.addFrame(frame);
        return this.doBusiness(calculation, frame);
    }

    @Override
    public List<Results> doBusiness(Calculation calculation, Frame frame) {
        List<Results> results = new ArrayList<>();
        double perimeter = frame.getOuterWallPerimeter();
        List<AperturesInFrames> apertures = frame.getApertures();
        double thickness = frame.getExternalWallThickness();
        double height = 3;
        double width = 0.05;
        int amountOfRacks = (int)Math.round(perimeter / 0.6); // внешние стойки
        amountOfRacks += (int)Math.round(perimeter * 2 / 3);
        double aperturesPerimeter = 0.0;
        double aperturesArea = 0.0;
        if (apertures != null) {
            for (AperturesInFrames current : apertures) {
                if (current.getAperture().getType().equals("Дверные проемы внешние") || current.getAperture().getType().equals("Оконные проемы")) {
                    aperturesArea += current.getAperture().getHeight() * current.getAperture().getWeight() * current.getAmount();
                    aperturesPerimeter += current.getAmount() *
                            2 * (current.getAperture().getHeight() + current.getAperture().getWeight());
                }
            }
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
        if (frame.getOSBExternalWall() != null && materialCharacteristicsRepository.existsByName(frame.getOSBExternalWall())) {
            materialCharacteristics = materialCharacteristicsRepository.findByName(frame.getOSBExternalWall());
            results.add(new Results(
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
        }
        if (frame.getSteamWaterproofingExternalWall() != null && materialCharacteristicsRepository.existsByName(frame.getSteamWaterproofingExternalWall())) {
            materialCharacteristics = materialCharacteristicsRepository.findByName(frame.getSteamWaterproofingExternalWall());
            results.add(new Results(
                    null,
                    "Внешние стены",
                    materialCharacteristics.getName(),
                    perimeter * height * 1.15 * materialCharacteristics.getVolume(),
                    materialCharacteristics.getUnit().getName(),
                    materialCharacteristics.getPriceList().getSellingPrice(),
                    perimeter * height * 1.15 * materialCharacteristics.getVolume() * materialCharacteristics.getPriceList().getSellingPrice(),
                    null,
                    frame,
                    materialCharacteristics
            ));
        }
        if (frame.getWindscreenExternalWall() != null && materialCharacteristicsRepository.existsByName(frame.getWindscreenExternalWall())) {
            materialCharacteristics = materialCharacteristicsRepository.findByName(frame.getWindscreenExternalWall());
            results.add(new Results(
                    null,
                    "Внешние стены",
                    materialCharacteristics.getName(),
                    perimeter * height * 1.15 * materialCharacteristics.getVolume(),
                    materialCharacteristics.getUnit().getName(),
                    materialCharacteristics.getPriceList().getSellingPrice(),
                    perimeter * height * 1.15 * materialCharacteristics.getVolume() * materialCharacteristics.getPriceList().getSellingPrice(),
                    null,
                    frame,
                    materialCharacteristics
            ));
        }
        if (frame.getInsulationExternalWall() != null && materialCharacteristicsRepository.existsByName(frame.getInsulationExternalWall())) {
            materialCharacteristics = materialCharacteristicsRepository.findByName(frame.getInsulationExternalWall());
            results.add(new Results(
                    null,
                    "Внешние стены",
                    materialCharacteristics.getName(),
                    (perimeter * height - aperturesArea) * 1.10 * materialCharacteristics.getVolume(),
                    materialCharacteristics.getUnit().getName(),
                    materialCharacteristics.getPriceList().getSellingPrice(),
                    (perimeter * height - aperturesArea) * 1.10 * materialCharacteristics.getVolume() * materialCharacteristics.getPriceList().getSellingPrice(),
                    null,
                    frame,
                    materialCharacteristics
            ));
        }

        // ВНУТРЕННИЕ СТЕНЫ
        int amountOfRacksInternalWalls = (int)Math.round(frame.getInternalWallLength() / 0.6);
        int amountOfRacksApertures = 0; //(int)Math.round(aperturesPerimeter / 3);

        if (apertures != null) {
            for (AperturesInFrames current : apertures) {
                if (current.getAperture().getType().equals("Дверные проемы внутренние")) {
                    amountOfRacksApertures += (int) Math.round(current.getAmount() *
                            2 * (current.getAperture().getHeight() + current.getAperture().getWeight()) / 3);
                }
            }
        }

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

        if (frame.getOSBInternalWall() != null && materialCharacteristicsRepository.existsByName(frame.getOSBInternalWall())) {
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
        }

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

        if (frame.getOSBInternalWall() != null && materialCharacteristicsRepository.existsByName(frame.getOSBThickness())) {
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
        }

        if (frame.getSteamWaterproofingThickness() != null && materialCharacteristicsRepository.existsByName(frame.getSteamWaterproofingThickness())) {
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
        }

        if (frame.getSteamWaterproofingThickness() != null && materialCharacteristicsRepository.existsByName(frame.getWindscreenProtectionThickness())) {
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
        }

        if (frame.getSteamWaterproofingThickness() != null && materialCharacteristicsRepository.existsByName(frame.getInsulationThickness())) {
            materialCharacteristics = materialCharacteristicsRepository.findByName(frame.getInsulationThickness());
            double areaOfInsulation = frame.getBaseArea() * 1.1;
            if (frame.getFloorNumber() == 1) {
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
        }


        results = resultsRepository.saveAll(results);
        calculation.setResults(results);
        calculationRepository.save(calculation);
        return results;
    }

    @Override
    public void deleteCalculation(UUID calculationNumber) {
        Calculation calculation = calculationRepository.findByNumber(calculationNumber);
        if (calculation == null)
            throw new RuntimeException("Calculation not found");

        calculationRepository.delete(calculation);
    }

    @Override
    public List<Status> getStatuses() {
        return statusRepository.findAll();
    }

    @Override
    public Calculation updateCalculation(Calculation calculation) {
        return calculationRepository.save(calculation);
    }
}
