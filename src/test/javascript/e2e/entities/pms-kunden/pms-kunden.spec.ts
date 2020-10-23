import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PmsKundenComponentsPage } from './pms-kunden.page-object';

const expect = chai.expect;

describe('PmsKunden e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let pmsKundenComponentsPage: PmsKundenComponentsPage;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PmsKundens', async () => {
    await navBarPage.goToEntity('pms-kunden');
    pmsKundenComponentsPage = new PmsKundenComponentsPage();
    await browser.wait(ec.visibilityOf(pmsKundenComponentsPage.title), 5000);
    expect(await pmsKundenComponentsPage.getTitle()).to.eq('elsaApp.pmsKunden.home.title');
    await browser.wait(ec.or(ec.visibilityOf(pmsKundenComponentsPage.entities), ec.visibilityOf(pmsKundenComponentsPage.noResult)), 1000);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
