package com.example.demo.service;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Optional<Item> findItem(Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if(!item.isPresent()) {
            return null;
        }

        return item;
    }
}
