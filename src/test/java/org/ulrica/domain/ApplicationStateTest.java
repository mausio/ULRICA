package org.ulrica.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class ApplicationStateTest {

    @Test
    public void testApplicationStateValues() {
        // Check that all expected states exist
        assertEquals(8, ApplicationState.values().length);
        assertNotNull(ApplicationState.WELCOME);
        assertNotNull(ApplicationState.MAIN_MENU);
        assertNotNull(ApplicationState.CAR_PROFILE_MENU);
        assertNotNull(ApplicationState.CREATE_CAR_PROFILE);
        assertNotNull(ApplicationState.EDIT_CAR_PROFILE);
        assertNotNull(ApplicationState.DELETE_CAR_PROFILE);
        assertNotNull(ApplicationState.ACTION_MENU);
        assertNotNull(ApplicationState.EXIT);
    }
} 