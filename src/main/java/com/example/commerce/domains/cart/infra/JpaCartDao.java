package com.example.commerce.domains.cart.infra;

import com.example.commerce.domains.cart.query.dao.CartDao;
import com.example.commerce.domains.cart.query.dto.CartLineDto;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

// Custom Repository
// JPQL 사용, DAO 인터페이스 구현체
@Repository
public class JpaCartDao implements CartDao {

    private EntityManager em;
    public JpaCartDao(EntityManager em) {
        this.em = em;
    }

    // DAO 는 데이터베이스 data 에 접근하기 위한 객체. 즉, 접근은 DAO 로 하되 데이터베이스에서 조회해올때는 DTO 로 조회해옴.
    // DTO 로 조회하기 위해서 select 절에서 new Dto() 로 기입
    @Override
    public List<CartLineDto> getCartLineListInCartPage(Long memberId) {

        // ERD 살펴보며 아래 JPQL 작성
        // 장바구니 화면에 보여줄 데이터를 JPQL 로 조회해오기 위해서는 아래와 같은 조건이 필요함. :
        // 1. 조회할 Cart의 데이터중 member_id 컬럼은, 현재 로그인 중인 사용자의 id이어야 한다. (where)
        // 2. 조회할 Cart_Line의 데이터중 cart_id 컬럼은 Carts테이블의 cart_id와 같아야 한다.(join)
        // 3. 조회할 items의 데이터중 item_id는 cart_line에 포함된 item_id와 같아야 한다.(join)

        List<CartLineDto> cartLineDtoList = em
                .createQuery("select new com.example.commerce.domains.cart.query.dto.CartLineDto(i.id, i.imagePath, i.name, i.price, cl.orderCount, i.stockQuantity)" +
                        " from CartEntity c" +
                        " join c.cartLines cl" + // 조회할 Cart_Line
//                        " on c.cartId = cl.cartId" +
                        " join ItemEntity i" + // 조회할 Item
                        " on cl.itemId = i.id" +
                        " where c.memberId = :memberId", // 파라미터로 들어온 memberId
                        CartLineDto.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return cartLineDtoList;
    }
}
