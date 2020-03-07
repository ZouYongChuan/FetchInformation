import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;

/**
 * @author zouyongchuan
 */
@Controller
@EnableAutoConfiguration
public class Main {
    public static void main(final String[] args){
        SpringApplication.run(Main.class,args);
    }
}
