package org.interactor.users;

import org.interactor.router.Controller;
import org.interactor.router.ResponseBody;

public class UsersWithOneParamController implements Controller {
    private String id;

    public UsersWithOneParamController(String id) {
        this.id = id;
    }

    @Override
    public ResponseBody getResponse() {
        return new ResponseBody(id, 200);
    }
}
