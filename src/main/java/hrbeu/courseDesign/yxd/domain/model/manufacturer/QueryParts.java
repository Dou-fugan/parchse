package hrbeu.courseDesign.yxd.domain.model.manufacturer;

import lombok.Data;

@Data
public class QueryParts {
    String partName;
    String partUsageDescription;
    String partAccuracyDimensionGrade;
    Integer page;
    Integer limit;
}
