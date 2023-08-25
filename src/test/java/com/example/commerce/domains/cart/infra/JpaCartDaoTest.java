package com.example.commerce.domains.cart.infra;

import com.example.commerce.domains.cart.domain.CartLine;
import com.example.commerce.domains.cart.domain.CartRepository;
import com.example.commerce.domains.cart.query.dto.CartLineDto;
import com.example.commerce.domains.cart.service.AddToCartRequestForm;
import com.example.commerce.domains.cart.service.CartService;
import com.example.commerce.domains.item.domain.ItemEntity;
import com.example.commerce.domains.item.domain.ItemRepository;
import com.example.commerce.domains.member.service.MemberService;
import com.example.commerce.domains.member.service.MemberSignUpRequestDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class JpaCartDaoTest {
    @Autowired
    private JpaCartDao dao;

    @Autowired
    private MemberService memberService;
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private EntityManager em;

    private Long TEST_MEMBER_ID;
    private Long TEST_MEMBER_ID2;
    private Long TEST_ITEM_ID;
    private Long TEST_ITEM_ID2;

    @BeforeEach
    public void init() {
        TEST_MEMBER_ID = createMember("M1");
        TEST_ITEM_ID = createItem("i1");
        TEST_ITEM_ID2 = createItem("i2");

        TEST_MEMBER_ID2 = createMember("M2");
        createItem("i3");
        createItem("i4");
    }

    @Test
    public void Cart페이지_DTO요청() throws Exception {
        // given
        // member1의 장바구니에 아이템 추가
        addItemToCart(TEST_MEMBER_ID, TEST_ITEM_ID);
        addItemToCart(TEST_MEMBER_ID, TEST_ITEM_ID2);

        // member2의 장바구니에 아이템 추가
        addItemToCart(TEST_MEMBER_ID2, TEST_ITEM_ID);
        addItemToCart(TEST_MEMBER_ID2, TEST_ITEM_ID2);

        // when
        // Custom Repository 인 JpaCartDao 로 조회한 아이템들
        List<CartLineDto> cartInCartPage = dao.getCartLineListInCartPage(TEST_MEMBER_ID);
        em.clear();

        // then
        // JpaRepository interface 를 사용하고 있는 cartRepository 으로 조회한 아이템
        Map<Long, CartLine> cart = cartRepository.findFirstByMemberId(TEST_MEMBER_ID)
                .getCartLines();

        // Custom Repository 에서 얻는 데이터는 Item 데이터을 포함하므로 훨씬 많지만,
        // 사이즈는 해당 고객의 장바구니 안의 아이템 갯수인 cartLines 의 갯수로 동일해야함.
        assertEquals(cartInCartPage.size(), cart.size(), "dao로 조회한 아이템 사이즈와, 사용자의 장바구니 사이즈가 일치해야함");
    }

    // ============ HELPER =============
    private Long createMember(String authId) {
        return memberService.signUp(
                new MemberSignUpRequestDTO(authId, "TEST", authId, "TEST", "TEST", "TEST")
        ).getId();
    }

    private Long createItem(String itemName) {
        ItemEntity itemEntity = ItemEntity.builder()
                .imagePath("TEST")
                .name(itemName)
                .price(1000)
                .stockQuantity(3)
                .build();

        return itemRepository.save(itemEntity)
                .getId();
    }

    private void addItemToCart(Long memberId, Long itemId) {
        int orderCount = 1;
        AddToCartRequestForm request = new AddToCartRequestForm();
        request.setItemId(itemId);
        request.setOrderCount(orderCount);

        //when
        cartService.addItemToCart(memberId, request);
    }
}
