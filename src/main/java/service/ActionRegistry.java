package service;

import java.util.ArrayList;
import java.util.List;

import actions.ChargingCalculatorAction;
import actions.RangeCalculationAction;
import interfaces.ProfileAction;

public class ActionRegistry {
    private final List<ProfileAction> actions;

    public ActionRegistry() {
        this.actions = new ArrayList<>();
        registerDefaultActions();
    }

    private void registerDefaultActions() {
        actions.add(new RangeCalculationAction());
        actions.add(new ChargingCalculatorAction());
    }

    public List<ProfileAction> getActions() {
        return new ArrayList<>(actions);
    }
} 