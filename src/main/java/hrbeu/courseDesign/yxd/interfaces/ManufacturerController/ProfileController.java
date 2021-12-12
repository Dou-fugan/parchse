package hrbeu.courseDesign.yxd.interfaces.ManufacturerController;

import hrbeu.courseDesign.yxd.domain.mapper.manufacturer.ManufacturerMapper;
import hrbeu.courseDesign.yxd.domain.model.manufacturer.Manufacturer;
import hrbeu.courseDesign.yxd.domain.shiro.entity.User;
import hrbeu.courseDesign.yxd.domain.shiro.vo.Resp;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/AuthYes")
public class ProfileController {

    @Autowired
    ManufacturerMapper manufacturerMapper;

    @GetMapping("/queryProfile")
    Manufacturer queryProfile(){
        hrbeu.courseDesign.yxd.domain.pojo.Manufacturer manufacturerPojo = null;
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        manufacturerPojo=manufacturerMapper.selectManufacturer(user.getId());
        Manufacturer manufacturerModel=new Manufacturer();
        manufacturerModel.usccode=manufacturerPojo.usccode;
        manufacturerModel.manufacturer_name=manufacturerPojo.manufacturerName;
        manufacturerModel.address=manufacturerPojo.address;
        manufacturerModel.agent=manufacturerPojo.agent;
        manufacturerModel.telephone_number=manufacturerPojo.telephoneNumber;
        return manufacturerModel;
    }

    @PostMapping("/updateProfile")
    ResponseEntity<Resp> updateProfile(HttpServletRequest request,@RequestBody Manufacturer manufacturer){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        try {
            if(manufacturer.getUsccode()!=null){
                //检查是否更改的是自己的企业（usccode是否一致）
                if(manufacturer.getUsccode().equals(manufacturerMapper.selectUsccode(user.getId()))){
                    manufacturerMapper.updateManufacturer(manufacturer.getUsccode(),manufacturer.manufacturer_name,
                            manufacturer.address,manufacturer.agent,manufacturer.telephone_number);
                }else {
                    throw new Exception("警告：不能更改他人企业的信息");
                }
            }else{
                throw new Exception("缺少主键：社会统一信用代码");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body((new Resp(e.getMessage())));
        }
        return ResponseEntity.ok((new Resp("更新成功")));
    }
}
