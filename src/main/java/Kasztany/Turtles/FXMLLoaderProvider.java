package Kasztany.Turtles;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class FXMLLoaderProvider {

    private final ApplicationContext applicationContext;

    public FXMLLoaderProvider(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public FXMLLoader getLoader(URL location) {
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        return fxmlLoader;
    }
}
