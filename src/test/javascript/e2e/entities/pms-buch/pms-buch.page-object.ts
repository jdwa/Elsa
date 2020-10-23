import { element, by } from 'protractor';

export class PmsBuchComponentsPage {
  title = element.all(by.css('jhi-pms-buch div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}
