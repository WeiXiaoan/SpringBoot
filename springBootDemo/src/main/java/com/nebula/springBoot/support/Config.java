package com.nebula.springBoot.support;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class Config
{
    private static final String CONFIG_NAME = "config.properties";
    public static final String SECRET_KEY;
    public static final String ADDR_SERVER_LIST;
    public static final String ADDR_GAME_PAY;
    public static final int DATAEYE_LOG_DAY_NUM;
    public static final int DATAEYE_LOG_DATA_NUM;
    public static final String ADDR_NOTICE;
    public static final String ADDR_NOTICE_SYNC;
    public static final String ROOT_NAME;
    public static final String ROOT_PWD;
    public static final boolean SYNC_MASTER_SERVER_LIST;

    /**
     * 读取配置文件
     */
    private static Properties readProperties(String name) {
        Resource resource = new ClassPathResource(name);
        Properties prop = null;
        try {
            prop = new Properties(PropertiesLoaderUtils.loadProperties(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    static {
        //获取配置
        Properties prop = readProperties(CONFIG_NAME);

        //读取配置
        SECRET_KEY = prop.getProperty("game.secret.key");
        ADDR_SERVER_LIST = prop.getProperty("addr.server.list");
        ADDR_GAME_PAY = prop.getProperty("addr.game.pay");
        DATAEYE_LOG_DAY_NUM = Integer.parseInt(prop.getProperty("dataeye.log.day.num"));
        DATAEYE_LOG_DATA_NUM = Integer.parseInt(prop.getProperty("dataeye.log.data.num"));
        ADDR_NOTICE = prop.getProperty("addr.notice");
        ADDR_NOTICE_SYNC = prop.getProperty("addr.notice.sync");
        ROOT_NAME = prop.getProperty("config.root.name");
        ROOT_PWD = prop.getProperty("config.root.pwd");
        SYNC_MASTER_SERVER_LIST = Boolean.parseBoolean(prop.getProperty("config.sync.master.server.list"));
    }
}