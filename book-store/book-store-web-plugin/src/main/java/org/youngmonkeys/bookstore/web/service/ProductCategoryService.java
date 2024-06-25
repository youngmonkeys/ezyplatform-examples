package org.youngmonkeys.bookstore.web.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.web.dto.CategoryMenuItem;
import org.youngmonkeys.bookstore.web.repository.ProductCategoryRepository;
import org.youngmonkeys.ecommerce.entity.ProductCategory;
import org.youngmonkeys.ecommerce.entity.ProductCategoryParent;
import org.youngmonkeys.ecommerce.repo.ProductCategoryParentRepository;
import org.youngmonkeys.ecommerce.service.ProductCategoryParentService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EzySingleton
@AllArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryParentRepository productCategoryParentRepository;
    private final ProductCategoryRepository myProductCategoryService;
    private final ProductCategoryParentService productCategoryParentService;

    public List<CategoryMenuItem> getListCategoryMenuItems() {
        List<CategoryMenuItem> menuItems = new ArrayList<>();
        List<Long> categoryParentIds = productCategoryParentRepository
                .findAll()
                .stream()
                .map(ProductCategoryParent::getCategoryId)
                .collect(Collectors.toList());

        List<ProductCategory> firstGenerationCategories = myProductCategoryService.findByIdNotIn(categoryParentIds);
        firstGenerationCategories.forEach(
                first -> {
                    CategoryMenuItem menuItem = new CategoryMenuItem();
                    menuItem.setId(first.getId());
                    menuItem.setDisplayName(first.getName());
                    menuItem.setDisplayOrder(first.getDisplayOrder());
                    menuItem.setMenuItems(getChildMenu(first.getId()));
                    menuItems.add(menuItem);
                }
        );
        return menuItems;
    }

    private List<CategoryMenuItem> getChildMenu(long parentId) {
        List<CategoryMenuItem> menuItems = new ArrayList<>();
        productCategoryParentService.getChildIdsByCategoryId(parentId).forEach(
                child -> {
                    ProductCategory productCategory = myProductCategoryService.findById(child);
                    CategoryMenuItem menuItem = new CategoryMenuItem();
                    menuItem.setId(productCategory.getId());
                    menuItem.setDisplayName(productCategory.getName());
                    menuItem.setDisplayOrder(productCategory.getDisplayOrder());
                    menuItem.setMenuItems(getChildMenu(productCategory.getId()));
                    menuItems.add(menuItem);
                }
        );
        return menuItems;
    }
}
