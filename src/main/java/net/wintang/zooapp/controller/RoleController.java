package net.wintang.zooapp.controller;

import net.wintang.zooapp.service.IRoleService;
import net.wintang.zooapp.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final IRoleService roleService;

    @Autowired
    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{role}/users")
    public ResponseEntity<ResponseObject> getUsersByRole(@PathVariable String role) {
        return roleService.getUsersByRoles(role);
    }
}
