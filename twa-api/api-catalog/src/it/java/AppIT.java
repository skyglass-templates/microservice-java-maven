import com.twa.flights.api.catalog.App;

import com.intuit.karate.junit5.Karate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Objects;

class AppIT {

    private static ConfigurableApplicationContext context;

    @BeforeAll
    static void beforeClass() {
        if (context == null) {
            context = App.run(new String[] { "--server.port=0" });
            Environment env = context.getBean(Environment.class);
            System.setProperty("AppIT.server.port", Objects.requireNonNull(env.getProperty("local.server.port")));
        }
    }

    @Karate.Test
    Karate runAllTests() {
        return Karate.run().tags("~@ignore").relativeTo(getClass());
    }

    @AfterAll
    static void afterClass() {
        context.close();
    }
}
