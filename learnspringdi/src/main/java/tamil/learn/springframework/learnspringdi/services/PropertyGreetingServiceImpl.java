/* Created by murugan_nagarajan on 9/19/2017 */
package tamil.learn.springframework.learnspringdi.services;

import org.springframework.stereotype.Service;

@Service
public class PropertyGreetingServiceImpl implements GreetingService {

    public static final String HELLO_TEAM = "HELLO TEAM !!!!";
    @Override
    public String sayGreeting() {
        return HELLO_TEAM + this.getClass().getSimpleName();
    }
}
