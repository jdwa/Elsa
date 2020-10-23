import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AppFunctionComponentsPage, AppFunctionDeleteDialog, AppFunctionUpdatePage } from './app-function.page-object';

const expect = chai.expect;

describe('AppFunction e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let appFunctionComponentsPage: AppFunctionComponentsPage;
  let appFunctionUpdatePage: AppFunctionUpdatePage;
  let appFunctionDeleteDialog: AppFunctionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AppFunctions', async () => {
    await navBarPage.goToEntity('app-function');
    appFunctionComponentsPage = new AppFunctionComponentsPage();
    await browser.wait(ec.visibilityOf(appFunctionComponentsPage.title), 5000);
    expect(await appFunctionComponentsPage.getTitle()).to.eq('elsaApp.appFunction.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(appFunctionComponentsPage.entities), ec.visibilityOf(appFunctionComponentsPage.noResult)),
      1000
    );
  });

  it('should load create AppFunction page', async () => {
    await appFunctionComponentsPage.clickOnCreateButton();
    appFunctionUpdatePage = new AppFunctionUpdatePage();
    expect(await appFunctionUpdatePage.getPageTitle()).to.eq('elsaApp.appFunction.home.createOrEditLabel');
    await appFunctionUpdatePage.cancel();
  });

  it('should create and save AppFunctions', async () => {
    const nbButtonsBeforeCreate = await appFunctionComponentsPage.countDeleteButtons();

    await appFunctionComponentsPage.clickOnCreateButton();

    await promise.all([appFunctionUpdatePage.setNameInput('name'), appFunctionUpdatePage.setDescriptionInput('description')]);

    expect(await appFunctionUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await appFunctionUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await appFunctionUpdatePage.save();
    expect(await appFunctionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await appFunctionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last AppFunction', async () => {
    const nbButtonsBeforeDelete = await appFunctionComponentsPage.countDeleteButtons();
    await appFunctionComponentsPage.clickOnLastDeleteButton();

    appFunctionDeleteDialog = new AppFunctionDeleteDialog();
    expect(await appFunctionDeleteDialog.getDialogTitle()).to.eq('elsaApp.appFunction.delete.question');
    await appFunctionDeleteDialog.clickOnConfirmButton();

    expect(await appFunctionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
