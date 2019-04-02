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

    public static <T> ElseReturn<T> given(Supplier<T> stringSupplier) {
        return new ElseReturn<>(stringSupplier.get());
    }

    public static <T> ElseReturn<T> given(T t) {
        return new ElseReturn<>(t);
    }

    public static FluentConditionals when(Supplier<Boolean> supplier) {
        return new FluentConditionals(supplier.get());
    }

    public static FluentConditionals when(boolean result) {
        return new FluentConditionals(result);
    }

    public <T> ElseReturn<T> then(Runnable runnable) {
        if (condition)
            runnable.run();
        return new ElseReturn<>(condition);
    }

    public <T> ElseReturn<T> thenReturn(Supplier<T> t) {
        return new ElseReturn<>(condition, t.get());
    }

    public <T> ElseReturn<T> thenReturn(T t) {
        return new ElseReturn<>(condition, t);
    }

   public static Runnable doNothing(){
       return null;
   }


    public static class ElseReturn<T> {
        private T t;
        private boolean condition;

        ElseReturn(boolean condition) {
            this.condition = condition;
        }

        ElseReturn(T t) {
            this.t = t;
        }

        ElseReturn(boolean condition, T t) {
            this(condition);
            this.t = t;
        }

        public ElseReturn<T> when(boolean condition) {
            return new ElseReturn<>(condition, t);
        }

        public ElseReturn<T> when(Supplier<Boolean> isTrue) {
            return new ElseReturn<>(isTrue.get(), t);
        }

        public ElseReturn<T> then(Consumer<T> consumer) {
            if (condition)
                consumer.accept(t);
            return new ElseReturn<>(condition, t);
        }

        public void orElse(Runnable t) {
            if (!condition)
                t.run();
        }

        public void orElse(Consumer<T> consumer) {
            if (!condition)
                consumer.accept(t);
        }

        public T orElse(Supplier<T> t) {
            return orElse(t.get());
        }

        public T orElse(T t) {
            if (condition)
                return this.t;
            return t;
        }

        public T orElseThrow(Supplier<Exception> e) throws Exception {
            return orElseThrowE(e.get());
        }

        public T orElseThrowE(Exception e) throws Exception {
            if (!condition)
                throw e;
            return t;
        }
        public <R> ThenRuter<T,R> thenReturn(Supplier<R> r) {
            R ret = r.get();
            return new ThenRuter<>(t, ret, condition);
        }

        public <R> ThenRuter<T,R> thenReturn(Function<T,R> function) {
            R ret = function.apply(t);
            return new ThenRuter<>(t, ret, condition);
        }

        public static class ThenRuter<T, R> {

            T t;
            R r;
            boolean condition;


            ThenRuter(T t, R r, boolean condition) {
                this.t = t;
                this.r = r;
                this.condition = condition;
            }

            public R orElse(Function<T,R> function) {
                if (!condition)
                    function.apply(t);
                return r;
            }

            public R orElse(Supplier<R> r){
                return orElse(r.get());
            }

            public R orElse(R r){
                if(!condition)
                    return r;
                return this.r;
            }

            public R orElseThrow(Supplier<RuntimeException> rt){
                return orElseThrowE(rt.get());
            }

            public R orElseThrow(Supplier<Exception> rt, String message) throws Exception {
                Exception e = new Exception(message, rt.get());
                return orElseThrowE(e);
            }

            R orElseThrowE(RuntimeException e) {
                if (!condition)
                    throw e;
                return r;
            }

            R orElseThrowE(Exception e) throws Exception {
                if (!condition)
                    throw e;
                return r;
            }
        }


        }


}