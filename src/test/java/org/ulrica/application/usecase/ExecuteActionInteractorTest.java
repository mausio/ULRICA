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
        // Create test dependencies
        navigationUseCase = new NavigationUseCase();
        actionResultView = new MockActionResultView();
        profileSelectionService = new MockProfileSelectionService();
        dcChargingController = new MockDcChargingController();
        acChargingController = new MockAcChargingController();
        rangeCalculationController = new MockRangeCalculationController();
        
        // Create the interactor with the mock dependencies
        executeActionInteractor = new ExecuteActionInteractor(
            navigationUseCase,
            actionResultView,
            profileSelectionService,
            dcChargingController,
            acChargingController,
            rangeCalculationController
        );
        
        // Create a test car profile
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
        // Arrange - ensure no profile is selected
        profileSelectionService.clearSelection();
        
        // Act
        boolean result = executeActionInteractor.executeAction(1);
        
        // Assert
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
        // Arrange
        profileSelectionService.selectProfile(testProfile);
        dcChargingController.setProcessDcChargingResult(true);
        
        // Act
        boolean result = executeActionInteractor.executeAction(1);
        
        // Assert
        assertTrue(result);
        assertEquals(0, actionResultView.getErrorCount());
        assertEquals(1, dcChargingController.getProcessCallCount());
        assertEquals(0, acChargingController.getProcessCallCount());
        assertEquals(0, rangeCalculationController.getProcessCallCount());
    }
    
    @Test
    public void testExecuteAction_DcChargingFailed() {
        // Arrange
        profileSelectionService.selectProfile(testProfile);
        dcChargingController.setProcessDcChargingResult(false);
        
        // Act
        boolean result = executeActionInteractor.executeAction(1);
        
        // Assert
        assertFalse(result);
        assertEquals(1, dcChargingController.getProcessCallCount());
    }

    @Test
    public void testExecuteAction_AcCharging() {
        // Arrange
        profileSelectionService.selectProfile(testProfile);
        acChargingController.setProcessAcChargingResult(true);
        
        // Act
        boolean result = executeActionInteractor.executeAction(2);
        
        // Assert
        assertTrue(result);
        assertEquals(0, dcChargingController.getProcessCallCount());
        assertEquals(1, acChargingController.getProcessCallCount());
        assertEquals(0, rangeCalculationController.getProcessCallCount());
    }
    
    @Test
    public void testExecuteAction_AcChargingFailed() {
        // Arrange
        profileSelectionService.selectProfile(testProfile);
        acChargingController.setProcessAcChargingResult(false);
        
        // Act
        boolean result = executeActionInteractor.executeAction(2);
        
        // Assert
        assertFalse(result);
        assertEquals(1, acChargingController.getProcessCallCount());
    }

    @Test
    public void testExecuteAction_RangeCalculation() {
        // Arrange
        profileSelectionService.selectProfile(testProfile);
        rangeCalculationController.setProcessRangeCalculationResult(true);
        
        // Act
        boolean result = executeActionInteractor.executeAction(3);
        
        // Assert
        assertTrue(result);
        assertEquals(0, dcChargingController.getProcessCallCount());
        assertEquals(0, acChargingController.getProcessCallCount());
        assertEquals(1, rangeCalculationController.getProcessCallCount());
    }
    
    @Test
    public void testExecuteAction_RangeCalculationFailed() {
        // Arrange
        profileSelectionService.selectProfile(testProfile);
        rangeCalculationController.setProcessRangeCalculationResult(false);
        
        // Act
        boolean result = executeActionInteractor.executeAction(3);
        
        // Assert
        assertFalse(result);
        assertEquals(1, rangeCalculationController.getProcessCallCount());
    }

    @Test
    public void testExecuteAction_BackToMainMenu() {
        // Arrange
        profileSelectionService.selectProfile(testProfile);
        
        // Act
        boolean result = executeActionInteractor.executeAction(4);
        
        // Assert
        assertTrue(result);
        assertEquals(0, dcChargingController.getProcessCallCount());
        assertEquals(0, acChargingController.getProcessCallCount());
        assertEquals(0, rangeCalculationController.getProcessCallCount());
    }

    @Test
    public void testExecuteAction_InvalidChoice() {
        // Arrange
        profileSelectionService.selectProfile(testProfile);
        
        // Act
        boolean result = executeActionInteractor.executeAction(99); // Invalid choice
        
        // Assert
        assertFalse(result);
        assertEquals(0, dcChargingController.getProcessCallCount());
        assertEquals(0, acChargingController.getProcessCallCount());
        assertEquals(0, rangeCalculationController.getProcessCallCount());
    }
} 