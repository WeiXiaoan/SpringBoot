package com.nebula.springBoot.support;

import com.google.common.base.MoreObjects;

public class ReasonResult
{
    public boolean success;
    public int code;
    public String reason = "";

    public Param params = new Param();

    public ReasonResult()
    {
    }

    public ReasonResult(boolean success)
    {
        this.success = success;
        if (this.success) this.code = 0;
    }

    public ReasonResult(boolean success, String reason)
    {
        this.success = success;
        this.reason = reason;
        if (this.success) this.code = 0;
    }

    public ReasonResult(boolean success, int code, String reason)
    {
        this.success = success;
        this.reason = reason;
        if (this.success) this.code = 0; else
            this.code = code;
    }

    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("success", this.success)
                .add("code", this.code)
                .add("reason", this.reason)
                .add("params", this.params)
                .toString();
    }
}