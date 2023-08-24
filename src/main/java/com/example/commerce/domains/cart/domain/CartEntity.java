package com.example.commerce.domains.cart.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "cart")
@Getter
@NoArgsConstructor
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    // Member를 참조하는 외래키 역할
    private Long memberId;

    // 장바구니 안의 상품 리스트
    // Map을 이용하므로써 @ElementCollection의 단점을 보완 (itemId를 키로 사용)
    // 1. 수정시, 모든 로우를 삭제 후, 수정된 로우를 추가하는 문제
    // 2. 삭제시, 모든 로우를 삭제 후, 삭제 대상을 제외한 모든 로우를 다시 입력하는 문제
    @ElementCollection // 일대다 관계 대신 값 타입을 컬렉션으로 매핑
    @CollectionTable(name = "cart_line")
    @MapKeyColumn(name = "map_key")
    private Map<Long, CartLine> cartLines =  new HashMap<>();

    public CartEntity(Long memberId) {
        this.memberId = memberId;
    }

    //  Map 자료형인 cart 을 사용한 addItemToCart() 메서드 예시
    public void addItemToCart(CartLine cartLine) {
        Long itemId = cartLine.getItemId(); // cartLine 의 itemId 가 map_key 가 될 예정

        // 해당 itemId로 된 아이템이 존재하는 경우
        if (cartLines.containsKey(itemId)) {
            // 해당 아이템의 주문 수량을 업데이트
            int existCartItemOrderCount = cartLines.get(itemId).getOrderCount();
            int newOrderCount = existCartItemOrderCount + cartLine.getOrderCount();
            cartLines.replace(itemId, new CartLine(cartId, itemId, newOrderCount));
        }
        // 해당 itemId로 된 아이템이 존재하지 않는 경우
        else {
            // 장바구니에 추가
            cartLines.put(itemId, cartLine);
        }
    }

}
