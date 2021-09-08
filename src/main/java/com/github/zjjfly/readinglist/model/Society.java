package com.github.zjjfly.readinglist.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjjfly[https://github.com/zjjfly] on 2020/4/16
 */
@Data
public class Society {

    private String name;

    public static String Advisors = "advisors";

    public static String President = "president";

    private List<Inventor> members = new ArrayList<>();

    private Map officers = new HashMap();

    public boolean isMember(String name) {
        for (Inventor inventor : members) {
            if (inventor.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
