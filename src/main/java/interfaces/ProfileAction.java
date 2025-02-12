package interfaces;

import model.CarProfile;


public interface ProfileAction {

    void execute(CarProfile profile);

    String getDisplayName();
} 