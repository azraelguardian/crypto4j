# Crypto4j

## Docs

* [Binance Api](https://github.com/binance-exchange/binance-official-api-docs)
* [Huobi Api](https://github.com/huobiapi/API_Docs)
* [Okex Spot Api](https://github.com/okcoin-okex/API-docs-OKEx.com/tree/master/API-For-Spot-CN)

## Hosts

```
54.95.204.136 stream.binance.com
#54.92.26.88 stream.binance.com
47.90.109.236 real.okex.com
104.16.230.188 api.huobi.pro
52.85.2.55 api.binance.com
104.25.20.25 www.okex.com
```

## Order

|         | Has IOC | Has User Data Websocket | Has Execution/Trade Details | Comment                          |
|---------|---------|-------------------------|-----------------------------|----------------------------------|
| Binance | YES     | YES                     | YES                         | Can't Query Trades from order Id |
| Huobi   | YES     | NO                      | YES                         | Trade Query has delay            |
| Okex    | NO      | YES                     | NO                          |                                  |

### Binance Order

#### Place Order 

* req: recvWindow=5000&timestamp=1532401141439&symbol=BTCUSDT&side=SELL&type=LIMIT&timeInForce=IOC&quantity=0.01&price=7700
* res: OrderResponse(symbol=BTCUSDT, orderId=136685394, clientOrderId=3KSTatVeIcySChuZG42OCw, transactTime=1532401146328, price=7700.00000000, origQty=0.01000000, executedQty=0.01000000, status=FILLED, timeInForce=IOC, type=LIMIT, side=SELL, fills=[Fill(price=7737.99000000, qty=0.01000000, commission=0.07737990, commissionAsset=USDT)])

#### Query Order

* req: /api/v3/order?recvWindow=5000&timestamp=1532402232610&symbol=BTCUSDT&orderId=136685394&origClientOrderId=3KSTatVeIcySChuZG42OCw&signature=xxx
* res: {"symbol":"BTCUSDT","orderId":136685394,"clientOrderId":"3KSTatVeIcySChuZG42OCw","price":"7700.00000000","origQty":"0.01000000","executedQty":"0.01000000","cummulativeQuoteQty":"77.37990000","status":"FILLED","timeInForce":"IOC","type":"LIMIT","side":"SELL","stopPrice":"0.00000000","icebergQty":"0.00000000","time":1532401146328,"updateTime":1532401146328,"isWorking":true}

#### Query Execution/Trade

* req: /api/v3/myTrades
* res: {"symbol":"BTCUSDT","id":58523157,"orderId":136685394,"price":"7737.99000000","qty":"0.01000000","commission":"0.07737990","commissionAsset":"USDT","time":1532401146328,"isBuyer":false,"isMaker":false,"isBestMatch":true}
* one order with multi trades: {"symbol":"BTCUSDT","id":58346827,"orderId":136276302,"price":"7756.50000000","qty":"0.00846500","commission":"0.06565877","commissionAsset":"USDT","time":1532331223480,"isBuyer":false,"isMaker":false,"isBestMatch":true},{"symbol":"BTCUSDT","id":58346828,"orderId":136276302,"price":"7755.86000000","qty":"0.02153500","commission":"0.16702245","commissionAsset":"USDT","time":1532331223480,"isBuyer":false,"isMaker":false,"isBestMatch":true}

### Huobi Order

#### Place Order 

* req: {"amount":0.08,"price":7000,"source":null,"symbol":"btcusdt","type":"sell-ioc","account-id":4275858}
* res: RestResponse(status=ok, errCode=null, errMsg=null, data=8467743061)

#### Query Order

* req: https://api.huobi.pro/v1/order/orders/8467743061
* res: RestResponse(status=ok, errCode=null, errMsg=null, data=OrderResult(id=8467743061, symbol=btcusdt, accountId=4275858, amount=0.080000000000000000, price=7000.000000000000000000, createdAt=1532398948811, type=SELL_IOC, fieldAmount=0.080000000000000000, fieldCashAmount=620.125600000000000000, fieldFees=1.240251200000000000, finishedAt=1532398949035, source=spot-api, state=filled, canceledAt=0))

#### Query Execution/Trade

* req: https://api.huobi.pro/v1/order/orders/8467743061/matchresults
* res: {"status":"ok","data":[{"id":2130365761,"order-id":8467743061,"match-id":13514172747,"symbol":"btcusdt","type":"sell-ioc","source":"spot-api","price":"7751.570000000000000000","filled-amount":"0.080000000000000000","filled-fees":"1.240251200000000000","filled-points":"0.0","created-at":1532398949074}]}

### Okex Order

#### Place Order 

* req: amount=0.09&api_key=xxx&price=7823&symbol=btc_usdt&type=buy&sign=xxx
* res: OrderResponse(result=true, orderId=848476058, errorCode=null)

#### Query Order

* req: api_key=xxx&order_id=848476058&symbol=btc_usdt&sign=xxx
* res: QueryOrderResponse(result=true, orders=[OrderResult(amount=0.09, avgPrice=7820.70847925, createDate=1532407241000, dealAmount=0, orderId=848476058, ordersId=848476058, price=7823, status=FILLED, symbol=btc_usdt, type=buy)])


#### Query Execution/Trade

Does not support

