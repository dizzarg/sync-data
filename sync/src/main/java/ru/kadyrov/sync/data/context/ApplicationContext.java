package ru.kadyrov.sync.data.context;

import org.apache.log4j.PropertyConfigurator;
import ru.kadyrov.sync.data.context.exception.ConfigException;
import ru.kadyrov.sync.data.db.api.*;
import ru.kadyrov.sync.data.db.h2.H2DepartmentRepository;
import ru.kadyrov.sync.data.db.h2.H2ConnectionHolder;
import ru.kadyrov.sync.data.transform.DepartmentTransformer;
import ru.kadyrov.sync.data.transform.xml.XMLDepartmentTransformer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Properties;

public enum ApplicationContext {

    INSTANCE;

    private Properties application;

    ApplicationContext() {
        Properties defaultProperties = new Properties();
        defaultProperties.put("db.url", "jdbc:h2:mem:sync");
        defaultProperties.put("db.user", "sa");
        defaultProperties.put("db.password", "");
        defaultProperties.put("log.configuration", "conf/log4j.properties");
        application = new Properties(defaultProperties);
    }

    public void reload(String propertyFileName) throws ConfigException {
        try {
            application.load(Files.newInputStream(FileSystems.getDefault().getPath(propertyFileName)));
        } catch (IOException e) {
            throw new ConfigException(e);
        }
    }

    public void configureLogger(){
        PropertyConfigurator.configure(application.getProperty("log.configuration"));
    }

    public DepartmentTransformer transformer(){
        return new XMLDepartmentTransformer();
    }

    public DepartmentService service(){
        return new DepartmentService(databaseContext());
    }

    public DatabaseContext databaseContext(){
        return new DatabaseContext() {
            @Override
            public ConnectionHolder connectionHolder() {
                return new H2ConnectionHolder(new ConnectionContext() {
                   @Override
                   public String url() {
                       return application.getProperty("db.url");
                   }

                   @Override
                   public String user() {
                       return application.getProperty("db.user");
                   }

                   @Override
                   public String password() {
                       return application.getProperty("db.password");
                   }
                });
            }

            @Override
            public DepartmentRepository repository() {
                return new H2DepartmentRepository();
            }
        };
    }

}
