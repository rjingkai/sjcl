package org.dcfj.sjcl.db1.service;

import org.dcfj.sjcl.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

public interface userServiceDb1 {
    List<User> getUser();
}
