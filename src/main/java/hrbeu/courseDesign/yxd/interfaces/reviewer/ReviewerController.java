package hrbeu.courseDesign.yxd.interfaces.reviewer;

import hrbeu.courseDesign.yxd.domain.mapper.reviewer.ProjectMapper;
import hrbeu.courseDesign.yxd.domain.model.TableModel;
import hrbeu.courseDesign.yxd.domain.pojo.Project;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/AuthYes")
public class ReviewerController {
    @Autowired
    ProjectMapper projectMapper;

    @PostMapping("/queryProject")
    TableModel queryProject(@RequestBody QueryBody queryBody){
        List<Project> list=null;
        list=projectMapper.selectProject();
        TableModel value=new TableModel();
        value.setCode(0);
        value.setCount(projectMapper.getCount(queryBody.limits,queryBody.limits*(queryBody.getPage()-1)));
        value.setData(list);
        value.setMsg("");
        return value;
    }
}

@Data
class QueryBody{
    int limits;
    int page;
}