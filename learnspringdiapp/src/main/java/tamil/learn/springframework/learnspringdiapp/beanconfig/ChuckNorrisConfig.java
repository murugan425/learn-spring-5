/* Created by murugan_nagarajan on 9/21/2017 */
package tamil.learn.springframework.learnspringdiapp.beanconfig;

import guru.springframework.norris.chuck.ChuckNorrisQuotes;

//@Configuration
public class ChuckNorrisConfig {

    // @Bean
    public ChuckNorrisQuotes chuckNorrisQuotes(){
        return new ChuckNorrisQuotes();
    }
}
