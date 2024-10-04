describe('API Category Tests', () => {
    it('should create a new category', () => {
      cy.request({
        method: 'POST',
        url: '/categories', 
        body: {
          name: 'New Category', 
          description: 'This is a new category'
        }
      }).then((response) => {
        expect(response.status).to.eq(200); 
        expect(response.body).to.have.property('name', 'New Category'); 
      });
    });
  });
  
  describe('API Category Tests', () => {
    it('should list all categories', () => {
      cy.request('GET', '/categories').then((response) => {
        expect(response.status).to.eq(200); 
        expect(response.body).to.be.an('array'); 
        expect(response.body.length).to.be.greaterThan(0); 
      });
    });
  });