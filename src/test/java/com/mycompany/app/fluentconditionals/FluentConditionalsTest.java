package com.mycompany.app.fluentconditionals;

import org.testng.annotations.Test;

import java.util.function.Supplier;

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

    @Test
    public void testNotThrowingExceptions(){
        RuntimeException e = mock(RuntimeException.class);
        Runnable r1 = mock(Runnable.class);
        Supplier<RuntimeException> s = mock(Supplier.class);

        FluentConditionals.when(true).then(r1).orElseThrowE(e);
        FluentConditionals.when(true).then(r1).orElseThrow(s);

        verify(r1).run();
        verify(e,never()).getMessage();
        verify(s,never()).get();
    }

    @Test
    public void testThrowingExceptions(){
        Runnable r1 = mock(Runnable.class);
        RuntimeException e = mock(RuntimeException.class);
        Supplier<RuntimeException> s = mock(Supplier.class);

        FluentConditionals.when(false).then(r1).orElseThrowE(e);
        FluentConditionals.when(false).then(r1).orElseThrow(s);

        verify(r1).run();
        verify(e).getMessage();
        verify(s).get();
    }

    @Test
    public void testThenReturn(){

    }


}