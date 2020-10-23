import { element, by } from 'protractor';

export class PmsKundenComponentsPage {
  title = element.all(by.css('jhi-pms-kunden div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}
