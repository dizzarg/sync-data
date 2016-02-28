package ru.kadyrov.sync.data;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kadyrov.sync.data.context.Action;
import ru.kadyrov.sync.data.context.ActionHandler;
import ru.kadyrov.sync.data.context.ApplicationContext;
import ru.kadyrov.sync.data.context.exception.ConfigException;
import ru.kadyrov.sync.data.files.FileUtils;

public class Launcher {

    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main( String[] args ){
        ApplicationContext context = ApplicationContext.INSTANCE;
        BasicConfigurator.configure();
//        context.configureLogger();
        if(args.length<2){
            usage();
        }
        String propertyFileName = System.getProperty("propertyFileName");
        if(propertyFileName==null || propertyFileName.trim().isEmpty()){
            logger.info("Property file name do not set up. Application will use default configuration.");
        } else {
            logger.info("Property file name: " + propertyFileName);
            try {
                context.reload(propertyFileName);
            } catch (ConfigException e) {
                logger.error("Cannot load properties file" + e.getMessage()+". Application will use default configuration.");
            }
        }
        context.configureLogger();
        logger.info("Start");
        boolean exists = FileUtils.fileExists(args[1]);
        Action action = exists ? Action.IMPORT : Action.EXPORT;
        ActionHandler handler = action.handler(context);
        handler.action(args[1]);
    }

    private static void usage() {
        System.err.println("usage: test.bat sync <xml file name>");
        System.exit(1);
    }
}
