/* Created by murugan_nagarajan on 9/19/2017 */
package tamil.learn.springframework.learnspringdi.services;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Primary
@Profile({"en_US", "default"})
public class GreetingServiceImpl implements GreetingService {

    public static final String HELLO_TEAM = "HELLO TEAM !!!!Primary Bean Service";
    @Override
    public String sayGreeting() {
        return HELLO_TEAM;
    }
}
