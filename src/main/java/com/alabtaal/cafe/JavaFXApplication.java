package com.alabtaal.cafe;

//import com.alabtaal.cafe.config.StageManager;
import com.alabtaal.cafe.config.StageManager;
import com.alabtaal.cafe.enums.FxmlView;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFXApplication extends Application {

    protected ConfigurableApplicationContext springContext;
    protected StageManager stageManager;

    @Override
    public void init(){
        springContext = springBootApplicationContext();
    }

    @Override
    public void start(Stage stage) {
        stageManager = springContext.getBean(StageManager.class , stage);
        displayInitialScene();
    }

    protected void displayInitialScene(){
        stageManager.switchScene(FxmlView.USER);
    }

    @Override
    public void stop() {
    springContext.close();
    }

    private ConfigurableApplicationContext springBootApplicationContext(){
        final SpringApplicationBuilder builder = new SpringApplicationBuilder(CafeApplication.class);
        builder.headless(false);
        final String[] args = getParameters().getRaw().toArray(String[] :: new);
        return builder.run(args);
    }
}
