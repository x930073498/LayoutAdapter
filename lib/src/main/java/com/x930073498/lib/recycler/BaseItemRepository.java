package com.x930073498.lib.recycler;

import android.support.v4.util.ArrayMap;

/**
 * Created by x930073498 on 2019/1/7 0007.
 */
public class BaseItemRepository {

    private static ArrayMap<String, Class<? extends BaseItem>> map = new ArrayMap<>();

    final static String ID = "BaseItemRepository";



    interface ItemTransfer {
        BaseItem get(Class<? extends BaseItem> clazz);
    }

    public static class ClassItemTransfer implements ItemTransfer {
        @Override
        public BaseItem get(Class<? extends BaseItem> clazz) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public static void register(String itemId, Class<? extends BaseItem> clazz) {
        map.put(itemId, clazz);
    }

    public static BaseItem create(String id) {
        return create(id, new ClassItemTransfer());
    }

    public static BaseItem create(String id, ItemTransfer transfer) {
        return transfer.get(map.get(id));
    }

}
