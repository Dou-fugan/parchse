package hrbeu.courseDesign.yxd.domain.model.manufacturer;

import lombok.Data;

@Data
public class UpdateParts {
    Integer partId;
    String partName;
    String partUsageDescription;
    Integer partAccuracyDimensionGrade;
}
