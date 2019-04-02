package com.mycompany.app.fluentconditionals;

import com.mycompany.app.TestHelper;
import org.testng.annotations.Test;

import java.util.function.Supplier;

import static com.mycompany.app.fluentconditionals.FluentConditionals.doNothing;
import static org.mockito.Mockito.*;

/**
 * @author Wiktor Rup
 */
@Test
public class FluentConditionalsTest {


    @Test
    public void testFluentConditionals() {
        Runnable r1 = mock(Runnable.class);
        Runnable r2 = mock(Runnable.class);

        FluentConditionals.when(true).then(r1).orElse(r2);

        verify(r1).run();
        verify(r2, never()).run();
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testNotThrowingExceptions() {
        Runnable r1 = ()-> System.out.println("nothing");
        RuntimeException e = mock(RuntimeException.class);
        Supplier<RuntimeException> s = mock(Supplier.class);


        FluentConditionals.when(true).then(r1).orElseThrowE(e);
        FluentConditionals.when(true).then(r1).orElseThrow(s);

        verify(r1).run();
        verify(e, never()).getMessage();
        verify(s).get();
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testThrowingExceptions() {
        Runnable r1 = mock(Runnable.class);
        RuntimeException e = mock(RuntimeException.class);
        Supplier<RuntimeException> s = mock(Supplier.class);

        FluentConditionals.when(false).then(r1).orElseThrowE(e);
        FluentConditionals.when(false).then(r1).orElseThrow(s);

        verify(r1, never()).run();
        verify(e).getMessage();
        verify(s).get();
    }

    @Test
    public void testThenReturn() {

    }

//    @Test
//    public void testFluentConditional() {
//        //FluentConditionals mock = mock(FluentConditionals.class);
//        assertNotNull(when(false));
//    }
//
//    @Test
//    public void testBooleanFunctionInterface() {
//        assertNotNull(when(() -> true));
//    }

    @Test
    public void testThenFunction() {
        Runnable firstRunnable = mock(Runnable.class);

        FluentConditionals.when(true).then(firstRunnable);
        verify(firstRunnable).run();
    }

    @Test
    public void testThenMethod() {
        Runnable firstRunnable = mock(Runnable.class);
        Runnable secondRunnable = mock(Runnable.class);

        FluentConditionals.when(() -> false).then(firstRunnable).orElse(firstRunnable);

        verify(firstRunnable).run();
        verify(secondRunnable, never()).run();
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testExceptionHandling() throws RuntimeException {
        //Exception exception = mock(RuntimeException.class);
        Runnable runnable = mock(Runnable.class);

        FluentConditionals.when(false).then(runnable).orElseThrow(RuntimeException::new);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testExceptionHandlingNoSupplier() throws RuntimeException {
        //Exception exception = mock(RuntimeException.class);
        Runnable runnable = mock(Runnable.class);
        FluentConditionals.when(false).then(runnable).orElseThrow(TestHelper::createException);
    }

    @Test
    public void testExceptionHandlingNoThrowing() throws RuntimeException {
        //Exception exception = mock(RuntimeException.class);
        Runnable runnable = mock(Runnable.class);
        FluentConditionals.when(true).then(runnable).orElseThrow(TestHelper::createException);
        FluentConditionals.when(true).then(runnable).orElseThrow(RuntimeException::new);
    }

    @Test
    public void testReturn() {
        int result = FluentConditionals.when(true)
                .thenReturn(TestHelper::getHighNumber)
                .orElse(0);

        int result2 = FluentConditionals.when(false)
                .thenReturn(TestHelper.getHighNumber())
                .orElse(0);

        assert result == 1000: "Result should be 1000";
        assert result2 == 0: "Result should be 0";
    }

    @Test
    public void testOrElseReturn() {
        int result = FluentConditionals.when(() -> false)
                .thenReturn(TestHelper::getHighNumber)
                .orElse(TestHelper::getLowNumber);

        assert result == 1 : "Result should be 1";
    }

    @Test
    public void testGiven() {
        FluentConditionals.given("this").when(true)
                .then(TestHelper::printFirstChar)
                .orElse(TestHelper::printLastChar);

        FluentConditionals.given("this").when(false)
                .then(TestHelper::printFirstChar)
                .orElse(TestHelper::printLastChar);

        FluentConditionals.given(() -> "this").when(() -> false)
                .then(TestHelper::printLastChar)
                .orElse(doNothing());
    }


}