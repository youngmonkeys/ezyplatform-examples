package org.youngmonkeys.bookstore.web.service;

import com.tvd12.ezyfox.io.EzyLists;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ecommerce.entity.ShopProductPriceListProduct;
import org.youngmonkeys.ecommerce.repo.ShopProductPriceListProductRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class WebProductPriceListProductService {

    private final ShopProductPriceListProductRepository priceListProductRepository;

    public List<Long> getProductIdsByPriceListId(
        long priceListId,
        int limit
    ) {
        return EzyLists.newArrayList(
            priceListProductRepository.findByPriceListIdAndIdGtOrderByIdAsc(
                priceListId,
                0L,
                Next.limit(limit)
            ),
            ShopProductPriceListProduct::getProductId
        );
    }
}
