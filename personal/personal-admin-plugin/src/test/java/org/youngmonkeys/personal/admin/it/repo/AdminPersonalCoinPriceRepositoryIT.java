package org.youngmonkeys.personal.admin.it.repo;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.test.assertion.Asserts;
import lombok.AllArgsConstructor;
import org.youngmonkeys.devtools.InstanceRandom;
import org.youngmonkeys.ezyplatform.test.IntegrationTest;
import org.youngmonkeys.personal.admin.repo.AdminPersonalCoinPriceRepository;
import org.youngmonkeys.personal.entity.PersonalCoinPrice;

@EzySingleton
@AllArgsConstructor
public class AdminPersonalCoinPriceRepositoryIT implements IntegrationTest {

    private final AdminPersonalCoinPriceRepository personalCoinPriceRepository;

    @Override
    public void test() throws Exception {
        saveFindTest();
    }

    private void saveFindTest() {
        // given
        PersonalCoinPrice entity = new InstanceRandom().randomObject(PersonalCoinPrice.class);
        entity.setSymbol("test");

        // when
        personalCoinPriceRepository.save(entity);
        PersonalCoinPrice actual = personalCoinPriceRepository.findById(entity.getId());

        // then
        Asserts.assertNotNull(actual);
        personalCoinPriceRepository.delete(entity.getId());
    }
}
