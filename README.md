
# Revolut App
## Run the app

    mvn clean install
    mvn exec:java -Dexec.mainClass="com.revolut.rest.TransferMoneyApp"

## Technologies

 - Jersey
 - Lombock
 - Jersey test framework

## Entry Points

**API Documentation**
https://web.postman.co/collections/2128520-60b3e4f6-7aaf-42f1-8169-df20f6cbd488?workspace=4a82284a-4183-44c8-8f27-94bedaf2e59f

**Create account**
http://localhost:8090/rest/account/create/1
or
http://localhost:8090/rest/account/create/
{"id":"1","balance":0.0}
{"id":"ee2dd106-81d3-42dc-a5d9-f051688e89ca","balance":0.0}

**Deposit**
http://localhost:8090/rest/account/deposit?accountId=1&amoount=100
{"id":"1","balance":100.0}

**Withdraw**
http://localhost:8090/rest/account/withdraw?accountId=1&amoount=20
{"id":"1","balance":80.0}

**List all**
http://localhost:8090/rest/account/all
[{"id":"1","balance":0.0},{"id":"ee2dd106-81d3-42dc-a5d9-f051688e89ca","balance":0.0}]

**Transfer Money**
http://localhost:8090/rest/transfer

**Request**
{
	"fromAccount" : "1",
	"toAccount": "2",
	"amount": 20
}
**Response**
{
    "fromAccount": {
        "id": "1",
        "balance": 80
    },
    "toAccount": {
        "id": "2",
        "balance": 20
    }
}


