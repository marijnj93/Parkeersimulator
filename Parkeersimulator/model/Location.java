package Parkeersimulator.model;

/**
 * A location containing floors, rows, and places.
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
public class Location {

    private int floor;
    private int row;
    private int place;
    private Boolean reserved = false;
    /**
     * Constructor for objects of class Location
     * @param floor The floor of a location.
     * @param row The row of a location.
     * @param place The place of a location.
     */
    public Location(int floor, int row, int place) {
        this.floor = floor;
        this.row = row;
        this.place = place;
        if (row < 6 && floor == 0) {
            reserved = true;
        }
    }

    /**
     * Check to see if a spot is reserved.
     * @return reserved, true if reserved, false if not reserved.
     */
    public boolean reserved() {
        return reserved;
    }
    /**
     * Implement content equality.
     * @param obj Object, the object to check.
     * @return true, if this object is the same as the parameter obj.
     */
    public boolean equals(Object obj) {
        if(obj instanceof Location) {
            Location other = (Location) obj;
            return floor == other.getFloor() && row == other.getRow() && place == other.getPlace();
        }
        else {
            return false;
        }
    }

    /**
     * Make a string in the form of: floor, row, place.
     * @return A string representation of the location.
     */
    public String toString() {
        return floor + "," + row + "," + place;
    }

    /**
     * Use the 10 bits for each of the floor, row and place
     * values. Except for very big car parks, this should give
     * a unique hash code for each (floor, row, place) tupel.
     * @return A hashcode for the location.
     */
    public int hashCode() {
        return (floor << 20) + (row << 10) + place;
    }

    /**
     * Get the floor of a location.
     * @return floor, The floor of a location.
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Get the row of a location.
     * @return row, The row of a location.
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the place of a location.
     * @return place, The place of a location.
     */
    public int getPlace() {
        return place;
    }
}