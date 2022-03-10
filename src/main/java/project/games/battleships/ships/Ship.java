package project.games.battleships.ships;

import project.games.battleships.board.CharacterAxis;
import project.games.battleships.board.Coords;
import project.games.battleships.exceptions.InvalidShipLocation;

public class Ship {
    private Coords[] coordinates;
    boolean sunk = false;

    public Ship(Coords start, Coords end) throws InvalidShipLocation {
        sanitiseInputs(start, end);
        if (!start.getRow().equals(end.getRow()))
        {
            setupCoordinates(start.getRow(), end.getRow(), start.getColumn());
        }
        else if(!start.getColumn().equals(end.getColumn())) {
            setupCoordinates(start.getColumn(), end.getColumn(), start.getRow());
        }
        else throw new InvalidShipLocation("Ship was not placed at a valid location. Locations: " + start + " " + end);
    }

    public Ship(String start, String end) throws InvalidShipLocation {
        this(new Coords(start), new Coords(end));
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

    private void sanitiseInputs(Coords start, Coords end) throws InvalidShipLocation {
        if (!start.getColumn().equals(end.getColumn()) && !start.getRow().equals(end.getRow())
                && !(start.getColumn() < 'k') && !(start.getRow() < 11)
                && !(end.getColumn() < 'k') && !(end.getColumn() < 11)) {
            throw new InvalidShipLocation("Ship was not placed at a valid location. Locations: " + start + " " + end);
        }
    }

    public boolean isOnCoordinate(Coords coordinate) {
        for (Coords coords : coordinates) {
            if (coords.equals(coordinate)) {
                return true;
            }
        }
        return false;
    }
}
