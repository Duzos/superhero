package mc.duzo.timeless.heroes;

import net.minecraft.util.Identifier;

public abstract class HeroSchema {
    protected final Identifier id;

    protected HeroSchema(Identifier id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() == null || getClass() != o.getClass()) return false;

        HeroSchema that = (HeroSchema) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Identifier id() {
        return this.id;
    }
}
