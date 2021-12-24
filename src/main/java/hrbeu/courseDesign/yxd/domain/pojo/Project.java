package hrbeu.courseDesign.yxd.domain.pojo;

import lombok.Data;

@Data
public class Project {
    Integer projectId;
    String projectName;
    String projectStartTime;
    String projectEndTime;
    String projectManagerName;
}
