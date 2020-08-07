package wg.application.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import wg.application.entity.Role;

/*************************************************************
 * @Package wg.application.mapper
 * @author wg
 * @date 2020/8/7 11:31
 * @version
 * @Copyright
 *************************************************************/
public interface RoleRepository extends JpaRepository<Role,String> {
}
