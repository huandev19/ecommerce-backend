package com.v8n.modules.cart.application.service;

import com.v8n.modules.cart.domain.entity.Cart;
import com.v8n.modules.cart.domain.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Scheduled service that cleans up expired/abandoned carts.
 * Carts that have not been completed and have been idle for more than the
 * configured expiration period (default: 24 hours) will be soft-deleted.
 */
@Service
public class CartCleanupService {

    private static final Logger log = LoggerFactory.getLogger(CartCleanupService.class);

    private static final int CART_EXPIRATION_HOURS = 24;

    private final CartRepository cartRepository;

    public CartCleanupService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * Runs every hour to soft-delete expired carts.
     * Carts older than 24 hours (not completed, not already deleted) are expired.
     */
    @Scheduled(fixedRate = 3600000) // every hour
    @Transactional
    public void cleanupExpiredCarts() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(CART_EXPIRATION_HOURS);
        List<Cart> expiredCarts = cartRepository.findExpiredCarts(cutoffTime);

        if (expiredCarts.isEmpty()) {
            return;
        }

        log.info("Found {} expired carts to clean up (threshold: {} hours)", expiredCarts.size(), CART_EXPIRATION_HOURS);

        for (Cart cart : expiredCarts) {
            cart.setDeletedAt(LocalDateTime.now());
            cartRepository.save(cart);
        }

        log.info("Successfully soft-deleted {} expired carts", expiredCarts.size());
    }
}