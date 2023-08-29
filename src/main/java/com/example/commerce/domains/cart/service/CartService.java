package com.example.commerce.domains.cart.service;

import com.example.commerce.domains.cart.domain.CartEntity;
import com.example.commerce.domains.cart.domain.CartLine;
import com.example.commerce.domains.cart.domain.CartRepository;
import com.example.commerce.domains.cart.query.dao.CartDao;
import com.example.commerce.domains.cart.query.dto.CartLineDto;
import com.example.commerce.domains.item.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final CartDao cartDao; // DAO 인터페이스 (구현체는 JpaCartDao - @Repository)

    // 장바구니 조회 기능
    // 각각의 상품 이미지, 상품 정보, 상품 금액, 수량, 총 금액 등을 볼 수 있어야 한다.
    // 즉 보다 복잡한 쿼리문이 필요. -> JPQL 사용한 Custom Repository 인 cartDao 사용하여 데이터 받아온다.
    // memberId 에 해당되는 Cart 에 대해서 (where), CartLine + Item 테이블 조인 해서 상세 정보를 받아와야 한다.
    public List<CartLineDto> getCartInCartPage(Long memberId){
        return cartDao.getCartLineListInCartPage(memberId);
        //return cartRepository.findFirstByMemberId(memberId).getCartLines(); // itemId, orderCount 뿐이므로 부족함.
    }

    // 장바구니 생성 기능
    // 현재는 회원가입 시 마다 장바구니 생성. -> 단, 이 경우 도메인이 서로 의존하는 문제 발생
    public Long createCart(Long memberId){
        Optional<CartEntity> cart = cartRepository.findFirstByMemberId(memberId);
        // memberId 로 된 장바구니가 이미 존재할 경우
        if(cart.isPresent()) {
            return cart.get().getCartId();
        } else {
            return cartRepository.save(new CartEntity(memberId)).getCartId();
        }
    }

    // 장바구니 아이템 추가 기능
    public void addItemToCart(Long memberId, AddToCartRequestForm addToCartRequestForm){
        CartEntity cartEntity = cartRepository.findById(memberId).orElseThrow();

        // 장바구니 아이템 추가 요청 클래스인 AddToCartRequestForm을 이용해, CartLine을 생성
        CartLine newCartLine = new CartLine(cartEntity.getCartId(),
                addToCartRequestForm.getItemId(),
                addToCartRequestForm.getOrderCount());

        int targetStockQuantity = itemRepository.findById(addToCartRequestForm.getItemId())
                .orElseThrow()
                .getStockQuantity();

        // 엔티티 클래스의 addItemToCart()을 통해 값 수정, 즉 CartEntity에 수량변경 기능을 위임
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
    public void removeCartLine(Long memberId, List<Long> itemIds) {
        CartEntity cartEntity = cartRepository.findById(memberId).get();
        itemIds.stream().forEach(itemId -> cartEntity.removeCartLine(itemId));
    }

    // 장바구니 안의 상품 삭제 기능 (상품 1개)
    public void removeCartLine(Long memberId, Long itemId) {
        CartEntity cartEntity = cartRepository.findById(memberId).get();
        cartEntity.removeCartLine(itemId);
    }
}
