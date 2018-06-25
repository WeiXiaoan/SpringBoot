package com.nebula.springBoot.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志管理类
 */
public class Log {
    public static Logger temp = LoggerFactory.getLogger("TEMP");
    public static Logger login = LoggerFactory.getLogger("LOGIN");
    public static Logger notice = LoggerFactory.getLogger("NOTICE");
    public static Logger server = LoggerFactory.getLogger("SERVER");
    public static Logger account = LoggerFactory.getLogger("ACCOUNT");
    public static Logger dataEye = LoggerFactory.getLogger("DATAEYE");
    public static Logger uc = LoggerFactory.getLogger("UC");
    public static Logger online = LoggerFactory.getLogger("ONLINE");
}