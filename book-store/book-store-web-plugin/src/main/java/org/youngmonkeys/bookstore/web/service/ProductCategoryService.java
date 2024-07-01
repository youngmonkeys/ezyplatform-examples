package org.youngmonkeys.bookstore.web.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.web.dto.CategoryMenuItem;
import org.youngmonkeys.bookstore.web.repository.ProductCategoryRepository;
import org.youngmonkeys.ecommerce.entity.ProductCategory;
import org.youngmonkeys.ecommerce.entity.ProductCategoryParent;
import org.youngmonkeys.ecommerce.repo.ProductCategoryParentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EzySingleton
@AllArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryParentRepository productCategoryParentRepository;
    private final ProductCategoryRepository productCategoryService;

    public List<CategoryMenuItem> getListCategoryMenuItems() {
        List<CategoryMenuItem> menuItems = new ArrayList<>();
        List<Long> categoryParentIds = productCategoryParentRepository.findAll().stream().map(ProductCategoryParent::getCategoryId).collect(Collectors.toList());

        List<ProductCategory> allCategories = productCategoryService.findAll();
        List<ProductCategoryParent> productCategoryParents = productCategoryParentRepository.findAll();
        allCategories.stream().filter(category -> !categoryParentIds.contains(category.getId())).forEach(category -> {
            CategoryMenuItem categoryMenuItem = new CategoryMenuItem();
            categoryMenuItem.setId(category.getId());
            categoryMenuItem.setDisplayName(category.getDisplayName());
            categoryMenuItem.setDisplayOrder(category.getDisplayOrder());
            categoryMenuItem.setMenuItems(getChildMenu(category, productCategoryParents));
            menuItems.add(categoryMenuItem);
        });
        return menuItems;
    }

    public List<CategoryMenuItem> getChildMenu(ProductCategory productCategory, List<ProductCategoryParent> productCategoryParents) {
        List<CategoryMenuItem> childMenu = new ArrayList<>();

        productCategoryParents.forEach(parentId -> {
            if (parentId.getParentId() == productCategory.getId()) {
                CategoryMenuItem menuItem = new CategoryMenuItem();
                ProductCategory childOfThisParent = productCategoryService.findById(parentId.getCategoryId());
                menuItem.setId(childOfThisParent.getId());
                menuItem.setDisplayName(childOfThisParent.getDisplayName());
                menuItem.setDisplayOrder(childOfThisParent.getDisplayOrder());
                menuItem.setMenuItems(getChildMenu(childOfThisParent, productCategoryParents));
                childMenu.add(menuItem);
            }
        });
        return childMenu;
    }
}
