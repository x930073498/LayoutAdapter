package com.x930073498.layoutadapter.vm;

import com.x930073498.lib.recycler.BaseItemRepository;

/**
 * Created by x930073498 on 2019/1/7 0007.
 */
public class TestItemRegister {
    static {
        BaseItemRepository.register("1",TestItem.class);
    }
}
