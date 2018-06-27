package es.uva.alumnos.serorca.sensorlibrary;


import io.reactivex.functions.Predicate;

public final class ReactiveSensorFilter {
    /**
     * Predicate, which can be used in filter(...) method from RxJava
     * to filter all events in which sensor value has changed
     *
     * @return BiPredicate<ReactiveSensorEvent, Boolean> predicate indicating if sensor value has
     * changed
     */
    public static Predicate<ReactiveSensorEvent> filterSensorChanged() {
        return new Predicate<ReactiveSensorEvent>() {
            @Override public boolean test(ReactiveSensorEvent event) throws Exception {
                return event.isSensorChanged();
            }
        };
    }

    /**
     * Predicate, which can be used in filter(...) method from RxJava
     * to filter all events in which accuracy value has changed
     *
     * @return BiPredicate<ReactiveSensorEvent, Boolean> predicate indicating if accuracy value has
     * changed
     */
    public static Predicate<ReactiveSensorEvent> filterAccuracyChanged() {

        return new Predicate<ReactiveSensorEvent>() {
            @Override public boolean test(ReactiveSensorEvent event) throws Exception {
                return event.isAccuracyChanged();
            }
        };
    }
}
