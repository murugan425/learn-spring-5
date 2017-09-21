/* Created by Murugan_Nagarajan on 9/21/2017 */
package tamil.learn.springframework.learnspringdiapp.services;

import guru.springframework.norris.chuck.ChuckNorrisQuotes;
import org.springframework.stereotype.Service;

@Service
public class JokeServiceImpl implements JokeService {

    private final ChuckNorrisQuotes chuckNorrisQuotes;

    /*public JokeServiceImpl() {
        this.chuckNorrisQuotes = new ChuckNorrisQuotes();
    }*/

    //The above constructor is replaced by a Java based configuration file
    //so that spring can inject the bean by itself instead of we creating the instance.
    //We will just inject it everywhere and use it, no need to create a instance everywhere.
    //Check the ChuckNorisConfig class for details on how to do that.
    public JokeServiceImpl(ChuckNorrisQuotes chuckNorrisQuotes) {
        this.chuckNorrisQuotes = chuckNorrisQuotes;
    }

    @Override
    public String getJoke() {
        return chuckNorrisQuotes.getRandomQuote();
    }
}
