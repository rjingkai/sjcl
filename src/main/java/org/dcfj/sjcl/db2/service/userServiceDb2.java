package org.dcfj.sjcl.db2.service;

import org.dcfj.sjcl.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

public interface userServiceDb2 {
    List<User> getUser();
}
