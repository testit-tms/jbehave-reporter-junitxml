package ru.testit.reporter.models;

import java.io.Serializable;


/**
 * The model object that stores information about test that was run.
 */
public class Test implements Serializable {
    private String className;
    private String name;
    private Throwable throwable;

    /**
     * Gets class name.
     *
     * @return the class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets class name.
     *
     * @param className the value
     * @return self for method chaining
     */
    public Test setClassName(String className) {
        this.className = className;
        return this;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the value
     * @return self for method chaining
     */
    public Test setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets throwable.
     *
     * @return the throwable
     */
    public Throwable getThrowable() {
        return throwable;
    }

    /**
     * Sets throwable.
     *
     * @param throwable the value
     * @return self for method chaining
     */
    public Test setThrowable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }
}
