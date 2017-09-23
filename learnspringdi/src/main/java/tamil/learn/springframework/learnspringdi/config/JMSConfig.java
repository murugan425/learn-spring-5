/* Created by Murugan_Nagarajan on 9/23/2017 */
package tamil.learn.springframework.learnspringdi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tamil.learn.springframework.learnspringdi.common.TestJmsBroker;

@Configuration
//@PropertySource("classpath:jms.properties")
public class JMSConfig {

    @Value("${tamil.jms.url}")
    String jmsUrl;
    @Value("${tamil.jms.username}")
    String jmsUserName;
    @Value("${tamil.jms.password}")
    String jmsPassword;

    @Bean
    public TestJmsBroker testJmsBroker() {
        TestJmsBroker testJmsBroker = new TestJmsBroker(jmsUrl, jmsUserName, jmsPassword);
        return testJmsBroker;
    }
}
