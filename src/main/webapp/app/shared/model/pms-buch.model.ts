import { Moment } from 'moment';

export interface IPmsBuch {
  id?: number;
  datumvon?: Moment;
  datumbis?: Moment;
}

export class PmsBuch implements IPmsBuch {
  constructor(public id?: number, public datumvon?: Moment, public datumbis?: Moment) {}
}
