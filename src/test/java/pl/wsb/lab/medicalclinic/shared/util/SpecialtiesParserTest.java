package pl.wsb.lab.medicalclinic.shared.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class SpecialtiesParserTest {

    @Test
    void testPrivateConstructor() throws NoSuchMethodException {
        Constructor<SpecialtiesParser> constructor = SpecialtiesParser.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertInstanceOf(UnsupportedOperationException.class, exception.getCause());
    }
}