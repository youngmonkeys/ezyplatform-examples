package org.youngmonkeys.personal.web.repo;

import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import org.youngmonkeys.ezyplatform.entity.Admin;

import java.util.Collection;
import java.util.List;

@EzyRepository
public interface WebPersonalAdminRepository
    extends EzyDatabaseRepository<Long, Admin> {

    List<Admin> findByUuidIn(Collection<String> uuids);
}
