package com.dd.admin.common.consts;

public class MemberShopConst {
    /**
     *入库
     */
    public final static Integer STOCK_IN = 1;
    /**
     *出库
     */
    public final static Integer STOCK_OUT = 2;

    /**
     *入库 类型销单返回
     */
    public final static Integer STOCK_IN_TYPE_CANCEL = 3;

    /**
     *出库 类型使用
     */
    public final static Integer STOCK_OUT_TYPE_USE = 3;

    /**
     *开启库存管理
     */
    public final static Integer STOCK_CONTROL_YES = 1;
    /**
     *关闭库存管理
     */
    public final static Integer STOCK_CONTROL_NO = 0;

    /**
     *订单状态 正常
     */
    public final static Integer SELL_STATUS_NORMAL = 0;

    /**
     *订单状态 销单
     */
    public final static Integer SELL_STATUS_CANCEL = 1;


    /**
     *提成固定
     */
    public final static Integer PUSH_FIXED = 0;


    /**
     *订单状态 销单
     */
    public final static Integer PUSH_SCALE = 1;
}
