package project.games.battleships.board;

import java.util.Map;
import java.util.TreeMap;

public class CharacterAxis {
    public static final TreeMap<Character, Integer> ALPHABET = new TreeMap<>(Map.of(
            'a', 1,
            'b', 2,
            'c', 3,
            'd', 4,
            'e', 5,
            'f', 6,
            'g', 7,
            'h', 8,
            'i', 9,
            'j', 10
    ));

    public static final TreeMap<Integer, Character> NUMERICAL_ALPHABET = new TreeMap<>(Map.of(
             1,'a',
             2,'b',
             3,'c',
             4,'d',
             5,'e',
             6,'f',
             7,'g',
             8,'h',
             9,'i',
             10, 'j'
    ));

    //It makes it easier to compare them.
    public static final TreeMap<Character, Integer> COMPARISON_ALPHABET = new TreeMap<>(Map.of(
            'a', 10,
            'b', 20,
            'c', 30,
            'd', 40,
            'e', 50,
            'f', 60,
            'g', 70,
            'h', 80,
            'i', 90,
            'j', 100
    ));
}
