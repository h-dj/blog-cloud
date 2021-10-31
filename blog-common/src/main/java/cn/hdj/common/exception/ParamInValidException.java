package cn.hdj.common.exception;

import cn.hdj.common.enums.ResponseCodeEnum;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/10/31 上午10:49
 */
public class ParamInValidException extends BaseException {

    public ParamInValidException() {
        super(ResponseCodeEnum.PARAM_INVALID.getMsg()
                , ResponseCodeEnum.RECORD_REPEAT.getCode()
                , false);
    }

    public ParamInValidException(String msg) {
        super(msg
                , ResponseCodeEnum.PARAM_INVALID.getCode()
                , false);
    }
}
