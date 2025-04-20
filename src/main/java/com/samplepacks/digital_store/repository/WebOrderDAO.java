package com.samplepacks.digital_store.repository;

import com.samplepacks.digital_store.entity.LocalUser;
import com.samplepacks.digital_store.entity.WebOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface WebOrderDAO extends ListCrudRepository<WebOrder, Long> {
    List<WebOrder> findByUser(LocalUser user);
}