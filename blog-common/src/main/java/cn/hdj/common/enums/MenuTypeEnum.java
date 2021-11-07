package cn.hdj.common.enums;


import lombok.Getter;

/**
 * @Description:
 * @Author huangjiajian
 */
@Getter
public enum MenuTypeEnum {

    /**
     * 目录
     */
    CATEGORY(0),
    /**
     * 菜单
     */
    MENU(1),
    /**
     * 按钮
     */
    BUTTON(2);
    private int type;

    MenuTypeEnum(int type) {
        this.type = type;
    }
}
