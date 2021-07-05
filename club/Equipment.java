package club;

import java.util.UUID;

public class Equipment {

    String code;
    long price;

    public Equipment(long price) {

        this.price = price;
        this.code = UUID.randomUUID().toString();
    }

    public String getCode() {
        return code;
    }

    public long getPrice() {
        return price;
    }
    public void setPrice(long p) {
        this.price = p;
    }
}
