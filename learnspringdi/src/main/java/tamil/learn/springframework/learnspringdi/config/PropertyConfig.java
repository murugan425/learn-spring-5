/* Created by Murugan_Nagarajan on 9/23/2017 */
package tamil.learn.springframework.learnspringdi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import tamil.learn.springframework.learnspringdi.common.TestEnvProp;

@Configuration
public class PropertyConfig {
    @Autowired
    private Environment env;

    @Bean
    public TestEnvProp testEnvProp() {
        TestEnvProp testEnvProp = new TestEnvProp(env);
        return testEnvProp;
    }
}
