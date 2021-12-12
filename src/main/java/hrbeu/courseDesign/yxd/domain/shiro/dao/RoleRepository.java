package hrbeu.courseDesign.yxd.domain.shiro.dao;

import hrbeu.courseDesign.yxd.domain.shiro.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by EalenXie on 2019/3/27 12:29.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
