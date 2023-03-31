package priv.xl.java8;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

/**
 * 主方法入口
 *
 * @author lei.xu
 * 2023-02-03 下午 3:16
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(predicate(str -> str.startsWith("张三")));
        System.out.println(boolSupplier(() -> 1 == 1));
    }

    public static Boolean predicate(Predicate<String> predicate) {
        return predicate.test("张三");
    }

    public static Boolean boolSupplier(BooleanSupplier supplier) {
        return supplier.getAsBoolean();
    }

}
