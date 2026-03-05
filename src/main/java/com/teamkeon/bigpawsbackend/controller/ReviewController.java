package com.teamkeon.bigpawsbackend.controller;

import com.teamkeon.bigpawsbackend.dto.ReviewCreateRequest;
import com.teamkeon.bigpawsbackend.dto.ReviewResponse;
import com.teamkeon.bigpawsbackend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/places/{placeId}/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public List<ReviewResponse> getReviews(@PathVariable Long placeId) {
        return reviewService.getReviewsByPlace(placeId);
    }

    /**
     * 실제 HTTP 요청 예시
     *
     *   POST /api/places/1/reviews HTTP/1.1
     *   Content-Type: multipart/form-data; boundary=----Boundary123
     *
     *   ------Boundary123
     *   Content-Disposition: form-data; name="review"
     *   Content-Type: application/json
     *
     *   {"rating": 5, "content": "강아지와 함께하기 최고입니다"}
     *   ------Boundary123
     *   Content-Disposition: form-data; name="images"; filename="dog1.jpg"
     *   Content-Type: image/jpeg
     *
     *   (바이너리 데이터... ÿØÿà...)
     *   ------Boundary123
     *   Content-Disposition: form-data; name="images"; filename="dog2.jpg"
     *   Content-Type: image/jpeg
     *
     *   (바이너리 데이터...)
     *   ------Boundary123--
     */

    // 멀티파트 : 말 그대로 여러 파트로 나뉜 데이터. 텍스트, JSON, 파일 등의 여러 파트의 데이터를 하나의 상자에 담아 전송하는 방식
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // 멀티파트만 받겠다는 속성. 다른 타입으로 오면 415 에러를 반환한다.
    public ReviewResponse createReview(
            @PathVariable Long placeId,
            // application/json일 때 -> @RequestBody. multipart/form-data일 때 -> @RequestPart
            @RequestPart("review") ReviewCreateRequest request, // value = "review": 멀티파트일 때 여러개의 데이터를 구분
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        return reviewService.createReview(placeId, request, images);
    }

    @PutMapping("/{reviewId}")
    public ReviewResponse updateReview(
            @PathVariable Long placeId,
            @PathVariable Long reviewId,
            @RequestBody ReviewCreateRequest request) {
        return reviewService.updateReview(reviewId, request);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long placeId, @PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
