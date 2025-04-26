package org.ulrica.application.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.ulrica.domain.entity.CarProfile;
import org.ulrica.domain.service.MockProfileSelectionService;
import org.ulrica.domain.valueobject.BatteryProfile;
import org.ulrica.domain.valueobject.BatteryType;
import org.ulrica.domain.valueobject.ConsumptionProfile;
import org.ulrica.presentation.controller.MockAcChargingController;
import org.ulrica.presentation.controller.MockDcChargingController;
import org.ulrica.presentation.controller.MockRangeCalculationController;
import org.ulrica.presentation.view.MockActionResultView;

public class ExecuteActionInteractorTest {

    private ExecuteActionInteractor executeActionInteractor;
    private NavigationUseCase navigationUseCase;
    private MockActionResultView actionResultView;
    private MockProfileSelectionService profileSelectionService;
    private MockDcChargingController dcChargingController;
    private MockAcChargingController acChargingController;
    private MockRangeCalculationController rangeCalculationController;
    private CarProfile testProfile;

    @Before
    public void setUp() {
        
        navigationUseCase = new NavigationUseCase();
        actionResultView = new MockActionResultView();
        profileSelectionService = new MockProfileSelectionService();
        dcChargingController = new MockDcChargingController();
        acChargingController = new MockAcChargingController();
        rangeCalculationController = new MockRangeCalculationController();
        
        
        executeActionInteractor = new ExecuteActionInteractor(
            navigationUseCase,
            actionResultView,
            profileSelectionService,
            dcChargingController,
            acChargingController,
            rangeCalculationController
        );
        
        
        BatteryProfile batteryProfile = new BatteryProfile(
            BatteryType.NMC,
            80.0,
            5.0,
            250.0,
            11.0
        );
        
        ConsumptionProfile consumptionProfile = new ConsumptionProfile(15.0, 20.0, 25.0);
        
        testProfile = new CarProfile.Builder()
            .id("test-id")
            .name("Test EV")
            .manufacturer("Test Manufacturer")
            .model("Test Model")
            .year(2023)
            .hasHeatPump(true)
            .wltpRangeKm(500)
            .maxDcPowerKw(250.0)
            .maxAcPowerKw(11.0)
            .batteryProfile(batteryProfile)
            .consumptionProfile(consumptionProfile)
            .build();
    }

    @Test
    public void testExecuteAction_NoProfileSelected() {
        
        profileSelectionService.clearSelection();
        
        
        boolean result = executeActionInteractor.executeAction(1);
        
        
        assertFalse(result);
        assertEquals(1, actionResultView.getErrorCount());
        assertEquals(0, actionResultView.getSuccessCount());
        assertEquals(0, dcChargingController.getProcessCallCount());
        assertEquals(0, acChargingController.getProcessCallCount());
        assertEquals(0, rangeCalculationController.getProcessCallCount());
        assertTrue(actionResultView.lastErrorContains("No car profile selected"));
    }

    @Test
    public void testExecuteAction_DcCharging() {
        
        profileSelectionService.selectProfile(testProfile);
        dcChargingController.setProcessDcChargingResult(true);
        
        
        boolean result = executeActionInteractor.executeAction(1);
        
        
        assertTrue(result);
        assertEquals(0, actionResultView.getErrorCount());
        assertEquals(1, dcChargingController.getProcessCallCount());
        assertEquals(0, acChargingController.getProcessCallCount());
        assertEquals(0, rangeCalculationController.getProcessCallCount());
    }
    
    @Test
    public void testExecuteAction_DcChargingFailed() {
        
        profileSelectionService.selectProfile(testProfile);
        dcChargingController.setProcessDcChargingResult(false);
        
        
        boolean result = executeActionInteractor.executeAction(1);
        
        
        assertFalse(result);
        assertEquals(1, dcChargingController.getProcessCallCount());
    }

    @Test
    public void testExecuteAction_AcCharging() {
        
        profileSelectionService.selectProfile(testProfile);
        acChargingController.setProcessAcChargingResult(true);
        
        
        boolean result = executeActionInteractor.executeAction(2);
        
        
        assertTrue(result);
        assertEquals(0, dcChargingController.getProcessCallCount());
        assertEquals(1, acChargingController.getProcessCallCount());
        assertEquals(0, rangeCalculationController.getProcessCallCount());
    }
    
    @Test
    public void testExecuteAction_AcChargingFailed() {
        
        profileSelectionService.selectProfile(testProfile);
        acChargingController.setProcessAcChargingResult(false);
        
        
        boolean result = executeActionInteractor.executeAction(2);
        
        
        assertFalse(result);
        assertEquals(1, acChargingController.getProcessCallCount());
    }

    @Test
    public void testExecuteAction_RangeCalculation() {
        
        profileSelectionService.selectProfile(testProfile);
        rangeCalculationController.setProcessRangeCalculationResult(true);
        
        
        boolean result = executeActionInteractor.executeAction(3);
        
        
        assertTrue(result);
        assertEquals(0, dcChargingController.getProcessCallCount());
        assertEquals(0, acChargingController.getProcessCallCount());
        assertEquals(1, rangeCalculationController.getProcessCallCount());
    }
    
    @Test
    public void testExecuteAction_RangeCalculationFailed() {
        
        profileSelectionService.selectProfile(testProfile);
        rangeCalculationController.setProcessRangeCalculationResult(false);
        
        
        boolean result = executeActionInteractor.executeAction(3);
        
        
        assertFalse(result);
        assertEquals(1, rangeCalculationController.getProcessCallCount());
    }

    @Test
    public void testExecuteAction_BackToMainMenu() {
        
        profileSelectionService.selectProfile(testProfile);
        
        
        boolean result = executeActionInteractor.executeAction(4);
        
        
        assertTrue(result);
        assertEquals(0, dcChargingController.getProcessCallCount());
        assertEquals(0, acChargingController.getProcessCallCount());
        assertEquals(0, rangeCalculationController.getProcessCallCount());
    }

    @Test
    public void testExecuteAction_InvalidChoice() {
        
        profileSelectionService.selectProfile(testProfile);
        
        
        boolean result = executeActionInteractor.executeAction(99); 
        
        
        assertFalse(result);
        assertEquals(0, dcChargingController.getProcessCallCount());
        assertEquals(0, acChargingController.getProcessCallCount());
        assertEquals(0, rangeCalculationController.getProcessCallCount());
    }
} 