package ru.javawebinar.topjava.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Artiom on 29.04.2017.
 */
public class TimeRule implements TestRule {

    private static final Logger LOG = LoggerFactory.getLogger(TimeRule.class);

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                long beforeTimeMilles = System.currentTimeMillis();
                base.evaluate();
                long afterTimeMilles = System.currentTimeMillis();
                String message = String.format("%s - %d milSec", description.getClassName(), afterTimeMilles - beforeTimeMilles);
                LOG.info(message);
                System.out.println(message);
            }
        };
    }
}
