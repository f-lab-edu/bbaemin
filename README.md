# bbaemin
| API                       | 분류   | 설명   |
|---------------------------|------|------|
| api/v1                    |||
| GET /category             | 카테고리 | 리스트  |
| GET /category/{idx}       | 카테고리 | 상세   |
| POST /category            | 카테고리 | 등록   |
| PUT /category/{idx}       | 카테고리 | 수정   |
| GET /cart                 | 장바구니 | 리스트  |
| POST /cart/{itemId}       | 장바구니 | 추가   |
| PATCH /cart/{orderItemId} | 장바구니 | 수량변경 |
| DELETE /cart              | 장바구니 | 삭제   |
| GET /orders               | 주문   | 리스트  |
| GET /orders/{orderId}     | 주문   | 상세   |
| POST /orders              | 주문   | 등록   |
| PATCH /orders/{orderId}   | 주문   | 취소   |
| DELETE /orders/{orderId}  | 주문   | 삭제   |
| GET /item                 | 상품   | 리스트  |
| GET /item/{itemId}        | 상품   | 상세   |
| POST /item                | 상품   | 등록   |
| PUT /item/{itemId}        | 상품   | 수정   |
| DELETE /item/{itemId}     | 상품   | 삭제   |
| GET /users                | 회원   | 리스트  |
| GET /users/{userId}       | 회원   | 상세   |
| POST /users               | 회원   | 등록   |
| PUT /users/{userId}       | 회원   | 수정   |
| PATCH /users/{userId}     | 회원   | 탈퇴   |
