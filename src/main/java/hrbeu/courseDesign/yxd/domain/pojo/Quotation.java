package hrbeu.courseDesign.yxd.domain.pojo;

import lombok.Data;

@Data
public class Quotation {
    String usccode;
    Integer partId;
    Integer projectId;
    Character procurementStatus;
    Double price;
    Integer quantity;
}
