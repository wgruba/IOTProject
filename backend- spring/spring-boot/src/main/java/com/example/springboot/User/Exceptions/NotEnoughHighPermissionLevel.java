package com.example.springboot.User.Exceptions;

import com.example.springboot.User.PermissionLevel;

public class NotEnoughHighPermissionLevel extends Exception {
    public NotEnoughHighPermissionLevel(PermissionLevel tempPermission) {
    }
}
