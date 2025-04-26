package org.ulrica.domain.valueobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class BatteryTypeTest {

    @Test
    public void testBatteryTypeValues() {
        
        assertEquals(3, BatteryType.values().length);
        assertNotNull(BatteryType.LFP);
        assertNotNull(BatteryType.NMC);
        assertNotNull(BatteryType.NCA);
    }
    
    @Test
    public void testBatteryTypeDescriptions() {
        
        assertEquals("Lithium Iron Phosphate", BatteryType.LFP.getDescription());
        assertEquals("Nickel Manganese Cobalt", BatteryType.NMC.getDescription());
        assertEquals("Nickel Cobalt Aluminum", BatteryType.NCA.getDescription());
    }
} 