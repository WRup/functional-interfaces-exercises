package com.mycompany.app.fluentconditionals;

import java.util.function.Supplier;

/**
 * @author Wiktor Rup
 */
public class FluentConditionals {

    public static final String doNothing = "";

    private Supplier suppValue;

    private FluentConditionals(Supplier suppValue) {
        this.suppValue = suppValue;
    }

    public static FluentConditionals when(Supplier somethingIsTrue) {
        return new FluentConditionals(somethingIsTrue);
    }

    public static FluentConditionals when(Boolean value) {
        return new FluentConditionals(() -> value);
    }


    public FluentConditionals then(Runnable data) {
        if (suppValue.get().equals(true)) {
            data.run();
        }
        return this;
    }


    public <T> FluentConditionalsGeneric<T> thenReturn(T supplier) {
        return new FluentConditionalsGeneric<>(suppValue, supplier) ;
    }

     public <T> FluentConditionalsGeneric<T> thenReturn(Supplier<T> supplier) {
         return thenReturn(supplier.get());
    }

}
