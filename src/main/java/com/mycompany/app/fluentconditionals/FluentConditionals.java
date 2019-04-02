package com.mycompany.app.fluentconditionals;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Wiktor Rup
 */
public class FluentConditionals {
    private boolean condition;
    public static Runnable doNothing = () -> {
    };

    FluentConditionals(boolean condition) {
        this.condition = condition;
    }

    public static <AcceptValue> ElseReturn<AcceptValue> given(Supplier<AcceptValue> stringSupplier) {
        return new ElseReturn<>(stringSupplier.get());
    }

    public static <AcceptValue> ElseReturn<AcceptValue> given(AcceptValue acceptValue) {
        return new ElseReturn<>(acceptValue);
    }

    public static FluentConditionals when(Supplier<Boolean> supplier) {
        return new FluentConditionals(supplier.get());
    }

    public static FluentConditionals when(boolean result) {
        return new FluentConditionals(result);
    }

    public <AcceptValue> ElseReturn<AcceptValue> then(Runnable runnable) {
        if (condition)
            runnable.run();
        return new ElseReturn(condition);
    }

    public <AcceptValue> ElseReturn<AcceptValue> thenReturn(Supplier<AcceptValue> t) {
        return new ElseReturn<>(condition, t.get());
    }

    public <AcceptValue> ElseReturn<AcceptValue> thenReturn(AcceptValue acceptValue) {
        return new ElseReturn<>(condition, acceptValue);
    }

    public <ExceptionTypeName extends Throwable> void thenThrow(Function<String, ExceptionTypeName> rt, String message) throws Throwable {
        throw rt.apply(message);
    }

   public static Runnable doNothing(){
       return () -> {};
   }


    public static class ElseReturn<AcceptValue> {
        private AcceptValue acceptValue;
        private boolean condition;

        ElseReturn(boolean condition) {
            this.condition = condition;
        }

        ElseReturn(AcceptValue acceptValue) {
            this.acceptValue = acceptValue;
        }

        ElseReturn(boolean condition, AcceptValue acceptValue) {
            this(condition);
            this.acceptValue = acceptValue;
        }

        public ElseReturn<AcceptValue> when(boolean condition) {
            return new ElseReturn<>(condition, acceptValue);
        }

        public ElseReturn<AcceptValue> when(Supplier<Boolean> isTrue) {
            return new ElseReturn<>(isTrue.get(), acceptValue);
        }

        public ElseReturn<AcceptValue> then(Consumer<AcceptValue> consumer) {
            if (condition)
                consumer.accept(acceptValue);
            return new ElseReturn<>(condition, acceptValue);
        }

        public void orElse(Runnable t) {
            if (!condition)
                t.run();
        }

        public void orElse(Consumer<AcceptValue> consumer) {
            if (!condition)
                consumer.accept(acceptValue);
        }

        public AcceptValue orElse(Supplier<AcceptValue> t) {
            return orElse(t.get());
        }

        public AcceptValue orElse(AcceptValue acceptValue) {
            if (condition)
                return this.acceptValue;
            return acceptValue;
        }

        public AcceptValue orElseThrow(Supplier<Throwable> e) throws Throwable {
            return orElseThrowE(e.get());
        }

        public AcceptValue orElseThrowE(Throwable e) throws Throwable {
            if (!condition)
                throw e;
            return acceptValue;
        }
        public <ReturnValue> ThenRuter<AcceptValue, ReturnValue> thenReturn(Supplier<ReturnValue> r) {
            ReturnValue ret = r.get();
            return new ThenRuter<>(acceptValue, ret, condition);
        }

        public <ReturnValue> ThenRuter<AcceptValue, ReturnValue> thenReturn(Function<AcceptValue, ReturnValue> function) {
            ReturnValue ret = function.apply(acceptValue);
            return new ThenRuter<>(acceptValue, ret, condition);
        }

        public <ExceptionTypeName extends Throwable> AcceptValue orElseThrow(Function<String, ExceptionTypeName> rt, String message) throws Throwable {
            ExceptionTypeName apply = rt.apply(message);
            if(!condition)
                throw apply;
            else return acceptValue;
        }


        public static class ThenRuter<AcceptValue, ReturnValue> {

            AcceptValue acceptValue;
            ReturnValue returnValue;
            boolean condition;


            ThenRuter(AcceptValue acceptValue, ReturnValue returnValue, boolean condition) {
                this.acceptValue = acceptValue;
                this.returnValue = returnValue;
                this.condition = condition;
            }

            public ReturnValue orElse(Function<AcceptValue, ReturnValue> function) {
                if (!condition)
                    function.apply(acceptValue);
                return returnValue;
            }

            public ReturnValue orElse(Supplier<ReturnValue> r){
                return orElse(r.get());
            }

            public ReturnValue orElse(ReturnValue returnValue){
                if(!condition)
                    return returnValue;
                return this.returnValue;
            }

            public ReturnValue orElseThrow(Supplier<RuntimeException> rt) throws Throwable {
                return orElseThrowE(rt.get());
            }

            public <ExceptionTypeName extends Throwable> ReturnValue orElseThrow(Function<String, ExceptionTypeName> rt, String message) throws Throwable {
                ExceptionTypeName apply = rt.apply(message);
                if(!condition)
                    throw apply;
                else return returnValue;
            }

//            AcceptValue orElseThrowE(ExceptionTypeName e) throws Throwable {
//                if (!condition)
//                    throw e;
//                return acceptValue;
//            }

            ReturnValue orElseThrowE(Exception e) throws Exception {
                if (!condition)
                    throw e;
                return returnValue;
            }
        }


        }


}