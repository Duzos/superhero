package mc.duzo.timeless.util;

import java.util.function.Supplier;

import dev.drtheo.scheduler.api.Scheduler;
import dev.drtheo.scheduler.api.TimeUnit;

public class CachableResult<T> {
    private final Supplier<T> getter;
    private T value;
    private boolean dirty;

    public CachableResult(Supplier<T> getter, TimeUnit unit, long time) {
        this.getter = getter;

        Scheduler.get().runTaskTimer((task) -> this.invalidate(), unit, time); // i dont like this anymore
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
            this.update();
            this.dirty = false;
        }
    }

    public T get() {
        this.validate();

        return this.value;
    }
}
