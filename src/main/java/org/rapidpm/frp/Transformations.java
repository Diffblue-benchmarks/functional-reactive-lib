package org.rapidpm.frp;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.rapidpm.frp.functions.TriFunction;

/**
 * Created by svenruppert on 24.04.17.
 *
 * @author svenruppert
 * @version $Id: $Id
 */
public interface Transformations {


  /**
   * <p>not.</p>
   *
   * @return a {@link java.util.function.Function} object.
   */
  static Function<Boolean, Boolean> not() {
    return (input) -> ! input;
  }


  /**
   * <p>higherCompose.</p>
   *
   * @param <T> a T object.
   * @param <U> a U object.
   * @param <V> a V object.
   * @return a {@link java.util.function.Function} object.
   */
  static <T, U, V> Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>> higherCompose() {
    return (Function<U, V> f) -> (Function<T, U> g) -> (T x) -> f.apply(g.apply(x));
  }

  /**
   * <p>enumToStream.</p>
   *
   * @param <T> a T object.
   * @return a {@link java.util.function.Function} object.
   */
  static <T> Function<Enumeration<T>, Stream<T>> enumToStream() {
    return (e) ->
        StreamSupport
            .stream(Spliterators.spliteratorUnknownSize(new Iterator<T>() {
              public T next() {
                return e.nextElement();
              }

              public boolean hasNext() {
                return e.hasMoreElements();
              }
            }, Spliterator.ORDERED), false);
  }


  /**
   * <p>curryBiFunction.</p>
   *
   * @param <A> a A object.
   * @param <B> a B object.
   * @param <R> a R object.
   * @return a {@link java.util.function.Function} object.
   */
  static <A, B, R> Function<BiFunction<A, B, R>, Function<A, Function<B, R>>> curryBiFunction() {
    return (func) -> a -> b -> func.apply(a, b);
  }

  /**
   * <p>unCurryBifunction.</p>
   *
   * @param <A> a A object.
   * @param <B> a B object.
   * @param <R> a R object.
   * @return a {@link java.util.function.Function} object.
   */
  static <A, B, R> Function<Function<A, Function<B, R>>, BiFunction<A, B, R>> unCurryBifunction() {
    return (func) -> (a, b) -> func.apply(a).apply(b);
  }

  /**
   * <p>curryTriFunction.</p>
   *
   * @param <A> a A object.
   * @param <B> a B object.
   * @param <C> a C object.
   * @param <R> a R object.
   * @return a {@link java.util.function.Function} object.
   */
  static <A, B, C, R> Function<
      TriFunction<A, B, C, R>,
      Function<A, Function<B, Function<C, R>>>> curryTriFunction() {
    return (func) -> a -> b -> c -> func.apply(a, b, c);
  }

  /**
   * <p>unCurryTrifunction.</p>
   *
   * @param <A> a A object.
   * @param <B> a B object.
   * @param <C> a C object.
   * @param <R> a R object.
   * @return a {@link java.util.function.Function} object.
   */
  static <A, B, C, R> Function<
      Function<A, Function<B, Function<C, R>>>,
      TriFunction<A, B, C, R>> unCurryTrifunction() {
    return (func) -> (a, b, c) -> func.apply(a).apply(b).apply(c);
  }




  //Function Casts
  /**
   * <p>not.</p>
   *
   * @param p a {@link java.util.function.Predicate} object.
   * @param <T> a T object.
   * @return a {@link java.util.function.Predicate} object.
   */
  static <T> Predicate<T> not(Predicate<T> p) {
    return t -> ! p.test(t);
  }

  /**
   * <p>asPredicate.</p>
   *
   * @param predicate a {@link java.util.function.Predicate} object.
   * @param <T> a T object.
   * @return a {@link java.util.function.Predicate} object.
   */
  static <T> Predicate<T> asPredicate(Predicate<T> predicate) {
    return predicate;
  }

  /**
   * <p>asConsumer.</p>
   *
   * @param consumer a {@link java.util.function.Consumer} object.
   * @param <T> a T object.
   * @return a {@link java.util.function.Consumer} object.
   */
  static <T> Consumer<T> asConsumer(Consumer<T> consumer) {
    return consumer;
  }

  /**
   * <p>asSupplier.</p>
   *
   * @param supplier a {@link java.util.function.Supplier} object.
   * @param <T> a T object.
   * @return a {@link java.util.function.Supplier} object.
   */
  static <T> Supplier<T> asSupplier(Supplier<T> supplier) {
    return supplier;
  }

  /**
   * <p>asFunc.</p>
   *
   * @param function a {@link java.util.function.Function} object.
   * @param <T> a T object.
   * @param <R> a R object.
   * @return a {@link java.util.function.Function} object.
   */
  static <T, R> Function<T, R> asFunc(Function<T, R> function) {
    return function;
  }

}