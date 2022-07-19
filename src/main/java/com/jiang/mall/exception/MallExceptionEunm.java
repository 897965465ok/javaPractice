package com.jiang.mall.exception;

/*
异常枚举
*/
public enum MallExceptionEunm {
    NEED_USER_NAME(10001, "用户名不能为空"),
    NEED_PASSWORD(10002,"密码不能为空"),
    PASSWORD_TO_SHORT(10003,"你的太短了。。。"),
    NAME_EXISTED( 10004,"不允许重名"),
    INSERT_FAILED(10005,"插入失败"),
    SYSTEM_ERROR(20000,"系统异常"),
    NEED_LOGIN(10007,"用户未登录"),
    UPDATE_FAILED(10008,"更新失败"),
    NEED_ADMIN(10009,"无管理权限"),
    PARA_NOT_NULL(10010,"参数不能为空"),
    CREATE_FAILED(10011,"新增失败"),
    REQUEST_PARAM_ERROR(10012,"参数错误"),
    DELETE_FAILED(10013,"删除失败"),
    MAKE_FAILED(10014,"文件创建失败"),
    STATUS_ERROR(10015,"商品没上架"),
    NOT_SALE(10016,"商品状态异常"),
    NOT_ENOUGH(10017,"商品库存不足"),
    CART_EMPTY(10017,"购物车已勾选的商品为空"),
    NO_ENUM(10017,"未找到对应的枚举类"),
    NO_ORDER(10018,"订单不存在"),
    NO_BELONG_YOU (10019,"订单不属于你"),
    WRONG_ORDER_STATUS (10020,"订单状态不符"),
    WRONG_PASSWORD(10006,"密码错误");

    /**
     * 异常码
     */
    Integer code;
    /**
     * 异常信息
     */
    String mes;

    MallExceptionEunm(Integer code, String mes) {
        this.code = code;
        this.mes = mes;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
