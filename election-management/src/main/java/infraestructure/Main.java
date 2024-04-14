package infraestructure;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main {
    
    public static void main(String... agrs){
        Quarkus.run(agrs);
    }
}
