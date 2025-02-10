package model;

public class CarProfile {
    private String id;
    private String name;
    private String manufacturer;
    private String model;
    private int buildYear;
    private boolean hasHeatPump;

    public CarProfile(String name, String manufacturer, String model, int buildYear, boolean hasHeatPump) {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.manufacturer = manufacturer;
        this.model = model;
        this.buildYear = buildYear;
        this.hasHeatPump = hasHeatPump;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public int getBuildYear() { return buildYear; }
    public void setBuildYear(int buildYear) { this.buildYear = buildYear; }
    
    public boolean isHasHeatPump() { return hasHeatPump; }
    public void setHasHeatPump(boolean hasHeatPump) { this.hasHeatPump = hasHeatPump; }
} 