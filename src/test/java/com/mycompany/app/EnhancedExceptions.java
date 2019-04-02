package com.mycompany.app;

import static com.mycompany.app.fluentconditionals.FluentConditionals.*;

//Task 9
public class EnhancedExceptions {

    public static void main(String[] args) throws Throwable {
        when(false)
                .then(doNothing)
                .orElseThrow(CustomException::new); // zadeklarowac jako supplier
        //Exception in thread "main" EnhancedExceptions$CustomException

        given("Greetings")
                .when(TestHelper::somethingIsTrue)
                .thenReturn(String::hashCode)
                .orElseThrow(CustomException::new, "Exception message"); // zadeklarowac jako funkcja

        when(TestHelper::somethingIsTrue)
                .then(doNothing)
                .orElseThrow(OutOfMemoryError::new, "Exception message");

        when(TestHelper::somethingIsTrue)
                .thenThrow(RuntimeException::new, "This was expected");
        //Exception in thread "main" java.lang.RuntimeException: This was expected

    }
    static class CustomException extends Exception {
        public CustomException() {
        }

        public CustomException(String message) {
            super(message);
        }
    }
}
