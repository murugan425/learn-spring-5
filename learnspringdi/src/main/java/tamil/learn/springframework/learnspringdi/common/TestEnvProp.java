/* Created by Murugan_Nagarajan on 9/23/2017 */
package tamil.learn.springframework.learnspringdi.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

public class TestEnvProp {

    @Value("${java.version}")
    public String JAVA_VERSION;

    private Environment env;

    public TestEnvProp(Environment env) {
        this.env = env;
    }

    public String getSystemUserName() {
        return this.env.getProperty("USERNAME");
    }

}
