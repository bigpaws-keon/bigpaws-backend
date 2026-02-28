package com.teamkeon.bigpawsbackend.service;

import com.teamkeon.bigpawsbackend.domain.Bookmark;
import com.teamkeon.bigpawsbackend.domain.Place;
import com.teamkeon.bigpawsbackend.domain.User;
import com.teamkeon.bigpawsbackend.dto.PlaceResponse;
import com.teamkeon.bigpawsbackend.repository.BookmarkRepository;
import com.teamkeon.bigpawsbackend.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final PlaceRepository placeRepository;
    private final PlaceService placeService;

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Transactional
    public boolean toggle(Long placeId) {
        User user = getCurrentUser();
        if (bookmarkRepository.existsByUserIdAndPlaceId(user.getId(), placeId)) {
            bookmarkRepository.deleteByUserIdAndPlaceId(user.getId(), placeId);
            return false; // 찜 해제
        } else {
            Place place = placeRepository.findById(placeId)
                    .orElseThrow(() -> new RuntimeException("장소를 찾을 수 없습니다."));
            bookmarkRepository.save(Bookmark.builder().user(user).place(place).build());
            return true; // 찜 추가
        }
    }

    public boolean isBookmarked(Long placeId) {
        User user = getCurrentUser();
        return bookmarkRepository.existsByUserIdAndPlaceId(user.getId(), placeId);
    }

    public List<Long> getMyBookmarkPlaceIds() {
        User user = getCurrentUser();
        return bookmarkRepository.findByUserId(user.getId()).stream()
                .map(b -> b.getPlace().getId())
                .toList();
    }
}
