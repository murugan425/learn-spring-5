/* Created by murugan_nagarajan on 9/19/2017 */
package tamil.learn.springframework.learnspringdi.services;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Primary
@Profile("zh_CN") //Profile names can be user defined, here we used the common locale code
public class ChineseGreetingServiceImpl implements GreetingService {

    public static final String HELLO_TEAM = "你好团队!!! 小豆服务";
    @Override
    public String sayGreeting() {
        return HELLO_TEAM;
    }
}
