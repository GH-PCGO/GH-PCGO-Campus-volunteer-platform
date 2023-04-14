package com.markerhub.common.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * 这个用于我们的异步统一返回的结果封装。一般来说，结果里面有几个要素必要的
 * <p>
 * 是否成功，可用code表示（如200表示成功，400表示异常）
 * 返回结果消息
 * 返回结果数据
 *
 * @author Conquer
 * @Time
 */

/**
 * @author Administrator
 * @date
 */

@Data
public class Result implements Serializable {

    private int code;
    private String msg;
    private Object data;

    public static Result succ(Object data) {
        return succ(200, "操作成功", data);
    }

    public static Result succ(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        System.out.println(r);
        return r;
    }

    public static Result fail(String msg) {
        return fail(400, msg, null);
    }

    public static Result fail(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

}
