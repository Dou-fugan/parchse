package hrbeu.courseDesign.yxd.interfaces.ManufacturerController;

import hrbeu.courseDesign.yxd.domain.mapper.login.RegisterUserMapper;
import hrbeu.courseDesign.yxd.domain.mapper.manufacturer.QuotationMapper;
import hrbeu.courseDesign.yxd.domain.model.TableModel;
import hrbeu.courseDesign.yxd.domain.model.manufacturer.QueryQuotation;
import hrbeu.courseDesign.yxd.domain.model.manufacturer.QuotationModel;
import hrbeu.courseDesign.yxd.domain.pojo.Quotation;
import hrbeu.courseDesign.yxd.domain.shiro.entity.User;
import hrbeu.courseDesign.yxd.domain.shiro.vo.Resp;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/AuthYes")
public class QuotationController {
    @Autowired
    QuotationMapper quotationMapper;

    @Autowired
    RegisterUserMapper registerUserMapper;

    @PostMapping("/createQuotation")
    ResponseEntity<Resp> createQuotation(@RequestBody QuotationModel quotationModel){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        quotationModel.setUsccode(registerUserMapper.getUsccode(String.valueOf(user.getId())));
        try {
            quotationMapper.insert(
                    quotationModel.getUsccode(),
                    quotationModel.getPartId(),
                    quotationModel.getProjectId(),
                    'a',
                    0.0,
                    0
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body((new Resp(e.getMessage())));
        }
        return ResponseEntity.ok((new Resp("修改成功")));
    }

    @PostMapping("/deleteQuotation")
    ResponseEntity<Resp> deleteQuotation(@RequestBody QuotationModel quotationModel){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        quotationModel.setUsccode(registerUserMapper.getUsccode(String.valueOf(user.getId())));
        try {
            Quotation quotation=null;
            quotation=quotationMapper.select(
                    quotationModel.getUsccode(),
                    quotationModel.getPartId(),
                    quotationModel.getProjectId()
            );
            if(quotation.getProcurementStatus()=='b'){
                throw new Exception("经过审核的询价单不可被删除");
            }else {
                quotationMapper.delete(
                        quotationModel.getUsccode(),
                        quotationModel.getPartId(),
                        quotationModel.getProjectId()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body((new Resp(e.getMessage())));
        }
        return ResponseEntity.ok((new Resp("删除成功")));
    }

    @PostMapping("/updateQuotation")
    ResponseEntity<Resp> updateQuotation(@RequestBody QuotationModel quotationModel){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        quotationModel.setUsccode(registerUserMapper.getUsccode(String.valueOf(user.getId())));
        try {
            Quotation quotation=null;
            quotation=quotationMapper.select(
                    quotationModel.getUsccode(),
                    quotationModel.getPartId(),
                    quotationModel.getProjectId()
            );
            if(quotation.getProcurementStatus()=='b'){
                throw new Exception("经过审核的询价单不可被修改");
            }else {
                quotationMapper.update(
                        quotationModel.getUsccode(),
                        quotationModel.getPartId(),
                        quotationModel.getProjectId(),
                        quotationModel.getPrice(),
                        quotationModel.getQuantity()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body((new Resp(e.getMessage())));
        }
        return ResponseEntity.ok((new Resp("增加成功")));
    }

    @PostMapping("/queryQuotation")
    TableModel queryQuotation(@RequestBody QueryQuotation quotation){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        quotation.setUsccode(registerUserMapper.getUsccode(String.valueOf(user.getId())));
        List<Quotation> queryList=null;
        //说明不是第一次搜索，而是重载的，那么后面的也一定有数据
        if(quotation.getPartId()==null&&quotation.getProjectId()==null&&quotation.getProcurementStatus()==null){
            queryList=quotationMapper.queryQuotation("","","","","","",
                    quotation.getLimit(),
                    quotation.getLimit()*(quotation.getPage()-1));
        }else {
            queryList=quotationMapper.queryQuotation(
                    quotation.getUsccode(),
                    String.valueOf(quotation.getPartId()!=null?quotation.getPartId():""),
                    String.valueOf(quotation.getProjectId()!=null?quotation.getProjectId():""),
                    String.valueOf(quotation.getProcurementStatus()!=null?quotation.getProcurementStatus():""),
                    String.valueOf(quotation.getPrice()!=null?quotation.getPrice():""),
                    String.valueOf(quotation.getQuantity()!=null?quotation.getQuantity():""),
                    quotation.getLimit(),
                    quotation.getLimit()*(quotation.getPage()-1));
        }


        TableModel value=new TableModel();
        value.setCode(0);
        value.setCount(quotationMapper.getCount());
        value.setData(queryList);
        value.setMsg("");
        return value;
    }
}
