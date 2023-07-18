package com.duzo.superhero.ids.impls;

public class IronManIdentifier extends IdentifierBuilder {
    private double vertical,blast;
    private int mark;
    protected boolean defaultRenderer = false;
    public IronManIdentifier(String name) {
        super(name);
    }

    @Override
    public String getHoverTextName() {
        return "Mark" + " " + this.mark();
    }

    public double vertical() {
        return this.vertical;
    }
    public IronManIdentifier vertical(double val) {
        this.vertical = val;
        return this;
    }

    public double blast() {
        return this.blast;
    }
    public IronManIdentifier blast(double val) {
        this.blast = val;
        return this;
    }

    public int mark() {
        return this.mark;
    }
    public IronManIdentifier mark(int val) {
        this.mark = val;
        return this;
    }
}
