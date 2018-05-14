package com.allen.androidcustomview.bean;

/**
 * <pre>
 *      @author : xiaoyao
 *      e-mail  : xiaoyao@51vest.com
 *      date    : 2018/05/14
 *      desc    :
 *      version : 1.0
 * </pre>
 */

public class TypeBean {
    private String title;
    private int type;

    public TypeBean(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
