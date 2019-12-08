import java.util.*;

public class Station {
    // station name
    private String name;

    // number of maintenance requests
    private int maintenanceRequest;

    // the station capacity (how many bikes can it take?)
    private int capacity;

    // true if station has a kiosk
    private boolean kioskBoolean;

    // number of kiosks if kioskBoolean is true
    private int kioskNumber;

    // address of the station
    private String address;

    // list of bike ids associated with station
    private LinkedList<Integer> bikeList;

    /**
     * Constructor to create a new station object
     *
     * @param nameValue               station name
     * @param maintenanceRequestValue number of maintenance requests
     * @param capacityValue           the station capacity
     * @param kiosk                   number of kiosks
     * @param address1                station address
     */
    Station(String nameValue,
                   // Integer bikesValue,
                   // Integer availableDocksValue,
                   Integer maintenanceRequestValue,
                   Integer capacityValue,
                   Integer kiosk, String address1) {
        this.name = nameValue;
        // this.bikes = bikesValue;
        // this.availableDocks = availableDocksValue;
        this.maintenanceRequest = maintenanceRequestValue;
        this.capacity = capacityValue;
        this.kioskNumber = kiosk;
        this.kioskBoolean = (kiosk > 0);
        this.address = address1;
        this.bikeList = new LinkedList<>();
    }

    Station(String nameValue,
            // Integer bikesValue,
            // Integer availableDocksValue,
            Integer maintenanceRequestValue,
            Integer capacityValue,
            Integer kiosk, String address1, LinkedList<Integer> bikeList) {
        this.name = nameValue;
        // this.bikes = bikesValue;
        // this.availableDocks = availableDocksValue;
        this.maintenanceRequest = maintenanceRequestValue;
        this.capacity = capacityValue;
        this.kioskNumber = kiosk;
        this.kioskBoolean = (kiosk > 0);
        this.address = address1;
        this.bikeList = bikeList;
    }

    String getStationName() {
        return this.name;
    }

    public void setStationName(String newName) {
        this.name = newName;
    }

    int getBikes() {
        return bikeList.size();
    }


    // public void setBikes(int newNumBikes) { this.bikes = newNumBikes; }

    int getAvailableDocks() {
        // return this.availableDocks;
        return (this.capacity - bikeList.size());
    }

    int getMaintenanceRequest() {
        return this.maintenanceRequest;
    }

    void setMaintenanceRequest(int newNumRqst) {
        this.maintenanceRequest = newNumRqst;
    }

    int getCapacity() {
        return this.capacity;
    }

    void setCapacity(int newCap) {
        this.capacity = newCap;
    }

    boolean getKioskBoolean() {
        return this.kioskBoolean;
    }

    void setKioskBoolean(boolean newKioskBool) {
        this.kioskBoolean = newKioskBool;
    }

    int getKioskNum() {
        return this.kioskNumber;
    }

    void setKioskNum(int newKioskNum) {
        this.kioskNumber = newKioskNum;
    }

    String getAddress() {
        return this.address;
    }

    void setAddress(String newAdd) {
        this.address = newAdd;
    }

    LinkedList<Integer> getBikeList() {
        return bikeList;
    }

    void addToBikeList(int bikeId) {
        bikeList.add(bikeId);
    }

    boolean removeFromBikeList(Bike bike) {
        // find index of bike ID in our linked list
        int bikeIndex = bikeList.indexOf(bike);

        // if our bike list doesn't contain this bike, return false
        if (bikeIndex == -1) { return false; }

        // remove bike from list and return true
        bikeList.remove(bikeIndex);
        return true;
    }

    public String getBikeListToString(){
        return bikeList.toString().replaceAll("\\[", "").replaceAll("\\]","");
    }

}