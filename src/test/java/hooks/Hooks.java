package hooks;

import config.BaseApiConfig;
import io.cucumber.java.Before;

public class Hooks {

    private static boolean apiConfigurada = false;

    @Before
    public void configurarApi() {
        if (!apiConfigurada) {
            BaseApiConfig.setup();
            apiConfigurada = true;
        }
    }
}