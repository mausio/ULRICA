package actions;

import interfaces.Action;
import interfaces.ProfileAction;
import model.CarProfile;

public class ProfileActionAdapter implements Action {
    private final ProfileAction profileAction;
    private final CarProfile profile;

    public ProfileActionAdapter(ProfileAction profileAction, CarProfile profile) {
        this.profileAction = profileAction;
        this.profile = profile;
    }

    @Override
    public void execute() {
        profileAction.execute(profile);
    }

    @Override
    public String getDescription() {
        return profileAction.getDisplayName();
    }
} 