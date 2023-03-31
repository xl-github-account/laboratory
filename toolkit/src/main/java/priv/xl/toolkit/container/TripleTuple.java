package priv.xl.toolkit.container;

import org.apache.commons.lang3.ObjectUtils;

/**
 * 三重数据元组
 *
 * @author lei.xu
 * @since 2023/3/29 1:42 下午
 */
public class TripleTuple<A, B, C> extends DoubleTuple<A, B> {

    private C three;

    public TripleTuple() {

    }

    public TripleTuple(A one, B two, C three) {
        super(one, two);
        this.three = three;
    }

    public C getThree() {
        return this.three;
    }

    public void setThree(C three) {
        this.three = three;
    }

    public boolean isThreeEmpty() {
        return ObjectUtils.isEmpty(this.three);
    }

}
