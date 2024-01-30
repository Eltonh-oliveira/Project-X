# Project-X

Ideia desse projeto é construir o BackEnd de um mini sistema para gestão de grupos.

Primeiro EndPoint criado.

POST - http://localhost:8080/functionary/
body : {
"id" : "",
"userName" : "elton",
"password" : "elton123",
"description" : "tem 22 anos",
"isActive" : true,
"identifier" : "09832423",
"contacts" : [
{
"label" : "47996330692",
"type" : "d609a294-d8da-425c-9480-1f48ea647cbd"
},
{
"label" : "elton.h.oli@gmail.com",
"type" : "d83de7c2-5bc5-47aa-963d-c70ff0938e6a"
}
]
}

PUT - http://localhost:8080/functionary/
body : {
"id" : "", //deve ser informado para ser feito alteração.
"userName" : "elton",
"password" : "elton123",
"description" : "tem 22 anos",
"isActive" : true,
"identifier" : "09832423",
"contacts" : [
{
"label" : "47996330692",
"type" : "d609a294-d8da-425c-9480-1f48ea647cbd"
},
{
"label" : "elton.h.oli@gmail.com",
"type" : "d83de7c2-5bc5-47aa-963d-c70ff0938e6a"
}
]
}