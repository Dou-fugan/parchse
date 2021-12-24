package hrbeu.courseDesign.yxd.domain.mapper.manufacturer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hrbeu.courseDesign.yxd.domain.pojo.Manufacturer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ManufacturerMapper extends BaseMapper<Manufacturer> {

    @Update("UPDATE `manufacturer` SET manufacturer_name=#{manufacturer_name},address=#{address},agent=#{agent},telephone_number=#{telephone_number} WHERE usccode=#{usccode}")
    void updateManufacturer(String usccode, String manufacturer_name,String address, String agent, String telephone_number);

    @Select("SELECT * FROM `manufacturer` WHERE usccode=(SELECT usccode FROM `system_shiro_user` WHERE id = #{id});")
    Manufacturer selectManufacturer(int id);

    @Select("SELECT usccode FROM `system_shiro_user` WHERE id = #{id};")
    String selectUsccode(int id);


}