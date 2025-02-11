package repository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import model.CarProfile;

public class MockCarProfileRepositoryTest {
    private MockCarProfileRepository repository;

    @Before
    public void setUp() {
        repository = new MockCarProfileRepository();
    }

    @Test
    public void testSaveAndFindById() {
        // Create a test car profile
        CarProfile profile = new CarProfile(
            "Test Car",
            "Test Manufacturer",
            "Test Model",
            2024,
            true,
            75.0,  // batteryCapacity
            450.0, // wltpRange
            150.0, // maxDcChargingPower
            11.0   // maxAcChargingPower
        );
        profile.setId("test-id-1");

        // Save the profile
        repository.save(profile);

        // Verify the profile was saved
        assertEquals(1, repository.getProfileCount());

        // Find the profile by ID
        CarProfile foundProfile = repository.findById("test-id-1");
        assertNotNull(foundProfile);
        assertEquals("Test Car", foundProfile.getName());

        // Check operation log
        List<String> log = repository.getOperationLog();
        assertTrue(log.contains("save called with profile id: test-id-1"));
        assertTrue(log.contains("findById called with id: test-id-1"));
    }

    @Test
    public void testFindAll() {
        // Create and save multiple profiles
        CarProfile profile1 = new CarProfile(
            "Car 1",
            "Manufacturer 1",
            "Model 1",
            2024,
            true,
            75.0,
            450.0,
            150.0,
            11.0
        );
        profile1.setId("test-id-1");

        CarProfile profile2 = new CarProfile(
            "Car 2",
            "Manufacturer 2",
            "Model 2",
            2024,
            false,
            80.0,
            500.0,
            170.0,
            22.0
        );
        profile2.setId("test-id-2");

        repository.save(profile1);
        repository.save(profile2);

        // Get all profiles
        List<CarProfile> profiles = repository.findAll();
        assertEquals(2, profiles.size());
    }

    @Test
    public void testDelete() {
        // Create and save a profile
        CarProfile profile = new CarProfile(
            "Test Car",
            "Test Manufacturer",
            "Test Model",
            2024,
            true,
            75.0,
            450.0,
            150.0,
            11.0
        );
        profile.setId("test-id-1");
        repository.save(profile);

        // Delete the profile
        repository.delete("test-id-1");

        // Verify the profile was deleted
        assertNull(repository.findById("test-id-1"));
        assertEquals(0, repository.getProfileCount());
    }

    @Test
    public void testUpdate() {
        // Create and save initial profile
        CarProfile profile = new CarProfile(
            "Original Name",
            "Test Manufacturer",
            "Test Model",
            2024,
            true,
            75.0,
            450.0,
            150.0,
            11.0
        );
        profile.setId("test-id-1");
        repository.save(profile);

        // Update the profile
        profile.setName("Updated Name");
        repository.update(profile);

        // Verify the update
        CarProfile updatedProfile = repository.findById("test-id-1");
        assertEquals("Updated Name", updatedProfile.getName());
    }

    @Test
    public void testClearProfiles() {
        // Add some profiles
        CarProfile profile1 = new CarProfile(
            "Car 1",
            "Manufacturer 1",
            "Model 1",
            2024,
            true,
            75.0,
            450.0,
            150.0,
            11.0
        );
        profile1.setId("test-id-1");

        CarProfile profile2 = new CarProfile(
            "Car 2",
            "Manufacturer 2",
            "Model 2",
            2024,
            false,
            80.0,
            500.0,
            170.0,
            22.0
        );
        profile2.setId("test-id-2");

        repository.save(profile1);
        repository.save(profile2);

        // Clear all profiles
        repository.clearProfiles();

        // Verify everything is cleared
        assertEquals(0, repository.getProfileCount());
        assertTrue(repository.getOperationLog().isEmpty());
    }
} 