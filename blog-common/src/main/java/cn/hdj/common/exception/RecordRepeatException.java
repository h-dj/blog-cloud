package cn.hdj.common.exception;

import cn.hdj.common.enums.ResponseCodeEnum;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/10/30 下午9:24
 */
public class RecordRepeatException extends BaseException {


    public RecordRepeatException() {
        super(ResponseCodeEnum.RECORD_REPEAT.getMsg()
                , ResponseCodeEnum.RECORD_REPEAT.getCode()
                , false);
    }

    public RecordRepeatException(String msg) {
        super(msg
                , ResponseCodeEnum.RECORD_REPEAT.getCode()
                , false);
    }
}
