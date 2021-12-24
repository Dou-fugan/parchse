package hrbeu.courseDesign.yxd.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class TableModel {
    Integer code;
    Integer count;
    List data;
    String msg;
}
