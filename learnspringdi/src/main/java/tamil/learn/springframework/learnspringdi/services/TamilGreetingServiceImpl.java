/* Created by murugan_nagarajan on 9/19/2017 */
package tamil.learn.springframework.learnspringdi.services;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Primary
@Profile("ta_IN")
public class TamilGreetingServiceImpl implements GreetingService {

    public static final String HELLO_TEAM = "வணக்கம் அணி!!!! முதன்மை பீன் சேவை";
    @Override
    public String sayGreeting() {
        return HELLO_TEAM;
    }
}
