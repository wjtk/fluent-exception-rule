package pl.wkr.fluentrule.api.performance;

import com.google.common.base.Stopwatch;
import org.junit.Test;
import pl.wkr.fluentrule.api.extending.ExpectedSQLExceptionAssert;
import pl.wkr.fluentrule.api.testutils.TestAssertCommandCollector;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class ExpectedSQLExceptionAssertPerformanceTest {

    private static final int TIMEOUT_MS = 2000;
    private static final int REQUIRED_ASSERTIONS_PER_MSEC = 10;

    @Test(timeout = TIMEOUT_MS)
    public void performance_test() {
        final int REQUIRED_ASSERTS = TIMEOUT_MS * REQUIRED_ASSERTIONS_PER_MSEC;
        Stopwatch s = Stopwatch.createStarted();
        TestAssertCommandCollector collector = new TestAssertCommandCollector();

        for(int i=0; i<REQUIRED_ASSERTS; i++) {
            SQLException exc = new SQLException("yo" + i);
            collector.clear();
            ExpectedSQLExceptionAssert ass = new ExpectedSQLExceptionAssert(collector);
            ass.hasMessage("yo" + i);
            collector.doChecks(exc);
        }
        long ms = s.elapsed(TimeUnit.MILLISECONDS);
        double speedPerSec = (1000.0 * REQUIRED_ASSERTS) / ms;
        System.out.printf("Performance test, used %d%% of max time, speed: %.0f[asserts/s]", (100*ms)/TIMEOUT_MS, speedPerSec);
    }
}


