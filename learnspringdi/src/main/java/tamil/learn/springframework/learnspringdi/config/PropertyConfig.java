/* Created by Murugan_Nagarajan on 9/23/2017 */
package tamil.learn.springframework.learnspringdi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import tamil.learn.springframework.learnspringdi.common.TestEnvProp;

@Configuration
//Wiring all the property files in the same place will be looking good
//Instead of having them in all the java classes, so commented the property source
@PropertySources({
        @PropertySource("classpath:jms.properties"),
        @PropertySource("classpath:datasource.properties")
})
public class PropertyConfig {
    @Autowired
    private Environment env;

    @Bean
    public TestEnvProp testEnvProp() {
        TestEnvProp testEnvProp = new TestEnvProp(env);
        return testEnvProp;
    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer =
                new PropertySourcesPlaceholderConfigurer();
        return propertySourcesPlaceholderConfigurer;

    }
}
