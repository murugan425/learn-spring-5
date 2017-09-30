/* Created by Murugan_Nagarajan on 9/23/2017 */
package tamil.learn.springframework.learnspringdi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tamil.learn.springframework.learnspringdi.common.TestDataSource;

@Configuration
//@PropertySource("classpath:datasource.properties")
public class DatasourceConfig {

    @Value("${tamil.db.url}")
    String dbUrl;
    @Value("${tamil.db.username}")
    String dbUserName;
    @Value("${tamil.db.password}")
    String dbPassword;

    @Bean
    public TestDataSource testDataSource() {
        TestDataSource testDataSource = new TestDataSource(dbUrl, dbUserName, dbPassword);
        return testDataSource;
    }
}
