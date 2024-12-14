package org.backender.router;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class PathOperationsTest {

    @Test
    void givenRequestPath_ShouldRemoveTheConfiguredAPIPath() {
        Function<String, String> operation = PathOperations.getPathWithoutTheAPIPart();
        String result = operation.apply("/api/library/books");

        assertEquals("library/books", result);
    }

}