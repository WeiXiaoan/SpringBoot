package com.nebula.springBoot.support;

import com.alibaba.fastjson.JSON;

public class AjaxResult {
    private int status;     //0是成功 其他是失败
    private String desc;    //错误描述

    public int getStatus()
    {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public AjaxResult success() {
        setStatus(0);
        return this;
    }

    public AjaxResult fail(String desc) {
        setStatus(-1);
        setDesc(desc);
        return this;
    }

    public boolean isSuccess() {
        return this.status == 0;
    }

    public String toString() {
        return JSON.toJSONString(this);
    }
}