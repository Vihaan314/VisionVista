package com.visionvista.utils;

import java.io.Serial;
import java.io.Serializable;

public record Pair<L, R>(L left, R right) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public Pair {
        assert left != null;
        assert right != null;

    }

    @Override
    public int hashCode() {
        return left.hashCode() ^ right.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair pairo)) return false;
        return this.left.equals(pairo.left()) &&
                this.right.equals(pairo.right());
    }

    @Override
    public String toString() {
        return "Left: " + this.left() + ", Right: " + this.right();
    }

}