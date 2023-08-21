package com.example.commerce.domains.item.presentation;

import com.example.commerce.common.exception.SuccessCode;
import com.example.commerce.common.exception.response.SuccessResponse;
import com.example.commerce.domains.item.service.ItemCreateRequestDTO;
import com.example.commerce.domains.item.service.ItemResponseDTO;
import com.example.commerce.domains.item.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 상품 조회
    @GetMapping("/items/{id}")
    public SuccessResponse<ItemResponseDTO> findItem(@PathVariable Long id){
        ItemResponseDTO data = itemService.findItem(id);
        return SuccessResponse.of(SuccessCode.GET_INFO_SUCCESS, data);
    }

    // 상품 저장
    @PostMapping("/items")
    public SuccessResponse<ItemResponseDTO> saveItem(@RequestBody @Valid ItemCreateRequestDTO request){
        ItemResponseDTO data = itemService.saveItem(request);
        return SuccessResponse.of(SuccessCode.CREATE_SUCCESS, data);
    }
}
