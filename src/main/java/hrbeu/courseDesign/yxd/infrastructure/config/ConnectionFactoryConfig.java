package hrbeu.courseDesign.yxd.infrastructure.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author caohongyun
 */
public class ConnectionFactoryConfig{


    private static interface Singleton{

        final ConnectionFactoryConfig INSTANCE = new ConnectionFactoryConfig();
    }

    private HikariDataSource dataSource;

    private ConnectionFactoryConfig(){


        //也可以使用配置文件直接加载
//        HikariConfig config = new HikariConfig("application.properties");
//        this.dataSource = new HikariDataSource(config);

        String user = "root";
        String password = "vnv0123";
        String url = "jdbc:mysql://localhost:3308/log4j2_demo?useUnicode=true&characterEncoding=UTF-8";
        String driverClassName = "com.mysql.cj.jdbc.Driver";

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts","true");
        config.addDataSourceProperty("prepstmtCacheSize","250");
        config.addDataSourceProperty("prepstmtCacheSqlLimit","2048");
        //设置连接超时为8小时
        config.setConnectionTimeout(8*60*60);
        this.dataSource = new HikariDataSource(config);
    }

    public static Connection getDatabaseConnection() throws SQLException{
        return Singleton.INSTANCE.dataSource.getConnection();
    }
}