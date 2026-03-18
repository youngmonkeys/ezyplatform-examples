package org.youngmonkeys.bookstore.web.service;

import com.tvd12.ezyfox.util.Next;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ecommerce.entity.ProductPriceListStatus;
import org.youngmonkeys.ecommerce.entity.ProductPriceListType;
import org.youngmonkeys.ecommerce.entity.ShopProductPriceList;
import org.youngmonkeys.ecommerce.repo.ShopProductPriceListRepository;
import org.youngmonkeys.ezyplatform.time.ClockProxy;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class WebProductPriceListService {

    private final ShopProductPriceListRepository shopProductPriceListRepository;
    private final ClockProxy clock;

    public ShopProductPriceList getActiveFlashSalePriceList() {
        LocalDateTime now = clock.nowDateTime();
        int limit = 100;
        List<ShopProductPriceList> priceLists =
            shopProductPriceListRepository
                .findByTypeAndStatusAndStartApplyAtLteOrderByStartApplyAtAscIdAsc(
                    ProductPriceListType.SCHEDULED_APPLY.toString(),
                    ProductPriceListStatus.APPLIED.toString(),
                    now,
                    Next.limit(limit)
                );

        for (ShopProductPriceList priceList : priceLists) {
            LocalDateTime finishApplyAt = priceList.getFinishApplyAt();
            if (finishApplyAt == null || finishApplyAt.isAfter(now)) {
                return priceList;
            }
        }
        return null;
    }

}
