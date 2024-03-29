package project.games.battleships.board;

import java.util.Objects;

public class Coords implements Comparable<Coords>{
    private Character column;
    private Integer row;
    private Boolean shot = false;

    public Boolean getShot() {
        return shot;
    }

    public void setShot(Boolean shot) {
        this.shot = shot;
    }

    public Coords(Character column, Integer row) {
        this.column = column;
        this.row = row;
    }
    public Coords(String coordinates) {
        try {
            this.column = coordinates.toLowerCase().charAt(0);
            this.row = Integer.parseInt(Character.toString(coordinates.toLowerCase().charAt(1)));
        } catch (NumberFormatException e) {
            this.column = coordinates.toLowerCase().charAt(1);
            this.row = Integer.parseInt(Character.toString(coordinates.toLowerCase().charAt(0)));
        }
    }

    public Character getColumn() {
        return column;
    }

    public void setColumn(Character column) {
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return Character.toString(column) + row;
    }

    @Override
    public int compareTo(Coords o) {
        return ((CharacterAxis.ALPHABET.get(this.column) * 100) + this.getRow()) -
               ((CharacterAxis.ALPHABET.get(o.column) * 100) + o.getRow());
    }

    public boolean is(Coords coordinates) {
        return (Objects.equals(this.column, coordinates.column)) && (Objects.equals(this.row, coordinates.row));
    }

    public static Coords of(String coordinateString) {
        return new Coords(coordinateString);
    }

    public Integer getLinearDistance(Coords comparedCoordinates) {
        int result = 0;
        if (!Objects.equals(this.getRow(), comparedCoordinates.getRow())) {
            result += comparedCoordinates.getRow() - this.getRow();
        }
        if (!Objects.equals(this.getColumn(), comparedCoordinates.getColumn())) {
            result += CharacterAxis.ALPHABET.get(comparedCoordinates.getColumn()) - CharacterAxis.ALPHABET.get(this.getColumn());
        }
        return result;
    }
}
