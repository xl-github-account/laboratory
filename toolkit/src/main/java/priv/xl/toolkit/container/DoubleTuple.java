package priv.xl.toolkit.container;

import org.apache.commons.lang3.ObjectUtils;

/**
 * 双重数据元组
 *
 * @author lei.xu
 * @since 2023/3/29 1:34 下午
 */
public class DoubleTuple <A, B> {

    protected A one;

    protected B two;

    public DoubleTuple() {

    }

    public DoubleTuple(A one, B two) {
        this.one = one;
        this.two = two;
    }

    public A getOne() {
        return this.one;
    }

    public void setOne(A one) {
        this.one = one;
    }

    public B getTwo() {
        return this.two;
    }

    public void setTwo(B two) {
        this.two = two;
    }

    public boolean isOneEmpty() {
        return ObjectUtils.isEmpty(this.one);
    }

    public boolean isTwoEmpty() {
        return ObjectUtils.isEmpty(this.two);
    }

}
