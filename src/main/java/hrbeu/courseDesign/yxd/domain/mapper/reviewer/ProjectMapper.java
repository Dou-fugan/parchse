package hrbeu.courseDesign.yxd.domain.mapper.reviewer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hrbeu.courseDesign.yxd.domain.pojo.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
    @Select("SELECT * FROM projects")
    List<Project> selectProject();

    @Select("SELECT COUNT(*) FROM projects "+
            "LIMIT #{limits} OFFSET #{offset}")
    Integer getCount(int limits,int offset);
}
