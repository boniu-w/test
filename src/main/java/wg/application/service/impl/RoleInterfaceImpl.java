package wg.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wg.application.entity.Role;
import wg.application.mapper.RoleRepository;
import wg.application.service.RoleInterface;

import java.util.List;

/*************************************************************
 * @Package wg.application.service.impl
 * @author wg
 * @date 2020/8/7 11:32
 * @version
 * @Copyright
 *************************************************************/
@Service
public class RoleInterfaceImpl implements RoleInterface {

    @Autowired
    RoleRepository roleRepository;

    public String[] getRoleName() {
        List<Role> all = roleRepository.findAll();
        String[] roleNames = new String[all.size()];
        for (int i = 0; i < all.size(); i++) {
            String roleName = all.get(i).getRoleName();
            roleNames[i] = roleName;
        }
        return roleNames;
    }

    public String[] getRoleCode() {
        List<Role> all = roleRepository.findAll();
        String[] roleCodes = new String[all.size()];
        for (int i = 0; i < all.size(); i++) {
            String roleCode = all.get(i).getRoleCode();
            roleCodes[i] = roleCode;
        }
        return roleCodes;
    }

}
