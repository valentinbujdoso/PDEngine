package org.rulez.demokracia.testhelpers;

@FunctionalInterface
public interface Thrower {
    void throwException() throws RuntimeException;
}
