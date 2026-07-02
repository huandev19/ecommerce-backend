# REPOMIX INDEX GUIDE

## Purpose
This file provides a quick lookup index for `repomix-output.md` — a packed file containing the entire codebase (~9465 lines, ~180 source files). Use this guide to find specific files by line number instead of reading the entire packed file.

## How to Use
1. Identify which module/area you need to explore
2. Use the index below to find the approximate line number range
3. Read only the relevant section of `repomix-output.md` using `start_line` and `end_line`
4. **NEVER** read the entire `repomix-output.md` file at once (9465 lines)

## File Index

### Module: core
| File | Approx. Line Range |
|------|-------------------|
| `BaseEntity.java` | ~30-80 |
| `BaseRepository.java` | ~80-110 |
| `BaseEnum.java` | ~110-130 |
| `ApiResponse.java` | ~130-190 |
| `PageResponse.java` | ~190-240 |
| `BusinessException.java` | ~240-270 |
| `ErrorCode.java` | ~270-310 |
| `BaseService.java` | ~310-340 |
| `DomainEvent.java` | ~340-360 |
| `GlobalExceptionHandler.java` | ~360-420 |
| `JpaAuditingConfig.java` | ~420-450 |
| `BusinessValidationException.java` | ~450-480 |
| `ResourceNotFoundException.java` | ~480-510 |
| `PublicEndpoint.java` | ~510-530 |

### Module: identity
| File | Approx. Line Range |
|------|-------------------|
| `UserAdmin.java` | ~530-600 |
| `UserAdminRepository.java` | ~600-630 |
| `CreateUserAdminRequest.java` | ~630-670 |
| `AdminUserResponse.java` | ~670-720 |
| `UserAdminService.java` | ~720-850 |
| `UserAdminController.java` | ~850-950 |
| `Address.java` | ~950-1000 |
| `AddressRepository.java` | ~1000-1020 |
| `AddressService.java` | ~1020-1080 |
| `AddressController.java` | ~1080-1150 |
| `Role.java` | ~1150-1200 |
| `RoleRepository.java` | ~1200-1230 |
| `Permission.java` | ~1230-1270 |
| `PermissionRepository.java` | ~1270-1300 |
| `LoginHistory.java` | ~1300-1350 |
| `LoginHistoryRepository.java` | ~1350-1380 |
| `AuthService.java` | ~1380-1550 |
| `AuthController.java` | ~1550-1650 |
| `JwtTokenProvider.java` | ~1650-1800 |
| `SecurityConfig.java` | ~1800-1900 |
| `User.java` | ~1900-1980 |
| `UserRepository.java` | ~1980-2010 |

### Module: catalog
| File | Approx. Line Range |
|------|-------------------|
| `Product.java` | ~2010-2150 |
| `ProductRepository.java` | ~2150-2200 |
| `ProductService.java` | ~2200-2400 |
| `ProductController.java` | ~2400-2550 |
| `Category.java` | ~2550-2650 |
| `CategoryRepository.java` | ~2650-2700 |
| `CategoryService.java` | ~2700-2850 |
| `CategoryController.java` | ~2850-2980 |
| `ProductVariant.java` | ~2980-3100 |
| `ProductVariantRepository.java` | ~3100-3150 |
| `Collection.java` | ~3150-3250 |
| `CollectionRepository.java` | ~3250-3300 |
| `Store.java` | ~3300-3400 |
| `StoreRepository.java` | ~3400-3450 |

### Module: cart
| File | Approx. Line Range |
|------|-------------------|
| `Cart.java` | ~3450-3600 |
| `CartRepository.java` | ~3600-3650 |
| `CartItem.java` | ~3650-3800 |
| `CartItemRepository.java` | ~3800-3850 |
| `CartService.java` | ~3850-4200 |
| `CartController.java` | ~4200-4400 |

### Module: order
| File | Approx. Line Range |
|------|-------------------|
| `Order.java` | ~4400-4600 |
| `OrderRepository.java` | ~4600-4650 |
| `OrderItem.java` | ~4650-4800 |
| `OrderItemRepository.java` | ~4800-4850 |
| `OrderStatusHistory.java` | ~4850-4950 |
| `OrderService.java` | ~4950-5300 |
| `OrderController.java` | ~5300-5500 |

### Module: payment
| File | Approx. Line Range |
|------|-------------------|
| `PaymentSession.java` | ~5500-5650 |
| `PaymentSessionRepository.java` | ~5650-5700 |
| `PaymentSessionService.java` | ~5700-5900 |
| `PaymentSessionController.java` | ~5900-6050 |
| `PaymentCollection.java` | ~6050-6200 |
| `Refund.java` | ~6200-6350 |

### Module: inventory
| File | Approx. Line Range |
|------|-------------------|
| `InventoryItem.java` | ~6350-6500 |
| `InventoryItemRepository.java` | ~6500-6550 |
| `InventoryLevel.java` | ~6550-6650 |
| `InventoryLevelRepository.java` | ~6650-6700 |
| `Reservation.java` | ~6700-6850 |
| `InventoryService.java` | ~6850-7100 |
| `InventoryController.java` | ~7100-7300 |

### Module: fulfillment
| File | Approx. Line Range |
|------|-------------------|
| `Fulfillment.java` | ~7300-7500 |
| `FulfillmentRepository.java` | ~7500-7550 |
| `FulfillmentItem.java` | ~7550-7700 |
| `FulfillmentService.java` | ~7700-8000 |
| `FulfillmentController.java` | ~8000-8200 |

### Module: promotion
| File | Approx. Line Range |
|------|-------------------|
| `Promotion.java` | ~8200-8400 |
| `PromotionRepository.java` | ~8400-8450 |
| `Discount.java` | ~8450-8600 |
| `DiscountCondition.java` | ~8600-8700 |
| `DiscountRule.java` | ~8700-8800 |
| `PromotionService.java` | ~8800-9100 |
| `PromotionController.java` | ~9100-9300 |

### Module: notification
| File | Approx. Line Range |
|------|-------------------|
| `Notification.java` | ~9300-9450 |
| `NotificationRepository.java` | ~9450-9465 |

## Search Tips
- Use `grep` or search on `repomix-output.md` with keywords like class names, method names, or DTO names to find exact line numbers
- The packed file format uses markers like `### File: path/to/file.java` to separate files
- Line numbers above are approximate — they may shift as the codebase evolves