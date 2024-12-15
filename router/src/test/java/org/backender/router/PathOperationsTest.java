package org.backender.router;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathOperationsTest {

    @Test
    void givenRequestPath_ShouldRemoveTheConfiguredAPIPath() {
        String result = PathOperations.getPathWithoutTheAPIPart("/api/library/books");

        assertEquals("library/books", result);
    }

    @Test
    void givenNullRequestPath_ShouldRemoveTheConfiguredAPIPath() {
        String result = PathOperations.getPathWithoutTheAPIPart(null);

        assertEquals("", result);
    }


}