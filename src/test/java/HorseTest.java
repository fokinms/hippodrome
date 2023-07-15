import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Test
    void whenConstructorFirstParameterIsNull_thenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 2.2, 1.0));
    }

    @Test
    void whenConstructorFirstParameterIsNull_thenMessageNameCannotBeNull() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 2.2, 1.0));
        assertEquals("Name cannot be null.", illegalArgumentException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\n", "\t", "\r"})
    void whenConstructorFirstParameterIsSpaceSymbol_thenIllegalArgumentException(String strings) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(strings, 2.2, 1.0));
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\n", "\t", "\r"})
    void whenConstructorFirstParameterIsSpaceSymbol_thenMessageNameCannotBeBlank(String strings) {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new Horse(strings, 2.2, 1.0));
        assertEquals("Name cannot be blank.", illegalArgumentException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.2, -2.3, -3.5, -4.0})
    void whenConstructorSecondParameterIsNegative_thenIllegalArgumentException(Double doubles) {
        assertThrows(IllegalArgumentException.class, () -> new Horse("horse", doubles, 1.0));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.3, -2.2, -3.4, -4.1})
    void whenConstructorSecondParameterIsNegative_thenMessageSpeedCannotBeNegative(Double doubles) {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new Horse("horse", doubles, 1.0));
        assertEquals("Speed cannot be negative.", illegalArgumentException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.2, -2.3, -3.5, -4.0})
    void whenConstructorThirdParameterIsNegative_thenIllegalArgumentException(Double doubles) {
        assertThrows(IllegalArgumentException.class, () -> new Horse("horse", 2.2, doubles));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.3, -2.2, -3.4, -4.1})
    void whenConstructorThirdParameterIsNegative_thenMessageDistanceCannotBeNegative(Double doubles) {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new Horse("horse", 2.2, doubles));
        assertEquals("Distance cannot be negative.", illegalArgumentException.getMessage());
    }

    @Test
    void whenConstructorFirstParameterIsString_thenReturnThisString() {
        String horseName = "horse";
        assertEquals(horseName, new Horse(horseName, 2.2, 1.0).getName());
    }

    @Test
    void whenConstructorSecondParameterIsDouble_thenReturnThisDouble() {
        Double horseSpeed = 2.2;
        assertEquals(horseSpeed, new Horse("horse", horseSpeed, 1.0).getSpeed());
    }

    @Test
    void whenConstructorThirdParameterIsDouble_thenReturnThisDouble() {
        Double horseDistance = 1.0;
        assertEquals(horseDistance, new Horse("horse", 2.2, horseDistance).getDistance());
    }

    @Test
    void whenConstructorHasNoThirdParameter_thenReturnZero() {
        Double horseDistance = 0.0;
        assertEquals(horseDistance, new Horse("horse", 2.2).getDistance());
    }

    @Test
    void whenMoveCallsMethodGetRandomData() {
        try (MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
            new Horse("horse", 2.2, 1.0).move();
            mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.4, 0.6, 0.8})
    void whenMoveAssignsDistanceValueOnFormula(Double doubles) {
        try (MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("horse", 2.2, 1.0);
            mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(doubles);
            horse.move();
            assertEquals(1 + 2.2 * doubles, horse.getDistance());
        }
    }
}