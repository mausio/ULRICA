package service;

import java.util.ArrayList;
import java.util.List;

import actions.RangeCalculationAction;
import interfaces.ProfileAction;

public class ActionRegistry {
    private static ActionRegistry instance;
    private final List<ProfileAction> actions;

    private ActionRegistry() {
        actions = new ArrayList<>();
        registerDefaultActions();
    }

    public static ActionRegistry getInstance() {
        if (instance == null) {
            instance = new ActionRegistry();
        }
        return instance;
    }

    private void registerDefaultActions() {
        actions.add(new RangeCalculationAction());
    }

    public List<ProfileAction> getAvailableActions() {
        return new ArrayList<>(actions);
    }
} 