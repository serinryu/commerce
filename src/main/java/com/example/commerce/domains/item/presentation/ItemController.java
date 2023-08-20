package com.example.commerce.domains.item.presentation;

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
    public ResponseEntity<ItemResponseDTO> findItem(@PathVariable Long id){
        ItemResponseDTO response = itemService.findItem(id);
        return ResponseEntity.ok().body(response);
    }

    // 상품 저장
    @PostMapping("/items")
    public ResponseEntity<ItemResponseDTO> saveItem(@RequestBody @Valid ItemCreateRequestDTO request){
        ItemResponseDTO response = itemService.saveItem(request);
        return ResponseEntity.status(201).body(response);
    }
}
