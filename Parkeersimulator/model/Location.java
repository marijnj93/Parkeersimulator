package Parkeersimulator.model;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @since 26-01-2017
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
        if (row == 1 && floor == 0) {
            reserved = true;
        }
    }
    public boolean reserved() {
        return reserved;
    }
    /**
     * Implement content equality.
     * @param obj Object.
     * @return boolean
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
     * Return a string of the form floor,row,place.
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
     * @return floor, The floor of a location.
     */
    public int getFloor() {
        return floor;
    }

    /**
     * @return row, The row of a location.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return place, The place of a location.
     */
    public int getPlace() {
        return place;
    }

}