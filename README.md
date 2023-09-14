# TestingHabitPresentation

## Subindo o ambiente:

* No diretório demotests, rode o comando `gradlew bootRun`
* No diretório front-end, rode o comando `npm install && npm start`

## Testes cypress:

* Com o ambiente de pé, acesse o diretório front-end e rode o comando `npm run cypress`

## Testes de coleção Postman:

* Rodar o comando na raiz, para o `Newman` rodar os testes de coleção e gerar o relatório:
`npx newman run TestingHabitPresentationCollection.postman_collection.json -r htmlextra --reporter-htmlextra-browserTitle "TestingHabits" --reporter-htmlextra-title "TestingHabits" --reporter-htmlextra-titleSize "12" --reporter-htmlextra-timezone "Brazil/East"`

## Relatórios Allure:

* No diretório demotests, rode o comando `gradlew clean build && cd reports && npx allure-commandline serve`