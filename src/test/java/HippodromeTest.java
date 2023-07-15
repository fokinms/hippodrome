import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HippodromeTest {

    @Test
    void whenConstructorParameterIsNull_thenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void whenConstructorParameterIsNull_thenMessageHorsesCannotBeNull() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", illegalArgumentException.getMessage());
    }

    @Test
    void whenConstructorParameterIsEmptyList_thenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    void whenConstructorParameterIsEmptyList_thenMessageHorsesCannotBeEmpty() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", illegalArgumentException.getMessage());
    }

    @Test
    void whenPassListOnHippodromeCreate_thenReceiveSameListOnHippodromeGetHorses() {
        ArrayList<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(Integer.toString(i), 2.2, 1.0));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
        void whenHippodromeMove_thenAllHorsesMove() {
        ArrayList<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse horse : horses) {
            verify(horse).move();
        }
    }

    @Test
    void whenReturnWinner_thenCheckTheMaximumDistance() {
        Horse firstHorse = new Horse("firstHorse", 1.0, 1.0);
        Horse secondHorse = new Horse("secondHorse", 1.0, 3.0);
        Horse thirdHorse = new Horse("thirdHorse", 1.0, 2.0);
        List<Horse> horses = List.of(firstHorse, secondHorse, thirdHorse);
        Hippodrome hippodrome = new Hippodrome(horses);
        assertSame(secondHorse, hippodrome.getWinner());
    }
}