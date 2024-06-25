package org.youngmonkeys.bookstore.web.repository;

import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import org.youngmonkeys.ecommerce.entity.ProductCategory;

import java.util.List;

@EzyRepository
public interface ProductCategoryRepository extends EzyDatabaseRepository<Long, ProductCategory> {

    @EzyQuery("SELECT DISTINCT e FROM ProductCategory e WHERE e.id NOT IN ?0 ")
    List<ProductCategory> findByIdNotIn(List<Long> parentIds);
}
