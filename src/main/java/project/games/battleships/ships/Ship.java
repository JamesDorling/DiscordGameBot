package project.games.battleships.ships;

import project.games.battleships.board.CharacterAxis;
import project.games.battleships.board.Coords;
import project.games.battleships.exceptions.InvalidShipLength;
import project.games.battleships.exceptions.InvalidShipLocation;

public class Ship {
    private Coords[] coordinates;
    private boolean sunk = false;

    public Ship(int size, Coords start, Coords end) throws InvalidShipLocation, InvalidShipLength {
        sanitiseInputs(size, start, end);
        if (!start.getRow().equals(end.getRow()))
        {
            setupCoordinates(start.getRow(), end.getRow(), start.getColumn());
        }
        else if(!start.getColumn().equals(end.getColumn())) {
            setupCoordinates(start.getColumn(), end.getColumn(), start.getRow());
        }
        else throw new InvalidShipLocation("Ship was not placed at a valid location. Locations: " + start + " " + end);
        checkSize(size);
    }

    public Ship(int size, String start, String end) throws InvalidShipLocation, InvalidShipLength {
        this(size, Coords.of(start), Coords.of(end));
    }

    public Coords[] getCoordinates() {
        return coordinates;
    }

    private void setupCoordinates(Integer start, Integer end, Character column) {
        if(start > end) {
            Integer temp = start;
            start = end;
            end = temp;
        }
        coordinates = new Coords[end - start + 1];
        for(int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coords(Character.toString(column) + (start + i));
        }
    }

    private void setupCoordinates(Character start, Character end, Integer row) {
        if(start > end) {
            Character temp = start;
            start = end;
            end = temp;
        }
        coordinates = new Coords[end - start + 1];
        for(int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coords(Character.toString(start + i) + row);
        }
    }

    private void sanitiseInputs(int size, Coords start, Coords end) throws InvalidShipLocation {
        if ((!start.getColumn().equals(end.getColumn()) && !start.getRow().equals(end.getRow())
                && !(start.getColumn() < 'k') && !(start.getRow() < 11)
                && !(end.getColumn() < 'k') && !(end.getColumn() < 11))) {
            throw new InvalidShipLocation("Ship was not placed at a valid location. Locations: " + start + " " + end);
        }
    }

    public boolean isOnCoordinate(Coords coordinate) {
        for (Coords coords : coordinates) {
            if (coords.is(coordinate)) {
                return true;
            }
        }
        return false;
    }

    public void getShot(Coords coordinates) {
        for (Coords coordinate : this.getCoordinates()) {
            if(coordinate.is(coordinates)) {
                coordinate.setShot(true);
                if (checkIsSunk()) {
                    setSunk(true);
                }
            }
        }
    }

    public Boolean checkIsSunk() {
        for (Coords coordinate : this.getCoordinates()) {
            if(!coordinate.getShot()) {
                return false;
            }
        }
        return true;
    }

    public boolean isSunk() {
        return sunk;
    }

    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

    public void checkSize(int desiredLength) throws InvalidShipLength {
        if(this.getCoordinates().length != desiredLength) throw new InvalidShipLength("Ship isnt the correct length.");
    }
}
