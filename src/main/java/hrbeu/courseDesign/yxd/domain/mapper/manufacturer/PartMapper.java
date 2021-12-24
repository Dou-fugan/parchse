package hrbeu.courseDesign.yxd.domain.mapper.manufacturer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hrbeu.courseDesign.yxd.domain.pojo.Part;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PartMapper extends BaseMapper<Part> {

    @Insert("BEGIN;\n" +
            "INSERT INTO parts (part_name,part_usage_description,part_accuracy_dimension_grade) VALUES (${partName},${partUsageDescription},${partAccuracyDimensionGrade});\n" +
            "INSERT INTO production VALUES(#{usccode},(SELECT LAST_INSERT_ID()));\n" +
            "COMMIT;")
    void addPart(
                 String usccode,
                 String partName,
                 String partUsageDescription,
                 Integer partAccuracyDimensionGrade
                 );

    @Insert("INSERT INTO parts (part_name,part_usage_description,part_accuracy_dimension_grade) VALUES (#{partName},#{partUsageDescription},${partAccuracyDimensionGrade});")
    void insertPart(
            String partName,
            String partUsageDescription,
            Integer partAccuracyDimensionGrade
    );

    @Insert("INSERT INTO production VALUES(#{usccode},(SELECT LAST_INSERT_ID()));")
    void insertProduction(String usccode);

    @Select("SELECT * FROM parts WHERE " +
            "part_name LIKE CONCAT('%',#{partName},'%') and " +
            "part_usage_description LIKE CONCAT('%',#{partUsageDescription},'%') and " +
            "part_accuracy_dimension_grade LIKE CONCAT('%',#{partAccuracyDimensionGrade},'%') " +
            "LIMIT #{limits} OFFSET #{offset}")
    List<Part> queryParts(String partName, String partUsageDescription, String partAccuracyDimensionGrade, Integer limits, int offset);

    @Select("SELECT COUNT(*) FROM parts;")
    Integer getCount();

    @Update("UPDATE parts SET part_name=#{partName},part_usage_description=#{partUsageDescription},part_accuracy_dimension_grade=#{partAccuracyDimensionGrade} WHERE part_id=#{partId};")
    void updatePart(Integer partId, String partName, String partUsageDescription, Integer partAccuracyDimensionGrade);
}