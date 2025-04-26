package org.ulrica.domain.valueobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ConsumptionProfileTest {

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        double consumptionAt50Kmh = 15.0;
        double consumptionAt100Kmh = 20.0;
        double consumptionAt130Kmh = 25.0;
        
        // Act
        ConsumptionProfile profile = new ConsumptionProfile(
            consumptionAt50Kmh, consumptionAt100Kmh, consumptionAt130Kmh
        );
        
        // Assert
        assertEquals(consumptionAt50Kmh, profile.getConsumptionAt50Kmh(), 0.001);
        assertEquals(consumptionAt100Kmh, profile.getConsumptionAt100Kmh(), 0.001);
        assertEquals(consumptionAt130Kmh, profile.getConsumptionAt130Kmh(), 0.001);
    }
    
    @Test
    public void testGetAverageConsumption() {
        // Arrange
        double consumptionAt50Kmh = 15.0;
        double consumptionAt100Kmh = 21.0;
        double consumptionAt130Kmh = 27.0;
        ConsumptionProfile profile = new ConsumptionProfile(
            consumptionAt50Kmh, consumptionAt100Kmh, consumptionAt130Kmh
        );
        
        // Act
        double averageConsumption = profile.getAverageConsumption();
        
        // Assert
        double expected = (consumptionAt50Kmh + consumptionAt100Kmh + consumptionAt130Kmh) / 3.0;
        assertEquals(expected, averageConsumption, 0.001);
    }
    
    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        ConsumptionProfile profile1 = new ConsumptionProfile(15.0, 20.0, 25.0);
        ConsumptionProfile profile2 = new ConsumptionProfile(15.0, 20.0, 25.0);
        ConsumptionProfile profile3 = new ConsumptionProfile(14.0, 19.0, 24.0);
        
        // Assert
        assertEquals(profile1, profile2);
        assertEquals(profile1.hashCode(), profile2.hashCode());
        assertNotEquals(profile1, profile3);
        assertNotEquals(profile1.hashCode(), profile3.hashCode());
    }
    
    @Test
    public void testToString() {
        // Arrange
        ConsumptionProfile profile = new ConsumptionProfile(15.0, 20.0, 25.0);
        
        // Act
        String result = profile.toString();
        
        // Assert
        assertTrue(result.contains("15.0"));
        assertTrue(result.contains("20.0"));
        assertTrue(result.contains("25.0"));
        assertTrue(result.contains("Average Consumption"));
    }
    
    @Test
    public void testInvalidConsumptionValues() {
        // Zero values
        assertThrows(IllegalArgumentException.class, () -> {
            new ConsumptionProfile(0.0, 20.0, 25.0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new ConsumptionProfile(15.0, 0.0, 25.0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new ConsumptionProfile(15.0, 20.0, 0.0);
        });
        
        // Negative values
        assertThrows(IllegalArgumentException.class, () -> {
            new ConsumptionProfile(-1.0, 20.0, 25.0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new ConsumptionProfile(15.0, -1.0, 25.0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new ConsumptionProfile(15.0, 20.0, -1.0);
        });
    }
} 