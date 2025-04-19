package actions;

import java.util.Scanner;

import interfaces.Action;
import model.CarProfile;
import service.RoutePlannerService;

public class RoutePlannerAction implements Action {
    private final RoutePlannerService routePlannerService;
    private final CarProfile profile;

    public RoutePlannerAction(Scanner scanner, CarProfile profile) {
        this.routePlannerService = new RoutePlannerService(scanner);
        this.profile = profile;
    }

    @Override
    public void execute() {
        routePlannerService.planRoute(profile);
    }

    @Override
    public String getDescription() {
        return "Plan a route with charging stops";
    }
} 