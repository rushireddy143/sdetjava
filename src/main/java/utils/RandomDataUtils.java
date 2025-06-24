package utils;

import com.github.javafaker.Faker;

public class RandomDataUtils {
    private static final Faker faker = new Faker();

    public static String randomEmail() {
        return faker.internet().emailAddress();
    }

    public static String randomPassword() {
        return faker.internet().password(8, 16, true, true, true);
    }

    public static String randomFullName() {
        return faker.name().fullName();
    }
}