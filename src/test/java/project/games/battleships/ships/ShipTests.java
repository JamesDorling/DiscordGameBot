package project.games.battleships.ships;

import org.junit.jupiter.api.*;
import project.games.battleships.exceptions.InvalidShipLocation;

public class ShipTests {
    @Nested
    @DisplayName("Ship Creation Tests")
    class shipCreationTests {
        Ship ship;

        @Test
        @DisplayName("Creating a valid 5 length ship")
        void creatingAValid5LengthShip()
        {
            try {
                ship = new Ship("a1", "a5");
                Assertions.assertEquals(5, ship.getCoordinates().length);
            } catch (InvalidShipLocation e) {
                Assertions.fail("Invalid ship location thrown.");
            }
        }
    }
}
