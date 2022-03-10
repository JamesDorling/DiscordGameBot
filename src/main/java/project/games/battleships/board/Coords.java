package project.games.battleships.board;

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
        this.column = coordinates.charAt(0);
        this.row = Integer.parseInt(Character.toString(coordinates.charAt(1)));
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

    @Override //Might not need the comparison alphabet thingy. Will have a look tomorrow after work.
    public int compareTo(Coords o) {
        return ((CharacterAxis.COMPARISON_ALPHABET.get(this.column) * 10) + this.getRow()) -
               ((CharacterAxis.COMPARISON_ALPHABET.get(o.column) * 10) + o.getRow());
    }
}
