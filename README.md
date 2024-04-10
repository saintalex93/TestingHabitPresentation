# TestingHabitPresentation
O projeto será utilizado para a apresentação "O hábito de criar testes como parte da entrega". Ele possui um Back-end escrito em Java com Spring Web, Front-end com Angular, banco de dados H2 em memória, testes em JUnit, Cypress e Postman Collections.
Os relatórios são feitos com o Allure, Jacoco e Newman.

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

## Vídeo da Apresentação TOTVS Developers:
https://www.youtube.com/watch?v=gCmzwPAj8ng&ab_channel=TOTVSDevelopers
