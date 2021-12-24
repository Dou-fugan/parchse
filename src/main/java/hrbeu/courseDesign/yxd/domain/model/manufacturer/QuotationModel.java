package hrbeu.courseDesign.yxd.domain.model.manufacturer;

import lombok.Data;

@Data
public class QuotationModel {
    String usccode;
    Integer partId;
    Integer projectId;
    Character procurementStatus;
    Double price;
    Integer quantity;
}
