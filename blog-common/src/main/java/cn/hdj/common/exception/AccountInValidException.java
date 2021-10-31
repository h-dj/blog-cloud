package cn.hdj.common.exception;

import cn.hdj.common.enums.ResponseCodeEnum;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/10/30 下午9:24
 */
public class AccountInValidException extends BaseException {


    public AccountInValidException() {
        super(ResponseCodeEnum.ACCOUNT_INVALID.getMsg()
                , ResponseCodeEnum.ACCOUNT_INVALID.getCode()
                , false);
    }

    public AccountInValidException(String msg) {
        super(msg
                , ResponseCodeEnum.ACCOUNT_INVALID.getCode()
                , false);
    }
}
