package actions;

import interfaces.ProfileAction;
import model.CarProfile;
import service.RangeCalculationService;


public class RangeCalculationAction implements ProfileAction {
    @Override
    public void execute(CarProfile profile) {
        RangeCalculationService rangeCalculationService = new RangeCalculationService(profile);
        rangeCalculationService.showMenu();
    }

    @Override
    public String getDisplayName() {
        return "Calculate Range with Current Conditions";
    }
} 