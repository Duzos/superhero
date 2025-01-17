package mc.duzo.timeless.util;

import java.util.function.Supplier;

import mc.duzo.timeless.util.time.Scheduler;
import mc.duzo.timeless.util.time.TimeUnit;

public class CachableResult<T> {
    private final Supplier<T> getter;
    private T value;
    private boolean dirty;

    public CachableResult(Supplier<T> getter, TimeUnit unit, long time) {
        this.getter = getter;

        Scheduler.runTaskTimer(this::invalidate, unit, time);
        this.invalidate();
    }

    private void update() {
        this.value = this.getter.get();
    }
    public void invalidate() {
        this.dirty = true;
    }
    private void validate() {
        if (this.dirty) {
            System.out.println("Validating");

            this.update();
            this.dirty = false;
        }
    }

    public T get() {
        this.validate();

        return this.value;
    }
}
