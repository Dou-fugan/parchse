package hrbeu.courseDesign.yxd.domain.mapper.manufacturer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hrbeu.courseDesign.yxd.domain.pojo.Quotation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuotationMapper extends BaseMapper<Quotation>{
    //增，确定增加的默认值
    @Insert("INSERT INTO quotation VALUES (#{usccode},#{partId},#{projectId},#{procurementStatus},#{price},#{quantity});")
    void insert(String usccode, Integer partId, Integer projectId, Character procurementStatus, Double price, Integer quantity);

    //删，需要在删除之前判断审核状态位
    @Delete("DELETE FROM quotation WHERE usccode =#{usccode} AND part_id=#{partId} AND project_id=#{projectId};")
    void delete(String usccode, Integer partId, Integer projectId);

    //搜索要修改或者删除的援助，验证是状态位是否为a
    @Select("SELECT * FROM `quotation` WHERE usccode=#{usccode} AND part_id=#{partId} AND project_id=#{projectId} ;")
    Quotation select(String usccode, Integer partId, Integer projectId);

    //不修改状态位
    @Update("UPDATE quotation SET " +
            "price=#{price} ," +
            "quantity=#{quantity}" +
            " WHERE part_id=#{partId} and `project_id`=#{projectId} and usccode=#{usccode};")
    void update(String usccode, Integer partId, Integer projectId, Double price, Integer quantity);

    @Select("SELECT * FROM quotation WHERE " +
            "usccode LIKE CONCAT('%',#{s},'%') and " +
            "part_id LIKE CONCAT('%',#{s1},'%') and " +
            "project_id LIKE CONCAT('%',#{s2},'%') and " +
            "procurement_status LIKE CONCAT('%',#{s3},'%') and " +
            "price LIKE CONCAT('%',#{s4},'%') and " +
            "quantity LIKE CONCAT('%',#{s5},'%') " +
            "LIMIT #{limit} OFFSET #{offset}")
    List<Quotation> queryQuotation(String s, String s1, String s2, String s3, String s4, String s5, Integer limit, int offset);

    //搜索count；此处有逻辑bug，查询返回的总数应该是符合搜索条件的总数
    @Select("SELECT COUNT(*) FROM quotation;")
    Integer getCount();
    //改，判断授权标志位，如果已经审核不予修改
    //查，查询特定的Quotation
    //查询特定元组的审核状态位
}
