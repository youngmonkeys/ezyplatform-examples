package org.youngmonkeys.personal.admin.it.repo;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.test.assertion.Asserts;
import lombok.AllArgsConstructor;
import org.youngmonkeys.devtools.InstanceRandom;
import org.youngmonkeys.personal.admin.repo.AdminPersonalPostWordCountRepository;
import org.youngmonkeys.personal.entity.PersonalPostWordCount;
import org.youngmonkeys.ezyplatform.test.IntegrationTest;

@EzySingleton
@AllArgsConstructor
public class AdminPersonalPostWordCountRepositoryIT implements IntegrationTest {

    private final AdminPersonalPostWordCountRepository personalPostWordCountRepository;

    @Override
    public void test() throws Exception {
        saveFindTest();
    }

    private void saveFindTest() {
        // given
        PersonalPostWordCount entity = new InstanceRandom().randomObject(PersonalPostWordCount.class);

        // when
        personalPostWordCountRepository.save(entity);
        PersonalPostWordCount actual = personalPostWordCountRepository.findById(entity.getPostId());

        // then
        Asserts.assertNotNull(actual);
        personalPostWordCountRepository.delete(entity.getPostId());
    }
}
