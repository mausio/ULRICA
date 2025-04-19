package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import actions.ACSlowChargingCalculatorAction;
import actions.DCFastChargingCalculatorAction;
import actions.ProfileActionAdapter;
import actions.RangeCalculationAction;
import actions.RoutePlannerAction;
import interfaces.Action;
import model.CarProfile;

public class ActionRegistry {
    private final List<Action> actions;
    private final Scanner scanner;
    private final CarProfile profile;

    public ActionRegistry(CarProfile profile) {
        this.scanner = new Scanner(System.in);
        this.actions = new ArrayList<>();
        this.profile = profile;
        registerDefaultActions();
    }

    private void registerDefaultActions() {
        // Add profile-based actions with adapter
        actions.add(new ProfileActionAdapter(new DCFastChargingCalculatorAction(), profile));
        actions.add(new ProfileActionAdapter(new ACSlowChargingCalculatorAction(), profile));
        actions.add(new ProfileActionAdapter(new RangeCalculationAction(), profile));
        
        // Add standalone actions
        actions.add(new RoutePlannerAction(scanner, profile));
    }

    public List<Action> getActions() {
        return new ArrayList<>(actions);
    }
} 