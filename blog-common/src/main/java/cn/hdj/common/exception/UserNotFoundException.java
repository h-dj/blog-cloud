package cn.hdj.common.exception;

import cn.hdj.common.enums.ResponseCodeEnum;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/10/30 下午9:24
 */
public class UserNotFoundException extends BaseException {


    public UserNotFoundException() {
        super(ResponseCodeEnum.ACCOUNT_NOT_EXIST.getMsg()
                , ResponseCodeEnum.ACCOUNT_NOT_EXIST.getCode()
                , false);
    }
}
