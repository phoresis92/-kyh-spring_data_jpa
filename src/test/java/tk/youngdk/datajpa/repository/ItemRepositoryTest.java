package tk.youngdk.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.youngdk.datajpa.domain.Item;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void save() {
        Item item = new Item("A");

        itemRepository.save(item);


        /*
        entityInformation.isNew();
        식별자가
        1. 객체일 경우 null
        2. 기본 타입일 경우 0
        으로 판단
        */
    }

}