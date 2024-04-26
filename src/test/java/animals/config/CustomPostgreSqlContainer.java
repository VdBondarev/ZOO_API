package animals.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class CustomPostgreSqlContainer extends PostgreSQLContainer<CustomPostgreSqlContainer> {
    private static final String DB_IMAGE = "postgres";
    private static CustomPostgreSqlContainer postgreSQLContainer;

    private CustomPostgreSqlContainer() {
        super(DB_IMAGE);
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("TEST_DB_URL", postgreSQLContainer.getJdbcUrl());
        System.setProperty("TEST_DB_USERNAME", postgreSQLContainer.getUsername());
        System.setProperty("TEST_DB_PASSWORD", postgreSQLContainer.getPassword());
    }

    @Override
    public void stop() {

    }
}
