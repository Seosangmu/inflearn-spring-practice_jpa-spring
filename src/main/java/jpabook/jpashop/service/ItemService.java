package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 상품 저장
     */
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     * 상품 수정
     */
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity, String author, String isbn/*파라미터가 많다면 DTO같은 걸 만들어 넣어보자*/) {
        Book findItem = (Book) itemRepository.findOne(itemId); // 영속성 엔티티

        // 아래처럼 set을 열어 수정하면 나중에 유지보수성에 좋지않다.
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
        findItem.setAuthor(author);
        findItem.setIsbn(isbn);

        // 아래처럼 엔티티에 명확한 메소드를 만들어 호출하는게 유지보수성에 더 좋다.
        // findItem.changeItem(name, price, stockQuantity);

    }


    /**
     * 상품 전체 조회
     */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    /**
     * 상품 단건 조회
     */
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
