This file is a merged representation of the entire codebase, combined into a single document by Repomix.
The content has been processed where content has been compressed (code blocks are separated by ⋮---- delimiter).

# File Summary

## Purpose
This file contains a packed representation of the entire repository's contents.
It is designed to be easily consumable by AI systems for analysis, code review,
or other automated processes.

## File Format
The content is organized as follows:
1. This summary section
2. Repository information
3. Directory structure
4. Repository files (if enabled)
5. Multiple file entries, each consisting of:
  a. A header with the file path (## File: path/to/file)
  b. The full contents of the file in a code block

## Usage Guidelines
- This file should be treated as read-only. Any changes should be made to the
  original repository files, not this packed version.
- When processing this file, use the file path to distinguish
  between different files in the repository.
- Be aware that this file may contain sensitive information. Handle it with
  the same level of security as you would the original repository.

## Notes
- Some files may have been excluded based on .gitignore rules and Repomix's configuration
- Binary files are not included in this packed representation. Please refer to the Repository Structure section for a complete list of file paths, including binary files
- Files matching patterns in .gitignore are excluded
- Files matching default ignore patterns are excluded
- Content has been compressed - code blocks are separated by ⋮---- delimiter
- Files are sorted by Git change count (files with more changes are at the bottom)

# Directory Structure
```
.agents/
  AGENTS.md
.github/
  workflows/
    deploy.yml
gradle/
  wrapper/
    gradle-wrapper.properties
modules/
  cart/
    src/
      main/
        java/
          com/
            v8n/
              modules/
                cart/
                  application/
                    dto/
                      AddLineItemRequest.java
                      CartResponse.java
                      CreateCartRequest.java
                      LineItemResponse.java
                      UpdateLineItemRequest.java
                    service/
                      CartCleanupService.java
                      CartMapper.java
                      CartService.java
                  domain/
                    entity/
                      Cart.java
                      LineItem.java
                    event/
                      CartCreatedEvent.java
                      ItemAddedToCartEvent.java
                      ItemRemovedFromCartEvent.java
                    repository/
                      CartRepository.java
                      LineItemRepository.java
                  interfaces/
                    rest/
                      CartController.java
      test/
        java/
          com/
            v8n/
              modules/
                cart/
                  domain/
                    entity/
                      CartEntityTest.java
    build.gradle
  catalog/
    src/
      main/
        java/
          com/
            v8n/
              modules/
                catalog/
                  application/
                    dto/
                      CategoryRequest.java
                      CategoryResponse.java
                      ProductRequest.java
                      ProductResponse.java
                      VariantRequest.java
                      VariantResponse.java
                    service/
                      CatalogService.java
                  domain/
                    entity/
                      Category.java
                      Product.java
                      ProductCollection.java
                      ProductImage.java
                      ProductOption.java
                      ProductOptionValue.java
                      ProductStatus.java
                      ProductType.java
                      ProductVariant.java
                      Region.java
                      Store.java
                    repository/
                      CategoryRepository.java
                      ProductCollectionRepository.java
                      ProductImageRepository.java
                      ProductOptionRepository.java
                      ProductOptionValueRepository.java
                      ProductRepository.java
                      ProductTypeRepository.java
                      ProductVariantRepository.java
                      RegionRepository.java
                      StoreRepository.java
                  interfaces/
                    rest/
                      AdminCatalogController.java
                      StoreCatalogController.java
    build.gradle
  core/
    src/
      main/
        java/
          com/
            v8n/
              modules/
                core/
                  application/
                    dto/
                      ApiResponse.java
                      PageResponse.java
                    exception/
                      BusinessException.java
                      ErrorCode.java
                    service/
                      BaseService.java
                  domain/
                    entity/
                      BaseEntity.java
                      BaseEnum.java
                    event/
                      DomainEvent.java
                    repository/
                      BaseRepository.java
                  infrastructure/
                    config/
                      BusinessValidationException.java
                      GlobalExceptionHandler.java
                      JpaAuditingConfig.java
                      ResourceNotFoundException.java
                    security/
                      annotation/
                        PublicEndpoint.java
    build.gradle
  fulfillment/
    src/
      main/
        java/
          com/
            v8n/
              fulfillment/
                application/
                  dto/
                    FulfillmentRequest.java
                    FulfillmentResponse.java
                  service/
                    FulfillmentService.java
                domain/
                  entity/
                    Fulfillment.java
                    FulfillmentItem.java
                  repository/
                    FulfillmentRepository.java
                interfaces/
                  rest/
                    AdminFulfillmentController.java
    build.gradle
  identity/
    src/
      main/
        java/
          com/
            v8n/
              modules/
                identity/
                  application/
                    dto/
                      ActivationResponse.java
                      AddressRequest.java
                      AddressResponse.java
                      AdminAuthResponse.java
                      AdminLoginRequest.java
                      AdminUserResponse.java
                      AuthResponse.java
                      CreateRoleRequest.java
                      CreateUserAdminRequest.java
                      CustomerResponse.java
                      LoginHistoryResponse.java
                      LoginRequest.java
                      PermissionResponse.java
                      RegisterRequest.java
                      RoleDetailResponse.java
                      RoleResponse.java
                      RoleSummary.java
                      SetPasswordRequest.java
                      UnlockResponse.java
                      UpdateProfileRequest.java
                      UpdateRoleRequest.java
                      UpdateUserAdminRequest.java
                      UpdateUserPermissionsRequest.java
                      UpdateUserRolesRequest.java
                      UserResponse.java
                    mapper/
                      RoleMapper.java
                      UserAdminMapper.java
                    service/
                      AddressService.java
                      AdminAuthService.java
                      AuthService.java
                      CustomerService.java
                      LoginHistoryService.java
                      PermissionService.java
                      RoleService.java
                      UserAdminService.java
                  domain/
                    entity/
                      Address.java
                      Customer.java
                      LoginHistory.java
                      Permission.java
                      Role.java
                      RolePermission.java
                      RolePermissionId.java
                      User.java
                      UserAdmin.java
                      UserAdminPermission.java
                      UserAdminPermissionId.java
                      UserAdminRole.java
                      UserAdminRoleId.java
                    enums/
                      LoginStatus.java
                    repository/
                      AddressRepository.java
                      CustomerRepository.java
                      LoginHistoryRepository.java
                      PermissionRepository.java
                      RolePermissionRepository.java
                      RoleRepository.java
                      UserAdminPermissionRepository.java
                      UserAdminRepository.java
                      UserAdminRoleRepository.java
                      UserRepository.java
                  infrastructure/
                    security/
                      CustomAccessDeniedHandler.java
                      CustomAuthenticationEntryPoint.java
                      JwtAuthenticationFilter.java
                      JwtTokenProvider.java
                      SecurityConfig.java
                  interfaces/
                    rest/
                      ActivateController.java
                      AddressController.java
                      AdminAuthController.java
                      AuthController.java
                      LoginHistoryController.java
                      PermissionController.java
                      ProfileController.java
                      RoleController.java
                      UserAdminController.java
      test/
        java/
          com/
            v8n/
              modules/
                identity/
                  application/
                    service/
                      AddressServiceTest.java
                      CustomerServiceTest.java
    build.gradle
  inventory/
    src/
      main/
        java/
          com/
            v8n/
              modules/
                inventory/
                  application/
                    dto/
                      InventoryItemRequest.java
                      InventoryItemResponse.java
                      ReservationRequest.java
                      ReservationResponse.java
                    service/
                      InventoryService.java
                  domain/
                    entity/
                      InventoryItem.java
                      InventoryLevel.java
                      ReservationItem.java
                    repository/
                      InventoryItemRepository.java
                      InventoryLevelRepository.java
                      ReservationItemRepository.java
                  interfaces/
                    rest/
                      AdminInventoryController.java
    build.gradle
  notification/
    src/
      main/
        java/
          com/
            v8n/
              notification/
                application/
                  dto/
                    NotificationRequest.java
                    NotificationResponse.java
                  service/
                    NotificationService.java
                domain/
                  entity/
                    Notification.java
                  repository/
                    NotificationRepository.java
                interfaces/
                  rest/
                    NotificationController.java
    build.gradle
  order/
    src/
      main/
        java/
          com/
            v8n/
              modules/
                order/
                  application/
                    dto/
                      CreateOrderRequest.java
                      OrderItemResponse.java
                      OrderResponse.java
                      OrderStatusHistoryResponse.java
                      UpdateOrderStatusRequest.java
                    service/
                      OrderMapper.java
                      OrderService.java
                      SequenceGeneratorService.java
                  domain/
                    entity/
                      FulfillmentStatus.java
                      Order.java
                      OrderItem.java
                      OrderStatus.java
                      OrderStatusConverter.java
                      OrderStatusHistory.java
                      PaymentStatus.java
                    event/
                      OrderCanceledEvent.java
                      OrderPlacedEvent.java
                      OrderStatusChangedEvent.java
                    repository/
                      OrderItemRepository.java
                      OrderRepository.java
                      OrderStatusHistoryRepository.java
                  interfaces/
                    rest/
                      OrderController.java
      test/
        java/
          com/
            v8n/
              modules/
                order/
                  domain/
                    entity/
                      OrderEntityTest.java
    build.gradle
  payment/
    src/
      main/
        java/
          com/
            v8n/
              payment/
                application/
                  dto/
                    PaymentCollectionResponse.java
                    PaymentSessionRequest.java
                    PaymentSessionResponse.java
                    RefundRequest.java
                    RefundResponse.java
                  service/
                    PaymentService.java
                domain/
                  entity/
                    Payment.java
                    PaymentCollection.java
                    PaymentSession.java
                    Refund.java
                  repository/
                    PaymentCollectionRepository.java
                    PaymentRepository.java
                    PaymentSessionRepository.java
                    RefundRepository.java
                interfaces/
                  rest/
                    PaymentController.java
    build.gradle
  promotion/
    src/
      main/
        java/
          com/
            v8n/
              promotion/
                application/
                  dto/
                    DiscountRequest.java
                    DiscountResponse.java
                    DiscountValidationRequest.java
                  service/
                    PromotionService.java
                domain/
                  entity/
                    Discount.java
                    DiscountCondition.java
                    DiscountRule.java
                  repository/
                    DiscountRepository.java
                interfaces/
                  rest/
                    AdminDiscountController.java
    build.gradle
src/
  main/
    java/
      com/
        v8n/
          V8nEcommerceApplication.java
    resources/
      db/
        migration/
          V1__baseline.sql
          V10__Create_Login_History.sql
          V2__add_thumbnail_to_cart_items.sql
          V3__add_address_fk_to_carts_and_orders.sql
          V4__Create_Permission_Table.sql
          V5__Create_Role_Table.sql
          V6__Create_Role_Permission.sql
          V7__Create_User_Admins_Table.sql
          V8__Create_User_Admin_Roles.sql
          V9__Create_User_Admin_Permissions.sql
      application-prod.yml
      application.yml
bootRun.sh
build.gradle
buildAndRun.sh
buildProject.sh
README.md
runBuiltProject.sh
settings.gradle
```

# Files

## File: .agents/AGENTS.md
````markdown
# V8N-ECOMMERCE BACKEND AI RULES

## Single Source of Truth for Codebase Context
- **CRITICAL**: Before starting to write code, search for logic flows, or analyze the project architecture, you MUST read and extract information from the file `/Volumes/Hdev/SkillLearn/StudyJava/SourceCode/Backend/v8n-ecommerce/repomix-output.md`.
- **Reason**: This file is a packed version of the entire codebase, providing a comprehensive overview of the project without the need to guess or blindly search for file names/paths.
- **How to execute**: 
  1. Use the `grep_search` or `view_file` tool on `repomix-output.md` to search for class, interface, function, or DTO definitions related to the task.
  2. Based on the packed content, map out the necessary changes.
  3. Only when you are absolutely certain about which physical file needs to be modified should you perform direct edit operations on that file in the actual source code directory.
````

## File: gradle/wrapper/gradle-wrapper.properties
````
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.7-bin.zip
networkTimeout=10000
validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
````

## File: modules/cart/src/main/java/com/v8n/modules/cart/application/dto/AddLineItemRequest.java
````java
public class AddLineItemRequest {
⋮----
private int unitPrice; // In cents
````

## File: modules/cart/src/main/java/com/v8n/modules/cart/application/dto/CartResponse.java
````java
public class CartResponse {
⋮----
private long subtotal; // In cents
````

## File: modules/cart/src/main/java/com/v8n/modules/cart/application/dto/CreateCartRequest.java
````java
public class CreateCartRequest {
````

## File: modules/cart/src/main/java/com/v8n/modules/cart/application/dto/LineItemResponse.java
````java
public class LineItemResponse {
⋮----
private int unitPrice; // In cents
private long subtotal; // In cents
````

## File: modules/cart/src/main/java/com/v8n/modules/cart/application/dto/UpdateLineItemRequest.java
````java
public class UpdateLineItemRequest {
````

## File: modules/cart/src/main/java/com/v8n/modules/cart/application/service/CartCleanupService.java
````java
/**
 * Scheduled service that cleans up expired/abandoned carts.
 * Carts that have not been completed and have been idle for more than the
 * configured expiration period (default: 24 hours) will be soft-deleted.
 */
⋮----
public class CartCleanupService {
⋮----
private static final Logger log = LoggerFactory.getLogger(CartCleanupService.class);
⋮----
/**
     * Runs every hour to soft-delete expired carts.
     * Carts older than 24 hours (not completed, not already deleted) are expired.
     */
@Scheduled(fixedRate = 3600000) // every hour
⋮----
public void cleanupExpiredCarts() {
LocalDateTime cutoffTime = LocalDateTime.now().minusHours(CART_EXPIRATION_HOURS);
List<Cart> expiredCarts = cartRepository.findExpiredCarts(cutoffTime);
⋮----
if (expiredCarts.isEmpty()) {
⋮----
log.info("Found {} expired carts to clean up (threshold: {} hours)", expiredCarts.size(), CART_EXPIRATION_HOURS);
⋮----
cart.setDeletedAt(LocalDateTime.now());
cartRepository.save(cart);
⋮----
log.info("Successfully soft-deleted {} expired carts", expiredCarts.size());
````

## File: modules/cart/src/main/java/com/v8n/modules/cart/application/service/CartMapper.java
````java
public class CartMapper {
⋮----
public CartResponse toResponse(Cart cart) {
⋮----
CartResponse.CartResponseBuilder builder = CartResponse.builder()
.id(cart.getId())
.regionId(cart.getRegion() != null ? cart.getRegion().getId() : null)
.customerId(cart.getCustomer() != null ? cart.getCustomer().getId() : null)
.email(cart.getEmail())
.currencyCode(cart.getCurrencyCode())
.shippingAddress(toAddressResponse(cart.getShippingAddress()))
.billingAddress(toAddressResponse(cart.getBillingAddress()))
.completedAt(cart.getCompletedAt())
.completed(cart.isCompleted())
.itemCount(cart.getItemCount())
.metadata(cart.getMetadata())
.createdAt(cart.getCreatedAt())
.updatedAt(cart.getUpdatedAt());
⋮----
// Calculate subtotal from line items
⋮----
if (cart.getLineItems() != null) {
builder.lineItems(cart.getLineItems().stream()
.map(this::toLineItemResponse)
.toList());
subtotal = cart.getLineItems().stream()
.mapToLong(item -> (long) item.getUnitPrice() * item.getQuantity())
.sum();
⋮----
builder.subtotal(subtotal);
⋮----
return builder.build();
⋮----
public LineItemResponse toLineItemResponse(LineItem item) {
⋮----
return LineItemResponse.builder()
.id(item.getId())
.cartId(item.getCart() != null ? item.getCart().getId() : null)
.variantId(item.getVariant() != null ? item.getVariant().getId() : null)
.variantSku(item.getVariant() != null ? item.getVariant().getSku() : null)
.title(item.getTitle())
.quantity(item.getQuantity())
.unitPrice(item.getUnitPrice())
.subtotal((long) item.getUnitPrice() * item.getQuantity())
.thumbnail(item.getThumbnail())
.metadata(item.getMetadata())
.createdAt(item.getCreatedAt())
.updatedAt(item.getUpdatedAt())
.build();
⋮----
private AddressResponse toAddressResponse(Address address) {
⋮----
return AddressResponse.builder()
.id(address.getId())
.label(address.getLabel())
.recipientName(address.getRecipientName())
.phone(address.getPhone())
.street(address.getStreet())
.ward(address.getWard())
.district(address.getDistrict())
.city(address.getCity())
.state(address.getState())
.country(address.getCountry())
.zipCode(address.getZipCode())
.defaultShipping(address.isDefaultShipping())
.defaultBilling(address.isDefaultBilling())
.addressType(address.getAddressType())
.createdAt(address.getCreatedAt())
.updatedAt(address.getUpdatedAt())
````

## File: modules/cart/src/main/java/com/v8n/modules/cart/application/service/CartService.java
````java
public class CartService {
⋮----
// ===== Query Methods =====
⋮----
public CartResponse getCartById(UUID cartId) {
Cart cart = findCartById(cartId);
return cartMapper.toResponse(cart);
⋮----
public CartResponse getActiveCartByCustomerId(UUID customerId) {
Cart cart = cartRepository.findActiveCartByCustomerId(customerId)
.orElseThrow(() -> new BusinessException(ErrorCode.CART_NOT_FOUND));
⋮----
// ===== Mutation Methods =====
⋮----
public CartResponse createCart(CreateCartRequest request) {
Cart cart = new Cart();
⋮----
// Set region
if (request.getRegionId() != null) {
Region region = regionRepository.findByIdNotDeleted(request.getRegionId())
.orElseThrow(() -> new BusinessException(ErrorCode.REGION_NOT_FOUND));
cart.setRegion(region);
⋮----
// Set customer if provided
if (request.getCustomerId() != null) {
Customer customer = customerRepository.findByIdNotDeleted(request.getCustomerId())
.orElseThrow(() -> new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND));
cart.setCustomer(customer);
cart.setEmail(customer.getEmail());
⋮----
// Set currency code from region or request
if (request.getCurrencyCode() != null) {
cart.setCurrencyCode(request.getCurrencyCode());
} else if (cart.getRegion() != null) {
cart.setCurrencyCode(cart.getRegion().getCurrencyCode());
⋮----
cart = cartRepository.save(cart);
log.info("Cart created with id: {}", cart.getId());
⋮----
public CartResponse addLineItem(UUID cartId, AddLineItemRequest request) {
⋮----
if (cart.isCompleted()) {
throw new BusinessException(ErrorCode.CART_EXPIRED);
⋮----
// Validate variant
ProductVariant variant = variantRepository.findByIdNotDeleted(request.getVariantId())
.orElseThrow(() -> new BusinessException(ErrorCode.VARIANT_NOT_FOUND));
⋮----
// Validate quantity
if (request.getQuantity() <= 0) {
throw new BusinessException(ErrorCode.INVALID_QUANTITY);
⋮----
String title = request.getTitle() != null ? request.getTitle() : variant.getProduct().getTitle();
cart.addItem(variant, request.getQuantity(), request.getUnitPrice(), title, request.getThumbnail());
⋮----
log.info("Line item added to cart {}: variant={}, quantity={}", cartId, request.getVariantId(), request.getQuantity());
⋮----
public CartResponse updateLineItemQuantity(UUID cartId, UUID lineItemId, UpdateLineItemRequest request) {
⋮----
LineItem lineItem = cart.getLineItems().stream()
.filter(item -> item.getId().equals(lineItemId))
.findFirst()
.orElseThrow(() -> new BusinessException(ErrorCode.LINE_ITEM_NOT_FOUND));
⋮----
lineItem.setQuantity(request.getQuantity());
⋮----
log.info("Line item {} quantity updated to {} in cart {}", lineItemId, request.getQuantity(), cartId);
⋮----
public CartResponse removeLineItem(UUID cartId, UUID lineItemId) {
⋮----
cart.removeItem(lineItem);
⋮----
log.info("Line item {} removed from cart {}", lineItemId, cartId);
⋮----
public CartResponse clearCart(UUID cartId) {
⋮----
cart.clearItems();
⋮----
log.info("Cart {} cleared", cartId);
⋮----
public CartResponse updateShippingAddress(UUID cartId, AddressRequest addressRequest) {
⋮----
Address address = new Address();
address.setLabel(addressRequest.getLabel());
address.setRecipientName(addressRequest.getRecipientName());
address.setPhone(addressRequest.getPhone());
address.setStreet(addressRequest.getStreet());
address.setWard(addressRequest.getWard());
address.setDistrict(addressRequest.getDistrict());
address.setCity(addressRequest.getCity());
address.setState(addressRequest.getState());
address.setCountry(addressRequest.getCountry());
address.setZipCode(addressRequest.getZipCode());
⋮----
cart.setShippingAddress(address);
⋮----
log.info("Shipping address updated for cart {}", cartId);
⋮----
public CartResponse updateBillingAddress(UUID cartId, AddressRequest addressRequest) {
⋮----
cart.setBillingAddress(address);
⋮----
log.info("Billing address updated for cart {}", cartId);
⋮----
public void deleteCart(UUID cartId) {
⋮----
cart.setDeletedAt(java.time.LocalDateTime.now());
cartRepository.save(cart);
log.info("Cart {} soft-deleted", cartId);
⋮----
// ===== Internal Helpers =====
⋮----
private Cart findCartById(UUID cartId) {
return cartRepository.findByIdNotDeleted(cartId)
````

## File: modules/cart/src/main/java/com/v8n/modules/cart/domain/entity/Cart.java
````java
public class Cart extends BaseEntity {
⋮----
// ---- Business Methods ----
⋮----
/**
     * Add an item to the cart. If the same variant already exists, increase its quantity.
     */
public void addItem(ProductVariant variant, int quantity, int unitPrice, String title, String thumbnail) {
⋮----
throw new IllegalStateException("Cannot add items to a completed cart");
⋮----
throw new IllegalArgumentException("Quantity must be at least 1");
⋮----
Optional<LineItem> existingItem = findItemByVariantId(variant.getId());
if (existingItem.isPresent()) {
existingItem.get().increaseQuantity(quantity);
⋮----
LineItem lineItem = new LineItem(this, variant, quantity, unitPrice, title);
lineItem.setThumbnail(thumbnail);
this.lineItems.add(lineItem);
⋮----
/**
     * Remove an item from the cart by variant ID.
     */
public boolean removeItemByVariantId(UUID variantId) {
Iterator<LineItem> iterator = lineItems.iterator();
while (iterator.hasNext()) {
LineItem item = iterator.next();
if (item.getVariant().getId().equals(variantId)) {
iterator.remove();
⋮----
/**
     * Remove a specific line item.
     */
public boolean removeItem(LineItem lineItem) {
return lineItems.remove(lineItem);
⋮----
/**
     * Clear all items from the cart.
     */
public void clearItems() {
lineItems.clear();
⋮----
/**
     * Find a line item by variant ID.
     */
public Optional<LineItem> findItemByVariant(UUID variantId) {
return findItemByVariantId(variantId);
⋮----
private Optional<LineItem> findItemByVariantId(UUID variantId) {
return lineItems.stream()
.filter(item -> item.getVariant().getId().equals(variantId))
.findFirst();
⋮----
/**
     * Get the total number of items (sum of quantities).
     */
public int getItemCount() {
return lineItems.stream().mapToInt(LineItem::getQuantity).sum();
⋮----
/**
     * Get the total subtotal in cents (sum of line item subtotals).
     */
public long getSubtotal() {
return lineItems.stream().mapToLong(LineItem::getSubtotal).sum();
⋮----
/**
     * Mark the cart as completed.
     */
public void markCompleted() {
this.completedAt = LocalDateTime.now();
⋮----
/**
     * Check if the cart is completed.
     */
public boolean isCompleted() {
⋮----
/**
     * Check if the cart is empty (has no items).
     */
public boolean isEmpty() {
return lineItems.isEmpty();
````

## File: modules/cart/src/main/java/com/v8n/modules/cart/domain/entity/LineItem.java
````java
public class LineItem extends BaseEntity {
⋮----
private int unitPrice = 0; // Price in cents (DB stores as INTEGER)
⋮----
/**
     * Get subtotal in cents (unitPrice * quantity)
     */
public long getSubtotal() {
⋮----
/**
     * Update quantity. Validates minimum value.
     */
public void updateQuantity(int newQuantity) {
⋮----
throw new IllegalArgumentException("Quantity must be at least 1, got: " + newQuantity);
⋮----
/**
     * Increase quantity by given amount.
     */
public void increaseQuantity(int amount) {
⋮----
throw new IllegalArgumentException("Increase amount must be positive, got: " + amount);
⋮----
public boolean equals(Object o) {
⋮----
if (o == null || getClass() != o.getClass()) return false;
if (getId() == null) return false;
⋮----
return Objects.equals(getId(), lineItem.getId());
⋮----
public int hashCode() {
return getId() != null ? Objects.hash(getId()) : super.hashCode();
````

## File: modules/cart/src/main/java/com/v8n/modules/cart/domain/event/CartCreatedEvent.java
````java
public class CartCreatedEvent extends DomainEvent {
⋮----
this.cartId = cart.getId();
this.customerId = cart.getCustomer() != null ? cart.getCustomer().getId() : null;
this.email = cart.getEmail();
````

## File: modules/cart/src/main/java/com/v8n/modules/cart/domain/event/ItemAddedToCartEvent.java
````java
public class ItemAddedToCartEvent extends DomainEvent {
⋮----
this.cartId = cart.getId();
this.lineItemId = item.getId();
this.variantId = item.getVariant() != null ? item.getVariant().getId() : null;
this.quantity = item.getQuantity();
this.unitPrice = item.getUnitPrice();
````

## File: modules/cart/src/main/java/com/v8n/modules/cart/domain/event/ItemRemovedFromCartEvent.java
````java
public class ItemRemovedFromCartEvent extends DomainEvent {
⋮----
this.cartId = cart.getId();
this.lineItemId = item.getId();
this.variantId = item.getVariant() != null ? item.getVariant().getId() : null;
````

## File: modules/cart/src/main/java/com/v8n/modules/cart/domain/repository/CartRepository.java
````java
public interface CartRepository extends BaseRepository<Cart, UUID> {
⋮----
List<Cart> findByCustomerId(@Param("customerId") UUID customerId);
⋮----
List<Cart> findByEmail(@Param("email") String email);
⋮----
Optional<Cart> findActiveCartByCustomerId(@Param("customerId") UUID customerId);
⋮----
List<Cart> findExpiredCarts(@Param("cutoffTime") LocalDateTime cutoffTime);
````

## File: modules/cart/src/main/java/com/v8n/modules/cart/domain/repository/LineItemRepository.java
````java
public interface LineItemRepository extends BaseRepository<LineItem, UUID> {
⋮----
List<LineItem> findByCartId(@Param("cartId") UUID cartId);
⋮----
List<LineItem> findByCartIdAndVariantId(@Param("cartId") UUID cartId, @Param("variantId") UUID variantId);
````

## File: modules/cart/src/main/java/com/v8n/modules/cart/interfaces/rest/CartController.java
````java
public class CartController {
⋮----
// ===== Query Endpoints =====
⋮----
public ResponseEntity<ApiResponse<CartResponse>> getCart(@PathVariable UUID cartId) {
CartResponse response = cartService.getCartById(cartId);
return ResponseEntity.ok(ApiResponse.success(response));
⋮----
public ResponseEntity<ApiResponse<CartResponse>> getActiveCart(@RequestParam UUID customerId) {
CartResponse response = cartService.getActiveCartByCustomerId(customerId);
⋮----
// ===== Mutation Endpoints =====
⋮----
public ResponseEntity<ApiResponse<CartResponse>> createCart(@Valid @RequestBody CreateCartRequest request) {
CartResponse response = cartService.createCart(request);
return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
⋮----
public ResponseEntity<ApiResponse<CartResponse>> addLineItem(
⋮----
CartResponse response = cartService.addLineItem(cartId, request);
⋮----
public ResponseEntity<ApiResponse<CartResponse>> updateLineItemQuantity(
⋮----
CartResponse response = cartService.updateLineItemQuantity(cartId, lineItemId, request);
⋮----
public ResponseEntity<ApiResponse<CartResponse>> removeLineItem(
⋮----
CartResponse response = cartService.removeLineItem(cartId, lineItemId);
⋮----
public ResponseEntity<ApiResponse<CartResponse>> clearCart(@PathVariable UUID cartId) {
CartResponse response = cartService.clearCart(cartId);
⋮----
public ResponseEntity<ApiResponse<CartResponse>> updateShippingAddress(
⋮----
CartResponse response = cartService.updateShippingAddress(cartId, addressRequest);
⋮----
public ResponseEntity<ApiResponse<CartResponse>> updateBillingAddress(
⋮----
CartResponse response = cartService.updateBillingAddress(cartId, addressRequest);
⋮----
public ResponseEntity<ApiResponse<Void>> deleteCart(@PathVariable UUID cartId) {
cartService.deleteCart(cartId);
return ResponseEntity.noContent().build();
````

## File: modules/cart/src/test/java/com/v8n/modules/cart/domain/entity/CartEntityTest.java
````java
class CartEntityTest {
⋮----
void setUp() {
region = new Region();
region.setId(UUID.randomUUID());
region.setCurrencyCode("VND");
⋮----
cart = new Cart();
cart.setId(UUID.randomUUID());
cart.setRegion(region);
cart.setCurrencyCode("VND");
⋮----
variant1 = new ProductVariant();
variant1.setId(UUID.randomUUID());
⋮----
variant2 = new ProductVariant();
variant2.setId(UUID.randomUUID());
⋮----
void testAddItem_success() {
cart.addItem(variant1, 2, 100000, "Product 1", "thumb1.jpg");
⋮----
assertEquals(1, cart.getLineItems().size());
assertEquals(2, cart.getItemCount());
assertEquals(200000, cart.getSubtotal());
⋮----
// Add same variant again
cart.addItem(variant1, 1, 100000, "Product 1", "thumb1.jpg");
⋮----
assertEquals(3, cart.getItemCount());
assertEquals(300000, cart.getSubtotal());
⋮----
void testAddItem_completedCart_throwsException() {
cart.markCompleted();
⋮----
IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
cart.addItem(variant1, 1, 100000, "Product 1", "thumb1.jpg")
⋮----
assertEquals("Cannot add items to a completed cart", exception.getMessage());
⋮----
void testRemoveItem_success() {
⋮----
cart.addItem(variant2, 1, 50000, "Product 2", "thumb2.jpg");
⋮----
assertEquals(2, cart.getLineItems().size());
⋮----
boolean removed = cart.removeItemByVariantId(variant1.getId());
⋮----
assertTrue(removed);
⋮----
assertEquals(50000, cart.getSubtotal());
⋮----
void testClearCart_emptiesItems() {
⋮----
assertFalse(cart.isEmpty());
⋮----
cart.clearItems();
⋮----
assertTrue(cart.isEmpty());
assertEquals(0, cart.getItemCount());
assertEquals(0, cart.getSubtotal());
````

## File: modules/cart/build.gradle
````
dependencies {
    api project(':modules:core')
    implementation project(':modules:catalog')
    implementation project(':modules:identity')
    implementation project(':modules:inventory')
}
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/application/dto/CategoryRequest.java
````java
public class CategoryRequest {
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/application/dto/CategoryResponse.java
````java
public class CategoryResponse {
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/application/dto/ProductRequest.java
````java
public class ProductRequest {
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/application/dto/ProductResponse.java
````java
public class ProductResponse {
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/application/dto/VariantRequest.java
````java
public class VariantRequest {
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/application/dto/VariantResponse.java
````java
public class VariantResponse {
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/application/service/CatalogService.java
````java
public class CatalogService {
⋮----
// ===== Product Methods =====
⋮----
public ProductResponse getProductById(UUID productId) {
Product product = findProductById(productId);
return toProductResponse(product);
⋮----
public ProductResponse getProductBySlug(String slug) {
Product product = productRepository.findBySlug(slug)
.orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
⋮----
public List<ProductResponse> getAllProducts() {
return productRepository.findAll().stream()
.map(this::toProductResponse)
.collect(Collectors.toList());
⋮----
public List<ProductResponse> getProductsByStatus(ProductStatus status) {
return productRepository.findByStatus(status).stream()
⋮----
public List<ProductResponse> getProductsByCategory(UUID categoryId) {
return productRepository.findByCategoryId(categoryId).stream()
⋮----
public ProductResponse createProduct(ProductRequest request) {
Product product = new Product();
product.setTitle(request.getTitle());
product.setSlug(request.getSlug() != null ? request.getSlug() : generateSlug(request.getTitle()));
product.setSubtitle(request.getSubtitle());
product.setDescription(request.getDescription());
product.setThumbnailUrl(request.getThumbnailUrl());
product.setStatus(request.getStatus() != null ? request.getStatus() : ProductStatus.draft);
product.setDiscountable(request.isDiscountable());
product.setOriginCountry(request.getOriginCountry());
product.setWeight(request.getWeight());
product.setHeight(request.getHeight());
product.setWidth(request.getWidth());
product.setLength(request.getLength());
product.setHsCode(request.getHsCode());
product.setMaterial(request.getMaterial());
⋮----
if (request.getCategoryId() != null) {
Category category = categoryRepository.findByIdNotDeleted(request.getCategoryId())
.orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
product.setCategory(category);
⋮----
product = productRepository.save(product);
log.info("Created product: {}", product.getId());
⋮----
public ProductResponse updateProduct(UUID productId, ProductRequest request) {
⋮----
if (request.getTitle() != null) product.setTitle(request.getTitle());
if (request.getSlug() != null) product.setSlug(request.getSlug());
if (request.getSubtitle() != null) product.setSubtitle(request.getSubtitle());
if (request.getDescription() != null) product.setDescription(request.getDescription());
if (request.getThumbnailUrl() != null) product.setThumbnailUrl(request.getThumbnailUrl());
if (request.getStatus() != null) product.setStatus(request.getStatus());
if (request.isDiscountable()) product.setDiscountable(request.isDiscountable());
if (request.getOriginCountry() != null) product.setOriginCountry(request.getOriginCountry());
if (request.getWeight() != null) product.setWeight(request.getWeight());
if (request.getHeight() != null) product.setHeight(request.getHeight());
if (request.getWidth() != null) product.setWidth(request.getWidth());
if (request.getLength() != null) product.setLength(request.getLength());
if (request.getHsCode() != null) product.setHsCode(request.getHsCode());
if (request.getMaterial() != null) product.setMaterial(request.getMaterial());
⋮----
log.info("Updated product: {}", product.getId());
⋮----
public void deleteProduct(UUID productId) {
⋮----
productRepository.delete(product);
log.info("Deleted product: {}", productId);
⋮----
// ===== Category Methods =====
⋮----
public CategoryResponse getCategoryById(UUID categoryId) {
Category category = findCategoryById(categoryId);
return toCategoryResponse(category);
⋮----
public List<CategoryResponse> getAllCategories() {
return categoryRepository.findAll().stream()
.map(this::toCategoryResponse)
⋮----
public List<CategoryResponse> getRootCategories() {
return categoryRepository.findByParentCategoryIdIsNull().stream()
⋮----
public List<CategoryResponse> getSubcategories(UUID parentId) {
return categoryRepository.findByParentCategoryIdOrderByDisplayOrderAsc(parentId).stream()
⋮----
public CategoryResponse createCategory(CategoryRequest request) {
Category category = new Category();
category.setName(request.getName());
category.setSlug(request.getSlug() != null ? request.getSlug() : generateSlug(request.getName()));
category.setDescription(request.getDescription());
category.setActive(request.isActive());
⋮----
if (request.getParentCategoryId() != null) {
Category parent = categoryRepository.findByIdNotDeleted(request.getParentCategoryId())
⋮----
category.setParentCategory(parent);
⋮----
category = categoryRepository.save(category);
log.info("Created category: {}", category.getId());
⋮----
public CategoryResponse updateCategory(UUID categoryId, CategoryRequest request) {
⋮----
if (request.getName() != null) category.setName(request.getName());
if (request.getSlug() != null) category.setSlug(request.getSlug());
if (request.getDescription() != null) category.setDescription(request.getDescription());
if (request.isActive()) category.setActive(request.isActive());
⋮----
log.info("Updated category: {}", category.getId());
⋮----
public void deleteCategory(UUID categoryId) {
⋮----
categoryRepository.delete(category);
log.info("Deleted category: {}", categoryId);
⋮----
// ===== Variant Methods =====
⋮----
public VariantResponse getVariantById(UUID variantId) {
ProductVariant variant = findVariantById(variantId);
return toVariantResponse(variant);
⋮----
public List<VariantResponse> getVariantsByProductId(UUID productId) {
return variantRepository.findByProductId(productId).stream()
.map(this::toVariantResponse)
⋮----
public VariantResponse createVariant(UUID productId, VariantRequest request) {
⋮----
ProductVariant variant = new ProductVariant();
variant.setProduct(product);
variant.setTitle(request.getTitle());
variant.setSku(request.getSku());
variant.setBarcode(request.getBarcode());
variant.setEan(request.getEan());
variant.setUpc(request.getUpc());
variant.setInventoryQuantity(request.getInventoryQuantity());
variant.setAllowBackorder(request.isAllowBackorder());
variant.setManageInventory(request.isManageInventory());
variant.setWeight(request.getWeight());
variant.setHeight(request.getHeight());
variant.setWidth(request.getWidth());
variant.setLength(request.getLength());
⋮----
variant = variantRepository.save(variant);
log.info("Created variant: {} for product: {}", variant.getId(), productId);
⋮----
public VariantResponse updateVariant(UUID variantId, VariantRequest request) {
⋮----
if (request.getTitle() != null) variant.setTitle(request.getTitle());
if (request.getSku() != null) variant.setSku(request.getSku());
if (request.getBarcode() != null) variant.setBarcode(request.getBarcode());
if (request.getEan() != null) variant.setEan(request.getEan());
if (request.getUpc() != null) variant.setUpc(request.getUpc());
if (request.getInventoryQuantity() > 0) variant.setInventoryQuantity(request.getInventoryQuantity());
⋮----
if (request.getWeight() != null) variant.setWeight(request.getWeight());
if (request.getHeight() != null) variant.setHeight(request.getHeight());
if (request.getWidth() != null) variant.setWidth(request.getWidth());
if (request.getLength() != null) variant.setLength(request.getLength());
⋮----
log.info("Updated variant: {}", variant.getId());
⋮----
public void deleteVariant(UUID variantId) {
⋮----
variantRepository.delete(variant);
log.info("Deleted variant: {}", variantId);
⋮----
// ===== Private Helper Methods =====
⋮----
private Product findProductById(UUID productId) {
return productRepository.findByIdNotDeleted(productId)
⋮----
private Category findCategoryById(UUID categoryId) {
return categoryRepository.findByIdNotDeleted(categoryId)
⋮----
private ProductVariant findVariantById(UUID variantId) {
return variantRepository.findByIdNotDeleted(variantId)
.orElseThrow(() -> new BusinessException(ErrorCode.VARIANT_NOT_FOUND));
⋮----
private String generateSlug(String title) {
if (title == null) return UUID.randomUUID().toString();
return title.toLowerCase()
.replaceAll("[^a-z0-9\\s-]", "")
.replaceAll("\\s+", "-")
.replaceAll("-+", "-")
.trim();
⋮----
private ProductResponse toProductResponse(Product product) {
return ProductResponse.builder()
.id(product.getId())
.title(product.getTitle())
.subtitle(product.getSubtitle())
.description(product.getDescription())
.slug(product.getSlug())
.thumbnailUrl(product.getThumbnailUrl())
.status(product.getStatus())
.categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
.originCountry(product.getOriginCountry())
.discountable(product.isDiscountable())
.weight(product.getWeight())
.height(product.getHeight())
.width(product.getWidth())
.length(product.getLength())
.hsCode(product.getHsCode())
.material(product.getMaterial())
.createdAt(product.getCreatedAt())
.updatedAt(product.getUpdatedAt())
.build();
⋮----
private CategoryResponse toCategoryResponse(Category category) {
return CategoryResponse.builder()
.id(category.getId())
.name(category.getName())
.slug(category.getSlug())
.description(category.getDescription())
.parentCategoryId(category.getParentCategory() != null ? category.getParentCategory().getId() : null)
.active(category.isActive())
.createdAt(category.getCreatedAt())
.updatedAt(category.getUpdatedAt())
⋮----
private VariantResponse toVariantResponse(ProductVariant variant) {
return VariantResponse.builder()
.id(variant.getId())
.productId(variant.getProduct().getId())
.title(variant.getTitle())
.sku(variant.getSku())
.barcode(variant.getBarcode())
.ean(variant.getEan())
.upc(variant.getUpc())
.inventoryQuantity(variant.getInventoryQuantity())
.allowBackorder(variant.isAllowBackorder())
.manageInventory(variant.isManageInventory())
.weight(variant.getWeight())
.height(variant.getHeight())
.width(variant.getWidth())
.length(variant.getLength())
.createdAt(variant.getCreatedAt())
.updatedAt(variant.getUpdatedAt())
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/entity/Category.java
````java
public class Category extends BaseEntity {
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/entity/Product.java
````java
public class Product extends BaseEntity {
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/entity/ProductCollection.java
````java
public class ProductCollection extends BaseEntity {
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/entity/ProductImage.java
````java
public class ProductImage extends BaseEntity {
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/entity/ProductOption.java
````java
public class ProductOption extends BaseEntity {
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/entity/ProductOptionValue.java
````java
public class ProductOptionValue extends BaseEntity {
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/entity/ProductStatus.java
````java

````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/entity/ProductType.java
````java
public class ProductType extends BaseEntity {
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/entity/ProductVariant.java
````java
public class ProductVariant extends BaseEntity {
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/entity/Region.java
````java
public class Region extends BaseEntity {
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/entity/Store.java
````java
public class Store extends BaseEntity {
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/repository/CategoryRepository.java
````java
public interface CategoryRepository extends BaseRepository<Category, UUID> {
⋮----
Optional<Category> findBySlug(String slug);
⋮----
List<Category> findByParentCategoryIdIsNull();
⋮----
List<Category> findByParentCategoryIdOrderByDisplayOrderAsc(UUID parentCategoryId);
⋮----
List<Category> findByActiveTrueOrderByDisplayOrderAsc();
⋮----
boolean existsBySlug(String slug);
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/repository/ProductCollectionRepository.java
````java
public interface ProductCollectionRepository extends BaseRepository<ProductCollection, UUID> {
⋮----
Optional<ProductCollection> findBySlug(String slug);
⋮----
List<ProductCollection> findByActiveTrueOrderByDisplayOrderAsc();
⋮----
boolean existsBySlug(String slug);
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/repository/ProductImageRepository.java
````java
public interface ProductImageRepository extends BaseRepository<ProductImage, UUID> {
⋮----
List<ProductImage> findByProductIdOrderByDisplayOrderAsc(UUID productId);
⋮----
List<ProductImage> findByProductId(UUID productId);
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/repository/ProductOptionRepository.java
````java
public interface ProductOptionRepository extends BaseRepository<ProductOption, UUID> {
⋮----
List<ProductOption> findByProductId(UUID productId);
⋮----
List<ProductOption> findByProductIdOrderByCreatedAtAsc(UUID productId);
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/repository/ProductOptionValueRepository.java
````java
public interface ProductOptionValueRepository extends BaseRepository<ProductOptionValue, UUID> {
⋮----
List<ProductOptionValue> findByOptionId(UUID optionId);
⋮----
List<ProductOptionValue> findByOptionIdOrderByCreatedAtAsc(UUID optionId);
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/repository/ProductRepository.java
````java
public interface ProductRepository extends BaseRepository<Product, UUID> {
⋮----
Optional<Product> findBySlug(String slug);
⋮----
List<Product> findByStatus(ProductStatus status);
⋮----
List<Product> findByCategoryId(UUID categoryId);
⋮----
List<Product> findByCollectionId(UUID collectionId);
⋮----
List<Product> findByTypeId(UUID typeId);
⋮----
boolean existsBySlug(String slug);
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/repository/ProductTypeRepository.java
````java
public interface ProductTypeRepository extends BaseRepository<ProductType, UUID> {
⋮----
Optional<ProductType> findByName(String name);
⋮----
Optional<ProductType> findBySlug(String slug);
⋮----
boolean existsByName(String name);
⋮----
boolean existsBySlug(String slug);
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/repository/ProductVariantRepository.java
````java
public interface ProductVariantRepository extends BaseRepository<ProductVariant, UUID> {
⋮----
List<ProductVariant> findByProductId(UUID productId);
⋮----
Optional<ProductVariant> findBySku(String sku);
⋮----
boolean existsBySku(String sku);
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/repository/RegionRepository.java
````java
public interface RegionRepository extends BaseRepository<Region, UUID> {
⋮----
Optional<Region> findByName(String name);
⋮----
Optional<Region> findByCurrencyCode(String currencyCode);
⋮----
boolean existsByName(String name);
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/domain/repository/StoreRepository.java
````java
public interface StoreRepository extends BaseRepository<Store, UUID> {
⋮----
Optional<Store> findByName(String name);
⋮----
boolean existsByName(String name);
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/interfaces/rest/AdminCatalogController.java
````java
public class AdminCatalogController {
⋮----
// ===== Product Endpoints =====
⋮----
public ApiResponse<List<ProductResponse>> getAllProducts() {
return ApiResponse.success(catalogService.getAllProducts());
⋮----
public ApiResponse<ProductResponse> getProductById(@PathVariable UUID id) {
return ApiResponse.success(catalogService.getProductById(id));
⋮----
public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
return ApiResponse.success(catalogService.createProduct(request));
⋮----
public ApiResponse<ProductResponse> updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductRequest request) {
return ApiResponse.success(catalogService.updateProduct(id, request));
⋮----
public ApiResponse<Void> deleteProduct(@PathVariable UUID id) {
catalogService.deleteProduct(id);
return ApiResponse.success(null);
⋮----
// ===== Variant Endpoints =====
⋮----
public ApiResponse<List<VariantResponse>> getVariantsByProductId(@PathVariable UUID id) {
return ApiResponse.success(catalogService.getVariantsByProductId(id));
⋮----
public ApiResponse<VariantResponse> getVariantById(@PathVariable UUID id) {
return ApiResponse.success(catalogService.getVariantById(id));
⋮----
public ApiResponse<VariantResponse> createVariant(@PathVariable UUID id, @Valid @RequestBody VariantRequest request) {
return ApiResponse.success(catalogService.createVariant(id, request));
⋮----
public ApiResponse<VariantResponse> updateVariant(@PathVariable UUID id, @Valid @RequestBody VariantRequest request) {
return ApiResponse.success(catalogService.updateVariant(id, request));
⋮----
public ApiResponse<Void> deleteVariant(@PathVariable UUID id) {
catalogService.deleteVariant(id);
⋮----
// ===== Category Endpoints =====
⋮----
public ApiResponse<List<CategoryResponse>> getAllCategories() {
return ApiResponse.success(catalogService.getAllCategories());
⋮----
public ApiResponse<CategoryResponse> getCategoryById(@PathVariable UUID id) {
return ApiResponse.success(catalogService.getCategoryById(id));
⋮----
public ApiResponse<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
return ApiResponse.success(catalogService.createCategory(request));
⋮----
public ApiResponse<CategoryResponse> updateCategory(@PathVariable UUID id, @Valid @RequestBody CategoryRequest request) {
return ApiResponse.success(catalogService.updateCategory(id, request));
⋮----
public ApiResponse<Void> deleteCategory(@PathVariable UUID id) {
catalogService.deleteCategory(id);
````

## File: modules/catalog/src/main/java/com/v8n/modules/catalog/interfaces/rest/StoreCatalogController.java
````java
public class StoreCatalogController {
⋮----
// ===== Product Endpoints =====
⋮----
public ApiResponse<List<ProductResponse>> getPublishedProducts() {
return ApiResponse.success(catalogService.getProductsByStatus(ProductStatus.published));
⋮----
public ApiResponse<ProductResponse> getProductById(@PathVariable UUID id) {
ProductResponse product = catalogService.getProductById(id);
return ApiResponse.success(product);
⋮----
public ApiResponse<ProductResponse> getProductBySlug(@PathVariable String slug) {
ProductResponse product = catalogService.getProductBySlug(slug);
⋮----
public ApiResponse<List<ProductResponse>> getProductsByCategory(@PathVariable UUID categoryId) {
return ApiResponse.success(catalogService.getProductsByCategory(categoryId));
⋮----
// ===== Category Endpoints =====
⋮----
public ApiResponse<List<CategoryResponse>> getRootCategories() {
return ApiResponse.success(catalogService.getRootCategories());
⋮----
public ApiResponse<List<CategoryResponse>> getSubcategories(@PathVariable UUID parentId) {
return ApiResponse.success(catalogService.getSubcategories(parentId));
````

## File: modules/catalog/build.gradle
````
dependencies {
    api project(':modules:core')
}
````

## File: modules/core/src/main/java/com/v8n/modules/core/application/dto/ApiResponse.java
````java
public class ApiResponse<T> {
⋮----
public static <T> ApiResponse<T> success(T data) {
return ApiResponse.<T>builder()
.success(true)
.data(data)
.timestamp(LocalDateTime.now())
.build();
⋮----
public static <T> ApiResponse<T> success(String message, T data) {
⋮----
.message(message)
⋮----
public static <T> ApiResponse<T> error(String message) {
⋮----
.success(false)
⋮----
public static <T> ApiResponse<T> error(String message, Map<String, String> errors) {
⋮----
.errors(errors)
````

## File: modules/core/src/main/java/com/v8n/modules/core/application/dto/PageResponse.java
````java
public class PageResponse<T> {
⋮----
public static <T> PageResponse<T> from(Page<T> page) {
return PageResponse.<T>builder()
.content(page.getContent())
.page(page.getNumber())
.size(page.getSize())
.totalElements(page.getTotalElements())
.totalPages(page.getTotalPages())
.first(page.isFirst())
.last(page.isLast())
.build();
````

## File: modules/core/src/main/java/com/v8n/modules/core/application/exception/BusinessException.java
````java
public class BusinessException extends RuntimeException {
⋮----
super(errorCode.getMessage());
````

## File: modules/core/src/main/java/com/v8n/modules/core/application/exception/ErrorCode.java
````java
// Generic
⋮----
// Auth & Identity
⋮----
// Cart
⋮----
// Inventory
⋮----
// Order
⋮----
// Payment
⋮----
// Fulfillment
⋮----
// Promotion
⋮----
// Catalog
⋮----
public String getMessage() {
````

## File: modules/core/src/main/java/com/v8n/modules/core/application/service/BaseService.java
````java
public abstract class BaseService<T, R extends BaseRepository<T, UUID>> {
⋮----
public T save(T entity) {
return repository.save(entity);
⋮----
public Optional<T> findById(UUID id) {
return repository.findByIdNotDeleted(id);
⋮----
public void delete(UUID id) {
repository.deleteById(id);
````

## File: modules/core/src/main/java/com/v8n/modules/core/domain/entity/BaseEntity.java
````java
public abstract class BaseEntity {
⋮----
protected void onCreate() {
createdAt = LocalDateTime.now();
updatedAt = LocalDateTime.now();
⋮----
protected void onUpdate() {
````

## File: modules/core/src/main/java/com/v8n/modules/core/domain/entity/BaseEnum.java
````java
public interface BaseEnum<T> {
T getValue();
⋮----
String getLabel();
````

## File: modules/core/src/main/java/com/v8n/modules/core/domain/event/DomainEvent.java
````java
public abstract class DomainEvent extends ApplicationEvent {
⋮----
this.eventId = UUID.randomUUID();
this.occurredAt = LocalDateTime.now();
````

## File: modules/core/src/main/java/com/v8n/modules/core/domain/repository/BaseRepository.java
````java
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
⋮----
default Optional<T> findByIdNotDeleted(UUID id) {
// findById does NOT filter soft-deletes by default.
// Concrete repositories must override this with @Query("WHERE deletedAt IS NULL").
// Fallback: fetch and check null.
⋮----
return Optional.empty();
⋮----
Optional<T> result = findById((ID) id);
if (result.isPresent() && result.get() instanceof BaseEntity) {
BaseEntity entity = (BaseEntity) result.get();
if (entity.getDeletedAt() != null) {
⋮----
default boolean existsByIdNotDeleted(UUID id) {
⋮----
return result.isPresent();
````

## File: modules/core/src/main/java/com/v8n/modules/core/infrastructure/config/BusinessValidationException.java
````java
public class BusinessValidationException extends RuntimeException {
⋮----
super(errorCode.getMessage());
````

## File: modules/core/src/main/java/com/v8n/modules/core/infrastructure/config/GlobalExceptionHandler.java
````java
public class GlobalExceptionHandler {
⋮----
public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
HttpStatus status = resolveStatus(ex.getErrorCode());
log.warn("Business exception [{}]: {}", ex.getErrorCode(), ex.getMessage());
⋮----
.status(status)
.body(ApiResponse.error(ex.getMessage()));
⋮----
public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
log.warn("Resource not found [{}]: {}", ex.getErrorCode(), ex.getMessage());
⋮----
.status(HttpStatus.NOT_FOUND)
⋮----
public ResponseEntity<ApiResponse<Void>> handleBusinessValidation(BusinessValidationException ex) {
log.warn("Validation exception [{}]: {}", ex.getErrorCode(), ex.getMessage());
⋮----
.badRequest()
.body(ApiResponse.error(ex.getMessage(), ex.getErrors()));
⋮----
public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
Map<String, String> errors = extractFieldErrors(ex.getBindingResult().getFieldErrors());
log.warn("Validation failed: {}", errors);
⋮----
.body(ApiResponse.error("Validation failed", errors));
⋮----
public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
Map<String, String> errors = ex.getConstraintViolations().stream()
.collect(Collectors.toMap(
violation -> violation.getPropertyPath().toString(),
⋮----
log.warn("Constraint violation: {}", errors);
⋮----
.body(ApiResponse.error("Constraint violation", errors));
⋮----
public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
log.warn("Malformed request body: {}", ex.getMessage());
⋮----
.body(ApiResponse.error("Malformed request body"));
⋮----
public ResponseEntity<ApiResponse<Void>> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
Map<String, String> errors = Map.of(ex.getParameterName(), "Required request parameter is missing");
log.warn("Missing request parameter: {}", errors);
⋮----
.body(ApiResponse.error("Missing request parameter", errors));
⋮----
public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(TypeMismatchException ex) {
String field = ex.getPropertyName() != null ? ex.getPropertyName() : "value";
Map<String, String> errors = Map.of(field, "Invalid value type");
log.warn("Type mismatch: {}", ex.getMessage());
⋮----
.body(ApiResponse.error("Invalid value type", errors));
⋮----
public ResponseEntity<ApiResponse<Void>> handleBindException(BindException ex) {
⋮----
log.warn("Binding failed: {}", errors);
⋮----
.body(ApiResponse.error("Binding failed", errors));
⋮----
public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
log.warn("Bad credentials: {}", ex.getMessage());
⋮----
.status(HttpStatus.UNAUTHORIZED)
.body(ApiResponse.error("Invalid credentials"));
⋮----
public ResponseEntity<ApiResponse<Void>> handleAuthentication(AuthenticationException ex) {
log.warn("Authentication failed: {}", ex.getMessage());
⋮----
.body(ApiResponse.error("Authentication required"));
⋮----
public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
log.warn("Access denied: {}", ex.getMessage());
⋮----
.status(HttpStatus.FORBIDDEN)
.body(ApiResponse.error("Access denied"));
⋮----
public ResponseEntity<ApiResponse<Void>> handleNoHandlerFound(NoHandlerFoundException ex) {
log.warn("No handler found: {} {}", ex.getHttpMethod(), ex.getRequestURL());
⋮----
.body(ApiResponse.error("Resource not found"));
⋮----
public ResponseEntity<ApiResponse<Void>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
log.warn("Method not allowed: {}", ex.getMessage());
⋮----
.status(HttpStatus.METHOD_NOT_ALLOWED)
.body(ApiResponse.error("Method not allowed"));
⋮----
public ResponseEntity<ApiResponse<Void>> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
log.warn("Unsupported media type: {}", ex.getMessage());
⋮----
.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
.body(ApiResponse.error("Unsupported media type"));
⋮----
public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
log.error("Unexpected error", ex);
⋮----
.status(HttpStatus.INTERNAL_SERVER_ERROR)
.body(ApiResponse.error("Internal server error"));
⋮----
private Map<String, String> extractFieldErrors(Iterable<FieldError> fieldErrors) {
⋮----
fieldErrors.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
⋮----
private HttpStatus resolveStatus(ErrorCode errorCode) {
````

## File: modules/core/src/main/java/com/v8n/modules/core/infrastructure/config/JpaAuditingConfig.java
````java
public class JpaAuditingConfig {
⋮----
public AuditorAware<UUID> auditorProvider() {
⋮----
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
if (authentication == null || !authentication.isAuthenticated()) {
return Optional.empty();
⋮----
// Extract user ID from the principal if available
// The principal can be a custom UserDetails or a UUID string
Object principal = authentication.getPrincipal();
⋮----
return Optional.of(UUID.fromString((String) principal));
````

## File: modules/core/src/main/java/com/v8n/modules/core/infrastructure/config/ResourceNotFoundException.java
````java
public class ResourceNotFoundException extends RuntimeException {
⋮----
super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
⋮----
super(errorCode.getMessage());
````

## File: modules/core/src/main/java/com/v8n/modules/core/infrastructure/security/annotation/PublicEndpoint.java
````java
/**
 * Annotation to mark endpoints as public.
 * Endpoints annotated with @PublicEndpoint will bypass Spring Security authentication.
 */
````

## File: modules/core/build.gradle
````
dependencies {
    api "org.springframework.boot:spring-boot-starter-web:${springBootVersion}"
    api "org.springframework.boot:spring-boot-starter-security:${springBootVersion}"
    api "org.springframework.boot:spring-boot-starter-data-jpa:${springBootVersion}"
    api "org.springframework.boot:spring-boot-starter-validation:${springBootVersion}"
    api "org.springdoc:springdoc-openapi-starter-webmvc-ui:${springdocVersion}"
}
````

## File: modules/fulfillment/src/main/java/com/v8n/fulfillment/application/dto/FulfillmentRequest.java
````java
public class FulfillmentRequest {
````

## File: modules/fulfillment/src/main/java/com/v8n/fulfillment/application/dto/FulfillmentResponse.java
````java
public class FulfillmentResponse {
````

## File: modules/fulfillment/src/main/java/com/v8n/fulfillment/application/service/FulfillmentService.java
````java
public class FulfillmentService {
⋮----
// ===== Fulfillment Methods =====
⋮----
public FulfillmentResponse getFulfillmentById(UUID fulfillmentId) {
Fulfillment fulfillment = findFulfillmentById(fulfillmentId);
return toFulfillmentResponse(fulfillment);
⋮----
public List<FulfillmentResponse> getFulfillmentByOrderId(UUID orderId) {
return fulfillmentRepository.findByOrderId(orderId).stream()
.map(this::toFulfillmentResponse)
.collect(Collectors.toList());
⋮----
public FulfillmentResponse createFulfillment(UUID orderId, FulfillmentRequest request) {
Order order = orderRepository.findByIdNotDeleted(orderId)
.orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
⋮----
Fulfillment fulfillment = new Fulfillment();
fulfillment.setOrder(order);
fulfillment.setStatus(FulfillmentStatus.NOT_FULFILLED);
⋮----
if (request.getTrackingNumber() != null) {
fulfillment.setTrackingNumber(request.getTrackingNumber());
⋮----
if (request.getCarrier() != null) {
fulfillment.setCarrier(request.getCarrier());
⋮----
for (OrderItem item : order.getItems()) {
FulfillmentItem fulfillmentItem = new FulfillmentItem();
fulfillmentItem.setFulfillment(fulfillment);
fulfillmentItem.setVariant(item.getVariant());
fulfillmentItem.setTitle(item.getTitle());
fulfillmentItem.setSku(item.getSku());
fulfillmentItem.setQuantity(item.getQuantity());
fulfillmentItem.setFulfilledQuantity(0);
fulfillmentItem.setReturnedQuantity(0);
fulfillment.addItem(fulfillmentItem);
⋮----
fulfillment = fulfillmentRepository.save(fulfillment);
log.info("Created fulfillment {} for order {}", fulfillment.getId(), orderId);
⋮----
public FulfillmentResponse shipFulfillment(UUID fulfillmentId, FulfillmentRequest request) {
⋮----
if (fulfillment.getStatus() != FulfillmentStatus.NOT_FULFILLED &&
fulfillment.getStatus() != FulfillmentStatus.PARTIALLY_FULFILLED) {
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Cannot ship fulfillment in status: " + fulfillment.getStatus());
⋮----
fulfillment.setShippedAt(LocalDateTime.now());
⋮----
int totalItems = fulfillment.getItems().stream().mapToInt(FulfillmentItem::getQuantity).sum();
int fulfilledItems = fulfillment.getItems().stream().mapToInt(FulfillmentItem::getFulfilledQuantity).sum();
⋮----
fulfillment.setStatus(FulfillmentStatus.FULFILLED);
⋮----
fulfillment.setStatus(FulfillmentStatus.PARTIALLY_FULFILLED);
⋮----
log.info("Shipped fulfillment {} with tracking {}", fulfillmentId, fulfillment.getTrackingNumber());
⋮----
public FulfillmentResponse deliverFulfillment(UUID fulfillmentId) {
⋮----
if (fulfillment.getStatus() != FulfillmentStatus.FULFILLED) {
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Fulfillment must be shipped before marking as delivered");
⋮----
fulfillment.setDeliveredAt(LocalDateTime.now());
⋮----
log.info("Delivered fulfillment {}", fulfillmentId);
⋮----
public FulfillmentResponse cancelFulfillment(UUID fulfillmentId) {
⋮----
if (fulfillment.getStatus() == FulfillmentStatus.RETURNED ||
fulfillment.getStatus() == FulfillmentStatus.CANCELLED) {
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Cannot cancel fulfillment in status: " + fulfillment.getStatus());
⋮----
fulfillment.setCanceledAt(LocalDateTime.now());
fulfillment.setStatus(FulfillmentStatus.CANCELLED);
⋮----
log.info("Cancelled fulfillment {}", fulfillmentId);
⋮----
public FulfillmentResponse returnFulfillment(UUID fulfillmentId, int returnQuantity) {
⋮----
if (fulfillment.getDeliveredAt() == null) {
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Fulfillment must be delivered before processing return");
⋮----
for (FulfillmentItem item : fulfillment.getItems()) {
item.setReturnedQuantity(item.getReturnedQuantity() + returnQuantity);
⋮----
fulfillment.setStatus(FulfillmentStatus.PARTIALLY_RETURNED);
⋮----
log.info("Processed return for fulfillment {} with quantity {}", fulfillmentId, returnQuantity);
⋮----
// ===== Private Helper Methods =====
⋮----
private Fulfillment findFulfillmentById(UUID fulfillmentId) {
return fulfillmentRepository.findByIdNotDeleted(fulfillmentId)
.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REQUEST, "Fulfillment not found"));
⋮----
private FulfillmentResponse toFulfillmentResponse(Fulfillment fulfillment) {
return FulfillmentResponse.builder()
.id(fulfillment.getId())
.orderId(fulfillment.getOrder().getId())
.displayId(fulfillment.getDisplayId())
.status(fulfillment.getStatus())
.trackingNumber(fulfillment.getTrackingNumber())
.carrier(fulfillment.getCarrier())
.shippedAt(fulfillment.getShippedAt())
.deliveredAt(fulfillment.getDeliveredAt())
.canceledAt(fulfillment.getCanceledAt())
.createdAt(fulfillment.getCreatedAt())
.updatedAt(fulfillment.getUpdatedAt())
.build();
````

## File: modules/fulfillment/src/main/java/com/v8n/fulfillment/domain/entity/Fulfillment.java
````java
public class Fulfillment extends BaseEntity {
⋮----
public void addItem(FulfillmentItem item) {
items.add(item);
item.setFulfillment(this);
⋮----
public void removeItem(FulfillmentItem item) {
items.remove(item);
item.setFulfillment(null);
⋮----
public boolean isFulfilled() {
⋮----
public boolean isShipped() {
````

## File: modules/fulfillment/src/main/java/com/v8n/fulfillment/domain/entity/FulfillmentItem.java
````java
public class FulfillmentItem extends BaseEntity {
````

## File: modules/fulfillment/src/main/java/com/v8n/fulfillment/domain/repository/FulfillmentRepository.java
````java
public interface FulfillmentRepository extends BaseRepository<Fulfillment, UUID> {
⋮----
List<Fulfillment> findByOrderId(@Param("orderId") UUID orderId);
⋮----
Optional<Fulfillment> findByOrderIdAndStatus(@Param("orderId") UUID orderId, @Param("status") FulfillmentStatus status);
⋮----
List<Fulfillment> findByStatus(@Param("status") FulfillmentStatus status);
⋮----
Long findMaxDisplayId();
````

## File: modules/fulfillment/src/main/java/com/v8n/fulfillment/interfaces/rest/AdminFulfillmentController.java
````java
public class AdminFulfillmentController {
⋮----
public ApiResponse<List<FulfillmentResponse>> getFulfillmentByOrderId(@PathVariable UUID orderId) {
return ApiResponse.success(fulfillmentService.getFulfillmentByOrderId(orderId));
⋮----
public ApiResponse<FulfillmentResponse> createFulfillment(
⋮----
return ApiResponse.success(fulfillmentService.createFulfillment(orderId, request));
⋮----
public ApiResponse<FulfillmentResponse> shipFulfillment(
⋮----
return ApiResponse.success(fulfillmentService.shipFulfillment(id, request));
⋮----
public ApiResponse<FulfillmentResponse> deliverFulfillment(@PathVariable UUID id) {
return ApiResponse.success(fulfillmentService.deliverFulfillment(id));
⋮----
public ApiResponse<FulfillmentResponse> cancelFulfillment(@PathVariable UUID id) {
return ApiResponse.success(fulfillmentService.cancelFulfillment(id));
⋮----
public ApiResponse<FulfillmentResponse> returnFulfillment(
⋮----
return ApiResponse.success(fulfillmentService.returnFulfillment(id, quantity));
````

## File: modules/fulfillment/build.gradle
````
dependencies {
    api project(':modules:core')
    implementation project(':modules:cart')
    implementation project(':modules:catalog')
    implementation project(':modules:order')
}
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/ActivationResponse.java
````java
public class ActivationResponse {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/AddressRequest.java
````java
public class AddressRequest {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/AddressResponse.java
````java
public class AddressResponse {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/AdminAuthResponse.java
````java
public class AdminAuthResponse {
⋮----
public static class AdminUserData {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/AdminLoginRequest.java
````java
public class AdminLoginRequest {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/AdminUserResponse.java
````java
public class AdminUserResponse {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/AuthResponse.java
````java
public class AuthResponse {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/CreateRoleRequest.java
````java
public class CreateRoleRequest {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/CreateUserAdminRequest.java
````java
public class CreateUserAdminRequest {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/CustomerResponse.java
````java
public class CustomerResponse {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/LoginHistoryResponse.java
````java
public class LoginHistoryResponse {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/LoginRequest.java
````java
public class LoginRequest {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/PermissionResponse.java
````java
public class PermissionResponse {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/RegisterRequest.java
````java
public class RegisterRequest {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/RoleDetailResponse.java
````java
public class RoleDetailResponse {
⋮----
public static class UserSummary {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/RoleResponse.java
````java
public class RoleResponse {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/RoleSummary.java
````java
public class RoleSummary {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/SetPasswordRequest.java
````java
public class SetPasswordRequest {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/UnlockResponse.java
````java
public class UnlockResponse {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/UpdateProfileRequest.java
````java
public class UpdateProfileRequest {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/UpdateRoleRequest.java
````java
public class UpdateRoleRequest {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/UpdateUserAdminRequest.java
````java
public class UpdateUserAdminRequest {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/UpdateUserPermissionsRequest.java
````java
public class UpdateUserPermissionsRequest {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/UpdateUserRolesRequest.java
````java
public class UpdateUserRolesRequest {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/dto/UserResponse.java
````java
public class UserResponse {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/mapper/RoleMapper.java
````java
public class RoleMapper {
⋮----
public RoleResponse toRoleResponse(Role role) {
return RoleResponse.builder()
.id(role.getId())
.name(role.getName())
.description(role.getDescription())
.permissions(mapPermissionCodes(role.getRolePermissions()))
.permissionsCount(role.getRolePermissions() != null ? role.getRolePermissions().size() : 0)
.usersCount(0)
.isSystem(role.getIsSystem() != null && role.getIsSystem())
.createdAt(role.getCreatedAt())
.updatedAt(role.getUpdatedAt())
.build();
⋮----
public RoleDetailResponse toRoleDetailResponse(Role role) {
return RoleDetailResponse.builder()
⋮----
.users(mapUsers(role.getUserAdminRoles()))
⋮----
public Set<String> mapPermissionCodes(Set<RolePermission> rolePermissions) {
if (rolePermissions == null) return Collections.emptySet();
return rolePermissions.stream()
.map(rp -> rp.getPermission().getCode())
.collect(Collectors.toSet());
⋮----
public List<RoleDetailResponse.UserSummary> mapUsers(Set<UserAdminRole> userAdminRoles) {
if (userAdminRoles == null) return Collections.emptyList();
return userAdminRoles.stream()
.map(uar -> RoleDetailResponse.UserSummary.builder()
.id(uar.getUser().getId())
.email(uar.getUser().getEmail())
.firstName(uar.getUser().getFirstName())
.lastName(uar.getUser().getLastName())
.build())
.collect(Collectors.toList());
⋮----
public List<RoleResponse> toRoleResponseList(List<Role> roles) {
return roles.stream().map(this::toRoleResponse).collect(Collectors.toList());
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/mapper/UserAdminMapper.java
````java
public class UserAdminMapper {
⋮----
public AdminUserResponse toAdminUserResponse(UserAdmin user) {
return AdminUserResponse.builder()
.id(user.getId())
.email(user.getEmail())
.firstName(user.getFirstName())
.lastName(user.getLastName())
.phone(user.getPhone())
.avatarUrl(user.getAvatarUrl())
.isActive(user.getIsActive() != null && user.getIsActive())
.isActivated(user.isActivated())
.roles(mapRoles(user.getUserAdminRoles()))
.permissionsOverride(mapPermissionCodes(user.getUserAdminPermissions()))
.effectivePermissions(user.getEffectivePermissions())
.failedLoginAttempts(user.getFailedLoginAttempts())
.lockedUntil(user.getLockedUntil())
.lastLoginAt(user.getLastLoginAt())
.createdAt(user.getCreatedAt())
.updatedAt(user.getUpdatedAt())
.build();
⋮----
public List<RoleSummary> mapRoles(Set<UserAdminRole> userAdminRoles) {
if (userAdminRoles == null) return Collections.emptyList();
return userAdminRoles.stream()
.map(uar -> {
Role role = uar.getRole();
return RoleSummary.builder()
.id(role.getId())
.name(role.getName())
⋮----
.collect(Collectors.toList());
⋮----
public Set<String> mapPermissionCodes(Set<UserAdminPermission> userAdminPermissions) {
if (userAdminPermissions == null) return Collections.emptySet();
return userAdminPermissions.stream()
.map(uap -> uap.getPermission().getCode())
.collect(Collectors.toSet());
⋮----
public List<AdminUserResponse> toAdminUserResponseList(List<UserAdmin> users) {
return users.stream().map(this::toAdminUserResponse).collect(Collectors.toList());
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/service/AddressService.java
````java
public class AddressService {
⋮----
public List<AddressResponse> getAddressesByUserId(UUID userId) {
Customer customer = getCustomerByUserId(userId);
return addressRepository.findAllByCustomerId(customer.getId()).stream()
.map(this::mapToResponse)
.collect(Collectors.toList());
⋮----
public AddressResponse getAddressById(UUID addressId, UUID userId) {
⋮----
Address address = addressRepository.findByIdAndCustomerId(addressId, customer.getId())
.orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Address not found"));
return mapToResponse(address);
⋮----
public AddressResponse createAddress(UUID userId, AddressRequest request) {
⋮----
long addressCount = addressRepository.countByCustomerId(customer.getId());
⋮----
throw new BusinessException(ErrorCode.INVALID_REQUEST,
⋮----
if (Boolean.TRUE.equals(request.getDefaultShipping())) {
clearDefaultShipping(customer.getId());
⋮----
if (Boolean.TRUE.equals(request.getDefaultBilling())) {
clearDefaultBilling(customer.getId());
⋮----
Address address = new Address();
address.setCustomer(customer);
mapRequestToEntity(request, address);
⋮----
address = addressRepository.save(address);
log.info("Address created for user {}: {}", userId, address.getId());
⋮----
public AddressResponse updateAddress(UUID addressId, UUID userId, AddressRequest request) {
⋮----
if (Boolean.TRUE.equals(request.getDefaultShipping()) && !address.isDefaultShipping()) {
⋮----
if (Boolean.TRUE.equals(request.getDefaultBilling()) && !address.isDefaultBilling()) {
⋮----
log.info("Address updated for user {}: {}", userId, addressId);
⋮----
public void deleteAddress(UUID addressId, UUID userId) {
⋮----
address.setDeletedAt(LocalDateTime.now());
addressRepository.save(address);
log.info("Address soft-deleted for user {}: {}", userId, addressId);
⋮----
public AddressResponse setDefaultShipping(UUID addressId, UUID userId) {
⋮----
address.setDefaultShipping(true);
⋮----
log.info("Default shipping address set for user {}: {}", userId, addressId);
⋮----
public AddressResponse setDefaultBilling(UUID addressId, UUID userId) {
⋮----
address.setDefaultBilling(true);
⋮----
log.info("Default billing address set for user {}: {}", userId, addressId);
⋮----
private void clearDefaultShipping(UUID customerId) {
addressRepository.findByCustomerIdAndDefaultShippingTrue(customerId)
.ifPresent(addr -> {
addr.setDefaultShipping(false);
addressRepository.save(addr);
⋮----
private void clearDefaultBilling(UUID customerId) {
addressRepository.findByCustomerIdAndDefaultBillingTrue(customerId)
⋮----
addr.setDefaultBilling(false);
⋮----
private void mapRequestToEntity(AddressRequest request, Address address) {
address.setLabel(request.getLabel());
address.setRecipientName(request.getRecipientName());
if (request.getRecipientName() != null) {
String[] names = request.getRecipientName().trim().split("\\s+", 2);
address.setFirstName(names[0]);
address.setLastName(names.length > 1 ? names[1] : null);
⋮----
address.setPhone(request.getPhone());
address.setStreet(request.getStreet());
address.setWard(request.getWard());
address.setDistrict(request.getDistrict());
address.setCity(request.getCity());
address.setState(request.getState());
address.setCountry(request.getCountry());
address.setZipCode(request.getZipCode());
address.setAddressType(request.getAddressType());
⋮----
if (request.getDefaultShipping() != null) {
address.setDefaultShipping(request.getDefaultShipping());
⋮----
if (request.getDefaultBilling() != null) {
address.setDefaultBilling(request.getDefaultBilling());
⋮----
private Customer getCustomerByUserId(UUID userId) {
User user = userRepository.findByIdNotDeleted(userId)
.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
return customerRepository.findByEmail(user.getEmail())
.orElseThrow(() -> new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND));
⋮----
private AddressResponse mapToResponse(Address address) {
return AddressResponse.builder()
.id(address.getId())
.label(address.getLabel())
.recipientName(address.getRecipientName())
.phone(address.getPhone())
.street(address.getStreet())
.ward(address.getWard())
.district(address.getDistrict())
.city(address.getCity())
.state(address.getState())
.country(address.getCountry())
.zipCode(address.getZipCode())
.defaultShipping(address.isDefaultShipping())
.defaultBilling(address.isDefaultBilling())
.addressType(address.getAddressType())
.createdAt(address.getCreatedAt())
.updatedAt(address.getUpdatedAt())
.build();
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/service/AdminAuthService.java
````java
public class AdminAuthService {
⋮----
/**
     * Admin login with lockout flow.
     */
⋮----
public AdminAuthResponse login(AdminLoginRequest request, String ipAddress, String userAgent) {
String email = request.getEmail().toLowerCase().trim();
⋮----
// 1. Find user by email (active only)
UserAdmin user = userAdminRepository.findByEmailActive(email)
.orElseThrow(() -> {
// Record failed attempt for unknown user
recordLoginHistory(null, email, LoginStatus.FAILED, "INVALID_CREDENTIALS", ipAddress, userAgent);
throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
⋮----
// 2. Check if password not set (not activated)
if (user.getPasswordHash() == null) {
throw new BusinessException(ErrorCode.INVALID_CREDENTIALS, "Tài khoản chưa được kích hoạt");
⋮----
// 3. Check if account is locked
if (user.isLocked()) {
recordLoginHistory(user, email, LoginStatus.FAILED, "ACCOUNT_LOCKED", ipAddress, userAgent);
throw new BusinessException(ErrorCode.ACCESS_DENIED,
"Tài khoản bị khóa đến " + user.getLockedUntil().toString());
⋮----
// 4. Check password
if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
int attempts = (user.getFailedLoginAttempts() != null ? user.getFailedLoginAttempts() : 0) + 1;
user.setFailedLoginAttempts(attempts);
⋮----
user.setLockedUntil(LocalDateTime.now().plusMinutes(LOCKOUT_DURATION_MINUTES));
userAdminRepository.save(user);
⋮----
recordLoginHistory(user, email, LoginStatus.FAILED, "INVALID_CREDENTIALS", ipAddress, userAgent);
⋮----
throw new BusinessException(ErrorCode.INVALID_CREDENTIALS,
⋮----
// 5. Success: reset failed attempts, update last login
user.setFailedLoginAttempts(0);
user.setLockedUntil(null);
user.setLastLoginAt(LocalDateTime.now());
user.setUpdatedAt(LocalDateTime.now());
⋮----
recordLoginHistory(user, email, LoginStatus.SUCCESS, null, ipAddress, userAgent);
⋮----
// Generate JWT with permissions
Set<String> permissions = user.getEffectivePermissions();
String accessToken = jwtTokenProvider.generateAccessToken(
user.getId(), user.getEmail(), "admin", permissions);
String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());
⋮----
// Build response
List<RoleSummary> roles = Collections.emptyList();
if (user.getUserAdminRoles() != null) {
roles = user.getUserAdminRoles().stream()
.map(uar -> {
Role role = uar.getRole();
return RoleSummary.builder()
.id(role.getId())
.name(role.getName())
.build();
⋮----
.collect(Collectors.toList());
⋮----
return AdminAuthResponse.builder()
.accessToken(accessToken)
.refreshToken(refreshToken)
.tokenType("Bearer")
.expiresIn(jwtTokenProvider.getAccessTokenExpiration() / 1000)
.user(AdminAuthResponse.AdminUserData.builder()
.id(user.getId())
.email(user.getEmail())
.firstName(user.getFirstName())
.lastName(user.getLastName())
.isActive(user.getIsActive() != null && user.getIsActive())
.isActivated(user.isActivated())
.roles(roles)
.permissions(permissions)
.build())
⋮----
/**
     * Refresh token for admin user.
     */
⋮----
public AdminAuthResponse refreshToken(String refreshToken) {
if (!jwtTokenProvider.validateToken(refreshToken)) {
throw new BusinessException(ErrorCode.INVALID_CREDENTIALS, "Invalid or expired refresh token");
⋮----
String userId = jwtTokenProvider.getUserIdFromTokenAsString(refreshToken);
UserAdmin user = userAdminRepository.findByIdActive(userId)
.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
⋮----
String newAccessToken = jwtTokenProvider.generateAccessToken(
⋮----
.accessToken(newAccessToken)
⋮----
/**
     * Get current admin user info.
     */
⋮----
public AdminAuthResponse getCurrentUser(String userId) {
UserAdmin user = userAdminRepository.findById(userId)
⋮----
/**
     * Check activation token validity.
     */
⋮----
public ActivationResponse checkActivation(String token) {
⋮----
activationToken = UUID.fromString(token);
⋮----
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Token không hợp lệ");
⋮----
UserAdmin user = userAdminRepository.findByActivationToken(activationToken)
.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REQUEST, "Token không tồn tại"));
⋮----
if (user.isActivated()) {
// Already activated — return but token is no longer valid
return ActivationResponse.builder()
⋮----
.tokenValid(false)
.expiresAt(null)
⋮----
// Check 24h expiry (activation token issued at user creation time)
LocalDateTime expiresAt = user.getCreatedAt().plusHours(ACTIVATION_TOKEN_HOURS);
boolean isValid = LocalDateTime.now().isBefore(expiresAt);
⋮----
.tokenValid(isValid)
.expiresAt(expiresAt)
⋮----
/**
     * Activate account: set password for the first time.
     */
⋮----
public void activate(SetPasswordRequest request, String ipAddress, String userAgent) {
// Validate password match
if (!request.getPassword().equals(request.getPasswordConfirm())) {
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Mật khẩu xác nhận không khớp");
⋮----
// Validate password strength
if (request.getPassword().length() < 8) {
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Mật khẩu phải có ít nhất 8 ký tự");
⋮----
activationToken = UUID.fromString(request.getActivationToken());
⋮----
// Check if already activated
⋮----
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Tài khoản đã được kích hoạt");
⋮----
// Check expiry
⋮----
if (LocalDateTime.now().isAfter(expiresAt)) {
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Token đã hết hạn");
⋮----
// Set password
user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
user.setPasswordSetAt(LocalDateTime.now());
user.setActivationToken(null);
⋮----
recordLoginHistory(user, user.getEmail(), LoginStatus.SUCCESS, null, ipAddress, userAgent);
log.info("Account activated: {}", user.getEmail());
⋮----
// --- Private helpers ---
⋮----
private void recordLoginHistory(UserAdmin user, String email, LoginStatus status,
⋮----
LoginHistory history = new LoginHistory();
history.setId(UUID.randomUUID().toString());
history.setUserAdmin(user);
history.setEmail(email);
history.setStatus(status);
history.setFailureReason(failureReason);
history.setIpAddress(ipAddress);
history.setUserAgent(userAgent);
history.setAttemptedAt(LocalDateTime.now());
loginHistoryRepository.save(history);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/service/AuthService.java
````java
public class AuthService {
⋮----
public AuthResponse register(RegisterRequest request) {
if (userRepository.existsByEmail(request.getEmail())) {
throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
⋮----
User user = new User();
user.setEmail(request.getEmail().toLowerCase().trim());
user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
user.setFirstName(request.getFirstName());
user.setLastName(request.getLastName());
user.setPhone(request.getPhone());
user.setStatus(User.UserStatus.ACTIVE);
⋮----
user = userRepository.save(user);
log.info("New user registered: {}", user.getEmail());
⋮----
return buildAuthResponse(user);
⋮----
public AuthResponse login(LoginRequest request) {
User user = userRepository.findByEmail(request.getEmail().toLowerCase().trim())
.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));
⋮----
if (user.getStatus() == User.UserStatus.BANNED) {
throw new BusinessException(ErrorCode.INVALID_CREDENTIALS, "Account is banned");
⋮----
if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
⋮----
user.setLastLoginAt(LocalDateTime.now());
userRepository.save(user);
log.info("User logged in: {}", user.getEmail());
⋮----
public UserResponse getCurrentUser(String userId) {
User user = userRepository.findByIdNotDeleted(java.util.UUID.fromString(userId))
.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
return mapToUserResponse(user);
⋮----
public AuthResponse refreshToken(String refreshToken) {
if (!jwtTokenProvider.validateToken(refreshToken)) {
throw new BusinessException(ErrorCode.INVALID_CREDENTIALS, "Invalid or expired refresh token");
⋮----
java.util.UUID userId = jwtTokenProvider.getUserIdFromToken(refreshToken);
User user = userRepository.findByIdNotDeleted(userId)
⋮----
private AuthResponse buildAuthResponse(User user) {
String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getEmail());
String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());
⋮----
return AuthResponse.builder()
.accessToken(accessToken)
.refreshToken(refreshToken)
.tokenType("Bearer")
.expiresIn(jwtTokenProvider.getAccessTokenExpiration() / 1000)
.user(mapToUserResponse(user))
.build();
⋮----
private UserResponse mapToUserResponse(User user) {
return UserResponse.builder()
.id(user.getId())
.email(user.getEmail())
.firstName(user.getFirstName())
.lastName(user.getLastName())
.fullName(user.getFullName())
.phone(user.getPhone())
.avatarUrl(user.getAvatarUrl())
.status(user.getStatus().name())
.emailVerified(user.isEmailVerified())
.createdAt(user.getCreatedAt())
.lastLoginAt(user.getLastLoginAt())
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/service/CustomerService.java
````java
public class CustomerService {
⋮----
public CustomerResponse getCustomerByUserId(UUID userId) {
User user = userRepository.findByIdNotDeleted(userId)
.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
⋮----
Customer customer = customerRepository.findByEmail(user.getEmail())
.orElseThrow(() -> new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND));
⋮----
return mapToResponse(customer, user);
⋮----
public CustomerResponse updateProfile(UUID userId, UpdateProfileRequest request) {
⋮----
if (request.getFirstName() != null) {
customer.setFirstName(request.getFirstName());
⋮----
if (request.getLastName() != null) {
customer.setLastName(request.getLastName());
⋮----
if (request.getPhone() != null) {
customer.setPhone(request.getPhone());
⋮----
if (request.getCompany() != null) {
customer.setCompany(request.getCompany());
⋮----
customer = customerRepository.save(customer);
log.info("Customer profile updated for user: {}", userId);
⋮----
public CustomerResponse createCustomerIfNotExists(UUID userId) {
⋮----
if (customerRepository.existsByEmail(user.getEmail())) {
return customerRepository.findByEmail(user.getEmail())
.map(c -> mapToResponse(c, userRepository.findByIdNotDeleted(userId).get()))
⋮----
Customer customer = new Customer();
customer.setUser(user);
customer.setEmail(user.getEmail());
customer.setFirstName(user.getFirstName());
customer.setLastName(user.getLastName());
customer.setPhone(user.getPhone());
customer.setHasAccount(true);
⋮----
log.info("Customer created for user: {}", userId);
⋮----
private CustomerResponse mapToResponse(Customer customer, User user) {
return CustomerResponse.builder()
.id(customer.getId())
.userId(user.getId())
.email(user.getEmail())
.firstName(customer.getFirstName())
.lastName(customer.getLastName())
.fullName(customer.getFullName())
.phone(customer.getPhone())
.company(customer.getCompany())
.avatarUrl(user.getAvatarUrl())
.createdAt(customer.getCreatedAt())
.updatedAt(customer.getUpdatedAt())
.build();
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/service/LoginHistoryService.java
````java
public class LoginHistoryService {
⋮----
/**
     * Lấy danh sách login history có phân trang và lọc.
     */
public Page<LoginHistoryResponse> listLoginHistory(
⋮----
Pageable pageable = PageRequest.of(page / size, size);
Page<LoginHistory> pageResult = loginHistoryRepository.findFiltered(email, status, from, to, pageable);
return pageResult.map(this::toResponse);
⋮----
/**
     * Export tất cả records matching filter dưới dạng CSV string.
     */
public String exportCsv(String email, LoginStatus status, LocalDateTime from, LocalDateTime to) {
List<LoginHistory> records = loginHistoryRepository.findFilteredAll(email, status, from, to);
StringWriter writer = new StringWriter();
⋮----
// CSV header
writer.write("id,email,status,failure_reason,ip_address,user_agent,attempted_at\n");
⋮----
// CSV rows
⋮----
writer.write(String.format("%s,%s,%s,%s,%s,%s,%s\n",
escapeCsv(lh.getId()),
escapeCsv(lh.getEmail()),
lh.getStatus() != null ? lh.getStatus().name() : "",
escapeCsv(lh.getFailureReason()),
escapeCsv(lh.getIpAddress()),
escapeCsv(lh.getUserAgent()),
lh.getAttemptedAt() != null ? lh.getAttemptedAt().toString() : ""
⋮----
return writer.toString();
⋮----
private LoginHistoryResponse toResponse(LoginHistory lh) {
return LoginHistoryResponse.builder()
.id(lh.getId())
.email(lh.getEmail())
.status(lh.getStatus() != null ? lh.getStatus().name() : null)
.failureReason(lh.getFailureReason())
.ipAddress(lh.getIpAddress())
.userAgent(lh.getUserAgent())
.attemptedAt(lh.getAttemptedAt())
.build();
⋮----
private String escapeCsv(String value) {
⋮----
if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
return "\"" + value.replace("\"", "\"\"") + "\"";
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/service/PermissionService.java
````java
public class PermissionService {
⋮----
/**
     * Trả về master data permissions grouped by resource.
     * Mỗi resource chứa danh sách operation.
     */
public List<PermissionResponse> getGroupedPermissions() {
List<Permission> allPermissions = permissionRepository.findAll();
⋮----
Map<String, List<String>> grouped = allPermissions.stream()
.collect(Collectors.groupingBy(
⋮----
Collectors.mapping(Permission::getOperation, Collectors.toList())
⋮----
return grouped.entrySet().stream()
.map(entry -> PermissionResponse.builder()
.resource(entry.getKey())
.operations(entry.getValue())
.build())
.collect(Collectors.toList());
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/service/RoleService.java
````java
public class RoleService {
⋮----
public List<RoleResponse> listRoles() {
List<Role> roles = roleRepository.findAll();
List<RoleResponse> responses = roleMapper.toRoleResponseList(roles);
// Populate usersCount for each role
for (int i = 0; i < roles.size(); i++) {
long count = roleRepository.countUsersByRoleId(roles.get(i).getId());
responses.get(i).setUsersCount(count);
responses.get(i).setPermissionsCount(
roles.get(i).getRolePermissions() != null ? roles.get(i).getRolePermissions().size() : 0
⋮----
public RoleDetailResponse getRole(UUID roleId) {
Role role = roleRepository.findById(roleId)
.orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));
return roleMapper.toRoleDetailResponse(role);
⋮----
public RoleResponse createRole(CreateRoleRequest request) {
// Check unique name
if (roleRepository.existsByName(request.getName())) {
throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Tên role đã tồn tại");
⋮----
// Validate permissions
validatePermissions(request.getPermissions());
⋮----
Role role = new Role();
role.setName(request.getName());
role.setDescription(request.getDescription());
role.setIsSystem(false);
role.setCreatedAt(LocalDateTime.now());
role = roleRepository.save(role);
⋮----
// Assign permissions (hard sync for new role)
syncRolePermissions(role, request.getPermissions());
⋮----
RoleResponse response = roleMapper.toRoleResponse(role);
response.setPermissions(request.getPermissions());
response.setPermissionsCount(request.getPermissions().size());
response.setUsersCount(0);
⋮----
public RoleResponse updateRole(UUID roleId, UpdateRoleRequest request) {
⋮----
// Cannot modify system roles
if (role.getIsSystem() != null && role.getIsSystem()) {
throw new BusinessException(ErrorCode.ACCESS_DENIED, "Không thể sửa role hệ thống");
⋮----
// Check unique name (exclude current role)
roleRepository.findByName(request.getName()).ifPresent(existing -> {
if (!existing.getId().equals(roleId)) {
⋮----
role.setUpdatedAt(LocalDateTime.now());
⋮----
// Hard sync permissions
⋮----
roleRepository.save(role);
⋮----
response.setUsersCount(roleRepository.countUsersByRoleId(roleId));
⋮----
public void deleteRole(UUID roleId) {
⋮----
// Cannot delete system roles
⋮----
throw new BusinessException(ErrorCode.ACCESS_DENIED, "Không thể xóa role hệ thống");
⋮----
// Check if role has users
long userCount = roleRepository.countUsersByRoleId(roleId);
⋮----
throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
⋮----
rolePermissionRepository.deleteByRoleId(roleId);
roleRepository.delete(role);
⋮----
/**
     * Validate that all permission codes exist in the database.
     */
private void validatePermissions(Set<String> permissionCodes) {
if (permissionCodes == null || permissionCodes.isEmpty()) {
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Phải chọn ít nhất 1 quyền");
⋮----
permissionRepository.findByCode(code)
.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REQUEST,
⋮----
/**
     * Hard sync: delete all existing role_permission, insert new set.
     */
private void syncRolePermissions(Role role, Set<String> permissionCodes) {
// Delete existing
rolePermissionRepository.deleteByRoleId(role.getId());
rolePermissionRepository.flush();
⋮----
// Insert new
⋮----
Permission perm = permissionRepository.findByCode(code).orElseThrow();
RolePermission rp = new RolePermission();
rp.setId(new RolePermissionId(role.getId(), perm.getId()));
rp.setRole(role);
rp.setPermission(perm);
rp.setCreatedAt(LocalDateTime.now());
newPermissions.add(rp);
⋮----
rolePermissionRepository.saveAll(newPermissions);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/application/service/UserAdminService.java
````java
public class UserAdminService {
⋮----
public List<AdminUserResponse> listUsers(String q, String status) {
// Simplified: fetch all and filter. In production, use Specification/QueryDSL.
List<UserAdmin> users = userAdminRepository.findAll();
⋮----
if (q != null && !q.isBlank()) {
String lowerQ = q.toLowerCase();
users = users.stream()
.filter(u -> (u.getEmail() != null && u.getEmail().toLowerCase().contains(lowerQ))
|| (u.getFirstName() != null && u.getFirstName().toLowerCase().contains(lowerQ))
|| (u.getLastName() != null && u.getLastName().toLowerCase().contains(lowerQ)))
.collect(Collectors.toList());
⋮----
if (status != null && !status.isBlank()) {
if ("active".equalsIgnoreCase(status)) {
users = users.stream().filter(u -> u.getIsActive() != null && u.getIsActive()).collect(Collectors.toList());
} else if ("inactive".equalsIgnoreCase(status)) {
users = users.stream().filter(u -> u.getIsActive() == null || !u.getIsActive()).collect(Collectors.toList());
} else if ("locked".equalsIgnoreCase(status)) {
users = users.stream().filter(UserAdmin::isLocked).collect(Collectors.toList());
⋮----
return userAdminMapper.toAdminUserResponseList(users);
⋮----
public AdminUserResponse getUser(String userId) {
UserAdmin user = userAdminRepository.findById(userId)
.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
return userAdminMapper.toAdminUserResponse(user);
⋮----
public AdminUserResponse createUser(CreateUserAdminRequest request, String currentUserId) {
// Soft unique check: email must be unique among active users
long activeWithEmail = userAdminRepository.countByEmailActive(request.getEmail().toLowerCase().trim());
⋮----
throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
⋮----
// Validate roleIds
⋮----
if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
for (String roleId : request.getRoleIds()) {
UUID roleUuid = UUID.fromString(roleId);
Role role = roleRepository.findById(roleUuid)
.orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));
roles.add(role);
⋮----
// Create user
UserAdmin user = new UserAdmin();
user.setId(UUID.randomUUID().toString());
user.setEmail(request.getEmail().toLowerCase().trim());
user.setFirstName(request.getFirstName());
user.setLastName(request.getLastName());
user.setPhone(request.getPhone());
user.setAvatarUrl(request.getAvatarUrl());
user.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
user.setActivationToken(UUID.randomUUID());
user.setFailedLoginAttempts(0);
user.setCreatedAt(LocalDateTime.now());
user.setUpdatedAt(LocalDateTime.now());
⋮----
user = userAdminRepository.save(user);
⋮----
// Assign roles
UserAdmin assigner = currentUserId != null ? userAdminRepository.findById(currentUserId).orElse(null) : null;
⋮----
UserAdminRole uar = new UserAdminRole();
uar.setId(new UserAdminRoleId(user.getId(), role.getId()));
uar.setUser(user);
uar.setRole(role);
uar.setAssignedAt(LocalDateTime.now());
uar.setAssignedBy(assigner);
userRoles.add(uar);
⋮----
userAdminRoleRepository.saveAll(userRoles);
⋮----
// User-level permission override (optional)
if (request.getPermissions() != null && !request.getPermissions().isEmpty()) {
validatePermissionsNotNarrowing(roles, request.getPermissions());
setUserPermissions(user, request.getPermissions(), assigner);
⋮----
log.info("Admin user created: {} by {}", user.getEmail(), currentUserId);
⋮----
public AdminUserResponse updateUser(String userId, UpdateUserAdminRequest request, String currentUserId) {
⋮----
if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
if (request.getLastName() != null) user.setLastName(request.getLastName());
if (request.getPhone() != null) user.setPhone(request.getPhone());
if (request.getAvatarUrl() != null) user.setAvatarUrl(request.getAvatarUrl());
if (request.getIsActive() != null) user.setIsActive(request.getIsActive());
⋮----
public AdminUserResponse updateRoles(String userId, List<String> roleIds, String currentUserId) {
// Cannot modify self
if (currentUserId != null && currentUserId.equals(userId)) {
throw new BusinessException(ErrorCode.ACCESS_DENIED, "Không thể tự sửa role của chính mình");
⋮----
// Validate roles
⋮----
// Hard sync: delete all existing user_admin_roles, insert new
userAdminRoleRepository.deleteByUserId(userId);
userAdminRoleRepository.flush();
⋮----
newRoles.add(uar);
⋮----
userAdminRoleRepository.saveAll(newRoles);
⋮----
public void deactivateUser(String userId, String currentUserId) {
⋮----
throw new BusinessException(ErrorCode.ACCESS_DENIED, "Không thể tự vô hiệu hóa chính mình");
⋮----
user.setIsActive(false);
user.setDeletedAt(LocalDateTime.now());
⋮----
userAdminRepository.save(user);
⋮----
log.info("User deactivated: {} by {}", user.getEmail(), currentUserId);
⋮----
public UnlockResponse unlockUser(String userId, String currentUserId) {
⋮----
if (!user.isLocked()) {
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Tài khoản không bị khóa");
⋮----
user.setLockedUntil(null);
⋮----
log.info("User unlocked: {} by {}", user.getEmail(), currentUserId);
⋮----
return UnlockResponse.builder()
.id(user.getId())
.email(user.getEmail())
.lockedUntil(null)
.failedLoginAttempts(0)
.unlockedAt(LocalDateTime.now())
.unlockedBy(currentUserId)
.build();
⋮----
public AdminUserResponse updatePermissions(String userId, Set<String> permissionCodes, String currentUserId) {
⋮----
// Get user's role permissions
Set<String> rolePermissions = user.getEffectivePermissions();
// Remove user-level overrides from role permissions to get "pure" role permissions
// Actually, effectivePermissions already includes both. We need to validate narrowing.
// Simplification: only validate that new permissions are valid codes
⋮----
permissionRepository.findByCode(code)
.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REQUEST,
⋮----
// Hard sync
userAdminPermissionRepository.deleteByUserId(userId);
userAdminPermissionRepository.flush();
⋮----
setUserPermissions(user, permissionCodes, assigner);
⋮----
public AdminUserResponse resendActivation(String userId, String currentUserId) {
⋮----
if (user.isActivated()) {
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Tài khoản đã được kích hoạt");
⋮----
log.info("Activation resent for: {} by {}", user.getEmail(), currentUserId);
⋮----
// --- Private helpers ---
⋮----
private void validatePermissionsNotNarrowing(List<Role> roles, Set<String> permissionCodes) {
// Get all permissions from assigned roles
⋮----
if (role.getRolePermissions() != null) {
for (var rp : role.getRolePermissions()) {
if (rp.getPermission() != null) {
rolePermissionCodes.add(rp.getPermission().getCode());
⋮----
// User-level permissions must be a superset of role permissions (only expand)
// But actually SRS says user-level permissions only ADD, never subtract.
// Subtracting is prevented because effective permissions = role ∪ user-level.
// So user-level permissions just add extra permissions. No validation needed against narrowing.
// We just validate that the permission codes exist.
⋮----
private void setUserPermissions(UserAdmin user, Set<String> permissionCodes, UserAdmin assigner) {
⋮----
Permission perm = permissionRepository.findByCode(code).orElseThrow();
UserAdminPermission uap = new UserAdminPermission();
uap.setId(new UserAdminPermissionId(user.getId(), perm.getId()));
uap.setUser(user);
uap.setPermission(perm);
uap.setAssignedAt(LocalDateTime.now());
uap.setAssignedBy(assigner);
perms.add(uap);
⋮----
userAdminPermissionRepository.saveAll(perms);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/Address.java
````java
public class Address extends BaseEntity {
⋮----
public User getUser() {
return customer == null ? null : customer.getUser();
⋮----
public void setUser(User user) {
Customer linkedCustomer = new Customer();
linkedCustomer.setUser(user);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/Customer.java
````java
public class Customer extends BaseEntity {
⋮----
public String getFullName() {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/Permission.java
````java
public class Permission {
⋮----
private LocalDateTime createdAt = LocalDateTime.now();
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/Role.java
````java
/**
 * JPA Entity mapping to the 'role' table.
 * Replaces the old {@code Role} enum which served customer-facing roles.
 * The old enum may be renamed to {@code CustomerRole} or removed if unused.
 */
⋮----
public class Role {
⋮----
private LocalDateTime createdAt = LocalDateTime.now();
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/RolePermission.java
````java
public class RolePermission {
⋮----
private LocalDateTime createdAt = LocalDateTime.now();
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/RolePermissionId.java
````java
public class RolePermissionId implements Serializable {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/User.java
````java
public class User extends BaseEntity {
⋮----
public String getFullName() {
⋮----
String name = (firstName != null ? firstName : "").trim()
⋮----
+ (lastName != null ? lastName : "").trim();
return name.trim().isEmpty() ? null : name.trim();
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/UserAdmin.java
````java
public class UserAdmin {
⋮----
private LocalDateTime createdAt = LocalDateTime.now();
⋮----
private LocalDateTime updatedAt = LocalDateTime.now();
⋮----
/**
     * Effective permissions = (Role permissions) UNION (User-level permissions).
     * Wildcard "*:*" (Super Admin) short-circuits to itself only.
     */
public Set<String> getEffectivePermissions() {
⋮----
// Permissions from Roles
⋮----
Role role = uar.getRole();
if (role != null && role.getRolePermissions() != null) {
for (RolePermission rp : role.getRolePermissions()) {
if (rp.getPermission() != null) {
perms.add(rp.getPermission().getCode());
⋮----
// User-level override permissions (only add, never subtract)
⋮----
if (uap.getPermission() != null) {
perms.add(uap.getPermission().getCode());
⋮----
// Wildcard *:* short-circuits (Super Admin gets only *:*)
if (perms.contains("*:*")) {
⋮----
allPerms.add("*:*");
⋮----
public boolean isActivated() {
⋮----
public boolean isLocked() {
return lockedUntil != null && lockedUntil.isAfter(LocalDateTime.now());
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/UserAdminPermission.java
````java
public class UserAdminPermission {
⋮----
private LocalDateTime assignedAt = LocalDateTime.now();
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/UserAdminPermissionId.java
````java
public class UserAdminPermissionId implements Serializable {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/UserAdminRole.java
````java
public class UserAdminRole {
⋮----
private LocalDateTime assignedAt = LocalDateTime.now();
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/UserAdminRoleId.java
````java
public class UserAdminRoleId implements Serializable {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/enums/LoginStatus.java
````java

````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/repository/AddressRepository.java
````java
public interface AddressRepository extends BaseRepository<Address, UUID> {
⋮----
List<Address> findAllByCustomerId(UUID customerId);
⋮----
Optional<Address> findByIdAndCustomerId(UUID id, UUID customerId);
⋮----
Optional<Address> findByCustomerIdAndDefaultShippingTrue(UUID customerId);
⋮----
Optional<Address> findByCustomerIdAndDefaultBillingTrue(UUID customerId);
⋮----
boolean existsByIdAndCustomerId(UUID id, UUID customerId);
⋮----
long countByCustomerId(UUID customerId);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/repository/CustomerRepository.java
````java
public interface CustomerRepository extends BaseRepository<Customer, UUID> {
⋮----
Optional<Customer> findByEmail(String email);
⋮----
boolean existsByEmail(String email);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/repository/LoginHistoryRepository.java
````java
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, String> {
⋮----
/**
     * Lọc login history theo email, status, khoảng thời gian.
     */
⋮----
Page<LoginHistory> findFiltered(
⋮----
/**
     * Lấy toàn bộ records cho export (không phân trang).
     */
⋮----
List<LoginHistory> findFilteredAll(
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/repository/PermissionRepository.java
````java
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
⋮----
Optional<Permission> findByCode(String code);
⋮----
List<Permission> findByResource(String resource);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/repository/RolePermissionRepository.java
````java
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {
⋮----
List<RolePermission> findByRoleId(UUID roleId);
⋮----
void deleteByRoleId(@Param("roleId") UUID roleId);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/repository/RoleRepository.java
````java
public interface RoleRepository extends JpaRepository<Role, UUID> {
⋮----
Optional<Role> findByName(String name);
⋮----
boolean existsByName(String name);
⋮----
/**
     * Đếm số user admin đang được gán role này.
     */
⋮----
long countUsersByRoleId(@Param("roleId") UUID roleId);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/repository/UserAdminPermissionRepository.java
````java
public interface UserAdminPermissionRepository extends JpaRepository<UserAdminPermission, UserAdminPermissionId> {
⋮----
List<UserAdminPermission> findByUserId(String userId);
⋮----
void deleteByUserId(@Param("userId") String userId);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/repository/UserAdminRepository.java
````java
public interface UserAdminRepository extends JpaRepository<UserAdmin, String> {
⋮----
/**
     * Tìm user admin active theo email (soft unique constraint).
     */
⋮----
Optional<UserAdmin> findByEmailActive(@Param("email") String email);
⋮----
/**
     * Tìm user admin theo email bất kể trạng thái active.
     */
Optional<UserAdmin> findByEmail(String email);
⋮----
/**
     * Tìm user theo activation token.
     */
Optional<UserAdmin> findByActivationToken(UUID activationToken);
⋮----
/**
     * Đếm số user đang active theo email (dùng để kiểm tra soft unique).
     */
⋮----
long countByEmailActive(@Param("email") String email);
⋮----
/**
     * Tìm user active theo ID (không bị soft-delete).
     */
⋮----
Optional<UserAdmin> findByIdActive(@Param("id") String id);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/repository/UserAdminRoleRepository.java
````java
public interface UserAdminRoleRepository extends JpaRepository<UserAdminRole, UserAdminRoleId> {
⋮----
List<UserAdminRole> findByUserId(String userId);
⋮----
void deleteByUserId(@Param("userId") String userId);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/repository/UserRepository.java
````java
public interface UserRepository extends BaseRepository<User, UUID> {
⋮----
Optional<User> findByEmail(String email);
⋮----
boolean existsByEmail(String email);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/security/CustomAccessDeniedHandler.java
````java
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
⋮----
public void handle(HttpServletRequest request,
⋮----
handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/security/CustomAuthenticationEntryPoint.java
````java
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
⋮----
public void commence(HttpServletRequest request,
⋮----
handlerExceptionResolver.resolveException(request, response, null, authException);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/security/JwtAuthenticationFilter.java
````java
public class JwtAuthenticationFilter extends OncePerRequestFilter {
⋮----
protected void doFilterInternal(@NonNull HttpServletRequest request,
⋮----
String jwt = extractJwtFromRequest(request);
⋮----
if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
String userId = jwtTokenProvider.getUserIdFromTokenAsString(jwt);
⋮----
// Extract permissions from JWT claims and convert to GrantedAuthority
List<String> permissions = jwtTokenProvider.getPermissionsFromToken(jwt);
List<SimpleGrantedAuthority> authorities = permissions.stream()
.map(SimpleGrantedAuthority::new)
.collect(Collectors.toList());
⋮----
new UsernamePasswordAuthenticationToken(
⋮----
authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
⋮----
SecurityContextHolder.getContext().setAuthentication(authentication);
⋮----
log.error("Cannot set user authentication: {}", e.getMessage());
⋮----
filterChain.doFilter(request, response);
⋮----
private String extractJwtFromRequest(HttpServletRequest request) {
String bearerToken = request.getHeader("Authorization");
if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
return bearerToken.substring(7);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/security/JwtTokenProvider.java
````java
public class JwtTokenProvider {
⋮----
public void init() {
if (jwtSecret == null || jwtSecret.isBlank()) {
jwtSecret = Base64.getEncoder().encodeToString(
"v8n-ecommerce-default-secret-key-must-be-changed-in-production-2024".getBytes(StandardCharsets.UTF_8)
⋮----
byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
⋮----
keyBytes = java.util.Arrays.copyOf(keyBytes, 32);
⋮----
this.key = Keys.hmacShaKeyFor(keyBytes);
⋮----
public String generateAccessToken(UUID userId, String email) {
Date now = new Date();
Date expiryDate = new Date(now.getTime() + accessTokenExpiration);
⋮----
return Jwts.builder()
.subject(userId.toString())
.claim("email", email)
.issuedAt(now)
.expiration(expiryDate)
.signWith(key)
.compact();
⋮----
/**
     * Generate access token cho admin user với actorType và permissions.
     */
public String generateAccessToken(String userId, String email, String actorType, Set<String> permissions) {
⋮----
.subject(userId)
⋮----
.claim("actor_type", actorType)
.claim("permissions", permissions != null ? new ArrayList<>(permissions) : Collections.emptyList())
⋮----
/**
     * Trích xuất permissions từ JWT claims.
     */
public List<String> getPermissionsFromToken(String token) {
Claims claims = Jwts.parser()
.verifyWith(key)
.build()
.parseSignedClaims(token)
.getPayload();
⋮----
List<String> permissions = (List<String>) claims.get("permissions", List.class);
return permissions != null ? permissions : Collections.emptyList();
⋮----
public String generateRefreshToken(UUID userId) {
⋮----
Date expiryDate = new Date(now.getTime() + refreshTokenExpiration);
⋮----
.claim("type", "refresh")
⋮----
public String generateRefreshToken(String userId) {
⋮----
public UUID getUserIdFromToken(String token) {
⋮----
return UUID.fromString(claims.getSubject());
⋮----
/**
     * Trả về userId dưới dạng String (dùng cho user_admins có TEXT UUID).
     */
public String getUserIdFromTokenAsString(String token) {
⋮----
return claims.getSubject();
⋮----
public boolean validateToken(String token) {
⋮----
Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
⋮----
log.error("Invalid JWT signature: {}", e.getMessage());
⋮----
log.error("Invalid JWT token: {}", e.getMessage());
⋮----
log.error("JWT token is expired: {}", e.getMessage());
⋮----
log.error("JWT token is unsupported: {}", e.getMessage());
⋮----
log.error("JWT claims string is empty: {}", e.getMessage());
⋮----
public long getAccessTokenExpiration() {
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/ActivateController.java
````java
public class ActivateController {
⋮----
public ResponseEntity<ApiResponse<ActivationResponse>> checkActivation(@PathVariable String token) {
ActivationResponse response = adminAuthService.checkActivation(token);
return ResponseEntity.ok(ApiResponse.success(response));
⋮----
public ResponseEntity<ApiResponse<Map<String, String>>> activate(
⋮----
String ipAddress = httpRequest.getRemoteAddr();
String userAgent = httpRequest.getHeader("User-Agent");
adminAuthService.activate(request, ipAddress, userAgent);
return ResponseEntity.ok(ApiResponse.success(
⋮----
Map.of("message", "Tài khoản đã được kích hoạt thành công. Vui lòng đăng nhập.")
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/AddressController.java
````java
public class AddressController {
⋮----
public ResponseEntity<ApiResponse<List<AddressResponse>>> getAddresses(Authentication authentication) {
UUID userId = getUserIdFromAuthentication(authentication);
List<AddressResponse> addresses = addressService.getAddressesByUserId(userId);
return ResponseEntity.ok(ApiResponse.success(addresses));
⋮----
public ResponseEntity<ApiResponse<AddressResponse>> getAddress(
⋮----
AddressResponse address = addressService.getAddressById(addressId, userId);
return ResponseEntity.ok(ApiResponse.success(address));
⋮----
public ResponseEntity<ApiResponse<AddressResponse>> createAddress(
⋮----
AddressResponse address = addressService.createAddress(userId, request);
return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(address));
⋮----
public ResponseEntity<ApiResponse<AddressResponse>> updateAddress(
⋮----
AddressResponse address = addressService.updateAddress(addressId, userId, request);
⋮----
public ResponseEntity<ApiResponse<Void>> deleteAddress(
⋮----
addressService.deleteAddress(addressId, userId);
return ResponseEntity.ok(ApiResponse.success(null));
⋮----
public ResponseEntity<ApiResponse<AddressResponse>> setDefaultShipping(
⋮----
AddressResponse address = addressService.setDefaultShipping(addressId, userId);
⋮----
public ResponseEntity<ApiResponse<AddressResponse>> setDefaultBilling(
⋮----
AddressResponse address = addressService.setDefaultBilling(addressId, userId);
⋮----
private UUID getUserIdFromAuthentication(Authentication authentication) {
return (UUID) authentication.getPrincipal();
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/AdminAuthController.java
````java
public class AdminAuthController {
⋮----
public ResponseEntity<ApiResponse<AdminAuthResponse>> login(
⋮----
String ipAddress = httpRequest.getRemoteAddr();
String userAgent = httpRequest.getHeader("User-Agent");
AdminAuthResponse response = adminAuthService.login(request, ipAddress, userAgent);
return ResponseEntity.ok(ApiResponse.success("Login successful", response));
⋮----
public ResponseEntity<ApiResponse<AdminAuthResponse>> getCurrentUser(Principal principal) {
AdminAuthResponse response = adminAuthService.getCurrentUser(principal.getName());
return ResponseEntity.ok(ApiResponse.success(response));
⋮----
public ResponseEntity<ApiResponse<AdminAuthResponse>> refreshToken(
⋮----
if (authHeader == null || !authHeader.startsWith("Bearer ")) {
return ResponseEntity.badRequest()
.body(ApiResponse.error("Invalid refresh token"));
⋮----
String refreshToken = authHeader.substring(7);
AdminAuthResponse response = adminAuthService.refreshToken(refreshToken);
return ResponseEntity.ok(ApiResponse.success("Token refreshed", response));
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/AuthController.java
````java
public class AuthController {
⋮----
public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
AuthResponse response = authService.register(request);
return ResponseEntity.status(HttpStatus.CREATED)
.body(ApiResponse.success("Registration successful", response));
⋮----
public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
AuthResponse response = authService.login(request);
return ResponseEntity.ok(ApiResponse.success("Login successful", response));
⋮----
public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(Principal principal) {
UserResponse response = authService.getCurrentUser(principal.getName());
return ResponseEntity.ok(ApiResponse.success(response));
⋮----
public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(
⋮----
if (authHeader == null || !authHeader.startsWith("Bearer ")) {
return ResponseEntity.badRequest()
.body(ApiResponse.error("Invalid refresh token"));
⋮----
String refreshToken = authHeader.substring(7);
AuthResponse response = authService.refreshToken(refreshToken);
return ResponseEntity.ok(ApiResponse.success("Token refreshed", response));
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/LoginHistoryController.java
````java
public class LoginHistoryController {
⋮----
public ResponseEntity<Map<String, Object>> list(
⋮----
if (status != null && !status.isBlank()) {
⋮----
loginStatus = LoginStatus.valueOf(status.toUpperCase());
⋮----
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Invalid status value: " + status);
⋮----
LocalDateTime fromDate = from != null ? LocalDateTime.parse(from) : null;
LocalDateTime toDate = to != null ? LocalDateTime.parse(to) : null;
⋮----
Page<LoginHistoryResponse> page = loginHistoryService.listLoginHistory(
⋮----
return ResponseEntity.ok(Map.of(
⋮----
"data", page.getContent(),
"count", page.getTotalElements(),
⋮----
public ResponseEntity<String> export(
⋮----
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Date range from and to are required");
⋮----
LocalDateTime fromDate = LocalDateTime.parse(from);
LocalDateTime toDate = LocalDateTime.parse(to);
⋮----
String csv = loginHistoryService.exportCsv(email, loginStatus, fromDate, toDate);
⋮----
return ResponseEntity.ok()
.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=login_history.csv")
.contentType(MediaType.TEXT_PLAIN)
.body(csv);
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/PermissionController.java
````java
public class PermissionController {
⋮----
public ResponseEntity<ApiResponse<Map<String, Object>>> list() {
List<PermissionResponse> permissions = permissionService.getGroupedPermissions();
return ResponseEntity.ok(ApiResponse.success(
Map.of("permissions", permissions)
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/ProfileController.java
````java
public class ProfileController {
⋮----
public ResponseEntity<ApiResponse<CustomerResponse>> getProfile(Authentication authentication) {
UUID userId = getUserIdFromAuthentication(authentication);
CustomerResponse customer = customerService.getCustomerByUserId(userId);
return ResponseEntity.ok(ApiResponse.success(customer));
⋮----
public ResponseEntity<ApiResponse<CustomerResponse>> updateProfile(
⋮----
CustomerResponse customer = customerService.updateProfile(userId, request);
⋮----
private UUID getUserIdFromAuthentication(Authentication authentication) {
return (UUID) authentication.getPrincipal();
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/RoleController.java
````java
public class RoleController {
⋮----
public ResponseEntity<ApiResponse<List<RoleResponse>>> list() {
List<RoleResponse> roles = roleService.listRoles();
return ResponseEntity.ok(ApiResponse.success(roles));
⋮----
public ResponseEntity<ApiResponse<RoleResponse>> create(@Valid @RequestBody CreateRoleRequest request) {
RoleResponse response = roleService.createRole(request);
return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
⋮----
public ResponseEntity<ApiResponse<RoleDetailResponse>> get(@PathVariable UUID id) {
RoleDetailResponse response = roleService.getRole(id);
return ResponseEntity.ok(ApiResponse.success(response));
⋮----
public ResponseEntity<ApiResponse<RoleResponse>> update(
⋮----
RoleResponse response = roleService.updateRole(id, request);
⋮----
public ResponseEntity<Void> delete(@PathVariable UUID id) {
roleService.deleteRole(id);
return ResponseEntity.noContent().build();
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/interfaces/rest/UserAdminController.java
````java
public class UserAdminController {
⋮----
public ResponseEntity<ApiResponse<List<AdminUserResponse>>> list(
⋮----
List<AdminUserResponse> users = userAdminService.listUsers(q, status);
return ResponseEntity.ok(ApiResponse.success(users));
⋮----
public ResponseEntity<ApiResponse<AdminUserResponse>> create(
⋮----
AdminUserResponse response = userAdminService.createUser(request, principal.getName());
return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
⋮----
public ResponseEntity<ApiResponse<AdminUserResponse>> get(@PathVariable String id) {
AdminUserResponse response = userAdminService.getUser(id);
return ResponseEntity.ok(ApiResponse.success(response));
⋮----
public ResponseEntity<ApiResponse<AdminUserResponse>> update(
⋮----
AdminUserResponse response = userAdminService.updateUser(id, request, principal.getName());
⋮----
public ResponseEntity<ApiResponse<AdminUserResponse>> updateRoles(
⋮----
AdminUserResponse response = userAdminService.updateRoles(id, request.getRoleIds(), principal.getName());
⋮----
public ResponseEntity<Void> delete(
⋮----
userAdminService.deactivateUser(id, principal.getName());
return ResponseEntity.noContent().build();
⋮----
public ResponseEntity<ApiResponse<UnlockResponse>> unlock(
⋮----
UnlockResponse response = userAdminService.unlockUser(id, principal.getName());
⋮----
public ResponseEntity<ApiResponse<AdminUserResponse>> updatePermissions(
⋮----
AdminUserResponse response = userAdminService.updatePermissions(
⋮----
request.getPermissions() != null ? request.getPermissions() : java.util.Collections.emptySet(),
principal.getName()
⋮----
public ResponseEntity<ApiResponse<AdminUserResponse>> resendActivation(
⋮----
AdminUserResponse response = userAdminService.resendActivation(id, principal.getName());
````

## File: modules/identity/src/test/java/com/v8n/modules/identity/application/service/AddressServiceTest.java
````java
class AddressServiceTest {
⋮----
void setUp() {
userId = UUID.randomUUID();
customerId = UUID.randomUUID();
addressId = UUID.randomUUID();
⋮----
mockUser = new User();
mockUser.setId(userId);
mockUser.setEmail("test@example.com");
⋮----
mockCustomer = new Customer();
mockCustomer.setId(customerId);
mockCustomer.setUser(mockUser);
mockCustomer.setEmail("test@example.com");
⋮----
mockAddress = new Address();
mockAddress.setId(addressId);
mockAddress.setCustomer(mockCustomer);
⋮----
void testDeleteAddress_softDelete_setsDeletedAt() {
when(userRepository.findByIdNotDeleted(userId)).thenReturn(Optional.of(mockUser));
when(customerRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockCustomer));
when(addressRepository.findByIdAndCustomerId(addressId, customerId)).thenReturn(Optional.of(mockAddress));
⋮----
assertNull(mockAddress.getDeletedAt());
⋮----
addressService.deleteAddress(addressId, userId);
⋮----
assertNotNull(mockAddress.getDeletedAt());
verify(addressRepository).save(mockAddress);
⋮----
void testDeleteAddress_addressNotFound_throwsException() {
⋮----
when(addressRepository.findByIdAndCustomerId(addressId, customerId)).thenReturn(Optional.empty());
⋮----
BusinessException exception = assertThrows(BusinessException.class, () ->
addressService.deleteAddress(addressId, userId)
⋮----
assertEquals(ErrorCode.RESOURCE_NOT_FOUND, exception.getErrorCode());
⋮----
void testCreateAddress_exceedsLimit_throwsException() {
⋮----
when(addressRepository.countByCustomerId(customerId)).thenReturn(10L); // MAX_ADDRESSES_PER_USER
⋮----
AddressRequest request = new AddressRequest();
⋮----
addressService.createAddress(userId, request)
⋮----
assertEquals(ErrorCode.INVALID_REQUEST, exception.getErrorCode());
assertTrue(exception.getMessage().contains("Maximum 10 addresses allowed"));
````

## File: modules/identity/src/test/java/com/v8n/modules/identity/application/service/CustomerServiceTest.java
````java
class CustomerServiceTest {
⋮----
void setUp() {
userId = UUID.randomUUID();
⋮----
mockUser = new User();
mockUser.setId(userId);
mockUser.setEmail("test@example.com");
⋮----
mockCustomer = new Customer();
mockCustomer.setId(UUID.randomUUID());
mockCustomer.setUser(mockUser);
mockCustomer.setEmail("test@example.com");
⋮----
void testGetCustomerByUserId_success() {
when(userRepository.findByIdNotDeleted(userId)).thenReturn(Optional.of(mockUser));
when(customerRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockCustomer));
⋮----
CustomerResponse response = customerService.getCustomerByUserId(userId);
⋮----
assertNotNull(response);
assertEquals(mockCustomer.getId(), response.getId());
assertEquals(userId, response.getUserId());
⋮----
void testGetCustomerByUserId_userNotFound() {
when(userRepository.findByIdNotDeleted(userId)).thenReturn(Optional.empty());
⋮----
BusinessException exception = assertThrows(BusinessException.class, () ->
customerService.getCustomerByUserId(userId)
⋮----
assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
⋮----
void testGetCustomerByUserId_customerNotFound() {
⋮----
when(customerRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.empty());
⋮----
assertEquals(ErrorCode.CUSTOMER_NOT_FOUND, exception.getErrorCode());
````

## File: modules/identity/build.gradle
````
dependencies {
    api project(':modules:core')
    implementation "io.jsonwebtoken:jjwt-api:${jjwtVersion}"
    runtimeOnly "io.jsonwebtoken:jjwt-impl:${jjwtVersion}"
    runtimeOnly "io.jsonwebtoken:jjwt-jackson:${jjwtVersion}"
}
````

## File: modules/inventory/src/main/java/com/v8n/modules/inventory/application/dto/InventoryItemRequest.java
````java
public class InventoryItemRequest {
````

## File: modules/inventory/src/main/java/com/v8n/modules/inventory/application/dto/InventoryItemResponse.java
````java
public class InventoryItemResponse {
````

## File: modules/inventory/src/main/java/com/v8n/modules/inventory/application/dto/ReservationRequest.java
````java
public class ReservationRequest {
````

## File: modules/inventory/src/main/java/com/v8n/modules/inventory/application/dto/ReservationResponse.java
````java
public class ReservationResponse {
````

## File: modules/inventory/src/main/java/com/v8n/modules/inventory/application/service/InventoryService.java
````java
public class InventoryService {
⋮----
// ===== Inventory Item Methods =====
⋮----
public InventoryItemResponse getInventoryItemById(UUID itemId) {
InventoryItem item = findInventoryItemById(itemId);
return toInventoryItemResponse(item);
⋮----
public InventoryItemResponse getInventoryItemBySku(String sku) {
InventoryItem item = inventoryItemRepository.findBySku(sku)
.orElseThrow(() -> new BusinessException(ErrorCode.INVENTORY_ITEM_NOT_FOUND));
⋮----
public InventoryItemResponse getInventoryItemByVariantId(UUID variantId) {
InventoryItem item = inventoryItemRepository.findByVariantId(variantId)
⋮----
public List<InventoryItemResponse> getAllInventoryItems() {
return inventoryItemRepository.findAll().stream()
.map(this::toInventoryItemResponse)
.collect(Collectors.toList());
⋮----
public InventoryItemResponse createInventoryItem(InventoryItemRequest request) {
InventoryItem item = new InventoryItem();
item.setSku(request.getSku());
item.setTitle(request.getTitle());
item.setRequiresShipping(request.isRequiresShipping());
item.setLocation(request.getLocation());
item.setOverselling(request.isOverselling());
item.setRestockThreshold(request.getRestockThreshold());
⋮----
if (request.getVariantId() != null) {
ProductVariant variant = variantRepository.findByIdNotDeleted(request.getVariantId())
.orElseThrow(() -> new BusinessException(ErrorCode.VARIANT_NOT_FOUND));
item.setVariant(variant);
⋮----
item = inventoryItemRepository.save(item);
log.info("Created inventory item: {}", item.getId());
⋮----
public InventoryItemResponse updateInventoryItem(UUID itemId, InventoryItemRequest request) {
⋮----
if (request.getSku() != null) item.setSku(request.getSku());
if (request.getTitle() != null) item.setTitle(request.getTitle());
if (request.getLocation() != null) item.setLocation(request.getLocation());
⋮----
if (request.getRestockThreshold() != null) item.setRestockThreshold(request.getRestockThreshold());
⋮----
log.info("Updated inventory item: {}", item.getId());
⋮----
public void adjustQuantity(UUID itemId, int delta, String reason) {
⋮----
item.setQuantity(item.getQuantity() + delta);
⋮----
log.info("Adjusted inventory item {} quantity by {} ({})", itemId, delta, reason);
⋮----
// ===== Reservation Methods =====
⋮----
public ReservationResponse reserveStock(ReservationRequest request) {
InventoryItem item = findInventoryItemById(request.getInventoryItemId());
⋮----
int availableQuantity = item.getAvailableQuantity();
if (!item.isOverselling() && availableQuantity < request.getQuantity()) {
throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK,
"Requested: " + request.getQuantity() + ", Available: " + availableQuantity);
⋮----
ReservationItem reservation = new ReservationItem();
reservation.setInventoryItem(item);
reservation.setLineItemId(request.getLineItemId());
reservation.setQuantity(request.getQuantity());
reservation.setExpiresAt(LocalDateTime.now().plusMinutes(DEFAULT_RESERVATION_MINUTES));
reservation.setStatus("RESERVED");
reservation.setActive(true);
⋮----
item.setReservedQuantity(item.getReservedQuantity() + request.getQuantity());
inventoryItemRepository.save(item);
⋮----
reservation = reservationItemRepository.save(reservation);
log.info("Reserved {} units for line item {}", request.getQuantity(), request.getLineItemId());
return toReservationResponse(reservation);
⋮----
public void releaseReservation(UUID reservationId) {
ReservationItem reservation = findReservationById(reservationId);
⋮----
if (!reservation.isActive()) {
log.warn("Reservation {} already released", reservationId);
⋮----
InventoryItem item = reservation.getInventoryItem();
item.setReservedQuantity(item.getReservedQuantity() - reservation.getQuantity());
⋮----
reservation.setActive(false);
reservationItemRepository.save(reservation);
log.info("Released reservation {}", reservationId);
⋮----
public void commitReservation(UUID reservationId) {
⋮----
throw new BusinessException(ErrorCode.RESERVATION_NOT_FOUND, "Reservation already released");
⋮----
item.setQuantity(item.getQuantity() - reservation.getQuantity());
⋮----
reservation.setStatus("COMMITTED");
⋮----
log.info("Committed reservation {}", reservationId);
⋮----
public void cancelReservation(UUID reservationId) {
⋮----
log.warn("Reservation {} already cancelled", reservationId);
⋮----
reservation.setStatus("CANCELLED");
⋮----
log.info("Cancelled reservation {}", reservationId);
⋮----
public void expireReservations() {
List<ReservationItem> expired = reservationItemRepository.findByStatusAndExpiresAtBefore(
"RESERVED", LocalDateTime.now());
⋮----
if (reservation.isActive()) {
⋮----
reservation.setStatus("EXPIRED");
⋮----
log.info("Expired {} reservations", expired.size());
⋮----
// ===== Convenience Methods for Order Integration =====
⋮----
public boolean checkAvailability(UUID variantId, int quantity) {
InventoryItem item = inventoryItemRepository.findByVariantId(variantId).orElse(null);
⋮----
if (item.isOverselling()) {
⋮----
return item.getAvailableQuantity() >= quantity;
⋮----
public void reserveInventory(UUID variantId, int quantity) {
⋮----
if (!item.isOverselling() && item.getAvailableQuantity() < quantity) {
⋮----
"Requested: " + quantity + ", Available: " + item.getAvailableQuantity());
⋮----
item.setReservedQuantity(item.getReservedQuantity() + quantity);
⋮----
log.info("Reserved {} units for variant {}", quantity, variantId);
⋮----
public void releaseReservation(UUID variantId, int quantity) {
⋮----
int toRelease = Math.min(quantity, item.getReservedQuantity());
item.setReservedQuantity(item.getReservedQuantity() - toRelease);
⋮----
log.info("Released {} units reservation for variant {}", toRelease, variantId);
⋮----
// ===== Private Helper Methods =====
⋮----
private InventoryItem findInventoryItemById(UUID itemId) {
return inventoryItemRepository.findByIdNotDeleted(itemId)
⋮----
private ReservationItem findReservationById(UUID reservationId) {
return reservationItemRepository.findByIdNotDeleted(reservationId)
.orElseThrow(() -> new BusinessException(ErrorCode.RESERVATION_NOT_FOUND));
⋮----
private InventoryItemResponse toInventoryItemResponse(InventoryItem item) {
return InventoryItemResponse.builder()
.id(item.getId())
.variantId(item.getVariant() != null ? item.getVariant().getId() : null)
.sku(item.getSku())
.title(item.getTitle())
.quantity(item.getQuantity())
.reservedQuantity(item.getReservedQuantity())
.availableQuantity(item.getAvailableQuantity())
.incomingQuantity(item.getIncomingQuantity())
.location(item.getLocation())
.requiresShipping(item.isRequiresShipping())
.overselling(item.isOverselling())
.restockThreshold(item.getRestockThreshold())
.createdAt(item.getCreatedAt())
.updatedAt(item.getUpdatedAt())
.build();
⋮----
private ReservationResponse toReservationResponse(ReservationItem reservation) {
return ReservationResponse.builder()
.id(reservation.getId())
.inventoryItemId(reservation.getInventoryItem().getId())
.lineItemId(reservation.getLineItemId())
.quantity(reservation.getQuantity())
.status(reservation.getStatus())
.expiresAt(reservation.getExpiresAt())
.isActive(reservation.isActive())
.createdAt(reservation.getCreatedAt())
````

## File: modules/inventory/src/main/java/com/v8n/modules/inventory/domain/entity/InventoryItem.java
````java
public class InventoryItem extends BaseEntity {
⋮----
public int getAvailableQuantity() {
````

## File: modules/inventory/src/main/java/com/v8n/modules/inventory/domain/entity/InventoryLevel.java
````java
public class InventoryLevel extends BaseEntity {
````

## File: modules/inventory/src/main/java/com/v8n/modules/inventory/domain/entity/ReservationItem.java
````java
public class ReservationItem extends BaseEntity {
⋮----
public boolean isExpired() {
return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
````

## File: modules/inventory/src/main/java/com/v8n/modules/inventory/domain/repository/InventoryItemRepository.java
````java
public interface InventoryItemRepository extends BaseRepository<InventoryItem, UUID> {
⋮----
Optional<InventoryItem> findBySku(String sku);
⋮----
boolean existsBySku(String sku);
⋮----
Optional<InventoryItem> findByVariantId(@Param("variantId") UUID variantId);
````

## File: modules/inventory/src/main/java/com/v8n/modules/inventory/domain/repository/InventoryLevelRepository.java
````java
public interface InventoryLevelRepository extends BaseRepository<InventoryLevel, UUID> {
⋮----
List<InventoryLevel> findByInventoryItemId(UUID inventoryItemId);
⋮----
List<InventoryLevel> findByLocationId(UUID locationId);
⋮----
List<InventoryLevel> findByInventoryItemIdAndLocationId(UUID inventoryItemId, UUID locationId);
````

## File: modules/inventory/src/main/java/com/v8n/modules/inventory/domain/repository/ReservationItemRepository.java
````java
public interface ReservationItemRepository extends BaseRepository<ReservationItem, UUID> {
⋮----
List<ReservationItem> findByInventoryItemId(UUID inventoryItemId);
⋮----
List<ReservationItem> findByLineItemId(UUID lineItemId);
⋮----
List<ReservationItem> findByStatusAndExpiresAtBefore(String status, LocalDateTime dateTime);
⋮----
List<ReservationItem> findByStatus(String status);
````

## File: modules/inventory/src/main/java/com/v8n/modules/inventory/interfaces/rest/AdminInventoryController.java
````java
public class AdminInventoryController {
⋮----
public ApiResponse<List<InventoryItemResponse>> getAllInventoryItems() {
return ApiResponse.success(inventoryService.getAllInventoryItems());
⋮----
public ApiResponse<InventoryItemResponse> getInventoryItemById(@PathVariable UUID id) {
return ApiResponse.success(inventoryService.getInventoryItemById(id));
⋮----
public ApiResponse<InventoryItemResponse> updateInventoryItem(@PathVariable UUID id, @Valid @RequestBody InventoryItemRequest request) {
return ApiResponse.success(inventoryService.updateInventoryItem(id, request));
````

## File: modules/inventory/build.gradle
````
dependencies {
    api project(':modules:core')
    api project(':modules:catalog')
}
````

## File: modules/notification/src/main/java/com/v8n/notification/application/dto/NotificationRequest.java
````java
public class NotificationRequest {
````

## File: modules/notification/src/main/java/com/v8n/notification/application/dto/NotificationResponse.java
````java
public class NotificationResponse {
````

## File: modules/notification/src/main/java/com/v8n/notification/application/service/NotificationService.java
````java
public class NotificationService {
⋮----
// ===== Notification CRUD Methods =====
⋮----
public NotificationResponse getNotificationById(UUID notificationId) {
Notification notification = findNotificationById(notificationId);
return toNotificationResponse(notification);
⋮----
public List<NotificationResponse> getNotificationsByRecipientId(UUID recipientId) {
return notificationRepository.findByRecipientId(recipientId).stream()
.map(this::toNotificationResponse)
.collect(Collectors.toList());
⋮----
public List<NotificationResponse> getUnreadNotifications(UUID recipientId) {
return notificationRepository.findByRecipientIdAndStatus(recipientId, NotificationStatus.SENT).stream()
⋮----
public NotificationResponse createNotification(NotificationRequest request) {
Notification notification = new Notification();
notification.setRecipientId(request.getRecipientId());
notification.setRecipientEmail(request.getRecipientEmail());
notification.setType(request.getType());
notification.setChannel(request.getChannel() != null ? request.getChannel() : Channel.EMAIL);
notification.setSubject(request.getSubject());
notification.setContent(request.getContent());
notification.setStatus(NotificationStatus.PENDING);
⋮----
notification = notificationRepository.save(notification);
log.info("Created notification: {} for recipient: {}", notification.getId(), request.getRecipientId());
⋮----
public NotificationResponse sendNotification(UUID notificationId) {
⋮----
if (!notification.isPending()) {
throw new BusinessException(ErrorCode.INTERNAL_ERROR, "Notification is not in PENDING status");
⋮----
sendViaChannel(notification);
notification.markAsSent();
log.info("Sent notification: {} via {}", notificationId, notification.getChannel());
⋮----
notification.markAsFailed(e.getMessage());
log.error("Failed to send notification: {}", notificationId, e);
⋮----
notificationRepository.save(notification);
⋮----
public NotificationResponse markAsRead(UUID notificationId) {
⋮----
if (notification.getStatus() != NotificationStatus.SENT) {
throw new BusinessException(ErrorCode.INTERNAL_ERROR, "Notification must be SENT before marking as READ");
⋮----
notification.markAsRead();
⋮----
log.info("Marked notification {} as read", notificationId);
⋮----
public void markAllAsRead(UUID recipientId) {
List<Notification> unread = notificationRepository.findByRecipientIdAndStatus(recipientId, NotificationStatus.SENT);
⋮----
log.info("Marked {} notifications as read for recipient {}", unread.size(), recipientId);
⋮----
public void deleteNotification(UUID notificationId) {
⋮----
notificationRepository.delete(notification);
log.info("Deleted notification: {}", notificationId);
⋮----
// ===== Order-related Notifications =====
⋮----
public NotificationResponse sendOrderConfirmation(UUID recipientId, String email, String orderDisplayId) {
⋮----
notification.setRecipientId(recipientId);
notification.setRecipientEmail(email);
notification.setType(NotificationType.ORDER_CONFIRMATION);
notification.setChannel(Channel.EMAIL);
notification.setSubject("Order Confirmation - #" + orderDisplayId);
notification.setContent("Thank you for your order #" + orderDisplayId + "! We have received your order and will process it shortly.");
⋮----
return sendNotification(notification.getId());
⋮----
public NotificationResponse sendOrderShipped(UUID recipientId, String email, String orderDisplayId, String trackingNumber) {
⋮----
notification.setType(NotificationType.ORDER_SHIPPED);
⋮----
notification.setSubject("Your Order #" + orderDisplayId + " Has Been Shipped!");
notification.setContent("Your order #" + orderDisplayId + " has been shipped. Tracking number: " + trackingNumber);
⋮----
public NotificationResponse sendOrderDelivered(UUID recipientId, String email, String orderDisplayId) {
⋮----
notification.setType(NotificationType.ORDER_DELIVERED);
⋮----
notification.setSubject("Your Order #" + orderDisplayId + " Has Been Delivered");
notification.setContent("Your order #" + orderDisplayId + " has been delivered. Thank you for shopping with us!");
⋮----
public NotificationResponse sendPaymentReceived(UUID recipientId, String email, String orderDisplayId, int amount) {
⋮----
notification.setType(NotificationType.PAYMENT_RECEIVED);
⋮----
notification.setSubject("Payment Received for Order #" + orderDisplayId);
notification.setContent("We have received your payment of " + (amount / 100.0) + " for order #" + orderDisplayId + ".");
⋮----
// ===== Private Helper Methods =====
⋮----
private Notification findNotificationById(UUID notificationId) {
return notificationRepository.findByIdNotDeleted(notificationId)
.orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_ERROR, "Notification not found"));
⋮----
private void sendViaChannel(Notification notification) {
switch (notification.getChannel()) {
⋮----
sendEmail(notification);
⋮----
sendSms(notification);
⋮----
sendPush(notification);
⋮----
private void sendEmail(Notification notification) {
log.info("Sending EMAIL to {}: {}", notification.getRecipientEmail(), notification.getSubject());
⋮----
private void sendSms(Notification notification) {
log.info("Sending SMS to recipient {}: {}", notification.getRecipientId(), notification.getContent());
⋮----
private void sendPush(Notification notification) {
log.info("Sending PUSH notification to recipient {}: {}", notification.getRecipientId(), notification.getSubject());
⋮----
private NotificationResponse toNotificationResponse(Notification notification) {
return NotificationResponse.builder()
.id(notification.getId())
.recipientId(notification.getRecipientId())
.recipientEmail(notification.getRecipientEmail())
.type(notification.getType())
.channel(notification.getChannel())
.subject(notification.getSubject())
.status(notification.getStatus())
.sentAt(notification.getSentAt())
.readAt(notification.getReadAt())
.createdAt(notification.getCreatedAt())
.updatedAt(notification.getUpdatedAt())
.build();
````

## File: modules/notification/src/main/java/com/v8n/notification/domain/entity/Notification.java
````java
public class Notification extends BaseEntity {
⋮----
public boolean isPending() {
⋮----
public boolean isSent() {
⋮----
public boolean isRead() {
⋮----
public boolean isFailed() {
⋮----
public void markAsSent() {
⋮----
this.sentAt = LocalDateTime.now();
⋮----
public void markAsRead() {
⋮----
this.readAt = LocalDateTime.now();
⋮----
public void markAsFailed(String errorMessage) {
⋮----
this.failedAt = LocalDateTime.now();
````

## File: modules/notification/src/main/java/com/v8n/notification/domain/repository/NotificationRepository.java
````java
public interface NotificationRepository extends BaseRepository<Notification, UUID> {
⋮----
List<Notification> findByRecipientId(@Param("recipientId") UUID recipientId);
⋮----
List<Notification> findByRecipientIdAndStatus(@Param("recipientId") UUID recipientId, @Param("status") NotificationStatus status);
⋮----
List<Notification> findByStatus(@Param("status") NotificationStatus status);
⋮----
List<Notification> findAllPending();
⋮----
List<Notification> findPendingByChannel(@Param("channel") com.v8n.notification.domain.entity.Notification.Channel channel);
⋮----
List<Notification> findPendingByType(@Param("type") NotificationType type);
````

## File: modules/notification/src/main/java/com/v8n/notification/interfaces/rest/NotificationController.java
````java
public class NotificationController {
⋮----
public ApiResponse<NotificationResponse> getNotificationById(@PathVariable UUID id) {
return ApiResponse.success(notificationService.getNotificationById(id));
⋮----
public ApiResponse<List<NotificationResponse>> getNotificationsByRecipientId(@PathVariable UUID recipientId) {
return ApiResponse.success(notificationService.getNotificationsByRecipientId(recipientId));
⋮----
public ApiResponse<NotificationResponse> markAsRead(@PathVariable UUID id) {
return ApiResponse.success(notificationService.markAsRead(id));
````

## File: modules/notification/build.gradle
````
dependencies {
    api project(':modules:core')
    implementation project(':modules:identity')
    implementation project(':modules:cart')
    implementation project(':modules:order')
    implementation project(':modules:payment')
    implementation project(':modules:fulfillment')
    implementation project(':modules:promotion')
}
````

## File: modules/order/src/main/java/com/v8n/modules/order/application/dto/CreateOrderRequest.java
````java
public class CreateOrderRequest {
````

## File: modules/order/src/main/java/com/v8n/modules/order/application/dto/OrderItemResponse.java
````java
public class OrderItemResponse {
````

## File: modules/order/src/main/java/com/v8n/modules/order/application/dto/OrderResponse.java
````java
public class OrderResponse {
````

## File: modules/order/src/main/java/com/v8n/modules/order/application/dto/OrderStatusHistoryResponse.java
````java
public class OrderStatusHistoryResponse {
````

## File: modules/order/src/main/java/com/v8n/modules/order/application/dto/UpdateOrderStatusRequest.java
````java
public class UpdateOrderStatusRequest {
````

## File: modules/order/src/main/java/com/v8n/modules/order/application/service/OrderMapper.java
````java
public class OrderMapper {
⋮----
public OrderResponse toResponse(Order order) {
⋮----
OrderResponse.OrderResponseBuilder builder = OrderResponse.builder()
.id(order.getId())
.displayId(order.getDisplayId())
.regionId(order.getRegion() != null ? order.getRegion().getId() : null)
.customerId(order.getCustomer() != null ? order.getCustomer().getId() : null)
.email(order.getEmail())
.currencyCode(order.getCurrencyCode())
.shippingAddressId(order.getShippingAddress() != null ? order.getShippingAddress().getId() : null)
.billingAddressId(order.getBillingAddress() != null ? order.getBillingAddress().getId() : null)
.status(order.getStatus() != null ? order.getStatus().name() : null)
.fulfillmentStatus(order.getFulfillmentStatus() != null ? order.getFulfillmentStatus().name() : null)
.paymentStatus(order.getPaymentStatus() != null ? order.getPaymentStatus().name() : null)
.subtotal(order.getSubtotal())
.discountTotal(order.getDiscountTotal())
.shippingTotal(order.getShippingTotal())
.taxTotal(order.getTaxTotal())
.total(order.getTotal())
.canceledAt(order.getCanceledAt())
.metadata(order.getMetadata())
.createdAt(order.getCreatedAt())
.updatedAt(order.getUpdatedAt());
⋮----
// Map items
if (order.getItems() != null) {
builder.items(order.getItems().stream()
.map(this::toOrderItemResponse)
.toList());
⋮----
// Map status history
if (order.getStatusHistory() != null) {
builder.statusHistory(order.getStatusHistory().stream()
.map(this::toStatusHistoryResponse)
⋮----
return builder.build();
⋮----
public OrderItemResponse toOrderItemResponse(OrderItem item) {
⋮----
return OrderItemResponse.builder()
.id(item.getId())
.variantId(item.getVariant() != null ? item.getVariant().getId() : null)
.title(item.getTitle())
.sku(item.getVariant() != null ? item.getVariant().getSku() : null)
.quantity(item.getQuantity())
.unitPrice(item.getUnitPrice())
.subtotal((int) item.getSubtotal())
.metadata(item.getMetadata())
.build();
⋮----
public OrderStatusHistoryResponse toStatusHistoryResponse(OrderStatusHistory history) {
⋮----
return OrderStatusHistoryResponse.builder()
.id(history.getId())
.fromStatus(history.getFromStatus() != null ? history.getFromStatus().name() : null)
.toStatus(history.getToStatus() != null ? history.getToStatus().name() : null)
.createdAt(history.getCreatedAt())
````

## File: modules/order/src/main/java/com/v8n/modules/order/application/service/OrderService.java
````java
public class OrderService {
⋮----
// ===== Query Methods =====
⋮----
public OrderResponse getOrderById(UUID orderId) {
Order order = orderRepository.findByIdNotDeleted(orderId)
.orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
return orderMapper.toResponse(order);
⋮----
public List<OrderResponse> getOrdersByCustomerId(UUID customerId) {
List<Order> orders = orderRepository.findByCustomerId(customerId);
return orders.stream()
.map(orderMapper::toResponse)
.collect(Collectors.toList());
⋮----
public List<OrderResponse> getOrdersByEmail(String email) {
List<Order> orders = orderRepository.findByEmail(email);
⋮----
public List<OrderResponse> getOrdersByStatus(OrderStatus status) {
List<Order> orders = orderRepository.findByStatus(status);
⋮----
public List<OrderResponse> getAllOrders() {
List<Order> orders = orderRepository.findAll();
⋮----
// ===== Mutation Methods =====
⋮----
/**
     * Create an order from a completed cart.
     * This is the main checkout flow:
     * 1. Validate the cart exists and is complete
     * 2. Validate stock for all line items
     * 3. Reserve inventory
     * 4. Create order with items from cart
     * 5. Mark cart as completed
     */
⋮----
public OrderResponse createOrderFromCart(CreateOrderRequest request) {
// 1. Fetch and validate the cart
Cart cart = cartRepository.findByIdNotDeleted(request.getCartId())
.orElseThrow(() -> new BusinessException(ErrorCode.CART_NOT_FOUND));
⋮----
if (cart.getCompletedAt() != null) {
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Cart is already completed");
⋮----
if (cart.getLineItems() == null || cart.getLineItems().isEmpty()) {
throw new BusinessException(ErrorCode.INVALID_REQUEST, "Cannot create order from empty cart");
⋮----
// 2. Validate stock for all items
for (LineItem lineItem : cart.getLineItems()) {
if (lineItem.getVariant() != null) {
boolean hasStock = inventoryService.checkAvailability(
lineItem.getVariant().getId(), lineItem.getQuantity());
⋮----
throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK,
"Insufficient stock for variant: " + lineItem.getVariant().getId());
⋮----
// 3. Reserve inventory
⋮----
inventoryService.reserveInventory(
⋮----
// 4. Create the order
Order order = new Order();
order.setDisplayId(sequenceGeneratorService.generateOrderDisplayId());
order.setRegion(cart.getRegion());
order.setCustomer(cart.getCustomer());
order.setEmail(cart.getEmail());
order.setCurrencyCode(cart.getCurrencyCode());
order.setShippingAddress(cart.getShippingAddress());
order.setBillingAddress(cart.getBillingAddress());
order.setStatus(OrderStatus.PENDING);
⋮----
// Copy line items from cart to order
⋮----
OrderItem orderItem = new OrderItem();
orderItem.setOrder(order);
orderItem.setVariant(lineItem.getVariant());
orderItem.setTitle(lineItem.getTitle());
orderItem.setSku(lineItem.getVariant() != null ? lineItem.getVariant().getSku() : null);
orderItem.setQuantity(lineItem.getQuantity());
orderItem.setUnitPrice(lineItem.getUnitPrice()); // cents
orderItem.setMetadata(lineItem.getMetadata() != null
? lineItem.getMetadata() : new java.util.HashMap<>());
order.addItem(orderItem);
⋮----
// Recalculate and set totals from cart
order.setSubtotal((int) cart.getSubtotal());
order.setDiscountTotal(0);
order.setShippingTotal(0);
order.setTaxTotal(0);
order.setTotal((int) cart.getSubtotal());
⋮----
// Add initial status history
OrderStatusHistory history = new OrderStatusHistory();
history.setOrder(order);
history.setFromStatus(null);
history.setToStatus(OrderStatus.PENDING);
order.getStatusHistory().add(history);
⋮----
// 5. Mark cart as completed
cart.setCompletedAt(LocalDateTime.now());
cartRepository.save(cart);
⋮----
// Save order
Order savedOrder = orderRepository.save(order);
log.info("Order created: id={}, displayId={}, customerId={}",
savedOrder.getId(), savedOrder.getDisplayId(),
savedOrder.getCustomer() != null ? savedOrder.getCustomer().getId() : "guest");
⋮----
return orderMapper.toResponse(savedOrder);
⋮----
public OrderResponse updateOrderStatus(UUID orderId, UpdateOrderStatusRequest request) {
⋮----
OrderStatus targetStatus = OrderStatus.valueOf(request.getStatus());
⋮----
order.transitionStatus(targetStatus);
⋮----
throw new BusinessException(ErrorCode.ORDER_INVALID_STATUS, e.getMessage());
⋮----
log.info("Order status updated: id={}, from={}, to={}",
orderId, order.getStatus(), targetStatus);
⋮----
public OrderResponse cancelOrder(UUID orderId) {
⋮----
order.cancel();
⋮----
throw new BusinessException(ErrorCode.ORDER_CANNOT_CANCEL, e.getMessage());
⋮----
// Release inventory reservations
for (OrderItem item : order.getItems()) {
if (item.getVariant() != null) {
inventoryService.releaseReservation(
item.getVariant().getId(), item.getQuantity());
⋮----
log.info("Order cancelled: id={}, displayId={}", savedOrder.getId(), savedOrder.getDisplayId());
⋮----
public void deleteOrder(UUID orderId) {
⋮----
order.setDeletedAt(LocalDateTime.now());
orderRepository.save(order);
log.info("Order soft-deleted: id={}", orderId);
````

## File: modules/order/src/main/java/com/v8n/modules/order/application/service/SequenceGeneratorService.java
````java
public class SequenceGeneratorService {
⋮----
public Long generateOrderDisplayId() {
Long maxId = orderRepository.findMaxDisplayId();
````

## File: modules/order/src/main/java/com/v8n/modules/order/domain/entity/FulfillmentStatus.java
````java

````

## File: modules/order/src/main/java/com/v8n/modules/order/domain/entity/Order.java
````java
public class Order extends BaseEntity {
⋮----
private int subtotal = 0; // Price in cents (DB stores as INTEGER)
⋮----
private int discountTotal = 0; // Price in cents
⋮----
private int shippingTotal = 0; // Price in cents
⋮----
private int taxTotal = 0; // Price in cents
⋮----
private int total = 0; // Price in cents
⋮----
// ──────────────────────────────────────────────
// Domain Business Methods
⋮----
/**
     * Add an item to the order and recalculate totals.
     */
public void addItem(OrderItem item) {
item.setOrder(this);
items.add(item);
recalculateTotals();
⋮----
/**
     * Remove an item from the order and recalculate totals.
     */
public void removeItem(OrderItem item) {
items.remove(item);
item.setOrder(null);
⋮----
/**
     * Recalculate subtotal = sum of item subtotals, then total = subtotal + tax + shipping - discount.
     */
public void recalculateTotals() {
⋮----
sumSubtotal += item.getSubtotal();
⋮----
/**
     * Valid order status transitions.
     */
private static final Set<OrderStatus> CANCELLABLE_STATUSES = Set.of(
⋮----
/**
     * Cancel the order if it's in a cancellable state.
     *
     * @throws IllegalStateException if order cannot be cancelled
     */
public void cancel() {
⋮----
throw new IllegalStateException("Order is already cancelled");
⋮----
throw new IllegalStateException("Cannot cancel order in status: " + status);
⋮----
this.canceledAt = LocalDateTime.now();
⋮----
/**
     * Transition to a new status with audit trail.
     */
public void transitionStatus(OrderStatus newStatus) {
⋮----
// Basic state machine validation
⋮----
throw new IllegalStateException("Cannot transition from CANCELLED status");
⋮----
throw new IllegalStateException("Delivered order can only transition to RETURNED");
⋮----
// Add audit trail
OrderStatusHistory history = new OrderStatusHistory();
history.setOrder(this);
history.setFromStatus(fromStatus);
history.setToStatus(newStatus);
this.statusHistory.add(history);
````

## File: modules/order/src/main/java/com/v8n/modules/order/domain/entity/OrderItem.java
````java
public class OrderItem extends BaseEntity {
⋮----
private int unitPrice = 0; // Price in cents (DB stores as INTEGER)
⋮----
/**
     * Get subtotal in cents (unitPrice * quantity).
     */
public long getSubtotal() {
⋮----
/**
     * Get total in cents (unitPrice * quantity) — same as subtotal without tax/discount.
     */
public long getTotal() {
return getSubtotal();
⋮----
/**
     * Update quantity. Validates minimum value.
     */
public void updateQuantity(int newQuantity) {
⋮----
throw new IllegalArgumentException("Quantity must be at least 1, got: " + newQuantity);
⋮----
public boolean equals(Object o) {
⋮----
if (o == null || getClass() != o.getClass()) return false;
if (getId() == null) return false;
⋮----
return Objects.equals(getId(), that.getId());
⋮----
public int hashCode() {
return getId() != null ? Objects.hash(getId()) : super.hashCode();
````

## File: modules/order/src/main/java/com/v8n/modules/order/domain/entity/OrderStatus.java
````java

````

## File: modules/order/src/main/java/com/v8n/modules/order/domain/entity/OrderStatusConverter.java
````java
/**
 * JPA AttributeConverter that maps OrderStatus enum to lowercase strings for DB storage.
 * DB stores status values as lowercase (e.g., 'pending', 'cancelled') while JPA
 * enum name() is UPPERCASE.
 */
⋮----
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {
⋮----
public String convertToDatabaseColumn(OrderStatus status) {
⋮----
return status.name().toLowerCase();
⋮----
public OrderStatus convertToEntityAttribute(String dbData) {
⋮----
return OrderStatus.valueOf(dbData.toUpperCase());
````

## File: modules/order/src/main/java/com/v8n/modules/order/domain/entity/OrderStatusHistory.java
````java
public class OrderStatusHistory extends BaseEntity {
````

## File: modules/order/src/main/java/com/v8n/modules/order/domain/entity/PaymentStatus.java
````java

````

## File: modules/order/src/main/java/com/v8n/modules/order/domain/event/OrderCanceledEvent.java
````java
public class OrderCanceledEvent extends DomainEvent {
⋮----
this.orderId = order.getId();
this.customerId = order.getCustomer() != null ? order.getCustomer().getId() : null;
````

## File: modules/order/src/main/java/com/v8n/modules/order/domain/event/OrderPlacedEvent.java
````java
public class OrderPlacedEvent extends DomainEvent {
⋮----
this.orderId = order.getId();
this.customerId = order.getCustomer() != null ? order.getCustomer().getId() : null;
this.email = order.getEmail();
this.total = order.getTotal();
````

## File: modules/order/src/main/java/com/v8n/modules/order/domain/event/OrderStatusChangedEvent.java
````java
public class OrderStatusChangedEvent extends DomainEvent {
⋮----
this.orderId = order.getId();
⋮----
this.newStatus = order.getStatus();
````

## File: modules/order/src/main/java/com/v8n/modules/order/domain/repository/OrderItemRepository.java
````java
public interface OrderItemRepository extends BaseRepository<OrderItem, UUID> {
⋮----
List<OrderItem> findByOrderId(@Param("orderId") UUID orderId);
⋮----
List<OrderItem> findByVariantId(@Param("variantId") UUID variantId);
````

## File: modules/order/src/main/java/com/v8n/modules/order/domain/repository/OrderRepository.java
````java
public interface OrderRepository extends BaseRepository<Order, UUID> {
⋮----
Optional<Order> findByDisplayId(Long displayId);
⋮----
List<Order> findByCustomerId(@Param("customerId") UUID customerId);
⋮----
List<Order> findByEmail(@Param("email") String email);
⋮----
List<Order> findByStatus(@Param("status") OrderStatus status);
⋮----
List<Order> findByPaymentStatus(@Param("paymentStatus") PaymentStatus paymentStatus);
⋮----
Long findMaxDisplayId();
````

## File: modules/order/src/main/java/com/v8n/modules/order/domain/repository/OrderStatusHistoryRepository.java
````java
public interface OrderStatusHistoryRepository extends BaseRepository<OrderStatusHistory, UUID> {
⋮----
List<OrderStatusHistory> findByOrderIdOrderByCreatedAtDesc(UUID orderId);
````

## File: modules/order/src/main/java/com/v8n/modules/order/interfaces/rest/OrderController.java
````java
public class OrderController {
⋮----
// ===== Query Endpoints =====
⋮----
public ResponseEntity<ApiResponse<OrderResponse>> getOrder(@PathVariable UUID orderId) {
OrderResponse response = orderService.getOrderById(orderId);
return ResponseEntity.ok(ApiResponse.success(response));
⋮----
public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
List<OrderResponse> responses = orderService.getAllOrders();
return ResponseEntity.ok(ApiResponse.success(responses));
⋮----
public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByCustomer(
⋮----
List<OrderResponse> responses = orderService.getOrdersByCustomerId(customerId);
⋮----
public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByEmail(
⋮----
List<OrderResponse> responses = orderService.getOrdersByEmail(email);
⋮----
public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByStatus(
⋮----
List<OrderResponse> responses = orderService.getOrdersByStatus(status);
⋮----
// ===== Mutation Endpoints =====
⋮----
public ResponseEntity<ApiResponse<OrderResponse>> createOrderFromCart(
⋮----
OrderResponse response = orderService.createOrderFromCart(request);
return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
⋮----
public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
⋮----
OrderResponse response = orderService.updateOrderStatus(orderId, request);
⋮----
public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(@PathVariable UUID orderId) {
OrderResponse response = orderService.cancelOrder(orderId);
⋮----
public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable UUID orderId) {
orderService.deleteOrder(orderId);
return ResponseEntity.noContent().build();
````

## File: modules/order/src/test/java/com/v8n/modules/order/domain/entity/OrderEntityTest.java
````java
class OrderEntityTest {
⋮----
void setUp() {
Region region = new Region();
region.setId(UUID.randomUUID());
⋮----
order = new Order();
order.setId(UUID.randomUUID());
order.setRegion(region);
order.setCurrencyCode("VND");
⋮----
item1 = new OrderItem();
item1.setId(UUID.randomUUID());
item1.setUnitPrice(100000);
item1.setQuantity(2); // subtotal = 200,000
⋮----
item2 = new OrderItem();
item2.setId(UUID.randomUUID());
item2.setUnitPrice(50000);
item2.setQuantity(1); // subtotal = 50,000
⋮----
void testAddItem_recalculatesTotals() {
order.addItem(item1);
⋮----
assertEquals(1, order.getItems().size());
assertEquals(200000, order.getSubtotal());
assertEquals(200000, order.getTotal());
⋮----
order.setShippingTotal(15000);
order.addItem(item2);
⋮----
assertEquals(2, order.getItems().size());
assertEquals(250000, order.getSubtotal());
assertEquals(265000, order.getTotal()); // 250k + 15k shipping
⋮----
void testCancelOrder_cancellableStatus_success() {
order.setStatus(OrderStatus.PENDING);
order.cancel();
⋮----
assertEquals(OrderStatus.CANCELLED, order.getStatus());
assertNotNull(order.getCanceledAt());
⋮----
void testCancelOrder_nonCancellable_throwsException() {
order.setStatus(OrderStatus.SHIPPED);
⋮----
IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
order.cancel()
⋮----
assertTrue(exception.getMessage().contains("Cannot cancel order"));
⋮----
void testStatusTransition_validTransitions() {
assertEquals(OrderStatus.PENDING, order.getStatus());
assertEquals(0, order.getStatusHistory().size());
⋮----
order.transitionStatus(OrderStatus.CONFIRMED);
assertEquals(OrderStatus.CONFIRMED, order.getStatus());
assertEquals(1, order.getStatusHistory().size());
assertEquals(OrderStatus.PENDING, order.getStatusHistory().get(0).getFromStatus());
assertEquals(OrderStatus.CONFIRMED, order.getStatusHistory().get(0).getToStatus());
⋮----
order.transitionStatus(OrderStatus.PROCESSING);
assertEquals(OrderStatus.PROCESSING, order.getStatus());
assertEquals(2, order.getStatusHistory().size());
⋮----
void testStatusTransition_invalidTransitions_throwsException() {
order.setStatus(OrderStatus.CANCELLED);
⋮----
order.transitionStatus(OrderStatus.CONFIRMED)
⋮----
assertTrue(exception.getMessage().contains("Cannot transition from CANCELLED"));
````

## File: modules/order/build.gradle
````
dependencies {
    api project(':modules:core')
    implementation project(':modules:catalog')
    implementation project(':modules:identity')
    implementation project(':modules:inventory')
    implementation project(':modules:cart')
}
````

## File: modules/payment/src/main/java/com/v8n/payment/application/dto/PaymentCollectionResponse.java
````java
public class PaymentCollectionResponse {
````

## File: modules/payment/src/main/java/com/v8n/payment/application/dto/PaymentSessionRequest.java
````java
public class PaymentSessionRequest {
````

## File: modules/payment/src/main/java/com/v8n/payment/application/dto/PaymentSessionResponse.java
````java
public class PaymentSessionResponse {
````

## File: modules/payment/src/main/java/com/v8n/payment/application/dto/RefundRequest.java
````java
public class RefundRequest {
````

## File: modules/payment/src/main/java/com/v8n/payment/application/dto/RefundResponse.java
````java
public class RefundResponse {
````

## File: modules/payment/src/main/java/com/v8n/payment/application/service/PaymentService.java
````java
public class PaymentService {
⋮----
// ===== Payment Collection Methods =====
⋮----
public PaymentCollectionResponse getCollectionById(UUID collectionId) {
PaymentCollection collection = findCollectionById(collectionId);
return toCollectionResponse(collection);
⋮----
public PaymentCollectionResponse getCollectionByOrderId(UUID orderId) {
PaymentCollection collection = paymentCollectionRepository.findByOrderId(orderId)
.orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_FAILED, "Payment collection not found for order"));
⋮----
public PaymentCollectionResponse createCollectionForOrder(UUID orderId) {
Order order = orderRepository.findByIdNotDeleted(orderId)
.orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
⋮----
paymentCollectionRepository.findByOrderIdAndStatus(orderId, PaymentCollectionStatus.PENDING)
.ifPresent(existing -> {
throw new BusinessException(ErrorCode.PAYMENT_FAILED, "Pending payment collection already exists for this order");
⋮----
PaymentCollection collection = new PaymentCollection();
collection.setOrder(order);
collection.setCurrencyCode(order.getCurrencyCode());
collection.setAmount(order.getTotal());
collection.setStatus(PaymentCollectionStatus.PENDING);
⋮----
collection = paymentCollectionRepository.save(collection);
log.info("Created payment collection {} for order {}", collection.getId(), orderId);
⋮----
// ===== Payment Session Methods =====
⋮----
public PaymentSessionResponse getSessionById(UUID sessionId) {
PaymentSession session = findSessionById(sessionId);
return toSessionResponse(session);
⋮----
public PaymentSessionResponse createSession(UUID collectionId, PaymentSessionRequest request) {
⋮----
PaymentSession session = new PaymentSession();
session.setPaymentCollection(collection);
session.setProvider(request.getProvider());
session.setAmount(request.getAmount() > 0 ? request.getAmount() : collection.getAmount());
session.setCurrencyCode(collection.getCurrencyCode());
session.setStatus(PaymentSessionStatus.PENDING);
⋮----
LocalDateTime expiresAt = request.getExpiresAtMinutes() > 0
? LocalDateTime.now().plusMinutes(request.getExpiresAtMinutes())
: LocalDateTime.now().plusMinutes(30);
session.setExpiresAt(expiresAt);
⋮----
session = paymentSessionRepository.save(session);
collection.addSession(session);
paymentCollectionRepository.save(collection);
⋮----
log.info("Created payment session {} for collection {}", session.getId(), collectionId);
⋮----
public PaymentSessionResponse confirmSession(UUID sessionId, String providerSessionId) {
⋮----
if (session.isExpired()) {
session.setStatus(PaymentSessionStatus.EXPIRED);
paymentSessionRepository.save(session);
throw new BusinessException(ErrorCode.PAYMENT_FAILED, "Payment session has expired");
⋮----
session.setProviderSessionId(providerSessionId);
session.setStatus(PaymentSessionStatus.AUTHORIZED);
session.setConfirmedAt(LocalDateTime.now());
⋮----
PaymentCollection collection = session.getPaymentCollection();
collection.setAuthorizedAmount(collection.getAuthorizedAmount() + session.getAmount());
if (collection.getAuthorizedAmount() >= collection.getAmount()) {
collection.setStatus(PaymentCollectionStatus.AUTHORIZED);
⋮----
log.info("Confirmed payment session {} with provider session {}", sessionId, providerSessionId);
⋮----
public PaymentSessionResponse cancelSession(UUID sessionId) {
⋮----
if (session.getStatus() != PaymentSessionStatus.PENDING && session.getStatus() != PaymentSessionStatus.REQUIRES_ACTION) {
throw new BusinessException(ErrorCode.PAYMENT_FAILED, "Cannot cancel session in status: " + session.getStatus());
⋮----
session.setStatus(PaymentSessionStatus.CANCELLED);
session.setCanceledAt(LocalDateTime.now());
⋮----
log.info("Cancelled payment session {}", sessionId);
⋮----
// ===== Payment Capture Methods =====
⋮----
public PaymentSessionResponse capturePayment(UUID sessionId) {
⋮----
if (session.getStatus() != PaymentSessionStatus.AUTHORIZED) {
throw new BusinessException(ErrorCode.PAYMENT_NOT_AUTHORIZED, "Session must be authorized before capture");
⋮----
Payment payment = new Payment();
payment.setPaymentCollection(session.getPaymentCollection());
payment.setPaymentSession(session);
payment.setAmount(session.getAmount());
payment.setCurrencyCode(session.getCurrencyCode());
payment.setStatus(PaymentStatus.CAPTURED);
payment.setCapturedAt(LocalDateTime.now());
paymentRepository.save(payment);
⋮----
session.setStatus(PaymentSessionStatus.CAPTURED);
⋮----
collection.setCapturedAmount(collection.getCapturedAmount() + payment.getAmount());
if (collection.getCapturedAmount() >= collection.getAmount()) {
collection.setStatus(PaymentCollectionStatus.CAPTURED);
⋮----
log.info("Captured payment {} from session {}", payment.getId(), sessionId);
⋮----
// ===== Refund Methods =====
⋮----
public RefundResponse getRefundById(UUID refundId) {
Refund refund = findRefundById(refundId);
return toRefundResponse(refund);
⋮----
public RefundResponse createRefund(UUID collectionId, RefundRequest request) {
⋮----
if (collection.getCapturedAmount() < request.getAmount()) {
throw new BusinessException(ErrorCode.PAYMENT_FAILED, "Refund amount exceeds captured amount");
⋮----
Refund refund = new Refund();
refund.setPaymentCollection(collection);
refund.setAmount(request.getAmount());
refund.setCurrencyCode(collection.getCurrencyCode());
refund.setReason(request.getReason());
refund.setStatus(RefundStatus.PENDING);
⋮----
collection.addRefund(refund);
⋮----
refund = refundRepository.save(refund);
⋮----
log.info("Created refund {} for collection {}", refund.getId(), collectionId);
⋮----
public RefundResponse processRefund(UUID refundId, String providerRefundId) {
⋮----
if (refund.getStatus() != RefundStatus.PENDING) {
throw new BusinessException(ErrorCode.PAYMENT_FAILED, "Refund must be in PENDING status to process");
⋮----
refund.setStatus(RefundStatus.PROCESSED);
refund.setProviderRefundId(providerRefundId);
refund.setProcessedAt(LocalDateTime.now());
⋮----
PaymentCollection collection = refund.getPaymentCollection();
collection.setRefundedAmount(collection.getRefundedAmount() + refund.getAmount());
if (collection.getRefundedAmount() >= collection.getCapturedAmount()) {
collection.setStatus(PaymentCollectionStatus.REFUNDED);
} else if (collection.getRefundedAmount() > 0) {
collection.setStatus(PaymentCollectionStatus.PARTIALLY_REFUNDED);
⋮----
refundRepository.save(refund);
⋮----
log.info("Processed refund {} with provider refund {}", refundId, providerRefundId);
⋮----
// ===== Private Helper Methods =====
⋮----
private PaymentCollection findCollectionById(UUID id) {
return paymentCollectionRepository.findByIdNotDeleted(id)
.orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_FAILED, "Payment collection not found"));
⋮----
private PaymentSession findSessionById(UUID id) {
return paymentSessionRepository.findByIdNotDeleted(id)
.orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_SESSION_NOT_FOUND));
⋮----
private Refund findRefundById(UUID id) {
return refundRepository.findByIdNotDeleted(id)
.orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_FAILED, "Refund not found"));
⋮----
private PaymentCollectionResponse toCollectionResponse(PaymentCollection collection) {
return PaymentCollectionResponse.builder()
.id(collection.getId())
.orderId(collection.getOrder().getId())
.currencyCode(collection.getCurrencyCode())
.status(collection.getStatus())
.amount(collection.getAmount())
.authorizedAmount(collection.getAuthorizedAmount())
.capturedAmount(collection.getCapturedAmount())
.refundedAmount(collection.getRefundedAmount())
.createdAt(collection.getCreatedAt())
.updatedAt(collection.getUpdatedAt())
.build();
⋮----
private PaymentSessionResponse toSessionResponse(PaymentSession session) {
return PaymentSessionResponse.builder()
.id(session.getId())
.collectionId(session.getPaymentCollection().getId())
.provider(session.getProvider())
.providerSessionId(session.getProviderSessionId())
.status(session.getStatus())
.amount(session.getAmount())
.currencyCode(session.getCurrencyCode())
.expiresAt(session.getExpiresAt())
.confirmedAt(session.getConfirmedAt())
.createdAt(session.getCreatedAt())
.updatedAt(session.getUpdatedAt())
⋮----
private RefundResponse toRefundResponse(Refund refund) {
return RefundResponse.builder()
.id(refund.getId())
.collectionId(refund.getPaymentCollection().getId())
.amount(refund.getAmount())
.currencyCode(refund.getCurrencyCode())
.status(refund.getStatus())
.reason(refund.getReason())
.providerRefundId(refund.getProviderRefundId())
.processedAt(refund.getProcessedAt())
.createdAt(refund.getCreatedAt())
.updatedAt(refund.getUpdatedAt())
````

## File: modules/payment/src/main/java/com/v8n/payment/domain/entity/Payment.java
````java
public class Payment extends BaseEntity {
⋮----
public boolean isCaptured() {
⋮----
public boolean isRefundable() {
````

## File: modules/payment/src/main/java/com/v8n/payment/domain/entity/PaymentCollection.java
````java
public class PaymentCollection extends BaseEntity {
⋮----
public void addSession(PaymentSession session) {
sessions.add(session);
session.setPaymentCollection(this);
⋮----
public void addRefund(Refund refund) {
refunds.add(refund);
refund.setPaymentCollection(this);
⋮----
public int getAuthorizedAmount() {
⋮----
public int getCapturedAmount() {
⋮----
public int getRefundedAmount() {
````

## File: modules/payment/src/main/java/com/v8n/payment/domain/entity/PaymentSession.java
````java
public class PaymentSession extends BaseEntity {
⋮----
public boolean isExpired() {
return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
⋮----
public boolean isPending() {
````

## File: modules/payment/src/main/java/com/v8n/payment/domain/entity/Refund.java
````java
public class Refund extends BaseEntity {
⋮----
public boolean isPending() {
⋮----
public boolean isProcessed() {
````

## File: modules/payment/src/main/java/com/v8n/payment/domain/repository/PaymentCollectionRepository.java
````java
public interface PaymentCollectionRepository extends BaseRepository<PaymentCollection, UUID> {
⋮----
Optional<PaymentCollection> findByOrderId(@Param("orderId") UUID orderId);
⋮----
List<PaymentCollection> findByStatus(@Param("status") PaymentCollectionStatus status);
⋮----
Optional<PaymentCollection> findByOrderIdAndStatus(@Param("orderId") UUID orderId, @Param("status") PaymentCollectionStatus status);
````

## File: modules/payment/src/main/java/com/v8n/payment/domain/repository/PaymentRepository.java
````java
public interface PaymentRepository extends BaseRepository<Payment, UUID> {
⋮----
List<Payment> findByPaymentCollectionId(@Param("collectionId") UUID collectionId);
⋮----
List<Payment> findByStatus(@Param("status") PaymentStatus status);
⋮----
Optional<Payment> findByProviderTransactionId(@Param("transactionId") String providerTransactionId);
````

## File: modules/payment/src/main/java/com/v8n/payment/domain/repository/PaymentSessionRepository.java
````java
public interface PaymentSessionRepository extends BaseRepository<PaymentSession, UUID> {
⋮----
List<PaymentSession> findByPaymentCollectionId(@Param("collectionId") UUID collectionId);
⋮----
List<PaymentSession> findByStatus(@Param("status") PaymentSessionStatus status);
⋮----
List<PaymentSession> findByStatusInAndExpiresAtBefore(@Param("statuses") List<PaymentSessionStatus> statuses, @Param("now") LocalDateTime now);
⋮----
Optional<PaymentSession> findByProviderSessionId(@Param("providerSessionId") String providerSessionId);
````

## File: modules/payment/src/main/java/com/v8n/payment/domain/repository/RefundRepository.java
````java
public interface RefundRepository extends BaseRepository<Refund, UUID> {
⋮----
List<Refund> findByPaymentCollectionId(@Param("collectionId") UUID collectionId);
⋮----
List<Refund> findByStatus(@Param("status") RefundStatus status);
⋮----
List<Refund> findByProviderRefundId(@Param("providerRefundId") String providerRefundId);
````

## File: modules/payment/src/main/java/com/v8n/payment/interfaces/rest/PaymentController.java
````java
public class PaymentController {
⋮----
public ApiResponse<String> handleWebhook(@RequestBody String payload) {
// In a real implementation, this would validate the provider signature
// and call paymentService.confirmSession() or paymentService.capturePayment()
return ApiResponse.success("Webhook received");
⋮----
public ApiResponse<PaymentSessionResponse> capturePayment(@PathVariable UUID sessionId) {
return ApiResponse.success(paymentService.capturePayment(sessionId));
⋮----
public ApiResponse<RefundResponse> createRefund(
⋮----
return ApiResponse.success(paymentService.createRefund(collectionId, request));
````

## File: modules/payment/build.gradle
````
dependencies {
    api project(':modules:core')
    implementation project(':modules:order')
}
````

## File: modules/promotion/src/main/java/com/v8n/promotion/application/dto/DiscountRequest.java
````java
public class DiscountRequest {
````

## File: modules/promotion/src/main/java/com/v8n/promotion/application/dto/DiscountResponse.java
````java
public class DiscountResponse {
````

## File: modules/promotion/src/main/java/com/v8n/promotion/application/dto/DiscountValidationRequest.java
````java
public class DiscountValidationRequest {
````

## File: modules/promotion/src/main/java/com/v8n/promotion/application/service/PromotionService.java
````java
public class PromotionService {
⋮----
// ===== Discount CRUD Methods =====
⋮----
public DiscountResponse getDiscountById(UUID discountId) {
Discount discount = findDiscountById(discountId);
return toDiscountResponse(discount);
⋮----
public DiscountResponse getDiscountByCode(String code) {
Discount discount = discountRepository.findByCodeNotDeleted(code)
.orElseThrow(() -> new BusinessException(ErrorCode.DISCOUNT_NOT_FOUND));
⋮----
public List<DiscountResponse> getAllDiscounts() {
return discountRepository.findAll().stream()
.map(this::toDiscountResponse)
.collect(Collectors.toList());
⋮----
public List<DiscountResponse> getActiveDiscounts() {
return discountRepository.findAllActive().stream()
⋮----
public DiscountResponse createDiscount(DiscountRequest request) {
if (request.getCode() != null && discountRepository.findByCodeNotDeleted(request.getCode()).isPresent()) {
throw new BusinessException(ErrorCode.DISCOUNT_INVALID, "Discount code already exists");
⋮----
Discount discount = new Discount();
discount.setCode(request.getCode());
discount.setType(request.getType() != null ? request.getType() : DiscountType.PERCENTAGE);
discount.setValue(request.getValue());
discount.setCurrencyCode(request.getCurrencyCode());
discount.setMinRequirementAmount(request.getMinRequirementAmount());
discount.setMaxDiscountAmount(request.getMaxDiscountAmount());
discount.setStartsAt(request.getStartsAt());
discount.setEndsAt(request.getEndsAt());
discount.setUsageLimit(request.getUsageLimit());
discount.setActive(request.isActive());
discount.setPublic(request.isPublic());
discount.setDescription(request.getDescription());
⋮----
discount = discountRepository.save(discount);
log.info("Created discount: {} with code: {}", discount.getId(), discount.getCode());
⋮----
public DiscountResponse updateDiscount(UUID discountId, DiscountRequest request) {
⋮----
if (request.getCode() != null && !request.getCode().equals(discount.getCode())) {
discountRepository.findByCodeNotDeleted(request.getCode()).ifPresent(existing -> {
⋮----
if (request.getType() != null) discount.setType(request.getType());
if (request.getValue() > 0) discount.setValue(request.getValue());
if (request.getCurrencyCode() != null) discount.setCurrencyCode(request.getCurrencyCode());
if (request.getMinRequirementAmount() > 0) discount.setMinRequirementAmount(request.getMinRequirementAmount());
if (request.getMaxDiscountAmount() > 0) discount.setMaxDiscountAmount(request.getMaxDiscountAmount());
if (request.getStartsAt() != null) discount.setStartsAt(request.getStartsAt());
if (request.getEndsAt() != null) discount.setEndsAt(request.getEndsAt());
if (request.getUsageLimit() != null) discount.setUsageLimit(request.getUsageLimit());
⋮----
if (request.getDescription() != null) discount.setDescription(request.getDescription());
⋮----
log.info("Updated discount: {}", discountId);
⋮----
public void deleteDiscount(UUID discountId) {
⋮----
discountRepository.delete(discount);
log.info("Deleted discount: {}", discountId);
⋮----
public void deactivateDiscount(UUID discountId) {
⋮----
discount.setActive(false);
discountRepository.save(discount);
log.info("Deactivated discount: {}", discountId);
⋮----
// ===== Discount Validation & Calculation =====
⋮----
public DiscountResponse validateDiscount(DiscountValidationRequest request) {
String code = request.getCode();
int cartSubtotal = request.getSubtotal();
⋮----
Discount discount = discountRepository.findValidByCode(code, LocalDateTime.now())
⋮----
if (!discount.isValid()) {
if (discount.isExpired()) {
throw new BusinessException(ErrorCode.DISCOUNT_EXPIRED);
⋮----
if (discount.getUsageLimit() != null && discount.getUsageCount() >= discount.getUsageLimit()) {
throw new BusinessException(ErrorCode.DISCOUNT_USAGE_LIMIT_REACHED);
⋮----
throw new BusinessException(ErrorCode.DISCOUNT_INVALID);
⋮----
if (discount.getMinRequirementAmount() > 0 && cartSubtotal < discount.getMinRequirementAmount()) {
throw new BusinessException(ErrorCode.DISCOUNT_INVALID,
"Minimum subtotal of " + discount.getMinRequirementAmount() + " required");
⋮----
public int calculateDiscount(String code, int subtotal) {
⋮----
switch (discount.getType()) {
⋮----
discountAmount = (int) ((long) subtotal * discount.getValue() / 100);
⋮----
discountAmount = discount.getValue();
⋮----
if (discount.getMaxDiscountAmount() > 0 && discountAmount > discount.getMaxDiscountAmount()) {
discountAmount = discount.getMaxDiscountAmount();
⋮----
public void incrementUsage(UUID discountId) {
⋮----
discount.incrementUsage();
⋮----
// ===== Private Helper Methods =====
⋮----
private Discount findDiscountById(UUID discountId) {
return discountRepository.findByIdNotDeleted(discountId)
⋮----
private DiscountResponse toDiscountResponse(Discount discount) {
return DiscountResponse.builder()
.id(discount.getId())
.code(discount.getCode())
.type(discount.getType())
.value(discount.getValue())
.currencyCode(discount.getCurrencyCode())
.minRequirementAmount(discount.getMinRequirementAmount())
.maxDiscountAmount(discount.getMaxDiscountAmount())
.startsAt(discount.getStartsAt())
.endsAt(discount.getEndsAt())
.usageLimit(discount.getUsageLimit())
.usageCount(discount.getUsageCount())
.isActive(discount.isActive())
.isPublic(discount.isPublic())
.description(discount.getDescription())
.createdAt(discount.getCreatedAt())
.updatedAt(discount.getUpdatedAt())
.build();
````

## File: modules/promotion/src/main/java/com/v8n/promotion/domain/entity/Discount.java
````java
public class Discount extends BaseEntity {
⋮----
public boolean isValid() {
⋮----
LocalDateTime now = LocalDateTime.now();
if (startsAt != null && now.isBefore(startsAt)) return false;
if (endsAt != null && now.isAfter(endsAt)) return false;
⋮----
public boolean isExpired() {
return endsAt != null && LocalDateTime.now().isAfter(endsAt);
⋮----
public void incrementUsage() {
⋮----
public void addRule(DiscountRule rule) {
rules.add(rule);
rule.setDiscount(this);
⋮----
public void addCondition(DiscountCondition condition) {
conditions.add(condition);
condition.setDiscount(this);
````

## File: modules/promotion/src/main/java/com/v8n/promotion/domain/entity/DiscountCondition.java
````java
public class DiscountCondition extends BaseEntity {
````

## File: modules/promotion/src/main/java/com/v8n/promotion/domain/entity/DiscountRule.java
````java
public class DiscountRule extends BaseEntity {
````

## File: modules/promotion/src/main/java/com/v8n/promotion/domain/repository/DiscountRepository.java
````java
public interface DiscountRepository extends BaseRepository<Discount, UUID> {
⋮----
Optional<Discount> findByCode(String code);
⋮----
Optional<Discount> findByCodeNotDeleted(@Param("code") String code);
⋮----
List<Discount> findAllActive();
⋮----
List<Discount> findAllValid(@Param("now") LocalDateTime now);
⋮----
Optional<Discount> findValidByCode(@Param("code") String code, @Param("now") LocalDateTime now);
````

## File: modules/promotion/src/main/java/com/v8n/promotion/interfaces/rest/AdminDiscountController.java
````java
public class AdminDiscountController {
⋮----
public ApiResponse<List<DiscountResponse>> getAllDiscounts() {
return ApiResponse.success(promotionService.getAllDiscounts());
⋮----
public ApiResponse<DiscountResponse> createDiscount(@Valid @RequestBody DiscountRequest request) {
return ApiResponse.success(promotionService.createDiscount(request));
⋮----
public ApiResponse<DiscountResponse> updateDiscount(@PathVariable UUID id, @Valid @RequestBody DiscountRequest request) {
return ApiResponse.success(promotionService.updateDiscount(id, request));
⋮----
public ApiResponse<Void> deleteDiscount(@PathVariable UUID id) {
promotionService.deleteDiscount(id);
return ApiResponse.success(null);
````

## File: modules/promotion/build.gradle
````
dependencies {
    api project(':modules:core')
    implementation project(':modules:cart')
}
````

## File: src/main/java/com/v8n/V8nEcommerceApplication.java
````java
public class V8nEcommerceApplication {
⋮----
public static void main(String[] args) {
SpringApplication.run(V8nEcommerceApplication.class, args);
````

## File: src/main/resources/db/migration/V10__Create_Login_History.sql
````sql
-- Migration: V10__Create_Login_History.sql
-- Tạo bảng login_history + 4 indexes
CREATE TABLE public.login_history (
    id              TEXT DEFAULT (gen_random_uuid())::text NOT NULL,
    user_admin_id   TEXT REFERENCES user_admins(id) ON DELETE SET NULL,
    email           VARCHAR(255) NOT NULL,
    status          VARCHAR(20) NOT NULL,
    failure_reason  VARCHAR(50),
    ip_address      INET,
    user_agent      TEXT,
    attempted_at    TIMESTAMPTZ DEFAULT now() NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX idx_login_history_user ON login_history(user_admin_id);
CREATE INDEX idx_login_history_email ON login_history(email);
CREATE INDEX idx_login_history_attempted ON login_history(attempted_at);
CREATE INDEX idx_login_history_status ON login_history(status);
````

## File: src/main/resources/db/migration/V2__add_thumbnail_to_cart_items.sql
````sql
ALTER TABLE cart_items ADD COLUMN IF NOT EXISTS thumbnail VARCHAR(500);
````

## File: src/main/resources/db/migration/V3__add_address_fk_to_carts_and_orders.sql
````sql
-- ALTER TABLE carts ADD COLUMN IF NOT EXISTS shipping_address_id text;
-- ALTER TABLE carts ADD CONSTRAINT IF NOT EXISTS fk_carts_shipping_address FOREIGN KEY (shipping_address_id) REFERENCES customer_addresses(id);

-- ALTER TABLE carts ADD COLUMN IF NOT EXISTS billing_address_id text;
-- ALTER TABLE carts ADD CONSTRAINT IF NOT EXISTS fk_carts_billing_address FOREIGN KEY (billing_address_id) REFERENCES customer_addresses(id);

-- ALTER TABLE orders ADD COLUMN IF NOT EXISTS shipping_address_id text;
-- ALTER TABLE orders ADD CONSTRAINT IF NOT EXISTS fk_orders_shipping_address FOREIGN KEY (shipping_address_id) REFERENCES customer_addresses(id);

-- ALTER TABLE orders ADD COLUMN IF NOT EXISTS billing_address_id text;
-- ALTER TABLE orders ADD CONSTRAINT IF NOT EXISTS fk_orders_billing_address FOREIGN KEY (billing_address_id) REFERENCES customer_addresses(id);
````

## File: src/main/resources/db/migration/V4__Create_Permission_Table.sql
````sql
-- Migration: V4__Create_Permission_Table.sql
-- Tạo bảng permission + seed 25 permissions
CREATE TABLE permission (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(100) NOT NULL UNIQUE,
    resource VARCHAR(50) NOT NULL,
    operation VARCHAR(20) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_permission_resource_operation UNIQUE (resource, operation)
);

CREATE INDEX idx_permission_resource ON permission(resource);

INSERT INTO permission (code, resource, operation, description) VALUES
('product:read', 'product', 'read', 'Xem sản phẩm'),
('product:create', 'product', 'create', 'Tạo sản phẩm mới'),
('product:update', 'product', 'update', 'Cập nhật sản phẩm'),
('product:delete', 'product', 'delete', 'Xóa sản phẩm'),
('order:read', 'order', 'read', 'Xem đơn hàng'),
('order:update', 'order', 'update', 'Cập nhật trạng thái đơn hàng'),
('user:read', 'user', 'read', 'Xem danh sách admin user'),
('user:create', 'user', 'create', 'Tạo admin user mới'),
('user:update', 'user', 'update', 'Cập nhật admin user'),
('user:delete', 'user', 'delete', 'Vô hiệu hóa admin user'),
('user:unlock', 'user', 'unlock', 'Mở khóa tài khoản admin'),
('inventory:read', 'inventory', 'read', 'Xem tồn kho'),
('inventory:update', 'inventory', 'update', 'Cập nhật tồn kho'),
('promotion:read', 'promotion', 'read', 'Xem khuyến mãi'),
('promotion:create', 'promotion', 'create', 'Tạo khuyến mãi'),
('promotion:update', 'promotion', 'update', 'Cập nhật khuyến mãi'),
('promotion:delete', 'promotion', 'delete', 'Xóa khuyến mãi'),
('setting:read', 'setting', 'read', 'Xem cấu hình hệ thống'),
('setting:update', 'setting', 'update', 'Cập nhật cấu hình'),
('login_history:read', 'login_history', 'read', 'Xem lịch sử đăng nhập'),
('login_history:export', 'login_history', 'export', 'Xuất báo cáo lịch sử đăng nhập'),
('role:read', 'role', 'read', 'Xem danh sách role'),
('role:create', 'role', 'create', 'Tạo role mới'),
('role:update', 'role', 'update', 'Cập nhật role'),
('role:delete', 'role', 'delete', 'Xóa role');
````

## File: src/main/resources/db/migration/V5__Create_Role_Table.sql
````sql
-- Migration: V5__Create_Role_Table.sql
-- Tạo bảng role + seed 6 roles mặc định
CREATE TABLE role (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    is_system BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);

CREATE INDEX idx_role_name ON role(name);

INSERT INTO role (id, name, description, is_system) VALUES
('00000000-0000-0000-0000-000000000001', 'Super Admin', 'Quản trị viên tối cao, toàn quyền hệ thống', TRUE),
('00000000-0000-0000-0000-000000000002', 'Catalog Manager', 'Quản lý sản phẩm và danh mục', TRUE),
('00000000-0000-0000-0000-000000000003', 'Order Manager', 'Quản lý và xử lý đơn hàng', TRUE),
('00000000-0000-0000-0000-000000000004', 'Inventory Manager', 'Quản lý kho hàng và tồn kho', TRUE),
('00000000-0000-0000-0000-000000000005', 'Marketing Manager', 'Quản lý khuyến mãi và giá', TRUE),
('00000000-0000-0000-0000-000000000006', 'Viewer', 'Chỉ xem, không có quyền sửa', TRUE);
````

## File: src/main/resources/db/migration/V6__Create_Role_Permission.sql
````sql
-- Migration: V6__Create_Role_Permission.sql
-- Tạo bảng role_permission + seed Super Admin (tất cả 25 permissions) + 5 role còn lại
CREATE TABLE role_permission (
    role_id UUID NOT NULL REFERENCES role(id) ON DELETE CASCADE,
    permission_id UUID NOT NULL REFERENCES permission(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (role_id, permission_id)
);

CREATE INDEX idx_role_permission_role ON role_permission(role_id);
CREATE INDEX idx_role_permission_perm ON role_permission(permission_id);

-- Super Admin: tất cả 25 permissions
INSERT INTO role_permission (role_id, permission_id)
SELECT '00000000-0000-0000-0000-000000000001', id FROM permission;

-- Catalog Manager: product:*
INSERT INTO role_permission (role_id, permission_id)
SELECT '00000000-0000-0000-0000-000000000002', id FROM permission WHERE code IN ('product:read', 'product:create', 'product:update', 'product:delete');

-- Order Manager: order:read, order:update
INSERT INTO role_permission (role_id, permission_id)
SELECT '00000000-0000-0000-0000-000000000003', id FROM permission WHERE code IN ('order:read', 'order:update');

-- Inventory Manager: inventory:read, inventory:update
INSERT INTO role_permission (role_id, permission_id)
SELECT '00000000-0000-0000-0000-000000000004', id FROM permission WHERE code IN ('inventory:read', 'inventory:update');

-- Marketing Manager: promotion:*
INSERT INTO role_permission (role_id, permission_id)
SELECT '00000000-0000-0000-0000-000000000005', id FROM permission WHERE code IN ('promotion:read', 'promotion:create', 'promotion:update', 'promotion:delete');

-- Viewer: chỉ đọc
INSERT INTO role_permission (role_id, permission_id)
SELECT '00000000-0000-0000-0000-000000000006', id FROM permission WHERE code IN ('product:read', 'order:read', 'inventory:read');
````

## File: src/main/resources/db/migration/V7__Create_User_Admins_Table.sql
````sql
-- Migration: V7__Create_User_Admins_Table.sql
-- Tạo bảng user_admins (19 cột) + soft unique index + seed Super Admin mặc định
CREATE TABLE public.user_admins (
    id                  TEXT DEFAULT (gen_random_uuid())::text NOT NULL,
    email               VARCHAR(255) NOT NULL,
    first_name          VARCHAR(100),
    last_name           VARCHAR(100),
    password_hash       TEXT,
    activation_token    UUID DEFAULT gen_random_uuid() NOT NULL,
    password_set_at     TIMESTAMPTZ,
    avatar_url          VARCHAR(500),
    phone               VARCHAR(50),
    is_active           BOOLEAN NOT NULL DEFAULT TRUE,
    last_login_at       TIMESTAMPTZ,
    failed_login_attempts INTEGER NOT NULL DEFAULT 0,
    locked_until        TIMESTAMPTZ,
    metadata            JSONB DEFAULT '{}'::jsonb NOT NULL,
    created_at          TIMESTAMPTZ DEFAULT now() NOT NULL,
    updated_at          TIMESTAMPTZ DEFAULT now() NOT NULL,
    deleted_at          TIMESTAMPTZ,
    PRIMARY KEY (id)
);

-- Soft unique: chỉ unique khi user còn hoạt động
CREATE UNIQUE INDEX uq_user_admins_email_active
    ON user_admins(email) WHERE is_active = TRUE;

CREATE INDEX idx_user_admins_email ON user_admins(email);
CREATE INDEX idx_user_admins_is_active ON user_admins(is_active);

-- Seed Super Admin mặc định
-- email: admin@v8n.com, password: Admin@123 (BCrypt hash)
INSERT INTO user_admins (id, email, first_name, last_name, password_hash, activation_token, password_set_at, is_active, failed_login_attempts)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'admin@v8n.com',
    'Super',
    'Admin',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    '00000000-0000-0000-0000-000000000001',
    NOW(),
    TRUE,
    0
);
````

## File: src/main/resources/db/migration/V8__Create_User_Admin_Roles.sql
````sql
-- Migration: V8__Create_User_Admin_Roles.sql
-- Tạo bảng user_admin_roles + seed Super Admin → role Super Admin
CREATE TABLE public.user_admin_roles (
    user_admin_id   TEXT NOT NULL REFERENCES user_admins(id) ON DELETE CASCADE,
    role_id         UUID NOT NULL REFERENCES role(id) ON DELETE CASCADE,
    assigned_at     TIMESTAMPTZ DEFAULT now() NOT NULL,
    assigned_by     TEXT REFERENCES user_admins(id),
    PRIMARY KEY (user_admin_id, role_id)
);

CREATE INDEX idx_user_admin_role_user ON user_admin_roles(user_admin_id);
CREATE INDEX idx_user_admin_role_role ON user_admin_roles(role_id);

-- Seed Super Admin user → Super Admin role
INSERT INTO user_admin_roles (user_admin_id, role_id)
VALUES ('00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
````

## File: src/main/resources/db/migration/V9__Create_User_Admin_Permissions.sql
````sql
-- Migration: V9__Create_User_Admin_Permissions.sql
-- Tạo bảng user_admin_permissions (user-level permission override)
CREATE TABLE public.user_admin_permissions (
    user_admin_id   TEXT NOT NULL REFERENCES user_admins(id) ON DELETE CASCADE,
    permission_id   UUID NOT NULL REFERENCES permission(id) ON DELETE CASCADE,
    assigned_at     TIMESTAMPTZ DEFAULT now() NOT NULL,
    assigned_by     TEXT REFERENCES user_admins(id),
    PRIMARY KEY (user_admin_id, permission_id)
);

CREATE INDEX idx_ua_perm_user ON user_admin_permissions(user_admin_id);
````

## File: src/main/resources/application.yml
````yaml
spring:
  application:
    name: v8n-ecommerce

  mvc:
    throw-exception-if-no-handler-found: true

  security:
    user:
      name: user_seeder
      password: '##123456'

  datasource:
    url: jdbc:postgresql://localhost:5432/v8n_db
    driver-class-name: org.postgresql.Driver
    username: v8n_ecom
    password: '#123456@'

  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect
        highlight_sql: true

  flyway:
    enabled: true
    baseline-on-migrate: true
    locations: classpath:db/migration

  data:
    redis:
      host: localhost
      port: 6379
      password:

  mail:
    host: smtp.gmail.com
    port: 587
    username: ${MAIL_USERNAME:}
    password: ${MAIL_PASSWORD:}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true

server:
  port: 8080

jwt:
  secret: ${JWT_SECRET:eW91ci0yNTYtYml0LXNlY3JldC1rZXktZm9yLWRldmVsb3BtZW50LW9ubHktY2hhbmdlLWluLXByb2Q=}
  expiration: 86400000

cors:
  allowed-origins: http://localhost:3000
  allowed-methods: GET,POST,PUT,DELETE,OPTIONS
  allowed-headers: "*"
  allow-credentials: true

springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html

logging:
  file:
    name: logs/application.log # Ghi log vào thư mục 'logs', tên file là 'application.log'
  level:
    org.hibernate.SQL: DEBUG # In ra các câu lệnh SQL
    org.hibernate.orm.jdbc.bind: TRACE # Cực kỳ quan trọng: In ra các giá trị của tham số (parameters) truyền vào câu SQL
````

## File: buildAndRun.sh
````bash
#!/bin/bash
echo "Đang tiến hành build project (bỏ qua bước test)..."
./gradlew clean build -x test

if [ $? -eq 0 ]; then
    echo "✅ Build thành công!"
    JAR_FILE=$(find build/libs -name "*.jar" ! -name "*plain.jar" | head -n 1)
    
    if [ -n "$JAR_FILE" ]; then
        echo "🚀 Đang chạy ứng dụng từ file: $JAR_FILE"
        java -jar "$JAR_FILE" &
        PID=$!
        echo "📌 Đã lưu PID ứng dụng: $PID"
        
        # Bắt sự kiện Ctrl+C (SIGINT) hoặc kill (SIGTERM)
        cleanup() {
            echo ""
            echo "🛑 Đã nhận tín hiệu dừng! Đang kill PID: $PID..."
            kill -9 $PID 2>/dev/null
            echo "✅ Đã dừng ứng dụng."
            exit 0
        }
        
        trap cleanup SIGINT SIGTERM
        
        # Đợi tiến trình hoàn thành
        wait $PID
    else
        echo "❌ Lỗi: Không tìm thấy file JAR nào trong thư mục build/libs/."
    fi
else
    echo "❌ Build thất bại! Vui lòng kiểm tra lại code."
    exit 1
fi
````

## File: buildProject.sh
````bash
#!/bin/bash
echo "Đang tiến hành build project (bỏ qua bước test)..."
./gradlew clean build -x test

if [ $? -eq 0 ]; then
    echo "✅ Build thành công! File jar nằm ở thư mục build/libs/"
else
    echo "❌ Build thất bại! Vui lòng kiểm tra lại code."
fi
````

## File: README.md
````markdown
# v8n-ecommerce Backend

This is the backend service for the v8n-ecommerce platform, built using Java and Spring Boot. The architecture is modular, separating different domains of the e-commerce system into dedicated modules.

## Modules

The project is structured into several core modules to handle different business capabilities:

- **Identity**: Manages user authentication, authorization, roles, and permissions (Admin/Customer).
- **Inventory**: Handles product inventory, stock levels, and item reservations.
- **Order**: Manages order creation, processing, fulfillment, and status tracking.
- **Payment**: Integrates payment processing, payment sessions, collections, and refunds.
- **Promotion**: Manages discount rules, conditions, and promotional campaigns.
- **Notification**: Handles system notifications and alerts.

## Technology Stack

- **Framework**: Java & Spring Boot
- **Build Tool**: Gradle
- **Database**: PostgreSQL (Flyway for database migrations)
- **Architecture**: Modular Monolith

## Prerequisites

- Java 17+
- Gradle
- PostgreSQL Database

## Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/huandev19/ecommerce-backend.git
   cd ecommerce-backend
   ```

2. **Configure Database**:
   Update the database connection settings in `src/main/resources/application.yml`.

3. **Build the project**:
   ```bash
   ./gradlew build
   ```

4. **Run the application**:
   You can run the application using the provided script or gradle:
   ```bash
   ./bootRun.sh
   # or
   ./gradlew bootRun
   ```

## API Documentation
*(To be updated with Swagger/OpenAPI details)*

## Deployment
For deployment instructions, please refer to the `AAPANEL_DEPLOY_GUIDE.md` included in the source code.
````

## File: runBuiltProject.sh
````bash
#!/bin/bash
JAR_FILE=$(find build/libs -name "*.jar" ! -name "*plain.jar" | head -n 1)

if [ -n "$JAR_FILE" ]; then
    echo "🚀 Đang chạy ứng dụng từ file: $JAR_FILE"
    java -jar "$JAR_FILE"
else
    echo "❌ Lỗi: Không tìm thấy file JAR nào trong thư mục build/libs/."
    echo "💡 Vui lòng chạy script ./buildProject.sh trước để đóng gói project!"
fi
````

## File: settings.gradle
````
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
}

rootProject.name = 'v8n-ecommerce'

include 'modules:core'
include 'modules:identity'
include 'modules:catalog'
include 'modules:inventory'
include 'modules:cart'
include 'modules:order'
include 'modules:payment'
include 'modules:fulfillment'
include 'modules:promotion'
include 'modules:notification'
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/domain/entity/LoginHistory.java
````java
public class LoginHistory {
⋮----
private LocalDateTime attemptedAt = LocalDateTime.now();
````

## File: modules/identity/src/main/java/com/v8n/modules/identity/infrastructure/security/SecurityConfig.java
````java
public class SecurityConfig {
⋮----
public PasswordEncoder passwordEncoder() {
return new BCryptPasswordEncoder();
⋮----
public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
⋮----
MatchableHandlerMapping mapping = introspector.getMatchableHandlerMapping(request);
⋮----
HandlerExecutionChain chain = mapping.getHandler(request);
if (chain != null && chain.getHandler() instanceof HandlerMethod handlerMethod) {
return handlerMethod.hasMethodAnnotation(PublicEndpoint.class);
⋮----
// Ignore exception and fall through
⋮----
.cors(cors -> cors.configurationSource(corsConfigurationSource()))
.csrf(csrf -> csrf.disable())
.sessionManagement(session ->
session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
.exceptionHandling(exception -> exception
.authenticationEntryPoint(customAuthenticationEntryPoint)
.accessDeniedHandler(customAccessDeniedHandler)
⋮----
.authorizeHttpRequests(auth -> auth
.requestMatchers("/api/v1/auth/**").permitAll()
.requestMatchers("/api/v1/auth/admin/**", "/api/v1/auth/activate/**").permitAll()
.requestMatchers("/api/v1/public/**").permitAll()
.requestMatchers("/actuator/health").permitAll()
.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
.requestMatchers(publicEndpointMatcher).permitAll()
.anyRequest().authenticated()
⋮----
.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
⋮----
return http.build();
⋮----
public CorsConfigurationSource corsConfigurationSource() {
CorsConfiguration configuration = new CorsConfiguration();
configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001"));
configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
configuration.setAllowCredentials(true);
configuration.setMaxAge(3600L);
⋮----
UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
source.registerCorsConfiguration("/**", configuration);
````

## File: src/main/resources/db/migration/V1__baseline.sql
````sql
--
-- PostgreSQL database dump
--
-- Dumped from database version 18.4 (Homebrew)
-- Dumped by pg_dump version 18.4 (Homebrew)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
-- SET transaction_timeout = 0;  -- Removed: not supported on PostgreSQL < 17
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: api_keys; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.api_keys (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    title character varying(255) NOT NULL,
    token_hash text NOT NULL,
    type character varying(50) DEFAULT 'secret'::character varying NOT NULL,
    created_by text,
    last_used_at timestamp with time zone,
    revoked_at timestamp with time zone,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: application_methods; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.application_methods (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    promotion_id text NOT NULL,
    type character varying(50) DEFAULT 'fixed'::character varying NOT NULL,
    target_type character varying(50) DEFAULT 'order'::character varying NOT NULL,
    allocation character varying(50) DEFAULT 'total'::character varying NOT NULL,
    value integer DEFAULT 0 NOT NULL,
    max_quantity integer,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: audit_logs; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.audit_logs (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    entity_type character varying(100) NOT NULL,
    entity_id text NOT NULL,
    action character varying(50) NOT NULL,
    changes jsonb DEFAULT '{}'::jsonb NOT NULL,
    performed_by text,
    performer_type character varying(50) DEFAULT 'system'::character varying NOT NULL,
    ip_address inet,
    user_agent text,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: auth_identities; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.auth_identities (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    app_metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    user_metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: campaigns; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.campaigns (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    promotion_id text,
    name character varying(255) NOT NULL,
    description text,
    starts_at timestamp with time zone,
    ends_at timestamp with time zone,
    budget jsonb DEFAULT '{}'::jsonb NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: captures; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.captures (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    payment_id text NOT NULL,
    amount integer NOT NULL,
    created_by text,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: cart_adjustments; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.cart_adjustments (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    cart_item_id text NOT NULL,
    code character varying(100),
    amount integer DEFAULT 0 NOT NULL,
    description text,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: cart_items; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.cart_items (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    cart_id text NOT NULL,
    variant_id text,
    title character varying(255) NOT NULL,
    quantity integer DEFAULT 1 NOT NULL,
    unit_price integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    thumbnail character varying(500)
);


--
-- Name: cart_tax_lines; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.cart_tax_lines (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    cart_item_id text NOT NULL,
    code character varying(100),
    rate numeric(5,4) DEFAULT 0 NOT NULL,
    amount integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: carts; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.carts (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    customer_id text,
    email character varying(255),
    currency_code text NOT NULL,
    region_id text,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    completed_at timestamp with time zone,
    deleted_at timestamp with time zone,
    shipping_address_id text,
    billing_address_id text
);


--
-- Name: currencies; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.currencies (
    code text NOT NULL,
    name character varying(100) NOT NULL,
    symbol character varying(10) NOT NULL,
    decimal_digits integer DEFAULT 2 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: customer_addresses; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.customer_addresses (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    customer_id text NOT NULL,
    first_name character varying(100),
    last_name character varying(100),
    company character varying(255),
    address_1 character varying(255) NOT NULL,
    address_2 character varying(255),
    city character varying(100) NOT NULL,
    province character varying(100),
    postal_code character varying(50),
    country_code character varying(2) NOT NULL,
    phone character varying(50),
    is_default_shipping boolean DEFAULT false NOT NULL,
    is_default_billing boolean DEFAULT false NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: customer_group_customers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.customer_group_customers (
    customer_id text NOT NULL,
    customer_group_id text NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: customer_groups; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.customer_groups (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    name character varying(255) NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: customers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.customers (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(100),
    last_name character varying(100),
    phone character varying(50),
    has_account boolean DEFAULT false NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: discount_conditions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.discount_conditions (
    id character varying(48) NOT NULL,
    discount_id character varying(48) NOT NULL,
    condition_type character varying(50) NOT NULL,
    operator character varying(20) NOT NULL,
    condition_value text NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    deleted_at timestamp without time zone
);


--
-- Name: discount_rules; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.discount_rules (
    id character varying(48) NOT NULL,
    discount_id character varying(48) NOT NULL,
    rule_type character varying(50) NOT NULL,
    rule_value integer DEFAULT 0 NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    deleted_at timestamp without time zone
);


--
-- Name: discounts; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.discounts (
    id character varying(48) NOT NULL,
    code character varying(50) NOT NULL,
    starts_at timestamp without time zone DEFAULT now() NOT NULL,
    ends_at timestamp without time zone,
    usage_limit integer,
    usage_count integer DEFAULT 0 NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    deleted_at timestamp without time zone,
    type character varying(50) DEFAULT 'PERCENTAGE'::character varying NOT NULL,
    value integer DEFAULT 0 NOT NULL,
    currency_code character varying(3),
    min_requirement_amount integer DEFAULT 0 NOT NULL,
    max_discount_amount integer DEFAULT 0 NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    is_public boolean DEFAULT false NOT NULL,
    description character varying(500),
    metadata jsonb DEFAULT '{}'::jsonb
);


--
-- Name: event_logs; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.event_logs (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    event_type character varying(100) NOT NULL,
    severity character varying(20) DEFAULT 'info'::character varying NOT NULL,
    source character varying(50) DEFAULT 'system'::character varying NOT NULL,
    message text NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);

--
-- Name: fulfillment_addresses; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.fulfillment_addresses (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    fulfillment_id text NOT NULL,
    first_name character varying(100),
    last_name character varying(100),
    company character varying(255),
    address_1 character varying(255) NOT NULL,
    address_2 character varying(255),
    city character varying(100) NOT NULL,
    province character varying(100),
    postal_code character varying(50),
    country_code character varying(2) NOT NULL,
    phone character varying(50),
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: fulfillment_items; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.fulfillment_items (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    fulfillment_id text NOT NULL,
    order_item_id text,
    title character varying(255) NOT NULL,
    sku character varying(100),
    quantity integer DEFAULT 1 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    fulfilled_quantity integer DEFAULT 0 NOT NULL,
    returned_quantity integer DEFAULT 0 NOT NULL,
    variant_title character varying(255),
    variant_id text
);


--
-- Name: fulfillment_labels; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.fulfillment_labels (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    fulfillment_id text NOT NULL,
    tracking_number character varying(255),
    tracking_url character varying(1000),
    label_url character varying(1000),
    carrier character varying(100),
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: fulfillments; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.fulfillments (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    order_id text,
    provider_id character varying(100),
    shipping_option_id text,
    status character varying(50) DEFAULT 'pending'::character varying NOT NULL,
    data jsonb DEFAULT '{}'::jsonb NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    shipped_at timestamp with time zone,
    delivered_at timestamp with time zone,
    canceled_at timestamp with time zone,
    deleted_at timestamp with time zone,
    carrier character varying(100),
    tracking_number character varying(255),
    display_id bigint
);


--
-- Name: inventory_items; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.inventory_items (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    sku character varying(100),
    title character varying(255),
    requires_shipping boolean DEFAULT true NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    quantity integer DEFAULT 0 NOT NULL,
    reserved_quantity integer DEFAULT 0 NOT NULL,
    incoming_quantity integer DEFAULT 0 NOT NULL,
    warehouse_id character varying(255),
    location character varying(255),
    overselling boolean DEFAULT false NOT NULL,
    restock_threshold integer DEFAULT 0,
    variant_id character varying(255)
);


--
-- Name: inventory_levels; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.inventory_levels (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    inventory_item_id text NOT NULL,
    location_id text NOT NULL,
    stocked_quantity integer DEFAULT 0 NOT NULL,
    reserved_quantity integer DEFAULT 0 NOT NULL,
    incoming_quantity integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    delta_quantity integer DEFAULT 0,
    current_quantity integer DEFAULT 0,
    reason character varying(255),
    note character varying(500),
    reference_type character varying(100),
    reference_id character varying(255),
    created_by character varying(255)
);


--
-- Name: mfa_factors; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.mfa_factors (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    auth_identity_id text NOT NULL,
    factor_type character varying(50) NOT NULL,
    secret text,
    phone character varying(50),
    is_enabled boolean DEFAULT false NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: mfa_recovery_codes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.mfa_recovery_codes (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    auth_identity_id text NOT NULL,
    code_hash text NOT NULL,
    used_at timestamp with time zone,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: notifications; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.notifications (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    to_address character varying(255) NOT NULL,
    channel character varying(50) NOT NULL,
    template character varying(100),
    status character varying(50) DEFAULT 'pending'::character varying NOT NULL,
    data jsonb DEFAULT '{}'::jsonb NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    content text,
    recipient_id uuid,
    recipient_email character varying(255),
    type character varying(50),
    subject character varying(255),
    sent_at timestamp with time zone,
    read_at timestamp with time zone,
    failed_at timestamp with time zone,
    error_message character varying(500)
);


--
-- Name: order_addresses; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_addresses (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    order_id text NOT NULL,
    type character varying(50) NOT NULL,
    first_name character varying(100),
    last_name character varying(100),
    company character varying(255),
    address_1 character varying(255) NOT NULL,
    address_2 character varying(255),
    city character varying(100) NOT NULL,
    province character varying(100),
    postal_code character varying(50),
    country_code character varying(2) NOT NULL,
    phone character varying(50),
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: order_carts; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_carts (
    order_id text NOT NULL,
    cart_id text NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: order_credit_lines; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_credit_lines (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    order_id text NOT NULL,
    amount integer NOT NULL,
    reference character varying(255),
    reference_id text,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: order_fulfillments; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_fulfillments (
    order_id text NOT NULL,
    fulfillment_id text NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: order_items; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_items (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    order_id text NOT NULL,
    variant_id text,
    title character varying(255) NOT NULL,
    sku character varying(100),
    quantity integer DEFAULT 1 NOT NULL,
    unit_price integer DEFAULT 0 NOT NULL,
    total integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: order_status_history; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_status_history (
    id character varying(36) NOT NULL,
    order_id character varying(36) NOT NULL,
    from_status character varying(50),
    to_status character varying(50) NOT NULL,
    note text,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone
);


--
-- Name: order_summaries; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_summaries (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    order_id text NOT NULL,
    item_total integer DEFAULT 0 NOT NULL,
    tax_total integer DEFAULT 0 NOT NULL,
    shipping_total integer DEFAULT 0 NOT NULL,
    discount_total integer DEFAULT 0 NOT NULL,
    paid_total integer DEFAULT 0 NOT NULL,
    refunded_total integer DEFAULT 0 NOT NULL,
    current_order_total integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: order_timelines; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_timelines (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    order_id text NOT NULL,
    previous_status character varying(50),
    new_status character varying(50) NOT NULL,
    reason text,
    action_by text,
    action_type character varying(50) DEFAULT 'system'::character varying NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: order_transactions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.order_transactions (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    order_id text NOT NULL,
    amount integer NOT NULL,
    currency_code text NOT NULL,
    reference character varying(255),
    reference_id text,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: orders; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.orders (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    display_id bigint NOT NULL,
    customer_id text,
    cart_id text,
    email character varying(255),
    currency_code text NOT NULL,
    region_id text,
    status character varying(50) DEFAULT 'pending'::character varying NOT NULL,
    payment_status character varying(50) DEFAULT 'not_paid'::character varying NOT NULL,
    fulfillment_status character varying(50) DEFAULT 'not_fulfilled'::character varying NOT NULL,
    subtotal integer DEFAULT 0 NOT NULL,
    tax_total integer DEFAULT 0 NOT NULL,
    shipping_total integer DEFAULT 0 NOT NULL,
    discount_total integer DEFAULT 0 NOT NULL,
    total integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    canceled_at timestamp with time zone,
    deleted_at timestamp with time zone,
    shipping_address_id text,
    billing_address_id text
);


--
-- Name: orders_display_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.orders_display_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: orders_display_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.orders_display_id_seq OWNED BY public.orders.display_id;


--
-- Name: payment_collections; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.payment_collections (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    order_id text,
    currency_code text NOT NULL,
    amount integer DEFAULT 0 NOT NULL,
    authorized_amount integer DEFAULT 0 NOT NULL,
    captured_amount integer DEFAULT 0 NOT NULL,
    refunded_amount integer DEFAULT 0 NOT NULL,
    status character varying(50) DEFAULT 'not_paid'::character varying NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: payment_sessions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.payment_sessions (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    payment_collection_id text NOT NULL,
    payment_id text,
    provider_id character varying(100) NOT NULL,
    amount integer NOT NULL,
    currency_code text NOT NULL,
    status character varying(50) DEFAULT 'pending'::character varying NOT NULL,
    data jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    canceled_at timestamp with time zone,
    provider_session_id character varying(255),
    expires_at timestamp with time zone,
    confirmed_at timestamp with time zone,
    error_message character varying(500),
    provider character varying(50) DEFAULT 'stripe'::character varying NOT NULL
);


--
-- Name: payments; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.payments (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    payment_collection_id text NOT NULL,
    provider_id character varying(100) NOT NULL,
    amount integer NOT NULL,
    currency_code text NOT NULL,
    status character varying(50) DEFAULT 'pending'::character varying NOT NULL,
    data jsonb DEFAULT '{}'::jsonb NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    captured_at timestamp with time zone,
    canceled_at timestamp with time zone,
    deleted_at timestamp with time zone,
    payment_session_id text,
    provider_transaction_id character varying(255)
);


--
-- Name: price_lists; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.price_lists (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    title character varying(255),
    description text,
    type character varying(50) DEFAULT 'sale'::character varying NOT NULL,
    status character varying(50) DEFAULT 'draft'::character varying NOT NULL,
    starts_at timestamp with time zone,
    ends_at timestamp with time zone,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: price_rules; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.price_rules (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    price_id text NOT NULL,
    attribute character varying(100) NOT NULL,
    operator character varying(50) DEFAULT 'eq'::character varying NOT NULL,
    value jsonb DEFAULT '[]'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: price_sets; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.price_sets (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: prices; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.prices (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    price_set_id text NOT NULL,
    price_list_id text,
    currency_code text NOT NULL,
    amount integer NOT NULL,
    min_quantity integer,
    max_quantity integer,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: product_categories; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_categories (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    parent_category_id text,
    name character varying(255) NOT NULL,
    handle character varying(255) NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    is_internal boolean DEFAULT false NOT NULL,
    rank integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: product_category_products; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_category_products (
    product_id text NOT NULL,
    category_id text NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: product_collections; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_collections (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    title character varying(255) NOT NULL,
    handle character varying(255) NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    rank integer DEFAULT 0 NOT NULL,
    is_active boolean DEFAULT true NOT NULL
);


--
-- Name: product_images; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_images (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    product_id text NOT NULL,
    url character varying(1000) NOT NULL,
    rank integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: product_option_values; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_option_values (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    option_id text NOT NULL,
    value character varying(255) NOT NULL,
    rank integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: product_options; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_options (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    product_id text NOT NULL,
    title character varying(255) NOT NULL,
    rank integer DEFAULT 0 NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: product_product_tags; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_product_tags (
    product_id text NOT NULL,
    tag_id text NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: product_tags; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_tags (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    value character varying(255) NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: product_types; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_types (
    id character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    slug character varying(255) NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone
);


--
-- Name: product_variant_inventory_items; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_variant_inventory_items (
    variant_id text NOT NULL,
    inventory_item_id text NOT NULL,
    required_quantity integer DEFAULT 1 NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: product_variant_price_sets; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_variant_price_sets (
    variant_id text NOT NULL,
    price_set_id text NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: product_variants; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_variants (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    product_id text NOT NULL,
    title character varying(255) NOT NULL,
    sku character varying(100),
    barcode character varying(100),
    ean character varying(100),
    upc character varying(100),
    inventory_quantity integer DEFAULT 0 NOT NULL,
    allow_backorder boolean DEFAULT false NOT NULL,
    manage_inventory boolean DEFAULT true NOT NULL,
    weight integer,
    height integer,
    width integer,
    length integer,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: products; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.products (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    handle character varying(255) NOT NULL,
    title character varying(500) NOT NULL,
    subtitle character varying(500),
    description text,
    thumbnail character varying(500),
    weight integer,
    height integer,
    width integer,
    length integer,
    origin_country character varying(2),
    hs_code character varying(50),
    mid_code character varying(50),
    material character varying(255),
    collection_id text,
    category_id text,
    type_id text,
    status character varying(50) DEFAULT 'draft'::character varying NOT NULL,
    discountable boolean DEFAULT true NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: promotions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.promotions (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    code character varying(100) NOT NULL,
    type character varying(50) DEFAULT 'standard'::character varying NOT NULL,
    status character varying(50) DEFAULT 'draft'::character varying NOT NULL,
    starts_at timestamp with time zone,
    ends_at timestamp with time zone,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: provider_identities; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.provider_identities (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    auth_identity_id text NOT NULL,
    provider character varying(50) NOT NULL,
    provider_user_id character varying(255) NOT NULL,
    provider_metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: refunds; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.refunds (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    payment_id text NOT NULL,
    amount integer NOT NULL,
    reason character varying(255),
    note text,
    created_by text,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone,
    currency_code character varying(3) DEFAULT 'USD'::character varying NOT NULL,
    failed_at timestamp with time zone,
    status character varying(50) DEFAULT 'PENDING'::character varying NOT NULL,
    provider_refund_id character varying(255),
    processed_at timestamp with time zone,
    failure_message character varying(500),
    payment_collection_id character varying(255)
);


--
-- Name: region_countries; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.region_countries (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    region_id text NOT NULL,
    country_code character varying(2) NOT NULL,
    country_name character varying(255) NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: regions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.regions (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    name character varying(255) NOT NULL,
    currency_code text NOT NULL,
    tax_rate numeric(5,4) DEFAULT 0 NOT NULL,
    tax_code character varying(50),
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: reservation_items; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.reservation_items (
    id character varying(255) NOT NULL,
    inventory_item_id character varying(255) NOT NULL,
    line_item_id uuid NOT NULL,
    quantity integer DEFAULT 0 NOT NULL,
    expires_at timestamp without time zone,
    status character varying(20) DEFAULT 'RESERVED'::character varying NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone
);


--
-- Name: roles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.roles (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    name character varying(100) NOT NULL,
    description text,
    permissions jsonb DEFAULT '[]'::jsonb NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: shipping_option_rules; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.shipping_option_rules (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    shipping_option_id text NOT NULL,
    attribute character varying(100) NOT NULL,
    operator character varying(50) DEFAULT 'eq'::character varying NOT NULL,
    value jsonb DEFAULT '[]'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: shipping_options; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.shipping_options (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    shipping_profile_id text NOT NULL,
    region_id text NOT NULL,
    name character varying(255) NOT NULL,
    price_type character varying(50) DEFAULT 'flat_rate'::character varying NOT NULL,
    amount integer DEFAULT 0 NOT NULL,
    data jsonb DEFAULT '{}'::jsonb NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: shipping_profiles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.shipping_profiles (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    name character varying(255) NOT NULL,
    type character varying(50) DEFAULT 'default'::character varying NOT NULL,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: store_currencies; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.store_currencies (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    store_id text NOT NULL,
    currency_code text NOT NULL,
    is_default boolean DEFAULT false NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: stores; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.stores (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    name character varying(255) NOT NULL,
    default_currency_code text,
    default_region_id text,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(100),
    last_name character varying(100),
    password_hash text,
    role character varying(50) DEFAULT 'admin'::character varying NOT NULL,
    role_id text,
    metadata jsonb DEFAULT '{}'::jsonb NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    updated_at timestamp with time zone DEFAULT now() NOT NULL,
    deleted_at timestamp with time zone
);


--
-- Name: verification_tokens; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.verification_tokens (
    id text DEFAULT (gen_random_uuid())::text NOT NULL,
    auth_identity_id text,
    identifier character varying(255) NOT NULL,
    token_hash text NOT NULL,
    type character varying(50) NOT NULL,
    expires_at timestamp with time zone NOT NULL,
    used_at timestamp with time zone,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


--
-- Name: orders display_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders ALTER COLUMN display_id SET DEFAULT nextval('public.orders_display_id_seq'::regclass);


--
-- Name: api_keys api_keys_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.api_keys
    ADD CONSTRAINT api_keys_pkey PRIMARY KEY (id);


--
-- Name: application_methods application_methods_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.application_methods
    ADD CONSTRAINT application_methods_pkey PRIMARY KEY (id);


--
-- Name: audit_logs audit_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.audit_logs
    ADD CONSTRAINT audit_logs_pkey PRIMARY KEY (id);


--
-- Name: auth_identities auth_identities_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.auth_identities
    ADD CONSTRAINT auth_identities_pkey PRIMARY KEY (id);


--
-- Name: campaigns campaigns_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.campaigns
    ADD CONSTRAINT campaigns_pkey PRIMARY KEY (id);


--
-- Name: captures captures_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.captures
    ADD CONSTRAINT captures_pkey PRIMARY KEY (id);


--
-- Name: cart_adjustments cart_adjustments_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cart_adjustments
    ADD CONSTRAINT cart_adjustments_pkey PRIMARY KEY (id);


--
-- Name: cart_items cart_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT cart_items_pkey PRIMARY KEY (id);


--
-- Name: cart_tax_lines cart_tax_lines_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cart_tax_lines
    ADD CONSTRAINT cart_tax_lines_pkey PRIMARY KEY (id);


--
-- Name: carts carts_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT carts_pkey PRIMARY KEY (id);


--
-- Name: currencies currencies_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.currencies
    ADD CONSTRAINT currencies_pkey PRIMARY KEY (code);


--
-- Name: customer_addresses customer_addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customer_addresses
    ADD CONSTRAINT customer_addresses_pkey PRIMARY KEY (id);


--
-- Name: customer_group_customers customer_group_customers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customer_group_customers
    ADD CONSTRAINT customer_group_customers_pkey PRIMARY KEY (customer_id, customer_group_id);


--
-- Name: customer_groups customer_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customer_groups
    ADD CONSTRAINT customer_groups_pkey PRIMARY KEY (id);


--
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);


--
-- Name: discount_conditions discount_conditions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.discount_conditions
    ADD CONSTRAINT discount_conditions_pkey PRIMARY KEY (id);


--
-- Name: discount_rules discount_rules_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.discount_rules
    ADD CONSTRAINT discount_rules_pkey PRIMARY KEY (id);


--
-- Name: discounts discounts_code_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.discounts
    ADD CONSTRAINT discounts_code_key UNIQUE (code);


--
-- Name: discounts discounts_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.discounts
    ADD CONSTRAINT discounts_pkey PRIMARY KEY (id);


--
-- Name: event_logs event_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.event_logs
    ADD CONSTRAINT event_logs_pkey PRIMARY KEY (id);


--
-- Name: fulfillment_addresses fulfillment_addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillment_addresses
    ADD CONSTRAINT fulfillment_addresses_pkey PRIMARY KEY (id);


--
-- Name: fulfillment_items fulfillment_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillment_items
    ADD CONSTRAINT fulfillment_items_pkey PRIMARY KEY (id);


--
-- Name: fulfillment_labels fulfillment_labels_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillment_labels
    ADD CONSTRAINT fulfillment_labels_pkey PRIMARY KEY (id);


--
-- Name: fulfillments fulfillments_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillments
    ADD CONSTRAINT fulfillments_pkey PRIMARY KEY (id);


--
-- Name: inventory_items inventory_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.inventory_items
    ADD CONSTRAINT inventory_items_pkey PRIMARY KEY (id);


--
-- Name: inventory_levels inventory_levels_inventory_item_id_location_id_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.inventory_levels
    ADD CONSTRAINT inventory_levels_inventory_item_id_location_id_key UNIQUE (inventory_item_id, location_id);


--
-- Name: inventory_levels inventory_levels_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.inventory_levels
    ADD CONSTRAINT inventory_levels_pkey PRIMARY KEY (id);


--
-- Name: mfa_factors mfa_factors_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.mfa_factors
    ADD CONSTRAINT mfa_factors_pkey PRIMARY KEY (id);


--
-- Name: mfa_recovery_codes mfa_recovery_codes_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.mfa_recovery_codes
    ADD CONSTRAINT mfa_recovery_codes_pkey PRIMARY KEY (id);


--
-- Name: notifications notifications_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.notifications
    ADD CONSTRAINT notifications_pkey PRIMARY KEY (id);


--
-- Name: order_addresses order_addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_addresses
    ADD CONSTRAINT order_addresses_pkey PRIMARY KEY (id);


--
-- Name: order_carts order_carts_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_carts
    ADD CONSTRAINT order_carts_pkey PRIMARY KEY (order_id, cart_id);


--
-- Name: order_credit_lines order_credit_lines_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_credit_lines
    ADD CONSTRAINT order_credit_lines_pkey PRIMARY KEY (id);


--
-- Name: order_fulfillments order_fulfillments_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_fulfillments
    ADD CONSTRAINT order_fulfillments_pkey PRIMARY KEY (order_id, fulfillment_id);


--
-- Name: order_items order_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_pkey PRIMARY KEY (id);


--
-- Name: order_status_history order_status_history_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_status_history
    ADD CONSTRAINT order_status_history_pkey PRIMARY KEY (id);


--
-- Name: order_summaries order_summaries_order_id_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_summaries
    ADD CONSTRAINT order_summaries_order_id_key UNIQUE (order_id);


--
-- Name: order_summaries order_summaries_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_summaries
    ADD CONSTRAINT order_summaries_pkey PRIMARY KEY (id);


--
-- Name: order_timelines order_timelines_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_timelines
    ADD CONSTRAINT order_timelines_pkey PRIMARY KEY (id);


--
-- Name: order_transactions order_transactions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_transactions
    ADD CONSTRAINT order_transactions_pkey PRIMARY KEY (id);


--
-- Name: orders orders_display_id_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_display_id_key UNIQUE (display_id);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: payment_collections payment_collections_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment_collections
    ADD CONSTRAINT payment_collections_pkey PRIMARY KEY (id);


--
-- Name: payment_sessions payment_sessions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment_sessions
    ADD CONSTRAINT payment_sessions_pkey PRIMARY KEY (id);


--
-- Name: payments payments_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT payments_pkey PRIMARY KEY (id);


--
-- Name: reservation_items pk_reservation_items; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.reservation_items
    ADD CONSTRAINT pk_reservation_items PRIMARY KEY (id);


--
-- Name: price_lists price_lists_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.price_lists
    ADD CONSTRAINT price_lists_pkey PRIMARY KEY (id);


--
-- Name: price_rules price_rules_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.price_rules
    ADD CONSTRAINT price_rules_pkey PRIMARY KEY (id);


--
-- Name: price_sets price_sets_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.price_sets
    ADD CONSTRAINT price_sets_pkey PRIMARY KEY (id);


--
-- Name: prices prices_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.prices
    ADD CONSTRAINT prices_pkey PRIMARY KEY (id);


--
-- Name: product_categories product_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_categories
    ADD CONSTRAINT product_categories_pkey PRIMARY KEY (id);


--
-- Name: product_category_products product_category_products_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_category_products
    ADD CONSTRAINT product_category_products_pkey PRIMARY KEY (product_id, category_id);


--
-- Name: product_collections product_collections_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_collections
    ADD CONSTRAINT product_collections_pkey PRIMARY KEY (id);


--
-- Name: product_images product_images_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_images
    ADD CONSTRAINT product_images_pkey PRIMARY KEY (id);


--
-- Name: product_option_values product_option_values_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_option_values
    ADD CONSTRAINT product_option_values_pkey PRIMARY KEY (id);


--
-- Name: product_options product_options_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_options
    ADD CONSTRAINT product_options_pkey PRIMARY KEY (id);


--
-- Name: product_product_tags product_product_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_product_tags
    ADD CONSTRAINT product_product_tags_pkey PRIMARY KEY (product_id, tag_id);


--
-- Name: product_tags product_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_tags
    ADD CONSTRAINT product_tags_pkey PRIMARY KEY (id);


--
-- Name: product_types product_types_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_types
    ADD CONSTRAINT product_types_pkey PRIMARY KEY (id);


--
-- Name: product_types product_types_slug_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_types
    ADD CONSTRAINT product_types_slug_key UNIQUE (slug);


--
-- Name: product_variant_inventory_items product_variant_inventory_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_variant_inventory_items
    ADD CONSTRAINT product_variant_inventory_items_pkey PRIMARY KEY (variant_id, inventory_item_id);


--
-- Name: product_variant_price_sets product_variant_price_sets_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_variant_price_sets
    ADD CONSTRAINT product_variant_price_sets_pkey PRIMARY KEY (variant_id, price_set_id);


--
-- Name: product_variants product_variants_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_variants
    ADD CONSTRAINT product_variants_pkey PRIMARY KEY (id);


--
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- Name: promotions promotions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.promotions
    ADD CONSTRAINT promotions_pkey PRIMARY KEY (id);


--
-- Name: provider_identities provider_identities_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.provider_identities
    ADD CONSTRAINT provider_identities_pkey PRIMARY KEY (id);


--
-- Name: provider_identities provider_identities_provider_provider_user_id_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.provider_identities
    ADD CONSTRAINT provider_identities_provider_provider_user_id_key UNIQUE (provider, provider_user_id);


--
-- Name: refunds refunds_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.refunds
    ADD CONSTRAINT refunds_pkey PRIMARY KEY (id);


--
-- Name: region_countries region_countries_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.region_countries
    ADD CONSTRAINT region_countries_pkey PRIMARY KEY (id);


--
-- Name: region_countries region_countries_region_id_country_code_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.region_countries
    ADD CONSTRAINT region_countries_region_id_country_code_key UNIQUE (region_id, country_code);


--
-- Name: regions regions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.regions
    ADD CONSTRAINT regions_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: shipping_option_rules shipping_option_rules_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.shipping_option_rules
    ADD CONSTRAINT shipping_option_rules_pkey PRIMARY KEY (id);


--
-- Name: shipping_options shipping_options_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.shipping_options
    ADD CONSTRAINT shipping_options_pkey PRIMARY KEY (id);


--
-- Name: shipping_profiles shipping_profiles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.shipping_profiles
    ADD CONSTRAINT shipping_profiles_pkey PRIMARY KEY (id);


--
-- Name: store_currencies store_currencies_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.store_currencies
    ADD CONSTRAINT store_currencies_pkey PRIMARY KEY (id);


--
-- Name: store_currencies store_currencies_store_id_currency_code_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.store_currencies
    ADD CONSTRAINT store_currencies_store_id_currency_code_key UNIQUE (store_id, currency_code);


--
-- Name: stores stores_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.stores
    ADD CONSTRAINT stores_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: verification_tokens verification_tokens_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.verification_tokens
    ADD CONSTRAINT verification_tokens_pkey PRIMARY KEY (id);


--
-- Name: idx_audit_logs_action; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_audit_logs_action ON public.audit_logs USING btree (action);


--
-- Name: idx_audit_logs_changes; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_audit_logs_changes ON public.audit_logs USING gin (changes);


--
-- Name: idx_audit_logs_created; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_audit_logs_created ON public.audit_logs USING btree (created_at DESC);


--
-- Name: idx_audit_logs_entity; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_audit_logs_entity ON public.audit_logs USING btree (entity_type, entity_id, created_at DESC);


--
-- Name: idx_audit_logs_performer; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_audit_logs_performer ON public.audit_logs USING btree (performed_by);


--
-- Name: idx_cart_adjustments_item; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_cart_adjustments_item ON public.cart_adjustments USING btree (cart_item_id);


--
-- Name: idx_cart_items_cart; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_cart_items_cart ON public.cart_items USING btree (cart_id);


--
-- Name: idx_cart_items_variant; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_cart_items_variant ON public.cart_items USING btree (variant_id);


--
-- Name: idx_cart_tax_lines_item; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_cart_tax_lines_item ON public.cart_tax_lines USING btree (cart_item_id);


--
-- Name: idx_customer_addresses_customer; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_customer_addresses_customer ON public.customer_addresses USING btree (customer_id);


--
-- Name: idx_customers_active; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_customers_active ON public.customers USING btree (id) WHERE (deleted_at IS NULL);


--
-- Name: idx_customers_email_unique; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX idx_customers_email_unique ON public.customers USING btree (email) WHERE (deleted_at IS NULL);


--
-- Name: idx_customers_metadata; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_customers_metadata ON public.customers USING gin (metadata);


--
-- Name: idx_event_logs_metadata; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_event_logs_metadata ON public.event_logs USING gin (metadata);


--
-- Name: idx_event_logs_severity; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_event_logs_severity ON public.event_logs USING btree (severity, created_at DESC);


--
-- Name: idx_event_logs_source; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_event_logs_source ON public.event_logs USING btree (source, created_at DESC);


--
-- Name: idx_event_logs_ttl; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_event_logs_ttl ON public.event_logs USING btree (created_at);


--
-- Name: idx_event_logs_type; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_event_logs_type ON public.event_logs USING btree (event_type, created_at DESC);


--
-- Name: idx_fulfillment_items_variant_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_fulfillment_items_variant_id ON public.fulfillment_items USING btree (variant_id);


--
-- Name: idx_fulfillments_carrier; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_fulfillments_carrier ON public.fulfillments USING btree (carrier);


--
-- Name: idx_fulfillments_display_id; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX idx_fulfillments_display_id ON public.fulfillments USING btree (display_id);


--
-- Name: idx_fulfillments_order; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_fulfillments_order ON public.fulfillments USING btree (order_id) WHERE (deleted_at IS NULL);


--
-- Name: idx_inventory_items_variant_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_inventory_items_variant_id ON public.inventory_items USING btree (variant_id);


--
-- Name: idx_inventory_levels_item; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_inventory_levels_item ON public.inventory_levels USING btree (inventory_item_id);


--
-- Name: idx_inventory_levels_location; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_inventory_levels_location ON public.inventory_levels USING btree (location_id);


--
-- Name: idx_order_addresses_order; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_order_addresses_order ON public.order_addresses USING btree (order_id);


--
-- Name: idx_order_credit_lines_order; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_order_credit_lines_order ON public.order_credit_lines USING btree (order_id);


--
-- Name: idx_order_items_order; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_order_items_order ON public.order_items USING btree (order_id);


--
-- Name: idx_order_items_variant; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_order_items_variant ON public.order_items USING btree (variant_id);


--
-- Name: idx_order_timelines_metadata; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_order_timelines_metadata ON public.order_timelines USING gin (metadata);


--
-- Name: idx_order_timelines_order; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_order_timelines_order ON public.order_timelines USING btree (order_id, created_at DESC);


--
-- Name: idx_order_timelines_status; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_order_timelines_status ON public.order_timelines USING btree (new_status);


--
-- Name: idx_order_transactions_order; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_order_transactions_order ON public.order_transactions USING btree (order_id);


--
-- Name: idx_orders_active; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_orders_active ON public.orders USING btree (id) WHERE (deleted_at IS NULL);


--
-- Name: idx_orders_customer; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_orders_customer ON public.orders USING btree (customer_id, created_at DESC) WHERE (deleted_at IS NULL);


--
-- Name: idx_orders_metadata; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_orders_metadata ON public.orders USING gin (metadata);


--
-- Name: idx_orders_status_created; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_orders_status_created ON public.orders USING btree (status, created_at DESC) WHERE (deleted_at IS NULL);


--
-- Name: idx_payments_collection; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_payments_collection ON public.payments USING btree (payment_collection_id);


--
-- Name: idx_payments_status; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_payments_status ON public.payments USING btree (status);


--
-- Name: idx_price_rules_price; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_price_rules_price ON public.price_rules USING btree (price_id);


--
-- Name: idx_prices_currency; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_prices_currency ON public.prices USING btree (currency_code);


--
-- Name: idx_prices_set; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_prices_set ON public.prices USING btree (price_set_id);


--
-- Name: idx_product_category_products_category; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_category_products_category ON public.product_category_products USING btree (category_id);


--
-- Name: idx_product_images_product; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_images_product ON public.product_images USING btree (product_id);


--
-- Name: idx_product_product_tags_tag; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_product_tags_tag ON public.product_product_tags USING btree (tag_id);


--
-- Name: idx_product_variant_inventory_items_item; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_variant_inventory_items_item ON public.product_variant_inventory_items USING btree (inventory_item_id);


--
-- Name: idx_product_variant_price_sets_set; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_variant_price_sets_set ON public.product_variant_price_sets USING btree (price_set_id);


--
-- Name: idx_product_variants_fts; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_variants_fts ON public.product_variants USING gin (to_tsvector('english'::regconfig, (((title)::text || ' '::text) || (COALESCE(sku, ''::character varying))::text)));


--
-- Name: idx_product_variants_metadata; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_variants_metadata ON public.product_variants USING gin (metadata);


--
-- Name: idx_product_variants_product; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_variants_product ON public.product_variants USING btree (product_id) WHERE (deleted_at IS NULL);


--
-- Name: idx_product_variants_sku; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_variants_sku ON public.product_variants USING btree (sku) WHERE (deleted_at IS NULL);


--
-- Name: idx_product_variants_sku_unique; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX idx_product_variants_sku_unique ON public.product_variants USING btree (sku) WHERE ((deleted_at IS NULL) AND (sku IS NOT NULL));


--
-- Name: idx_products_active; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_products_active ON public.products USING btree (id, handle) WHERE (deleted_at IS NULL);


--
-- Name: idx_products_category_status; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_products_category_status ON public.products USING btree (category_id, status) WHERE (deleted_at IS NULL);


--
-- Name: idx_products_collection_status; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_products_collection_status ON public.products USING btree (collection_id, status) WHERE (deleted_at IS NULL);


--
-- Name: idx_products_fts; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_products_fts ON public.products USING gin (to_tsvector('english'::regconfig, (((title)::text || ' '::text) || COALESCE(description, ''::text))));


--
-- Name: idx_products_handle_unique; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX idx_products_handle_unique ON public.products USING btree (handle) WHERE (deleted_at IS NULL);


--
-- Name: idx_products_metadata; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_products_metadata ON public.products USING gin (metadata);


--
-- Name: idx_products_sales; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_products_sales ON public.products USING btree (collection_id, status, created_at DESC) WHERE (deleted_at IS NULL);


--
-- Name: idx_provider_identities_auth; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_provider_identities_auth ON public.provider_identities USING btree (auth_identity_id);


--
-- Name: idx_region_countries_country; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_region_countries_country ON public.region_countries USING btree (country_code);


--
-- Name: idx_shipping_options_region; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_shipping_options_region ON public.shipping_options USING btree (region_id);


--
-- Name: idx_users_email_unique; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX idx_users_email_unique ON public.users USING btree (email) WHERE (deleted_at IS NULL);


--
-- Name: api_keys api_keys_created_by_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.api_keys
    ADD CONSTRAINT api_keys_created_by_fkey FOREIGN KEY (created_by) REFERENCES public.users(id);


--
-- Name: application_methods application_methods_promotion_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.application_methods
    ADD CONSTRAINT application_methods_promotion_id_fkey FOREIGN KEY (promotion_id) REFERENCES public.promotions(id) ON DELETE CASCADE;


--
-- Name: campaigns campaigns_promotion_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.campaigns
    ADD CONSTRAINT campaigns_promotion_id_fkey FOREIGN KEY (promotion_id) REFERENCES public.promotions(id) ON DELETE CASCADE;


--
-- Name: captures captures_created_by_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.captures
    ADD CONSTRAINT captures_created_by_fkey FOREIGN KEY (created_by) REFERENCES public.users(id);


--
-- Name: captures captures_payment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.captures
    ADD CONSTRAINT captures_payment_id_fkey FOREIGN KEY (payment_id) REFERENCES public.payments(id) ON DELETE CASCADE;


--
-- Name: cart_adjustments cart_adjustments_cart_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cart_adjustments
    ADD CONSTRAINT cart_adjustments_cart_item_id_fkey FOREIGN KEY (cart_item_id) REFERENCES public.cart_items(id) ON DELETE CASCADE;


--
-- Name: cart_items cart_items_cart_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT cart_items_cart_id_fkey FOREIGN KEY (cart_id) REFERENCES public.carts(id) ON DELETE CASCADE;


--
-- Name: cart_items cart_items_variant_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT cart_items_variant_id_fkey FOREIGN KEY (variant_id) REFERENCES public.product_variants(id);


--
-- Name: cart_tax_lines cart_tax_lines_cart_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cart_tax_lines
    ADD CONSTRAINT cart_tax_lines_cart_item_id_fkey FOREIGN KEY (cart_item_id) REFERENCES public.cart_items(id) ON DELETE CASCADE;


--
-- Name: carts carts_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT carts_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: carts carts_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT carts_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(id);


--
-- Name: carts carts_region_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT carts_region_id_fkey FOREIGN KEY (region_id) REFERENCES public.regions(id);


--
-- Name: customer_addresses customer_addresses_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customer_addresses
    ADD CONSTRAINT customer_addresses_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(id) ON DELETE CASCADE;


--
-- Name: customer_group_customers customer_group_customers_customer_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customer_group_customers
    ADD CONSTRAINT customer_group_customers_customer_group_id_fkey FOREIGN KEY (customer_group_id) REFERENCES public.customer_groups(id) ON DELETE CASCADE;


--
-- Name: customer_group_customers customer_group_customers_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customer_group_customers
    ADD CONSTRAINT customer_group_customers_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(id) ON DELETE CASCADE;


--
-- Name: discount_conditions discount_conditions_discount_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.discount_conditions
    ADD CONSTRAINT discount_conditions_discount_id_fkey FOREIGN KEY (discount_id) REFERENCES public.discounts(id) ON DELETE CASCADE;


--
-- Name: discount_rules discount_rules_discount_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.discount_rules
    ADD CONSTRAINT discount_rules_discount_id_fkey FOREIGN KEY (discount_id) REFERENCES public.discounts(id) ON DELETE CASCADE;


--
-- Name: carts fk_carts_billing_address; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT fk_carts_billing_address FOREIGN KEY (billing_address_id) REFERENCES public.customer_addresses(id);


--
-- Name: carts fk_carts_shipping_address; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.carts
    ADD CONSTRAINT fk_carts_shipping_address FOREIGN KEY (shipping_address_id) REFERENCES public.customer_addresses(id);


--
-- Name: discount_conditions fk_discount_conditions_discount; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.discount_conditions
    ADD CONSTRAINT fk_discount_conditions_discount FOREIGN KEY (discount_id) REFERENCES public.discounts(id) ON DELETE CASCADE;


--
-- Name: order_status_history fk_order_status_history_order; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_status_history
    ADD CONSTRAINT fk_order_status_history_order FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- Name: orders fk_orders_billing_address; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk_orders_billing_address FOREIGN KEY (billing_address_id) REFERENCES public.customer_addresses(id);


--
-- Name: orders fk_orders_shipping_address; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk_orders_shipping_address FOREIGN KEY (shipping_address_id) REFERENCES public.customer_addresses(id);


--
-- Name: reservation_items fk_reservation_items_inventory_item; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.reservation_items
    ADD CONSTRAINT fk_reservation_items_inventory_item FOREIGN KEY (inventory_item_id) REFERENCES public.inventory_items(id);


--
-- Name: fulfillment_addresses fulfillment_addresses_fulfillment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillment_addresses
    ADD CONSTRAINT fulfillment_addresses_fulfillment_id_fkey FOREIGN KEY (fulfillment_id) REFERENCES public.fulfillments(id) ON DELETE CASCADE;


--
-- Name: fulfillment_items fulfillment_items_fulfillment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillment_items
    ADD CONSTRAINT fulfillment_items_fulfillment_id_fkey FOREIGN KEY (fulfillment_id) REFERENCES public.fulfillments(id) ON DELETE CASCADE;


--
-- Name: fulfillment_items fulfillment_items_order_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillment_items
    ADD CONSTRAINT fulfillment_items_order_item_id_fkey FOREIGN KEY (order_item_id) REFERENCES public.order_items(id);


--
-- Name: fulfillment_labels fulfillment_labels_fulfillment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillment_labels
    ADD CONSTRAINT fulfillment_labels_fulfillment_id_fkey FOREIGN KEY (fulfillment_id) REFERENCES public.fulfillments(id) ON DELETE CASCADE;


--
-- Name: fulfillments fulfillments_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillments
    ADD CONSTRAINT fulfillments_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- Name: fulfillments fulfillments_shipping_option_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fulfillments
    ADD CONSTRAINT fulfillments_shipping_option_id_fkey FOREIGN KEY (shipping_option_id) REFERENCES public.shipping_options(id);


--
-- Name: inventory_levels inventory_levels_inventory_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.inventory_levels
    ADD CONSTRAINT inventory_levels_inventory_item_id_fkey FOREIGN KEY (inventory_item_id) REFERENCES public.inventory_items(id) ON DELETE CASCADE;


--
-- Name: mfa_factors mfa_factors_auth_identity_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.mfa_factors
    ADD CONSTRAINT mfa_factors_auth_identity_id_fkey FOREIGN KEY (auth_identity_id) REFERENCES public.auth_identities(id) ON DELETE CASCADE;


--
-- Name: mfa_recovery_codes mfa_recovery_codes_auth_identity_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.mfa_recovery_codes
    ADD CONSTRAINT mfa_recovery_codes_auth_identity_id_fkey FOREIGN KEY (auth_identity_id) REFERENCES public.auth_identities(id) ON DELETE CASCADE;


--
-- Name: order_addresses order_addresses_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_addresses
    ADD CONSTRAINT order_addresses_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: order_carts order_carts_cart_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_carts
    ADD CONSTRAINT order_carts_cart_id_fkey FOREIGN KEY (cart_id) REFERENCES public.carts(id) ON DELETE CASCADE;


--
-- Name: order_carts order_carts_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_carts
    ADD CONSTRAINT order_carts_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: order_credit_lines order_credit_lines_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_credit_lines
    ADD CONSTRAINT order_credit_lines_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: order_fulfillments order_fulfillments_fulfillment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_fulfillments
    ADD CONSTRAINT order_fulfillments_fulfillment_id_fkey FOREIGN KEY (fulfillment_id) REFERENCES public.fulfillments(id) ON DELETE CASCADE;


--
-- Name: order_fulfillments order_fulfillments_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_fulfillments
    ADD CONSTRAINT order_fulfillments_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: order_items order_items_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: order_items order_items_variant_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_variant_id_fkey FOREIGN KEY (variant_id) REFERENCES public.product_variants(id);


--
-- Name: order_summaries order_summaries_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_summaries
    ADD CONSTRAINT order_summaries_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: order_timelines order_timelines_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_timelines
    ADD CONSTRAINT order_timelines_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: order_transactions order_transactions_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_transactions
    ADD CONSTRAINT order_transactions_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: order_transactions order_transactions_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.order_transactions
    ADD CONSTRAINT order_transactions_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;


--
-- Name: orders orders_cart_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_cart_id_fkey FOREIGN KEY (cart_id) REFERENCES public.carts(id);


--
-- Name: orders orders_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: orders orders_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(id);


--
-- Name: orders orders_region_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_region_id_fkey FOREIGN KEY (region_id) REFERENCES public.regions(id);


--
-- Name: payment_collections payment_collections_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment_collections
    ADD CONSTRAINT payment_collections_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: payment_collections payment_collections_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment_collections
    ADD CONSTRAINT payment_collections_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- Name: payment_sessions payment_sessions_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment_sessions
    ADD CONSTRAINT payment_sessions_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: payment_sessions payment_sessions_payment_collection_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment_sessions
    ADD CONSTRAINT payment_sessions_payment_collection_id_fkey FOREIGN KEY (payment_collection_id) REFERENCES public.payment_collections(id) ON DELETE CASCADE;


--
-- Name: payment_sessions payment_sessions_payment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment_sessions
    ADD CONSTRAINT payment_sessions_payment_id_fkey FOREIGN KEY (payment_id) REFERENCES public.payments(id);


--
-- Name: payments payments_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT payments_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: payments payments_payment_collection_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT payments_payment_collection_id_fkey FOREIGN KEY (payment_collection_id) REFERENCES public.payment_collections(id) ON DELETE CASCADE;


--
-- Name: price_rules price_rules_price_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.price_rules
    ADD CONSTRAINT price_rules_price_id_fkey FOREIGN KEY (price_id) REFERENCES public.prices(id) ON DELETE CASCADE;


--
-- Name: prices prices_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.prices
    ADD CONSTRAINT prices_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: prices prices_price_list_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.prices
    ADD CONSTRAINT prices_price_list_id_fkey FOREIGN KEY (price_list_id) REFERENCES public.price_lists(id);


--
-- Name: prices prices_price_set_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.prices
    ADD CONSTRAINT prices_price_set_id_fkey FOREIGN KEY (price_set_id) REFERENCES public.price_sets(id) ON DELETE CASCADE;


--
-- Name: product_categories product_categories_parent_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_categories
    ADD CONSTRAINT product_categories_parent_category_id_fkey FOREIGN KEY (parent_category_id) REFERENCES public.product_categories(id);


--
-- Name: product_category_products product_category_products_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_category_products
    ADD CONSTRAINT product_category_products_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.product_categories(id) ON DELETE CASCADE;


--
-- Name: product_category_products product_category_products_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_category_products
    ADD CONSTRAINT product_category_products_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- Name: product_images product_images_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_images
    ADD CONSTRAINT product_images_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- Name: product_option_values product_option_values_option_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_option_values
    ADD CONSTRAINT product_option_values_option_id_fkey FOREIGN KEY (option_id) REFERENCES public.product_options(id) ON DELETE CASCADE;


--
-- Name: product_options product_options_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_options
    ADD CONSTRAINT product_options_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- Name: product_product_tags product_product_tags_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_product_tags
    ADD CONSTRAINT product_product_tags_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- Name: product_product_tags product_product_tags_tag_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_product_tags
    ADD CONSTRAINT product_product_tags_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES public.product_tags(id) ON DELETE CASCADE;


--
-- Name: product_variant_inventory_items product_variant_inventory_items_inventory_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_variant_inventory_items
    ADD CONSTRAINT product_variant_inventory_items_inventory_item_id_fkey FOREIGN KEY (inventory_item_id) REFERENCES public.inventory_items(id) ON DELETE CASCADE;


--
-- Name: product_variant_inventory_items product_variant_inventory_items_variant_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_variant_inventory_items
    ADD CONSTRAINT product_variant_inventory_items_variant_id_fkey FOREIGN KEY (variant_id) REFERENCES public.product_variants(id) ON DELETE CASCADE;


--
-- Name: product_variant_price_sets product_variant_price_sets_price_set_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_variant_price_sets
    ADD CONSTRAINT product_variant_price_sets_price_set_id_fkey FOREIGN KEY (price_set_id) REFERENCES public.price_sets(id) ON DELETE CASCADE;


--
-- Name: product_variant_price_sets product_variant_price_sets_variant_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_variant_price_sets
    ADD CONSTRAINT product_variant_price_sets_variant_id_fkey FOREIGN KEY (variant_id) REFERENCES public.product_variants(id) ON DELETE CASCADE;


--
-- Name: product_variants product_variants_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.product_variants
    ADD CONSTRAINT product_variants_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE;


--
-- Name: products products_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.product_categories(id);


--
-- Name: products products_collection_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_collection_id_fkey FOREIGN KEY (collection_id) REFERENCES public.product_collections(id);


--
-- Name: provider_identities provider_identities_auth_identity_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.provider_identities
    ADD CONSTRAINT provider_identities_auth_identity_id_fkey FOREIGN KEY (auth_identity_id) REFERENCES public.auth_identities(id) ON DELETE CASCADE;


--
-- Name: refunds refunds_created_by_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.refunds
    ADD CONSTRAINT refunds_created_by_fkey FOREIGN KEY (created_by) REFERENCES public.users(id);


--
-- Name: refunds refunds_payment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.refunds
    ADD CONSTRAINT refunds_payment_id_fkey FOREIGN KEY (payment_id) REFERENCES public.payments(id) ON DELETE CASCADE;


--
-- Name: region_countries region_countries_region_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.region_countries
    ADD CONSTRAINT region_countries_region_id_fkey FOREIGN KEY (region_id) REFERENCES public.regions(id) ON DELETE CASCADE;


--
-- Name: regions regions_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.regions
    ADD CONSTRAINT regions_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: shipping_option_rules shipping_option_rules_shipping_option_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.shipping_option_rules
    ADD CONSTRAINT shipping_option_rules_shipping_option_id_fkey FOREIGN KEY (shipping_option_id) REFERENCES public.shipping_options(id) ON DELETE CASCADE;


--
-- Name: shipping_options shipping_options_region_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.shipping_options
    ADD CONSTRAINT shipping_options_region_id_fkey FOREIGN KEY (region_id) REFERENCES public.regions(id);


--
-- Name: shipping_options shipping_options_shipping_profile_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.shipping_options
    ADD CONSTRAINT shipping_options_shipping_profile_id_fkey FOREIGN KEY (shipping_profile_id) REFERENCES public.shipping_profiles(id);


--
-- Name: store_currencies store_currencies_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.store_currencies
    ADD CONSTRAINT store_currencies_currency_code_fkey FOREIGN KEY (currency_code) REFERENCES public.currencies(code);


--
-- Name: store_currencies store_currencies_store_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.store_currencies
    ADD CONSTRAINT store_currencies_store_id_fkey FOREIGN KEY (store_id) REFERENCES public.stores(id) ON DELETE CASCADE;


--
-- Name: stores stores_default_currency_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.stores
    ADD CONSTRAINT stores_default_currency_code_fkey FOREIGN KEY (default_currency_code) REFERENCES public.currencies(code);


--
-- Name: users users_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- Name: verification_tokens verification_tokens_auth_identity_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.verification_tokens
    ADD CONSTRAINT verification_tokens_auth_identity_id_fkey FOREIGN KEY (auth_identity_id) REFERENCES public.auth_identities(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--
````

## File: src/main/resources/application-prod.yml
````yaml
spring:
  datasource:
    url: jdbc:postgresql://${DB_HOST:localhost}:5432/${DB_NAME:v8n_db}
    username: ${DB_USERNAME:v8n_ecom}
    password: '${DB_PASSWORD:#123456@}'
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

  flyway:
    enabled: true
    locations: classpath:db/migration
````

## File: bootRun.sh
````bash
#!/bin/bash

# Clear the log file before every run
> error-build.log

# Bắt sự kiện Ctrl+C (SIGINT) và gửi tín hiệu tắt (SIGTERM) tới toàn bộ process, sau đó xoá nội dung file log
trap '> error-build.log; kill -TERM 0' SIGINT

# ---- Đọc server port từ application.yml ----
APP_YML="src/main/resources/application.yml"
SERVER_PORT=$(sed -n '/^server:/,/^[a-z]/p' "$APP_YML" | grep -E '^\s+port:\s+[0-9]+' | awk '{print $2}')

if [ -z "$SERVER_PORT" ]; then
    echo "WARNING: Không tìm thấy port trong $APP_YML, mặc định dùng port 8080"
    SERVER_PORT=8080
fi

echo "Port được cấu hình: $SERVER_PORT"

# ---- Kill tiến trình đang chiếm port (nếu có) ----
PID=$(lsof -ti:$SERVER_PORT 2>/dev/null)
if [ -n "$PID" ]; then
    echo "Port $SERVER_PORT đang bị chiếm bởi PID $PID. Đang kill..."
    kill -15 "$PID" 2>/dev/null
    sleep 2
    # Kiểm tra lại, nếu chưa chết thì force kill
    if kill -0 "$PID" 2>/dev/null; then
        echo "PID $PID chưa tắt, force kill..."
        kill -9 "$PID" 2>/dev/null
    fi
    echo "Đã giải phóng port $SERVER_PORT"
else
    echo "Port $SERVER_PORT đang trống."
fi

# ---- Run ----
if [ "$1" == "log" ]; then
    echo "Running with log redirection to error-build.log..."
    ./gradlew bootRun 2>&1 | tee error-build.log
else
    ./gradlew bootRun
fi
````

## File: build.gradle
````
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.2.5'
    id 'io.spring.dependency-management' version '1.1.4'
}

ext {
    set('springCloudVersion', '2023.0.1')
    set('jjwtVersion', '0.12.5')
    set('mapstructVersion', '1.5.5.Final')

    // Đưa các phiên bản cố định vào đây để quản lý tập trung, dễ nâng cấp
    set('lombokVersion', '1.18.32')
    set('springBootVersion', '3.2.5')
    set('flywayVersion', '10.11.0')
    set('springdocVersion', '2.5.0')
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencyManagement {
    imports {
        mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
    }
}

dependencies {
    implementation project(':modules:core')
    implementation project(':modules:identity')
    implementation project(':modules:catalog')
    implementation project(':modules:inventory')
    implementation project(':modules:cart')
    implementation project(':modules:order')
    implementation project(':modules:payment')
    implementation project(':modules:fulfillment')
    implementation project(':modules:promotion')
    implementation project(':modules:notification')

    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-data-redis'
    implementation 'org.springframework.boot:spring-boot-starter-mail'
    implementation 'org.springframework.boot:spring-boot-starter-websocket'

    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'

    implementation "io.jsonwebtoken:jjwt-api:${jjwtVersion}"
    runtimeOnly "io.jsonwebtoken:jjwt-impl:${jjwtVersion}"
    runtimeOnly "io.jsonwebtoken:jjwt-jackson:${jjwtVersion}"

    runtimeOnly 'com.h2database:h2'
    runtimeOnly 'org.postgresql:postgresql'

    // Fix Flyway gốc bằng biến ext
    implementation "org.flywaydb:flyway-core:${flywayVersion}"
    implementation "org.flywaydb:flyway-database-postgresql:${flywayVersion}"

    implementation "org.mapstruct:mapstruct:${mapstructVersion}"
    annotationProcessor "org.mapstruct:mapstruct-processor:${mapstructVersion}"

    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
}

tasks.named('test') {
    useJUnitPlatform()
}

// Cố định tên file JAR để CI/CD không bị phụ thuộc vào version trong tên file
bootJar {
    archiveFileName = 'v8n-ecommerce.jar'
}

// Áp dụng cho TẤT CẢ các subprojects mà không cần điều kiện lọc phức tạp
subprojects {
    apply plugin: 'java-library'
    apply plugin: 'io.spring.dependency-management'

    group = 'com.v8n'
    version = '0.0.1-SNAPSHOT'

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        // Ép tất cả các module (kể cả module cha :modules rỗng nếu có bị quét qua)
        // đều nhận diện đúng version từ biến ext phía trên
        implementation "org.springframework.boot:spring-boot-starter-data-jpa:${springBootVersion}"
        implementation "org.springframework.boot:spring-boot-starter-validation:${springBootVersion}"

        compileOnly "org.projectlombok:lombok:${lombokVersion}"
        annotationProcessor "org.projectlombok:lombok:${lombokVersion}"

        testImplementation "org.springframework.boot:spring-boot-starter-test:${springBootVersion}"
    }

    tasks.withType(Test).configureEach {
        useJUnitPlatform()
    }
}
````

## File: .github/workflows/deploy.yml
````yaml
name: Deploy to VPS

on:
  push:
    branches: [main, dev]

concurrency:
  group: deploy-${{ github.ref }}
  cancel-in-progress: true

jobs:
  deploy-backend:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Cache Gradle packages
        uses: actions/cache@v4
        with:
          path: ~/.gradle/caches
          key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
          restore-keys: ${{ runner.os }}-gradle-

      - name: Build with Gradle
        run: ./gradlew clean build -x test

      - name: Upload fat JAR to VPS
        run: |
          mkdir -p ~/.ssh
          echo "${{ secrets.VPS_DEPLOY_KEY }}" > ~/.ssh/vps_deploy_key
          chmod 600 ~/.ssh/vps_deploy_key
          # User vps bị chroot jail vào /www/v8n_ecommerce/ → đường dẫn là /backend/ (chroot-relative)
          cd build/libs && scp -o StrictHostKeyChecking=no -o HostKeyAlgorithms=+ssh-ed25519 -i ~/.ssh/vps_deploy_key v8n-ecommerce.jar vps@${{ secrets.VPS_HOST }}:/backend/

      - name: Ensure & Restart service on VPS
        uses: appleboy/ssh-action@master
        with:
          host: ${{ secrets.VPS_HOST }}
          username: root
          key: ${{ secrets.VPS_SSH_KEY }}
          script: |
            SERVICE_FILE=/etc/systemd/system/v8n-ecommerce.service

            echo "Updating systemd service..."
            sudo tee "$SERVICE_FILE" > /dev/null << 'SVC_EOF'
            [Unit]
            Description=V8N Ecommerce Backend
            After=network.target postgresql.service

            [Service]
            User=root
            WorkingDirectory=/www/v8n_ecommerce/backend
            Environment="DB_HOST=${{ secrets.DB_HOST }}"
            Environment="DB_NAME=${{ secrets.DB_NAME }}"
            Environment="DB_USERNAME=${{ secrets.DB_USERNAME }}"
            Environment="DB_PASSWORD=${{ secrets.DB_PASSWORD }}"
            Environment="JWT_SECRET=${{ secrets.JWT_PROD_SECRET }}"
            ExecStart=/usr/bin/java -jar /www/v8n_ecommerce/backend/v8n-ecommerce.jar --spring.profiles.active=prod
            SuccessExitStatus=143
            TimeoutStopSec=10
            Restart=on-failure
            RestartSec=5

            [Install]
            WantedBy=multi-user.target
            SVC_EOF

            sudo systemctl daemon-reload
            sudo systemctl enable v8n-ecommerce
            sudo systemctl restart v8n-ecommerce
            sudo systemctl status v8n-ecommerce --no-pager
````
