package wg.application.service.impl;

import org.springframework.stereotype.Service;
import wg.application.service.RoleInterface;

@Service
public class RoleInterfaceImpl implements RoleInterface {
    @Override
    public String[] getRoleCode() {
        return new String[0];
    }
}
