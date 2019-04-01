package com.mycompany.app.fluentconditionals;

import java.util.function.Supplier;

/**
 * @author Wiktor Rup
 */
public class FluentConditionalsGeneric<ReturnValue> {

    private Supplier<ReturnValue> suppVal;
    private ReturnValue returnValue;

    private FluentConditionalsGeneric(Supplier suppVal, ReturnValue returnValue) {
        this.returnValue = returnValue;
        this.suppVal = suppVal;
    }

    FluentConditionalsGeneric(Supplier suppVal) {
        this.suppVal = suppVal;
    }

    public ReturnValue orElse(Supplier<ReturnValue> data) {
        return orElse(data.get());
    }

//    public void orElse(String string) {
//    }

    public ReturnValue orElseThrowE(RuntimeException e) {
        if (suppVal.get().equals(false))
            throw e;
        else return this.suppVal.get();
    }

    public ReturnValue orElseThrow(Supplier<RuntimeException> supplier) {
        return orElseThrowE(supplier.get());
    }

    //
    public ReturnValue orElse(ReturnValue data) {
        if (suppVal.get().equals(false))
            return data;
        else return this.returnValue;
    }
//
//    private int orElse(Integer integer) {
//        if (suppVal.get().equals(false))
//            return integer;
//        else return (Integer) this.suppVal.get();
//    }

}
