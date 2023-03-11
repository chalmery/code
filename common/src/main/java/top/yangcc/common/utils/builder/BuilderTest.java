package top.yangcc.common.utils.builder;

public class BuilderTest {
    public static void main(String[] args) {
        Cow cow = Builder.of(Cow::new)
                .with(Cow::setName, "奶奶牛")
                .build();
        System.out.println(cow);
    }
}
