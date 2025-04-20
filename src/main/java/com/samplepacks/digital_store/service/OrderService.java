package com.samplepacks.digital_store.service;

import com.samplepacks.digital_store.entity.LocalUser;
import com.samplepacks.digital_store.entity.WebOrder;
import com.samplepacks.digital_store.repository.WebOrderDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final WebOrderDAO webOrderDAO;
    public List<WebOrder> getOrders(LocalUser user){
        return webOrderDAO.findByUser(user);
    }
}