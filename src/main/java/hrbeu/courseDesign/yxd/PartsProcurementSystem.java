package hrbeu.courseDesign.yxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PartsProcurementSystem {

    public static void main(String[] args) {
        SpringApplication.run(PartsProcurementSystem.class, args);
    }

}
