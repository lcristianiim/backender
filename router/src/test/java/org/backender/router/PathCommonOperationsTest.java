package org.backender.router;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PathCommonOperationsTest {

    @Test
    void givenOnePathParameter_ShouldReturnOneElementInResult() {
        PathCommonOperations pathCommonOperations = new PathCommonOperations();
        Map<String, String> result = pathCommonOperations.getPathParams( "product/{id}","product/1");

        assertEquals(1, result.size());
        assertEquals("1", result.get("id"));
    }

    @Test
    void givenTwoPathParameters_ShouldReturnTwoElements() {
        PathCommonOperations pathCommonOperations = new PathCommonOperations();
        Map<String, String> result = pathCommonOperations.getPathParams(
                "product/category/{cool}/number/{id}",
                "product/category/cars/number/200");

        assertEquals(2, result.size());
        assertEquals("200", result.get("id"));
        assertEquals("cars", result.get("cool"));
    }

    @Test
    void givenNoPathParameter_ShouldReturnNoElementInResult() {
        PathCommonOperations pathCommonOperations = new PathCommonOperations();
        Map<String, String> result = pathCommonOperations.getPathParams("product", "product/1");

        assertEquals(0, result.size());
    }

    @Test
    void givenOneQueryParam_ShouldReturnMapWithOneElement() {
        PathCommonOperations pathCommonOperations = new PathCommonOperations();
        Map<String, String> result = pathCommonOperations.getQueryParams("product?name", "product?name=Doe");

        assertEquals(1, result.size());
        assertEquals("Doe", result.get("name"));
    }

    @Test
    void givenOneQueryParamThatIsNotRegistered_ShouldBeIgnoredAndNotPartOfTheResult() {
        PathCommonOperations pathCommonOperations = new PathCommonOperations();
        Map<String, String> result = pathCommonOperations.getQueryParams("product?name", "product?name=Doe&age=20");

        assertEquals(1, result.size());
        assertEquals("Doe", result.get("name"));
    }

    @Test
    void givenTwoRegisteredQueryParams_ShouldReturnThemInTheResult() {
        PathCommonOperations pathCommonOperations = new PathCommonOperations();
        Map<String, String> result = pathCommonOperations.getQueryParams("product?name?age", "product/1?name=Doe&age=20");

        assertEquals(2, result.size());
        assertEquals("Doe", result.get("name"));
        assertEquals("20", result.get("age"));
    }

    @Test
    void givenCombinationOfQueryAndPathParams_ShouldReturnProperlyAllOfThem() {

        PathCommonOperations pathCommonOperations = new PathCommonOperations();
        String registeredPath = "product/{category}/{id}?name&age";
        String path = "product/cool-category/10?name=Doe&age=20";

        Map<String, String> pathParams = pathCommonOperations.getPathParams(registeredPath, path);
        Map<String, String> queryParams = pathCommonOperations.getQueryParams(registeredPath, path);


        assertEquals(2, queryParams.size());
        assertEquals("Doe", queryParams.get("name"));
        assertEquals("20", queryParams.get("age"));

        assertEquals(2, pathParams.size());
        assertEquals("cool-category", pathParams.get("category"));
        assertEquals("10", pathParams.get("id"));
    }

    @Test
    void givenRegisteredPathThatMatchesWithRequestPath_ShouldReturnTrue() {
        PathCommonOperations pathCommonOperations = new PathCommonOperations();
        String registeredPath = "product/{id}";
        String path = "product/1";

        assertTrue(pathCommonOperations.isARegisteredControllerMatch(registeredPath, path));
    }

    @Test
    void givenNonRegisteredPathThatMatchesWithRequestPath_ShouldReturnTrue() {
        PathCommonOperations pathCommonOperations = new PathCommonOperations();
        String registeredPath = "product/{id}/{category}";
        String path = "product/1";

        assertFalse(pathCommonOperations.isARegisteredControllerMatch(registeredPath, path));
    }

    @Test
    void givenPathThatDoesNotMatchRegisteredPath_ShouldReturnFalse() {
        PathCommonOperations pathCommonOperations = new PathCommonOperations();
        String registeredPath = "product/{id}";
        String path = "product/1/123";

        assertFalse(pathCommonOperations.isARegisteredControllerMatch(registeredPath, path));
    }

    @Test
    void givenMultiplePathParams_ShouldMatchRegisterPathWithPath() {
        PathCommonOperations pathCommonOperations = new PathCommonOperations();
        String registeredPath = "product/category/{cool}/number/{id}";
        String path = "product/category/cars/number/200";

        assertTrue(pathCommonOperations.isARegisteredControllerMatch(registeredPath, path));
    }

    @Test
    void givenPathAndQueryParams_ShouldFindMatch() {
        PathCommonOperations pathCommonOperations = new PathCommonOperations();
        String registeredPath = "product/{category}/{id}?name&age";
        String path = "product/cool-category/10?name=Doe&age=20";

        assertTrue(pathCommonOperations.isARegisteredControllerMatch(registeredPath, path));
    }

    @Test
    void a() {
        PathCommonOperations pathCommonOperations = new PathCommonOperations();
        String registeredPath = "product/{id}?name&age";
        String path = "product/200?name=Doe&age=20";

        assertTrue(pathCommonOperations.isARegisteredControllerMatch(registeredPath, path));
    }


}