/* Created by Murugan_Nagarajan on 9/23/2017 */
package tamil.learn.springframework.learnspringdi.common;

public class TestDataSource {
    private String dbUrl;
    private String dbUserName;
    private String dbPassWord;

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public String getDbPassWord() {
        return dbPassWord;
    }

    public TestDataSource(String dbUrl, String dbUserName, String dbPassWord) {
        this.dbUrl = dbUrl;
        this.dbUserName = dbUserName;
        this.dbPassWord = dbPassWord;
    }
}
