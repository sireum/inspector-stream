package core

import reactor.core.publisher.{Flux => JFlux}
import java.util.function.{Function => JFunction}
import java.util.function.{BiFunction => JBiFunction}
import java.util.function.{Predicate => JPredicate}
import java.util.function.{BiPredicate => JBiPredicate}
import java.util.function.{BiConsumer => JBiConsumer}
import java.lang.{Iterable => JIterable}
import java.util.{Map => JMap}
import java.time.{Duration => JDuration}
import java.util.function.{Consumer => JConsumer}
import java.util.function.{LongConsumer => JLongConsumer}
import java.util.function.{Supplier => JSupplier}
import java.util.function.{BooleanSupplier => JBooleanSupplier}
import java.lang.{Boolean => JBoolean}
import java.lang.{Long => JLong}
import java.util.stream.{Stream => JStream}
import reactor.core.publisher.{FluxSink => JFluxSink, Flux => JFlux}
import java.util.{Comparator => JComparator}
import java.lang.{Runnable => JRunnable}
import java.util
import java.util.{Optional => JOptional}
import java.util.concurrent.CompletionStage
import java.util.concurrent.{Callable => JCallable}

import reactor.util.function.{Tuple2 => JTuple2}
import reactor.util.function.{Tuple3 => JTuple3}
import reactor.util.function.{Tuple4 => JTuple4}
import reactor.util.function.{Tuple5 => JTuple5}
import reactor.util.function.{Tuple6 => JTuple6}
import reactor.util.function.{Tuple7 => JTuple7}
import reactor.util.function.{Tuple8 => JTuple8}

import scala.collection.{JavaConverters, mutable}
import scala.concurrent.duration.FiniteDuration
import scala.language.implicitConversions
import scala.collection.JavaConverters._
import scala.collection.convert.Wrappers
import scala.collection.convert.Wrappers.JIterableWrapper
import scala.concurrent.Future


// todo remove these or use PimpMyLibrary pattern
trait ImplicitJavaInterop {

//  def toScalaBoolean(boolean: JBoolean): Boolean = boolean.asInstanceOf[Boolean]

  ///
  /// UTILITY
  ///

  def asJavaDuration(finiteDuration: FiniteDuration): JDuration = JDuration.ofNanos(finiteDuration.toNanos)
  def asJavaCompletionStage[T](future: Future[T]): CompletionStage[T] = scala.compat.java8.FutureConverters.toJava(future)

  def asJavaOptional[T](option: Option[T]): JOptional[T] = scala.compat.java8.OptionConverters.toJava(option)
  def asJavaBoolean(boolean: Boolean): JBoolean = boolean2Boolean(boolean)
  def asJavaLong(long: Long): JLong = long2Long(long)

  // COLLECTIONS

  def asJavaIterable[T](iterable: Iterable[T]): JIterable[T] = JavaConverters.asJavaIterable(iterable)

  def toScalaTuple2[T1, T2](tuple2: JTuple2[T1, T2]): (T1, T2) = (tuple2.getT1, tuple2.getT2)
  def toScalaTuple3[T1, T2, T3](tuple3: JTuple3[T1, T2, T3]): (T1, T2, T3) = (tuple3.getT1, tuple3.getT2, tuple3.getT3)
  def toScalaTuple4[T1, T2, T3, T4](tuple4: JTuple4[T1, T2, T3, T4]): (T1, T2, T3, T4) = (tuple4.getT1, tuple4.getT2, tuple4.getT3, tuple4.getT4)
  def toScalaTuple5[T1, T2, T3, T4, T5](tuple5: JTuple5[T1, T2, T3, T4, T5]): (T1, T2, T3, T4, T5) = (tuple5.getT1, tuple5.getT2, tuple5.getT3, tuple5.getT4, tuple5.getT5)
  def toScalaTuple6[T1, T2, T3, T4, T5, T6](tuple6: JTuple6[T1, T2, T3, T4, T5, T6]): (T1, T2, T3, T4, T5, T6) = (tuple6.getT1, tuple6.getT2, tuple6.getT3, tuple6.getT4, tuple6.getT5, tuple6.getT6)
  def toScalaTuple7[T1, T2, T3, T4, T5, T6, T7](tuple7: JTuple7[T1, T2, T3, T4, T5, T6, T7]): (T1, T2, T3, T4, T5, T6, T7) = (tuple7.getT1, tuple7.getT2, tuple7.getT3, tuple7.getT4, tuple7.getT5, tuple7.getT6, tuple7.getT7)
  def toScalaTuple8[T1, T2, T3, T4, T5, T6, T7, T8](tuple8: JTuple8[T1, T2, T3, T4, T5, T6, T7, T8]): (T1, T2, T3, T4, T5, T6, T7, T8) = (tuple8.getT1, tuple8.getT2, tuple8.getT3, tuple8.getT4, tuple8.getT5, tuple8.getT6, tuple8.getT7, tuple8.getT8)


  // FUNCTIONS

  def asJFluxSink[T](jfluxSink: JFluxSink[T]): FluxSink[T] = FluxSink.wrap(jfluxSink)

  // Known as a Runnable in the java world
  def asJavaRunnable(runnable: () => Unit): JRunnable = () => runnable.apply()
  def asJavaCallable[T](callable: () => T): JCallable[T] = () => callable.apply()

  // Known as a LongConsumer in the java world
  def asJavaLongConsumer(consumer: Long => Unit): JLongConsumer = (n: Long) => consumer.apply(n)

  // Known as a Consumer in the java world
  def asJavaConsumer[T](consumer: T => Unit): JConsumer[T] = (t: T) => consumer.apply(t)

  // Known as a Consumer in the java world
  def asJavaBooleanSupplier(supplier: () => Boolean): JBooleanSupplier = () => supplier.apply()
  def asJavaSupplier[T](supplier: () => T): JSupplier[T] = () => supplier.apply()

  // Known as a Predicate in the java world
  def asJavaPredicate[T](predicate: T => Boolean): JPredicate[T] = (t: T) => predicate.apply(t)

  // Known as a Function in the java world
  def asJavaFn1[T, R](function: T => R): JFunction[T, R] = (t: T) => function.apply(t)

  // Known as BiPredicate in the java world
  def asJavaBiConsumer[T, U](biConsumer: (T, U) => Unit): JBiConsumer[T, U] = (t: T, u: U) => biConsumer.apply(t, u)

  // Known as BiPredicate in the java world
  def asJavaBiPredicate[T, U](biPredicate: (T, U) => Boolean): JBiPredicate[T, U] = (t: T, u: U) => biPredicate.apply(t, u)

  // Known as a BiFunction in the java world
  def asJavaFn2[T, U, R](function: (T, U) => R): JBiFunction[T, U, R] = (t: T, u: U) => function.apply(t, u)

  // Does not exist in the java world (without @FunctionalInterface), so follow convention of reactor by converting to function of Array[AnyRef]
  def asJavaFn3[T1 >: AnyRef, T2 >: AnyRef, T3 >: AnyRef, R](function: (T1, T2, T3) => R): JFunction[Array[AnyRef], R] = (arr: Array[AnyRef]) => function.apply(arr(0), arr(1), arr(2))

  // Does not exist in the java world (without @FunctionalInterface), so follow convention of reactor by converting to function of Array[AnyRef]
  def asJavaFn4[T1 >: AnyRef, T2 >: AnyRef, T3 >: AnyRef, T4 >: AnyRef, R](function: (T1, T2, T3, T4) => R): JFunction[Array[AnyRef], R] = (arr: Array[AnyRef]) => function.apply(arr(0), arr(1), arr(2), arr(3))

  // Does not exist in the java world (without @FunctionalInterface), so follow convention of reactor by converting to function of Array[AnyRef]
  def asJavaFn5[T1 >: AnyRef, T2 >: AnyRef, T3 >: AnyRef, T4 >: AnyRef, T5 >: AnyRef, R](function: (T1, T2, T3, T4, T5) => R): JFunction[Array[AnyRef], R] = (arr: Array[AnyRef]) => function.apply(arr(0), arr(1), arr(2), arr(3), arr(4))

  // Does not exist in the java world (without @FunctionalInterface), so follow convention of reactor by converting to function of Array[AnyRef]
  def asJavaFn6[T1 >: AnyRef, T2 >: AnyRef, T3 >: AnyRef, T4 >: AnyRef, T5 >: AnyRef, T6 >: AnyRef, R](function: (T1, T2, T3, T4, T5, T6) => R): JFunction[Array[AnyRef], R] = (arr: Array[AnyRef]) => function.apply(arr(0), arr(1), arr(2), arr(3), arr(4), arr(5))



}
