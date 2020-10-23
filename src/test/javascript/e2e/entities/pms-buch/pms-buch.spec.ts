import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PmsBuchComponentsPage } from './pms-buch.page-object';

const expect = chai.expect;

describe('PmsBuch e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let pmsBuchComponentsPage: PmsBuchComponentsPage;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PmsBuches', async () => {
    await navBarPage.goToEntity('pms-buch');
    pmsBuchComponentsPage = new PmsBuchComponentsPage();
    await browser.wait(ec.visibilityOf(pmsBuchComponentsPage.title), 5000);
    expect(await pmsBuchComponentsPage.getTitle()).to.eq('elsaApp.pmsBuch.home.title');
    await browser.wait(ec.or(ec.visibilityOf(pmsBuchComponentsPage.entities), ec.visibilityOf(pmsBuchComponentsPage.noResult)), 1000);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
