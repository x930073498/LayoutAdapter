package com.x930073498;


import java.util.HashMap;

/**
 * Created by x930073498 on 2019/1/7 0007.
 */
@SuppressWarnings("ALL")
public class RegisterRepository {
    private static HashMap<String, Register> map = new HashMap<>();

    private static void register(Register register) {
        if (register == null) return;
        map.put(register.getId(), register);
    }

    public static void run(String id) {
        Register register = map.get(id);
        if (register == null) return;
        register.run();
    }

    public static void remove(String id) {
        map.remove(id);
    }


}
