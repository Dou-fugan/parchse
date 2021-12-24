package hrbeu.courseDesign.yxd.interfaces.ManufacturerController;

import hrbeu.courseDesign.yxd.domain.mapper.login.RegisterUserMapper;
import hrbeu.courseDesign.yxd.domain.mapper.manufacturer.PartMapper;
import hrbeu.courseDesign.yxd.domain.model.TableModel;
import hrbeu.courseDesign.yxd.domain.model.manufacturer.AddPart;
import hrbeu.courseDesign.yxd.domain.model.manufacturer.QueryParts;
import hrbeu.courseDesign.yxd.domain.model.manufacturer.UpdateParts;
import hrbeu.courseDesign.yxd.domain.shiro.entity.User;
import hrbeu.courseDesign.yxd.domain.shiro.vo.Resp;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/AuthYes")
public class PartsController {

    @Autowired
    PartMapper partMapper;

    @Autowired
    RegisterUserMapper registerUserMapper;

    @PostMapping("/addPart")
    @Transactional
    ResponseEntity<Resp> addPart(@RequestBody AddPart addPart){
        if(addPart.getPartAccuracyDimensionGrade()==null){
            addPart.setPartAccuracyDimensionGrade(0);
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        //这是一个包含事务的插入
        try {
            partMapper.insertPart(
                    addPart.getPartName(),
                    addPart.getPartUsageDescription(),
                    addPart.getPartAccuracyDimensionGrade()
            );
            partMapper.insertProduction(
                    registerUserMapper.getUsccode(String.valueOf(user.getId()))
                    );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body((new Resp(e.getMessage())));
        }
        return ResponseEntity.ok((new Resp("增加成功")));
    }

    @PostMapping("/queryParts")
    TableModel quaryParts(@RequestBody QueryParts queryParts){
        List<hrbeu.courseDesign.yxd.domain.pojo.Part> queryList=null;
        //说明不是第一次搜索，而是重载的，那么后面的也一定有数据
        if(queryParts.getPartName()==null){
            queryList=partMapper.queryParts("","","",
                    queryParts.getLimit(),
                    queryParts.getLimit()*(queryParts.getPage()-1));
        }else {
            queryList=partMapper.queryParts(
                    queryParts.getPartName(),
                    queryParts.getPartUsageDescription(),
                    String.valueOf(queryParts.getPartAccuracyDimensionGrade()),
                    queryParts.getLimit(),
                    queryParts.getLimit()*(queryParts.getPage()-1)
            );
        }

        TableModel value=new TableModel();
        value.setCode(0);
        value.setCount(partMapper.getCount());
        value.setData(queryList);
        value.setMsg("");
        return value;
    }

    @PostMapping("/updateParts")
    ResponseEntity<Resp> updateParts(@RequestBody UpdateParts updateParts){
        if(updateParts.getPartName()==null){
            updateParts.setPartName("");
        }
        if(updateParts.getPartUsageDescription()==null){
            updateParts.setPartUsageDescription("");
        }
        if(updateParts.getPartAccuracyDimensionGrade()==null){
            updateParts.setPartAccuracyDimensionGrade(0);
        }

        try {
            partMapper.updatePart(
                updateParts.getPartId(), updateParts.getPartName(),updateParts.getPartUsageDescription(),updateParts.getPartAccuracyDimensionGrade()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body((new Resp(e.getMessage())));
        }
        return ResponseEntity.ok((new Resp("增加成功")));
    }
}
