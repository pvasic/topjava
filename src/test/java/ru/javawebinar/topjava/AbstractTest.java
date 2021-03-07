package ru.javawebinar.topjava;

import org.junit.Rule;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractTest {
    private static Logger log;
    private long startTime;
    protected static final StringBuilder result = new StringBuilder();

    @Rule
    public final TestName name = new TestName();

    public AbstractTest() {
        log = LoggerFactory.getLogger(this.getClass());
    }

    protected void starting() {
        startTime = System.currentTimeMillis();
    }

    protected String finished() {
        String str = String.format("\nTest name: %s \n Time execution: %d",name.getMethodName(), (System.currentTimeMillis() - startTime));
        log.info(str);
        return str;
    }
}
