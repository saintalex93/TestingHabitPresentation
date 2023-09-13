describe('Should open the system', () => {
  it('passes', () => {
    cy.visit('/')
  })
})

describe('Should open invalid user modal', () => {
  it('passes', () => {
    login("invalid_user@totvs.com.br")
    cy.get('.swal2-popup').contains("Usuário não encontrado.")
    cy.get('.swal2-confirm').click();
  })
})

describe('Should login and redirect to home', () => {
  it('passes', () => {
    login("alex.santos@totvs.com.br")
    cy.get('h1').contains("O hábito de enxergar os testes como parte da entrega.")
  })
})


describe('Should sign out', () => {
  it('passes', () => {
    login("alex.santos@totvs.com.br")
    cy.get('.nav-link').click()

    cy.url().should('eq', 'http://localhost:4200/')
  })
})

function login(email: string, password: string = "Teste@123") {
  cy.visit('/')
  cy.get(':nth-child(1) > .form-control').click().type(email);
  cy.get(':nth-child(2) > .form-control').click().type(password)
  cy.get('.btn').click()
}

