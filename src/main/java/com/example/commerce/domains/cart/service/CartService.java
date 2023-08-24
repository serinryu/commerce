package com.example.commerce.domains.cart.service;

import com.example.commerce.domains.cart.domain.CartEntity;
import com.example.commerce.domains.cart.domain.CartLine;
import com.example.commerce.domains.cart.domain.CartRepository;
import com.example.commerce.domains.item.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    // 장바구니 생성 기능
    public Long createCart(Long memberId){
        return cartRepository.save(new CartEntity(memberId))
                .getCartId();
    }

    // 장바구니 아이템 추가 기능
    public void addItemToCart(Long memberId, AddToCartRequestForm addToCartRequestForm){
        CartEntity cartEntity = cartRepository.findById(memberId).get();

        CartLine newCartLine = new CartLine(cartEntity.getCartId(),
                addToCartRequestForm.getItemId(),
                addToCartRequestForm.getOrderCount());

        int targetStockQuantity = itemRepository.findById(addToCartRequestForm.getItemId())
                .get()
                .getStockQuantity();

        // 엔티티 값 수정
        cartEntity.addItemToCart(targetStockQuantity, newCartLine);
    }

    // 장바구니 수량 변경
    public void modifyOrderCount(Long memberId, ModifyOrderCountRequestForm modifyOrderCountRequestForm) {
        CartEntity cartEntity = cartRepository.findById(memberId).get();

        CartLine newCartLine = new CartLine(cartEntity.getCartId(),
                modifyOrderCountRequestForm.getItemId(),
                modifyOrderCountRequestForm.getOrderCount());

        int targetStockQuantity = itemRepository.findById(modifyOrderCountRequestForm.getItemId())
                .get()
                .getStockQuantity();

        // 엔티티 값 수정
        cartEntity.modifyOrderCount(targetStockQuantity, newCartLine);
    }

    // 장바구니 안의 상품 삭제 기능 (상품 여러개)
    public void removeCartLines(Long memberId, List<Long> itemIds) {
        CartEntity cartEntity = cartRepository.findById(memberId).get();
        itemIds.stream().forEach(itemId -> cartEntity.removeCartLine(itemId));
    }

    // 장바구니 안의 상품 삭제 기능 (상품 1개)
    public void removeCartLine(Long memberId, Long itemId) {
        CartEntity cartEntity = cartRepository.findById(memberId).get();
        cartEntity.removeCartLine(itemId);
    }
}
