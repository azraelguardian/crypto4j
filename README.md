# crypto4j

## Order

### Huobi Order

#### Place Order 

* req: {"amount":0.08,"price":7000,"source":null,"symbol":"btcusdt","type":"sell-ioc","account-id":4275858}
* res: RestResponse(status=ok, errCode=null, errMsg=null, data=8467743061)

#### Query Order

* req: https://api.huobi.pro/v1/order/orders/8467743061
* res: RestResponse(status=ok, errCode=null, errMsg=null, data=OrderResult(id=8467743061, symbol=btcusdt, accountId=4275858, amount=0.080000000000000000, price=7000.000000000000000000, createdAt=1532398948811, type=SELL_IOC, fieldAmount=0.080000000000000000, fieldCashAmount=620.125600000000000000, fieldFees=1.240251200000000000, finishedAt=1532398949035, source=spot-api, state=filled, canceledAt=0))

#### Query Execution

* req: https://api.huobi.pro/v1/order/orders/8467743061/matchresults
* res: {"status":"ok","data":[{"id":2130365761,"order-id":8467743061,"match-id":13514172747,"symbol":"btcusdt","type":"sell-ioc","source":"spot-api","price":"7751.570000000000000000","filled-amount":"0.080000000000000000","filled-fees":"1.240251200000000000","filled-points":"0.0","created-at":1532398949074}]}


