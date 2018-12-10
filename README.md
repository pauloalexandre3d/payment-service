
### API
Create Payment

```sh
{
	"client": {
		"id": "0"
	},
	"buyer": {
		"nome": "nome",
		"email": "email",
		"cpf": "cpf"
	},
	"payment": {
		"amount": 0,
		"type": "CREDIT_CARD",
		"card": {
			"holder_name": "Holder Name",
			"number:": "1111 2222 3333 4444",
			"cvv": "123"
		}
	}
}
```