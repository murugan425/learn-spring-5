/* Created by Murugan_Nagarajan on 9/24/2017 */
package tamil.learn.springframework.learnspringdi.common;

public class TestJmsBroker {
    private String jmsUrl;
    private String jmsUserName;
    private String jmsPassword;

    public String getJmsUrl() {
        return jmsUrl;
    }

    public String getJmsUserName() {
        return jmsUserName;
    }

    public String getJmsPassword() {
        return jmsPassword;
    }

    public TestJmsBroker(String url, String userName, String password) {
        this.jmsUrl = url;
        this.jmsUserName = userName;
        this.jmsPassword = password;
    }
}
