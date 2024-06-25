package org.youngmonkeys.bookstore.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryMenuItem {
    private long id;
    private String displayName;
    private int displayOrder;
    private String url;
    private List<CategoryMenuItem> menuItems;
}
